package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.entity.ai.DinoAIWander;
import com.fossil.fossil.entity.prehistoric.base.IDinosaur;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityTypeAI;
import com.fossil.fossil.item.ModItems;
import com.fossil.fossil.util.Diet;
import com.fossil.fossil.util.FossilAnimation;
import com.fossil.fossil.util.TimePeriod;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import software.bernie.geckolib3.core.IAnimatable;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;
import software.bernie.geckolib3.core.manager.AnimationData;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

// TODO Accurately adjust values here, for now setting it identical to Triceratops
public class Therizinosaurus extends Prehistoric implements IAnimatable, IDinosaur {
    public AnimationFactory factory = GeckoLibUtil.createFactory(this);
    public static final String IDLE = "fa.therizinosaurus.idle";
    public static final String WALK = "fa.therizinosaurus.walk";
    public Therizinosaurus(EntityType<? extends Prehistoric> entityType, Level level) {
        super(
            entityType,
            level,
            false,
            0.4F,
            5F,
            5,
            6,
            12,
            12,
            12,
            64,
            0.2,
            0.35,
            5,
            15,
            TimePeriod.MESOZOIC,
            Diet.HERBIVORE,
            ModItems.THERIZINOSAURUS_SPAWN_EGG.get()
        );
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(3, new DinoAIWander(this, 1.0D));
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

    public PlayState onFrame(AnimationEvent<Therizinosaurus> event) {
        String animation;
        if (this.getCurrentAnimation() == FossilAnimation.WALK) {
            animation = WALK;
        } else {
            animation = IDLE;
        }
        event.getController().setAnimation(new AnimationBuilder().addAnimation(animation, ILoopType.EDefaultLoopTypes.LOOP));
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
}
