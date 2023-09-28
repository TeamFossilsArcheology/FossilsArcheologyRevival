package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.entity.ai.*;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.sounds.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class Pachyrhinosaurus extends Prehistoric {
    public static final String ANIMATIONS = "pachyrhinosaurus.animation.json";
    public static final String IDLE = "animation.pachyrhinosaurus.idle";
    public static final String EAT = "animation.pachyrhinosaurus.eat";
    public static final String WALK = "animation.pachyrhinosaurus.walk";
    public static final String SWIM = "animation.pachyrhinosaurus.swim";
    public static final String RUN = "animation.pachyrhinosaurus.run";
    public static final String ATTACK1 = "animation.pachyrhinosaurus.attack1";
    public static final String ATTACK2 = "animation.pachyrhinosaurus.attack2";
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public Pachyrhinosaurus(EntityType<Pachyrhinosaurus> entityType, Level level) {
        super(entityType, level, false);
    }

    @Override
    public Entity[] getCustomParts() {
        return new Entity[0];
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(0, new DinoMeleeAttackGoal(this, 1, false));
        goalSelector.addGoal(1, new FloatGoal(this));
        goalSelector.addGoal(3, new DinoWanderGoal(this, 1));
        goalSelector.addGoal(6, new DinoFollowOwnerGoal(this, 1, 10, 2, false));
        goalSelector.addGoal(7, new DinoLookAroundGoal(this));
        targetSelector.addGoal(1, new DinoOwnerHurtByTargetGoal(this));
        targetSelector.addGoal(2, new DinoOwnerHurtTargetGoal(this));
        targetSelector.addGoal(3, new DinoHurtByTargetGoal(this));
    }

    @Override
    public PrehistoricEntityType type() {
        return PrehistoricEntityType.PACHYRHINOSAURUS;
    }

    @Override
    public Item getOrderItem() {
        return Items.STICK;
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
        boolean isChasing = goalSelector.getRunningGoals().anyMatch(it -> it.getGoal() instanceof DinoMeleeAttackGoal);
        if (isInWater()) {
            key = SWIM;
        } else if (isChasing) {
            key = RUN;
        }

        return getAllAnimations().get(key);
    }

    @Override
    public @NotNull Animation nextChasingAnimation() {
        String key;
        if (isInWater()) {
            key = SWIM;
        } else {
            key = RUN;
        }
        return getAllAnimations().get(key);
    }

    @Override
    public @NotNull Animation nextAttackAnimation() {
        int random = getRandom().nextInt(2);
        String key;
        if (random == 0) {
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
        return ModSounds.PACHYCEPHALOSAURUS_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.PACHYCEPHALOSAURUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.PACHYCEPHALOSAURUS_DEATH.get();
    }
}