package com.fossil.fossil.world.feature.structures;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.config.FossilConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.RangeConfiguration;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class HellBoatFeature extends StructureFeature<RangeConfiguration> {

    public HellBoatFeature() {
        super(RangeConfiguration.CODEC, HellBoatFeature::pieceGeneratorSupplier);
    }

    private static Optional<PieceGenerator<RangeConfiguration>> pieceGeneratorSupplier(PieceGeneratorSupplier.Context<RangeConfiguration> context) {
        if (!FossilConfig.isEnabled("generateHellShips")) {
            return Optional.empty();
        }
        BlockPos origin = context.chunkPos().getMiddleBlockPosition(0);
        NoiseColumn noiseColumn = context.chunkGenerator().getBaseColumn(origin.getX(), origin.getZ(), context.heightAccessor());
        Fossil.LOGGER.debug("Hellboat: Trying to place at " + origin.atY(30));
        if (noiseColumn.getBlock(31).getBlock() != Blocks.LAVA) {
            Fossil.LOGGER.debug("Hellboat: No Lava");
            return Optional.empty();
        }
        for (int i = 32; i < 50; i++) {
            Block block = noiseColumn.getBlock(i).getBlock();
            if (block != Blocks.AIR && block != Blocks.CAVE_AIR) {
                Fossil.LOGGER.debug("Hellboat: No Air");
                return Optional.empty();
            }
        }
        WorldgenRandom worldgenRandom = new WorldgenRandom(new LegacyRandomSource(0L));
        Rotation rotation = Rotation.getRandom(worldgenRandom);
        Fossil.LOGGER.debug("Hellboat: Placed");
        return Optional.of((builder, context2) -> builder.addPiece(new HellBoatPiece(context.structureManager(), origin.atY(30), rotation)));
    }

    @Override
    public GenerationStep.@NotNull Decoration step() {
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }
}
