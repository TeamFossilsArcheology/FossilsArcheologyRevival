package fossilsarcheology.server.entity.monster;

import fossilsarcheology.Revival;
import fossilsarcheology.server.block.FABlockRegistry;
import fossilsarcheology.server.item.FAItemRegistry;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.Item;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;

import javax.annotation.Nullable;

public class EntityTarSlime extends EntitySlime {

    public static final ResourceLocation LOOT = LootTableList.register(new ResourceLocation("fossil", "tar_slime"));


    public EntityTarSlime(World world) {
        super(world);
    }

    @Override
    protected EnumParticleTypes getParticleType() {
        return EnumParticleTypes.SUSPENDED_DEPTH;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damageSource, float f) {
        if (damageSource.getTrueSource() != null && this.getRidingEntity() != null && this.getRidingEntity().isEntityEqual(damageSource.getTrueSource())) {
            if (rand.nextBoolean()) {
                this.dismountRidingEntity();
            }
        }
        return super.attackEntityFrom(damageSource, f);
    }

    @Override
    protected EntitySlime createInstance() {
        return new EntityTarSlime(this.world);
    }

    @Override
    protected float getSoundPitch() {
        return 0.5F;
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return this.getSlimeSize() == 1 ? LOOT : LootTableList.EMPTY;
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        int j = MathHelper.floor(this.posX);
        int k = MathHelper.floor(this.posY);
        int l = MathHelper.floor(this.posZ);
        if (this.world.getBlockState(new BlockPos(j, k, l)).getBlock() == FABlockRegistry.TAR) {
            this.motionY *= 1.3;
        }
        if (this.isBurning()) {
            this.setFire(10);
        }
        boolean flag = onGround;
        if (onGround && !flag || this.isJumping) {
            this.playSound(this.getJumpSound(), this.getSoundVolume(), getSoundPitch() * ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) / 0.8F);
        }

    }

    @Override
    public void setDead() {
        int i = this.getSlimeSize();

        if (!this.world.isRemote && i > 1 && this.getHealth() <= 0.0F) {
            int j = 2 + this.rand.nextInt(3);

            for (int k = 0; k < j; ++k) {
                float f = ((float) (k % 2) - 0.5F) * (float) i / 4.0F;
                float f1 = ((float) (k / 2) - 0.5F) * (float) i / 4.0F;
                EntityTarSlime entityslime = new EntityTarSlime(this.world);
                if (this.getFlag(0)) {
                    entityslime.setFire(15);
                }
                if (this.hasCustomName()) {
                    entityslime.setCustomNameTag(this.getCustomNameTag());
                }
                if (this.isNoDespawnRequired()) {
                    entityslime.enablePersistence();
                }

                entityslime.setSlimeSize(i / 2, true);
                entityslime.setLocationAndAngles(this.posX + (double) f, this.posY + 0.5D, this.posZ + (double) f1, this.rand.nextFloat() * 360.0F, 0.0F);
                this.world.spawnEntity(entityslime);
            }
        }
        this.isDead = true;
    }

    @Override
    protected void alterSquishAmount() {
        this.squishAmount *= 1F;
    }

    @Override
    public void updateRidden() {
        super.updateRidden();
        if (this.getRidingEntity() != null) {
            if (!this.getRidingEntity().isEntityAlive()) {
                this.dismountRidingEntity();
            }
            if (this.getRidingEntity() instanceof EntityPlayer) {
                this.rotationYaw = ((EntityPlayer) getRidingEntity()).rotationYawHead;
                this.setPosition(posX, posY, posZ);
            }
            if (getRidingEntity() instanceof EntityLivingBase) {
                ((EntityLivingBase) getRidingEntity()).addPotionEffect(new PotionEffect(MobEffects.BLINDNESS, 100, 1));
            }
            if (this.ticksExisted % 20 == 0) {
                this.getRidingEntity().attackEntityFrom(DamageSource.causeMobDamage(this), this.getSlimeSize());
                this.playSound(this.getJumpSound(), this.getSoundVolume(), getSoundPitch() * ((this.rand.nextFloat() - this.rand.nextFloat()) * 0.2F + 1.0F) / 0.8F);
                squishFactor = 0.7F;
            } else {
                squishFactor = 0.0F;
            }
            // alterSquishAmount();
        }
    }

    @Override
    protected Item getDropItem() {
        return FAItemRegistry.TARDROP;
    }

    @Override
    public void onCollideWithPlayer(EntityPlayer player) {
        super.onCollideWithPlayer(player);
        if (this.rand.nextInt(6) == 0 && player.getPassengers().isEmpty()) {
            if (!player.capabilities.isCreativeMode) {
                this.startRiding(player);
            }
        }
    }

    protected boolean spawnCustomParticles() {
        int i = this.getSlimeSize();
        for (int j = 0; j < i * 4; ++j) {
            float f = this.rand.nextFloat() * ((float) Math.PI * 2F);
            float f1 = this.rand.nextFloat() * 0.5F + 0.5F;
            float f2 = MathHelper.sin(f) * (float) i * 0.5F * f1;
            float f3 = MathHelper.cos(f) * (float) i * 0.5F * f1;
            double d0 = this.posX + (double) f2;
            double d1 = this.posZ + (double) f3;
            Revival.PROXY.spawnFAParticle("tar_bubble", (float)d0, (float)this.getEntityBoundingBox().minY, (float)d1, 0.0D, 0.0D, 0.0D);
        }

        return true;
    }

    @Override
    protected boolean makesSoundOnJump() {
        return false;
    }

    @Override
    public boolean isEntityInsideOpaqueBlock() {
        for (int i = 0; i < 8; ++i) {
            float f = ((float) ((i) % 2) - 0.5F) * this.width * 0.8F;
            float f1 = ((float) ((i >> 1) % 2) - 0.5F) * 0.1F;
            float f2 = ((float) ((i >> 2) % 2) - 0.5F) * this.width * 0.8F;
            int j = MathHelper.floor(this.posX + (double) f);
            int k = MathHelper.floor(this.posY + (double) this.getEyeHeight() + (double) f1);
            int l = MathHelper.floor(this.posZ + (double) f2);

            if (this.world.getBlockState(new BlockPos(j, k, l)).isNormalCube() && this.world.getBlockState(new BlockPos(j, k, l)).getBlock() != FABlockRegistry.TAR) {
                return true;
            }
        }

        return false;
    }
}
