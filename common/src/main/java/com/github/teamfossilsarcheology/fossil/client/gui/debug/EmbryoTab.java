package com.github.teamfossilsarcheology.fossil.client.gui.debug;

import com.github.teamfossilsarcheology.fossil.capabilities.ModCapabilities;
import com.github.teamfossilsarcheology.fossil.config.FossilConfig;
import com.github.teamfossilsarcheology.fossil.network.MessageHandler;
import com.github.teamfossilsarcheology.fossil.network.debug.SyncDebugInfoMessage;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.animal.Animal;

public class EmbryoTab extends DebugTab<Animal> {
    private int hatchingTime;

    protected EmbryoTab(DebugScreen debugScreen, Animal entity) {
        super(debugScreen, entity);
        hatchingTime = ModCapabilities.getEmbryoProgress(entity);
    }

    @Override
    protected void init(int width, int height) {
        super.init(width, height);
        int yPos = 0;

        DebugSlider hatchingTimeSlider = new DebugSlider(20, yPos += 30, 150, 20, Component.literal("Embryo time in ticks: "), Component.literal(""), 1, FossilConfig.getInt(FossilConfig.PREGNANCY_DURATION), hatchingTime, 5, 0, true) {
            @Override
            protected void applyValue() {
                hatchingTime = (int) (stepSize * Math.round(Mth.lerp(value, minValue, maxValue) / stepSize));
            }
        };
        addWidget(hatchingTimeSlider);
        addWidget(Button.builder(Component.literal("Set Info"), button -> {
                    MessageHandler.DEBUG_CHANNEL.sendToServer(new SyncDebugInfoMessage(entity.getId(), "", hatchingTime, 0, 0, 0, 0, 0));
                })
                .bounds(20, yPos += 30, 150, 20)
                .tooltip(Tooltip.create(Component.literal("Set the info above on the server")))
                .build());
    }
}
