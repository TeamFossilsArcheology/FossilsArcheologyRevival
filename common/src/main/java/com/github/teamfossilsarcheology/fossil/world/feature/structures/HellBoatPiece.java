package com.github.teamfossilsarcheology.fossil.world.feature.structures;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;

import java.util.Random;

public class HellBoatPiece extends TemplateStructurePiece {
    private static final ResourceLocation STRUCTURE = FossilMod.location("hell_boat");

    public HellBoatPiece(StructureManager structureManager, BlockPos blockPos, Rotation rotation) {
        super(StructurePieceType.NETHER_FOSSIL, 0, structureManager, STRUCTURE, STRUCTURE.toString(), makeSettings(rotation), blockPos);
    }

    public HellBoatPiece(StructureManager structureManager, CompoundTag compoundTag) {
        super(StructurePieceType.NETHER_FOSSIL, compoundTag, structureManager,
                (ResourceLocation resourceLocation) -> makeSettings(Rotation.valueOf(compoundTag.getString("Rot"))));
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
