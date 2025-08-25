package com.github.teamfossilsarcheology.fossil.fabric.mixin;

import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.material.ModFluids;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.NodeEvaluator;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(WalkNodeEvaluator.class)
public abstract class WalkNodeEvaluatorMixin extends NodeEvaluator {

    @Inject(method = "getBlockPathTypeRaw", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/material/FluidState;is(Lnet/minecraft/tags/TagKey;)Z", ordinal = 0), cancellable = true)
    private static void addTarAsDangerousPath(BlockGetter level, BlockPos pos, CallbackInfoReturnable<BlockPathTypes> cir, @Local FluidState fluidState) {
        if (ModFluids.TAR.isPresent() && fluidState.is(ModFluids.TAR.get())) {
            cir.setReturnValue(BlockPathTypes.LAVA);
        }
    }
    @Inject(method = "isBurningBlock", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/block/state/BlockState;is(Lnet/minecraft/tags/TagKey;)Z", ordinal = 0), cancellable = true)
    private static void addTarAsDangerousPath(BlockState state, CallbackInfoReturnable<Boolean> cir) {
        if (ModBlocks.TAR.isPresent() && state.is(ModBlocks.TAR.get())) {
            cir.setReturnValue(true);
        }
    }

    @WrapOperation(method = "findAcceptedNode", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/level/pathfinder/WalkNodeEvaluator;canReachWithoutCollision(Lnet/minecraft/world/level/pathfinder/Node;)Z"))
    public boolean preventMobFromGettingStuck(WalkNodeEvaluator instance, Node node, Operation<Boolean> original) {
        boolean collides = !original.call(instance, node);
        if (collides && mob instanceof Prehistoric && mob.blockPosition().equals(node.asBlockPos())) {
            //Mobs with certain BB widths ie 1-1.3 etc will get stuck in fence corners. See Trello
            return false;
        }
        return collides;
    }
}
