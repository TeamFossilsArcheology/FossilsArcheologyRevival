package com.fossil.fossil.world.feature.structures;

import com.fossil.fossil.Fossil;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ServerLevelAccessor;
import net.minecraft.world.level.block.Mirror;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.TemplateStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceSerializationContext;
import net.minecraft.world.level.levelgen.structure.pieces.StructurePieceType;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructurePlaceSettings;

import java.util.Random;

public class HellBoatPiece extends TemplateStructurePiece {
    private static final ResourceLocation STRUCTURE = new ResourceLocation(Fossil.MOD_ID, "hell_boat");

    public HellBoatPiece(StructureManager structureManager, BlockPos blockPos, Rotation rotation) {
        super(StructurePieceType.NETHER_FOSSIL, 0, structureManager, STRUCTURE, STRUCTURE.toString(), makeSettings(rotation), blockPos);
    }

    public HellBoatPiece(StructureManager structureManager, CompoundTag compoundTag) {
        super(StructurePieceType.NETHER_FOSSIL, compoundTag, structureManager,
                (ResourceLocation resourceLocation) -> makeSettings(Rotation.valueOf(compoundTag.getString("Rot"))));
    }

    private static StructurePlaceSettings makeSettings(Rotation rotation) {
        return new StructurePlaceSettings().setRotation(rotation).setMirror(Mirror.NONE);
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
