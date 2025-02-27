package com.github.teamfossilsarcheology.fossil.client.model.block;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.google.common.collect.Maps;
import com.google.gson.*;
import net.minecraft.client.renderer.texture.TextureAtlas;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.GsonHelper;
import org.jetbrains.annotations.Nullable;
import org.joml.Vector3f;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is a modification of the vanilla {@link net.minecraft.client.renderer.block.model.BlockModel BlockModel} that
 * has no restrictions on rotations and block size
 */
public record PlantBlockModel(List<PlantBlockElement> elements, HashMap<String, Material> materials) {

    public static final ResourceLocation LOADER = FossilMod.location("plant_loader");

    public static class Deserializer implements JsonDeserializer<PlantBlockModel> {

        @Override
        public PlantBlockModel deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonObject = json.getAsJsonObject();
            List<PlantBlockElement> list = new ArrayList<>();

            //We can't use "elements" because the vanilla deserializer might try and fail to parse the file first
            if (jsonObject.has("parts")) {
                for (JsonElement jsonElement : GsonHelper.getAsJsonArray(jsonObject, "parts")) {
                    list.add(context.deserialize(jsonElement, PlantBlockElement.class));
                }
            }

            HashMap<String, Material> materials = Maps.newHashMap();
            if (jsonObject.has("textures")) {
                JsonObject textureSet = GsonHelper.getAsJsonObject(jsonObject, "textures");
                for (Map.Entry<String, JsonElement> entry : textureSet.entrySet()) {
                    materials.put(entry.getKey(), new Material(TextureAtlas.LOCATION_BLOCKS, ResourceLocation.tryParse(entry.getValue().getAsString())));
                }
            }
            return new PlantBlockModel(list, materials);
        }
    }

    public record PlantBlockElement(Vector3f from, Vector3f to, Vector3f origin, Vector3f rotations,
                                    Map<Direction, PlantBlockElementFace> faces, String name) {

        public static class Deserializer implements JsonDeserializer<PlantBlockElement> {

            @Override
            public PlantBlockElement deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                JsonObject jsonObject = json.getAsJsonObject();
                Vector3f from = getVector3f(jsonObject, "from");
                Vector3f to = getVector3f(jsonObject, "to");
                Vector3f origin = jsonObject.has("origin") ? getVector3f(jsonObject, "origin") : new Vector3f(0, 0, 0);
                origin.mul(1 / 16f);
                Vector3f rotations = jsonObject.has("rotation") ? getVector3f(jsonObject, "rotation") : new Vector3f(0, 0, 0);
                Map<Direction, PlantBlockElementFace> map = getFaces(context, jsonObject);
                return new PlantBlockElement(from, to, origin, rotations, map, jsonObject.has("name") ? jsonObject.get("name").getAsString() : "");
            }

            private Map<Direction, PlantBlockElementFace> getFaces(JsonDeserializationContext context, JsonObject json) {
                Map<Direction, PlantBlockElementFace> map = Maps.newEnumMap(Direction.class);
                JsonObject jsonObject = GsonHelper.getAsJsonObject(json, "faces");
                for (Map.Entry<String, JsonElement> entry : jsonObject.entrySet()) {
                    Direction direction = getFacing(entry.getKey());
                    map.put(direction, context.deserialize(entry.getValue(), PlantBlockElementFace.class));
                }
                if (map.isEmpty()) {
                    throw new JsonParseException("Expected between 1 and 6 unique faces, got 0");
                }
                return map;
            }

            private Direction getFacing(String name) {
                Direction direction = Direction.byName(name);
                if (direction == null) {
                    throw new JsonParseException("Unknown facing: " + name);
                }
                return direction;
            }

            private Vector3f getVector3f(JsonObject json, String name) {
                JsonArray jsonArray = GsonHelper.getAsJsonArray(json, name);
                if (jsonArray.size() != 3) {
                    throw new JsonParseException("Expected 3 " + name + " values, found: " + jsonArray.size());
                }
                float[] fs = new float[3];
                for (int i = 0; i < fs.length; ++i) {
                    fs[i] = GsonHelper.convertToFloat(jsonArray.get(i), name + "[" + i + "]");
                }
                return new Vector3f(fs[0], fs[1], fs[2]);
            }
        }
    }

    public record PlantBlockElementFace(String texture, PlantBlockFaceUV uv) {
        public static class Deserializer implements JsonDeserializer<PlantBlockElementFace> {
            @Override
            public PlantBlockElementFace deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                JsonObject jsonObject = json.getAsJsonObject();
                String texture = GsonHelper.getAsString(jsonObject, "texture");
                PlantBlockFaceUV blockFaceUV = context.deserialize(jsonObject, PlantBlockFaceUV.class);
                return new PlantBlockElementFace(texture, blockFaceUV);
            }
        }
    }

    public record PlantBlockFaceUV(float[] uvs, int rotation) {

        public float getU(int index) {
            if (uvs == null) {
                throw new NullPointerException("uvs");
            }
            int i = getShiftedIndex(index);
            return uvs[i == 0 || i == 1 ? 0 : 2];
        }

        public float getV(int index) {
            if (uvs == null) {
                throw new NullPointerException("uvs");
            }
            int i = getShiftedIndex(index);
            return uvs[i == 0 || i == 3 ? 1 : 3];
        }

        private int getShiftedIndex(int index) {
            return (index + rotation / 90) % 4;
        }

        public static class Deserializer implements JsonDeserializer<PlantBlockFaceUV> {
            @Override
            public PlantBlockFaceUV deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
                JsonObject jsonObject = json.getAsJsonObject();
                return new PlantBlockFaceUV(getUVs(jsonObject), getRotation(jsonObject));
            }

            protected int getRotation(JsonObject json) {
                return GsonHelper.getAsInt(json, "rotation", 0);
            }

            private float @Nullable [] getUVs(JsonObject json) {
                if (!json.has("uv")) {
                    return null;
                }
                JsonArray jsonArray = GsonHelper.getAsJsonArray(json, "uv");
                if (jsonArray.size() != 4) {
                    throw new JsonParseException("Expected 4 uv values, found: " + jsonArray.size());
                }
                float[] fs = new float[4];
                for (int i = 0; i < fs.length; ++i) {
                    fs[i] = GsonHelper.convertToFloat(jsonArray.get(i), "uv[" + i + "]");
                }
                return fs;
            }
        }
    }
}
