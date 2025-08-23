package com.github.teamfossilsarcheology.fossil.entity.variant;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.FriendlyByteBuf;

import java.lang.reflect.Type;
import java.util.*;

/**
 * Each {@link VariantCondition} needs to have their serializers and id registered here
 */
public class VariantRegistry {
    private static final Map<String, RegistryObject<? extends VariantCondition>> BY_KEY = new Object2ObjectOpenHashMap<>();
    private static final Map<Class<? extends VariantCondition>, RegistryObject<? extends VariantCondition>> BY_CLASS = new Object2ObjectOpenHashMap<>();
    private static final List<RegistryObject<? extends VariantCondition>> PRIORITY = new ObjectArrayList<>();

    public static final RegistryObject<NameTagCondition> NAME_TAG = register("nametag", new NameTagCondition.Serializer(), NameTagCondition.class);
    public static final RegistryObject<NbtCondition> NBT = register("nbt", new NbtCondition.Serializer(), NbtCondition.class);
    public static final RegistryObject<DateCondition> DATE = register("date", new DateCondition.Serializer(), DateCondition.class);
    public static final RegistryObject<ConfigCondition> CONFIG = register("config", new ConfigCondition.Serializer(), ConfigCondition.class);

    private VariantRegistry() {
    }

    /**
     * @param key        unique id used for serialization
     * @param serializer used for network and tag serialization
     * @param type       used when deserializing the json object. {@link GsonBuilder#registerTypeAdapter(Type, Object)}
     */
    public static <T extends VariantCondition> RegistryObject<T> register(String key, VariantCondition.Serializer<T> serializer, Class<T> type) {
        Objects.requireNonNull(key, "key");
        Objects.requireNonNull(serializer, "serializer");
        Preconditions.checkState(key.equals(key.toLowerCase(Locale.ROOT)), "key must be lowercase: %s", key);
        Preconditions.checkState(!BY_KEY.containsKey(key), "key '%s' already registered as VariantCondition", key);
        RegistryObject<T> registryObject = new RegistryObject<>(key, serializer, type, new GsonBuilder().registerTypeAdapter(type, serializer).create());
        BY_KEY.put(key, registryObject);
        BY_CLASS.put(type, registryObject);
        //TODO: Properly implement priority
        PRIORITY.add(registryObject);
        return registryObject;
    }

    public static RegistryObject<? extends VariantCondition> get(String key) {
        return BY_KEY.get(key);
    }

    public static RegistryObject<? extends VariantCondition> get(VariantCondition condition) {
        return BY_CLASS.get(condition.getClass());
    }

    /**
     * Returns the variant with the highest priority from the given map
     */
    public static Optional<Variant> getHighestPriority(Map<VariantRegistry.RegistryObject<?>, VariantCondition.WithVariant<?>> map) {
        for (RegistryObject<? extends VariantCondition> registryObject : PRIORITY) {
            if (map.containsKey(registryObject)) {
                return Optional.ofNullable(map.get(registryObject).variant());
            }
        }
        return Optional.empty();
    }

    public static void register() {

    }

    public static void toNetwork(FriendlyByteBuf buf, VariantCondition condition) {
        BY_CLASS.get(condition.getClass()).toNetwork(buf, condition);
    }

    public static VariantCondition fromNetwork(FriendlyByteBuf buf) {
        String type = buf.readUtf();
        return get(type).serializer.fromNetwork(buf);
    }

    public record RegistryObject<T extends VariantCondition>(String id, VariantCondition.Serializer<T> serializer, Type type, Gson gson) {
        public CompoundTag save(CompoundTag tag, VariantCondition.WithVariant<? extends VariantCondition> pair) {
            tag.putString("VariantConditionId", id);
            tag.putString("VariantId", pair.variant().getVariantId());
            serializer.save(tag, (T) pair.condition());
            return tag;
        }

        public static RegistryObject<? extends VariantCondition> parse(CompoundTag tag) {
            return get(tag.getString("VariantConditionId"));
        }

        VariantCondition fromJson(JsonObject object) {
            return gson.fromJson(object, type);
        }

        void toNetwork(FriendlyByteBuf buf, VariantCondition condition) {
            buf.writeUtf(id);
            serializer.toNetwork(buf, (T) condition);
        }
    }
}
