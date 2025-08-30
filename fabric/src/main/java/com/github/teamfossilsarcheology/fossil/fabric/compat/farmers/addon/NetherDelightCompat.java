package com.github.teamfossilsarcheology.fossil.fabric.compat.farmers.addon;

import com.github.teamfossilsarcheology.fossil.food.FoodMappingsManager;
import com.nethersdelight.core.registry.NDBlocks;
import com.nethersdelight.core.registry.NDItems;

public class NetherDelightCompat {
    public static void registerFoodMappings(FoodMappingsManager manager) {
        manager.addMeat(NDItems.GRILLED_STRIDER);
        manager.addMeat(NDItems.GROUND_STRIDER);
        manager.addMeat(NDItems.HOGLIN_EAR);
        manager.addMeat(NDItems.HOGLIN_LOIN);
        manager.addMeat(NDItems.HOGLIN_SIRLOIN);
        manager.addPlant(NDBlocks.PROPELPLANT_CANE.get(), 15);
        manager.addPlant(NDBlocks.PROPELPLANT_STEM.get(), 15);
        //manager.addMeat(NDItems.MAGMA_GELATIN); Excluded because it doesn't make sense for it to be edible by dinos
        manager.addMeat(NDItems.PLATE_OF_STUFFED_HOGLIN_HAM);
        manager.addMeat(NDItems.PLATE_OF_STUFFED_HOGLIN_ROAST);
        manager.addMeat(NDItems.PLATE_OF_STUFFED_HOGLIN_SNOUT);
        manager.addMeat(NDItems.NETHER_SKEWER);
        manager.addPlant(NDItems.PROPELPEARL);  //This seems like a plant? It grows from canes.
        manager.addMeat(NDItems.STRIDER_MOSS_STEW);
        manager.addMeat(NDItems.STRIDER_SLICE);
        manager.addMeat(NDItems.WARPED_MOLDY_MEAT);
        //13/14 added, 1 excluded
    }
}
