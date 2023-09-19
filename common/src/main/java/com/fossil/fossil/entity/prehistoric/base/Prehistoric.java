package com.fossil.fossil.entity.prehistoric.base;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.block.IDinoUnbreakable;
import com.fossil.fossil.config.FossilConfig;
import com.fossil.fossil.entity.ModEntities;
import com.fossil.fossil.entity.ToyBase;
import com.fossil.fossil.entity.ai.*;
import com.fossil.fossil.entity.ai.navigation.PrehistoricPathNavigation;
import com.fossil.fossil.entity.animation.AnimationInfoManager;
import com.fossil.fossil.entity.animation.AnimationLogic;
import com.fossil.fossil.entity.animation.AttackAnimationLogic;
import com.fossil.fossil.entity.data.AI;
import com.fossil.fossil.entity.data.EntityDataManager;
import com.fossil.fossil.entity.data.Stat;
import com.fossil.fossil.item.ModItems;
import com.fossil.fossil.sounds.ModSounds;
import com.fossil.fossil.util.Diet;
import com.fossil.fossil.util.FoodMappings;
import com.fossil.fossil.util.Gender;
import com.fossil.fossil.util.TimePeriod;
import dev.architectury.extensions.network.EntitySpawnExtension;
import dev.architectury.networking.NetworkManager;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ItemParticleOption;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.damagesource.EntityDamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.HitResult;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.resource.GeckoLibCache;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Map;

import static com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityTypeAI.*;

public abstract class Prehistoric extends TamableAnimal implements PlayerRideableJumping, EntitySpawnExtension, PrehistoricAnimatable, PrehistoricDebug {

