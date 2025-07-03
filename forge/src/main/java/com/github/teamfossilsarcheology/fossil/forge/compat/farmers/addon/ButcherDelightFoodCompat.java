package com.github.teamfossilsarcheology.fossil.forge.compat.farmers.addon;

import com.github.teamfossilsarcheology.fossil.util.FoodMappings;
import net.mcreator.butchersdelightfoods.init.ButchersdelightfoodsModItems;
import net.minecraft.world.item.Item;
import net.minecraftforge.registries.RegistryObject;

public class ButcherDelightFoodCompat {
    public static void registerButcherDelightFoodFoodMappings(){
        for(RegistryObject<Item> meat: ButchersdelightfoodsModItems.REGISTRY.getEntries()){
            FoodMappings.addMeat(meat.get());
        }
        //This is safe to do because literally all Butcher Delight Foods items are raw and cooked meat
    }
}
