package com.github.teamfossilsarcheology.fossil.client.gui.filters;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import dev.architectury.hooks.client.screen.ScreenAccess;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TextComponent;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class FilterTab {
    private static final ResourceLocation FILTER_TEXTURE = FossilMod.location("textures/gui/filters.png");

    private final List<FilterButton> buttons = new ArrayList<>();

    public FilterTab(Screen screen, List<Filter> filters) {
        int leftPos = (screen.width - 195) / 2;
        int rightPos = leftPos + 191;
        int topPos = (screen.height - 136) / 2;
        int x = leftPos - 28;
        int y = topPos + 6;
        for (int i = 0; i < filters.size(); i++) {
            buttons.add(new FilterButton(screen, x, y, i <= 3, filters.get(i), button -> enableButton((FilterButton) button)));
            y += 30;
            if (i == 3) {
                x = rightPos;
                y = topPos + 6;
            }
        }
    }

    public static FilterTab build(Screen screen, List<Filter> filters, ScreenAccess access) {
        FilterTab tab = new FilterTab(screen, filters);
        tab.buttons.forEach(access::addWidget);
        return tab;
    }

    public List<Item> getItems() {
        List<Item> list = new ArrayList<>();
        var enabledButton = buttons.stream().filter(button -> button.filter.enabled).findFirst();
        if (enabledButton.isPresent()) {
            var optional = Registry.ITEM.getTag(enabledButton.get().filter.tag);
            if (optional.isPresent()) {
                list = optional.get().stream().map(Holder::value).toList();
            }
        }
        return list;
    }

    public Optional<TagKey<Item>> getTag() {
        var enabledButton = buttons.stream().filter(button -> button.filter.enabled).findFirst();
        return enabledButton.map(filterButton -> filterButton.filter.tag);
    }

    public void renderButtons(PoseStack poseStack, int mouseX, int mouseY, float partialTicks) {
        buttons.forEach(button -> button.render(poseStack, mouseX, mouseY, partialTicks));
    }

    private void enableButton(FilterButton button) {
        if (button.filter.enabled) {
            button.filter.enabled = false;
        } else {
            for (FilterButton filterButton : buttons) {
                filterButton.filter.enabled = false;
            }
            button.filter.enabled = true;
        }
    }

    public void enableButtons() {
        buttons.forEach(FilterButton::setActive);
    }

    public void disableButtons() {
        buttons.forEach(FilterButton::setInActive);
    }

    public static class FilterButton extends Button {
        private final Screen screen;
        private final Filter filter;
        private final boolean left;

        public FilterButton(Screen screen, int i, int j, boolean left, Filter filter, OnPress onPress) {
            super(i, j, 32, 28, TextComponent.EMPTY, onPress);
            this.screen = screen;
            this.left = left;
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
            RenderSystem.setShaderTexture(0, FILTER_TEXTURE);
            ItemRenderer itemRenderer = Minecraft.getInstance().getItemRenderer();
            float j = left ? 0 : 64;
            j = filter.enabled ? j + 32 : j;
            GuiComponent.blit(poseStack, x, y, 0, j, 0, 32, 28, 128, 128);
            itemRenderer.blitOffset = 100;
            itemRenderer.renderAndDecorateItem(filter.icon, x + 8, y + 6);
            itemRenderer.blitOffset = 0;
            if (mouseX > x && mouseY > y && mouseX < x + 32 && mouseY < y + 28) {
                screen.renderTooltip(poseStack, filter.tooltip, mouseX, mouseY);
            }
        }
    }

    public static class Filter {
        public final TagKey<Item> tag;
        private final ItemStack icon;
        private final Component tooltip;
        private boolean enabled;

        public Filter(TagKey<Item> tag, ItemStack icon) {
            this.tag = tag;
            this.icon = icon;
            this.tooltip = new TranslatableComponent("filter.fossil." + tag.location().getPath());
        }
    }
}
