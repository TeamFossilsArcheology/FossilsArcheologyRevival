package com.github.teamfossilsarcheology.fossil.forge.compat.alexsmobs;

import com.github.teamfossilsarcheology.fossil.util.FoodMappings;
import com.github.alexthe666.alexsmobs.item.AMItemRegistry;

public class AlexsMobsCompat {
	public static void registerFoodMappings() {
		FoodMappings.addMeat(AMItemRegistry.MAGGOT.get());
		FoodMappings.addMeat(AMItemRegistry.MOOSE_RIBS.get());
		FoodMappings.addMeat(AMItemRegistry.COOKED_MOOSE_RIBS.get());
		FoodMappings.addMeat(AMItemRegistry.KANGAROO_MEAT.get());
		FoodMappings.addMeat(AMItemRegistry.COOKED_KANGAROO_MEAT.get());

		FoodMappings.addFish(AMItemRegistry.LOBSTER_TAIL.get());
		FoodMappings.addFish(AMItemRegistry.COOKED_LOBSTER_TAIL.get());
		FoodMappings.addFish(AMItemRegistry.BLOBFISH.get());
		FoodMappings.addFish(AMItemRegistry.COSMIC_COD.get());
		FoodMappings.addFish(AMItemRegistry.RAINBOW_JELLY.get());
		FoodMappings.addFish(AMItemRegistry.RAW_CATFISH.get());
		FoodMappings.addFish(AMItemRegistry.COOKED_CATFISH.get());
		FoodMappings.addFish(AMItemRegistry.FLYING_FISH.get());

        FoodMappings.addPlant(AMItemRegistry.BANANA.get());
	}
}
