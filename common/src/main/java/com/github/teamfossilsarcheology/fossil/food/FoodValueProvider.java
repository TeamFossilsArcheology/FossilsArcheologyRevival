package com.github.teamfossilsarcheology.fossil.food;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.CachedOutput;
import net.minecraft.data.DataProvider;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Block;
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
        builders = Arrays.stream(FoodType.values()).collect(Collectors.toMap(Function.identity(), FoodAppender::new));
    }

    private Path getPath(FoodType type) {
        return basePath.resolve(type.name().toLowerCase(Locale.ENGLISH) + ".json");
    }

    @Override
    public @NotNull CompletableFuture<?> run(CachedOutput output) {
        buildFoodValues();
        List<CompletableFuture<?>> list = new ArrayList<>();
        builders.forEach((type, foodAppender) -> {
            if (foodAppender.items.isEmpty() && foodAppender.entities.isEmpty()) {
                return;
            }
            JsonObject jsonobject = foodAppender.serializeToJson();
            list.add(DataProvider.saveStable(output, jsonobject, getPath(type)));
        });
        return CompletableFuture.allOf(list.toArray(CompletableFuture[]::new));
    }

    protected abstract void buildFoodValues();

    protected FoodAppender type(FoodType type) {
        return builders.get(type);
    }

    protected static class FoodAppender {
        private final FoodType type;
        private final List<Entry> items = new ObjectArrayList<>();
        private final List<Entry> entities = new ObjectArrayList<>();

        public FoodAppender(FoodType type) {
            this.type = type;
        }

        public void itemTag(TagKey<Item> tag) {
            items.add(new Entry(tag.location(), -1, "tag"));
        }

        /**
         * Adds the given item to the list without a fixed value. At runtime either the food property of the item or the {@link FoodType#fallback()} value will be used
         */
        public void item(Item item) {
            item(item, -1);
        }

        /**
         * Adds the given item to the list with the given value
         */
        public void item(Item item, int value) {
            items.add(new Entry(BuiltInRegistries.ITEM.getKey(item.asItem()), value, "item"));
        }

        /**
         * Adds the given block to the list with the given value multiplied by the {@link FoodType#multiplier()}
         */
        public void block(Block block, int value) {
            items.add(new Entry(BuiltInRegistries.ITEM.getKey(block.asItem()), value * type.multiplier(), "item"));
        }

        public void entityTag(TagKey<EntityType<?>> tag) {
            entities.add(new Entry(tag.location(), -1, "tag"));
        }

        /**
         * Adds the given entity to the list without a fixed value. At runtime this value will be calculated based on mob size
         */
        public void entity(EntityType<?> entityType) {
            entity(entityType, -1);
        }

        /**
         * Adds the given entity to the list with the given value
         */
        public void entity(EntityType<?> entityType, int value) {
            entity(BuiltInRegistries.ENTITY_TYPE.getKey(entityType), value);
        }

        /**
         * Adds the given entity to the list with the given value
         */
        public void entity(ResourceLocation location, int value) {
            entities.add(new Entry(location, value, "entity"));
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