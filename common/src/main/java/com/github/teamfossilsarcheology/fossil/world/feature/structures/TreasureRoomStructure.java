package com.github.teamfossilsarcheology.fossil.world.feature.structures;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

public class TreasureRoomStructure extends Structure {
    public static final Codec<TreasureRoomStructure> CODEC = RecordCodecBuilder
            .create(instance -> instance.group(settingsCodec(instance)).apply(instance, TreasureRoomStructure::new));

    public TreasureRoomStructure(Structure.StructureSettings settings) {
        super(settings);
    }

    @Override
    public @NotNull Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        BlockPos blockPos = new BlockPos(context.chunkPos().getMinBlockX(), 70, context.chunkPos().getMinBlockZ());
        return Optional.of(new GenerationStub(blockPos, builder -> builder.addPiece(new TreasureRoomPiece(context.structureTemplateManager(), blockPos, Rotation.NONE))));
    }

    @Override
    public @NotNull StructureType<?> type() {
        return ModStructureType.TREASURE_ROOM;
    }

    static class TreasureRoomPiece extends TemplateStructurePiece {
        private static final ResourceLocation STRUCTURE = FossilMod.location("treasure_room");

        public TreasureRoomPiece(StructureTemplateManager structureTemplateManager, BlockPos blockPos, Rotation rotation) {
            super(StructurePieceType.NETHER_FOSSIL, 0, structureTemplateManager, STRUCTURE, STRUCTURE.toString(), makeSettings(rotation), blockPos);
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
        protected void handleDataMarker(String name, BlockPos pos, ServerLevelAccessor level, RandomSource random, BoundingBox box) {

        }
    }
}
