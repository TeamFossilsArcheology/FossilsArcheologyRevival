package com.github.teamfossilsarcheology.fossil.entity.variant;

import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.Tag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;

import java.lang.reflect.Type;

/**
 * With this condition a variant will be applied when a CompoundTag has a certain tag
 * Required json entries: <b>type: "nbt"</b>, <b>key</b>
 */
public class NbtCondition extends VariantCondition {
    private final String nbtKey;

    public NbtCondition(String nbtKey) {
        super(1);
        this.nbtKey = nbtKey;
    }

    public boolean test(CompoundTag tag) {
        return tag.contains(nbtKey, Tag.TAG_BYTE) && tag.getBoolean(nbtKey);
    }

    public static class Serializer implements VariantCondition.Serializer<NbtCondition> {
        @Override
        public NbtCondition deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return new NbtCondition(GsonHelper.getAsString(json.getAsJsonObject(), "key"));
        }

        @Override
        public void save(CompoundTag tag, NbtCondition condition) {
            tag.putString("NbtKey", condition.nbtKey);
        }

        @Override
        public NbtCondition load(CompoundTag tag) {
            return new NbtCondition(tag.getString("NbtKey"));
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, NbtCondition condition) {
            buf.writeUtf(condition.nbtKey);
        }

        @Override
        public NbtCondition fromNetwork(FriendlyByteBuf buf) {
            return new NbtCondition(buf.readUtf());
        }
    }
}
