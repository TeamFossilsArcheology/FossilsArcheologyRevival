package com.fossil.fossil.client.gui.debug;

import com.fossil.fossil.client.gui.debug.navigation.PathingDebug;
import com.fossil.fossil.client.gui.debug.navigation.PlayerPathNavigation;
import net.minecraft.client.CycleOption;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.TextComponent;
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
        super(new TextComponent("Pathing Screen"));
    }

    @Override
    protected void init() {
        addRenderableWidget(new DebugSlider(470, height - 70, 150, 20, new TextComponent("Width: "), new TextComponent(""), 0.1, 5, bbWidth, 0.01, 3, true) {
            @Override
            protected void applyValue() {
                bbWidth = (stepSize * Math.round(Mth.lerp(value, minValue, maxValue) / stepSize));
            }
        });
        addRenderableWidget(new DebugSlider(620, height - 70, 150, 20, new TextComponent("Height: "), new TextComponent(""), 0.1, 5, bbHeight, 0.01, 3, true) {
            @Override
            protected void applyValue() {
                bbHeight = (stepSize * Math.round(Mth.lerp(value, minValue, maxValue) / stepSize));
            }
        });
        tickSlider = new DebugSlider(470, height - 50, 300, 20, new TextComponent("Tick: "), new TextComponent(""), 0, baseTick, tick, 1, 2, true) {
            @Override
            protected void applyValue() {
                tick = (int) (stepSize * Math.round(Mth.lerp(value, minValue, maxValue) / stepSize));
            }
        };
        addRenderableWidget(tickSlider);
        addRenderableWidget(new DebugSlider(100, height - 70, 100, 20, new TextComponent("Corner/Triple: "), new TextComponent(""), 0, 25, corner, 1, 2, true) {
            @Override
            protected void applyValue() {
                corner = (int) (stepSize * Math.round(Mth.lerp(value, minValue, maxValue) / stepSize));
                PathingDebug.rePath();
            }
        });
        addRenderableWidget(new DebugSlider(200, height - 70, 100, 20, new TextComponent("Edge/Double: "), new TextComponent(""), 0, 25, edge, 1, 2, true) {
            @Override
            protected void applyValue() {
                edge = (int) (stepSize * Math.round(Mth.lerp(value, minValue, maxValue) / stepSize));
                PathingDebug.rePath();
            }
        });
        addRenderableWidget(new DebugSlider(300, height - 70, 100, 20, new TextComponent("Face/Single: "), new TextComponent(""), 0, 25, face, 1, 2, true) {
            @Override
            protected void applyValue() {
                face = (int) (stepSize * Math.round(Mth.lerp(value, minValue, maxValue) / stepSize));
                PathingDebug.rePath();
            }
        });
        addRenderableWidget(new Button(200, height - 50, 100, 20, new TextComponent("Reset scroll"), button -> {
            PathingDebug.pickBlockOffset = 0;
        }));
        addRenderableWidget(new Button(300, height - 50, 100, 20, new TextComponent("Reset pos3"), button -> {
            PathingDebug.pos3 = null;
        }));
        List<PlayerPathNavigation> paths = List.of(PathingDebug.pathNavigation1, PathingDebug.pathNavigation3, PathingDebug.pathNavigation4, PathingDebug.pathNavigation5);
        addRenderableWidget(CycleOption.create("Path", () -> paths, nav -> new TextComponent(nav.name),
                        options -> currentNav, (options, option, controller) -> currentNav = controller)
                .createButton(Minecraft.getInstance().options, 30, 90, 100));
    }
}
