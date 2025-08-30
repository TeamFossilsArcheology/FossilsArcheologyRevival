package com.github.teamfossilsarcheology.fossil.fabric.compat.farmers.addon;

import com.axedgaming.common.registry.EDBlocks;
import com.axedgaming.common.registry.EDItems;
import com.github.teamfossilsarcheology.fossil.food.FoodMappingsManager;
import vectorwing.farmersdelight.common.block.PieBlock;

import static com.github.teamfossilsarcheology.fossil.fabric.compat.farmers.FarmersDelightCompat.getPieValue;

public class EnderDelightCompat {
    public static void registerFoodMappings(FoodMappingsManager manager) {
        manager.addPlant(EDItems.CHORUS_STEW.get());
        manager.addMeat(EDItems.CRAWLING_SANDWICH.get());
        manager.addMeat(EDItems.ENDER_PAELLA.get()); //This has meat in it so I will count it as meat
        manager.addMeat(EDItems.ENDERMITE_STEW.get());
        manager.addPlant(EDItems.PEARL_PASTA.get()); //This is pasta so I will count it as a plant
        manager.addPlant(EDItems.STRANGE_ECLAIR.get()); //This is made of wheat, sugar and eye of ender so I will count it as a plant
        manager.addPlant(EDItems.STUFFED_SHULKER_BOWL.get());
        manager.addPlant(EDItems.TWISTED_CEREAL.get()); //This is made of ender sight, ender eyes, chorus fruit and milk, I will add it as a plant because ender sight/ender eyes isn't really "meat"
        manager.addMeat(EDItems.UNCANNY_COOKIES.get()); //Has endermite skin in it, I will count that as meat.
        manager.addMeat(EDItems.CRISPY_SKEWER.get());
        manager.addPlant(EDItems.CHORUS_PIE_SLICE.get());
        manager.addPlant(EDItems.CHORUS_PIE.get(), getPieValue((PieBlock) EDBlocks.CHORUS_PIE.get()));
        //18/21 added, Excluded 3 items because they cause nausea.
    }
}
