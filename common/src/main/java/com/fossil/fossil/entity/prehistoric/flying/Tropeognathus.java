package com.fossil.fossil.entity.prehistoric.flying;

import com.fossil.fossil.entity.ai.*;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricFlying;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class Tropeognathus extends PrehistoricFlying {
    public static final String ANIMATIONS = "tropeognathus.animation.json";
    public static final String ATTACK = "fa.tropeognathus.biteattack";
    public static final String ATTACK_WATER = "fa.tropeognathus.biteattackwater";
    public static final String ATTACK_AIR = "fa.tropeognathus.bitefly";
    public static final String DISPLAY = "fa.tropeognathus.display";
    public static final String EAT = "fa.tropeognathus.biteeat";
    public static final String EAT_WATER = "fa.tropeognathus.biteeatwater";
    public static final String FLY = "fa.tropeognathus.fly";
    public static final String IDLE = "fa.tropeognathus.idle";
    public static final String IDLE_CALL = "fa.tropeognathus.idlecall";
    public static final String IDLE_LOOKAROUND = "fa.tropeognathus.idlelookaround";
    public static final String IDLE_PREEN = "fa.tropeognathus.idlepreen";
    public static final String IDLE_SWIM = "fa.tropeognathus.idleswim";
    public static final String RUN = "fa.tropeognathus.run";
    public static final String SLEEP = "fa.tropeognathus.sleep";
    public static final String SWIM = "fa.tropeognathus.swim";
    public static final String TAKEOFF_GROUND = "fa.tropeognathus.groundtakeoff";
    public static final String TAKEOFF_WATER = "fa.tropeognathus.watertakeoff";
    public static final String WALK = "fa.tropeognathus.walk";
    public final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public Tropeognathus(EntityType<Tropeognathus> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(0, new FleeBattleGoal(this, 1.0D));
        goalSelector.addGoal(2, new DelayedAttackGoal(this, 1.0, true));
        targetSelector.addGoal(1, new DinoOwnerHurtByTargetGoal(this));
        targetSelector.addGoal(2, new DinoOwnerHurtTargetGoal(this));
        targetSelector.addGoal(3, new DinoHurtByTargetGoal(this));
    }

    @Override
    public PrehistoricEntityInfo info() {
        return PrehistoricEntityInfo.TROPEOGNATHUS;
    }

    @Override
    public Item getOrderItem() {
        return Items.STICK;
    }

    @Override
    public @NotNull Animation nextEatingAnimation() {
        return getAllAnimations().get(IDLE);
    }

    @Override
    public @NotNull Animation nextIdleAnimation() {
        String key = IDLE;

        if (isInWater()) {
            key = IDLE_SWIM;
        } else if (isFlying()) {
            key = FLY;//TODO: Hover anim missing
        } else {
            int number = random.nextInt(10);
            switch (number) {
                case 0, 1, 2, 3, 4, 5, 6 -> key = IDLE;
                case 7 -> key = IDLE_PREEN;
                case 8 -> key = IDLE_LOOKAROUND;
                case 9 -> key = IDLE_CALL;
            }
        }

        return getAllAnimations().get(key);
    }

    @Override
    public @NotNull Animation nextSleepingAnimation() {
        return getAllAnimations().get(SLEEP);
    }

    @Override
    public @NotNull Animation nextMovingAnimation() {
        String key = WALK;
        boolean isChasing = goalSelector.getRunningGoals().anyMatch(it -> it.getGoal() instanceof DelayedAttackGoal);

        if (isChasing) key = RUN;
        if (isInWater()) key = SWIM;
        if (isFlying()) key = FLY;

        return getAllAnimations().get(key);
    }

    @Override
    public @NotNull Animation nextSprintingAnimation() {
        String key = RUN;
        if (isInWater()) key = SWIM;
        if (isFlying()) key = FLY;

        return getAllAnimations().get(key);
    }

    @Override
    public @NotNull Animation nextAttackAnimation() {
        String key = ATTACK;
        if (isInWater()) key = ATTACK_WATER;
        if (isFlying()) key = ATTACK_AIR;

        return getAllAnimations().get(key);
    }

    @Override
    public @NotNull Animation nextTakeOffAnimation() {
        String key = TAKEOFF_GROUND;
        if (isInWater()) key = TAKEOFF_WATER;

        return getAllAnimations().get(key);
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
