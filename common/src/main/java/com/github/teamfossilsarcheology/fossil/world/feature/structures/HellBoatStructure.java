package com.github.teamfossilsarcheology.fossil.world.feature.structures;

import com.github.teamfossilsarcheology.fossil.config.FossilConfig;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class HellBoatStructure extends Structure {
    public static final Codec<HellBoatStructure> CODEC = RecordCodecBuilder.create(
            instance -> instance.group(settingsCodec(instance), HeightProvider.CODEC.fieldOf("height").forGetter(hellBoatStructure -> hellBoatStructure.height))
                    .apply(instance, HellBoatStructure::new)
    );
    public final HeightProvider height;

    public HellBoatStructure(Structure.StructureSettings settings, HeightProvider height) {
        super(settings);
        this.height = height;
    }

    @Override
    public @NotNull Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        if (!FossilConfig.isEnabled(FossilConfig.GENERATE_HELL_SHIPS)) {
            return Optional.empty();
        }
        BlockPos origin = context.chunkPos().getMiddleBlockPosition(0);
        WorldgenRandom worldgenRandom = context.random();
        int x = context.chunkPos().getMinBlockX() + worldgenRandom.nextInt(16);
        int z = context.chunkPos().getMinBlockZ() + worldgenRandom.nextInt(16);
        NoiseColumn noiseColumn = context.chunkGenerator().getBaseColumn(x, z, context.heightAccessor(), context.randomState());
        if (noiseColumn.getBlock(31).getBlock() != Blocks.LAVA) {
            return Optional.empty();
        }
        for (int i = 32; i < 50; i++) {
            Block block = noiseColumn.getBlock(i).getBlock();
            if (block != Blocks.AIR && block != Blocks.CAVE_AIR) {
                return Optional.empty();
            }
        }
        Rotation rotation = Rotation.getRandom(worldgenRandom);
        BlockPos blockPos = origin.atY(30);

        return Optional.of(new Structure.GenerationStub(blockPos, builder -> builder.addPiece(new HellBoatPiece(context.structureTemplateManager(), blockPos, rotation))));
    }

    @Override
    public @NotNull StructureType<?> type() {
        return ModStructureType.HELL_BOAT;
    }
}
