package com.fossil.fossil.fabric.client.model;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.client.model.block.PlantBlockModel;
import com.fossil.fossil.client.model.block.PlantBlockModel.PlantBlockElement;
import com.fossil.fossil.client.model.block.PlantBlockModel.PlantBlockElementFace;
import com.fossil.fossil.client.model.block.PlantBlockModel.PlantBlockFaceUV;
import com.google.gson.*;
import net.fabricmc.fabric.api.client.model.ModelProviderContext;
import net.fabricmc.fabric.api.client.model.ModelResourceProvider;
import net.minecraft.client.resources.model.UnbakedModel;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import org.jetbrains.annotations.Nullable;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

public class PlantModelProvider implements ModelResourceProvider {
    static final Gson GSON = new GsonBuilder()
            .registerTypeAdapter(PlantBlockModel.class, new PlantBlockModel.Deserializer())
            .registerTypeAdapter(PlantBlockElement.class, new PlantBlockElement.Deserializer())
            .registerTypeAdapter(PlantBlockElementFace.class, new PlantBlockElementFace.Deserializer())
            .registerTypeAdapter(PlantBlockFaceUV.class, new PlantBlockFaceUV.Deserializer()).create();
    private final ResourceManager resourceManager;

    public PlantModelProvider(ResourceManager resourceManager) {
        this.resourceManager = resourceManager;
    }

    @Override
    public @Nullable UnbakedModel loadModelResource(ResourceLocation location, ModelProviderContext context) {
        //TODO: Would probably be better to just get a list of valid locations from the Block registry (is a CustomPlantBlock)
        if (location.getNamespace().equals(Fossil.MOD_ID)) {
            try {
                try (Resource resource = resourceManager.getResource(new ResourceLocation(Fossil.MOD_ID, "models/"+location.getPath()+".json"))) {
                    try (InputStream inputStream = resource.getInputStream(); BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));) {
                        JsonObject jsonObject = GsonHelper.fromJson(GSON, reader, JsonElement.class).getAsJsonObject();
                        if (jsonObject.has("loader") && jsonObject.get("loader").getAsString().equals(PlantBlockModel.LOADER.toString())) {
                            return new FabricPlantUnbakedModel(GSON.getAdapter(PlantBlockModel.class).fromJsonTree(jsonObject));
                        }
                    } catch (JsonParseException | IOException | IllegalArgumentException exception) {
                        Fossil.LOGGER.error("Couldn't parse data file {}: {}", location, exception);
                    }
                }
            } catch (IOException exception) {
                //Fossil.LOGGER.error("Couldn't get Resource {}: {}", location, exception);
            }
        }
        return null;
    }
}
