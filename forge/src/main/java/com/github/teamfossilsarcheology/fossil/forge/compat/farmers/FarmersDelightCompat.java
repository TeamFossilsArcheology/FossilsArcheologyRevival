package com.github.teamfossilsarcheology.fossil.forge.compat.farmers;

import com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon.util.AddonConstants;
import com.github.teamfossilsarcheology.fossil.util.FoodMappings;
import net.minecraftforge.fml.ModList;
import vectorwing.farmersdelight.common.block.PieBlock;
import vectorwing.farmersdelight.common.registry.ModBlocks;
import vectorwing.farmersdelight.common.registry.ModItems;

import static com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon.AlexDelightCompat.registerAlexDelightFoodMappings;
import static com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon.ArgentinaDelightCompat.registerArgentinaDelightFoodMappings;
import static com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon.BrewinAndChewinCompat.registerBrewinAndChewinFoodMappings;
import static com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon.ButcherDelightFoodCompat.registerButcherDelightFoodFoodMappings;
import static com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon.CasualnessDelightCompat.registerCasualnessDelightFoodMappings;
import static com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon.CoffeeDelightCompat.registerCoffeeDelightFoodMappings;
import static com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon.CornDelightCompat.registerCornDelightFoodMappings;
import static com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon.CrabberDelightCompat.registerCrabberDelightFoodMappings;
import static com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon.CulturalDelightCompat.registerCulturalDelightFoodMappings;
import static com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon.DelightfulCompat.registerDelightfulFoodMappings;
import static com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon.EndDelightCompat.registerEndDelightFoodMappings;
import static com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon.EnderDelightCompat.registerEnderDelightFoodMappings;
import static com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon.FarmerRespiteCompat.registerFarmerRespiteFoodMappings;
import static com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon.FestiveDelightCompat.registerFestiveDelightFoodMappings;
import static com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon.HoneyExpansionCompat.registerHoneyExpansionFoodMappings;
import static com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon.ItalianDelightCompat.registerItalianDelightFoodMappings;
import static com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon.LargeMealsCompat.registerLargeMealsFoodMappings;
import static com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon.MinerDelightCompat.registerMinerDelightFoodMappings;
import static com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon.NetherDelightCompat.registerNetherDelightFoodMappings;
import static com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon.OceanDelightCompat.registerOceanDelightFoodMappings;
import static com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon.PineappleDelightCompat.registerPineappleDelightFoodMappings;
import static com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon.SeedDelightCompat.registerSeedDelightFoodMappings;

