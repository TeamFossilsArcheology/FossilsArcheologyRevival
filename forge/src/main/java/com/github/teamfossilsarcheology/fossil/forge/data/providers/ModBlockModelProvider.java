package com.github.teamfossilsarcheology.fossil.forge.data.providers;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockModelProvider extends BlockModelProvider {
    public ModBlockModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, FossilMod.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {

    }

    public void registerExistingTexture(ResourceLocation... resourceLocation) {
        for (ResourceLocation location : resourceLocation) {
            existingFileHelper.trackGenerated(location, TEXTURE);
        }
    }

    public void registerExistingModel(ResourceLocation... resourceLocation) {
        for (ResourceLocation location : resourceLocation) {
            existingFileHelper.trackGenerated(location, MODEL);
        }
    }
}
