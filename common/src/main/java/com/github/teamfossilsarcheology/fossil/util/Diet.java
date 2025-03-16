package com.github.teamfossilsarcheology.fossil.util;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.util.GsonHelper;

import java.lang.reflect.Type;
import java.util.Locale;

public enum Diet implements DinopediaInfo {
    CARNIVORE(true, false, false),
    HERBIVORE(false, false, true),
    OMNIVORE(true, true, true),
    PISCIVORE(false, true, false),
    CARNIVORE_EGG(true, false, false),
    INSECTIVORE(true, false, false),
    PISCI_CARNIVORE(true, true, false),
    PASSIVE(false, false, false);
    private final Component name = new TranslatableComponent("pedia.fossil.diet." + name().toLowerCase(Locale.ROOT));
    private final Component description = new TranslatableComponent("pedia.fossil.diet." + name().toLowerCase(Locale.ROOT) + ".desc");

    private final boolean canEatMeat;
    private final boolean canEatFish;
    private final boolean canEatPlant;

    Diet(boolean canEatMeat, boolean canEatFish, boolean canEatPlant) {
        this.canEatMeat = canEatMeat;
        this.canEatFish = canEatFish;
        this.canEatPlant = canEatPlant;
    }

    public boolean canEatMeat() {
        return canEatMeat;
    }

    public boolean canEatFish() {
        return canEatFish;
    }

    public boolean canEatPlant() {
        return canEatPlant;
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

    public static class Deserializer implements JsonDeserializer<Diet> {
        @Override
        public Diet deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return Diet.valueOf(GsonHelper.getAsString(json.getAsJsonObject(), "diet", Diet.PASSIVE.name()));
        }
    }
}
