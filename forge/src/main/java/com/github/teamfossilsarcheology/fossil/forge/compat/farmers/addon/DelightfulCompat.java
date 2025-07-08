package com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon;

import com.github.teamfossilsarcheology.fossil.util.FoodMappings;
import net.brdle.delightful.common.block.DelightfulBlocks;
import net.brdle.delightful.common.item.DelightfulItems;
import net.minecraftforge.fml.ModList;
import vectorwing.farmersdelight.common.block.PieBlock;

import static com.github.teamfossilsarcheology.fossil.forge.compat.farmers.FarmersDelightCompat.getPieValue;

public class DelightfulCompat {
    public static void registerFoodMappings() {
        FoodMappings.addPlant(DelightfulItems.ACORN.get());
        FoodMappings.addMeat(DelightfulItems.ANIMAL_FAT.get());
        FoodMappings.addPlant(DelightfulItems.CACTUS_FLESH.get());
        FoodMappings.addPlant(DelightfulItems.CACTUS_STEAK.get());
        FoodMappings.addPlant(DelightfulItems.CANTALOUPE_SLICE.get());
        FoodMappings.addMeat(DelightfulItems.CHEESEBURGER.get());
        FoodMappings.addPlant(DelightfulItems.CHOPPED_CLOVER.get());
        FoodMappings.addMeat(DelightfulItems.CHUNK_NUGGET.get()); //These chunk things seem like meat, they are from a mod called Rotten Leather(Delightful has compatability for that mod)
        FoodMappings.addMeat(DelightfulItems.CHUNKWICH.get());
        FoodMappings.addPlant(DelightfulItems.COCONUT_CURRY.get()); //Has coconut in it so I counted it as a plant
        FoodMappings.addMeat(DelightfulItems.COOKED_GOAT.get());
        //FoodMappings.addPlant(DelightfulItems.COOKED_MARSHMALLOW_STICK.get()); This doesn't really classify as a plant, meat, egg, insect nor fish, and is unhealthy(for animals), so I didn't add it
        FoodMappings.addMeat(DelightfulItems.COOKED_VENISON_CHOPS.get());
        FoodMappings.addMeat(DelightfulItems.CRAB_RANGOON.get());
        FoodMappings.addMeat(DelightfulItems.DELUXE_CHEESEBURGER.get());
        FoodMappings.addPlant(DelightfulItems.FIELD_SALAD.get());
        //FoodMappings.addPlant(DelightfulItems.GLOW_JELLY_BOTTLE.get()); Same as the marshmallow
        FoodMappings.addPlant(DelightfulItems.GREEN_TEA_LEAF.get());
        FoodMappings.addPlant(DelightfulItems.HONEY_GLAZED_WALNUT.get()); //I'll count a walnut as a plant.
        //FoodMappings.addPlant(DelightfulItems.JELLY_BOTTLE.get()); Same as the glow jelly bottle
        //FoodMappings.addPlant(DelightfulItems.MARSHMALLOW_STICK.get()); Same as the cooked marshmallow
        //FoodMappings.addPlant(DelightfulItems.MATCHA_ICE_CREAM.get()); Same as marshmallow/glow jelly bottle
        //FoodMappings.addPlant(DelightfulItems.NUT_BUTTER_AND_JELLY_SANDWICH.get()); Same as the matcha ice cream
        FoodMappings.addPlant(DelightfulItems.NUT_BUTTER_BOTTLE.get()); //I thought of not including this, but it is made of acorns.
        FoodMappings.addMeat(DelightfulItems.RAW_GOAT.get());
        //FoodMappings.addPlant(DelightfulItems.ROCK_CANDY.get()); Same as the marshmallow
        FoodMappings.addPlant(DelightfulItems.SALMONBERRIES.get()); //This is more berry than salmon.
        //FoodMappings.addPlant(DelightfulItems.SALMONBERRY_ICE_CREAM.get()); Same as matcha ice cream
        FoodMappings.addMeat(DelightfulItems.SINIGANG.get());
        //FoodMappings.addPlant(DelightfulItems.SMORE.get()); Same as marshmallow(this is made from marshmallow+some other excluded stuf)
        FoodMappings.addMeat(DelightfulItems.VENISON_CHOPS.get());
        FoodMappings.addPlant(DelightfulItems.SALMONBERRY_PIE.get(), getPieValue((PieBlock) DelightfulBlocks.SALMONBERRY_PIE.get()));
        FoodMappings.addPlant(DelightfulItems.PUMPKIN_PIE_SLICE.get());
        //24/33 added, 9 excluded
        if (ModList.get().isLoaded("byg")) {//Biomes you'll go
            BYGAndDelightfuLCompat.registerFoodMappings();
        }
        if (ModList.get().isLoaded("ars_nouveau")) {
            ArsNouveauAndDelightfulCompat.registerFoodMappings();
        }
    }
}
