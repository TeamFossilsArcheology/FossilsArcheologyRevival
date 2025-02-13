package com.github.teamfossilsarcheology.fossil.forge.compat.alexsmobs;

import net.minecraft.resources.ResourceLocation;

import com.github.teamfossilsarcheology.fossil.util.FoodMappings;
import com.github.alexthe666.alexsmobs.item.AMItemRegistry;
import com.github.alexthe666.alexsmobs.entity.AMEntityRegistry;

public class AlexsMobsCompat {
	public static void register() {
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

		FoodMappings.addMeat(AMEntityRegistry.ANTEATER.get(), 10);
		FoodMappings.addMeat(AMEntityRegistry.BALD_EAGLE.get(), 8);
		FoodMappings.addMeat(AMEntityRegistry.BISON.get(), 20);
		FoodMappings.addMeat(AMEntityRegistry.CAPUCHIN_MONKEY.get(), 5);
		FoodMappings.addMeat(AMEntityRegistry.CROW.get(), 4);
		FoodMappings.addMeat(AMEntityRegistry.DROPBEAR.get(), 11);
		FoodMappings.addMeat(AMEntityRegistry.ELEPHANT.get(), 33);
		FoodMappings.addMeat(AMEntityRegistry.EMU.get(), 10);
		FoodMappings.addMeat(AMEntityRegistry.GAZELLE.get(), 4);
		FoodMappings.addMeat(AMEntityRegistry.GELADA_MONKEY.get(), 9);
		FoodMappings.addMeat(AMEntityRegistry.GORILLA.get(), 25);
		FoodMappings.addMeat(AMEntityRegistry.GRIZZLY_BEAR.get(), 28);
		FoodMappings.addMeat(AMEntityRegistry.HUMMINGBIRD.get(), 2);
		FoodMappings.addMeat(AMEntityRegistry.JERBOA.get(), 2);
		FoodMappings.addMeat(AMEntityRegistry.KANGAROO.get(), 11);
		FoodMappings.addMeat(AMEntityRegistry.KOMODO_DRAGON.get(), 15);
		FoodMappings.addMeat(AMEntityRegistry.MANED_WOLF.get(), 8);
		FoodMappings.addMeat(AMEntityRegistry.MOOSE.get(), 28);
		FoodMappings.addMeat(AMEntityRegistry.RACCOON.get(), 5);
		FoodMappings.addMeat(AMEntityRegistry.RATTLESNAKE.get(), 4);
		FoodMappings.addMeat(AMEntityRegistry.ROADRUNNER.get(), 4);
		FoodMappings.addMeat(AMEntityRegistry.SEAGULL.get(), 4);
		FoodMappings.addMeat(AMEntityRegistry.SHOEBILL.get(), 5);
		FoodMappings.addMeat(AMEntityRegistry.SNOW_LEOPARD.get(), 15);
		FoodMappings.addMeat(AMEntityRegistry.SUNBIRD.get(), 10);
		FoodMappings.addMeat(AMEntityRegistry.TASMANIAN_DEVIL.get(), 7);
		FoodMappings.addMeat(AMEntityRegistry.TIGER.get(), 25);
		FoodMappings.addMeat(AMEntityRegistry.TOUCAN.get(), 3);
		FoodMappings.addMeat(AMEntityRegistry.TUSKLIN.get(), 20);
		FoodMappings.addMeat(AMEntityRegistry.PLATYPUS.get(), 5);
		FoodMappings.addMeat(AMEntityRegistry.COSMAW.get(), 10);
		FoodMappings.addMeat(AMEntityRegistry.WARPED_TOAD.get(), 15);
		FoodMappings.addMeat(AMEntityRegistry.ENDERGRADE.get(), 10);
		FoodMappings.addMeat(AMEntityRegistry.MUNGUS.get(), 8);
		FoodMappings.addMeat(AMEntityRegistry.BUNFUNGUS.get(), 40);

		FoodMappings.addFish(AMEntityRegistry.ALLIGATOR_SNAPPING_TURTLE.get(), 9);
		FoodMappings.addFish(AMEntityRegistry.BLOBFISH.get(), 4);
		FoodMappings.addFish(AMEntityRegistry.CACHALOT_WHALE.get(), 80);
		FoodMappings.addFish(AMEntityRegistry.CATFISH.get(), 5);
		FoodMappings.addFish(AMEntityRegistry.COMB_JELLY.get(), 3);
		FoodMappings.addFish(AMEntityRegistry.COSMIC_COD.get(), 2);
		FoodMappings.addFish(AMEntityRegistry.DEVILS_HOLE_PUPFISH.get(), 1);
		FoodMappings.addFish(AMEntityRegistry.FLYING_FISH.get(), 3);
		FoodMappings.addFish(AMEntityRegistry.FRILLED_SHARK.get(), 10);
		FoodMappings.addFish(AMEntityRegistry.GIANT_SQUID.get(), 19);
		FoodMappings.addFish(AMEntityRegistry.HAMMERHEAD_SHARK.get(), 15);
		FoodMappings.addFish(AMEntityRegistry.LOBSTER.get(), 3);
		FoodMappings.addFish(AMEntityRegistry.MANTIS_SHRIMP.get(), 10);
		FoodMappings.addFish(AMEntityRegistry.MIMIC_OCTOPUS.get(), 8);
		FoodMappings.addFish(AMEntityRegistry.ORCA.get(), 30);
		FoodMappings.addFish(AMEntityRegistry.SEA_BEAR.get(), 100);
		FoodMappings.addFish(AMEntityRegistry.SEAL.get(), 5);
		FoodMappings.addFish(AMEntityRegistry.TERRAPIN.get(), 7);
		FoodMappings.addFish(AMEntityRegistry.ANACONDA.get(), 5);
		FoodMappings.addFish(AMEntityRegistry.CROCODILE.get(), 15);

		FoodMappings.addInsect(AMEntityRegistry.COCKROACH.get(), 9);
		FoodMappings.addInsect(AMEntityRegistry.CRIMSON_MOSQUITO.get(), 5);
		FoodMappings.addInsect(AMEntityRegistry.FLY.get(), 1);
		FoodMappings.addInsect(AMEntityRegistry.LEAFCUTTER_ANT.get(), 3);
		FoodMappings.addInsect(AMEntityRegistry.CENTIPEDE_HEAD.get(), 18);
		FoodMappings.addInsect(AMEntityRegistry.TARANTULA_HAWK.get(), 9);
		FoodMappings.addInsect(AMEntityRegistry.WARPED_MOSCO.get(), 50);
	}
}
