package com.github.teamfossilsarcheology.fossil.entity.variant;

import com.github.teamfossilsarcheology.fossil.config.FossilConfig;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.util.GsonHelper;

import java.lang.reflect.Type;
import java.util.Objects;

/**
 * With this condition a variant will be automatically applied if a config option is enabled.
 * Required json entries: <b>type: "config"</b>, <b>configKey</b>
 */
public class ConfigCondition extends VariantCondition {
    private final String configKey;

    /**
     * @param configKey a key from {@link FossilConfig}
     */
    private ConfigCondition(String configKey) {
        super(1);
        this.configKey = configKey;
    }

    /**
     * @return {@code true} if the config option is enabled
     */
    public boolean test() {
        return FossilConfig.isEnabled(configKey);
    }

    @Override
    public boolean equals(Object object) {
        if (this == object) return true;
        if (object == null || getClass() != object.getClass()) return false;
        ConfigCondition that = (ConfigCondition) object;
        return Objects.equals(configKey, that.configKey);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(configKey);
    }

    public static class Serializer implements VariantCondition.Serializer<ConfigCondition> {
        @Override
        public ConfigCondition deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
            return new ConfigCondition(GsonHelper.getAsString(json.getAsJsonObject(), "configKey"));
        }

        @Override
        public void save(CompoundTag tag, ConfigCondition condition) {
            tag.putString("ConfigKey", condition.configKey);
        }

        @Override
        public ConfigCondition load(CompoundTag tag) {
            return new ConfigCondition(tag.getString("ConfigKey"));
        }

        @Override
        public void toNetwork(FriendlyByteBuf buf, ConfigCondition condition) {
            buf.writeUtf(condition.configKey);
        }

        @Override
        public ConfigCondition fromNetwork(FriendlyByteBuf buf) {
            return new ConfigCondition(buf.readUtf());
        }
    }
}
