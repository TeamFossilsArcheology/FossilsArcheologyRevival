package com.fossil.fossil.world.feature.placement;

import com.fossil.fossil.config.FossilConfig;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.placement.PlacementContext;
import net.minecraft.world.level.levelgen.placement.PlacementFilter;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraft.world.level.levelgen.placement.RarityFilter;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

/**
 * Custom {@link RarityFilter} that gets its chance value from a config entry
 */
public class LazyRarityFilter extends PlacementFilter {

    public static final Codec<LazyRarityFilter> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    Codec.STRING.fieldOf("configEntry").forGetter(lazyRarityFilter -> lazyRarityFilter.configEntry))
            .apply(instance, LazyRarityFilter::new));
    public static final PlacementModifierType<LazyRarityFilter> TYPE = () -> LazyRarityFilter.CODEC;

    private final String configEntry;

    public LazyRarityFilter(String configEntry) {
        this.configEntry = configEntry;
    }

    @Override
    protected boolean shouldPlace(PlacementContext context, Random random, BlockPos pos) {
        return random.nextFloat() < 1 / (float) FossilConfig.getInt(configEntry);
    }

    @Override
    public @NotNull PlacementModifierType<?> type() {
        return TYPE;
    }
}
