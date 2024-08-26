package com.fossil.fossil.fabric.mixins;

import com.fossil.fossil.util.Version;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.StructureBlockEntity;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArg;

@Mixin(StructureBlockEntity.class)
public abstract class DebugStructureBlockEntityMixin {

    @ModifyArg(method = "saveStructure(Z)Z", index = 4, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/levelgen/structure/templatesystem/StructureTemplate;fillFromWorld(Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Vec3i;ZLnet/minecraft/world/level/block/Block;)V"))
    private @Nullable Block saveStructureVoid(@Nullable Block toIgnore) {
        if (Version.debugEnabled()) {
            boolean saveStructureVoid = false;//Set to true with debugger to allow saving of structure void
            return saveStructureVoid ? null : toIgnore;
        }
        return toIgnore;
    }
}
