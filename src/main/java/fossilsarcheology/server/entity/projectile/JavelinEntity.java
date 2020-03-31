package fossilsarcheology.server.entity.projectile;

import fossilsarcheology.server.item.FAItemRegistry;
import io.netty.buffer.ByteBuf;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityEnderman;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.play.server.SPacketChangeGameState;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.registry.IEntityAdditionalSpawnData;

public class JavelinEntity extends EntityArrow implements IEntityAdditionalSpawnData {
    public Item.ToolMaterial material;
    protected int itemDamage;
    protected int newKnockBackStrength;

    public JavelinEntity(World world) {
        super(world);
    }

    public JavelinEntity(World world, Item.ToolMaterial material, int itemDamage, double x, double y, double z) {
        this(world);
        this.itemDamage = itemDamage;
        this.material = material;
        this.setDamage(getDamageFromMaterial(material));
        this.setPosition(x, y, z);
    }

    public JavelinEntity(World world, Item.ToolMaterial material, int damage, EntityLivingBase shooter) {
        this(world, material, damage, shooter.posX, shooter.posY + shooter.getEyeHeight() - 0.1, shooter.posZ);
        this.shootingEntity = shooter;
        if (shooter instanceof EntityPlayer) {
            this.pickupStatus = EntityArrow.PickupStatus.ALLOWED;
        }
        this.setDamage(getDamageFromMaterial(material));
    }

    public void setKnockbackStrength(int knockbackStrengthIn) {
        this.newKnockBackStrength = knockbackStrengthIn;
    }

    protected void onHit(RayTraceResult raytraceResultIn) {
        Entity entity = raytraceResultIn.entityHit;

        if (entity != null) {
            float f = MathHelper.sqrt(this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ);
            int i = MathHelper.ceil((double) f * this.getDamageFromMaterial(material));

            if (this.getIsCritical()) {
                i += this.rand.nextInt(i / 2 + 2);
            }

            DamageSource damagesource;

            if (this.shootingEntity == null) {
                damagesource = DamageSource.causeArrowDamage(this, this);
            } else {
                damagesource = DamageSource.causeArrowDamage(this, this.shootingEntity);
            }

            if (this.isBurning() && !(entity instanceof EntityEnderman)) {
                entity.setFire(5);
            }

            if (entity.attackEntityFrom(damagesource, (float) i)) {
                if (entity instanceof EntityLivingBase) {
                    EntityLivingBase entitylivingbase = (EntityLivingBase) entity;

                    if (!this.world.isRemote) {
                        entitylivingbase.setArrowCountInEntity(entitylivingbase.getArrowCountInEntity() + 1);
                    }

                    if (this.newKnockBackStrength > 0) {
                        float f1 = MathHelper.sqrt(this.motionX * this.motionX + this.motionZ * this.motionZ);

                        if (f1 > 0.0F) {
                            entitylivingbase.addVelocity(this.motionX * (double) this.newKnockBackStrength * 0.6000000238418579D / (double) f1, 0.1D, this.motionZ * (double) this.newKnockBackStrength * 0.6000000238418579D / (double) f1);
                        }
                    }

                    if (this.shootingEntity instanceof EntityLivingBase) {
                        EnchantmentHelper.applyThornEnchantments(entitylivingbase, this.shootingEntity);
                        EnchantmentHelper.applyArthropodEnchantments((EntityLivingBase) this.shootingEntity, entitylivingbase);
                    }

                    this.arrowHit(entitylivingbase);

                    if (this.shootingEntity != null && entitylivingbase != this.shootingEntity && entitylivingbase instanceof EntityPlayer && this.shootingEntity instanceof EntityPlayerMP) {
                        ((EntityPlayerMP) this.shootingEntity).connection.sendPacket(new SPacketChangeGameState(6, 0.0F));
                    }
                }

                this.playSound(SoundEvents.ENTITY_ARROW_HIT, 1.0F, 1.2F / (this.rand.nextFloat() * 0.2F + 0.9F));
            } else {
                this.motionX *= -0.10000000149011612D;
                this.motionY *= -0.10000000149011612D;
                this.motionZ *= -0.10000000149011612D;
                this.rotationYaw += 180.0F;
                this.prevRotationYaw += 180.0F;
                if (!this.world.isRemote && this.motionX * this.motionX + this.motionY * this.motionY + this.motionZ * this.motionZ < 0.0010000000474974513D) {
                    if (this.pickupStatus == EntityArrow.PickupStatus.ALLOWED) {
                        this.entityDropItem(this.getArrowStack(), 0.1F);
                    }

                    this.setDead();
                }
            }
        } else {
            super.onHit(raytraceResultIn);
        }
    }

    private double getDamageFromMaterial(Item.ToolMaterial material) {
        switch (material) {
            case WOOD:
                return 2D;
            case STONE:
                return 3D;
            case IRON:
                return 4D;
            case GOLD:
                return 5D;
            case DIAMOND:
                return 7D;
        }
        return 2D;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        this.ticksInGround = 0;
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setByte("Material", (byte) this.material.ordinal());
        compound.setInteger("Damage", this.itemDamage);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.material = Item.ToolMaterial.values()[compound.getByte("Material")];
        this.itemDamage = compound.getInteger("Damage");
    }

    @Override
    protected ItemStack getArrowStack() {
        switch (this.material) {
            case WOOD:
                return new ItemStack(FAItemRegistry.WOODEN_JAVELIN, 1, FAItemRegistry.WOODEN_JAVELIN.getMaxDamage() - itemDamage);
            case STONE:
                return new ItemStack(FAItemRegistry.STONE_JAVELIN, 1, FAItemRegistry.STONE_JAVELIN.getMaxDamage() - itemDamage);
            case IRON:
                return new ItemStack(FAItemRegistry.IRON_JAVELIN, 1, FAItemRegistry.IRON_JAVELIN.getMaxDamage() - itemDamage);
            case GOLD:
                return new ItemStack(FAItemRegistry.GOLD_JAVELIN, 1, FAItemRegistry.GOLD_JAVELIN.getMaxDamage() - itemDamage);
            case DIAMOND:
                return new ItemStack(FAItemRegistry.DIAMOND_JAVELIN, 1, FAItemRegistry.DIAMOND_JAVELIN.getMaxDamage() - itemDamage);
        }
        return null;
    }

    @Override
    public void writeSpawnData(ByteBuf buffer) {
        buffer.writeByte(this.material.ordinal());
    }

    @Override
    public void readSpawnData(ByteBuf buffer) {
        this.material = Item.ToolMaterial.values()[buffer.readByte()];
    }
}
