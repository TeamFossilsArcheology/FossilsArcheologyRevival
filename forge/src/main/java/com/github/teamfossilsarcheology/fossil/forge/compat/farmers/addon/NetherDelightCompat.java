package com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon;

import com.github.teamfossilsarcheology.fossil.food.FoodMappingsManager;
import umpaz.nethersdelight.common.registry.NDBlocks;
import umpaz.nethersdelight.common.registry.NDItems;

public class NetherDelightCompat {
    public static void registerFoodMappings(FoodMappingsManager manager) {
        manager.addMeat(NDItems.GRILLED_STRIDER.get());
        manager.addMeat(NDItems.GROUND_STRIDER.get());
        manager.addMeat(NDItems.HOGLIN_EAR.get());
        manager.addMeat(NDItems.HOGLIN_LOIN.get());
        manager.addMeat(NDItems.HOGLIN_SIRLOIN.get());
        manager.addPlant(NDBlocks.PROPELPLANT_CANE.get(), 15);
        manager.addPlant(NDBlocks.PROPELPLANT_STEM.get(), 15);
        //manager.addMeat(NDItems.MAGMA_GELATIN.get()); Excluded because it doesn't make sense for it to be edible by dinos
        manager.addMeat(NDItems.PLATE_OF_STUFFED_HOGLIN_HAM.get());
        manager.addMeat(NDItems.PLATE_OF_STUFFED_HOGLIN_ROAST.get());
        manager.addMeat(NDItems.PLATE_OF_STUFFED_HOGLIN_SNOUT.get());
        manager.addMeat(NDItems.NETHER_SKEWER.get());
        manager.addPlant(NDItems.PROPELPEARL.get());  //This seems like a plant? It grows from canes.
        manager.addMeat(NDItems.STRIDER_MOSS_STEW.get());
        manager.addMeat(NDItems.STRIDER_SLICE.get());
        manager.addMeat(NDItems.WARPED_MOLDY_MEAT.get());
        //13/14 added, 1 excluded
    }
}
