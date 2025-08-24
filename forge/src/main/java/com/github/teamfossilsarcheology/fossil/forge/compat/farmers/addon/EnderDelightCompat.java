package com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon;

import com.axedgaming.endersdelight.block.ModBlocks;
import com.axedgaming.endersdelight.block.PieBlock;
import com.axedgaming.endersdelight.item.ModItems;
import com.github.teamfossilsarcheology.fossil.util.FoodMappings;

public class EnderDelightCompat {
    public static void registerFoodMappings() {
        FoodMappings.addPlant(ModItems.CHORUS_STEW.get());
        FoodMappings.addMeat(ModItems.CRAWLING_SANDWICH.get());
        FoodMappings.addMeat(ModItems.ENDER_PAELLA.get()); //This has meat in it so I will count it as meat
        FoodMappings.addMeat(ModItems.ENDERMITE_STEW.get());
        FoodMappings.addPlant(ModItems.PEARL_PASTA.get()); //This is pasta so I will count it as a plant
        FoodMappings.addPlant(ModItems.STRANGE_ECLAIR.get()); //This is made of wheat, sugar and eye of ender so I will count it as a plant
        FoodMappings.addPlant(ModItems.STUFFED_SHULKER_BOWL.get());
        FoodMappings.addPlant(ModItems.TWISTED_CEREAL.get()); //This is made of ender sight, ender eyes, chorus fruit and milk, I will add it as a plant because ender sight/ender eyes isn't really "meat"
        FoodMappings.addMeat(ModItems.UNCANNY_COOKIES.get()); //Has endermite skin in it, I will count that as meat.
        FoodMappings.addMeat(ModItems.CRISPY_SKEWER.get());
        FoodMappings.addPlant(ModItems.CHORUS_PIE_SLICE.get());
        FoodMappings.addPlant(ModItems.CHORUS_PIE.get(), getPieValue((PieBlock) ModBlocks.CHORUS_PIE.get()));
        //18/21 added, Excluded 3 items because they cause nausea.
    }

    private static int getPieValue(PieBlock block) {
        return block.getPieSliceItem().getItem().getFoodProperties().getNutrition() * block.getMaxBites() * 5;
    }
}
