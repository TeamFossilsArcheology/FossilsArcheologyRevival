package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricFish;
import com.fossil.fossil.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.manager.AnimationFactory;
import software.bernie.geckolib3.util.GeckoLibUtil;

import java.util.Random;

public class Coelacanth extends PrehistoricFish {
    public static final String ANIMATIONS = "coelacanth.animation.json";
    public static final String IDLE = "animation.coelacanth.idle";
    public static final String BEACHED = "animation.coelacanth.land";
    public static final String SWIM = "animation.coelacanth.swim";
    public static final String SWIM_FAST = "animation.coelacanth.swim_fast";
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public Coelacanth(EntityType<Coelacanth> entityType, Level level) {
        super(entityType, level);
    }

    public static boolean canCoelacanthSpawn(EntityType<Coelacanth> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, Random rand) {
        return pos.getY() < 35 && PrehistoricFish.canSpawn(entityType, level, spawnType, pos, rand);
    }

    @Override
    public @NotNull ItemStack getBucketItemStack() {
        return new ItemStack(ModItems.COELACANTH_BUCKET.get());
    }

    @Override
    public @NotNull PrehistoricEntityInfo info() {
        return PrehistoricEntityInfo.COELACANTH;
    }

    @Override
    public @NotNull Animation nextBeachedAnimation() {
        return getAllAnimations().get(BEACHED);
    }

    @Override
    public @NotNull Animation nextIdleAnimation() {
        return getAllAnimations().get(IDLE);
    }

    @Override
    public @NotNull Animation nextMovingAnimation() {
        return getAllAnimations().get(SWIM);
    }

    @Override
    public @NotNull Animation nextSprintingAnimation() {
        return getAllAnimations().get(SWIM_FAST);
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}