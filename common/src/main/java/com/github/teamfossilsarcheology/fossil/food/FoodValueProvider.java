package com.github.teamfossilsarcheology.fossil.food;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.level.ItemLike;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.function.Function;
import java.util.stream.Collectors;

public abstract class FoodValueProvider implements DataProvider {
    protected final PackOutput output;
    private final Path basePath;
    protected Map<FoodType, FoodAppender> builders;

    protected FoodValueProvider(PackOutput output) {
        this.output = output;
        this.basePath = output.getOutputFolder().resolve("data/" + FossilMod.MOD_ID + "/food");
        builders = Arrays.stream(FoodType.values()).collect(Collectors.toMap(Function.identity(), type -> new FoodAppender()));
    }

    private Path getPath(FoodType type) {
        return basePath.resolve(type.name().toLowerCase(Locale.ENGLISH) + ".json");
    }

    @Override
    public @NotNull CompletableFuture<?> run(CachedOutput output) {
        buildFoodValues();
        List<CompletableFuture<?>> list = new ArrayList<>();
        builders.forEach((type, foodAppender) -> {
            Path path = getPath(type);
            if (foodAppender.items.isEmpty() && foodAppender.entities.isEmpty() && output instanceof HashCache.CacheUpdater updater) {
                return;
            }
            JsonObject jsonobject = foodAppender.serializeToJson();
            list.add(DataProvider.saveStable(output, jsonobject, path));
        });
        return CompletableFuture.allOf(list.toArray(CompletableFuture[]::new));
    }

    protected abstract void buildFoodValues();

    protected FoodAppender type(FoodType type) {
        return builders.get(type);
    }

    protected static class FoodAppender {
        private final Map<ResourceLocation, Integer> items = new Object2IntOpenHashMap<>();
        private final Map<ResourceLocation, Integer> entities = new Object2IntOpenHashMap<>();

        public FoodAppender() {
        }

        public void item(ItemLike item) {
            item(item, -1);
        }

        public void item(ItemLike item, int value) {
            item(BuiltInRegistries.ITEM.getKey(item.asItem()), value);
        }

        public void item(ResourceLocation location, int value) {
            items.put(location, value);
        }

        public void entity(EntityType<?> entityType) {
            entity(entityType, -1);
        }

        public void entity(EntityType<?> entityType, int value) {
            entity(BuiltInRegistries.ENTITY_TYPE.getKey(entityType), value);
        }

        public void entity(ResourceLocation location, int value) {
            entities.put(location, value);
        }

        public JsonObject serializeToJson() {
            JsonObject root = new JsonObject();
            JsonArray itemArray = new JsonArray();
            JsonArray entityArray = new JsonArray();
            root.add("items", itemArray);
            root.add("entities", entityArray);
            items.entrySet().stream().sorted(Map.Entry.comparingByKey(FoodAppender::compare)).forEach(entry -> {
                JsonObject object = new JsonObject();
                object.addProperty("id", entry.getKey().toString());
                if (entry.getValue() >= 0) {
                    object.addProperty("value", entry.getValue());
                }
                itemArray.add(object);
            });
            entities.entrySet().stream().sorted(Map.Entry.comparingByKey(FoodAppender::compare)).forEach(entry -> {
                JsonObject object = new JsonObject();
                object.addProperty("id", entry.getKey().toString());
                if (entry.getValue() >= 0) {
                    object.addProperty("value", entry.getValue());
                }
                entityArray.add(object);
            });
            return root;
        }

        public static int compare(ResourceLocation one, ResourceLocation other) {
            int i = one.getNamespace().compareTo(other.getNamespace());
            if (i == 0) {
                i = one.getPath().compareTo(other.getPath());
            }
            return i;
        }
    }
}