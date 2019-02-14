package fossilsarcheology.server.compat.jei.analyzer;

import fossilsarcheology.server.block.FABlockRegistry;
import fossilsarcheology.server.entity.prehistoric.PrehistoricEntityType;
import fossilsarcheology.server.entity.prehistoric.TimePeriod;
import fossilsarcheology.server.item.FAItemRegistry;
import fossilsarcheology.server.item.variant.DinosaurBoneType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;

import java.util.ArrayList;
import java.util.List;

//Fixme: Fix bones and bone blocks, they can't actually be analyzed.
public class AnalyzerRecipes {
    public static List<RecipeAnalyzer> getRecipes(){
        List<RecipeAnalyzer> list = new ArrayList<>();
        list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.BIOFOSSIL), new ItemStack(Items.DYE, 1, 15), 50));
        list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.BIOFOSSIL), new ItemStack(Blocks.SAND), 35));
        for(PrehistoricEntityType type : PrehistoricEntityType.getTimePeriodList(TimePeriod.MESOZOIC, TimePeriod.PALEOZOIC)){
            list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.BIOFOSSIL), new ItemStack(type.dnaItem), 15));
        }
        for(PrehistoricEntityType type : PrehistoricEntityType.getTimePeriodList(TimePeriod.MESOZOIC, TimePeriod.PALEOZOIC, TimePeriod.CENOZOIC)){
            list.add(new RecipeAnalyzer(new ItemStack(Blocks.BONE_BLOCK), new ItemStack(type.dnaItem), 10));
        }
        for(PrehistoricEntityType type : PrehistoricEntityType.getTimePeriodList(TimePeriod.MESOZOIC, TimePeriod.PALEOZOIC, TimePeriod.CENOZOIC)){
            list.add(new RecipeAnalyzer(new ItemStack(Items.BONE), new ItemStack(type.dnaItem), 0));
        }
        list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.DOMINICAN_AMBER), new ItemStack(Items.SPIDER_EYE), 9));
        list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.DOMINICAN_AMBER), new ItemStack(Items.STRING), 10));
        list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.DOMINICAN_AMBER), new ItemStack(Blocks.DIRT), 25));
        list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.DOMINICAN_AMBER), new ItemStack(Blocks.GRAVEL), 25));
        list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.DOMINICAN_AMBER), new ItemStack(Blocks.SAND), 25));
        list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.DOMINICAN_AMBER), new ItemStack(FAItemRegistry.FOSSIL_SEED_FERN), 1));
        list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.DOMINICAN_AMBER), new ItemStack(FAItemRegistry.PALAE_SAPLING_FOSSIL), 1));
        list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.DOMINICAN_AMBER), new ItemStack(Items.WHEAT_SEEDS), 1));
        list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.DOMINICAN_AMBER), new ItemStack(Items.BEETROOT_SEEDS), 1));
        list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.DOMINICAN_AMBER), new ItemStack(Items.PUMPKIN_SEEDS), 1));
        list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.DOMINICAN_AMBER), new ItemStack(Items.MELON_SEEDS), 1));

        list.add(new RecipeAnalyzer(new ItemStack(Items.BONE), new ItemStack(Items.DYE, 1, 15), 50));
        list.add(new RecipeAnalyzer(new ItemStack(Blocks.BONE_BLOCK), new ItemStack(Items.DYE, 1, 15), 25));
        list.add(new RecipeAnalyzer(new ItemStack(Blocks.BONE_BLOCK), new ItemStack(Items.BONE), 25));
        list.add(new RecipeAnalyzer(new ItemStack(Blocks.BONE_BLOCK), new ItemStack(Blocks.SAND), 20));
        list.add(new RecipeAnalyzer(new ItemStack(Blocks.BONE_BLOCK), new ItemStack(Blocks.GRAVEL), 20));
        for(DinosaurBoneType type : DinosaurBoneType.values()){
            list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.LEG_BONE, 1, type.ordinal()), new ItemStack(Items.DYE, 1, 15), 30));
            list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.LEG_BONE, 1, type.ordinal()), new ItemStack(Items.BONE), 35));
            list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.LEG_BONE, 1, type.ordinal()), new ItemStack(DinosaurBoneType.getEntity(DinosaurBoneType.values()[type.ordinal()]).dnaItem), 35));
            list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.UNIQUE_ITEM, 1, type.ordinal()), new ItemStack(Items.DYE, 1, 15), 30));
            list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.UNIQUE_ITEM, 1, type.ordinal()), new ItemStack(Items.BONE), 35));
            list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.UNIQUE_ITEM, 1, type.ordinal()), new ItemStack(DinosaurBoneType.getEntity(DinosaurBoneType.values()[type.ordinal()]).dnaItem), 35));
            list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.FOOT, 1, type.ordinal()), new ItemStack(Items.DYE, 1, 15), 30));
            list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.FOOT, 1, type.ordinal()), new ItemStack(Items.BONE), 35));
            list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.FOOT, 1, type.ordinal()), new ItemStack(DinosaurBoneType.getEntity(DinosaurBoneType.values()[type.ordinal()]).dnaItem), 35));
            list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.SKULL, 1, type.ordinal()), new ItemStack(Items.DYE, 1, 15), 30));
            list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.SKULL, 1, type.ordinal()), new ItemStack(Items.BONE), 35));
            list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.SKULL, 1, type.ordinal()), new ItemStack(DinosaurBoneType.getEntity(DinosaurBoneType.values()[type.ordinal()]).dnaItem), 35));
            list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.ARM_BONE, 1, type.ordinal()), new ItemStack(Items.DYE, 1, 15), 30));
            list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.ARM_BONE, 1, type.ordinal()), new ItemStack(Items.BONE), 35));
            list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.ARM_BONE, 1, type.ordinal()), new ItemStack(DinosaurBoneType.getEntity(DinosaurBoneType.values()[type.ordinal()]).dnaItem), 35));
            list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.RIBCAGE, 1, type.ordinal()), new ItemStack(Items.DYE, 1, 15), 30));
            list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.RIBCAGE, 1, type.ordinal()), new ItemStack(Items.BONE), 35));
            list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.RIBCAGE, 1, type.ordinal()), new ItemStack(DinosaurBoneType.getEntity(DinosaurBoneType.values()[type.ordinal()]).dnaItem), 35));
            list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.VERTEBRAE, 1, type.ordinal()), new ItemStack(Items.DYE, 1, 15), 30));
            list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.VERTEBRAE, 1, type.ordinal()), new ItemStack(Items.BONE), 35));
            list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.VERTEBRAE, 1, type.ordinal()), new ItemStack(DinosaurBoneType.getEntity(DinosaurBoneType.values()[type.ordinal()]).dnaItem), 35));
        }
        list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.TARDROP), new ItemStack(Items.COAL), 20));
        list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.TARDROP), new ItemStack(Items.COAL, 1, 1), 20));
        list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.TARDROP), new ItemStack(FAItemRegistry.TAR_FOSSIL), 45));
        list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.TARDROP), new ItemStack(FABlockRegistry.VOLCANIC_ROCK), 15));
        list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.TAR_FOSSIL), new ItemStack(Items.DYE, 3, 15), 50));
        list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.TAR_FOSSIL), new ItemStack(FABlockRegistry.VOLCANIC_ROCK), 30));
        for(PrehistoricEntityType type : PrehistoricEntityType.getTimePeriodList(TimePeriod.CENOZOIC)) {
            list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.TAR_FOSSIL), new ItemStack(type.dnaItem), 20));
        }
        list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.PLANT_FOSSIL), new ItemStack(Blocks.SAND), 35));
        list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.PLANT_FOSSIL), new ItemStack(Items.COAL), 30));
        list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.PLANT_FOSSIL), new ItemStack(FAItemRegistry.PALAE_SAPLING_FOSSIL), 10));
        list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.PLANT_FOSSIL), new ItemStack(Items.DYE, 1, 2), 10));
        for(int i = 0; i < 14; i++){
            list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.PLANT_FOSSIL), new ItemStack(FAItemRegistry.FOSSIL_SEED, 1, i), 15));
        }
        list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.PLANT_FOSSIL), new ItemStack(FAItemRegistry.FOSSIL_SEED_FERN), 15));
        list.add(new RecipeAnalyzer(new ItemStack(Blocks.WOOL), new ItemStack(Items.STRING), 60));
        list.add(new RecipeAnalyzer(new ItemStack(Blocks.WOOL), new ItemStack(PrehistoricEntityType.SHEEP.dnaItem), 27));
        list.add(new RecipeAnalyzer(new ItemStack(Blocks.WOOL), new ItemStack(PrehistoricEntityType.LLAMA.dnaItem), 13));
        list.add(new RecipeAnalyzer(new ItemStack(Items.PORKCHOP), new ItemStack(PrehistoricEntityType.PIG.dnaItem), 100));
        list.add(new RecipeAnalyzer(new ItemStack(Items.FISH), new ItemStack(PrehistoricEntityType.POLARBEAR.dnaItem), 10));
        list.add(new RecipeAnalyzer(new ItemStack(Items.FISH), new ItemStack(Items.PRISMARINE_CRYSTALS), 15));
        list.add(new RecipeAnalyzer(new ItemStack(Items.FISH), new ItemStack(Items.DYE, 1, 15), 75));
        list.add(new RecipeAnalyzer(new ItemStack(Items.BEEF), new ItemStack(PrehistoricEntityType.COW.dnaItem), 100));
        list.add(new RecipeAnalyzer(new ItemStack(Items.MUTTON), new ItemStack(PrehistoricEntityType.SHEEP.dnaItem), 100));
        list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.FAILURESAURUS_FLESH), new ItemStack(Items.ROTTEN_FLESH), 33));
        list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.SHELL), new ItemStack(PrehistoricEntityType.NAUTILUS.dnaItem), 100));
        for(PrehistoricEntityType type : PrehistoricEntityType.values()){
            list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.FAILURESAURUS_FLESH), new ItemStack(type.dnaItem), 67));
            if(type.foodItem != null){
                list.add(new RecipeAnalyzer(new ItemStack(type.foodItem), new ItemStack(type.dnaItem), 100));
            }
            if(type.eggItem != null){
                list.add(new RecipeAnalyzer(new ItemStack(type.eggItem), new ItemStack(type.dnaItem), 100));
            }
            if(type.birdEggItem != null){
                list.add(new RecipeAnalyzer(new ItemStack(type.birdEggItem), new ItemStack(type.dnaItem), 100));
            }
            if(type.bestBirdEggItem != null){
                list.add(new RecipeAnalyzer(new ItemStack(type.bestBirdEggItem), new ItemStack(type.dnaItem), 100));
            }
            if(type.fishItem != null){
                list.add(new RecipeAnalyzer(new ItemStack(type.fishItem), new ItemStack(type.dnaItem), 100));
            }
            if(type.embryoItem != null){
                list.add(new RecipeAnalyzer(new ItemStack(type.embryoItem), new ItemStack(type.dnaItem), 100));
            }
        }
        list.add(new RecipeAnalyzer(new ItemStack(Items.LEATHER), new ItemStack(PrehistoricEntityType.COW.dnaItem), 60));
        list.add(new RecipeAnalyzer(new ItemStack(Items.LEATHER), new ItemStack(PrehistoricEntityType.DONKEY.dnaItem), 10));
        list.add(new RecipeAnalyzer(new ItemStack(Items.LEATHER), new ItemStack(PrehistoricEntityType.HORSE.dnaItem), 30));
        list.add(new RecipeAnalyzer(new ItemStack(Items.RABBIT), new ItemStack(PrehistoricEntityType.RABBIT.dnaItem), 100));
        list.add(new RecipeAnalyzer(new ItemStack(Items.RABBIT_FOOT), new ItemStack(PrehistoricEntityType.RABBIT.dnaItem), 100));
        list.add(new RecipeAnalyzer(new ItemStack(Items.RABBIT_HIDE), new ItemStack(PrehistoricEntityType.RABBIT.dnaItem), 100));
        list.add(new RecipeAnalyzer(new ItemStack(Items.EGG), new ItemStack(PrehistoricEntityType.CHICKEN.dnaItem), 100));
        list.add(new RecipeAnalyzer(new ItemStack(Items.FEATHER), new ItemStack(PrehistoricEntityType.CHICKEN.dnaItem), 95));
        list.add(new RecipeAnalyzer(new ItemStack(Items.FEATHER), new ItemStack(PrehistoricEntityType.PARROT.dnaItem), 5));
        list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.ICED_MEAT), new ItemStack(Items.CHICKEN), 15));
        list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.ICED_MEAT), new ItemStack(Items.MUTTON), 15));
        list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.ICED_MEAT), new ItemStack(Items.BEEF), 15));
        list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.ICED_MEAT), new ItemStack(Items.PORKCHOP), 15));
        list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.ICED_MEAT), new ItemStack(FAItemRegistry.TAR_FOSSIL), 20));
        for(PrehistoricEntityType type : PrehistoricEntityType.getTimePeriodList(TimePeriod.CENOZOIC)) {
            list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.ICED_MEAT), new ItemStack(type.dnaItem), 20));
        }
        list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.RELIC_SCRAP), new ItemStack(Blocks.GRAVEL), 40));
        list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.RELIC_SCRAP), new ItemStack(FAItemRegistry.STONE_TABLET), 30));
        list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.RELIC_SCRAP), new ItemStack(Items.FLINT), 18));
        list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.RELIC_SCRAP), new ItemStack(FAItemRegistry.POTTERY_SHARD), 4));
        for(int i = 0; i < 5; i++) {
            list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.RELIC_SCRAP), new ItemStack(FABlockRegistry.FIGURINE, 1, 10 + i), 1));
            list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.RELIC_SCRAP), new ItemStack(FABlockRegistry.FIGURINE, 1, 5 + i), 1));
        }
        list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.RELIC_SCRAP), new ItemStack(FAItemRegistry.BROKEN_SWORD), 4));
        list.add(new RecipeAnalyzer(new ItemStack(FAItemRegistry.RELIC_SCRAP), new ItemStack(FAItemRegistry.BROKEN_HELMET), 4));
        return list;
    }
}
