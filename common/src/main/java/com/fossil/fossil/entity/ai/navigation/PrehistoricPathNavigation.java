/*
MIT License

Copyright (c) 2021 GeckoThePecko

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */
package com.fossil.fossil.entity.ai.navigation;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.level.pathfinder.PathComputationType;
import net.minecraft.world.level.pathfinder.PathFinder;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

/*
Based off of navigation code in AzureLib:
https://github.com/AzureDoom/AzureLib/blob/1.18.2/Fabric/src/main/java/mod/azure/azurelib/ai/pathing/AzureNavigation.java
 */
public class PrehistoricPathNavigation extends GroundPathNavigation {
    @Nullable
    private BlockPos pathToPosition;
    public PrehistoricPathNavigation(Prehistoric prehistoric, Level level) {
        super(prehistoric, level);
    }

    @Override
    protected @NotNull PathFinder createPathFinder(int maxVisitedNodes) {
        nodeEvaluator = new PrehistoricNodeEvaluator();
        return new PrehistoricPathFinder(nodeEvaluator, maxVisitedNodes);
    }

    @Override
    protected void followThePath() {
        Path path = Objects.requireNonNull(this.path);
        Vec3 entityPos = getTempMobPos();
        int pathLength = path.getNodeCount();
        for (int i = path.getNextNodeIndex(); i < path.getNodeCount(); i++) {
            if (path.getNode(i).y != Math.floor(entityPos.y)) {
                pathLength = i;
                break;
            }
        }
        final Vec3 base = entityPos.add(-mob.getBbWidth() * 0.5F, 0, -mob.getBbWidth() * 0.5F);
        final Vec3 max = base.add(mob.getBbWidth(), mob.getBbHeight(), mob.getBbWidth());
        if (tryShortcut(path, new Vec3(mob.getX(), mob.getY(), mob.getZ()), pathLength, base, max)) {
            if (isAt(path, 0.5F) || atElevationChange(path) && isAt(path, mob.getBbWidth() * 0.5F)) {
                mob.getLookControl().setLookAt(path.getNextEntityPos(mob));
                path.setNextNodeIndex(path.getNextNodeIndex() + 1);
            }
        }
        doStuckDetection(entityPos);
    }

    @Override
    public Path createPath(BlockPos blockPos, int i) {
        pathToPosition = blockPos;
        return super.createPath(blockPos, i);
    }

    @Override
    public Path createPath(Entity entity, int i) {
        pathToPosition = entity.blockPosition();
        return super.createPath(entity, i);
    }

    @Override
    public boolean moveTo(Entity entity, double d) {
        Path path = createPath(entity, 0);
        if (path != null) {
            return moveTo(path, d);
        }
        pathToPosition = entity.blockPosition();
        speedModifier = d;
        return true;
    }

    @Override
    public void tick() {
        super.tick();
        if (isDone()) {
            if (pathToPosition != null) {
                if (pathToPosition.closerToCenterThan(mob.position(), mob.getBbWidth()) || mob.getY() > (double)pathToPosition.getY() && new BlockPos(pathToPosition.getX(), mob.getY(), pathToPosition.getZ()).closerToCenterThan(mob.position(), mob.getBbWidth())) {
                    pathToPosition = null;
                } else {
                    mob.getMoveControl().setWantedPosition(pathToPosition.getX(), pathToPosition.getY(), pathToPosition.getZ(), speedModifier);
                }
            }
            return;
        }
        if (getTargetPos() != null)
            mob.getLookControl().setLookAt(getTargetPos().getX(), getTargetPos().getY(), getTargetPos().getZ());
    }

    private boolean isAt(Path path, float threshold) {
        final Vec3 pathPos = path.getNextEntityPos(mob);
        return Mth.abs((float) (mob.getX() - pathPos.x)) < threshold
                && Mth.abs((float) (mob.getZ() - pathPos.z)) < threshold
                && Math.abs(mob.getY() - pathPos.y) < 1.0D;
    }

    private boolean atElevationChange(Path path) {
        final int curr = path.getNextNodeIndex();
        final int end = Math.min(path.getNodeCount(), curr + Mth.ceil(mob.getBbWidth() * 0.5F) + 1);
        final int currY = path.getNode(curr).y;
        for (int i = curr + 1; i < end; i++) {
            if (path.getNode(i).y != currY) {
                return true;
            }
        }
        return false;
    }

