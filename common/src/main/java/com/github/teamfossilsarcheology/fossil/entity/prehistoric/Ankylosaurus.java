package com.github.teamfossilsarcheology.fossil.entity.prehistoric;

import com.github.teamfossilsarcheology.fossil.entity.ai.FleeBattleGoal;
import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationCategory;
import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationInfo;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.github.teamfossilsarcheology.fossil.entity.util.Util;
import com.github.teamfossilsarcheology.fossil.sounds.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityDimensions;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Ankylosaurus extends Prehistoric {
    public static final String ATTACK_FRONT_RIGHT = "animation.ankylosaurus.attack_strong_right";
    public static final String ATTACK_FRONT_LEFT = "animation.ankylosaurus.attack_strong_left";

    public Ankylosaurus(EntityType<Ankylosaurus> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(Util.IMMOBILE + 3, new FleeBattleGoal(this, 1));
    }

    @Override
    public @NotNull EntityDimensions getDimensions(Pose poseIn) {
        if (poseIn == Pose.SLEEPING) {
            return super.getDimensions(poseIn).scale(1, 0.7f);
        }
        return super.getDimensions(poseIn);
    }

    @Override
    public PrehistoricEntityInfo info() {
        return PrehistoricEntityInfo.ANKYLOSAURUS;
    }

    @Override
    public Item getOrderItem() {
        return Items.BONE;
    }

    @Override
    public AnimationInfo getAnimation(AnimationCategory category) {
        if (category == AnimationCategory.ATTACK) {
            if (getTarget() != null) {
                double x = getTarget().getX() - getX();
                double z = getTarget().getZ() - getZ();
                double yawDiff = (Mth.atan2(z, x) * Mth.RAD_TO_DEG);
                float yRotD = Mth.degreesDifference(yBodyRot, Util.yawToYRot(yawDiff));
                return getAllAnimations().get(yRotD < 0 ? ATTACK_FRONT_RIGHT : ATTACK_FRONT_LEFT);
            }
        }
        return super.getAnimation(category);
    }

    @Override
    public @NotNull AnimationInfo nextAttackAnimation() {
        if (getTarget() != null) {
            double x = getTarget().getX() - getX();
            double z = getTarget().getZ() - getZ();
            double yawDiff = (Mth.atan2(z, x) * Mth.RAD_TO_DEG);
            float yRotD = Mth.degreesDifference(yBodyRot, Util.yawToYRot(yawDiff));
            return getAllAnimations().get(yRotD < 0 ? ATTACK_FRONT_RIGHT : ATTACK_FRONT_LEFT);
        }
        /*Vec3d right = getRotationVector().rotateY(90 * MathHelper.RADIANS_PER_DEGREE).normalize();
        if(Math.abs(right.dotProduct(entity.getPos().subtract(getPos()))) > 1.75f)
            return false;*/
        return getAllAnimations().get(ATTACK_FRONT_RIGHT);
    }
    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.ANKYLOSAURUS_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.ANKYLOSAURUS_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.ANKYLOSAURUS_DEATH.get();
    }
}