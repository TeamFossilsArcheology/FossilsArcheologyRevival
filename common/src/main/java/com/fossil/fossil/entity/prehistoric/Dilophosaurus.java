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
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
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

public class Dilophosaurus extends Prehistoric implements IScaryDinosaur {
    public final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public static final String ANIMATIONS = "dilophosaurus.animation.json";
    public static final String IDLE = "animation.dilophosaurus.idle";
    public static final String SIT1 = "animation.dilophosaurus.sit1";
    public static final String SIT2 = "animation.dilophosaurus.sit2";
    public static final String SLEEP1 = "animation.dilophosaurus.sleep1";
    public static final String SLEEP2 = "animation.dilophosaurus.sleep2";
    public static final String WALK = "animation.dilophosaurus.walk";
    public static final String RUN = "animation.dilophosaurus.run";
    public static final String JUMP_FALL = "animation.dilophosaurus.jump/fall";
    public static final String SWIM = "animation.dilophosaurus.swim";
    public static final String EAT = "animation.dilophosaurus.eat";
    public static final String INFLATE_START = "animation.dilophosaurus.inflate_start";
    public static final String INFLATE_HOLD = "animation.dilophosaurus.inflate_hold";
    public static final String INFLATE_END = "animation.dilophosaurus.inflate_end";
    public static final String TURN_RIGHT = "animation.dilophosaurus.turn_right";
    public static final String TURN_LEFT = "animation.dilophosaurus.turn_left";
    public static final String SPEAK = "animation.dilophosaurus.speak";
    public static final String CALL = "animation.dilophosaurus.call";
    public static final String ATTACK1 = "animation.dilophosaurus.attack1";
    public static final String ATTACK2 = "animation.dilophosaurus.attack2";

    private static final LazyLoadedValue<Map<String, ServerAnimationInfo>> allAnimations = new LazyLoadedValue<>(() -> {
        var file = GeckoLibCache.getInstance().getAnimations().get(new ResourceLocation(Fossil.MOD_ID, "animations/" + ANIMATIONS));
        Map<String, ServerAnimationInfo> newMap = new HashMap<>();
        file.animations().forEach((key, value) -> {
            ServerAnimationInfo info;
            switch (key) {
                case ATTACK1, ATTACK2 -> info = new ServerAttackAnimationInfo(value, ATTACKING_PRIORITY, 12);
                case IDLE -> info = new ServerAnimationInfo(value, IDLE_PRIORITY);
                case WALK, RUN, SWIM -> info = new ServerAnimationInfo(value, MOVING_PRIORITY);
                default -> info = new ServerAnimationInfo(value, DEFAULT_PRIORITY);
            }
            newMap.put(key, info);
        });
        return newMap;
    });
    public Dilophosaurus(EntityType<Dilophosaurus> type, Level level) {
        super(type, PrehistoricEntityType.DILOPHOSAURUS, level, false, false, 0.18f, 1.4f, 0, 0, 4, 8, 1, 8, 8, 40, 0.25, 0.35, 0, 5);
    }

    @Override
    public Entity[] getCustomParts() {
        return new Entity[0];
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        double speed = getAttributeValue(Attributes.MOVEMENT_SPEED);
        goalSelector.addGoal(0, new FleeBattleGoal(this, 1));
        goalSelector.addGoal(1, new DinoMeleeAttackAI(this, speed, false));
        goalSelector.addGoal(1, new FloatGoal(this));
        goalSelector.addGoal(3, new DinoWanderGoal(this, speed));
        goalSelector.addGoal(3, new EatFromFeederGoal(this));
        goalSelector.addGoal(4, new EatItemEntityGoal(this));
        goalSelector.addGoal(6, new DinoFollowOwnerGoal(this, 1, 10, 2, false));
        goalSelector.addGoal(7, new DinoLookAroundGoal(this));
        targetSelector.addGoal(3, new HurtByTargetGoal(this));
        targetSelector.addGoal(4, new HuntGoal(this));
    }
    @Override
    public PrehistoricEntityTypeAI.Activity aiActivityType() {
        return PrehistoricEntityTypeAI.Activity.DIURNAL;
    }

    @Override
    public PrehistoricEntityTypeAI.Attacking aiAttackType() {
        return PrehistoricEntityTypeAI.Attacking.CHARGE;
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
        return PrehistoricEntityTypeAI.Response.TERRITORIAL;
    }

    @Override
    public PrehistoricEntityTypeAI.Stalking aiStalkType() {
        return PrehistoricEntityTypeAI.Stalking.STEALTH;
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
    public int getMaxHunger() {
        return 100;
    }

    @Override
    public Map<String, ServerAnimationInfo> getAllAnimations() {
        return allAnimations.get();
    }

    @Override
    public Item getOrderItem() {
        return Items.BONE;
    }

    @Override
    public boolean canBeRidden() {
        return true;
    }

    @Override
    public double getPassengersRidingOffset() {
        return super.getPassengersRidingOffset() + 0.1;
    }

    @Override
    public ServerAnimationInfo nextEatingAnimation() {
        return getAllAnimations().get(EAT);
    }

    @Override
    public @NotNull ServerAnimationInfo nextIdleAnimation() {
        return getAllAnimations().get(IDLE);
    }

    @Override
    public @NotNull ServerAnimationInfo nextMovingAnimation() {
        String key = WALK;
        boolean isChasing = goalSelector.getRunningGoals().anyMatch(it -> it.getGoal() instanceof DinoMeleeAttackAI);
        if (isInWater()) {
            key = SWIM;
        } else if (isChasing) {
            key = RUN;
        }
        return getAllAnimations().get(key);
    }

    @Override
    public @NotNull Prehistoric.ServerAnimationInfo nextChasingAnimation() {
        String key = RUN;
        if (isInWater()) {
            key = SWIM;
        }
        return getAllAnimations().get(key);
    }

    @Override
    public @NotNull Prehistoric.ServerAttackAnimationInfo nextAttackAnimation() {
        String key;
        if (getRandom().nextInt(2) == 0) {
            key = ATTACK1;
        } else {
            key = ATTACK2;
        }
        return (ServerAttackAnimationInfo) getAllAnimations().get(key);
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}
