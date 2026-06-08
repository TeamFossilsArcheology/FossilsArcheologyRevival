import untamedwilds.init.ModItems.java;
import com.github.teamfossilsarcheology.fossil.food.FoodMappingsManager;
import untamedwilds.init.ModBlock.java;
import untamedwilds.init.ModEntity.java;

public class UntamedWildsCompat {

    public static void register(FoodMappingsManager manager) {

        //Meat Foods
        manager.addMeat(ModItems.MEAT_BEAR_RAW.get());
        manager.addMeat(ModItems.MEAT_BEAR_COOKED.get());
        manager.addMeat(ModItems.MEAT_HIPPO_RAW.get());
        manager.addMeat(ModItems.MEAT_HIPPO_COOKED.get());
        manager.addMeat(ModItems.MEAT_TURTLE_RAW.get());
        manager.addMeat(ModItems.MEAT_TURTLE_COOKED.get());
        manager.addMeat(ModItems.FOOD_PEMMICAN.get());

        //Plant Foods
        manager.addPlant(ModItems.VEGETABLE_AARDVARK_CUCUMBER.get());
        manager.addPlant(ModItems.SEED_TITAN_ARUM.get());
        manager.addPlant(ModItems.SEED_ZIMBABWE_ALOE.get());
        manager.addPlant(ModItems.WATER_HYACINTH_BLOCK.get());
        manager.addPlant(ModBlock.COMMON_REED.get());
        manager.addPlant(ModBlock.BUSH_TEMPERATE.get());
        manager.addPlant(ModBlock.ZIMBABWE_ALOE.get());
        manager.addPlant(ModBlock.BUSH_CREOSOTE.get());
        manager.addPlant(ModBlock.ELEPHANT_EAR.get());
        manager.addPlant(ModBlock.YARROW.get());
        manager.addPlant(ModBlock.JUNEGRASS.get());
        manager.addPlant(ModBlock.CANOLA.get());
        manager.addPlant(ModBlock.AMAZON_SWORD.get());
        manager.addPlant(ModBlock.EELGRASS.get());
        manager.addPlant(ModBlock.ORCHID_MAGENTA.get());
        manager.addPlant(ModBlock.TITAN_ARUM.get());
        manager.addPlant(ModBlock.ORCHID_PURPLE.get());
        manager.addPlant(ModBlock.ORCHID_RED.get());
        manager.addPlant(ModBlock.ORCHID_PINK.get());

        //Fish Foods
        manager.addFish(ModItems.CHUM.get());


        //Meat Entities
        manager.addMeatEntity(ModEntity.TARANTULA.get(), 2);
        manager.addMeatEntity(ModEntity.BUTTERFLY.get(), 1);
        manager.addMeatEntity(ModEntity.SNAKE.get(), 5);
        manager.addMeatEntity(ModEntity.SOFTSHELL_TURTLE.get(), 6);
        manager.addMeatEntity(ModEntity.TORTOISE.get(), 7);
        manager.addMeatEntity(ModEntity.ANACONDA.get(), 14);
        manager.addMeatEntity(ModEntity.MONITOR.get(), 10);
        manager.addMeatEntity(ModEntity.BEAR.get(), 29);
        manager.addMeatEntity(ModEntity.BIG_CAT.get(), 17);
        manager.addMeatEntity(ModEntity.HIPPO.get(), 35);
        manager.addMeatEntity(ModEntity.AARDVARK.get(), 8);
        manager.addMeatEntity(ModEntity.RHINO.get(), 35);
        manager.addMeatEntity(ModEntity.HYENA.get(), 13);
        manager.addMeatEntity(ModEntity.BOAR.get(), 12);
        manager.addMeatEntity(ModEntity.BISON.get(), 35);
        manager.addMeatEntity(ModEntity.CAMEL.get(), 21);
        manager.addMeatEntity(ModEntity.OPOSSUM.get(), 5);
        manager.addMeatEntity(ModEntity.WILDEBEEST.get(), 15);
        manager.addMeatEntity(ModEntity.TERROR_BIRD.get(), 15);
        manager.addMeatEntity(ModEntity.SPITTER.get(), 18);

        //Fish Entities
        manager.addFishEntity(ModEntity.KING_CRAB.get(), 7);
        manager.addFishEntity(ModEntity.GIANT_CLAM.get(), 10);
        manager.addFishEntity(ModEntity.GIANT_SNAIL.get(), 4);
        manager.addFishEntity(ModEntity.MANATEE.get(), 25);
        manager.addFishEntity(ModEntity.BALEEN_WHALE.get(), 100);
        manager.addFishEntity(ModEntity.SUNFISH.get(), 18);
        manager.addFishEntity(ModEntity.TREVALLY.get(), 2);
        manager.addFishEntity(ModEntity.AROWANA.get(), 4);
        manager.addFishEntity(ModEntity.SHARK.get(), 21);
        manager.addFishEntity(ModEntity.FOOTBALL_FISH.get(), 2);
        manager.addFishEntity(ModEntity.WHALE_SHARK.get(), 80);
        manager.addFishEntity(ModEntity.TRIGGERFISH.get(), 2);
        manager.addFishEntity(ModEntity.CATFISH.get(), 7);
        manager.addFishEntity(ModEntity.SPADEFISH.get(), 3);
        manager.addFishEntity(ModEntity.SAWFISH.get(), 15);
        manager.addFishEntity(ModEntity.GIANT_SALAMANDER.get(), 6);
        manager.addFishEntity(ModEntity.NEWT.get(), 2);
    }
}

