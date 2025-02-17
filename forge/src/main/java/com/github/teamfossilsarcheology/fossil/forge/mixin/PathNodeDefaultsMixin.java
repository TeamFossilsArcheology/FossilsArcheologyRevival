package com.github.teamfossilsarcheology.fossil.forge.mixin;

import com.github.teamfossilsarcheology.fossil.material.ModFluids;
import com.llamalad7.mixinextras.sugar.Local;
import me.jellysquid.mods.lithium.common.ai.pathing.PathNodeDefaults;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

/**
 * {@link WalkNodeEvaluatorMixinOpt} replacement
 */
@Mixin(PathNodeDefaults.class)
public class PathNodeDefaultsMixin {

    @Inject(method = "getNodeType", remap = false, at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/material/FluidState;is(Lnet/minecraft/tags/TagKey;)Z", ordinal = 0), cancellable = true)
    private static void addTarAsDangerousPath(BlockState state, CallbackInfoReturnable<BlockPathTypes> cir, @Local FluidState fluidState) {
        if (fluidState.is(ModFluids.TAR.get())) {
            cir.setReturnValue(BlockPathTypes.LAVA);
        }
    }
}
