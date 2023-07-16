package com.fossil.fossil.entity.data;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;

import java.util.Map;

/**
 * Loads static dino information from data/entity_info files
 */
public class EntityDataManager extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(Stat.class, new Stat.Supplier())
            .registerTypeAdapter(AI.class, new AI.Supplier())
            .disableHtmlEscaping().create();
    public static final EntityDataManager ENTITY_DATA = new EntityDataManager();
    private ImmutableMap<String, Data> entities = ImmutableMap.of();

    public EntityDataManager() {
        super(GSON, "entity_info");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsons, ResourceManager resourceManager, ProfilerFiller profiler) {
        ImmutableMap.Builder<String, Data> builder = ImmutableMap.builder();
        for (Map.Entry<ResourceLocation, JsonElement> fileEntry : jsons.entrySet()) {
            if (!(fileEntry.getValue() instanceof JsonObject)) {
                continue;
            }
            JsonObject root = (JsonObject) fileEntry.getValue();
            Stat stat = GSON.getAdapter(Stat.class).fromJsonTree(root.getAsJsonObject("stats"));
            AI ai = GSON.getAdapter(AI.class).fromJsonTree(root.getAsJsonObject("ai"));
            float minScale = root.get("minScale").getAsFloat();
            float maxScale = root.get("maxScale").getAsFloat();
            int teenAgeDays = root.get("teenAgeDays").getAsInt();
            int adultAgeDays = root.get("adultAgeDays").getAsInt();
            int maxHunger = root.get("maxHunger").getAsInt();
            int maxPopulation = root.has("maxPopulation") ? root.get("maxPopulation").getAsInt() : 15;
            boolean canBeRidden = root.has("canBeRidden") && root.get("canBeRidden").getAsBoolean();
            boolean breaksBlocks = root.has("breaksBlocks") && root.get("breaksBlocks").getAsBoolean();
            builder.put(fileEntry.getKey().getPath(), new Data(stat, ai, minScale, maxScale, teenAgeDays, adultAgeDays,
                    maxHunger, maxPopulation, canBeRidden, breaksBlocks));
        }
        entities = builder.build();
    }

    public Data getData(String entityName) {
        return entities.get(entityName);
    }

    public record Data(Stat stats, AI ai, float minScale, float maxScale, int teenAgeDays, int adultAgeDays,
                       int maxHunger, int maxPopulation, boolean canBeRidden, boolean breaksBlocks) {
    }
}
