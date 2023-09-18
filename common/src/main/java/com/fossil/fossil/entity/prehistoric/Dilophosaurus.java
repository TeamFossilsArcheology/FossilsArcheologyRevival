package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.entity.ai.*;
import com.fossil.fossil.entity.data.EntityDataManager;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricScary;
import com.fossil.fossil.sounds.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class Dilophosaurus extends Prehistoric implements PrehistoricScary {
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
    private static final EntityDataManager.Data data = EntityDataManager.ENTITY_DATA.getData("dilophosaurus");
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public Dilophosaurus(EntityType<Dilophosaurus> type, Level level) {
        super(type, level, false);
    }

    @Override
    public Entity[] getCustomParts() {
        return new Entity[0];
    }

    @Override
    public void registerGoals() {
        super.registerGoals();
        double speed = getAttributeValue(Attributes.MOVEMENT_SPEED);
        goalSelector.addGoal(1, new DinoMeleeAttackAI(this, speed, false));
        goalSelector.addGoal(1, new FloatGoal(this));
        goalSelector.addGoal(3, new DinoWanderGoal(this, speed));
        goalSelector.addGoal(6, new DinoFollowOwnerGoal(this, 1, 10, 2, false));
        goalSelector.addGoal(7, new DinoLookAroundGoal(this));
        targetSelector.addGoal(1, new DinoOwnerHurtByTargetGoal(this));
        targetSelector.addGoal(2, new DinoOwnerHurtTargetGoal(this));
        targetSelector.addGoal(3, new HurtByTargetGoal(this));
    }

    @Override
    public PrehistoricEntityType type() {
        return PrehistoricEntityType.DILOPHOSAURUS;
    }

    @Override
    public Item getOrderItem() {
        return Items.BONE;
    }

    @Override
    public double getPassengersRidingOffset() {
        return super.getPassengersRidingOffset() + 0.1;
    }

    @Override
    public @NotNull Animation nextEatingAnimation() {
        return getAllAnimations().get(EAT);
    }

    @Override
    public @NotNull Animation nextIdleAnimation() {
        return getAllAnimations().get(IDLE);
    }

    @Override
    public @NotNull Animation nextMovingAnimation() {
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
    public @NotNull Animation nextChasingAnimation() {
        String key = RUN;
        if (isInWater()) {
            key = SWIM;
        }
        return getAllAnimations().get(key);
    }

    @Override
    public @NotNull Animation nextAttackAnimation() {
        String key;
        if (getRandom().nextInt(2) == 0) {
            key = ATTACK1;
        } else {
            key = ATTACK2;
        }
        return getAllAnimations().get(key);
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.DILOPHOSAURUS_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.DILOPHOSAURUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.DILOPHOSAURUS_DEATH.get();
    }
}