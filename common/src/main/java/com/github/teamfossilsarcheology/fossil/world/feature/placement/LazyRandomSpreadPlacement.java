package com.github.teamfossilsarcheology.fossil.world.feature.placement;

import com.github.teamfossilsarcheology.fossil.config.FossilConfig;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.Vec3i;
import net.minecraft.util.ExtraCodecs;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadStructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.RandomSpreadType;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacement;
import net.minecraft.world.level.levelgen.structure.placement.StructurePlacementType;
import org.jetbrains.annotations.NotNull;

/**
 * Custom {@link RandomSpreadStructurePlacement} that gets its spacing and separation values from a config entry
 */
public record LazyRandomSpreadPlacement(String spacingEntry, String separationEntry, RandomSpreadType spreadType, int salt,
                                        Vec3i locateOffset) implements StructurePlacement {

    public static final Codec<LazyRandomSpreadPlacement> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                    Codec.STRING.fieldOf("spacingEntry").forGetter(LazyRandomSpreadPlacement::spacingEntry),
                    Codec.STRING.fieldOf("separationEntry").forGetter(LazyRandomSpreadPlacement::separationEntry),
                    RandomSpreadType.CODEC.optionalFieldOf("spread_type", RandomSpreadType.LINEAR).forGetter(LazyRandomSpreadPlacement::spreadType),
                    ExtraCodecs.NON_NEGATIVE_INT.fieldOf("salt").forGetter(LazyRandomSpreadPlacement::salt),
                    Vec3i.offsetCodec(16).optionalFieldOf("locate_offset", Vec3i.ZERO).forGetter(LazyRandomSpreadPlacement::locateOffset))
            .apply(instance, LazyRandomSpreadPlacement::new));

    public static final StructurePlacementType<LazyRandomSpreadPlacement> TYPE = () -> LazyRandomSpreadPlacement.CODEC;

    public LazyRandomSpreadPlacement(String spacingEntry, String separationEntry, RandomSpreadType spreadType, int salt) {
        this(spacingEntry, separationEntry, spreadType, salt, Vec3i.ZERO);
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
    public boolean isFeatureChunk(ChunkGenerator generator, long seed, int x, int z) {
        ChunkPos chunkPos = getPotentialFeatureChunk(seed, x, z);
        return chunkPos.x == x && chunkPos.z == z;
    }

    @Override
    public @NotNull StructurePlacementType<?> type() {
        return TYPE;
    }
}
