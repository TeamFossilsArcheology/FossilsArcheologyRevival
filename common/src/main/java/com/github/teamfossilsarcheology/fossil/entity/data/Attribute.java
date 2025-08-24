package com.github.teamfossilsarcheology.fossil.entity.data;

import com.google.gson.*;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;

import java.lang.reflect.Type;

/**
 * Entity attributes ranges
 */
public record Attribute(double baseDamage, double maxDamage, double baseHealth, double maxHealth, double sprintMod, double baseSpeed,
                        double minSpeed, double maxSpeed, double baseSwimSpeed, double minSwimSpeed, double maxSwimSpeed,
                        double baseArmor, double maxArmor, double baseKnockBackResistance, double maxKnockBackResistance) {
    public static Attribute readBuf(FriendlyByteBuf buf) {
        return new Attribute(buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(),
                buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(), buf.readDouble(),
                buf.readDouble(), buf.readDouble(), buf.readDouble());
    }

    public static void writeBuf(FriendlyByteBuf buf, Attribute attribute) {
        buf.writeDouble(attribute.baseDamage).writeDouble(attribute.maxDamage)
                .writeDouble(attribute.baseHealth).writeDouble(attribute.maxHealth)
                .writeDouble(attribute.sprintMod)
                .writeDouble(attribute.baseSpeed).writeDouble(attribute.minSpeed).writeDouble(attribute.maxSpeed)
                .writeDouble(attribute.baseSwimSpeed).writeDouble(attribute.minSwimSpeed).writeDouble(attribute.maxSwimSpeed)
                .writeDouble(attribute.baseArmor).writeDouble(attribute.maxArmor)
                .writeDouble(attribute.baseKnockBackResistance).writeDouble(attribute.maxKnockBackResistance);
    }

    public static class Deserializer implements JsonDeserializer<Attribute> {

        @Override
        public Attribute deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            JsonObject jsonobject = json.getAsJsonObject();
            double baseDamage = GsonHelper.getAsDouble(jsonobject, "damageBase");
            double maxDamage = GsonHelper.getAsDouble(jsonobject, "damageMax");
            double baseHealth = GsonHelper.getAsDouble(jsonobject, "healthBase");
            double maxHealth = GsonHelper.getAsDouble(jsonobject, "healthMax");
            double baseSpeed = GsonHelper.getAsDouble(jsonobject, "speedBase");
            double minSpeed = GsonHelper.getAsDouble(jsonobject, "speedMin", baseSpeed);
            double maxSpeed = GsonHelper.getAsDouble(jsonobject, "speedMax");
            double sprintMod = GsonHelper.getAsDouble(jsonobject, "sprintMod", 1.4);
            double baseSwimSpeed = GsonHelper.getAsDouble(jsonobject, "swimSpeedBase", baseSpeed);
            double minSwimSpeed = GsonHelper.getAsDouble(jsonobject, "swimSpeedMin", jsonobject.has("swimSpeedBase") ? baseSwimSpeed : minSpeed);
            double maxSwimSpeed = GsonHelper.getAsDouble(jsonobject, "swimSpeedMax", maxSpeed);
            double baseArmor = GsonHelper.getAsDouble(jsonobject, "armorBase");
            double maxArmor = GsonHelper.getAsDouble(jsonobject, "armorMax");
            double baseKnockBackResistance = GsonHelper.getAsDouble(jsonobject, "knockBackResistanceBase");
            double maxKnockBackResistance = GsonHelper.getAsDouble(jsonobject, "knockBackResistanceMax");
            return new Attribute(baseDamage, maxDamage, baseHealth, maxHealth, sprintMod, baseSpeed, minSpeed, maxSpeed,
                    baseSwimSpeed, minSwimSpeed, maxSwimSpeed, baseArmor, maxArmor, baseKnockBackResistance, maxKnockBackResistance);
        }
    }
}