    private boolean tryShortcut(Path path, Vec3 entityPos, int pathLength, Vec3 base, Vec3 max) {
        for (int i = pathLength; --i > path.getNextNodeIndex();) {
            final Vec3 vec = path.getEntityPosAtNode(mob, i).subtract(entityPos);
            if (sweep(vec, base, max)) {
                path.setNextNodeIndex(i);
                return false;
            }
        }
        return true;
    }

    static final float EPSILON = 1.0E-8F;

    // Based off of
    // https://github.com/andyhall/voxel-aabb-sweep/blob/d3ef85b19c10e4c9d2395c186f9661b052c50dc7/index.js
    private boolean sweep(Vec3 vec, Vec3 base, Vec3 max) {
        float t = 0;
        float max_t = (float) vec.length();
        if (max_t < EPSILON)
            return true;
        final float[] tr = new float[3];
        final int[] ldi = new int[3];
        final int[] tri = new int[3];
        final int[] step = new int[3];
        final float[] tDelta = new float[3];
        final float[] tNext = new float[3];
        final float[] normed = new float[3];
        for (int i = 0; i < 3; i++) {
            float value = element(vec, i);
            boolean dir = value >= 0;
            step[i] = dir ? 1 : -1;
            float lead = element(dir ? max : base, i);
            tr[i] = element(dir ? base : max, i);
            ldi[i] = leadEdgeToInt(lead, step[i]);
            tri[i] = trailEdgeToInt(tr[i], step[i]);
            normed[i] = value / max_t;
            tDelta[i] = Mth.abs(max_t / value);
            float dist = dir ? (ldi[i] + 1 - lead) : (lead - ldi[i]);
            tNext[i] = tDelta[i] < Float.POSITIVE_INFINITY ? tDelta[i] * dist : Float.POSITIVE_INFINITY;
        }
        final BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();
        do {
            // stepForward
            int axis = (tNext[0] < tNext[1]) ? ((tNext[0] < tNext[2]) ? 0 : 2) : ((tNext[1] < tNext[2]) ? 1 : 2);
            float dt = tNext[axis] - t;
            t = tNext[axis];
            ldi[axis] += step[axis];
            tNext[axis] += tDelta[axis];
            for (int i = 0; i < 3; i++) {
                tr[i] += dt * normed[i];
                tri[i] = trailEdgeToInt(tr[i], step[i]);
            }
            // checkCollision
            int stepx = step[0];
            int x0 = (axis == 0) ? ldi[0] : tri[0];
            int x1 = ldi[0] + stepx;
            int stepy = step[1];
            int y0 = (axis == 1) ? ldi[1] : tri[1];
            int y1 = ldi[1] + stepy;
            int stepz = step[2];
            int z0 = (axis == 2) ? ldi[2] : tri[2];
            int z1 = ldi[2] + stepz;
            for (int x = x0; x != x1; x += stepx) {
                for (int z = z0; z != z1; z += stepz) {
                    for (int y = y0; y != y1; y += stepy) {
                        BlockState block = level.getBlockState(pos.set(x, y, z));
                        if (!block.isPathfindable(level, pos, PathComputationType.LAND))
                            return false;
                    }
                    BlockPathTypes below = nodeEvaluator.getBlockPathType(level, x, y0 - 1, z, mob, 1, 1,
                            1, true, true);
                    if (below == BlockPathTypes.WATER || below == BlockPathTypes.LAVA || below == BlockPathTypes.OPEN)
                        return false;
                    BlockPathTypes in = nodeEvaluator.getBlockPathType(level, x, y0, z, mob, 1, y1 - y0,
                            1, true, true);
                    float priority = mob.getPathfindingMalus(in);
                    if (priority < 0 || priority >= 8)
                        return false;
                    if (in == BlockPathTypes.DAMAGE_FIRE || in == BlockPathTypes.DANGER_FIRE
                            || in == BlockPathTypes.DAMAGE_OTHER)
                        return false;
                }
            }
        } while (t <= max_t);
        return true;
    }

    static int leadEdgeToInt(float coord, int step) {
        return Mth.floor(coord - step * EPSILON);
    }

    static int trailEdgeToInt(float coord, int step) {
        return Mth.floor(coord + step * EPSILON);
    }

    static float element(Vec3 v, int i) {
        return switch (i) {
            case 0 -> (float) v.x;
            case 1 -> (float) v.y;
            case 2 -> (float) v.z;
            default -> 0;
        };
    }
}
