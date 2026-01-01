package com.github.teamfossilsarcheology.fossil.forge.data.providers;

import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import com.github.teamfossilsarcheology.fossil.block.PrehistoricPlantInfo;
import com.github.teamfossilsarcheology.fossil.block.custom_blocks.FourTallFlowerBlock;
import com.github.teamfossilsarcheology.fossil.block.custom_blocks.GrowableFlowerBlock;
import com.github.teamfossilsarcheology.fossil.block.custom_blocks.ShortFlowerBlock;
import com.github.teamfossilsarcheology.fossil.block.custom_blocks.TallFlowerBlock;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricMobType;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.VanillaEntityInfo;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.block.Block;
import superlord.prehistoricfauna.init.PFTags;

import java.util.Arrays;
import java.util.Set;

public class PFaunaTagsProvider {

    protected static void addBlockTags(ModBlockTagsProvider provider) {
        for (PrehistoricPlantInfo info : PrehistoricPlantInfo.values()) {
            Block flower = info.getPlantBlock();
            if (info == PrehistoricPlantInfo.MUTANT_PLANT) {
                provider.addTag(PFTags.PLANTS_2_HUNGER, flower);
            } else if (flower instanceof ShortFlowerBlock || flower instanceof GrowableFlowerBlock) {
                provider.addTag(PFTags.PLANTS_6_HUNGER, flower);
            } else if (info.berryAge() != 0) {
                provider.addTag(PFTags.PLANTS_8_HUNGER, flower);
            } else if (flower instanceof TallFlowerBlock) {
                provider.addTag(PFTags.PLANTS_12_HUNGER, flower);
            } else if (flower instanceof FourTallFlowerBlock) {
                provider.addTag(PFTags.PLANTS_25_HUNGER, flower);
            }
        }
        provider.addTag(PFTags.PLANTS_6_HUNGER, ModBlocks.FERNS.get());
    }

    private static final Set<PrehistoricEntityInfo> egg5 = Set.of(
            PrehistoricEntityInfo.COMPSOGNATHUS,
            PrehistoricEntityInfo.CITIPATI,
            PrehistoricEntityInfo.DEINONYCHUS,
            PrehistoricEntityInfo.DILOPHOSAURUS,
            PrehistoricEntityInfo.DIMORPHODON,
            PrehistoricEntityInfo.DRYOSAURUS,
            PrehistoricEntityInfo.EDAPHOSAURUS,
            PrehistoricEntityInfo.GALLIMIMUS,
            PrehistoricEntityInfo.ORNITHOLESTES,
            PrehistoricEntityInfo.PROTOCERATOPS,
            PrehistoricEntityInfo.PSITTACOSAURUS,
            PrehistoricEntityInfo.PTERANODON,
            PrehistoricEntityInfo.VELOCIRAPTOR
    );
    private static final Set<PrehistoricEntityInfo> egg15 = Set.of(
            PrehistoricEntityInfo.BRACHIOSAURUS,
            PrehistoricEntityInfo.DIPLODOCUS,
            PrehistoricEntityInfo.QUETZALCOATLUS,
            PrehistoricEntityInfo.TYRANNOSAURUS
    );

    protected static void addItemTags(ModItemTagsProvider provider) {
        for (PrehistoricEntityInfo info : PrehistoricEntityInfo.values()) {
            if (info.mobType == PrehistoricMobType.FISH) {
                if (info.eggItem != null) provider.addTag(PFTags.EGGS_5_HUNGER, info.eggItem);
                if (info.foodItem != null) provider.addTag(PFTags.FISH_4_HUNGER, info.foodItem);
            } else {
                if (info.foodItem != null) provider.addTag(PFTags.MEATS_6_HUNGER, info.foodItem);
                if (egg5.contains(info)) {
                    if (info.eggItem != null) provider.addTag(PFTags.EGGS_5_HUNGER, info.eggItem);
                } else if (egg15.contains(info)) {
                    if (info.eggItem != null) provider.addTag(PFTags.EGGS_15_HUNGER, info.eggItem);
                } else {
                    if (info.eggItem != null) provider.addTag(PFTags.EGGS_10_HUNGER, info.eggItem);
                }
            }
            if (info.mobType == PrehistoricMobType.BIRD) {
                provider.addTag(PFTags.EGGS_10_HUNGER, info.cultivatedBirdEggItem);
                provider.addTag(PFTags.EGGS_5_HUNGER, info.cultivatedBirdEggItem);
            }
        }
        for (VanillaEntityInfo info : VanillaEntityInfo.values()) {
            if (info.mobType == PrehistoricMobType.VANILLA_BIRD) {
                provider.addTag(PFTags.EGGS_5_HUNGER, info.cultivatedBirdEggItem);
            }
        }

        for (PrehistoricPlantInfo info : PrehistoricPlantInfo.values()) {
            Block flower = info.getPlantBlock();
            if (info == PrehistoricPlantInfo.MUTANT_PLANT) {
                provider.addTag(PFTags.PLANTS_2_HUNGER_ITEM, flower.asItem());
            } else if (flower instanceof ShortFlowerBlock || flower instanceof GrowableFlowerBlock) {
                provider.addTag(PFTags.PLANTS_6_HUNGER_ITEM, flower.asItem());
            } else if (info.berryAge() != 0) {
                provider.addTag(PFTags.PLANTS_4_HUNGER_ITEM, info.berryItem().get());
            } else if (flower instanceof TallFlowerBlock) {
                provider.addTag(PFTags.PLANTS_12_HUNGER_ITEM, flower.asItem());
            } else if (flower instanceof FourTallFlowerBlock) {
                provider.addTag(PFTags.PLANTS_25_HUNGER_ITEM, flower.asItem());
            }
        }
        provider.addTag(PFTags.PLANTS_6_HUNGER_ITEM, ModBlocks.FERNS.get().asItem());
    }

