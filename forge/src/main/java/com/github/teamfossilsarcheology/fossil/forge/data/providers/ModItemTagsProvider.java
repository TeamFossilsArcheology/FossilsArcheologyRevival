package com.github.teamfossilsarcheology.fossil.forge.data.providers;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.block.PrehistoricPlantInfo;
import com.github.teamfossilsarcheology.fossil.block.custom_blocks.VaseBlock;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.VanillaEntityInfo;
import com.github.teamfossilsarcheology.fossil.item.*;
import com.github.teamfossilsarcheology.fossil.tags.ModBlockTags;
import com.github.teamfossilsarcheology.fossil.tags.ModItemTags;
import com.github.teamfossilsarcheology.fossil.util.ModConstants;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.PackOutput;
import net.minecraft.data.tags.IntrinsicHolderTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.fml.ModList;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;
import java.util.function.Supplier;

import static com.github.teamfossilsarcheology.fossil.block.ModBlocks.*;
import static com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo.*;
import static com.github.teamfossilsarcheology.fossil.item.ModItems.*;

public class ModItemTagsProvider extends ItemTagsProvider {


    public ModItemTagsProvider(PackOutput arg, CompletableFuture<HolderLookup.Provider> lookup, CompletableFuture<TagLookup<Block>> tagLookup, @Nullable ExistingFileHelper existingFileHelper) {
        super(arg, lookup, tagLookup, FossilMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.@NotNull Provider provider) {
        copy(ModBlockTags.ANCIENT_WOOD_LOGS, ModItemTags.ANCIENT_WOOD_LOGS);
        copy(ModBlockTags.CALAMITES_LOGS, ModItemTags.CALAMITES_LOGS);
        copy(ModBlockTags.CORDAITES_LOGS, ModItemTags.CORDAITES_LOGS);
        copy(ModBlockTags.MUTANT_TREE_LOGS, ModItemTags.MUTANT_TREE_LOGS);
        copy(ModBlockTags.PALM_LOGS, ModItemTags.PALM_LOGS);
        copy(ModBlockTags.SIGILLARIA_LOGS, ModItemTags.SIGILLARIA_LOGS);
        copy(ModBlockTags.TEMPSKYA_LOGS, ModItemTags.TEMPSKYA_LOGS);
        copy(ModBlockTags.FIGURINES, ModItemTags.FIGURINES);
        copy(ModBlockTags.UNBREAKABLE, ModItemTags.FILTER_UNBREAKABLE);
        //Vanilla item tags
        addTag(ItemTags.PLANKS, ANCIENT_WOOD_PLANKS, CALAMITES_PLANKS, CORDAITES_PLANKS, MUTANT_TREE_PLANKS, PALM_PLANKS, SIGILLARIA_PLANKS, TEMPSKYA_PLANKS);
        addTag(ItemTags.WOODEN_BUTTONS, CALAMITES_BUTTON, CORDAITES_BUTTON, MUTANT_TREE_BUTTON, PALM_BUTTON, SIGILLARIA_BUTTON, TEMPSKYA_BUTTON);
        addTag(ItemTags.WOODEN_DOORS, CALAMITES_DOOR, CORDAITES_DOOR, MUTANT_TREE_DOOR, PALM_DOOR, SIGILLARIA_DOOR, TEMPSKYA_DOOR);
        addTag(ItemTags.WOODEN_STAIRS, ANCIENT_WOOD_STAIRS, CALAMITES_STAIRS, CORDAITES_STAIRS, MUTANT_TREE_STAIRS, PALM_STAIRS, SIGILLARIA_STAIRS, TEMPSKYA_STAIRS);
        addTag(ItemTags.WOODEN_SLABS, ANCIENT_WOOD_SLAB, CALAMITES_SLAB, CORDAITES_SLAB, MUTANT_TREE_SLAB, PALM_SLAB, SIGILLARIA_SLAB, TEMPSKYA_SLAB);
        addTag(ItemTags.WOODEN_FENCES, CALAMITES_FENCE, CORDAITES_FENCE, MUTANT_TREE_FENCE, PALM_FENCE, SIGILLARIA_FENCE, TEMPSKYA_FENCE);
        addTag(ItemTags.WOODEN_PRESSURE_PLATES, CALAMITES_PRESSURE_PLATE, CORDAITES_PRESSURE_PLATE, MUTANT_TREE_PRESSURE_PLATE, PALM_PRESSURE_PLATE, SIGILLARIA_PRESSURE_PLATE, TEMPSKYA_PRESSURE_PLATE);
        addTag(ItemTags.WOODEN_TRAPDOORS, CALAMITES_TRAPDOOR, CORDAITES_TRAPDOOR, MUTANT_TREE_TRAPDOOR, PALM_TRAPDOOR, SIGILLARIA_TRAPDOOR, TEMPSKYA_TRAPDOOR);
        addTag(ItemTags.SAPLINGS, CALAMITES_SAPLING, CORDAITES_SAPLING, MUTANT_TREE_SAPLING, PALM_SAPLING, SIGILLARIA_SAPLING, TEMPSKYA_SAPLING);
        addTag(ItemTags.STAIRS, ANCIENT_STONE_STAIRS, VOLCANIC_BRICK_STAIRS, VOLCANIC_TILE_STAIRS);
        addTag(ItemTags.SLABS, ANCIENT_STONE_SLAB, VOLCANIC_BRICK_SLAB, VOLCANIC_TILE_SLAB);
        addTag(ItemTags.WALLS, ANCIENT_STONE_WALL, VOLCANIC_BRICK_WALL, VOLCANIC_TILE_WALL);
        addTag(ItemTags.RAILS, SLIME_TRAIL);
        addTag(ItemTags.LEAVES, CALAMITES_LEAVES, CORDAITES_LEAVES, MUTANT_TREE_LEAVES, PALM_LEAVES, SIGILLARIA_LEAVES, TEMPSKYA_LEAF);
        addTag(ItemTags.BEDS, COMFY_BED);
        addTag(ItemTags.DIRT, TARRED_DIRT, ICED_DIRT);
        addTag(ItemTags.BOOKSHELF_BOOKS, DINOPEDIA);
        addTag(ItemTags.SWORDS, ANCIENT_SWORD, SCARAB_SWORD);
        addTag(ItemTags.AXES, SCARAB_AXE);
        addTag(ItemTags.HOES, SCARAB_HOE);
        addTag(ItemTags.PICKAXES, SCARAB_PICKAXE);
        addTag(ItemTags.SHOVELS, SCARAB_SHOVEL);

        //https://minecraft.wiki/w/Item_tag_(Java_Edition)
        //Creative Tab Filters
        addTag(ModItemTags.FOSSILS, BIO_FOSSIL, TAR_FOSSIL, SHALE_FOSSIL, PlANT_FOSSIL);
        addTag(ModItemTags.FILTER_BONES, ModItemTags.ALL_BONES);
        addTag(ModItemTags.FILTER_DNA, ModItemTags.DNA, ModItemTags.EMBRYOS);
        addTag(ModItemTags.FILTER_EGGS, ModItemTags.ALL_EGGS);
        var filterEggs = addTag(ModItemTags.FILTER_EGGS, ANU_BOSS_SPAWN_EGG, FAILURESAURUS_SPAWN_EGG, SENTRY_PIGLIN_SPAWN_EGG, TAR_SLIME_SPAWN_EGG, ARTIFICIAL_HONEYCOMB);
        addTag(ModItemTags.FILTER_MEAT, ModItemTags.UNCOOKED_MEAT);
        var filterMeat = addTag(ModItemTags.FILTER_MEAT, FAILURESAURUS_FLESH, COOKED_CHICKEN_SOUP, RAW_CHICKEN_SOUP, COOKED_EGG);
        var filterPlants = addTag(ModItemTags.FILTER_PLANTS, ModItemTags.FOSSIL_SEEDS, ModItemTags.RESTORED_SEEDS);
        addTag(ModItemTags.FILTER_PLANTS, CALAMITES_FOSSIL_SAPLING, CORDAITES_FOSSIL_SAPLING, PALM_FOSSIL_SAPLING, SIGILLARIA_FOSSIL_SAPLING, TEMPSKYA_FOSSIL_SAPLING);
        addTag(ModItemTags.FILTER_OTHER, ELASMOTHERIUM_FUR, MAMMOTH_FUR, THERIZINOSAURUS_DOWN, MAGIC_CONCH);
        addTag(ModItemTags.FILTER_MACHINES, BIO_FOSSIL, PlANT_FOSSIL, SHALE_FOSSIL, TAR_FOSSIL, BIO_GOO, POTTERY_SHARD, TAR_DROP, RELIC_SCRAP, ANALYZER, CULTURE_VAT, FEEDER, SIFTER, WORKTABLE, DRUM);
        addTag(ModItemTags.FILTER_BUILDING_BLOCKS, SHELL, AMBER_ORE, AMBER_BLOCK, AMBER_CHUNK, AMBER_CHUNK_DOMINICAN, AMBER_CHUNK_MOSQUITO,
                PERMAFROST_BLOCK, SKULL_BLOCK, SKULL_LANTERN, SLIME_TRAIL, ANCIENT_STONE, ANCIENT_STONE_BRICKS,
                ANCIENT_STONE_SLAB, ANCIENT_STONE_STAIRS, ANCIENT_STONE_WALL, CALCITE_FOSSIL, DEEPSLATE_FOSSIL, DRIPSTONE_FOSSIL, RED_SANDSTONE_FOSSIL,
                SANDSTONE_FOSSIL, STONE_FOSSIL, TUFF_FOSSIL, TARRED_DIRT, ICED_DIRT, VOLCANIC_ASH, VOLCANIC_ROCK, VOLCANIC_BRICKS,
                VOLCANIC_BRICK_SLAB, VOLCANIC_BRICK_STAIRS, VOLCANIC_BRICK_WALL, VOLCANIC_TILES, VOLCANIC_TILE_SLAB,
                VOLCANIC_TILE_STAIRS, VOLCANIC_TILE_WALL);
        addTag(ModItemTags.FILTER_TOOLS, BROKEN_SWORD, BROKEN_HELMET, ANCIENT_SWORD, ANCIENT_HELMET, FROZEN_MEAT, TOOTH_DAGGER,
                WOODEN_JAVELIN, STONE_JAVELIN, GOLD_JAVELIN, IRON_JAVELIN, DIAMOND_JAVELIN, ANCIENT_JAVELIN, SCARAB_SWORD,
                SCARAB_PICKAXE, SCARAB_AXE, SCARAB_SHOVEL, SCARAB_HOE, BONE_HELMET, BONE_CHESTPLATE, BONE_LEGGINGS, BONE_BOOTS);
        addTag(ModItemTags.FILTER_PARK, BUBBLE_BLOWER, FEEDER, SKULL_STICK, STUNTED_ESSENCE, CHICKEN_ESSENCE,
                WHIP, DINOPEDIA);
        var filterPark = tag(ModItemTags.FILTER_PARK);
        for (DyeColor color : DyeColor.values()) {//Keeps same order
            filterPark.add(TOY_BALLS.get(color).get());
        }
        for (RegistrySupplier<ToyScratchingPostItem> toy : TOY_SCRATCHING_POSTS.values()) {
            filterPark.add(toy.get());
        }
        for (RegistrySupplier<ToyTetheredLogItem> toy : TOY_TETHERED_LOGS.values()) {
            filterPark.add(toy.get());
        }
        var filterVases = tag(ModItemTags.FILTER_VASES);
        for (RegistrySupplier<VaseBlock> vase : VASES) {
            filterVases.add(vase.get().asItem());
        }
        addTag(ModItemTags.FILTER_TREES, ModItemTags.ANCIENT_WOOD_LOGS, ModItemTags.CALAMITES_LOGS, ModItemTags.CORDAITES_LOGS,
                ModItemTags.MUTANT_TREE_LOGS, ModItemTags.PALM_LOGS, ModItemTags.SIGILLARIA_LOGS, ModItemTags.TEMPSKYA_LOGS);

        addTag(ModItemTags.FILTER_TREES,
                ANCIENT_WOOD_PLANKS,
                CALAMITES_PLANKS, CALAMITES_LEAVES, CALAMITES_SAPLING,
                CORDAITES_PLANKS, CORDAITES_LEAVES, CORDAITES_SAPLING,
                MUTANT_TREE_PLANKS, MUTANT_TREE_LEAVES, MUTANT_TREE_SAPLING, MUTANT_TREE_VINE,
                PALM_PLANKS, PALM_LEAVES, PALM_SAPLING,
                SIGILLARIA_PLANKS, SIGILLARIA_LEAVES, SIGILLARIA_SAPLING,
                TEMPSKYA_PLANKS, TEMPSKYA_TOP, TEMPSKYA_LEAF, TEMPSKYA_SAPLING);
        for (BlockFamily.Variant variant : BlockFamily.Variant.values()) {
            addTag(ModItemTags.FILTER_TREES,
                    ModRecipeProvider.ANCIENT_WOOD_PLANKS.get(variant),
                    ModRecipeProvider.CALAMITES_PLANKS.get(variant),
                    ModRecipeProvider.CORDAITES_PLANKS.get(variant),
                    ModRecipeProvider.MUTANT_TREE_PLANKS.get(variant),
                    ModRecipeProvider.PALM_PLANKS.get(variant),
                    ModRecipeProvider.SIGILLARIA_PLANKS.get(variant),
                    ModRecipeProvider.TEMPSKYA_PLANKS.get(variant));
        }
        addTag(ModItemTags.FILTER_BUCKETS, Arrays.stream(values()).filter(info -> info.bucketItem != null).map(info -> info.bucketItem).toArray(Item[]::new));
        var fossilSeeds = addTag(ModItemTags.FOSSIL_SEEDS, FERN_SEED_FOSSIL);
        var restoredSeeds = addTag(ModItemTags.RESTORED_SEEDS, FERN_SEED);
        for (PrehistoricPlantInfo info : PrehistoricPlantInfo.values()) {
            filterPlants.add(info.getPlantBlock().asItem());
        }
        for (PrehistoricPlantInfo info : PrehistoricPlantInfo.plantsWithSeeds()) {
            fossilSeeds.add(info.getFossilizedPlantSeedItem());
            restoredSeeds.add(info.getPlantSeedItem());
            if (info.berryItem() != null && info.berryItem().isPresent()) {
                filterPlants.add(info.berryItem().get());
            }
        }
        addTag(ModItemTags.FOSSIL_SAPLINGS, CALAMITES_FOSSIL_SAPLING, CORDAITES_FOSSIL_SAPLING, PALM_FOSSIL_SAPLING, SIGILLARIA_FOSSIL_SAPLING, TEMPSKYA_FOSSIL_SAPLING);
        addTag(ModItemTags.DNA_INSECTS, ARTHROPLEURA.dnaItem, MEGANEURA.dnaItem, NAUTILUS.dnaItem);
        addTag(ModItemTags.DNA_LIMBLESS, ALLIGATOR_GAR.dnaItem, COELACANTH.dnaItem, STURGEON.dnaItem);
        addTag(ModItemTags.DNA_PLANTS, ModItemTags.FOSSIL_SEEDS, ModItemTags.FOSSIL_SAPLINGS);
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
        addTag(ModItemTags.COOKABLE_EGGS, ModItemTags.DINO_EGGS, ModItemTags.BIRD_EGGS).add(Items.EGG);
        addTag(ModItemTags.ALL_EGGS, ModItemTags.DINO_EGGS, ModItemTags.FISH_EGGS, ModItemTags.BIRD_EGGS);
        addTag(ModItemTags.ALL_BONES, ModItemTags.ARM_BONES, ModItemTags.FOOT_BONES, ModItemTags.LEG_BONES, ModItemTags.RIBCAGE_BONES, ModItemTags.SKULL_BONES, ModItemTags.TAIL_BONES, ModItemTags.UNIQUE_BONES, ModItemTags.VERTEBRAE_BONES);
        addTag(ItemTags.CREEPER_DROP_MUSIC_DISCS, MUSIC_DISC_ANU, MUSIC_DISC_BONES, MUSIC_DISC_DISCOVERY, MUSIC_DISC_SCARAB);
        addTag(ModItemTags.SIFTER_INPUTS, ItemTags.SAND, ItemTags.DIRT).add(DENSE_SAND.get().asItem(),
                VOLCANIC_ASH.get().asItem(), Blocks.SOUL_SAND.asItem());
        if (ModList.get().isLoaded(ModConstants.PREHISTORIC_FAUNA)) {
            PFaunaTagsProvider.addItemTags(this);
        }
    }

    @SafeVarargs
    protected final IntrinsicHolderTagsProvider.IntrinsicTagAppender<Item> addTag(TagKey<Item> key, TagKey<Item>... toAdd) {
        return tag(key).addTags(toAdd);
    }

    @SafeVarargs
    protected final IntrinsicHolderTagsProvider.IntrinsicTagAppender<Item> addTag(TagKey<Item> key, RegistrySupplier<? extends ItemLike>... toAdd) {
        return tag(key).add(Arrays.stream(toAdd).filter(RegistrySupplier::isPresent).map(Supplier::get).map(ItemLike::asItem).toArray(Item[]::new));
    }

    protected IntrinsicHolderTagsProvider.IntrinsicTagAppender<Item> addTag(TagKey<Item> key, ItemLike... toAdd) {
        return tag(key).add(Arrays.stream(toAdd).filter(Objects::nonNull).map(ItemLike::asItem).toArray(Item[]::new));
    }
}
