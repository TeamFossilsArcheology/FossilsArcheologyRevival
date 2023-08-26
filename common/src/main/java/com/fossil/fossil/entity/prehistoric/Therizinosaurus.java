package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.entity.ai.*;
import com.fossil.fossil.entity.animation.AnimationManager;
import com.fossil.fossil.entity.data.EntityDataManager;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityTypeAI;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.FloatGoal;
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

// TODO Accurately adjust values here, for now setting it identical to Triceratops
public class Therizinosaurus extends Prehistoric {
    public static final String ANIMATIONS = "fa.therizinosaurus.animations.json";
    public static final String IDLE = "fa.therizinosaurus.idle";
    public static final String WALK = "fa.therizinosaurus.walk";
    public static final String SLEEP = "fa.therizinosaurus.sleep";
    public static final String SLEEP_BABY = "fa.therizinosaurus.sleep_baby";
    public static final String THREAT = "fa.therizinosaurus.threat";
    public static final String ATTACK1 = "fa.therizinosaurus.attack1";
    public static final String ATTACK2 = "fa.therizinosaurus.attack2";
    public static final String EAT = "fa.therizinosaurus.eat";
    private static final LazyLoadedValue<Map<String, ServerAnimationInfo>> allAnimations = new LazyLoadedValue<>(() -> {
        Map<String, ServerAnimationInfo> newMap = new HashMap<>();
        List<AnimationManager.Animation> animations = AnimationManager.ANIMATIONS.getAnimation(ANIMATIONS);
        for (AnimationManager.Animation animation : animations) {
            ServerAnimationInfo info;
            switch (animation.animationId()) {
                case ATTACK1 -> info = new ServerAttackAnimationInfo(animation, ATTACKING_PRIORITY, 12);
                case ATTACK2 -> info = new ServerAttackAnimationInfo(animation, ATTACKING_PRIORITY, 12, 25);
                case IDLE -> info = new ServerAnimationInfo(animation, IDLE_PRIORITY);
                case WALK -> info = new ServerAnimationInfo(animation, MOVING_PRIORITY);
                default -> info = new ServerAnimationInfo(animation, DEFAULT_PRIORITY);
            }
            newMap.put(animation.animationId(), info);
        }
        return newMap;
    });
    private static final EntityDataManager.Data data = EntityDataManager.ENTITY_DATA.getData("therizinosaurus");
    public final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public Therizinosaurus(EntityType<Therizinosaurus> entityType, Level level) {
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
        goalSelector.addGoal(1, new DinoMeleeAttackAI(this, 1.0, true));
        goalSelector.addGoal(1, new FloatGoal(this));
        goalSelector.addGoal(5, new DinoFollowOwnerGoal(this, 1, 10, 2, false));
        goalSelector.addGoal(7, new DinoWanderGoal(this, 1.0));
        goalSelector.addGoal(8, new DinoLookAroundGoal(this));
        targetSelector.addGoal(1, new DinoOwnerHurtByTargetGoal(this));
        targetSelector.addGoal(2, new DinoOwnerHurtTargetGoal(this));
        targetSelector.addGoal(2, new HurtByTargetGoal(this));
        targetSelector.addGoal(4, new HuntGoal(this));
    }

    @Override
    public PrehistoricEntityType type() {
        return PrehistoricEntityType.THERIZINOSAURUS;
    }

    @Override
    public PrehistoricEntityTypeAI.Response aiResponseType() {
        return this.isBaby() ? PrehistoricEntityTypeAI.Response.SCARED : PrehistoricEntityTypeAI.Response.TERRITORIAL;
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
    public EntityDataManager.Data data() {
        return data;
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
    public @NotNull ServerAnimationInfo nextEatingAnimation() {
        return getAllAnimations().get(EAT);
    }

    @Override
    @NotNull
    public Prehistoric.ServerAttackAnimationInfo nextAttackAnimation() {
        String key;

        if (getRandom().nextBoolean()) {
            key = ATTACK1;
        } else {
            key = ATTACK2;
        }

        return (ServerAttackAnimationInfo) allAnimations.get().get(key);
    }
}
