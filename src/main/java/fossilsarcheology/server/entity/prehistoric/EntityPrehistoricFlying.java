package fossilsarcheology.server.entity.prehistoric;

import net.minecraft.block.material.Material;
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

public abstract class EntityPrehistoricFlying extends EntityPrehistoric implements EntityFlying {

	public static final int FLYING_INDEX = 29;
	private static final DataParameter<Boolean> FLYING = EntityDataManager.createKey(EntityPrehistoricFlying.class, DataSerializers.BOOLEAN);
	public BlockPos airTarget;
	public float flyProgress;
	private boolean isFlying;
	private int ticksFlying;

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

	@Override
	public void jump() {
		super.jump();
	}

	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		boolean flying = world.isAirBlock(this.getPosition().down());
		if (!this.onGround && this.motionY < 0.0D) {
			this.motionY *= 0.6D;
		}
		if(!isFlying() && !this.onGround){
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
		if (!this.isFlying() && !this.isMovementBlockedSoft() && rand.nextInt(200) == 0 && !this.world.isRemote && this.isAdult() && this.getControllingPassenger() == null && this.onGround && ticksExisted > 50) {
			this.setFlying(true);
		}
		if(!this.world.isRemote && isFlying()){
			ticksFlying++;
		}
		if(!this.world.isRemote && !isFlying()){
			ticksFlying = 0;
		}
		if (!this.world.isRemote && ticksFlying > 80 && this.onGround) {
			this.setFlying(false);
		}
		if (this.isFlying() && getAttackTarget() == null) {
			flyAround();
		} else if (getAttackTarget() != null) {
			flyTowardsTarget();
		}
	}

	@Override
    public boolean canSleep() {
		if (super.canSleep() && this.isFlying() && this.onGround) {
			this.setFlying(false);
			return super.canSleep();
		}
		return super.canSleep();
	}

	public void setFlying(boolean flying) {
		this.dataManager.set(FLYING, flying);
		if (!world.isRemote) {
			this.isFlying = flying;
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
		if (airTarget != null && isTargetInAir() && this.isFlying() && this.getDistanceSquared(new Vec3d(airTarget.getX(), this.posY, airTarget.getZ())) > 3) {
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
			rotationYaw += rotation;
		} else {
			this.airTarget = null;
		}
	   /* if (airTarget != null && isTargetInAir() && this.isFlying() && this.getDistanceSquared(new Vec3d(airTarget.getX(), this.posY, airTarget.getZ())) < 3) {
	        this.setFlying(false);
        }*/
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
}
