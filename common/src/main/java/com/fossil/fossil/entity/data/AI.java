package com.fossil.fossil.entity.data;

import com.google.gson.*;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;

import java.lang.reflect.Type;

import static com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityInfoAI.*;

public record AI(Activity activity, Attacking attacking, Climbing climbing, Following following, Jumping jumping,
                 Response response, Stalking stalking, Taming taming, Untaming untaming, Moving moving,
                 WaterAbility waterAbility) {
    public static <T extends Enum<T>> T getInstance(Class<T> enumClass, JsonObject jsonObject, String fallback) {
        try {
            return Enum.valueOf(enumClass, GsonHelper.getAsString(jsonObject, enumClass.getSimpleName().toLowerCase(), fallback).toUpperCase());
        } catch (IllegalArgumentException e) {
            return Enum.valueOf(enumClass, fallback.toUpperCase());
        }

    }

    public static AI readBuf(FriendlyByteBuf buf) {
        return new AI(Activity.valueOf(buf.readUtf()), Attacking.valueOf(buf.readUtf()), Climbing.valueOf(buf.readUtf()),
                Following.valueOf(buf.readUtf()), Jumping.valueOf(buf.readUtf()), Response.valueOf(buf.readUtf()),
                Stalking.valueOf(buf.readUtf()), Taming.valueOf(buf.readUtf()), Untaming.valueOf(buf.readUtf()),
                Moving.valueOf(buf.readUtf()), WaterAbility.valueOf(buf.readUtf()));
    }

    public static void writeBuf(FriendlyByteBuf buf, AI ai) {
        buf.writeUtf(ai.activity.name()).writeUtf(ai.attacking.name()).writeUtf(ai.climbing.name()).writeUtf(ai.following.name())
                .writeUtf(ai.jumping.name()).writeUtf(ai.response.name()).writeUtf(ai.stalking.name()).writeUtf(ai.taming.name())
                .writeUtf(ai.untaming.name()).writeUtf(ai.moving.name()).writeUtf(ai.waterAbility.name());
    }

    public static class Supplier implements JsonDeserializer<AI> {

        @Override
        public AI deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonobject = json.getAsJsonObject();
            Activity activity = getInstance(Activity.class, jsonobject, Activity.BOTH.name());
            Attacking attacking = getInstance(Attacking.class, jsonobject, Attacking.BASIC.name());
            Climbing climbing = getInstance(Climbing.class, jsonobject, Climbing.NONE.name());
            Following following = getInstance(Following.class, jsonobject, Following.NONE.name());
            Jumping jumping = getInstance(Jumping.class, jsonobject, Jumping.BASIC.name());
            Response response = getInstance(Response.class, jsonobject, Response.CALM.name());
            Stalking stalking = getInstance(Stalking.class, jsonobject, Stalking.NONE.name());
            Taming taming = getInstance(Taming.class, jsonobject, Taming.NONE.name());
            Untaming untaming = getInstance(Untaming.class, jsonobject, Untaming.NONE.name());
            Moving moving = getInstance(Moving.class, jsonobject, Moving.WALK.name());
            WaterAbility waterAbility = getInstance(WaterAbility.class, jsonobject, WaterAbility.NONE.name());
            return new AI(activity, attacking, climbing, following, jumping, response, stalking, taming, untaming, moving, waterAbility);
        }
    }
}
