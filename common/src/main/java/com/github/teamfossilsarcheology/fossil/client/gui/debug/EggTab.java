package com.github.teamfossilsarcheology.fossil.client.gui.debug;

import com.github.teamfossilsarcheology.fossil.entity.data.EntityDataLoader;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.DinosaurEgg;
import com.github.teamfossilsarcheology.fossil.network.MessageHandler;
import com.github.teamfossilsarcheology.fossil.network.debug.SyncDebugInfoMessage;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.Tooltip;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;

public class EggTab extends DebugTab<DinosaurEgg> {
    private int hatchingTime;
    private double scaleOverride;

    protected EggTab(DebugScreen debugScreen, DinosaurEgg entity) {
        super(debugScreen, entity);
        this.hatchingTime = entity.getHatchingTime();
    }

    @Override
    protected void init(int width, int height) {
        super.init(width, height);
        int yPos = 0;
        EntityDataLoader.Data data = EntityDataLoader.INSTANCE.getData(entity.getPrehistoricEntityInfo().resourceName);

        DebugSlider hatchingTimeSlider = new DebugSlider(20, yPos += 30, 150, 20, Component.literal("Hatching time in ticks: "), Component.literal(""), 0, entity.getTotalHatchingTime(), entity.getHatchingTime(), 5, 0, true) {
            @Override
            protected void applyValue() {
                hatchingTime = (int) (stepSize * Math.round(Mth.lerp(value, minValue, maxValue) / stepSize));
            }
        };
        DebugSlider scaleSlider = new DebugSlider(20, yPos += 30, 150, 20, Component.literal("Scale: "), Component.literal(""), 0.05, Math.max(2, data.eggScale()), data.eggScale(), 0.05, 2, true) {
            @Override
            protected void applyValue() {
                scaleOverride = (stepSize * Math.round(Mth.lerp(value, minValue, maxValue) / stepSize));
                entity.setScaleOverride((float) scaleOverride);
            }

            @Override
            public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
                super.render(guiGraphics, mouseX, mouseY, partialTick);
                int j = active ? 16777215 : 10526880;
                String currentEggScale = String.valueOf(data.eggScale());
                int currentScaleX = (int) (((data.eggScale() - minValue) / (maxValue - minValue)) * (width - minecraft.font.width(currentEggScale)));
                guiGraphics.drawString(minecraft.font, currentEggScale, getX() + currentScaleX, getY() - 8, j | Mth.ceil(alpha * 255.0F) << 24);
            }
        };

        addWidget(hatchingTimeSlider);
        addWidget(scaleSlider);
        addWidget(Button.builder(Component.literal("Set Info"), button -> {
                    entity.setScaleOverride(-1);
                    MessageHandler.DEBUG_CHANNEL.sendToServer(new SyncDebugInfoMessage(entity.getId(), "", hatchingTime, 0, 0, 0, 0, 0));
                })
                .bounds(20, yPos += 30, 150, 20)
                .tooltip(Tooltip.create(Component.literal("Set the info above on the server")))
                .build());
    }
}
