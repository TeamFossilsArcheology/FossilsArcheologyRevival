package com.fossil.fossil.recipe;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.block.ModBlocks;
import com.fossil.fossil.block.PrehistoricPlantType;
import com.fossil.fossil.block.entity.SifterBlockEntity;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.item.ModItems;
import com.fossil.fossil.util.TimePeriod;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.Nullable;

import java.util.*;

public class ModRecipes {
    public static final Map<Item, AnalyzerRecipe> ANALYZER_RECIPES = new HashMap<>();
    public static final Map<Item, AnalyzerRecipe> SIFTER_RECIPES = new HashMap<>();
    public static final Map<Item, WorktableRecipe> WORKTABLE_RECIPES = new HashMap<>();
    public static final Map<ItemLike, Integer> WORKTABLE_FUEL_VALUES = new HashMap<>();
    public static final Map<ItemLike, WorktableRecipe> CULTIVATE_RECIPES = new HashMap<>();
    public static final Map<ItemLike, Integer> CULTIVATE_FUEL_VALUES = new HashMap<>();

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(Fossil.MOD_ID,
            Registry.RECIPE_SERIALIZER_REGISTRY);
    public static final DeferredRegister<RecipeType<?>> TYPES = DeferredRegister.create(Fossil.MOD_ID, Registry.RECIPE_TYPE_REGISTRY);

    public static final RegistrySupplier<RecipeSerializer<AnalyzerRecipe>> ANALYZER_SERIALIZER = SERIALIZERS.register("analyzer",
            () -> AnalyzerRecipe.Serializer.INSTANCE);
    public static final RegistrySupplier<RecipeType<AnalyzerRecipe>> ANALYZER_TYPE = TYPES.register("analyzer", () -> AnalyzerRecipe.Type.INSTANCE);

    public static void register() {
        SERIALIZERS.register();
        TYPES.register();
    }

