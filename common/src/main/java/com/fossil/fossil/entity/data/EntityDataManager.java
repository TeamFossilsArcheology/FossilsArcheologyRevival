package com.fossil.fossil.entity.data;

import com.fossil.fossil.util.Diet;
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
    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(Attribute.class, new Attribute.Supplier())
            .registerTypeAdapter(AI.class, new AI.Supplier()).registerTypeAdapter(Diet.class, new Diet.Supplier())
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
            Attribute attribute = GSON.getAdapter(Attribute.class).fromJsonTree(root.getAsJsonObject("attributes"));
            AI ai = GSON.getAdapter(AI.class).fromJsonTree(root.getAsJsonObject("ai"));
            Diet diet = GSON.fromJson(root, Diet.class);
            float eggScale = root.has("eggScale") ? root.get("eggScale").getAsFloat() : 1;
            float minScale = root.get("scaleBase").getAsFloat();
            float maxScale = root.get("scaleMax").getAsFloat();
            int teenAgeDays = root.get("teenAgeDays").getAsInt();
            int adultAgeDays = root.get("adultAgeDays").getAsInt();
            int maxHunger = root.get("maxHunger").getAsInt();
            int maxPopulation = root.has("maxPopulation") ? root.get("maxPopulation").getAsInt() : 15;
            boolean canBeRidden = root.has("canBeRidden") && root.get("canBeRidden").getAsBoolean();
            boolean breaksBlocks = root.has("breaksBlocks") && root.get("breaksBlocks").getAsBoolean();
            builder.put(fileEntry.getKey().getPath(), new Data(attribute, ai, diet, eggScale, minScale, maxScale, teenAgeDays, adultAgeDays,
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

    public record Data(Attribute attributes, AI ai, Diet diet, float eggScale, float minScale, float maxScale,
                       int teenAgeDays, int adultAgeDays,
                       int maxHunger, int maxPopulation, boolean canBeRidden, boolean breaksBlocks) {

        public static Data readBuf(FriendlyByteBuf buf) {
            return new Data(Attribute.readBuf(buf), AI.readBuf(buf), Diet.readBuf(buf), buf.readFloat(), buf.readFloat(), buf.readFloat(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readInt(), buf.readBoolean(), buf.readBoolean());
        }

        public static void writeBuf(FriendlyByteBuf buf, Data data) {
            Attribute.writeBuf(buf, data.attributes);
            AI.writeBuf(buf, data.ai);
            Diet.writeBuf(buf, data.diet);
            buf.writeFloat(data.eggScale).writeFloat(data.minScale).writeFloat(data.maxScale).writeInt(data.teenAgeDays).writeInt(data.adultAgeDays)
                    .writeInt(data.maxHunger).writeInt(data.maxPopulation).writeBoolean(data.canBeRidden).writeBoolean(data.breaksBlocks);
        }
    }
}
