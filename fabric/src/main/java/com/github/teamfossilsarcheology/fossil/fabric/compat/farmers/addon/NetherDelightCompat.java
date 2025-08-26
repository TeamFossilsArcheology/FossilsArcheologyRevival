package com.github.teamfossilsarcheology.fossil.fabric.compat.farmers.addon;

import com.github.teamfossilsarcheology.fossil.util.FoodMappings;
import com.nethersdelight.core.registry.NDBlocks;
import com.nethersdelight.core.registry.NDItems;

public class NetherDelightCompat {
    public static void registerFoodMappings() {
        FoodMappings.addMeat(NDItems.GRILLED_STRIDER);
        FoodMappings.addMeat(NDItems.GROUND_STRIDER);
        FoodMappings.addMeat(NDItems.HOGLIN_EAR);
        FoodMappings.addMeat(NDItems.HOGLIN_LOIN);
        FoodMappings.addMeat(NDItems.HOGLIN_SIRLOIN);
        FoodMappings.addPlant(NDBlocks.PROPELPLANT_CANE.get(), 15);
        FoodMappings.addPlant(NDBlocks.PROPELPLANT_STEM.get(), 15);
        //FoodMappings.addMeat(NDItems.MAGMA_GELATIN); Excluded because it doesn't make sense for it to be edible by dinos
        FoodMappings.addMeat(NDItems.PLATE_OF_STUFFED_HOGLIN_HAM);
        FoodMappings.addMeat(NDItems.PLATE_OF_STUFFED_HOGLIN_ROAST);
        FoodMappings.addMeat(NDItems.PLATE_OF_STUFFED_HOGLIN_SNOUT);
        FoodMappings.addMeat(NDItems.NETHER_SKEWER);
        FoodMappings.addPlant(NDItems.PROPELPEARL);  //This seems like a plant? It grows from canes.
        FoodMappings.addMeat(NDItems.STRIDER_MOSS_STEW);
        FoodMappings.addMeat(NDItems.STRIDER_SLICE);
        FoodMappings.addMeat(NDItems.WARPED_MOLDY_MEAT);
        //13/14 added, 1 excluded
    }
}
