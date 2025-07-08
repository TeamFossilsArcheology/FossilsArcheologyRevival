package com.github.teamfossilsarcheology.fossil.world.feature.structures;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.*;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.ChunkPos;
import net.minecraft.world.level.block.JigsawBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.Structure;
import net.minecraft.world.level.levelgen.structure.StructureType;
import net.minecraft.world.level.levelgen.structure.pools.JigsawJunction;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.structures.JigsawStructure;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplateManager;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Based on {@link JigsawStructure} but with a fixed rotation and no limitations for structure size
 * (normally jigsaw structures are limited by the size of the starting piece)
 */
public class AnuCastleStructure extends Structure {

    public static final Codec<AnuCastleStructure> CODEC = RecordCodecBuilder
            .create(instance -> instance.group(settingsCodec(instance)).apply(instance, AnuCastleStructure::new));

    public AnuCastleStructure(Structure.StructureSettings settings) {
        super(settings);
    }

    @Override
    public @NotNull Optional<GenerationStub> findGenerationPoint(GenerationContext context) {
        ChunkPos chunkPos = context.chunkPos();
        BlockPos blockPos = new BlockPos(chunkPos.getMinBlockX(), 63, chunkPos.getMinBlockZ());
        return addPieces(context, blockPos);
    }

    /**
     * Copy of {@link JigsawPlacement#addPieces(GenerationContext, Holder, Optional, int, BlockPos, boolean, Optional, int)}
     * that we can optimize a bit because we always place the castle at 0, 63, 0 with no rotation
     */
    private static Optional<GenerationStub> addPieces(GenerationContext context, BlockPos blockPos) {
        RegistryAccess registryAccess = context.registryAccess();
        StructureTemplateManager structureTemplateManager = context.structureTemplateManager();
        WorldgenRandom worldgenRandom = context.random();
        Registry<StructureTemplatePool> templatePools = registryAccess.registryOrThrow(Registries.TEMPLATE_POOL);
        Rotation rotation = Rotation.NONE;
        StructureTemplatePool structureTemplatePool = templatePools.get(ModTemplatePools.ANU_CASTLE_START);
        StructurePoolElement structurePoolElement = structureTemplatePool.getRandomTemplate(worldgenRandom);

        PoolElementStructurePiece poolElementStructurePiece = new PoolElementStructurePiece(
                structureTemplateManager,
                structurePoolElement,
                blockPos,
                structurePoolElement.getGroundLevelDelta(),
                rotation,
                structurePoolElement.getBoundingBox(structureTemplateManager, blockPos, rotation)
        );
        BoundingBox boundingBox = poolElementStructurePiece.getBoundingBox();
        int posY = blockPos.getY();

        int delta = boundingBox.minY() + poolElementStructurePiece.getGroundLevelDelta();//63+1
        poolElementStructurePiece.move(0, posY - delta, 0);
        return Optional.of(new GenerationStub(
                blockPos,
                builder -> {
                    List<PoolElementStructurePiece> pieces = Lists.newArrayList();
                    pieces.add(poolElementStructurePiece);
                    Placer placer = new Placer(templatePools, 4, structureTemplateManager, pieces, worldgenRandom);
                    placer.placing.addLast(new PieceState(poolElementStructurePiece, 0));
                    while (!placer.placing.isEmpty()) {
                        PieceState pieceState = placer.placing.removeFirst();
                        placer.tryPlacingChildren(pieceState.piece, pieceState.depth);
                    }
                    pieces.forEach(builder::addPiece);
                }
        ));
    }

    @Override
    public @NotNull StructureType<?> type() {
        return ModStructureType.ANU_CASTLE;
    }

    private static final class PieceState {
        private final PoolElementStructurePiece piece;
        private final int depth;

        public PieceState(PoolElementStructurePiece poolElementStructurePiece, int i) {
            this.piece = poolElementStructurePiece;
            this.depth = i;
        }
    }

    private static final class Placer {
        private final Registry<StructureTemplatePool> pools;
        private final int maxDepth;
        private final StructureTemplateManager structureTemplateManager;
        private final List<? super PoolElementStructurePiece> pieces;
        private final RandomSource random;
        private final Deque<PieceState> placing = Queues.newArrayDeque();

