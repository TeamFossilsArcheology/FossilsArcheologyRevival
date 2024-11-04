package com.github.teamfossilsarcheology.fossil.client.gui.debug;

import com.github.teamfossilsarcheology.fossil.entity.data.EntityDataLoader;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.DinosaurEgg;
import com.github.teamfossilsarcheology.fossil.network.MessageHandler;
import com.github.teamfossilsarcheology.fossil.network.debug.SyncDebugInfoMessage;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.network.chat.TextComponent;
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

        DebugSlider hatchingTimeSlider = new DebugSlider(20, yPos += 30, 150, 20, new TextComponent("Hatching time in ticks: "), new TextComponent(""), 0, entity.getTotalHatchingTime(), entity.getHatchingTime(), 5, 0, true) {
            @Override
            protected void applyValue() {
                hatchingTime = (int) (stepSize * Math.round(Mth.lerp(value, minValue, maxValue) / stepSize));
            }
        };
        DebugSlider scaleSlider = new DebugSlider(20, yPos += 30, 150, 20, new TextComponent("Scale: "), new TextComponent(""), 0.05, Math.max(2, data.eggScale()), data.eggScale(), 0.05, 2, true) {
            @Override
            protected void applyValue() {
                scaleOverride = (stepSize * Math.round(Mth.lerp(value, minValue, maxValue) / stepSize));
                entity.setScaleOverride((float) scaleOverride);
            }

            @Override
            public void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
                super.render(poseStack, mouseX, mouseY, partialTick);
                int j = active ? 16777215 : 10526880;
                String currentEggScale = String.valueOf(data.eggScale());
                int currentScaleX = (int) (((data.eggScale() - minValue) / (maxValue - minValue)) * (width - minecraft.font.width(currentEggScale)));
                drawString(poseStack, minecraft.font, currentEggScale, x + currentScaleX, y - 8, j | Mth.ceil(alpha * 255.0F) << 24);
            }
        };

        addWidget(hatchingTimeSlider);
        addWidget(scaleSlider);
        addWidget(new Button(20, yPos += 30, 150, 20, new TextComponent("Set Info"), button -> {
            entity.setScaleOverride(-1);
            MessageHandler.DEBUG_CHANNEL.sendToServer(new SyncDebugInfoMessage(entity.getId(), "", hatchingTime, 0, 0, 0, 0, 0));
        }, (button, poseStack, i, j) -> {
            debugScreen.renderTooltip(poseStack, new TextComponent("Set the info above on the server"), i, j);
        }));
    }
}
