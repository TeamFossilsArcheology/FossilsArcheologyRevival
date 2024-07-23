package com.fossil.fossil.forge.data.providers;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.block.PrehistoricPlantInfo;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.fossil.fossil.entity.prehistoric.base.VanillaEntityInfo;
import com.fossil.fossil.item.BirdEggItem;
import com.fossil.fossil.item.DinoEggItem;
import com.fossil.fossil.tags.ModBlockTags;
import com.fossil.fossil.tags.ModItemTags;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.data.tags.ItemTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.Items;
import net.minecraftforge.common.Tags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

import static com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityInfo.*;
import static com.fossil.fossil.item.ModItems.*;

public class ModItemTagsProvider extends ItemTagsProvider {

    public ModItemTagsProvider(DataGenerator arg, BlockTagsProvider blockTagsProvider, ExistingFileHelper exFileHelper) {
        super(arg, blockTagsProvider, Fossil.MOD_ID, exFileHelper);
    }

    @Override
    protected void addTags() {
        //Creative Tab Filters
        tag(ModItemTags.FILTER_BONES).addTags(ModItemTags.ALL_BONES);
        tag(ModItemTags.FILTER_DNA).addTags(ModItemTags.DNA, ModItemTags.EMBRYOS);
        var filterEggs = tag(ModItemTags.FILTER_EGGS).addTags(ModItemTags.DINO_EGGS);
        var filterMeat = tag(ModItemTags.FILTER_MEAT).addTags(ModItemTags.UNCOOKED_MEAT).add(FAILURESAURUS_FLESH.get(),
                COOKED_CHICKEN_SOUP.get(), RAW_CHICKEN_SOUP.get(), COOKED_EGG.get());
        var filterPlants = tag(ModItemTags.FILTER_PLANTS).addTags(ModItemTags.FOSSIL_SEEDS, ModItemTags.RESTORED_SEEDS);

        var fossilSeeds = tag(ModItemTags.FOSSIL_SEEDS).add(FERN_SEED_FOSSIL.get());
        var restoredSeeds = tag(ModItemTags.RESTORED_SEEDS).add(FERN_SEED.get());
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
        var embryos = tag(ModItemTags.EMBRYOS);
        var allEgg = tag(ModItemTags.DINO_EGGS);
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
            if (info.dnaItem != null) {
                allDNA.add(info.dnaItem);
            }
            if (info.embryoItem != null) {
                embryos.add(info.embryoItem);
            }
            if (info.eggItem instanceof DinoEggItem || info.eggItem instanceof BirdEggItem) {
                allEgg.add(info.eggItem);
            }
            if (info.armBoneItem != null) {
                allArm.add(info.armBoneItem);
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
            }
            if (info.cookedFoodItem != null) {
                filterMeat.add(info.cookedFoodItem);
            }
            if (info.spawnEggItem != null) {
                filterEggs.add(info.spawnEggItem);
            }
        }
        for (VanillaEntityInfo info : VanillaEntityInfo.values()) {
            if (info.dnaItem != null) {
                allDNA.add(info.dnaItem);
            }
            if (info.embryoItem != null) {
                embryos.add(info.embryoItem);
            }
        }
        tag(ModItemTags.COOKABLE_EGGS).addTags(ModItemTags.DINO_EGGS).add(Items.EGG);
        tag(ModItemTags.ALL_BONES).addTags(ModItemTags.ARM_BONES, ModItemTags.FOOT_BONES, ModItemTags.LEG_BONES, ModItemTags.RIBCAGE_BONES, ModItemTags.SKULL_BONES, ModItemTags.TAIL_BONES, ModItemTags.UNIQUE_BONES, ModItemTags.VERTEBRAE_BONES);
        tag(ItemTags.MUSIC_DISCS).add(RECORD_ANU.get(), RECORD_BONES.get(), RECORD_DISCOVERY.get(), RECORD_SCARAB.get());
        copy(ModBlockTags.ANCIENT_WOOD_LOGS, ModItemTags.ANCIENT_WOOD_LOGS);
        copy(ModBlockTags.CALAMITES_LOGS, ModItemTags.CALAMITES_LOGS);
        copy(ModBlockTags.CORDAITES_LOGS, ModItemTags.CORDAITES_LOGS);
        copy(ModBlockTags.PALM_LOGS, ModItemTags.PALM_LOGS);
        copy(ModBlockTags.SIGILLARIA_LOGS, ModItemTags.SIGILLARIA_LOGS);
        copy(ModBlockTags.TEMPSKYA_LOGS, ModItemTags.TEMPSKYA_LOGS);
        copy(ModBlockTags.FIGURINES, ModItemTags.FIGURINES);
        //Forge Tags
        tag(Tags.Items.EGGS).addTags(ModItemTags.DINO_EGGS);
        tag(Tags.Items.SLIMEBALLS).add(TAR_DROP.get());
        //Fabric Tags
        var fabricEggs = ItemTags.create(new ResourceLocation("c:eggs"));
        var fabricSlimeBalls = ItemTags.create(new ResourceLocation("c:slime_balls"));
        tag(fabricEggs).addTags(ModItemTags.DINO_EGGS).add(Items.EGG);
        tag(fabricSlimeBalls).add(TAR_DROP.get(), Items.SLIME_BALL);
    }

    @Override
    public @NotNull String getName() {
        return "Fossil Item Tags";
    }
}
