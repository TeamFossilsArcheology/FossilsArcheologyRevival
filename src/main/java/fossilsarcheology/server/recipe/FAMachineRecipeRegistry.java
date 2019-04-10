package fossilsarcheology.server.recipe;

import fossilsarcheology.server.block.FABlockRegistry;
import fossilsarcheology.server.entity.prehistoric.PrehistoricEntityType;
import fossilsarcheology.server.entity.prehistoric.TimePeriod;
import fossilsarcheology.server.item.FAItemRegistry;
import fossilsarcheology.server.item.FossilSeedsItem;
import fossilsarcheology.server.item.variant.DinosaurBoneType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.List;

public class FAMachineRecipeRegistry {
    public static List<RecipeAnalyzer> analyzerRecipes = new ArrayList<>();

    public static void init() {
        RecipeAnalyzer plantFossil = new RecipeAnalyzer(FAItemRegistry.PLANT_FOSSIL)
                .addOutput(new ItemStack(Blocks.SAND, 2), 35F)
                .addOutput(new ItemStack(Items.DYE, 1, 2), 20F)
                .addOutput(new ItemStack(FAItemRegistry.FERN_SEED, 1), 5F)
                .addOutput(new ItemStack(FAItemRegistry.PALAE_SAPLING_FOSSIL, 1), 5F)
                .addOutput(new ItemStack(FAItemRegistry.CALAMITES_SAPLING_FOSSIL, 1), 5F);
        float seedWeight = (100F - plantFossil.getTotalWeight()) / (float) FossilSeedsItem.fossilSeeds.length;
        for (int i = 0; i < FossilSeedsItem.fossilSeeds.length; i++) {
            plantFossil.addOutput(new ItemStack(FAItemRegistry.FOSSIL_SEED, 1, i), seedWeight);
        }
        register(plantFossil);
        RecipeAnalyzer bioFossil = new RecipeAnalyzer(FAItemRegistry.BIOFOSSIL)
                .addOutput(new ItemStack(Items.DYE, 1, 15), 50F)
                .addOutput(new ItemStack(Blocks.SAND, 2), 35F);
        List<PrehistoricEntityType> bioFossilEntityList = PrehistoricEntityType.getTimePeriodList(TimePeriod.MESOZOIC, TimePeriod.PALEOZOIC);
        float bioFossilDNAChance = 15F / (float) bioFossilEntityList.size();
        for (int i = 0; i < bioFossilEntityList.size(); i++) {
            bioFossil.addOutput(new ItemStack(bioFossilEntityList.get(i).dnaItem), bioFossilDNAChance);
        }
        register(bioFossil);
        for (DinosaurBoneType type : DinosaurBoneType.values()) {
            register(new RecipeAnalyzer(new ItemStack(FAItemRegistry.LEG_BONE, 1, type.ordinal()))
                    .addOutput(new ItemStack(Items.DYE, 1, 15), 30)
                    .addOutput(new ItemStack(Items.BONE), 35)
                    .addOutput(new ItemStack(DinosaurBoneType.getEntity(DinosaurBoneType.values()[type.ordinal()]).dnaItem), 35));
            register(new RecipeAnalyzer(new ItemStack(FAItemRegistry.UNIQUE_ITEM, 1, type.ordinal()))
                    .addOutput(new ItemStack(Items.DYE, 1, 15), 30)
                    .addOutput(new ItemStack(Items.BONE), 35)
                    .addOutput(new ItemStack(DinosaurBoneType.getEntity(DinosaurBoneType.values()[type.ordinal()]).dnaItem), 35));
            register(new RecipeAnalyzer(new ItemStack(FAItemRegistry.FOOT, 1, type.ordinal()))
                    .addOutput(new ItemStack(Items.DYE, 1, 15), 30)
                    .addOutput(new ItemStack(Items.BONE), 35)
                    .addOutput(new ItemStack(DinosaurBoneType.getEntity(DinosaurBoneType.values()[type.ordinal()]).dnaItem), 35));
            register(new RecipeAnalyzer(new ItemStack(FAItemRegistry.SKULL, 1, type.ordinal()))
                    .addOutput(new ItemStack(Items.DYE, 1, 15), 30)
                    .addOutput(new ItemStack(Items.BONE), 35)
                    .addOutput(new ItemStack(DinosaurBoneType.getEntity(DinosaurBoneType.values()[type.ordinal()]).dnaItem), 35));
            register(new RecipeAnalyzer(new ItemStack(FAItemRegistry.ARM_BONE, 1, type.ordinal()))
                    .addOutput(new ItemStack(Items.DYE, 1, 15), 30)
                    .addOutput(new ItemStack(Items.BONE), 35)
                    .addOutput(new ItemStack(DinosaurBoneType.getEntity(DinosaurBoneType.values()[type.ordinal()]).dnaItem), 35));
            register(new RecipeAnalyzer(new ItemStack(FAItemRegistry.RIBCAGE, 1, type.ordinal()))
                    .addOutput(new ItemStack(Items.DYE, 1, 15), 30)
                    .addOutput(new ItemStack(Items.BONE), 35)
                    .addOutput(new ItemStack(DinosaurBoneType.getEntity(DinosaurBoneType.values()[type.ordinal()]).dnaItem), 35));
            register(new RecipeAnalyzer(new ItemStack(FAItemRegistry.VERTEBRAE, 1, type.ordinal()))
                    .addOutput(new ItemStack(Items.DYE, 1, 15), 30)
                    .addOutput(new ItemStack(Items.BONE), 35)
                    .addOutput(new ItemStack(DinosaurBoneType.getEntity(DinosaurBoneType.values()[type.ordinal()]).dnaItem), 35));
        }
        register(new RecipeAnalyzer(new ItemStack(FAItemRegistry.TARDROP))
                .addOutput(new ItemStack(Items.COAL), 20)
                .addOutput(new ItemStack(Items.COAL, 1, 1), 20)
                .addOutput(new ItemStack(FAItemRegistry.TAR_FOSSIL), 45)
                .addOutput(new ItemStack(FABlockRegistry.VOLCANIC_ROCK), 15));
        RecipeAnalyzer tarFossil = new RecipeAnalyzer(FAItemRegistry.TAR_FOSSIL)
                .addOutput(new ItemStack(Items.DYE, 3, 15), 50)
                .addOutput(new ItemStack(FABlockRegistry.VOLCANIC_ROCK), 30);
        List<PrehistoricEntityType> tarFossilEntityList = PrehistoricEntityType.getTimePeriodList(TimePeriod.CENOZOIC);
        float tarFossilDNAChance = 20F / tarFossilEntityList.size();
        for (PrehistoricEntityType type : tarFossilEntityList) {
            tarFossil.addOutput(new ItemStack(type.dnaItem), tarFossilDNAChance);
        }
        register(tarFossil);
        register(new RecipeAnalyzer(new ItemStack(Blocks.WOOL))
                .addOutput(new ItemStack(Items.STRING, 3), 60)
                .addOutput(new ItemStack(PrehistoricEntityType.SHEEP.dnaItem), 27)
                .addOutput(new ItemStack(PrehistoricEntityType.LLAMA.dnaItem), 13));
        register(new RecipeAnalyzer(new ItemStack(Items.PORKCHOP)).addOutput(new ItemStack(PrehistoricEntityType.PIG.dnaItem), 100));
        register(new RecipeAnalyzer(new ItemStack(Items.BEEF)).addOutput(new ItemStack(PrehistoricEntityType.COW.dnaItem), 100));
        register(new RecipeAnalyzer(new ItemStack(Items.MUTTON)).addOutput(new ItemStack(PrehistoricEntityType.SHEEP.dnaItem), 100));
        register(new RecipeAnalyzer(new ItemStack(Items.CHICKEN)).addOutput(new ItemStack(PrehistoricEntityType.CHICKEN.dnaItem), 100));
        register(new RecipeAnalyzer(new ItemStack(Items.EGG)).addOutput(new ItemStack(PrehistoricEntityType.CHICKEN.dnaItem), 100));
        register(new RecipeAnalyzer(new ItemStack(Items.FEATHER))
                .addOutput(new ItemStack(PrehistoricEntityType.CHICKEN.dnaItem), 95)
                .addOutput(new ItemStack(PrehistoricEntityType.PARROT.dnaItem), 5));
        register(new RecipeAnalyzer(new ItemStack(Items.RABBIT)).addOutput(new ItemStack(PrehistoricEntityType.RABBIT.dnaItem), 100));
        register(new RecipeAnalyzer(new ItemStack(Items.RABBIT_FOOT)).addOutput(new ItemStack(PrehistoricEntityType.RABBIT.dnaItem), 100));
        register(new RecipeAnalyzer(new ItemStack(Items.RABBIT_HIDE)).addOutput(new ItemStack(PrehistoricEntityType.RABBIT.dnaItem), 100));
        register(new RecipeAnalyzer(new ItemStack(Items.FISH))
                .addOutput(new ItemStack(PrehistoricEntityType.POLARBEAR.dnaItem), 10)
                .addOutput(new ItemStack(Items.PRISMARINE_CRYSTALS), 15)
                .addOutput(new ItemStack(Items.DYE, 1, 15), 75));
        RecipeAnalyzer failuresaurusFlesh = new RecipeAnalyzer(FAItemRegistry.FAILURESAURUS_FLESH).addOutput(new ItemStack(Items.ROTTEN_FLESH), 33);
        float failuresaurusDNAChance = 67F / PrehistoricEntityType.values().length;
        for (PrehistoricEntityType type : PrehistoricEntityType.values()) {
            failuresaurusFlesh.addOutput(new ItemStack(type.dnaItem), failuresaurusDNAChance);
            if (type.foodItem != null) {
                register(new RecipeAnalyzer(new ItemStack(type.foodItem)).addOutput(new ItemStack(type.dnaItem), 100));
            }
            if (type.eggItem != null) {
                register(new RecipeAnalyzer(new ItemStack(type.eggItem)).addOutput(new ItemStack(type.dnaItem), 100));
            }
            if (type.birdEggItem != null) {
                register(new RecipeAnalyzer(new ItemStack(type.birdEggItem)).addOutput(new ItemStack(type.dnaItem), 100));
            }
            if (type.bestBirdEggItem != null) {
                register(new RecipeAnalyzer(new ItemStack(type.bestBirdEggItem)).addOutput(new ItemStack(type.dnaItem), 100));
            }
            if (type.fishItem != null) {
                register(new RecipeAnalyzer(new ItemStack(type.fishItem)).addOutput(new ItemStack(type.dnaItem), 100));
            }
            if (type.embryoItem != null) {
                register(new RecipeAnalyzer(new ItemStack(type.embryoItem)).addOutput(new ItemStack(type.dnaItem), 100));
            }
        }
        register(failuresaurusFlesh);
        register(new RecipeAnalyzer(new ItemStack(FAItemRegistry.SHELL)).addOutput(new ItemStack(PrehistoricEntityType.NAUTILUS.dnaItem), 100));
        register(new RecipeAnalyzer(new ItemStack(Items.LEATHER))
                .addOutput(new ItemStack(PrehistoricEntityType.COW.dnaItem), 60)
                .addOutput(new ItemStack(PrehistoricEntityType.DONKEY.dnaItem), 10)
                .addOutput(new ItemStack(PrehistoricEntityType.HORSE.dnaItem), 30));
        RecipeAnalyzer icedMeat = new RecipeAnalyzer(FAItemRegistry.ICED_MEAT);
        icedMeat.addOutput(new ItemStack(Items.CHICKEN), 15);
        icedMeat.addOutput(new ItemStack(Items.MUTTON), 15);
        icedMeat.addOutput(new ItemStack(Items.BEEF), 15);
        icedMeat.addOutput(new ItemStack(Items.PORKCHOP), 15);
        icedMeat.addOutput(new ItemStack(FAItemRegistry.TAR_FOSSIL), 20);
        for (PrehistoricEntityType type : tarFossilEntityList) {
            icedMeat.addOutput(new ItemStack(type.dnaItem), tarFossilDNAChance);
        }
        register(icedMeat);
        RecipeAnalyzer relicScrap = new RecipeAnalyzer(FAItemRegistry.RELIC_SCRAP);
        relicScrap.addOutput(new ItemStack(Blocks.GRAVEL), 30);
        relicScrap.addOutput(new ItemStack(FAItemRegistry.STONE_TABLET), 30);
        relicScrap.addOutput(new ItemStack(Items.FLINT), 18);
        relicScrap.addOutput(new ItemStack(FAItemRegistry.POTTERY_SHARD), 4);
        relicScrap.addOutput(new ItemStack(FAItemRegistry.BROKEN_HELMET), 4);
        relicScrap.addOutput(new ItemStack(FAItemRegistry.BROKEN_SWORD), 4);
        float damagedFigureWeight = (100F - relicScrap.getTotalWeight()) / 5;
        for (int i = 0; i <= 5; i++) {
            relicScrap.addOutput(new ItemStack(FABlockRegistry.FIGURINE, 1, 5 + i), damagedFigureWeight);
        }
        float brokenFigureWeight = (100F - relicScrap.getTotalWeight()) / 5;
        for (int i = 0; i <= 5; i++) {
            relicScrap.addOutput(new ItemStack(FABlockRegistry.FIGURINE, 1, 10 + i), brokenFigureWeight);
        }
        register(relicScrap);
        register(new RecipeAnalyzer(FAItemRegistry.DOMINICAN_AMBER)
                .addOutput(new ItemStack(Items.SPIDER_EYE), 9)
                .addOutput(new ItemStack(Items.STRING), 10)
                .addOutput(new ItemStack(Blocks.DIRT), 25)
                .addOutput(new ItemStack(Blocks.GRAVEL), 25)
                .addOutput(new ItemStack(FAItemRegistry.FOSSIL_SEED_FERN), 1)
                .addOutput(new ItemStack(FAItemRegistry.PALAE_SAPLING_FOSSIL), 1)
                .addOutput(new ItemStack(FAItemRegistry.CALAMITES_SAPLING_FOSSIL), 1)
                .addOutput(new ItemStack(Items.WHEAT_SEEDS), 1)
                .addOutput(new ItemStack(Items.BEETROOT_SEEDS), 1)
                .addOutput(new ItemStack(Items.PUMPKIN_SEEDS), 1)
                .addOutput(new ItemStack(Items.MELON_SEEDS), 1)
        );
    }

    private static void register(RecipeAnalyzer recipe) {
        analyzerRecipes.add(recipe);
    }

    public static RecipeAnalyzer getAnalyzerRecipeForItem(ItemStack stack) {
        for (RecipeAnalyzer recipe : analyzerRecipes) {
            if (OreDictionary.itemMatches(recipe.getInput(), stack, false)) {
                return recipe;
            }
        }
        return null;
    }
}
