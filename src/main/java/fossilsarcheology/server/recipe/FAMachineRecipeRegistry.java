package fossilsarcheology.server.recipe;

import com.google.common.collect.Maps;
import fossilsarcheology.server.block.EnumFossilPlant;
import fossilsarcheology.server.block.FABlockRegistry;
import fossilsarcheology.server.block.entity.TileEntityCultivate;
import fossilsarcheology.server.block.entity.TileEntitySifter;
import fossilsarcheology.server.compat.jei.sifter.RecipeSifter;
import fossilsarcheology.server.entity.prehistoric.PrehistoricEntityType;
import fossilsarcheology.server.entity.prehistoric.TimePeriod;
import fossilsarcheology.server.item.FAItemRegistry;
import fossilsarcheology.server.item.FossilSeedsItem;
import fossilsarcheology.server.item.variant.DinosaurBoneType;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class FAMachineRecipeRegistry {
    public static List<RecipeAnalyzer> analyzerRecipes = new ArrayList<>();
    public static List<ItemStack> toBeRemovedAnalyzer = new ArrayList<>();
    public static List<RecipeAnalyzer> sifterRecipes = new ArrayList<>();
    public static List<ItemStack> toBeRemovedSifter = new ArrayList<>();
    public static Map<ItemStack, ItemStack> cultivateRecipes = Maps.newHashMap();
    public static List<ItemStack> toBeRemovedCultivate = new ArrayList<>();
    public static Map<ItemStack, Integer> cultivateFuelValues = Maps.newHashMap();
    public static List<RecipeWorktable> worktableRecipes = new ArrayList<>();
    public static List<ItemStack> toBeRemovedWorktable = new ArrayList<>();


    public static void init() {
        RecipeAnalyzer plantFossil = new RecipeAnalyzer(FAItemRegistry.PLANT_FOSSIL)
                .addOutput(new ItemStack(Blocks.SAND, 2), 35F)
                .addOutput(new ItemStack(Items.DYE, 1, 2), 20F)
                .addOutput(new ItemStack(FAItemRegistry.FOSSIL_SEED_FERN, 1), 5F)
                .addOutput(new ItemStack(FAItemRegistry.PALAE_SAPLING_FOSSIL, 1), 2.5F)
                .addOutput(new ItemStack(FAItemRegistry.CALAMITES_SAPLING_FOSSIL, 1), 2.5F)
                .addOutput(new ItemStack(FAItemRegistry.SIGILLARIA_SAPLING_FOSSIL, 1), 2.5F)
                .addOutput(new ItemStack(FAItemRegistry.CORDAITES_SAPLING_FOSSIL, 1), 2.5F);

        float seedWeight = (100F - plantFossil.getTotalWeight()) / (float) EnumFossilPlant.values().length;
        for (int i = 0; i < EnumFossilPlant.values().length; i++) {
            plantFossil.addOutput(new ItemStack(FAItemRegistry.FOSSIL_SEED, 1, i), seedWeight);
        }
        registerAnalyzer(plantFossil);
        RecipeAnalyzer bioFossil = new RecipeAnalyzer(FAItemRegistry.BIOFOSSIL)
                .addOutput(new ItemStack(Items.DYE, 1, 15), 50F)
                .addOutput(new ItemStack(Blocks.SAND, 2), 35F);
        List<PrehistoricEntityType> bioFossilEntityList = PrehistoricEntityType.getTimePeriodList(TimePeriod.MESOZOIC, TimePeriod.PALEOZOIC);
        float bioFossilDNAChance = 15F / (float) bioFossilEntityList.size();
        for (int i = 0; i < bioFossilEntityList.size(); i++) {
            bioFossil.addOutput(new ItemStack(bioFossilEntityList.get(i).dnaItem), bioFossilDNAChance);
        }
        registerAnalyzer(bioFossil);
        for (DinosaurBoneType type : DinosaurBoneType.values()) {
            registerAnalyzer(new RecipeAnalyzer(new ItemStack(FAItemRegistry.LEG_BONE, 1, type.ordinal()))
                    .addOutput(new ItemStack(Items.DYE, 1, 15), 30)
                    .addOutput(new ItemStack(Items.BONE), 35)
                    .addOutput(new ItemStack(DinosaurBoneType.getEntity(DinosaurBoneType.values()[type.ordinal()]).dnaItem), 35));
            registerAnalyzer(new RecipeAnalyzer(new ItemStack(FAItemRegistry.UNIQUE_ITEM, 1, type.ordinal()))
                    .addOutput(new ItemStack(Items.DYE, 1, 15), 30)
                    .addOutput(new ItemStack(Items.BONE), 35)
                    .addOutput(new ItemStack(DinosaurBoneType.getEntity(DinosaurBoneType.values()[type.ordinal()]).dnaItem), 35));
            registerAnalyzer(new RecipeAnalyzer(new ItemStack(FAItemRegistry.FOOT, 1, type.ordinal()))
                    .addOutput(new ItemStack(Items.DYE, 1, 15), 30)
                    .addOutput(new ItemStack(Items.BONE), 35)
                    .addOutput(new ItemStack(DinosaurBoneType.getEntity(DinosaurBoneType.values()[type.ordinal()]).dnaItem), 35));
            registerAnalyzer(new RecipeAnalyzer(new ItemStack(FAItemRegistry.SKULL, 1, type.ordinal()))
                    .addOutput(new ItemStack(Items.DYE, 1, 15), 30)
                    .addOutput(new ItemStack(Items.BONE), 35)
                    .addOutput(new ItemStack(DinosaurBoneType.getEntity(DinosaurBoneType.values()[type.ordinal()]).dnaItem), 35));
            registerAnalyzer(new RecipeAnalyzer(new ItemStack(FAItemRegistry.ARM_BONE, 1, type.ordinal()))
                    .addOutput(new ItemStack(Items.DYE, 1, 15), 30)
                    .addOutput(new ItemStack(Items.BONE), 35)
                    .addOutput(new ItemStack(DinosaurBoneType.getEntity(DinosaurBoneType.values()[type.ordinal()]).dnaItem), 35));
            registerAnalyzer(new RecipeAnalyzer(new ItemStack(FAItemRegistry.RIBCAGE, 1, type.ordinal()))
                    .addOutput(new ItemStack(Items.DYE, 1, 15), 30)
                    .addOutput(new ItemStack(Items.BONE), 35)
                    .addOutput(new ItemStack(DinosaurBoneType.getEntity(DinosaurBoneType.values()[type.ordinal()]).dnaItem), 35));
            registerAnalyzer(new RecipeAnalyzer(new ItemStack(FAItemRegistry.VERTEBRAE, 1, type.ordinal()))
                    .addOutput(new ItemStack(Items.DYE, 1, 15), 30)
                    .addOutput(new ItemStack(Items.BONE), 35)
                    .addOutput(new ItemStack(DinosaurBoneType.getEntity(DinosaurBoneType.values()[type.ordinal()]).dnaItem), 35));
        }
        registerAnalyzer(new RecipeAnalyzer(new ItemStack(FAItemRegistry.TARDROP))
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
        registerAnalyzer(tarFossil);
        registerAnalyzer(new RecipeAnalyzer(new ItemStack(Blocks.WOOL))
                .addOutput(new ItemStack(Items.STRING, 3), 60)
                .addOutput(new ItemStack(PrehistoricEntityType.SHEEP.dnaItem), 27)
                .addOutput(new ItemStack(PrehistoricEntityType.LLAMA.dnaItem), 13));
        registerAnalyzer(new RecipeAnalyzer(new ItemStack(Items.PORKCHOP)).addOutput(new ItemStack(PrehistoricEntityType.PIG.dnaItem), 100));
        registerAnalyzer(new RecipeAnalyzer(new ItemStack(Items.BEEF)).addOutput(new ItemStack(PrehistoricEntityType.COW.dnaItem), 100));
        registerAnalyzer(new RecipeAnalyzer(new ItemStack(Items.MUTTON)).addOutput(new ItemStack(PrehistoricEntityType.SHEEP.dnaItem), 100));
        registerAnalyzer(new RecipeAnalyzer(new ItemStack(Items.CHICKEN)).addOutput(new ItemStack(PrehistoricEntityType.CHICKEN.dnaItem), 100));
        registerAnalyzer(new RecipeAnalyzer(new ItemStack(Items.EGG)).addOutput(new ItemStack(PrehistoricEntityType.CHICKEN.dnaItem), 100));
        registerAnalyzer(new RecipeAnalyzer(new ItemStack(Items.FEATHER))
                .addOutput(new ItemStack(PrehistoricEntityType.CHICKEN.dnaItem), 95)
                .addOutput(new ItemStack(PrehistoricEntityType.PARROT.dnaItem), 5));
        registerAnalyzer(new RecipeAnalyzer(new ItemStack(Items.RABBIT)).addOutput(new ItemStack(PrehistoricEntityType.RABBIT.dnaItem), 100));
        registerAnalyzer(new RecipeAnalyzer(new ItemStack(Items.RABBIT_FOOT)).addOutput(new ItemStack(PrehistoricEntityType.RABBIT.dnaItem), 100));
        registerAnalyzer(new RecipeAnalyzer(new ItemStack(Items.RABBIT_HIDE)).addOutput(new ItemStack(PrehistoricEntityType.RABBIT.dnaItem), 100));
        registerAnalyzer(new RecipeAnalyzer(new ItemStack(Items.FISH))
                .addOutput(new ItemStack(PrehistoricEntityType.POLARBEAR.dnaItem), 10)
                .addOutput(new ItemStack(Items.PRISMARINE_CRYSTALS), 15)
                .addOutput(new ItemStack(Items.DYE, 1, 15), 75));
        RecipeAnalyzer failuresaurusFlesh = new RecipeAnalyzer(FAItemRegistry.FAILURESAURUS_FLESH).addOutput(new ItemStack(Items.ROTTEN_FLESH), 33);
        float failuresaurusDNAChance = 67F / PrehistoricEntityType.values().length;
        for (PrehistoricEntityType type : PrehistoricEntityType.values()) {
            failuresaurusFlesh.addOutput(new ItemStack(type.dnaItem), failuresaurusDNAChance);
            if (type.foodItem != null) {
                registerAnalyzer(new RecipeAnalyzer(new ItemStack(type.foodItem)).addOutput(new ItemStack(type.dnaItem), 100));
            }
            if (type.eggItem != null) {
                registerAnalyzer(new RecipeAnalyzer(new ItemStack(type.eggItem)).addOutput(new ItemStack(type.dnaItem), 100));
            }
            if (type.birdEggItem != null) {
                registerAnalyzer(new RecipeAnalyzer(new ItemStack(type.birdEggItem)).addOutput(new ItemStack(type.dnaItem), 100));
            }
            if (type.bestBirdEggItem != null) {
                registerAnalyzer(new RecipeAnalyzer(new ItemStack(type.bestBirdEggItem)).addOutput(new ItemStack(type.dnaItem), 100));
            }
            if (type.fishItem != null) {
                registerAnalyzer(new RecipeAnalyzer(new ItemStack(type.fishItem)).addOutput(new ItemStack(type.dnaItem), 100));
            }
            if (type.embryoItem != null) {
                registerAnalyzer(new RecipeAnalyzer(new ItemStack(type.embryoItem)).addOutput(new ItemStack(type.dnaItem), 100));
            }
        }
        registerAnalyzer(failuresaurusFlesh);
        registerAnalyzer(new RecipeAnalyzer(new ItemStack(FAItemRegistry.SHELL)).addOutput(new ItemStack(PrehistoricEntityType.NAUTILUS.dnaItem), 100));
        registerAnalyzer(new RecipeAnalyzer(new ItemStack(Items.LEATHER))
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
        registerAnalyzer(icedMeat);
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
        registerAnalyzer(relicScrap);
        registerAnalyzer(new RecipeAnalyzer(FAItemRegistry.DOMINICAN_AMBER)
                .addOutput(new ItemStack(Items.SPIDER_EYE), 9)
                .addOutput(new ItemStack(Items.STRING), 10)
                .addOutput(new ItemStack(Blocks.DIRT), 25)
                .addOutput(new ItemStack(Blocks.GRAVEL), 25)
                .addOutput(new ItemStack(FAItemRegistry.FOSSIL_SEED_FERN), 1)
                .addOutput(new ItemStack(FAItemRegistry.PALAE_SAPLING_FOSSIL), 1)
                .addOutput(new ItemStack(FAItemRegistry.CALAMITES_SAPLING_FOSSIL), 1)
                .addOutput(new ItemStack(FAItemRegistry.SIGILLARIA_SAPLING_FOSSIL), 1)
                .addOutput(new ItemStack(FAItemRegistry.CORDAITES_SAPLING_FOSSIL), 1)
                .addOutput(new ItemStack(Items.WHEAT_SEEDS), 1)
                .addOutput(new ItemStack(Items.BEETROOT_SEEDS), 1)
                .addOutput(new ItemStack(Items.PUMPKIN_SEEDS), 1)
                .addOutput(new ItemStack(Items.MELON_SEEDS), 1)
        );
        List<ItemStack> sediment = new ArrayList<>();
        for (Item item : Item.REGISTRY) {
            if (item instanceof ItemBlock && TileEntitySifter.getSiftTypeFromStack(new ItemStack(item)) != TileEntitySifter.EnumSiftType.NONE) {
                NonNullList<ItemStack> items = NonNullList.create();
                if (item.getCreativeTab() != null) {
                    item.getSubItems(item.getCreativeTab(), items);
                    sediment.addAll(items);
                }
            }
        }
        for (ItemStack itemstack : sediment) {
            RecipeAnalyzer sifterRecipe = new RecipeAnalyzer(itemstack);
            if (itemstack.getItem() == Item.getItemFromBlock(Blocks.SAND)) {
                sifterRecipe.addOutput(new ItemStack(Blocks.SAND), 25);
            } else {
                sifterRecipe.addOutput(new ItemStack(Blocks.SAND), 20);
            }
            sifterRecipe.addOutput(new ItemStack(FAItemRegistry.DOMINICAN_AMBER), 1);
            sifterRecipe.addOutput(new ItemStack(FAItemRegistry.PLANT_FOSSIL), 14);
            sifterRecipe.addOutput(new ItemStack(Items.POTATO), 15);
            sifterRecipe.addOutput(new ItemStack(Items.CARROT), 10);
            sifterRecipe.addOutput(new ItemStack(Items.DYE, 1, 15), 20);
            sifterRecipe.addOutput(new ItemStack(FAItemRegistry.FOSSIL_SEED_FERN), 10);
            sifterRecipe.addOutput(new ItemStack(FAItemRegistry.POTTERY_SHARD), 5);
            sifterRecipe.addOutput(new ItemStack(FAItemRegistry.BIOFOSSIL), 2);
            registerSifter(sifterRecipe);
        }
        for (PrehistoricEntityType type : PrehistoricEntityType.values()) {
            registerCultivate(new ItemStack(type.dnaItem), TileEntityCultivate.getCultivationOutput(new ItemStack(type.dnaItem)));
        }
        for (int i = 0; i < EnumFossilPlant.values().length; i++) {
            registerCultivate(new ItemStack(FAItemRegistry.FOSSIL_SEED, 1, i), new ItemStack(FAItemRegistry.SEED, 1, i));
        }
        registerCultivate(new ItemStack(FAItemRegistry.FOSSIL_SEED_FERN), new ItemStack(FAItemRegistry.FERN_SEED));
        registerCultivate(new ItemStack(FAItemRegistry.PALAE_SAPLING_FOSSIL), new ItemStack(FABlockRegistry.PALM_SAPLING));
        registerCultivate(new ItemStack(FAItemRegistry.CALAMITES_SAPLING_FOSSIL), new ItemStack(FABlockRegistry.CALAMITES_SAPLING));
        registerCultivate(new ItemStack(FAItemRegistry.SIGILLARIA_SAPLING_FOSSIL), new ItemStack(FABlockRegistry.SIGILLARIA_SAPLING));
        registerCultivate(new ItemStack(FAItemRegistry.CORDAITES_SAPLING_FOSSIL), new ItemStack(FABlockRegistry.CORDAITES_SAPLING));
        registerWorktable(new ItemStack(FAItemRegistry.BROKEN_SWORD), new ItemStack(FAItemRegistry.ANCIENT_SWORD), new ItemStack(FAItemRegistry.RELIC_SCRAP));
        registerWorktable(new ItemStack(FAItemRegistry.BROKEN_HELMET), new ItemStack(FAItemRegistry.ANCIENT_HELMET), new ItemStack(FAItemRegistry.RELIC_SCRAP));
        registerWorktable(new ItemStack(FAItemRegistry.ANCIENT_SWORD), new ItemStack(FAItemRegistry.ANCIENT_SWORD), new ItemStack(FAItemRegistry.RELIC_SCRAP));
        registerWorktable(new ItemStack(FAItemRegistry.ANCIENT_HELMET), new ItemStack(FAItemRegistry.ANCIENT_HELMET), new ItemStack(FAItemRegistry.RELIC_SCRAP));
        registerWorktable(new ItemStack(FAItemRegistry.SCARAB_AXE), new ItemStack(FAItemRegistry.SCARAB_AXE), new ItemStack(FAItemRegistry.RELIC_SCRAP));
        registerWorktable(new ItemStack(FAItemRegistry.SCARAB_PICKAXE), new ItemStack(FAItemRegistry.SCARAB_PICKAXE), new ItemStack(FAItemRegistry.RELIC_SCRAP));
        registerWorktable(new ItemStack(FAItemRegistry.SCARAB_SWORD), new ItemStack(FAItemRegistry.SCARAB_SWORD), new ItemStack(FAItemRegistry.RELIC_SCRAP));
        registerWorktable(new ItemStack(FAItemRegistry.SCARAB_HOE), new ItemStack(FAItemRegistry.SCARAB_HOE), new ItemStack(FAItemRegistry.RELIC_SCRAP));
        registerWorktable(new ItemStack(FAItemRegistry.SCARAB_SHOVEL), new ItemStack(FAItemRegistry.SCARAB_SHOVEL), new ItemStack(FAItemRegistry.RELIC_SCRAP));
        registerWorktable(new ItemStack(FAItemRegistry.WOODEN_JAVELIN), new ItemStack(FAItemRegistry.WOODEN_JAVELIN), new ItemStack(FAItemRegistry.RELIC_SCRAP));
        registerWorktable(new ItemStack(FAItemRegistry.STONE_JAVELIN), new ItemStack(FAItemRegistry.STONE_JAVELIN), new ItemStack(FAItemRegistry.RELIC_SCRAP));
        registerWorktable(new ItemStack(FAItemRegistry.IRON_JAVELIN), new ItemStack(FAItemRegistry.IRON_JAVELIN), new ItemStack(FAItemRegistry.RELIC_SCRAP));
        registerWorktable(new ItemStack(FAItemRegistry.GOLD_JAVELIN), new ItemStack(FAItemRegistry.GOLD_JAVELIN), new ItemStack(FAItemRegistry.RELIC_SCRAP));
        registerWorktable(new ItemStack(FAItemRegistry.DIAMOND_JAVELIN), new ItemStack(FAItemRegistry.DIAMOND_JAVELIN), new ItemStack(FAItemRegistry.RELIC_SCRAP));
        registerWorktable(new ItemStack(FAItemRegistry.ANCIENT_JAVELIN), new ItemStack(FAItemRegistry.ANCIENT_JAVELIN), new ItemStack(FAItemRegistry.RELIC_SCRAP));
        registerWorktable(new ItemStack(FABlockRegistry.KYLIX_VASE, 1, 0), new ItemStack(FABlockRegistry.KYLIX_VASE, 1, 1), new ItemStack(FAItemRegistry.POTTERY_SHARD));
        registerWorktable(new ItemStack(FABlockRegistry.VOLUTE_VASE, 1, 0), new ItemStack(FABlockRegistry.VOLUTE_VASE, 1, 1), new ItemStack(FAItemRegistry.POTTERY_SHARD));
        registerWorktable(new ItemStack(FABlockRegistry.AMPHORA_VASE, 1, 0), new ItemStack(FABlockRegistry.AMPHORA_VASE, 1, 1), new ItemStack(FAItemRegistry.POTTERY_SHARD));
        for (int i = 5; i <= 14; i++) {
            registerWorktable(new ItemStack(FABlockRegistry.FIGURINE, 1, i), new ItemStack(FABlockRegistry.FIGURINE, 1, i - 5), new ItemStack(FAItemRegistry.POTTERY_SHARD));
        }
        cultivateFuelValues.put(new ItemStack(FAItemRegistry.BIO_GOO), 6000);
    }

    public static void registerAnalyzer(RecipeAnalyzer recipe) {
        analyzerRecipes.add(recipe);
    }

    public static void registerSifter(RecipeAnalyzer recipe) {
        sifterRecipes.add(recipe);
    }

    public static void registerCultivate(ItemStack input, ItemStack output) {
        cultivateRecipes.put(input, output);
    }

    public static void registerWorktable(ItemStack input, ItemStack output, ItemStack fuel) {
        worktableRecipes.add(new RecipeWorktable(input, output, fuel));
    }

    public static ItemStack getCultivateResult(ItemStack stack) {
        for (Map.Entry<ItemStack, ItemStack> entry : cultivateRecipes.entrySet()) {
            if (OreDictionary.itemMatches(stack, entry.getKey(), false)) {
                return entry.getValue();
            }
        }
        return ItemStack.EMPTY;
    }

    public static RecipeWorktable getWorktableRecipeForItem(ItemStack stack) {
        for (RecipeWorktable recipe : worktableRecipes) {
            if (stack.isItemStackDamageable() && stack.getItem() == recipe.getInput().getItem() || OreDictionary.itemMatches(recipe.getInput(), stack, false)) {
                return recipe;
            }
        }
        return null;
    }


    public static RecipeAnalyzer getAnalyzerRecipeForItem(ItemStack stack) {
        for (RecipeAnalyzer recipe : analyzerRecipes) {
            if (OreDictionary.itemMatches(recipe.getInput(), stack, false)) {
                return recipe;
            }
        }
        return null;
    }

    public static RecipeAnalyzer getSifterRecipeForItem(ItemStack stack) {
        for (RecipeAnalyzer recipe : sifterRecipes) {
            if (OreDictionary.itemMatches(recipe.getInput(), stack, false)) {
                return recipe;
            }
        }
        return null;
    }

    public static void postInit() {
        Iterator<RecipeAnalyzer> itr = FAMachineRecipeRegistry.analyzerRecipes.iterator();
        while (itr.hasNext()) {
            RecipeAnalyzer recipe = itr.next();
            for (ItemStack stack : FAMachineRecipeRegistry.toBeRemovedAnalyzer) {
                if (recipe.getInput().isItemEqual(stack)) {
                    itr.remove();
                }
            }
        }
        Iterator<RecipeAnalyzer> itr1 = FAMachineRecipeRegistry.sifterRecipes.iterator();
        while (itr1.hasNext()) {
            RecipeAnalyzer recipe = itr1.next();
            for (ItemStack stack : FAMachineRecipeRegistry.toBeRemovedSifter) {
                if (recipe.getInput().isItemEqual(stack)) {
                    itr1.remove();
                }
            }
        }
        for (ItemStack cultivateStack : toBeRemovedCultivate) {
            FAMachineRecipeRegistry.cultivateRecipes.remove(cultivateStack);
        }
        Iterator<RecipeWorktable> itr3 = FAMachineRecipeRegistry.worktableRecipes.iterator();
        while (itr3.hasNext()) {
            RecipeWorktable recipe = itr3.next();
            for (ItemStack stack : FAMachineRecipeRegistry.toBeRemovedWorktable) {
                if (recipe.getInput().isItemEqual(stack)) {
                    itr3.remove();
                }
            }
        }
    }
}