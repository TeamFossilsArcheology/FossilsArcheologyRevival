package com.fossil.fossil.recipe;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.block.ModBlocks;
import com.fossil.fossil.block.entity.SifterBlockEntity;
import com.fossil.fossil.item.ModItems;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
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

import java.util.HashMap;
import java.util.Map;
import java.util.NavigableMap;
import java.util.TreeMap;

public class ModRecipes {
    public static final Map<ItemLike, AnalyzerRecipe> ANALYZER_RECIPES = new HashMap<>();
    public static final Map<ItemLike, AnalyzerRecipe> SIFTER_RECIPES = new HashMap<>();
    public static final Map<ItemLike, WorktableRecipe> WORKTABLE_RECIPES = new HashMap<>();
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
        registerAnalyzer(Items.BEEF, new ItemLike[]{Items.BLUE_DYE, Items.ACACIA_LOG}, new Double[]{80.0, 20.0});
        var outputs = new ItemLike[]{Blocks.SAND, Items.POTATO, Items.CARROT, Items.BONE_MEAL};
        var weights = new Double[]{25d, 15d, 10d, 20d};
        for (Item item : Registry.ITEM) {
            if (item instanceof BlockItem && SifterBlockEntity.getSiftTypeFromStack(new ItemStack(item)) != SifterBlockEntity.EnumSiftType.NONE) {
                registerSifter(item, outputs, weights);
            }
        }
        registerWorktable(ModBlocks.AMPHORA_VASE_DAMAGED.get().asItem(), ModBlocks.AMPHORA_VASE_RESTORED.get(), ModItems.POTTERY_SHARD.get());
        registerWorktable(ModBlocks.VOLUTE_VASE_DAMAGED.get().asItem(), ModBlocks.VOLUTE_VASE_RESTORED.get(), ModItems.POTTERY_SHARD.get());
        registerWorktable(ModBlocks.KYLIX_VASE_DAMAGED.get().asItem(), ModBlocks.KYLIX_VASE_RESTORED.get(), ModItems.POTTERY_SHARD.get());
        registerCultivate(ModBlocks.AMBER_ORE.get().asItem(), ModItems.AMBER.get(), ModItems.BIO_GOO.get());
    }

    private static void registerSifter(ItemLike item, ItemLike[] outputs, Double[] weights) {
        NavigableMap<Double, ItemStack> map = new TreeMap<>();
        for (int i = 0; i < outputs.length; i++) {
            map.put(weights[i], new ItemStack(outputs[i]));
        }
        SIFTER_RECIPES.put(item, new AnalyzerRecipe(new ResourceLocation(Fossil.MOD_ID, item.toString()), Ingredient.of(item), map));
    }

    private static void registerAnalyzer(ItemLike item, ItemLike[] outputs, Double[] weights) {
        NavigableMap<Double, ItemStack> map = new TreeMap<>();
        for (int i = 0; i < outputs.length; i++) {
            map.put(weights[i], new ItemStack(outputs[i]));
        }
        ANALYZER_RECIPES.put(item, new AnalyzerRecipe(new ResourceLocation(Fossil.MOD_ID, item.toString()), Ingredient.of(item), map));
    }

    private static void registerWorktable(ItemLike item, ItemLike output, ItemLike fuel) {
        WORKTABLE_FUEL_VALUES.putIfAbsent(fuel, 300);
        WORKTABLE_RECIPES.put(item, new WorktableRecipe(new ItemStack(item), new ItemStack(output), new ItemStack(fuel)));
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
