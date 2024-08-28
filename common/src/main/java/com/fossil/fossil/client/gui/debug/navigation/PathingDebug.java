package com.fossil.fossil.client.gui.debug.navigation;

import com.fossil.fossil.client.gui.debug.PathingScreen;
import com.google.common.collect.Maps;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.ClipContext;
import net.minecraft.world.level.pathfinder.BlockPathTypes;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;

import java.util.Map;

public class PathingDebug {
    public static PlayerPathNavigation pathNavigation1;
    public static DebugCenteredPathNavigation pathNavigation3;
    public static SweepPathNavigation pathNavigation4;
    public static WaterPathNavigation pathNavigation5;
    public static BlockPos pos1;
    public static BlockPos pos2;
    public static Vec3 pos3;
    public static boolean showHelpMenu;
    public static boolean addNodeToPathMode;


    private static final Map<BlockPathTypes, Float> pathfindingMalus = Maps.newEnumMap(BlockPathTypes.class);

    public static void addToPath(BlockPos pos) {
        if (pathNavigation1 != null && pathNavigation1.getPath() != null) {
            pathNavigation1.getPath().addForcedNode(pos);
        }
        if (pathNavigation3 != null && pathNavigation3.getPath() != null) {
            pathNavigation3.getPath().addForcedNode(pos);
        }
        if (pathNavigation4 != null && pathNavigation4.getPath() != null) {
            pathNavigation4.getPath().addForcedNode(pos);
        }
        if (pathNavigation5 != null && pathNavigation5.getPath() != null) {
            pathNavigation5.getPath().addForcedNode(pos);
        }
    }

    public static void removeFromPath(BlockPos pos) {
        if (pathNavigation1 != null && pathNavigation1.getPath() != null) {
            pathNavigation1.getPath().removeForcedNode(pos);
        }
        if (pathNavigation3 != null && pathNavigation3.getPath() != null) {
            pathNavigation3.getPath().removeForcedNode(pos);
        }
        if (pathNavigation4 != null && pathNavigation4.getPath() != null) {
            pathNavigation4.getPath().removeForcedNode(pos);
        }
        if (pathNavigation5 != null && pathNavigation5.getPath() != null) {
            pathNavigation5.getPath().removeForcedNode(pos);
        }
    }

    public static void rePath() {
        if (PathingScreen.currentNav != null && pos1 != null && pos2 != null) {
            PathingScreen.currentNav.stop();
            PathingScreen.currentNav.moveTo(pos2);
        }
    }

    public static void setPos1(BlockPos pos1) {
        PathingDebug.pos1 = pos1;
        if (pathNavigation1 != null && pos2 != null) {
            pathNavigation1.moveTo(pos2);
            pathNavigation3.moveTo(pos2);
            pathNavigation4.moveTo(pos2);
            pathNavigation5.moveTo(pos2);
        }
    }

    public static void setPos2(BlockPos pos2) {
        PathingDebug.pos2 = pos2;
        if (pathNavigation1 != null && pos1 != null) {
            pathNavigation1.moveTo(pos2);
            pathNavigation3.moveTo(pos2);
            pathNavigation4.moveTo(pos2);
            pathNavigation5.moveTo(pos2);
        }
    }

    public static void setPos3(Vec3 pos3) {
        PathingDebug.pos3 = pos3;
        if (pathNavigation4 != null) {
            pathNavigation4.setSweepStartPos(pos3);
        }
        if (pathNavigation5 != null) {
            pathNavigation5.setSweepStartPos(pos3);
        }
    }

    public static Vec3 getHitResult(Minecraft mc) {
        Entity camera = mc.getCameraEntity();
        Vec3 eye = camera.getEyePosition();
        Vec3 view = camera.getViewVector(1.0f);
        double range = 30;
        Vec3 end = eye.add(view.x * range, view.y * range, view.z * range);
        BlockHitResult hitResult = camera.level.clip(new ClipContext(eye, end, ClipContext.Block.OUTLINE, ClipContext.Fluid.ANY, camera));
        return hitResult.getLocation();
    }

    public static BlockPos getBlockHitResult(Minecraft mc) {
        Entity camera = mc.getCameraEntity();
        Vec3 eye = camera.getEyePosition();
        Vec3 view = camera.getViewVector(1.0f);
        double range = 30;
        Vec3 end = eye.add(view.x * range, view.y * range, view.z * range);
        BlockHitResult hitResult = camera.level.clip(new ClipContext(eye, end, ClipContext.Block.COLLIDER, ClipContext.Fluid.ANY, camera));
        return hitResult.getBlockPos().offset(hitResult.getDirection().getNormal());
    }

    public static float getPathfindingMalus(BlockPathTypes nodeType) {
        Float float_ = pathfindingMalus.get(nodeType);
        return float_ == null ? nodeType.getMalus() : float_;
    }

    public static void setPathfindingMalus(BlockPathTypes nodeType, float malus) {
        pathfindingMalus.put(nodeType, malus);
    }

    public static void tick() {
        if (pathNavigation1 != null) pathNavigation1.tick();
        if (pathNavigation3 != null) pathNavigation3.tick();
    }
}
