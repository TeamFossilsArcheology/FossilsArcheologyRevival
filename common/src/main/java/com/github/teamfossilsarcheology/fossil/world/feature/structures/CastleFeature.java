package com.github.teamfossilsarcheology.fossil.world.feature.structures;

import com.github.teamfossilsarcheology.fossil.Fossil;
import com.google.common.collect.Lists;
import com.google.common.collect.Queues;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.QuartPos;
import net.minecraft.core.Registry;
import net.minecraft.data.worldgen.Pools;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.JigsawBlock;
import net.minecraft.world.level.block.Rotation;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.LegacyRandomSource;
import net.minecraft.world.level.levelgen.WorldgenRandom;
import net.minecraft.world.level.levelgen.feature.JigsawFeature;
import net.minecraft.world.level.levelgen.feature.StructureFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.structure.BoundingBox;
import net.minecraft.world.level.levelgen.structure.PoolElementStructurePiece;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGenerator;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraft.world.level.levelgen.structure.pools.JigsawJunction;
import net.minecraft.world.level.levelgen.structure.pools.JigsawPlacement;
import net.minecraft.world.level.levelgen.structure.pools.StructurePoolElement;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureManager;
import net.minecraft.world.level.levelgen.structure.templatesystem.StructureTemplate;
import org.jetbrains.annotations.NotNull;

import java.util.*;

/**
 * Based on {@link JigsawFeature} but with a fixed rotation and no limitations for structure size
 * (normally jigsaw structures are limited by the size of the starting piece)
 */
public class CastleFeature extends StructureFeature<JigsawConfiguration> {
    public CastleFeature() {
        super(JigsawConfiguration.CODEC, pContext -> {
            //BlockPos blockPos = new BlockPos(0, 63, 0);
            BlockPos blockPos = new BlockPos(pContext.chunkPos().getMinBlockX(), 63, pContext.chunkPos().getMinBlockZ());
            return addPieces(pContext, (structureManager, structurePoolElement, pos, groundLevelDelta, rotation, boundingBox) -> new PoolElementStructurePiece(structureManager, structurePoolElement, pos, groundLevelDelta, Rotation.NONE, boundingBox), blockPos);
        });
    }

    /**
     * Copy of {@link JigsawPlacement#addPieces(PieceGeneratorSupplier.Context, JigsawPlacement.PieceFactory, BlockPos, boolean, boolean) addPieces}
     * that we can optimize a bit because we always place the castle at 0, 63, 0 with no rotation
     */
    private static Optional<PieceGenerator<JigsawConfiguration>> addPieces(PieceGeneratorSupplier.Context<JigsawConfiguration> context2, JigsawPlacement.PieceFactory factory, BlockPos pos) {
        WorldgenRandom worldgenRandom = new WorldgenRandom(new LegacyRandomSource(0L));
        worldgenRandom.setLargeFeatureSeed(context2.seed(), context2.chunkPos().x, context2.chunkPos().z);
        JigsawConfiguration jigsawConfiguration = context2.config();
        ChunkGenerator chunkGenerator = context2.chunkGenerator();
        StructureManager structureManager = context2.structureManager();
        Registry<StructureTemplatePool> templatePoolRegistry = context2.registryAccess().registryOrThrow(Registry.TEMPLATE_POOL_REGISTRY);
        Rotation rotation = Rotation.NONE;
        StructureTemplatePool structureTemplatePool = jigsawConfiguration.startPool().value();
        StructurePoolElement structurePoolElement = structureTemplatePool.templates.get(0);
        PoolElementStructurePiece poolElementStructurePiece = factory.create(structureManager, structurePoolElement, pos, structurePoolElement.getGroundLevelDelta(), rotation, structurePoolElement.getBoundingBox(structureManager, pos, rotation));
        BoundingBox boundingBox = poolElementStructurePiece.getBoundingBox();
        int middleX = (boundingBox.maxX() + boundingBox.minX()) / 2;
        int middleZ = (boundingBox.maxZ() + boundingBox.minZ()) / 2;
        int posY = pos.getY();
        if (!context2.validBiome().test(chunkGenerator.getNoiseBiome(QuartPos.fromBlock(middleX), QuartPos.fromBlock(posY), QuartPos.fromBlock(middleZ)))) {
            return Optional.empty();
        }
        int delta = boundingBox.minY() + poolElementStructurePiece.getGroundLevelDelta();//63+1
        poolElementStructurePiece.move(0, posY - delta, 0);
        return Optional.of((structurePiecesBuilder, context) -> {
            ArrayList<PoolElementStructurePiece> pieces = Lists.newArrayList();
            pieces.add(poolElementStructurePiece);
            Placer placer = new Placer(templatePoolRegistry, jigsawConfiguration.maxDepth(), factory, structureManager, pieces, worldgenRandom);
            placer.placing.addLast(new PieceState(poolElementStructurePiece, 0));
            while (!placer.placing.isEmpty()) {
                PieceState pieceState = placer.placing.removeFirst();
                placer.tryPlacingChildren(pieceState.piece, pieceState.depth);
            }
            pieces.forEach(structurePiecesBuilder::addPiece);
        });
    }

