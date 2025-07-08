package com.github.teamfossilsarcheology.fossil.client.gui.debug.navigation;

import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.FluidTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.FluidState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.pathfinder.Target;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.jetbrains.annotations.Nullable;

import java.util.EnumSet;

public class PlayerNodeEvaluator {
    protected PathNavigationRegion level;
    protected Player player;
    protected final Int2ObjectMap<Node> nodes = new Int2ObjectOpenHashMap<>();
    protected int entityWidth;
    protected int entityHeight;
    protected int entityDepth;
    protected boolean canPassDoors;
    protected boolean canOpenDoors;
    protected boolean canFloat;
    public static final double SPACE_BETWEEN_WALL_POSTS = 0.5;
    protected float oldWaterCost;
    private final Long2ObjectMap<BlockPathTypes> pathTypesByPosCache = new Long2ObjectOpenHashMap<>();
    private final Object2BooleanMap<AABB> collisionCache = new Object2BooleanOpenHashMap<>();

    public void prepare(PathNavigationRegion l, Player p) {
        level = l;
        player = p;
        nodes.clear();
        entityWidth = Mth.floor(PathingRenderer.getBbWidth() + 1);
        entityHeight = Mth.floor(PathingRenderer.getBbHeight() + 1);
        entityDepth = Mth.floor(PathingRenderer.getBbWidth() + 1);
        oldWaterCost = PathingDebug.getPathfindingMalus(BlockPathTypes.WATER);
    }

    public void done() {
        PathingDebug.setPathfindingMalus(BlockPathTypes.WATER, oldWaterCost);
        pathTypesByPosCache.clear();
        collisionCache.clear();
        level = null;
        player = null;
    }

    protected Node getNode(BlockPos pos) {
        return getNode(pos.getX(), pos.getY(), pos.getZ());
    }

    /**
     * Returns a mapped point or creates and adds one
     */
    protected Node getNode(int x, int y, int z) {
        return nodes.computeIfAbsent(Node.createHash(x, y, z), l -> new Node(x, y, z));
    }

    public Node getStart() {
        int i = Mth.floor(PathingDebug.pos1.getY() + 0.5);
        BlockPathTypes blockPathTypes = getCachedBlockType(player, PathingDebug.pos1.getX(), i, PathingDebug.pos1.getZ());
        if (PathingDebug.getPathfindingMalus(blockPathTypes) < 0) {
            AABB aABB = PathingRenderer.getBigHitbox().move(PathingDebug.pos1);
            BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
            if (hasPositiveMalus(mutableBlockPos.set(aABB.minX, i, aABB.minZ)) || hasPositiveMalus(mutableBlockPos.set(aABB.minX, i, aABB.maxZ)) || hasPositiveMalus(mutableBlockPos.set(aABB.maxX, i, aABB.minZ)) || hasPositiveMalus(mutableBlockPos.set(aABB.maxX, i, aABB.maxZ))) {
                Node node = getNode(mutableBlockPos);
                node.type = getBlockPathType(player, node.asBlockPos());
                node.costMalus = PathingDebug.getPathfindingMalus(node.type);
                return node;
            }
        }
        Node node2 = getNode(PathingDebug.pos1.getX(), i, PathingDebug.pos1.getZ());
        node2.type = getBlockPathType(player, node2.asBlockPos());
        node2.costMalus = PathingDebug.getPathfindingMalus(node2.type);
        return node2;
    }

    protected boolean hasPositiveMalus(BlockPos pos) {
        BlockPathTypes blockPathTypes = getBlockPathType(player, pos);
        return PathingDebug.getPathfindingMalus(blockPathTypes) >= 0;
    }

    public Target getGoal(double x, double y, double z) {
        return new Target(getNode(Mth.floor(x), Mth.floor(y), Mth.floor(z)));
    }


    public int getBlockedNeighbors(Node[] nodes, Node start, BlockPos[] neighborsX) {
        double d = getFloorLevel(new BlockPos(start.x, start.y, start.z));
        int count = 0;
        int countX = 0;
        for (int i = 0; i < entityWidth; ++i) {
            for (int j = 0; j < entityHeight; ++j) {
                for (int k = 0; k < entityDepth; ++k) {
                    Node node = findBlockedNode(start.x + i, start.y + j, start.z + k, d);
                    if (node != null)  {
                        nodes[count++] = node;
                    } else {
                        neighborsX[countX++] = new BlockPos(start.x + i, start.y + j, start.z + k);
                    }
                }
            }
        }
        return count;
    }

