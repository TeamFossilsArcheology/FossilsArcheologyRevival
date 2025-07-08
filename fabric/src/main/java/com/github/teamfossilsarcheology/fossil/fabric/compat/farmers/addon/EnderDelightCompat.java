package com.github.teamfossilsarcheology.fossil.fabric.compat.farmers.addon;

import com.axedgaming.common.registry.EDBlocks;
import com.axedgaming.common.registry.EDItems;
import com.github.teamfossilsarcheology.fossil.util.FoodMappings;
import vectorwing.farmersdelight.common.block.PieBlock;

import static com.github.teamfossilsarcheology.fossil.fabric.compat.farmers.FarmersDelightCompat.getPieValue;

public class EnderDelightCompat {
    public static void registerFoodMappings() {
        FoodMappings.addPlant(EDItems.CHORUS_STEW.get());
        FoodMappings.addMeat(EDItems.CRAWLING_SANDWICH.get());
        FoodMappings.addMeat(EDItems.ENDER_PAELLA.get()); //This has meat in it so I will count it as meat
        FoodMappings.addMeat(EDItems.ENDERMITE_STEW.get());
        FoodMappings.addPlant(EDItems.PEARL_PASTA.get()); //This is pasta so I will count it as a plant
        FoodMappings.addPlant(EDItems.STRANGE_ECLAIR.get()); //This is made of wheat, sugar and eye of ender so I will count it as a plant
        FoodMappings.addPlant(EDItems.STUFFED_SHULKER_BOWL.get());
        FoodMappings.addPlant(EDItems.TWISTED_CEREAL.get()); //This is made of ender sight, ender eyes, chorus fruit and milk, I will add it as a plant because ender sight/ender eyes isn't really "meat"
        FoodMappings.addMeat(EDItems.UNCANNY_COOKIES.get()); //Has endermite skin in it, I will count that as meat.
        FoodMappings.addMeat(EDItems.CRISPY_SKEWER.get());
        FoodMappings.addPlant(EDItems.CHORUS_PIE_SLICE.get());
        FoodMappings.addPlant(EDItems.CHORUS_PIE.get(), getPieValue((PieBlock) EDBlocks.CHORUS_PIE.get()));
        //18/21 added, Excluded 3 items because they cause nausea.
    }
}
