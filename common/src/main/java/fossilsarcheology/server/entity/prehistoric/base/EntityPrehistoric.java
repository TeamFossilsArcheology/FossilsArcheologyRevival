package fossilsarcheology.server.entity.prehistoric.base;

import com.github.alexthe666.citadel.animation.Animation;
import com.github.alexthe666.citadel.animation.AnimationHandler;
import com.github.alexthe666.citadel.animation.IAnimatedEntity;
import com.google.common.base.Predicate;
import fossilsarcheology.Revival;
import fossilsarcheology.server.block.IDinoUnbreakable;
import fossilsarcheology.server.entity.util.EntityToyBase;
import fossilsarcheology.server.item.FAItemRegistry;
import fossilsarcheology.server.util.*;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.BushBlock;
import net.minecraft.block.material.Material;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.ai.attributes.ModifiableAttributeInstance;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.entity.passive.TameableEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.particles.ItemParticleData;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.pathfinding.ClimberPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fluids.IFluidBlock;

import javax.annotation.Nullable;
import java.util.*;

public abstract class EntityPrehistoric extends TameableEntity implements IPrehistoricAI, IAnimatedEntity, IJumpingMount {

    private static final DataParameter<Integer> AGETICK = EntityDataManager.createKey(EntityPrehistoric.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> HUNGER = EntityDataManager.createKey(EntityPrehistoric.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> MODELIZED = EntityDataManager.createKey(EntityPrehistoric.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> ANGRY = EntityDataManager.createKey(EntityPrehistoric.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> FLEEING = EntityDataManager.createKey(EntityPrehistoric.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> SUBSPECIES = EntityDataManager.createKey(EntityPrehistoric.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> GENDER = EntityDataManager.createKey(EntityPrehistoric.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> SLEEPING = EntityDataManager.createKey(EntityPrehistoric.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> SITTING = EntityDataManager.createKey(EntityPrehistoric.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> MOOD = EntityDataManager.createKey(EntityPrehistoric.class, DataSerializers.VARINT);
    private static final DataParameter<String> OWNERDISPLAYNAME = EntityDataManager.createKey(EntityPrehistoric.class, DataSerializers.STRING);
    private static final DataParameter<Byte> CLIMBING = EntityDataManager.createKey(EntityPrehistoric.class, DataSerializers.BYTE);
    private static final DataParameter<Boolean> AGINGDISABLED = EntityDataManager.createKey(EntityPrehistoric.class, DataSerializers.BOOLEAN);
    private static final Predicate PREHISTORIC_PREDICATE = new Predicate<Entity>() {
        public boolean apply(@Nullable Entity entity) {
            return entity != null && entity instanceof EntityPrehistoric;
        }
    };
    public final Animation SPEAK_ANIMATION;
    public final PrehistoricEntityType dinoType;
    public final double baseDamage;
    public final double maxDamage;
    public final double baseHealth;
    public final double maxHealth;
    public final double baseSpeed;
    public final double maxSpeed;
    public final double baseArmor;
    public final double maxArmor;
    public Animation ATTACK_ANIMATION;
    public float minSize;
    public float maxSize;
    public int teenAge;
    public ItemStack ItemInMouth = ItemStack.EMPTY;
    public OrderType currentOrder;
    public boolean hasFeatherToggle = false;
    public boolean featherToggle;
    public boolean hasTeenTexture = false;
    public boolean hasBabyTexture;
    public float weakProgress;
    public float sitProgress;
    public int ticksSitted;
    public float sleepProgress;
    public float climbProgress;
    public int ticksSlept;
    public float pediaScale;
    public int pediaY = 0;
    public int ticksTillPlay;
    public int ticksTillMate;
    public boolean isDaytime;
    public float ridingXZ;
    public float ridingY = 1;
    public float actualWidth;
    public boolean shouldWander = true;
    public boolean isRunningAway = false;
    protected boolean isSitting;
    protected boolean isSleeping;
    protected boolean developsResistance;
    protected boolean breaksBlocks;
    protected int nearByMobsAllowed;
    protected float jumpPower;
    protected boolean horseJumping;
    private Animation currentAnimation;
    private boolean droppedBiofossil = false;
    private int animTick;
    private int fleeTicks = 0;
    private int moodCheckCooldown = 0;
    private int cathermalSleepCooldown = 0;
    private int ticksClimbing = 0;

    public EntityPrehistoric(EntityType t, World world, PrehistoricEntityType dinoType, double baseDamage, double maxDamage, double baseHealth, double maxHealth, double baseSpeed, double maxSpeed, double baseArmor, double maxArmor) {
        super(t, world);
        this.moveController = new DinoMoveHelper(this);
        this.setHunger(this.getMaxHunger() / 2);
        SPEAK_ANIMATION = Animation.create(this.getSpeakLength());
        ATTACK_ANIMATION = Animation.create(this.getAttackLength());
        this.hasBabyTexture = true;
        this.dinoType = dinoType;
        this.pediaScale = 1.0F;
        this.nearByMobsAllowed = 15;
        this.currentOrder = OrderType.WANDER;
        if (ticksTillMate == 0) {
            ticksTillMate = this.rand.nextInt(6000) + 6000;
        }
        this.baseDamage = baseDamage;
        this.maxDamage = maxDamage;
        this.baseHealth = baseHealth;
        this.maxHealth = maxHealth;
        this.baseSpeed = baseSpeed;
        this.maxSpeed = maxSpeed;
        this.baseArmor = baseArmor;
        this.maxArmor = maxArmor;
        this.updateAbilities();
        if (!(this instanceof EntityPrehistoricSwimming)) {
            this.setPathPriority(PathNodeType.WATER, -1.0F);
            this.getNavigator().getNodeProcessor().setCanSwim(true);
            this.getNavigator().getNodeProcessor().setCanSwim(true);
        }
    }

    public static AttributeModifierMap.MutableAttribute bakeAttributes() {
        return MonsterEntity.func_234295_eP_()
                .createMutableAttribute(Attributes.MAX_HEALTH, 20D)
                .createMutableAttribute(Attributes.ATTACK_DAMAGE, 2D);
    }


    @Override
    public EntitySize getSize(Pose poseIn) {
        return this.getType().getSize().scale(this.getRenderScale());
    }

    @Override
    public float getRenderScale() {
        return this.getAgeScale();
    }


    public static boolean isCannibalistic() {
        return false;
    }

    public static boolean isEntitySmallerThan(Entity entity, float size) {
        if (entity instanceof EntityPrehistoric) {
            return ((EntityPrehistoric) entity).getActualWidth() <= size;
        } else {
            return entity.getWidth() <= size;
        }
    }

    public static void knockBackMob(Entity entity, double xMotion, double yMotion, double zMotion) {
        entity.isAirBorne = true;
        float f1 = MathHelper.sqrt(xMotion * xMotion + zMotion * zMotion);
        entity.setMotion(entity.getMotion().mul(0.5F, 0.5F, 0.5F));
        entity.setMotion(entity.getMotion().add(-xMotion / (double) f1, yMotion, -zMotion / (double) f1));
    }

    public static boolean canBreak(Block block) {
        return !(block instanceof IDinoUnbreakable) &&
                block != Blocks.BARRIER &&
                block != Blocks.OBSIDIAN &&
                block != Blocks.END_STONE &&
                block != Blocks.BEDROCK &&
                block != Blocks.END_PORTAL &&
                block != Blocks.END_PORTAL_FRAME &&
                block != Blocks.COMMAND_BLOCK &&
                block != Blocks.REPEATING_COMMAND_BLOCK &&
                block != Blocks.CHAIN_COMMAND_BLOCK &&
                block != Blocks.IRON_BARS &&
                block != Blocks.END_GATEWAY
                || block == Blocks.LILY_PAD;
    }

    public int getSpeakLength() {
        return 20;
    }

    public int getAttackLength() {
        return 10;
    }

    @Override
    protected void registerData() {
        super.registerData();
        this.dataManager.register(AGETICK, 0);
        this.dataManager.register(HUNGER, 0);
        this.dataManager.register(MODELIZED, false);
        this.dataManager.register(ANGRY, false);
        this.dataManager.register(FLEEING, false);
        this.dataManager.register(SUBSPECIES, 0);
        this.dataManager.register(GENDER, 0);
        this.dataManager.register(SLEEPING, false);
        this.dataManager.register(SITTING, false);
        this.dataManager.register(CLIMBING, (byte) 0);
        this.dataManager.register(MOOD, 0);
        this.dataManager.register(OWNERDISPLAYNAME, "");
        this.dataManager.register(AGINGDISABLED, false);
    }

    @Override
    public void writeAdditional(CompoundNBT compound) {
        super.writeAdditional(compound);
        compound.putInt("AgeTick", this.getAgeInTicks());
        compound.putInt("Hunger", this.getHunger());
        compound.putBoolean("isModelized", this.isSkeleton());
        compound.putBoolean("Angry", this.isAngry());
        compound.putBoolean("DinoTamed", this.isTamed());
        compound.putBoolean("Fleeing", this.isFleeingFlag());
        compound.putInt("SubSpecies", this.getSubSpecies());
        compound.putInt("Gender", this.getGender());
        compound.putBoolean("Sleeping", this.isSleeping);
        compound.putInt("Mood", this.getMood());
        compound.putBoolean("Sitting", this.isSitting);
        compound.putInt("TicksSincePlay", this.ticksTillPlay);
        compound.putInt("TicksSlept", this.ticksSlept);
        compound.putInt("TicksSinceMate", this.ticksTillMate);
        compound.putInt("TicksClimbing", this.ticksClimbing);
        compound.putByte("currentOrder", (byte) this.currentOrder.ordinal());
        compound.putString("OwnerDisplayName", this.getOwnerDisplayName());
        compound.putFloat("YawRotation", this.rotationYaw);
        compound.putFloat("HeadRotation", this.rotationYawHead);
        compound.putBoolean("AgingDisabled", this.isAgingDisabled());
        compound.putInt("CathermalTimer", this.cathermalSleepCooldown);
    }


    @Override
    public void readAdditional(CompoundNBT compound) {
        super.readAdditional(compound);
        this.setAgeinTicks(compound.getInt("AgeTick"));
        this.setHunger(compound.getInt("Hunger"));
        this.setFleeingFlag(compound.getBoolean("Fleeing"));
        this.setSkeleton(compound.getBoolean("isModelized"));
        this.setAngry(compound.getBoolean("Angry"));
        this.setTamed(compound.getBoolean("DinoTamed"));
        this.setSubSpecies(compound.getInt("SubSpecies"));
        this.setGender(compound.getInt("Gender"));
        this.setSleeping(compound.getBoolean("Sleeping"));
        this.setSitting(compound.getBoolean("Sitting"));
        this.setAgingDisabled(compound.getBoolean("AgingDisabled"));
        this.setMood(compound.getInt("Mood"));
        if (compound.contains("currentOrder")) {
            this.setOrder(OrderType.values()[compound.getByte("currentOrder")]);
        }
        this.ticksTillPlay = compound.getInt("TicksSincePlay");
        this.ticksClimbing = compound.getInt("TicksClimbing");
        this.ticksTillMate = compound.getInt("TicksSinceMate");
        this.ticksSlept = compound.getInt("TicksSlept");
        this.rotationYaw = compound.getInt("YawRotation");
        this.rotationYawHead = compound.getInt("HeadRotation");
        String s = "";
        if (compound.contains("Owner", 8)) {
            s = compound.getString("Owner");
            this.setOwnerDisplayName(s);
        } else {
            this.setOwnerDisplayName(compound.getString("OwnerDisplayName"));
        }
        this.cathermalSleepCooldown = compound.getInt("CathermalTimer");
    }

    public String getOwnerDisplayName() {
        return this.dataManager.get(OWNERDISPLAYNAME);
    }

    public void setOwnerDisplayName(String displayName) {
        this.dataManager.set(OWNERDISPLAYNAME, displayName);
    }

    public AxisAlignedBB getAttackBounds() {
        float size = (float) (this.getBoundingBox().getAverageEdgeLength() * 0.25F);
        return this.getBoundingBox().grow(2.0F + size, 2.0F + size, 2.0F + size);
    }

    @Override
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        spawnDataIn = super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
        Random random = new Random();
        this.setAgeInDays(this.getAdultAge());
        this.setSpawnValues();
        this.setGender(random.nextInt(2));
        this.updateAbilities();
        ticksTillPlay = 0;
        ticksTillMate = 24000;
        this.onGround = true;
        this.heal(this.getMaxHealth());
        this.currentOrder = OrderType.WANDER;
        this.grow(0);
        this.setNoAI(false);
        return spawnDataIn;
    }

    @Override
    public boolean isAIDisabled() {
        return this.isSkeleton() || super.isAIDisabled();
    }

    public void doPlayBonus(int playBonus) {
        if (ticksTillPlay == 0) {
            this.setMood(this.getMood() + playBonus);
            if (!this.world.isRemote) {
                //TODO
                // Revival.sendMSGToAll(new MessageHappyParticles(this.getEntityId()));
            }
            ticksTillPlay = this.rand.nextInt(600) + 600;
        }
    }

    public abstract void setSpawnValues();

    public OrderType getOrderType() {
        return this.currentOrder;
    }

    @Override
    public boolean isMovementBlocked() {
        return this.getHealth() <= 0.0F || isSitting() || this.isSkeleton() || this.isActuallyWeak() || this.isBeingRidden();
    }

    public boolean isMovementBlockedSoft() {
        return isMovementBlocked() || this.isSleeping();
    }

    public boolean isSleeping() {
        if (world.isRemote) {
            boolean isSleeping = this.dataManager.get(SLEEPING);
            this.isSleeping = isSleeping;
            return isSleeping;
        }

        return isSleeping;
    }

    public void setSleeping(boolean sleeping) {
        this.dataManager.set(SLEEPING, sleeping);
        if (!world.isRemote) {
            this.isSleeping = sleeping;
        }
        if (!sleeping) {
            cathermalSleepCooldown = 10000 + rand.nextInt(6000);
        }
    }

    public BlockPos getBlockToEat(int range) {
        BlockPos pos;

        for (int r = 1; r <= range; r++) {
            for (int ds = -r; ds <= r; ds++) {
                for (int dy = 4; dy > -5; dy--) {
                    int x = MathHelper.floor(this.getPosX() + ds);
                    int y = MathHelper.floor(this.getPosY() + dy);
                    int z = MathHelper.floor(this.getPosZ() - r);
                    if (this.getPosY() + dy >= 0 && this.getPosY() + dy <= this.world.getHeight() && FoodMappings.getBlockFoodAmount(this.world.getBlockState(new BlockPos(x, y, z)).getBlock(), dinoType.diet) != 0) {
                        pos = new BlockPos(x, y, z);
                        return pos;
                    }

                    if (this.getPosY() + dy >= 0 && this.getPosY() + dy <= this.world.getHeight() && FoodMappings.getBlockFoodAmount(this.world.getBlockState(new BlockPos(x, y, z)).getBlock(), dinoType.diet) != 0) {
                        pos = new BlockPos(x, y, z);
                        return pos;
                    }
                }
            }

            for (int ds = -r + 1; ds <= r - 1; ds++) {
                for (int dy = 4; dy > -5; dy--) {
                    int x = MathHelper.floor(this.getPosX() + ds);
                    int y = MathHelper.floor(this.getPosY() + dy);
                    int z = MathHelper.floor(this.getPosZ() - r);

                    if (this.getPosY() + dy >= 0 && this.getPosY() + dy <= this.world.getHeight() && FoodMappings.getBlockFoodAmount(this.world.getBlockState(new BlockPos(x, y, z)).getBlock(), dinoType.diet) != 0) {
                        pos = new BlockPos(x, y, z);
                        return pos;
                    }

                    if (this.getPosY() + dy >= 0 && this.getPosY() + dy <= this.world.getHeight() && FoodMappings.getBlockFoodAmount(this.world.getBlockState(new BlockPos(x, y, z)).getBlock(), dinoType.diet) != 0) {
                        pos = new BlockPos(x, y, z);
                        return pos;
                    }
                }
            }
        }

        return null;
    }

    public void setOrder(OrderType var1) {
        this.currentOrder = var1;
    }

    @Override
    public boolean preventDespawn() {
        return true;
    }

    /*public TileEntityFeeder getNearestFeeder(int feederRange) {
        for (int dx = -2; dx != -(feederRange + 1); dx += (dx < 0) ? (dx * -2) : (-(2 * dx + 1))) {
            for (int dy = -5; dy < 4; dy++) {
                for (int dz = -2; dz != -(feederRange + 1); dz += (dz < 0) ? (dz * -2) : (-(2 * dz + 1))) {
                    if (this.getPosY() + dy >= 0 && this.getPosY() + dy <= this.world.getHeight()) {
                        TileEntity feeder = this.world.getTileEntity(new BlockPos(MathHelper.floor(this.getPosX() + dx), MathHelper.floor(this.getPosY() + dy), MathHelper.floor(this.getPosZ() + dz)));

                        if (feeder instanceof TileEntityFeeder && !((TileEntityFeeder) feeder).isEmpty(dinoType)) {
                            return (TileEntityFeeder) feeder;
                        }
                    }
                }
            }
        }

        return null;
    }*/
    // TODO ^s

    public float getActualWidth() {
        return this.actualWidth * this.getAgeScale();
    }

    public boolean arePlantsNearby(int range) {
        for (int i = MathHelper.floor(this.getPosX() - range); i < MathHelper.ceil(this.getPosX() + range); i++) {
            for (int j = MathHelper.floor(this.getPosY() - range / 2); j < MathHelper.ceil(this.getPosY() + range / 2); j++) {
                for (int k = MathHelper.floor(this.getPosZ() - range); k < MathHelper.ceil(this.getPosZ() + range); k++) {
                    if (j <= this.world.getHeight() + 1D && isPlantBlock(this.world.getBlockState(new BlockPos(i, j, k)))) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    @Override
    public boolean canBePushed() {
        return !this.isSkeleton() && super.canBePushed();
    }

    public boolean isPlantBlock(BlockState block) {
        return block.getMaterial() == Material.ORGANIC || block.getMaterial() == Material.PLANTS || block.getMaterial() == Material.LEAVES;
    }

    public boolean canSleep() {
        if (this.aiActivityType() == PrehistoricEntityTypeAI.Activity.DIURINAL) {
            return !this.isDaytime();
        } else if (this.aiActivityType() == PrehistoricEntityTypeAI.Activity.NOCTURNAL) {
            return this.isDaytime() && !this.world.canSeeSky(new BlockPos(MathHelper.floor(this.getPosX()), MathHelper.floor(this.getPosY() + 1), MathHelper.floor(this.getPosZ())));
        } else return this.aiActivityType() == PrehistoricEntityTypeAI.Activity.BOTH;
    }

    public boolean canWakeUp() {
        if (this.aiActivityType() == PrehistoricEntityTypeAI.Activity.DIURINAL) {
            return this.isDaytime();
        } else if (this.aiActivityType() == PrehistoricEntityTypeAI.Activity.NOCTURNAL) {
            return !this.isDaytime() || this.world.canSeeSky(new BlockPos(MathHelper.floor(this.getPosX()), MathHelper.floor(this.getPosY() + 1), MathHelper.floor(this.getPosZ())));
        } else {
            return this.ticksSlept > 4000;
        }
    }

    public boolean isDaytime() {
        return this.world.isDaytime();
    }

    @Override
    public void livingTick() {
        super.livingTick();
        if (this.isSkeleton()) {
            this.setMotion(Vector3d.ZERO);
        }
        if ((this.getAttackTarget() != null || this.getRevengeTarget() != null) && this.isSleeping()) {
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
            this.stepHeight = 1;
        }
        if (Revival.CONFIG_OPTIONS.dinosaurBreeding && !world.isRemote && ticksTillMate == 0 && this.getGender() == 1 && this.getMood() > 50) {
            float cramDist = 30;
            List<EntityPrehistoric> crammedList = world.getEntitiesWithinAABB(this.getClass(), new AxisAlignedBB(this.getPosX() - cramDist, this.getPosY() - cramDist / 2, this.getPosZ() - cramDist, this.getPosX() + cramDist, this.getPosY() + cramDist, this.getPosZ() + cramDist));
            if (crammedList.size() > this.getMaxPopulation()) {
                this.ticksTillMate = this.rand.nextInt(6000) + 6000;
            } else {
                this.mate();
            }
        }
        if (Revival.CONFIG_OPTIONS.healingDinos && !this.world.isRemote) {
            if (this.rand.nextInt(500) == 0 && this.deathTime == 0) {
                this.heal(1.0F);
            }
        }
        if (this.moodCheckCooldown-- <= 0) {
            this.doMoodCheck();
            this.moodCheckCooldown = 3000 + this.getRNG().nextInt(5000);
        }

        if (this.isSleeping() && (this.getAttackTarget() != null && this.getAttackTarget().isAlive() || this.getRevengeTarget() != null && this.getRevengeTarget().isAlive())) {
            this.setSleeping(false);
        }
        if (this.isSitting()) {
            ticksSitted++;
        }
        if (!world.isRemote) {
            if (this.isSleeping()) {
                ticksSlept++;
            } else {
                ticksSlept = 0;
            }
        }
        if (!world.isRemote && !this.isInWater() && !this.isBeingRidden() && !this.isSitting() && this.getRNG().nextInt(1000) == 1 && !this.isPassenger() && (this.getAnimation() == NO_ANIMATION || this.getAnimation() == SPEAK_ANIMATION) && !this.isSleeping()) {
            this.setSitting(true);
            ticksSitted = 0;
        }

        if (!world.isRemote && !this.isInWater() && (this.isSitting() && ticksSitted > 100 && this.getRNG().nextInt(100) == 1 || this.getAttackTarget() != null) && !this.isSleeping()) {
            this.setSitting(false);
            ticksSitted = 0;
        }
        if (cathermalSleepCooldown > 0) {
            cathermalSleepCooldown--;
        }
        if (!this.world.isRemote && this.wantsToSleep()) {
            if (this.aiActivityType() == PrehistoricEntityTypeAI.Activity.BOTH) {
                if (cathermalSleepCooldown == 0) {
                    if (this.getRNG().nextInt(1200) == 1 && !this.isSleeping()) {
                        this.setSitting(false);
                        this.setSleeping(true);
                    }
                }
            } else if (this.aiActivityType() != PrehistoricEntityTypeAI.Activity.NOSLEEP) {
                if (this.getRNG().nextInt(200) == 1 && !this.isSleeping()) {
                    this.setSitting(false);
                    this.setSleeping(true);
                }
            }
        }
        if (!this.world.isRemote && (!this.wantsToSleep() || !this.canSleep() || canWakeUp())) {
            this.setSitting(false);
            this.setSleeping(false);
        }

        if (this.currentOrder == OrderType.STAY && !this.isSitting() && !this.isActuallyWeak()) {
            this.setSitting(true);
            this.setSleeping(false);
        }
        if (breaksBlocks && this.getMood() < 0) {
            this.breakBlock(5);
        }
        if (this.getAttackTarget() != null && this.getAttackTarget() instanceof EntityToyBase && (isPreyBlocked(this.getAttackTarget()) || this.ticksTillPlay > 0)) {
            this.setAttackTarget(null);
        }
        if (isFleeingFlag()) {
            fleeTicks++;
            if (fleeTicks > getFleeingCooldown()) {
                this.setFleeingFlag(false);
                fleeTicks = 0;
            }
        }
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
        if (!world.isRemote && this.aiActivityType() == PrehistoricEntityTypeAI.Activity.BOTH && this.ticksSlept > 8000) {
            return false;
        }
        return !world.isRemote && this.getAttackTarget() == null && this.getRevengeTarget() == null && !this.isInWater() && !this.isBeingRidden() && !this.isActuallyWeak() && this.canSleep() && canSleepWhileHunting() && (this.getAnimation() == NO_ANIMATION || this.getAnimation() == SPEAK_ANIMATION) && this.getOrderType() != OrderType.FOLLOW;
    }

    @Override
    public void onDeath(DamageSource source) {
        super.onDeath(source);
    }

    private boolean canSleepWhileHunting() {
        return this.getAttackTarget() == null || this.getAttackTarget() instanceof EntityToyBase;
    }

    @Override
    public boolean isBreedingItem(ItemStack stack) {
        return false;
    }

    public Block getBlockUnder() {
        return this.world.getBlockState(this.getPositionUnderneath()).getBlock();
    }

    public PlayerEntity getRidingPlayer() {
        if (this.getControllingPassenger() instanceof PlayerEntity) {
            return (PlayerEntity) getControllingPassenger();
        } else {
            return null;
        }
    }

    public void setRidingPlayer(PlayerEntity player) {
        player.rotationYaw = this.rotationYaw;
        player.rotationPitch = this.rotationPitch;
        player.startRiding(this);
    }

    @Override
    public void travel(Vector3d movement) {
        if ((this.isSitting() || this.isMovementBlockedSoft()) && !this.isBeingRidden()) {
            super.travel(Vector3d.ZERO);
            return;
        }
        super.travel(movement);
    }

    @Override
    @Nullable
    public Entity getControllingPassenger() {
        for (Entity passenger : this.getPassengers()) {
            if (passenger instanceof PlayerEntity && this.getAttackTarget() != passenger) {
                PlayerEntity player = (PlayerEntity) passenger;
                if (this.isTamed() && this.isOwner(player)) {
                    return player;
                }
            }
        }
        return null;
    }

    @Override
    public void tick() {
        super.tick();
        //don't use the vanilla system
        if (this.getGrowingAge() < 0) {
            this.setGrowingAge(0);
        }
        recalculateSize();
        if (!this.isSkeleton()) {
            if (!this.isAgingDisabled()) {
                this.setAgeinTicks(this.getAgeInTicks() + 1);
                if (this.getAgeInTicks() % 24000 == 0) {
                    this.updateAbilities();
                    this.grow(0);
                }
            }
            if (this.ticksExisted % 1200 == 0 && this.getHunger() > 0 && Revival.CONFIG_OPTIONS.starvingDinos) {
                this.setHunger(this.getHunger() - 1);
            }
            if (this.getHealth() > this.getMaxHealth() / 2 && this.getHunger() == 0 && this.ticksExisted % 40 == 0) {
                this.attackEntityFrom(DamageSource.STARVE, 1);
            }
        }
        boolean sitting = isSitting();
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
        boolean climbing = this.aiClimbType() == PrehistoricEntityTypeAI.Climbing.ARTHROPOD && (this.isBesideClimbableBlock() && !this.onGround);
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
        if (!this.world.isRemote) {
            if (this.aiClimbType() == PrehistoricEntityTypeAI.Climbing.ARTHROPOD && !this.wantsToSleep() && !this.isSleeping() && ticksClimbing >= 0 && ticksClimbing < 100) {
                this.setBesideClimbableBlock(this.collidedHorizontally);
            } else {
                this.setBesideClimbableBlock(false);
                if (ticksClimbing >= 100) {
                    ticksClimbing = -900;
                }
            }
            if (this.isOnLadder() || ticksClimbing < 0) {
                ticksClimbing++;
                if (world.getBlockState(this.getPosition().up()).getMaterial().isSolid()) {
                    ticksClimbing = 200;
                }
            }
        }
        AnimationHandler.INSTANCE.updateAnimations(this);
    }

    private boolean isAboveGround() {
        BlockPos blockPos = this.getPosition();
        while (world.isAirBlock(blockPos) && blockPos.getY() > 1) {
            blockPos = blockPos.down();
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

    public abstract int getAdultAge();

    public boolean doesFlock() {
        return false;
    }

    public float getAgeScale() {
        float step = (this.maxSize - this.minSize) / ((this.getAdultAge() * 24000) + 1);
        if (this.getAgeInTicks() > this.getAdultAge() * 24000) {
            return this.minSize + ((step) * this.getAdultAge() * 24000);
        }
        return this.minSize + ((step * this.getAgeInTicks()));
    }

    @Override
    protected int getExperiencePoints(PlayerEntity par1PlayerEntity) {
        float base = 6 * this.getActualWidth() * (this.dinoType.diet == Diet.HERBIVORE ? 1.0F : 2.0F)
                * (this.aiTameType() == PrehistoricEntityTypeAI.Taming.GEM ? 1.0F : 2.0F)
                * (this.aiAttackType() == PrehistoricEntityTypeAI.Attacking.BASIC ? 1.0F : 1.25F);
        return MathHelper.floor((float) Math.min(this.getAdultAge(), this.getAgeInDays()) * base);
    }

    public void updateAbilities() {
        double healthStep = (maxHealth - baseHealth) / (this.getAdultAge());
        double attackStep = (maxDamage - baseDamage) / (this.getAdultAge());
        double speedStep = (maxSpeed - baseSpeed) / (this.getAdultAge());
        double armorStep = (maxArmor - baseArmor) / (this.getAdultAge());
        if (this.getAgeInDays() <= this.getAdultAge()) {
            this.getAttribute(Attributes.MAX_HEALTH).setBaseValue(Math.round(baseHealth + (healthStep * this.getAgeInDays())));
            this.getAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(Math.round(baseDamage + (attackStep * this.getAgeInDays())));
            this.getAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(baseSpeed + (speedStep * this.getAgeInDays()));
            this.getAttribute(Attributes.ARMOR).setBaseValue(baseArmor + (armorStep * this.getAgeInDays()));
            if (this.developsResistance) {
                if (this.isTeen()) {
                    this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(0.5D);
                } else if (this.isAdult()) {
                    this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(2.0D);
                } else {
                    this.getAttribute(Attributes.KNOCKBACK_RESISTANCE).setBaseValue(0.0D);
                }
            }
        }
        this.heal((float) healthStep);
    }

    public void breakBlock(float hardness) {
        if (Revival.CONFIG_OPTIONS.dinoBlockBreaking) {
            if (!isSkeleton() && this.isAdult() && this.isHungry() && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this)) {
                for (int a = (int) Math.round(this.getBoundingBox().minX) - 1; a <= (int) Math.round(this.getBoundingBox().maxX) + 1; a++) {
                    for (int b = (int) Math.round(this.getBoundingBox().minY) + 1; (b <= (int) Math.round(this.getBoundingBox().maxY) + 2) && (b <= 127); b++) {
                        for (int c = (int) Math.round(this.getBoundingBox().minZ) - 1; c <= (int) Math.round(this.getBoundingBox().maxZ) + 1; c++) {
                            BlockPos pos = new BlockPos(a, b, c);
                            if (!world.isAirBlock(pos)) {
                                BlockState state = world.getBlockState(pos);
                                Block block = state.getBlock();
                                if (!(block instanceof BushBlock) && state.getFluidState().isEmpty() && state.getBlockHardness(world, new BlockPos(a, b, c)) < hardness && canBreak(state.getBlock()) || block == Blocks.LILY_PAD) {
                                    this.setMotion(this.getMotion().mul(0.6F, 1F, 0.6F));
                                    if (!world.isRemote) {
                                        world.destroyBlock(new BlockPos(a, b, c), true);
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }


    public Entity createEgg(AgeableEntity entity) {
        Entity baby = null;
        if (this.dinoType.mobType == MobType.MAMMAL) {
            baby = this.dinoType.create(this.world);
        }
        if (this.dinoType.mobType == MobType.BIRD) {
            baby = new ItemEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ(), new ItemStack(this.dinoType.eggItem));
        }
        if (this.dinoType.mobType == MobType.DINOSAUR || this.dinoType.mobType == MobType.DINOSAUR_AQUATIC) {
            if (Revival.CONFIG_OPTIONS.eggsLikeChickens || this.isVivariousAquatic()) {
                baby = new ItemEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ(), new ItemStack(this.dinoType.eggItem));
            } else {
                baby = new EntityDinosaurEgg(EntityType.SHEEP, this.world);
                ((EntityDinosaurEgg) baby).selfType = this.dinoType;
            }
        }
        return baby;
    }

    public boolean isVivariousAquatic() {
        return false;
    }

    public boolean isAdult() {
        return this.getAgeInDays() >= getAdultAge();
    }

    public boolean isTeen() {
        return this.getAgeInDays() >= teenAge && this.getAgeInDays() < getAdultAge();
    }

    @Override
    public boolean isChild() {
        return this.getAgeInDays() < teenAge && !this.isSkeleton();
    }

    public abstract int getMaxHunger();

    public boolean isSkeleton() {
        return this.dataManager.get(MODELIZED);
    }

    public void setSkeleton(boolean skeleton) {
        this.dataManager.set(MODELIZED, skeleton);
    }

    public int getAgeInDays() {
        return this.dataManager.get(AGETICK) / 24000;
    }

    public void setAgeInDays(int days) {
        this.dataManager.set(AGETICK, days * 24000);
    }

    public int getAgeInTicks() {
        return this.dataManager.get(AGETICK);
    }

    public void setAgeinTicks(int ticks) {
        this.dataManager.set(AGETICK, ticks);
    }

    public int getHunger() {
        return this.dataManager.get(HUNGER);
    }

    public void setHunger(int hunger) {
        if (this.getHunger() > this.getMaxHunger()) {
            this.dataManager.set(HUNGER, this.getMaxHunger());
        } else {
            this.dataManager.set(HUNGER, hunger);
        }
    }

    public boolean isAgingDisabled() {
        return this.dataManager.get(AGINGDISABLED).booleanValue();
    }

    public void setAgingDisabled(boolean isAgingDisabled) {
        this.dataManager.set(AGINGDISABLED, isAgingDisabled);
    }


    public boolean increaseHunger(int hunger) {
        if (this.getHunger() >= this.getMaxHunger()) {
            return false;
        }
        this.setHunger(this.getHunger() + hunger);
        if (this.getHunger() > this.getMaxHunger()) {
            this.setHunger(this.getMaxHunger());
        }
        this.world.playSound(null, this.getPosition(), SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.NEUTRAL, this.getSoundVolume(), this.getSoundPitch());
        return true;
    }


    public boolean isHungry() {
        return this.getHunger() < this.getMaxHunger() * 0.75F;
    }

    public boolean isDeadlyHungry() {
        return this.getHunger() < this.getMaxHunger() * 0.25F;
    }

    @Override
    public boolean isOnLadder() {
        if (this.aiMovingType() == PrehistoricEntityTypeAI.Moving.AQUATIC || this.aiMovingType() == PrehistoricEntityTypeAI.Moving.SEMIAQUATIC) {
            return false;
        } else {
            return this.aiClimbType() == PrehistoricEntityTypeAI.Climbing.ARTHROPOD && this.isBesideClimbableBlock() && !this.isMovementBlockedSoft();
        }
    }

    public boolean isAngry() {
        return this.dataManager.get(ANGRY);
    }

    public void setAngry(boolean angry) {
        this.dataManager.set(ANGRY, angry);
    }

    public int getSubSpecies() {
        return this.dataManager.get(SUBSPECIES);
    }

    public void setSubSpecies(int subspecies) {
        this.dataManager.set(SUBSPECIES, subspecies);
    }

    public int getGender() {
        return this.dataManager.get(GENDER);
    }

    public void setGender(int gender) {
        this.dataManager.set(GENDER, gender);
    }

    public int getMood() {
        return MathHelper.clamp(this.dataManager.get(MOOD), -100, 100);
    }

    public void setMood(int mood) {
        this.dataManager.set(MOOD, MathHelper.clamp(mood, -100, 100));
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
    public boolean isSitting() {
        if (world.isRemote) {
            boolean isSitting = this.dataManager.get(SITTING);
            if ((isSitting != this.isSitting)) {
                ticksSitted = 0;
            }
            this.isSitting = isSitting;
            return isSitting;
        }

        return isSitting;
    }

    public void setSitting(boolean sitting) {

        this.dataManager.set(SITTING, sitting);
        if (!world.isRemote) {
            this.isSitting = sitting;
        }
    }

    public boolean shouldDismountInWater(Entity rider) {
        return false;
    }

    @Override
    public boolean attackEntityFrom(DamageSource dmg, float i) {
        if (dmg == DamageSource.IN_WALL) {
            return false;
        }
        if (i > 0 && this.isSkeleton()) {
            this.world.playSound(null, this.getPosition(), SoundEvents.ENTITY_SKELETON_HURT, SoundCategory.NEUTRAL, this.getSoundVolume(), this.getSoundPitch());
            if (!world.isRemote && !droppedBiofossil) {
                if (this.dinoType.timePeriod == TimePeriod.CENOZOIC) {
                    this.entityDropItem(FAItemRegistry.TAR_FOSSIL, 1);
                } else {
                    this.entityDropItem(FAItemRegistry.BIO_FOSSIL, 1);
                }
                this.entityDropItem(new ItemStack(Items.BONE, Math.min(this.getAgeInDays(), this.getAdultAge())), 1);
                droppedBiofossil = true;
            }
            this.setDead();
            return true;
        }
        if (this.getLastAttackedEntity() instanceof PlayerEntity) {
            if (this.getOwner() == this.getLastAttackedEntity()) {
                this.setTamed(false);
                this.setMood(this.getMood() - 15);
            }
        }

        if (i > 0) {
            this.setSitting(false);
            this.setSleeping(false);
        }
        if (dmg.getTrueSource() != null) {
            this.setMood(this.getMood() - 5);
        }
        if (this.getHurtSound(DamageSource.GENERIC) != null && i >= 1 && dmg != DamageSource.IN_WALL) {
            if (this.getAnimation() != null) {
                if (this.getAnimation() == NO_ANIMATION && world.isRemote) {
                    this.setAnimation(SPEAK_ANIMATION);
                }
            }
        }
        return super.attackEntityFrom(dmg, i);
    }

    public boolean isBesideClimbableBlock() {
        return (this.dataManager.get(CLIMBING) & 1) != 0;
    }

    public void setBesideClimbableBlock(boolean climbing) {
        byte b0 = this.dataManager.get(CLIMBING);

        if (climbing) {
            b0 = (byte) (b0 | 1);
        } else {
            b0 = (byte) (b0 & -2);
        }

        this.dataManager.set(CLIMBING, b0);
    }

    @Override
    public boolean onLivingFall(float distance, float damageMultiplier) {
        if (this.aiClimbType() == PrehistoricEntityTypeAI.Climbing.ARTHROPOD || this.aiMovingType() == PrehistoricEntityTypeAI.Moving.WALKANDGLIDE || this.aiMovingType() == PrehistoricEntityTypeAI.Moving.FLIGHT) {
            return false;
        } else {
          return  super.onLivingFall(distance, damageMultiplier);
        }
    }

    @Override
    public ActionResultType func_230254_b_(PlayerEntity player, Hand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        if (this.isSkeleton()) {
            if (itemstack.isEmpty()) {
                if (player.isSneaking()) {
                    this.nudgeEntity(player);
                } else {
                    double d0 = player.getPosX() - this.getPosX();
                    double d2 = player.getPosZ() - this.getPosZ();
                    double d3 = MathHelper.sqrt(d0 * d0 + d2 * d2);
                    float f = (float) (MathHelper.atan2(d2, d0) * (180D / Math.PI)) - 90.0F;
                    this.rotationYawHead = f;
                    this.rotationYaw = f;
                    this.renderYawOffset = f;
                }
                return ActionResultType.SUCCESS;
            } else {
                if (itemstack.getItem() == Items.BONE && this.getAgeInDays() < this.getAdultAge()) {
                    this.world.playSound(null, this.getPosition(), SoundEvents.ENTITY_SKELETON_AMBIENT, SoundCategory.NEUTRAL, 0.8F, 1);
                    this.setAgeInDays(this.getAgeInDays() + 1);
                    if (!player.isCreative()) {
                        itemstack.shrink(1);
                    }
                    return ActionResultType.SUCCESS;
                }
            }
        } else {

            if (!itemstack.isEmpty()) {
                if ((this.aiTameType() == PrehistoricEntityTypeAI.Taming.GEM && itemstack.getItem() == FAItemRegistry.SCARAB_GEM) || (this.aiTameType() == PrehistoricEntityTypeAI.Taming.BLUEGEM && itemstack.getItem() == FAItemRegistry.AQUATIC_SCARAB_GEM)) {
                    if (!this.isTamed() && !this.isOwner(player) && this.isActuallyWeak()) {
                        this.triggerTamingAcheivement(player);
                        this.heal(200);
                        this.setMood(100);
                        this.increaseHunger(500);
                        this.getNavigator().clearPath();
                        this.setAttackTarget(null);
                        this.setRevengeTarget(null);
                        this.setTamed(true);
                        this.setOwnerId(player.getUniqueID());
                        this.world.setEntityState(this, (byte) 35);
                        itemstack.shrink(1);
                        return ActionResultType.SUCCESS;
                    }
                }

                if (itemstack.getItem() == FAItemRegistry.CHICKEN_ESSENCE && this.aiTameType() != PrehistoricEntityTypeAI.Taming.GEM && this.aiTameType() != PrehistoricEntityTypeAI.Taming.BLUEGEM && !player.world.isRemote) {
                    if (this.getAgeInDays() < this.getAdultAge() && this.getHunger() > 0) {
                        if (this.getHunger() > 0) {
                            itemstack.shrink(1);
                            if (!player.isCreative()) {
                                player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE, 1));
                            }
                            //Revival.NETWORK_WRAPPER.sendToAll(new MessageFoodParticles(getEntityId(), Item.getIdFromItem(FAItemRegistry.CHICKEN_ESSENCE)));
                            this.grow(1);
                            this.setHunger(1 + (new Random()).nextInt(this.getHunger()));
                            return ActionResultType.SUCCESS;
                        }
                    }
                    if (!this.world.isRemote) {
                        player.sendStatusMessage(new TranslationTextComponent("prehistoric.essencefail"), true);
                    }
                    return ActionResultType.SUCCESS;
                }
                if (itemstack.getItem() == FAItemRegistry.STUNTED_ESSENCE && !isAgingDisabled()) {
                    this.setHunger(this.getHunger() + 20);
                    this.heal(this.getMaxHealth());
                    this.playSound(SoundEvents.ENTITY_ZOMBIE_VILLAGER_CURE, this.getSoundVolume(), this.getSoundPitch());
                    spawnItemCrackParticles(itemstack.getItem());
                    spawnItemCrackParticles(itemstack.getItem());
                    spawnItemCrackParticles(Items.POISONOUS_POTATO);
                    spawnItemCrackParticles(Items.POISONOUS_POTATO);
                    spawnItemCrackParticles(Items.EGG);
                    this.setAgingDisabled(true);
                    if (!player.isCreative()) {
                        itemstack.shrink(1);
                    }
                    return ActionResultType.SUCCESS;
                }

                if (FoodMappings.getItemFoodAmount(itemstack, this.dinoType.diet) != 0) {
                    if (!player.world.isRemote) {
                        if (this.getHunger() < this.getMaxHunger() || this.getHealth() < this.getMaxHealth() && Revival.CONFIG_OPTIONS.healingDinos || !this.isTamed() && this.aiTameType() == PrehistoricEntityTypeAI.Taming.FEEDING) {
                            this.setHunger(this.getHunger() + FoodMappings.getItemFoodAmount(itemstack, this.dinoType.diet));
                            if (!world.isRemote) {
                                this.eatItem(itemstack);
                            }
                            if (Revival.CONFIG_OPTIONS.healingDinos) {
                                this.heal(3);
                            }
                            if (this.getHunger() >= this.getMaxHunger()) {
                                if (this.isTamed()) {
                                }
                            }
                            itemstack.shrink(1);
                            if (this.aiTameType() == PrehistoricEntityTypeAI.Taming.FEEDING) {
                                if (!this.isTamed() && this.isTameable() && (new Random()).nextInt(10) == 1) {
                                    this.setTamed(true);
                                    this.setOwnerId(player.getUniqueID());
                                    this.world.setEntityState(this, (byte) 35);
                                }
                            }

                            return ActionResultType.SUCCESS;
                        } else {
                            return ActionResultType.PASS;
                        }
                    }

                    return ActionResultType.PASS;
                } else {
                    if (itemstack.getItem() == Items.LEAD && this.isTamed()) {
                        if (this.isOwner(player)) {
                            this.setLeashHolder(player, true);
                            itemstack.shrink(1);
                            return ActionResultType.SUCCESS;
                        }
                    }
                    if (world.isRemote && itemstack.getItem() == FAItemRegistry.DINOPEDIA) {
                        this.setPedia();
                      //  player.openGui(Revival.INSTANCE, 6, this.world, (int) this.getPosX(), (int) this.getPosY(), (int) this.getPosZ());
                        return ActionResultType.SUCCESS;
                    }

                    if (itemstack.getItem() == FAItemRegistry.WHIP && this.aiTameType() != PrehistoricEntityTypeAI.Taming.NONE && this.isAdult()) {
                        if (this.isTamed() && isOwner(player) && this.canBeRidden()) {
                            if (this.getRidingPlayer() == null) {
                                if (!this.world.isRemote) {
                                 //   Revival.NETWORK_WRAPPER.sendToAll(new MessageFoodParticles(getEntityId(), FABlockRegistry.VOLCANIC_ROCK));
                                    setRidingPlayer(player);
                                }
                                this.setOrder(OrderType.WANDER);
                                this.setSitting(false);
                                this.setSleeping(false);
                            } else if (this.getRidingPlayer() == player) {
                                this.setSprinting(true);
                                if (!this.world.isRemote) {
                                //    Revival.NETWORK_WRAPPER.sendToAll(new MessageFoodParticles(getEntityId(), FABlockRegistry.VOLCANIC_ROCK));
                                }
                                this.setMood(this.getMood() - 5);
                            }
                        } else if (!this.isTamed() && this.aiTameType() != PrehistoricEntityTypeAI.Taming.BLUEGEM && this.aiTameType() != PrehistoricEntityTypeAI.Taming.GEM) {
                            this.setMood(this.getMood() - 5);
                          //  Revival.NETWORK_WRAPPER.sendToAll(new MessageFoodParticles(getEntityId(), FABlockRegistry.VOLCANIC_ROCK));
                            if (getRNG().nextInt(5) == 0) {
                                ITextComponent itextcomponent = new StringTextComponent(this.getName().getString());
                                player.sendStatusMessage(new TranslationTextComponent("prehistoric.autotame", itextcomponent), true);
                                this.setMood(this.getMood() - 25);
                                this.setTamed(true);
                            //    Revival.NETWORK_WRAPPER.sendToAll(new MessageFoodParticles(getEntityId(), Item.getIdFromItem(Items.GOLD_INGOT)));
                                this.setOwnerId(player.getUniqueID());
                            }
                        }
                        this.setSitting(false);
                        // this.setOrder(OrderType.WANDER);

                        // this.currentOrder = OrderType.FreeMove;
                        // setRidingPlayer(player);
                    }
                    if (this.getOrderItem() != null && itemstack.getItem() == this.getOrderItem() && this.isTamed() && this.isOwner(player) && !player.isPassenger()) {
                        if (!this.world.isRemote) {
                            this.isJumping = false;
                            this.getNavigator().clearPath();
                            this.currentOrder = OrderType.values()[(this.currentOrder.ordinal() + 1) % 3];
                            this.sendOrderMessage(this.currentOrder);
                        }
                        return ActionResultType.SUCCESS;
                    }
                }
            }
        }
        return super.func_230254_b_(player, hand);
    }

    protected boolean isTameable(){
        return true;
    }

    public abstract Item getOrderItem();

    private void triggerTamingAcheivement(PlayerEntity player) {
        // player.triggerAchievement(FossilAchievementHandler.theKing);

    }

    public void grow(int ageInDays) {
        if (this.isAgingDisabled()) {
            return;
        }
        this.setAgeInDays(this.getAgeInDays() + ageInDays);
        for (int i = 0; i < this.getAgeScale() * 4; i++) {
            double motionX = getRNG().nextGaussian() * 0.07D;
            double motionY = getRNG().nextGaussian() * 0.07D;
            double motionZ = getRNG().nextGaussian() * 0.07D;
            float f = (float) (getRNG().nextFloat() * (this.getBoundingBox().maxX - this.getBoundingBox().minX) + this.getBoundingBox().minX);
            float f1 = (float) (getRNG().nextFloat() * (this.getBoundingBox().maxY - this.getBoundingBox().minY) + this.getBoundingBox().minY);
            float f2 = (float) (getRNG().nextFloat() * (this.getBoundingBox().maxZ - this.getBoundingBox().minZ) + this.getBoundingBox().minZ);
            this.world.addParticle(ParticleTypes.HAPPY_VILLAGER, f, f1, f2, motionX, motionY, motionZ);

        }
        this.updateAbilities();
    }

    public boolean isWeak() {
        return (this.getHealth() < 8) && (this.getAgeInDays() >= this.getAdultAge()) && !this.isTamed();
    }

    protected void setPedia() {
        Revival.PROXY.setPediaObject(this);
    }

    private void sendOrderMessage(OrderType var1) {
        String s = "dino.order." + var1.name().toLowerCase();
        if (this.getOwner() instanceof PlayerEntity) {
            ((PlayerEntity) this.getOwner()).sendStatusMessage(new TranslationTextComponent(s, this.getName()), true);
        }
    }

    public void nudgeEntity(PlayerEntity player) {
        this.setPositionAndUpdate(this.getPosX() + (player.getPosX() - this.getPosX()) * 0.01F, this.getPosY(), this.getPosZ() + (player.getPosZ() - this.getPosZ()) * 0.01F);
    }

    public ArrayList<Class<? extends Entity>> preyList() {
        return new ArrayList<>();
    }

    public ArrayList<Class<? extends Entity>> preyBlacklist() {
        return new ArrayList<>();
    }

    public void playerRoar(PlayerEntity player) {
    }

    public void playerAttack(PlayerEntity player) {
    }

    public void playerJump(PlayerEntity player) {
    }

    public void playerFlyUp(PlayerEntity player) {
    }

    public void playerFlyDown(PlayerEntity player) {
    }

    public String getTexture() {
        if (this.isSkeleton()) {
            return "fossil:textures/entity/" + dinoType.toString().toLowerCase() + "/" + dinoType.toString().toLowerCase() + "_skeleton.png";
        }
        if (this.hasBabyTexture) {
            String toggle = this.hasFeatherToggle ? this.featherToggle ? "feathered/" : "scaled/" : "";
            boolean isBaby = this.isChild() && this.hasBabyTexture;
            String gender = this.hasTeenTexture ? this.isTeen() ? "_teen" : isBaby ? "_baby" : this.getGender() == 0 ? "_female" : "_male" : this.isChild() ? "_baby" : this.getGender() == 0 ? "_female" : "_male";
            String sleeping = !this.isSleeping() ? this.isActuallyWeak() ? "_sleeping" : "" : "_sleeping";
            String toggleList = this.hasFeatherToggle ? this.featherToggle ? "_feathered" : "_scaled" : "";
            return "fossil:textures/entity/" + dinoType.toString().toLowerCase() + "/" + toggle + dinoType.toString().toLowerCase() + gender + toggleList + sleeping + ".png";
        } else {
            String toggle = this.hasFeatherToggle ? this.featherToggle ? "feathered/" : "scaled/" : "";
            String gender = this.getGender() == 0 ? "_female" : "_male";
            String sleeping = !this.isSleeping() ? this.isActuallyWeak() ? "_sleeping" : "" : "_sleeping";
            String toggleList = this.hasFeatherToggle ? this.featherToggle ? "_feathered" : "_scaled" : "";
            return "fossil:textures/entity/" + dinoType.toString().toLowerCase() + "/" + toggle + dinoType.toString().toLowerCase() + gender + toggleList + sleeping + ".png";
        }
    }

    public boolean isActuallyWeak() {
        return (this.aiTameType() == PrehistoricEntityTypeAI.Taming.BLUEGEM || this.aiTameType() == PrehistoricEntityTypeAI.Taming.GEM) && this.isWeak();
    }

    public int getTailSegments() {
        return 3;
    }

    private double getSpeed() {
        return 0.4D;
    }

    public float getMaleSize() {
        return 1.0F;
    }

    public String getOverlayTexture() {
        return "fossil:textures/blank.png";
    }

    @Override
    public int getAnimationTick() {
        return animTick;
    }

    @Override
    public void setAnimationTick(int tick) {
        animTick = tick;
    }

    @Override
    public Animation getAnimation() {
        return currentAnimation == null ? NO_ANIMATION : currentAnimation;
    }

    @Override
    public void setAnimation(Animation animation) {
        currentAnimation = animation;
    }

    @Override
    public Animation[] getAnimations() {
        return new Animation[]{SPEAK_ANIMATION, ATTACK_ANIMATION};
    }

    @Override
    public void playAmbientSound() {
        if (!this.isSleeping() && !this.isSkeleton()) {
            super.playAmbientSound();
            if (this.getAnimation() != null) {
                if (this.getAnimation() == NO_ANIMATION && !world.isRemote) {
                    this.setAnimation(SPEAK_ANIMATION);
                }
            }
        }
    }

    public void knockbackEntity(Entity knockBackMob, float knockbackStrength, float knockbackStrengthUp) {
        if (!(knockBackMob instanceof EntityToyBase) && knockBackMob instanceof LivingEntity) {
            double resistance = ((LivingEntity) knockBackMob).getAttribute(Attributes.KNOCKBACK_RESISTANCE).getValue();
            double reversed = 1 - resistance;
            knockBackMob.setMotion(knockBackMob.getMotion().add(0, 0.4000000059604645D * reversed + 0.1D, 0));
            if (resistance < 1) {
                knockBackMob(knockBackMob, 0.25D, 0.2D, 0.25D);
            }
        }
    }

    public boolean canDinoHunt(LivingEntity target, boolean hunger) {
        if (target instanceof EntityToyBase) {
            return true;
        }
        boolean isAnotherDino = target instanceof EntityPrehistoric;
        boolean b = true;
        if (target instanceof LivingEntity) {
            b = true;// FoodHelper.getMobFoodPoints((LivingEntity) target, this.dinoType.diet) > 0;
        }
        if (this.dinoType.diet != Diet.HERBIVORE && this.dinoType.diet != Diet.NONE && b && canAttack(target)) {
            if (isAnotherDino ? this.getActualWidth() * getTargetScale() >= ((EntityPrehistoric) target).getActualWidth() : this.getActualWidth() * getTargetScale() >= target.getWidth()) {
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

    public void mate() {
        double d0 = 64;
        List<Entity> list = world.getEntitiesInAABBexcluding(this, this.getBoundingBox().expand(d0, 4.0D, d0), PREHISTORIC_PREDICATE);
        List<EntityPrehistoric> listOfFemales = new ArrayList<>();
        if (!list.isEmpty()) {
            for (Entity e : list) {
                EntityPrehistoric mob = (EntityPrehistoric) e;
                if (!mob.isEntityEqual(this) && mob.dinoType == this.dinoType && mob.isAdult() && mob.getGender() == 0 && mob.ticksTillMate == 0) {
                    listOfFemales.add(mob);
                }
            }
        }
        if (!listOfFemales.isEmpty() && this.ticksTillMate == 0) {
            EntityPrehistoric prehistoric = listOfFemales.get(0);
            if (prehistoric.ticksTillMate == 0) {
                this.getNavigator().tryMoveToEntityLiving(prehistoric, 1);
                double distance = this.getWidth() * 8.0F * this.getWidth() * 8.0F + prehistoric.getWidth();
                if (this.getDistanceSq(prehistoric.getPosX(), prehistoric.getBoundingBox().minY, prehistoric.getPosZ()) <= distance && prehistoric.onGround && this.onGround && this.isAdult() && prehistoric.isAdult()) {
                    prehistoric.procreate(this);
                    this.ticksTillMate = this.rand.nextInt(6000) + 6000;
                    prehistoric.ticksTillMate = this.rand.nextInt(12000) + 24000;
                }
            }
        }
    }

    protected PathNavigator createNavigator(World worldIn) {
        return this.aiClimbType() == PrehistoricEntityTypeAI.Climbing.ARTHROPOD ? new ClimberPathNavigator(this, worldIn) : new DinosaurPathNavigator(this, worldIn);
    }


    public abstract boolean canBeRidden();

    @Override
    public boolean canBeSteered() {
        return canBeRidden() && this.getControllingPassenger() instanceof LivingEntity && this.isOwner((LivingEntity) this.getControllingPassenger());
    }

    public void procreate(EntityPrehistoric mob) {
        for (int i = 0; i < 7; ++i) {
            double dd = this.rand.nextGaussian() * 0.02D;
            double dd1 = this.rand.nextGaussian() * 0.02D;
            double dd2 = this.rand.nextGaussian() * 0.02D;
      //      Revival.PROXY.spawnPacketHeartParticles(this.world, (float) (this.getPosX() + (this.rand.nextFloat() * this.getWidth() * 2.0F) - this.getWidth()), (float) (this.getPosY() + 0.5D + (this.rand.nextFloat() * this.height)), (float) (this.getPosZ() + (this.rand.nextFloat() * this.getWidth() * 2.0F) - this.getWidth()), dd, dd1, dd2);
      //      Revival.PROXY.spawnPacketHeartParticles(mob.world, (float) (mob.posX + (mob.rand.nextFloat() * mob.width * 2.0F) - mob.width), (float) (mob.posY + 0.5D + (mob.rand.nextFloat() * mob.height)), (float) (mob.posZ + (mob.rand.nextFloat() * mob.width * 2.0F) - mob.width), dd, dd1, dd2);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        if (this.rand.nextInt(100) == 0 || calendar.get(2) + 1 == 4 && calendar.get(5) == 1) {
         //   this.playSound(FASoundRegistry.MUSIC_MATING, 1, 1);
        }
        Entity hatchling = this.createEgg(mob);
        if (hatchling != null && !world.isRemote) {
            this.setAttackTarget(null);
            mob.setAttackTarget(null);
            hatchling.setPositionAndRotation(mob.getPosX(), mob.getPosY() + 1, mob.getPosZ(), mob.rotationYaw, 0);
            if (hatchling instanceof EntityDinosaurEgg) {
          //      Revival.NETWORK_WRAPPER.sendToAll(new MessageUpdateEgg(hatchling.getEntityId(), this.dinoType.ordinal()));
            } else {
                if (hatchling instanceof EntityPrehistoric) {
                    ((EntityPrehistoric) hatchling).grow(1);
                    ((EntityPrehistoric) hatchling).setHealth((float) this.baseHealth);
                }
            }
            this.world.addEntity(hatchling);
        }
    }

    public boolean isThereNearbyTypes() {
        double d0 = 40;
        List<EntityPrehistoric> list = world.getEntitiesWithinAABB(this.getClass(), this.getBoundingBox().expand(d0, 4.0D, d0), null);
        if (list.isEmpty()) {
            return false;
        } else {
            List<EntityPrehistoric> listOfType = new ArrayList<>();
            for (EntityPrehistoric mob : list) {
                if (mob != this && mob.dinoType == this.dinoType && mob.isAdult()) {
                    listOfType.add(mob);
                }
            }
            return listOfType.size() > this.nearByMobsAllowed;
        }
    }

    public void doFoodEffect(Item item) {
        this.world.playSound(null, this.getPosition(), SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.NEUTRAL, this.getSoundVolume(), this.getSoundPitch());
        if (item != null) {
            if (item instanceof BlockItem) {
                spawnItemParticle(item, true);
            } else {
                spawnItemParticle(item, false);
            }
        }
    }

    public void doFoodEffect() {
        this.world.playSound(null, this.getPosition(), SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.NEUTRAL, this.getSoundVolume(), this.getSoundPitch());
        switch (this.dinoType.diet) {
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
            double motionX = getRNG().nextGaussian() * 0.07D;
            double motionY = getRNG().nextGaussian() * 0.07D;
            double motionZ = getRNG().nextGaussian() * 0.07D;
            float f = (float) (getRNG().nextFloat() * (this.getBoundingBox().maxX - this.getBoundingBox().minX) + this.getBoundingBox().minX);
            float f1 = (float) (getRNG().nextFloat() * (this.getBoundingBox().maxY - this.getBoundingBox().minY) + this.getBoundingBox().minY);
            float f2 = (float) (getRNG().nextFloat() * (this.getBoundingBox().maxZ - this.getBoundingBox().minZ) + this.getBoundingBox().minZ);
            if (world.isRemote) {
                this.world.addParticle(new ItemParticleData(ParticleTypes.ITEM, new ItemStack(item)), f, f1, f2, motionX, motionY, motionZ);
            }
        }
    }


    public void spawnItemParticle(Item item, boolean itemBlock) {
        if (!world.isRemote) {
            double motionX = rand.nextGaussian() * 0.07D;
            double motionY = rand.nextGaussian() * 0.07D;
            double motionZ = rand.nextGaussian() * 0.07D;
            float f = (float) (getRNG().nextFloat() * (this.getBoundingBox().maxX - this.getBoundingBox().minX) + this.getBoundingBox().minX);
            float f1 = (float) (getRNG().nextFloat() * (this.getBoundingBox().maxY - this.getBoundingBox().minY) + this.getBoundingBox().minY);
            float f2 = (float) (getRNG().nextFloat() * (this.getBoundingBox().maxZ - this.getBoundingBox().minZ) + this.getBoundingBox().minZ);
          /*  if (itemBlock && item instanceof ItemBlock) {
                Revival.NETWORK_WRAPPER.sendToAll(new MessageFoodParticles(this.getEntityId(), Block.getIdFromBlock(((ItemBlock) item).getBlock())));
            } else {
                Revival.NETWORK_WRAPPER.sendToAll(new MessageFoodParticles(this.getEntityId(), Item.getIdFromItem(item)));
            }
           */
        }
    }

    public boolean isInWaterMaterial() {
        double d0 = this.getPosY();
        int i = MathHelper.floor(this.getPosX());
        int j = MathHelper.floor((float) MathHelper.floor(d0));
        int k = MathHelper.floor(this.getPosZ());
        BlockState blockState = this.world.getBlockState(new BlockPos(i, j, k));
        if (blockState.getMaterial() == Material.WATER) {
            double filled = 1.0f;
            if (blockState.getBlock() instanceof IFluidBlock) {
                filled = ((IFluidBlock) blockState.getBlock()).getFilledPercentage(world, new BlockPos(i, j, k));
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
    }

    public void eatItem(ItemStack stack) {
        if (stack != null && stack.getItem() != null) {
            if (FoodMappings.getItemFoodAmount(stack, dinoType.diet) != 0) {
                this.setMood(this.getMood() + 5);
                doFoodEffect(stack.getItem());
                //Revival.NETWORK_WRAPPER.sendToAll(new MessageFoodParticles(getEntityId(), Item.getIdFromItem(stack.getItem())));
                this.setHunger(this.getHunger() + FoodMappings.getItemFoodAmount(stack, dinoType.diet));
                stack.shrink(1);
                if (this.getAnimation() == NO_ANIMATION) {
                    this.setAnimation(SPEAK_ANIMATION);
                }
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
        if (this.getWidth() <= entity.getWidth()) {
            if (entity instanceof EntityPrehistoric) {
                EntityPrehistoric mob = (EntityPrehistoric) entity;
                return mob.dinoType.diet != Diet.HERBIVORE;
            } else {
                if (entity instanceof PlayerEntity) {
                    PlayerEntity player = (PlayerEntity) entity;
                    return !this.isOwner(player);
                }
                return true;
            }
        }
        return false;
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return PrehistoricEntityType.DINOSAUR_LOOT;
    }


    @Override
    public double getMountedYOffset() {
        return super.getMountedYOffset();
    }

    @Override
    public void updatePassenger(Entity passenger) {
        super.updatePassenger(passenger);
        if (this.isPassenger(passenger)) {
            renderYawOffset = rotationYaw;
            this.rotationYaw = passenger.rotationYaw;
        }
        if (this.getRidingPlayer() != null && this.getRidingPlayer() instanceof PlayerEntity && this.isOwner(this.getRidingPlayer()) && this.getAttackTarget() != this.getRidingPlayer()) {
            rotationYaw = renderYawOffset;
            rotationYaw = this.getRidingPlayer().rotationYaw;
            rotationYawHead = this.getRidingPlayer().rotationYaw;
            float radius = ridingXZ * (0.7F * getAgeScale()) * -3;
            float angle = (0.01745329251F * this.renderYawOffset);
            double extraX = radius * MathHelper.sin((float) (Math.PI + angle));
            double extraZ = radius * MathHelper.cos(angle);
            double extraY = ridingY * (getAgeScale());
            float spinosaurusAddition = 0;
         /*    if (this instanceof EntitySpinosaurus) {
                spinosaurusAddition = -(((EntitySpinosaurus) this).swimProgress * 0.1F);
            }*/
            this.getRidingPlayer().setPosition(this.getPosX() + extraX, this.getPosY() + extraY + spinosaurusAddition - 1.75F, this.getPosZ() + extraZ);
        }
       /* if (passenger instanceof EntityVelociraptor || passenger instanceof EntityDeinonychus) {
            double extraY = Math.min(ridingY * (getAgeScale()) - 1D, 0.5D);
            passenger.setPosition(this.getPosX(), this.getPosY() + extraY, this.getPosZ());
        }/
        //TODO
        */
    }

    private double getJumpStrength() {
        return 3D;
    }

    protected boolean isDinoJumping() {
        return horseJumping;
    }

    protected void setDinoJumping(boolean jump) {
        horseJumping = jump;
    }

    @Override
    public AgeableEntity func_241840_a(ServerWorld serverWorld, AgeableEntity ageableEntity) {
        Entity baby = this.dinoType.create(this.world);
        if (ageableEntity instanceof EntityPrehistoric) {
            EntityPrehistoric prehistoric = (EntityPrehistoric) baby;
            prehistoric.setAgeInDays(0);
            prehistoric.grow(0);
            prehistoric.updateAbilities();
            prehistoric.setNoAI(false);
            return ((EntityPrehistoric) baby);
        }
        return null;
    }

    public boolean isAquatic() {
        return this instanceof EntityPrehistoricSwimming;
    }

    public void onWhipRightClick() {

    }

    public boolean canReachPrey() {
        return this.getAttackTarget() != null && getAttackBounds().intersects(this.getAttackTarget().getBoundingBox()) && !isPreyBlocked(this.getAttackTarget());
    }

    public boolean isPreyBlocked(Entity prey) {
        return this.canEntityBeSeen(prey);
    }

    public boolean rayTraceFeeder(BlockPos position, boolean leaves) {

        return true;
    }

    private boolean isFeeder(BlockPos pos, boolean leaves) {
        if (leaves) {
            BlockState state = world.getBlockState(pos);
            return FoodMappings.getBlockFoodAmount(state.getBlock(), this.dinoType.diet) > 0;
        } else {
            BlockState state = world.getBlockState(pos);
            TileEntity entity = this.world.getTileEntity(pos);
            return false;//  return entity instanceof TileEntityFeeder;
        }
    }

    public float getDeathRotation() {
        return 90.0F;
    }

    protected float getSoundVolume() {
        return this.isChild() ? super.getSoundVolume() * 0.75F : 1.0F;
    }

    protected void doAttackKnockback(float strength) {
        if (this.getAttackTarget() != null) {
            if (this.getAttackTarget().getRidingEntity() != null) {
                if (this.getAttackTarget().getRidingEntity() == this) {
                    this.getAttackTarget().stopRiding();
                }
            }
            knockbackEntity(this.getAttackTarget(), strength, 0.1F);
            this.getAttackTarget().isAirBorne = false;
        }
    }

    public void doAttack() {
        ModifiableAttributeInstance iattributeinstance = this.getAttribute(Attributes.ATTACK_DAMAGE);
        if (getAttackTarget() != null) {
            boolean b = this.getAttackTarget().attackEntityFrom(DamageSource.causeMobDamage(this), (float) iattributeinstance.getValue());
            this.setFleeingFlag(b);
        }
    }

    @OnlyIn(Dist.CLIENT)
    public void handleStatusUpdate(byte id) {
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
            super.handleStatusUpdate(id);
        }
    }

    public void spawnItemParticle(Item item) {
        Random rand = new Random();
        double motionX = rand.nextGaussian() * 0.07D;
        double motionY = rand.nextGaussian() * 0.07D;
        double motionZ = rand.nextGaussian() * 0.07D;
        double f = (float) (rand.nextFloat() * (this.getBoundingBox().maxX - this.getBoundingBox().minX) + this.getBoundingBox().minX);
        double f1 = (float) (rand.nextFloat() * (this.getBoundingBox().maxY - this.getBoundingBox().minY) + this.getBoundingBox().minY);
        double f2 = (float) (rand.nextFloat() * (this.getBoundingBox().maxZ - this.getBoundingBox().minZ) + this.getBoundingBox().minZ);
        this.world.addParticle(new ItemParticleData(ParticleTypes.ITEM, new ItemStack(item)), f, f1, f2, motionX, motionY, motionZ);
    }

    public float getMaxTurnDistancePerTick() {
        return MathHelper.clamp(90 - this.getActualWidth() * 20, 10, 90);
    }

    @OnlyIn(Dist.CLIENT)
    public void setJumpPower(int jumpPowerIn) {
        if (this.isBeingRidden()) {
            if (jumpPowerIn < 0) {
                jumpPowerIn = 0;
            }

            if (jumpPowerIn >= 90) {
                this.jumpPower = 1.0F;
            } else {
                this.jumpPower = 0.4F + 0.4F * (float) jumpPowerIn / 90.0F;
            }
        }
    }

    public boolean canJump() {
        return this.isBeingRidden();
    }

    public void handleStartJump(int i) {
    }

    public void handleStopJump() {
    }

    public float getProximityToNextPathSkip() {
        return this.getWidth() > 0.75F ? this.getWidth() / 2.0F : 0.75F - this.getWidth() / 2.0F;
    }

    public Animation getExtraAnimation(int i) {
        return NO_ANIMATION;
    }

    public boolean useSpecialAttack() {
        return false;
    }

    public boolean isFleeingFlag() {
        return this.dataManager.get(FLEEING);
    }

    public void setFleeingFlag(boolean fleeing) {
        this.dataManager.set(FLEEING, fleeing);
    }

    public boolean isFleeing() {
        return isFleeingFlag() && this.dinoType.diet == Diet.HERBIVORE;
    }

    protected int getFleeingCooldown() {
        if (this.getRevengeTarget() != null) {
            int i = (int) (Math.max(this.getRevengeTarget().getWidth() / 2F, 1) * 95);
            int j = (int) (Math.min(this.getWidth() / 2F, 0.5D) * 50);
            return i - j;
        }
        return 100;
    }
}