    public static final EntityDataAccessor<CompoundTag> DEBUG = SynchedEntityData.defineId(Prehistoric.class, EntityDataSerializers.COMPOUND_TAG);
    private static final EntityDataAccessor<CompoundTag> ACTIVE_ANIMATIONS = SynchedEntityData.defineId(Prehistoric.class, EntityDataSerializers.COMPOUND_TAG);
    private static final EntityDataAccessor<Boolean> START_EAT_ANIMATION = SynchedEntityData.defineId(Prehistoric.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> MOOD = SynchedEntityData.defineId(Prehistoric.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> AGE_TICK = SynchedEntityData.defineId(Prehistoric.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> MATING_TICK = SynchedEntityData.defineId(Prehistoric.class, EntityDataSerializers.INT);
    public static final EntityDataAccessor<Integer> PLAYING_TICK = SynchedEntityData.defineId(Prehistoric.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> HUNGER = SynchedEntityData.defineId(Prehistoric.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> MODELIZED = SynchedEntityData.defineId(Prehistoric.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> FLEEING = SynchedEntityData.defineId(Prehistoric.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> SLEEPING = SynchedEntityData.defineId(Prehistoric.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Byte> CLIMBING = SynchedEntityData.defineId(Prehistoric.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Boolean> AGING_DISABLED = SynchedEntityData.defineId(Prehistoric.class, EntityDataSerializers.BOOLEAN);
    public final MoodSystem moodSystem = new MoodSystem(this);
    protected final WhipSteering steering = new WhipSteering(this);
    private final AttackAnimationLogic<Prehistoric> animationLogic = new AttackAnimationLogic<>(this);
    private final ResourceLocation animationLocation;
    private final boolean isMultiPart;
    public OrderType currentOrder;
    public boolean hasFeatherToggle = false;
    public boolean featherToggle;
    public boolean hasTeenTexture = true;
    public boolean hasBabyTexture = true;
    public float weakProgress;
    public float sitProgress;
    public int ticksSat;
    public float sleepProgress;
    public float climbProgress;
    public int ticksSlept;
    public float pediaScale;
    public float ridingXZ;
    public boolean shouldWander = true;
    public ResourceLocation textureLocation;
    public DinoMatingGoal matingGoal;
    protected float playerJumpPendingScale;
    private Gender gender = Gender.random(random);
    private boolean droppedBiofossil = false;
    private int fleeTicks = 0;
    private int cathermalSleepCooldown = 0;
    private int ticksClimbing = 0;

    public Prehistoric(EntityType<? extends Prehistoric> entityType, Level level, boolean isMultiPart) {
        super(entityType, level);
        this.animationLocation = new ResourceLocation(Fossil.MOD_ID, "animations/" + EntityType.getKey(entityType).getPath() + ".animation.json");
        this.isMultiPart = isMultiPart;
        this.setHunger(this.getMaxHunger() / 2);
        this.pediaScale = 1.0F;
        this.currentOrder = OrderType.WANDER;
        this.updateAbilities();
        if (this.getMobType() == MobType.WATER) {
            this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
            this.getNavigation().getNodeEvaluator().setCanFloat(true);
        }
        setPersistenceRequired();
        refreshTexturePath();
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20D)
                .add(Attributes.ATTACK_DAMAGE, 2D)
                .add(Attributes.FLYING_SPEED, 0.4f);
    }

    public static boolean isEntitySmallerThan(Entity entity, float size) {
        if (entity instanceof Prehistoric prehistoric) {
            return prehistoric.getBbWidth() <= size;
        } else {
            return entity.getBbWidth() <= size;
        }
    }

    /**
     * Do things before {@code  LivingEntity#knockBack}
     * This is supposed to launch up any entities always
     *
     * @return newly updated strength value
     */
    public static double beforeKnockBack(LivingEntity entity, double strength, double x, double y) {
        double resistance = entity.getAttribute(Attributes.KNOCKBACK_RESISTANCE).getValue();
        double reversed = 1 - resistance;
        entity.setDeltaMovement(entity.getDeltaMovement().add(0, 0.4 * reversed + 0.1, 0));
        return strength * 2.0;
    }

    public static boolean canBreak(Block block) {
        //TODO: Big break Test
        if (block instanceof IDinoUnbreakable) return false;
        BlockState state = block.defaultBlockState();
        if (!state.requiresCorrectToolForDrops()) return false;
        return !state.is(BlockTags.NEEDS_DIAMOND_TOOL);
    }

    public boolean isCustomMultiPart() {
        return isMultiPart;
    }

    @Override
    public void setId(int id) {
        super.setId(id);
        for (int i = 0; i < getCustomParts().length; ++i) {
            this.getCustomParts()[i].setId(id + i + 1);
        }
    }

    /**
     * @return The child parts of this entity.
     * @implSpec On the forge classpath this implementation should return objects that inherit from PartEntity instead of Entity.
     */
    public abstract Entity[] getCustomParts();

    @Override
    public boolean isPickable() {
        return !isCustomMultiPart();
    }

    @Override
    public boolean canBeCollidedWith() {
        return false;
    }

    @Override
    protected void doPush(Entity entity) {
        if (!isCustomMultiPart()) {
            super.doPush(entity);
        }
    }

    @Override
    public boolean isColliding(BlockPos pos, BlockState state) {
        if (isCustomMultiPart()) {
            VoxelShape voxelShape = state.getCollisionShape(this.level, pos, CollisionContext.of(this));
            VoxelShape voxelShape2 = voxelShape.move(pos.getX(), pos.getY(), pos.getZ());
            return Shapes.joinIsNotEmpty(voxelShape2, Shapes.create(getCustomParts()[0].getBoundingBox()), BooleanOp.AND);
        }
        return super.isColliding(pos, state);
    }

    @Override
    public float getPickRadius() {
        if (isCustomMultiPart()) {
            //return Math.max(getDimensions(Pose.STANDING).width - getBbWidth(), 0);
            return getType().getWidth() * getScale() - getBbWidth();
        }
        return super.getPickRadius();
    }

    @Override
    protected @NotNull AABB makeBoundingBox() {
        if (isCustomMultiPart()) {
            //Using the position of the custom part will not work because its behind by 1 tick?
            // return getCustomParts()[0].getDimensions(Pose.STANDING).makeBoundingBox(getCustomParts()[0].position());
            return getCustomParts()[0].getDimensions(Pose.STANDING).makeBoundingBox(position());
        }
        return super.makeBoundingBox();
    }

    @Override
    public void remove(RemovalReason reason) {
        super.remove(reason);
        for (WrappedGoal availableGoal : goalSelector.getAvailableGoals()) {
            if (availableGoal.getGoal() instanceof CacheMoveToBlockGoal goal) {
                goal.stop();//Only for the debug message
            }
        }
    }

    public boolean hurt(Entity part, DamageSource source, float damage) {
        return super.hurt(source, damage);
    }

    @Override
    public CompoundTag getDebugTag() {
        return entityData.get(DEBUG);
    }

    @Override
    public void disableCustomAI(byte type, boolean disableAI) {
        CompoundTag tag = entityData.get(DEBUG).copy();
        switch (type) {
            case 0 -> setNoAi(disableAI);
            case 1 -> tag.putBoolean("disableGoalAI", disableAI);
            case 2 -> tag.putBoolean("disableMoveAI", disableAI);
            case 3 -> tag.putBoolean("disableLookAI", disableAI);
        }
        entityData.set(DEBUG, tag);
    }

    @Override
    public @NotNull EntityDimensions getDimensions(Pose poseIn) {
        return this.getType().getDimensions().scale(this.getScale());
        //return this.getType().getDimensions();
    }

    @Override
    protected void registerGoals() {
        matingGoal = new DinoMatingGoal(this, getAttributeValue(Attributes.MOVEMENT_SPEED));
        goalSelector.addGoal(1, new DinoPanicGoal(this, 1.5));
        goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        goalSelector.addGoal(2, matingGoal);
        goalSelector.addGoal(3, new EatFromFeederGoal(this));
        goalSelector.addGoal(4, new EatItemEntityGoal(this));
        goalSelector.addGoal(5, new EatBlockGoal(this));
        goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0f));
        targetSelector.addGoal(4, new HuntAndPlayGoal(this));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        entityData.define(ACTIVE_ANIMATIONS, new CompoundTag());
        entityData.define(START_EAT_ANIMATION, false);
        entityData.define(MOOD, 0);
        entityData.define(AGE_TICK, data().adultAgeDays() * 24000);
        entityData.define(MATING_TICK, random.nextInt(6000) + 6000);
        entityData.define(PLAYING_TICK, random.nextInt(6000) + 6000);
        entityData.define(HUNGER, 0);
        entityData.define(MODELIZED, false);
        entityData.define(FLEEING, false);
        entityData.define(SLEEPING, false);
        entityData.define(CLIMBING, (byte) 0);
        entityData.define(AGING_DISABLED, false);

        CompoundTag tag = new CompoundTag();
        tag.putDouble("x", position().x);
        tag.putDouble("y", position().y);
        tag.putDouble("z", position().z);
        tag.putBoolean("disableGoalAI", false);
        tag.putBoolean("disableMoveAI", false);
        tag.putBoolean("disableLookAI", false);
        entityData.define(DEBUG, tag);
    }

    @Override
    public void saveAdditionalSpawnData(FriendlyByteBuf buf) {
        buf.writeBoolean(getGender() == Gender.MALE);
    }

    @Override
    public void loadAdditionalSpawnData(FriendlyByteBuf buf) {
        if (buf.readBoolean()) {
            gender = Gender.MALE;
        } else {
            gender = Gender.FEMALE;
        }
        refreshTexturePath();
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("Mood", moodSystem.getMood());
        compound.putInt("TicksTillMate", getMatingTick());
        compound.putInt("TicksTillPlay", moodSystem.getPlayingTick());
        compound.putInt("Hunger", getHunger());
        compound.putBoolean("isModelized", isSkeleton());
        compound.putBoolean("Fleeing", isFleeing());
        compound.putBoolean("Sleeping", isSleeping());
        compound.putInt("TicksSlept", ticksSlept);
        compound.putInt("TicksClimbing", ticksClimbing);
        compound.putByte("currentOrder", (byte) currentOrder.ordinal());
        compound.putFloat("YBodyRot", yBodyRot);
        compound.putFloat("YHeadRot", yHeadRot);
        compound.putBoolean("AgingDisabled", isAgingDisabled());
        compound.putInt("CathermalTimer", cathermalSleepCooldown);
        compound.putString("Gender", getGender().toString());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        moodSystem.setMood(compound.getInt("Mood"));
        setAgeInTicks(compound.getInt("Age"));
        setMatingTick(compound.getInt("TicksTillMate"));
        moodSystem.setPlayingTick(compound.getInt("TicksTillPlay"));
        setHunger(compound.getInt("Hunger"));
        setSkeleton(compound.getBoolean("isModelized"));
        setFleeing(compound.getBoolean("Fleeing"));
        setSleeping(compound.getBoolean("Sleeping"));
        ticksSlept = compound.getInt("TicksSlept");
        ticksClimbing = compound.getInt("TicksClimbing");
        if (compound.contains("currentOrder")) {
            setOrder(OrderType.values()[compound.getByte("currentOrder")]);
        }
        yBodyRot = compound.getInt("YBodyRot");
        yHeadRot = compound.getInt("YHeadRot");
        setAgingDisabled(compound.getBoolean("AgingDisabled"));
        cathermalSleepCooldown = compound.getInt("CathermalTimer");
        if ("female".equalsIgnoreCase(compound.getString("Gender"))) {
            setGender(Gender.FEMALE);
        } else {
            setGender(Gender.MALE);
        }
        refreshTexturePath();
    }

    public abstract PrehistoricEntityType type();

    public AABB getAttackBounds() {
        float size = (float) (this.getBoundingBox().getSize() * 0.25F);
        return this.getBoundingBox().inflate(2.0F + size, 2.0F + size, 2.0F + size);
    }

    @Override
    public SpawnGroupData finalizeSpawn(ServerLevelAccessor levelIn, DifficultyInstance difficultyIn, MobSpawnType reason, @Nullable SpawnGroupData spawnDataIn, @Nullable CompoundTag dataTag) {
        //Skip AgeableMob#finalizeSpawn
        getAttribute(Attributes.FOLLOW_RANGE).addPermanentModifier(new AttributeModifier("Random spawn bonus", this.random.nextGaussian() * 0.05, AttributeModifier.Operation.MULTIPLY_BASE));
        if (spawnDataIn instanceof PrehistoricGroupData prehistoricGroupData) {
            setAgeInDays(prehistoricGroupData.ageInDays());
        } else {
            setAgeInDays(data().adultAgeDays());
        }
        updateAbilities();
        moodSystem.setPlayingTick(0);
        setMatingTick(24000);
        heal(getMaxHealth());
        currentOrder = OrderType.WANDER;
        setNoAi(false);
        return spawnDataIn;
    }

    @Override
    public boolean isNoAi() {
        return this.isSkeleton() || super.isNoAi();
    }

    public OrderType getOrderType() {
        return this.currentOrder;
    }

    @Override
    public boolean isImmobile() {
        return getHealth() <= 0 || isOrderedToSit() || isSkeleton() || isActuallyWeak() || isVehicle() || isSleeping();
    }

    @Override
    public boolean isSleeping() {
        return entityData.get(SLEEPING);
    }

    public void setSleeping(boolean sleeping) {
        entityData.set(SLEEPING, sleeping);
        if (!sleeping) {
            cathermalSleepCooldown = 10000 + random.nextInt(6000);
        }
        refreshTexturePath();
    }

    public void setOrder(OrderType newOrder) {
        currentOrder = newOrder;
    }

    @Override
    public boolean isPushable() {
        //TODO: Maybe also !isVehicle()?
        return !this.isSkeleton() && super.isPushable();
    }

    public boolean canSleep() {
        if (aiActivityType() == Activity.DIURNAL) {
            return !level.isDay();
        } else if (aiActivityType() == Activity.NOCTURNAL) {
            return level.isDay() && !level.canSeeSky(blockPosition().above());
        }
        return aiActivityType() == Activity.BOTH;
    }

    public boolean canWakeUp() {
        if (aiActivityType() == Activity.DIURNAL) {
            return level.isDay();
        } else if (aiActivityType() == Activity.NOCTURNAL) {
            return !level.isDay() || level.canSeeSky(blockPosition().above());
        } else {
            return ticksSlept > 4000;
        }
    }

    public boolean wantsToSleep() {
        if (level.isClientSide) {
            return true;
        }
        if (aiActivityType() == Activity.BOTH && ticksSlept > 8000) {
            return false;
        }
        if ((getTarget() == null || getTarget() instanceof ToyBase) && getLastHurtByMob() == null && !isInWater() && !isVehicle() && !isActuallyWeak() && canSleep()) {
            return getOrderType() != OrderType.FOLLOW;
        }
        return false;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return false;
    }

    @Nullable
    public Player getRidingPlayer() {
        return getControllingPassenger() instanceof Player player ? player : null;
    }

    public void setRidingPlayer(Player player) {
        player.yBodyRot = this.yBodyRot;
        player.setXRot(getXRot());
        player.startRiding(this);
    }

    private void doJump(double upwardMovement, double forwardMovement) {
        hasImpulse = true;
        if (forwardMovement > 0) {
            float h = Mth.sin(getYRot() * ((float) Math.PI / 180));
            float i = Mth.cos(getYRot() * ((float) Math.PI / 180));
            setDeltaMovement(getDeltaMovement().add(-0.4f * h * playerJumpPendingScale, upwardMovement, 0.4f * i * playerJumpPendingScale));
        } else {
            setDeltaMovement(getDeltaMovement().x, upwardMovement, getDeltaMovement().z);
        }
    }

    @Override
    public void travel(Vec3 travelVector) {
        if (isImmobile() && !isVehicle()) {
            super.travel(Vec3.ZERO);
            return;
        }
        LivingEntity rider = (LivingEntity) getControllingPassenger();
        if (rider == null || !canBeControlledByRider() || !steering.trySteer(rider)) {
            super.travel(travelVector);
            return;
        }
        setYRot(rider.getYRot());
        yRotO = getYRot();
        setXRot(rider.getXRot() * 0.5f);
        setRot(getYRot(), getXRot());
        yBodyRot = getYRot();
        yHeadRot = getYRot();
        float newStrafeMovement = rider.xxa * 0.5f;
        float newForwardMovement = rider.zza;
        boolean fastInWater = aiMovingType() == Moving.AQUATIC || aiMovingType() == Moving.SEMI_AQUATIC;
        if (playerJumpPendingScale > 0) {
            double newYMovement = getJumpStrength() * playerJumpPendingScale * getBlockJumpFactor() + getJumpBoostPower();
            if (isInWater()) {//TODO: Fix use in swimming?
                if (fastInWater) {
                    doJump(newYMovement, newForwardMovement);
                } else {
                    doJump(newYMovement / 2, newForwardMovement / 2);
                }
            } else if (isOnGround()) {
                doJump(newYMovement, newForwardMovement);
            }
            playerJumpPendingScale = 0;
        }
        if (isControlledByLocalInstance()) {
            setSpeed((float) getAttributeValue(Attributes.MOVEMENT_SPEED));
            Vec3 newMovement = new Vec3(newStrafeMovement, travelVector.y, newForwardMovement).scale(0.5);
            if (isInWater()) {
                steering.slowWaterTravel(newMovement);
            } else {
                super.travel(newMovement);
            }
        } else {
            setDeltaMovement(Vec3.ZERO);
        }
        if (isOnGround()) {
            playerJumpPendingScale = 0;
        }
    }

    @Override
    @Nullable
    public Entity getControllingPassenger() {
        for (Entity passenger : getPassengers()) {
            if (passenger instanceof Player player && isOwnedBy(player) && getTarget() != passenger) {
                return player;
            }
        }
        return null;
    }

    @Override
    public void aiStep() {
        updateSwingTime();
        super.aiStep();
        if (isSkeleton()) {
            setDeltaMovement(Vec3.ZERO);
        }
        if ((getTarget() != null || getLastHurtByMob() != null) && isSleeping()) {
            setSleeping(false);
        }
        if (getHunger() > getMaxHunger()) {
            setHunger(getMaxHunger());
        }
        moodSystem.tick();
        if (getMatingTick() > 0) {
            setMatingTick(getMatingTick() - 1);
        }
        if (getRidingPlayer() != null) {
            maxUpStep = 1;
        } else {
            maxUpStep = 0;
        }
        if (FossilConfig.isEnabled(FossilConfig.HEALING_DINOS) && !level.isClientSide) {
            if (random.nextInt(500) == 0 && deathTime == 0) {
                heal(1);
            }
        }

        if (isSleeping()) {
            if ((getTarget() != null && getTarget().isAlive()) || (getLastHurtByMob() != null && getLastHurtByMob().isAlive())) {
                setSleeping(false);
            }
        }
        if (isOrderedToSit()) {
            ticksSat++;
        }
        if (cathermalSleepCooldown > 0) {
            cathermalSleepCooldown--;
        }
        if (!level.isClientSide) {
            if (isSleeping()) {
                ticksSlept++;
            } else {
                ticksSlept = 0;
                if (!isInWater()) {
                    if (!isOrderedToSit() && !isVehicle() && random.nextInt(1000) == 1 && !isPassenger()) {
                        setOrderedToSit(true);
                        ticksSat = 0;
                    }
                    if (isOrderedToSit() && ticksSat > 100 && random.nextInt(100) == 1 || getTarget() != null) {
                        setOrderedToSit(false);
                    }
                }
                if (wantsToSleep()) {
                    if (aiActivityType() == Activity.BOTH) {
                        if (cathermalSleepCooldown == 0) {
                            if (random.nextInt(1200) == 1) {
                                setOrderedToSit(false);
                                setSleeping(true);
                            }
                        }
                    } else if (aiActivityType() != Activity.NO_SLEEP) {
                        if (random.nextInt(200) == 1) {
                            setOrderedToSit(false);
                            setSleeping(true);
                        }
                    }
                }
            }
            if (!wantsToSleep() || !canSleep() || canWakeUp()) {
                setOrderedToSit(false);
                setSleeping(false);
            }
            if (currentOrder == OrderType.STAY && !isOrderedToSit() && !isActuallyWeak()) {
                setOrderedToSit(true);
                ticksSat = 0;
                setSleeping(false);
            }
        }
        if (data().breaksBlocks() && moodSystem.getMood() < 0) {
            breakBlock(5);//TODO: Check if only server side
        }
        if (getTarget() instanceof ToyBase && (isPreyBlocked(getTarget()) || moodSystem.getPlayingTick() > 0)) {
            setTarget(null);
        }
        if (isFleeing()) {
            fleeTicks++;
            if (fleeTicks > getFleeingCooldown()) {
                this.setFleeing(false);
                fleeTicks = 0;
            }
        }
    }

    @Override
    public void tick() {
        super.tick();

        refreshDimensions();
        if (!isSkeleton()) {
            if (!isAgingDisabled()) {
                setAgeInTicks(getAge() + 1);
                if (getAge() % 24000 == 0) {
                    grow(0);
                }
            }
            if (tickCount % 1200 == 0 && getHunger() > 0 && FossilConfig.isEnabled(FossilConfig.STARVING_DINOS)) {
                setHunger(getHunger() - 1);
            }
            if (getHealth() > getMaxHealth() / 2 && getHunger() == 0 && tickCount % 40 == 0) {
                hurt(DamageSource.STARVE, 1);
            }
        }
        boolean sitting = isOrderedToSit();
        if (sitting && sitProgress < 20.0F) {
            sitProgress += 0.5F;
            if (sleepProgress != 0) {
                sleepProgress = 0F;
            }
        } else if (!sitting && sitProgress > 0.0F) {
            sitProgress -= 0.5F;
            if (sleepProgress != 0) {
                sleepProgress = 0F;
            }
        }
        boolean sleeping = isSleeping();
        if (sleeping && sleepProgress < 20.0F) {
            sleepProgress += 0.5F;
            if (sitProgress != 0) {
                sitProgress = 0F;
            }
        } else if (!sleeping && sleepProgress > 0.0F) {
            sleepProgress -= 0.5F;
            if (sitProgress != 0) {
                sitProgress = 0F;
            }
        }

        boolean climbing = aiClimbType() == Climbing.ARTHROPOD &&
                this.isBesideClimbableBlock() &&
                !this.onGround;

        if (climbing && climbProgress < 20.0F) {
            climbProgress += 2F;
            if (sitProgress != 0) {
                sitProgress = 0F;
            }
        } else if (!climbing && climbProgress > 0.0F) {
            climbProgress -= 2F;
            if (sitProgress != 0) {
                sitProgress = 0F;
            }
        }
        boolean weak = this.isActuallyWeak();
        if (weak && weakProgress < 20.0F) {
            weakProgress += 0.5F;
            sitProgress = 0F;
            sleepProgress = 0F;
        } else if (!weak && weakProgress > 0.0F) {
            weakProgress -= 0.5F;
            sitProgress = 0F;
            sleepProgress = 0F;
        }
        if (!this.level.isClientSide) {
            if (this.aiClimbType() == Climbing.ARTHROPOD &&
                    !this.wantsToSleep() &&
                    !this.isSleeping() &&
                    ticksClimbing >= 0 && ticksClimbing < 100) {
                this.setBesideClimbableBlock(this.horizontalCollision);
            } else {
                this.setBesideClimbableBlock(false);
                if (ticksClimbing >= 100) {
                    ticksClimbing = -900;
                }
            }
            if (this.onClimbable() || ticksClimbing < 0) {
                ticksClimbing++;
                if (level.getBlockState(this.blockPosition().above()).getMaterial().isSolid()) {
                    ticksClimbing = 200;
                }
            }
        }
    }

    @Override
    public boolean onClimbable() {
        if (this.aiMovingType() == Moving.AQUATIC ||
                this.aiMovingType() == Moving.SEMI_AQUATIC) {
            return false;
        } else {
            return this.aiClimbType() == Climbing.ARTHROPOD &&
                    this.isBesideClimbableBlock() && !this.isImmobile();
        }
    }

    public boolean isBesideClimbableBlock() {
        return (this.entityData.get(CLIMBING) & 1) != 0;
    }

    public void setBesideClimbableBlock(boolean climbing) {
        byte b0 = this.entityData.get(CLIMBING);

        if (climbing) {
            b0 = (byte) (b0 | 1);
        } else {
            b0 = (byte) (b0 & -2);
        }

        this.entityData.set(CLIMBING, b0);
    }

    @Override
    public boolean causeFallDamage(float distance, float damageMultiplier, DamageSource source) {
        if (aiClimbType() == Climbing.ARTHROPOD || aiMovingType() == Moving.WALK_AND_GLIDE || aiMovingType() == Moving.FLIGHT) {
            return false;
        } else {
            return super.causeFallDamage(distance, damageMultiplier, source);
        }
    }

    public Activity aiActivityType() {
        return ai().activity();
    }

    public Attacking aiAttackType() {
        return ai().attacking();
    }

    public Climbing aiClimbType() {
        return ai().climbing();
    }

    public Following aiFollowType() {
        return ai().following();
    }

    public Jumping aiJumpType() {
        return ai().jumping();
    }

    public Response aiResponseType() {
        return ai().response();
    }

    public Stalking aiStalkType() {
        return ai().stalking();
    }

    public Taming aiTameType() {
        return ai().taming();
    }

    public Untaming aiUntameType() {
        return ai().untaming();
    }

    public Moving aiMovingType() {
        return ai().moving();
    }

    public WaterAbility aiWaterAbilityType() {
        return ai().waterAbility();
    }

    @Override
    public float getScale() {
        float step = (data().maxScale() - data().minScale()) / ((data().adultAgeDays() * 24000) + 1);
        if (getAgeInDays() >= data().adultAgeDays()) {
            return data().maxScale();
        }
        return data().minScale() + (step * getAge());
    }

    protected float getGenderedScale() {
        return 1;
    }

    public float getModelScale() {
        return getScale() * getGenderedScale();
    }

    @Override
    protected int getExperienceReward(Player player) {
        float base = 6 * getBbWidth() * (type().diet == Diet.HERBIVORE ? 1 : 2)
                * (aiTameType() == Taming.GEM ? 1 : 2)
                * (aiAttackType() == Attacking.BASIC ? 1 : 1.25f);
        return Mth.floor((float) Math.min(data().adultAgeDays(), getAgeInDays()) * base);
    }

    public void updateAbilities() {
        double percent = Math.min((1f / data().adultAgeDays()) * getAgeInDays(), 1);

        double healthDifference = getAttributeValue(Attributes.MAX_HEALTH);
        getAttribute(Attributes.MAX_HEALTH).setBaseValue(Math.round(Mth.lerp(percent, stats().baseHealth(), stats().maxHealth())));
        healthDifference = getAttributeValue(Attributes.MAX_HEALTH) - healthDifference;
        heal((float) healthDifference);
        getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(Math.round(Mth.lerp(percent, stats().baseDamage(), stats().maxDamage())));
        getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(Mth.lerp(percent, stats().baseSpeed(), stats().maxSpeed()));
        getAttribute(Attributes.ARMOR).setBaseValue(Mth.lerp(percent, stats().baseArmor(), stats().maxArmor()));
        getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(Mth.lerp(percent, stats().baseKnockBackResistance(), stats().maxKnockBackResistance()));
    }

    public void breakBlock(float maxHardness) {
        if (!FossilConfig.isEnabled(FossilConfig.DINOS_BREAK_BLOCKS) || !level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
            return;
        }
        if (isSkeleton() || !isAdult() || !isHungry()) {
            return;
        }
        for (int a = (int) Math.round(getBoundingBox().minX) - 1; a <= (int) Math.round(getBoundingBox().maxX) + 1; a++) {
            for (int b = (int) Math.round(getBoundingBox().minY) + 1; (b <= (int) Math.round(getBoundingBox().maxY) + 2) && (b <= 127); b++) {
                for (int c = (int) Math.round(getBoundingBox().minZ) - 1; c <= (int) Math.round(getBoundingBox().maxZ) + 1; c++) {
                    BlockPos targetPos = new BlockPos(a, b, c);
                    if (level.isEmptyBlock(targetPos)) continue;
                    BlockState state = level.getBlockState(targetPos);
                    Block block = state.getBlock();

                    if (block instanceof BushBlock) continue;
                    if (!state.getFluidState().isEmpty()) continue;
                    if (state.getDestroySpeed(level, targetPos) >= maxHardness) continue;
                    if (!canBreak(state.getBlock())) continue;
                    setDeltaMovement(getDeltaMovement().multiply(0.6, 1, 0.6));
                    if (!level.isClientSide) {
                        level.destroyBlock(targetPos, true);
                    }
                }
            }
        }
    }

    public boolean isAdult() {
        return getAgeInDays() >= data().adultAgeDays();
    }

    public boolean isTeen() {
        return getAgeInDays() >= data().teenAgeDays() && getAgeInDays() < data().adultAgeDays();
    }

    @Override
    public boolean isBaby() {
        return getAgeInDays() < data().teenAgeDays() && !isSkeleton();
    }

    public int getMaxHunger() {
        return data().maxHunger();
    }

    public boolean isSkeleton() {
        return this.entityData.get(MODELIZED);
    }

    public void setSkeleton(boolean skeleton) {
        entityData.set(MODELIZED, skeleton);
    }

    public int getAgeInDays() {
        return this.entityData.get(AGE_TICK) / 24000;
    }

    public void setAgeInDays(int days) {
        setAgeInTicks(days * 24000);
    }

    @Override
    public int getAge() {
        return entityData.get(AGE_TICK);
    }

    @Override
    public void setAge(int age) {
    }

    public void setAgeInTicks(int age) {
        if (isAgingDisabled()) {
            return;
        }
        entityData.set(AGE_TICK, age);
        if (tickCount % 20 == 0) {
            refreshTexturePath();
            refreshDimensions();
        }
    }

    public boolean isAgingDisabled() {
        return this.entityData.get(AGING_DISABLED);
    }

    public void setAgingDisabled(boolean isAgingDisabled) {
        this.entityData.set(AGING_DISABLED, isAgingDisabled);
    }

    public int getMatingTick() {
        return entityData.get(MATING_TICK);
    }

    public void setMatingTick(int ticks) {
        entityData.set(MATING_TICK, ticks);
    }

    public boolean isHungry() {
        return getHunger() < getMaxHunger() * 0.75F;
    }

    public boolean isDeadlyHungry() {
        return getHunger() < getMaxHunger() * 0.25F;
    }

    public int getHunger() {
        return this.entityData.get(HUNGER);
    }

    public void setHunger(int hunger) {
        if (getHunger() > getMaxHunger()) {
            entityData.set(HUNGER, getMaxHunger());
        } else {
            entityData.set(HUNGER, hunger);
        }
    }

    public void eatItem(ItemStack stack) {
        if (stack != null) {
            if (FoodMappings.getFoodAmount(stack.getItem(), type().diet) != 0) {
                moodSystem.increaseMood(5);
                doFoodEffect(stack.getItem());
                setHunger(getHunger() + FoodMappings.getFoodAmount(stack.getItem(), type().diet));
                stack.shrink(1);
                setStartEatAnimation(true);
            }
        }
    }

    public boolean feed(int hunger) {
        if (getHunger() >= getMaxHunger()) {
            return false;
        }
        setHunger(getHunger() + hunger);
        if (getHunger() > getMaxHunger()) {
            setHunger(getMaxHunger());
        }
        level.playSound(null, blockPosition(), SoundEvents.GENERIC_EAT, SoundSource.NEUTRAL, getSoundVolume(), getVoicePitch());
        return true;
    }

    @Override
    public void killed(ServerLevel level, LivingEntity killedEntity) {
        super.killed(level, killedEntity);
        if (type().diet != Diet.HERBIVORE) {
            feed(FoodMappings.getMobFoodPoints(killedEntity, type().diet));
            heal(FoodMappings.getMobFoodPoints(killedEntity, type().diet) / 3F);
            moodSystem.increaseMood(25);
        }
    }

    @Override
    public boolean rideableUnderWater() {
        return true;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (isCustomMultiPart() && getCustomParts().length > 0) {
            if (source instanceof EntityDamageSource && ((EntityDamageSource) source).isThorns() && !this.level.isClientSide) {
                return this.hurt(getCustomParts()[0], source, amount);
            }
        }
        if (source == DamageSource.IN_WALL) {
            return false;
        }
        if (amount > 0 && isSkeleton()) {
            level.playSound(null, blockPosition(), SoundEvents.SKELETON_HURT, SoundSource.NEUTRAL, getSoundVolume(), getVoicePitch());
            if (!level.isClientSide && !droppedBiofossil) {
                if (type().timePeriod == TimePeriod.CENOZOIC) {
                    spawnAtLocation(ModItems.TAR_FOSSIL.get(), 1);
                } else {
                    spawnAtLocation(ModItems.BIO_FOSSIL.get(), 1);
                }
                spawnAtLocation(new ItemStack(Items.BONE, Math.min(getAgeInDays(), data().adultAgeDays())), 1);
                droppedBiofossil = true;
            }
            dead = true;
            return true;
        }
        if (getLastHurtByMob() instanceof Player player) {
            if (getOwner() == getLastHurtByMob()) {
                setTame(false);
                setOwnerUUID(null);
                moodSystem.increaseMood(-15);
                player.displayClientMessage(new TranslatableComponent("entity.fossil.situation.betrayed", getName()), true);
            }
        }

        if (amount > 0) {
            setOrderedToSit(false);
            setSleeping(false);
        }
        if (source.getEntity() != null) {
            moodSystem.increaseMood(-5);
        }
        return super.hurt(source, amount);
    }

    @Override
    public @NotNull InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);
        if (isSkeleton()) {
            if (stack.isEmpty()) {
                if (player.isShiftKeyDown()) {
                    teleportTo(getX() + (player.getX() - getX()) * 0.01, getY(), getZ() + (player.getZ() - getZ()) * 0.01);
                } else {
                    double d0 = player.getX() - this.getX();
                    double d2 = player.getZ() - this.getZ();
                    float f = (float) (Mth.atan2(d2, d0) * Mth.RAD_TO_DEG) - 90.0F;
                    this.yHeadRot = f;
                    this.yBodyRot = f;
                }
                return InteractionResult.SUCCESS;
            } else {
                if (stack.is(Items.BONE) && this.getAgeInDays() < data().adultAgeDays()) {
                    this.level.playSound(null, this.blockPosition(), SoundEvents.SKELETON_AMBIENT, SoundSource.NEUTRAL, 0.8F, 1);
                    this.setAgeInDays(this.getAgeInDays() + 1);
                    usePlayerItem(player, hand, stack);
                    return InteractionResult.SUCCESS;
                }
            }
        } else {
            if (stack.isEmpty()) {
                return InteractionResult.PASS;
            }
            if (isWeak() && (aiTameType() == Taming.GEM && stack.is(ModItems.SCARAB_GEM.get()) || aiTameType() == Taming.AQUATIC_GEM && stack.is(ModItems.AQUATIC_SCARAB_GEM.get()))) {
                heal(200);
                moodSystem.setMood(100);
                feed(500);
                getNavigation().stop();
                setTarget(null);
                setLastHurtByMob(null);
                tame(player);
                level.broadcastEntityEvent(this, (byte) 35);
                usePlayerItem(player, hand, stack);
                return InteractionResult.SUCCESS;
            }

            if (stack.is(ModItems.CHICKEN_ESSENCE.get()) && aiTameType() != Taming.GEM && aiTameType() != Taming.AQUATIC_GEM && !level.isClientSide) {
                if (getAgeInDays() < data().adultAgeDays() && getHunger() > 0) {
                    usePlayerItem(player, hand, stack);
                    if (!player.isCreative()) {
                        player.addItem(new ItemStack(Items.GLASS_BOTTLE, 1));
                    }
                    grow(1);
                    setHunger(1 + random.nextInt(getHunger()));
                    return InteractionResult.SUCCESS;
                }
                player.displayClientMessage(new TranslatableComponent("prehistoric.essencefail"), true);
                return InteractionResult.PASS;
            }
            if (stack.is(ModItems.STUNTED_ESSENCE.get()) && !isAgingDisabled()) {
                setHunger(getHunger() + 20);
                heal(getMaxHealth());
                playSound(SoundEvents.ZOMBIE_VILLAGER_CURE, getSoundVolume(), getVoicePitch());
                spawnItemCrackParticles(stack.getItem());
                spawnItemCrackParticles(stack.getItem());
                spawnItemCrackParticles(Items.POISONOUS_POTATO);
                spawnItemCrackParticles(Items.POISONOUS_POTATO);
                spawnItemCrackParticles(Items.EGG);
                setAgingDisabled(true);
                usePlayerItem(player, hand, stack);
                return InteractionResult.SUCCESS;
            }
            if (FoodMappings.getFoodAmount(stack.getItem(), type().diet) > 0) {
                if (!level.isClientSide) {
                    if (getHunger() < getMaxHunger() || getHealth() < getMaxHealth() && FossilConfig.isEnabled(FossilConfig.HEALING_DINOS) || !isTame() && aiTameType() == Taming.FEEDING) {
                        setHunger(getHunger() + FoodMappings.getFoodAmount(stack.getItem(), type().diet));
                        eatItem(stack);
                        if (FossilConfig.isEnabled(FossilConfig.HEALING_DINOS)) {
                            heal(3);
                        }
                        if (getHunger() >= getMaxHunger() && isTame()) {
                            player.displayClientMessage(new TranslatableComponent("entity.fossil.situation.full", getName()), true);
                        }
                        usePlayerItem(player, hand, stack);
                        if (aiTameType() == Taming.FEEDING) {
                            if (!isTame() && isTameable() && random.nextInt(10) == 1) {
                                tame(player);
                                level.broadcastEntityEvent(this, (byte) 35);
                            }
                        }
                        return InteractionResult.SUCCESS;
                    }
                }
                return InteractionResult.PASS;
            } else {
                if (stack.is(Items.LEAD) && isTame()) {
                    if (isOwnedBy(player)) {
                        setLeashedTo(player, true);
                        usePlayerItem(player, hand, stack);
                        return InteractionResult.SUCCESS;
                    }
                }

                if (stack.is(ModItems.WHIP.get()) && aiTameType() != Taming.NONE && isAdult()) {
                    if (isTame() && isOwnedBy(player) && canBeRidden()) {
                        if (getRidingPlayer() == null) {
                            if (!level.isClientSide) {
                                setRidingPlayer(player);
                            }
                            setOrder(OrderType.WANDER);
                            setOrderedToSit(false);
                            setSleeping(false);
                        } else if (getRidingPlayer() == player) {
                            setSprinting(true);
                            moodSystem.increaseMood(-5);
                        }
                    } else if (FossilConfig.isEnabled(FossilConfig.WHIP_TO_TAME_DINO) && !isTame() && aiTameType() != Taming.AQUATIC_GEM && aiTameType() != Taming.GEM) {
                        moodSystem.increaseMood(-5);
                        if (random.nextInt(5) == 0) {//TODO: Shouldnt be clientside. Check others things here as well(including the returned results)
                            player.displayClientMessage(new TranslatableComponent("entity.fossil.prehistoric.tamed", type().displayName.get()), true);
                            moodSystem.increaseMood(-25);
                            tame(player);
                        }
                    }
                    setOrderedToSit(false);
                }
                if (stack.is(getOrderItem()) && isTame() && isOwnedBy(player) && !player.isPassenger()) {
                    if (!level.isClientSide) {
                        jumping = false;
                        getNavigation().stop();
                        currentOrder = OrderType.values()[(this.currentOrder.ordinal() + 1) % 3];
                        sendOrderMessage(this.currentOrder);
                    }
                    return InteractionResult.SUCCESS;
                }
            }
        }
        return InteractionResult.PASS;
    }

    protected boolean isTameable() {
        return true;//TOOD: set in data() via old PrehistoriCentityType
    }

    public abstract Item getOrderItem();

    public void grow(int ageInDays) {
        if (isAgingDisabled()) {
            return;
        }
        setAgeInDays(getAgeInDays() + ageInDays);
        for (int i = 0; i < getScale() * 4; i++) {
            double motionX = getRandom().nextGaussian() * 0.07;
            double motionY = getRandom().nextGaussian() * 0.07;
            double motionZ = getRandom().nextGaussian() * 0.07;
            double minX = getBoundingBox().minX;
            double minY = getBoundingBox().minY;
            double minZ = getBoundingBox().minZ;
            float x = (float) (getRandom().nextFloat() * (getBoundingBox().maxX - minX) + minX);
            float y = (float) (getRandom().nextFloat() * (getBoundingBox().maxY - minY) + minY);
            float z = (float) (getRandom().nextFloat() * (getBoundingBox().maxZ - minZ) + minZ);
            level.addParticle(ParticleTypes.HAPPY_VILLAGER, x, y, z, motionX, motionY, motionZ);
        }
        updateAbilities();
    }

    public boolean isWeak() {
        return (getHealth() < 8) && (getAgeInDays() >= data().adultAgeDays()) && !isTame();
    }

    public boolean isActuallyWeak() {
        return (aiTameType() == Taming.AQUATIC_GEM || aiTameType() == Taming.GEM) && isWeak();
    }

    private void sendOrderMessage(OrderType orderType) {
        if (getOwner() instanceof Player player) {
            player.displayClientMessage(new TranslatableComponent("entity.fossil.order." + orderType.name().toLowerCase(), getName()), true);
        }
    }

    public void refreshTexturePath() {
        String name = getType().arch$registryName().getPath();
        StringBuilder builder = new StringBuilder();
        builder.append("textures/entity/");
        builder.append(name);
        builder.append("/");
        builder.append(name);
        if (isSkeleton()) {
            builder.append("_skeleton.png");
        } else {
            if (hasBabyTexture && isBaby()) builder.append("_baby");
            if (hasTeenTexture && isTeen()) builder.append("_teen");
            if (isAdult()) {
                if (gender == Gender.MALE) {
                    builder.append("_male");
                } else {
                    builder.append("_female");
                }
            }
            if (isSleeping()) builder.append("_sleeping");
            builder.append(".png");
        }
        String path = builder.toString();
        textureLocation = new ResourceLocation(Fossil.MOD_ID, path);
    }

    public float getFemaleScale() {
        return 1.0F;
    }

    @Override
    public void playAmbientSound() {
        if (!isSleeping() && !isSkeleton()) {
            super.playAmbientSound();
        }
    }

    public boolean canDinoHunt(LivingEntity target) {
        if (target instanceof ToyBase) {
            return true;
        }
        if (target != null) {
            boolean isFood = FoodMappings.getMobFoodPoints(target, type().diet) > 0;
            if (type().diet != Diet.HERBIVORE && type().diet != Diet.NONE && isFood && canAttack(target)) {
                if (getBbWidth() * getTargetScale() >= target.getBbWidth()) {
                    return isHungry();
                }
            }
        }
        return false;
    }

    public float getTargetScale() {
        return 1;
    }

    @Override
    public boolean canMate(Animal otherAnimal) {
        if (otherAnimal == this || otherAnimal.getClass() != getClass() || moodSystem.getMood() <= 50 || !isAdult() || getMatingTick() > 0) {
            return false;
        }
        Prehistoric other = ((Prehistoric) otherAnimal);
        if (other.gender == gender || other.getMatingTick() > 0 || (matingGoal.getPartner() != null && matingGoal.getPartner() != otherAnimal)) {
            return false;
        }
        return true;
    }

    @Override
    protected @NotNull PathNavigation createNavigation(Level levelIn) {
        return aiClimbType() == Climbing.ARTHROPOD ? new WallClimberNavigation(this, levelIn) : new PrehistoricPathNavigation(this, levelIn);
    }

    public boolean canBeRidden() {
        return data().canBeRidden();
    }

    @Override
    public boolean canBeControlledByRider() {
        return canBeRidden() && this.getControllingPassenger() instanceof LivingEntity rider && isOwnedBy(rider);
    }

    public void procreate(Prehistoric mob) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        if (random.nextInt(100) == 0 || calendar.get(Calendar.MONTH) + 1 == 4 && calendar.get(Calendar.DATE) == 1) {
            playSound(ModSounds.MUSIC_MATING.get(), 1, 1);
        }
        if (!level.isClientSide) {
            Entity hatchling;
            if (this instanceof Mammal mammal) {
                hatchling = mammal.createChild((ServerLevel) level);
            } else if (type().cultivatedBirdEggItem != null) {
                hatchling = new ItemEntity(level, getX(), getY(), getZ(), new ItemStack(type().cultivatedBirdEggItem));
            } else {
                if (FossilConfig.isEnabled(FossilConfig.EGGS_LIKE_CHICKENS) || type().isVivariousAquatic()) {
                    hatchling = new ItemEntity(level, getX(), getY(), getZ(), new ItemStack(type().eggItem));
                } else {
                    hatchling = ModEntities.DINOSAUR_EGG.get().create(level);
                    ((DinosaurEgg) hatchling).setPrehistoricEntityType(type());
                }
            }
            setTarget(null);
            mob.setTarget(null);
            hatchling.moveTo(mob.getX(), mob.getY(), mob.getZ(), mob.yBodyRot, 0);
            if (hatchling instanceof Prehistoric prehistoric) {
                prehistoric.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(blockPosition()),
                        MobSpawnType.BREEDING, new Prehistoric.PrehistoricGroupData(0), null);
                prehistoric.grow(0);
            }
            level.addFreshEntity(hatchling);
        }
    }

    public List<? extends Prehistoric> getNearbySpeciesMembers(int range) {
        return level.getEntitiesOfClass(getClass(), getBoundingBox().inflate(range, 4.0D, range), prehistoric -> prehistoric != this);
    }

    //TODO: This method uses Forge-specific API and needs porting.
    /*public boolean isInWaterMaterial() {
        double d0 = this.getY();
        int i = Mth.floor(this.getX());
        int j = Mth.floor((float) Mth.floor(d0));
        int k = Mth.floor(this.getZ());
        BlockState blockState = this.level.getBlockState(new BlockPos(i, j, k));
        if (blockState.getMaterial() == Material.WATER) {
            double filled = 1.0f;
            if (blockState.getBlock() instanceof IFluidBlock) {
                filled = ((IFluidBlock) blockState.getBlock()).getFilledPercentage(level, new BlockPos(i, j, k));
            }
            if (filled < 0) {
                filled *= -1;
                return d0 > j + (1 - filled);
            } else {
                return d0 < j + filled;
            }
        } else {
            return false;
        }
    }*/

    public void doFoodEffect(Item item) {
        this.level.playSound(null, this.blockPosition(), SoundEvents.GENERIC_EAT, SoundSource.NEUTRAL, this.getSoundVolume(), this.getVoicePitch());
        if (item != null) {
            spawnItemParticle(item, item instanceof BlockItem);
        }
    }

    public void doFoodEffect() {
        this.level.playSound(null, this.blockPosition(), SoundEvents.GENERIC_EAT, SoundSource.NEUTRAL, this.getSoundVolume(), this.getVoicePitch());
        switch (this.type().diet) {
            case HERBIVORE -> spawnItemParticle(Items.WHEAT_SEEDS, false);
            case OMNIVORE -> spawnItemParticle(Items.BREAD, false);
            case PISCIVORE -> spawnItemParticle(Items.COD, false);
            default -> spawnItemParticle(Items.BEEF, false);
        }
    }

    public void spawnItemCrackParticles(Item item) {
        for (int i = 0; i < 15; i++) {
            double motionX = getRandom().nextGaussian() * 0.07D;
            double motionY = getRandom().nextGaussian() * 0.07D;
            double motionZ = getRandom().nextGaussian() * 0.07D;
            float f = (float) (getRandom().nextFloat() * (this.getBoundingBox().maxX - this.getBoundingBox().minX) + this.getBoundingBox().minX);
            float f1 = (float) (getRandom().nextFloat() * (this.getBoundingBox().maxY - this.getBoundingBox().minY) + this.getBoundingBox().minY);
            float f2 = (float) (getRandom().nextFloat() * (this.getBoundingBox().maxZ - this.getBoundingBox().minZ) + this.getBoundingBox().minZ);
            if (level.isClientSide) {
                this.level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(item)), f, f1, f2, motionX, motionY, motionZ);
            }
        }
    }

