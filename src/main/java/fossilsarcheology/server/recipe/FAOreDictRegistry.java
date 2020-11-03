package fossilsarcheology.server.recipe;

import fossilsarcheology.server.block.EnumFossilPlant;
import fossilsarcheology.server.block.FABlockRegistry;
import fossilsarcheology.server.entity.prehistoric.MobType;
import fossilsarcheology.server.entity.prehistoric.PrehistoricEntityType;
import fossilsarcheology.server.item.FAItemRegistry;
import fossilsarcheology.server.item.FossilSeedsItem;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

public class FAOreDictRegistry {

    public static void register() {
        OreDictionary.registerOre("blockWool", new ItemStack(Blocks.WOOL, 1, OreDictionary.WILDCARD_VALUE));

        OreDictionary.registerOre("plankWood", new ItemStack(FABlockRegistry.ANCIENT_WOOD));
        OreDictionary.registerOre("stairWood", new ItemStack(FABlockRegistry.ANCIENT_WOOD_STAIRS));
        OreDictionary.registerOre("plankWood", new ItemStack(FABlockRegistry.ANCIENT_WOOD));
        OreDictionary.registerOre("stairWood", new ItemStack(FABlockRegistry.ANCIENT_WOOD_STAIRS));

        OreDictionary.registerOre("sapling", new ItemStack(FABlockRegistry.PALM_SAPLING));
        OreDictionary.registerOre("plankWood", new ItemStack(FABlockRegistry.PALM_PLANKS));
        OreDictionary.registerOre("logWood", new ItemStack(FABlockRegistry.PALM_LOG));
        OreDictionary.registerOre("treeLeaves", new ItemStack(FABlockRegistry.PALM_LEAVES));
        OreDictionary.registerOre("slabWood", new ItemStack(FABlockRegistry.PALM_PLANKS_SINGLESLAB));
        OreDictionary.registerOre("stairWood", new ItemStack(FABlockRegistry.PALM_PLANKS_STAIRS));
        OreDictionary.registerOre("doorWood", new ItemStack(FAItemRegistry.PALM_DOOR_ITEM));
        OreDictionary.registerOre("trapdoorWood", new ItemStack(FABlockRegistry.PALM_TRAPDOOR));
        OreDictionary.registerOre("fenceWood", new ItemStack(FABlockRegistry.PALM_FENCE));
        OreDictionary.registerOre("fenceGateWood", new ItemStack(FABlockRegistry.PALM_FENCE_GATE));
        OreDictionary.registerOre("treeSapling", new ItemStack(FABlockRegistry.PALM_SAPLING));
        OreDictionary.registerOre("saplingTree", new ItemStack(FABlockRegistry.PALM_SAPLING));

        OreDictionary.registerOre("plankWood", new ItemStack(FABlockRegistry.CALAMITES_PLANKS));
        OreDictionary.registerOre("logWood", new ItemStack(FABlockRegistry.CALAMITES_LOG));
        OreDictionary.registerOre("treeLeaves", new ItemStack(FABlockRegistry.CALAMITES_LEAVES));
        OreDictionary.registerOre("slabWood", new ItemStack(FABlockRegistry.CALAMITES_PLANKS_SINGLESLAB));
        OreDictionary.registerOre("stairWood", new ItemStack(FABlockRegistry.CALAMITES_PLANKS_STAIRS));
        OreDictionary.registerOre("doorWood", new ItemStack(FAItemRegistry.CALAMITES_DOOR_ITEM));
        OreDictionary.registerOre("trapdoorWood", new ItemStack(FABlockRegistry.CALAMITES_TRAPDOOR));
        OreDictionary.registerOre("fenceWood", new ItemStack(FABlockRegistry.CALAMITES_FENCE));
        OreDictionary.registerOre("fenceGateWood", new ItemStack(FABlockRegistry.CALAMITES_FENCE_GATE));
        OreDictionary.registerOre("treeSapling", new ItemStack(FABlockRegistry.CALAMITES_SAPLING));
        OreDictionary.registerOre("saplingTree", new ItemStack(FABlockRegistry.CALAMITES_SAPLING));
        OreDictionary.registerOre("sapling", new ItemStack(FABlockRegistry.CALAMITES_SAPLING));

        OreDictionary.registerOre("plankWood", new ItemStack(FABlockRegistry.CORDAITES_PLANKS));
        OreDictionary.registerOre("logWood", new ItemStack(FABlockRegistry.CORDAITES_LOG));
        OreDictionary.registerOre("treeLeaves", new ItemStack(FABlockRegistry.CORDAITES_LEAVES));
        OreDictionary.registerOre("slabWood", new ItemStack(FABlockRegistry.CORDAITES_PLANKS_SINGLESLAB));
        OreDictionary.registerOre("stairWood", new ItemStack(FABlockRegistry.CORDAITES_PLANKS_STAIRS));
        OreDictionary.registerOre("doorWood", new ItemStack(FAItemRegistry.CORDAITES_DOOR_ITEM));
        OreDictionary.registerOre("trapdoorWood", new ItemStack(FABlockRegistry.CORDAITES_TRAPDOOR));
        OreDictionary.registerOre("fenceWood", new ItemStack(FABlockRegistry.CORDAITES_FENCE));
        OreDictionary.registerOre("fenceGateWood", new ItemStack(FABlockRegistry.CORDAITES_FENCE_GATE));
        OreDictionary.registerOre("treeSapling", new ItemStack(FABlockRegistry.CORDAITES_SAPLING));
        OreDictionary.registerOre("saplingTree", new ItemStack(FABlockRegistry.CORDAITES_SAPLING));
        OreDictionary.registerOre("sapling", new ItemStack(FABlockRegistry.CORDAITES_SAPLING));

        OreDictionary.registerOre("plankWood", new ItemStack(FABlockRegistry.SIGILLARIA_PLANKS));
        OreDictionary.registerOre("logWood", new ItemStack(FABlockRegistry.SIGILLARIA_LOG));
        OreDictionary.registerOre("treeLeaves", new ItemStack(FABlockRegistry.SIGILLARIA_LEAVES));
        OreDictionary.registerOre("slabWood", new ItemStack(FABlockRegistry.SIGILLARIA_PLANKS_SINGLESLAB));
        OreDictionary.registerOre("stairWood", new ItemStack(FABlockRegistry.SIGILLARIA_PLANKS_STAIRS));
        OreDictionary.registerOre("doorWood", new ItemStack(FAItemRegistry.SIGILLARIA_DOOR_ITEM));
        OreDictionary.registerOre("trapdoorWood", new ItemStack(FABlockRegistry.SIGILLARIA_TRAPDOOR));
        OreDictionary.registerOre("fenceWood", new ItemStack(FABlockRegistry.SIGILLARIA_FENCE));
        OreDictionary.registerOre("fenceGateWood", new ItemStack(FABlockRegistry.SIGILLARIA_FENCE_GATE));
        OreDictionary.registerOre("treeSapling", new ItemStack(FABlockRegistry.SIGILLARIA_SAPLING));
        OreDictionary.registerOre("saplingTree", new ItemStack(FABlockRegistry.SIGILLARIA_SAPLING));
        OreDictionary.registerOre("sapling", new ItemStack(FABlockRegistry.SIGILLARIA_SAPLING));

        OreDictionary.registerOre("oreFossil", new ItemStack(FABlockRegistry.FOSSIL));
        OreDictionary.registerOre("oreAmber", new ItemStack(FABlockRegistry.AMBER_ORE));
        OreDictionary.registerOre("stoneBasalt", new ItemStack(FABlockRegistry.VOLCANIC_ROCK));
        OreDictionary.registerOre("stoneBasaltBricks", new ItemStack(FABlockRegistry.VOLCANIC_BRICK));
        OreDictionary.registerOre("blockGlass", new ItemStack(FABlockRegistry.STRONG_GLASS));
        OreDictionary.registerOre("glass", new ItemStack(FABlockRegistry.STRONG_GLASS));
        OreDictionary.registerOre("blockGlassColorless", new ItemStack(FABlockRegistry.STRONG_GLASS));
        OreDictionary.registerOre("blockGlass", new ItemStack(FABlockRegistry.ANCIENT_GLASS));
        OreDictionary.registerOre("glass", new ItemStack(FABlockRegistry.ANCIENT_GLASS));
        OreDictionary.registerOre("dustAsh", new ItemStack(FABlockRegistry.VOLCANIC_ASH));
        OreDictionary.registerOre("blockAsh", new ItemStack(FABlockRegistry.VOLCANIC_ASH));
        OreDictionary.registerOre("blockTar", new ItemStack(FABlockRegistry.TAR));
        OreDictionary.registerOre("skull", new ItemStack(FABlockRegistry.SKULL_BLOCK));
        OreDictionary.registerOre("denseSand", new ItemStack(FABlockRegistry.DENSE_SAND));
        OreDictionary.registerOre("skullLantern", new ItemStack(FABlockRegistry.SKULL_LANTERN));
        OreDictionary.registerOre("stoneAncient", new ItemStack(FABlockRegistry.ANCIENT_STONE));
        OreDictionary.registerOre("stoneAncientBrick", new ItemStack(FABlockRegistry.ANCIENT_STONE_BRICK));
        OreDictionary.registerOre("stone", new ItemStack(FABlockRegistry.ANCIENT_STONE));
        OreDictionary.registerOre("stoneBrick", new ItemStack(FABlockRegistry.ANCIENT_STONE_BRICK));

        OreDictionary.registerOre("artifact", new ItemStack(FAItemRegistry.RELIC_SCRAP));
        OreDictionary.registerOre("fossil", new ItemStack(FAItemRegistry.BIOFOSSIL));
        OreDictionary.registerOre("gemScarab", new ItemStack(FAItemRegistry.SCARAB_GEM));
        OreDictionary.registerOre("gemScarabBlue", new ItemStack(FAItemRegistry.AQUATIC_SCARAB_GEM));
        OreDictionary.registerOre("gemAmber", new ItemStack(FAItemRegistry.AMBER));
        OreDictionary.registerOre("gemDominicanAmber", new ItemStack(FAItemRegistry.DOMINICAN_AMBER));
        OreDictionary.registerOre("dinosaurClaw", new ItemStack(FAItemRegistry.UNIQUE_ITEM, 1, 1));
        OreDictionary.registerOre("dinosaurClaw", new ItemStack(FAItemRegistry.UNIQUE_ITEM, 1, 8));
        OreDictionary.registerOre("dinosaurClaw", new ItemStack(FAItemRegistry.UNIQUE_ITEM, 1, 11));
        OreDictionary.registerOre("dinosaurClaw", new ItemStack(FAItemRegistry.UNIQUE_ITEM, 1, 14));
        OreDictionary.registerOre("dinosaurClaw", new ItemStack(FAItemRegistry.UNIQUE_ITEM, 1, 15));
        for (int i = 0; i < EnumFossilPlant.values().length; i++) {
            OreDictionary.registerOre("listAllseed", new ItemStack(FAItemRegistry.SEED, 1, i));
            OreDictionary.registerOre("seeds", new ItemStack(FAItemRegistry.SEED, 1, i));
            OreDictionary.registerOre("flowerSeed", new ItemStack(FAItemRegistry.SEED, 1, i));
        }
        OreDictionary.registerOre("listAllseed", new ItemStack(FAItemRegistry.FERN_SEED));
        OreDictionary.registerOre("seeds", new ItemStack(FAItemRegistry.FERN_SEED));
        OreDictionary.registerOre("sporeFern", new ItemStack(FAItemRegistry.FERN_SEED));
        OreDictionary.registerOre("materialRotten", new ItemStack(FAItemRegistry.FAILURESAURUS_FLESH));
        OreDictionary.registerOre("slimeball", new ItemStack(FAItemRegistry.FAILURESAURUS_FLESH));
        OreDictionary.registerOre("tar", new ItemStack(FAItemRegistry.TARDROP));
        OreDictionary.registerOre("slimeball", new ItemStack(FAItemRegistry.TARDROP));
        //OreDictionary.registerOre("tarBucket", new ItemStack(FAItemRegistry.TAR_BUCKET));
        //OreDictionary.registerOre("bucketTar", new ItemStack(FAItemRegistry.TAR_BUCKET));
        OreDictionary.registerOre("dinosaurSkull", new ItemStack(FAItemRegistry.SKULL, 1, OreDictionary.WILDCARD_VALUE));
        OreDictionary.registerOre("dinosaurLegBone", new ItemStack(FAItemRegistry.LEG_BONE, 1, OreDictionary.WILDCARD_VALUE));
        OreDictionary.registerOre("dinosaurFoot", new ItemStack(FAItemRegistry.FOOT, 1, OreDictionary.WILDCARD_VALUE));
        OreDictionary.registerOre("dinosaurArmBone", new ItemStack(FAItemRegistry.ARM_BONE, 1, OreDictionary.WILDCARD_VALUE));
        OreDictionary.registerOre("dinosaurVertebrae", new ItemStack(FAItemRegistry.VERTEBRAE, 1, OreDictionary.WILDCARD_VALUE));
        OreDictionary.registerOre("dinosaurRibCage", new ItemStack(FAItemRegistry.RIBCAGE, 1, OreDictionary.WILDCARD_VALUE));

        OreDictionary.registerOre("record", new ItemStack(FAItemRegistry.FOSSIL_RECORD_BONES));
        OreDictionary.registerOre("record", new ItemStack(FAItemRegistry.FOSSIL_RECORD_NANO_ANU));
        OreDictionary.registerOre("record", new ItemStack(FAItemRegistry.FOSSIL_RECORD_NANO_DISCOVERING));
        OreDictionary.registerOre("record", new ItemStack(FAItemRegistry.FOSSIL_RECORD_NANO_SCARAB));

        for (PrehistoricEntityType prehistoric : PrehistoricEntityType.values()) {
            if (prehistoric.eggItem != null) {
                OreDictionary.registerOre("listAllegg", prehistoric.eggItem);
                OreDictionary.registerOre("objectEgg", prehistoric.eggItem);
                OreDictionary.registerOre("bakingEgg", prehistoric.eggItem);
                OreDictionary.registerOre("egg", prehistoric.eggItem);
                OreDictionary.registerOre("eggCookable", prehistoric.eggItem);
                OreDictionary.registerOre("ingredientEgg", prehistoric.eggItem);
                OreDictionary.registerOre("foodSimpleEgg", prehistoric.eggItem);
                if (prehistoric.mobType == MobType.FISH) {
                    OreDictionary.registerOre("foodRoe", prehistoric.eggItem);
                    OreDictionary.registerOre("foodCaviar", prehistoric.eggItem);
                }
            }
            if (prehistoric.birdEggItem != null ) {
                OreDictionary.registerOre("listAllegg", prehistoric.birdEggItem);
                OreDictionary.registerOre("objectEgg", prehistoric.birdEggItem);
                OreDictionary.registerOre("bakingEgg", prehistoric.birdEggItem);
                OreDictionary.registerOre("egg", prehistoric.birdEggItem);
                OreDictionary.registerOre("eggCookable", prehistoric.birdEggItem);
                OreDictionary.registerOre("ingredientEgg", prehistoric.birdEggItem);
                OreDictionary.registerOre("foodSimpleEgg", prehistoric.birdEggItem);
            }
            if (prehistoric.bestBirdEggItem != null) {
                OreDictionary.registerOre("listAllegg", prehistoric.bestBirdEggItem);
                OreDictionary.registerOre("objectEgg", prehistoric.bestBirdEggItem);
                OreDictionary.registerOre("bakingEgg", prehistoric.bestBirdEggItem);
                OreDictionary.registerOre("egg", prehistoric.bestBirdEggItem);
                OreDictionary.registerOre("eggCookable", prehistoric.bestBirdEggItem);
                OreDictionary.registerOre("ingredientEgg", prehistoric.bestBirdEggItem);
                OreDictionary.registerOre("foodSimpleEgg", prehistoric.bestBirdEggItem);
            }
            if (prehistoric.foodItem != null) {
                OreDictionary.registerOre("listAllmeatraw", prehistoric.foodItem);
                OreDictionary.registerOre("foodMeat", prehistoric.foodItem);
                OreDictionary.registerOre("meatRaw", prehistoric.foodItem);
                OreDictionary.registerOre("listAllMeat", prehistoric.foodItem);
                OreDictionary.registerOre("allFlesh", prehistoric.foodItem);
            }
            if (prehistoric.cookedFoodItem != null) {
                OreDictionary.registerOre("listAllmeatcooked", prehistoric.cookedFoodItem);
                OreDictionary.registerOre("foodMeat", prehistoric.cookedFoodItem);
                OreDictionary.registerOre("foodCooked", prehistoric.cookedFoodItem);
            }
        }
        OreDictionary.registerOre("foodCalamariraw", PrehistoricEntityType.NAUTILUS.fishItem);
        OreDictionary.registerOre("foodCalamaricooked", FAItemRegistry.SJL);
    }
}
