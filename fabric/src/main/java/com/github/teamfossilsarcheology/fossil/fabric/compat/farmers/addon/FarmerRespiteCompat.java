package com.github.teamfossilsarcheology.fossil.fabric.compat.farmers.addon;

import com.chefsdelights.farmersrespite.core.registry.FRBlocks;
import com.chefsdelights.farmersrespite.core.registry.FRItems;
import com.github.teamfossilsarcheology.fossil.food.FoodMappingsManager;
import com.nhoryzon.mc.farmersdelight.block.PieBlock;

import static com.github.teamfossilsarcheology.fossil.fabric.compat.farmers.FarmersDelightCompat.getPieValue;

public class FarmerRespiteCompat {
    public static void registerFoodMappings(FoodMappingsManager manager) {
        manager.addPlant(FRBlocks.WILD_TEA_BUSH, 20);
        manager.addPlant(FRItems.TEA_SEEDS, 5);
        manager.addPlant(FRItems.GREEN_TEA_LEAVES, 5);
        manager.addPlant(FRItems.YELLOW_TEA_LEAVES, 5);
        manager.addPlant(FRItems.BLACK_TEA_LEAVES, 5);
        manager.addPlant(FRItems.COFFEE_BERRIES);
        manager.addPlant(FRItems.ROSE_HIPS, 5);
        manager.addPlant(FRItems.GREEN_TEA_COOKIE);
        manager.addPlant(FRItems.NETHER_WART_SOURDOUGH);
        manager.addFish(FRItems.BLACK_COD);
        manager.addPlant(FRItems.TEA_CURRY);
        manager.addMeat(FRItems.BLAZING_CHILI);
        manager.addPlant(FRItems.COFFEE_CAKE, 7 * 7 * 3);
        manager.addPlant(FRItems.COFFEE_CAKE_SLICE);
        manager.addPlant(FRItems.ROSE_HIP_PIE_SLICE);
        manager.addPlant(FRItems.ROSE_HIP_PIE, getPieValue((PieBlock) FRBlocks.ROSE_HIP_PIE));
    }
}
