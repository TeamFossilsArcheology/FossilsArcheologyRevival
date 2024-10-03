package com.github.teamfossilsarcheology.fossil.world.feature.placement;

import com.github.teamfossilsarcheology.fossil.config.FossilConfig;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.levelgen.placement.CountPlacement;
import net.minecraft.world.level.levelgen.placement.PlacementModifierType;
import net.minecraft.world.level.levelgen.placement.RepeatingPlacement;
import org.jetbrains.annotations.NotNull;

import java.util.Random;

/**
 * Custom {@link CountPlacement} that gets its count value
 * from a config entry
 */
public class LazyCountPlacement extends RepeatingPlacement {

    public static final Codec<LazyCountPlacement> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    Codec.STRING.fieldOf("configEntry").forGetter(lazyCountPlacement -> lazyCountPlacement.configEntry))
            .apply(instance, LazyCountPlacement::new));
    public static final PlacementModifierType<LazyCountPlacement> TYPE = () -> LazyCountPlacement.CODEC;

    private final String configEntry;

    public LazyCountPlacement(String configEntry) {
        this.configEntry = configEntry;
    }

    @Override
    protected int count(Random random, BlockPos pos) {
        return FossilConfig.getInt(configEntry);
    }

    @Override
    public @NotNull PlacementModifierType<?> type() {
        return TYPE;
    }
}
