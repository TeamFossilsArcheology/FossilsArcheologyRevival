package com.fossil.fossil.entity.prehistoric.base;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.advancements.ModTriggers;
import com.fossil.fossil.client.renderer.entity.PrehistoricGeoRenderer;
import com.fossil.fossil.config.FossilConfig;
import com.fossil.fossil.entity.ModEntities;
import com.fossil.fossil.entity.ai.*;
import com.fossil.fossil.entity.ai.control.SmoothTurningMoveControl;
import com.fossil.fossil.entity.ai.navigation.PrehistoricPathNavigation;
import com.fossil.fossil.entity.animation.AnimationInfoManager;
import com.fossil.fossil.entity.animation.AnimationLogic;
import com.fossil.fossil.entity.data.AI;
import com.fossil.fossil.entity.data.Attribute;
import com.fossil.fossil.entity.data.EntityDataManager;
import com.fossil.fossil.entity.data.EntityHitboxManager;
import com.fossil.fossil.entity.prehistoric.Deinonychus;
import com.fossil.fossil.entity.prehistoric.Velociraptor;
import com.fossil.fossil.entity.prehistoric.parts.MultiPart;
import com.fossil.fossil.entity.util.InstructionSystem;
import com.fossil.fossil.entity.util.Util;
import com.fossil.fossil.item.ModItems;
import com.fossil.fossil.network.C2SHitPlayerMessage;
import com.fossil.fossil.network.MessageHandler;
import com.fossil.fossil.network.debug.C2SDisableAIMessage;
import com.fossil.fossil.network.debug.C2SSyncDebugInfoMessage;
import com.fossil.fossil.sounds.ModSounds;
import com.fossil.fossil.util.Diet;
import com.fossil.fossil.util.FoodMappings;
import com.fossil.fossil.util.Gender;
import com.fossil.fossil.util.Version;
import dev.architectury.extensions.network.EntitySpawnExtension;
import dev.architectury.networking.NetworkManager;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
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
import net.minecraft.util.Mth;
import net.minecraft.world.Difficulty;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.RandomLookAroundGoal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.GameRules;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.AABB;
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

    private static final EntityDataAccessor<CompoundTag> DEBUG = SynchedEntityData.defineId(Prehistoric.class, EntityDataSerializers.COMPOUND_TAG);
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
    private final InstructionSystem instructionSystem = new InstructionSystem(this);
    public final ResourceLocation animationLocation;
    private OrderType currentOrder;
    protected boolean hasTeenTexture = true;
    protected boolean hasBabyTexture = true;
    private float headRadius;
    private float frustumWidthRadius;
    private float frustumHeightRadius;
    public ResourceLocation textureLocation;
    protected DinoMatingGoal matingGoal;
    protected float playerJumpPendingScale;
    private Gender gender = Gender.random(random);
    public int ticksSlept;
    /**
     * Sleep cooldown for mobs with {@link PrehistoricEntityInfoAI.Activity#BOTH}
     */
    private int cathermalSleepCooldown = 0;
    private int fleeTicks = 0;
    private int matingCooldown = random.nextInt(6000) + 6000;
    private int ticksClimbing = 0;
    private int climbingCooldown = 0;
    public long attackBoxEndTime;
    private final List<MultiPart> parts = new ArrayList<>();
    private final Map<String, MultiPart> partsByRef = new HashMap<>();
    public Vec3 eatPos;
    public final Map<String, EntityHitboxManager.Hitbox> attackBoxes = new HashMap<>();
    public final Map<EntityHitboxManager.Hitbox, Vec3> activeAttackBoxes = new HashMap<>();

    protected Prehistoric(EntityType<? extends Prehistoric> entityType, Level level, ResourceLocation animationLocation) {
        super(entityType, level);
        this.animationLocation = animationLocation;
        this.moveControl = new SmoothTurningMoveControl(this);
        this.setHunger(this.getMaxHunger() / 2);
        this.currentOrder = OrderType.WANDER;
        this.updateAbilities();
        if (this.getMobType() == MobType.WATER) {
            this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
            this.getNavigation().getNodeEvaluator().setCanFloat(true);
        }
        setPersistenceRequired();
        List<EntityHitboxManager.Hitbox> hitboxes = EntityHitboxManager.HITBOX_DATA.getHitboxes(EntityType.getKey(getType()).getPath());
        if (hitboxes != null && !hitboxes.isEmpty()) {
            spawnHitBoxes(hitboxes, entityType);
        }
        if (level.isClientSide) {
            System.out.println(Fossil.LOGGER.isDebugEnabled());
            for (Animation anim : getAllAnimations().values()) {
                Fossil.LOGGER.debug("{} is loop: {}", anim.animationName, anim.loop);
            }
        }
    }

    protected Prehistoric(EntityType<? extends Prehistoric> entityType, Level level) {
        this(entityType, level, new ResourceLocation(Fossil.MOD_ID, "animations/" + EntityType.getKey(entityType).getPath() + ".animation.json"));
    }

    private void spawnHitBoxes(List<EntityHitboxManager.Hitbox> hitboxes, EntityType<? extends Prehistoric> entityType) {
        float maxFrustumWidthRadius = 0;
        float maxFrustumHeightRadius = 0;
        for (EntityHitboxManager.Hitbox hitbox : hitboxes) {
            if (hitbox.isAttackBox()) {
                attackBoxes.put(hitbox.ref(), hitbox);
            } else {
                MultiPart part = MultiPart.get(this, hitbox);
                parts.add(part);
                if (hitbox.ref() != null) {
                    partsByRef.put(hitbox.ref(), part);
                }
                //Caching this value might be overkill but this ensures that the entity will be visible even if parts are outside its bounding box
                float j = hitbox.getFrustumWidthRadius();
                if (hitbox.name().contains("head") && (headRadius == 0 || j > maxFrustumWidthRadius)) {
                    headRadius = j;
                }
                if (j > maxFrustumWidthRadius) {
                    maxFrustumWidthRadius = j;
                }
                float h = hitbox.getFrustumHeightRadius();
                if (h > maxFrustumHeightRadius) {
                    maxFrustumHeightRadius = h;
                }
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
        if (aiTameType() == Taming.GEM || aiTameType() == Taming.AQUATIC_GEM) {
            goalSelector.addGoal(Util.IMMOBILE, new ActuallyWeakGoal(this));
        }
        goalSelector.addGoal(Util.IMMOBILE + 1, new DinoPanicGoal(this, 1.5));
        goalSelector.addGoal(Util.IMMOBILE + 2, new FloatGoal(this));
        goalSelector.addGoal(Util.SLEEP, new DinoSleepGoal(this));
        goalSelector.addGoal(Util.SLEEP + 1, new DinoSitGoal(this));
        goalSelector.addGoal(Util.SLEEP + 2, matingGoal);
        goalSelector.addGoal(Util.NEEDS, new EatFromFeederGoal(this));
        goalSelector.addGoal(Util.NEEDS + 1, new EatItemEntityGoal(this));
        goalSelector.addGoal(Util.NEEDS + 2, new EatBlockGoal(this));
        goalSelector.addGoal(Util.NEEDS + 3, new PlayGoal(this, 1));
        goalSelector.addGoal(Util.WANDER, new DinoFollowOwnerGoal(this, 1, 10, 2, false));
        goalSelector.addGoal(Util.WANDER + 1, new DinoWanderGoal(this, 1));
        goalSelector.addGoal(Util.LOOK, new LookAtPlayerGoal(this, Player.class, 8.0f));
        goalSelector.addGoal(Util.LOOK + 1, new RandomLookAroundGoal(this));
        targetSelector.addGoal(1, new DinoOwnerHurtByTargetGoal(this));
        targetSelector.addGoal(2, new DinoOwnerHurtTargetGoal(this));
        targetSelector.addGoal(3, new DinoHurtByTargetGoal(this));
        targetSelector.addGoal(5, new HuntingTargetGoal(this));
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
    public void onSyncedDataUpdated(EntityDataAccessor<?> key) {
        if (SLEEPING.equals(key)) {
            refreshTexturePath();
        }
        super.onSyncedDataUpdated(key);
    }

    @Override
    public void saveAdditionalSpawnData(FriendlyByteBuf buf) {
        buf.writeBoolean(getGender() == Gender.MALE);
        buf.writeInt(getAge());
    }

    @Override
    public void loadAdditionalSpawnData(FriendlyByteBuf buf) {
        if (buf.readBoolean()) {
            gender = Gender.MALE;
        } else {
            gender = Gender.FEMALE;
        }
        setAgeInTicks(buf.readInt());
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
        if (compound.contains("CurrentOrder", Tag.TAG_BYTE)) {
            setCurrentOrder(OrderType.values()[compound.getByte("CurrentOrder")]);
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
    public boolean isPickable() {
        return !isCustomMultiPart();
    }

    @Override
    public @NotNull AABB getBoundingBoxForCulling() {
        if (isCustomMultiPart()) {
            float x = frustumWidthRadius * getScale();
            float y = frustumHeightRadius * getScale();
            Vec3 pos = position();
            return new AABB(pos.x - x, pos.y, pos.z - x, pos.x + x, pos.y + y, pos.z + x);
        }
        return super.getBoundingBoxForCulling();
    }

    public AABB getAttackBounds() {
        if (headRadius != 0) {
            float radius = headRadius * getScale();
            return getBoundingBox().inflate(radius, radius * 0.5, radius);
        }
        float increase = Math.min(getBbWidth() / 2, 2.25f);
        return getBoundingBox().inflate(increase);
    }

    public float getHeadRadius() {
        return headRadius * getScale();
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
        ((PrehistoricGeoRenderer<? extends Prehistoric>) Minecraft.getInstance().getEntityRenderDispatcher().getRenderer(this)).removeTickForEntity(this);
    }

    public boolean hurt(Entity part, DamageSource source, float damage) {
        return hurt(source, damage);
    }

    @Override
    public @NotNull EntityDimensions getDimensions(Pose poseIn) {
        return getType().getDimensions().scale(getScale());
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

    public OrderType getCurrentOrder() {
        return this.currentOrder;
    }

    public void setCurrentOrder(OrderType newOrder) {
        //TODO: Look into this
        currentOrder = newOrder;
    }

    @Override
    public boolean isImmobile() {
        return super.isImmobile() || isActuallyWeak();
    }

    @Override
    public boolean isPushable() {
        return super.isPushable() && !isVehicle();
    }

    /**
     * Returns whether the mob can sleep. Depends on the time of day and how long it has been asleep.
     *
     * @return whether the mob can sleep at the moment
     */
    public boolean wantsToSleep() {
        if (aiActivityType() == PrehistoricEntityInfoAI.Activity.DIURNAL) {
            return !level.isDay();
        } else if (aiActivityType() == PrehistoricEntityInfoAI.Activity.NOCTURNAL) {
            return level.isDay() && !level.canSeeSky(blockPosition().above());
        }
        return aiActivityType() == PrehistoricEntityInfoAI.Activity.BOTH && ticksSlept <= 4000 && cathermalSleepCooldown == 0;
    }

    public boolean hasTarget() {
        return getTarget() != null || moodSystem.getToyTarget() != null;
    }

    @Override
    public float getWalkTargetValue(BlockPos pos, LevelReader level) {
        return 0;
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
                if (map.get("rider_pos") != null) {
                    if (map.get("rider_pos").getLeft() instanceof GeoBone geoBone) {
                        float radius = (geoBone.getPivotZ() * getScale() / 16) * -1;
                        float angle = (Mth.DEG_TO_RAD * yBodyRot);
                        double extraX = radius * Mth.sin((float) (Math.PI + angle));
                        double extraY = geoBone.getPivotY() * getScale() / 16 - 0.2;
                        double extraZ = radius * Mth.cos(angle);
                        rider.setPos(getX() + extraX, getY() + extraY + rider.getMyRidingOffset(), getZ() + extraZ);
                    }
                }
            } else {
                //Not sure if this will be accurate enough for larger dinos. Need to await player feedback
                rider.setPos(getX(), getY() + getPassengersRidingOffset() + rider.getMyRidingOffset(), getZ());
            }
        }
        if (passenger instanceof Velociraptor || passenger instanceof Deinonychus) {
            //TODO: Offset for leap attack
        }
    }

    public double getJumpStrength() {
        return 0.3;
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
        playerJumpPendingScale = jumpPower >= 90 ? 1.0f : 0.4f + 0.4f * jumpPower / 90.0f;
    }

    @Override
    public boolean canBeControlledByRider() {
        return data().canBeRidden() && getControllingPassenger() instanceof LivingEntity rider && isOwnedBy(rider);
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
            if (isInWater()) {
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
    public boolean rideableUnderWater() {
        return true;
    }

    @Override
    public boolean causeFallDamage(float distance, float damageMultiplier, DamageSource source) {
        if (aiClimbType() == Climbing.ARTHROPOD || aiMovingType() == Moving.WALK_AND_GLIDE || aiMovingType() == Moving.FLIGHT) {
            return false;
        } else {
            return super.causeFallDamage(distance, damageMultiplier, source);
        }
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
        if (getHunger() > getMaxHunger()) {
            setHunger(getMaxHunger());
        }
        if (getMatingCooldown() > 0) {
            setMatingCooldown(getMatingCooldown() - 1);
        }
        if (getRidingPlayer() != null) {
            maxUpStep = 1;
        } else {
            maxUpStep = 0.6f;
        }
        if (FossilConfig.isEnabled(FossilConfig.HEALING_DINOS) && !level.isClientSide) {
            if (random.nextInt(500) == 0 && deathTime == 0) {
                heal(1);
            }
        }

        if (!level.isClientSide) {
            if (Version.debugEnabled()) {
                MessageHandler.DEBUG_CHANNEL.sendToPlayers(((ServerLevel) level).getPlayers(serverPlayer -> serverPlayer.distanceTo(this) < 16),
                        new C2SSyncDebugInfoMessage(getId(), gender.name(), getAge(), matingCooldown, moodSystem.getPlayingCooldown(), climbingCooldown, getHunger(), moodSystem.getMood()));
            }
            if (cathermalSleepCooldown > 0) {
                cathermalSleepCooldown--;
            }
            moodSystem.tick();
            instructionSystem.tick();
        }
        if (!level.isClientSide && horizontalCollision && data().breaksBlocks() && moodSystem.getMood() < 0) {
            breakBlock((float) FossilConfig.getDouble(FossilConfig.BLOCK_BREAK_HARDNESS));
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

    @Override
    public void tick() {
        super.tick();

        if (!isAgingDisabled()) {
            setAgeInTicks(getAge() + 1);
        }
        if (tickCount % 1200 == 0 && getHunger() > 0 && FossilConfig.isEnabled(FossilConfig.ENABLE_HUNGER)) {
            if (!isNoAi()) {
                setHunger(getHunger() - 1);
            } else {
                setHunger(getMaxHunger());
                setHealth(getMaxHealth());
            }
        }
        if (tickCount % 40 == 0 && getHunger() == 0) {
            if (getHealth() > (FossilConfig.isEnabled(FossilConfig.ENABLE_STARVATION) ? 0 : getMaxHealth() / 2)) {
                hurt(DamageSource.STARVE, 1);
            }
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
        if (level.isClientSide && !activeAttackBoxes.isEmpty()) {
            if (level.getGameTime() > attackBoxEndTime) {
                activeAttackBoxes.clear();
            }
            for (Map.Entry<EntityHitboxManager.Hitbox, Vec3> entry : activeAttackBoxes.entrySet()) {
                EntityHitboxManager.Hitbox hitbox = entry.getKey();
                EntityDimensions size = EntityDimensions.scalable(hitbox.width(), hitbox.height()).scale(getScale());
                AABB aabb = size.makeBoundingBox(entry.getValue());
                if (Minecraft.getInstance().player.getBoundingBox().intersects(aabb)) {
                    activeAttackBoxes.clear();
                    MessageHandler.SYNC_CHANNEL.sendToServer(new C2SHitPlayerMessage(this, Minecraft.getInstance().player));
                    break;
                }
            }
        }
    }

    @Override
    public float getScale() {
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
        float base = 6 * getBbWidth() * (data().diet() == Diet.HERBIVORE ? 1 : 2)
                * (aiTameType() == Taming.GEM ? 2 : 1)
                * (aiAttackType() == Attacking.BASIC ? 1 : 1.25f);
        return Mth.floor((float) Math.min(data().adultAgeDays(), getAgeInDays()) * base);
    }

    public void updateAbilities() {
        double percent = Math.min(getAge() / data().adultAgeDays() * 24000, 1);

        double healthDifference = getAttributeValue(Attributes.MAX_HEALTH);
        getAttribute(Attributes.MAX_HEALTH).setBaseValue(Math.round(Mth.lerp(percent, attributes().baseHealth(), attributes().maxHealth())));
        healthDifference = getAttributeValue(Attributes.MAX_HEALTH) - healthDifference;
        heal((float) healthDifference);
        getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(Math.round(Mth.lerp(percent, attributes().baseDamage(), attributes().maxDamage())));
        float scale = (data().minScale() + (data().maxScale() - data().minScale()) / (data().adultAgeDays() * 24000) * getAge());
        scale = Math.min(scale, data().maxScale());
        getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(Util.calculateSpeed(data(), scale));
        getAttribute(Attributes.ARMOR).setBaseValue(Mth.lerp(percent, attributes().baseArmor(), attributes().maxArmor()));
        getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(Mth.lerp(percent, attributes().baseKnockBackResistance(), attributes().maxKnockBackResistance()));
    }

    public void breakBlock(float maxHardness) {
        if (!FossilConfig.isEnabled(FossilConfig.DINOS_BREAK_BLOCKS) || !level.getGameRules().getBoolean(GameRules.RULE_MOBGRIEFING)) {
            return;
        }
        if (!isAdult() || !isHungry()) {
            return;
        }
        AABB aabb = getBoundingBox().inflate(0.1, 0, 0.1);
        boolean waterMob = this instanceof PrehistoricSwimming;
        int lowest = Mth.floor(aabb.minY) + (waterMob ? 0 : 1);
        for (BlockPos targetPos : BlockPos.betweenClosed(Mth.floor(aabb.minX), lowest, Mth.floor(aabb.minZ), Mth.floor(aabb.maxX), Mth.ceil(aabb.maxY), Mth.floor(aabb.maxZ))) {
            if (Util.canBreak(level, targetPos, maxHardness)) {
                setDeltaMovement(getDeltaMovement().multiply(0.6, 1, 0.6));
                level.destroyBlock(targetPos, random.nextInt(10) == 0, this);
                if (waterMob && targetPos.getY() == lowest) {
                    level.setBlock(targetPos, Blocks.WATER.defaultBlockState(), 3);
                }
            }
        }
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
        setDeltaMovement(Vec3.ZERO);
        hasImpulse = true;
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

    @Override
    public boolean onClimbable() {
        return aiClimbType() == Climbing.ARTHROPOD && isClimbing();
    }

    public boolean isClimbing() {
        return entityData.get(CLIMBING);
    }

    public void setClimbing(boolean climbing) {
        this.entityData.set(CLIMBING, climbing);
    }

    public boolean isFleeing() {
        return entityData.get(FLEEING);
    }

    public void setFleeing(boolean fleeing) {
        entityData.set(FLEEING, fleeing);
    }

    protected int getFleeingCooldown() {
        if (this.getLastHurtByMob() != null) {
            int i = (int) (Math.max(this.getLastHurtByMob().getBbWidth() / 2F, 1) * 95);
            int j = (int) (Math.min(this.getBbWidth() / 2F, 0.5D) * 50);
            return i - j;
        }
        return 100;
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
        if (tickCount % 120 == 0) {
            refreshDimensions();
        }
        if (tickCount % 12000 == 0) {
            refreshTexturePath();
            updateAbilities();
        }
    }

    public void grow(int ageInDays) {
        if (isAgingDisabled()) {
            return;
        }
        setAgeInDays(getAgeInDays() + ageInDays);
        updateAbilities();
        level.broadcastEntityEvent(this, GROW_UP_PARTICLES);
    }

    public boolean isAgingDisabled() {
        return this.entityData.get(AGING_DISABLED);
    }

    public void setAgingDisabled(boolean isAgingDisabled) {
        this.entityData.set(AGING_DISABLED, isAgingDisabled);
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
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob otherParent) {
        if (otherParent instanceof Prehistoric) {
            Entity baby = info().entityType().create(level);
            if (baby instanceof Prehistoric prehistoricBaby) {
                prehistoricBaby.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(blockPosition()),
                        MobSpawnType.BREEDING, new Prehistoric.PrehistoricGroupData(0), null);
                prehistoricBaby.grow(0);
                return prehistoricBaby;
            }
        }
        return null;
    }

    public void procreate(Prehistoric other) {
        if (getGender() == Gender.MALE) {
            setTarget(null);
            return;
        }
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
            hatchling.moveTo(getX(), getY(), getZ(), yBodyRot, 0);
            if (hatchling instanceof Prehistoric prehistoricHatchling) {
                prehistoricHatchling.finalizeSpawn((ServerLevelAccessor) level, level.getCurrentDifficultyAt(blockPosition()),
                        MobSpawnType.BREEDING, new Prehistoric.PrehistoricGroupData(0), null);
                prehistoricHatchling.grow(0);
            }
            level.addFreshEntity(hatchling);
        }
    }

    public int getMatingCooldown() {
        return matingCooldown;
    }

    public void setMatingCooldown(int cooldown) {
        this.matingCooldown = cooldown;
    }

    public Gender getGender() {
        return gender == null ? Gender.MALE : gender;
    }

    public void setGender(@NotNull Gender gender) {
        this.gender = gender;
        refreshTexturePath();
    }

    public int getClimbingCooldown() {
        return climbingCooldown;
    }

    public void setClimbingCooldown(int cooldown) {
        this.climbingCooldown = cooldown;
    }

    public boolean isWeak() {
        return getHealth() < 8 && isAdult() && !isTame();
    }

    public boolean isActuallyWeak() {
        return (aiTameType() == Taming.AQUATIC_GEM || aiTameType() == Taming.GEM) && isWeak();
    }

    public int getMaxHunger() {
        return data().maxHunger();
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
        if (stack != null && (FoodMappings.getFoodAmount(stack.getItem(), data().diet()) != 0)) {
            moodSystem.increaseMood(5);
            feed(FoodMappings.getFoodAmount(stack.getItem(), data().diet()));
            stack.shrink(1);
            animationLogic.triggerAnimation(AnimationLogic.IDLE_CTRL, nextEatingAnimation(), AnimationLogic.Category.EAT);
        }
    }

    public void feed(int foodAmount) {
        setHunger(getHunger() + foodAmount);
    }

    @Override
    public void killed(ServerLevel level, LivingEntity killedEntity) {
        super.killed(level, killedEntity);
        if (data().diet() != Diet.HERBIVORE) {
            feed(FoodMappings.getMobFoodPoints(killedEntity, data().diet()));
            heal(FoodMappings.getMobFoodPoints(killedEntity, data().diet()) / 10f);
            moodSystem.increaseMood(25);
        }
    }

    @Override
    public boolean hurt(DamageSource source, float amount) {
        if (source == DamageSource.IN_WALL) {
            return false;
        }
        if (getLastHurtByMob() instanceof Player player && getOwner() == player) {
            setOwnerUUID(null);
            setTame(false);
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
    protected void reassessTameGoals() {
        if (!isTame()) {
            setCurrentOrder(OrderType.WANDER);
        }
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
            if (!level.isClientSide) {
                if (isAdult()) {
                    player.displayClientMessage(new TranslatableComponent("prehistoric.essence_fail_adult"), true);
                    return InteractionResult.PASS;
                }
                if (isDeadlyHungry()) {
                    player.displayClientMessage(new TranslatableComponent("prehistoric.essence_fail_hungry"), true);
                    return InteractionResult.PASS;
                }
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
            } else {
                AABB aabb = eatPos == null ? getBoundingBoxForCulling() : new AABB(eatPos, eatPos);
                Util.spawnItemParticles(level, stack.getItem(), 15, aabb);
                Util.spawnItemParticles(level, stack.getItem(), 15, aabb);
                Util.spawnItemParticles(level, Items.POISONOUS_POTATO, 15, aabb);
                Util.spawnItemParticles(level, Items.POISONOUS_POTATO, 15, aabb);
                Util.spawnItemParticles(level, Items.EGG, 15, aabb);
            }
            return InteractionResult.sidedSuccess(level.isClientSide);
        }
        if (FoodMappings.getFoodAmount(stack.getItem(), data().diet()) > 0) {
            //Feed dino
            if (getHunger() < getMaxHunger() || getHealth() < getMaxHealth() && FossilConfig.isEnabled(FossilConfig.HEALING_DINOS) || !isTame() && aiTameType() == Taming.FEEDING) {
                if (!level.isClientSide) {
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
                if (isOwnedBy(player) && data().canBeRidden()) {
                    if (getRidingPlayer() == null && !level.isClientSide) {
                        player.yBodyRot = this.yBodyRot;
                        player.setXRot(getXRot());
                        player.startRiding(this);
                        setCurrentOrder(OrderType.WANDER);
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
                    currentOrder = OrderType.values()[(currentOrder.ordinal() + 1) % 3];
                    sendOrderMessage(currentOrder);
                }
                return InteractionResult.sidedSuccess(level.isClientSide);
            }
        }
        return InteractionResult.PASS;
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
        textureLocation = new ResourceLocation(Fossil.MOD_ID, builder.toString());
    }

    @Override
    public boolean canAttack(LivingEntity target) {
        if (isOwnedBy(target) && moodSystem.getMoodFace() != PrehistoricMoodType.ANGRY) {
            return false;
        }
        if (target instanceof Player && level.getDifficulty() == Difficulty.PEACEFUL) {
            return false;
        }
        return target.canBeSeenAsEnemy();
    }

    public float getTargetScale() {
        return 1;
    }

    @Override
    protected @NotNull PathNavigation createNavigation(Level levelIn) {
        return aiClimbType() == Climbing.ARTHROPOD ? new WallClimberNavigation(this, levelIn) : new PrehistoricPathNavigation(this, levelIn);
    }

    public List<? extends Prehistoric> getNearbySpeciesMembers(int range) {
        return level.getEntitiesOfClass(getClass(), getBoundingBox().inflate(range, 4.0D, range), prehistoric -> prehistoric != this);
    }

    @Override
    public void playAmbientSound() {
        if (!isSleeping()) {
            super.playAmbientSound();
        }
    }

    @Override
    protected float getSoundVolume() {
        return isBaby() ? super.getSoundVolume() * 0.75f : 1;
    }

    public AnimationInfoManager.ServerAnimationInfo startAttack() {
        AnimationInfoManager.ServerAnimationInfo attackAnim = (AnimationInfoManager.ServerAnimationInfo) nextAttackAnimation();
        getAnimationLogic().triggerAnimation(AnimationLogic.ATTACK_CTRL, attackAnim, AnimationLogic.Category.ATTACK);
        return attackAnim;
    }

    public void activateAttackBoxes(double attackDuration) {
        attackBoxes.values().forEach(hitbox -> activeAttackBoxes.put(hitbox, Vec3.ZERO));
        attackBoxEndTime = (long) (level.getGameTime() + attackDuration);
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

    public static final byte TOTEM_PARTICLES = 35;
    public static final byte WHEAT_SEEDS_PARTICLES = 62;
    public static final byte BREAD_PARTICLES = 63;
    public static final byte BEEF_PARTICLES = 64;
    public static final byte HAPPY_VILLAGER_PARTICLES = 65;
    public static final byte GROW_UP_PARTICLES = 66;

    @Override
    public void handleEntityEvent(byte id) {
        if (id == WHEAT_SEEDS_PARTICLES) {
            Util.spawnItemParticles(this, Items.WHEAT_SEEDS, 3);
        } else if (id == BREAD_PARTICLES) {
            Util.spawnItemParticles(this, Items.BREAD, 3);
        } else if (id == BEEF_PARTICLES) {
            Util.spawnItemParticles(this, Items.BREAD, 3);
            Util.spawnItemParticles(this, Items.BEEF, 3);
        } else if (id == HAPPY_VILLAGER_PARTICLES) {
            Util.spawnParticles(this, ParticleTypes.HAPPY_VILLAGER, 6);
        } else if (id == GROW_UP_PARTICLES) {
            Util.spawnParticles(this, ParticleTypes.HAPPY_VILLAGER, getAgeInDays());
        } else {
            super.handleEntityEvent(id);
        }
    }

    private void makeEatingSounds() {
        if (level.isClientSide) {
            level.playSound(Minecraft.getInstance().player, blockPosition(), SoundEvents.GENERIC_EAT, getSoundSource(), getSoundVolume(), getVoicePitch());
        } else {
            playSound(SoundEvents.GENERIC_EAT, getSoundVolume(), getVoicePitch());
        }
    }

    public float getMaxTurnDistancePerTick() {
        return Mth.clamp(90 - getBbWidth() * 20, 10, 90);
    }

    public float getProximityToNextPathSkip() {
        return this.getBbWidth() > 0.75F ? this.getBbWidth() / 2.0F : 0.75F - this.getBbWidth() / 2.0F;
    }

    @Override
    public @NotNull Packet<?> getAddEntityPacket() {
        return NetworkManager.createAddEntityPacket(this);
    }

    public abstract PrehistoricEntityInfo info();

    public abstract Item getOrderItem();

    public EntityDataManager.Data data() {
        return EntityDataManager.ENTITY_DATA.getData(EntityType.getKey(getType()).getPath());
    }

    public Attribute attributes() {
        return data().attributes();
    }

    private AI ai() {
        return data().ai();
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
        return isBaby() ? Response.SCARED : ai().response();
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
    public Map<String, Animation> getAllAnimations() {
        if (level.isClientSide) {
            return GeckoLibCache.getInstance().getAnimations().get(animationLocation).animations();
        }
        return AnimationInfoManager.ANIMATIONS.getClientAnimations(animationLocation);
    }

    @Override
    public Map<String, AnimationInfoManager.ServerAnimationInfo> getServerAnimationInfos() {
        return AnimationInfoManager.ANIMATIONS.getServerAnimations(animationLocation);
    }

    public abstract @NotNull Animation nextAttackAnimation();

    @Override
    public void registerControllers(AnimationData data) {
        var controller = new AnimationController<>(
                this, AnimationLogic.IDLE_CTRL, 5, animationLogic::landPredicate);
        controller.registerParticleListener(event -> {
            if ("eat".equals(event.effect)) {
                //TODO: Could use event.script + getScale to increase the aabb size
                AABB aabb = eatPos == null ? getBoundingBoxForCulling() : new AABB(eatPos, eatPos);
                switch (data().diet()) {
                    case HERBIVORE -> Util.spawnItemParticles(level, Items.WHEAT_SEEDS, 4, aabb);
                    case OMNIVORE -> Util.spawnItemParticles(level, Items.BREAD, 4, aabb);
                    case PISCIVORE -> Util.spawnItemParticles(level, Items.COD, 4, aabb);
                    default -> Util.spawnItemParticles(level, Items.BEEF, 4, aabb);
                }
            }
        });
        controller.registerSoundListener(event -> {
            if ("eat".equals(event.sound)) {
                makeEatingSounds();
            }
        });
        data.addAnimationController(controller);
        data.addAnimationController(new AnimationController<>(
                this, AnimationLogic.ATTACK_CTRL, 5, animationLogic::attackPredicate));
    }

    public AnimationLogic<Prehistoric> getAnimationLogic() {
        return animationLogic;
    }

    public InstructionSystem getInstructionSystem() {
        return instructionSystem;
    }

    @Override
    public CompoundTag getDebugTag() {
        return entityData.get(DEBUG);
    }

    @Override
    public void disableCustomAI(byte type, boolean disableAI) {
        if (!Version.debugEnabled()) return;
        CompoundTag tag = entityData.get(DEBUG).copy();
        switch (type) {
            case 0 -> setNoAi(disableAI);
            case 1 -> tag.putBoolean("disableGoalAI", disableAI);
            case 2 -> tag.putBoolean("disableMoveAI", disableAI);
            case 3 -> tag.putBoolean("disableLookAI", disableAI);
        }
        entityData.set(DEBUG, tag);
        MessageHandler.DEBUG_CHANNEL.sendToPlayers(((ServerLevel) level).getPlayers(serverPlayer -> serverPlayer.distanceTo(this) < 32),
                new C2SDisableAIMessage(getId(), disableAI, type));
    }

    public record PrehistoricGroupData(int ageInDays) implements SpawnGroupData {
    }
}
