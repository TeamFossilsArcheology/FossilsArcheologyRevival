package com.github.teamfossilsarcheology.fossil.forge.data.providers;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.block.PrehistoricPlantInfo;
import com.github.teamfossilsarcheology.fossil.block.custom_blocks.VaseBlock;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.VanillaEntityInfo;
import com.github.teamfossilsarcheology.fossil.item.*;
import com.github.teamfossilsarcheology.fossil.tags.ModBlockTags;
import com.github.teamfossilsarcheology.fossil.tags.ModItemTags;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import static com.github.teamfossilsarcheology.fossil.block.ModBlocks.*;
import static com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo.*;
import static com.github.teamfossilsarcheology.fossil.item.ModItems.*;

public class ModItemTagsProvider extends ItemTagsProvider {

    public ModItemTagsProvider(DataGenerator arg, BlockTagsProvider blockTagsProvider, ExistingFileHelper exFileHelper) {
        super(arg, blockTagsProvider, FossilMod.MOD_ID, exFileHelper);
    }

    @Override
    protected void addTags() {
        copy(ModBlockTags.ANCIENT_WOOD_LOGS, ModItemTags.ANCIENT_WOOD_LOGS);
        copy(ModBlockTags.CALAMITES_LOGS, ModItemTags.CALAMITES_LOGS);
        copy(ModBlockTags.CORDAITES_LOGS, ModItemTags.CORDAITES_LOGS);
        copy(ModBlockTags.MUTANT_TREE_LOGS, ModItemTags.MUTANT_TREE_LOGS);
        copy(ModBlockTags.PALM_LOGS, ModItemTags.PALM_LOGS);
        copy(ModBlockTags.SIGILLARIA_LOGS, ModItemTags.SIGILLARIA_LOGS);
        copy(ModBlockTags.TEMPSKYA_LOGS, ModItemTags.TEMPSKYA_LOGS);
        copy(ModBlockTags.FIGURINES, ModItemTags.FIGURINES);
        copy(ModBlockTags.UNBREAKABLE, ModItemTags.FILTER_UNBREAKABLE);
        //Creative Tab Filters
        tag(ModItemTags.FILTER_BONES).addTags(ModItemTags.ALL_BONES);
        tag(ModItemTags.FILTER_DNA).addTags(ModItemTags.DNA, ModItemTags.EMBRYOS);
        var filterEggs = tag(ModItemTags.FILTER_EGGS).addTags(ModItemTags.DINO_EGGS, ModItemTags.FISH_EGGS, ModItemTags.BIRD_EGGS);
        addNullable(filterEggs, ANU_BOSS_SPAWN_EGG, FAILURESAURUS_SPAWN_EGG, SENTRY_PIGLIN_SPAWN_EGG, TAR_SLIME_SPAWN_EGG, ARTIFICIAL_HONEYCOMB);
        var filterMeat = tag(ModItemTags.FILTER_MEAT).addTags(ModItemTags.UNCOOKED_MEAT);
        addNullable(filterMeat, FAILURESAURUS_FLESH, COOKED_CHICKEN_SOUP, RAW_CHICKEN_SOUP, COOKED_EGG);
        var filterPlants = tag(ModItemTags.FILTER_PLANTS).addTags(ModItemTags.FOSSIL_SEEDS, ModItemTags.RESTORED_SEEDS);
        addNullable(filterPlants, CALAMITES_FOSSIL_SAPLING, CORDAITES_FOSSIL_SAPLING, PALM_FOSSIL_SAPLING, SIGILLARIA_FOSSIL_SAPLING, TEMPSKYA_FOSSIL_SAPLING);
        var filterVases = tag(ModItemTags.FILTER_VASES);
        tag(ModItemTags.FILTER_OTHER).add(ELASMOTHERIUM_FUR.get(), MAMMOTH_FUR.get(), THERIZINOSAURUS_DOWN.get(), MAGIC_CONCH.get());
        var filterMachines = tag(ModItemTags.FILTER_MACHINES).add(BIO_FOSSIL.get(), PlANT_FOSSIL.get(), SHALE_FOSSIL.get(), TAR_FOSSIL.get(),
                BIO_GOO.get(), POTTERY_SHARD.get(), TAR_DROP.get(), RELIC_SCRAP.get());
        var filterBuildingBlocks = tag(ModItemTags.FILTER_BUILDING_BLOCKS);
        addNullable(filterBuildingBlocks, SHELL, AMBER_ORE, AMBER_BLOCK, AMBER_CHUNK, AMBER_CHUNK_DOMINICAN, AMBER_CHUNK_MOSQUITO,
                PERMAFROST_BLOCK, SKULL_BLOCK, SKULL_LANTERN, SLIME_TRAIL, ANCIENT_STONE, ANCIENT_STONE_BRICKS,
                ANCIENT_STONE_SLAB, ANCIENT_STONE_STAIRS, ANCIENT_STONE_WALL, CALCITE_FOSSIL, DEEPSLATE_FOSSIL, DRIPSTONE_FOSSIL, RED_SANDSTONE_FOSSIL,
                SANDSTONE_FOSSIL, STONE_FOSSIL, TUFF_FOSSIL, TARRED_DIRT, ICED_DIRT, VOLCANIC_ASH, VOLCANIC_ROCK, VOLCANIC_BRICKS,
                VOLCANIC_BRICK_SLAB, VOLCANIC_BRICK_STAIRS, VOLCANIC_BRICK_WALL, VOLCANIC_TILES, VOLCANIC_TILE_SLAB,
                VOLCANIC_TILE_STAIRS, VOLCANIC_TILE_WALL);
        var filterTools = tag(ModItemTags.FILTER_TOOLS);
        addNullable(filterTools, BROKEN_SWORD, BROKEN_HELMET, ANCIENT_SWORD, ANCIENT_HELMET, FROZEN_MEAT, TOOTH_DAGGER,
                WOODEN_JAVELIN, STONE_JAVELIN, GOLD_JAVELIN, IRON_JAVELIN, DIAMOND_JAVELIN, ANCIENT_JAVELIN, SCARAB_SWORD,
                SCARAB_PICKAXE, SCARAB_AXE, SCARAB_SHOVEL, SCARAB_HOE, BONE_HELMET, BONE_CHESTPLATE, BONE_LEGGINGS, BONE_BOOTS);
        var filterPark = tag(ModItemTags.FILTER_PARK);
        addNullable(filterPark, BUBBLE_BLOWER, FEEDER, SKULL_STICK, STUNTED_ESSENCE, CHICKEN_ESSENCE,
                WHIP, DINOPEDIA);
        for (DyeColor color : DyeColor.values()) {
            filterPark.add(TOY_BALLS.get(color).get());
        }
        for (RegistrySupplier<ToyScratchingPostItem> toy : TOY_SCRATCHING_POSTS.values()) {
            filterPark.add(toy.get());
        }
        for (RegistrySupplier<ToyTetheredLogItem> toy : TOY_TETHERED_LOGS.values()) {
            filterPark.add(toy.get());
        }
        addNullable(filterMachines, ANALYZER, CULTURE_VAT, FEEDER, SIFTER, WORKTABLE, DRUM);
        for (RegistrySupplier<VaseBlock> vase : VASES) {
            filterVases.add(vase.get().asItem());
        }
        var filterTrees = tag(ModItemTags.FILTER_TREES).addTags(ModItemTags.ANCIENT_WOOD_LOGS, ModItemTags.CALAMITES_LOGS, ModItemTags.CORDAITES_LOGS,
                ModItemTags.MUTANT_TREE_LOGS, ModItemTags.PALM_LOGS, ModItemTags.SIGILLARIA_LOGS, ModItemTags.TEMPSKYA_LOGS);
        var filterBuckets = tag(ModItemTags.FILTER_BUCKETS);

        addNullable(filterTrees,
                ANCIENT_WOOD_PLANKS,
                CALAMITES_PLANKS, CALAMITES_LEAVES, CALAMITES_SAPLING,
                CORDAITES_PLANKS, CORDAITES_LEAVES, CORDAITES_SAPLING,
                MUTANT_TREE_PLANKS, MUTANT_TREE_LEAVES, MUTANT_TREE_SAPLING, MUTANT_TREE_VINE,
                PALM_PLANKS, PALM_LEAVES, PALM_SAPLING,
                SIGILLARIA_PLANKS, SIGILLARIA_LEAVES, SIGILLARIA_SAPLING,
                TEMPSKYA_PLANKS, TEMPSKYA_TOP, TEMPSKYA_LEAF, TEMPSKYA_SAPLING);
        for (BlockFamily.Variant variant : BlockFamily.Variant.values()) {
            addNullable(filterTrees,
                    ModRecipeProvider.ANCIENT_WOOD_PLANKS.get(variant),
                    ModRecipeProvider.CALAMITES_PLANKS.get(variant),
                    ModRecipeProvider.CORDAITES_PLANKS.get(variant),
                    ModRecipeProvider.MUTANT_TREE_PLANKS.get(variant),
                    ModRecipeProvider.PALM_PLANKS.get(variant),
                    ModRecipeProvider.SIGILLARIA_PLANKS.get(variant),
                    ModRecipeProvider.TEMPSKYA_PLANKS.get(variant));
        }

        var fossilSeeds = tag(ModItemTags.FOSSIL_SEEDS).add(FERN_SEED_FOSSIL.get());
        var restoredSeeds = tag(ModItemTags.RESTORED_SEEDS).add(FERN_SEED.get());
        for (PrehistoricPlantInfo info : PrehistoricPlantInfo.values()) {
            filterPlants.add(info.getPlantBlock().asItem());
        }
        for (PrehistoricPlantInfo info : PrehistoricPlantInfo.plantsWithSeeds()) {
            fossilSeeds.add(info.getFossilizedPlantSeedItem());
            restoredSeeds.add(info.getPlantSeedItem());
            if (info.berryItem != null && info.berryItem.isPresent()) {
                filterPlants.add(info.berryItem.get());
            }
        }
        tag(ModItemTags.FOSSIL_SAPLINGS).add(CALAMITES_FOSSIL_SAPLING.get(), CORDAITES_FOSSIL_SAPLING.get(), PALM_FOSSIL_SAPLING.get(), SIGILLARIA_FOSSIL_SAPLING.get(), TEMPSKYA_FOSSIL_SAPLING.get());
        tag(ModItemTags.DNA_INSECTS).add(ARTHROPLEURA.dnaItem, MEGANEURA.dnaItem, NAUTILUS.dnaItem);
        tag(ModItemTags.DNA_LIMBLESS).add(ALLIGATOR_GAR.dnaItem, COELACANTH.dnaItem, STURGEON.dnaItem);
        tag(ModItemTags.DNA_PLANTS).addTags(ModItemTags.FOSSIL_SEEDS, ModItemTags.FOSSIL_SAPLINGS);
        var allDNA = tag(ModItemTags.DNA);
        var bonesDNA = tag(ModItemTags.BONES_DNA);
        var meatDNA = tag(ModItemTags.MEAT_DNA);
        var dinoDNA = tag(ModItemTags.DINO_DNA);
        var fishDNA = tag(ModItemTags.FISH_DNA);
        var embryoDNA = tag(ModItemTags.EMBRYO_DNA);
        var embryos = tag(ModItemTags.EMBRYOS);
        var birdEgg = tag(ModItemTags.BIRD_EGGS);
        var dinoEgg = tag(ModItemTags.DINO_EGGS);
        var fishEgg = tag(ModItemTags.FISH_EGGS);
        var allArm = tag(ModItemTags.ARM_BONES);
        var allFoot = tag(ModItemTags.FOOT_BONES);
        var allLeg = tag(ModItemTags.LEG_BONES);
        var allRibcage = tag(ModItemTags.RIBCAGE_BONES);
        var allSkull = tag(ModItemTags.SKULL_BONES);
        var allTails = tag(ModItemTags.TAIL_BONES);
        var allUnique = tag(ModItemTags.UNIQUE_BONES);
        var allVertebrae = tag(ModItemTags.VERTEBRAE_BONES);
        var uncookedMeat = tag(ModItemTags.UNCOOKED_MEAT);
        for (PrehistoricEntityInfo info : PrehistoricEntityInfo.values()) {
            boolean hasDNA = info.dnaItem != null;
            if (hasDNA) {
                allDNA.add(info.dnaItem);
            }
            if (info.embryoItem != null) {
                embryos.add(info.embryoItem);
                if (hasDNA) embryoDNA.add(info.dnaItem);
            }
            if (info.eggItem instanceof DinoEggItem) {
                dinoEgg.add(info.eggItem);
                if (hasDNA) dinoDNA.add(info.dnaItem);
            }
            if (info.birdEggItem instanceof BirdEggItem) {
                birdEgg.add(info.birdEggItem);
            }
            if (info.cultivatedBirdEggItem instanceof BirdEggItem) {
                birdEgg.add(info.cultivatedBirdEggItem);
            }
            if (info.eggItem instanceof FishEggItem) {
                fishEgg.add(info.eggItem);
                if (hasDNA) fishDNA.add(info.dnaItem);
            }
            if (info.armBoneItem != null) {
                allArm.add(info.armBoneItem);
                if (hasDNA) bonesDNA.add(info.dnaItem);
            }
            if (info.footBoneItem != null) {
                allFoot.add(info.footBoneItem);
            }
            if (info.legBoneItem != null) {
                allLeg.add(info.legBoneItem);
            }
            if (info.ribcageBoneItem != null) {
                allRibcage.add(info.ribcageBoneItem);
            }
            if (info.skullBoneItem != null) {
                allSkull.add(info.skullBoneItem);
            }
            if (info.tailBoneItem != null) {
                allTails.add(info.tailBoneItem);
            }
            if (info.uniqueBoneItem != null) {
                allUnique.add(info.uniqueBoneItem);
            }
            if (info.vertebraeBoneItem != null) {
                allVertebrae.add(info.vertebraeBoneItem);
            }
            if (info.foodItem != null) {
                uncookedMeat.add(info.foodItem);
                if (hasDNA) meatDNA.add(info.dnaItem);
            }
            if (info.cookedFoodItem != null) {
                filterMeat.add(info.cookedFoodItem);
            }
            if (info.spawnEggItem != null) {
                filterEggs.add(info.spawnEggItem);
            }
            if (info.bucketItem != null) {
                filterBuckets.add(info.bucketItem);
            }
        }
        for (VanillaEntityInfo info : VanillaEntityInfo.values()) {
            boolean hasDNA = info.dnaItem != null;
            if (hasDNA) {
                allDNA.add(info.dnaItem);
            }
            if (info.embryoItem != null) {
                embryos.add(info.embryoItem);
                if (hasDNA) embryoDNA.add(info.dnaItem);
            }
            if (info.eggItem instanceof FishEggItem) {
                fishEgg.add(info.eggItem);
                if (hasDNA) fishDNA.add(info.dnaItem);
            }
            if (info.cultivatedBirdEggItem instanceof BirdEggItem) {
                birdEgg.add(info.cultivatedBirdEggItem);
            }
        }
        tag(ModItemTags.COOKABLE_EGGS).addTags(ModItemTags.DINO_EGGS).add(Items.EGG);
        tag(ModItemTags.ALL_BONES).addTags(ModItemTags.ARM_BONES, ModItemTags.FOOT_BONES, ModItemTags.LEG_BONES, ModItemTags.RIBCAGE_BONES, ModItemTags.SKULL_BONES, ModItemTags.TAIL_BONES, ModItemTags.UNIQUE_BONES, ModItemTags.VERTEBRAE_BONES);
        tag(ItemTags.MUSIC_DISCS).add(MUSIC_DISC_ANU.get(), MUSIC_DISC_BONES.get(), MUSIC_DISC_DISCOVERY.get(), MUSIC_DISC_SCARAB.get());

        tag(ModItemTags.SIFTER_INPUTS).addTags(ItemTags.SAND, ItemTags.DIRT).add(DENSE_SAND.get().asItem(),
                VOLCANIC_ASH.get().asItem(), Blocks.GRAVEL.asItem(), Blocks.SOUL_SAND.asItem());
    }

    public void addNullable(TagAppender<Item> tag, RegistrySupplier<? extends ItemLike>... blocks) {
        for (RegistrySupplier<? extends ItemLike> block : blocks) {
            if (block.isPresent()) {
                tag.add(block.get().asItem());
            }
        }
    }

    public void addNullable(TagAppender<Item> tag, Block... blocks) {
        for (Block block : blocks) {
            if (block != null) {
                tag.add(block.asItem());
            }
        }
    }

    @Override
    public @NotNull String getName() {
        return "Fossil Item Tags";
    }
}
