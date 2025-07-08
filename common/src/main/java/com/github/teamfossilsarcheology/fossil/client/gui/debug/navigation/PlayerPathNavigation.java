package com.github.teamfossilsarcheology.fossil.client.gui.debug.navigation;

import com.google.common.collect.ImmutableSet;
import net.minecraft.Util;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Vec3i;
import net.minecraft.tags.BlockTags;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.PathNavigationRegion;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Node;
import net.minecraft.world.level.pathfinder.WalkNodeEvaluator;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class PlayerPathNavigation {

    private static final int MAX_TIME_RECOMPUTE = 20;
    protected final Player player;
    protected final Level level;
    @Nullable
    protected PlayerPath path;
    protected double speedModifier = 1;
    protected int tick;
    protected int lastStuckCheck;
    protected Vec3 lastStuckCheckPos = Vec3.ZERO;
    protected Vec3i timeoutCachedNode = Vec3i.ZERO;
    protected long timeoutTimer;
    protected long lastTimeoutCheck;
    protected double timeoutLimit;
    protected float maxDistanceToWaypoint = 0.5f;
    protected boolean hasDelayedRecomputation;
    protected long timeLastRecompute;
    protected PlayerNodeEvaluator nodeEvaluator;
    @Nullable
    private BlockPos targetPos;
    /**
     * Distance in which a path point counts as target-reaching
     */
    private int reachRange;
    protected float maxVisitedNodesMultiplier = 1.0f;
    private final PlayerPathFinder pathFinder;
    private boolean isStuck;

    //Render
    public boolean shouldRender;
    public String name;


    public PlayerPathNavigation(Player p, Level l, String name) {
        player = p;
        level = l;
        this.name = name;
        int i = Mth.floor(16f * 16f);
        pathFinder = createPathFinder(i);
        moveControl = new DebugMoveControl(p);
    }

    @Nullable
    public BlockPos getTargetPos() {
        return targetPos;
    }

    protected PlayerPathFinder createPathFinder(int maxVisitedNodes) {
        nodeEvaluator = new PlayerNodeEvaluator();
        return new PlayerPathFinder(nodeEvaluator, maxVisitedNodes);
    }

    public void recomputePath() {
        if (level.getGameTime() - timeLastRecompute > 20L) {
            if (targetPos != null) {
                path = null;
                path = createPath(targetPos, reachRange);
                timeLastRecompute = level.getGameTime();
                hasDelayedRecomputation = false;
            }
        } else {
            hasDelayedRecomputation = true;
        }
    }

    /**
     * Returns path to given BlockPos
     */
    @Nullable
    public PlayerPath createPath(BlockPos pos, int accuracy) {
        BlockPos blockPos;
        if (level.getBlockState(pos).isAir()) {
            blockPos = pos.below();
            while (blockPos.getY() > level.getMinBuildHeight() && level.getBlockState(blockPos).isAir()) {
                blockPos = blockPos.below();
            }
            if (blockPos.getY() > level.getMinBuildHeight()) {
                return createPath(ImmutableSet.of(pos), 8, false, accuracy);
            }
            while (blockPos.getY() < level.getMaxBuildHeight() && level.getBlockState(blockPos).isAir()) {
                blockPos = blockPos.above();
            }
            pos = blockPos;
        }
        if (level.getBlockState(pos).isSolid()) {
            blockPos = pos.above();
            while (blockPos.getY() < level.getMaxBuildHeight() && level.getBlockState(blockPos).isSolid()) {
                blockPos = blockPos.above();
            }
            return createPath(ImmutableSet.of(pos), 8, false, accuracy);
        }
        return createPath(ImmutableSet.of(pos), 8, false, accuracy);
    }

    @Nullable
    public PlayerPath createPath(Entity entity, int accuracy) {
        return createPath(entity.blockPosition(), accuracy);
    }

    @Nullable
    protected PlayerPath createPath(Set<BlockPos> targets, int regionOffset, boolean offsetUpward, int accuracy) {
        return createPath(targets, regionOffset, offsetUpward, accuracy, 32);
    }

    @Nullable
    protected PlayerPath createPath(Set<BlockPos> targets, int regionOffset, boolean offsetUpward, int accuracy, float followRange) {
        if (targets.isEmpty()) {
            return null;
        }
        if (player.getY() < (double) level.getMinBuildHeight()) {
            return null;
        }
        if (!canUpdatePath()) {
            return null;
        }
        if (path != null && !path.isDone() && targets.contains(targetPos)) {
            return path;
        }
        BlockPos blockPos = offsetUpward ? player.blockPosition().above() : player.blockPosition();
        int i = (int) (followRange + (float) regionOffset);
        PathNavigationRegion pathNavigationRegion = new PathNavigationRegion(level, blockPos.offset(-i, -i, -i), blockPos.offset(i, i, i));
        PlayerPath path = pathFinder.findPath(pathNavigationRegion, player, targets, followRange, accuracy, maxVisitedNodesMultiplier);
        if (path != null && path.getTarget() != null) {
            targetPos = path.getTarget();
            reachRange = accuracy;
            resetStuckTimeout();
        }
        return path;
    }

    /**
     * Try to find and set a path to EntityLiving. Returns true if successful. Args : entity, speed
     */
    public boolean moveTo(BlockPos targetPos) {
        PlayerPath path = createPath(targetPos, 1);
        return path != null && moveTo(path);
    }

    /**
     * Sets a new path. If it's diferent from the old path. Checks to adjust path for sun avoiding, and stores start coords. Args : path, speed
     */
    public boolean moveTo(@Nullable PlayerPath pathentity) {
        if (pathentity == null) {
            path = null;
            return false;
        }
        if (!pathentity.sameAs(path)) {
            path = pathentity;
        }
        if (isDone()) {
            return false;
        }
        trimPath();
        if (path.getNodeCount() <= 0) {
            return false;
        }
        lastStuckCheck = tick;
        lastStuckCheckPos = getTempMobPos();
        return true;
    }

    /**
     * gets the actively used PathEntity
     */
    @Nullable
    public PlayerPath getPath() {
        return path;
    }

    public void tick() {
        Vec3 vec3;
        ++tick;
        if (hasDelayedRecomputation) {
            recomputePath();
        }
        if (isDone()) {
            return;
        }
        if (canUpdatePath()) {
            followThePath();
        } else if (path != null && !path.isDone()) {
            vec3 = getTempMobPos();
            Vec3 vec32 = path.getNextEntityPos(player);
            if (vec3.y > vec32.y && !player.onGround() && Mth.floor(vec3.x) == Mth.floor(vec32.x) && Mth.floor(vec3.z) == Mth.floor(vec32.z)) {
                path.advance();
            }
        }
        if (isDone()) {
            return;
        }
        vec3 = path.getNextEntityPos(player);
        setNextWantedPosition(vec3.x, getGroundY(vec3), vec3.z);
        vec3 = path.getSweepEntityPos(player);
        setSweepWantedPosition(vec3.x, getGroundY(vec3), vec3.z);
        moveControl.tick();
    }

    public Vec3 wantedPos;
    public Vec3 sweepStartPos;
    public Vec3 sweepWantedPos;
    public DebugMoveControl moveControl;

    public void setNextWantedPosition(double x, double y, double z) {
        wantedPos = new Vec3(x, y, z);
        moveControl.setWantedPosition(x, y, z, speedModifier);
    }

    public void setSweepWantedPosition(double x, double y, double z) {
        sweepWantedPos = new Vec3(x, y, z);
    }

    public void setSweepStartPos(Vec3 vec) {
        sweepStartPos = vec;
    }

    protected double getGroundY(Vec3 vec) {
        BlockPos blockPos = BlockPos.containing(vec);
        return level.getBlockState(blockPos.below()).isAir() ? vec.y : WalkNodeEvaluator.getFloorLevel(level, blockPos);
    }

    protected void followThePath() {
        Vec3 vec3 = getTempMobPos();
        maxDistanceToWaypoint = PathingRenderer.getBbWidth() > 0.75f ? PathingRenderer.getBbWidth() / 2.0f : 0.75f - PathingRenderer.getBbWidth() / 2.0f;
        BlockPos vec3i = path.getNextNodePos();
        double d = Math.abs(player.getX() - (vec3i.getX() + 0.5));
        double e = Math.abs(player.getY() - vec3i.getY());
        double f = Math.abs(player.getZ() - (vec3i.getZ() + 0.5));
        boolean bl = d < maxDistanceToWaypoint && f < maxDistanceToWaypoint && e < 1.0;
        if (bl || canCutCorner(path.getNextNode().type) && shouldTargetNextNodeInDirection(vec3)) {
            path.advance();
        }
        //doStuckDetection(vec3);
    }

    public boolean canCutCorner(BlockPathTypes pathType) {
        return pathType != BlockPathTypes.DANGER_FIRE && pathType != BlockPathTypes.DANGER_OTHER && pathType != BlockPathTypes.WALKABLE_DOOR;
    }

    private boolean shouldTargetNextNodeInDirection(Vec3 vec) {
        if (path.getNextNodeIndex() + 1 >= path.getNodeCount()) {
            return false;
        }
        Vec3 vec3 = Vec3.atBottomCenterOf(path.getNextNodePos());
        if (!vec.closerThan(vec3, 2.0)) {
            return false;
        }
        if (canMoveDirectly(vec, path.getNextEntityPos(player))) {
            return true;
        }
        Vec3 vec32 = Vec3.atBottomCenterOf(path.getNodePos(path.getNextNodeIndex() + 1));
        Vec3 vec33 = vec32.subtract(vec3);
        return vec33.dot(vec.subtract(vec3)) > 0.0;
    }

    protected void doStuckDetection(Vec3 positionVec3) {
        if (tick - lastStuckCheck > 100) {
            if (positionVec3.distanceToSqr(lastStuckCheckPos) < 2.25) {
                isStuck = true;
                stop();
            } else {
                isStuck = false;
            }
            lastStuckCheck = tick;
            lastStuckCheckPos = positionVec3;
        }
        if (path != null && !path.isDone()) {
            BlockPos vec3i = path.getNextNodePos();
            if (vec3i.equals(timeoutCachedNode)) {
                timeoutTimer += Util.getMillis() - lastTimeoutCheck;
            } else {
                timeoutCachedNode = vec3i;
                double d = positionVec3.distanceTo(Vec3.atBottomCenterOf(timeoutCachedNode));
                timeoutLimit = player.getSpeed() > 0.0f ? d / (double) player.getSpeed() * 1000.0 : 0.0;
            }
            if (timeoutLimit > 0.0 && (double) timeoutTimer > timeoutLimit * 3.0) {
                timeoutPath();
            }
            lastTimeoutCheck = Util.getMillis();
        }
    }

    private void timeoutPath() {
        resetStuckTimeout();
        stop();
    }

    private void resetStuckTimeout() {
        timeoutCachedNode = Vec3i.ZERO;
        timeoutTimer = 0L;
        timeoutLimit = 0.0;
        isStuck = false;
    }

    /**
     * If null path or reached the end
     */
    public boolean isDone() {
        return path == null || path.isDone();
    }

    public boolean isInProgress() {
        return !isDone();
    }

    /**
     * sets active PathEntity to null
     */
    public void stop() {
        path = null;
    }

    protected Vec3 getTempMobPos() {
        return new Vec3(player.getX(), getSurfaceY(), player.getZ());
    }

    private int getSurfaceY() {
        if (!player.isInWater() || !canFloat()) {
            return Mth.floor(player.getY() + 0.5);
        }
        int i = player.getBlockY();
        BlockState blockState = level.getBlockState(BlockPos.containing(player.getX(), i, player.getZ()));
        int j = 0;
        while (blockState.is(Blocks.WATER)) {
            blockState = level.getBlockState(BlockPos.containing(player.getX(), ++i, player.getZ()));
            if (++j <= 16) continue;
            return player.getBlockY();
        }
        return i;
    }

    /**
     * If on ground or swimming and can swim
     */
    protected boolean canUpdatePath() {
        return player.onGround() || player.getAbilities().flying || isInLiquid() || player.isPassenger();
    }

    /**
     * Returns true if the entity is in water or lava, false otherwise
     */
    protected boolean isInLiquid() {
        return player.isInWaterOrBubble() || player.isInLava();
    }

    /**
     * Trims path data from the end to the first sun covered block
     */
    protected void trimPath() {
        if (path == null) {
            return;
        }
        for (int i = 0; i < path.getNodeCount(); ++i) {
            Node node = path.getNode(i);
            Node node2 = i + 1 < path.getNodeCount() ? path.getNode(i + 1) : null;
            BlockState blockState = level.getBlockState(new BlockPos(node.x, node.y, node.z));
            if (!blockState.is(BlockTags.CAULDRONS)) continue;
            path.replaceNode(i, node.cloneAndMove(node.x, node.y + 1, node.z));
            if (node2 == null || node.y < node2.y) continue;
            path.replaceNode(i + 1, node.cloneAndMove(node2.x, node.y + 1, node2.z));
        }
    }

    /**
     * Checks if the specified entity can safely walk to the specified location.
     */
    protected boolean canMoveDirectly(Vec3 posVec31, Vec3 posVec32) {
        return false;
    }

    public boolean canFloat() {
        return nodeEvaluator.canFloat();
    }

    public boolean isStuck() {
        return this.isStuck;
    }
}
