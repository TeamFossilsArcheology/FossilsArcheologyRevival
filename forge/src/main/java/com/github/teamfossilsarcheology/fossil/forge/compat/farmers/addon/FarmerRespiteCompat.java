package com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon;

import com.farmersrespite.core.registry.FRBlocks;
import com.farmersrespite.core.registry.FRItems;
import com.github.teamfossilsarcheology.fossil.food.FoodMappings;
import vectorwing.farmersdelight.common.block.PieBlock;

import static com.github.teamfossilsarcheology.fossil.forge.compat.farmers.FarmersDelightCompat.getPieValue;

public class FarmerRespiteCompat {
    public static void registerFoodMappings() {
        FoodMappings.addPlant(FRBlocks.WILD_TEA_BUSH.get(), 20);
        FoodMappings.addPlant(FRItems.TEA_SEEDS.get(), 5);
        FoodMappings.addPlant(FRItems.GREEN_TEA_LEAVES.get(), 5);
        FoodMappings.addPlant(FRItems.YELLOW_TEA_LEAVES.get(), 5);
        FoodMappings.addPlant(FRItems.BLACK_TEA_LEAVES.get(), 5);
        FoodMappings.addPlant(FRItems.COFFEE_BERRIES.get());
        FoodMappings.addPlant(FRItems.ROSE_HIPS.get(), 5);
        FoodMappings.addPlant(FRItems.GREEN_TEA_COOKIE.get());
        FoodMappings.addPlant(FRItems.NETHER_WART_SOURDOUGH.get());
        FoodMappings.addFish(FRItems.BLACK_COD.get());
        FoodMappings.addPlant(FRItems.TEA_CURRY.get());
        FoodMappings.addMeat(FRItems.BLAZING_CHILI.get());
        FoodMappings.addPlant(FRItems.COFFEE_CAKE.get(), 7 * 7 * 3);
        FoodMappings.addPlant(FRItems.COFFEE_CAKE_SLICE.get());
        FoodMappings.addPlant(FRItems.ROSE_HIP_PIE_SLICE.get());
        FoodMappings.addPlant(FRItems.ROSE_HIP_PIE.get(), getPieValue((PieBlock) FRBlocks.ROSE_HIP_PIE.get()));
    }
}
