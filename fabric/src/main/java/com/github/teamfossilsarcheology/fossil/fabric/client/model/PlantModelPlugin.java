package com.github.teamfossilsarcheology.fossil.fabric.client.model;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.client.model.block.PlantBlockModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.fabricmc.fabric.api.client.model.loading.v1.ModelLoadingPlugin;
import net.fabricmc.fabric.api.client.model.loading.v1.PreparableModelLoadingPlugin;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import org.apache.commons.io.IOUtils;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;

public class PlantModelPlugin implements PreparableModelLoadingPlugin<Map<ResourceLocation, FabricPlantUnbakedModel>> {

    @Override
    public void onInitializeModelLoader(Map<ResourceLocation, FabricPlantUnbakedModel> data, ModelLoadingPlugin.Context pluginContext) {
        pluginContext.resolveModel().register(context -> data.get(context.id()));
    }

    public static class Loader implements DataLoader<Map<ResourceLocation, FabricPlantUnbakedModel>> {
        static final Gson GSON = new GsonBuilder()
                .registerTypeAdapter(PlantBlockModel.class, new PlantBlockModel.Deserializer())
                .registerTypeAdapter(PlantBlockModel.PlantBlockElement.class, new PlantBlockModel.PlantBlockElement.Deserializer())
                .registerTypeAdapter(PlantBlockModel.PlantBlockElementFace.class, new PlantBlockModel.PlantBlockElementFace.Deserializer())
                .registerTypeAdapter(PlantBlockModel.PlantBlockFaceUV.class, new PlantBlockModel.PlantBlockFaceUV.Deserializer()).create();

        @Override
        public CompletableFuture<Map<ResourceLocation, FabricPlantUnbakedModel>> load(ResourceManager resourceManager, Executor executor) {
            return CompletableFuture.supplyAsync(() -> resourceManager.listResources("models",
                    location -> location.getNamespace().equals(FossilMod.MOD_ID) && location.getPath().contains(".json")), executor).thenApplyAsync(
                    resources -> {
                        Map<ResourceLocation, FabricPlantUnbakedModel> map = new Object2ObjectOpenHashMap<>();
                        for (ResourceLocation location : resources.keySet()) {
                            try {
                                Resource resource = resourceManager.getResourceOrThrow(location);
                                try (InputStream inputStream = resource.open()) {
                                    JsonObject jsonObject = GsonHelper.fromJson(GSON, IOUtils.toString(inputStream, Charset.defaultCharset()), JsonElement.class).getAsJsonObject();
                                    if (jsonObject.has("loader") && jsonObject.get("loader").getAsString().equals(PlantBlockModel.LOADER.toString())) {
                                        map.put(fixLocation(location), new FabricPlantUnbakedModel(GSON.getAdapter(PlantBlockModel.class).fromJsonTree(jsonObject)));
                                    }
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            } catch (FileNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                        }
                        return map;
                    }
            );
        }

        private static ResourceLocation fixLocation(ResourceLocation location) {
            return new ResourceLocation(location.getNamespace(), location.getPath().replaceFirst("models/", "").replaceFirst(".json", ""));
        }
    }
}
