package com.fossil.fossil.recipe;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.block.entity.CustomBlockEntity;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.fossil.fossil.entity.prehistoric.base.VanillaEntityInfo;
import com.fossil.fossil.item.ModItems;
import com.fossil.fossil.util.Version;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.RecipeSerializer;
import net.minecraft.world.item.crafting.RecipeType;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;

public class ModRecipes {
    private static final Map<Item, AnalyzerRecipe> ANALYZER_RECIPES = new HashMap<>();
    private static final Map<ItemLike, Integer> WORKTABLE_FUEL_VALUES = new HashMap<>();
    private static final Map<ItemLike, Integer> CULTURE_VAT_FUEL_VALUES = new HashMap<>();

    public static final DeferredRegister<RecipeSerializer<?>> SERIALIZERS = DeferredRegister.create(Fossil.MOD_ID,
            Registry.RECIPE_SERIALIZER_REGISTRY);
    public static final DeferredRegister<RecipeType<?>> TYPES = DeferredRegister.create(Fossil.MOD_ID, Registry.RECIPE_TYPE_REGISTRY);

    public static final RegistrySupplier<RecipeSerializer<AnalyzerRecipe>> ANALYZER_SERIALIZER = SERIALIZERS.register("analyzer",
            () -> AnalyzerRecipe.Serializer.INSTANCE);
    public static final RegistrySupplier<RecipeSerializer<CultureVatRecipe>> CULTURE_VAT_SERIALIZER = SERIALIZERS.register("culture_vat",
            () -> CultureVatRecipe.Serializer.INSTANCE);
    public static final RegistrySupplier<RecipeSerializer<SifterRecipe>> SIFTER_SERIALIZER = SERIALIZERS.register("sifter",
            () -> SifterRecipe.Serializer.INSTANCE);
    public static final RegistrySupplier<RecipeSerializer<WorktableRecipe>> WORKTABLE_SERIALIZER = SERIALIZERS.register("worktable",
            () -> WorktableRecipe.Serializer.INSTANCE);
    public static final RegistrySupplier<RecipeType<AnalyzerRecipe>> ANALYZER_TYPE = TYPES.register("analyzer", () -> AnalyzerRecipe.Type.INSTANCE);
    public static final RegistrySupplier<RecipeType<CultureVatRecipe>> CULTURE_VAT_TYPE = TYPES.register("culture_vat", () -> CultureVatRecipe.Type.INSTANCE);
    public static final RegistrySupplier<RecipeType<SifterRecipe>> SIFTER_TYPE = TYPES.register("sifter", () -> SifterRecipe.Type.INSTANCE);
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

        WORKTABLE_FUEL_VALUES.put(ModItems.POTTERY_SHARD.get(), 300);
        WORKTABLE_FUEL_VALUES.put(ModItems.RELIC_SCRAP.get(), 300);
        CULTURE_VAT_FUEL_VALUES.put(ModItems.BIO_GOO.get(), Version.debugEnabled() ? 1000 : 6000);
    }

    private static void registerAnalyzer(AnalyzerRecipe.Builder recipe) {
        ANALYZER_RECIPES.put(recipe.item.asItem(), recipe.build());
    }

    @Nullable
    public static SifterRecipe getSifterRecipeForItem(CustomBlockEntity container, Level level) {
        return level.getRecipeManager().getRecipeFor(SIFTER_TYPE.get(), container, level).orElse(null);
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
