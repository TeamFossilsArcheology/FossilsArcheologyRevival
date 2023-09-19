package com.fossil.fossil.world.feature.structures;

import com.fossil.fossil.Fossil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;
import java.util.Random;

public class TreasureRoomFeature extends StructureFeature<NoneFeatureConfiguration> {
    public TreasureRoomFeature() {
        super(NoneFeatureConfiguration.CODEC, TreasureRoomFeature::generatePiece);
    }

    private static Optional<PieceGenerator<NoneFeatureConfiguration>> generatePiece(PieceGeneratorSupplier.Context<NoneFeatureConfiguration> context) {
        BlockPos blockPos = new BlockPos(context.chunkPos().getMinBlockX(), 70, context.chunkPos().getMinBlockZ());
        return Optional.of((builder, context1) -> builder.addPiece(new TreasureRoomPiece(context.structureManager(), blockPos, Rotation.NONE)));
    }

    @Override
    public GenerationStep.@NotNull Decoration step() {
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }

    static class TreasureRoomPiece extends TemplateStructurePiece {
        private static final ResourceLocation STRUCTURE = new ResourceLocation(Fossil.MOD_ID, "treasure_room");

        public TreasureRoomPiece(StructureManager structureManager, BlockPos blockPos, Rotation rotation) {
            super(StructurePieceType.NETHER_FOSSIL, 0, structureManager, STRUCTURE, STRUCTURE.toString(), makeSettings(rotation), blockPos);
        }

        private static StructurePlaceSettings makeSettings(Rotation rotation) {
            return new StructurePlaceSettings().setRotation(rotation);
        }

        @Override
        protected void addAdditionalSaveData(StructurePieceSerializationContext context, CompoundTag tag) {
            super.addAdditionalSaveData(context, tag);
            tag.putString("Rot", placeSettings.getRotation().name());
        }

        @Override
        protected void handleDataMarker(String marker, BlockPos pos, ServerLevelAccessor level, Random random, BoundingBox box) {

        }
    }
}