    public int getNeighbors(Node[] nodes, Node node) {
        Node node9;
        Node node8;
        Node node7;
        Node node6;
        Node node5;
        Node node4;
        Node node3;
        double d;
        Node node2;
        int i = 0;
        int j = 0;
        BlockPathTypes blockTypeAbove = getCachedBlockType(player, node.x, node.y + 1, node.z);
        BlockPathTypes blockType = getCachedBlockType(player, node.x, node.y, node.z);
        if (PathingDebug.getPathfindingMalus(blockTypeAbove) >= 0 && blockType != BlockPathTypes.STICKY_HONEY) {
            j = Mth.floor(Math.max(1, player.maxUpStep()));
        }
        if (isNeighborValid(node2 = findAcceptedNode(node.x, node.y, node.z + 1, j, d = getFloorLevel(new BlockPos(node.x, node.y, node.z)), Direction.SOUTH, blockType), node)) {
            nodes[i++] = node2;
        }
        if (isNeighborValid(node3 = findAcceptedNode(node.x - 1, node.y, node.z, j, d, Direction.WEST, blockType), node)) {
            nodes[i++] = node3;
        }
        if (isNeighborValid(node4 = findAcceptedNode(node.x + 1, node.y, node.z, j, d, Direction.EAST, blockType), node)) {
            nodes[i++] = node4;
        }
        if (isNeighborValid(node5 = findAcceptedNode(node.x, node.y, node.z - 1, j, d, Direction.NORTH, blockType), node)) {
            nodes[i++] = node5;
        }
        if (isDiagonalValid(node, node3, node5, node6 = findAcceptedNode(node.x - 1, node.y, node.z - 1, j, d, Direction.NORTH, blockType))) {
            nodes[i++] = node6;
        }
        if (isDiagonalValid(node, node4, node5, node7 = findAcceptedNode(node.x + 1, node.y, node.z - 1, j, d, Direction.NORTH, blockType))) {
            nodes[i++] = node7;
        }
        if (isDiagonalValid(node, node3, node2, node8 = findAcceptedNode(node.x - 1, node.y, node.z + 1, j, d, Direction.SOUTH, blockType))) {
            nodes[i++] = node8;
        }
        if (isDiagonalValid(node, node4, node2, node9 = findAcceptedNode(node.x + 1, node.y, node.z + 1, j, d, Direction.SOUTH, blockType))) {
            nodes[i++] = node9;
        }
        return i;
    }

    protected boolean isNeighborValid(@Nullable Node node, Node node2) {
        return node != null && !node.closed && (node.costMalus >= 0 || node2.costMalus < 0);
    }

    protected boolean isDiagonalValid(Node node, @Nullable Node node2, @Nullable Node node3, @Nullable Node node4) {
        if (node4 == null || node3 == null || node2 == null) {
            return false;
        }
        if (node4.closed) {
            return false;
        }
        if (node3.y > node.y || node2.y > node.y) {
            return false;
        }
        if (node2.type == BlockPathTypes.WALKABLE_DOOR || node3.type == BlockPathTypes.WALKABLE_DOOR || node4.type == BlockPathTypes.WALKABLE_DOOR) {
            return false;
        }
        boolean bl = node3.type == BlockPathTypes.FENCE && node2.type == BlockPathTypes.FENCE && (double) PathingRenderer.getBbWidth() < 0.5;
        return node4.costMalus >= 0 && (node3.y < node.y || node3.costMalus >= 0 || bl) && (node2.y < node.y || node2.costMalus >= 0 || bl);
    }

    private boolean canReachWithoutCollision(Node node) {
        Vec3 vec3 = new Vec3((double)node.x - player.getX(), (double)node.y - player.getY(), (double)node.z - player.getZ());
        AABB aABB = player.getBoundingBox();
        int i = Mth.ceil(vec3.length() / aABB.getSize());
        vec3 = vec3.scale(1 / (float)i);
        for (int j = 1; j <= i; ++j) {
            if (!hasCollisions(aABB = aABB.move(vec3))) continue;
            return false;
        }
        return true;
    }

