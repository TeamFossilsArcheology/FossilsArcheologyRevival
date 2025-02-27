package com.github.teamfossilsarcheology.fossil.fabric.client.model;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.client.model.block.PlantBlockModel;
import com.github.teamfossilsarcheology.fossil.client.model.block.PlantBlockModel.PlantBlockElement;
import com.github.teamfossilsarcheology.fossil.client.model.block.PlantBlockModel.PlantBlockElementFace;
import com.github.teamfossilsarcheology.fossil.client.model.block.PlantBlockModel.PlantBlockFaceUV;
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
import java.util.Optional;

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
        if (location.getNamespace().equals(FossilMod.MOD_ID)) {
            Optional<Resource> opt = resourceManager.getResource(FossilMod.location("models/" + location.getPath() + ".json"));
            if (opt.isEmpty()) {
                return null;
            }
            try (InputStream inputStream = opt.get().open(); BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8));) {
                JsonObject jsonObject = GsonHelper.fromJson(GSON, reader, JsonElement.class).getAsJsonObject();
                if (jsonObject.has("loader") && jsonObject.get("loader").getAsString().equals(PlantBlockModel.LOADER.toString())) {
                    return new FabricPlantUnbakedModel(GSON.getAdapter(PlantBlockModel.class).fromJsonTree(jsonObject));
                }
            } catch (JsonParseException | IOException | IllegalArgumentException exception) {
                FossilMod.LOGGER.error("Couldn't parse data file {}: {}", location, exception);
            }
        }
        return null;
    }
}
