package com.fossil.fossil.entity.prehistoric.base;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.block.IDinoUnbreakable;
import com.fossil.fossil.entity.ToyBase;
import com.fossil.fossil.entity.ai.CacheMoveToBlockGoal;
import com.fossil.fossil.entity.ai.DinoAIMating;
import com.fossil.fossil.entity.ai.WhipSteering;
import com.fossil.fossil.entity.ai.navigation.PrehistoricPathNavigation;
import com.fossil.fossil.item.ModItems;
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
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.LookAtPlayerGoal;
import net.minecraft.world.entity.ai.goal.SitWhenOrderedToGoal;
import net.minecraft.world.entity.ai.goal.WrappedGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WallClimberNavigation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.animal.FlyingAnimal;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
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
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;

import java.util.*;

public abstract class Prehistoric extends TamableAnimal implements IPrehistoricAI, PlayerRideableJumping, EntitySpawnExtension, IAnimatable {

    private static final EntityDataAccessor<Integer> AGETICK = SynchedEntityData.defineId(Prehistoric.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Integer> HUNGER = SynchedEntityData.defineId(Prehistoric.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> MODELIZED = SynchedEntityData.defineId(Prehistoric.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> ANGRY = SynchedEntityData.defineId(Prehistoric.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> FLEEING = SynchedEntityData.defineId(Prehistoric.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> SUBSPECIES = SynchedEntityData.defineId(Prehistoric.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<Boolean> SLEEPING = SynchedEntityData.defineId(Prehistoric.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Integer> MOOD = SynchedEntityData.defineId(Prehistoric.class, EntityDataSerializers.INT);
    private static final EntityDataAccessor<String> OWNERDISPLAYNAME = SynchedEntityData.defineId(Prehistoric.class, EntityDataSerializers.STRING);
    private static final EntityDataAccessor<Byte> CLIMBING = SynchedEntityData.defineId(Prehistoric.class, EntityDataSerializers.BYTE);
    private static final EntityDataAccessor<Boolean> AGINGDISABLED = SynchedEntityData.defineId(Prehistoric.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<ItemStack> HOLDING_IN_MOUTH = SynchedEntityData.defineId(Prehistoric.class, EntityDataSerializers.ITEM_STACK);
    private static final EntityDataAccessor<String> CURRENT_ANIMATION = SynchedEntityData.defineId(Prehistoric.class, EntityDataSerializers.STRING);

    private Gender gender; // should be effectively final
    private final boolean isMultiPart;
    public final double baseDamage;
    public final double maxDamage;
    public final double baseHealth;
    public final double maxHealth;
    public final double baseSpeed;
    public final double maxSpeed;
    public final double baseArmor;
    public final double maxArmor;
    // public Animation ATTACK_ANIMATION;
    public final float minScale;
    public final float maxScale;
    public final float baseKnockBackResistance;
    public final float maxKnockBackResistance;
    public final int teenAgeDays;
    public final int adultAgeDays;
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
    public int pediaY = 0;
    public int ticksTillPlay;
    public int ticksTillMate;
    public boolean isDaytime;
    public float ridingXZ;
    public boolean shouldWander = true;
    protected boolean breaksBlocks;
    protected int nearByMobsAllowed;
    protected float playerJumpPendingScale;
    protected boolean isJumping;
    // private Animation currentAnimation;
    private boolean droppedBiofossil = false;
    private int changedAnimationAt;
    private int fleeTicks = 0;
    private int moodCheckCooldown = 0;
    private int cathermalSleepCooldown = 0;
    private int ticksClimbing = 0;
    private float eggScale = 1.0F;
    public final PrehistoricEntityType type;
    // public final Item uncultivatedEggItem;
    public final boolean isCannibalistic;
    public ResourceLocation textureLocation;
    public DinoAIMating matingGoal;
    private final WhipSteering steering = new WhipSteering();

    public Prehistoric(
            EntityType<? extends Prehistoric> entityType, PrehistoricEntityType type,
            Level level,
            boolean isMultiPart,
            boolean isCannibalistic,
            float minScale,
            float maxScale,
            float baseKnockBackResistance,
            float maxKnockBackResistance,
            int teenAgeDays,
            int adultAgeDays,
            double baseDamage,
            double maxDamage,
            double baseHealth,
            double maxHealth,
            double baseSpeed,
            double maxSpeed,
            double baseArmor,
            double maxArmor
    ) {
        super(entityType, level);
        this.type = type;
        this.isMultiPart = isMultiPart;
        this.setHunger(this.getMaxHunger() / 2);
        this.pediaScale = 1.0F;
        this.nearByMobsAllowed = 15;
        this.currentOrder = OrderType.WANDER;
        if (ticksTillMate == 0) {
            ticksTillMate = this.random.nextInt(6000) + 6000;
        }
        this.isCannibalistic = isCannibalistic;
        this.minScale = minScale;
        this.maxScale = maxScale;
        this.baseKnockBackResistance = baseKnockBackResistance;
        this.maxKnockBackResistance = maxKnockBackResistance;
        this.teenAgeDays = teenAgeDays;
        this.adultAgeDays = adultAgeDays;
        this.baseDamage = baseDamage;
        this.maxDamage = maxDamage;
        this.baseHealth = baseHealth;
        this.maxHealth = maxHealth;
        this.baseSpeed = baseSpeed;
        this.maxSpeed = maxSpeed;
        this.baseArmor = baseArmor;
        this.maxArmor = maxArmor;
        this.updateAbilities();
        if (this.getMobType() == MobType.WATER) {
            this.setPathfindingMalus(BlockPathTypes.WATER, -1.0F);
            this.getNavigation().getNodeEvaluator().setCanFloat(true);
        }
        setPersistenceRequired();
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
     *
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
        return !isCustomMultiPart();
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
            return getType().getWidth()*getScale() - getBbWidth();
        }
        return super.getPickRadius();
    }

    @Override
    protected AABB makeBoundingBox() {
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
                goal.stop();
            }
        }
    }
    public boolean hurt(Entity part, DamageSource source, float damage) {
        return super.hurt(source, damage);
    }

    public static AttributeSupplier.Builder createAttributes() {
        return Mob.createMobAttributes()
            .add(Attributes.MAX_HEALTH, 20D)
            .add(Attributes.ATTACK_DAMAGE, 2D);
    }

    public static final EntityDataAccessor<CompoundTag> DEBUG = SynchedEntityData.defineId(Prehistoric.class, EntityDataSerializers.COMPOUND_TAG);

    public void disableCustomAI(byte type, boolean disableAI) {
        CompoundTag tag = entityData.get(DEBUG).copy();
        switch (type) {
            case 0 -> setNoAi(disableAI);
            case 1 -> {
                tag.putBoolean("disableGoalAI", disableAI);
            }
            case 2 -> {
                tag.putBoolean("disableMoveAI", disableAI);
            }
            case 3 -> {
                tag.putBoolean("disableLookAI", disableAI);
            }
        }
        entityData.set(DEBUG, tag);
    }


    @Override
    public @NotNull EntityDimensions getDimensions(Pose poseIn) {
        //return this.getType().getDimensions().scale(this.getScale());
        return this.getType().getDimensions();
    }

    @Override
    protected void registerGoals() {
        this.matingGoal = new DinoAIMating(this, getAttributeValue(Attributes.MOVEMENT_SPEED));
        this.goalSelector.addGoal(2, new SitWhenOrderedToGoal(this));
        this.goalSelector.addGoal(2, matingGoal);
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0f));
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

    public static void knockBackMob(Entity entity, double xMotion, double yMotion, double zMotion) {
        double horizontalSpeed = Math.sqrt(xMotion * xMotion + zMotion * zMotion);
        Vec3 knockBack = entity.getDeltaMovement().scale(0.5).add(
            -xMotion / horizontalSpeed,
            yMotion,
            -zMotion / horizontalSpeed
        );
        entity.setDeltaMovement(knockBack);
        entity.hurtMarked = true;
    }

    public static boolean canBreak(Block block) {
        if (block instanceof IDinoUnbreakable) return false;
        BlockState state = block.defaultBlockState();
        if (!state.requiresCorrectToolForDrops()) return false;
        if (state.is(BlockTags.NEEDS_DIAMOND_TOOL)) return false;
        return true;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(AGETICK, 0);
        this.entityData.define(HUNGER, 0);
        this.entityData.define(MODELIZED, false);
        this.entityData.define(ANGRY, false);
        this.entityData.define(FLEEING, false);
        this.entityData.define(SUBSPECIES, 0);
        this.entityData.define(SLEEPING, false);
        this.entityData.define(CLIMBING, (byte) 0);
        this.entityData.define(MOOD, 0);
        this.entityData.define(OWNERDISPLAYNAME, "");
        this.entityData.define(AGINGDISABLED, false);
        this.entityData.define(HOLDING_IN_MOUTH, ItemStack.EMPTY);
        var idle = nextIdleAnimation();
        this.entityData.define(CURRENT_ANIMATION, idle.animationId);
        changedAnimationAt = tickCount;

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
        compound.putInt("AgeTick", this.getAgeInTicks());
        compound.putInt("Hunger", this.getHunger());
        compound.putBoolean("isModelized", this.isSkeleton());
        compound.putBoolean("Angry", this.isAngry());
        compound.putBoolean("DinoTamed", this.isTame());
        compound.putBoolean("Fleeing", this.isFleeing());
        compound.putInt("SubSpecies", this.getSubSpecies());
        compound.putString("Gender", this.gender.toString());
        compound.putInt("Mood", this.getMood());
        compound.putInt("TicksTillPlay", this.ticksTillPlay);
        compound.putInt("TicksSlept", this.ticksSlept);
        compound.putInt("TicksTillMate", this.ticksTillMate);
        compound.putInt("TicksClimbing", this.ticksClimbing);
        compound.putByte("currentOrder", (byte) this.currentOrder.ordinal());
        compound.putString("OwnerDisplayName", this.getOwnerDisplayName());
        compound.putFloat("YawRotation", this.yBodyRot);
        compound.putFloat("HeadRotation", this.yHeadRot);
        compound.putBoolean("AgingDisabled", this.isAgingDisabled());
        compound.putInt("CathermalTimer", this.cathermalSleepCooldown);
    }


    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        this.setAgeinTicks(compound.getInt("AgeTick"));
        this.setHunger(compound.getInt("Hunger"));
        this.setFleeing(compound.getBoolean("Fleeing"));
        this.setSkeleton(compound.getBoolean("isModelized"));
        this.setAngry(compound.getBoolean("Angry"));
        this.setTame(compound.getBoolean("DinoTamed"));
        this.setSubSpecies(compound.getInt("SubSpecies"));
        if ("female".equalsIgnoreCase(compound.getString("Gender"))) {
            gender = Gender.FEMALE;
        } else {
            gender = Gender.MALE;
        }
        this.setSleeping(compound.getBoolean("Sleeping"));
        this.setOrderedToSit(compound.getBoolean("Sitting"));
        this.setAgingDisabled(compound.getBoolean("AgingDisabled"));
        this.setMood(compound.getInt("Mood"));
        if (compound.contains("currentOrder")) {
            this.setOrder(OrderType.values()[compound.getByte("currentOrder")]);
        }
        this.ticksTillPlay = compound.getInt("TicksTillPlay");
        this.ticksClimbing = compound.getInt("TicksClimbing");
        this.ticksTillMate = compound.getInt("TicksTillMate");
        this.ticksSlept = compound.getInt("TicksSlept");
        this.yBodyRot = compound.getInt("YawRotation");
        this.yHeadRot = compound.getInt("HeadRotation");
        if (compound.contains("Owner", 8)) {
            String s = compound.getString("Owner");
            this.setOwnerDisplayName(s);
        } else {
            this.setOwnerDisplayName(compound.getString("OwnerDisplayName"));
        }
        this.cathermalSleepCooldown = compound.getInt("CathermalTimer");
        refreshTexturePath();
    }

    public String getOwnerDisplayName() {
        return this.entityData.get(OWNERDISPLAYNAME);
    }

    public void setOwnerDisplayName(String displayName) {
        this.entityData.set(OWNERDISPLAYNAME, displayName);
    }

    public AABB getAttackBounds() {
        float size = (float) (this.getBoundingBox().getSize() * 0.25F);
        return this.getBoundingBox().inflate(2.0F + size, 2.0F + size, 2.0F + size);
    }

    @Override
    public SpawnGroupData finalizeSpawn(
        ServerLevelAccessor levelIn,
        DifficultyInstance difficultyIn,
        MobSpawnType reason,
        @Nullable SpawnGroupData spawnDataIn,
        @Nullable CompoundTag dataTag
    ) {
        spawnDataIn = super.finalizeSpawn(levelIn, difficultyIn, reason, spawnDataIn, dataTag);
        this.setAgeInDays(this.adultAgeDays);
        this.updateAbilities();
        ticksTillPlay = 0;
        ticksTillMate = 24000;
        this.onGround = true;
        this.heal(this.getMaxHealth());
        this.currentOrder = OrderType.WANDER;
        this.setNoAi(false);
        if (gender == null) gender = Gender.random(random);

        return spawnDataIn;
    }

    @Override
    public boolean isNoAi() {
        return this.isSkeleton() || super.isNoAi();
    }

    public void useToy(int playBonus) {
        if (ticksTillPlay == 0) {
            this.setMood(this.getMood() + playBonus);
            ticksTillPlay = this.random.nextInt(600) + 600;
        }
    }

    public OrderType getOrderType() {
        return this.currentOrder;
    }

    @Override
    public boolean isImmobile() {
        return this.getHealth() <= 0.0F ||
            isOrderedToSit() ||
            this.isSkeleton() ||
            this.isActuallyWeak() ||
            this.isVehicle() ||
            this.isSleeping();
    }

    @Override
    public boolean isSleeping() {
        return this.entityData.get(SLEEPING);
    }

    public void setSleeping(boolean sleeping) {
        this.entityData.set(SLEEPING, sleeping);
        if (!sleeping) {
            cathermalSleepCooldown = 10000 + random.nextInt(6000);
        }
        refreshTexturePath();
    }

    public BlockPos getBlockToEat(int range) {
        BlockPos pos;

        for (int r = 1; r <= range; r++) {
            for (int ds = -r; ds <= r; ds++) {
                for (int dy = 4; dy > -5; dy--) {
                    int x = Mth.floor(this.getX() + ds);
                    int y = Mth.floor(this.getY() + dy);
                    int z = Mth.floor(this.getZ() - r);
                    if (this.getY() + dy >= 0 &&
                        this.getY() + dy <= this.level.getHeight() &&
                        FoodMappings.getFoodAmount(this.level.getBlockState(new BlockPos(x, y, z)).getBlock(), type.diet) != 0
                    ) {
                        pos = new BlockPos(x, y, z);
                        return pos;
                    }

                    if (this.getY() + dy >= 0 &&
                        this.getY() + dy <= this.level.getHeight() &&
                        FoodMappings.getFoodAmount(this.level.getBlockState(new BlockPos(x, y, z)).getBlock(), type.diet) != 0
                    ) {
                        pos = new BlockPos(x, y, z);
                        return pos;
                    }
                }
            }

            for (int ds = -r + 1; ds <= r - 1; ds++) {
                for (int dy = 4; dy > -5; dy--) {
                    int x = Mth.floor(this.getX() + ds);
                    int y = Mth.floor(this.getY() + dy);
                    int z = Mth.floor(this.getZ() - r);

                    if (this.getY() + dy >= 0 &&
                        this.getY() + dy <= this.level.getHeight() &&
                        FoodMappings.getFoodAmount(this.level.getBlockState(new BlockPos(x, y, z)).getBlock(), type.diet) != 0
                    ) {
                        pos = new BlockPos(x, y, z);
                        return pos;
                    }

                    if (this.getY() + dy >= 0 &&
                        this.getY() + dy <= this.level.getHeight() &&
                        FoodMappings.getFoodAmount(this.level.getBlockState(new BlockPos(x, y, z)).getBlock(), type.diet) != 0
                    ) {
                        pos = new BlockPos(x, y, z);
                        return pos;
                    }
                }
            }
        }

        return null;
    }

    public void setOrder(OrderType newOrder) {
        this.currentOrder = newOrder;
    }

    public boolean arePlantsNearby(int range) {
        for (int i = Mth.floor(this.getX() - range); i < Mth.ceil(this.getX() + range); i++) {
            for (int j = Mth.floor(this.getY() - range / 2.0); j < Mth.ceil(this.getY() + range / 2.0); j++) {
                for (int k = Mth.floor(this.getZ() - range); k < Mth.ceil(this.getZ() + range); k++) {
                    if (j <= this.level.getHeight() + 1D && isPlantBlock(this.level.getBlockState(new BlockPos(i, j, k)))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean isPushable() {
        //TODO: Maybe also !isVehicle()?
        return !this.isSkeleton() && super.isPushable();
    }

    public boolean isPlantBlock(BlockState block) {
        return block.getMaterial() == Material.CACTUS ||
            block.getMaterial() == Material.PLANT ||
            block.getMaterial() == Material.LEAVES ||
            block.getMaterial() == Material.MOSS ||
            block.getMaterial() == Material.VEGETABLE;
    }

    public boolean canSleep() {
        if (this.aiActivityType() == PrehistoricEntityTypeAI.Activity.DIURINAL) {
            return !this.isDaytime();
        } else if (this.aiActivityType() == PrehistoricEntityTypeAI.Activity.NOCTURNAL) {
            return this.isDaytime() && !this.level.canSeeSky(this.blockPosition().above());
        } else return this.aiActivityType() == PrehistoricEntityTypeAI.Activity.BOTH;
    }

    public boolean canWakeUp() {
        if (this.aiActivityType() == PrehistoricEntityTypeAI.Activity.DIURINAL) {
            return this.isDaytime();
        } else if (this.aiActivityType() == PrehistoricEntityTypeAI.Activity.NOCTURNAL) {
            return !this.isDaytime() || this.level.canSeeSky(this.blockPosition().above());
        } else {
            return this.ticksSlept > 4000;
        }
    }

    public boolean isDaytime() {
        return this.level.isDay();
    }

    protected void doMoodCheck() {
        int overallMoodAddition = 0;
        if (this.arePlantsNearby(16)) {
            overallMoodAddition += 50;
        } else {
            overallMoodAddition -= 50;
        }
        if (!this.isThereNearbyTypes()) {
            overallMoodAddition += 50;
        } else {
            overallMoodAddition -= 50;
        }
        this.setMood(this.getMood() + overallMoodAddition);
    }

    /*
        How many dinosaurs of this type can exist in the same small, enclosed space.
     */
    public int getMaxPopulation() {
        return nearByMobsAllowed;
    }

    public boolean wantsToSleep() {
        if (!level.isClientSide &&
            this.aiActivityType() == PrehistoricEntityTypeAI.Activity.BOTH &&
            this.ticksSlept > 8000
        ) {
            return false;
        }
        return !level.isClientSide &&
            this.getTarget() == null &&
            this.getLastHurtByMob() == null &&
            !this.isInWater() &&
            !this.isVehicle() &&
            !this.isActuallyWeak() &&
            this.canSleep() &&
            canSleepWhileHunting() &&
            // (this.getAnimation() == NO_ANIMATION || this.getAnimation() == SPEAK_ANIMATION) &&
            this.getOrderType() != OrderType.FOLLOW;
    }

    private boolean canSleepWhileHunting() {
        return this.getTarget() == null || this.getTarget() instanceof ToyBase;
    }

    @Override
    public @NotNull Packet<?> getAddEntityPacket() {
        return NetworkManager.createAddEntityPacket(this);
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

    @Override
    public void travel(Vec3 travelVector) {
        if ((this.isOrderedToSit() || this.isImmobile()) && !this.isVehicle()) {
            super.travel(Vec3.ZERO);
            return;
        }
        if (!isVehicle() || !canBeControlledByRider()) {
            flyingSpeed = 0.02f;
            super.travel(travelVector);
            return;
        }
        Player rider = getRidingPlayer();
        if (getTarget() != null) {
            setTarget(null);
            getNavigation().stop();
        }
        setYRot(rider.getYRot());
        yRotO = getYRot();
        setXRot(rider.getXRot() * 0.5f);
        setRot(getYRot(), getXRot());
        yHeadRot = yBodyRot = getYRot();
        float newStrafeMovement = rider.xxa * 0.5f;
        float newForwardMovement = rider.zza;
        if (onGround && playerJumpPendingScale > 0 && !isJumping()) {
            double newYMovement = getJumpStrength() * playerJumpPendingScale * getBlockJumpFactor() + getJumpBoostPower();
            Vec3 currentMovement = getDeltaMovement();
            setDeltaMovement(currentMovement.x, newYMovement, currentMovement.z);
            setIsJumping(true);
            hasImpulse = true;
            if (newForwardMovement > 0) {
                float h = Mth.sin(getYRot() * ((float)Math.PI / 180));
                float i = Mth.cos(getYRot() * ((float)Math.PI / 180));
                this.setDeltaMovement(getDeltaMovement().add(-0.4f * h * playerJumpPendingScale, 0.0, 0.4f * i * playerJumpPendingScale));
            }
            playerJumpPendingScale = 0;
        }
        flyingSpeed = getSpeed() * 0.1f;
        fallDistance = 0;
        if (this.isControlledByLocalInstance()) {
            setSpeed((float)getAttributeValue(Attributes.MOVEMENT_SPEED));
            steering.travel(this, rider, travelVector);
            super.travel(new Vec3(newStrafeMovement, travelVector.y, newForwardMovement));
        } else {
            setDeltaMovement(Vec3.ZERO);
        }
        if (onGround) {
            playerJumpPendingScale = 0;
            setIsJumping(false);
        }
    }

    @Override
    @Nullable
    public Entity getControllingPassenger() {
        for (Entity passenger : this.getPassengers()) {
            if (passenger instanceof Player player && this.getTarget() != passenger) {
                if (this.isTame() && this.isOwnedBy(player)) {
                    return player;
                }
            }
        }
        return null;
    }

    @Override
    public void tick() {
        super.tick();
        if (this.isSkeleton()) {
            this.setDeltaMovement(Vec3.ZERO);
        }
        if ((this.getTarget() != null) && this.isSleeping()) {
            this.setSleeping(false);
        }
        if (this.getOwner() != null && this.getOwnerDisplayName().equals("")) {
            this.setOwnerDisplayName(this.getOwner().getDisplayName().toString());
        }
        if (this.getHunger() > this.getMaxHunger()) {
            this.setHunger(this.getMaxHunger());
        }
        if (this.getMood() > 100) {
            this.setMood(100);
        }
        if (this.getMood() < -100) {
            this.setMood(-100);
        }
        if (this.isDeadlyHungry() && this.getMood() > -50) {
            this.setMood(-50);
        }
        if (this.ticksTillPlay > 0) {
            this.ticksTillPlay--;
        }
        if (this.ticksTillMate > 0) {
            this.ticksTillMate--;
        }
        if (this.getRidingPlayer() != null) {
            this.maxUpStep = 1;
        }
        if (Fossil.CONFIG_OPTIONS.healingDinos && !this.level.isClientSide) {
            if (this.random.nextInt(500) == 0 && this.deathTime == 0) {
                this.heal(1.0F);
            }
        }
        if (this.moodCheckCooldown-- <= 0) {
            this.doMoodCheck();
            this.moodCheckCooldown = 3000 + this.getRandom().nextInt(5000);
        }

        if (this.isSleeping()) {
            if ((this.getTarget() != null && this.getTarget().isAlive()) || (this.getLastHurtByMob() != null && this.getLastHurtByMob().isAlive())) {
                this.setSleeping(false);
            }
        }
        if (this.isOrderedToSit()) {
            ticksSat++;
        }
        if (!level.isClientSide) {
            if (this.isSleeping()) {
                ticksSlept++;
            } else {
                ticksSlept = 0;
            }
        }
        if (!level.isClientSide &&
            !this.isInWater() &&
            this.isVehicle() &&
            !this.isOrderedToSit() &&
            this.getRandom().nextInt(1000) == 1 &&
            !this.isPassenger() &&
            // (this.getAnimation() == NO_ANIMATION || this.getAnimation() == SPEAK_ANIMATION) &&
            !this.isSleeping()) {
            this.setOrderedToSit(true);
            ticksSat = 0;
        }

        if (!level.isClientSide &&
            !this.isInWater() &&
            (this.isOrderedToSit() && ticksSat > 100 && this.getRandom().nextInt(100) == 1 || this.getTarget() != null) &&
            !this.isSleeping()) {
            this.setOrderedToSit(false);
            ticksSat = 0;
        }
        if (cathermalSleepCooldown > 0) {
            cathermalSleepCooldown--;
        }
        if (!this.level.isClientSide && this.wantsToSleep()) {
            if (this.aiActivityType() == PrehistoricEntityTypeAI.Activity.BOTH) {
                if (cathermalSleepCooldown == 0) {
                    if (this.getRandom().nextInt(1200) == 1 && !this.isSleeping()) {
                        this.setOrderedToSit(false);
                        this.setSleeping(true);
                    }
                }
            } else if (this.aiActivityType() != PrehistoricEntityTypeAI.Activity.NOSLEEP) {
                if (this.getRandom().nextInt(200) == 1 && !this.isSleeping()) {
                    this.setOrderedToSit(false);
                    this.setSleeping(true);
                }
            }
        }
        if (!this.level.isClientSide && (!this.wantsToSleep() || !this.canSleep() || canWakeUp())) {
            this.setOrderedToSit(false);
            this.setSleeping(false);
        }

        if (this.currentOrder == OrderType.STAY && !this.isOrderedToSit() && !this.isActuallyWeak()) {
            this.setOrderedToSit(true);
            this.setSleeping(false);
        }
        if (breaksBlocks && this.getMood() < 0) {
            this.breakBlock(5);
        }
        if (this.getTarget() != null &&
            this.getTarget() instanceof ToyBase &&
            (isPreyBlocked(this.getTarget()) || this.ticksTillPlay > 0)) {
            this.setTarget(null);
        }
        if (isFleeing()) {
            fleeTicks++;
            if (fleeTicks > getFleeingCooldown()) {
                this.setFleeing(false);
                fleeTicks = 0;
            }
        }

        //don't use the vanilla system
        if (this.age < 0) {
            this.age = 0;
        }
        refreshDimensions();
        if (!this.isSkeleton()) {
            if (!this.isAgingDisabled()) {
                this.setAgeinTicks(this.getAgeInTicks() + 1);
                if (this.getAgeInTicks() % 24000 == 0) {
                    this.updateAbilities();
                    this.grow(0);
                }
            }
            if (this.tickCount % 1200 == 0 && this.getHunger() > 0 && Fossil.CONFIG_OPTIONS.starvingDinos) {
                this.setHunger(this.getHunger() - 1);
            }
            if (this.getHealth() > this.getMaxHealth() / 2 && this.getHunger() == 0 && this.tickCount % 40 == 0) {
                this.hurt(DamageSource.STARVE, 1);
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

        boolean climbing = this.aiClimbType() == PrehistoricEntityTypeAI.Climbing.ARTHROPOD &&
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
            if (this.aiClimbType() == PrehistoricEntityTypeAI.Climbing.ARTHROPOD &&
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

            if (isAnimationFrozen()) {
                setCurrentAnimation(nextIdleAnimation());
            }

            if (getCurrentAnimation().priority <= MOVING_PRIORITY) {
                ServerAnimationInfo next;
                if (navigation.isInProgress()) {
                    next = nextMovingAnimation();
                } else {
                    next = nextIdleAnimation();
                }
                setCurrentAnimation(next);
            }
        }
    }

    private boolean isAboveGround() {
        BlockPos blockPos = blockPosition();
        while (level.getBlockState(blockPos).isAir() && blockPos.getY() > 1) {
            blockPos = blockPos.below();
        }
        return this.getBoundingBox().minY > blockPos.getY();
    }

    @Override
    public abstract PrehistoricEntityTypeAI.Activity aiActivityType();

    @Override
    public abstract PrehistoricEntityTypeAI.Attacking aiAttackType();

    @Override
    public abstract PrehistoricEntityTypeAI.Climbing aiClimbType();

    @Override
    public abstract PrehistoricEntityTypeAI.Following aiFollowType();

    @Override
    public abstract PrehistoricEntityTypeAI.Jumping aiJumpType();

    @Override
    public abstract PrehistoricEntityTypeAI.Response aiResponseType();

    @Override
    public abstract PrehistoricEntityTypeAI.Stalking aiStalkType();

    @Override
    public abstract PrehistoricEntityTypeAI.Taming aiTameType();

    @Override
    public abstract PrehistoricEntityTypeAI.Untaming aiUntameType();

    @Override
    public abstract PrehistoricEntityTypeAI.Moving aiMovingType();

    @Override
    public abstract PrehistoricEntityTypeAI.WaterAbility aiWaterAbilityType();

    public boolean doesFlock() {
        return false;
    }

    @Override
    public float getScale() {
        float step = ((this.maxScale * getFemaleScale()) - this.minScale) / ((this.adultAgeDays * 24000) + 1);
        if (this.getAgeInTicks() > this.adultAgeDays * 24000) {
            return this.minScale + ((step) * this.adultAgeDays * 24000);
        }
        return this.minScale + ((step * this.getAgeInTicks()));
    }

    public float getModelScale() {
        return getScale();
    }

    @Override
    protected int getExperienceReward(Player player) {
        float base = 6 * this.getBbWidth() * (this.type.diet == Diet.HERBIVORE ? 1.0F : 2.0F)
            * (this.aiTameType() == PrehistoricEntityTypeAI.Taming.GEM ? 1.0F : 2.0F)
            * (this.aiAttackType() == PrehistoricEntityTypeAI.Attacking.BASIC ? 1.0F : 1.25F);
        return Mth.floor((float) Math.min(this.adultAgeDays, this.getAgeInDays()) * base);
    }

    public void updateAbilities() {
        double percent = Math.min((1.0 / this.adultAgeDays) * this.getAgeInDays(), 1.0);

        double healthDifference = this.getAttributeValue(Attributes.MAX_HEALTH);
        this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(Math.round(Mth.lerp(percent, baseHealth, maxHealth)));
        healthDifference = this.getAttributeValue(Attributes.MAX_HEALTH) - healthDifference;

        this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(Math.round(Mth.lerp(percent, baseDamage, maxDamage)));
        this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(Mth.lerp(percent, baseSpeed, maxSpeed));
        this.getAttribute(Attributes.ARMOR).setBaseValue(Mth.lerp(percent, baseArmor, maxArmor));
        this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(Mth.lerp(percent, baseKnockBackResistance, maxKnockBackResistance));

        this.heal((float) healthDifference);
    }

    public void breakBlock(float maxHardness) {
        if (!Fossil.CONFIG_OPTIONS.dinoBlockBreaking) return;
        if (isSkeleton()) return;
        if (!this.isAdult()) return;
        if (!this.isHungry()) return;
        // if (!net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.level, this)) return;
        // how do we respect other handlers canceling it?

        for (int a = (int) Math.round(this.getBoundingBox().minX) - 1; a <= (int) Math.round(this.getBoundingBox().maxX) + 1; a++) {
            for (int b = (int) Math.round(this.getBoundingBox().minY) + 1; (b <= (int) Math.round(this.getBoundingBox().maxY) + 2) && (b <= 127); b++) {
                for (int c = (int) Math.round(this.getBoundingBox().minZ) - 1; c <= (int) Math.round(this.getBoundingBox().maxZ) + 1; c++) {
                    BlockPos pos = new BlockPos(a, b, c);
                    if (level.getBlockState(pos).isAir()) continue;
                    BlockState state = level.getBlockState(pos);
                    Block block = state.getBlock();

                    if (block instanceof BushBlock) continue;
                    if (!state.getFluidState().isEmpty()) continue;
                    if (state.getDestroySpeed(level, pos) >= maxHardness) continue;
                    if (!canBreak(state.getBlock())) continue;
                    this.setDeltaMovement(this.getDeltaMovement().multiply(0.6F, 1F, 0.6F));
                    if (!level.isClientSide) {
                        level.destroyBlock(new BlockPos(a, b, c), true);
                    }
                }
            }
        }
    }


    @Nullable
    public Entity createChild() {
        if (level.isClientSide) return null;
        Entity baby = null;
        if (this instanceof Mammal mammal) {
            baby = mammal.createChild((ServerLevel) level);
        }
        if (this instanceof FlyingAnimal) {
            baby = new ItemEntity(this.level, this.getX(), this.getY(), this.getZ(), new ItemStack(type.cultivatedBirdEggItem));
        }
        /*
        For now disable this behavior as egg model does not exist yet.
        if (Fossil.CONFIG_OPTIONS.eggsLikeChickens || this.isVivariousAquatic()) {
        } else {
            baby = new DinosaurEgg(this.level, (EntityType<? extends Prehistoric>) this.getType(), eggScale);
        }
        */
        return baby;
    }

    public boolean isVivariousAquatic() {
        return false;
    }

    public boolean isAdult() {
        return this.getAgeInDays() >= adultAgeDays;
    }

    public boolean isTeen() {
        return this.getAgeInDays() >= teenAgeDays && this.getAgeInDays() < adultAgeDays;
    }

    @Override
    public boolean isBaby() {
        return this.getAgeInDays() < teenAgeDays && !this.isSkeleton();
    }

    public abstract int getMaxHunger();

    public boolean isSkeleton() {
        return this.entityData.get(MODELIZED);
    }

    public void setSkeleton(boolean skeleton) {
        this.entityData.set(MODELIZED, skeleton);
    }

    public int getAgeInDays() {
        return this.entityData.get(AGETICK) / 24000;
    }

    public void setAgeInDays(int days) {
        this.entityData.set(AGETICK, days * 24000);
        refreshDimensions();
        if (!level.isClientSide) {
            updateAbilities();
        }
    }

    public int getAgeInTicks() {
        return this.entityData.get(AGETICK);
    }

    public void setAgeinTicks(int ticks) {
        refreshTexturePath();
        refreshDimensions();
        this.entityData.set(AGETICK, ticks);
    }

    public int getHunger() {
        return this.entityData.get(HUNGER);
    }

    public void setHunger(int hunger) {
        if (this.getHunger() > this.getMaxHunger()) {
            this.entityData.set(HUNGER, this.getMaxHunger());
        } else {
            this.entityData.set(HUNGER, hunger);
        }
    }

    public boolean isAgingDisabled() {
        return this.entityData.get(AGINGDISABLED);
    }

    public void setAgingDisabled(boolean isAgingDisabled) {
        this.entityData.set(AGINGDISABLED, isAgingDisabled);
    }

    public ItemStack getItemInMouth() {
        return this.entityData.get(HOLDING_IN_MOUTH);
    }

    public void setItemInMouth(@NotNull ItemStack stack) {
        this.entityData.set(HOLDING_IN_MOUTH, stack);
    }

    public abstract Map<String, ServerAnimationInfo> getAllAnimations();

    public ServerAnimationInfo getCurrentAnimation() {
        return getAllAnimations().get(this.entityData.get(CURRENT_ANIMATION));
    }

    public void setCurrentAnimation(@NotNull Prehistoric.ServerAnimationInfo newAnimation) {
        if (this.entityData.get(CURRENT_ANIMATION).equals(newAnimation.animationId)) return;
        this.entityData.set(CURRENT_ANIMATION, newAnimation.animationId);
        changedAnimationAt = tickCount;
    }

    public boolean isAnimationFrozen() {
        var current = getCurrentAnimation();
        if (current.isLoop) return false;

        return changedAnimationAt + current.length < tickCount;
    }

    public boolean increaseHunger(int hunger) {
        if (this.getHunger() >= this.getMaxHunger()) {
            return false;
        }
        this.setHunger(this.getHunger() + hunger);
        if (this.getHunger() > this.getMaxHunger()) {
            this.setHunger(this.getMaxHunger());
        }
        this.level.playSound(null,
            this.blockPosition(),
            SoundEvents.GENERIC_EAT,
            SoundSource.NEUTRAL,
            this.getSoundVolume(),
            this.getVoicePitch()
        );
        return true;
    }

    @Override
    public void killed(ServerLevel level, LivingEntity killedEntity) {
        super.killed(level, killedEntity);
        if (type.diet != Diet.HERBIVORE) {
            increaseHunger(FoodMappings.getMobFoodPoints(killedEntity, type.diet));
            heal(FoodMappings.getMobFoodPoints(killedEntity, type.diet) / 3F);
            setMood(getMood() + 25);
        }
    }

    public boolean isHungry() {
        return this.getHunger() < this.getMaxHunger() * 0.75F;
    }

    public boolean isDeadlyHungry() {
        return this.getHunger() < this.getMaxHunger() * 0.25F;
    }

    @Override
    public boolean onClimbable() {
        if (this.aiMovingType() == PrehistoricEntityTypeAI.Moving.AQUATIC ||
            this.aiMovingType() == PrehistoricEntityTypeAI.Moving.SEMIAQUATIC) {
            return false;
        } else {
            return this.aiClimbType() == PrehistoricEntityTypeAI.Climbing.ARTHROPOD &&
                this.isBesideClimbableBlock() && !this.isImmobile();
        }
    }

    public boolean isAngry() {
        return this.entityData.get(ANGRY);
    }

    public void setAngry(boolean angry) {
        this.entityData.set(ANGRY, angry);
    }

    public int getSubSpecies() {
        return this.entityData.get(SUBSPECIES);
    }

    public void setSubSpecies(int subspecies) {
        this.entityData.set(SUBSPECIES, subspecies);
    }

    public int getMood() {
        return Mth.clamp(this.entityData.get(MOOD), -100, 100);
    }

    public void setMood(int mood) {
        this.entityData.set(MOOD, Mth.clamp(mood, -100, 100));
    }

    public PrehistoricMoodType getMoodFace() {
        if (this.getMood() == 100) {
            return PrehistoricMoodType.HAPPY;
        } else if (this.getMood() >= 50) {
            return PrehistoricMoodType.CONTENT;
        } else if (this.getMood() == -100) {
            return PrehistoricMoodType.ANGRY;
        } else if (this.getMood() <= -50) {
            return PrehistoricMoodType.SAD;
        } else {
            return PrehistoricMoodType.CALM;
        }
    }

    public int getScaledMood() {
        return (int) (71 * -(this.getMood() * 0.01));
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
        if (amount > 0 && this.isSkeleton()) {
            this.level.playSound(null,
                this.blockPosition(),
                SoundEvents.SKELETON_HURT,
                SoundSource.NEUTRAL,
                this.getSoundVolume(),
                this.getVoicePitch()
            );
            if (!level.isClientSide && !droppedBiofossil) {
                if (type.timePeriod == TimePeriod.CENOZOIC) {
                    this.spawnAtLocation(ModItems.TAR_FOSSIL.get(), 1);
                } else {
                    this.spawnAtLocation(ModItems.BIO_FOSSIL.get(), 1);
                }
                this.spawnAtLocation(new ItemStack(Items.BONE, Math.min(this.getAgeInDays(), this.adultAgeDays)), 1);
                droppedBiofossil = true;
            }
            this.dead = true;
            return true;
        }
        if (this.getLastHurtByMob() instanceof Player) {
            if (this.getOwner() == this.getLastHurtByMob()) {
                setTame(false);
                setOwnerUUID(null);
                this.setMood(this.getMood() - 15);
            }
        }

        if (amount > 0) {
            this.setOrderedToSit(false);
            this.setSleeping(false);
        }
        if (source.getEntity() != null) {
            this.setMood(this.getMood() - 5);
        }
        /*if (this.getHurtSound(DamageSource.GENERIC) != null && amount >= 1 && dmg != DamageSource.IN_WALL) {
            if (this.getAnimation() != null) {
                if (this.getAnimation() == NO_ANIMATION && level.isClientSide) {
                    this.setAnimation(SPEAK_ANIMATION);
                }
            }
        }*/
        return super.hurt(source, amount);
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
        if (this.aiClimbType() == PrehistoricEntityTypeAI.Climbing.ARTHROPOD ||
            this.aiMovingType() == PrehistoricEntityTypeAI.Moving.WALKANDGLIDE ||
            this.aiMovingType() == PrehistoricEntityTypeAI.Moving.FLIGHT
        ) {
            return false;
        } else {
            return super.causeFallDamage(distance, damageMultiplier, source);
        }
    }

    @Override
    public InteractionResult mobInteract(Player player, InteractionHand hand) {
        ItemStack itemstack = player.getItemInHand(hand);
        if (this.isSkeleton()) {
            if (itemstack.isEmpty()) {
                if (player.isShiftKeyDown()) {
                    this.nudgeEntity(player);
                } else {
                    double d0 = player.getX() - this.getX();
                    double d2 = player.getZ() - this.getZ();
                    float f = (float) (Mth.atan2(d2, d0) * (180D / Math.PI)) - 90.0F;
                    this.yHeadRot = f;
                    this.yBodyRot = f;
                }
                return InteractionResult.SUCCESS;
            } else {
                if (itemstack.is(Items.BONE) && this.getAgeInDays() < this.adultAgeDays) {
                    this.level.playSound(null, this.blockPosition(), SoundEvents.SKELETON_AMBIENT, SoundSource.NEUTRAL, 0.8F, 1);
                    this.setAgeInDays(this.getAgeInDays() + 1);
                    if (!player.isCreative()) {
                        itemstack.shrink(1);
                    }
                    return InteractionResult.SUCCESS;
                }
            }
        } else {
            if (!itemstack.isEmpty()) {
                if ((this.aiTameType() == PrehistoricEntityTypeAI.Taming.GEM && itemstack.is(ModItems.SCARAB_GEM.get())) ||
                    (this.aiTameType() == PrehistoricEntityTypeAI.Taming.BLUEGEM && itemstack.is(ModItems.AQUATIC_SCARAB_GEM.get()))) {
                    if (!this.isTame() && !this.isOwnedBy(player) && this.isActuallyWeak()) {
                        this.triggerTamingAcheivement(player);
                        this.heal(200);
                        this.setMood(100);
                        this.increaseHunger(500);
                        this.getNavigation().stop();
                        this.setTarget(null);
                        this.setLastHurtByMob(null);
                        tame(player);
                        this.level.broadcastEntityEvent(this, (byte) 35);
                        itemstack.shrink(1);
                        return InteractionResult.SUCCESS;
                    }
                }

                if (itemstack.is(ModItems.CHICKEN_ESSENCE.get()) && this.aiTameType() != PrehistoricEntityTypeAI.Taming.GEM && this.aiTameType() != PrehistoricEntityTypeAI.Taming.BLUEGEM && !player.level.isClientSide) {
                    if (this.getAgeInDays() < this.adultAgeDays && this.getHunger() > 0) {
                        if (this.getHunger() > 0) {
                            itemstack.shrink(1);
                            if (!player.isCreative()) {
                                player.addItem(new ItemStack(Items.GLASS_BOTTLE, 1));
                            }
                            //Revival.NETWORK_WRAPPER.sendToAll(new MessageFoodParticles(getEntityId(), Item.getIdFromItem(ModItems.CHICKEN_ESSENCE)));
                            this.grow(1);
                            this.setHunger(1 + (new Random()).nextInt(this.getHunger()));
                            return InteractionResult.SUCCESS;
                        }
                    }
                    if (!this.level.isClientSide) {
                        player.displayClientMessage(new TranslatableComponent("prehistoric.essencefail"), true);
                    }
                    return InteractionResult.SUCCESS;
                }
                /*if (itemstack.is(ModItems.STUNTED_ESSENCE) && !isAgingDisabled()) { // TODO Add STUNTED_ESSENCE
                    this.setHunger(this.getHunger() + 20);
                    this.heal(this.getMaxHealth());
                    this.playSound(SoundEvents.ZOMBIE_VILLAGER_CURE, this.getSoundVolume(), this.getVoicePitch());
                    spawnItemCrackParticles(itemstack.getItem());
                    spawnItemCrackParticles(itemstack.getItem());
                    spawnItemCrackParticles(Items.POISONOUS_POTATO);
                    spawnItemCrackParticles(Items.POISONOUS_POTATO);
                    spawnItemCrackParticles(Items.EGG);
                    this.setAgingDisabled(true);
                    if (!player.isCreative()) {
                        itemstack.shrink(1);
                    }
                    return InteractionResult.SUCCESS;
                }*/

                if (FoodMappings.getFoodAmount(itemstack.getItem(), this.type.diet) > 0) {
                    if (!level.isClientSide) {
                        if (this.getHunger() < this.getMaxHunger() || this.getHealth() < this.getMaxHealth() && Fossil.CONFIG_OPTIONS.healingDinos || !this.isTame() && this.aiTameType() == PrehistoricEntityTypeAI.Taming.FEEDING) {
                            this.setHunger(this.getHunger() + FoodMappings.getFoodAmount(itemstack.getItem(), this.type.diet));
                            this.eatItem(itemstack);
                            if (Fossil.CONFIG_OPTIONS.healingDinos) {
                                this.heal(3);
                            }
                            if (this.getHunger() >= this.getMaxHunger()) {
                                if (this.isTame()) {
                                }
                            }
                            usePlayerItem(player, hand, itemstack);
                            if (this.aiTameType() == PrehistoricEntityTypeAI.Taming.FEEDING) {
                                if (!this.isTame() && this.isTameable() && (new Random()).nextInt(10) == 1) {
                                    this.tame(player);
                                    this.level.broadcastEntityEvent(this, (byte) 35);
                                }
                            }

                            return InteractionResult.SUCCESS;
                        } else {
                            return InteractionResult.PASS;
                        }
                    }

                    return InteractionResult.PASS;
                } else {
                    if (itemstack.is(Items.LEAD) && this.isTame()) {
                        if (this.isOwnedBy(player)) {
                            this.setLeashedTo(player, true);
                            itemstack.shrink(1);
                            return InteractionResult.SUCCESS;
                        }
                    }

                    if (itemstack.is(ModItems.WHIP.get()) && aiTameType() != PrehistoricEntityTypeAI.Taming.NONE && isAdult()) {
                        if (isTame() && isOwnedBy(player) && canBeRidden()) {
                            if (getRidingPlayer() == null) {
                                if (!this.level.isClientSide) {
                                    setRidingPlayer(player);
                                }
                                setOrder(OrderType.WANDER);
                                setOrderedToSit(false);
                                setSleeping(false);
                            } else if (getRidingPlayer() == player) {
                                setSprinting(true);
                                setMood(getMood() - 5);
                            }
                        } else if (!isTame() && aiTameType() != PrehistoricEntityTypeAI.Taming.BLUEGEM && aiTameType() != PrehistoricEntityTypeAI.Taming.GEM) {
                            setMood(getMood() - 5);
                            if (getRandom().nextInt(5) == 0) {
                                player.displayClientMessage(new TranslatableComponent("prehistoric.autotame", getName().getString()), true);
                                setMood(getMood() - 25);
                                tame(player);
                            }
                        }
                        this.setOrderedToSit(false);
                    }
                    if (this.getOrderItem() != null && itemstack.is(this.getOrderItem()) && this.isTame() && this.isOwnedBy(player) && !player.isPassenger()) {
                        if (!this.level.isClientSide) {
                            this.jumping = false;
                            this.getNavigation().stop();
                            this.currentOrder = OrderType.values()[(this.currentOrder.ordinal() + 1) % 3];
                            this.sendOrderMessage(this.currentOrder);
                        }
                        return InteractionResult.SUCCESS;
                    }
                }
            }
        }
        return super.mobInteract(player, hand);
    }

    protected boolean isTameable() {
        return true;
    }

    public abstract Item getOrderItem();

    private void triggerTamingAcheivement(Player player) {
        // player.triggerAchievement(FossilAchievementHandler.theKing);

    }

    public void grow(int ageInDays) {
        if (this.isAgingDisabled()) {
            return;
        }
        this.setAgeInDays(this.getAgeInDays() + ageInDays);
        for (int i = 0; i < this.getScale() * 4; i++) {
            double motionX = getRandom().nextGaussian() * 0.07D;
            double motionY = getRandom().nextGaussian() * 0.07D;
            double motionZ = getRandom().nextGaussian() * 0.07D;
            double minX = this.getBoundingBox().minX;
            double minY = this.getBoundingBox().minY;
            double minZ = this.getBoundingBox().minZ;


            float x = (float) (getRandom().nextFloat() * (this.getBoundingBox().maxX - minX) + minX);
            float y = (float) (getRandom().nextFloat() * (this.getBoundingBox().maxY - minY) + minY);
            float z = (float) (getRandom().nextFloat() * (this.getBoundingBox().maxZ - minZ) + minZ);
            this.level.addParticle(ParticleTypes.HAPPY_VILLAGER, x, y, z, motionX, motionY, motionZ);

        }
        this.updateAbilities();
    }

    public boolean isWeak() {
        return (this.getHealth() < 8) && (this.getAgeInDays() >= this.adultAgeDays) && !this.isTame();
    }

    /*protected void setPedia() {
        Revival.PROXY.setPediaObject(this);
        // Currently there's only empty implementation for `ServerProxy#setPediaObject`
    }*/

    private void sendOrderMessage(OrderType var1) {
        String s = "dino.order." + var1.name().toLowerCase();
        if (this.getOwner() instanceof Player player) {
            player.displayClientMessage(new TranslatableComponent(s, this.getName()), true);
        }
    }

    public void nudgeEntity(Player player) {
        this.teleportTo(this.getX() + (player.getX() - this.getX()) * 0.01F, this.getY(), this.getZ() + (player.getZ() - this.getZ()) * 0.01F);
    }

    public ArrayList<Class<? extends Entity>> preyList() {
        return new ArrayList<>();
    }

    public ArrayList<Class<? extends Entity>> preyBlacklist() {
        return new ArrayList<>();
    }

    public void playerRoar(Player player) {
    }

    public void playerAttack(Player player) {
    }

    public void playerJump(Player player) {
    }

    public void playerFlyUp(Player player) {
    }

    public void playerFlyDown(Player player) {
    }

    public void refreshTexturePath() {
        String name = getType().arch$registryName().getPath();
        StringBuilder builder = new StringBuilder();
        builder.append("textures/entity/");
        builder.append(name);
        builder.append("/");
        builder.append(name);
        if (this.isSkeleton()) {
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

    public boolean isActuallyWeak() {
        return (this.aiTameType() == PrehistoricEntityTypeAI.Taming.BLUEGEM || this.aiTameType() == PrehistoricEntityTypeAI.Taming.GEM) && this.isWeak();
    }

    public float getFemaleScale() {
        return 1.0F;
    }

    public String getOverlayTexture() {
        return "fossil:textures/blank.png";
    }

    @Override
    public void playAmbientSound() {
        if (!this.isSleeping() && !this.isSkeleton()) {
            super.playAmbientSound();
            /*if (this.getAnimation() != null) {
                if (this.getAnimation() == NO_ANIMATION && !level.isClientSide) {
                    this.setAnimation(SPEAK_ANIMATION);
                }
            }*/
        }
    }

    public void knockbackEntity(Entity knockBackMob, float knockbackStrength, float knockbackStrengthUp) {
        if (!(knockBackMob instanceof ToyBase) && knockBackMob instanceof LivingEntity) {
            double resistance = ((LivingEntity) knockBackMob).getAttribute(Attributes.KNOCKBACK_RESISTANCE).getValue();
            double reversed = 1 - resistance;
            knockBackMob.setDeltaMovement(knockBackMob.getDeltaMovement().add(0, 0.4000000059604645D * reversed + 0.1D, 0));
            if (resistance < 1) {
                knockBackMob(knockBackMob, 0.25D, 0.2D, 0.25D);
            }
        }
    }

    public boolean canDinoHunt(LivingEntity target, boolean hunger) {
        if (target instanceof ToyBase) {
            return true;
        }
        boolean b = true;
        if (target != null) {
            b = FoodMappings.getMobFoodPoints(target, type.diet) > 0;
        }
        if (this.type.diet != Diet.HERBIVORE && this.type.diet != Diet.NONE && b && canAttack(target)) {
            //target instanceof Prehistoric dino ? getBbWidth() * getTargetScale() >= dino.getActualWidth() : getBbWidth() * getTargetScale() >= target.getBbWidth()
            if (this.getBbWidth() * getTargetScale() >= target.getBbWidth()) {
                if (hunger) {
                    return isHungry();
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    public float getTargetScale() {
        return 1.0F;
    }

    public boolean isMad() {
        return this.getMoodFace() == PrehistoricMoodType.SAD;
    }

    @Override
    public boolean canMate(Animal otherAnimal) {
        return otherAnimal != this &&
            otherAnimal.getClass() == this.getClass() &&
            this.isAdult() &&
            this.getMood() > 50 &&
            this.ticksTillMate == 0 &&
            ((Prehistoric) otherAnimal).gender != this.gender &&
            (this.matingGoal.getPartner() == null || this.matingGoal.getPartner() == otherAnimal);
    }

    @Override
    protected PathNavigation createNavigation(Level levelIn) {
        return this.aiClimbType() == PrehistoricEntityTypeAI.Climbing.ARTHROPOD ? new WallClimberNavigation(this, levelIn) : new PrehistoricPathNavigation(this, levelIn);
    }


    public abstract boolean canBeRidden();

    @Override
    public boolean canBeControlledByRider() {
        return canBeRidden() && this.getControllingPassenger() instanceof LivingEntity rider && this.isOwnedBy(rider);
    }

    public void procreate(Prehistoric mob) {
        for (int i = 0; i < 7; ++i) {
            double dd = this.random.nextGaussian() * 0.02D;
            double dd1 = this.random.nextGaussian() * 0.02D;
            double dd2 = this.random.nextGaussian() * 0.02D;
            //      Revival.PROXY.spawnPacketHeartParticles(this.level, (float) (this.getX() + (this.random.nextFloat() * this.getBbWidth() * 2.0F) - this.getBbWidth()), (float) (this.getY() + 0.5D + (this.random.nextFloat() * this.height)), (float) (this.getZ() + (this.random.nextFloat() * this.getBbWidth() * 2.0F) - this.getBbWidth()), dd, dd1, dd2);
            //      Revival.PROXY.spawnPacketHeartParticles(mob.level, (float) (mob.posX + (mob.random.nextFloat() * mob.width * 2.0F) - mob.width), (float) (mob.posY + 0.5D + (mob.random.nextFloat() * mob.height)), (float) (mob.posZ + (mob.random.nextFloat() * mob.width * 2.0F) - mob.width), dd, dd1, dd2);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        if (this.random.nextInt(100) == 0 || calendar.get(2) + 1 == 4 && calendar.get(5) == 1) {
            //   this.playSound(FASoundRegistry.MUSIC_MATING, 1, 1);
        }
        if (!level.isClientSide) {
            Entity hatchling = this.createChild();
            this.setTarget(null);
            mob.setTarget(null);
            hatchling.moveTo(mob.getX(), mob.getY() + 1, mob.getZ(), mob.yBodyRot, 0);
            if (hatchling instanceof DinosaurEgg) {
                //      Revival.NETWORK_WRAPPER.sendToAll(new MessageUpdateEgg(hatchling.getEntityId(), this.dinoType.ordinal()));
            } else {
                if (hatchling instanceof Prehistoric) {
                    ((Prehistoric) hatchling).grow(1);
                    ((Prehistoric) hatchling).setHealth((float) this.baseHealth);
                }
            }
            this.level.addFreshEntity(hatchling);
        }
    }

    public boolean isThereNearbyTypes() {
        double d0 = 40;
        List<? extends Prehistoric> list = level.getEntitiesOfClass(this.getClass(), this.getBoundingBox().inflate(d0, 4.0D, d0), Prehistoric::isAdult);
        list.remove(this);
        return list.size() > this.nearByMobsAllowed;

    }

    public void doFoodEffect(Item item) {
        this.level.playSound(null, this.blockPosition(), SoundEvents.GENERIC_EAT, SoundSource.NEUTRAL, this.getSoundVolume(), this.getVoicePitch());
        if (item != null) {
            spawnItemParticle(item, item instanceof BlockItem);
        }
    }

    public void doFoodEffect() {
        this.level.playSound(null, this.blockPosition(), SoundEvents.GENERIC_EAT, SoundSource.NEUTRAL, this.getSoundVolume(), this.getVoicePitch());
        switch (this.type.diet) {
            case HERBIVORE:
                spawnItemParticle(Items.WHEAT_SEEDS, false);
                break;
            case OMNIVORE:
                spawnItemParticle(Items.BREAD, false);
                break;
            case PISCIVORE:
                spawnItemParticle(Items.COD, false);
                break;
            default:
                spawnItemParticle(Items.BEEF, false);
                break;
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

    // This method uses Forge-specific API and needs porting. However, it's currently unused so I'm commenting it for now.
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

    public void eatItem(ItemStack stack) {
        if (stack != null) {
            if (FoodMappings.getFoodAmount(stack.getItem(), type.diet) != 0) {
                this.setMood(this.getMood() + 5);
                doFoodEffect(stack.getItem());
                this.setHunger(this.getHunger() + FoodMappings.getFoodAmount(stack.getItem(), type.diet));
                stack.shrink(1);
                setCurrentAnimation(nextEatingAnimation());
            }
        }
    }

    public String getTempermentString() {
        String s = null;
        if (this.aiResponseType() == PrehistoricEntityTypeAI.Response.AGRESSIVE || this.aiResponseType() == PrehistoricEntityTypeAI.Response.WATERAGRESSIVE) {
            s = "agressive";
        } else if (this.aiResponseType() == PrehistoricEntityTypeAI.Response.SCARED) {
            s = "scared";
        } else if (this.aiResponseType() == PrehistoricEntityTypeAI.Response.NONE || this.aiResponseType() == PrehistoricEntityTypeAI.Response.WATERCALM) {
            s = "none";
        } else if (this.aiResponseType() == PrehistoricEntityTypeAI.Response.TERITORIAL) {
            s = "territorial";
        }
        return "pedia.temperament." + s;
    }

    public boolean canRunFrom(Entity entity) {
        if (this.getBbWidth() <= entity.getBbWidth()) {
            if (entity instanceof Prehistoric) {
                Prehistoric mob = (Prehistoric) entity;
                return mob.type.diet != Diet.HERBIVORE;
            } else {
                if (entity instanceof Player) {
                    Player player = (Player) entity;
                    return !this.isOwnedBy(player);
                }
                return true;
            }
        }
        return false;
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

    protected double getJumpStrength() {
        return 1;//TODO: Jump Strength for all rideable dinos
    }

    protected boolean isJumping() {
        return isJumping;
    }

    protected void setIsJumping(boolean jumping) {
        isJumping = jumping;
    }

    @Override
    public AgeableMob getBreedOffspring(ServerLevel serverLevel, AgeableMob ageableMob) {
        if (ageableMob instanceof Prehistoric) {
            Entity baby = this.createChild();
            Prehistoric prehistoric = (Prehistoric) baby;
            prehistoric.setAgeInDays(0);
            prehistoric.grow(0);
            prehistoric.updateAbilities();
            prehistoric.setNoAi(false);
            return ((Prehistoric) baby);
        }
        return null;
    }

    public boolean isAquatic() {
        return this.getMobType() == MobType.WATER;
    }
    public boolean canReachPrey() {
        return this.getTarget() != null && getAttackBounds().intersects(this.getTarget().getBoundingBox()) && !isPreyBlocked(this.getTarget());
    }

    public boolean isPreyBlocked(Entity prey) {
        return this.hasLineOfSight(prey);
    }

    public boolean canSeeFood(BlockPos position) {
        Vec3 target = new Vec3(position.getX() + 0.5, position.getY() + 0.5, position.getZ() + 0.5);
        BlockHitResult rayTrace = level.clip(new ClipContext(position(), target, ClipContext.Block.OUTLINE, ClipContext.Fluid.NONE, this));
        return rayTrace.getType() != HitResult.Type.MISS;
    }
    public float getDeathRotation() {
        return 90.0F;
    }

    protected float getSoundVolume() {
        return this.isBaby() ? super.getSoundVolume() * 0.75F : 1.0F;
    }

    protected void doAttackKnockback(float strength) {
        if (this.getTarget() != null) {
            if (this.getTarget().hasPassenger(this)) {
                this.getTarget().stopRiding();
            }
            knockbackEntity(this.getTarget(), strength, 0.1F);
            this.getTarget().hurtMarked = false;
        }
    }

    @Override
    public boolean doHurtTarget(Entity target) {
        // TODO Implement this properly, all dinosaurs may run away after attacking player if pvp is disabled
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
        return this.isVehicle();
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
        playerJumpPendingScale = jumpPower >= 90 ? 1.0f : 0.4f + 0.4f * (float)jumpPower / 90.0f;
    }

    public float getProximityToNextPathSkip() {
        return this.getBbWidth() > 0.75F ? this.getBbWidth() / 2.0F : 0.75F - this.getBbWidth() / 2.0F;
    }

    public boolean useSpecialAttack() {
        return false;
    }

    public boolean isFleeing() {
        return this.entityData.get(FLEEING);
    }

    public void setFleeing(boolean fleeing) {
        this.entityData.set(FLEEING, fleeing);
    }

    protected int getFleeingCooldown() {
        if (this.getLastHurtByMob() != null) {
            int i = (int) (Math.max(this.getLastHurtByMob().getBbWidth() / 2F, 1) * 95);
            int j = (int) (Math.min(this.getBbWidth() / 2F, 0.5D) * 50);
            return i - j;
        }
        return 100;
    }

    public void setGender(@NotNull Gender gender) {
        this.gender = gender;
        refreshTexturePath();
    }

    @Nullable
    public Gender getGender() {
        return this.gender;
    }

    protected PlayState onFrame(AnimationEvent<? extends Prehistoric> event, ServerAnimationInfo currentAnimation) {
        var controller = event.getController();
        controller.setAnimation(new AnimationBuilder().addAnimation(currentAnimation.animationId));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 4, event -> this.onFrame(event, getCurrentAnimation())));
    }

    public abstract ServerAnimationInfo nextEatingAnimation();

    @NotNull
    public abstract ServerAnimationInfo nextIdleAnimation();

    @NotNull
    public abstract ServerAnimationInfo nextMovingAnimation();

    @NotNull
    public abstract Prehistoric.ServerAnimationInfo nextChasingAnimation();

    @NotNull
    public abstract Prehistoric.ServerAttackAnimationInfo nextAttackAnimation();

    /**
     * Some animations attack multiple times in single animation, so it holds var int.
     * When current gameTime equals to {@link Level#getGameTime()} + {@code any of supplied delays},
     * dinosaur will attack.
     */
    public static final class ServerAttackAnimationInfo extends ServerAnimationInfo {
        public final int[] attackDelays;

        /**
         * @param attackDelays array of delays to attack target.
         */
        public ServerAttackAnimationInfo(String animationId, int priority, int lengthInTicks, int... attackDelays) {
            super(animationId, priority, lengthInTicks, false);
            this.attackDelays = attackDelays;
            if (attackDelays.length == 0) throw new IllegalArgumentException("Attack delays must not be empty");
        }

        public ServerAttackAnimationInfo(Animation animation, int priority, int... attackDelays) {
            this(animation.animationName, priority, (int) Math.round(animation.animationLength), attackDelays);
        }
    }

    public static class ServerAnimationInfo {
        public final @NotNull String animationId;
        public final int length;
        public final int priority;
        public final boolean isLoop;

        public ServerAnimationInfo(@NotNull String animationId, int priority, int lengthInTicks, boolean isLoop) {
            this.animationId = animationId;
            this.priority = priority;
            this.length = lengthInTicks;
            this.isLoop = isLoop;
        }

        public ServerAnimationInfo(Animation animation, int priority) {
            this(animation.animationName, priority, (int) Math.round(animation.animationLength), animation.loop == ILoopType.EDefaultLoopTypes.LOOP);
        }
    }

    public static final int IDLE_PRIORITY = 0;
    public static final int MOVING_PRIORITY = 1;
    public static final int DEFAULT_PRIORITY = 2;
    public static final int ATTACKING_PRIORITY = 3;
}
