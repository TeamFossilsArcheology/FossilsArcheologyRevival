package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.entity.ai.*;
import com.fossil.fossil.entity.data.EntityDataManager;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricSwimming;
import com.fossil.fossil.sounds.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class Liopleurodon extends PrehistoricSwimming {
    public static final String ANIMATIONS = "liopleurodon.animation.json";
    public static final String IDLE = "animation.dilophosaurus.idle";
    public static final String ATTACK1 = "animation.dilophosaurus.attack1";
    private static final EntityDataManager.Data data = EntityDataManager.ENTITY_DATA.getData("liopleurodon");
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public Liopleurodon(EntityType<Liopleurodon> entityType, Level level) {
        super(entityType, level, false);
        hasTeenTexture = false;
    }

    @Override
    public Entity[] getCustomParts() {
        return new Entity[0];
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(0, new EnterWaterWithoutTargetGoal(this, 1));
        goalSelector.addGoal(0, new DinoFollowOwnerGoal(this, 1, 10, 2, false));
        goalSelector.addGoal(1, new EnterWaterWithTargetGoal(this, 1));
        goalSelector.addGoal(1, new DinoMeleeAttackAI(this, 1, false));
        goalSelector.addGoal(3, new DinoWanderGoal(this, 1));
        goalSelector.addGoal(7, new DinoLookAroundGoal(this));
        targetSelector.addGoal(3, new HurtByTargetGoal(this));
    }

    @Override
    public PrehistoricEntityType type() {
        return PrehistoricEntityType.LIOPLEURODON;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (getTarget() != null) {
            if (canReachPrey()) {
                //
            }
        }
    }

    @Override
    public Item getOrderItem() {
        return Items.NAUTILUS_SHELL;
    }

    @Override
    protected boolean canHuntMobsOnLand() {
        return false;
    }

    @Override
    public boolean isAmphibious() {
        return false;
    }

    @Override
    public float swimSpeed() {
        return 6;
    }

    @Override
    public @NotNull Animation nextEatingAnimation() {
        return getAllAnimations().get(IDLE);
    }

    @Override
    public @NotNull Animation nextIdleAnimation() {
        return getAllAnimations().get(IDLE);
    }

    @Override
    public @NotNull Animation nextMovingAnimation() {
        return getAllAnimations().get(IDLE);
    }

    @Override
    public @NotNull Animation nextChasingAnimation() {
        return getAllAnimations().get(IDLE);
    }

    @Override
    public @NotNull Animation nextAttackAnimation() {
        return getAllAnimations().get(ATTACK1);
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return isInWater() ? ModSounds.LIOPLEURODON_AMBIENT_INSIDE.get() : ModSounds.LIOPLEURODON_AMBIENT_OUTSIDE.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.LIOPLEURODON_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.LIOPLEURODON_DEATH.get();
    }
}