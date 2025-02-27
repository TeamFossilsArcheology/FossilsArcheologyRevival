package com.github.teamfossilsarcheology.fossil.client.gui.debug;

import com.github.teamfossilsarcheology.fossil.client.gui.debug.navigation.PathingDebug;
import com.github.teamfossilsarcheology.fossil.client.gui.debug.navigation.PlayerPathNavigation;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

import java.util.List;

public class PathingScreen extends Screen {
    public static double bbWidth = 1;
    public static double bbHeight = 2;
    public static int tick = 0;
    public static int baseTick = 0;
    public static DebugSlider tickSlider;

    public static int corner = 17;//TODO: Higher for less vertical diagonals
    public static int edge = 14;
    public static int face = 10;
    public static PlayerPathNavigation currentNav = PathingDebug.pathNavigation1;

    public PathingScreen() {
        super(Component.literal("Pathing Screen"));
    }

    @Override
    protected void init() {
        addRenderableWidget(new DebugSlider(470, height - 70, 150, 20, Component.literal("Width: "), Component.literal(""), 0.1, 5, bbWidth, 0.01, 3, true) {
            @Override
            protected void applyValue() {
                bbWidth = (stepSize * Math.round(Mth.lerp(value, minValue, maxValue) / stepSize));
            }
        });
        addRenderableWidget(new DebugSlider(620, height - 70, 150, 20, Component.literal("Height: "), Component.literal(""), 0.1, 5, bbHeight, 0.01, 3, true) {
            @Override
            protected void applyValue() {
                bbHeight = (stepSize * Math.round(Mth.lerp(value, minValue, maxValue) / stepSize));
            }
        });
        tickSlider = new DebugSlider(470, height - 50, 300, 20, Component.literal("Tick: "), Component.literal(""), 0, baseTick, tick, 1, 2, true) {
            @Override
            protected void applyValue() {
                tick = (int) (stepSize * Math.round(Mth.lerp(value, minValue, maxValue) / stepSize));
            }
        };
        addRenderableWidget(tickSlider);
        addRenderableWidget(new DebugSlider(100, height - 70, 100, 20, Component.literal("Corner/Triple: "), Component.literal(""), 0, 25, corner, 1, 2, true) {
            @Override
            protected void applyValue() {
                corner = (int) (stepSize * Math.round(Mth.lerp(value, minValue, maxValue) / stepSize));
                PathingDebug.rePath();
            }
        });
        addRenderableWidget(new DebugSlider(200, height - 70, 100, 20, Component.literal("Edge/Double: "), Component.literal(""), 0, 25, edge, 1, 2, true) {
            @Override
            protected void applyValue() {
                edge = (int) (stepSize * Math.round(Mth.lerp(value, minValue, maxValue) / stepSize));
                PathingDebug.rePath();
            }
        });
        addRenderableWidget(new DebugSlider(300, height - 70, 100, 20, Component.literal("Face/Single: "), Component.literal(""), 0, 25, face, 1, 2, true) {
            @Override
            protected void applyValue() {
                face = (int) (stepSize * Math.round(Mth.lerp(value, minValue, maxValue) / stepSize));
                PathingDebug.rePath();
            }
        });
        addRenderableWidget(Button.builder(Component.literal("Reset scroll"), button -> PathingDebug.pickBlockOffset = 0)
                .bounds(200, height - 50, 100, 20).build());
        addRenderableWidget(Button.builder(Component.literal("Reset pos3"), button -> PathingDebug.pos3 = null)
                .bounds(300, height - 50, 100, 20).build());
        List<PlayerPathNavigation> paths = List.of(PathingDebug.pathNavigation1, PathingDebug.pathNavigation3, PathingDebug.pathNavigation4, PathingDebug.pathNavigation5);
        addRenderableWidget(CycleButton.<PlayerPathNavigation>builder(nav -> Component.literal(nav.name)).withValues(paths)
                .withInitialValue(currentNav).create(30, 90, 100, 20, Component.literal("Path"),
                        (cycleButton, newNav) -> currentNav = newNav));
    }
}
