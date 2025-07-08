package com.github.teamfossilsarcheology.fossil.client.gui.filters;

import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import com.github.teamfossilsarcheology.fossil.block.PrehistoricPlantInfo;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.github.teamfossilsarcheology.fossil.item.ModItems;
import com.github.teamfossilsarcheology.fossil.item.ModTabs;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.client.ClientGuiEvent;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.client.gui.screens.inventory.CreativeModeInventoryScreen;
import net.minecraft.core.NonNullList;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

import java.util.Map;

import static com.github.teamfossilsarcheology.fossil.client.gui.filters.FilterTab.Filter;
import static com.github.teamfossilsarcheology.fossil.tags.ModItemTags.*;

public class CreativeTabFilters {
    private static final Map<CreativeModeTab, FilterTab> tabs = new Object2ObjectOpenHashMap<>();
    private static CreativeModeTab activeTab;

    public static void register() {
        NonNullList<Filter> entityItems = NonNullList.create();
        entityItems.add(new Filter(FILTER_BONES, new ItemStack(PrehistoricEntityInfo.TRICERATOPS.skullBoneItem)));
        entityItems.add(new Filter(FILTER_DNA, new ItemStack(PrehistoricEntityInfo.TRICERATOPS.dnaItem)));
        entityItems.add(new Filter(FILTER_EGGS, new ItemStack(PrehistoricEntityInfo.TRICERATOPS.spawnEggItem)));
        entityItems.add(new Filter(FILTER_MEAT, new ItemStack(PrehistoricEntityInfo.TRICERATOPS.foodItem)));
        entityItems.add(new Filter(FILTER_PLANTS, new ItemStack(ModItems.FERN_SEED.get())));
        entityItems.add(new Filter(FILTER_BUCKETS, new ItemStack(PrehistoricEntityInfo.NAUTILUS.bucketItem)));
        entityItems.add(new Filter(FILTER_OTHER, new ItemStack(ModItems.MAMMOTH_FUR.get())));

        NonNullList<Filter> blocks = NonNullList.create();
        blocks.add(new Filter(FILTER_TREES, new ItemStack(ModBlocks.PALM_LOG.get())));
        blocks.add(new Filter(FILTER_VASES, new ItemStack(ModBlocks.AMPHORA_VASE_RESTORED.get())));
        blocks.add(new Filter(FIGURINES, new ItemStack(ModBlocks.ANU_FIGURINE_DESTROYED.get())));
        blocks.add(new Filter(FILTER_PLANTS, new ItemStack(PrehistoricPlantInfo.BENNETTITALES_LARGE.getPlantBlock())));
        blocks.add(new Filter(FILTER_UNBREAKABLE, new ItemStack(ModBlocks.REINFORCED_GLASS.get())));
        blocks.add(new Filter(FILTER_MACHINES, new ItemStack(ModBlocks.ANALYZER.get())));
        blocks.add(new Filter(FILTER_BUILDING_BLOCKS, new ItemStack(ModBlocks.ANCIENT_STONE_BRICKS.get())));
        blocks.add(new Filter(FILTER_PARK, new ItemStack(ModItems.TOY_BALLS.get(DyeColor.RED).get())));

        ClientGuiEvent.RENDER_CONTAINER_BACKGROUND.register((screen, guiGraphics, mouseX, mouseY, delta) -> {
            if (screen instanceof CreativeModeInventoryScreen && tabs.containsKey(CreativeModeInventoryScreen.selectedTab)) {
                tabs.get(CreativeModeInventoryScreen.selectedTab).renderButtons(guiGraphics, mouseX, mouseY, delta);
            }
        });
        ClientGuiEvent.RENDER_PRE.register((screen, matrices, mouseX, mouseY, delta) -> {
            if (screen instanceof CreativeModeInventoryScreen creativeScreen && tabs.containsKey(CreativeModeInventoryScreen.selectedTab)) {
                CreativeModeTab oldTab = activeTab;
                activeTab = CreativeModeInventoryScreen.selectedTab;
                boolean switchedTab = activeTab != oldTab;
                FilterTab filterTab = tabs.get(activeTab);
                if (oldTab == null) {
                    filterTab.enableButtons();
                } else if (switchedTab) {
                    tabs.get(oldTab).disableButtons();
                    filterTab.enableButtons();
                }
                tabs.get(activeTab).getTag().ifPresent(tag -> {
                    creativeScreen.getMenu().items.clear();
                    creativeScreen.getMenu().items.addAll(activeTab.getDisplayItems().stream().filter(itemStack -> itemStack.is(tag)).toList());
                });
                creativeScreen.getMenu().scrollTo(switchedTab ? 0 : creativeScreen.scrollOffs);
            }
            return EventResult.pass();
        });
        ClientGuiEvent.INIT_POST.register((screen, access) -> {
            if (screen instanceof CreativeModeInventoryScreen) {
                tabs.put(ModTabs.FA_MOB_ITEM_TAB.get(), FilterTab.build(screen, entityItems, access));
                tabs.put(ModTabs.FA_BLOCK_TAB.get(), FilterTab.build(screen, blocks, access));
                activeTab = null;
            }
        });
    }
}