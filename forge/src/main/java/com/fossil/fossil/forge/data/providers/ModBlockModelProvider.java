package com.fossil.fossil.forge.data.providers;

import com.fossil.fossil.Fossil;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockModelProvider extends BlockModelProvider {
    public ModBlockModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, Fossil.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

    }

    public BlockModelBuilder tintedCross(String name, ResourceLocation cross) {
        return singleTexture(name, mcLoc(BLOCK_FOLDER + "/tinted_cross"), "tinted_cross", cross);
    }

    public void registerExisting(ResourceLocation... resourceLocation) {
        for (ResourceLocation location : resourceLocation) {
            existingFileHelper.trackGenerated(location, TEXTURE);
        }
    }
}
