package com.fossil.fossil.world.feature.tree;

import com.fossil.fossil.world.feature.configuration.ModConfiguredFeatures;
import net.minecraft.core.Holder;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class SigillariaTreeGrower extends AbstractTreeGrower {
    @Nullable
    @Override
    protected Holder<? extends ConfiguredFeature<?, ?>> getConfiguredFeature(Random random, boolean largeHive) {
        return ModConfiguredFeatures.SIGILLARIA_TREE;
    }
}
