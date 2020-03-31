package fossilsarcheology.server.entity.prehistoric;

import com.google.common.base.Predicate;
import fossilsarcheology.client.sound.FASoundRegistry;
import fossilsarcheology.server.entity.ai.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.MobEffects;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IShearable;

import javax.annotation.Nullable;
import java.util.ArrayList;

public class EntityMammoth extends EntityPrehistoric implements IShearable {

    private static final DataParameter<Boolean> SHEARED = EntityDataManager.createKey(EntityMammoth.class, DataSerializers.BOOLEAN);
    private static final PotionEffect BIOME_EFFECT = new PotionEffect(MobEffects.WEAKNESS, 60, 1);
    protected boolean isSheared;
    private int eatGrassTimes = 0;

    public EntityMammoth(World world) {
        super(world, PrehistoricEntityType.MAMMOTH, 2, 12, 10, 66, 0.25, 0.35, 0, 10);
        this.setActualSize(1.2F, 0.7F);
        this.pediaScale = 60F;
        minSize = 1.1F;
        maxSize = 3.6F;
        teenAge = 7;
        developsResistance = true;
        breaksBlocks = true;
        this.ridingY = 1.25F;
        this.eatGrassTimes = 0;
        this.setSheared(false);
    }

    public void initEntityAI() {
        this.tasks.addTask(0, new DinoAIFleeBattle(this, 1.0D));
        this.tasks.addTask(1, new DinoMeleeAttackAI(this, 1.0D, false));
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(2, this.aiSit = new EntityAISit(this));
        this.tasks.addTask(3, new DinoAIFlockWander<>(this, 3, 6, 0.75F));
        this.tasks.addTask(3, new DinoAIEatBlocks(this));
        this.tasks.addTask(3, new DinoAIEatFeeders(this));
        this.targetTasks.addTask(0, new DinoAIEatItems(this));
        this.tasks.addTask(4, new DinoAIRiding(this, 1.0F));
        this.tasks.addTask(5, new DinoAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
        this.tasks.addTask(8, new DinoAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new DinoAILookIdle(this));
        this.targetTasks.addTask(1, new DinoAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new DinoAIOwnerHurtTarget(this));
        this.targetTasks.addTask(3, new DinoAIHurtByTarget(this));
        this.targetTasks.addTask(4, new DinoAIHunt(this, EntityLivingBase.class, true, (Predicate<Entity>) entity -> entity instanceof EntityLivingBase));
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return PrehistoricEntityType.MAMMOTH_LOOT;
    }


    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(SHEARED, false);
    }

    @Override
    public int getAttackLength() {
        return 35;
    }

    @Override
    public int getSpeakLength() {
        return 35;
    }


    @Override
    public boolean isShearable(ItemStack item, IBlockAccess world, BlockPos pos) {
        return !this.isSheared() && !this.isChild() && !this.isSkeleton();
    }

