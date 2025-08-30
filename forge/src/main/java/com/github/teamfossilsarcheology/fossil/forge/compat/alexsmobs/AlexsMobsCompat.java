package com.github.teamfossilsarcheology.fossil.forge.compat.alexsmobs;

import com.github.alexthe666.alexsmobs.entity.AMEntityRegistry;
import com.github.alexthe666.alexsmobs.item.AMItemRegistry;
import com.github.teamfossilsarcheology.fossil.food.FoodMappingsManager;

public class AlexsMobsCompat {
	public static void register(FoodMappingsManager manager) {
		manager.addMeat(AMItemRegistry.MAGGOT.get());
		manager.addMeat(AMItemRegistry.MOOSE_RIBS.get());
		manager.addMeat(AMItemRegistry.COOKED_MOOSE_RIBS.get());
		manager.addMeat(AMItemRegistry.KANGAROO_MEAT.get());
		manager.addMeat(AMItemRegistry.COOKED_KANGAROO_MEAT.get());

		manager.addFish(AMItemRegistry.LOBSTER_TAIL.get());
		manager.addFish(AMItemRegistry.COOKED_LOBSTER_TAIL.get());
		manager.addFish(AMItemRegistry.BLOBFISH.get());
		manager.addFish(AMItemRegistry.COSMIC_COD.get());
		manager.addFish(AMItemRegistry.RAINBOW_JELLY.get());
		manager.addFish(AMItemRegistry.RAW_CATFISH.get());
		manager.addFish(AMItemRegistry.COOKED_CATFISH.get());
		manager.addFish(AMItemRegistry.FLYING_FISH.get());

		manager.addPlant(AMItemRegistry.BANANA.get());

		manager.addMeat(AMEntityRegistry.ANTEATER.get(), 10);
		manager.addMeat(AMEntityRegistry.BALD_EAGLE.get(), 8);
		manager.addMeat(AMEntityRegistry.BISON.get(), 20);
		manager.addMeat(AMEntityRegistry.CAPUCHIN_MONKEY.get(), 5);
		manager.addMeat(AMEntityRegistry.CROW.get(), 4);
		manager.addMeat(AMEntityRegistry.DROPBEAR.get(), 11);
		manager.addMeat(AMEntityRegistry.ELEPHANT.get(), 33);
		manager.addMeat(AMEntityRegistry.EMU.get(), 10);
		manager.addMeat(AMEntityRegistry.GAZELLE.get(), 4);
		manager.addMeat(AMEntityRegistry.GELADA_MONKEY.get(), 9);
		manager.addMeat(AMEntityRegistry.GORILLA.get(), 25);
		manager.addMeat(AMEntityRegistry.GRIZZLY_BEAR.get(), 28);
		manager.addMeat(AMEntityRegistry.HUMMINGBIRD.get(), 2);
		manager.addMeat(AMEntityRegistry.JERBOA.get(), 2);
		manager.addMeat(AMEntityRegistry.KANGAROO.get(), 11);
		manager.addMeat(AMEntityRegistry.KOMODO_DRAGON.get(), 15);
		manager.addMeat(AMEntityRegistry.MANED_WOLF.get(), 8);
		manager.addMeat(AMEntityRegistry.MOOSE.get(), 28);
		manager.addMeat(AMEntityRegistry.RACCOON.get(), 5);
		manager.addMeat(AMEntityRegistry.RATTLESNAKE.get(), 4);
		manager.addMeat(AMEntityRegistry.ROADRUNNER.get(), 4);
		manager.addMeat(AMEntityRegistry.SEAGULL.get(), 4);
		manager.addMeat(AMEntityRegistry.SHOEBILL.get(), 5);
		manager.addMeat(AMEntityRegistry.SNOW_LEOPARD.get(), 15);
		manager.addMeat(AMEntityRegistry.SUNBIRD.get(), 10);
		manager.addMeat(AMEntityRegistry.TASMANIAN_DEVIL.get(), 7);
		manager.addMeat(AMEntityRegistry.TIGER.get(), 25);
		manager.addMeat(AMEntityRegistry.TOUCAN.get(), 3);
		manager.addMeat(AMEntityRegistry.TUSKLIN.get(), 20);
		manager.addMeat(AMEntityRegistry.PLATYPUS.get(), 5);
		manager.addMeat(AMEntityRegistry.COSMAW.get(), 10);
		manager.addMeat(AMEntityRegistry.WARPED_TOAD.get(), 15);
		manager.addMeat(AMEntityRegistry.ENDERGRADE.get(), 10);
		manager.addMeat(AMEntityRegistry.MUNGUS.get(), 8);
		manager.addMeat(AMEntityRegistry.BUNFUNGUS.get(), 40);
		manager.addMeat(AMEntityRegistry.FROSTSTALKER.get(), 12);

		manager.addFish(AMEntityRegistry.ALLIGATOR_SNAPPING_TURTLE.get(), 9);
		manager.addFish(AMEntityRegistry.BLOBFISH.get(), 4);
		manager.addFish(AMEntityRegistry.CACHALOT_WHALE.get(), 80);
		manager.addFish(AMEntityRegistry.CATFISH.get(), 5);
		manager.addFish(AMEntityRegistry.COMB_JELLY.get(), 3);
		manager.addFish(AMEntityRegistry.COSMIC_COD.get(), 2);
		manager.addFish(AMEntityRegistry.DEVILS_HOLE_PUPFISH.get(), 1);
		manager.addFish(AMEntityRegistry.FLYING_FISH.get(), 3);
		manager.addFish(AMEntityRegistry.FRILLED_SHARK.get(), 10);
		manager.addFish(AMEntityRegistry.GIANT_SQUID.get(), 19);
		manager.addFish(AMEntityRegistry.HAMMERHEAD_SHARK.get(), 15);
		manager.addFish(AMEntityRegistry.LOBSTER.get(), 3);
		manager.addFish(AMEntityRegistry.MANTIS_SHRIMP.get(), 10);
		manager.addFish(AMEntityRegistry.MIMIC_OCTOPUS.get(), 8);
		manager.addFish(AMEntityRegistry.ORCA.get(), 30);
		manager.addFish(AMEntityRegistry.SEA_BEAR.get(), 100);
		manager.addFish(AMEntityRegistry.SEAL.get(), 5);
		manager.addFish(AMEntityRegistry.TERRAPIN.get(), 7);
		manager.addFish(AMEntityRegistry.ANACONDA.get(), 5);
		manager.addFish(AMEntityRegistry.CROCODILE.get(), 15);

		manager.addMeat(AMEntityRegistry.COCKROACH.get(), 9);
		manager.addMeat(AMEntityRegistry.CRIMSON_MOSQUITO.get(), 5);
		manager.addMeat(AMEntityRegistry.CENTIPEDE_HEAD.get(), 18);
		manager.addMeat(AMEntityRegistry.TARANTULA_HAWK.get(), 9);
		manager.addMeat(AMEntityRegistry.WARPED_MOSCO.get(), 50);
	}
}
