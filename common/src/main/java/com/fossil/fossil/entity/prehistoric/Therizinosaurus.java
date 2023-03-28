package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.entity.ai.*;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityTypeAI;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.resource.GeckoLibCache;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.HashMap;
import java.util.Map;

// TODO Accurately adjust values here, for now setting it identical to Triceratops
public class Therizinosaurus extends Prehistoric {
    public static final String ANIMATIONS = "fa.therizinosaurus.animations.json";
    public final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    public static final String IDLE = "fa.therizinosaurus.idle";
    public static final String WALK = "fa.therizinosaurus.walk";
    public static final String SLEEP = "fa.therizinosaurus.sleep";
    public static final String SLEEP_BABY = "fa.therizinosaurus.sleep_baby";
    public static final String THREAT = "fa.therizinosaurus.threat";
    public static final String ATTACK1 = "fa.therizinosaurus.attack1";
    public static final String ATTACK2 = "fa.therizinosaurus.attack2";
    public static final String EAT = "fa.therizinosaurus.eat";

    private static final LazyLoadedValue<Map<String, ServerAnimationInfo>> allAnimations = new LazyLoadedValue<>(() -> {
        var file = GeckoLibCache.getInstance().getAnimations().get(new ResourceLocation(Fossil.MOD_ID, "animations/" + ANIMATIONS));
        Map<String, ServerAnimationInfo> newMap = new HashMap<>();
        file.animations().forEach((key, value) -> {
            ServerAnimationInfo info;
            switch (key) {
                case ATTACK1 -> info = new ServerAttackAnimationInfo(value, ATTACKING_PRIORITY, 12);
                case ATTACK2 -> info = new ServerAttackAnimationInfo(value, ATTACKING_PRIORITY, 12, 25);
                case IDLE -> info = new ServerAnimationInfo(value, IDLE_PRIORITY);
                case WALK -> info = new ServerAnimationInfo(value, MOVING_PRIORITY);
                default -> info = new ServerAnimationInfo(value, DEFAULT_PRIORITY);
            }
            newMap.put(key, info);
        });
        return newMap;
    });

    public Therizinosaurus(EntityType<Therizinosaurus> entityType, Level level) {
        super(
            entityType,
            PrehistoricEntityType.THERIZINOSAURUS,
            level,
            false,
            false,
            0.4F,
            2.0F,
            2F,
            2F,
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
    public Entity[] getCustomParts() {
        return new Entity[0];
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new DinoAIFleeBattle(this, 1.0D));
        this.goalSelector.addGoal(2, new DinoMeleeAttackAI(this, 1.0, true));
        this.goalSelector.addGoal(3, new EatFromFeederGoal(this));
        this.goalSelector.addGoal(4, new EatItemEntityGoal(this));
        this.goalSelector.addGoal(5, new EatPlantGoal(this));
        goalSelector.addGoal(5, new DinoFollowOwnerGoal(this, 1, 10, 2, false));
        this.goalSelector.addGoal(7, new DinoAIWander(this, 1.0));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
        targetSelector.addGoal(4, new HuntGoal(this));
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
    public Map<String, ServerAnimationInfo> getAllAnimations() {
        return allAnimations.get();
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

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    @NotNull
    public ServerAnimationInfo nextIdleAnimation() {
        return allAnimations.get().get(IDLE);
    }

    @Override
    @NotNull
    public ServerAnimationInfo nextMovingAnimation() {
        return allAnimations.get().get(WALK);
    }

    @Override
    @NotNull
    public Prehistoric.ServerAnimationInfo nextChasingAnimation() {
        return nextMovingAnimation();
    }

    @Override
    public ServerAnimationInfo nextEatingAnimation() {
        return getAllAnimations().get(EAT);
    }

    @Override
    @NotNull
    public Prehistoric.ServerAttackAnimationInfo nextAttackAnimation() {
        String key;

        if (getRandom().nextBoolean()) {
            key = ATTACK1;
        } else {
            key =ATTACK2;
        }

        return (ServerAttackAnimationInfo) allAnimations.get().get(key);
    }
}
