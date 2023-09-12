package com.fossil.fossil.world.feature.structures;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.config.FossilConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.QuartPos;
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
import net.minecraft.world.level.levelgen.structure.pieces.StructurePiecesBuilder;
import org.jetbrains.annotations.NotNull;

public class HellBoatFeature extends StructureFeature<RangeConfiguration> {

    public HellBoatFeature() {
        super(RangeConfiguration.CODEC, PieceGeneratorSupplier.simple(HellBoatFeature::checkLocation, HellBoatFeature::generatePieces));
    }

    private static boolean checkLocation(PieceGeneratorSupplier.Context<RangeConfiguration> context) {
        return FossilConfig.isEnabled(FossilConfig.GENERATE_HELL_SHIPS) && context.validBiome().test(context.chunkGenerator().getNoiseBiome(QuartPos.fromBlock(context.chunkPos().getMiddleBlockX()), QuartPos.fromBlock(64), QuartPos.fromBlock(context.chunkPos().getMiddleBlockZ())));
    }

    private static void generatePieces(StructurePiecesBuilder builder, PieceGenerator.Context<RangeConfiguration> context) {
        BlockPos origin = context.chunkPos().getMiddleBlockPosition(0);
        NoiseColumn noiseColumn = context.chunkGenerator().getBaseColumn(origin.getX(), origin.getZ(), context.heightAccessor());
        Fossil.LOGGER.debug("Hellboat: Trying to place at " + origin.atY(30));
        if (noiseColumn.getBlock(31).getBlock() != Blocks.LAVA) {
            Fossil.LOGGER.debug("Hellboat: No Lava");
            return;
        }
        for (int i = 32; i < 50; i++) {
            Block block = noiseColumn.getBlock(i).getBlock();
            if (block != Blocks.AIR && block != Blocks.CAVE_AIR) {
                Fossil.LOGGER.debug("Hellboat: No Air");
                return;
            }
        }
        WorldgenRandom worldgenRandom = new WorldgenRandom(new LegacyRandomSource(0L));
        Rotation rotation = Rotation.getRandom(worldgenRandom);
        Fossil.LOGGER.debug("Hellboat: Placed");
        builder.addPiece(new HellBoatPiece(context.structureManager(), origin.atY(30), rotation));
    }

    @Override
    public GenerationStep.@NotNull Decoration step() {
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }
}
