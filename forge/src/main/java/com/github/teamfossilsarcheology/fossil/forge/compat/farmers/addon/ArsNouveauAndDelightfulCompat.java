package com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon;

import com.github.teamfossilsarcheology.fossil.food.FoodMappingsManager;
import com.hollingsworth.arsnouveau.setup.registry.ItemsRegistry;
import net.brnbrd.delightful.common.block.DelightfulBlocks;
import net.brnbrd.delightful.common.item.DelightfulItems;

import static com.github.teamfossilsarcheology.fossil.forge.compat.farmers.FarmersDelightCompat.getPieValue;

public class ArsNouveauAndDelightfulCompat {
    public static void registerFoodMappings(FoodMappingsManager manager) {
        manager.addPlant(ItemsRegistry.SOURCE_BERRY_PIE.get(), getPieValue(DelightfulBlocks.SOURCE_BERRY_PIE.get()));
        manager.addPlant(DelightfulItems.SOURCE_BERRY_PIE_SLICE.get());
        //2/2 added
    }
}
