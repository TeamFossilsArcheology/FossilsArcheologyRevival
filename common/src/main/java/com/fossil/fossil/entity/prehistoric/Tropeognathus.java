package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.entity.ai.DinoAIFleeBattle;
import com.fossil.fossil.entity.ai.DinoAIWander;
import com.fossil.fossil.entity.ai.DinoMeleeAttackAI;
import com.fossil.fossil.entity.ai.EatFeedersAndBlocksGoal;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityTypeAI;
import com.fossil.fossil.entity.prehistoric.base.Pterosaurs;
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

public class Tropeognathus extends Pterosaurs {
    public static final String ANIMATIONS = "fa.tropeognathus.animations.json";
    public final AnimationFactory factory = GeckoLibUtil.createFactory(this);
    public static final String FLY = "fa.tropeognathus.fly";
    public static final String GROUND_TAKEOFF = "fa.tropeognathus.groundtakeoff";
    public static final String RUN = "fa.tropeognathus.run";
    public static final String WALK = "fa.tropeognathus.walk";
    public static final String BITE_EAT = "fa.tropeognathus.biteeat";
    public static final String BITE_ATTACK = "fa.tropeognathus.biteattack";
    public static final String BITE_EAT_IN_WATER = "fa.tropeognathus.biteeatwater";
    public static final String IDLE_SWIM = "fa.tropeognathus.idleswim";
    public static final String SWIM = "fa.tropeognathus.swim";
    public static final String EAT = "fa.tropeognathus.eat";
    public static final String BITE_ATTACK_WATER = "fa.tropeognathus.biteattackwater";
    public static final String BITE_IN_AIR = "fa.tropeognathus.bitefly";
    public static final String DISPLAY = "fa.tropeognathus.display";
    public static final String IDLE = "fa.tropeognathus.idle";
    public static final String IDLE_PREEN = "fa.tropeognathus.idlepreen";
    public static final String IDLE_CALL = "fa.tropeognathus.idlecall";
    public static final String IDLE_LOOKAROUND = "fa.tropeognathus.idlelookaround";
    public static final String WATER_TAKEOFF = "fa.tropeognathus.watertakeoff";
    public static final String SLEEP = "fa.tropeognathus.sleep";

    private static final LazyLoadedValue<Map<String, ServerAnimationInfo>> allAnimations = new LazyLoadedValue<>(() -> {
        var file = GeckoLibCache.getInstance().getAnimations().get(new ResourceLocation(Fossil.MOD_ID, "animations/" + ANIMATIONS));
        Map<String, ServerAnimationInfo> newMap = new HashMap<>();
        file.animations().forEach((key, value) -> {
            ServerAnimationInfo info;
            switch (key) {
                case BITE_ATTACK -> info = new ServerAttackAnimationInfo(value, ATTACKING_PRIORITY, 20);
                case BITE_ATTACK_WATER -> info = new ServerAttackAnimationInfo(value, ATTACKING_PRIORITY, 11);
                case BITE_IN_AIR -> info = new ServerAttackAnimationInfo(value, ATTACKING_PRIORITY, 15);
                case SWIM, WALK, RUN, FLY -> info = new ServerAnimationInfo(value, MOVING_PRIORITY);
                case IDLE, IDLE_CALL, IDLE_LOOKAROUND, IDLE_PREEN, IDLE_SWIM -> info = new ServerAnimationInfo(value, IDLE_PRIORITY);
                default -> info = new ServerAnimationInfo(value, DEFAULT_PRIORITY);
            }
            newMap.put(key, info);
        });
        return newMap;
    });

    public Tropeognathus(EntityType<Tropeognathus> entityType, Level level) {
        super(
            entityType,
            PrehistoricEntityType.PTEROSAUR,
            level,
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
        this.goalSelector.addGoal(3, new EatFeedersAndBlocksGoal(this));
        this.goalSelector.addGoal(7, new DinoAIWander(this, 1.0));
        this.targetSelector.addGoal(2, new HurtByTargetGoal(this));
    }

    // TODO These may not be correct, should be adjusted
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
        return PrehistoricEntityTypeAI.Response.NONE;
    }

    @Override
    public PrehistoricEntityTypeAI.Stalking aiStalkType() {
        return PrehistoricEntityTypeAI.Stalking.NONE;
    }

    @Override
    public PrehistoricEntityTypeAI.Taming aiTameType() {
        return PrehistoricEntityTypeAI.Taming.NONE;
    }

    @Override
    public PrehistoricEntityTypeAI.Untaming aiUntameType() {
        return PrehistoricEntityTypeAI.Untaming.NONE;
    }

    @Override
    public PrehistoricEntityTypeAI.Moving aiMovingType() {
        return PrehistoricEntityTypeAI.Moving.FLIGHT;
    }

    @Override
    public PrehistoricEntityTypeAI.WaterAbility aiWaterAbilityType() {
        return PrehistoricEntityTypeAI.WaterAbility.ATTACK;
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
    @NotNull
    public ServerAnimationInfo nextIdleAnimation() {
        String key = IDLE;

        if (isInWater()) {
            key = IDLE_SWIM;
        }
        else {
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
    @NotNull
    public ServerAnimationInfo nextMovingAnimation() {
        String key = WALK;
        boolean isChasing = goalSelector.getRunningGoals().anyMatch(it -> it.getGoal() instanceof DinoMeleeAttackAI);

        if (isChasing) key = RUN;
        if (isInWater()) key = SWIM;
        if (isFlying()) key = FLY;

        return getAllAnimations().get(key);
    }

    @Override
    @NotNull
    public Prehistoric.ServerAnimationInfo nextChasingAnimation() {
        String key = RUN;
        if (isInWater()) key = SWIM;
        if (isFlying()) key = FLY;

        return getAllAnimations().get(key);
    }

    @Override
    public ServerAnimationInfo nextEatingAnimation() {
        return getAllAnimations().get(IDLE);
        //return getAllAnimations().get(EAT);
    }

    @Override
    @NotNull
    public Prehistoric.ServerAttackAnimationInfo nextAttackAnimation() {
        String key = BITE_ATTACK;
        if (isInWater()) key = BITE_ATTACK_WATER;
        if (isFlying()) key = BITE_IN_AIR;

        return (ServerAttackAnimationInfo) getAllAnimations().get(key);
    }

    @Override
    public boolean isFlying() {
        return !onGround;
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Override
    public ServerAnimationInfo getTakeOffAnimation() {
        String key = GROUND_TAKEOFF;
        if (isInWater()) key = WATER_TAKEOFF;

        return getAllAnimations().get(key);
    }
}
