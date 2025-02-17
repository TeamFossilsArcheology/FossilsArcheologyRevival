package com.github.teamfossilsarcheology.fossil.forge.mixin;

import com.github.teamfossilsarcheology.fossil.material.ModFluids;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * Will be disabled if Radium is loaded. This can be removed in 1.19
 */
@Mixin(WalkNodeEvaluator.class)
public abstract class WalkNodeEvaluatorMixinOpt {

    @Inject(method = "getBlockPathTypeRaw", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/material/FluidState;is(Lnet/minecraft/tags/TagKey;)Z", ordinal = 0), cancellable = true)
    private static void addTarAsDangerousPath(BlockGetter level, BlockPos pos, CallbackInfoReturnable<BlockPathTypes> cir, @Local FluidState fluidState) {
        if (fluidState.is(ModFluids.TAR.get())) {
            cir.setReturnValue(BlockPathTypes.LAVA);
        }
    }
}
