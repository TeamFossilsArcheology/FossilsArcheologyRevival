package com.fossil.fossil.recipe;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.block.ModBlocks;
import com.fossil.fossil.block.PrehistoricPlantType;
import com.fossil.fossil.block.entity.CustomBlockEntity;
import com.fossil.fossil.block.entity.SifterBlockEntity;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
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
    public static final Map<Item, AnalyzerRecipe> ANALYZER_RECIPES = new HashMap<>();
    public static final Map<Item, AnalyzerRecipe> SIFTER_RECIPES = new HashMap<>();
    public static final Map<Item, WorktableRecipe> WORKTABLE_RECIPES = new HashMap<>();
    public static final Map<ItemLike, Integer> WORKTABLE_FUEL_VALUES = new HashMap<>();
    public static final Map<ItemLike, WorktableRecipe> CULTURE_VAT_RECIPES = new HashMap<>();
    public static final Map<ItemLike, Integer> CULTURE_VAT_FUEL_VALUES = new HashMap<>();

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
        for (PrehistoricEntityType type : PrehistoricEntityType.entitiesWithBones()) {
            registerAnalyzer(new AnalyzerRecipe.Builder(type.armBoneItem)
                    .addOutput(Items.BONE_MEAL, 30)
                    .addOutput(Items.BONE, 35)
                    .addOutput(type.dnaItem, 35));
            registerAnalyzer(new AnalyzerRecipe.Builder(type.footBoneItem)
                    .addOutput(Items.BONE_MEAL, 30)
                    .addOutput(Items.BONE, 35)
                    .addOutput(type.dnaItem, 35));
            registerAnalyzer(new AnalyzerRecipe.Builder(type.legBoneItem)
                    .addOutput(Items.BONE_MEAL, 30)
                    .addOutput(Items.BONE, 35)
                    .addOutput(type.dnaItem, 35));
            registerAnalyzer(new AnalyzerRecipe.Builder(type.legBoneItem)
                    .addOutput(Items.BONE_MEAL, 30)
                    .addOutput(Items.BONE, 35)
                    .addOutput(type.dnaItem, 35));
            registerAnalyzer(new AnalyzerRecipe.Builder(type.ribcageBoneItem)
                    .addOutput(Items.BONE_MEAL, 30)
                    .addOutput(Items.BONE, 35)
                    .addOutput(type.dnaItem, 35));
            registerAnalyzer(new AnalyzerRecipe.Builder(type.skullBoneItem)
                    .addOutput(Items.BONE_MEAL, 30)
                    .addOutput(Items.BONE, 35)
                    .addOutput(type.dnaItem, 35));
            registerAnalyzer(new AnalyzerRecipe.Builder(type.tailBoneItem)
                    .addOutput(Items.BONE_MEAL, 30)
                    .addOutput(Items.BONE, 35)
                    .addOutput(type.dnaItem, 35));
            registerAnalyzer(new AnalyzerRecipe.Builder(type.uniqueBoneItem)
                    .addOutput(Items.BONE_MEAL, 30)
                    .addOutput(Items.BONE, 35)
                    .addOutput(type.dnaItem, 35));
            registerAnalyzer(new AnalyzerRecipe.Builder(type.vertebraeBoneItem)
                    .addOutput(Items.BONE_MEAL, 30)
                    .addOutput(Items.BONE, 35)
                    .addOutput(type.dnaItem, 35));
        }
        for (PrehistoricEntityType type : PrehistoricEntityType.values()) {
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
            if (type.embryoItem != null) {
                registerAnalyzer(new AnalyzerRecipe.Builder(type.embryoItem).addOutput(type.dnaItem, 100));
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
        registerWorktable(ModBlocks.AMPHORA_VASE_DAMAGED.get(), ModBlocks.AMPHORA_VASE_RESTORED.get(), ModItems.POTTERY_SHARD.get());
        registerWorktable(ModBlocks.VOLUTE_VASE_DAMAGED.get(), ModBlocks.VOLUTE_VASE_RESTORED.get(), ModItems.POTTERY_SHARD.get());
        registerWorktable(ModBlocks.KYLIX_VASE_DAMAGED.get(), ModBlocks.KYLIX_VASE_RESTORED.get(), ModItems.POTTERY_SHARD.get());
        registerWorktable(ModBlocks.ANU_FIGURINE_DESTROYED.get(), ModBlocks.ANU_FIGURINE_RESTORED.get(), ModItems.POTTERY_SHARD.get());
        registerWorktable(ModBlocks.ENDERMAN_FIGURINE_DESTROYED.get(), ModBlocks.ENDERMAN_FIGURINE_RESTORED.get(), ModItems.POTTERY_SHARD.get());
        registerWorktable(ModBlocks.PIGLIN_FIGURINE_DESTROYED.get(), ModBlocks.PIGLIN_FIGURINE_RESTORED.get(), ModItems.POTTERY_SHARD.get());
        registerWorktable(ModBlocks.SKELETON_FIGURINE_DESTROYED.get(), ModBlocks.SKELETON_FIGURINE_RESTORED.get(), ModItems.POTTERY_SHARD.get());
        registerWorktable(ModBlocks.STEVE_FIGURINE_DESTROYED.get(), ModBlocks.STEVE_FIGURINE_RESTORED.get(), ModItems.POTTERY_SHARD.get());
        registerWorktable(ModBlocks.ZOMBIE_FIGURINE_DESTROYED.get(), ModBlocks.ZOMBIE_FIGURINE_RESTORED.get(), ModItems.POTTERY_SHARD.get());
        registerWorktable(ModBlocks.ANU_FIGURINE_RESTORED.get(), ModBlocks.ANU_FIGURINE_PRISTINE.get(), ModItems.POTTERY_SHARD.get());
        registerWorktable(ModBlocks.ENDERMAN_FIGURINE_RESTORED.get(), ModBlocks.ENDERMAN_FIGURINE_PRISTINE.get(), ModItems.POTTERY_SHARD.get());
        registerWorktable(ModBlocks.PIGLIN_FIGURINE_RESTORED.get(), ModBlocks.PIGLIN_FIGURINE_PRISTINE.get(), ModItems.POTTERY_SHARD.get());
        registerWorktable(ModBlocks.SKELETON_FIGURINE_RESTORED.get(), ModBlocks.SKELETON_FIGURINE_PRISTINE.get(), ModItems.POTTERY_SHARD.get());
        registerWorktable(ModBlocks.STEVE_FIGURINE_RESTORED.get(), ModBlocks.STEVE_FIGURINE_PRISTINE.get(), ModItems.POTTERY_SHARD.get());
        registerWorktable(ModBlocks.ZOMBIE_FIGURINE_RESTORED.get(), ModBlocks.ZOMBIE_FIGURINE_PRISTINE.get(), ModItems.POTTERY_SHARD.get());
        registerWorktable(ModItems.BROKEN_SWORD.get(), ModItems.ANCIENT_SWORD.get(), ModItems.RELIC_SCRAP.get());
        registerWorktable(ModItems.BROKEN_HELMET.get(), ModItems.ANCIENT_HELMET.get(), ModItems.RELIC_SCRAP.get());
        registerWorktable(ModItems.ANCIENT_SWORD.get(), ModItems.ANCIENT_SWORD.get(), ModItems.RELIC_SCRAP.get());
        registerWorktable(ModItems.ANCIENT_HELMET.get(), ModItems.ANCIENT_HELMET.get(), ModItems.RELIC_SCRAP.get());
        registerWorktable(ModItems.SCARAB_SWORD.get(), ModItems.SCARAB_SWORD.get(), ModItems.RELIC_SCRAP.get());
        registerWorktable(ModItems.SCARAB_PICKAXE.get(), ModItems.SCARAB_PICKAXE.get(), ModItems.RELIC_SCRAP.get());
        registerWorktable(ModItems.SCARAB_AXE.get(), ModItems.SCARAB_AXE.get(), ModItems.RELIC_SCRAP.get());
        registerWorktable(ModItems.SCARAB_SHOVEL.get(), ModItems.SCARAB_SHOVEL.get(), ModItems.RELIC_SCRAP.get());
        registerWorktable(ModItems.SCARAB_HOE.get(), ModItems.SCARAB_HOE.get(), ModItems.RELIC_SCRAP.get());
        registerWorktable(ModItems.WOODEN_JAVELIN.get(), ModItems.WOODEN_JAVELIN.get(), ModItems.RELIC_SCRAP.get());
        registerWorktable(ModItems.STONE_JAVELIN.get(), ModItems.STONE_JAVELIN.get(), ModItems.RELIC_SCRAP.get());
        registerWorktable(ModItems.IRON_JAVELIN.get(), ModItems.IRON_JAVELIN.get(), ModItems.RELIC_SCRAP.get());
        registerWorktable(ModItems.GOLD_JAVELIN.get(), ModItems.GOLD_JAVELIN.get(), ModItems.RELIC_SCRAP.get());
        registerWorktable(ModItems.DIAMOND_JAVELIN.get(), ModItems.DIAMOND_JAVELIN.get(), ModItems.RELIC_SCRAP.get());
        registerWorktable(ModItems.ANCIENT_JAVELIN.get(), ModItems.ANCIENT_JAVELIN.get(), ModItems.RELIC_SCRAP.get());

        for (PrehistoricEntityType type : PrehistoricEntityType.values()) {
            if (type.dnaItem != null) {
                registerCultureVat(type.dnaItem, type.getDNAResult(), ModItems.BIO_GOO.get());
            }
        }
        for (VanillaEntityInfo info : VanillaEntityInfo.values()) {
            if (info.dnaItem != null) {
                registerCultureVat(info.dnaItem, info.getDNAResult(), ModItems.BIO_GOO.get());
            }
        }
        registerCultureVat(ModItems.FERN_SEED_FOSSIL.get(), ModItems.FERN_SEED.get(), ModItems.BIO_GOO.get());
        registerCultureVat(ModItems.CALAMITES_SAPLING_FOSSIL.get(), ModBlocks.CALAMITES_SAPLING.get(), ModItems.BIO_GOO.get());
        registerCultureVat(ModItems.CORDAITES_SAPLING_FOSSIL.get(), ModBlocks.CORDAITES_SAPLING.get(), ModItems.BIO_GOO.get());
        registerCultureVat(ModItems.PALM_SAPLING_FOSSIL.get(), ModBlocks.PALM_SAPLING.get(), ModItems.BIO_GOO.get());
        registerCultureVat(ModItems.SIGILLARIA_SAPLING_FOSSIL.get(), ModBlocks.SIGILLARIA_SAPLING.get(), ModItems.BIO_GOO.get());
        registerCultureVat(ModItems.TEMPSKYA_SAPLING_FOSSIL.get(), ModBlocks.TEMPSKYA_SAPLING.get(), ModItems.BIO_GOO.get());
        for (PrehistoricPlantType type : PrehistoricPlantType.plantsWithSeeds()) {
            registerCultureVat(type.getFossilizedPlantSeedItem(), type.getPlantSeedItem(), ModItems.BIO_GOO.get());
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

    private static void registerCultureVat(ItemLike item, ItemLike output, ItemLike fuel) {
        CULTURE_VAT_FUEL_VALUES.putIfAbsent(fuel, Version.debugEnabled() ? 300 : 6000);
        CULTURE_VAT_RECIPES.put(item, new WorktableRecipe(new ItemStack(item), new ItemStack(output), new ItemStack(fuel)));
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
    public static AnalyzerRecipe getAnalyzerRecipeForItem(CustomBlockEntity container, Level level) {
        if (ANALYZER_RECIPES.containsKey(container.getItem(0).getItem())) {
            return ANALYZER_RECIPES.get(container.getItem(0).getItem());
        }
        Optional<AnalyzerRecipe> optional = level.getRecipeManager().getRecipeFor(ANALYZER_TYPE.get(), container, level);
        return optional.orElse(null);
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
    public static WorktableRecipe getCultureVatRecipeForItem(ItemStack itemStack, Level level) {
        if (!CULTURE_VAT_RECIPES.containsKey(itemStack.getItem())) {
            WorktableRecipe recipe = (WorktableRecipe) level.getRecipeManager().byKey(
                    new ResourceLocation(Fossil.MOD_ID, "culture_vat/" + itemStack.getItem())).orElse(null);
            CULTURE_VAT_RECIPES.put(itemStack.getItem(), recipe);
        }
        return CULTURE_VAT_RECIPES.get(itemStack.getItem());
    }
}