    protected double getFloorLevel(BlockPos pos) {
        return getFloorLevel(level, pos);
    }

    public static double getFloorLevel(BlockGetter level, BlockPos pos) {
        BlockPos blockPos = pos.below();
        VoxelShape voxelShape = level.getBlockState(blockPos).getCollisionShape(level, blockPos);
        return (double)blockPos.getY() + (voxelShape.isEmpty() ? 0.0 : voxelShape.max(Direction.Axis.Y));
    }

    protected boolean isAmphibious() {
        return false;
    }

    @Nullable
    protected Node findBlockedNode(int x, int y, int z, double d) {
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        double e = getFloorLevel(mutableBlockPos.set(x, y, z));
        if ((y - d > entityHeight) && e - d > 1.125) {
            return null;
        }

        BlockPathTypes blockPathType = getBlockPathType(level, x, y, z);
        blockPathType = evaluateBlockPathType(level, canOpenDoors, false, BlockPos.ZERO, blockPathType);
        Node node = getNode(x, y, z);
        node.closed = true;
        node.type = blockPathType;
        node.costMalus = blockPathType.getMalus();
        return node;
    }

    @Nullable
    protected Node findAcceptedNode(int x, int y, int z, int i, double d, Direction direction, BlockPathTypes blockPathTypes) {
        double j;
        double h;
        Node node = null;
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        double e = getFloorLevel(mutableBlockPos.set(x, y, z));
        if ((e - d > entityHeight) && e - d > 1.125) {
            return null;
        }
        BlockPathTypes blockPathTypes2 = getCachedBlockType(player, x, y, z);
        float f = PathingDebug.getPathfindingMalus(blockPathTypes2);
        double g = (double) PathingRenderer.getBbWidth() / 2.0;
        if (f >= 0) {
            node = getNode(x, y, z);
            node.type = blockPathTypes2;
            node.costMalus = Math.max(node.costMalus, f);
        }
        if (blockPathTypes == BlockPathTypes.FENCE && node != null && node.costMalus >= 0 && !canReachWithoutCollision(node)) {
            node = null;
        }
        if (blockPathTypes2 == BlockPathTypes.WALKABLE || isAmphibious() && blockPathTypes2 == BlockPathTypes.WATER) {
            return node;
        }
        if ((node == null || node.costMalus < 0) && i > 0 && blockPathTypes2 != BlockPathTypes.FENCE && blockPathTypes2 != BlockPathTypes.UNPASSABLE_RAIL && blockPathTypes2 != BlockPathTypes.TRAPDOOR && blockPathTypes2 != BlockPathTypes.POWDER_SNOW && (node = findAcceptedNode(x, y + 1, z, i - 1, d, direction, blockPathTypes)) != null && (node.type == BlockPathTypes.OPEN || node.type == BlockPathTypes.WALKABLE) && PathingRenderer.getBbWidth() < 1 && hasCollisions(new AABB((h = (double)(x - direction.getStepX()) + 0.5) - g, getFloorLevel(level, mutableBlockPos.set(h, (double)(y + 1), j = (double)(z - direction.getStepZ()) + 0.5)) + 0.001, j - g, h + g, (double) PathingRenderer.getBbHeight() + getFloorLevel(level, mutableBlockPos.set((double)node.x, (double)node.y, (double)node.z)) - 0.002, j + g))) {
            node = null;
        }
        if (!isAmphibious() && blockPathTypes2 == BlockPathTypes.WATER && !canFloat()) {
            if (getCachedBlockType(player, x, y - 1, z) != BlockPathTypes.WATER) {
                return node;
            }
            while (y > player.level().getMinBuildHeight()) {
                if ((blockPathTypes2 = getCachedBlockType(player, x, --y, z)) == BlockPathTypes.WATER) {
                    node = getNode(x, y, z);
                    node.type = blockPathTypes2;
                    node.costMalus = Math.max(node.costMalus, PathingDebug.getPathfindingMalus(blockPathTypes2));
                    continue;
                }
                return node;
            }
        }
        if (blockPathTypes2 == BlockPathTypes.OPEN) {
            int k = 0;
            int l = y;
            while (blockPathTypes2 == BlockPathTypes.OPEN) {
                if (--y < player.level().getMinBuildHeight()) {
                    Node node2 = getNode(x, l, z);
                    node2.type = BlockPathTypes.BLOCKED;
                    node2.costMalus = -1;
                    return node2;
                }
                if (k++ >= player.getMaxFallDistance()) {
                    Node node2 = getNode(x, y, z);
                    node2.type = BlockPathTypes.BLOCKED;
                    node2.costMalus = -1;
                    return node2;
                }
                blockPathTypes2 = getCachedBlockType(player, x, y, z);
                f = PathingDebug.getPathfindingMalus(blockPathTypes2);
                if (blockPathTypes2 != BlockPathTypes.OPEN && f >= 0) {
                    node = getNode(x, y, z);
                    node.type = blockPathTypes2;
                    node.costMalus = Math.max(node.costMalus, f);
                    break;
                }
                if (!(f < 0)) continue;
                Node node2 = getNode(x, y, z);
                node2.type = BlockPathTypes.BLOCKED;
                node2.costMalus = -1;
                return node2;
            }
        }
        if (blockPathTypes2 == BlockPathTypes.FENCE) {
            node = getNode(x, y, z);
            node.closed = true;
            node.type = blockPathTypes2;
            node.costMalus = blockPathTypes2.getMalus();
        }
        return node;
    }

