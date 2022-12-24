package fossilsarcheology.server.entity.prehistoric;

import fossilsarcheology.Revival;
import fossilsarcheology.server.entity.prehistoric.base.EntityPrehistoric;
import fossilsarcheology.server.entity.prehistoric.base.PrehistoricEntityTypeAI;
import fossilsarcheology.server.util.PrehistoricEntityType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

public class EntityTriceratops extends EntityPrehistoric {

    public EntityTriceratops(EntityType type, World world) {
        super(type, world, PrehistoricEntityType.TRICERATOPS, 1, 12, 12, 64, 0.2, 0.35, 5, 15);
        this.hasFeatherToggle = true;
        this.featherToggle = Revival.CONFIG_OPTIONS.quilledTriceratops;
        this.nearByMobsAllowed = 7;
        minSize = 0.4F;
        maxSize = 5F;
        teenAge = 5;
        developsResistance = true;
        breaksBlocks = true;
        this.ridingY = 0.73F;
        this.ridingXZ = -0.05F;
        this.pediaScale = 55;
    }

    public void registerGoals() {
 /*
            this.goalSelector.addGoal(0, new DinoAIFleeBattle(this, 1.0D));
        this.goalSelector.addGoal(1, new DinoMeleeAttackAI(this, 1.0D, false));
        this.goalSelector.addGoal(1, new EntityAISwimming(this));
        this.goalSelector.addGoal(2, this.aiSit = new EntityAISit(this));
        this.goalSelector.addGoal(3, new DinoAIWander(this, 1.0D));
        this.goalSelector.addGoal(3, new DinoAIEatFeedersAndBlocks(this));
        this.targetSelector.addGoal(0, new DinoAIEatItems(this));
        this.goalSelector.addGoal(4, new DinoAIRiding(this, 1.0F));
        this.goalSelector.addGoal(4, new DinoAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
        this.goalSelector.addGoal(7, new DinoAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.goalSelector.addGoal(7, new DinoAILookIdle(this));
        this.targetSelector.addGoal(1, new DinoAIOwnerHurtByTarget(this));
        this.targetSelector.addGoal(2, new DinoAIOwnerHurtTarget(this));
        this.targetSelector.addGoal(3, new DinoAIHurtByTarget(this));
        this.targetSelector.addGoal(4, new DinoAIHunt(this, LivingEntity.class, true, (Predicate<Entity>) entity -> entity instanceof EntityLivingBase));
}
  */
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

        return PrehistoricEntityTypeAI.Following.NORMAL;
    }

    @Override
    public PrehistoricEntityTypeAI.Jumping aiJumpType() {

        return PrehistoricEntityTypeAI.Jumping.BASIC;
    }

    @Override
    public PrehistoricEntityTypeAI.Response aiResponseType() {

        return this.isChild() ? PrehistoricEntityTypeAI.Response.SCARED : PrehistoricEntityTypeAI.Response.TERITORIAL;
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

        return PrehistoricEntityTypeAI.Untaming.STARVE;
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
    public Item getOrderItem() {
        return Items.STICK;
    }

    @Override
    public int getAdultAge() {
        return 12;
    }

    @Override
    public int getTailSegments() {
        return 3;
    }

    @Override
    public void livingTick() {
        super.livingTick();
        if (this.getAnimation() == ATTACK_ANIMATION && this.getAnimationTick() == 12 && this.getAttackTarget() != null) {
            doAttack();
            doAttackKnockback(0.5F);
        }
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
        return 175;
    }

   /* @Override
    protected SoundEvent getAmbientSound() {
        return FASoundRegistry.TRICERATOPS_LIVING;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return FASoundRegistry.TRICERATOPS_HURT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return FASoundRegistry.TRICERATOPS_DEATH;
    }*/

    @Override
    public boolean canBeRidden() {
        return true;
    }
}
