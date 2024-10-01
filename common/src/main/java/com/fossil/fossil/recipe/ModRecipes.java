package com.fossil.fossil.recipe;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.block.ModBlocks;
import com.fossil.fossil.block.entity.CustomBlockEntity;
import com.fossil.fossil.block.entity.SifterBlockEntity;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.fossil.fossil.entity.prehistoric.base.VanillaEntityInfo;
import com.fossil.fossil.item.ModItems;
import com.fossil.fossil.util.Version;
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
    private static final Map<Item, AnalyzerRecipe> ANALYZER_RECIPES = new HashMap<>();
    private static final Map<Item, AnalyzerRecipe> SIFTER_RECIPES = new HashMap<>();
    private static final Map<ItemLike, Integer> WORKTABLE_FUEL_VALUES = new HashMap<>();
    private static final Map<ItemLike, Integer> CULTURE_VAT_FUEL_VALUES = new HashMap<>();

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(Fossil.MOD_ID,
            Registry.RECIPE_SERIALIZER_REGISTRY);
    public static final DeferredRegister<RecipeType<?>> TYPES = DeferredRegister.create(Fossil.MOD_ID, Registry.RECIPE_TYPE_REGISTRY);

    public static final RegistrySupplier<RecipeSerializer<AnalyzerRecipe>> ANALYZER_SERIALIZER = SERIALIZERS.register("analyzer",
            () -> AnalyzerRecipe.Serializer.INSTANCE);
    public static final RegistrySupplier<RecipeSerializer<CultureVatRecipe>> CULTURE_VAT_SERIALIZER = SERIALIZERS.register("culture_vat",
            () -> CultureVatRecipe.Serializer.INSTANCE);
    public static final RegistrySupplier<RecipeSerializer<WorktableRecipe>> WORKTABLE_SERIALIZER = SERIALIZERS.register("worktable",
            () -> WorktableRecipe.Serializer.INSTANCE);
    public static final RegistrySupplier<RecipeType<AnalyzerRecipe>> ANALYZER_TYPE = TYPES.register("analyzer", () -> AnalyzerRecipe.Type.INSTANCE);
    public static final RegistrySupplier<RecipeType<CultureVatRecipe>> CULTURE_VAT_TYPE = TYPES.register("culture_vat", () -> CultureVatRecipe.Type.INSTANCE);
    public static final RegistrySupplier<RecipeType<WorktableRecipe>> WORKTABLE_TYPE = TYPES.register("worktable", () -> WorktableRecipe.Type.INSTANCE);

    public static void register() {
        SERIALIZERS.register();
        TYPES.register();
    }

    public static void initRecipes() {
        for (PrehistoricEntityInfo info : PrehistoricEntityInfo.entitiesWithBones()) {
            registerAnalyzer(new AnalyzerRecipe.Builder(info.armBoneItem)
                    .addOutput(Items.BONE_MEAL, 30)
                    .addOutput(Items.BONE, 35)
                    .addOutput(info.dnaItem, 35));
            registerAnalyzer(new AnalyzerRecipe.Builder(info.footBoneItem)
                    .addOutput(Items.BONE_MEAL, 30)
                    .addOutput(Items.BONE, 35)
                    .addOutput(info.dnaItem, 35));
            registerAnalyzer(new AnalyzerRecipe.Builder(info.legBoneItem)
                    .addOutput(Items.BONE_MEAL, 30)
                    .addOutput(Items.BONE, 35)
                    .addOutput(info.dnaItem, 35));
            registerAnalyzer(new AnalyzerRecipe.Builder(info.legBoneItem)
                    .addOutput(Items.BONE_MEAL, 30)
                    .addOutput(Items.BONE, 35)
                    .addOutput(info.dnaItem, 35));
            registerAnalyzer(new AnalyzerRecipe.Builder(info.ribcageBoneItem)
                    .addOutput(Items.BONE_MEAL, 30)
                    .addOutput(Items.BONE, 35)
                    .addOutput(info.dnaItem, 35));
            registerAnalyzer(new AnalyzerRecipe.Builder(info.skullBoneItem)
                    .addOutput(Items.BONE_MEAL, 30)
                    .addOutput(Items.BONE, 35)
                    .addOutput(info.dnaItem, 35));
            registerAnalyzer(new AnalyzerRecipe.Builder(info.tailBoneItem)
                    .addOutput(Items.BONE_MEAL, 30)
                    .addOutput(Items.BONE, 35)
                    .addOutput(info.dnaItem, 35));
            registerAnalyzer(new AnalyzerRecipe.Builder(info.uniqueBoneItem)
                    .addOutput(Items.BONE_MEAL, 30)
                    .addOutput(Items.BONE, 35)
                    .addOutput(info.dnaItem, 35));
            registerAnalyzer(new AnalyzerRecipe.Builder(info.vertebraeBoneItem)
                    .addOutput(Items.BONE_MEAL, 30)
                    .addOutput(Items.BONE, 35)
                    .addOutput(info.dnaItem, 35));
        }
        registerAnalyzer(new AnalyzerRecipe.Builder(PrehistoricEntityInfo.MEGALODON.uniqueBoneItem)
                .addOutput(Items.BONE_MEAL, 30)
                .addOutput(Items.BONE, 35)
                .addOutput(PrehistoricEntityInfo.MEGALODON.dnaItem, 35));
        for (PrehistoricEntityInfo info : PrehistoricEntityInfo.values()) {
            if (info.foodItem != null) {
                registerAnalyzer(new AnalyzerRecipe.Builder(info.foodItem).addOutput(info.dnaItem, 100));
            }
            if (info.eggItem != null) {
                registerAnalyzer(new AnalyzerRecipe.Builder(info.eggItem).addOutput(info.dnaItem, 100));
            }
            if (info.birdEggItem != null) {
                registerAnalyzer(new AnalyzerRecipe.Builder(info.birdEggItem).addOutput(info.dnaItem, 100));
            }
            if (info.cultivatedBirdEggItem != null) {
                registerAnalyzer(new AnalyzerRecipe.Builder(info.cultivatedBirdEggItem).addOutput(info.dnaItem, 100));
            }
            if (info.embryoItem != null) {
                registerAnalyzer(new AnalyzerRecipe.Builder(info.embryoItem).addOutput(info.dnaItem, 100));
            }
        }
        for (VanillaEntityInfo info : VanillaEntityInfo.values()) {
            if (info.eggItem != null) {
                registerAnalyzer(new AnalyzerRecipe.Builder(info.eggItem).addOutput(info.dnaItem, 100));
            }
            if (info.cultivatedBirdEggItem != null) {
                registerAnalyzer(new AnalyzerRecipe.Builder(info.cultivatedBirdEggItem).addOutput(info.dnaItem, 100));
            }
            if (info.embryoItem != null) {
                registerAnalyzer(new AnalyzerRecipe.Builder(info.embryoItem).addOutput(info.dnaItem, 100));
            }
        }

        List<Tuple<ItemLike, Double>> outputs = new ArrayList<>();
        outputs.add(new Tuple<>(Blocks.SAND, 20d));
        outputs.add(new Tuple<>(Items.POTATO, 15d));
        outputs.add(new Tuple<>(Items.CARROT, 10d));
        outputs.add(new Tuple<>(Items.BONE_MEAL, 20d));
        outputs.add(new Tuple<>(ModBlocks.AMBER_CHUNK_DOMINICAN.get(), 1d));
        outputs.add(new Tuple<>(ModItems.FERN_SEED_FOSSIL.get(), 10d));
        outputs.add(new Tuple<>(ModItems.PlANT_FOSSIL.get(), 14d));
        outputs.add(new Tuple<>(ModItems.BIO_FOSSIL.get(), 2d));
        outputs.add(new Tuple<>(ModItems.POTTERY_SHARD.get(), 5d));
        for (Item item : Registry.ITEM) {
            if (item instanceof BlockItem && SifterBlockEntity.getSiftTypeFromStack(new ItemStack(item)) != SifterBlockEntity.EnumSiftType.NONE) {
                registerSifter(item, outputs);
            }
        }

        WORKTABLE_FUEL_VALUES.put(ModItems.POTTERY_SHARD.get(), 300);
        WORKTABLE_FUEL_VALUES.put(ModItems.RELIC_SCRAP.get(), 300);
        CULTURE_VAT_FUEL_VALUES.put(ModItems.BIO_GOO.get(), Version.debugEnabled() ? 1000 : 6000);
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

    @Nullable
    public static AnalyzerRecipe getSifterRecipeForItem(ItemStack itemStack, Level level) {
        return SIFTER_RECIPES.computeIfAbsent(itemStack.getItem(), item -> (AnalyzerRecipe) level.getRecipeManager().byKey(
                new ResourceLocation(Fossil.MOD_ID, "sifter/" + itemStack.getItem())).orElse(null));
    }

    @Nullable
    public static AnalyzerRecipe getAnalyzerRecipeForItem(CustomBlockEntity container, Level level) {
        return ANALYZER_RECIPES.computeIfAbsent(container.getItem(0).getItem(), item ->
                level.getRecipeManager().getRecipeFor(ANALYZER_TYPE.get(), container, level).orElse(null));
    }

    @Nullable
    public static WorktableRecipe getWorktableRecipeForItem(WithFuelRecipe.ContainerWithAnyFuel container, Level level) {
        return level.getRecipeManager().getRecipeFor(WORKTABLE_TYPE.get(), container, level).orElse(null);
    }

    @Nullable
    public static CultureVatRecipe getCultureVatRecipeForItem(WithFuelRecipe.ContainerWithAnyFuel container, Level level) {
        return level.getRecipeManager().getRecipeFor(CULTURE_VAT_TYPE.get(), container, level).orElse(null);
    }

    public static boolean isWorktableFuel(ItemLike itemLike) {
        return WORKTABLE_FUEL_VALUES.containsKey(itemLike);
    }

    public static int getWorktableFuelValue(ItemLike itemLike) {
        return WORKTABLE_FUEL_VALUES.getOrDefault(itemLike, 0);
    }

    public static boolean isCultureVatFuel(ItemLike itemLike) {
        return CULTURE_VAT_FUEL_VALUES.containsKey(itemLike);
    }

    public static int getCultureVatFuelValue(ItemLike itemLike) {
        return CULTURE_VAT_FUEL_VALUES.getOrDefault(itemLike, 0);
    }
}
