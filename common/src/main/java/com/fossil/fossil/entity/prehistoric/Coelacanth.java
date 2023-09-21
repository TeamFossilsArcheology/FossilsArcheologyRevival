package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricFish;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
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
    public static final String SWIM = "animation.coelacanth.swim";
    public static final String SWIM_FAST = "animation.coelacanth.swim_fast";
    public static final String LAND = "animation.coelacanth.land";
    private final AnimationFactory factory = GeckoLibUtil.createFactory(this);

    public Coelacanth(EntityType<Coelacanth> entityType, Level level) {
        super(entityType, level);
    }

    public static boolean canCoelacanthSpawn(EntityType<Coelacanth> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, Random rand) {
        return pos.getY() < 35 && PrehistoricFish.canSpawn(entityType, level, spawnType, pos, rand);
    }

    @Override
    public @NotNull PrehistoricEntityType type() {
        return PrehistoricEntityType.COELACANTH;
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
    public @NotNull Animation nextFloppingAnimation() {
        return getAllAnimations().get(LAND);
    }

    @Override
    public AnimationFactory getFactory() {
        return factory;
    }
}