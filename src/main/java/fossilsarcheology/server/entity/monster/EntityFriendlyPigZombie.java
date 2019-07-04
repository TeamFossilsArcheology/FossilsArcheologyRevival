package fossilsarcheology.server.entity.monster;

import net.minecraft.block.Block;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.passive.EntityHorse;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityArrow;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

public class EntityFriendlyPigZombie extends EntityTameable {

    public EntityFriendlyPigZombie(World var1) {
        super(var1);
        this.setSize(0.5F, 1.9F);
        this.isImmuneToFire = true;
        this.aiSit = new EntityAISit(this);
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, this.aiSit);
        this.tasks.addTask(3, new EntityAIAttackMelee(this, 1.0D, true));
        this.tasks.addTask(4, new EntityAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
        this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new EntityAIOwnerHurtTarget(this));
        this.targetTasks.addTask(3, new EntityAIHurtByTarget(this, true));
        this.setTamed(false);
    }

    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.UNDEAD;
    }

    @Override
    public void onKillEntity(EntityLivingBase entity) {
        super.onKillEntity(entity);
        sendMessageToOwner("pigman.kill");
    }

    @Override
    public void setDead() {
        if (!this.world.isRemote && isTamed() && getHealth() > 0) {
            return;
        }
        super.setDead();
    }

    @Override
    public boolean attackEntityFrom(DamageSource damage, float f) {
        if (this.isEntityInvulnerable(damage)) {
            return false;
        } else {
            Entity entity = damage.getTrueSource();
            this.aiSit.setSitting(false);

            if (entity != null && !(entity instanceof EntityPlayer) && !(entity instanceof EntityArrow)) {
                f = (f + 1.0F) / 2.0F;
            }

            return super.attackEntityFrom(damage, f);
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        boolean flag = entity.attackEntityFrom(DamageSource.causeMobDamage(this), 5);

        if (flag) {
            this.swingArm(EnumHand.MAIN_HAND);
            int i = this.world.getDifficulty().getId();

            if (this.getHeldItemMainhand().isEmpty() && this.isBurning() && this.rand.nextFloat() < (float) i * 0.3F) {
                entity.setFire(2 * i);
            }
        }

        return flag;
    }

    @Override
    protected void playStepSound(BlockPos pos, Block blockIn) {
        this.playSound(SoundEvents.ENTITY_ZOMBIE_STEP, 0.15F, 1.0F);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.30000001192092896D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
        if (this.isTamed()) {
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(25.0D);
        } else {
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
        }
    }


    public void sendMessageToOwner(String words) {
        if (this.getOwner() instanceof EntityPlayer) {
            ((EntityPlayer) this.getOwner()).sendStatusMessage(new TextComponentTranslation(words), false);
        }
    }

    @Override
    public boolean shouldAttackEntity(EntityLivingBase entity, EntityLivingBase thisMobsOwner) {

        if (entity instanceof EntityWolf) {
            EntityWolf entitywolf = (EntityWolf) entity;
            if (entitywolf.isTamed() && entitywolf.getOwner() == thisMobsOwner) {
                return false;
            }
        }
        if (entity instanceof EntityFriendlyPigZombie) {
            EntityFriendlyPigZombie entitywfpz = (EntityFriendlyPigZombie) entity;

            if (entitywfpz.isTamed() && entitywfpz.getOwner() == thisMobsOwner) {
                return false;
            }
        }

        return !(entity instanceof EntityPlayer && thisMobsOwner instanceof EntityPlayer && !((EntityPlayer) thisMobsOwner).canAttackPlayer((EntityPlayer) entity)) && (!(entity instanceof EntityHorse) || !((EntityHorse) entity).isTame());

    }


    @Override
    public boolean isAIDisabled() {
        return false;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.ENTITY_ZOMBIE_PIG_AMBIENT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource soucre) {
        return SoundEvents.ENTITY_ZOMBIE_PIG_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_ZOMBIE_PIG_DEATH;
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        if (this.isTamed()) {
            if (!itemstack.isEmpty()) {
                if (itemstack.getItem() == Items.GOLD_NUGGET) {
                    if (!player.capabilities.isCreativeMode) {
                        itemstack.shrink(1);
                    }
                    this.heal(2);
                    world.setEntityState(this, (byte) 7);
                    return true;
                }
            }
            if (this.isOwner(player) && !this.world.isRemote && itemstack.getItem() != Items.GOLD_NUGGET) {
                this.aiSit.setSitting(!this.isSitting());
                this.isJumping = false;
                this.navigator.clearPath();
                this.setAttackTarget((EntityLivingBase) null);
            }
        }
        return super.processInteract(player, hand);
    }

    @Override
    protected void setEquipmentBasedOnDifficulty(DifficultyInstance difficulty) {
        this.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));
    }

    @Override
    public EntityAgeable createChild(EntityAgeable ageble) {
        return null;
    }
}
