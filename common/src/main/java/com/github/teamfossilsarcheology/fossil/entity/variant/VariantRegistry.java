package com.github.teamfossilsarcheology.fossil.entity.variant;

import com.google.common.base.Preconditions;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.nbt.CompoundTag;

import java.lang.reflect.Type;
import java.util.*;
import java.util.function.Function;

/**
 * Each {@link VariantCondition} needs to have their serializers and id registered here
 */
public class VariantRegistry {
    private static final Map<String, RegistryObject<? extends VariantCondition>> REGISTRY_MAP = new Object2ObjectOpenHashMap<>();
    private static final List<RegistryObject<? extends VariantCondition>> PRIORITY = new ObjectArrayList<>();

    public static final RegistryObject<NameTagCondition> NAME_TAG = register("nametag", NameTagCondition::save, NameTagCondition::load, NameTagCondition.class, new NameTagCondition.Deserializer());
    public static final RegistryObject<DateCondition> DATE = register("date", DateCondition::save, DateCondition::load, DateCondition.class, new DateCondition.Deserializer());
    public static final RegistryObject<ConfigCondition> CONFIG = register("config", ConfigCondition::save, ConfigCondition::load, ConfigCondition.class, new ConfigCondition.Deserializer());

    private VariantRegistry() {
    }

    /**
     * @param key          unique id used for serialization
     * @param serializer   used to save the active conditions for the entity
     * @param deserializer used to load the active conditions for the entity
     * @param type         used when deserializing the json object
     * @param typeAdapter  used when deserializing the json object. {@link GsonBuilder#registerTypeAdapter(Type, Object)}
     */
    public static <T extends VariantCondition> RegistryObject<T> register(String key, RegistryObject.Serializer<T> serializer, Function<CompoundTag, VariantCondition> deserializer, Type type, Object typeAdapter) {
        Objects.requireNonNull(key, "key");
        Objects.requireNonNull(serializer, "serializer");
        Objects.requireNonNull(deserializer, "deserializer");
        Preconditions.checkState(key.equals(key.toLowerCase(Locale.ROOT)), "key must be lowercase: %s", key);
        Preconditions.checkState(!REGISTRY_MAP.containsKey(key), "key '%s' already registered as VariantCondition", key);
        Preconditions.checkState(typeAdapter instanceof JsonDeserializer<?>, "typeAdapter '%s' should be an instance of JsonDeserializer", typeAdapter);
        RegistryObject<T> registryObject = new RegistryObject<>(key, serializer, deserializer, type, typeAdapter);
        REGISTRY_MAP.put(key, registryObject);
        //TODO: Properly implement priority
        PRIORITY.add(registryObject);
        return registryObject;
    }

    public static RegistryObject<? extends VariantCondition> get(String key) {
        return REGISTRY_MAP.get(key);
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

    public static class RegistryObject<T extends VariantCondition> {
        private final String id;
        private final Serializer<T> serializer;
        private final Function<CompoundTag, VariantCondition> deserializer;
        private final Type type;
        private final Gson gson;

        public RegistryObject(String id, Serializer<T> serializer, Function<CompoundTag, VariantCondition> deserializer, Type type, Object typeAdapter) {
            this.serializer = serializer;
            this.id = id;
            this.deserializer = deserializer;
            this.type = type;
            this.gson = new GsonBuilder().registerTypeAdapter(type, typeAdapter).create();
        }

        public CompoundTag save(CompoundTag tag, VariantCondition.WithVariant<? extends VariantCondition> pair) {
            tag.putString("VariantConditionId", id);
            tag.putString("VariantId", pair.variant().getVariantId());
            serializer.save(tag, (T) pair.condition());
            return tag;
        }

        public VariantCondition load(CompoundTag tag) {
            return deserializer.apply(tag);
        }

        public static RegistryObject<? extends VariantCondition> parse(CompoundTag tag) {
            return get(tag.getString("VariantConditionId"));
        }

        VariantCondition fromJson(JsonObject object) {
            return gson.fromJson(object, type);
        }

        public interface Serializer<T> {
            void save(CompoundTag tag, T condition);
        }
    }
}
