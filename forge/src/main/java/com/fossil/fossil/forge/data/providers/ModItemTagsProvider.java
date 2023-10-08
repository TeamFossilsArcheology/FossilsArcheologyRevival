package com.fossil.fossil.forge.data.providers;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.block.PrehistoricPlantType;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.item.ModItems;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;

public class ModItemTagsProvider extends TagsProvider<Item> {
    public static final TagKey<Item> FOSSIL_SEEDS = ItemTags.create(new ResourceLocation(Fossil.MOD_ID, "fossil_seeds"));
    public static final TagKey<Item> DNA_INSECTS = ItemTags.create(new ResourceLocation(Fossil.MOD_ID, "dna_insects"));
    public static final TagKey<Item> DNA_LIMBLESS = ItemTags.create(new ResourceLocation(Fossil.MOD_ID, "dna_limbless"));
    public static final TagKey<Item> DNA_PLANTS = ItemTags.create(new ResourceLocation(Fossil.MOD_ID, "dna_plants"));

    public ModItemTagsProvider(DataGenerator arg, ExistingFileHelper exFileHelper) {
        super(arg, Registry.ITEM, Fossil.MOD_ID, exFileHelper);
    }

    @Override
    protected void addTags() {
        var fossilSeeds = tag(FOSSIL_SEEDS);
        for (PrehistoricPlantType type : PrehistoricPlantType.plantsWithSeeds()) {
            fossilSeeds.add(type.getFossilizedPlantSeedItem());
        }
        tag(DNA_INSECTS).add(PrehistoricEntityType.ARTHROPLEURA.dnaItem, PrehistoricEntityType.MEGANEURA.dnaItem, PrehistoricEntityType.NAUTILUS.dnaItem);
        tag(DNA_LIMBLESS).add(PrehistoricEntityType.ALLIGATOR_GAR.dnaItem, PrehistoricEntityType.COELACANTH.dnaItem, PrehistoricEntityType.STURGEON.dnaItem);
        tag(DNA_PLANTS).addTags(FOSSIL_SEEDS, ItemTags.SAPLINGS);
        tag(ItemTags.MUSIC_DISCS).add(ModItems.RECORD_ANU.get(), ModItems.RECORD_BONES.get(), ModItems.RECORD_DISCOVERY.get(), ModItems.RECORD_SCARAB.get());
    }

    @Override
    public @NotNull String getName() {
        return "Fossil Item Tags";
    }
}