        public Placer(Registry<StructureTemplatePool> pools, int maxDepth, StructureTemplateManager structureTemplateManager, List<? super PoolElementStructurePiece> list, RandomSource random) {
            this.pools = pools;
            this.maxDepth = maxDepth;
            this.structureTemplateManager = structureTemplateManager;
            this.pieces = list;
            this.random = random;
        }

        private void tryPlacingChildren(PoolElementStructurePiece baseStructurePiece, int depth) {
            StructurePoolElement structurePoolElement = baseStructurePiece.getElement();
            BlockPos baseStructurePosition = baseStructurePiece.getPosition();
            Rotation baseStructureRotation = baseStructurePiece.getRotation();
            StructureTemplatePool.Projection projection = structurePoolElement.getProjection();
            BoundingBox baseStructureBoundingBox = baseStructurePiece.getBoundingBox();
            block0:
            for (StructureTemplate.StructureBlockInfo baseStructureJigSaw : structurePoolElement.getShuffledJigsawBlocks(structureTemplateManager, baseStructurePosition, baseStructureRotation, random)) {
                ResourceLocation poolLocation = new ResourceLocation(baseStructureJigSaw.nbt().getString("pool"));
                Optional<StructureTemplatePool> baseTargetPool = pools.getOptional(poolLocation);
                if (baseTargetPool.isEmpty() || baseTargetPool.get().size() == 0 && !Objects.equals(poolLocation, Pools.EMPTY.location())) {
                    FossilMod.LOGGER.warn("Empty or non-existent pool: {}", poolLocation);
                    continue;
                }
                ArrayList<StructurePoolElement> targetPoolElements = Lists.newArrayList();
                if (depth != maxDepth) {
                    targetPoolElements.addAll(baseTargetPool.get().getShuffledTemplates(random));
                }
                if (targetPoolElements.isEmpty()) {
                    continue;
                }
                BlockPos baseJigsawPosition = baseStructureJigSaw.pos();
                Direction baseJigsawDirection = JigsawBlock.getFrontFacing(baseStructureJigSaw.state());
                BlockPos expectedJigsawPosition = baseJigsawPosition.relative(baseJigsawDirection);
                int baseMinY = baseStructureBoundingBox.minY();
                int baseJigsawOffset = baseJigsawPosition.getY() - baseMinY;
                StructurePoolElement targetElement = targetPoolElements.get(0);
                List<StructureTemplate.StructureBlockInfo> allTargetJigsaws = targetElement.getShuffledJigsawBlocks(structureTemplateManager, BlockPos.ZERO, baseStructureRotation, random);
                for (StructureTemplate.StructureBlockInfo targetJigsaw : allTargetJigsaws) {
                    int groundLevelDelta = baseStructurePiece.getGroundLevelDelta();
                    if (!JigsawBlock.canAttach(baseStructureJigSaw, targetJigsaw)) continue;
                    BlockPos targetJigsawPosition = targetJigsaw.pos();
                    BlockPos vecToTargetJigsaw = expectedJigsawPosition.subtract(targetJigsawPosition);
                    BoundingBox targetBoundingBox = targetElement.getBoundingBox(structureTemplateManager, vecToTargetJigsaw, baseStructureRotation);
                    PoolElementStructurePiece poolElementStructurePiece2 = new PoolElementStructurePiece(structureTemplateManager, targetElement, vecToTargetJigsaw, groundLevelDelta, baseStructureRotation, targetBoundingBox);
                    baseStructurePiece.addJunction(new JigsawJunction(expectedJigsawPosition.getX(), baseMinY + groundLevelDelta, expectedJigsawPosition.getZ(), 0, targetElement.getProjection()));
                    poolElementStructurePiece2.addJunction(new JigsawJunction(baseJigsawPosition.getX(), baseMinY + baseJigsawOffset - targetJigsawPosition.getY() + groundLevelDelta, baseJigsawPosition.getZ(), 0, projection));
                    pieces.add(poolElementStructurePiece2);
                    if (depth + 1 > maxDepth) continue block0;
                    placing.addLast(new PieceState(poolElementStructurePiece2, depth + 1));
                    continue block0;
                }
            }
        }
    }
}
