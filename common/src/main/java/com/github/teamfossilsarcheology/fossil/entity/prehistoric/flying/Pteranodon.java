package com.github.teamfossilsarcheology.fossil.entity.prehistoric.flying;

import com.github.teamfossilsarcheology.fossil.entity.ai.DelayedAttackGoal;
import com.github.teamfossilsarcheology.fossil.entity.ai.DinoHurtByTargetGoal;
import com.github.teamfossilsarcheology.fossil.entity.ai.DinoOwnerHurtByTargetGoal;
import com.github.teamfossilsarcheology.fossil.entity.ai.DinoOwnerHurtTargetGoal;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricFlying;
import com.github.teamfossilsarcheology.fossil.entity.util.Util;
import com.github.teamfossilsarcheology.fossil.sounds.ModSounds;
import com.github.teamfossilsarcheology.fossil.util.Gender;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.builder.Animation;

public class Pteranodon extends PrehistoricFlying {
    public static final String ANIMATIONS = "pteranodon.animation.json";
    public static final String ATTACK = "animation.pteranodon.attack";
    public static final String DIVE = "animation.pteranodon.dive";
    public static final String EAT = "animation.pteranodon.eat";
    public static final String FLY = "animation.pteranodon.fly";
    public static final String FLY_FLAST = "animation.pteranodon.flyfast";
    public static final String HOVER = "animation.pteranodon.hover";
    public static final String IDLE = "animation.pteranodon.idle";
    public static final String IDLE_SWIM = "animation.pteranodon.swimidle";
    public static final String SIT = "animation.pteranodon.sit";
    public static final String SLEEP = "animation.pteranodon.sleep";
    public static final String SWIM = "animation.pteranodon.swim";
    public static final String TAKEOFF = "animation.pteranodon.takeoff";
    public static final String WALK = "animation.pteranodon.walk";

    public Pteranodon(EntityType<Pteranodon> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        goalSelector.addGoal(Util.ATTACK, new DelayedAttackGoal(this, 1, false));
        targetSelector.addGoal(1, new DinoOwnerHurtByTargetGoal(this));
        targetSelector.addGoal(2, new DinoOwnerHurtTargetGoal(this));
        targetSelector.addGoal(3, new DinoHurtByTargetGoal(this));
    }

    @Override
    public PrehistoricEntityInfo info() {
        return PrehistoricEntityInfo.PTERANODON;
    }

    @Override
    public Item getOrderItem() {
        return Items.ARROW;
    }

    @Override
    public float getGenderedScale() {
        return getGender() == Gender.MALE ? 1.3f : super.getGenderedScale();
    }


    @Override
    public void aiStep() {
        super.aiStep();
        if (isInWater() && !isFlying()) {
            startTakeOff();
        }
    }

    @Override
    public @Nullable Vec3 generateAirTarget() {
        isHungry();
        if (false) {
            BlockPos groundPos = blockPosition();
            while (groundPos.getY() > 3 && level.isEmptyBlock(groundPos)) {
                groundPos = groundPos.below();
            }
            for (int i = 0; i < 10; i++) {
                BlockPos checkForWaterPos = groundPos.offset(random.nextInt(16) - 8, 0, random.nextInt(16) - 8);
                if (level.getFluidState(checkForWaterPos).is(FluidTags.WATER)) {
                    return Vec3.atCenterOf(checkForWaterPos.above());
                }
            }
        }
        return super.generateAirTarget();
    }

    @Override
    public void onReachAirTarget(BlockPos target) {
        if (level.getFluidState(target.below()).is(FluidTags.WATER) && isHungry()) {
            ItemStack stack;
            if (random.nextInt(2) == 0) {
                stack = new ItemStack(Items.COD, 1);
            } else {
                stack = new ItemStack(Items.SALMON, 1);
            }
            spawnAtLocation(stack, 1);
            playSound(SoundEvents.GENERIC_SWIM, 0.7f, 1 + random.nextFloat() * 0.4f);
            playSound(SoundEvents.GENERIC_SWIM, 0.4f, 1 + random.nextFloat() * 0.4f);
            if (level.isClientSide) {
                for (int i = 0; i < 20; ++i) {
                    double motionX = getRandom().nextGaussian() * 0.02D;
                    double motionY = getRandom().nextGaussian() * 0.02D;
                    double motionZ = getRandom().nextGaussian() * 0.02D;
                    double x = target.below().getX() + 0.5 + random.nextFloat() * getBbWidth() * 2 - getBbWidth() - motionX * 10;
                    double y = target.below().getY() + 1 + random.nextFloat() * getBbHeight() - motionY * 10;
                    double z = target.below().getZ() + 0.5 + random.nextFloat() * getBbWidth() * 2 - getBbWidth() - motionZ * 10;
                    level.addParticle(ParticleTypes.HAPPY_VILLAGER, x, y, z, motionX, motionY, motionZ);
                }
            }
        }
    }

    @Override
    public @NotNull Animation nextAttackAnimation() {
        return getAllAnimations().get(ATTACK);
    }

    @Override
    public @NotNull Animation nextEatingAnimation() {
        return getAllAnimations().get(EAT);
    }

    @Override
    public @NotNull Animation nextIdleAnimation() {
        String key = IDLE;

        if (isInWater()) {
            key = IDLE_SWIM;
        } else if (isFlying()) {
            key = HOVER;
        }

        return getAllAnimations().get(key);
    }

    @Override
    public @NotNull Animation nextSittingAnimation() {
        return getAllAnimations().get(SIT);
    }

    @Override
    public @NotNull Animation nextSleepingAnimation() {
        return getAllAnimations().get(SLEEP);
    }

    @Override
    public @NotNull Animation nextWalkingAnimation() {
        String key = WALK;
        if (isInWater()) key = SWIM;
        if (isFlying()) key = FLY;

        return getAllAnimations().get(key);
    }

    @Override
    public @NotNull Animation nextSprintingAnimation() {
        String key = WALK;
        if (isInWater()) key = SWIM;
        if (isFlying()) key = FLY_FLAST;

        return getAllAnimations().get(key);
    }

    @Override
    public @NotNull Animation nextTakeOffAnimation() {
        return getAllAnimations().get(TAKEOFF);
    }

    @Nullable
    @Override
    protected SoundEvent getAmbientSound() {
        return ModSounds.PTERANODON_AMBIENT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getHurtSound(DamageSource damageSource) {
        return ModSounds.PTERANODON_HURT.get();
    }

    @Nullable
    @Override
    protected SoundEvent getDeathSound() {
        return ModSounds.PTERANODON_DEATH.get();
    }
}
