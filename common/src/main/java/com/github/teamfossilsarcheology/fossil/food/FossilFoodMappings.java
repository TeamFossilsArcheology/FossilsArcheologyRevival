package com.github.teamfossilsarcheology.fossil.food;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricMobType;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.VanillaEntityInfo;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.tags.BlockTags;

public class FossilFoodMappings {
    public static void register() {
        FoodMappingsManager.INSTANCE.listen(manager -> {
            for (PrehistoricEntityInfo info : PrehistoricEntityInfo.values()) {
                if (info.mobType == PrehistoricMobType.FISH) {
                    if (info.eggItem != null) manager.addFish(info.eggItem, 20);
                    if (info.foodItem != null) manager.addFish(info.foodItem);
                    if (info.cookedFoodItem != null) manager.addFish(info.cookedFoodItem);
                } else {
                    if (info.foodItem != null) manager.addMeat(info.foodItem);
                    if (info.cookedFoodItem != null) manager.addMeat(info.cookedFoodItem);
                }
                if (info.mobType == PrehistoricMobType.BIRD) {
                    manager.addEgg(info.cultivatedBirdEggItem, 15);
                    manager.addEgg(info.birdEggItem, 10);
                }
            }
            for (VanillaEntityInfo info : VanillaEntityInfo.values()) {
                if (info.mobType == PrehistoricMobType.VANILLA_BIRD) {
                    manager.addEgg(info.cultivatedBirdEggItem, 15);
                }
            }
        });
    }
}
