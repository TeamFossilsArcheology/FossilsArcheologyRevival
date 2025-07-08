package com.github.teamfossilsarcheology.fossil.mixin;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.google.common.collect.Lists;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.LevelHeightAccessor;
import net.minecraft.world.level.block.JigsawBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.Heightmap;
import net.minecraft.world.level.levelgen.RandomState;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.pools.*;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
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

import java.util.Deque;
import java.util.List;
import java.util.Optional;


@Mixin(targets = "net/minecraft/world/level/levelgen/structure/pools/JigsawPlacement$Placer")
public abstract class JigsawPlacementMixin {

    @Final
    @Shadow
    private RandomSource random;
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
    Deque<JigsawPlacement.PieceState> placing;
    @Final
    @Shadow
    private List<? super PoolElementStructurePiece> pieces;

    @Shadow
    @Final
    private StructureTemplateManager structureTemplateManager;

    @Shadow
    private static ResourceKey<StructureTemplatePool> readPoolName(StructureTemplate.StructureBlockInfo structureBlockInfo) {
        return null;
    }

    @Inject(method = "tryPlacingChildren", at = @At(value = "HEAD"), cancellable = true)
    private void tryPlacingCustomStructures(PoolElementStructurePiece structurePiece, MutableObject<VoxelShape> mutableObject, int depth, boolean bl, LevelHeightAccessor levelHeightAccessor, RandomState randomState, CallbackInfo ci) {
        //This is a copy of the base method that allows us to place some of our structures
        String name = structurePiece.getElement().toString();
        boolean isTop = name.contains("house_taiga_top") || name.contains("house_plains_top") || name.contains("tent_option");
        boolean isBottom = name.contains("house_taiga_base") || name.contains("house_plains_base");
        if (name.contains(FossilMod.MOD_ID) && (isTop || isBottom)) {
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
            for (StructureTemplate.StructureBlockInfo baseStructureJigSaw : structurePoolElement.getShuffledJigsawBlocks(structureTemplateManager, baseStructurePosition, baseStructureRotation, random)) {
                MutableObject<VoxelShape> mutableObject3;
                Direction baseJigsawDirection = JigsawBlock.getFrontFacing(baseStructureJigSaw.state());
                BlockPos baseJigsawPosition = baseStructureJigSaw.pos();
                BlockPos expectedJigsawPosition = baseJigsawPosition.relative(baseJigsawDirection);
                int k = baseJigsawPosition.getY() - baseMinY;
                int l = -1;
                ResourceKey<StructureTemplatePool> baseTargetKey = readPoolName(baseStructureJigSaw);
                Optional<? extends Holder<StructureTemplatePool>> baseTargetPool = pools.getHolder(baseTargetKey);
                if (baseTargetPool.isEmpty()) {
                    continue;
                }
                Holder<StructureTemplatePool> baseTarget = baseTargetPool.get();
                if (baseTarget.value().size() == 0 && !baseTarget.is(Pools.EMPTY)) {
                    continue;
                }
                Holder<StructureTemplatePool> baseFallback = baseTarget.value().getFallback();
                if (baseFallback.value().size() == 0 && !baseFallback.is(Pools.EMPTY)) {
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
                    list.addAll(baseTarget.value().getShuffledTemplates(random));
                }
                list.addAll(baseFallback.value().getShuffledTemplates(random));
                for (StructurePoolElement targetElement : list) {
                    if (targetElement == EmptyPoolElement.INSTANCE) {
                        break;
                    }
                    for (Rotation targetElementRotation : Rotation.getShuffled(random)) {
                        List<StructureTemplate.StructureBlockInfo> allTargetJigsaws = targetElement.getShuffledJigsawBlocks(structureTemplateManager, BlockPos.ZERO, targetElementRotation, this.random);
                        BoundingBox boundingBox2 = targetElement.getBoundingBox(structureTemplateManager, BlockPos.ZERO, targetElementRotation);
                        int m = !bl || boundingBox2.getYSpan() > 16 ? 0 : allTargetJigsaws.stream().mapToInt(structureBlockInfo -> {
                            if (!boundingBox2.isInside(structureBlockInfo.pos().relative(JigsawBlock.getFrontFacing(structureBlockInfo.state())))) {
                                return 0;
                            }
                            ResourceKey<StructureTemplatePool> resourceKey = readPoolName(structureBlockInfo);
                            Optional<Holder.Reference<StructureTemplatePool>> optional = pools.getHolder(resourceKey);
                            Optional<Holder<StructureTemplatePool>> optional2 = optional.map(argx -> argx.value().getFallback());
                            int a = optional.map(structureTemplatePool -> structureTemplatePool.value().getMaxSize(structureTemplateManager)).orElse(0);
                            int b = optional2.map(structureTemplatePool -> structureTemplatePool.value().getMaxSize(structureTemplateManager)).orElse(0);
                            return Math.max(a, b);
                        }).max().orElse(0);
                        for (StructureTemplate.StructureBlockInfo targetJigsaw : allTargetJigsaws) {
                            int u;
                            int s;
                            int q;
                            if (!JigsawBlock.canAttach(baseStructureJigSaw, targetJigsaw)) continue;
                            BlockPos targetJigsawPosition = targetJigsaw.pos();
                            BlockPos vecToTargetJigsaw = expectedJigsawPosition.subtract(targetJigsawPosition);
                            BoundingBox boundingBox3 = targetElement.getBoundingBox(structureTemplateManager, vecToTargetJigsaw, targetElementRotation);
                            int n = boundingBox3.minY();
                            StructureTemplatePool.Projection targetProjection = targetElement.getProjection();
                            boolean targetIsRigid = targetProjection == StructureTemplatePool.Projection.RIGID;
                            int targetJigsawY = targetJigsawPosition.getY();
                            int p = k - targetJigsawY + JigsawBlock.getFrontFacing(baseStructureJigSaw.state()).getStepY();
                            if (baseIsRigid && targetIsRigid) {
                                q = baseMinY + p;
                            } else {
                                if (l == -1) {
                                    l = chunkGenerator.getFirstFreeHeight(baseJigsawPosition.getX(), baseJigsawPosition.getZ(), Heightmap.Types.WORLD_SURFACE_WG, levelHeightAccessor, randomState);
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
                            PoolElementStructurePiece targetStructurePiece = new PoolElementStructurePiece(
                                    structureTemplateManager, targetElement, blockPos6, t, targetElementRotation, boundingBox4);
                            if (baseIsRigid) {
                                u = baseMinY + k;
                            } else if (targetIsRigid) {
                                u = q + targetJigsawY;
                            } else {
                                if (l == -1) {
                                    l = chunkGenerator.getFirstFreeHeight(baseJigsawPosition.getX(), baseJigsawPosition.getZ(), Heightmap.Types.WORLD_SURFACE_WG, levelHeightAccessor, randomState);
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
