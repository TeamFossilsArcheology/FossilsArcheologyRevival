package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.entity.ai.DinoAIFleeBattle;
import com.fossil.fossil.entity.ai.DinoAIWander;
import com.fossil.fossil.entity.ai.DinoMeleeAttackAI;
import com.fossil.fossil.entity.ai.EatFeedersAndBlocksGoal;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityTypeAI;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class Triceratops extends Prehistoric {
    public AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public static final String IDLE = "animation.triceratops.idle";
    public static final String WALK = "animation.triceratops.walk";
    public static final String RUN = "animation.triceratops.run";
    public static final String SWIM = "animation.triceratops.swim";
    public static final String DRINK = "animation.triceratops.drink";
    public static final String EAT = "animation.triceratops.eat";
    public static final String SIT = "animation.triceratops.sit";
    public static final String SLEEP1 = "animation.triceratops.sleep1";
    public static final String SLEEP2 = "animation.triceratops.sleep2";
    public static final String RAM = "animation.triceratops.ram";
    public static final String RAM_WINDUP = "animation.triceratops.ram_windup";
    public static final String TURN_RIGHT = "animation.triceratops.turn_right";
    public static final String TURN_LEFT = "animation.triceratops.turn_left";
    public static final String SPEAK = "animation.triceratops.speak";
    public static final String CALL = "animation.triceratops.call";
    public static final String ATTACK1 = "animation.triceratops.attack1";
    public static final String ATTACK2 = "animation.triceratops.attack2";

    public Triceratops(EntityType<? extends Triceratops> type, Level level) {
        super(
            type, PrehistoricEntityType.TRICERATOPS,
            level,
            false,
            0.4F,
            1.4F,
            0.5F,
            2F,
            5,
            12,
            1,
            12,
            12,
            64,
            0.1,
            0.25,
            5,
            15
        );
        this.hasFeatherToggle = true;
        this.featherToggle = Fossil.CONFIG_OPTIONS.quilledTriceratops;
        this.nearByMobsAllowed = 7;
        breaksBlocks = true;
        this.ridingY = 0.73F;
        this.ridingXZ = -0.05F;
        this.pediaScale = 55;
        this.maxUpStep = 1.5F;
    }

    @Override
    public void registerGoals() {
        super.registerGoals();

        double speed = getAttributeValue(Attributes.MOVEMENT_SPEED);
        this.goalSelector.addGoal(0, new DinoAIFleeBattle(this, 1.2 * speed));
        this.goalSelector.addGoal(1, new DinoMeleeAttackAI(this, speed, false));
        this.goalSelector.addGoal(1, new FloatGoal(this));
        this.goalSelector.addGoal(7, new DinoAIWander(this, speed));
        this.goalSelector.addGoal(3, new EatFeedersAndBlocksGoal(this));
        this.targetSelector.addGoal(3, new HurtByTargetGoal(this));
        /*this.goalSelector.addGoal(3, new DinoAIEatFeedersAndBlocks(this));
        this.targetSelector.addGoal(3, new DinoAIEatItems(this));
        this.goalSelector.addGoal(4, new DinoAIRiding(this, 1.0F));
        this.goalSelector.addGoal(4, new DinoAIFollowOwner(this, 1.0D, 10.0F, 2.0F));
        this.goalSelector.addGoal(7, new DinoAIWatchClosest(this, EntityPlayer.class, 8.0F));
        this.goalSelector.addGoal(7, new DinoAILookIdle(this));

        this.targetSelector.addGoal(1, new DinoAIOwnerHurtByTarget(this));
        this.targetSelector.addGoal(2, new DinoAIOwnerHurtTarget(this));
        this.targetSelector.addGoal(3, new DinoAIHurtByTarget(this));
        this.targetSelector.addGoal(4, new DinoAIHunt(this, LivingEntity.class, true, entity -> entity instanceof LivingEntity));*/
    }

    @Override
    public float getModelScale() {
        return super.getModelScale() * 3;
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

        return this.isBaby() ? PrehistoricEntityTypeAI.Response.SCARED : PrehistoricEntityTypeAI.Response.TERITORIAL;
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
    public void tick() {
        super.tick();
        /*if (this.getAnimation() == ATTACK_ANIMATION && this.getAnimationTick() == 12 && this.getAttackTarget() != null) {
            doAttack();
            doAttackKnockback(0.5F);
        }*/
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

    public PlayState onFrame(AnimationEvent<Triceratops> event) {
        /*if (event.getController().getAnimationState() == AnimationState.Stopped) {
            setCurrentAnimation(IDLE);
        }
        if (IDLE.equals(getCurrentAnimation()) && navigation.isInProgress()) setCurrentAnimation(WALK);*/

        String animation = getCurrentAnimation();
        ILoopType type;
        if (IDLE.equals(animation) || WALK.equals(animation) || SLEEP1.equals(animation) || SLEEP2.equals(animation) ||
            RUN.equals(animation) || SWIM.equals(animation) || SIT.equals(animation) || RAM.equals(animation)) {
            type = ILoopType.EDefaultLoopTypes.LOOP;
        } else {
            type = ILoopType.EDefaultLoopTypes.HOLD_ON_LAST_FRAME;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation(animation, type));
        return PlayState.CONTINUE;
    }

    @Override
    public boolean canBeRidden() {
        return true;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 15, this::onFrame));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public String getWalkingAnimation() {
        return WALK;
    }

    @Override
    public String getChasingAnimation() {
        return RUN;
    }

    @Override
    public AttackAnimationInfo[] getAttackAnimationsWithDelay() {
        return new AttackAnimationInfo[]{
            new AttackAnimationInfo(ATTACK1, 20, 8),
            new AttackAnimationInfo(ATTACK2, 20, 8)
        };
    }

    @Override
    public String getIdleAnimation() {
        return IDLE;
    }
}
