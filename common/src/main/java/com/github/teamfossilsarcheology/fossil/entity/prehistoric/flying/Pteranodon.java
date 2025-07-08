package com.github.teamfossilsarcheology.fossil.entity.prehistoric.flying;

import com.github.teamfossilsarcheology.fossil.entity.ai.DinoHurtByTargetGoal;
import com.github.teamfossilsarcheology.fossil.entity.ai.DinoOwnerHurtByTargetGoal;
import com.github.teamfossilsarcheology.fossil.entity.ai.DinoOwnerHurtTargetGoal;
import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationInfo;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricFlying;
import com.github.teamfossilsarcheology.fossil.sounds.ModSounds;
import com.github.teamfossilsarcheology.fossil.util.Gender;
import net.minecraft.core.BlockPos;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class Pteranodon extends PrehistoricFlying {
    public static final String TAKEOFF = "animation.pteranodon.takeoff";

    public Pteranodon(EntityType<Pteranodon> entityType, Level level) {
        super(entityType, level);
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
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
    public void onReachAirTarget(BlockPos target) {
        /*if (level().getFluidState(target.below()).is(FluidTags.WATER) && isHungry()) {
            ItemStack stack;
            if (random.nextInt(2) == 0) {
                stack = new ItemStack(Items.COD, 1);
            } else {
                stack = new ItemStack(Items.SALMON, 1);
            }
            spawnAtLocation(stack, 1);
            playSound(SoundEvents.GENERIC_SWIM, 0.7f, 1 + random.nextFloat() * 0.4f);
            playSound(SoundEvents.GENERIC_SWIM, 0.4f, 1 + random.nextFloat() * 0.4f);
            if (level().isClientSide) {
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
        }*/
    }

    @Override
    public @NotNull AnimationInfo nextTakeOffAnimation() {
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
