package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.entity.ai.*;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricFlying;
import com.fossil.fossil.sounds.ModSounds;
import com.fossil.fossil.util.Gender;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

public class Pteranodon extends PrehistoricFlying {
    public static final String ANIMATIONS = "pteranodon.animation.json";
    public static final String FLY = "fa.tropeognathus.fly";
    public static final String GROUND_TAKEOFF = "fa.tropeognathus.groundtakeoff";
    public static final String RUN = "fa.tropeognathus.run";
    public static final String WALK = "fa.tropeognathus.walk";
    public static final String BITE_EAT = "fa.tropeognathus.biteeat";
    public static final String BITE_ATTACK = "fa.tropeognathus.biteattack";
    public static final String BITE_EAT_IN_WATER = "fa.tropeognathus.biteeatwater";
    public static final String IDLE_SWIM = "fa.tropeognathus.idleswim";
    public static final String SWIM = "fa.tropeognathus.swim";
    public static final String BITE_ATTACK_WATER = "fa.tropeognathus.biteattackwater";
    public static final String BITE_IN_AIR = "fa.tropeognathus.bitefly";
    public static final String DISPLAY = "fa.tropeognathus.display";
    public static final String IDLE = "fa.tropeognathus.idle";
    public static final String IDLE_PREEN = "fa.tropeognathus.idlepreen";
    public static final String IDLE_CALL = "fa.tropeognathus.idlecall";
    public static final String IDLE_LOOKAROUND = "fa.tropeognathus.idlelookaround";
    public static final String WATER_TAKEOFF = "fa.tropeognathus.watertakeoff";
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public Pteranodon(EntityType<Pteranodon> entityType, Level level) {
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
        goalSelector.addGoal(6, new DinoFollowOwnerGoal(this, 1, 10, 2, false));
        goalSelector.addGoal(8, new DinoLookAroundGoal(this));
        targetSelector.addGoal(1, new DinoOwnerHurtByTargetGoal(this));
        targetSelector.addGoal(2, new DinoOwnerHurtTargetGoal(this));
        targetSelector.addGoal(3, new DinoHurtByTargetGoal(this));
    }

    @Override
    public PrehistoricEntityType type() {
        return PrehistoricEntityType.PTERANODON;
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
        if (isHungry() && false) {
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
    public @NotNull Animation nextIdleAnimation() {
        String key = IDLE;

        if (isInWater()) {
            key = IDLE_SWIM;
        } else if (isFlying()) {
            key = FLY;
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
    public @NotNull Animation nextMovingAnimation() {
        String key = WALK;
        boolean isChasing = goalSelector.getRunningGoals().anyMatch(it -> it.getGoal() instanceof DinoMeleeAttackGoal);

        if (isChasing) key = RUN;
        if (isInWater()) key = SWIM;
        if (isFlying() || !isOnGround()) key = FLY;

        return getAllAnimations().get(key);
    }

    @Override
    public @NotNull Animation nextChasingAnimation() {
        String key = RUN;
        if (isInWater()) key = SWIM;
        if (isFlying()) key = FLY;

        return getAllAnimations().get(key);
    }

    @Override
    public @NotNull Animation nextEatingAnimation() {
        return getAllAnimations().get(IDLE);
    }

    @Override
    public @NotNull Animation nextAttackAnimation() {
        String key = BITE_ATTACK;
        if (isInWater()) key = BITE_ATTACK_WATER;
        if (isFlying()) key = BITE_IN_AIR;

        return getAllAnimations().get(key);
    }

    @Override
    public @NotNull Animation nextTakeOffAnimation() {
        String key = GROUND_TAKEOFF;
        if (isInWater()) key = WATER_TAKEOFF;

        return getAllAnimations().get(key);
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
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
