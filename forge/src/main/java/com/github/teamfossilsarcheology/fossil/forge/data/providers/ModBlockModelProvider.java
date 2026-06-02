package com.github.teamfossilsarcheology.fossil.forge.data.providers;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import net.minecraft.data.DataGenerator;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.generators.BlockModelBuilder;
import net.minecraftforge.client.model.generators.BlockModelProvider;
import net.minecraftforge.common.data.ExistingFileHelper;

public class ModBlockModelProvider extends BlockModelProvider {
    public ModBlockModelProvider(DataGenerator generator, ExistingFileHelper existingFileHelper) {
        super(generator, FossilMod.MOD_ID, existingFileHelper);
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

    public BlockModelBuilder orientableWithBack(String name, ResourceLocation top, ResourceLocation front, ResourceLocation side, ResourceLocation back) {
        return withExistingParent(name, FossilMod.location(BLOCK_FOLDER + "/orientable_with_back"))
                .texture("top", top)
                .texture("front", front)
                .texture("side", side)
                .texture("back", back)
                .texture("bottom", back);
    }

    public BlockModelBuilder particleOnly(String name, String texture) {
        return getBuilder(name).texture("particle", texture);
    }
}
