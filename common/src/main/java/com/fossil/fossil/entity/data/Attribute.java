package com.fossil.fossil.entity.data;

import com.google.gson.*;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;

import java.lang.reflect.Type;

public record Attribute(double baseDamage, double maxDamage, double baseHealth, double maxHealth, double baseSpeed, double minSpeed,
                        double maxSpeed, double baseArmor, double maxArmor, double baseKnockBackResistance,
                        double maxKnockBackResistance) {
    public static Attribute readBuf(FriendlyByteBuf buf) {
        return new Attribute(buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble());
    }

    public static void writeBuf(FriendlyByteBuf buf, Attribute attribute) {
        buf.writeDouble(attribute.baseDamage).writeDouble(attribute.maxDamage).writeDouble(attribute.baseHealth).writeDouble(attribute.maxHealth)
                .writeDouble(attribute.baseSpeed).writeDouble(attribute.minSpeed).writeDouble(attribute.maxSpeed).writeDouble(attribute.baseArmor)
                .writeDouble(attribute.maxArmor).writeDouble(attribute.baseKnockBackResistance).writeDouble(attribute.maxKnockBackResistance);
    }

    public static class Supplier implements JsonDeserializer<Attribute> {

        @Override
        public Attribute deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
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
            return new Attribute(baseDamage, maxDamage, baseHealth, maxHealth, baseSpeed, minSpeed, maxSpeed, baseArmor, maxArmor, baseKnockBackResistance, maxKnockBackResistance);
        }
    }
}
