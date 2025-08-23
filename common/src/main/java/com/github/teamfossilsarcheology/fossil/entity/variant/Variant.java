package com.github.teamfossilsarcheology.fossil.entity.variant;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.util.Gender;
import com.google.gson.*;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * A json defined texture variant. Can be automatically applied based on conditions
 *
 * @see VariantCondition
 * @see EntityVariantLoader
 */
public record Variant(String variantId, String textureName, boolean hasBabyTexture, boolean hasTeenTexture, boolean hasGenderTextures, VariantCondition[] conditions) {
    public static Variant readBuf(FriendlyByteBuf buf) {
        VariantCondition[] conditions = new VariantCondition[buf.readVarInt()];
        for (int i = 0; i < conditions.length; i++) {
            conditions[i] = VariantRegistry.fromNetwork(buf);
        }
        return new Variant(buf.readUtf(), buf.readUtf(), buf.readBoolean(), buf.readBoolean(), buf.readBoolean(), conditions);
    }

    public static void writeBuf(FriendlyByteBuf buf, Variant variant) {
        buf.writeVarInt(variant.conditions.length);
        for (VariantCondition condition : variant.conditions) {
            VariantRegistry.toNetwork(buf, condition);
        }
        buf.writeUtf(variant.variantId);
        buf.writeUtf(variant.textureName);
        buf.writeBoolean(variant.hasBabyTexture);
        buf.writeBoolean(variant.hasTeenTexture);
        buf.writeBoolean(variant.hasGenderTextures);
    }

    public void appendTextureString(StringBuilder builder, Prehistoric entity) {
        builder.append("_");
        builder.append(textureName);
        if (hasBabyTexture && entity.isBaby()) builder.append("_baby");
        if (hasTeenTexture && entity.isTeen()) builder.append("_teen");
        if (hasGenderTextures && (!hasTeenTexture && entity.isTeen() || entity.isAdult())) {
            if (entity.getGender() == Gender.MALE) {
                builder.append("_male");
            } else {
                builder.append("_female");
            }
        }
    }

    public String getVariantId() {
        return variantId;
    }

    static class Deserializer implements JsonDeserializer<Variant> {
        @Override
        public Variant deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject root = json.getAsJsonObject();
            String variantId = GsonHelper.getAsString(root, "variantId");
            String textureName = GsonHelper.getAsString(root, "textureName", "");

            JsonArray conditionArray = GsonHelper.getAsJsonArray(root, "conditions", new JsonArray());
            List<VariantCondition> conditions = new ArrayList<>();
            for (JsonElement condition : conditionArray) {
                try {
                    conditions.add(VariantCondition.deserialize(condition.getAsJsonObject()));
                } catch (JsonSyntaxException e) {
                    FossilMod.LOGGER.error("Could not parse {} condition for variant {}.", condition, variantId);
                }
            }

            boolean hasBabyTexture = GsonHelper.getAsBoolean(root, "hasBabyTexture", false);
            boolean hasTeenTexture = GsonHelper.getAsBoolean(root, "hasTeenTexture", false);
            boolean hasGenderTextures = GsonHelper.getAsBoolean(root, "hasGenderTextures", false);
            return new Variant(variantId, textureName, hasBabyTexture, hasTeenTexture, hasGenderTextures, conditions.toArray(VariantCondition[]::new));
        }
    }
}
