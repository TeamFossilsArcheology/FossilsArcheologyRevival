package com.github.teamfossilsarcheology.fossil.forge.data.providers;

import com.github.teamfossilsarcheology.fossil.Fossil;
import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import com.github.teamfossilsarcheology.fossil.block.PrehistoricPlantInfo;
import com.github.teamfossilsarcheology.fossil.block.custom_blocks.VaseBlock;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.VanillaEntityInfo;
import com.github.teamfossilsarcheology.fossil.item.BirdEggItem;
import com.github.teamfossilsarcheology.fossil.item.DinoEggItem;
import com.github.teamfossilsarcheology.fossil.item.FishEggItem;
import com.github.teamfossilsarcheology.fossil.tags.ModBlockTags;
import com.github.teamfossilsarcheology.fossil.tags.ModItemTags;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.data.BlockFamily;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import static com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo.*;
import static com.github.teamfossilsarcheology.fossil.item.ModItems.*;

public class ModItemTagsProvider extends ItemTagsProvider {

    public ModItemTagsProvider(DataGenerator arg, BlockTagsProvider blockTagsProvider, ExistingFileHelper exFileHelper) {
        super(arg, blockTagsProvider, Fossil.MOD_ID, exFileHelper);
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
        var filterEggs = tag(ModItemTags.FILTER_EGGS).addTags(ModItemTags.DINO_EGGS, ModItemTags.FISH_EGGS);
        var filterMeat = tag(ModItemTags.FILTER_MEAT).addTags(ModItemTags.UNCOOKED_MEAT).add(FAILURESAURUS_FLESH.get(),
                COOKED_CHICKEN_SOUP.get(), RAW_CHICKEN_SOUP.get(), COOKED_EGG.get());
        var filterPlants = tag(ModItemTags.FILTER_PLANTS).addTags(ModItemTags.FOSSIL_SEEDS, ModItemTags.RESTORED_SEEDS);
        var filterVases = tag(ModItemTags.FILTER_VASES);
        for (RegistrySupplier<VaseBlock> vase : ModBlocks.VASES) {
            filterVases.add(vase.get().asItem());
        }
        var filterTrees = tag(ModItemTags.FILTER_TREES).addTags(ModItemTags.ANCIENT_WOOD_LOGS, ModItemTags.CALAMITES_LOGS, ModItemTags.CALAMITES_LOGS,
                ModItemTags.MUTANT_TREE_LOGS, ModItemTags.PALM_LOGS, ModItemTags.SIGILLARIA_LOGS, ModItemTags.TEMPSKYA_LOGS);
        for (BlockFamily.Variant variant : BlockFamily.Variant.values()) {
            addNullable(filterTrees, ModRecipeProvider.ANCIENT_WOOD_PLANKS.get(variant));
            addNullable(filterTrees, ModRecipeProvider.CALAMITES_PLANKS.get(variant));
            addNullable(filterTrees, ModRecipeProvider.CORDAITES_PLANKS.get(variant));
            addNullable(filterTrees, ModRecipeProvider.MUTANT_TREE_PLANKS.get(variant));
            addNullable(filterTrees, ModRecipeProvider.PALM_PLANKS.get(variant));
            addNullable(filterTrees, ModRecipeProvider.SIGILLARIA_PLANKS.get(variant));
            addNullable(filterTrees, ModRecipeProvider.TEMPSKYA_PLANKS.get(variant));
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
        tag(ModItemTags.FOSSIL_SAPLINGS).add(CALAMITES_SAPLING_FOSSIL.get(), CORDAITES_SAPLING_FOSSIL.get(), PALM_SAPLING_FOSSIL.get(), SIGILLARIA_SAPLING_FOSSIL.get(), TEMPSKYA_SAPLING_FOSSIL.get());
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
            if (info.eggItem instanceof DinoEggItem || info.eggItem instanceof BirdEggItem) {
                dinoEgg.add(info.eggItem);
                if (hasDNA) dinoDNA.add(info.dnaItem);
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
        }
        tag(ModItemTags.COOKABLE_EGGS).addTags(ModItemTags.DINO_EGGS).add(Items.EGG);
        tag(ModItemTags.ALL_BONES).addTags(ModItemTags.ARM_BONES, ModItemTags.FOOT_BONES, ModItemTags.LEG_BONES, ModItemTags.RIBCAGE_BONES, ModItemTags.SKULL_BONES, ModItemTags.TAIL_BONES, ModItemTags.UNIQUE_BONES, ModItemTags.VERTEBRAE_BONES);
        tag(ItemTags.MUSIC_DISCS).add(RECORD_ANU.get(), RECORD_BONES.get(), RECORD_DISCOVERY.get(), RECORD_SCARAB.get());

        tag(ModItemTags.SIFTER_INPUTS).addTags(ItemTags.SAND, ItemTags.DIRT).add(ModBlocks.DENSE_SAND.get().asItem(),
                ModBlocks.VOLCANIC_ASH.get().asItem(), Blocks.GRAVEL.asItem(), Blocks.SOUL_SAND.asItem());
    }

    public void addNullable(TagAppender<Item> tag, Block block) {
        if (block != null) {
            tag.add(block.asItem());
        }
    }

    @Override
    public @NotNull String getName() {
        return "Fossil Item Tags";
    }
}
