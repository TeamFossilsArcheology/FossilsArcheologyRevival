package com.github.teamfossilsarcheology.fossil.food;

import com.github.teamfossilsarcheology.fossil.util.DinopediaInfo;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.chat.Component;
import net.minecraft.util.GsonHelper;

import java.lang.reflect.Type;
import java.util.EnumSet;
import java.util.Locale;
import java.util.Set;

import static com.github.teamfossilsarcheology.fossil.food.FoodType.*;

public enum Diet implements DinopediaInfo {
    CARNIVORE(MEAT),
    HERBIVORE(PLANT),
    OMNIVORE(MEAT, PLANT, FISH),
    PISCIVORE(FISH),
    CARNIVORE_EGG(MEAT, EGG),
    INSECTIVORE(MEAT),
    PISCI_CARNIVORE(MEAT, FISH),
    PASSIVE;
    private final Component name = Component.translatable("pedia.fossil.diet." + name().toLowerCase(Locale.ROOT));
    private final Component description = Component.translatable("pedia.fossil.diet." + name().toLowerCase(Locale.ROOT) + ".desc");
    private final Set<FoodType> flag;

    Diet(FoodType... types) {
        this.flag = types.length > 0 ? EnumSet.of(types[0], types) : EnumSet.noneOf(FoodType.class);
    }

    public boolean canEat(FoodType type) {
        return flag.contains(type);
    }

    public Set<FoodType> flags() {
        return flag;
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
