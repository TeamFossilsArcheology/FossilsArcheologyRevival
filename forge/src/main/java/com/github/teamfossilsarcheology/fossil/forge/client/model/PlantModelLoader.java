package com.github.teamfossilsarcheology.fossil.forge.client.model;

import com.github.teamfossilsarcheology.fossil.client.model.block.PlantBlockModel;
import com.github.teamfossilsarcheology.fossil.client.model.block.PlantBlockModel.PlantBlockElement;
import com.github.teamfossilsarcheology.fossil.client.model.block.PlantBlockModel.PlantBlockElementFace;
import com.github.teamfossilsarcheology.fossil.client.model.block.PlantBlockModel.PlantBlockFaceUV;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonObject;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraftforge.client.model.IModelLoader;
import org.jetbrains.annotations.NotNull;


public class PlantModelLoader implements IModelLoader<PlantModelGeometry> {
    static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(PlantBlockModel.class, new PlantBlockModel.Deserializer())
            .registerTypeAdapter(PlantBlockElement.class, new PlantBlockElement.Deserializer())
            .registerTypeAdapter(PlantBlockElementFace.class, new PlantBlockElementFace.Deserializer())
            .registerTypeAdapter(PlantBlockFaceUV.class, new PlantBlockFaceUV.Deserializer()).create();

    @Override
    public @NotNull PlantModelGeometry read(JsonDeserializationContext context, JsonObject jsonObject) {
        return new PlantModelGeometry(GSON.getAdapter(PlantBlockModel.class).fromJsonTree(jsonObject));
    }

    @Override
    public void onResourceManagerReload(@NotNull ResourceManager resourceManager) {

    }
}
