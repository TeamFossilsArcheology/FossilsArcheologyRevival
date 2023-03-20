package com.fossil.fossil.entity.ai;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.network.DebugHandler;
import com.fossil.fossil.network.MarkMessage;
import it.unimi.dsi.fastutil.longs.LongArrayList;
import it.unimi.dsi.fastutil.longs.LongList;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.entity.ai.goal.MoveToBlockGoal;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.Path;

import java.util.EnumSet;

/**
 * Custom Implementation of {@link MoveToBlockGoal} that caches unreachable targets and removes them from the pool of targets.
 * The cache will be cleared after if it is full and a necessary amount of ticks has passed.
 */
public abstract class CacheMoveToBlockGoal extends Goal {
    private static final int GIVE_UP_TICKS = 1200;
    private static final int STAY_TICKS = 1200;
    private static final int INTERVAL_TICKS = 200;
    protected static final int CLEAR_TICKS = 1200;
    protected final Prehistoric entity;
    public final double speedModifier;
    /**
     * Controls task execution delay
     */
    protected int nextStartTick;
    protected int tryTicks;
    /**
     * Controls cache clear delay and task execution delay
     */
    protected int clearTicks;
    protected int stuckTicks;
    /**
     * Block to move to
     */
    protected BlockPos blockPos = BlockPos.ZERO;
    private boolean reachedTarget;
    protected final int searchRange;
    private final int verticalSearchRange;
    protected int verticalSearchStart;
    private Path path;
    /**
     * Cache that contains all block positions that should be avoided
     */
    protected final LongList cache = new LongArrayList();

    public CacheMoveToBlockGoal(Prehistoric entity, double speedModifier, int searchRange) {
        this(entity, speedModifier, searchRange, 1);
    }

    public CacheMoveToBlockGoal(Prehistoric entity, double speedModifier, int searchRange, int verticalSearchRange) {
        this.entity = entity;
        this.speedModifier = speedModifier;
        this.searchRange = searchRange;
        this.verticalSearchStart = 0;
        this.verticalSearchRange = verticalSearchRange;
        this.setFlags(EnumSet.of(Goal.Flag.MOVE, Goal.Flag.JUMP));
    }

    /**
     * @implNote If the cache is set to be cleared this will return false until the cache has been cleared.
     */
    @Override
    public boolean canUse() {
        boolean dontStart = false;
        if (clearTicks > 0) {
            clearTicks--;
            if (clearTicks == 0) {
                cache.clear();
            }
            dontStart = true;
        }
        if (nextStartTick > 0) {
            nextStartTick--;
            dontStart = true;
        }
        if (dontStart) return false;
        nextStartTick = nextStartTick(this.entity);

        //TODO: Movement Soft Blocked
        return findNearestBlock();
    }

    /**
     * Returns a random integer between {@link CacheMoveToBlockGoal#INTERVAL_TICKS} and 2x {@link CacheMoveToBlockGoal#INTERVAL_TICKS}.
     * Used to set the delay between two goal executions.
     *
     * @return a random integer between {@link CacheMoveToBlockGoal#INTERVAL_TICKS} and 2x {@link CacheMoveToBlockGoal#INTERVAL_TICKS}
     */
    protected int nextStartTick(Prehistoric entity) {
        return reducedTickDelay(INTERVAL_TICKS + entity.getRandom().nextInt(INTERVAL_TICKS));
    }

    /**
     * @implNote Will not continue if the execution took to long to reach the target or stayed too long at the target.
     */
    @Override
    public boolean canContinueToUse() {
        //TODO: Movement Soft Blocked
        return this.tryTicks >= -STAY_TICKS && this.tryTicks < GIVE_UP_TICKS && this.isValidTarget(this.entity.level, this.blockPos);
    }

    @Override
    public void start() {
        this.moveMobToBlock();
        this.tryTicks = 0;
    }

    @Override
    public void stop() {
        resetBlocks();
        entity.setCurrentAnimation(entity.nextIdleAnimation());
    }

    protected void moveMobToBlock() {
        resetBlocks();
        var old = entity.getAttribute(Attributes.FOLLOW_RANGE).getBaseValue();
        entity.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(32);
        path = entity.getNavigation().createPath(getMoveToTarget().getX() + 0.5d, getMoveToTarget().getY(), getMoveToTarget().getZ() + 0.5d, 1);
        entity.getAttribute(Attributes.FOLLOW_RANGE).setBaseValue(old);
        setBlocks(false);
        entity.getNavigation().moveTo(path, speedModifier);
    }

    public double acceptedDistance() {
        if (entity.isCustomMultiPart()) {
            return (entity.getBbWidth() + entity.getCustomParts()[1].getBbWidth()) / 2;
        }
        return entity.getBbWidth() / 2 + 1;
    }

    protected BlockPos getMoveToTarget() {
        return this.blockPos.above();
    }

    @Override
    public boolean requiresUpdateEveryTick() {
        return true;
    }