    public static void initRecipes() {
        AnalyzerRecipe.Builder plantFossil = new AnalyzerRecipe.Builder(ModItems.PlANT_FOSSIL.get())
                .addOutput(Blocks.SAND, 35)
                .addOutput(Items.GREEN_DYE, 20)
                .addOutput(ModItems.FERN_SEED_FOSSIL.get(), 5)
                .addOutput(ModItems.PALAE_SAPLING_FOSSIL.get(), 2.5)
                .addOutput(ModItems.CALAMITES_SAPLING_FOSSIL.get(), 2.5)
                .addOutput(ModItems.SIGILLARIA_SAPLING_FOSSIL.get(), 2.5)
                .addOutput(ModItems.CORDAITES_SAPLING_FOSSIL.get(), 2.5);

        double seedWeight = (100F - plantFossil.total) / (double) PrehistoricPlantType.plantsWithSeeds().size();
        for (PrehistoricPlantType type : PrehistoricPlantType.plantsWithSeeds()) {
            plantFossil.addOutput(type.getFossilPlantSeedItem(), seedWeight);
        }
        registerAnalyzer(plantFossil);
        AnalyzerRecipe.Builder bioFossil = new AnalyzerRecipe.Builder(ModItems.BIO_FOSSIL.get())
                .addOutput(Blocks.SAND, 35)
                .addOutput(Items.BONE_MEAL, 50);
        List<PrehistoricEntityType> bioFossilEntityList = PrehistoricEntityType.getTimePeriodList(TimePeriod.MESOZOIC, TimePeriod.PALEOZOIC);
        double bioFossilDNAChance = 15F / (double) bioFossilEntityList.size();
        for (PrehistoricEntityType type : bioFossilEntityList) {
            bioFossil.addOutput(type.dnaItem, bioFossilDNAChance);
        }
        registerAnalyzer(bioFossil);
        for (PrehistoricEntityType type : PrehistoricEntityType.entitiesWithBones()) {
            registerAnalyzer(new AnalyzerRecipe.Builder(type.legBoneItem)
                    .addOutput(Items.BONE_MEAL, 30)
                    .addOutput(Items.BONE, 35)
                    .addOutput(type.dnaItem, 35));
            registerAnalyzer(new AnalyzerRecipe.Builder(type.armBoneItem)
                    .addOutput(Items.BONE_MEAL, 30)
                    .addOutput(Items.BONE, 35)
                    .addOutput(type.dnaItem, 35));
            registerAnalyzer(new AnalyzerRecipe.Builder(type.footBoneItem)
                    .addOutput(Items.BONE_MEAL, 30)
                    .addOutput(Items.BONE, 35)
                    .addOutput(type.dnaItem, 35));
            registerAnalyzer(new AnalyzerRecipe.Builder(type.skullBoneItem)
                    .addOutput(Items.BONE_MEAL, 30)
                    .addOutput(Items.BONE, 35)
                    .addOutput(type.dnaItem, 35));
            registerAnalyzer(new AnalyzerRecipe.Builder(type.ribcageBoneItem)
                    .addOutput(Items.BONE_MEAL, 30)
                    .addOutput(Items.BONE, 35)
                    .addOutput(type.dnaItem, 35));
            registerAnalyzer(new AnalyzerRecipe.Builder(type.vertebraeBoneItem)
                    .addOutput(Items.BONE_MEAL, 30)
                    .addOutput(Items.BONE, 35)
                    .addOutput(type.dnaItem, 35));
            registerAnalyzer(new AnalyzerRecipe.Builder(type.uniqueBoneItem)
                    .addOutput(Items.BONE_MEAL, 30)
                    .addOutput(Items.BONE, 35)
                    .addOutput(type.dnaItem, 35));
        }
        AnalyzerRecipe.Builder tarDrop = new AnalyzerRecipe.Builder(ModItems.TAR_DROP.get())
                .addOutput(Items.COAL, 20)
                .addOutput(Items.CHARCOAL, 20)
                .addOutput(ModItems.TAR_FOSSIL.get(), 45)
                .addOutput(ModBlocks.VOLCANIC_ROCK.get(), 15);
        registerAnalyzer(tarDrop);
        AnalyzerRecipe.Builder tarFossil = new AnalyzerRecipe.Builder(ModItems.TAR_FOSSIL.get())
                .addOutput(Items.BONE_MEAL, 15)
                .addOutput(ModBlocks.VOLCANIC_ROCK.get(), 30);
        List<PrehistoricEntityType> tarFossilEntityList = PrehistoricEntityType.getTimePeriodList(TimePeriod.CENOZOIC);
        double tarFossilDNAChance = 15F / (double) tarFossilEntityList.size();
        for (PrehistoricEntityType type : tarFossilEntityList) {
            tarFossil.addOutput(type.dnaItem, tarFossilDNAChance);
        }
        registerAnalyzer(tarFossil);

        AnalyzerRecipe.Builder failuresaurusFlesh = new AnalyzerRecipe.Builder(ModItems.FAILURESAURUS_FLESH.get())
                .addOutput(Items.ROTTEN_FLESH, 33);
        double failuresaurusDNAChance = 67F / PrehistoricEntityType.values().length;
        for (PrehistoricEntityType type : PrehistoricEntityType.values()) {
            failuresaurusFlesh.addOutput(type.dnaItem, failuresaurusDNAChance);
            if (type.foodItem != null) {
                registerAnalyzer(new AnalyzerRecipe.Builder(type.foodItem).addOutput(type.dnaItem, 100));
            }
            if (type.eggItem != null) {
                registerAnalyzer(new AnalyzerRecipe.Builder(type.eggItem).addOutput(type.dnaItem, 100));
            }
            if (type.birdEggItem != null) {
                registerAnalyzer(new AnalyzerRecipe.Builder(type.birdEggItem).addOutput(type.dnaItem, 100));
            }
            if (type.cultivatedBirdEggItem != null) {
                registerAnalyzer(new AnalyzerRecipe.Builder(type.cultivatedBirdEggItem).addOutput(type.dnaItem, 100));
            }
            if (type.fishItem != null) {
                registerAnalyzer(new AnalyzerRecipe.Builder(type.fishItem).addOutput(type.dnaItem, 100));
            }
            if (type.embryoItem != null) {
                registerAnalyzer(new AnalyzerRecipe.Builder(type.embryoItem).addOutput(type.dnaItem, 100));
            }
        }
        registerAnalyzer(failuresaurusFlesh);
        AnalyzerRecipe.Builder frozenMeat = new AnalyzerRecipe.Builder(ModItems.FROZEN_MEAT.get())
                .addOutput(Items.CHICKEN, 15)
                .addOutput(Items.MUTTON, 15)
                .addOutput(Items.BEEF, 15)
                .addOutput(Items.PORKCHOP, 15)
                .addOutput(Items.CHICKEN, 15)
                .addOutput(ModItems.TAR_FOSSIL.get(), 20);
        for (PrehistoricEntityType type : tarFossilEntityList) {
            frozenMeat.addOutput(type.dnaItem, tarFossilDNAChance);
        }
        registerAnalyzer(frozenMeat);

        List<Tuple<ItemLike, Double>> outputs = new ArrayList<>();
        outputs.add(new Tuple<>(Blocks.SAND, 20d));
        outputs.add(new Tuple<>(Items.POTATO, 15d));
        outputs.add(new Tuple<>(Items.CARROT, 10d));
        outputs.add(new Tuple<>(Items.BONE_MEAL, 20d));
        outputs.add(new Tuple<>(ModItems.DOMINICAN_AMBER.get(), 1d));
        outputs.add(new Tuple<>(ModItems.FERN_SEED_FOSSIL.get(), 10d));
        outputs.add(new Tuple<>(ModItems.PlANT_FOSSIL.get(), 14d));
        outputs.add(new Tuple<>(ModItems.BIO_FOSSIL.get(), 2d));
        outputs.add(new Tuple<>(ModItems.POTTERY_SHARD.get(), 5d));
        for (Item item : Registry.ITEM) {
            if (item instanceof BlockItem && SifterBlockEntity.getSiftTypeFromStack(new ItemStack(item)) != SifterBlockEntity.EnumSiftType.NONE) {
                registerSifter(item, outputs);
            }
        }
        registerWorktable(ModItems.BROKEN_SWORD.get(), ModItems.ANCIENT_SWORD.get(), ModItems.RELIC_SCRAP.get());
        registerWorktable(ModItems.BROKEN_HELMET.get(), ModItems.ANCIENT_HELMET.get(), ModItems.RELIC_SCRAP.get());
        registerWorktable(ModItems.ANCIENT_SWORD.get(), ModItems.ANCIENT_SWORD.get(), ModItems.RELIC_SCRAP.get());
        registerWorktable(ModItems.ANCIENT_HELMET.get(), ModItems.ANCIENT_HELMET.get(), ModItems.RELIC_SCRAP.get());
        registerWorktable(ModBlocks.AMPHORA_VASE_DAMAGED.get(), ModBlocks.AMPHORA_VASE_RESTORED.get(), ModItems.POTTERY_SHARD.get());
        registerWorktable(ModBlocks.VOLUTE_VASE_DAMAGED.get(), ModBlocks.VOLUTE_VASE_RESTORED.get(), ModItems.POTTERY_SHARD.get());
        registerWorktable(ModBlocks.KYLIX_VASE_DAMAGED.get(), ModBlocks.KYLIX_VASE_RESTORED.get(), ModItems.POTTERY_SHARD.get());
        registerWorktable(ModBlocks.STEVE_FIGURINE_BROKEN.get(), ModBlocks.STEVE_FIGURINE_DAMAGED.get(), ModItems.POTTERY_SHARD.get());
        registerWorktable(ModBlocks.SKELETON_FIGURINE_BROKEN.get(), ModBlocks.SKELETON_FIGURINE_DAMAGED.get(), ModItems.POTTERY_SHARD.get());
        registerWorktable(ModBlocks.ZOMBIE_FIGURINE_BROKEN.get(), ModBlocks.ZOMBIE_FIGURINE_DAMAGED.get(), ModItems.POTTERY_SHARD.get());
        registerWorktable(ModBlocks.ENDERMAN_FIGURINE_BROKEN.get(), ModBlocks.ENDERMAN_FIGURINE_DAMAGED.get(), ModItems.POTTERY_SHARD.get());
        registerWorktable(ModBlocks.PIGZOMBIE_FIGURINE_BROKEN.get(), ModBlocks.PIGZOMBIE_FIGURINE_DAMAGED.get(), ModItems.POTTERY_SHARD.get());
        registerWorktable(ModBlocks.STEVE_FIGURINE_DAMAGED.get(), ModBlocks.STEVE_FIGURINE_PRISTINE.get(), ModItems.POTTERY_SHARD.get());
        registerWorktable(ModBlocks.SKELETON_FIGURINE_DAMAGED.get(), ModBlocks.SKELETON_FIGURINE_PRISTINE.get(), ModItems.POTTERY_SHARD.get());
        registerWorktable(ModBlocks.ZOMBIE_FIGURINE_DAMAGED.get(), ModBlocks.ZOMBIE_FIGURINE_PRISTINE.get(), ModItems.POTTERY_SHARD.get());
        registerWorktable(ModBlocks.ENDERMAN_FIGURINE_DAMAGED.get(), ModBlocks.ENDERMAN_FIGURINE_PRISTINE.get(), ModItems.POTTERY_SHARD.get());
        registerWorktable(ModBlocks.PIGZOMBIE_FIGURINE_DAMAGED.get(), ModBlocks.PIGZOMBIE_FIGURINE_PRISTINE.get(), ModItems.POTTERY_SHARD.get());
        registerWorktable(ModItems.WOODEN_JAVELIN.get(), ModItems.WOODEN_JAVELIN.get(), ModItems.RELIC_SCRAP.get());
        registerWorktable(ModItems.STONE_JAVELIN.get(), ModItems.STONE_JAVELIN.get(), ModItems.RELIC_SCRAP.get());
        registerWorktable(ModItems.IRON_JAVELIN.get(), ModItems.IRON_JAVELIN.get(), ModItems.RELIC_SCRAP.get());
        registerWorktable(ModItems.GOLD_JAVELIN.get(), ModItems.GOLD_JAVELIN.get(), ModItems.RELIC_SCRAP.get());
        registerWorktable(ModItems.DIAMOND_JAVELIN.get(), ModItems.DIAMOND_JAVELIN.get(), ModItems.RELIC_SCRAP.get());
        registerWorktable(ModItems.ANCIENT_JAVELIN.get(), ModItems.ANCIENT_JAVELIN.get(), ModItems.RELIC_SCRAP.get());

        for (PrehistoricEntityType type : PrehistoricEntityType.entitiesWithDNAResult()) {
            registerCultivate(type.dnaItem, type.getDNAResult(), ModItems.BIO_GOO.get());
        }
        registerCultivate(ModItems.FERN_SEED_FOSSIL.get(), ModItems.FERN_SEED.get(), ModItems.BIO_GOO.get());
        registerCultivate(ModItems.CORDAITES_SAPLING_FOSSIL.get(), ModBlocks.CORDAITES_SAPLING.get(), ModItems.BIO_GOO.get());
        registerCultivate(ModItems.SIGILLARIA_SAPLING_FOSSIL.get(), ModBlocks.SIGILLARIA_SAPLING.get(), ModItems.BIO_GOO.get());
        //registerCultivate(ModItems.PALAE_SAPLING_FOSSIL.get(), ModBlocks.PALAE_SAPLING.get(), ModItems.BIO_GOO.get());
        //registerCultivate(ModItems.CALAMITES_SAPLING_FOSSIL.get(), ModBlocks.CALAMITES_SAPLING.get(), ModItems.BIO_GOO.get());
        for (PrehistoricPlantType type : PrehistoricPlantType.plantsWithSeeds()) {
            registerCultivate(type.getFossilPlantSeedItem(), type.getPlantSeedItem(), ModItems.BIO_GOO.get());
        }
    }

