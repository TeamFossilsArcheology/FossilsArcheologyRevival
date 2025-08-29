package com.github.teamfossilsarcheology.fossil.food;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.client.ResourceLoader;
import com.github.teamfossilsarcheology.fossil.network.S2CSyncFoodMappingsMessage;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.gson.*;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.GsonHelper;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.NotNull;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.stream.Collectors;

public class FoodMappingsManager extends ResourceLoader<FoodMappingsManager.MapPair> {
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
    public static final FoodMappingsManager INSTANCE = new FoodMappingsManager();
    private Map<FoodType, Map<Item, Integer>> itemValues = ImmutableMap.of();
    private Map<FoodType, Map<EntityType<?>, Integer>> entityValues = ImmutableMap.of();
    private Map<Diet, TreeSet<Item>> items = ImmutableMap.of();
    private Set<EntityType<?>> entities = ImmutableSet.of();
    private final List<Consumer<FoodMappingsManager>> listeners = new ArrayList<>();

    public FoodMappingsManager() {
        super(PackType.SERVER_DATA, "food", "json");
    }

    @Override
    protected @NotNull MapPair prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
        ImmutableMap.Builder<FoodType, Map<Item, Integer>> itemBuilder = new ImmutableMap.Builder<>();
        ImmutableMap.Builder<FoodType, Map<EntityType<?>, Integer>> entityBuilder = new ImmutableMap.Builder<>();
        ImmutableSet.Builder<EntityType<?>> setBuilder = new ImmutableSet.Builder<>();
        for (FoodType type : FoodType.values()) {
            Map<Item, Integer> innerItemBuilder = new Object2IntOpenHashMap<>();
            Map<EntityType<?>, Integer> innerEntityBuilder = new Object2IntOpenHashMap<>();

            for (ResourceLocation resourceLocation : listResources(resourceManager, type.name().toLowerCase() + ".json")) {
                Optional<Resource> opt = resourceManager.getResource(resourceLocation);
                if (opt.isEmpty()) {
                    continue;
                }
                try (InputStream inputStream = opt.get().open(); BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                    //TODO: Could be less bad
                    JsonElement jsonElement = GsonHelper.fromJson(GSON, reader, JsonElement.class);
                    if (jsonElement != null) {
                        JsonObject root = jsonElement.getAsJsonObject();
                        if (root.has("items")) {
                            JsonArray entries = root.get("items").getAsJsonArray();
                            for (JsonElement entry : entries) {
                                JsonObject object = entry.getAsJsonObject();
                                Optional<Item> optional = Registry.ITEM.getOptional(new ResourceLocation(object.get("id").getAsString()));
                                if (optional.isPresent()) {
                                    Item item = optional.get();
                                    if (object.has("value")) {
                                        innerItemBuilder.put(item, object.get("value").getAsInt());
                                    } else if (item.getFoodProperties() != null) {
                                        innerItemBuilder.put(item, item.getFoodProperties().getNutrition() * type.multiplier());
                                    } else {
                                        innerItemBuilder.put(item, type.fallback());
                                    }
                                }
                            }
                        }
                        if (root.has("entities")) {
                            JsonArray entries = root.get("entities").getAsJsonArray();
                            for (JsonElement entry : entries) {
                                JsonObject object = entry.getAsJsonObject();
                                Optional<EntityType<?>> optional = Registry.ENTITY_TYPE.getOptional(new ResourceLocation(object.get("id").getAsString()));
                                if (optional.isPresent()) {
                                    EntityType<?> entityType = optional.get();
                                    setBuilder.add(entityType);
                                    if (object.has("value")) {
                                        innerEntityBuilder.put(entityType, object.get("value").getAsInt());
                                    } else {
                                        innerEntityBuilder.put(entityType, 0);
                                    }
                                }
                            }
                        }
                    }
                } catch (IOException e) {
                    FossilMod.LOGGER.error("Failed to load food values in {}: {}", resourceLocation, e);
                    throw new RuntimeException(e);
                }
            }
            itemBuilder.put(type, innerItemBuilder);
            entityBuilder.put(type, innerEntityBuilder);
        }
        return new MapPair(itemBuilder.build(), entityBuilder.build(), setBuilder.build());
    }

    @Override
    protected void apply(MapPair mapPair, ResourceManager resourceManager, ProfilerFiller profiler) {
        itemValues = mapPair.items;
        entityValues = mapPair.entities;
        entities = mapPair.allEntities;
        listeners.forEach(listener -> listener.accept(this));
        Comparator<Item> byId = Comparator.comparingInt(item -> Item.getId(item.asItem()));
        Supplier<TreeSet<Item>> set = () -> new TreeSet<>(byId);
        items = Arrays.stream(Diet.values()).collect(Collectors.toMap(Function.identity(), diet -> diet.flags().stream().flatMap(type -> itemValues.get(type).keySet().stream()).collect(Collectors.toCollection(set))));
    }

    public void listen(Consumer<FoodMappingsManager> listener) {
        listeners.add(listener);
    }

    public void replaceValues(Map<FoodType, Map<Item, Integer>> itemValues, Map<FoodType, Map<EntityType<?>, Integer>> entityValues, Set<EntityType<?>> entities) {
        this.itemValues = itemValues;
        this.entityValues = entityValues;
        this.entities = entities;
    }

    public Map<Item, Integer> getItemValues(FoodType type) {
        return itemValues.get(type);
    }

    public Map<EntityType<?>, Integer> getEntityValues(FoodType type) {
        return entityValues.get(type);
    }

    public boolean hasEntityEntry(EntityType<?> entityType) {
        return entities.contains(entityType);
    }

    public void addItem(FoodType type, Item item, int value) {
        itemValues.get(type).put(item, value);
    }

    public void addEntity(FoodType type, EntityType<?> entityType, int value) {
        entityValues.get(type).put(entityType, value);
    }

    public Map<Diet, TreeSet<Item>> getItemCache() {
        if (items == null) {
            Comparator<Item> byId = Comparator.comparingInt(item -> Item.getId(item.asItem()));
            Supplier<TreeSet<Item>> set = () -> new TreeSet<>(byId);
            items = Arrays.stream(Diet.values()).collect(Collectors.toMap(Function.identity(), diet -> diet.flags().stream().flatMap(type -> itemValues.get(type).keySet().stream()).collect(Collectors.toCollection(set))));
        }
        return items;
    }

    public void addMeat(EntityType<?> entity, int food) {
        addEntity(FoodType.MEAT, entity, food);
    }

    public void addMeat(ItemLike itemLike) {
        int food = itemLike.asItem().getFoodProperties() != null ? itemLike.asItem().getFoodProperties().getNutrition() * 7 : 20;
        addMeat(itemLike, food);
    }

    public void addMeat(ItemLike itemLike, int food) {
        addItem(FoodType.MEAT, itemLike.asItem(), food);
    }

    public void addFish(EntityType<?> entity, int food) {
        addEntity(FoodType.FISH, entity, food);
    }

    public void addFish(Item item) {
        int food = item.getFoodProperties() != null ? item.getFoodProperties().getNutrition() * 7 : 10;
        addFish(item, food);
    }

    public void addFish(Item item, int food) {
        addItem(FoodType.FISH, item, food);
    }

    public void addEgg(Item item) {
        int food = item.getFoodProperties() != null ? item.getFoodProperties().getNutrition() * 7 : 10;
        addEgg(item, food);
    }

    public void addEgg(Item item, int food) {
        addItem(FoodType.EGG, item, food);
    }

    public void addPlant(Block block, int food) {
        addItem(FoodType.PLANT, block.asItem(), food);
    }

    public void addPlant(Item item) {
        int food = item.getFoodProperties() != null ? item.getFoodProperties().getNutrition() * 5 : 17;
        addPlant(item, food);
    }

    public void addPlant(Item item, int food) {
        addItem(FoodType.PLANT, item, food);
    }

    public S2CSyncFoodMappingsMessage message() {
        return new S2CSyncFoodMappingsMessage(itemValues, entityValues, entities);
    }

    protected record MapPair(Map<FoodType, Map<Item, Integer>> items, Map<FoodType, Map<EntityType<?>, Integer>> entities, Set<EntityType<?>> allEntities) {

    }
}