    public void spawnItemParticle(Item item, boolean itemBlock) {
        if (!level.isClientSide) {
            double motionX = random.nextGaussian() * 0.07D;
            double motionY = random.nextGaussian() * 0.07D;
            double motionZ = random.nextGaussian() * 0.07D;
            float f = (float) (getRandom().nextFloat() * (this.getBoundingBox().maxX - this.getBoundingBox().minX) + this.getBoundingBox().minX);
            float f1 = (float) (getRandom().nextFloat() * (this.getBoundingBox().maxY - this.getBoundingBox().minY) + this.getBoundingBox().minY);
            float f2 = (float) (getRandom().nextFloat() * (this.getBoundingBox().maxZ - this.getBoundingBox().minZ) + this.getBoundingBox().minZ);
          /*  if (itemBlock && item instanceof ItemBlock) {
                Revival.NETWORK_WRAPPER.sendToAll(new MessageFoodParticles(this.getEntityId(), Block.getIdFromBlock(((ItemBlock) item).getBlock())));
            } else {
                Revival.NETWORK_WRAPPER.sendToAll(new MessageFoodParticles(this.getEntityId(), Item.getIdFromItem(item)));
            }
           */
        }
    }

    @Override
    public void positionRider(Entity passenger) {
        super.positionRider(passenger);
        if (passenger instanceof Mob mob) {
            this.yBodyRot = mob.yBodyRot;
        }
        Player rider = getRidingPlayer();
        if (isOwnedBy(rider) && getTarget() != rider) {
            float radius = ridingXZ * (0.7F * getScale()) * -3;
            float angle = (0.01745329251F * this.yBodyRot);
            double extraX = radius * Mth.sin((float) (Math.PI + angle));
            double extraZ = radius * Mth.cos(angle);
            rider.setPos(getX() + extraX, getY() + getPassengersRidingOffset() + rider.getMyRidingOffset(), getZ() + extraZ);
        }
    }