    private static void registerSifter(ItemLike item, List<Tuple<ItemLike, Double>> outputs) {
        NavigableMap<Double, ItemStack> map = new TreeMap<>();
        for (Tuple<ItemLike, Double> output : outputs) {
            map.put(output.getB(), new ItemStack(output.getA()));
        }
        SIFTER_RECIPES.put(item.asItem(), new AnalyzerRecipe(new ResourceLocation(Fossil.MOD_ID, item.toString()), Ingredient.of(item), map));
    }

    private static void registerAnalyzer(AnalyzerRecipe.Builder recipe) {
        ANALYZER_RECIPES.put(recipe.item.asItem(), recipe.build());
    }

    private static void registerWorktable(ItemLike item, ItemLike output, ItemLike fuel) {
        WORKTABLE_FUEL_VALUES.putIfAbsent(fuel, 300);
        WORKTABLE_RECIPES.put(item.asItem(), new WorktableRecipe(new ItemStack(item), new ItemStack(output), new ItemStack(fuel)));
    }

    private static void registerCultivate(ItemLike item, ItemLike output, ItemLike fuel) {
        CULTIVATE_FUEL_VALUES.putIfAbsent(fuel, 6000);
        CULTIVATE_RECIPES.put(item, new WorktableRecipe(new ItemStack(item), new ItemStack(output), new ItemStack(fuel)));
    }

