package com.fossil.fossil.client.gui.filters;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.item.ModItems;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.architectury.hooks.client.screen.ScreenAccess;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.renderer.GameRenderer;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

public class FilterTab {
    private static final ResourceLocation FILTER_TEXTURE = new ResourceLocation(Fossil.MOD_ID, "textures/gui/filters.png");

    private final List<FilterButton> buttons = new ArrayList<>();
    private int enabledButton = -1;

    public FilterTab(int i, int j, List<Filter> filters) {
        for (Filter filter : filters) {
            buttons.add(new FilterButton(i, j, filter, button -> enableButton((FilterButton) button)));
            j += 30;
        }
    }

    public List<Holder<Item>> getItems() {
        List<Holder<Item>> list = new ArrayList<>();
        if (enabledButton != -1) {
            var optional = Registry.ITEM.getTag(buttons.get(enabledButton).filter.tag);
            if (optional.isPresent()) {
                list = optional.get().stream().toList();
            }
        }
        return list;
    }

    public void renderButtons(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        buttons.forEach(button -> button.render(poseStack, mouseX, mouseY, partialTicks));
    }

    private void enableButton(FilterButton button) {
        int newIndex = buttons.indexOf(button);
        if (button.filter.enabled) {
            newIndex = -1;
        } else if (enabledButton != -1) {
            buttons.get(enabledButton).filter.enabled = false;
        }
        enabledButton = newIndex;
        button.filter.enabled = !button.filter.enabled;
    }

    public void enableButtons() {
        buttons.forEach(FilterButton::setActive);
        buttons.stream().filter(button -> button.filter.enabled).map(buttons::indexOf).findFirst().ifPresent(idx -> enabledButton = idx);
    }

    public void disableButtons() {
        buttons.forEach(FilterButton::setInActive);
    }

    public static FilterTab build(int i, int j, List<Filter> filters, ScreenAccess access) {
        FilterTab tab = new FilterTab(i, j, filters);
        tab.buttons.forEach(access::addWidget);
        return tab;
    }

    public static class FilterButton extends Button {
        private final Filter filter;

        public FilterButton(int i, int j, Filter filter, OnPress onPress) {
            super(i, j, 32, 28, TextComponent.EMPTY, onPress);
            this.filter = filter;
            this.active = false;
        }

        protected void setInActive() {
            this.active = false;
        }

        protected void setActive() {
            this.active = true;
        }

        @Override
        public void renderButton(PoseStack poseStack, int mouseX, int mouseY, float partialTick) {
            RenderSystem.setShader(GameRenderer::getPositionTexShader);
            RenderSystem.setShaderTexture(0, FILTER_TEXTURE);
            if (filter.enabled) {
                GuiComponent.blit(poseStack, x, y, 0, 32, 0, 32, 28, 128, 128);
            } else {
                GuiComponent.blit(poseStack, x, y, 0, 0, 0, 28, 28, 128, 128);
            }
            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
            itemRenderer.blitOffset = 100.0f;
            itemRenderer.renderAndDecorateItem(filter.icon, x + 8, y + 6);
            itemRenderer.blitOffset = 0.0f;
        }
    }

    public static class Filter {
        private final TagKey<Item> tag;
        private final ItemStack icon;
        private boolean enabled;

        public Filter(String tagLocation, ItemStack icon) {
            this.tag = TagKey.create(ModItems.ITEMS.getRegistrar().key(), new ResourceLocation(Fossil.MOD_ID, tagLocation));
            this.icon = icon;
        }
    }
}
