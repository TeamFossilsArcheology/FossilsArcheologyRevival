package com.github.teamfossilsarcheology.fossil.world.feature.placement;

import com.github.teamfossilsarcheology.fossil.config.FossilConfig;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Vec3i;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * Custom {@link LazyRandomSpreadPlacement} that gets its spacing and separation values from a config entry
 */
public class LazyRandomSpreadPlacement extends StructurePlacement {
    public static final Codec<LazyRandomSpreadPlacement> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    Codec.STRING.fieldOf("spacingEntry").forGetter(LazyRandomSpreadPlacement::spacingEntry),
                    Codec.STRING.fieldOf("separationEntry").forGetter(LazyRandomSpreadPlacement::separationEntry),
                    RandomSpreadType.CODEC.optionalFieldOf("spread_type", RandomSpreadType.LINEAR).forGetter(LazyRandomSpreadPlacement::spreadType),
                    ExtraCodecs.NON_NEGATIVE_INT.fieldOf("salt").forGetter(LazyRandomSpreadPlacement::salt))
            .apply(instance, LazyRandomSpreadPlacement::new));

    public static final StructurePlacementType<LazyRandomSpreadPlacement> TYPE = () -> LazyRandomSpreadPlacement.CODEC;
    private final String spacingEntry;
    private final String separationEntry;
    private final RandomSpreadType spreadType;

    public LazyRandomSpreadPlacement(String spacingEntry, String separationEntry, RandomSpreadType spreadType, int salt) {
        super(Vec3i.ZERO, FrequencyReductionMethod.DEFAULT, 1, salt, Optional.empty());
        this.spacingEntry = spacingEntry;
        this.separationEntry = separationEntry;
        this.spreadType = spreadType;
    }

    public String spacingEntry() {
        return this.spacingEntry;
    }

    public String separationEntry() {
        return this.separationEntry;
    }

    public RandomSpreadType spreadType() {
        return this.spreadType;
    }

    public ChunkPos getPotentialFeatureChunk(long seed, int x, int z) {
        int spacing = FossilConfig.getInt(spacingEntry);
        int separation = FossilConfig.getInt(separationEntry);
        int m = Math.floorDiv(x, spacing);
        int n = Math.floorDiv(z, spacing);
        WorldgenRandom worldgenRandom = new WorldgenRandom(new LegacyRandomSource(0L));
        worldgenRandom.setLargeFeatureWithSalt(seed, m, n, this.salt());
        int o = spacing - separation;
        int p = spreadType().evaluate(worldgenRandom, o);
        int q = spreadType().evaluate(worldgenRandom, o);
        return new ChunkPos(m * spacing + p, n * spacing + q);
    }

    @Override
    protected boolean isPlacementChunk(ChunkGenerator generator, RandomState randomState, long seed, int x, int z) {
        ChunkPos chunkPos = getPotentialFeatureChunk(seed, x, z);
        return chunkPos.x == x && chunkPos.z == z;
    }

    @Override
    public @NotNull StructurePlacementType<?> type() {
        return TYPE;
    }
}
