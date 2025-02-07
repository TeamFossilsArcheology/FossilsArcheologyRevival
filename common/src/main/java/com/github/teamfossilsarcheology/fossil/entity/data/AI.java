package com.github.teamfossilsarcheology.fossil.entity.data;

import com.google.gson.*;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;

import java.lang.reflect.Type;
import java.util.Locale;

import static com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfoAI.*;

/**
 * AI Behaviour records
 */
public record AI(Activity activity, Attacking attacking, Climbing climbing, Response response, Taming taming, Moving moving) {
    public static <T extends Enum<T>> T getInstance(Class<T> enumClass, JsonObject jsonObject, String fallback) {
        try {
            return Enum.valueOf(enumClass, GsonHelper.getAsString(jsonObject, enumClass.getSimpleName().toLowerCase(Locale.ROOT), fallback).toUpperCase());
        } catch (IllegalArgumentException e) {
            return Enum.valueOf(enumClass, fallback.toUpperCase());
        }

    }

    public static AI readBuf(FriendlyByteBuf buf) {
        return new AI(Activity.valueOf(buf.readUtf()), Attacking.valueOf(buf.readUtf()), Climbing.valueOf(buf.readUtf()),
                Response.valueOf(buf.readUtf()), Taming.valueOf(buf.readUtf()), Moving.valueOf(buf.readUtf()));
    }

    public static void writeBuf(FriendlyByteBuf buf, AI ai) {
        buf.writeUtf(ai.activity.name()).writeUtf(ai.attacking.name()).writeUtf(ai.climbing.name())
                .writeUtf(ai.response.name()).writeUtf(ai.taming.name()).writeUtf(ai.moving.name());
    }

    public static class Deserializer implements JsonDeserializer<AI> {

        @Override
        public AI deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonobject = json.getAsJsonObject();
            Activity activity = getInstance(Activity.class, jsonobject, Activity.BOTH.name());
            Attacking attacking = getInstance(Attacking.class, jsonobject, Attacking.BASIC.name());
            Climbing climbing = getInstance(Climbing.class, jsonobject, Climbing.NONE.name());
            Response response = getInstance(Response.class, jsonobject, Response.CALM.name());
            Taming taming = getInstance(Taming.class, jsonobject, Taming.NONE.name());
            Moving moving = getInstance(Moving.class, jsonobject, Moving.WALK.name());
            return new AI(activity, attacking, climbing, response, taming, moving);
        }
    }
}
