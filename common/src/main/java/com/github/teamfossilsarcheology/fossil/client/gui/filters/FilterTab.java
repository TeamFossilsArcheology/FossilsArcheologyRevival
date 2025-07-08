package com.github.teamfossilsarcheology.fossil.client.gui.filters;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import dev.architectury.hooks.client.screen.ScreenAccess;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
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
            var optional = BuiltInRegistries.ITEM.getTag(enabledButton.get().filter.tag);
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

    public void renderButtons(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        buttons.forEach(button -> button.render(guiGraphics, mouseX, mouseY, partialTicks));
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
            super(i, j, 32, 28, Component.empty(), onPress, DEFAULT_NARRATION);
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
        public void renderWidget(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTick) {
            float j = left ? 0 : 64;
            j = filter.enabled ? j + 32 : j;
            guiGraphics.blit(FILTER_TEXTURE, getX(), getY(), 0, j, 0, 32, 28, 128, 128);
            guiGraphics.renderItem(filter.icon, getX() + 8, getY() + 6);
            if (mouseX > getX() && mouseY > getY() && mouseX < getX() + 32 && mouseY < getY() + 28) {
                guiGraphics.renderTooltip(Minecraft.getInstance().font, filter.tooltip, mouseX, mouseY);
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
            this.tooltip = Component.translatable("filter.fossil." + tag.location().getPath());
        }
    }
}
