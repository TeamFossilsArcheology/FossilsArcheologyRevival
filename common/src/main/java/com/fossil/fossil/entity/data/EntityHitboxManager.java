package com.fossil.fossil.entity.data;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.gson.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.phys.Vec3;

import java.util.List;
import java.util.Map;


//TODO: Hitbox system explanation
/**
 *
 */
public class EntityHitboxManager extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new GsonBuilder().disableHtmlEscaping().create();
    public static final EntityHitboxManager HITBOX_DATA = new EntityHitboxManager(GSON);
    private ImmutableMap<String, List<Hitbox>> entities = ImmutableMap.of();

    public EntityHitboxManager(Gson gson) {
        super(gson, "hitboxes");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsons, ResourceManager resourceManager, ProfilerFiller profiler) {
        ImmutableMap.Builder<String, List<Hitbox>> builder = ImmutableMap.builder();
        for (Map.Entry<ResourceLocation, JsonElement> fileEntry : jsons.entrySet()) {
            if (!(fileEntry.getValue() instanceof JsonObject root)) {
                continue;
            }
            JsonArray elements = GsonHelper.getAsJsonArray(root, "elements");
            ImmutableList.Builder<Hitbox> listBuilder = ImmutableList.builder();
            for (JsonElement element : elements) {
                JsonObject elemObject = element.getAsJsonObject();
                double[] pos = new double[3];
                JsonArray posArray = GsonHelper.getAsJsonArray(elemObject, "pos");
                for (int i = 0; i < pos.length; ++i) {
                    pos[i] = GsonHelper.convertToDouble(posArray.get(i),"pos[" + i + "]");
                }
                float width = GsonHelper.getAsFloat(elemObject, "width") / 16;
                float height = GsonHelper.getAsFloat(elemObject, "height") / 16;

                JsonElement refElement = elemObject.get("ref");
                String ref = refElement == null ? null : refElement.getAsString();
                listBuilder.add(new Hitbox(elemObject.get("name").getAsString(), new Vec3(pos[0] / 16, pos[1] / 16, pos[2] / 16), width, height, ref));
            }
            builder.put(fileEntry.getKey().getPath(), listBuilder.build());
        }
        entities = builder.build();
    }

    public List<Hitbox> getHitboxes(String entityName) {
        return entities.get(entityName);
    }

    public record Hitbox(String name, Vec3 pos, float width, float height, String ref) {
        public float getFrustumWidthRadius() {
            return (float) Math.max(Math.abs(pos.x) + width, Math.abs(pos.z) + width);
        }
        public float getFrustumHeightRadius() {
            return (float) pos.y + height;
        }
    }
}
