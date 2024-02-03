package com.fossil.fossil.forge.data.providers;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.block.PrehistoricPlantType;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.entity.prehistoric.base.VanillaEntityInfo;
import com.fossil.fossil.item.BirdEggItem;
import com.fossil.fossil.item.DinoEggItem;
import com.fossil.fossil.item.ModItems;
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
        var filterMeat = tag(ModItemTags.FILTER_MEAT).addTags(ModItemTags.UNCOOKED_MEAT).add(ModItems.FAILURESAURUS_FLESH.get(),
                ModItems.COOKED_CHICKEN_SOUP.get(), ModItems.RAW_CHICKEN_SOUP.get(), ModItems.COOKED_EGG.get());
        var filterPlants = tag(ModItemTags.FILTER_PLANTS).addTags(ModItemTags.FOSSIL_SEEDS, ModItemTags.RESTORED_SEEDS);

        var fossilSeeds = tag(ModItemTags.FOSSIL_SEEDS).add(ModItems.FERN_SEED_FOSSIL.get());
        var restoredSeeds = tag(ModItemTags.RESTORED_SEEDS).add(ModItems.FERN_SEED.get());
        for (PrehistoricPlantType type : PrehistoricPlantType.plantsWithSeeds()) {
            fossilSeeds.add(type.getFossilizedPlantSeedItem());
            restoredSeeds.add(type.getPlantSeedItem());
            if (type.berryItem != null && type.berryItem.isPresent()) {
                filterPlants.add(type.berryItem.get());
            }
        }
        tag(ModItemTags.FOSSIL_SAPLINGS).add(ModItems.CALAMITES_SAPLING_FOSSIL.get(), ModItems.CORDAITES_SAPLING_FOSSIL.get(), ModItems.PALM_SAPLING_FOSSIL.get(), ModItems.SIGILLARIA_SAPLING_FOSSIL.get(), ModItems.TEMPSKYA_SAPLING_FOSSIL.get());
        tag(ModItemTags.DNA_INSECTS).add(PrehistoricEntityType.ARTHROPLEURA.dnaItem, PrehistoricEntityType.MEGANEURA.dnaItem, PrehistoricEntityType.NAUTILUS.dnaItem);
        tag(ModItemTags.DNA_LIMBLESS).add(PrehistoricEntityType.ALLIGATOR_GAR.dnaItem, PrehistoricEntityType.COELACANTH.dnaItem, PrehistoricEntityType.STURGEON.dnaItem);
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
        for (PrehistoricEntityType type : PrehistoricEntityType.values()) {
            if (type.dnaItem != null) {
                allDNA.add(type.dnaItem);
            }
            if (type.embryoItem != null) {
                embryos.add(type.embryoItem);
            }
            if (type.eggItem instanceof DinoEggItem || type.eggItem instanceof BirdEggItem) {
                allEgg.add(type.eggItem);
            }
            if (type.armBoneItem != null) {
                allArm.add(type.armBoneItem);
            }
            if (type.footBoneItem != null) {
                allFoot.add(type.footBoneItem);
            }
            if (type.legBoneItem != null) {
                allLeg.add(type.legBoneItem);
            }
            if (type.ribcageBoneItem != null) {
                allRibcage.add(type.ribcageBoneItem);
            }
            if (type.skullBoneItem != null) {
                allSkull.add(type.skullBoneItem);
            }
            if (type.tailBoneItem != null) {
                allTails.add(type.tailBoneItem);
            }
            if (type.uniqueBoneItem != null) {
                allUnique.add(type.uniqueBoneItem);
            }
            if (type.vertebraeBoneItem != null) {
                allVertebrae.add(type.vertebraeBoneItem);
            }
            if (type.foodItem != null) {
                uncookedMeat.add(type.foodItem);
            }
            if (type.cookedFoodItem != null) {
                filterMeat.add(type.cookedFoodItem);
            }
            if (type.spawnEggItem != null) {
                filterEggs.add(type.spawnEggItem);
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
        tag(ItemTags.MUSIC_DISCS).add(ModItems.RECORD_ANU.get(), ModItems.RECORD_BONES.get(), ModItems.RECORD_DISCOVERY.get(), ModItems.RECORD_SCARAB.get());
        copy(ModBlockTags.CALAMITES_LOGS, ModItemTags.CALAMITES_LOGS);
        copy(ModBlockTags.CORDAITES_LOGS, ModItemTags.CORDAITES_LOGS);
        copy(ModBlockTags.PALM_LOGS, ModItemTags.PALM_LOGS);
        copy(ModBlockTags.SIGILLARIA_LOGS, ModItemTags.SIGILLARIA_LOGS);
        copy(ModBlockTags.TEMPSKYA_LOGS, ModItemTags.TEMPSKYA_LOGS);
        copy(ModBlockTags.FIGURINES, ModItemTags.FIGURINES);
        //Forge Tags
        tag(Tags.Items.EGGS).addTags(ModItemTags.DINO_EGGS);
        tag(Tags.Items.SLIMEBALLS).add(ModItems.TAR_DROP.get());
        //Fabric Tags
        var fabricEggs = ItemTags.create(new ResourceLocation("c:eggs"));
        var fabricSlimeBalls = ItemTags.create(new ResourceLocation("c:slime_balls"));
        tag(fabricEggs).addTags(ModItemTags.DINO_EGGS).add(Items.EGG);
        tag(fabricSlimeBalls).add(ModItems.TAR_DROP.get(), Items.SLIME_BALL);
    }

    @Override
    public @NotNull String getName() {
        return "Fossil Item Tags";
    }
}
