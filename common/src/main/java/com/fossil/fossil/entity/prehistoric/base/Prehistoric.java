package com.fossil.fossil.entity.prehistoric.base;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.advancements.ModTriggers;
import com.fossil.fossil.block.IDinoUnbreakable;
import com.fossil.fossil.config.FossilConfig;
import com.fossil.fossil.entity.ModEntities;
import com.fossil.fossil.entity.ToyBase;
import com.fossil.fossil.entity.ai.*;
import com.fossil.fossil.entity.ai.control.SmoothTurningMoveControl;
import com.fossil.fossil.entity.ai.navigation.PrehistoricPathNavigation;
import com.fossil.fossil.entity.animation.AnimationInfoManager;
import com.fossil.fossil.entity.animation.AnimationLogic;
import com.fossil.fossil.entity.data.AI;
import com.fossil.fossil.entity.data.EntityDataManager;
import com.fossil.fossil.entity.data.EntityHitboxManager;
import com.fossil.fossil.entity.data.Stat;
import com.fossil.fossil.entity.prehistoric.Deinonychus;
import com.fossil.fossil.entity.prehistoric.Velociraptor;
import com.fossil.fossil.entity.prehistoric.parts.MultiPart;
import com.fossil.fossil.item.ModItems;
import com.fossil.fossil.network.MessageHandler;
import com.fossil.fossil.network.debug.SyncDebugInfoMessage;
import com.fossil.fossil.sounds.ModSounds;
import com.fossil.fossil.util.Diet;
import com.fossil.fossil.util.FoodMappings;
import com.fossil.fossil.util.Gender;
import com.fossil.fossil.util.Version;
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
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.effect.MobEffectUtil;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
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
import org.apache.commons.lang3.tuple.Pair;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.processor.IBone;
import software.bernie.geckolib3.core.snapshot.BoneSnapshot;
import software.bernie.geckolib3.geo.render.built.GeoBone;
import software.bernie.geckolib3.resource.GeckoLibCache;

import java.util.*;

import static com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityInfoAI.*;

public abstract class Prehistoric extends TamableAnimal implements PlayerRideableJumping, EntitySpawnExtension, PrehistoricAnimatable<Prehistoric>, PrehistoricDebug {

