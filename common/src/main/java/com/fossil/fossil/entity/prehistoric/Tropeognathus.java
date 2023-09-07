package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.entity.ai.*;
import com.fossil.fossil.entity.animation.AnimationManager;
import com.fossil.fossil.entity.data.EntityDataManager;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricFlying;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.fossil.fossil.entity.animation.AnimationLogic.ServerAnimationInfo;
import static com.fossil.fossil.entity.animation.AttackAnimationLogic.ServerAttackAnimationInfo;

public class Tropeognathus extends PrehistoricFlying {
    public static final String ANIMATIONS = "tropeognathus.animations.json";
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
        Map<String, ServerAnimationInfo> newMap = new HashMap<>();
        List<AnimationManager.Animation> animations = AnimationManager.ANIMATIONS.getAnimation(ANIMATIONS);
        for (AnimationManager.Animation animation : animations) {
            ServerAnimationInfo info;
            switch (animation.animationId()) {
                case BITE_ATTACK, BITE_ATTACK_WATER, BITE_IN_AIR -> info = new ServerAttackAnimationInfo(animation, animation.attackDelay());
                case SWIM, WALK, RUN, FLY -> info = new ServerAnimationInfo(animation);
                case IDLE, IDLE_CALL, IDLE_LOOKAROUND, IDLE_PREEN, IDLE_SWIM ->
                        info = new ServerAnimationInfo(animation);
                default -> info = new ServerAnimationInfo(animation);
            }
            newMap.put(animation.animationId(), info);
        }
        return newMap;
    });
    private static final EntityDataManager.Data data = EntityDataManager.ENTITY_DATA.getData("tropeognathus");
    public final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public Tropeognathus(EntityType<Tropeognathus> entityType, Level level) {
        super(entityType, level, false);
    }

    @Override
    public Entity[] getCustomParts() {
        return new Entity[0];
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(0, new FleeBattleGoal(this, 1.0D));
        goalSelector.addGoal(2, new DinoMeleeAttackAI(this, 1.0, true));
        goalSelector.addGoal(6, new DinoFollowOwnerGoal(this, 1, 10, 2, true));
        goalSelector.addGoal(7, new DinoWanderGoal(this, 1.0));
        goalSelector.addGoal(8, new DinoLookAroundGoal(this));
        targetSelector.addGoal(2, new HurtByTargetGoal(this));
    }

    @Override
    public PrehistoricEntityType type() {
        return PrehistoricEntityType.PTERANODON;
    }

    @Override
    public Item getOrderItem() {
        return Items.STICK;
    }

    @Override
    public EntityDataManager.Data data() {
        return data;
    }

    @Override
    public @NotNull ServerAnimationInfo nextIdleAnimation() {
        String key = IDLE;

        if (isInWater()) {
            key = IDLE_SWIM;
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
    public Map<String, ServerAnimationInfo> getAllAnimations() {
        return allAnimations.get();
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
    public ServerAnimationInfo nextChasingAnimation() {
        String key = RUN;
        if (isInWater()) key = SWIM;
        if (isFlying()) key = FLY;

        return getAllAnimations().get(key);
    }

    @Override
    public @NotNull ServerAnimationInfo nextEatingAnimation() {
        return getAllAnimations().get(IDLE);
        //return getAllAnimations().get(EAT);
    }

    @Override
    @NotNull
    public ServerAttackAnimationInfo nextAttackAnimation() {
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
