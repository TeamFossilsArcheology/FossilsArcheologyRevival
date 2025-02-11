package com.github.teamfossilsarcheology.fossil.fabric.mixin;

import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import com.github.teamfossilsarcheology.fossil.material.ModFluids;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WalkNodeEvaluator.class)
public abstract class WalkNodeEvaluatorMixin {

    @Inject(method = "getBlockPathTypeRaw", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/material/FluidState;is(Lnet/minecraft/tags/TagKey;)Z", ordinal = 0), cancellable = true)
    private static void addTarAsDangerousPath(BlockGetter level, BlockPos pos, CallbackInfoReturnable<BlockPathTypes> cir, @Local FluidState fluidState) {
        if (level.getFluidState(pos).is(ModFluids.TAR.get())) {
            cir.setReturnValue(BlockPathTypes.LAVA);
        }
    }
    @Inject(method = "isBurningBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/tags/TagKey;)Z", ordinal = 0), cancellable = true)
    private static void addTarAsDangerousPath(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (state.is(ModBlocks.TAR.get())) {
            cir.setReturnValue(true);
        }
    }
}