    public static final EntityDataAccessor<CompoundTag> DEBUG = SynchedEntityData.defineId(Prehistoric.class, EntityDataSerializers.COMPOUND_TAG);
    private static final EntityDataAccessor<Boolean> EATING = SynchedEntityData.defineId(Prehistoric.class, EntityDataSerializers.BOOLEAN);
    public static final EntityDataAccessor<Integer> MOOD = SynchedEntityData.defineId(Prehistoric.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> AGE_TICK = SynchedEntityData.defineId(Prehistoric.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> HUNGER = SynchedEntityData.defineId(Prehistoric.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> FLEEING = SynchedEntityData.defineId(Prehistoric.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> SITTING = SynchedEntityData.defineId(Prehistoric.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> SLEEPING = SynchedEntityData.defineId(Prehistoric.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> CLIMBING = SynchedEntityData.defineId(Prehistoric.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> AGING_DISABLED = SynchedEntityData.defineId(Prehistoric.class, EntityDataSerializers.BOOLEAN);
    public final MoodSystem moodSystem = new MoodSystem(this);
    protected final WhipSteering steering = new WhipSteering(this);
    private final AnimationLogic<Prehistoric> animationLogic = new AnimationLogic<>(this);
    private final ResourceLocation animationLocation;
    public OrderType currentOrder;
    protected boolean hasFeatherToggle = false;
    protected boolean featherToggle;
    protected boolean hasTeenTexture = true;
    protected boolean hasBabyTexture = true;
    private float frustumWidthRadius;
    private float frustumHeightRadius;
    private int ticksSat;
    private int ticksSlept;
    public float pediaScale;
    public ResourceLocation textureLocation;
    protected DinoMatingGoal matingGoal;
    protected float playerJumpPendingScale;
    private Gender gender = Gender.random(random);
    private int fleeTicks = 0;
    /**
     * Sleep cooldown for mobs with {@link Activity#BOTH}
     */
    private int cathermalSleepCooldown = 0;
    private int matingCooldown = random.nextInt(6000) + 6000;
    private int ticksClimbing = 0;
    private int climbingCooldown = 0;
    private final List<MultiPart> parts = new ArrayList<>();
    private final Map<String, MultiPart> partsByRef = new HashMap<>();

    protected Prehistoric(EntityType<? extends Prehistoric> entityType, Level level) {
        super(entityType, level);
        this.animationLocation = new ResourceLocation(Fossil.MOD_ID, "animations/" + EntityType.getKey(entityType).getPath() + ".animation.json");
        this.moveControl = new SmoothTurningMoveControl(this);
        this.setHunger(this.getMaxHunger() / 2);
        this.pediaScale = 1.0F;
        this.currentOrder = OrderType.WANDER;
        this.updateAbilities();
        if (this.getMobType() == MobType.WATER) {
            this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
            this.getNavigation().getNodeEvaluator().setCanFloat(true);
        }
        setPersistenceRequired();
        List<EntityHitboxManager.Hitbox> hitboxes = hitboxes();
        if (hitboxes != null && !hitboxes.isEmpty()) {
            spawnHitBoxes(hitboxes, entityType);
        }
    }

    private void spawnHitBoxes(List<EntityHitboxManager.Hitbox> hitboxes, EntityType<? extends Prehistoric> entityType) {
        float maxFrustumWidthRadius = 0;
        float maxFrustumHeightRadius = 0;
        for (EntityHitboxManager.Hitbox hitbox : hitboxes) {
            MultiPart part = MultiPart.get(this, hitbox);
            parts.add(part);
            if (hitbox.ref() != null) {
                partsByRef.put(hitbox.ref(), part);
            }
            //Caching this value might be overkill but this ensures that the entity will be visible even if parts are outside its bounding box
            float j = hitbox.getFrustumWidthRadius() + entityType.getDimensions().width / 2;
            if (j > maxFrustumWidthRadius) {
                maxFrustumWidthRadius = j;
            }
            float h = hitbox.getFrustumHeightRadius() + entityType.getDimensions().height;
            if (h > maxFrustumHeightRadius) {
                maxFrustumHeightRadius = h;
            }
        }
        //TODO: SLEEPING_DIMENSIONS
        frustumWidthRadius = maxFrustumWidthRadius;
        frustumHeightRadius = maxFrustumHeightRadius;
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
                .add(Attributes.MAX_HEALTH, 20)
                .add(Attributes.ATTACK_DAMAGE, 2)
                .add(Attributes.FLYING_SPEED, 0.4f);
    }

    @Override
    protected void registerGoals() {
        matingGoal = new DinoMatingGoal(this, 1);
        goalSelector.addGoal(1, new DinoPanicGoal(this, 1.5));
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
        entityData.define(EATING, false);
        entityData.define(MOOD, 0);
        entityData.define(AGE_TICK, data().adultAgeDays() * 24000);
        entityData.define(HUNGER, 0);
        entityData.define(FLEEING, false);
        entityData.define(SITTING, false);
        entityData.define(SLEEPING, false);
        entityData.define(CLIMBING, false);
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
        compound.putInt("MatingCooldown", getMatingCooldown());
        compound.putInt("PlayingCooldown", moodSystem.getPlayingCooldown());
        compound.putInt("Hunger", getHunger());
        compound.putBoolean("Fleeing", isFleeing());
        compound.putBoolean("Sitting", isSitting());
        compound.putBoolean("Sleeping", isSleeping());
        compound.putInt("TicksSlept", ticksSlept);
        compound.putInt("TicksClimbing", ticksClimbing);
        compound.putInt("ClimbingCooldown", climbingCooldown);
        compound.putByte("CurrentOrder", (byte) currentOrder.ordinal());
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
        setMatingCooldown(compound.getInt("MatingCooldown"));
        moodSystem.setPlayingCooldown(compound.getInt("PlayingCooldown"));
        setHunger(compound.getInt("Hunger"));
        setFleeing(compound.getBoolean("Fleeing"));
        setSitting(compound.getBoolean("Sitting"));
        setSleeping(compound.getBoolean("Sleeping"));
        ticksSlept = compound.getInt("TicksSlept");
        ticksClimbing = compound.getInt("TicksClimbing");
        climbingCooldown = compound.getInt("ClimbingCooldown");
        if (compound.contains("CurrentOrder", CompoundTag.TAG_BYTE)) {
            setOrder(OrderType.values()[compound.getByte("CurrentOrder")]);
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
    }

    public static boolean isEntitySmallerThan(Entity entity, float size) {
        if (entity instanceof Prehistoric prehistoric) {
            return prehistoric.getBbWidth() <= size;
        } else {
            return entity.getBbWidth() <= size;
        }
    }

    public static boolean canBreak(Block block) {
        //TODO: Big break Test
        if (block instanceof IDinoUnbreakable) return false;
        BlockState state = block.defaultBlockState();
        if (!state.requiresCorrectToolForDrops()) return false;
        return !state.is(BlockTags.NEEDS_DIAMOND_TOOL);
    }

    public boolean isCustomMultiPart() {
        return !parts.isEmpty();
    }

    /**
     * @return The child parts of this entity.
     * @implSpec On the forge classpath this implementation should return objects that inherit from PartEntity instead of Entity.
     */
    public List<MultiPart> getCustomParts() {
        return parts;
    }

    /**
     * @param ref the name of the bone the hitbox is attached to
     * @return the hitbox attached to the given bone
     */
    @Nullable
    public MultiPart getCustomPart(String ref) {
        return partsByRef.get(ref);
    }

    @Override
    public void refreshDimensions() {
        if (isCustomMultiPart()) {
            super.refreshDimensions();
            for (MultiPart part : parts) {
                part.getEntity().refreshDimensions();
            }
        } else {
            super.refreshDimensions();
        }
    }

    @Override
    public @NotNull AABB getBoundingBoxForCulling() {
        if (isCustomMultiPart()) {
            float x = frustumWidthRadius * getScale() / 2;
            float y = frustumHeightRadius * getScale() / 2;
            AABB aabb = getBoundingBox();
            return new AABB(aabb.minX - x, aabb.minY, aabb.minZ - x, aabb.maxX + x, aabb.maxY + y, aabb.maxZ + x);
        }
        return super.getBoundingBoxForCulling();
    }

    @Override
    public void setId(int id) {
        super.setId(id);
        for (int i = 0; i < parts.size(); ++i) {
            parts.get(i).getEntity().setId(id + i + 1);
        }
    }

    @Override
    public void remove(RemovalReason reason) {
        super.remove(reason);
        if (Version.debugEnabled()) {
            for (WrappedGoal availableGoal : goalSelector.getAvailableGoals()) {
                if (availableGoal.getGoal() instanceof CacheMoveToBlockGoal goal) {
                    goal.stop();//Only for the debug message
                }
            }
        }
        if (isCustomMultiPart()) {
            //Ensures that the callbacks get called. Probably not necessary because the multiparts are not added to the server
            for (MultiPart part : parts) {
                part.getEntity().remove(reason);
            }
        }
    }

    @Override
    public void onClientRemoval() {
        super.onClientRemoval();
        if (isCustomMultiPart()) {
            //Ensures that the callbacks get called on the client side
            for (MultiPart part : parts) {
                part.getEntity().remove(RemovalReason.DISCARDED);
            }
        }
    }

    public boolean hurt(Entity part, DamageSource source, float damage) {
        return hurt(source, damage);
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
        return getType().getDimensions().scale(getScale());
    }

    public abstract PrehistoricEntityInfo info();

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
        } else if (spawnDataIn == null) {
            spawnDataIn = new PrehistoricGroupData(data().adultAgeDays());
            setAgeInDays(((PrehistoricGroupData) spawnDataIn).ageInDays);
        }
        updateAbilities();
        moodSystem.setPlayingCooldown(0);
        setMatingCooldown(24000);
        heal(getMaxHealth());
        currentOrder = OrderType.WANDER;
        setNoAi(false);
        return spawnDataIn;
    }

    public OrderType getOrderType() {
        return this.currentOrder;
    }

    @Override
    public boolean isImmobile() {//TODO: isVehicle not necessary for goals
        return getHealth() <= 0 || isSitting() || isActuallyWeak() || isVehicle() || isSleeping();
    }

    public boolean isSitting() {
        return entityData.get(SITTING);
    }

    public void setSitting(boolean sitting) {
        entityData.set(SITTING, sitting);
    }

    @Override
    public boolean isSleeping() {
        return entityData.get(SLEEPING);
    }

    @Override
    public void startSleeping(BlockPos pos) {
        setSleeping(true);
        getNavigation().stop();
    }

    public void setSleeping(boolean sleeping) {
        entityData.set(SLEEPING, sleeping);
        if (!sleeping) {
            cathermalSleepCooldown = 10000 + random.nextInt(6000);
            setPose(Pose.STANDING);
        } else {
            setPose(Pose.SLEEPING);
        }
    }

    @Override
    public void stopSleeping() {
    }

    public void setOrder(OrderType newOrder) {
        //TODO: Look into this
        currentOrder = newOrder;
    }

    @Override
    public boolean isPushable() {
        //TODO: Maybe also !isVehicle()?
        return super.isPushable();
    }

    /**
     * @return whether something is preventing the mob from sitting
     */
    public boolean canSit() {
        return !isVehicle() && !isPassenger() && !isActuallyWeak();
    }

    /**
     * @return whether something is preventing the mob from sleeping
     */
    public boolean canSleep() {
        if ((getTarget() == null || getTarget() instanceof ToyBase) && getLastHurtByMob() == null && !isInWater() && !isVehicle() && !isActuallyWeak()) {
            return getOrderType() != OrderType.FOLLOW;
        }
        return false;
    }

    /**
     * Returns whether the mob can sleep. Depends on the time of day and how long it has been asleep.
     *
     * @return whether the mob can sleep at the moment
     */
    public boolean wantsToSleep() {
        if (aiActivityType() == Activity.DIURNAL) {
            return !level.isDay();
        } else if (aiActivityType() == Activity.NOCTURNAL) {
            return level.isDay() && !level.canSeeSky(blockPosition().above());
        }
        return aiActivityType() == Activity.BOTH && ticksSlept <= 4000 && cathermalSleepCooldown == 0;
    }

    @Override
    public boolean isFood(ItemStack stack) {
        return false;
    }

    @Override
    public boolean canBeLeashed(Player player) {
        return super.canBeLeashed(player) && isOwnedBy(player);
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

    @Override
    public double getPassengersRidingOffset() {
        return getBbHeight() * 0.72;
    }

    @Override
    public void positionRider(Entity passenger) {
        super.positionRider(passenger);
        if (hasPassenger(passenger) && passenger instanceof Mob mob) {
            yBodyRot = mob.yBodyRot;
        }
        Player rider = getRidingPlayer();
        if (rider != null && isOwnedBy(rider) && getTarget() != rider) {
            if (level.isClientSide) {
                AnimationData data = getFactory().getOrCreateAnimationData(getId());
                Map<String, Pair<IBone, BoneSnapshot>> map = data.getBoneSnapshotCollection();
                if (map.get("riderPos") != null) {
                    if (map.get("riderPos").getLeft() instanceof GeoBone geoBone) {
                        float radius = (geoBone.getPivotZ() * getScale() / 16) * -1;
                        float angle = (Mth.DEG_TO_RAD * yBodyRot);
                        double extraX = radius * Mth.sin((float) (Math.PI + angle));
                        double extraY = geoBone.getPivotY() * getScale() / 16 - 0.2;
                        double extraZ = radius * Mth.cos(angle);
                        rider.setPos(getX() + extraX, getY() + extraY + rider.getMyRidingOffset(), getZ() + extraZ);
                    }
                }
            } else {
                //TODO: Figure out if this needs to be more accurate
                rider.setPos(getX(), getY() + getPassengersRidingOffset() + rider.getMyRidingOffset(), getZ());
            }
        }
        if (passenger instanceof Velociraptor || passenger instanceof Deinonychus) {
            //TODO: Offset for leap attack
        }
    }

    public double getJumpStrength() {
        return 1;//TODO: Jump Strength for all rideable dinos
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
            //TODO: Fall damage reset
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
    protected void customServerAiStep() {
        super.customServerAiStep();
        setSprinting(getMoveControl().getSpeedModifier() >= 1.25);
    }

    @Override
    public void aiStep() {
        updateSwingTime();
        super.aiStep();
        if ((getTarget() != null || getLastHurtByMob() != null) && isSleeping()) {
            setSleeping(false);
        }
        if (getHunger() > getMaxHunger()) {
            setHunger(getMaxHunger());
        }
        moodSystem.tick();
        if (getMatingCooldown() > 0) {
            setMatingCooldown(getMatingCooldown() - 1);
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
        if (isSitting()) {
            ticksSat++;
        }
        if (!level.isClientSide) {
            if (Version.debugEnabled()) {
                MessageHandler.DEBUG_CHANNEL.sendToPlayers(((ServerLevel) level).getPlayers(serverPlayer -> serverPlayer.distanceTo(this) < 16),
                        new SyncDebugInfoMessage(getId(), gender.name(), getAge(), matingCooldown, moodSystem.getPlayingCooldown(), climbingCooldown, moodSystem.getMood()));
            }
            if (cathermalSleepCooldown > 0) {
                cathermalSleepCooldown--;
            }
            if (isSleeping()) {
                ticksSlept++;
                if (!wantsToSleep() || !canSleep()) {
                    setSitting(false);
                    setSleeping(false);
                }
            } else {
                ticksSlept = 0;
                if (!isInWater()) {
                    if (!isSitting() && canSit() && random.nextInt(1000) == 1 && getTarget() == null) {
                        //TODO: Maybe !isSleeping?. Maybe a priority system?
                        setSitting(true);
                        ticksSat = 0;
                    }
                    if (isSitting() && (!canSit() || ticksSat > 100 && random.nextInt(100) == 1 || getTarget() != null)) {
                        setSitting(false);
                    }
                }
                if (wantsToSleep()) {
                    if (aiActivityType() == Activity.BOTH) {
                        if (random.nextInt(1200) == 1) {
                            setSitting(false);
                            startSleeping(BlockPos.ZERO);
                        }
                    } else if (aiActivityType() != Activity.NO_SLEEP) {
                        if (random.nextInt(200) == 1) {
                            setSitting(false);
                            startSleeping(BlockPos.ZERO);
                        }
                    }
                }
            }
            if (currentOrder == OrderType.STAY && !isSitting() && canSit()) {
                setSitting(true);
                ticksSat = 0;
                setSleeping(false);
            }
        }
        if (data().breaksBlocks() && moodSystem.getMood() < 0) {
            breakBlock(5);//TODO: Check if only server side
        }
        if (getTarget() instanceof ToyBase && (isPreyBlocked(getTarget()) || moodSystem.getPlayingCooldown() > 0)) {
            setTarget(null);
        }
        if (isFleeing()) {
            fleeTicks++;
            if (fleeTicks > getFleeingCooldown()) {
                this.setFleeing(false);
                fleeTicks = 0;
            }
        }
        if (isCustomMultiPart()) {
            for (MultiPart part : parts) {
                part.updatePosition();
            }
        }
    }

    public List<EntityHitboxManager.Hitbox> hitboxes() {
        return EntityHitboxManager.HITBOX_DATA.getHitboxes(EntityType.getKey(getType()).getPath());
    }

    @Override
    public void tick() {
        super.tick();

        if (!isAgingDisabled()) {
            setAgeInTicks(getAge() + 1);
        }
        if (tickCount % 1200 == 0 && getHunger() > 0 && FossilConfig.isEnabled(FossilConfig.STARVING_DINOS)) {
            if (!isNoAi()) {
                setHunger(getHunger() - 1);
            } else {
                setHunger(getMaxHunger());
                setHealth(getMaxHealth());
            }
        }
        if (getHealth() > getMaxHealth() / 2 && getHunger() == 0 && tickCount % 40 == 0) {
            hurt(DamageSource.STARVE, 1);
        }
        if (!level.isClientSide && aiClimbType() == Climbing.ARTHROPOD) {
            if (isClimbing()) {
                ticksClimbing++;
                boolean onCooldown = ticksClimbing >= 100 || level.getBlockState(blockPosition().above()).getMaterial().isSolid();
                if (isSleeping() || wantsToSleep() || onCooldown || !horizontalCollision) {
                    setClimbing(false);
                    ticksClimbing = 0;
                    if (onCooldown) {
                        climbingCooldown = 900;
                    }
                }
            } else {
                climbingCooldown--;
                if (ticksClimbing == 0 && climbingCooldown <= 0 && horizontalCollision && !wantsToSleep() && !isSleeping()) {
                    ticksClimbing = 0;
                    setClimbing(true);
                }
            }
        }
    }

    @Override
    public boolean onClimbable() {
        if (aiMovingType() == Moving.AQUATIC || aiMovingType() == Moving.SEMI_AQUATIC) {
            return false;
        } else {
            return aiClimbType() == Climbing.ARTHROPOD && isClimbing() && !isImmobile();
        }
    }

    public boolean isClimbing() {
        return entityData.get(CLIMBING);
    }

    public void setClimbing(boolean climbing) {
        this.entityData.set(CLIMBING, climbing);
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
    public float getScale() {//TODO: Refresh Eye Height
        if (isAdult()) {
            return data().maxScale() * getGenderedScale();
        }
        float step = (data().maxScale() - data().minScale()) / ((data().adultAgeDays() * 24000) + 1);
        return (data().minScale() + step * getAge()) * getGenderedScale();
    }

    public float getGenderedScale() {
        return 1;
    }

    @Override
    protected int getExperienceReward(Player player) {
        float base = 6 * getBbWidth() * (info().diet == Diet.HERBIVORE ? 1 : 2)
                * (aiTameType() == Taming.GEM ? 1 : 2)
                * (aiAttackType() == Attacking.BASIC ? 1 : 1.25f);
        return Mth.floor((float) Math.min(data().adultAgeDays(), getAgeInDays()) * base);
    }

    public void updateAbilities() {
        double percent = Math.min(getAgeInDays() / data().adultAgeDays(), 1);

        double healthDifference = getAttributeValue(Attributes.MAX_HEALTH);
        getAttribute(Attributes.MAX_HEALTH).setBaseValue(Math.round(Mth.lerp(percent, stats().baseHealth(), stats().maxHealth())));
        healthDifference = getAttributeValue(Attributes.MAX_HEALTH) - healthDifference;
        heal((float) healthDifference);
        getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(Math.round(Mth.lerp(percent, stats().baseDamage(), stats().maxDamage())));
        float scale = (data().minScale() + (data().maxScale() - data().minScale()) / (data().adultAgeDays() * 24000) * getAge());
        scale = Math.min(scale, data().maxScale());
        double newSpeed = stats().baseSpeed();
        if (scale < 1) {
            float min = data().minScale();
            float max = data().maxScale() > 1 ? 1 : data().maxScale();
            if (min != max) {
                newSpeed = Mth.lerp((scale - min) / (max - min), stats().minSpeed(), stats().baseSpeed());
            }
        } else {
            float min = data().minScale() < 1 ? 1 : data().minScale();
            float max = data().maxScale();
            if (max != min) {
                newSpeed = Mth.lerp((scale - min) / (max - min), stats().baseSpeed(), stats().maxSpeed());
            }
        }
        getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(newSpeed);
        getAttribute(Attributes.ARMOR).setBaseValue(Mth.lerp(percent, stats().baseArmor(), stats().maxArmor()));
        getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(Mth.lerp(percent, stats().baseKnockBackResistance(), stats().maxKnockBackResistance()));
    }

    public void breakBlock(float maxHardness) {
        if (!FossilConfig.isEnabled(FossilConfig.DINOS_BREAK_BLOCKS) || !level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
            return;
        }
        if (!isAdult() || !isHungry()) {
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
        return getAgeInDays() < data().teenAgeDays();
    }

    public int getMaxHunger() {
        return data().maxHunger();
    }

    public int getAgeInDays() {
        return getAge() / 24000;
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
        if (tickCount % 100 == 0) {
            updateAbilities();
        }
    }

    public boolean isAgingDisabled() {
        return this.entityData.get(AGING_DISABLED);
    }

    public void setAgingDisabled(boolean isAgingDisabled) {
        this.entityData.set(AGING_DISABLED, isAgingDisabled);
    }

    public int getMatingCooldown() {
        return matingCooldown;
    }

    public void setMatingCooldown(int cooldown) {
        this.matingCooldown = cooldown;
    }

    public int getClimbingCooldown() {
        return climbingCooldown;
    }

    public void setClimbingCooldown(int cooldown) {
        this.climbingCooldown = cooldown;
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
        entityData.set(HUNGER, Math.min(hunger, getMaxHunger()));
    }

    public void eatItem(ItemStack stack) {
        if (stack != null && (FoodMappings.getFoodAmount(stack.getItem(), info().diet) != 0)) {
            moodSystem.increaseMood(5);
            doFoodEffect(stack.getItem());
            setHunger(getHunger() + FoodMappings.getFoodAmount(stack.getItem(), info().diet));
            stack.shrink(1);
            setStartEatAnimation(true);
        }
    }

    public void feed(int foodAmount) {
        setHunger(getHunger() + foodAmount);
        level.playSound(null, blockPosition(), SoundEvents.GENERIC_EAT, SoundSource.NEUTRAL, getSoundVolume(), getVoicePitch());
    }

    @Override
    public int getCurrentSwingDuration() {
        int time = 10;
        var activeAnimation = getAnimationLogic().getActiveAnimation("Attack");
        if (activeAnimation != null) {
            time = (int) (getAllAnimations().get(activeAnimation.animationName()).animationLength * 20);
        }

        if (MobEffectUtil.hasDigSpeed(this)) {
            time -= 1 + MobEffectUtil.getDigSpeedAmplification(this);
        }

        if (hasEffect(MobEffects.DIG_SLOWDOWN)) {
            time += (1 + getEffect(MobEffects.DIG_SLOWDOWN).getAmplifier()) * 2;
        }

        return time;
    }

    @Override
    public void killed(ServerLevel level, LivingEntity killedEntity) {
        super.killed(level, killedEntity);
        if (info().diet != Diet.HERBIVORE) {
            feed(FoodMappings.getMobFoodPoints(killedEntity, info().diet));
            heal(FoodMappings.getMobFoodPoints(killedEntity, info().diet) / 10f);
            moodSystem.increaseMood(25);
        }
    }

    @Override
    public boolean rideableUnderWater() {
        return true;
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source == DamageSource.IN_WALL) {
            return false;
        }
        if (getLastHurtByMob() instanceof Player player && getOwner() == player) {
            setTame(false);
            setOwnerUUID(null);
            moodSystem.increaseMood(-15);
            player.displayClientMessage(new TranslatableComponent("entity.fossil.situation.betrayed", getName()), true);
        }

        if (amount > 0) {
            setSitting(false);
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
        if (stack.isEmpty()) {
            return InteractionResult.PASS;
        }
        if (isWeak() && (aiTameType() == Taming.GEM && stack.is(ModItems.SCARAB_GEM.get()) || aiTameType() == Taming.AQUATIC_GEM && stack.is(ModItems.AQUATIC_SCARAB_GEM.get()))) {
            //Tame with gem
            if (!level.isClientSide) {
                ModTriggers.SCARAB_TAME_TRIGGER.trigger((ServerPlayer) player);
                heal(200);
                moodSystem.setMood(100);
                feed(500);
                getNavigation().stop();
                setTarget(null);
                setLastHurtByMob(null);
                tame(player);
                level.broadcastEntityEvent(this, TOTEM_PARTICLES);
                stack.shrink(1);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }

        if (stack.is(ModItems.CHICKEN_ESSENCE.get()) && aiTameType() != Taming.GEM && aiTameType() != Taming.AQUATIC_GEM) {
            //Grow up with chicken essence
            if (isAdult() || getHunger() >= getMaxHunger()) {
                player.displayClientMessage(new TranslatableComponent("prehistoric.essencefail"), true);
                return InteractionResult.PASS;
            }
            if (!level.isClientSide) {
                if (!player.getAbilities().instabuild) {
                    stack.shrink(1);
                    player.addItem(new ItemStack(Items.GLASS_BOTTLE, 1));
                }
                grow(1);
                setHunger(1 + random.nextInt(getHunger()));
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        if (stack.is(ModItems.STUNTED_ESSENCE.get()) && !isAgingDisabled()) {
            //Stunt growth with stunted essence
            if (!level.isClientSide) {
                setHunger(getHunger() + 20);
                heal(getMaxHealth());
                setAgingDisabled(true);
                stack.shrink(1);
                playSound(SoundEvents.ZOMBIE_VILLAGER_CURE, getSoundVolume(), getVoicePitch());
            }
            spawnItemParticles(stack.getItem(), 15);
            spawnItemParticles(stack.getItem(), 15);
            spawnItemParticles(Items.POISONOUS_POTATO, 15);
            spawnItemParticles(Items.POISONOUS_POTATO, 15);
            spawnItemParticles(Items.EGG, 15);
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        if (FoodMappings.getFoodAmount(stack.getItem(), info().diet) > 0) {
            //Feed dino
            if (getHunger() < getMaxHunger() || getHealth() < getMaxHealth() && FossilConfig.isEnabled(FossilConfig.HEALING_DINOS) || !isTame() && aiTameType() == Taming.FEEDING) {
                if (!level.isClientSide) {
                    setHunger(getHunger() + FoodMappings.getFoodAmount(stack.getItem(), info().diet));
                    eatItem(stack);
                    if (FossilConfig.isEnabled(FossilConfig.HEALING_DINOS)) {
                        heal(3);
                    }
                    if (getHunger() >= getMaxHunger() && isTame()) {
                        player.displayClientMessage(new TranslatableComponent("entity.fossil.situation.full", getName()), true);
                    }
                    if (aiTameType() == Taming.FEEDING && !isTame() && random.nextInt(10) == 1) {
                        tame(player);
                        level.broadcastEntityEvent(this, TOTEM_PARTICLES);
                    }
                }
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        } else {
            if (stack.is(ModItems.WHIP.get()) && aiTameType() != Taming.NONE && isAdult()) {
                if (isOwnedBy(player) && canBeRidden()) {
                    if (getRidingPlayer() == null) {
                        if (!level.isClientSide) {
                            setRidingPlayer(player);
                        }
                        setOrder(OrderType.WANDER);
                        setSitting(false);
                        setSleeping(false);
                    } else if (getRidingPlayer() == player) {
                        setSprinting(true);
                        moodSystem.increaseMood(-5);
                    }
                } else if (FossilConfig.isEnabled(FossilConfig.WHIP_TO_TAME_DINO) && !isTame() && aiTameType() != Taming.AQUATIC_GEM && aiTameType() != Taming.GEM) {
                    if (!level.isClientSide) {
                        moodSystem.increaseMood(-5);
                        if (random.nextInt(5) == 0) {
                            player.displayClientMessage(new TranslatableComponent("entity.fossil.prehistoric.tamed", info().displayName.get()), true);
                            moodSystem.increaseMood(-25);
                            tame(player);
                        }
                    }
                }
                setSitting(false);
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
            if (stack.is(getOrderItem()) && isOwnedBy(player) && !player.isPassenger()) {
                if (!level.isClientSide) {
                    jumping = false;
                    getNavigation().stop();
                    currentOrder = OrderType.values()[(this.currentOrder.ordinal() + 1) % 3];
                    sendOrderMessage(this.currentOrder);
                }
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        }
        return InteractionResult.PASS;
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
        return (getHealth() < 8) && isAdult() && !isTame();
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
        if (!level.isClientSide) {
            return;
        }
        String name = getType().arch$registryName().getPath();
        StringBuilder builder = new StringBuilder();
        builder.append("textures/entity/");
        builder.append(name);
        builder.append("/");
        builder.append(name);
        if (hasBabyTexture && isBaby()) builder.append("_baby");
        if (hasTeenTexture && isTeen()) builder.append("_teen");
        if (!hasTeenTexture && isTeen() || isAdult()) {
            if (gender == Gender.MALE) {
                builder.append("_male");
            } else {
                builder.append("_female");
            }
        }
        if (isSleeping()) builder.append("_sleeping");
        builder.append(".png");
        String path = builder.toString();
        textureLocation = new ResourceLocation(Fossil.MOD_ID, path);
    }

    @Override
    public void playAmbientSound() {
        if (!isSleeping()) {
            super.playAmbientSound();
        }
    }

    public boolean canDinoHunt(LivingEntity target) {
        if (target instanceof ToyBase) {
            return true;
        }
        if (target != null) {
            boolean isFood = FoodMappings.getMobFoodPoints(target, info().diet) > 0;
            if (info().diet != Diet.HERBIVORE && info().diet != Diet.NONE && isFood && canAttack(target)) {
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
        if (otherAnimal == this || otherAnimal.getClass() != getClass() || moodSystem.getMood() <= 50 || !isAdult() || getMatingCooldown() > 0) {
            return false;
        }
        Prehistoric other = ((Prehistoric) otherAnimal);
        return other.gender != gender && other.getMatingCooldown() <= 0 && (matingGoal.getPartner() == null || matingGoal.getPartner() == otherAnimal);
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
            } else if (info().cultivatedBirdEggItem != null) {
                hatchling = new ItemEntity(level, getX(), getY(), getZ(), new ItemStack(info().cultivatedBirdEggItem));
            } else {
                if (FossilConfig.isEnabled(FossilConfig.EGGS_LIKE_CHICKENS) || info().isVivariousAquatic()) {
                    hatchling = new ItemEntity(level, getX(), getY(), getZ(), new ItemStack(info().eggItem));
                } else {
                    hatchling = ModEntities.DINOSAUR_EGG.get().create(level);
                    ((DinosaurEgg) hatchling).setPrehistoricEntityInfo(info());
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
        playSound(SoundEvents.GENERIC_EAT, getSoundVolume(), getVoicePitch());
        if (item != null) {
            spawnItemParticles(item, 4);
        }
    }

    public void doFoodEffect() {
        playSound(SoundEvents.GENERIC_EAT, getSoundVolume(), getVoicePitch());
        switch (info().diet) {
            case HERBIVORE -> spawnItemParticles(Items.WHEAT_SEEDS, 4);
            case OMNIVORE -> spawnItemParticles(Items.BREAD, 4);
            case PISCIVORE -> spawnItemParticles(Items.COD, 4);
            default -> spawnItemParticles(Items.BEEF, 4);
        }
    }

    public void spawnItemParticles(Item item, int count) {
        if (level.isClientSide) {
            for (int i = 0; i < count; i++) {
                double motionX = getRandom().nextGaussian() * 0.07;
                double motionY = getRandom().nextGaussian() * 0.07;
                double motionZ = getRandom().nextGaussian() * 0.07;
                double minX = getBoundingBox().minX;
                double minY = getBoundingBox().minY;
                double minZ = getBoundingBox().minZ;
                float x = (float) (getRandom().nextFloat() * (getBoundingBox().maxX - minX) + minX);
                float y = (float) (getRandom().nextFloat() * (getBoundingBox().maxY - minY) + minY);
                float z = (float) (getRandom().nextFloat() * (getBoundingBox().maxZ - minZ) + minZ);
                level.addParticle(new ItemParticleOption(ParticleTypes.ITEM, new ItemStack(item)), x, y, z, motionX, motionY, motionZ);
            }
        }
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob otherParent) {
        if (otherParent instanceof Prehistoric) {
            Entity baby = info().entityType().create(level);
            if (baby instanceof Prehistoric prehistoric) {
                prehistoric.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(blockPosition()),
                        MobSpawnType.BREEDING, new Prehistoric.PrehistoricGroupData(0), null);
                prehistoric.grow(0);
                return prehistoric;
            }
        }
        return null;
    }

    public boolean canReachPrey(Entity target) {
        return getAttackBounds().intersects(target.getBoundingBox()) && !isPreyBlocked(target);
    }

    public boolean isPreyBlocked(Entity prey) {
        return !getSensing().hasLineOfSight(prey);
    }

    public boolean canSeeFood(BlockPos position) {
        Vec3 target = new Vec3(position.getX() + 0.5, position.getY() + 0.5, position.getZ() + 0.5);
        BlockHitResult rayTrace = level.clip(new ClipContext(position(), target, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, this));
        return rayTrace.getType() != HitResult.Type.MISS;
    }

    @Override
    protected float getSoundVolume() {
        return isBaby() ? super.getSoundVolume() * 0.75f : 1;
    }

    public void startAttack() {
        swing(InteractionHand.MAIN_HAND);
        getAnimationLogic().forceActiveAnimation("Attack", nextAttackAnimation(), "Attack", 1);
    }

    public boolean attackTarget(LivingEntity target) {
        if (getBbWidth() > target.getBbWidth()) {
            double resistance = 1 - target.getAttributeValue(Attributes.KNOCKBACK_RESISTANCE);
            target.setDeltaMovement(target.getDeltaMovement().add(0, 0.4 * resistance + 0.1, 0));
        }
        return doHurtTarget(target);
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

    private static final byte TOTEM_PARTICLES = 35;
    private static final byte WHEAT_SEEDS_PARTICLES = 62;
    private static final byte BREAD_PARTICLES = 63;
    private static final byte BEEF_PARTICLES = 64;

    @Override
    public void handleEntityEvent(byte id) {
        if (id == WHEAT_SEEDS_PARTICLES) {
            spawnItemParticle(Items.WHEAT_SEEDS);
            spawnItemParticle(Items.WHEAT_SEEDS);
            spawnItemParticle(Items.WHEAT_SEEDS);
        } else if (id == BREAD_PARTICLES) {
            spawnItemParticle(Items.BREAD);
            spawnItemParticle(Items.BREAD);
            spawnItemParticle(Items.BREAD);
        } else if (id == BEEF_PARTICLES) {
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
        return Mth.clamp(90 - getBbWidth() * 20, 10, 90);
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

    public boolean isFleeing() {
        return entityData.get(FLEEING);
    }

    public void setFleeing(boolean fleeing) {
        entityData.set(FLEEING, fleeing);
    }

    public boolean shouldStartEatAnimation() {
        return entityData.get(EATING);
    }

    public void setStartEatAnimation(boolean start) {
        //TODO: There is still a delay between the feeding start and the animation start. Maybe do it similar to attack delay?
        entityData.set(EATING, start);
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
        if (level.isClientSide) {
            return GeckoLibCache.getInstance().getAnimations().get(animationLocation).animations();
        }
        return AnimationInfoManager.ANIMATIONS.getClientAnimations(animationLocation.getPath());
    }

    @Override
    public Map<String, AnimationInfoManager.ServerAnimationInfo> getServerAnimationInfos() {
        return AnimationInfoManager.ANIMATIONS.getServerAnimations(animationLocation.getPath());
    }

    public abstract @NotNull Animation nextAttackAnimation();

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "Movement/Idle/Eat", 5, animationLogic::landPredicate));
        data.addAnimationController(new AnimationController<>(this, "Attack", 5, animationLogic::attackPredicate));
    }

    public AnimationLogic<Prehistoric> getAnimationLogic() {
        return animationLogic;
    }

    public record PrehistoricGroupData(int ageInDays) implements SpawnGroupData {
    }
}