    @Override
    public GenerationStep.@NotNull Decoration step() {
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
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
        private final JigsawPlacement.PieceFactory factory;
        private final StructureManager structureManager;
        private final List<? super PoolElementStructurePiece> pieces;
        private final Random random;
        private final Deque<PieceState> placing = Queues.newArrayDeque();

        public Placer(Registry<StructureTemplatePool> registry, int i, JigsawPlacement.PieceFactory pieceFactory, StructureManager structureManager, List<? super PoolElementStructurePiece> list, Random random) {
            this.pools = registry;
            this.maxDepth = i;
            this.factory = pieceFactory;
            this.structureManager = structureManager;
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
            for (StructureTemplate.StructureBlockInfo baseStructureJigSaw : structurePoolElement.getShuffledJigsawBlocks(structureManager, baseStructurePosition, baseStructureRotation, random)) {
                ResourceLocation poolLocation = new ResourceLocation(baseStructureJigSaw.nbt.getString("pool"));
                Optional<StructureTemplatePool> baseTargetPool = pools.getOptional(poolLocation);
                if (baseTargetPool.isEmpty() || baseTargetPool.get().size() == 0 && !Objects.equals(poolLocation, Pools.EMPTY.location())) {
                    Fossil.LOGGER.warn("Empty or non-existent pool: {}", poolLocation);
                    continue;
                }
                ArrayList<StructurePoolElement> targetPoolElements = Lists.newArrayList();
                if (depth != maxDepth) {
                    targetPoolElements.addAll(baseTargetPool.get().getShuffledTemplates(random));
                }
                if (targetPoolElements.isEmpty()) {
                    continue;
                }
                BlockPos baseJigsawPosition = baseStructureJigSaw.pos;
                Direction baseJigsawDirection = JigsawBlock.getFrontFacing(baseStructureJigSaw.state);
                BlockPos expectedJigsawPosition = baseJigsawPosition.relative(baseJigsawDirection);
                int baseMinY = baseStructureBoundingBox.minY();
                int baseJigsawOffset = baseJigsawPosition.getY() - baseMinY;
                StructurePoolElement targetElement = targetPoolElements.get(0);
                List<StructureTemplate.StructureBlockInfo> allTargetJigsaws = targetElement.getShuffledJigsawBlocks(structureManager, BlockPos.ZERO, baseStructureRotation, random);
                for (StructureTemplate.StructureBlockInfo targetJigsaw : allTargetJigsaws) {
                    int groundLevelDelta = baseStructurePiece.getGroundLevelDelta();
                    if (!JigsawBlock.canAttach(baseStructureJigSaw, targetJigsaw)) continue;
                    BlockPos targetJigsawPosition = targetJigsaw.pos;
                    BlockPos vecToTargetJigsaw = expectedJigsawPosition.subtract(targetJigsawPosition);
                    BoundingBox targetBoundingBox = targetElement.getBoundingBox(structureManager, vecToTargetJigsaw, baseStructureRotation);
                    PoolElementStructurePiece poolElementStructurePiece2 = factory.create(structureManager, targetElement, vecToTargetJigsaw, groundLevelDelta, baseStructureRotation, targetBoundingBox);
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