    @Override
    public ArrayList<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) {
        ArrayList var7 = new ArrayList();
        int var8 = 1 + this.rand.nextInt(20);
        for (int var9 = 0; var9 < var8; ++var9) {
            var7.add(new ItemStack(Blocks.WOOL, 1, 12));
        }
        this.setSheared(true);
        this.playSound(SoundEvents.ENTITY_SHEEP_SHEAR, 1.0F, 1.0F);
        return var7;
    }

    @Override
    public String getTexture() {
        if (this.isSkeleton()) {
            return "fossil:textures/model/" + type.toString().toLowerCase() + "_0/" + type.toString().toLowerCase() + "_skeleton.png";
        }
        String toggle = this.hasFeatherToggle ? !this.featherToggle ? "feathered/" : "scaled/" : "";
        String gender = this.isChild() ? "_baby" : this.getGender() == 0 ? "_female" : "_male";
        String sleeping = !this.isSleeping() ? this.isActuallyWeak() ? "_sleeping" : "" : "_sleeping";
        String toggleList = this.hasFeatherToggle ? !this.featherToggle ? "_feathered" : "_scaled" : "";
        return "fossil:textures/model/" + type.toString().toLowerCase() + "_0/" + toggle + type.toString().toLowerCase() + gender + toggleList + sleeping + (this.isSheared() ? "_sheared" : "") + ".png";
    }

    @Override
    public void setSpawnValues() {
    }

    @Override
    public void writeEntityToNBT(NBTTagCompound var1) {
        super.writeEntityToNBT(var1);
        var1.setInteger("GrassTicks", this.eatGrassTimes);
        var1.setBoolean("Sheared", this.isSheared());
    }

    @Override
    public void readEntityFromNBT(NBTTagCompound var1) {
        super.readEntityFromNBT(var1);
        this.setSheared(var1.getBoolean("Sheared"));
        this.eatGrassTimes = var1.getInteger("GrassTicks");
    }

    @Override
    public void doFoodEffect(Item item) {
        super.doFoodEffect(item);
        if (this.isSheared()) {
            ++this.eatGrassTimes;
            if (this.eatGrassTimes >= 5) {
                this.setSheared(false);
                this.eatGrassTimes = 0;
            }
        }
    }

    @Override
    public void onLivingUpdate() {
        int i = MathHelper.floor(this.posX);
        int j = MathHelper.floor(this.posY);
        int k = MathHelper.floor(this.posZ);
        if (!this.isPotionActive(MobEffects.WEAKNESS) && this.world.getBiome(new BlockPos(i, 0, k)).getTemperature(new BlockPos(i, j, k)) > 1.0 && !this.isSheared() && !this.isSkeleton()) {
            this.addPotionEffect(BIOME_EFFECT);
        }
        if (this.getAnimation() == ATTACK_ANIMATION && this.getAnimationTick() == 17 && this.getAttackTarget() != null) {
            doAttack();
            doAttackKnockback(2F);
        }
        super.onLivingUpdate();
    }

    @Override
    public PrehistoricEntityTypeAI.Activity aiActivityType() {
        return PrehistoricEntityTypeAI.Activity.DIURINAL;
    }

    @Override
    public PrehistoricEntityTypeAI.Attacking aiAttackType() {
        return PrehistoricEntityTypeAI.Attacking.KNOCKUP;
    }

    @Override
    public PrehistoricEntityTypeAI.Climbing aiClimbType() {
        return PrehistoricEntityTypeAI.Climbing.NONE;
    }

    @Override
    public PrehistoricEntityTypeAI.Following aiFollowType() {
        return PrehistoricEntityTypeAI.Following.NONE;
    }

    @Override
    public PrehistoricEntityTypeAI.Jumping aiJumpType() {
        return PrehistoricEntityTypeAI.Jumping.BASIC;
    }

    @Override
    public PrehistoricEntityTypeAI.Response aiResponseType() {
        return PrehistoricEntityTypeAI.Response.TERITORIAL;
    }

    @Override
    public PrehistoricEntityTypeAI.Stalking aiStalkType() {
        return PrehistoricEntityTypeAI.Stalking.NONE;
    }

    @Override
    public PrehistoricEntityTypeAI.Taming aiTameType() {
        return PrehistoricEntityTypeAI.Taming.IMPRINTING;
    }

    @Override
    public PrehistoricEntityTypeAI.Untaming aiUntameType() {
        return PrehistoricEntityTypeAI.Untaming.ATTACK;
    }

    @Override
    public PrehistoricEntityTypeAI.Moving aiMovingType() {
        return PrehistoricEntityTypeAI.Moving.WALK;
    }

    @Override
    public PrehistoricEntityTypeAI.WaterAbility aiWaterAbilityType() {
        return PrehistoricEntityTypeAI.WaterAbility.NONE;
    }

    @Override
    public boolean doesFlock() {
        return true;
    }

    @Override
    public Item getOrderItem() {
        return Items.STICK;
    }

    @Override
    public int getAdultAge() {
        return 14;
    }

    @Override
    public float getMaleSize() {
        return 1.15F;
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        if (this.getAnimation() != ATTACK_ANIMATION) {
            this.setAnimation(ATTACK_ANIMATION);
        }
        return false;
    }

    @Override
    public int getMaxHunger() {
        return 150;
    }

    public boolean isSheared() {
        if (world.isRemote) {
            boolean isStanding = this.dataManager.get(SHEARED);
            this.isSheared = isStanding;
            return isStanding;
        }

        return isSheared;
    }

    public void setSheared(boolean sheared) {
        this.dataManager.set(SHEARED, sheared);
        if (!world.isRemote) {
            this.isSheared = sheared;
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return FASoundRegistry.MAMMOTH_LIVING;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return FASoundRegistry.MAMMOTH_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FASoundRegistry.MAMMOTH_DEATH;
    }

    @Override
    public boolean canBeRidden() {
        return true;
    }

}
