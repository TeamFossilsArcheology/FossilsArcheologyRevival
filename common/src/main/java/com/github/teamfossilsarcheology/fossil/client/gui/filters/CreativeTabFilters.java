package com.github.teamfossilsarcheology.fossil.client.gui.filters;

import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import com.github.teamfossilsarcheology.fossil.block.PrehistoricPlantInfo;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.github.teamfossilsarcheology.fossil.item.ModItems;
import com.github.teamfossilsarcheology.fossil.item.ModTabs;
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

import static com.github.teamfossilsarcheology.fossil.client.gui.filters.FilterTab.Filter;
import static com.github.teamfossilsarcheology.fossil.tags.ModItemTags.*;

public class CreativeTabFilters {
    private static final Map<Integer, FilterTab> tabs = new HashMap<>();
    private static int activeTab = -1;

    public static void register() {
        NonNullList<Filter> filters = NonNullList.create();
        filters.add(new Filter(FILTER_BONES, new ItemStack(PrehistoricEntityInfo.TRICERATOPS.skullBoneItem)));
        filters.add(new Filter(FILTER_DNA, new ItemStack(PrehistoricEntityInfo.TRICERATOPS.dnaItem)));
        filters.add(new Filter(FILTER_EGGS, new ItemStack(PrehistoricEntityInfo.TRICERATOPS.spawnEggItem)));
        filters.add(new Filter(FILTER_MEAT, new ItemStack(PrehistoricEntityInfo.TRICERATOPS.foodItem)));
        filters.add(new Filter(FILTER_PLANTS, new ItemStack(ModItems.FERN_SEED.get())));

        NonNullList<Filter> blocks = NonNullList.create();
        blocks.add(new Filter(FILTER_TREES, new ItemStack(ModBlocks.MUTANT_TREE_LOG.get())));
        blocks.add(new Filter(FILTER_VASES, new ItemStack(ModBlocks.AMPHORA_VASE_RESTORED.get())));
        blocks.add(new Filter(FIGURINES, new ItemStack(ModBlocks.ANU_FIGURINE_DESTROYED.get())));
        blocks.add(new Filter(FILTER_PLANTS, new ItemStack(PrehistoricPlantInfo.BENNETTITALES_LARGE.getPlantBlock())));
        blocks.add(new Filter(FILTER_UNBREAKABLE, new ItemStack(ModBlocks.REINFORCED_GLASS.get())));
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
                tabs.put(ModTabs.FAITEMTAB.getId(), FilterTab.build(screen, filters, access));
                tabs.put(ModTabs.FABLOCKTAB.getId(), FilterTab.build(screen, blocks, access));
                activeTab = -1;
            }
        });
    }
}