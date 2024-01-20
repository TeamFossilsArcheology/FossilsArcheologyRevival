package com.fossil.fossil.entity.data;

import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.network.FriendlyByteBuf;
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
    public static final EntityDataManager ENTITY_DATA = new EntityDataManager(GSON);
    private ImmutableMap<String, Data> entities = ImmutableMap.of();

    public EntityDataManager(Gson gson) {
        super(gson, "entity_info");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsons, ResourceManager resourceManager, ProfilerFiller profiler) {
        ImmutableMap.Builder<String, Data> builder = ImmutableMap.builder();
        for (Map.Entry<ResourceLocation, JsonElement> fileEntry : jsons.entrySet()) {
            if (!(fileEntry.getValue() instanceof JsonObject root)) {
                continue;
            }
            Stat stat = GSON.getAdapter(Stat.class).fromJsonTree(root.getAsJsonObject("stats"));
            AI ai = GSON.getAdapter(AI.class).fromJsonTree(root.getAsJsonObject("ai"));
            float minScale = root.get("scaleBase").getAsFloat();
            float maxScale = root.get("scaleMax").getAsFloat();
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

    public ImmutableMap<String, Data> getEntities() {
        return entities;
    }

    public void replaceData(Map<String, Data> dataMap) {
        entities = ImmutableMap.copyOf(dataMap);
    }

    public record Data(Stat stats, AI ai, float minScale, float maxScale, int teenAgeDays, int adultAgeDays,
                       int maxHunger, int maxPopulation, boolean canBeRidden, boolean breaksBlocks) {

        public static Data readBuf(FriendlyByteBuf buf) {
            return new Data(Stat.readBuf(buf), AI.readBuf(buf), buf.readFloat(), buf.readFloat(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readBoolean(), buf.readBoolean());
        }

        public static void writeBuf(FriendlyByteBuf buf, Data data) {
            Stat.writeBuf(buf, data.stats);
            AI.writeBuf(buf, data.ai);
            buf.writeFloat(data.minScale).writeFloat(data.maxScale).writeInt(data.teenAgeDays).writeInt(data.adultAgeDays)
                    .writeInt(data.maxHunger).writeInt(data.maxPopulation).writeBoolean(data.canBeRidden).writeBoolean(data.breaksBlocks);
        }
    }
}
