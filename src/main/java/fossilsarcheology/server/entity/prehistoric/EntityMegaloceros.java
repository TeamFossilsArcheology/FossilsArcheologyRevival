package fossilsarcheology.server.entity.prehistoric;

import com.google.common.base.Predicate;
import fossilsarcheology.client.sound.FASoundRegistry;
import fossilsarcheology.server.entity.ai.*;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAISit;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityMegaloceros extends EntityPrehistoric {

    public EntityMegaloceros(World world) {
        super(world, PrehistoricEntityType.MEGALOCEROS, 1, 9, 10, 44, 0.1, 0.35, 0, 4);
        this.setActualSize(1.9F, 1.7F);
        this.nearByMobsAllowed = 9;
        this.pediaScale = 25;
        minSize = 0.5F;
        maxSize = 1.25F;
        teenAge = 4;
        developsResistance = true;
        breaksBlocks = false;
        this.ridingY = 2.35F;
    }

    public void initEntityAI() {
        this.tasks.addTask(0, new DinoAIFleeBattle(this, 1.0D));
        this.tasks.addTask(1, new DinoMeleeAttackAI(this, 1.0D, false));
        this.tasks.addTask(1, new DinoMeleeAttackAI(this, 1.0D, false));
        this.tasks.addTask(2, this.aiSit = new EntityAISit(this));
        this.tasks.addTask(3, new DinoAIWander(this, 1.0D));
        this.tasks.addTask(3, new DinoAIEatBlocks(this));
        this.tasks.addTask(3, new DinoAIEatFeeders(this));
        this.targetTasks.addTask(0, new DinoAIEatItems(this));
        this.tasks.addTask(4, new DinoAIPanic(this, 1.5D));
        this.tasks.addTask(4, new DinoAIRiding(this, 1.0F));
        this.tasks.addTask(5, new DinoAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
        this.tasks.addTask(8, new DinoAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.tasks.addTask(8, new DinoAILookIdle(this));
        this.targetTasks.addTask(1, new DinoAIOwnerHurtByTarget(this));
        this.targetTasks.addTask(2, new DinoAIOwnerHurtTarget(this));
        this.targetTasks.addTask(3, new DinoAIHurtByTarget(this));
        this.targetTasks.addTask(4, new DinoAIHunt(this, EntityLivingBase.class, true, (Predicate<Entity>) entity -> entity instanceof EntityLivingBase));
    }

    @Override
    public int getAttackLength() {
        return 30;
    }

    @Override
    public void setSpawnValues() {
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

        return PrehistoricEntityTypeAI.Following.AGRESSIVE;
    }

    @Override
    public PrehistoricEntityTypeAI.Jumping aiJumpType() {

        return PrehistoricEntityTypeAI.Jumping.BASIC;
    }

    @Override
    public PrehistoricEntityTypeAI.Response aiResponseType() {
        return this.getGender() == 1 && !this.isChild() ? PrehistoricEntityTypeAI.Response.TERITORIAL : PrehistoricEntityTypeAI.Response.SCARED;
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

        return false;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        this.ridingY = this.getGender() == 1 ? 2.45F : 2.1F;
        if (this.getAnimation() == ATTACK_ANIMATION && this.getAnimationTick() == 15 && this.getAttackTarget() != null && this.canReachPrey()) {
            doAttack();
            doAttackKnockback(2F);
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
    public Item getOrderItem() {

        return Items.STICK;
    }

    @Override
    public int getAdultAge() {
        return 9;
    }

    @Override
    public float getMaleSize() {
        return 1.2F;
    }

    @Override
    public int getMaxHunger() {
        return 100;
    }

    @Override
    public boolean canBeRidden() {
        return true;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return FASoundRegistry.MEGALOCEROS_LIVING;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return FASoundRegistry.MEGALOCEROS_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FASoundRegistry.MEGALOCEROS_DEATH;
    }

}