    protected static void addEntityTypeTags(ModEntityTypeTagsProvider provider) {
        addTag(provider, PrehistoricEntityInfo.ALLOSAURUS, PFTags.ANIMALS_40_HUNGER);
        addTag(provider, PrehistoricEntityInfo.ANKYLOSAURUS, PFTags.ANIMALS_80_HUNGER);
        addTag(provider, PrehistoricEntityInfo.AQUILOLAMNA, PFTags.ANIMALS_20_HUNGER);
        addTag(provider, PrehistoricEntityInfo.ARTHROPLEURA, PFTags.ANIMALS_30_HUNGER);
        addTag(provider, PrehistoricEntityInfo.BRACHIOSAURUS, PFTags.ANIMALS_200_HUNGER);
        addTag(provider, PrehistoricEntityInfo.CERATOSAURUS, PFTags.ANIMALS_40_HUNGER);
        addTag(provider, PrehistoricEntityInfo.CITIPATI, PFTags.ANIMALS_20_HUNGER);
        addTag(provider, PrehistoricEntityInfo.COMPSOGNATHUS, PFTags.ANIMALS_6_HUNGER);
        addTag(provider, PrehistoricEntityInfo.CONFUCIUSORNIS, PFTags.ANIMALS_6_HUNGER);
        addTag(provider, PrehistoricEntityInfo.CRASSIGYRINUS, PFTags.ANIMALS_15_HUNGER);
        addTag(provider, PrehistoricEntityInfo.DEINONYCHUS, PFTags.ANIMALS_20_HUNGER);
        addTag(provider, PrehistoricEntityInfo.DICRANURUS, PFTags.ANIMALS_6_HUNGER);
        addTag(provider, PrehistoricEntityInfo.DILOPHOSAURUS, PFTags.ANIMALS_30_HUNGER);
        addTag(provider, PrehistoricEntityInfo.DIMETRODON, PFTags.ANIMALS_30_HUNGER);
        addTag(provider, PrehistoricEntityInfo.DIMORPHODON, PFTags.ANIMALS_15_HUNGER);
        addTag(provider, PrehistoricEntityInfo.DIPLOCAULUS, PFTags.ANIMALS_6_HUNGER);
        addTag(provider, PrehistoricEntityInfo.DIPLODOCUS, PFTags.ANIMALS_200_HUNGER);
        addTag(provider, PrehistoricEntityInfo.DODO, PFTags.ANIMALS_10_HUNGER);
        addTag(provider, PrehistoricEntityInfo.DRYOSAURUS, PFTags.ANIMALS_20_HUNGER);
        addTag(provider, PrehistoricEntityInfo.EDAPHOSAURUS, PFTags.ANIMALS_30_HUNGER);
        addTag(provider, PrehistoricEntityInfo.ELASMOTHERIUM, PFTags.ANIMALS_60_HUNGER);
        addTag(provider, PrehistoricEntityInfo.GALLIMIMUS, PFTags.ANIMALS_30_HUNGER);
        addTag(provider, PrehistoricEntityInfo.GASTORNIS, PFTags.ANIMALS_30_HUNGER);
        addTag(provider, PrehistoricEntityInfo.HENODUS, PFTags.ANIMALS_30_HUNGER);
        addTag(provider, PrehistoricEntityInfo.ICHTHYOSAURUS, PFTags.ANIMALS_20_HUNGER);
        addTag(provider, PrehistoricEntityInfo.KELENKEN, PFTags.ANIMALS_30_HUNGER);
        addTag(provider, PrehistoricEntityInfo.LIOPLEURODON, PFTags.ANIMALS_40_HUNGER);
        addTag(provider, PrehistoricEntityInfo.LONCHODOMAS, PFTags.ANIMALS_6_HUNGER);
        addTag(provider, PrehistoricEntityInfo.MAMMOTH, PFTags.ANIMALS_80_HUNGER);
        addTag(provider, PrehistoricEntityInfo.MEGALANIA, PFTags.ANIMALS_30_HUNGER);
        addTag(provider, PrehistoricEntityInfo.MEGALOCEROS, PFTags.ANIMALS_30_HUNGER);
        addTag(provider, PrehistoricEntityInfo.MEGALODON, PFTags.ANIMALS_80_HUNGER);
        addTag(provider, PrehistoricEntityInfo.MEGALOGRAPTUS, PFTags.ANIMALS_20_HUNGER);
        addTag(provider, PrehistoricEntityInfo.MOSASAURUS, PFTags.ANIMALS_60_HUNGER);
        addTag(provider, PrehistoricEntityInfo.ORNITHOLESTES, PFTags.ANIMALS_15_HUNGER);
        addTag(provider, PrehistoricEntityInfo.PACHYCEPHALOSAURUS, PFTags.ANIMALS_20_HUNGER);
        addTag(provider, PrehistoricEntityInfo.PACHYRHINOSAURUS, PFTags.ANIMALS_60_HUNGER);
        addTag(provider, PrehistoricEntityInfo.PARASAUROLOPHUS, PFTags.ANIMALS_60_HUNGER);
        addTag(provider, PrehistoricEntityInfo.PHORUSRHACOS, PFTags.ANIMALS_30_HUNGER);
        addTag(provider, PrehistoricEntityInfo.PLATYBELODON, PFTags.ANIMALS_40_HUNGER);
        addTag(provider, PrehistoricEntityInfo.PLESIOSAURUS, PFTags.ANIMALS_20_HUNGER);
        addTag(provider, PrehistoricEntityInfo.POSTOSUCHUS, PFTags.ANIMALS_30_HUNGER);
        addTag(provider, PrehistoricEntityInfo.PROTOCERATOPS, PFTags.ANIMALS_20_HUNGER);
        addTag(provider, PrehistoricEntityInfo.PSITTACOSAURUS, PFTags.ANIMALS_15_HUNGER);
        addTag(provider, PrehistoricEntityInfo.PTERANODON, PFTags.ANIMALS_30_HUNGER);
        addTag(provider, PrehistoricEntityInfo.QUETZALCOATLUS, PFTags.ANIMALS_40_HUNGER);
        addTag(provider, PrehistoricEntityInfo.SARCOSUCHUS, PFTags.ANIMALS_80_HUNGER);
        addTag(provider, PrehistoricEntityInfo.SCOTOHARPES, PFTags.ANIMALS_6_HUNGER);
        addTag(provider, PrehistoricEntityInfo.SMILODON, PFTags.ANIMALS_20_HUNGER);
        addTag(provider, PrehistoricEntityInfo.SPINOSAURUS, PFTags.ANIMALS_60_HUNGER);
        addTag(provider, PrehistoricEntityInfo.STEGOSAURUS, PFTags.ANIMALS_60_HUNGER);
        addTag(provider, PrehistoricEntityInfo.THERIZINOSAURUS, PFTags.ANIMALS_60_HUNGER);
        addTag(provider, PrehistoricEntityInfo.TIKTAALIK, PFTags.ANIMALS_15_HUNGER);
        addTag(provider, PrehistoricEntityInfo.TITANIS, PFTags.ANIMALS_30_HUNGER);
        addTag(provider, PrehistoricEntityInfo.TRICERATOPS, PFTags.ANIMALS_60_HUNGER);
        addTag(provider, PrehistoricEntityInfo.TYRANNOSAURUS, PFTags.ANIMALS_60_HUNGER);
        addTag(provider, PrehistoricEntityInfo.VELOCIRAPTOR, PFTags.ANIMALS_15_HUNGER);
        addTag(provider, PrehistoricEntityInfo.WALLISEROPS, PFTags.ANIMALS_6_HUNGER);

        for (PrehistoricEntityInfo info : PrehistoricEntityInfo.values()) {
            if (info.mobType == PrehistoricMobType.FISH) {
                provider.addTag(PFTags.ANIMALS_10_HUNGER, info.entityType());
            }
        }
    }

    private static void addTag(ModEntityTypeTagsProvider provider, PrehistoricEntityInfo info, TagKey<EntityType<?>> key) {
        provider.addTag(key, info.entitySupplier.get());
    }

    private static void addTag(ModEntityTypeTagsProvider provider, TagKey<EntityType<?>> key, PrehistoricEntityInfo... toAdd) {
        provider.addTag(key, Arrays.stream(toAdd).map(info -> info.entitySupplier.get()).toArray(EntityType<?>[]::new));
    }
}
