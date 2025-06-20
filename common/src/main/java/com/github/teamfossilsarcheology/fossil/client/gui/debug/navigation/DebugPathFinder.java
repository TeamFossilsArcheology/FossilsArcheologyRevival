package com.github.teamfossilsarcheology.fossil.client.gui.debug.navigation;

import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.PathNavigationRegion;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class DebugPathFinder extends PlayerPathFinder {
    private final boolean useCentered;
    public DebugPathFinder(PlayerNodeEvaluator nodeEvaluator, int maxVisitedNodes, boolean useCentered) {
        super(nodeEvaluator, maxVisitedNodes);
        this.useCentered = useCentered;
    }

    @Override
    public @Nullable PlayerPath findPath(PathNavigationRegion region, Player player, Set<BlockPos> targetPositions, float maxRange, int accuracy, float searchDepthMultiplier) {
        PlayerPath path = super.findPath(region, player, targetPositions, maxRange, accuracy, searchDepthMultiplier);
        return path == null ? null : (useCentered ? DebugCenteredPath.createFromPath(path) : DebugLargePath.createFromPath(path));
    }
}
