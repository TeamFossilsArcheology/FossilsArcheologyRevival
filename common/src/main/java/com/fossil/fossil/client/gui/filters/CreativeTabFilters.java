package com.fossil.fossil.client.gui.filters;

import com.fossil.fossil.block.ModBlocks;
import com.fossil.fossil.item.ModItems;
import com.fossil.fossil.item.ModTabs;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientGuiEvent;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.core.NonNullList;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static com.fossil.fossil.client.gui.filters.FilterTab.Filter;

public class CreativeTabFilters {
    private static final Map<Integer, FilterTab> tabs = new HashMap<>();
    private static int activeTab = -1;

    public static void register() {
        NonNullList<Filter> filters = NonNullList.create();
        filters.add(new Filter("filter_plants", new ItemStack(ModItems.PlANT_FOSSIL.get())));
        filters.add(new Filter("filter_archaeology", new ItemStack(ModBlocks.WORKTABLE.get())));
        filters.add(new Filter("filter_skeletons", new ItemStack(ModBlocks.FOSSIL.get())));
        filters.add(new Filter("filter_dinos", new ItemStack(ModItems.THERIZINOSAURUS_SPAWN_EGG.get())));
        ClientGuiEvent.RENDER_CONTAINER_BACKGROUND.register((screen, matrices, mouseX, mouseY, delta) -> {
            if (screen instanceof CreativeModeInventoryScreen creativeScreen && tabs.containsKey(creativeScreen.getSelectedTab())) {
                tabs.get(creativeScreen.getSelectedTab()).renderButtons(matrices, mouseX, mouseY, delta);
            }
        });
        ClientGuiEvent.RENDER_PRE.register((screen, matrices, mouseX, mouseY, delta) -> {
            if (screen instanceof CreativeModeInventoryScreen creativeScreen && tabs.containsKey(creativeScreen.getSelectedTab())) {
                FilterTab filterTab = tabs.get(creativeScreen.getSelectedTab());
                boolean first = activeTab == -1;
                boolean switchedTab = activeTab != creativeScreen.getSelectedTab();
                creativeScreen.getMenu().items.clear();
                if (first) {
                    filterTab.enableButtons();
                } else if (switchedTab) {
                    tabs.get(activeTab).disableButtons();
                    filterTab.enableButtons();
                }
                NonNullList<ItemStack> stacks = NonNullList.create();
                CreativeModeTab.TABS[creativeScreen.getSelectedTab()].fillItemList(stacks);
                activeTab = creativeScreen.getSelectedTab();
                Optional<TagKey<Item>> selectedTag = tabs.get(creativeScreen.getSelectedTab()).getTag();
                selectedTag.ifPresent(tag -> stacks.removeIf(stack -> !stack.is(tag)));
                creativeScreen.getMenu().items.addAll(stacks);
                //List<Item> list = tabs.get(creativeScreen.getSelectedTab()).getItems();
                //creativeScreen.getMenu().items.addAll(stacks.stream().filter(stack -> list.contains(stack.getItem())).toList());
                if (creativeScreen.getMenu().items.isEmpty()) {
                    CreativeModeTab.TABS[creativeScreen.getSelectedTab()].fillItemList(creativeScreen.getMenu().items);
                }
                creativeScreen.getMenu().scrollTo(switchedTab ? 0 : creativeScreen.scrollOffs);
            }
            return EventResult.pass();
        });
        ClientGuiEvent.INIT_POST.register((screen, access) -> {
            if (screen instanceof CreativeModeInventoryScreen) {
                int leftPos = (screen.width - 195) / 2;
                int topPos = (screen.height - 136) / 2;
                tabs.put(ModTabs.FABLOCKTAB.getId(), FilterTab.build(leftPos - 28, topPos + 6, filters, access));
                tabs.put(ModTabs.FAITEMTAB.getId(), FilterTab.build(leftPos - 28, topPos + 6, filters, access));
                activeTab = -1;
            }
        });
    }
}