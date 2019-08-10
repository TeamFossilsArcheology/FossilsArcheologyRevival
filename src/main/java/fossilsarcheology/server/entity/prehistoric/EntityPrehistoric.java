package fossilsarcheology.server.entity.prehistoric;

import com.google.common.base.Predicate;
import fossilsarcheology.Revival;
import fossilsarcheology.client.sound.FASoundRegistry;
import fossilsarcheology.server.block.FABlockRegistry;
import fossilsarcheology.server.block.IDinoUnbreakable;
import fossilsarcheology.server.block.entity.TileEntityFeeder;
import fossilsarcheology.server.entity.EntityDinosaurEgg;
import fossilsarcheology.server.entity.FoodHelper;
import fossilsarcheology.server.entity.utility.EntityToyBase;
import fossilsarcheology.server.entity.utility.FossilsPlayerProperties;
import fossilsarcheology.server.item.FAItemRegistry;
import fossilsarcheology.server.message.MessageFoodParticles;
import fossilsarcheology.server.message.MessageHappyParticles;
import fossilsarcheology.server.message.MessageUpdateEgg;
import fossilsarcheology.server.util.FoodMappings;
import net.ilexiconn.llibrary.client.model.tools.ChainBuffer;
import net.ilexiconn.llibrary.server.animation.Animation;
import net.ilexiconn.llibrary.server.animation.AnimationHandler;
import net.ilexiconn.llibrary.server.animation.IAnimatedEntity;
import net.ilexiconn.llibrary.server.entity.EntityPropertiesHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.ai.attributes.IAttributeInstance;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateClimber;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.*;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextComponentString;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraftforge.fluids.IFluidBlock;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.*;

public abstract class EntityPrehistoric extends EntityTameable implements IPrehistoricAI, IAnimatedEntity {

