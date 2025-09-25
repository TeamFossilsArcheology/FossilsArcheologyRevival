package com.github.teamfossilsarcheology.fossil.mixin;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.util.ModConstants;
import dev.architectury.platform.Platform;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.WorldGenerationContext;
import net.minecraft.world.level.levelgen.heightproviders.HeightProvider;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Optional;

@Mixin(JigsawStructure.class)
public class JigsawStructureMixin {

    @Shadow
    @Final
    private Holder<StructureTemplatePool> startPool;

    @Shadow
    @Final
    private HeightProvider startHeight;

    @Shadow
    @Final
    private Optional<ResourceLocation> startJigsawName;

    @Shadow
    @Final
    private int maxDepth;

    @Shadow
    @Final
    private boolean useExpansionHack;

    @Shadow
    @Final
    private Optional<Heightmap.Types> projectStartToHeightmap;

    @Shadow
    @Final
    private int maxDistanceFromCenter;

    @Inject(method = "findGenerationPoint", at = @At("HEAD"), cancellable = true)
    public void overrideReturnValueIfLithostitchedIsInstalled(Structure.GenerationContext context, CallbackInfoReturnable<Optional<Structure.GenerationStub>> cir) {
        if (Platform.isModLoaded(ModConstants.LITHOSTITCHED) && startPool instanceof Holder.Reference<StructureTemplatePool> reference) {
            //Override lithostitched redirect to ensure that our later mixin still get run
            if (reference.key().location().getNamespace().equals(FossilMod.MOD_ID)) {
                ChunkPos chunkPos = context.chunkPos();
                int i = startHeight.sample(context.random(), new WorldGenerationContext(context.chunkGenerator(), context.heightAccessor()));
                BlockPos blockPos = new BlockPos(chunkPos.getMinBlockX(), i, chunkPos.getMinBlockZ());
                cir.setReturnValue(JigsawPlacement.addPieces(context, startPool, startJigsawName, maxDepth, blockPos, useExpansionHack, projectStartToHeightmap, maxDistanceFromCenter));
            }
        }
    }
}