public class FarmersDelightCompat {
    public static void registerFoodMappings() {
        ModList mods = ModList.get();
        FoodMappings.addPlant(ModItems.CABBAGE.get());
        FoodMappings.addPlant(ModItems.TOMATO.get());
        FoodMappings.addPlant(ModItems.ONION.get());
        FoodMappings.addPlant(ModItems.TOMATO_SAUCE.get());
        FoodMappings.addPlant(ModItems.WHEAT_DOUGH.get());
        FoodMappings.addPlant(ModItems.RAW_PASTA.get());
        FoodMappings.addPlant(ModItems.PUMPKIN_SLICE.get());
        FoodMappings.addPlant(ModItems.CABBAGE_LEAF.get());
        FoodMappings.addPlant(ModItems.PIE_CRUST.get());
        FoodMappings.addPlant(ModItems.APPLE_PIE.get(), getPieValue((PieBlock) ModBlocks.APPLE_PIE.get()));
        FoodMappings.addPlant(ModItems.SWEET_BERRY_CHEESECAKE.get(), getPieValue((PieBlock) ModBlocks.SWEET_BERRY_CHEESECAKE.get()));
        FoodMappings.addPlant(ModItems.CHOCOLATE_PIE.get(), getPieValue((PieBlock) ModBlocks.CHOCOLATE_PIE.get()));
        FoodMappings.addPlant(ModItems.APPLE_PIE_SLICE.get());
        FoodMappings.addPlant(ModItems.SWEET_BERRY_CHEESECAKE_SLICE.get());
        FoodMappings.addPlant(ModItems.CHOCOLATE_PIE_SLICE.get());
        FoodMappings.addPlant(ModItems.SWEET_BERRY_COOKIE.get());
        FoodMappings.addPlant(ModItems.HONEY_COOKIE.get());
        FoodMappings.addPlant(ModItems.MELON_POPSICLE.get());
        FoodMappings.addPlant(ModItems.GLOW_BERRY_CUSTARD.get());
        FoodMappings.addPlant(ModItems.FRUIT_SALAD.get());
        FoodMappings.addPlant(ModItems.MIXED_SALAD.get());
        FoodMappings.addPlant(ModItems.NETHER_SALAD.get());
        FoodMappings.addPlant(ModItems.KELP_ROLL.get());
        FoodMappings.addPlant(ModItems.KELP_ROLL_SLICE.get());
        FoodMappings.addPlant(ModItems.COOKED_RICE.get());
        FoodMappings.addPlant(ModItems.VEGETABLE_SOUP.get());
        FoodMappings.addPlant(ModItems.FRIED_RICE.get());
        FoodMappings.addPlant(ModItems.PUMPKIN_SOUP.get());
        FoodMappings.addPlant(ModItems.MUSHROOM_RICE.get());
        FoodMappings.addPlant(ModItems.VEGETABLE_NOODLES.get());
        FoodMappings.addPlant(ModItems.VEGETABLE_NOODLES.get());
        FoodMappings.addPlant(ModItems.STUFFED_PUMPKIN.get());
        FoodMappings.addPlant(ModItems.ROAST_CHICKEN.get());
        FoodMappings.addPlant(ModItems.ROAST_CHICKEN.get());
        FoodMappings.addPlant(ModItems.CABBAGE_SEEDS.get(), 5);
        FoodMappings.addPlant(ModItems.TOMATO_SEEDS.get(), 5);

        FoodMappings.addEgg(ModItems.FRIED_EGG.get());
        FoodMappings.addEgg(ModItems.EGG_SANDWICH.get());


        FoodMappings.addMeat(ModItems.MINCED_BEEF.get());
        FoodMappings.addMeat(ModItems.BEEF_PATTY.get());
        FoodMappings.addMeat(ModItems.CHICKEN_CUTS.get());
        FoodMappings.addMeat(ModItems.COOKED_CHICKEN_CUTS.get());
        FoodMappings.addMeat(ModItems.BACON.get());
        FoodMappings.addMeat(ModItems.COOKED_BACON.get());
        FoodMappings.addMeat(ModItems.HAM.get());
        FoodMappings.addMeat(ModItems.SMOKED_HAM.get());
        FoodMappings.addMeat(ModItems.BARBECUE_STICK.get());
        FoodMappings.addMeat(ModItems.CHICKEN_SANDWICH.get());
        FoodMappings.addMeat(ModItems.HAMBURGER.get());
        FoodMappings.addMeat(ModItems.BACON_SANDWICH.get());
        FoodMappings.addMeat(ModItems.MUTTON_WRAP.get());
        FoodMappings.addMeat(ModItems.DUMPLINGS.get());
        FoodMappings.addMeat(ModItems.STUFFED_POTATO.get());
        FoodMappings.addMeat(ModItems.BONE_BROTH.get());
        FoodMappings.addMeat(ModItems.BEEF_STEW.get());
        FoodMappings.addMeat(ModItems.CHICKEN_SOUP.get());
        FoodMappings.addMeat(ModItems.NOODLE_SOUP.get());
        FoodMappings.addMeat(ModItems.BACON_AND_EGGS.get());
        FoodMappings.addMeat(ModItems.PASTA_WITH_MEATBALLS.get());
        FoodMappings.addMeat(ModItems.PASTA_WITH_MUTTON_CHOP.get());
        FoodMappings.addMeat(ModItems.CABBAGE_ROLLS.get());
        FoodMappings.addMeat(ModItems.ROASTED_MUTTON_CHOPS.get());
        FoodMappings.addMeat(ModItems.STEAK_AND_POTATOES.get());
        FoodMappings.addMeat(ModItems.ROAST_CHICKEN.get());
        FoodMappings.addMeat(ModItems.ROASTED_MUTTON_CHOPS.get());
        FoodMappings.addMeat(ModItems.ROASTED_MUTTON_CHOPS.get());
        FoodMappings.addMeat(ModItems.ROASTED_MUTTON_CHOPS.get());

        FoodMappings.addFish(ModItems.COD_SLICE.get());
        FoodMappings.addFish(ModItems.COOKED_COD_SLICE.get());
        FoodMappings.addFish(ModItems.SALMON_SLICE.get());
        FoodMappings.addFish(ModItems.COOKED_SALMON_SLICE.get());
        FoodMappings.addFish(ModItems.SALMON_ROLL.get());
        FoodMappings.addFish(ModItems.COD_ROLL.get());
        FoodMappings.addFish(ModItems.FISH_STEW.get());
        FoodMappings.addFish(ModItems.BAKED_COD_STEW.get());
        FoodMappings.addFish(ModItems.GRILLED_SALMON.get());
        FoodMappings.addFish(ModItems.COD_ROLL.get());
        FoodMappings.addFish(ModItems.COD_ROLL.get());

        registerAddonFoodMappings();
    }

