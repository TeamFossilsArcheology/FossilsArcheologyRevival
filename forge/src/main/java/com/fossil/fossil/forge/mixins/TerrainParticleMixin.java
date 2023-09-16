package com.fossil.fossil.forge.mixins;

import com.fossil.fossil.block.ModBlocks;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.particle.TerrainParticle;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(TerrainParticle.class)
public abstract class TerrainParticleMixin {


    @Inject(method = "<init>(Lnet/minecraft/client/multiplayer/ClientLevel;DDDDDDLnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/core/BlockPos;)V", at = @At(value = "TAIL"))
    private void changeGravity(ClientLevel clientLevel, double d, double e, double f, double g, double h, double i, BlockState blockState,
                               BlockPos blockPos, CallbackInfo ci) {
        if (blockState.getBlock() == ModBlocks.VOLCANIC_ASH.get()) {
            ((ParticleAccessor) this).setGravity(-0.15f);
        }
    }

}
