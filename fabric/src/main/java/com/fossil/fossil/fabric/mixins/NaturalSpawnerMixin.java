package com.fossil.fossil.fabric.mixins;

import com.fossil.fossil.world.dimension.ModDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.chunk.LevelChunk;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

@Mixin(NaturalSpawner.class)
public class NaturalSpawnerMixin {

    @Inject(method="getRandomPosWithin", at= @At("RETURN"), cancellable = true, locals = LocalCapture.CAPTURE_FAILSOFT)
    private static void fixAnuLairMobSpawning(Level level, LevelChunk chunk, CallbackInfoReturnable<BlockPos> cir, ChunkPos chunkPos, int x, int z, int maxY, int y) {
        //Prevent spawning of our sentries in the void
        if (level.dimension().location().equals(ModDimensions.ANU_LAIR.location())) {
            y = Mth.randomBetweenInclusive(level.random, 63, maxY - 3);
            BlockPos pos = new BlockPos(x, y, z);
            if (!level.isEmptyBlock(pos.below())) {
                cir.setReturnValue(pos);
            } else {
                //Below min height will be cancelled
                cir.setReturnValue(new BlockPos(0, -5, 0));
            }
        }
    }
}