    private boolean hasCollisions(AABB aABB) {
        return collisionCache.computeIfAbsent(aABB, object -> !level.noCollision(player, aABB));
    }

    /**
     * Returns the significant (e.g LAVA if the entity were half in lava) node type at the location taking the surroundings and the entity size in account
     */
    public BlockPathTypes getBlockPathType(BlockGetter blockaccess, int x, int y, int z, Player entityliving, int xSize, int ySize, int zSize, boolean canBreakDoors, boolean canEnterDoors) {
        EnumSet<BlockPathTypes> enumSet = EnumSet.noneOf(BlockPathTypes.class);
        BlockPathTypes blockPathTypes = BlockPathTypes.BLOCKED;
        BlockPos blockPos = entityliving.blockPosition();
        blockPathTypes = getBlockPathTypes(blockaccess, x, y, z, xSize, ySize, zSize, canBreakDoors, canEnterDoors, enumSet, blockPathTypes, blockPos);
        if (enumSet.contains(BlockPathTypes.FENCE)) {
            return BlockPathTypes.FENCE;
        }
        if (enumSet.contains(BlockPathTypes.UNPASSABLE_RAIL)) {
            return BlockPathTypes.UNPASSABLE_RAIL;
        }
        BlockPathTypes blockPathTypes2 = BlockPathTypes.BLOCKED;
        for (BlockPathTypes blockPathTypes3 : enumSet) {
            if (PathingDebug.getPathfindingMalus(blockPathTypes3) < 0) {
                return blockPathTypes3;
            }
            if (PathingDebug.getPathfindingMalus(blockPathTypes3) < PathingDebug.getPathfindingMalus(blockPathTypes2)) continue;
            blockPathTypes2 = blockPathTypes3;
        }
        if (blockPathTypes == BlockPathTypes.OPEN && PathingDebug.getPathfindingMalus(blockPathTypes2) == 0 && xSize <= 1) {
            return BlockPathTypes.OPEN;
        }
        return blockPathTypes2;
    }

    public BlockPathTypes getBlockPathTypes(BlockGetter level, int x, int y, int z, int xSize, int ySize, int zSize, boolean canOpenDoors, boolean canEnterDoors, EnumSet<BlockPathTypes> nodeTypeEnum, BlockPathTypes nodeType, BlockPos pos) {
        for (int i = 0; i < xSize; ++i) {
            for (int j = 0; j < ySize; ++j) {
                for (int k = 0; k < zSize; ++k) {
                    int l = i + x;
                    int m = j + y;
                    int n = k + z;
                    BlockPathTypes blockPathTypes = getBlockPathType(level, l, m, n);
                    blockPathTypes = evaluateBlockPathType(level, canOpenDoors, canEnterDoors, pos, blockPathTypes);
                    if (i == 0 && j == 0 && k == 0) {
                        nodeType = blockPathTypes;
                    }
                    nodeTypeEnum.add(blockPathTypes);
                }
            }
        }
        return nodeType;
    }

