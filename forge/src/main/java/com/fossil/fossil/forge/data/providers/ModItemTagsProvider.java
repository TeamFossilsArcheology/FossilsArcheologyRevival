package com.fossil.fossil.forge.data.providers;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.block.PrehistoricPlantType;
import com.fossil.fossil.item.ModItems;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.TagsProvider;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.registries.ForgeRegistries;
import org.jetbrains.annotations.NotNull;

public class ModItemTagsProvider extends TagsProvider<Item> {
    private static final ResourceKey<? extends Registry<Item>> key = ModItems.ITEMS.getRegistrar().key();
    public static final TagKey<Item> FOSSIL_SEEDS = TagKey.create(key, new ResourceLocation(Fossil.MOD_ID, "fossil_seeds"));

    public ModItemTagsProvider(DataGenerator arg, ExistingFileHelper exFileHelper) {
        super(arg, Registry.ITEM, Fossil.MOD_ID, exFileHelper);
    }

    @Override
    protected void addTags() {
        for (Block block : ForgeRegistries.BLOCKS.tags().getTag(BlockTags.LEAVES)) {

        }
        var fossilSeeds = tag(FOSSIL_SEEDS);
        for (PrehistoricPlantType type : PrehistoricPlantType.plantsWithSeeds()) {
            fossilSeeds.add(type.getFossilizedPlantSeedItem());
        }
    }

    @Override
    public @NotNull String getName() {
        return "Fossil Item Tags";
    }
}
