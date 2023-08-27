package com.fossil.fossil.entity.data;

import com.google.gson.*;
import net.minecraft.util.GsonHelper;

import java.lang.reflect.Type;

public record Stat(double baseDamage, double maxDamage, double baseHealth, double maxHealth, double baseSpeed,
                   double maxSpeed, double baseArmor, double maxArmor, double baseKnockBackResistance,
                   double maxKnockBackResistance) {
    public static class Supplier implements JsonDeserializer<Stat> {

        @Override
        public Stat deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonobject = json.getAsJsonObject();
            double baseDamage = GsonHelper.getAsDouble(jsonobject, "baseDamage");
            double maxDamage = GsonHelper.getAsDouble(jsonobject, "maxDamage");
            double baseHealth = GsonHelper.getAsDouble(jsonobject, "baseHealth");
            double maxHealth = GsonHelper.getAsDouble(jsonobject, "maxHealth");
            double baseSpeed = GsonHelper.getAsDouble(jsonobject, "baseSpeed");
            double maxSpeed = GsonHelper.getAsDouble(jsonobject, "maxSpeed");
            double baseArmor = GsonHelper.getAsDouble(jsonobject, "baseArmor");
            double maxArmor = GsonHelper.getAsDouble(jsonobject, "maxArmor");
            double baseKnockBackResistance = GsonHelper.getAsDouble(jsonobject, "baseKnockBackResistance");
            double maxKnockBackResistance = GsonHelper.getAsDouble(jsonobject, "maxKnockBackResistance");
            return new Stat(baseDamage, maxDamage, baseHealth, maxHealth, baseSpeed, maxSpeed, baseArmor, maxArmor, baseKnockBackResistance, maxKnockBackResistance);
        }
    }
}
