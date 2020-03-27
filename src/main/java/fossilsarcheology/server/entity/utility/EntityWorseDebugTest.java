package fossilsarcheology.server.entity.utility;

import fossilsarcheology.server.entity.prehistoric.EntityMeganeura;
import fossilsarcheology.server.entity.prehistoric.EntityPrehistoricSwimming;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.*;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityLargeFireball;
import net.minecraft.init.SoundEvents;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.pathfinding.PathNavigateSwimmer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityWorseDebugTest extends EntityMob {

    public EntityWorseDebugTest(World worldIn) {
        super(worldIn);
        this.setSize(1.8F, 3.1F);
        this.isImmuneToFire = true;
        this.moveHelper = new EntityWorseDebugTest.FlightMoveHelper(this);
        this.navigator = new PathNavigateFlying(this, world);
    }

    @Override
    public boolean isAIDisabled() {
        return false;
    }

    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityWorseDebugTest.AIFireballAttack(this));
        this.tasks.addTask(2, new AIWander());
        this.tasks.addTask(3, new EntityAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(4, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false, new Class[0]));
        this.targetTasks.addTask(2, new EntityAINearestAttackableTarget(this, EntityLivingBase.class, false));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(1000.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(20.0D);
    }

    public void attackEntityWithRangedAttack(EntityLivingBase target) {
        double d1 = target.posX - this.posX;
        double d2 = target.getEntityBoundingBox().minY + (double)(target.height / 2.0F) - (this.posY + (double)(this.height / 2.0F));
        double d3 = target.posZ - this.posZ;
        double d0 = this.getDistanceSq(target);
        float f = MathHelper.sqrt(MathHelper.sqrt(d0)) * 0.5F;

        EntityLargeFireball entitysmallfireball = new EntityLargeFireball(this.world, this, d1 + this.getRNG().nextGaussian() * (double)f, d2, d3 + this.getRNG().nextGaussian() * (double)f);
        entitysmallfireball.posY = this.posY + (double)(this.height / 2.0F) + 0.5D;
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
        entity.rotationPitch = (float) (MathHelper.atan2(y, (double) f1) * (180D / Math.PI));
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
        private final EntityWorseDebugTest mob;
        private int attackStep;
        private int attackTime;

        public AIFireballAttack(EntityWorseDebugTest mob) {
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

    class FlightMoveHelper extends EntityMoveHelper {
        public FlightMoveHelper(EntityWorseDebugTest meganura) {
            super(meganura);
            this.speed = 3F;
        }

        public void onUpdateMoveHelper() {
            if (this.action == EntityMoveHelper.Action.MOVE_TO) {
                if (EntityWorseDebugTest.this.collidedHorizontally) {
                    EntityWorseDebugTest.this.rotationYaw += 180.0F;
                    this.speed = 0.1F;
                    BlockPos target = EntityMeganeura.getPositionRelativetoGround(EntityWorseDebugTest.this, EntityWorseDebugTest.this.world, EntityWorseDebugTest.this.posX + EntityWorseDebugTest.this.rand.nextInt(15) - 7, EntityWorseDebugTest.this.posZ + EntityWorseDebugTest.this.rand.nextInt(15) - 7, EntityWorseDebugTest.this.rand);
                    this.posX = target.getX();
                    this.posY = target.getY();
                    this.posZ = target.getZ();
                }
                double d0 = this.posX - EntityWorseDebugTest.this.posX;
                double d1 = this.posY - EntityWorseDebugTest.this.posY;
                double d2 = this.posZ - EntityWorseDebugTest.this.posZ;
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                d3 = (double) MathHelper.sqrt(d3);

                if (d3 < EntityWorseDebugTest.this.getEntityBoundingBox().getAverageEdgeLength()) {
                    this.action = EntityMoveHelper.Action.WAIT;
                    EntityWorseDebugTest.this.motionX *= 0.5D;
                    EntityWorseDebugTest.this.motionY *= 0.5D;
                    EntityWorseDebugTest.this.motionZ *= 0.5D;
                } else {
                    EntityWorseDebugTest.this.motionX += d0 / d3 * 0.1D * this.speed;
                    EntityWorseDebugTest.this.motionY += d1 / d3 * 0.1D * this.speed;
                    EntityWorseDebugTest.this.motionZ += d2 / d3 * 0.1D * this.speed;

                    if (EntityWorseDebugTest.this.getAttackTarget() == null) {
                        EntityWorseDebugTest.this.rotationYaw = -((float) MathHelper.atan2(EntityWorseDebugTest.this.motionX, EntityWorseDebugTest.this.motionZ)) * (180F / (float) Math.PI);
                        EntityWorseDebugTest.this.renderYawOffset = EntityWorseDebugTest.this.rotationYaw;
                    } else {
                        double d4 = EntityWorseDebugTest.this.getAttackTarget().posX - EntityWorseDebugTest.this.posX;
                        double d5 = EntityWorseDebugTest.this.getAttackTarget().posZ - EntityWorseDebugTest.this.posZ;
                        EntityWorseDebugTest.this.rotationYaw = -((float) MathHelper.atan2(d4, d5)) * (180F / (float) Math.PI);
                        EntityWorseDebugTest.this.renderYawOffset = EntityWorseDebugTest.this.rotationYaw;
                    }
                }
            }
        }
    }
    class AIWander extends EntityAIBase {
        BlockPos target;

        public AIWander() {
            this.setMutexBits(1);
        }

        public boolean shouldExecute() {
            target = EntityMeganeura.getPositionRelativetoGround(EntityWorseDebugTest.this, EntityWorseDebugTest.this.world, EntityWorseDebugTest.this.posX + EntityWorseDebugTest.this.rand.nextInt(16) - 8, EntityWorseDebugTest.this.posZ + EntityWorseDebugTest.this.rand.nextInt(16) - 8, EntityWorseDebugTest.this.rand);
            return EntityWorseDebugTest.this.rand.nextInt(4) == 0;
        }

        public boolean shouldContinueExecuting() {
            return false;
        }

        public void updateTask() {
            if (!EntityWorseDebugTest.this.isDirectPathBetweenPoints(new Vec3d(target))) {
                target = EntityMeganeura.getPositionRelativetoGround(EntityWorseDebugTest.this, EntityWorseDebugTest.this.world, EntityWorseDebugTest.this.posX + EntityWorseDebugTest.this.rand.nextInt(15) - 7, EntityWorseDebugTest.this.posZ + EntityWorseDebugTest.this.rand.nextInt(15) - 7, EntityWorseDebugTest.this.rand);
            }
            if (EntityWorseDebugTest.this.world.isAirBlock(target)) {
                EntityWorseDebugTest.this.moveHelper.setMoveTo((double) target.getX() + 0.5D, (double) target.getY() + 0.5D, (double) target.getZ() + 0.5D, 0.25D);
                if (EntityWorseDebugTest.this.getAttackTarget() == null) {
                    EntityWorseDebugTest.this.getLookHelper().setLookPosition((double) target.getX() + 0.5D, (double) target.getY() + 0.5D, (double) target.getZ() + 0.5D, 180.0F, 20.0F);

                }
            }
        }
    }

    private boolean isDirectPathBetweenPoints(Vec3d vec3d) {
        RayTraceResult rayTrace = world.rayTraceBlocks(this.getPositionVector().add(0, -0.25, 0), vec3d, false);
        if (rayTrace != null && rayTrace.hitVec != null) {
            BlockPos sidePos = rayTrace.getBlockPos();
            BlockPos pos = new BlockPos(rayTrace.hitVec);
            if (!world.isAirBlock(pos) || !world.isAirBlock(sidePos) ) {
                return true;
            }else{
                return rayTrace.typeOfHit != RayTraceResult.Type.MISS;
            }
        }
        return true;
    }
}
