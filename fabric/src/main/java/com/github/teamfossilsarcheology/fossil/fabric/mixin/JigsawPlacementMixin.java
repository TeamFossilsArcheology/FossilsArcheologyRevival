package com.github.teamfossilsarcheology.fossil.fabric.mixin;

import com.github.teamfossilsarcheology.fossil.Fossil;
import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.block.JigsawBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.pools.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.shapes.BooleanOp;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.apache.commons.lang3.mutable.MutableObject;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.*;


@Mixin(targets = "net/minecraft/world/level/levelgen/structure/pools/JigsawPlacement$Placer")
public abstract class JigsawPlacementMixin {

    @Final
    @Shadow
    private StructureManager structureManager;
    @Final
    @Shadow
    private Random random;
    @Final
    @Shadow
    private Registry<StructureTemplatePool> pools;
    @Final
    @Shadow
    private int maxDepth;
    @Final
    @Shadow
    private ChunkGenerator chunkGenerator;
    @Final
    @Shadow
    private JigsawPlacement.PieceFactory factory;
    @Final
    @Shadow
    Deque<JigsawPlacement.PieceState> placing;
    @Final
    @Shadow
    private List<? super PoolElementStructurePiece> pieces;

    @Inject(method = "tryPlacingChildren", at = @At(value = "HEAD"), cancellable = true)
    private void tryPlacingCustomStructures(PoolElementStructurePiece structurePiece, MutableObject<VoxelShape> mutableObject, int depth, boolean bl, LevelHeightAccessor levelHeightAccessor, CallbackInfo ci) {
        //This is a copy of the base method that allows us to place some of our structures
        String name = structurePiece.getElement().toString();
        boolean isTop = name.contains("house_taiga_top") || name.contains("house_plains_top") || name.contains("tent_option");
        boolean isBottom = name.contains("house_taiga_base") || name.contains("house_plains_base");
        if (name.contains(Fossil.MOD_ID) && (isTop || isBottom)) {
            ci.cancel();
            StructurePoolElement structurePoolElement = structurePiece.getElement();
            BlockPos baseStructurePosition = structurePiece.getPosition();
            Rotation baseStructureRotation = structurePiece.getRotation();
            StructureTemplatePool.Projection projection = structurePoolElement.getProjection();
            boolean baseIsRigid = projection == StructureTemplatePool.Projection.RIGID;
            MutableObject<VoxelShape> mutableObject2 = new MutableObject<>();
            BoundingBox baseStructureBoundingBox = structurePiece.getBoundingBox();
            int baseMinY = baseStructureBoundingBox.minY();
            block0:
            for (StructureTemplate.StructureBlockInfo baseStructureJigSaw : structurePoolElement.getShuffledJigsawBlocks(structureManager, baseStructurePosition, baseStructureRotation, random)) {
                MutableObject<VoxelShape> mutableObject3;
                Direction baseJigsawDirection = JigsawBlock.getFrontFacing(baseStructureJigSaw.state);
                BlockPos baseJigsawPosition = baseStructureJigSaw.pos;
                BlockPos expectedJigsawPosition = baseJigsawPosition.relative(baseJigsawDirection);
                int k = baseJigsawPosition.getY() - baseMinY;
                int l = -1;
                ResourceLocation baseTargetLocation = new ResourceLocation(baseStructureJigSaw.nbt.getString("pool"));
                Optional<StructureTemplatePool> baseTargetPool = pools.getOptional(baseTargetLocation);
                if (baseTargetPool.isEmpty() || baseTargetPool.get().size() == 0 && !Objects.equals(baseTargetLocation, Pools.EMPTY.location())) {
                    continue;
                }
                ResourceLocation baseFallbackLocation = baseTargetPool.get().getFallback();
                Optional<StructureTemplatePool> baseFallbackPool = pools.getOptional(baseFallbackLocation);
                if (baseFallbackPool.isEmpty() || baseFallbackPool.get().size() == 0 && !Objects.equals(baseFallbackLocation, Pools.EMPTY.location())) {
                    continue;
                }
                if (baseStructureBoundingBox.isInside(expectedJigsawPosition)) {
                    mutableObject3 = mutableObject2;
                    if (mutableObject2.getValue() == null) {
                        mutableObject2.setValue(Shapes.create(AABB.of(baseStructureBoundingBox)));
                    }
                } else {
                    mutableObject3 = mutableObject;
                }
                List<StructurePoolElement> list = Lists.newArrayList();
                //Always place basement even if limit is reached
                if (depth != maxDepth || isBottom) {
                    list.addAll(baseTargetPool.get().getShuffledTemplates(this.random));
                }
                list.addAll(baseFallbackPool.get().getShuffledTemplates(this.random));
                Iterator<StructurePoolElement> targetPoolIterator = list.iterator();
                StructurePoolElement targetElement;
                while (targetPoolIterator.hasNext() && (targetElement = targetPoolIterator.next()) != EmptyPoolElement.INSTANCE) {
                    for (Rotation targetElementRotation : Rotation.getShuffled(this.random)) {
                        List<StructureTemplate.StructureBlockInfo> allTargetJigsaws = targetElement.getShuffledJigsawBlocks(this.structureManager, BlockPos.ZERO, targetElementRotation, this.random);
                        BoundingBox boundingBox2 = targetElement.getBoundingBox(this.structureManager, BlockPos.ZERO, targetElementRotation);
                        int m = !bl || boundingBox2.getYSpan() > 16 ? 0 : allTargetJigsaws.stream().mapToInt(structureBlockInfo -> {
                            if (!boundingBox2.isInside(structureBlockInfo.pos.relative(JigsawBlock.getFrontFacing(structureBlockInfo.state)))) {
                                return 0;
                            }
                            ResourceLocation resourceLocation = new ResourceLocation(structureBlockInfo.nbt.getString("pool"));
                            Optional<StructureTemplatePool> optional = this.pools.getOptional(resourceLocation);
                            Optional<StructureTemplatePool> optional2 = optional.flatMap(structureTemplatePool -> this.pools.getOptional(structureTemplatePool.getFallback()));
                            int a = optional.map(structureTemplatePool -> structureTemplatePool.getMaxSize(this.structureManager)).orElse(0);
                            int b = optional2.map(structureTemplatePool -> structureTemplatePool.getMaxSize(this.structureManager)).orElse(0);
                            return Math.max(a, b);
                        }).max().orElse(0);
                        for (StructureTemplate.StructureBlockInfo targetJigsaw : allTargetJigsaws) {
                            int u;
                            int s;
                            int q;
                            if (!JigsawBlock.canAttach(baseStructureJigSaw, targetJigsaw)) continue;
                            BlockPos targetJigsawPosition = targetJigsaw.pos;
                            BlockPos vecToTargetJigsaw = expectedJigsawPosition.subtract(targetJigsawPosition);
                            BoundingBox boundingBox3 = targetElement.getBoundingBox(this.structureManager, vecToTargetJigsaw, targetElementRotation);
                            int n = boundingBox3.minY();
                            StructureTemplatePool.Projection targetProjection = targetElement.getProjection();
                            boolean targetIsRigid = targetProjection == StructureTemplatePool.Projection.RIGID;
                            int targetJigsawY = targetJigsawPosition.getY();
                            int p = k - targetJigsawY + JigsawBlock.getFrontFacing(baseStructureJigSaw.state).getStepY();
                            if (baseIsRigid && targetIsRigid) {
                                q = baseMinY + p;
                            } else {
                                if (l == -1) {
                                    l = chunkGenerator.getFirstFreeHeight(baseJigsawPosition.getX(), baseJigsawPosition.getZ(), Heightmap.Types.WORLD_SURFACE_WG, levelHeightAccessor);
                                }
                                q = l - targetJigsawY;
                            }
                            int r = q - n;
                            BoundingBox boundingBox4 = boundingBox3.moved(0, r, 0);
                            BlockPos blockPos6 = vecToTargetJigsaw.offset(0, r, 0);
                            if (m > 0) {
                                s = Math.max(m + 1, boundingBox4.maxY() - boundingBox4.minY());
                                boundingBox4.encapsulate(new BlockPos(boundingBox4.minX(), boundingBox4.minY() + s, boundingBox4.minZ()));
                            }
                            //Skip shape test for basement because that one always fails
                            boolean shouldSkip = targetElement.toString().contains("base") || targetElement.toString().contains("tent");
                            if (!shouldSkip && Shapes.joinIsNotEmpty(mutableObject3.getValue(), Shapes.create(AABB.of(boundingBox4).deflate(0.25)), BooleanOp.ONLY_SECOND)) {
                                continue;
                            }
                            mutableObject3.setValue(Shapes.joinUnoptimized(mutableObject3.getValue(), Shapes.create(AABB.of(boundingBox4)), BooleanOp.ONLY_FIRST));
                            s = structurePiece.getGroundLevelDelta();
                            int t = targetIsRigid ? s - p : targetElement.getGroundLevelDelta();
                            PoolElementStructurePiece targetStructurePiece = factory.create(this.structureManager, targetElement, blockPos6, t, targetElementRotation, boundingBox4);
                            if (baseIsRigid) {
                                u = baseMinY + k;
                            } else if (targetIsRigid) {
                                u = q + targetJigsawY;
                            } else {
                                if (l == -1) {
                                    l = chunkGenerator.getFirstFreeHeight(baseJigsawPosition.getX(), baseJigsawPosition.getZ(), Heightmap.Types.WORLD_SURFACE_WG, levelHeightAccessor);
                                }
                                u = l + p / 2;
                            }
                            structurePiece.addJunction(new JigsawJunction(expectedJigsawPosition.getX(), u - k + s, expectedJigsawPosition.getZ(), p, targetProjection));
                            targetStructurePiece.addJunction(new JigsawJunction(baseJigsawPosition.getX(), u - targetJigsawY + t, baseJigsawPosition.getZ(), -p, projection));
                            pieces.add(targetStructurePiece);
                            //Always place basement even if limit is reached
                            if (!shouldSkip && depth + 1 > this.maxDepth) continue block0;
                            placing.addLast(new JigsawPlacement.PieceState(targetStructurePiece, mutableObject3, depth + 1));
                            continue block0;
                        }
                    }
                }
            }
        }
    }
}
