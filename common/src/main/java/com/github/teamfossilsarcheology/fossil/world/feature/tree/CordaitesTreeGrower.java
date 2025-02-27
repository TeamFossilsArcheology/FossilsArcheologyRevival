package com.github.teamfossilsarcheology.fossil.world.feature.tree;

import com.github.teamfossilsarcheology.fossil.world.feature.configuration.ModConfiguredFeatures;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.grower.AbstractTreeGrower;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;
import org.jetbrains.annotations.Nullable;

public class CordaitesTreeGrower extends AbstractTreeGrower {

    @Override
    protected @Nullable ResourceKey<ConfiguredFeature<?, ?>> getConfiguredFeature(RandomSource random, boolean hasFlowers) {
        return ModConfiguredFeatures.CORDAITES_TREE_KEY;
    }
}
