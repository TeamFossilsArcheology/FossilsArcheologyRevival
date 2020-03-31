package fossilsarcheology.server.entity.utility;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

public class EntityDebugTest extends EntityMob {

    public EntityDebugTest(World worldIn) {
        super(worldIn);
        this.setSize(0.8F, 2.1F);
        this.isImmuneToFire = true;
    }

    @Override
    public boolean isAIDisabled() {
        return false;
    }

    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, new EntityAIRestrictSun(this));
        this.tasks.addTask(3, new EntityAIFleeSun(this, 1.0D));
        this.tasks.addTask(4, new EntityDebugTest.AIFireballAttack(this));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 1.0D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityLivingBase.class, false));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(800.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(20.0D);
    }

    public void attackEntityWithRangedAttack(EntityLivingBase target) {
        double d1 = target.posX - this.posX;
        double d2 = target.getEntityBoundingBox().minY + (double) (target.height / 2.0F) - (this.posY + (double) (this.height / 2.0F));
        double d3 = target.posZ - this.posZ;
        double d0 = this.getDistanceSq(target);
        float f = MathHelper.sqrt(MathHelper.sqrt(d0)) * 0.5F;

        EntityLargeFireball entitysmallfireball = new EntityLargeFireball(this.world, this, d1 + this.getRNG().nextGaussian() * (double) f, d2, d3 + this.getRNG().nextGaussian() * (double) f);
        entitysmallfireball.posY = this.posY + (double) (this.height / 2.0F) + 0.5D;
        entitysmallfireball.explosionPower = 5;
        this.world.spawnEntity(entitysmallfireball);
    }

    public void shoot(Entity entity, double x, double y, double z, float velocity, float inaccuracy) {
        float f = MathHelper.sqrt(x * x + y * y + z * z);
        x = x / (double) f;
        y = y / (double) f;
        z = z / (double) f;
        x = x + this.rand.nextGaussian() * 0.007499999832361937D * (double) inaccuracy;
        y = y + this.rand.nextGaussian() * 0.007499999832361937D * (double) inaccuracy;
        z = z + this.rand.nextGaussian() * 0.007499999832361937D * (double) inaccuracy;
        x = x * (double) velocity;
        y = y * (double) velocity;
        z = z * (double) velocity;
        entity.motionX = x;
        entity.motionY = y;
        entity.motionZ = z;
        float f1 = MathHelper.sqrt(x * x + z * z);
        entity.rotationYaw = (float) (MathHelper.atan2(x, z) * (180D / Math.PI));
        entity.rotationPitch = (float) (MathHelper.atan2(y, f1) * (180D / Math.PI));
        entity.prevRotationYaw = entity.rotationYaw;
        entity.prevRotationPitch = entity.rotationPitch;
    }

    @Override
    public void onDeath(DamageSource source) {
        world.newExplosion(this, this.posX, this.posY, this.posZ, 12, true, world.getGameRules().getBoolean("doMobGriefing"));
        super.onDeath(source);
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return SoundEvents.AMBIENT_CAVE;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        return SoundEvents.AMBIENT_CAVE;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_ZOMBIE_PIG_DEATH;
    }


    static class AIFireballAttack extends EntityAIBase {
        private final EntityDebugTest mob;
        private int attackStep;
        private int attackTime;

        public AIFireballAttack(EntityDebugTest mob) {
            this.mob = mob;
            this.setMutexBits(3);
        }

        public boolean shouldExecute() {
            EntityLivingBase entitylivingbase = this.mob.getAttackTarget();
            return entitylivingbase != null && entitylivingbase.isEntityAlive();
        }

        public void startExecuting() {
            this.attackStep = 0;
        }

        public void updateTask() {
            --this.attackTime;
            EntityLivingBase entitylivingbase = this.mob.getAttackTarget();
            double d0 = this.mob.getDistanceSq(entitylivingbase);

            if (d0 < 4.0D) {
                if (this.attackTime <= 0) {
                    this.attackTime = 20;
                    this.mob.attackEntityAsMob(entitylivingbase);
                }

                this.mob.getMoveHelper().setMoveTo(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, 1.0D);
            } else if (d0 < this.getFollowDistance() * this.getFollowDistance()) {
                double d1 = entitylivingbase.posX - this.mob.posX;
                double d2 = entitylivingbase.getEntityBoundingBox().minY + (double) (entitylivingbase.height / 2.0F) - (this.mob.posY + (double) (this.mob.height / 2.0F));
                double d3 = entitylivingbase.posZ - this.mob.posZ;

                if (this.attackTime <= 0) {
                    ++this.attackStep;

                    if (this.attackStep <= 4) {
                        this.attackTime = 6;
                    } else {
                        this.attackTime = 20;
                        this.attackStep = 0;
                    }
                    this.mob.attackEntityWithRangedAttack(entitylivingbase);
                }
                this.mob.getLookHelper().setLookPositionWithEntity(entitylivingbase, 10.0F, 10.0F);
            } else {
                this.mob.getNavigator().clearPath();
                this.mob.getMoveHelper().setMoveTo(entitylivingbase.posX, entitylivingbase.posY, entitylivingbase.posZ, 1.0D);
            }

            super.updateTask();
        }

        private double getFollowDistance() {
            IAttributeInstance iattributeinstance = this.mob.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE);
            return iattributeinstance == null ? 16.0D : iattributeinstance.getAttributeValue();
        }
    }
}