    private static final DataParameter<Integer> AGETICK = EntityDataManager.createKey(EntityPrehistoric.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> HUNGER = EntityDataManager.createKey(EntityPrehistoric.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> MODELIZED = EntityDataManager.createKey(EntityPrehistoric.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> ANGRY = EntityDataManager.createKey(EntityPrehistoric.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> SUBSPECIES = EntityDataManager.createKey(EntityPrehistoric.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> GENDER = EntityDataManager.createKey(EntityPrehistoric.class, DataSerializers.VARINT);
    private static final DataParameter<Boolean> SLEEPING = EntityDataManager.createKey(EntityPrehistoric.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> SITTING = EntityDataManager.createKey(EntityPrehistoric.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> MOOD = EntityDataManager.createKey(EntityPrehistoric.class, DataSerializers.VARINT);
    private static final DataParameter<String> OWNERDISPLAYNAME = EntityDataManager.createKey(EntityPrehistoric.class, DataSerializers.STRING);
    private static final DataParameter<Byte> CLIMBING = EntityDataManager.createKey(EntityPrehistoric.class, DataSerializers.BYTE);
    public final Animation SPEAK_ANIMATION;
    public Animation ATTACK_ANIMATION;
    public float minSize;
    public float maxSize;
    public int teenAge;
    public final PrehistoricEntityType type;
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
    @SideOnly(Side.CLIENT)
    public ChainBuffer chainBuffer;
    public float pediaScale;
    public int pediaY = 0;
    public boolean mood_nospace;
    public boolean mood_noplants;
    public int ticksTillPlay;
    public int ticksTillMate;
    public int prevAge;
    public boolean isDaytime;
    public Flock flockObj;
    public final double baseDamage;
    public final double maxDamage;
    public final double baseHealth;
    public final double maxHealth;
    public final double baseSpeed;
    public final double maxSpeed;
    public final double baseArmor;
    public final double maxArmor;
    public float ridingXZ;
    public float ridingY;
    public float actualWidth;
    protected boolean isSitting;
    protected boolean isSleeping;
    protected boolean developsResistance;
    protected boolean breaksBlocks;
    protected int nearByMobsAllowed;
    private Animation currentAnimation;
    private int animTick;
    public boolean shouldWander = true;
    public boolean isRunningAway = false;
    private static final Predicate PREHISTORIC_PREDICATE = new Predicate<EntityLivingBase>() {
        public boolean apply(@Nullable EntityLivingBase entity) {
            return entity != null && entity instanceof EntityPrehistoric;
        }
    };

    public EntityPrehistoric(World world, PrehistoricEntityType type, double baseDamage, double maxDamage, double baseHealth, double maxHealth, double baseSpeed, double maxSpeed, double baseArmor, double maxArmor) {
        super(world);
        this.aiSit = new EntityAISit(this);
        this.setHunger(this.getMaxHunger() / 2);
        this.setScaleForAge(false);
        SPEAK_ANIMATION = Animation.create(this.getSpeakLength());
        ATTACK_ANIMATION = Animation.create(this.getAttackLength());
        this.hasBabyTexture = true;
        this.type = type;
        this.pediaScale = 1.0F;
        this.nearByMobsAllowed = 15;
        this.currentOrder = OrderType.WANDER;
        if (ticksTillMate == 0) {
            ticksTillMate = this.rand.nextInt(6000) + 6000;
        }
        if (FMLCommonHandler.instance().getSide().isClient()) {
            this.chainBuffer = new ChainBuffer();
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
        }
    }

    public static boolean isCannibalistic() {
        return false;
    }

    public static boolean isEntitySmallerThan(Entity entity, float size) {
        if (entity instanceof EntityPrehistoric) {
            return ((EntityPrehistoric) entity).getActualWidth() <= size;
        } else {
            return entity.width <= size;
        }
    }

    public static void knockBackMob(Entity entity, double xMotion, double yMotion, double zMotion) {
        entity.isAirBorne = true;
        float f1 = MathHelper.sqrt(xMotion * xMotion + zMotion * zMotion);
        entity.motionX /= 2.0D;
        entity.motionY /= 2.0D;
        entity.motionZ /= 2.0D;
        entity.motionX -= xMotion / (double) f1;
        entity.motionY += yMotion;
        entity.motionZ -= zMotion / (double) f1;
    }

    public int getSpeakLength() {
        return 20;
    }

    public int getAttackLength() {
        return 10;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(AGETICK, 0);
        this.dataManager.register(HUNGER, 0);
        this.dataManager.register(MODELIZED, false);
        this.dataManager.register(ANGRY, false);
        this.dataManager.register(SUBSPECIES, 0);
        this.dataManager.register(GENDER, 0);
        this.dataManager.register(SLEEPING, false);
        this.dataManager.register(SITTING, false);
        this.dataManager.register(CLIMBING, (byte) 0);
        this.dataManager.register(MOOD, 0);
        this.dataManager.register(OWNERDISPLAYNAME, "");
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound compound) {
        super.writeEntityToNBT(compound);
        compound.setInteger("AgeTick", this.getAgeInTicks());
        compound.setInteger("Hunger", this.getHunger());
        compound.setBoolean("isModelized", this.isSkeleton());
        compound.setBoolean("Angry", this.isAngry());
        compound.setInteger("SubSpecies", this.getSubSpecies());
        compound.setInteger("Gender", this.getGender());
        compound.setBoolean("Sleeping", this.isSleeping);
        compound.setInteger("Mood", this.getMood());
        compound.setBoolean("Sitting", this.isSitting);
        compound.setBoolean("MoodNoSpace", this.mood_nospace);
        compound.setBoolean("MoodNoPlants", this.mood_noplants);
        compound.setInteger("TicksSincePlay", this.ticksTillPlay);
        compound.setInteger("TicksSlept", this.ticksSlept);
        compound.setInteger("TicksSinceMate", this.ticksTillMate);
        compound.setByte("currentOrder", (byte) this.currentOrder.ordinal());
        compound.setString("OwnerDisplayName", this.getOwnerDisplayName());
        compound.setFloat("YawRotation", this.rotationYaw);
        compound.setFloat("HeadRotation", this.rotationYawHead);

    }

    public String getOwnerDisplayName() {
        return this.dataManager.get(OWNERDISPLAYNAME);
    }

    public void setOwnerDisplayName(String displayName) {
        this.dataManager.set(OWNERDISPLAYNAME, displayName);
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.maxSpeed);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(this.maxArmor);
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(1D);
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound compound) {
        super.readEntityFromNBT(compound);
        this.setAgeinTicks(compound.getInteger("AgeTick"));
        this.setHunger(compound.getInteger("Hunger"));
        this.setSkeleton(compound.getBoolean("isModelized"));
        this.setAngry(compound.getBoolean("Angry"));
        this.setSubSpecies(compound.getInteger("SubSpecies"));
        this.setGender(compound.getInteger("Gender"));
        this.setSleeping(compound.getBoolean("Sleeping"));
        this.setSitting(compound.getBoolean("Sitting"));
        this.setMood(compound.getInteger("Mood"));
        if (compound.hasKey("currentOrder")) {
            this.setOrder(OrderType.values()[compound.getByte("currentOrder")]);
        }
        this.mood_nospace = compound.getBoolean("MoodNoSpace");
        this.mood_noplants = compound.getBoolean("MoodNoPlants");
        this.ticksTillPlay = compound.getInteger("TicksSincePlay");
        this.ticksTillMate = compound.getInteger("TicksSinceMate");
        this.ticksSlept = compound.getInteger("TicksSlept");
        this.rotationYaw = compound.getInteger("YawRotation");
        this.rotationYawHead = compound.getInteger("HeadRotation");
        String s = "";
        if (compound.hasKey("Owner", 8)) {
            s = compound.getString("Owner");
            this.setOwnerDisplayName(s);
        } else {
            this.setOwnerDisplayName(compound.getString("OwnerDisplayName"));
        }
    }

    public AxisAlignedBB getAttackBounds() {
        float size = this.getRenderSizeModifier() * 0.25F;
        return this.getEntityBoundingBox().grow(2.0F + size, 2.0F + size, 2.0F + size);
    }

    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        livingdata = super.onInitialSpawn(difficulty, livingdata);
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
        return livingdata;
    }

    public void setActualSize(float width, float height) {
        this.actualWidth = width;
        this.setSize(width, height);
    }

    @Override
    public boolean isAIDisabled() {
        return this.isSkeleton() || super.isAIDisabled();
    }

    public void doPlayBonus(int playBonus) {
        ticksTillPlay = this.rand.nextInt(600) + 600;
        this.setMood(this.getMood() + playBonus);
        Revival.NETWORK_WRAPPER.sendToAll(new MessageHappyParticles(this.getEntityId()));
    }

    public abstract void setSpawnValues();

    public OrderType getOrderType() {
        return this.currentOrder;
    }

    @Override
    public boolean isMovementBlocked() {
        return this.getHealth() <= 0.0F || isSitting() || isSleeping() || this.isSkeleton() || this.isActuallyWeak() || this.isBeingRidden();
    }

    public boolean isSleeping() {
        if (world.isRemote) {
            boolean isSleeping = this.dataManager.get(SLEEPING);
            this.isSleeping = isSleeping;
            return isSleeping;
        }

        return isSleeping;
    }

    public BlockPos getBlockToEat(int range) {
        BlockPos pos;

        for (int r = 1; r <= range; r++) {
            for (int ds = -r; ds <= r; ds++) {
                for (int dy = 4; dy > -5; dy--) {
                    int x = MathHelper.floor(this.posX + ds);
                    int y = MathHelper.floor(this.posY + dy);
                    int z = MathHelper.floor(this.posZ - r);
                    if (this.posY + dy >= 0 && this.posY + dy <= this.world.getHeight() && FoodMappings.INSTANCE.getBlockFoodAmount(this.world.getBlockState(new BlockPos(x, y, z)).getBlock(), type.diet) != 0) {
                        pos = new BlockPos(x, y, z);
                        return pos;
                    }

                    if (this.posY + dy >= 0 && this.posY + dy <= this.world.getHeight() && FoodMappings.INSTANCE.getBlockFoodAmount(this.world.getBlockState(new BlockPos(x, y, z)).getBlock(), type.diet) != 0) {
                        pos = new BlockPos(x, y, z);
                        return pos;
                    }
                }
            }

            for (int ds = -r + 1; ds <= r - 1; ds++) {
                for (int dy = 4; dy > -5; dy--) {
                    int x = MathHelper.floor(this.posX + ds);
                    int y = MathHelper.floor(this.posY + dy);
                    int z = MathHelper.floor(this.posZ - r);

                    if (this.posY + dy >= 0 && this.posY + dy <= this.world.getHeight() && FoodMappings.INSTANCE.getBlockFoodAmount(this.world.getBlockState(new BlockPos(x, y, z)).getBlock(), type.diet) != 0) {
                        pos = new BlockPos(x, y, z);
                        return pos;
                    }

                    if (this.posY + dy >= 0 && this.posY + dy <= this.world.getHeight() && FoodMappings.INSTANCE.getBlockFoodAmount(this.world.getBlockState(new BlockPos(x, y, z)).getBlock(), type.diet) != 0) {
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
    protected boolean canDespawn() {
        return false;
    }

    public TileEntityFeeder getNearestFeeder(int feederRange) {
        for (int dx = -2; dx != -(feederRange + 1); dx += (dx < 0) ? (dx * -2) : (-(2 * dx + 1))) {
            for (int dy = -5; dy < 4; dy++) {
                for (int dz = -2; dz != -(feederRange + 1); dz += (dz < 0) ? (dz * -2) : (-(2 * dz + 1))) {
                    if (this.posY + dy >= 0 && this.posY + dy <= this.world.getHeight()) {
                        TileEntity feeder = this.world.getTileEntity(new BlockPos(MathHelper.floor(this.posX + dx), MathHelper.floor(this.posY + dy), MathHelper.floor(this.posZ + dz)));

                        if (feeder instanceof TileEntityFeeder && !((TileEntityFeeder) feeder).isEmpty(type)) {
                            return (TileEntityFeeder) feeder;
                        }
                    }
                }
            }
        }

        return null;
    }

    public float getActualWidth() {
        return this.actualWidth * this.getAgeScale();
    }

    public boolean arePlantsNearby(int range) {
        for (int r = 1; r <= range; r++) {
            for (int ds = -r; ds <= r; ds++) {
                for (int dy = 4; dy > -5; dy--) {
                    int x = MathHelper.floor(this.posX + ds);
                    int y = MathHelper.floor(this.posY + dy);
                    int z = MathHelper.floor(this.posZ - r);
                    if (this.posY + dy >= 0 && this.posY + dy <= this.world.getHeight() && isPlantBlock(this.world.getBlockState(new BlockPos(x, y, z)))) {
                        return true;
                    }

                    if (this.posY + dy >= 0 && this.posY + dy <= this.world.getHeight() && isPlantBlock(this.world.getBlockState(new BlockPos(x, y, z)))) {
                        return true;
                    }
                }
            }
            for (int ds = -r + 1; ds <= r - 1; ds++) {
                for (int dy = 4; dy > -5; dy--) {
                    int x = MathHelper.floor(this.posX + ds);
                    int y = MathHelper.floor(this.posY + dy);
                    int z = MathHelper.floor(this.posZ - r);

                    if (this.posY + dy >= 0 && this.posY + dy <= this.world.getHeight() && isPlantBlock(this.world.getBlockState(new BlockPos(x, y, z)))) {
                        return true;
                    }

                    if (this.posY + dy >= 0 && this.posY + dy <= this.world.getHeight() && isPlantBlock(this.world.getBlockState(new BlockPos(x, y, z)))) {
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

    public int getNearestBubbleBlock(int range, int type) {
        for (int r = 1; r <= range; r++) {
            for (int ds = -r; ds <= r; ds++) {
                for (int dy = 4; dy > -5; dy--) {
                    int x = MathHelper.floor(this.posX + ds);
                    int y = MathHelper.floor(this.posY + dy);
                    int z = MathHelper.floor(this.posZ - r);
                    if (this.posY + dy >= 0 && this.posY + dy <= this.world.getHeight() && this.world.getBlockState(new BlockPos(x, y, z)).getBlock() == FABlockRegistry.BUBBLE_MACHINE && this.world.getRedstonePowerFromNeighbors(new BlockPos(x, y, z)) > 0) {
                        switch (type) {
                            case 0:
                                return x;
                            case 1:
                                return y;
                            case 2:
                                return z;
                        }
                    }

                    if (this.posY + dy >= 0 && this.posY + dy <= this.world.getHeight() && this.world.getBlockState(new BlockPos(x, y, z)).getBlock() == FABlockRegistry.BUBBLE_MACHINE && this.world.getRedstonePowerFromNeighbors(new BlockPos(x, y, z)) > 0) {
                        switch (type) {
                            case 0:
                                return x;
                            case 1:
                                return y;
                            case 2:
                                return z;
                        }
                    }
                }
            }
        }
        return 0;
    }

    public boolean isPlantBlock(IBlockState block) {
        return block.getMaterial() == Material.GRASS || block.getMaterial() == Material.PLANTS || block.getMaterial() == Material.LEAVES;
    }

    public boolean canSleep() {
        if (this.aiActivityType() == PrehistoricEntityTypeAI.Activity.DIURINAL) {
            return !this.isDaytime();
        } else if (this.aiActivityType() == PrehistoricEntityTypeAI.Activity.NOCTURNAL) {
            return this.isDaytime() && !this.world.canSeeSky(new BlockPos(MathHelper.floor(this.posX), MathHelper.floor(this.posY + 1), MathHelper.floor(this.posZ)));
        } else if (this.aiActivityType() == PrehistoricEntityTypeAI.Activity.BOTH) {
            return true;
        } else {
            return false;
        }
    }

    public boolean canWakeUp() {
        if (this.aiActivityType() == PrehistoricEntityTypeAI.Activity.DIURINAL) {
            return this.isDaytime();
        } else if (this.aiActivityType() == PrehistoricEntityTypeAI.Activity.NOCTURNAL) {
            return !this.isDaytime() || this.world.canSeeSky(new BlockPos(MathHelper.floor(this.posX), MathHelper.floor(this.posY + 1), MathHelper.floor(this.posZ)));
        } else {
            return false;
        }
    }

    public boolean isDaytime() {
        return this.world.isDaytime();
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.isSkeleton()) {
            this.motionX *= 0;
            this.motionY *= 0;
            this.motionZ *= 0;
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
        int blockX = MathHelper.floor(this.posX);
        int blockY = MathHelper.floor(this.getEntityBoundingBox().minY) - 1;
        int blockZ = MathHelper.floor(this.posZ);
        if (this.getBlockUnder() == FABlockRegistry.BUBBLE_MACHINE && this.world.getRedstonePowerFromNeighbors(new BlockPos(blockX, blockY, blockZ)) > 0 && this.ticksTillPlay == 0) {
            this.jump();
            for (int i = 0; i < 1; ++i) {
                double dd = this.getRNG().nextGaussian() * 0.02D;
                double dd1 = this.getRNG().nextGaussian() * 0.02D;
                double dd2 = this.getRNG().nextGaussian() * 0.02D;
                Revival.PROXY.spawnPacketHeartParticles(this.world, (float) (this.posX + (this.getRNG().nextFloat() * this.width * 2.0F) - this.width), (float) (this.posY + 0.5D + (this.getRNG().nextFloat() * this.height)), (float) (this.posZ + (this.getRNG().nextFloat() * this.width * 2.0F) - this.width), dd, dd1, dd2);
            }
            this.doPlayBonus(15);
        }
        if (Revival.CONFIG_OPTIONS.dinosaurBreeding && !world.isRemote && ticksTillMate == 0 && this.getGender() == 1 && this.getMood() > 50) {
            this.mate();
        }
        if (Revival.CONFIG_OPTIONS.healingDinos && !this.world.isRemote) {
            if (this.rand.nextInt(500) == 0 && this.deathTime == 0) {
                this.heal(1.0F);
            }
        }
        if (this.arePlantsNearby(16)) {
            boolean inital_mood_noplants = mood_noplants;
            this.mood_noplants = false;
            if (mood_noplants != inital_mood_noplants) {
                this.setMood(this.getMood() + 50);
            }
        } else if (!mood_noplants) {
            boolean inital_mood_noplants = mood_noplants;
            this.mood_noplants = true;
            if (mood_noplants != inital_mood_noplants) {
                this.setMood(this.getMood() - 50);
            }
        }
        if (!this.isThereNearbyTypes()) {
            boolean inital_mood_nospace = mood_nospace;
            this.mood_nospace = false;
            if (mood_nospace != inital_mood_nospace) {
                this.setMood(this.getMood() + 50);
            }
        } else if (!mood_nospace) {
            boolean inital_mood_nospace = mood_nospace;
            this.mood_nospace = true;
            if (mood_nospace != inital_mood_nospace) {
                this.setMood(this.getMood() - 50);
            }
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
        if (!world.isRemote && !this.isInWater() && !this.isBeingRidden() && !this.isSitting() && this.getRNG().nextInt(100) == 1 && !this.isRiding() && (this.getAnimation() == NO_ANIMATION || this.getAnimation() == SPEAK_ANIMATION) && !this.isSleeping()) {
            this.setSitting(true);
            ticksSitted = 0;
        }

        if (!world.isRemote && !this.isInWater() && (this.isSitting() && ticksSitted > 100 && this.getRNG().nextInt(100) == 1 || this.getAttackTarget() != null) && !this.isSleeping()) {
            this.setSitting(false);
            ticksSitted = 0;
        }
        if (!this.world.isRemote && this.wantsToSleep() && this.getRNG().nextInt(this.aiActivityType() == PrehistoricEntityTypeAI.Activity.BOTH ? 700 : 100) == 1 && !this.isSleeping()) {
            this.setSitting(false);
            this.setSleeping(true);
        }
        if (!this.world.isRemote && (!this.wantsToSleep() || !this.canSleep() || canWakeUp() && this.aiActivityType() != PrehistoricEntityTypeAI.Activity.BOTH)) {
            this.setSitting(false);
            this.setSleeping(false);
        }

        if (this.currentOrder == OrderType.STAY && !this.isSitting() && !this.isActuallyWeak()) {
            this.setSitting(true);
            this.setSleeping(false);
        }
        if (breaksBlocks && net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this)) {
            this.breakBlock(5);
        }
        if (this.doesFlock() && flockObj == null) {
            Flock nearbyFlock = this.getNearbyFlock();
            if (nearbyFlock != null) {
                nearbyFlock.flockMembers.add(this);
                flockObj = nearbyFlock;
            } else {
                flockObj = new Flock();
                flockObj.createFlock(this);
            }
        }
        if (this.flockObj != null) {
            if (this == flockObj.flockLeader) {
                this.flockObj.onUpdate();
            }
        }
        if (this.getAttackTarget() != null && this.getAttackTarget() instanceof EntityToyBase && (isPreyBlocked(this.getAttackTarget()) || this.ticksTillPlay > 0)) {
            this.setAttackTarget(null);
        }
    }

    public boolean wantsToSleep() {
        if (!world.isRemote && this.aiActivityType() == PrehistoricEntityTypeAI.Activity.BOTH && this.ticksSlept > 12000) {
            return false;
        }
        return !world.isRemote && this.getAttackTarget() == null && this.getRevengeTarget() == null && !this.isInWater() && !this.isBeingRidden() && !this.isActuallyWeak() && this.canSleep() && canSleepWhileHunting() && (this.getAnimation() == NO_ANIMATION || this.getAnimation() == SPEAK_ANIMATION) && this.getOrderType() != OrderType.FOLLOW;
    }

    @Override
    public void onDeath(DamageSource source) {
        if (this.flockObj != null) {
            this.flockObj.flockMembers.remove(this);
            this.flockObj.setNewLeader();
        }
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
        int blockX = MathHelper.floor(this.posX);
        int blockY = MathHelper.floor(this.getEntityBoundingBox().minY) - 1;
        int blockZ = MathHelper.floor(this.posZ);
        return this.world.getBlockState(new BlockPos(blockX, blockY, blockZ)).getBlock();
    }

    public EntityPrehistoric findFlockLeader(List<EntityPrehistoric> flock) {
        int index = new Random().nextInt(flock.size());
        return flock.get(index);
    }

    public EntityPlayer getRidingPlayer() {
        if (this.getControllingPassenger() instanceof EntityPlayer) {
            return (EntityPlayer) getControllingPassenger();
        } else {
            return null;
        }
    }

    @Override
    public void travel(float strafe, float vertical, float forward) {
        if (this.isSitting()) {
            super.travel(0, 0, 0);
            return;
        }
        if (this.isBeingRidden() && this.canBeSteered()) {
            EntityLivingBase controller = (EntityLivingBase) this.getControllingPassenger();
            if (controller != null) {
                if (this.getAttackTarget() != null) {
                    this.setAttackTarget(null);
                    this.getNavigator().clearPath();
                }
                strafe = controller.moveStrafing * 0.5F;
                forward = controller.moveForward;
                this.fallDistance = 0;
                this.isJumping = false;
                this.setAIMoveSpeed(onGround ? (float) this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue() : 2);
                super.travel(strafe, vertical, forward);
                return;
            }
        }
        super.travel(strafe, vertical, forward);
    }

    @Override
    @Nullable
    public Entity getControllingPassenger() {
        for (Entity passenger : this.getPassengers()) {
            if (passenger instanceof EntityPlayer && this.getAttackTarget() != passenger) {
                EntityPlayer player = (EntityPlayer) passenger;
                if (this.isTamed() && this.isOwner(player)) {
                    return player;
                }
            }
        }
        return null;
    }

    public void setRidingPlayer(EntityPlayer player) {
        player.rotationYaw = this.rotationYaw;
        player.rotationPitch = this.rotationPitch;
        player.startRiding(this);
    }

    @Override
    public void onUpdate() {
        super.onUpdate();
        //don't use the vanilla system
        if (this.getGrowingAge() < 0) {
            this.setGrowingAge(0);
        }
        if (world.isRemote) {
            this.setScaleForAge(true);
        }
        if (!this.isSkeleton()) {
            this.setAgeinTicks(this.getAgeInTicks() + 1);
            if (this.getAgeInTicks() % 24000 == 0) {
                this.updateAbilities();
            }
            if (this.getAgeInTicks() % 1200 == 0 && this.getHunger() > 0 && Revival.CONFIG_OPTIONS.starvingDinos) {
                this.setHunger(this.getHunger() - 1);
            }
            if (this.getHealth() > this.getMaxHealth() / 2 && this.getHunger() == 0 && this.getAgeInTicks() % 40 == 0) {
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
            if (this.aiClimbType() == PrehistoricEntityTypeAI.Climbing.ARTHROPOD && !this.wantsToSleep()) {
                this.setBesideClimbableBlock(this.collidedHorizontally);
            } else {
                this.setBesideClimbableBlock(false);
            }
        }

        Revival.PROXY.calculateChainBuffer(this);
        AnimationHandler.INSTANCE.updateAnimations(this);
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

    public abstract boolean doesFlock();

    @Override
    public boolean canAttackClass(Class clazz) {
        return this.getClass() != clazz && clazz != EntityDinosaurEgg.class;
    }

    public float getAgeScale() {
        float step = (this.maxSize - this.minSize) / ((this.getAdultAge() * 24000) + 1);
        if (this.getAgeInTicks() > this.getAdultAge() * 24000) {
            return this.minSize + ((step) * this.getAdultAge() * 24000);
        }
        return this.minSize + ((step * this.getAgeInTicks()));
    }

    @Override
    protected int getExperiencePoints(EntityPlayer par1EntityPlayer) {
        float base = 6 * this.getActualWidth() * (this.type.diet == Diet.HERBIVORE ? 1.0F : 2.0F)
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
            this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(Math.round(baseHealth + (healthStep * this.getAgeInDays())));
            this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(Math.round(baseDamage + (attackStep * this.getAgeInDays())));
            this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(baseSpeed + (speedStep * this.getAgeInDays()));
            this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(baseArmor + (armorStep * this.getAgeInDays()));
            if (this.developsResistance) {
                if (this.isTeen()) {
                    this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.5D);
                } else if (this.isAdult()) {
                    this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(2.0D);
                } else {
                    this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.0D);
                }
            }
        }
        this.heal((float) healthStep);
    }

    public void breakBlock(float hardness) {
        if (Revival.CONFIG_OPTIONS.dinoBlockBreaking) {
            if (!isSkeleton() && this.isAdult() && this.isHungry()) {
                for (int a = (int) Math.round(this.getEntityBoundingBox().minX) - 1; a <= (int) Math.round(this.getEntityBoundingBox().maxX) + 1; a++) {
                    for (int b = (int) Math.round(this.getEntityBoundingBox().minY) + 1; (b <= (int) Math.round(this.getEntityBoundingBox().maxY) + 2) && (b <= 127); b++) {
                        for (int c = (int) Math.round(this.getEntityBoundingBox().minZ) - 1; c <= (int) Math.round(this.getEntityBoundingBox().maxZ) + 1; c++) {
                            BlockPos pos = new BlockPos(a, b, c);
                            if (!world.isAirBlock(pos)) {
                                IBlockState state = world.getBlockState(pos);
                                Block block = state.getBlock();
                                if (!(block instanceof BlockBush) && !(block instanceof BlockLiquid) && state.getBlockHardness(world, new BlockPos(a, b, c)) < hardness && canBreak(state.getBlock()) || block == Blocks.WATERLILY) {
                                    this.motionX *= 0.6D;
                                    this.motionZ *= 0.6D;
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

    public static boolean canBreak(Block block) {
        return !(block instanceof IDinoUnbreakable) &&
                block != net.minecraft.init.Blocks.BARRIER &&
                block != net.minecraft.init.Blocks.OBSIDIAN &&
                block != net.minecraft.init.Blocks.END_STONE &&
                block != net.minecraft.init.Blocks.BEDROCK &&
                block != net.minecraft.init.Blocks.END_PORTAL &&
                block != net.minecraft.init.Blocks.END_PORTAL_FRAME &&
                block != net.minecraft.init.Blocks.COMMAND_BLOCK &&
                block != net.minecraft.init.Blocks.REPEATING_COMMAND_BLOCK &&
                block != net.minecraft.init.Blocks.CHAIN_COMMAND_BLOCK &&
                block != net.minecraft.init.Blocks.IRON_BARS &&
                block != net.minecraft.init.Blocks.END_GATEWAY
                || block == Blocks.WATERLILY;
    }


    @Override
    public void setScaleForAge(boolean child) {
        this.setScale(Math.min(this.getAgeScale() * 0.85F, 4F));
    }

    public Entity createEgg(EntityAgeable entity) {
        Entity baby = null;
        if (this.type.mobType == MobType.MAMMAL) {
            baby = this.type.invokeClass(this.world);
        }
        if (this.type.mobType == MobType.BIRD) {
            baby = new EntityItem(this.world, this.posX, this.posY, this.posZ, new ItemStack(this.type.birdEggItem));
        }
        if (this.type.mobType == MobType.DINOSAUR || this.type.mobType == MobType.DINOSAUR_AQUATIC) {
            if (Revival.CONFIG_OPTIONS.eggsLikeChickens || this.type.isVivariousAquatic()) {
                baby = new EntityItem(this.world, this.posX, this.posY, this.posZ, new ItemStack(this.type.eggItem));
            } else {
                baby = new EntityDinosaurEgg(this.world, this.type);
                ((EntityDinosaurEgg) baby).selfType = this.type;
            }
        }
        return baby;
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

    @Override
    public void onKillEntity(EntityLivingBase var1) {
        super.onKillEntity(var1);
        this.increaseHunger(FoodHelper.getMobFoodPoints(var1, this.type.diet));
        this.heal(FoodHelper.getMobFoodPoints(var1, this.type.diet) / 3);
        this.setMood(this.getMood() + 25);
    }

    public boolean isHungry() {
        return this.getHunger() < this.getMaxHunger() * 0.75F;
    }

    public boolean isDeadlyHungry() {
        return this.getHunger() < this.getMaxHunger() * 0.25F;
    }

    public void sendStatusMessage(SituationType var1) {
        if (this.getOwner() != null && this.getDistance(this.getOwner()) < 50.0F) {
            ITextComponent itextcomponent = new TextComponentString(this.getName());
            ((EntityPlayer) this.getOwner()).sendStatusMessage(new TextComponentTranslation(var1 == SituationType.Betrayed ? "prehistoric.betrayed" : "prehistoric.full", new Object[]{itextcomponent}), true);
        }
    }

    @Override
    public boolean isOnLadder() {
        if (this.aiMovingType() == PrehistoricEntityTypeAI.Moving.AQUATIC || this.aiMovingType() == PrehistoricEntityTypeAI.Moving.SEMIAQUATIC) {
            return false;
        } else {
            return this.aiClimbType() == PrehistoricEntityTypeAI.Climbing.ARTHROPOD && this.isBesideClimbableBlock();
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

    public void setSleeping(boolean sleeping) {
        this.dataManager.set(SLEEPING, sleeping);
        if (!world.isRemote) {
            this.isSleeping = sleeping;
        }
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

    @Override
    public void setSitting(boolean sitting) {
        this.dataManager.set(SITTING, sitting);
        if (!world.isRemote) {
            this.isSitting = sitting;
        }
    }

    @Override
    public boolean shouldDismountInWater(Entity rider) {
        return false;
    }

    @Override
    public boolean attackEntityFrom(DamageSource dmg, float i) {
        if (i > 0 && this.isSkeleton()) {
            if (dmg == DamageSource.IN_WALL) {
                return false;
            } else {
                this.world.playSound(null, this.getPosition(), SoundEvents.ENTITY_SKELETON_HURT, SoundCategory.NEUTRAL, this.getSoundVolume(), this.getSoundPitch());
                if (!world.isRemote) {
                    if (this.type.timePeriod == TimePeriod.CENOZOIC) {
                        this.dropItem(FAItemRegistry.TAR_FOSSIL, 1);
                    } else {
                        this.dropItem(FAItemRegistry.BIOFOSSIL, 1);
                    }
                    this.entityDropItem(new ItemStack(Items.BONE, Math.min(this.getAgeInDays(), this.getAdultAge())), 1);
                }
                if (dmg.getTrueSource() != null && dmg.getTrueSource() instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) dmg.getTrueSource();
                    FossilsPlayerProperties properties = EntityPropertiesHandler.INSTANCE.getProperties(player, FossilsPlayerProperties.class);
                    if (properties != null) {
                        properties.killedBiofossilCooldown = 5;
                    }
                }

                this.setDead();
                return true;
            }
        }
        if (dmg == DamageSource.IN_WALL && this.aiClimbType() == PrehistoricEntityTypeAI.Climbing.ARTHROPOD) {
            return false;
        }
        if (this.getLastAttackedEntity() instanceof EntityPlayer) {
            if (this.getOwner() == this.getLastAttackedEntity()) {
                this.setTamed(false);
                this.setMood(this.getMood() - 15);
                this.sendStatusMessage(SituationType.Betrayed);
            }
        }

        if (i > 0) {
            this.setSitting(false);
            this.setSleeping(false);
        }
        if (dmg.getTrueSource() != null) {
            this.setMood(this.getMood() - 5);
        }
        if (this.getHurtSound(DamageSource.GENERIC) != null) {
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
    public void fall(float distance, float damageMultiplier) {
        if (this.aiClimbType() == PrehistoricEntityTypeAI.Climbing.ARTHROPOD || this.aiMovingType() == PrehistoricEntityTypeAI.Moving.WALKANDGLIDE || this.aiMovingType() == PrehistoricEntityTypeAI.Moving.FLIGHT) {
        } else {
            super.fall(distance, damageMultiplier);
        }
    }

    @Override
    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        if (this.isSkeleton()) {
            if (itemstack.isEmpty()) {
                if (player.isSneaking()) {
                    this.nudgeEntity(player);
                } else {
                    this.rotationYawHead = -player.rotationYaw;
                    this.renderYawOffset = -player.rotationYaw;
                }
            } else {
                if (itemstack.getItem() == Items.BONE && this.getAgeInDays() < this.getAdultAge()) {
                    this.world.playSound(null, this.getPosition(), SoundEvents.ENTITY_SKELETON_AMBIENT, SoundCategory.NEUTRAL, 0.8F, 1);
                    this.setAgeInDays(this.getAgeInDays() + 1);
                    if (!player.capabilities.isCreativeMode) {
                        itemstack.shrink(1);
                    }
                    return true;
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
                        setAttackTarget(null);
                        this.setTamed(true);
                        this.setOwnerId(player.getUniqueID());
                        this.world.setEntityState(this, (byte) 35);
                        itemstack.shrink(1);
                        return true;
                    }
                }

                if (itemstack.getItem() == FAItemRegistry.CHICKEN_ESSENCE && this.aiTameType() != PrehistoricEntityTypeAI.Taming.GEM && this.aiTameType() != PrehistoricEntityTypeAI.Taming.BLUEGEM && !player.world.isRemote) {
                    if (this.getAgeInDays() < this.getAdultAge() && this.getHunger() > 0) {
                        if (this.getHunger() > 0) {
                            itemstack.shrink(1);
                            if (!player.capabilities.isCreativeMode) {
                                player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE, 1));
                            }
                            Revival.NETWORK_WRAPPER.sendToAll(new MessageFoodParticles(getEntityId(), Item.getIdFromItem(FAItemRegistry.CHICKEN_ESSENCE)));
                            this.grow(1);
                            this.setHunger(1 + (new Random()).nextInt(this.getHunger()));
                            return true;
                        }
                    }

                    if (!this.world.isRemote) {
                        player.sendStatusMessage(new TextComponentTranslation("prehistoric.essencefail"), true);
                    }

                    return false;
                }

                if (FoodMappings.INSTANCE.getItemFoodAmount(itemstack, this.type.diet) != 0) {
                    if (!player.world.isRemote) {
                        if (this.getHunger() < this.getMaxHunger() || this.getHealth() < this.getMaxHealth() && Revival.CONFIG_OPTIONS.healingDinos || !this.isTamed() && this.aiTameType() == PrehistoricEntityTypeAI.Taming.FEEDING) {
                            this.setHunger(this.getHunger() + FoodMappings.INSTANCE.getItemFoodAmount(itemstack, this.type.diet));
                            if (!world.isRemote) {
                                this.eatItem(itemstack);
                            }
                            if (Revival.CONFIG_OPTIONS.healingDinos) {
                                this.heal(3);
                            }
                            if (this.getHunger() >= this.getMaxHunger()) {
                                if (this.isTamed()) {
                                    this.sendStatusMessage(SituationType.Full);
                                }
                            }
                            itemstack.shrink(1);
                            if (this.aiTameType() == PrehistoricEntityTypeAI.Taming.FEEDING) {
                                if (!this.isTamed() && this.type.isTameable() && (new Random()).nextInt(10) == 1) {
                                    this.setTamed(true);
                                    this.setOwnerId(player.getUniqueID());
                                    this.world.setEntityState(this, (byte) 35);
                                }
                            }

                            return true;
                        } else {
                            return this.ItemInMouth.isEmpty();
                        }
                    }

                    return false;
                } else {
                    if (itemstack.getItem() == Items.LEAD && this.isTamed()) {
                        if (this.isOwner(player)) {
                            this.setLeashHolder(player, true);
                            itemstack.shrink(1);
                            return true;
                        }
                    }
                    if (FMLCommonHandler.instance().getSide().isClient() && itemstack.getItem() == FAItemRegistry.DINOPEDIA) {
                        this.setPedia();
                        player.openGui(Revival.INSTANCE, 6, this.world, (int) this.posX, (int) this.posY, (int) this.posZ);
                        return true;
                    }

                    if (itemstack.getItem() == FAItemRegistry.WHIP && this.aiTameType() != PrehistoricEntityTypeAI.Taming.NONE && this.isAdult() && !this.world.isRemote) {
                        if (this.isTamed() && isOwner(player) && this.canBeRidden()) {
                            if (this.getRidingPlayer() == null) {
                                Revival.NETWORK_WRAPPER.sendToAll(new MessageFoodParticles(getEntityId(), FABlockRegistry.VOLCANIC_ROCK));
                                this.setOrder(OrderType.WANDER);
                                setRidingPlayer(player);
                                this.setSitting(false);
                                this.setSleeping(false);
                            } else if (this.getRidingPlayer() == player) {
                                this.setSprinting(true);
                                Revival.NETWORK_WRAPPER.sendToAll(new MessageFoodParticles(getEntityId(), FABlockRegistry.VOLCANIC_ROCK));
                                this.setMood(this.getMood() - 5);
                            }
                        } else if (!this.isTamed() && this.aiTameType() != PrehistoricEntityTypeAI.Taming.BLUEGEM && this.aiTameType() != PrehistoricEntityTypeAI.Taming.GEM) {
                            this.setMood(this.getMood() - 5);
                            Revival.NETWORK_WRAPPER.sendToAll(new MessageFoodParticles(getEntityId(), FABlockRegistry.VOLCANIC_ROCK));
                            if (getRNG().nextInt(5) == 0) {
                                ITextComponent itextcomponent = new TextComponentString(this.getName());
                                player.sendStatusMessage(new TextComponentTranslation("prehistoric.autotame", new Object[]{itextcomponent}), true);
                                this.setMood(this.getMood() - 25);
                                this.setTamed(true);
                                Revival.NETWORK_WRAPPER.sendToAll(new MessageFoodParticles(getEntityId(), Item.getIdFromItem(Items.GOLD_INGOT)));
                                this.setOwnerId(player.getUniqueID());
                            }
                        }
                        this.setSitting(false);
                        // this.setOrder(OrderType.WANDER);

                        // this.currentOrder = OrderType.FreeMove;
                        // setRidingPlayer(player);
                    }
                    if (this.getOrderItem() != null && itemstack.getItem() == this.getOrderItem() && this.isTamed() && this.isOwner(player) && !player.isRiding()) {
                        if (!this.world.isRemote) {
                            this.isJumping = false;
                            this.getNavigator().clearPath();
                            this.currentOrder = OrderType.values()[(this.currentOrder.ordinal() + 1) % 3];
                            this.sendOrderMessage(this.currentOrder);
                        }
                        return true;
                    }
                }
            }
        }
        return super.processInteract(player, hand);
    }

    public abstract Item getOrderItem();

    private void triggerTamingAcheivement(EntityPlayer player) {
        // player.triggerAchievement(FossilAchievementHandler.theKing);

    }

    public void grow(int ageInDays) {
        this.setAgeInDays(this.getAgeInDays() + ageInDays);
        this.setScaleForAge(false);
        for (int i = 0; i < this.getAgeScale() * 4; i++) {
            double motionX = getRNG().nextGaussian() * 0.07D;
            double motionY = getRNG().nextGaussian() * 0.07D;
            double motionZ = getRNG().nextGaussian() * 0.07D;
            float f = (float) (getRNG().nextFloat() * (this.getEntityBoundingBox().maxX - this.getEntityBoundingBox().minX) + this.getEntityBoundingBox().minX);
            float f1 = (float) (getRNG().nextFloat() * (this.getEntityBoundingBox().maxY - this.getEntityBoundingBox().minY) + this.getEntityBoundingBox().minY);
            float f2 = (float) (getRNG().nextFloat() * (this.getEntityBoundingBox().maxZ - this.getEntityBoundingBox().minZ) + this.getEntityBoundingBox().minZ);
            if (world.isRemote) {
                this.world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, f, f1, f2, motionX, motionY, motionZ, new int[]{});
            }
        }
        this.updateAbilities();
    }

    public boolean isWeak() {
        return (this.getHealth() < 8) && (this.getAgeInDays() >= this.getAdultAge()) && !this.isTamed();
    }

    protected void setPedia() {
        Revival.PEDIA_OBJECT = this;
    }

    private void sendOrderMessage(OrderType var1) {
        String s = "dino.order." + var1.name().toLowerCase();
        ITextComponent itextcomponent = new TextComponentString(this.getName());
        if (this.getOwner() instanceof EntityPlayer) {
            ((EntityPlayer) this.getOwner()).sendStatusMessage(new TextComponentTranslation(s, new Object[]{itextcomponent}), true);
        }
    }

    public void nudgeEntity(EntityPlayer player) {
        this.setPositionAndUpdate(this.posX + (player.posX - this.posX) * 0.01F, this.posY, this.posZ + (player.posZ - this.posZ) * 0.01F);
    }

    public ArrayList<Class<? extends Entity>> preyList() {
        return new ArrayList<>();
    }

    public ArrayList<Class<? extends Entity>> preyBlacklist() {
        return new ArrayList<>();
    }

    public void playerRoar(EntityPlayer player) {
    }

    public void playerAttack(EntityPlayer player) {
    }

    public void playerJump(EntityPlayer player) {
    }

    public void playerFlyUp(EntityPlayer player) {
    }

    public void playerFlyDown(EntityPlayer player) {
    }

    public String getTexture() {
        if (this.isSkeleton()) {
            return "fossil:textures/model/" + type.toString().toLowerCase() + "_0/" + type.toString().toLowerCase() + "_skeleton.png";
        }
        if (this.hasBabyTexture) {
            String toggle = this.hasFeatherToggle ? this.featherToggle ? "feathered/" : "scaled/" : "";
            boolean isBaby = this.isChild() && this.hasBabyTexture;
            String gender = this.hasTeenTexture ? this.isTeen() ? "_teen" : isBaby ? "_baby" : this.getGender() == 0 ? "_female" : "_male" : this.isChild() ? "_baby" : this.getGender() == 0 ? "_female" : "_male";
            String sleeping = !this.isSleeping() ? this.isActuallyWeak() ? "_sleeping" : "" : "_sleeping";
            String toggleList = this.hasFeatherToggle ? this.featherToggle ? "_feathered" : "_scaled" : "";
            return "fossil:textures/model/" + type.toString().toLowerCase() + "_0/" + toggle + type.toString().toLowerCase() + gender + toggleList + sleeping + ".png";
        } else {
            String toggle = this.hasFeatherToggle ? this.featherToggle ? "feathered/" : "scaled/" : "";
            String gender = this.getGender() == 0 ? "_female" : "_male";
            String sleeping = !this.isSleeping() ? this.isActuallyWeak() ? "_sleeping" : "" : "_sleeping";
            String toggleList = this.hasFeatherToggle ? this.featherToggle ? "_feathered" : "_scaled" : "";
            return "fossil:textures/model/" + type.toString().toLowerCase() + "_0/" + toggle + type.toString().toLowerCase() + gender + toggleList + sleeping + ".png";
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
    public void playLivingSound() {
        if (!this.isSleeping() && !this.isSkeleton()) {
            super.playLivingSound();
            if (this.getAnimation() != null) {
                if (this.getAnimation() == NO_ANIMATION && !world.isRemote) {
                    this.setAnimation(SPEAK_ANIMATION);
                }
            }
        }
    }

    public void knockbackEntity(Entity knockBackMob, float knockbackStrength, float knockbackStrengthUp) {
        if (!(knockBackMob instanceof EntityToyBase) && knockBackMob instanceof EntityLivingBase) {
            double resistance = ((EntityLivingBase) knockBackMob).getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).getAttributeValue();
            double reversed = 1 - resistance;
            knockBackMob.motionY += 0.4000000059604645D * reversed + 0.1D;
            if (resistance < 1) {
                knockBackMob(knockBackMob, 0.25D, 0.2D, 0.25D);
            }
        }
    }

    @Override
    public void knockBack(Entity entity, float f, double x, double z) {
        if (entity instanceof EntityPrehistoric) {
            if (this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).getAttributeValue() <= 0 && this.onGround) {
                this.velocityChanged = false;
            }
        } else {
            super.knockBack(entity, f, x, z);
        }
    }

    public boolean canDinoHunt(Entity target, boolean hunger) {
        if (target instanceof EntityToyBase) {
            return true;
        }
        boolean isAnotherDino = target instanceof EntityPrehistoric;
        if (this.type.diet != Diet.HERBIVORE && this.type.diet != Diet.NONE && canAttackClass(target.getClass())) {
            if (isAnotherDino ? this.getActualWidth() * getTargetScale() >= ((EntityPrehistoric) target).getActualWidth() : this.getActualWidth() * getTargetScale() >= target.width) {
                if (hunger) {
                    return isHungry() || target instanceof EntityToyBase && this.ticksTillPlay == 0;
                } else {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * How much larger prey can this mob hunt. Ex. 1.5F = 150% larger prey.
     */
    public float getTargetScale() {
        return 1.0F;
    }

    public boolean isMad() {
        return this.getMoodFace() == PrehistoricMoodType.SAD;
    }

    public Flock getNearbyFlock() {
        EntityAINearestAttackableTarget.Sorter theNearestAttackableTargetSorter = new EntityAINearestAttackableTarget.Sorter(this);
        double d0 = 64;
        List<EntityPrehistoric> list = world.getEntitiesWithinAABB(EntityPrehistoric.class, this.getEntityBoundingBox().expand(d0, 4.0D, d0), null);
        list.sort(theNearestAttackableTargetSorter);
        if (!list.isEmpty()) {
            for (EntityPrehistoric mob : list) {
                if (mob != this && mob.type == this.type && mob.flockObj != null && mob.flockObj.flockLeader == mob) {
                    return mob.flockObj;
                }
            }
        }
        return null;
    }

    public void mate() {
        EntityAINearestAttackableTarget.Sorter theNearestAttackableTargetSorter = new EntityAINearestAttackableTarget.Sorter(this);
        double d0 = 64;
        List<Entity> list = world.getEntitiesInAABBexcluding(this, this.getEntityBoundingBox().expand(d0, 4.0D, d0), PREHISTORIC_PREDICATE);
        list.sort(theNearestAttackableTargetSorter);
        List<EntityPrehistoric> listOfFemales = new ArrayList<>();
        if (!list.isEmpty()) {
            for (Entity e : list) {
                EntityPrehistoric mob = (EntityPrehistoric)e;
                if (!mob.isEntityEqual(this) && mob.type == this.type && mob.isAdult() && mob.getGender() == 0 && mob.ticksTillMate == 0) {
                    listOfFemales.add(mob);
                }
            }
        }
        if (!listOfFemales.isEmpty() && this.ticksTillMate == 0) {
            EntityPrehistoric prehistoric = listOfFemales.get(0);
            if (prehistoric.ticksTillMate == 0) {
                this.getNavigator().tryMoveToEntityLiving(prehistoric, 1);
                double distance = (double) (this.width * 8.0F * this.width * 8.0F + prehistoric.width);
                if (this.getDistanceSq(prehistoric.posX, prehistoric.getEntityBoundingBox().minY, prehistoric.posZ) <= distance && prehistoric.onGround && this.onGround && this.isAdult() && prehistoric.isAdult()) {
                    prehistoric.procreate(this);
                    this.ticksTillMate = this.rand.nextInt(6000) + 6000;
                    prehistoric.ticksTillMate = this.rand.nextInt(12000) + 24000;
                }
            }
        }
    }

    protected PathNavigate createNavigator(World worldIn) {
        return this.aiClimbType() == PrehistoricEntityTypeAI.Climbing.ARTHROPOD ? new PathNavigateClimber(this, worldIn) : super.createNavigator(worldIn);
    }


    public abstract boolean canBeRidden();

    @Override
    public boolean canBeSteered() {
        return canBeRidden() && (this.getRidingPlayer() != null && this.isOwner(this.getRidingPlayer()));
    }

    public void procreate(EntityPrehistoric mob) {
        for (int i = 0; i < 7; ++i) {
            double dd = this.rand.nextGaussian() * 0.02D;
            double dd1 = this.rand.nextGaussian() * 0.02D;
            double dd2 = this.rand.nextGaussian() * 0.02D;
            Revival.PROXY.spawnPacketHeartParticles(this.world, (float) (this.posX + (this.rand.nextFloat() * this.width * 2.0F) - this.width), (float) (this.posY + 0.5D + (this.rand.nextFloat() * this.height)), (float) (this.posZ + (this.rand.nextFloat() * this.width * 2.0F) - this.width), dd, dd1, dd2);
            Revival.PROXY.spawnPacketHeartParticles(mob.world, (float) (mob.posX + (mob.rand.nextFloat() * mob.width * 2.0F) - mob.width), (float) (mob.posY + 0.5D + (mob.rand.nextFloat() * mob.height)), (float) (mob.posZ + (mob.rand.nextFloat() * mob.width * 2.0F) - mob.width), dd, dd1, dd2);
        }
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());
        if (this.rand.nextInt(100) == 0 || calendar.get(2) + 1 == 4 && calendar.get(5) == 1) {
            this.playSound(FASoundRegistry.MUSIC_MATING, 1, 1);
        }
        Entity hatchling = this.createEgg(mob);
        if (hatchling != null && !world.isRemote) {
            this.setAttackTarget(null);
            mob.setAttackTarget(null);
            hatchling.setPositionAndRotation(mob.posX, mob.posY + 1, mob.posZ, mob.rotationYaw, 0);
            if (hatchling instanceof EntityDinosaurEgg) {
                Revival.NETWORK_WRAPPER.sendToAll(new MessageUpdateEgg(hatchling.getEntityId(), this.type.ordinal()));
            } else {
                if (hatchling instanceof EntityPrehistoric) {
                    ((EntityPrehistoric) hatchling).onInitialSpawn(null, null);
                    ((EntityPrehistoric) hatchling).grow(1);
                    ((EntityPrehistoric) hatchling).setHealth((float) this.baseHealth);
                }
            }
            this.world.spawnEntity(hatchling);
        }
    }

    public boolean isThereNearbyTypes() {
        Entity targetEntity;
        EntityAINearestAttackableTarget.Sorter theNearestAttackableTargetSorter = new EntityAINearestAttackableTarget.Sorter(this);
        double d0 = 64;
        List<EntityPrehistoric> list = world.getEntitiesWithinAABB(EntityPrehistoric.class, this.getEntityBoundingBox().expand(d0, 4.0D, d0), null);
        list.sort(theNearestAttackableTargetSorter);

        if (list.isEmpty() || this.doesFlock()) {
            return false;
        } else {
            List<EntityPrehistoric> listOfType = new ArrayList<>();
            for (EntityPrehistoric mob : list) {
                if (mob != this && mob.type == this.type && mob.isAdult()) {
                    listOfType.add(mob);
                }
            }
            return listOfType.size() > this.nearByMobsAllowed;
        }
    }

    public void doFoodEffect(Item item) {
        this.world.playSound(null, this.getPosition(), SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.NEUTRAL, this.getSoundVolume(), this.getSoundPitch());
        if (item != null) {
            if (item instanceof ItemBlock) {
                spawnItemParticle(item, true);
            } else {
                spawnItemParticle(item, false);
            }
        }
    }

    public void doFoodEffect() {
        this.world.playSound(null, this.getPosition(), SoundEvents.ENTITY_GENERIC_EAT, SoundCategory.NEUTRAL, this.getSoundVolume(), this.getSoundPitch());
        switch (this.type.diet) {
            case HERBIVORE:
                spawnItemParticle(Items.WHEAT_SEEDS, false);
                break;
            case OMNIVORE:
                spawnItemParticle(Items.BREAD, false);
                break;
            case PISCIVORE:
                spawnItemParticle(Items.FISH, false);
                break;
            default:
                spawnItemParticle(Items.BEEF, false);
                break;
        }
    }

    public void spawnItemParticle(Item item, boolean itemBlock) {
        if (!world.isRemote) {
            double motionX = rand.nextGaussian() * 0.07D;
            double motionY = rand.nextGaussian() * 0.07D;
            double motionZ = rand.nextGaussian() * 0.07D;
            float f = (float) (getRNG().nextFloat() * (this.getEntityBoundingBox().maxX - this.getEntityBoundingBox().minX) + this.getEntityBoundingBox().minX);
            float f1 = (float) (getRNG().nextFloat() * (this.getEntityBoundingBox().maxY - this.getEntityBoundingBox().minY) + this.getEntityBoundingBox().minY);
            float f2 = (float) (getRNG().nextFloat() * (this.getEntityBoundingBox().maxZ - this.getEntityBoundingBox().minZ) + this.getEntityBoundingBox().minZ);
            if (itemBlock && item instanceof ItemBlock) {
                Revival.NETWORK_WRAPPER.sendToAll(new MessageFoodParticles(this.getEntityId(), Block.getIdFromBlock(((ItemBlock) item).getBlock())));
            } else {
                Revival.NETWORK_WRAPPER.sendToAll(new MessageFoodParticles(this.getEntityId(), Item.getIdFromItem(item)));
            }
        }
    }

    public boolean isInWaterMaterial() {
        double d0 = this.posY;
        int i = MathHelper.floor(this.posX);
        int j = MathHelper.floor((float) MathHelper.floor(d0));
        int k = MathHelper.floor(this.posZ);
        IBlockState blockState = this.world.getBlockState(new BlockPos(i, j, k));
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
            if (FoodMappings.INSTANCE.getItemFoodAmount(stack, type.diet) != 0) {
                this.setMood(this.getMood() + 5);
                doFoodEffect(stack.getItem());
                Revival.NETWORK_WRAPPER.sendToAll(new MessageFoodParticles(getEntityId(), Item.getIdFromItem(stack.getItem())));
                this.setHunger(this.getHunger() + FoodMappings.INSTANCE.getItemFoodAmount(stack, type.diet));
                stack.shrink(1);
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
        if (width <= entity.width) {
            if (entity instanceof EntityPrehistoric) {
                EntityPrehistoric mob = (EntityPrehistoric) entity;
                return mob.type.diet != Diet.HERBIVORE;
            } else {
                if (entity instanceof EntityPlayer) {
                    EntityPlayer player = (EntityPlayer) entity;
                    return !this.isOwner(player);
                }
                return true;
            }
        }
        return false;
    }

   /* @Override
    protected void dropFewItems(boolean bool, int rand) {
        int j = this.rand.nextInt(3) + this.rand.nextInt(1 + rand);
        if (this.type.mobType == MobType.BIRD || this.type.mobType == MobType.TERRORBIRD) {
            for (int k = 0; k < j; ++k) {
                this.dropItem(Items.FEATHER, 1 + this.rand.nextInt(3));
            }
        }
        if (this.isBurning() && this.type.cookedFoodItem != null) {
            this.dropItem(this.type.cookedFoodItem, Math.min(this.getAgeInDays(), this.getAdultAge()));
        } else if (this.type.foodItem != null) {
            this.dropItem(this.type.foodItem, Math.min(this.getAgeInDays(), this.getAdultAge()));
        }
        if (DinosaurBoneType.get(this.type) != null) {
            this.entityDropItem(new ItemStack(FAItemRegistry.SKULL, this.rand.nextInt(1), DinosaurBoneType.get(this.type).ordinal()), 0);
            this.entityDropItem(new ItemStack(FAItemRegistry.ARM_BONE, this.rand.nextInt(2), DinosaurBoneType.get(this.type).ordinal()), 0);
            this.entityDropItem(new ItemStack(FAItemRegistry.RIBCAGE, this.rand.nextInt(1), DinosaurBoneType.get(this.type).ordinal()), 0);
            this.entityDropItem(new ItemStack(FAItemRegistry.VERTEBRAE, this.rand.nextInt(5), DinosaurBoneType.get(this.type).ordinal()), 0);
            this.entityDropItem(new ItemStack(FAItemRegistry.FOOT, this.rand.nextInt(2), DinosaurBoneType.get(this.type).ordinal()), 0);
            this.entityDropItem(new ItemStack(FAItemRegistry.UNIQUE_ITEM, this.rand.nextInt(2), DinosaurBoneType.get(this.type).ordinal()), 0);
        }
    }*/


    @Nullable
    protected ResourceLocation getLootTable() {
        return PrehistoricEntityType.DINOSAUR_LOOT;
    }


    @Override
    public double getMountedYOffset() {
        return 0;
    }

    @Override
    public void updatePassenger(Entity passenger) {
        super.updatePassenger(passenger);
        if (this.isPassenger(passenger)) {
            renderYawOffset = rotationYaw;
            this.rotationYaw = passenger.rotationYaw;
        }
        if (this.getRidingPlayer() != null && this.getRidingPlayer() instanceof EntityPlayer && this.isOwner(this.getRidingPlayer()) && this.getAttackTarget() != this.getRidingPlayer()) {
            rotationYaw = renderYawOffset;
            rotationYaw = this.getRidingPlayer().rotationYaw;
            rotationYawHead = this.getRidingPlayer().rotationYaw;
            float radius = ridingXZ * (0.7F * getAgeScale()) * -3;
            float angle = (0.01745329251F * this.renderYawOffset);
            double extraX = (double) (radius * MathHelper.sin((float) (Math.PI + angle)));
            double extraZ = (double) (radius * MathHelper.cos(angle));
            double extraY = ridingY * (getAgeScale());
            float spinosaurusAddition = 0;
            if (this instanceof EntitySpinosaurus) {
                spinosaurusAddition = -(((EntitySpinosaurus) this).swimProgress * 0.1F);
            }
            this.getRidingPlayer().setPosition(this.posX + extraX, this.posY + extraY + spinosaurusAddition - 1.75F, this.posZ + extraZ);
        }
        if (passenger instanceof EntityVelociraptor || passenger instanceof EntityDeinonychus) {
            double extraY = ridingY * (getAgeScale()) - 1F;
            passenger.setPosition(this.posX, this.posY + extraY, this.posZ);
        }
    }

    @Override
    public EntityAgeable createChild(EntityAgeable entity) {
        Entity baby = this.type.invokeClass(this.world);
        if (entity instanceof EntityPrehistoric) {
            EntityPrehistoric prehistoric = (EntityPrehistoric) baby;
            prehistoric.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(prehistoric)), null);
            prehistoric.setAgeInDays(0);
            prehistoric.grow(0);
            prehistoric.updateAbilities();
            prehistoric.setNoAI(false);
            return ((EntityPrehistoric) baby);
        }
        return null;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return null;//"fossil:" + this.type.name().toLowerCase() + "_living";
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return null; //"fossil:" + this.type.name().toLowerCase() + "_hurt";
    }

    @Override
    protected SoundEvent getDeathSound() {
        return null; //"fossil:" + this.type.name().toLowerCase() + "_death";
    }

    public boolean isAquatic() {
        return this instanceof EntityPrehistoricSwimming;
    }

    public void onWhipRightClick() {

    }

    public boolean canReachPrey() {
        return this.getAttackTarget() != null && getAttackBounds().intersects(this.getAttackTarget().getEntityBoundingBox()) && !isPreyBlocked(this.getAttackTarget());
    }

    public boolean isPreyBlocked(Entity prey) {
        RayTraceResult rayTrace = world.rayTraceBlocks(this.getPositionVector(), prey.getPositionVector(), false);
        if (rayTrace != null && rayTrace.hitVec != null) {
            BlockPos sidePos = rayTrace.getBlockPos();
            BlockPos pos = new BlockPos(rayTrace.hitVec);
            if (!world.isAirBlock(pos) || !world.isAirBlock(sidePos)) {
                return true;
            } else {
                return rayTrace.typeOfHit != RayTraceResult.Type.MISS;
            }
        }
        return false;
    }

    public boolean rayTraceFeeder(BlockPos position, boolean leaves) {
        RayTraceResult rayTrace = world.rayTraceBlocks(this.getPositionVector(), new Vec3d(position.getX() + 0.5, position.getY() + 0.5, position.getZ() + 0.5), false);
        if (rayTrace != null && rayTrace.hitVec != null) {
            BlockPos sidePos = rayTrace.getBlockPos();
            BlockPos pos = new BlockPos(rayTrace.hitVec);
            if (isFeeder(pos, leaves) || isFeeder(sidePos, leaves)) {
                return true;
            } else {
                return rayTrace.typeOfHit == RayTraceResult.Type.MISS;
            }
        }
        return true;
    }

    private boolean isFeeder(BlockPos pos, boolean leaves) {
        if (leaves) {
            IBlockState state = world.getBlockState(pos);
            return FoodMappings.INSTANCE.getBlockFoodAmount(state.getBlock(), this.type.diet) > 0;
        } else {
            IBlockState state = world.getBlockState(pos);
            TileEntity entity = this.world.getTileEntity(pos);
            return entity instanceof TileEntityFeeder;
        }
    }

    public boolean shouldFollowFlock() {
        return !this.isSleeping() && !this.isSitting() && this.getOrderType() != OrderType.FOLLOW;
    }

    public float getDeathRotation() {
        return 90.0F;
    }

    public boolean shouldWanderInFlock() {
        return shouldFollowFlock() && this.getAttackTarget() == null;
    }

    protected float getSoundVolume() {
        return this.isChild() ? super.getSoundVolume() * 0.75F : 1.0F;
    }

    protected void doAttackKnockback(float strength) {
        if (this.getAttackTarget() != null) {
            if (this.getAttackTarget().getRidingEntity() != null) {
                if (this.getAttackTarget().getRidingEntity() == this) {
                    this.getAttackTarget().dismountRidingEntity();
                }
            }
            knockbackEntity(this.getAttackTarget(), strength, 0.1F);
            this.getAttackTarget().isAirBorne = false;
        }
    }

    public void doAttack() {
        IAttributeInstance iattributeinstance = this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        if (getAttackTarget() != null) {
            this.getAttackTarget().attackEntityFrom(DamageSource.causeMobDamage(this), (float) iattributeinstance.getAttributeValue());
        }
    }

    @SideOnly(Side.CLIENT)
    public void handleStatusUpdate(byte id) {
        if (id == 45) {
            spawnItemParticle(Items.REEDS);
            spawnItemParticle(Items.REEDS);
            spawnItemParticle(Items.REEDS);
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
        float f = (float) (rand.nextFloat() * (this.getEntityBoundingBox().maxX - this.getEntityBoundingBox().minX) + this.getEntityBoundingBox().minX);
        float f1 = (float) (rand.nextFloat() * (this.getEntityBoundingBox().maxY - this.getEntityBoundingBox().minY) + this.getEntityBoundingBox().minY);
        float f2 = (float) (rand.nextFloat() * (this.getEntityBoundingBox().maxZ - this.getEntityBoundingBox().minZ) + this.getEntityBoundingBox().minZ);
        this.world.spawnParticle(EnumParticleTypes.ITEM_CRACK, f, f1, f2, motionX, motionY, motionZ, Item.getIdFromItem(item));
    }

}
