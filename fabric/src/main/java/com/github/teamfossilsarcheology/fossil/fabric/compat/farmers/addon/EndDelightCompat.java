package com.github.teamfossilsarcheology.fossil.fabric.compat.farmers.addon;

import cn.foggyhillside.ends_delight.registry.ModBlock;
import cn.foggyhillside.ends_delight.registry.ModItem;
import com.github.teamfossilsarcheology.fossil.food.FoodMappingsManager;
import vectorwing.farmersdelight.common.block.PieBlock;

import static com.github.teamfossilsarcheology.fossil.fabric.compat.farmers.FarmersDelightCompat.getPieValue;

public class EndDelightCompat {
    public static void registerFoodMappings(FoodMappingsManager manager) {
        manager.addPlant(ModItem.AssortedSalad.get());
        manager.addPlant(ModItem.ChorusCookie.get());
        manager.addPlant(ModItem.ChorusFruitPie.get(), getPieValue((PieBlock) ModBlock.ChorusFruitPie));
        manager.addPlant(ModItem.ChorusFlowerPie.get());
        manager.addPlant(ModItem.ChorusFruitPieSlice.get());
        manager.addPlant(ModItem.ChorusFruitGrain.get());
        manager.addPlant(ModItem.ChorusFruitPopsicle.get());
        manager.addPlant(ModItem.ChorusSucculent.get());
        manager.addPlant(ModItem.DragonBreathAndChorusSoup.get());
        manager.addMeat(ModItem.DragonLeg.get());
        manager.addMeat(ModItem.DragonLegWithSauce.get());
        manager.addMeat(ModItem.DragonMeatStew.get());
        manager.addMeat(ModItem.DriedEnderMiteMeat.get());
        manager.addMeat(ModItem.EndBarbecueStick.get());
        manager.addMeat(ModItem.EnderCongee.get());
        manager.addPlant(ModItem.ChorusSauce.get());
        manager.addPlant(ModItem.EndMixedSalad.get());
        manager.addEgg(ModItem.FriedDragonEgg.get());
        manager.addMeat(ModItem.GrilledShulker.get());
        manager.addEgg(ModItem.LiquidDragonEgg.get());
        manager.addMeat(ModItem.RawDragonMeat.get());
        manager.addMeat(ModItem.RawDragonMeatCuts.get());
        manager.addMeat(ModItem.RawEnderMiteMeat.get());
        manager.addMeat(ModItem.RoastedDragonMeat.get());
        manager.addMeat(ModItem.RoastedDragonMeatCuts.get());
        manager.addMeat(ModItem.RoastedDragonSteak.get());
        manager.addMeat(ModItem.RoastedShulkerMeat.get());
        manager.addMeat(ModItem.RoastedShulkerMeatSlice.get());
        manager.addMeat(ModItem.ShulkerMeat.get());
        manager.addMeat(ModItem.ShulkerMeatSlice.get());
        manager.addMeat(ModItem.SmokedDragonLeg.get());
        manager.addMeat(ModItem.StirFriedShulkerMeat.get());
        manager.addPlant(ModItem.StuffedRiceCake.get());
        manager.addPlant(ModItem.DriedChorusFlower.get(), 5);
        manager.addEgg(ModItem.SteamedDragonEgg.get());
        //33/33 added
    }
}
