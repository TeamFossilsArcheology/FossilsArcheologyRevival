package com.fossil.fossil.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.GsonHelper;

import java.lang.reflect.Type;

public enum Diet implements DinopediaInfo {
    CARNIVORE(3),
    HERBIVORE(0),
    OMNIVORE(1),
    PISCIVORE(1),
    CARNIVORE_EGG(2),
    INSECTIVORE(0),
    PISCI_CARNIVORE(3),
    NONE(0);
    private final TranslatableComponent name = new TranslatableComponent("pedia.fossil.diet." + name().toLowerCase());
    private final TranslatableComponent description = new TranslatableComponent("pedia.fossil.diet." + name().toLowerCase() + ".desc");

    private final int fearIndex;

    Diet(int fearIndex) {
        this.fearIndex = fearIndex;
    }

    public int getFearIndex() {
        return this.fearIndex;
    }

    @Override
    public Component getName() {
        return name;
    }

    @Override
    public Component getDescription() {
        return description;
    }

    public static Diet readBuf(FriendlyByteBuf buf) {
        return Diet.valueOf(buf.readUtf());
    }

    public static void writeBuf(FriendlyByteBuf buf, Diet diet) {
        buf.writeUtf(diet.name());
    }

    public static class Supplier implements JsonDeserializer<Diet> {
        @Override
        public Diet deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return Diet.valueOf(GsonHelper.getAsString(json.getAsJsonObject(), "diet", Diet.NONE.name()));
        }
    }
}
