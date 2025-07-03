package com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon;

import cn.foggyhillside.endsdelight.registry.BlockRegistry;
import cn.foggyhillside.endsdelight.registry.ItemRegistry;
import com.github.teamfossilsarcheology.fossil.util.FoodMappings;
import vectorwing.farmersdelight.common.block.PieBlock;

import static com.github.teamfossilsarcheology.fossil.forge.compat.farmers.FarmersDelightCompat.getPieValue;

public class EndDelightCompat {
    public static void registerEndDelightFoodMappings() {
        FoodMappings.addPlant(ItemRegistry.AssortedSalad.get());
        FoodMappings.addPlant(ItemRegistry.ChorusCookie.get());
        FoodMappings.addPlant(ItemRegistry.ChorusFruitPie.get(), getPieValue((PieBlock) BlockRegistry.ChorusFruitPie.get()));
        FoodMappings.addPlant(ItemRegistry.ChorusFlowerPie.get());
        FoodMappings.addPlant(ItemRegistry.ChorusFruitPieSlice.get());
        FoodMappings.addPlant(ItemRegistry.ChorusFruitGrain.get());
        FoodMappings.addPlant(ItemRegistry.ChorusFruitPopsicle.get());
        FoodMappings.addPlant(ItemRegistry.ChorusSucculent.get());
        FoodMappings.addPlant(ItemRegistry.DragonBreathAndChorusSoup.get());
        FoodMappings.addMeat(ItemRegistry.DragonLeg.get());
        FoodMappings.addMeat(ItemRegistry.DragonLegWithSauce.get());
        FoodMappings.addMeat(ItemRegistry.DragonMeatStew.get());
        FoodMappings.addMeat(ItemRegistry.DriedEnderMiteMeat.get());
        FoodMappings.addMeat(ItemRegistry.EndBarbecueStick.get());
        FoodMappings.addMeat(ItemRegistry.EnderCongee.get());
        FoodMappings.addPlant(ItemRegistry.EnderSauce.get());
        FoodMappings.addPlant(ItemRegistry.EndMixedSalad.get());
        FoodMappings.addEgg(ItemRegistry.FriedDragonEgg.get());
        FoodMappings.addMeat(ItemRegistry.GrilledShulker.get());
        FoodMappings.addEgg(ItemRegistry.LiquidDragonEgg.get());
        FoodMappings.addMeat(ItemRegistry.RawDragonMeat.get());
        FoodMappings.addMeat(ItemRegistry.RawDragonMeatCuts.get());
        FoodMappings.addMeat(ItemRegistry.RawEnderMiteMeat.get());
        FoodMappings.addMeat(ItemRegistry.RoastedDragonMeat.get());
        FoodMappings.addMeat(ItemRegistry.RoastedDragonMeatCuts.get());
        FoodMappings.addMeat(ItemRegistry.RoastedDragonSteak.get());
        FoodMappings.addMeat(ItemRegistry.RoastedShulkerMeat.get());
        FoodMappings.addMeat(ItemRegistry.RoastedShulkerMeatSlice.get());
        FoodMappings.addMeat(ItemRegistry.ShulkerMeat.get());
        FoodMappings.addMeat(ItemRegistry.ShulkerMeatSlice.get());
        FoodMappings.addMeat(ItemRegistry.SmokedDragonLeg.get());
        FoodMappings.addMeat(ItemRegistry.StirFriedShulkerMeat.get());
        FoodMappings.addPlant(ItemRegistry.StuffedRiceCake.get());
        FoodMappings.addPlant(ItemRegistry.DriedChorusFlower.get(), 5);
        FoodMappings.addEgg(ItemRegistry.SteamedDragonEgg.get());
        //33/33 added
    }
}
