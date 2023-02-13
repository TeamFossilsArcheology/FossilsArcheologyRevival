package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.entity.ai.DinoAIFleeBattle;
import com.fossil.fossil.entity.ai.DinoAIWander;
import com.fossil.fossil.entity.ai.DinoMeleeAttackAI;
import com.fossil.fossil.entity.ai.EatFeedersAndBlocksGoal;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityTypeAI;
import net.minecraft.world.entity.EntityType;
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

// TODO Accurately adjust values here, for now setting it identical to Triceratops
public class Therizinosaurus extends Prehistoric {
    public AnimationFactory factory = GeckoLibUtil.createFactory(this);
    public static final String IDLE = "fa.therizinosaurus.idle";
    public static final String WALK = "fa.therizinosaurus.walk";
    public static final String SLEEP = "fa.therizinosaurus.sleep";
    public static final String SLEEP_BABY = "fa.therizinosaurus.sleep_baby";
    public static final String THREAT = "fa.therizinosaurus.threat";
    public static final String ATTACK1 = "fa.therizinosaurus.attack1";
    public static final String ATTACK2 = "fa.therizinosaurus.attack2";
    public static final String EAT = "fa.therizinosaurus.eat";

    public Therizinosaurus(EntityType<? extends Prehistoric> entityType, Level level) {
        super(
            entityType,
            PrehistoricEntityType.THERIZINOSAURUS,
            level,
            false,
            0.4F,
            2.0F,
            5,
            6,
            12,
            12,
            12,
            64,
            0.2,
            0.35,
            5,
            15
        );
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new DinoAIFleeBattle(this, 1.0D));
        this.goalSelector.addGoal(2, new DinoMeleeAttackAI(this, 1.0, true));
        this.goalSelector.addGoal(3, new EatFeedersAndBlocksGoal(this));
        this.goalSelector.addGoal(7, new DinoAIWander(this, 1.0));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
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
    public int getMaxHunger() {
        return 175;
    }

    @Override
    public Item getOrderItem() {
        return Items.STICK;
    }

    @Override
    public boolean canBeRidden() {
        return false;
    }

    @Override
    public float getFemaleScale() {
        return 1.12F;
    }

    public PlayState onFrame(AnimationEvent<Therizinosaurus> event) {
        String animation = getCurrentAnimation();
        ILoopType type;
        if (IDLE.equals(animation) || WALK.equals(animation) || SLEEP.equals(animation) || SLEEP_BABY.equals(animation)) {
            type = ILoopType.EDefaultLoopTypes.LOOP;
        } else {
            type = ILoopType.EDefaultLoopTypes.HOLD_ON_LAST_FRAME;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation(animation, type));
        return PlayState.CONTINUE;
    }

    @Override
    public void registerControllers(AnimationData data) {
        data.addAnimationController(new AnimationController<>(this, "controller", 0, this::onFrame));
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public String getIdleAnimation() {
        return IDLE;
    }

    @Override
    public String getWalkingAnimation() {
        return WALK;
    }

    @Override
    public String getChasingAnimation() {
        return WALK;
    }

    @Override
    public AttackAnimationInfo[] getAttackAnimationsWithDelay() {
        return new AttackAnimationInfo[] {
            new AttackAnimationInfo(ATTACK1, 21, 12),
            new AttackAnimationInfo(ATTACK2, 25, 12, 15)
        };
    }
}
