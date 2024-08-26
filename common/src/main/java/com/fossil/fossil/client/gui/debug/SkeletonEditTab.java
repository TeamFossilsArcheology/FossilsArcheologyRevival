package com.fossil.fossil.client.gui.debug;

import com.fossil.fossil.entity.PrehistoricSkeleton;
import com.fossil.fossil.entity.data.EntityDataManager;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.fossil.fossil.network.MessageHandler;
import com.fossil.fossil.network.debug.C2SSyncDebugInfoMessage;
import com.fossil.fossil.util.TimePeriod;
import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.ContainerObjectSelectionList;
import net.minecraft.client.gui.components.events.GuiEventListener;
import net.minecraft.client.gui.narration.NarratableEntry;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.util.Mth;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class SkeletonEditTab extends DebugTab {
    private final int maxAge;
    private int age;
    private PrehistoricEntityInfo info;


    protected SkeletonEditTab(DebugScreen debugScreen, PrehistoricSkeleton entity) {
        super(debugScreen, entity);
        this.maxAge = entity.data().adultAgeDays();
        this.age = entity.getAge();
        this.info = entity.info();
    }

    @Override
    protected void init(int width, int height) {
        DebugSlider ageSlider;
        super.init(width, height);
        ageSlider = new DebugSlider(20, 30, 150, 20, new TextComponent("Age: "), new TextComponent(""), 0, maxAge, age, 1, 0, true) {
            @Override
            protected void applyValue() {
                age = (int) (stepSize * Math.round(Mth.lerp(value, minValue, maxValue) / stepSize));
            }
        };
        addWidget(ageSlider);
        addWidget(new AnimationsList());
        addWidget(new Button(20, 210, 150, 20, new TextComponent("Set Info"), button -> {
            MessageHandler.DEBUG_CHANNEL.sendToServer(new C2SSyncDebugInfoMessage(entity.getId(), info.name(), age, 0, 0, 0, 0, 0));
            ageSlider.maxValue = EntityDataManager.ENTITY_DATA.getData(info.resourceName).adultAgeDays();
            age = (int) Math.min(age, ageSlider.maxValue);
            ageSlider.setValue(age);
        }));
    }

    @Override
    protected void render(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
        super.render(poseStack, mouseX, mouseY, partialTick);
        if (entity instanceof Prehistoric prehistoric) {
            drawString(poseStack, minecraft.font, new TextComponent("Age: " + prehistoric.getAge()), 175, 35, 16777215);
            drawString(poseStack, minecraft.font, new TextComponent("Type: " + prehistoric.info().name()), 175, 185, 16777215);
        }
    }

    private class AnimationsList extends ContainerObjectSelectionList<AnimationsList.AnimationEntry> {

        public AnimationsList() {
            super(SkeletonEditTab.this.minecraft, 200, SkeletonEditTab.this.height, 60, SkeletonEditTab.this.height, 25);
            List<String> sortedAnimations = PrehistoricEntityInfo.entitiesWithSkeleton(TimePeriod.values()).stream().map(Enum::name).toList();
            for (String typeName : sortedAnimations) {
                addEntry(new AnimationsList.AnimationEntry(typeName));
            }
            setRenderBackground(false);
            setRenderTopAndBottom(false);
            x0 = (SkeletonEditTab.this.width - SkeletonEditTab.this.width / 4);
            x1 = x0 + width;
        }

        @Override
        protected int getScrollbarPosition() {
            return x0 + width - 6;
        }

        private class AnimationEntry extends ContainerObjectSelectionList.Entry<AnimationsList.AnimationEntry> {
            private final Button changeButton;

            AnimationEntry(String text) {
                changeButton = new Button(0, 0, 200, 20, new TextComponent(text), button -> {
                    SkeletonEditTab.this.info = PrehistoricEntityInfo.valueOf(button.getMessage().getContents());
                });
            }

            @Override
            public @NotNull List<? extends NarratableEntry> narratables() {
                return ImmutableList.of(changeButton);
            }

            @Override
            public void render(PoseStack poseStack, int index, int top, int left, int width, int height, int mouseX, int mouseY, boolean isMouseOver,
                               float partialTick) {
                changeButton.x = left;
                changeButton.y = top;
                changeButton.render(poseStack, mouseX, mouseY, partialTick);
            }

            @Override
            public @NotNull List<? extends GuiEventListener> children() {
                return ImmutableList.of(changeButton);
            }

            @Override
            public boolean mouseClicked(double mouseX, double mouseY, int button) {
                return changeButton.mouseClicked(mouseX, mouseY, button);
            }

            @Override
            public boolean mouseReleased(double mouseX, double mouseY, int button) {
                return changeButton.mouseReleased(mouseX, mouseY, button);
            }
        }
    }
}
