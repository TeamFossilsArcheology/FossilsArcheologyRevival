package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.entity.ai.*;
import com.fossil.fossil.entity.animation.AnimationManager;
import com.fossil.fossil.entity.data.EntityDataManager;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricFlying;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricLeaping;
import com.fossil.fossil.sounds.ModSounds;
import com.fossil.fossil.util.Gender;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.LazyLoadedValue;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.FloatGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.material.Material;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Pteranodon extends PrehistoricFlying implements PrehistoricLeaping {
    public static final String ANIMATIONS = "pteranodon.animation.json";
    public static final String IDLE = "animation.dilophosaurus.idle";
    public static final String ATTACK1 = "animation.dilophosaurus.attack1";
    private static final LazyLoadedValue<Map<String, ServerAnimationInfo>> allAnimations = new LazyLoadedValue<>(() -> {
        Map<String, ServerAnimationInfo> newMap = new HashMap<>();
        List<AnimationManager.Animation> animations = AnimationManager.ANIMATIONS.getAnimation(ANIMATIONS);
        for (AnimationManager.Animation animation : animations) {
            ServerAnimationInfo info;
            switch (animation.animationId()) {
                case ATTACK1 -> info = new ServerAttackAnimationInfo(animation, ATTACKING_PRIORITY, animation.attackDelay());
                case IDLE -> info = new ServerAnimationInfo(animation, IDLE_PRIORITY);
                default -> info = new ServerAnimationInfo(animation, DEFAULT_PRIORITY);
            }
            newMap.put(animation.animationId(), info);
        }
        return newMap;
    });
    private static final EntityDataManager.Data data = EntityDataManager.ENTITY_DATA.getData("pteranodon");
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
        goalSelector.addGoal(0, new DinoMeleeAttackAI(this, 1, false));
        goalSelector.addGoal(1, new FloatGoal(this));
        goalSelector.addGoal(3, new DinoWanderGoal(this, 1));
        goalSelector.addGoal(7, new DinoFollowOwnerGoal(this, 1, 10, 2, false));
        goalSelector.addGoal(8, new DinoLookAroundGoal(this));
        targetSelector.addGoal(1, new DinoOwnerHurtByTargetGoal(this));
        targetSelector.addGoal(2, new DinoOwnerHurtTargetGoal(this));
        targetSelector.addGoal(3, new HurtByTargetGoal(this));
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
    protected float getGenderedScale() {
        return getGender() == Gender.MALE ? 1.3f : super.getGenderedScale();
    }


    @Override
    public EntityDataManager.Data data() {
        return data;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        if (isInWater() && !isFlying()) {
            startFlying();
        }
    }

    @Override
    public @Nullable BlockPos generateAirTarget() {
        if (isHungry()) {
            BlockPos groundPos = blockPosition();
            while (groundPos.getY() > 3 && level.isEmptyBlock(groundPos)) {
                groundPos = groundPos.below();
            }
            for (int i = 0; i < 10; i++) {
                BlockPos checkForWaterPos = groundPos.offset(random.nextInt(16) - 8, 0, random.nextInt(16) - 8);
                if (level.getBlockState(checkForWaterPos).getMaterial() == Material.WATER) {
                    return checkForWaterPos.above();
                }
            }
        }
        return super.generateAirTarget();
    }

    @Override
    protected void onReachAirTarget(BlockPos target) {
        if (level.getBlockState(target.below()).getMaterial() == Material.WATER && isHungry()) {
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
    public Map<String, ServerAnimationInfo> getAllAnimations() {
        return allAnimations.get();
    }

    @Override
    public @NotNull ServerAnimationInfo nextEatingAnimation() {
        return getAllAnimations().get(IDLE);
    }

    @Override
    public @NotNull ServerAnimationInfo nextIdleAnimation() {
        return getAllAnimations().get(IDLE);
    }

    @Override
    public @NotNull ServerAnimationInfo nextMovingAnimation() {
        return getAllAnimations().get(IDLE);
    }

    @Override
    public @NotNull Prehistoric.ServerAnimationInfo nextChasingAnimation() {
        return getAllAnimations().get(IDLE);
    }

    @Override
    public @NotNull Prehistoric.ServerAttackAnimationInfo nextAttackAnimation() {
        return (ServerAttackAnimationInfo) getAllAnimations().get(ATTACK1);
    }

    @Override
    public @NotNull Prehistoric.ServerAttackAnimationInfo nextLeapAnimation() {
        return (ServerAttackAnimationInfo) getAllAnimations().get(ATTACK1);
    }

    @Override
    public ServerAnimationInfo getTakeOffAnimation() {
        return null;
    }

    @Override
    public boolean isFlying() {
        return false;
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
