package fossilsarcheology.server.entity.prehistoric;

import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import fossilsarcheology.client.sound.FASoundRegistry;
import fossilsarcheology.server.entity.ai.*;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.pathfinding.PathNavigateSwimmer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.Random;

public class EntityMeganeura extends EntityPrehistoricSwimming {

    protected static final DataParameter<Optional<BlockPos>> ATTACHED_BLOCK_POS = EntityDataManager.createKey(EntityMeganeura.class, DataSerializers.OPTIONAL_BLOCK_POS);
    protected static final DataParameter<EnumFacing> ATTACHED_FACE = EntityDataManager.createKey(EntityMeganeura.class, DataSerializers.FACING);
    private int attachCooldown = 0;
    private int attachTicks = 0;

    public EntityMeganeura(World world) {
        super(world, PrehistoricEntityType.MEGANEURA, 1, 2, 4, 18, 0.15, 0.2, 0, 4);
        this.setActualSize(2.2F, 1.95F);
        minSize = 0.15F;
        maxSize = 0.3F;
        teenAge = 4;
        pediaScale = 45;
    }

    public static BlockPos getPositionRelativetoGround(Entity entity, World world, double x, double z, Random rand) {
        BlockPos pos = new BlockPos(x, entity.posY, z);
        while (world.isAirBlock(pos.down()) && pos.getY() > 0) {
            pos = pos.down();
        }
        return pos.up(2 + rand.nextInt(3));
    }

    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(ATTACHED_FACE, EnumFacing.DOWN);
        this.dataManager.register(ATTACHED_BLOCK_POS, Optional.absent());
    }

    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.dataManager.set(ATTACHED_FACE, EnumFacing.byIndex(compound.getByte("AttachFace")));
        this.attachCooldown = compound.getInteger("AttachCooldown");
        this.attachTicks = compound.getInteger("AttachTicks");
        if (compound.hasKey("APX")) {
            int i = compound.getInteger("APX");
            int j = compound.getInteger("APY");
            int k = compound.getInteger("APZ");
            this.dataManager.set(ATTACHED_BLOCK_POS, Optional.of(new BlockPos(i, j, k)));
        } else {
            this.dataManager.set(ATTACHED_BLOCK_POS, Optional.absent());
        }
    }

    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setByte("AttachFace", (byte) this.dataManager.get(ATTACHED_FACE).getIndex());
        BlockPos blockpos = this.getAttachmentPos();
        compound.setInteger("AttachCooldown", attachCooldown);
        compound.setInteger("AttachTicks", attachTicks);
        if (blockpos != null) {
            compound.setInteger("APX", blockpos.getX());
            compound.setInteger("APY", blockpos.getY());
            compound.setInteger("APZ", blockpos.getZ());
        }
    }

    public EnumFacing getAttachmentFacing() {
        return this.dataManager.get(ATTACHED_FACE);
    }

    @Nullable
    public BlockPos getAttachmentPos() {
        return (BlockPos) ((Optional) this.dataManager.get(ATTACHED_BLOCK_POS)).orNull();
    }

    public void setAttachmentPos(@Nullable BlockPos pos) {
        this.dataManager.set(ATTACHED_BLOCK_POS, Optional.fromNullable(pos));
    }

    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.ARTHROPOD;
    }

    public void applyEntityCollision(Entity entityIn) {
    }

    @Override
    public boolean attackEntityFrom(DamageSource dmg, float i) {
        if (dmg == DamageSource.IN_WALL) {
            return false;
        }
        this.attachTicks = 0;
        attachCooldown = 1000 + rand.nextInt(1500);
        this.dataManager.set(ATTACHED_FACE, EnumFacing.DOWN);
        this.setAttachmentPos(null);
        return super.attackEntityFrom(dmg, i);
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return PrehistoricEntityType.BONELESS_LOOT;
    }

    public void initEntityAI() {
        this.tasks.addTask(0, new DinoAIFindWaterTarget(this, 10, true));
        this.tasks.addTask(1, new MeganeuraAIGetInWater(this, 1.0D));
        this.tasks.addTask(2, this.aiSit = new EntityAISit(this));
        this.tasks.addTask(3, new DinoAIEatFeedersAndBlocks(this));
        this.targetTasks.addTask(0, new DinoAIEatItems(this));
        this.tasks.addTask(4, new MeganeuraAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
        this.tasks.addTask(3, new AIWander());
        this.tasks.addTask(5, new DinoMeleeAttackAI(this, 1.5D, false));
        this.tasks.addTask(6, new DinoAILeapAtTarget(this));
        this.tasks.addTask(8, new DinoAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new DinoAILookIdle(this));
        this.targetTasks.addTask(1, new DinoAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new DinoAIOwnerHurtTarget(this));
        this.targetTasks.addTask(3, new DinoAIHurtByTarget(this));
        this.targetTasks.addTask(4, new DinoAIHunt(this, EntityLivingBase.class, true, (Predicate<Entity>) entity -> entity instanceof EntityLivingBase));
        this.navigator.getNodeProcessor().setCanSwim(true);
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        boolean flying = isFlying();
        if (attachCooldown > 0) {
            attachCooldown--;
        }
        boolean flag = true;
        if (this.getAttachmentPos() == null) {
            attachTicks = 0;
            if(collided && attachCooldown == 0 && !onGround){
                attachCooldown = 5;
                Vec3d vec3d = this.getPositionEyes(0);
                Vec3d vec3d1 = this.getLook(0);
                Vec3d vec3d2 = vec3d.add(vec3d1.x * 1, vec3d1.y * 1, vec3d1.z * 1);
                RayTraceResult rayTrace = world.rayTraceBlocks(vec3d, vec3d2, false);
                if (rayTrace != null && rayTrace.hitVec != null) {
                    BlockPos sidePos = rayTrace.getBlockPos();
                    if(world.isSideSolid(sidePos, rayTrace.sideHit)){
                        this.setAttachmentPos(sidePos);
                        this.dataManager.set(ATTACHED_FACE, rayTrace.sideHit.getOpposite());
                        this.motionX = 0.0D;
                        this.motionY = 0.0D;
                        this.motionZ = 0.0D;
                    }
                }
            }
        } else if(flag){
            BlockPos pos = this.getAttachmentPos();
            double dist = getDistanceSqToCenter(pos);
            if (world.isSideSolid(pos, this.getAttachmentFacing())) {
                attachTicks++;
                attachCooldown = 150;
                this.renderYawOffset = 180.0F;
                this.prevRenderYawOffset = 180.0F;
                this.rotationYaw = 180.0F;
                this.prevRotationYaw = 180.0F;
                this.rotationYawHead = 180.0F;
                this.prevRotationYawHead = 180.0F;
                this.moveHelper.action = EntityMoveHelper.Action.WAIT;
                this.motionX = 0.0D;
                this.motionY = 0.0D;
                this.motionZ = 0.0D;
            } else {
                this.attachTicks = 0;
                this.dataManager.set(ATTACHED_FACE, EnumFacing.DOWN);
                this.setAttachmentPos(null);
            }
        }
        if(attachTicks > 1500 || this.getAttachmentPos() != null && this.getAttackTarget() != null){
            this.attachTicks = 0;
            attachCooldown = 1000 + rand.nextInt(1500);
            this.dataManager.set(ATTACHED_FACE, EnumFacing.DOWN);
            this.setAttachmentPos(null);
        }

        if (flying && flyProgress < 20.0F) {
            flyProgress += 0.5F;
            if (sitProgress != 0)
                sitProgress = sleepProgress = 0F;
        } else if (!flying && flyProgress > 0.0F) {
            flyProgress -= 0.5F;
            if (sitProgress != 0)
                sitProgress = sleepProgress = 0F;
        }
        if (!this.isMovementBlockedSoft() && !this.useSwimAI() && this.getAttachmentPos() == null) {
            this.motionY += 0.08D;
        } else if (!this.isChild()) {
            this.moveHelper.action = EntityMoveHelper.Action.WAIT;
        }
        if (flying && this.ticksExisted % 20 == 0 && !world.isRemote && !this.isChild()  && this.getAttachmentPos() == null) {
            this.playSound(FASoundRegistry.MEGANEURA_FLY, this.getSoundVolume(), 1);
        }
        if (this.getAnimation() == ATTACK_ANIMATION && this.getAnimationTick() == 9 && this.getAttackTarget() != null && this.canReachPrey()) {
            doAttack();
        }
    }

    private boolean isFlying() {
        return !this.onGround && !this.isMovementBlockedSoft();
    }

    protected void switchNavigator(boolean onLand) {
        if (onLand) {
            this.moveHelper = new FlightMoveHelper(this);
            this.navigator = new PathNavigateFlying(this, world);
            this.isLandNavigator = true;
        } else {
            this.moveHelper = new EntityPrehistoricSwimming.SwimmingMoveHelper();
            this.navigator = new PathNavigateSwimmer(this, world);
            this.isLandNavigator = false;
        }
    }

    public boolean isSleeping() {
        return false;
    }

    @Override
    public String getTexture() {
        String gender = this.isChild() ? "_baby" : this.getGender() == 0 ? "_female" : "_male";
        if (this.isSkeleton()) {
            return "fossil:textures/model/" + type.toString().toLowerCase() + "_0/" + type.toString().toLowerCase() + "_skeleton.png";
        }
        return "fossil:textures/model/" + type.toString().toLowerCase() + "_0/" + type.toString().toLowerCase() + gender + ".png";
    }

    protected boolean useSwimAI() {
        return this.isChild();
    }

    @Override
    public int getAdultAge() {
        return 9;
    }

    @Override
    public void setSpawnValues() {

    }

    @Override
    protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {
    }

    @Override
    public PrehistoricEntityTypeAI.Activity aiActivityType() {

        return PrehistoricEntityTypeAI.Activity.DIURINAL;
    }

    @Override
    public PrehistoricEntityTypeAI.Attacking aiAttackType() {

        return PrehistoricEntityTypeAI.Attacking.BASIC;
    }

    @Override
    public PrehistoricEntityTypeAI.Climbing aiClimbType() {

        return PrehistoricEntityTypeAI.Climbing.NONE;
    }

    @Override
    public PrehistoricEntityTypeAI.Following aiFollowType() {

        return PrehistoricEntityTypeAI.Following.AGRESSIVE;
    }

    @Override
    public PrehistoricEntityTypeAI.Jumping aiJumpType() {

        return PrehistoricEntityTypeAI.Jumping.BASIC;
    }

    @Override
    public PrehistoricEntityTypeAI.Response aiResponseType() {

        return PrehistoricEntityTypeAI.Response.SCARED;
    }

    @Override
    public PrehistoricEntityTypeAI.Stalking aiStalkType() {

        return PrehistoricEntityTypeAI.Stalking.NONE;
    }

    @Override
    public PrehistoricEntityTypeAI.Taming aiTameType() {

        return PrehistoricEntityTypeAI.Taming.FEEDING;
    }

    @Override
    public PrehistoricEntityTypeAI.Untaming aiUntameType() {

        return PrehistoricEntityTypeAI.Untaming.ATTACK;
    }

    @Override
    public PrehistoricEntityTypeAI.Moving aiMovingType() {

        return PrehistoricEntityTypeAI.Moving.FLIGHT;
    }

    @Override
    public PrehistoricEntityTypeAI.WaterAbility aiWaterAbilityType() {

        return PrehistoricEntityTypeAI.WaterAbility.IGNOREANDFISH;
    }

    @Override
    public double swimSpeed() {
        return 0.25D;
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        if (this.getAnimation() == NO_ANIMATION) {
            this.setAnimation(ATTACK_ANIMATION);
        }
        return false;
    }

    @Override
    public boolean doesFlock() {
        return false;
    }

    @Override
    public Item getOrderItem() {
        return Items.ARROW;
    }

    @Override
    public float getMaleSize() {
        return 0.8F;
    }

    @Override
    public int getMaxHunger() {
        return 45;
    }

    @Override
    public boolean canBeRidden() {
        return false;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return FASoundRegistry.MEGANEURA_LIVING;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return FASoundRegistry.MEGANEURA_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FASoundRegistry.MEGANEURA_DEATH;
    }

    protected float getSoundVolume() {
        return 0.5F;
    }

    public boolean isDirectPathBetweenPoints(Vec3d target) {
        RayTraceResult rayTrace = world.rayTraceBlocks(this.getPositionVector().add(0, -0.25, 0), target, false);
        if (rayTrace != null && rayTrace.hitVec != null) {
            BlockPos sidePos = rayTrace.getBlockPos();
            BlockPos pos = new BlockPos(rayTrace.hitVec);
            if (!world.isAirBlock(pos) || !world.isAirBlock(sidePos)) {
                return true;
            } else {
                return rayTrace.typeOfHit != RayTraceResult.Type.MISS;
            }
        }
        return true;
    }

    class FlightMoveHelper extends EntityMoveHelper {
        public FlightMoveHelper(EntityMeganeura meganura) {
            super(meganura);
            this.speed = 3F;
        }

        public void onUpdateMoveHelper() {
            if (this.action == EntityMoveHelper.Action.MOVE_TO) {
                if (EntityMeganeura.this.collidedHorizontally) {
                    EntityMeganeura.this.rotationYaw += 180.0F;
                    this.speed = 0.1F;
                    BlockPos target = EntityMeganeura.getPositionRelativetoGround(EntityMeganeura.this, EntityMeganeura.this.world, EntityMeganeura.this.posX + EntityMeganeura.this.rand.nextInt(15) - 7, EntityMeganeura.this.posZ + EntityMeganeura.this.rand.nextInt(15) - 7, EntityMeganeura.this.rand);
                    this.posX = target.getX();
                    this.posY = target.getY();
                    this.posZ = target.getZ();
                }
                double d0 = this.posX - EntityMeganeura.this.posX;
                double d1 = this.posY - EntityMeganeura.this.posY;
                double d2 = this.posZ - EntityMeganeura.this.posZ;
                double d3 = d0 * d0 + d1 * d1 + d2 * d2;
                d3 = MathHelper.sqrt(d3);

                if (d3 < EntityMeganeura.this.getEntityBoundingBox().getAverageEdgeLength()) {
                    this.action = EntityMoveHelper.Action.WAIT;
                    EntityMeganeura.this.motionX *= 0.5D;
                    EntityMeganeura.this.motionY *= 0.5D;
                    EntityMeganeura.this.motionZ *= 0.5D;
                } else {
                    EntityMeganeura.this.motionX += d0 / d3 * 0.1D * this.speed;
                    EntityMeganeura.this.motionY += d1 / d3 * 0.1D * this.speed;
                    EntityMeganeura.this.motionZ += d2 / d3 * 0.1D * this.speed;

                    if (EntityMeganeura.this.getAttackTarget() == null) {
                        EntityMeganeura.this.rotationYaw = -((float) MathHelper.atan2(EntityMeganeura.this.motionX, EntityMeganeura.this.motionZ)) * (180F / (float) Math.PI);
                        EntityMeganeura.this.renderYawOffset = EntityMeganeura.this.rotationYaw;
                    } else {
                        double d4 = EntityMeganeura.this.getAttackTarget().posX - EntityMeganeura.this.posX;
                        double d5 = EntityMeganeura.this.getAttackTarget().posZ - EntityMeganeura.this.posZ;
                        EntityMeganeura.this.rotationYaw = -((float) MathHelper.atan2(d4, d5)) * (180F / (float) Math.PI);
                        EntityMeganeura.this.renderYawOffset = EntityMeganeura.this.rotationYaw;
                    }
                }
            }
        }
    }

    class AIWander extends EntityAIBase {
        BlockPos target;
        boolean isGoingToAttach = false;

        public AIWander() {
            this.setMutexBits(1);
        }

        public boolean shouldExecute() {
            if(EntityMeganeura.this.attachCooldown == 0){
                for(int i = 0; i < 15; i++){
                    BlockPos randomPos = new BlockPos(EntityMeganeura.this).add(rand.nextInt(16) - 8, rand.nextInt(10) - 5, rand.nextInt(16) - 8);
                    if(!world.isAirBlock(randomPos)){
                        RayTraceResult rayTrace = world.rayTraceBlocks(EntityMeganeura.this.getPositionVector().add(0, 0.25, 0), new Vec3d(randomPos).add(0.5, 0.5, 0.5), false);
                        if (rayTrace != null && rayTrace.hitVec != null) {
                            if(!world.isSideSolid(rayTrace.getBlockPos(), rayTrace.sideHit)){
                                target = rayTrace.getBlockPos();
                                isGoingToAttach = true;
                            }
                        }
                    }
                }
            }
            target = EntityMeganeura.getPositionRelativetoGround(EntityMeganeura.this, EntityMeganeura.this.world, EntityMeganeura.this.posX + EntityMeganeura.this.rand.nextInt(16) - 8, EntityMeganeura.this.posZ + EntityMeganeura.this.rand.nextInt(16) - 8, EntityMeganeura.this.rand);
            return !EntityMeganeura.this.useSwimAI() && !EntityMeganeura.this.isSitting() && EntityMeganeura.this.isDirectPathBetweenPoints(new Vec3d(target).add(0.5D, 0.5D, 0.5D)) && EntityMeganeura.this.rand.nextInt(4) == 0 && EntityMeganeura.this.getAttachmentPos() == null;
        }

        public boolean shouldContinueExecuting() {
            return false;
        }

        public void updateTask() {
            if (!EntityMeganeura.this.isDirectPathBetweenPoints(new Vec3d(target))) {
                target = EntityMeganeura.getPositionRelativetoGround(EntityMeganeura.this, EntityMeganeura.this.world, EntityMeganeura.this.posX + EntityMeganeura.this.rand.nextInt(15) - 7, EntityMeganeura.this.posZ + EntityMeganeura.this.rand.nextInt(15) - 7, EntityMeganeura.this.rand);
            }
            if (EntityMeganeura.this.world.isAirBlock(target) || isGoingToAttach) {
                if (!EntityMeganeura.this.isFlying()) {
                    EntityMeganeura.this.switchNavigator(false);
                }
                EntityMeganeura.this.moveHelper.setMoveTo((double) target.getX() + 0.5D, (double) target.getY() + 0.5D, (double) target.getZ() + 0.5D, 0.25D);
                if (EntityMeganeura.this.getAttackTarget() == null) {
                    EntityMeganeura.this.getLookHelper().setLookPosition((double) target.getX() + 0.5D, (double) target.getY() + 0.5D, (double) target.getZ() + 0.5D, 180.0F, 20.0F);

                }
            }
        }
    }

}
