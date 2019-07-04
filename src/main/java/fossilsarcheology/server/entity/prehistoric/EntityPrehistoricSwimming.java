package fossilsarcheology.server.entity.prehistoric;

import fossilsarcheology.server.entity.ai.LargeSwimNodeProcessor;
import fossilsarcheology.server.entity.ai.PathNavigateAmphibious;
import net.ilexiconn.llibrary.server.animation.Animation;
import net.minecraft.block.material.Material;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathFinder;
import net.minecraft.pathfinding.PathNavigateSwimmer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public abstract class EntityPrehistoricSwimming extends EntityPrehistoric {
    public boolean movesOnLand;
    public Animation FISH_ANIMATION;
    public float onLandProgress;
    protected boolean isAmphibious;
    public int timeInWater = 0;
    public int timeOnLand = 0;
    protected boolean isLandNavigator;
    public float flyProgress;
    private static final int MAX_TIME_ON_LAND = 1000;
    private static final int MAX_TIME_IN_WATER = 1000;


    public EntityPrehistoricSwimming(World world, PrehistoricEntityType type, double baseDamage, double maxDamage, double baseHealth, double maxHealth, double baseSpeed, double maxSpeed, double baseArmor, double maxArmor) {
        super(world, type, baseDamage, maxDamage, baseHealth, maxHealth, baseSpeed, maxSpeed, baseArmor, maxArmor);
        this.switchNavigator(true);
        this.hasBabyTexture = false;
    }

    protected void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveHelper = new EntityMoveHelper(this);
            this.navigator = new PathNavigateAmphibious(this, world);
            this.isLandNavigator = true;
        } else {
            this.moveHelper = new EntityPrehistoricSwimming.SwimmingMoveHelper();
            this.navigator = new EntityPrehistoricSwimming.PathNavigateLargeSwimmer(this, world);
            this.isLandNavigator = false;
        }
    }

    public boolean shouldLeaveWater(){
        return isAmphibious && this.timeInWater > MAX_TIME_IN_WATER && timeOnLand < MAX_TIME_ON_LAND;
    }

    public boolean shouldEnterWater(){
        if(!isAmphibious){
            return true;
        }
        return this.timeInWater == 0 && timeOnLand > MAX_TIME_ON_LAND;
    }

    protected double getStrongAttackPower(){
        return this.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE).getAttributeValue() * 2;
    }


    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }

    @Override
    public boolean canBreatheUnderwater() {
        return true;
    }

    public abstract double swimSpeed();

    private double getScaledSwimSpeed(){
        return getAgeScale() / maxSize * swimSpeed();
    }

    @Override
    protected boolean canTriggerWalking() {
        return false;
    }

    @Override
    public boolean isOnLadder() {
        return false;
    }

    public void destroyBoat(Entity sailor){
        if (sailor.getRidingEntity() != null && sailor.getRidingEntity() instanceof EntityBoat && !world.isRemote) {
            EntityBoat boat = (EntityBoat) sailor.getRidingEntity();
            boat.setDead();
            if (this.world.getGameRules().getBoolean("doEntityDrops")) {
                for (int i = 0; i < 3; ++i) {
                    boat.entityDropItem(new ItemStack(Item.getItemFromBlock(Blocks.PLANKS), 1, boat.getBoatType().getMetadata()), 0.0F);
                }
                for (int j = 0; j < 2; ++j) {
                    boat.dropItemWithOffset(Items.STICK, 1, 0.0F);
                }
            }
        }
    }
    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.isInWater() && this.useSwimAI() && this.isLandNavigator && !this.world.isRemote) {
            switchNavigator(false);
        }
        if (!this.isInWater() && !this.useSwimAI() && !this.isLandNavigator && !this.world.isRemote) {
            switchNavigator(true);
        }
        this.renderYawOffset = this.rotationYaw;
        if ((this.isSitting() || this.isSleeping()) && this.isInWater()) {
            this.setSitting(false);
            this.setSleeping(false);
        }
        if (this.getRidingPlayer() != null && this.isInWater()) {
            if (this.getRidingPlayer().rotationPitch > 45) {
                this.motionY -= 0.2D * swimSpeed();
            }
            if (this.getRidingPlayer().rotationPitch < -45) {
                this.motionY += 0.2D * swimSpeed();
            }
        }
        if(this.isInWaterMaterial()){
            this.timeInWater++;
            this.timeOnLand = 0;
        }
        if(this.onGround && !this.isInWaterMaterial()){
            this.timeInWater = 0;
            this.timeOnLand++;
        }
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("TimeOnLand", this.timeOnLand);
        compound.setInteger("TimeInWater", this.timeInWater);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.timeOnLand = compound.getInteger("TimeOnLand");
        this.timeInWater = compound.getInteger("TimeInWater");

    }

    protected boolean useSwimAI() {
        return this.isInWater();
    }

    @Override
    public boolean isInWater() {
        return super.isInWater() || this.isInsideOfMaterial(Material.WATER) || this.isInsideOfMaterial(Material.CORAL);
    }

    @Override
    public boolean canBeSteered() {
        return this.type.isVivariousAquatic() ? this.isInWater() && super.canBeSteered() : super.canBeSteered();
    }

    @Override
    public void travel(float strafe, float vertical, float forward) {
        float f4;
        if (this.isSitting() || !this.isAmphibious && !this.isInWaterMaterial()) {
            super.travel(0, 0, 0);
            return;
        }
        if (this.isBeingRidden() && this.canBeSteered()) {
            EntityLivingBase controller = (EntityLivingBase) this.getControllingPassenger();
            if (controller != null) {
                strafe = controller.moveStrafing * 0.5F;
                forward = controller.moveForward;
                if (forward <= 0.0F) {
                    forward *= 0.25F;
                }
                this.fallDistance = 0;
                if (this.isInWater()) {
                    forward = controller.moveForward * 0.25F;
                    strafe = controller.moveStrafing * 0.125F;
                    this.moveRelative(strafe, vertical, forward, 1F);
                    f4 = 0.01F;
                    double d0 = this.getScaledSwimSpeed() * 0.5F;
                    if (!this.onGround) {
                        d0 *= 0.5F;
                    }
                    if (d0 > 0.0F) {
                        f4 += (0.54600006F - f4) * d0 / 3.0F;
                    }
                    this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
                    this.motionX *= (double) f4;
                    this.motionX *= 0.900000011920929D;
                    this.motionY *= 0.900000011920929D;
                    this.motionY *= (double) f4;
                    this.motionZ *= 0.900000011920929D;
                    this.motionZ *= (double) f4;
                    motionY += 0.01185D;
                } else {
                    forward = controller.moveForward * 0.25F;
                    strafe = controller.moveStrafing * 0.125F;

                    this.setAIMoveSpeed(1);
                    super.travel(strafe, vertical, forward);
                    return;
                }
                this.setAIMoveSpeed(1);
                super.travel(strafe, vertical = 0, forward);
                this.prevLimbSwingAmount = this.limbSwingAmount;
                double deltaX = this.posX - this.prevPosX;
                double deltaZ = this.posZ - this.prevPosZ;
                double deltaY = this.posY - this.prevPosY;
                float delta = MathHelper.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ) * 4.0F;
                if (delta > 1.0F) {
                    delta = 1.0F;
                }
                this.limbSwingAmount += (delta - this.limbSwingAmount) * 0.4F;
                this.limbSwing += this.limbSwingAmount;
                return;
            }
        }
        if (this.isServerWorld()) {
            if (this.isInWater()) {
                this.moveRelative(strafe * (float)swimSpeed(), vertical * (float)swimSpeed(), forward * (float)swimSpeed(), 0.1F);
                f4 = 0.8F;
                float speedModifier = (float) EnchantmentHelper.getDepthStriderModifier(this);
                if (speedModifier > 3.0F) {
                    speedModifier = 3.0F;
                }
                if (!this.onGround) {
                    speedModifier *= 0.15F;
                }
                if (speedModifier > 0.0F) {
                    f4 += (0.54600006F - f4) * speedModifier / 3.0F;
                }
                this.move(MoverType.SELF, this.motionX, this.motionY, this.motionZ);
                this.motionX *= (double) f4;
                this.motionX *= 0.9;
                this.motionY *= 0.9;
                this.motionZ *= 0.9;
                this.motionY *= (double) f4;
                this.motionZ *= (double) f4;
            } else {
                super.travel(strafe, vertical, forward);
            }
        }
        this.prevLimbSwingAmount = this.limbSwingAmount;
        double deltaX = this.posX - this.prevPosX;
        double deltaZ = this.posZ - this.prevPosZ;
        double deltaY = this.posY - this.prevPosY;
        float delta = MathHelper.sqrt(deltaX * deltaX + deltaY * deltaY + deltaZ * deltaZ) * 4.0F;
        if (delta > 1.0F) {
            delta = 1.0F;
        }
        this.limbSwingAmount += (delta - this.limbSwingAmount) * 0.4F;
        this.limbSwing += this.limbSwingAmount;
    }

    @Override
    public Vec3d getPositionVector() {
        return new Vec3d(this.posX, this.posY + 0.5D, this.posZ);
    }


    @Override
    public void onEntityUpdate() {
        int i = this.getAir();
        super.onEntityUpdate();
        if(!canBreathOnLand()) {
            if (this.isEntityAlive() && !this.isInWater()) {
                --i;
                this.setAir(i);

                if (this.getAir() == -40) {
                    this.setAir(0);
                    this.attackEntityFrom(DamageSource.DROWN, 2.0F);
                }
            } else {
                this.setAir(500);
            }
        }
    }

    public boolean canBreathOnLand(){
        return true;
    }

    class PathNavigateLargeSwimmer extends PathNavigateSwimmer {

        public PathNavigateLargeSwimmer(EntityLiving entitylivingIn, World worldIn) {
            super(entitylivingIn, worldIn);
        }

        @Override
        protected PathFinder getPathFinder() {
            return new PathFinder(new LargeSwimNodeProcessor());
        }

        @Override
        protected Vec3d getEntityPosition() {
            return new Vec3d(this.entity.posX, this.entity.posY + 0.49F, this.entity.posZ);
        }

        @Override
        protected boolean canNavigate() {
            return this.entity.isInWater();
        }

        @Override
        protected boolean isDirectPathBetweenPoints(Vec3d posVec31, Vec3d posVec32, int sizeX, int sizeY, int sizeZ) {
            RayTraceResult raytraceresult = this.world.rayTraceBlocks(posVec31, new Vec3d(posVec32.x, posVec32.y + 0.49F, posVec32.z), false, true, false);
            return raytraceresult == null || raytraceresult.typeOfHit == RayTraceResult.Type.MISS;
        }
    }

    class SwimmingMoveHelper extends EntityMoveHelper {
        private final EntityPrehistoricSwimming dinosaur = EntityPrehistoricSwimming.this;

        public SwimmingMoveHelper() {
            super(EntityPrehistoricSwimming.this);
        }

        @Override
        public void onUpdateMoveHelper() {
            if (this.action == EntityMoveHelper.Action.MOVE_TO && !this.dinosaur.getNavigator().noPath() && !this.dinosaur.isBeingRidden()) {
                double distanceX = this.posX - this.dinosaur.posX;
                double distanceY = this.posY - this.dinosaur.posY;
                double distanceZ = this.posZ - this.dinosaur.posZ;
                double distance = Math.abs(distanceX * distanceX + distanceY * distanceY + distanceZ * distanceZ);
                distance = (double) MathHelper.sqrt(distance);
                distanceY /= distance;
                float angle = (float) (Math.atan2(distanceZ, distanceX) * 180.0D / Math.PI) - 90.0F;
                this.dinosaur.rotationYaw = this.limitAngle(this.dinosaur.rotationYaw, angle, 30.0F);
                this.dinosaur.setAIMoveSpeed(0.65F);
                this.dinosaur.motionY += (double) this.dinosaur.getAIMoveSpeed() * distanceY * 0.1D;
            } else if (this.action == EntityMoveHelper.Action.JUMPING) {
                this.entity.setAIMoveSpeed((float) (this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue()));
                if (this.entity.onGround) {
                    this.action = EntityMoveHelper.Action.WAIT;
                }
            } else {
                this.dinosaur.setAIMoveSpeed(0.0F);
            }
        }
    }
}
