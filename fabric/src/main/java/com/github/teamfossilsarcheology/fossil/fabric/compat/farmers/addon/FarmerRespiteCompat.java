package com.github.teamfossilsarcheology.fossil.fabric.compat.farmers.addon;

import com.chefsdelights.farmersrespite.core.registry.FRBlocks;
import com.chefsdelights.farmersrespite.core.registry.FRItems;
import com.github.teamfossilsarcheology.fossil.food.FoodMappings;
import com.nhoryzon.mc.farmersdelight.block.PieBlock;

import static com.github.teamfossilsarcheology.fossil.fabric.compat.farmers.FarmersDelightCompat.getPieValue;

public class FarmerRespiteCompat {
    public static void registerFoodMappings() {
        FoodMappings.addPlant(FRBlocks.WILD_TEA_BUSH, 20);
        FoodMappings.addPlant(FRItems.TEA_SEEDS, 5);
        FoodMappings.addPlant(FRItems.GREEN_TEA_LEAVES, 5);
        FoodMappings.addPlant(FRItems.YELLOW_TEA_LEAVES, 5);
        FoodMappings.addPlant(FRItems.BLACK_TEA_LEAVES, 5);
        FoodMappings.addPlant(FRItems.COFFEE_BERRIES);
        FoodMappings.addPlant(FRItems.ROSE_HIPS, 5);
        FoodMappings.addPlant(FRItems.GREEN_TEA_COOKIE);
        FoodMappings.addPlant(FRItems.NETHER_WART_SOURDOUGH);
        FoodMappings.addFish(FRItems.BLACK_COD);
        FoodMappings.addPlant(FRItems.TEA_CURRY);
        FoodMappings.addMeat(FRItems.BLAZING_CHILI);
        FoodMappings.addPlant(FRItems.COFFEE_CAKE, 7 * 7 * 3);
        FoodMappings.addPlant(FRItems.COFFEE_CAKE_SLICE);
        FoodMappings.addPlant(FRItems.ROSE_HIP_PIE_SLICE);
        FoodMappings.addPlant(FRItems.ROSE_HIP_PIE, getPieValue((PieBlock) FRBlocks.ROSE_HIP_PIE));
    }
}
