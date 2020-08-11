package fossilsarcheology.server.entity.prehistoric;

import com.google.common.base.Predicate;
import fossilsarcheology.client.sound.FASoundRegistry;
import fossilsarcheology.server.entity.ai.*;
import fossilsarcheology.server.item.FAItemRegistry;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityDiplocaulus extends EntityPrehistoricSwimming {

    public EntityDiplocaulus(World world) {
        super(world, PrehistoricEntityType.DIPLOCAULUS, 1, 2, 2, 10, 0.15, 0.3, 1, 2);
        this.setActualSize(0.65F, 0.4F);
        minSize = 0.5F;
        maxSize = 1F;
        teenAge = 3;
        this.hasBabyTexture = true;
        this.pediaScale = 100;
        this.pediaY = 15;
    }

    public void initEntityAI() {
        this.tasks.addTask(0, new DinoMeleeAttackAI(this, 1.0D, false));
        this.tasks.addTask(1, new DinoAIFindWaterTarget(this, 10, true));
        this.tasks.addTask(1, new DinoAIGetInWater(this, 1.0D));
        this.tasks.addTask(2, this.aiSit = new EntityAISit(this));
        this.tasks.addTask(3, new DinoAIEatFeedersAndBlocks(this));
        this.targetTasks.addTask(0, new DinoAIEatItems(this));
        this.tasks.addTask(4, new DinoAIRiding(this, 1.0F));
        this.tasks.addTask(5, new DinoAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
        this.tasks.addTask(7, new DinoAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(7, new DinoAILookIdle(this));
        this.targetTasks.addTask(1, new DinoAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new DinoAIOwnerHurtTarget(this));
        this.targetTasks.addTask(3, new DinoAIHurtByTarget(this));
        this.targetTasks.addTask(4, new DinoAIHunt(this, EntityLivingBase.class, true, (Predicate<Entity>) entity -> entity instanceof EntityLivingBase));
    }

    @Override
    public void setSpawnValues() {

    }

    @Override
    public String getTexture() {
        if (isSkeleton()) {
            return "fossil:textures/model/diplocaulus_0/" + "diplocaulus_skeleton.png";
        }
        String toggle = this.hasFeatherToggle ? !this.featherToggle ? "feathered/" : "scaled/" : "";
        String gender = this.getGender() == 0 ? "_female" : "_male";
        String sleeping = !this.isSleeping() ? "" : "_sleeping";
        String toggleList = this.hasFeatherToggle ? !this.featherToggle ? "_feathered" : "_scaled" : "";
        return "fossil:textures/model/diplocaulus_0/" + toggle + "diplocaulus" + gender + toggleList + sleeping + ".png";
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
        return PrehistoricEntityTypeAI.Following.NORMAL;
    }

    @Override
    public PrehistoricEntityTypeAI.Jumping aiJumpType() {
        return PrehistoricEntityTypeAI.Jumping.BASIC;
    }

    @Override
    public PrehistoricEntityTypeAI.Response aiResponseType() {
        return PrehistoricEntityTypeAI.Response.WATERAGRESSIVE;
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
        return PrehistoricEntityTypeAI.Untaming.NONE;
    }

    @Override
    public PrehistoricEntityTypeAI.Moving aiMovingType() {
        return PrehistoricEntityTypeAI.Moving.AQUATIC;
    }

    @Override
    public PrehistoricEntityTypeAI.WaterAbility aiWaterAbilityType() {
        return PrehistoricEntityTypeAI.WaterAbility.IGNOREANDFISH;
    }

    @Override
    public boolean doesFlock() {
        return false;
    }

    @Override
    public Item getOrderItem() {
        return FAItemRegistry.SHELL;
    }

    @Override
    public int getAdultAge() {
        return 6;
    }

    @Override
    public int getMaxHunger() {
        return 50;
    }

    @Override
    public double swimSpeed() {
        return 0.5D;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (this.getAnimation() == ATTACK_ANIMATION && this.getAnimationTick() == 5 && this.getAttackTarget() != null) {
            doAttack();
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        if (this.getAnimation() == NO_ANIMATION) {
            this.setAnimation(ATTACK_ANIMATION);
        }
        return false;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return this.isInWater() ? FASoundRegistry.TIKTAALIK_LIVING : FASoundRegistry.HENODUS_LIVING;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return FASoundRegistry.TIKTAALIK_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FASoundRegistry.TIKTAALIK_DEATH;
    }

    @Override
    public float getSoundPitch() {
        return super.getSoundPitch() * 1.5F;
    }

    @Override
    public float getSoundVolume() {
        return super.getSoundVolume() * 0.15F;
    }

    @Override
    public boolean canBeRidden() {
        return false;
    }
}
