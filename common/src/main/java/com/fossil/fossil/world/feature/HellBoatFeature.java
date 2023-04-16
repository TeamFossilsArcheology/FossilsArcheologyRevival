package com.fossil.fossil.world.feature;

import com.fossil.fossil.Fossil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.NoiseColumn;
import net.minecraft.world.level.WorldGenLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.FeaturePlaceContext;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;

import java.util.Random;

public class HellBoatFeature extends Feature<NoneFeatureConfiguration> {
    private static final ResourceLocation STRUCTURE = new ResourceLocation(Fossil.MOD_ID, "hell_boat");

    public HellBoatFeature() {
        super(NoneFeatureConfiguration.CODEC);
    }

    @Override
    public boolean place(FeaturePlaceContext<NoneFeatureConfiguration> context) {
        WorldGenLevel worldGenLevel = context.level();
        BlockPos origin = context.origin();
        NoiseColumn noiseColumn = context.chunkGenerator().getBaseColumn(origin.getX(), origin.getZ(), worldGenLevel);
        if (noiseColumn.getBlock(31).getBlock() != Blocks.LAVA) {
            return false;
        }
        for (int i = 32; i < 50; i++) {
            Block block = noiseColumn.getBlock(i).getBlock();
            if (block != Blocks.AIR && block != Blocks.CAVE_AIR) {
                return false;
            }
        }
        StructureManager structureManager = worldGenLevel.getLevel().getServer().getStructureManager();
        StructureTemplate hellBoatTemplate = structureManager.getOrCreate(STRUCTURE);
        Random random = context.random();
        Rotation rotation = Rotation.getRandom(random);
        Vec3i size = hellBoatTemplate.getSize(rotation);
        BlockPos blockPos = new BlockPos(-size.getX() / 2 + origin.getX(), 30, -size.getZ() / 2 + origin.getZ());
        BlockPos transformedPos = hellBoatTemplate.getZeroPositionWithTransform(blockPos, Mirror.NONE, rotation);
        ChunkPos chunkPos = new ChunkPos(origin);
        BoundingBox boundingBox = new BoundingBox(chunkPos.getMinBlockX() - 16, worldGenLevel.getMinBuildHeight(), chunkPos.getMinBlockZ() - 16,
                chunkPos.getMaxBlockX() + 16, worldGenLevel.getMaxBuildHeight(), chunkPos.getMaxBlockZ() + 16);
        var structurePlaceSettings = new StructurePlaceSettings().setRotation(rotation).setBoundingBox(boundingBox).setRandom(random);
        structurePlaceSettings.clearProcessors();
        hellBoatTemplate.placeInWorld(worldGenLevel, transformedPos, transformedPos, structurePlaceSettings, random, 4);
        return true;
    }
}
