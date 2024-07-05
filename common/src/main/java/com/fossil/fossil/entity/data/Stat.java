package com.fossil.fossil.entity.data;

import com.google.gson.*;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;

import java.lang.reflect.Type;

public record Stat(double baseDamage, double maxDamage, double baseHealth, double maxHealth, double baseSpeed, double minSpeed,
                   double maxSpeed, double baseArmor, double maxArmor, double baseKnockBackResistance,
                   double maxKnockBackResistance) {
    public static Stat readBuf(FriendlyByteBuf buf) {
        return new Stat(buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble());
    }

    public static void writeBuf(FriendlyByteBuf buf, Stat stat) {
        buf.writeDouble(stat.baseDamage).writeDouble(stat.maxDamage).writeDouble(stat.baseHealth).writeDouble(stat.maxHealth)
                .writeDouble(stat.baseSpeed).writeDouble(stat.minSpeed).writeDouble(stat.maxSpeed).writeDouble(stat.baseArmor)
                .writeDouble(stat.maxArmor).writeDouble(stat.baseKnockBackResistance).writeDouble(stat.maxKnockBackResistance);
    }

    public static class Supplier implements JsonDeserializer<Stat> {

        @Override
        public Stat deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonobject = json.getAsJsonObject();
            double baseDamage = GsonHelper.getAsDouble(jsonobject, "damageBase");
            double maxDamage = GsonHelper.getAsDouble(jsonobject, "damageMax");
            double baseHealth = GsonHelper.getAsDouble(jsonobject, "healthBase");
            double maxHealth = GsonHelper.getAsDouble(jsonobject, "healthMax");
            double baseSpeed = GsonHelper.getAsDouble(jsonobject, "speedBase");
            double minSpeed = jsonobject.get("speedMin") != null ? GsonHelper.getAsDouble(jsonobject, "speedMin") : baseSpeed;
            double maxSpeed = GsonHelper.getAsDouble(jsonobject, "speedMax");
            double baseArmor = GsonHelper.getAsDouble(jsonobject, "armorBase");
            double maxArmor = GsonHelper.getAsDouble(jsonobject, "armorMax");
            double baseKnockBackResistance = GsonHelper.getAsDouble(jsonobject, "knockBackResistanceBase");
            double maxKnockBackResistance = GsonHelper.getAsDouble(jsonobject, "knockBackResistanceMax");
            return new Stat(baseDamage, maxDamage, baseHealth, maxHealth, baseSpeed, minSpeed, maxSpeed, baseArmor, maxArmor, baseKnockBackResistance, maxKnockBackResistance);
        }
    }
}
