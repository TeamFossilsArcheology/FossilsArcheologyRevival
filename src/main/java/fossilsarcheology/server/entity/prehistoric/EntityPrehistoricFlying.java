package fossilsarcheology.server.entity.prehistoric;

import fossilsarcheology.Revival;
import net.ilexiconn.llibrary.server.animation.Animation;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.passive.EntityFlying;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public abstract class EntityPrehistoricFlying extends EntityPrehistoric implements EntityFlying {

    private static final DataParameter<Boolean> FLYING = EntityDataManager.createKey(EntityPrehistoricFlying.class, DataSerializers.BOOLEAN);
    public BlockPos airTarget;
    public float flyProgress;
    private boolean isFlying;
    private int ticksFlying;
    public Animation TAKEOFF_ANIMATION;

    public EntityPrehistoricFlying(World world, PrehistoricEntityType type, double baseDamage, double maxDamage, double baseHealth, double maxHealth, double baseSpeed, double maxSpeed, double baseArmor, double maxArmor) {
        super(world, type, baseDamage, maxDamage, baseHealth, maxHealth, baseSpeed, maxSpeed, baseArmor, maxArmor);
    }

    public boolean isDirectPathBetweenPoints(Vec3d vec1, Vec3d vec2) {
        RayTraceResult movingobjectposition = this.world.rayTraceBlocks(vec1, new Vec3d(vec2.x, vec2.y + (double) this.height * 0.5D, vec2.z), false, true, false);
        return movingobjectposition == null || movingobjectposition.typeOfHit != RayTraceResult.Type.BLOCK;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(FLYING, false);
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setBoolean("Flying", this.isFlying());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.setFlying(compound.getBoolean("Flying"));

    }

    public boolean isFlying() {
        if (world.isRemote) {
            boolean isFlying = this.dataManager.get(FLYING);
            this.isFlying = isFlying;
            return isFlying;
        }

        return isFlying;
    }

    public void setFlying(boolean flying) {
        this.dataManager.set(FLYING, flying);
        if (!world.isRemote) {
            this.isFlying = flying;
        }
    }

    @Override
    public void jump() {
        super.jump();
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        boolean flying = world.isAirBlock(this.getPosition().down());
        if (isFlying() && isSleeping()) {
            setSleeping(false);
        }
        if (!this.onGround && this.motionY < 0.0D) {
            this.motionY *= 0.6D;
        }
        if (!isFlying() && !this.onGround) {
            this.limbSwingAmount += 0.4F;
            this.limbSwing += this.limbSwingAmount;
        }
        if (flying && flyProgress < 20.0F) {
            flyProgress += 2F;
            if (sitProgress != 0)
                sitProgress = sleepProgress = 0F;
        } else if (!flying && flyProgress > 0.0F) {
            flyProgress -= 1F;
            if (sitProgress != 0)
                sitProgress = sleepProgress = 0F;
        }
        int flightChance = 250;
        if (!this.isFlying() && !this.isMovementBlockedSoft() && rand.nextInt(flightChance) == 0 && !this.world.isRemote && this.isAdult() && this.getControllingPassenger() == null && this.onGround && ticksExisted > 50) {
            this.startFlying();
        }
        if (!this.world.isRemote && isFlying()) {
            ticksFlying++;
        }
        if (!this.world.isRemote && !isFlying()) {
            ticksFlying = 0;
        }
        if (!this.world.isRemote && ticksFlying > 80 && this.onGround) {
            this.setFlying(false);
        }
        if (this.isFlying() && this.canSleep() && !world.isAirBlock(this.getPosition().down(2)) && !this.isOverWater()) {
            this.setFlying(false);

        }
        if (this.isFlying() && getAttackTarget() == null) {
            flyAround();
        } else if (getAttackTarget() != null) {
            flyTowardsTarget();
        }
        if (this.hasTakeoffAnimation() && !this.isFlying() && this.getAnimation() == TAKEOFF_ANIMATION && this.getAnimationTick() >= getTakeoffTick()) {
            this.setFlying(true);
        }
    }

    public void startFlying(){
        if(this.hasTakeoffAnimation()){
            this.setAnimation(TAKEOFF_ANIMATION);
        }else{
            this.setFlying(true);
        }
    }

    public void flyAround() {
        if (airTarget != null && this.isFlying()) {
            if (!isTargetInAir() || !this.isFlying()) {
                airTarget = null;
            }
            flyTowardsTarget();
        }
    }

    public void flyTowardsTarget() {
        double bbLength = this.getEntityBoundingBox().getAverageEdgeLength() * 2.5D;
        double maxDist = Math.max(3, bbLength * bbLength);
        if (airTarget != null && isTargetInAir() && this.isFlying()) {
            if(this.getDistanceSquared(new Vec3d(airTarget.getX() + 0.5D, airTarget.getY() + 0.5D, airTarget.getZ() + 0.5D)) > maxDist){
                double targetX = airTarget.getX() + 0.5D - posX;
                double targetY = Math.min(airTarget.getY(), 256) + 1D - posY;
                double targetZ = airTarget.getZ() + 0.5D - posZ;
                motionX += (Math.signum(targetX) * 0.5D - motionX) * 0.100000000372529 * 2;
                motionY += (Math.signum(targetY) * 0.5D - motionY) * 0.100000000372529 * 2;
                motionZ += (Math.signum(targetZ) * 0.5D - motionZ) * 0.100000000372529 * 2;
                float angle = (float) (Math.atan2(motionZ, motionX) * 180.0D / Math.PI) - 90.0F;
                float rotation = MathHelper.wrapDegrees(angle - rotationYaw);
                moveForward = 0.5F;
                prevRotationYaw = rotationYaw;
                if(Math.abs(motionX) > 0.12 || Math.abs(motionZ) > 0.12){
                    rotationYaw += rotation;
                }
            }else{
                this.onReachAirTarget(this.airTarget);
                this.airTarget = null;
            }
        } else {
            this.airTarget = null;
        }
        if(collidedHorizontally){
            this.airTarget = null;
        }
    }

    protected void onReachAirTarget(BlockPos airTarget) {
    }

    protected boolean isTargetInAir() {
        return airTarget != null && ((world.getBlockState(airTarget).getMaterial() == Material.AIR) || world.getBlockState(airTarget).getMaterial() == Material.AIR);
    }

    public float getDistanceSquared(Vec3d vec) {
        float f = (float) (this.posX - vec.x);
        float f1 = (float) (this.posY - vec.y);
        float f2 = (float) (this.posZ - vec.z);
        return f * f + f1 * f1 + f2 * f2;
    }

    protected abstract double getFlySpeed();

    public static BlockPos getBlockInView(EntityPrehistoricFlying dinosaur) {
        float radius = 0.75F * (0.7F * 4) * -3 - dinosaur.getRNG().nextInt(20);
        float neg = dinosaur.getRNG().nextBoolean() ? 1 : -1;
        float angle = (0.01745329251F * dinosaur.renderYawOffset) + 3.15F + (dinosaur.getRNG().nextFloat() * neg);
        double extraX = radius * MathHelper.sin((float) (Math.PI + angle));
        double extraZ = radius * MathHelper.cos(angle);
        BlockPos radialPos = new BlockPos(dinosaur.posX + extraX, 0, dinosaur.posZ + extraZ);
        BlockPos ground = dinosaur.world.getHeight(radialPos);
        int distFromGround = (int) dinosaur.posY - ground.getY();
        BlockPos newPos = radialPos.up(distFromGround > 16 ? (int) Math.min(Revival.CONFIG_OPTIONS.flyingTargetMaxHeight, dinosaur.posY + dinosaur.getRNG().nextInt(16) - 8) : (int) dinosaur.posY + dinosaur.getRNG().nextInt(16) + 1);
        if (!isTargetBlocked(dinosaur, new Vec3d(newPos)) && dinosaur.getDistanceSqToCenter(newPos) > 6) {
            return newPos;
        }
        return null;
    }

    public static boolean isTargetBlocked(Entity entity, Vec3d target) {
        if (target != null) {
            RayTraceResult rayTrace = entity.world.rayTraceBlocks(new Vec3d(entity.getPosition()), target, false);
            if (rayTrace != null && rayTrace.hitVec != null) {
                BlockPos sidePos = rayTrace.getBlockPos();
                BlockPos pos = new BlockPos(rayTrace.hitVec);
                return !entity.world.isAirBlock(pos) || !entity.world.isAirBlock(sidePos);
            }
        }
        return false;
    }

    @Nullable
    public BlockPos generateAirTarget(){
        BlockPos pos = null;
        for (int i = 0; i < 10; i++) {
            pos = getBlockInView(this);
            if (pos != null && this.world.getBlockState(pos).getMaterial() == Material.AIR && !isTargetBlocked(this, new Vec3d(pos))) {
                return pos;
            }
        }
        return pos;
    }

    @Override
    public Animation[] getAnimations() {
        return new Animation[]{SPEAK_ANIMATION, ATTACK_ANIMATION, TAKEOFF_ANIMATION};
    }

    public boolean hasTakeoffAnimation(){
        return false;
    }

    public int getTakeoffTick() {
        return TAKEOFF_ANIMATION.getDuration() - 1;
    }
}
