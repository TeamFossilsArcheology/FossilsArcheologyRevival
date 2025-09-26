package com.github.teamfossilsarcheology.fossil.food;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.mojang.logging.LogUtils;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.Registry;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import org.slf4j.Logger;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class FoodValueProvider implements DataProvider {
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
    protected final DataGenerator generator;
    protected final boolean keepOldFiles;
    private final Path basePath;
    protected Map<FoodType, FoodAppender> builders;

    protected FoodValueProvider(DataGenerator generator, boolean keepOldFiles) {
        this.generator = generator;
        this.keepOldFiles = keepOldFiles;
        this.basePath = generator.getOutputFolder().resolve("data/" + FossilMod.MOD_ID + "/food");
        builders = Arrays.stream(FoodType.values()).collect(Collectors.toMap(Function.identity(), type -> new FoodAppender()));
    }

    private Path getPath(FoodType type) {
        return basePath.resolve(type.name().toLowerCase(Locale.ENGLISH) + ".json");
    }

    @Override
    public void run(HashCache cache) throws IOException {
        buildFoodValues();
        if (keepOldFiles) {
            cache.oldCache.entrySet().stream().filter(entry -> entry.getKey().startsWith(basePath)).forEach(entry -> cache.putNew(entry.getKey(), entry.getValue()));
        }
        builders.forEach((type, foodAppender) -> {
            Path path = getPath(type);
            if (foodAppender.items.isEmpty() && foodAppender.entities.isEmpty()) {
                if (keepOldFiles && cache.oldCache.containsKey(path)) {
                    cache.putNew(path, cache.oldCache.get(path));
                }
                return;
            }
            JsonObject jsonobject = foodAppender.serializeToJson();
            try {
                String jsonString = GSON.toJson(jsonobject);
                String hash = SHA1.hashUnencodedChars(jsonString).toString();
                if (!Objects.equals(cache.getHash(path), hash) || !Files.exists(path)) {
                    Files.createDirectories(path.getParent());
                    try (BufferedWriter bufferedWriter = Files.newBufferedWriter(path)) {
                        bufferedWriter.write(jsonString);
                    }
                }
                cache.putNew(path, hash);
            } catch (IOException var14) {
                LOGGER.error("Couldn't save food values to {}", path, var14);
            }
        });
    }

    protected abstract void buildFoodValues();

    protected FoodAppender type(FoodType type) {
        return builders.get(type);
    }

    protected static class FoodAppender {
        private final List<Entry> items = new ObjectArrayList<>();
        private final List<Entry> entities = new ObjectArrayList<>();

        public FoodAppender() {
        }

        public void itemTag(TagKey<Item> tag) {
            items.add(new Entry(tag.location(), -1, "tag"));
        }

        public void item(ItemLike item) {
            item(item, -1);
        }

        public void item(ItemLike item, int value) {
            items.add(new Entry(Registry.ITEM.getKey(item.asItem()), value, "item"));
        }

        public void entityTag(TagKey<EntityType<?>> tag) {
            entities.add(new Entry(tag.location(), -1, "tag"));
        }

        public void entity(EntityType<?> entityType) {
            entity(entityType, -1);
        }

        public void entity(EntityType<?> entityType, int value) {
            entity(Registry.ENTITY_TYPE.getKey(entityType), value);
        }

        public void entity(ResourceLocation location, int value) {
            entities.add(new Entry(location, value, "entry"));
        }

        public JsonObject serializeToJson() {
            JsonObject root = new JsonObject();
            JsonArray itemArray = new JsonArray();
            JsonArray entityArray = new JsonArray();
            root.add("items", itemArray);
            root.add("entities", entityArray);
            items.stream().sorted(FoodAppender::compare).forEach(entry -> {
                itemArray.add(entry.serialize());
            });
            entities.stream().sorted(FoodAppender::compare).forEach(entry -> {
                entityArray.add(entry.serialize());
            });
            return root;
        }

        private static int compare(Entry one, Entry other) {
            int i = one.id.getNamespace().compareTo(other.id.getNamespace());
            if (i == 0) {
                i = one.id.getPath().compareTo(other.id.getPath());
            }
            return i;
        }

        private record Entry(ResourceLocation id, int value, String key) {
            public JsonObject serialize() {
                JsonObject object = new JsonObject();
                object.addProperty(key, id.toString());
                if (value >= 0) {
                    object.addProperty("value", value);
                }
                return object;
            }
        }
    }
}