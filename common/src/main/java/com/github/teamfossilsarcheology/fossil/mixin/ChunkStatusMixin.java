package com.github.teamfossilsarcheology.fossil.mixin;

import com.github.teamfossilsarcheology.fossil.world.chunk.AnuLairChunkGenerator;
import com.github.teamfossilsarcheology.fossil.world.chunk.TreasureChunkGenerator;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ThreadedLevelLightEngine;
import net.minecraft.world.level.chunk.ChunkAccess;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.chunk.ChunkStatus;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Function;

@Mixin(ChunkStatus.class)
public class ChunkStatusMixin {

    //require 0 to prevent crash in development
    @Inject(method = "method_39464", at = @At("HEAD"), require = 0)
    private static void allow(ChunkStatus chunkStatus, Executor executor, ServerLevel serverLevel, ChunkGenerator chunkGenerator, StructureTemplateManager structureTemplateManager, ThreadedLevelLightEngine threadedLevelLightEngine, Function function, List list, ChunkAccess chunkAccess, CallbackInfoReturnable<CompletableFuture> cir) {
        if (chunkGenerator instanceof TreasureChunkGenerator || chunkGenerator instanceof AnuLairChunkGenerator) {
            if (!chunkAccess.getStatus().isOrAfter(chunkStatus) && !serverLevel.getServer().getWorldData().worldGenOptions().generateStructures()) {
                chunkGenerator.createStructures(serverLevel.registryAccess(), serverLevel.getChunkSource().getGeneratorState(), serverLevel.structureManager(), chunkAccess, structureTemplateManager);
            }
        }
    }
}