    /**
     * Returns the node type at the specified postion taking the block below into account
     */
    protected BlockPathTypes evaluateBlockPathType(BlockGetter level, boolean canOpenDoors, boolean canEnterDoors, BlockPos pos, BlockPathTypes nodeType) {
        if (nodeType == BlockPathTypes.DOOR_WOOD_CLOSED && canOpenDoors && canEnterDoors) {
            nodeType = BlockPathTypes.WALKABLE_DOOR;
        }
        if (nodeType == BlockPathTypes.DOOR_OPEN && !canEnterDoors) {
            nodeType = BlockPathTypes.BLOCKED;
        }
        if (nodeType == BlockPathTypes.RAIL && !(level.getBlockState(pos).getBlock() instanceof BaseRailBlock) && !(level.getBlockState(pos.below()).getBlock() instanceof BaseRailBlock)) {
            nodeType = BlockPathTypes.UNPASSABLE_RAIL;
        }
        if (nodeType == BlockPathTypes.LEAVES) {
            nodeType = BlockPathTypes.BLOCKED;
        }
        return nodeType;
    }
    private BlockPathTypes getBlockPathType(Player entityliving, BlockPos pos) {
        return getCachedBlockType(entityliving, pos.getX(), pos.getY(), pos.getZ());
    }
    protected BlockPathTypes getCachedBlockType(Player entity, int x, int y, int z) {
        return pathTypesByPosCache.computeIfAbsent(BlockPos.asLong(x, y, z), l -> getBlockPathType(level, x, y, z, entity, entityWidth, entityHeight, entityDepth, canOpenDoors(), canPassDoors()));
    }
    public BlockPathTypes getBlockPathType(BlockGetter level, int x, int y, int z) {
        return getBlockPathTypeStatic(level, new BlockPos.MutableBlockPos(x, y, z));
    }

    /**
     * Returns the node type at the specified postion taking the block below into account
     */
    public static BlockPathTypes getBlockPathTypeStatic(BlockGetter level, BlockPos.MutableBlockPos pos) {
        int i = pos.getX();
        int j = pos.getY();
        int k = pos.getZ();
        BlockPathTypes blockPathTypes = getBlockPathTypeRaw(level, pos);
        if (blockPathTypes == BlockPathTypes.OPEN && j >= level.getMinBuildHeight() + 1) {
            BlockPathTypes blockTypeBelow = getBlockPathTypeRaw(level, pos.set(i, j - 1, k));
            blockPathTypes = blockTypeBelow == BlockPathTypes.WALKABLE || blockTypeBelow == BlockPathTypes.OPEN || blockTypeBelow == BlockPathTypes.WATER || blockTypeBelow == BlockPathTypes.LAVA ? BlockPathTypes.OPEN : BlockPathTypes.WALKABLE;
            if (blockTypeBelow == BlockPathTypes.DAMAGE_FIRE) {
                blockPathTypes = BlockPathTypes.DAMAGE_FIRE;
            }
            if (blockTypeBelow == BlockPathTypes.DAMAGE_OTHER) {
                blockPathTypes = BlockPathTypes.DAMAGE_OTHER;
            }
            if (blockTypeBelow == BlockPathTypes.STICKY_HONEY) {
                blockPathTypes = BlockPathTypes.STICKY_HONEY;
            }
            if (blockTypeBelow == BlockPathTypes.POWDER_SNOW) {
                blockPathTypes = BlockPathTypes.DANGER_POWDER_SNOW;
            }
        }
        if (blockPathTypes == BlockPathTypes.WALKABLE) {
            blockPathTypes = checkNeighbourBlocks(level, pos.set(i, j, k), blockPathTypes);
        }
        return blockPathTypes;
    }