    public static void registerAddonFoodMappings() {
        //We cycle through each addon and check if it is loaded
        for (String addonId : AddonConstants.SUPPORTED_ADDONS) {
            if (!ModList.get().isLoaded(addonId)) {
                continue;
            }
            if (addonId.equals("delightful")) {
                registerDelightfulFoodMappings( //Delightful supports some more mods.
                        ModList.get().isLoaded("byg"), //Biomes you'll go
                        ModList.get().isLoaded("ars_nouveau") //Ars Nouveau
                );
                continue;
            }
            registerFoodMappingsByAddonId(addonId);
        }
    }

    public static void registerFoodMappingsByAddonId(String addonId) {
        switch (addonId) {
            //We have to have a unique class for each food mapping because if the class uses something from an unloaded mod it and we call one of its methods it causes issues in the FML Common Setup
            case "oceansdelight" -> {
                registerOceanDelightFoodMappings();
            }
            case "nethersdelight" -> {
                registerNetherDelightFoodMappings();
            }
            case "endersdelight" -> {
                registerEnderDelightFoodMappings();
            }
            case "ends_delight" -> {
                registerEndDelightFoodMappings();
            }
            case "crabbersdelight" -> {
                registerCrabberDelightFoodMappings();
            }
            case "miners_delight" -> {
                registerMinerDelightFoodMappings();
            }
            case "corn_delight" -> {
                registerCornDelightFoodMappings();
            }
            case "culturaldelights" -> {
                registerCulturalDelightFoodMappings();
            }
            case "pineapple_delight" -> {
                registerPineappleDelightFoodMappings();
            }
            case "largemeals" -> {
                registerLargeMealsFoodMappings();
            }
            case "festive_delight" -> {
                registerFestiveDelightFoodMappings();
            }
            case "butchersdelightfoods" -> {
                registerButcherDelightFoodFoodMappings();
            }
            case "coffee_delight" -> {
                registerCoffeeDelightFoodMappings();
            }
            case "casualness_delight" -> {
                registerCasualnessDelightFoodMappings();
            }
            case "italian_delight" -> {
                registerItalianDelightFoodMappings();
            }
            case "seeddelight" -> {
                registerSeedDelightFoodMappings();
            }
            case "argentinas_delight" -> {
                registerArgentinaDelightFoodMappings();
            }
            case "honeyexpansion" -> {
                registerHoneyExpansionFoodMappings();
            }
            case "brewinandchewin" -> {
                registerBrewinAndChewinFoodMappings();
            }
            case "alexsdelight" -> {
                registerAlexDelightFoodMappings();
            }
            case "farmersrespite" -> {
                registerFarmerRespiteFoodMappings();
            }
        }
    }

    public static int getPieValue(PieBlock block) {
        return block.getPieSliceItem().getItem().getFoodProperties().getNutrition() * block.getMaxBites();
    }

}
