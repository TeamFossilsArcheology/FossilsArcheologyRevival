package com.github.teamfossilsarcheology.fossil.client.gui.debug.navigation;

import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;

public class DebugCenteredPathNavigation extends PlayerPathNavigation {

    public DebugCenteredPathNavigation(Player p, Level l) {
        super(p, l, "Centered");
    }

    @Override
    protected PlayerPathFinder createPathFinder(int maxVisitedNodes) {
        nodeEvaluator = new DebugCenteredNodeEvaluator();
        return new DebugPathFinder(nodeEvaluator, maxVisitedNodes, true);
    }
}
