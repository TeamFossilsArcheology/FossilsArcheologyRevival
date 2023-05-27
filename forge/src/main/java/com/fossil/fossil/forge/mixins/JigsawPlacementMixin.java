package com.fossil.fossil.forge.mixins;

import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.pools.SinglePoolElement;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.commons.lang3.mutable.MutableObject;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net/minecraft/world/level/levelgen/structure/pools/JigsawPlacement$Placer")
public abstract class JigsawPlacementMixin {


    @Inject(method = "tryPlacingChildren", at = @At(value = "HEAD"))
    private void addPartEntities(PoolElementStructurePiece poolElementStructurePiece, MutableObject<VoxelShape> mutableObject, int i, boolean bl, LevelHeightAccessor levelHeightAccessor, CallbackInfo ci) {
        if (poolElementStructurePiece.getElement() instanceof SinglePoolElement singlePoolElement && singlePoolElement.toString().equals("Single[Left[fossil:anu_castle_nw]]")) {
            mutableObject.setValue(Shapes.INFINITY);
        }
    }
}
