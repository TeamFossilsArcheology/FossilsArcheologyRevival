package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.entity.ai.*;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricScary;
import com.fossil.fossil.item.ModItems;
import com.fossil.fossil.sounds.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class Tyrannosaurus extends Prehistoric implements PrehistoricScary {
    public static final String ANIMATIONS = "tyrannosaurus.animation.json";
    public static final String IDLE = "animation.tyrannosaurus.idle";
    public static final String SIT1 = "animation.tyrannosaurus.sit1";
    public static final String SIT2 = "animation.tyrannosaurus.sit2";
    public static final String SLEEP1 = "animation.tyrannosaurus.sleep1";
    public static final String SLEEP2 = "animation.tyrannosaurus.sleep2";
    public static final String WALK = "animation.tyrannosaurus.walk";
    public static final String RUN = "animation.tyrannosaurus.run";
    public static final String JUMP_FALL = "animation.tyrannosaurus.jump/fall";
    public static final String SWIM = "animation.tyrannosaurus.swim";
    public static final String EAT = "animation.tyrannosaurus.eat";
    public static final String TURN_RIGHT = "animation.tyrannosaurus.turn_right";
    public static final String TURN_LEFT = "animation.tyrannosaurus.turn_left";
    public static final String SPEAK = "animation.tyrannosaurus.speak";
    public static final String CALL1 = "animation.tyrannosaurus.call1";
    public static final String ATTACK_NORMAL1 = "animation.tyrannosaurus.attack_normal1";
    public static final String ATTACK_NORMAL2 = "animation.tyrannosaurus.attack_normal2";
    
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public Tyrannosaurus(EntityType<Tyrannosaurus> entityType, Level level) {
        super(entityType, level, false);
    }

    @Override
    public Entity[] getCustomParts() {
        return new Entity[0];
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(0, new DinoMeleeAttackGoal(this, 1.5, false));
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
        return PrehistoricEntityType.TYRANNOSAURUS;
    }

    @Override
    public Item getOrderItem() {
        return ModItems.SKULL_STICK.get();
    }

    @Override
    public float getTargetScale() {
        return 2;
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
        return getAllAnimations().get(WALK);
    }

    @Override
    public @NotNull Animation nextChasingAnimation() {
        return getAllAnimations().get(RUN);
    }

    @Override
    public @NotNull Animation nextAttackAnimation() {
        return getAllAnimations().get(ATTACK_NORMAL1);
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return isWeak() ? ModSounds.TYRANNOSAURUS_WEAK.get() : ModSounds.TYRANNOSAURUS_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.TYRANNOSAURUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.TYRANNOSAURUS_DEATH.get();
    }
}