    public double getJumpStrength() {
        return 1;//TODO: Jump Strength for all rideable dinos
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob otherParent) {
        if (otherParent instanceof Prehistoric) {
            Entity baby = type().entityType().create(level);
            if (baby instanceof Prehistoric prehistoric) {
                prehistoric.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(blockPosition()),
                        MobSpawnType.BREEDING, new Prehistoric.PrehistoricGroupData(0), null);
                prehistoric.grow(0);
                return prehistoric;
            }
        }
        return null;
    }

    public boolean canReachPrey() {
        return getTarget() != null && getAttackBounds().intersects(getTarget().getBoundingBox()) && !isPreyBlocked(getTarget());
    }

    public boolean isPreyBlocked(Entity prey) {
        return !getSensing().hasLineOfSight(prey);
    }

    public boolean canSeeFood(BlockPos position) {
        Vec3 target = new Vec3(position.getX() + 0.5, position.getY() + 0.5, position.getZ() + 0.5);
        BlockHitResult rayTrace = level.clip(new ClipContext(position(), target, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, this));
        return rayTrace.getType() != HitResult.Type.MISS;
    }

    protected float getSoundVolume() {
        return isBaby() ? super.getSoundVolume() * 0.75f : 1;
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        // TODO Implement this properly, all dinosaurs may run away after attacking player if pvp is disabled
        //Not to be affected: Compso, Lio, Megalodon, Mosa, Nautilus, Spino. Why? Who knows
        // Should the check be based on size?
        boolean wasEffective = super.doHurtTarget(target);
        if (!wasEffective) setFleeing(true);
        return wasEffective;
    }

    @Override
    public void handleEntityEvent(byte id) {
        if (id == 45) {
            spawnItemParticle(Items.WHEAT_SEEDS);
            spawnItemParticle(Items.WHEAT_SEEDS);
            spawnItemParticle(Items.WHEAT_SEEDS);
        } else if (id == 46) {
            spawnItemParticle(Items.BREAD);
            spawnItemParticle(Items.BREAD);
            spawnItemParticle(Items.BREAD);
        } else if (id == 47) {
            spawnItemParticle(Items.BEEF);
            spawnItemParticle(Items.BEEF);
            spawnItemParticle(Items.BEEF);
        } else {
            super.handleEntityEvent(id);
        }
    }

    public void spawnItemParticle(Item item) {
        if (this.level.isClientSide) return;
        double motionX = random.nextGaussian() * 0.07D;
        double motionY = random.nextGaussian() * 0.07D;
        double motionZ = random.nextGaussian() * 0.07D;
        double f = (float) (random.nextFloat() * (this.getBoundingBox().maxX - this.getBoundingBox().minX) + this.getBoundingBox().minX);
        double f1 = (float) (random.nextFloat() * (this.getBoundingBox().maxY - this.getBoundingBox().minY) + this.getBoundingBox().minY);
        double f2 = (float) (random.nextFloat() * (this.getBoundingBox().maxZ - this.getBoundingBox().minZ) + this.getBoundingBox().minZ);
        this.level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(item)), f, f1, f2, motionX, motionY, motionZ);
    }

    public float getMaxTurnDistancePerTick() {
        return Mth.clamp(90 - this.getBbWidth() * 20, 10, 90);
    }

    @Override
    public boolean canJump() {
        return isVehicle();
    }

    @Override
    public void handleStartJump(int i) {
        playSound(SoundEvents.HORSE_JUMP, 0.4f, 1);
    }

    @Override
    public void handleStopJump() {
    }

    @Override
    public void onPlayerJump(int jumpPower) {
        playerJumpPendingScale = jumpPower >= 90 ? 1.0f : 0.4f + 0.4f * (float) jumpPower / 90.0f;
    }

    public float getProximityToNextPathSkip() {
        return this.getBbWidth() > 0.75F ? this.getBbWidth() / 2.0F : 0.75F - this.getBbWidth() / 2.0F;
    }

    public boolean useLeapAttack() {
        return false;
    }

    public boolean isFleeing() {
        return entityData.get(FLEEING);
    }

    public void setFleeing(boolean fleeing) {
        entityData.set(FLEEING, fleeing);
    }

    public boolean shouldStartEatAnimation() {
        return entityData.get(START_EAT_ANIMATION);
    }

    public void setStartEatAnimation(boolean start) {
        //TODO: There is still a delay between the feeding start and the animation start. Maybe do it similar to attack delay?
        entityData.set(START_EAT_ANIMATION, start);
    }

    protected int getFleeingCooldown() {
        if (this.getLastHurtByMob() != null) {
            int i = (int) (Math.max(this.getLastHurtByMob().getBbWidth() / 2F, 1) * 95);
            int j = (int) (Math.min(this.getBbWidth() / 2F, 0.5D) * 50);
            return i - j;
        }
        return 100;
    }

    @Override
    public @NotNull Packet<?> getAddEntityPacket() {
        return NetworkManager.createAddEntityPacket(this);
    }

    public EntityDataManager.Data data() {
        return EntityDataManager.ENTITY_DATA.getData(EntityType.getKey(getType()).getPath());
    }

    private Stat stats() {
        return data().stats();
    }

    private AI ai() {
        return data().ai();
    }

    public Gender getGender() {
        return gender == null ? Gender.MALE : gender;
    }

    public void setGender(@NotNull Gender gender) {
        this.gender = gender;
        refreshTexturePath();
    }

    @Override
    public Map<String, Animation> getAllAnimations() {
        return GeckoLibCache.getInstance().getAnimations().get(animationLocation).animations();
    }

    @Override
    public Map<String, AnimationInfoManager.ServerAnimationInfo> getServerAnimationInfos() {
        return AnimationInfoManager.ANIMATIONS.getAnimation(animationLocation.getPath());
    }

    public @Nullable AnimationLogic.ActiveAnimationInfo getActiveAnimation(String controller) {
        CompoundTag animationTag = entityData.get(ACTIVE_ANIMATIONS).getCompound(controller);
        if (animationTag.contains("Animation")) {
            return new AnimationLogic.ActiveAnimationInfo(animationTag.getString("Animation"), animationTag.getDouble("EndTick"));
        }
        return null;
    }

    public void addActiveAnimation(String controller, Animation animation) {
        CompoundTag allAnimations = new CompoundTag().merge(entityData.get(ACTIVE_ANIMATIONS));
        CompoundTag animationTag = new CompoundTag();
        if (animation != null) {
            animationTag.putString("Animation", animation.animationName);
            animationTag.putDouble("EndTick", level.getGameTime() + animation.animationLength);
            allAnimations.put(controller, animationTag);
            entityData.set(ACTIVE_ANIMATIONS, allAnimations);
        } else {
            Fossil.LOGGER.error("Prehistoric Animation is null: " + controller);
        }
    }

    public abstract @NotNull Animation nextChasingAnimation();

    public abstract @NotNull Animation nextAttackAnimation();

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "Movement/Idle/Eat", 5, animationLogic::movementPredicate));
        data.addAnimationController(new AnimationController<>(this, "Attack", 5, animationLogic::attackPredicate));
    }

    public AttackAnimationLogic<Prehistoric> getAnimationLogic() {
        return animationLogic;
    }

    public record PrehistoricGroupData(int ageInDays) implements SpawnGroupData {
    }
}
