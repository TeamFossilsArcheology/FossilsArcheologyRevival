package com.github.teamfossilsarcheology.fossil.entity.variant;

import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;
import com.google.gson.JsonSyntaxException;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.Mth;

/**
 * Base class for variant conditions
 *
 * @see VariantRegistry
 * @see Variant
 */
public abstract class VariantCondition {
    protected final double chance;

    /**
     * @param chance a number between 0 and 1. A value of 1 = 100%
     */
    protected VariantCondition(double chance) {
        this.chance = Mth.clamp(chance, 0, 1);
    }

    public static VariantCondition deserialize(JsonObject object) {
        if (!object.has("type")) {
            throw new JsonSyntaxException("Missing type entry");
        }
        String type = object.get("type").getAsString();
        return VariantRegistry.get(type).fromJson(object);
    }

    public record WithVariant<T extends VariantCondition>(T condition, Variant variant) {
        public static <T extends VariantCondition> WithVariant<T> of(T condition, Variant variant) {
            return new WithVariant<>(condition, variant);
        }
    }

    public interface Serializer<T extends VariantCondition> extends JsonDeserializer<T> {
        void save(CompoundTag tag, T condition);

        T load(CompoundTag tag);

        void toNetwork(FriendlyByteBuf buf, T condition);

        T fromNetwork(FriendlyByteBuf buf);
    }
}