    /**
     * Returns the amount of ticks the entity will stay stuck before aborting the current execution of the goal.
     *
     * @return the amount of ticks the entity will stay stuck before aborting the current execution of the goal
     */
    protected int getStuckPatience() {
        return 100;
    }

    /**
     * @implNote Will stop the entity navigation when the target is reached.
     */
    @Override
    public void tick() {
        BlockPos blockPos = this.getMoveToTarget();
        if (checkReachedTarget()) {
            reachedTarget = true;
            entity.getNavigation().stop();
            --tryTicks;
        } else if (stuckTicks > getStuckPatience()) {
            cache.add(blockPos.asLong());
            tryTicks = GIVE_UP_TICKS;
        } else {
            reachedTarget = false;
            ++tryTicks;
            if (shouldRecalculatePath()) {
                moveMobToBlock();
            }
        }
        if ((!isReachedTarget() && entity.getNavigation().isDone()) || entity.getNavigation().isStuck()) {
            setBlocks(true);
            //if the navigation is done but hasn't reached the target, there is no complete path to the target.
            //if the navigation is stuck, the entity is stuck before reaching the end of the current path.
            stuckTicks++;
        } else {
            stuckTicks = 0;
        }
    }

    public boolean shouldRecalculatePath() {
        return this.tryTicks % 40 == 0;
    }

    protected boolean checkReachedTarget() {
        return blockPos.closerToCenterThan(entity.position(), acceptedDistance());
    }

    protected boolean isReachedTarget() {
        return reachedTarget;
    }

    /**
     * Searches and sets new destination block and returns true if a suitable block (specified in {@link CacheMoveToBlockGoal#isValidTarget}) can be found.
     *
     * @implNote If no block has been found the cache will be set to be cleared.
     */
    protected boolean findNearestBlock() {
        BlockPos entityPos = entity.blockPosition();
        BlockPos.MutableBlockPos mutableBlockPos = new BlockPos.MutableBlockPos();
        int y = verticalSearchStart;
        while (y <= verticalSearchRange) {
            for (int l = 0; l < searchRange; ++l) {
                int x = 0;
                while (x <= l) {
                    int z = x < l && x > -l ? l : 0;
                    while (z <= l) {
                        mutableBlockPos.setWithOffset(entityPos, x, y - 1, z);
                        if (entity.isWithinRestriction(mutableBlockPos) && isValidTarget(entity.level, mutableBlockPos)) {
                            blockPos = mutableBlockPos;
                            return true;
                        }
                        z = z > 0 ? -z : 1 - z;
                    }
                    x = x > 0 ? -x : 1 - x;
                }
            }
            y = y > 0 ? -y : 1 - y;
        }
        clearTicks = cache.size() > 0 ? CLEAR_TICKS : 0;
        return false;
    }

    /**
     * Return true to set given position as destination
     *
     * @implNote Returns false if the cache contains the block position
     */
    protected boolean isValidTarget(LevelReader level, BlockPos pos) {
        return !cache.contains(pos.asLong());
    }

    private void setBlocks(boolean stuck) {
        if (path != null) {
            int[] targets = new int[path.getNodeCount() * 3];
            BlockState[] blocks = new BlockState[path.getNodeCount()];
            for (int i = 0; i < path.getNodeCount(); i++) {
                Node node = path.getNode(i);
                if (stuck) {
                    blocks[i] = node.equals(path.getEndNode()) && path.getNodeCount() != 1 ? Blocks.GOLD_BLOCK.defaultBlockState() : Blocks.REDSTONE_BLOCK.defaultBlockState();
                } else {
                    blocks[i] = node.equals(path.getEndNode()) ? Blocks.GOLD_BLOCK.defaultBlockState() : Blocks.EMERALD_BLOCK.defaultBlockState();
                }
                targets[3 * i] = node.x;
                targets[3 * i + 1] = node.y;
                targets[3 * i + 2] = node.z;
            }
            DebugHandler.DEBUG_CHANNEL.sendToPlayers(((ServerLevel) entity.level).getPlayers(serverPlayer -> serverPlayer.hasLineOfSight(entity)),
                    new MarkMessage(targets, blocks));
        }
    }

    private void resetBlocks() {
        if (path != null) {
            int[] targets = new int[path.getNodeCount() * 3];
            BlockState[] blocks = new BlockState[path.getNodeCount()];
            for (int i = 0; i < path.getNodeCount(); i++) {
                Node node = path.getNode(i);
                blocks[i] = entity.level.getBlockState(new BlockPos(node.x, node.y, node.z).below());
                targets[3 * i] = node.x;
                targets[3 * i + 1] = node.y;
                targets[3 * i + 2] = node.z;
            }
            DebugHandler.DEBUG_CHANNEL.sendToPlayers(((ServerLevel) entity.level).getPlayers(serverPlayer -> serverPlayer.hasLineOfSight(entity)),
                    new MarkMessage(targets, blocks));
        }
    }
}
