package com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon;

import com.github.teamfossilsarcheology.fossil.food.FoodMappingsManager;
import net.brnbrd.delightful.common.block.DelightfulBlocks;
import net.brnbrd.delightful.common.item.DelightfulItems;
import net.minecraftforge.fml.ModList;
import vectorwing.farmersdelight.common.block.PieBlock;

import static com.github.teamfossilsarcheology.fossil.forge.compat.farmers.FarmersDelightCompat.getPieValue;

public class DelightfulCompat {
    public static void registerFoodMappings(FoodMappingsManager manager) {
        manager.addPlant(DelightfulItems.ACORN.get());
        manager.addMeat(DelightfulItems.ANIMAL_FAT.get());
        manager.addPlant(DelightfulItems.CACTUS_FLESH.get());
        manager.addPlant(DelightfulItems.CACTUS_STEAK.get());
        manager.addPlant(DelightfulItems.CANTALOUPE_SLICE.get());
        manager.addMeat(DelightfulItems.CHEESEBURGER.get());
        manager.addPlant(DelightfulItems.CHOPPED_CLOVER.get());
        manager.addMeat(DelightfulItems.CHUNKWICH.get());
        manager.addPlant(DelightfulItems.COCONUT_CURRY.get()); //Has coconut in it so I counted it as a plant
        manager.addMeat(DelightfulItems.COOKED_GOAT.get());
        //manager.addPlant(DelightfulItems.COOKED_MARSHMALLOW_STICK.get()); This doesn't really classify as a plant, meat, egg, insect nor fish, and is unhealthy(for animals), so I didn't add it
        manager.addMeat(DelightfulItems.COOKED_VENISON_CHOPS.get());
        manager.addMeat(DelightfulItems.CRAB_RANGOON.get());
        manager.addMeat(DelightfulItems.DELUXE_CHEESEBURGER.get());
        manager.addPlant(DelightfulItems.FIELD_SALAD.get());
        //manager.addPlant(DelightfulItems.GLOW_JELLY_BOTTLE.get()); Same as the marshmallow
        manager.addPlant(DelightfulItems.GREEN_TEA_LEAF.get());
        manager.addPlant(DelightfulItems.HONEY_GLAZED_WALNUT.get()); //I'll count a walnut as a plant.
        //manager.addPlant(DelightfulItems.JELLY_BOTTLE.get()); Same as the glow jelly bottle
        //manager.addPlant(DelightfulItems.MARSHMALLOW_STICK.get()); Same as the cooked marshmallow
        //manager.addPlant(DelightfulItems.MATCHA_ICE_CREAM.get()); Same as marshmallow/glow jelly bottle
        //manager.addPlant(DelightfulItems.NUT_BUTTER_AND_JELLY_SANDWICH.get()); Same as the matcha ice cream
        manager.addPlant(DelightfulItems.NUT_BUTTER_BOTTLE.get()); //I thought of not including this, but it is made of acorns.
        manager.addMeat(DelightfulItems.RAW_GOAT.get());
        //manager.addPlant(DelightfulItems.ROCK_CANDY.get()); Same as the marshmallow
        manager.addPlant(DelightfulItems.SALMONBERRIES.get()); //This is more berry than salmon.
        //manager.addPlant(DelightfulItems.SALMONBERRY_ICE_CREAM.get()); Same as matcha ice cream
        manager.addMeat(DelightfulItems.SINIGANG.get());
        //manager.addPlant(DelightfulItems.SMORE.get()); Same as marshmallow(this is made from marshmallow+some other excluded stuf)
        manager.addMeat(DelightfulItems.VENISON_CHOPS.get());
        manager.addPlant(DelightfulItems.SALMONBERRY_PIE.get(), getPieValue((PieBlock) DelightfulBlocks.SALMONBERRY_PIE.get()));
        manager.addPlant(DelightfulItems.PUMPKIN_PIE_SLICE.get());
        //24/33 added, 9 excluded
        if (ModList.get().isLoaded("byg")) {//Biomes you'll go
            BYGAndDelightfuLCompat.registerFoodMappings(manager);
        }
        if (ModList.get().isLoaded("ars_nouveau")) {
            ArsNouveauAndDelightfulCompat.registerFoodMappings(manager);
        }
    }
}
