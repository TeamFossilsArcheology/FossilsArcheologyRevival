package com.github.teamfossilsarcheology.fossil.entity.variant;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.minecraft.ChatFormatting;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.util.GsonHelper;
import net.minecraft.world.entity.Entity;

import java.lang.reflect.Type;

/**
 * With this condition a variant will be applied when the entity has been named with a matching name tag.
 * Required json entries: <b>type: "nametag"</b>, <b>name</b>
 */
public class NameTagCondition extends VariantCondition {
    private final String nameTagName;

    public NameTagCondition(String nameTagName) {
        super(1);
        this.nameTagName = nameTagName;
    }

    public static class Deserializer implements JsonDeserializer<NameTagCondition> {
        @Override
        public NameTagCondition deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return new NameTagCondition(GsonHelper.getAsString(json.getAsJsonObject(), "name"));
        }
    }

    public static void save(CompoundTag tag, NameTagCondition condition) {
        tag.putString("NameTagName", condition.nameTagName);
    }

    public static NameTagCondition load(CompoundTag tag) {
        return new NameTagCondition(tag.getString("NameTagName"));
    }

    public boolean test(Component component) {
        return nameTagName.equals(ChatFormatting.stripFormatting(component.getString()));
    }

    public boolean test(Entity entity) {
        return test(entity.getName());
    }
}
