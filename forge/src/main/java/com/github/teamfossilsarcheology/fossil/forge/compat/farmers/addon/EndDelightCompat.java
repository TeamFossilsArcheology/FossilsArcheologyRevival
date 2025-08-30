package com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon;

import cn.foggyhillside.ends_delight.registry.BlockRegistry;
import cn.foggyhillside.ends_delight.registry.ItemRegistry;
import com.github.teamfossilsarcheology.fossil.food.FoodMappingsManager;
import vectorwing.farmersdelight.common.block.PieBlock;

import static com.github.teamfossilsarcheology.fossil.forge.compat.farmers.FarmersDelightCompat.getPieValue;

public class EndDelightCompat {
    public static void registerFoodMappings(FoodMappingsManager manager) {
        manager.addPlant(ItemRegistry.AssortedSalad.get());
        manager.addPlant(ItemRegistry.ChorusCookie.get());
        manager.addPlant(ItemRegistry.ChorusFruitPie.get(), getPieValue((PieBlock) BlockRegistry.ChorusFruitPie.get()));
        manager.addPlant(ItemRegistry.ChorusFlowerPie.get());
        manager.addPlant(ItemRegistry.ChorusFruitPieSlice.get());
        manager.addPlant(ItemRegistry.ChorusFruitGrain.get());
        manager.addPlant(ItemRegistry.ChorusFruitPopsicle.get());
        manager.addPlant(ItemRegistry.ChorusSucculent.get());
        manager.addPlant(ItemRegistry.DragonBreathAndChorusSoup.get());
        manager.addMeat(ItemRegistry.DragonLeg.get());
        manager.addMeat(ItemRegistry.DragonLegWithSauce.get());
        manager.addMeat(ItemRegistry.DragonMeatStew.get());
        manager.addMeat(ItemRegistry.DriedEndermiteMeat.get());
        manager.addMeat(ItemRegistry.EndBarbecueStick.get());
        manager.addMeat(ItemRegistry.EnderCongee.get());
        manager.addPlant(ItemRegistry.ChorusSauce.get());
        manager.addPlant(ItemRegistry.EndMixedSalad.get());
        manager.addEgg(ItemRegistry.FriedDragonEgg.get());
        manager.addMeat(ItemRegistry.GrilledShulker.get());
        manager.addEgg(ItemRegistry.LiquidDragonEgg.get());
        manager.addMeat(ItemRegistry.RawDragonMeat.get());
        manager.addMeat(ItemRegistry.RawDragonMeatCuts.get());
        manager.addMeat(ItemRegistry.RawEndermiteMeat.get());
        manager.addMeat(ItemRegistry.RoastedDragonMeat.get());
        manager.addMeat(ItemRegistry.RoastedDragonMeatCuts.get());
        manager.addMeat(ItemRegistry.RoastedDragonSteak.get());
        manager.addMeat(ItemRegistry.RoastedShulkerMeat.get());
        manager.addMeat(ItemRegistry.RoastedShulkerMeatSlice.get());
        manager.addMeat(ItemRegistry.ShulkerMeat.get());
        manager.addMeat(ItemRegistry.ShulkerMeatSlice.get());
        manager.addMeat(ItemRegistry.SmokedDragonLeg.get());
        manager.addMeat(ItemRegistry.StirFriedShulkerMeat.get());
        manager.addPlant(ItemRegistry.StuffedRiceCake.get());
        manager.addPlant(ItemRegistry.DriedChorusFlower.get(), 5);
        manager.addEgg(ItemRegistry.SteamedDragonEgg.get());
        //33/33 added
    }
}