    /**
     * Returns possible dangers in a 3x3 cube, otherwise nodeType
     */
    public static BlockPathTypes checkNeighbourBlocks(BlockGetter level, BlockPos.MutableBlockPos centerPos, BlockPathTypes nodeType) {
        int i = centerPos.getX();
        int j = centerPos.getY();
        int k = centerPos.getZ();
        for (int l = -1; l <= 1; ++l) {
            for (int m = -1; m <= 1; ++m) {
                for (int n = -1; n <= 1; ++n) {
                    if (l == 0 && n == 0) continue;
                    centerPos.set(i + l, j + m, k + n);
                    BlockState blockState = level.getBlockState(centerPos);
                    if (blockState.is(Blocks.CACTUS) || blockState.is(Blocks.SWEET_BERRY_BUSH)) {
                        return BlockPathTypes.DANGER_OTHER;
                    }
                    if (isBurningBlock(blockState)) {
                        return BlockPathTypes.DANGER_FIRE;
                    }
                    if (!level.getFluidState(centerPos).is(FluidTags.WATER)) continue;
                    return BlockPathTypes.WATER_BORDER;
                }
            }
        }
        return nodeType;
    }

    protected static BlockPathTypes getBlockPathTypeRaw(BlockGetter level, BlockPos pos) {
        BlockState blockState = level.getBlockState(pos);
        Block block = blockState.getBlock();
        if (blockState.isAir()) {
            return BlockPathTypes.OPEN;
        }
        if (blockState.is(BlockTags.TRAPDOORS) || blockState.is(Blocks.LILY_PAD) || blockState.is(Blocks.BIG_DRIPLEAF)) {
            return BlockPathTypes.TRAPDOOR;
        }
        if (blockState.is(Blocks.POWDER_SNOW)) {
            return BlockPathTypes.POWDER_SNOW;
        }
        if (blockState.is(Blocks.CACTUS) || blockState.is(Blocks.SWEET_BERRY_BUSH)) {
            return BlockPathTypes.DAMAGE_OTHER;
        }
        if (blockState.is(Blocks.HONEY_BLOCK)) {
            return BlockPathTypes.STICKY_HONEY;
        }
        if (blockState.is(Blocks.COCOA)) {
            return BlockPathTypes.COCOA;
        }
        FluidState fluidState = level.getFluidState(pos);
        if (fluidState.is(FluidTags.LAVA)) {
            return BlockPathTypes.LAVA;
        }
        if (isBurningBlock(blockState)) {
            return BlockPathTypes.DAMAGE_FIRE;
        }
        if (DoorBlock.isWoodenDoor(blockState) && Boolean.FALSE.equals(blockState.getValue(DoorBlock.OPEN))) {
            return BlockPathTypes.DOOR_WOOD_CLOSED;
        }
        if (block instanceof DoorBlock doorBlock) {
            if (Boolean.TRUE.equals(blockState.getValue(DoorBlock.OPEN))) {
                return BlockPathTypes.DOOR_OPEN;
            } else {
                return doorBlock.type().canOpenByHand() ? BlockPathTypes.DOOR_WOOD_CLOSED : BlockPathTypes.DOOR_IRON_CLOSED;
            }
        }
        if (block instanceof BaseRailBlock) {
            return BlockPathTypes.RAIL;
        }
        if (block instanceof LeavesBlock) {
            return BlockPathTypes.LEAVES;
        }
        if (blockState.is(BlockTags.FENCES) || blockState.is(BlockTags.WALLS) || block instanceof FenceGateBlock && !blockState.getValue(FenceGateBlock.OPEN).booleanValue()) {
            return BlockPathTypes.FENCE;
        }
        if (!blockState.isPathfindable(level, pos, PathComputationType.LAND)) {
            return BlockPathTypes.BLOCKED;
        }
        if (fluidState.is(FluidTags.WATER)) {
            return BlockPathTypes.WATER;
        }
        return BlockPathTypes.OPEN;
    }

    /**
     * Checks whether the specified block state can cause burn damage
     */
    public static boolean isBurningBlock(BlockState state) {
        return state.is(BlockTags.FIRE) || state.is(Blocks.LAVA) || state.is(Blocks.MAGMA_BLOCK) || CampfireBlock.isLitCampfire(state) || state.is(Blocks.LAVA_CAULDRON);
    }

    public void setCanFloat(boolean canSwim) {
        canFloat = canSwim;
    }

    public boolean canPassDoors() {
        return canPassDoors;
    }

    public boolean canOpenDoors() {
        return canOpenDoors;
    }

    public boolean canFloat() {
        return canFloat;
    }
}