    @Nullable
    public static AnalyzerRecipe getSifterRecipeForItem(ItemStack itemStack, Level level) {
        if (!SIFTER_RECIPES.containsKey(itemStack.getItem())) {
            AnalyzerRecipe recipe = (AnalyzerRecipe) level.getRecipeManager().byKey(
                    new ResourceLocation(Fossil.MOD_ID, "sifter/" + itemStack.getItem())).orElse(null);
            SIFTER_RECIPES.put(itemStack.getItem(), recipe);
        }
        return SIFTER_RECIPES.get(itemStack.getItem());
    }

    @Nullable
    public static AnalyzerRecipe getAnalyzerRecipeForItem(ItemStack itemStack, Level level) {
        if (!ANALYZER_RECIPES.containsKey(itemStack.getItem())) {
            AnalyzerRecipe recipe = (AnalyzerRecipe) level.getRecipeManager().byKey(
                    new ResourceLocation(Fossil.MOD_ID, "analyzer/" + itemStack.getItem())).orElse(null);
            ANALYZER_RECIPES.put(itemStack.getItem(), recipe);
        }
        return ANALYZER_RECIPES.get(itemStack.getItem());
    }

    @Nullable
    public static WorktableRecipe getWorktableRecipeForItem(ItemStack itemStack, Level level) {
        if (!WORKTABLE_RECIPES.containsKey(itemStack.getItem())) {
            WorktableRecipe recipe = (WorktableRecipe) level.getRecipeManager().byKey(
                    new ResourceLocation(Fossil.MOD_ID, "worktable/" + itemStack.getItem())).orElse(null);
            WORKTABLE_RECIPES.put(itemStack.getItem(), recipe);
        }
        return WORKTABLE_RECIPES.get(itemStack.getItem());
    }

    @Nullable
    public static WorktableRecipe getCultivateRecipeForItem(ItemStack itemStack, Level level) {
        if (!CULTIVATE_RECIPES.containsKey(itemStack.getItem())) {
            WorktableRecipe recipe = (WorktableRecipe) level.getRecipeManager().byKey(
                    new ResourceLocation(Fossil.MOD_ID, "cultivate/" + itemStack.getItem())).orElse(null);
            CULTIVATE_RECIPES.put(itemStack.getItem(), recipe);
        }
        return CULTIVATE_RECIPES.get(itemStack.getItem());
    }
}
