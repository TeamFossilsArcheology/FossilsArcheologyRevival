package com.github.teamfossilsarcheology.fossil.food;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.client.ResourceLoader;
import com.github.teamfossilsarcheology.fossil.network.S2CSyncFoodMappingsMessage;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Sets;
import com.google.gson.*;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import it.unimi.dsi.fastutil.objects.ObjectOpenHashSet;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.tags.TagKey;
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
    private Map<FoodType, Map<TagKey<Block>, Integer>> blockTags = ImmutableMap.of();
    private Map<FoodType, Map<TagKey<Item>, Integer>> itemTags = ImmutableMap.of();
    private Map<FoodType, Set<TagKey<EntityType<?>>>> entityTags = ImmutableMap.of();
    private Map<FoodType, Map<Block, Integer>> blockValues = ImmutableMap.of();
    private Map<FoodType, Map<Item, Integer>> itemValues = ImmutableMap.of();
    private Map<FoodType, Map<EntityType<?>, Integer>> entityValues = ImmutableMap.of();
    private Map<Diet, TreeSet<ItemLike>> items;
    private Set<EntityType<?>> entities = ImmutableSet.of();
    private final List<Consumer<FoodMappingsManager>> listeners = new ArrayList<>();

    public FoodMappingsManager() {
        super(PackType.SERVER_DATA, "food", "json");
    }

    @Override
    protected @NotNull MapPair prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
        ImmutableMap.Builder<FoodType, Map<TagKey<Block>, Integer>> blockTagsBuilder = new ImmutableMap.Builder<>();
        ImmutableMap.Builder<FoodType, Map<TagKey<Item>, Integer>> itemTagsBuilder = new ImmutableMap.Builder<>();
        ImmutableMap.Builder<FoodType, Set<TagKey<EntityType<?>>>> entityTagsBuilder = new ImmutableMap.Builder<>();
        ImmutableMap.Builder<FoodType, Map<Block, Integer>> blockBuilder = new ImmutableMap.Builder<>();
        ImmutableMap.Builder<FoodType, Map<Item, Integer>> itemBuilder = new ImmutableMap.Builder<>();
        ImmutableMap.Builder<FoodType, Map<EntityType<?>, Integer>> entityBuilder = new ImmutableMap.Builder<>();
        ImmutableSet.Builder<EntityType<?>> setBuilder = new ImmutableSet.Builder<>();
        for (FoodType type : FoodType.values()) {
            Map<TagKey<Block>, Integer> blockTags = new Object2IntOpenHashMap<>();
            Map<TagKey<Item>, Integer> itemTags = new Object2IntOpenHashMap<>();
            Set<TagKey<EntityType<?>>> entityTags = new ObjectOpenHashSet<>();
            Map<Block, Integer> innerBlockBuilder = new Object2IntOpenHashMap<>();
            Map<Item, Integer> innerItemBuilder = new Object2IntOpenHashMap<>();
            Map<EntityType<?>, Integer> innerEntityBuilder = new Object2IntOpenHashMap<>();

            for (Map.Entry<ResourceLocation, Resource> fileEntry : listResources(resourceManager, type.name().toLowerCase() + ".json").entrySet()) {
                try (InputStream inputStream = fileEntry.getValue().open(); BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                    JsonElement jsonElement = GsonHelper.fromJson(GSON, reader, JsonElement.class);
                    JsonObject root = jsonElement.getAsJsonObject();
                    if (root.has("blocks")) {
                        JsonArray entries = root.get("blocks").getAsJsonArray();
                        for (JsonElement entry : entries) {
                            JsonObject object = entry.getAsJsonObject();
                            if (object.has("block")) {
                                BuiltInRegistries.BLOCK.getOptional(new ResourceLocation(object.get("block").getAsString())).ifPresent(block -> {
                                    if (object.has("value")) {
                                        innerBlockBuilder.put(block, object.get("value").getAsInt());
                                    } else {
                                        innerBlockBuilder.put(block, type.fallback());
                                    }
                                });
                            } else if (object.has("tag")) {
                                if (object.has("value")) {
                                    blockTags.put(TagKey.create(Registries.BLOCK, new ResourceLocation(object.get("tag").getAsString())), object.get("value").getAsInt());
                                } else {
                                    blockTags.put(TagKey.create(Registries.BLOCK, new ResourceLocation(object.get("tag").getAsString())), -1);
                                }
                            }
                        }
                    }
                    if (root.has("items")) {
                        JsonArray entries = root.get("items").getAsJsonArray();
                        for (JsonElement entry : entries) {
                            JsonObject object = entry.getAsJsonObject();
                            if (object.has("item")) {
                                BuiltInRegistries.ITEM.getOptional(new ResourceLocation(object.get("item").getAsString())).ifPresent(item -> {
                                    if (object.has("value")) {
                                        innerItemBuilder.put(item, object.get("value").getAsInt());
                                    } else if (item.getFoodProperties() != null) {
                                        innerItemBuilder.put(item, item.getFoodProperties().getNutrition() * type.multiplier());
                                    } else {
                                        innerItemBuilder.put(item, type.fallback());
                                    }
                                });
                            } else if (object.has("tag")) {
                                if (object.has("value")) {
                                    itemTags.put(TagKey.create(Registries.ITEM, new ResourceLocation(object.get("tag").getAsString())), object.get("value").getAsInt());
                                } else {
                                    itemTags.put(TagKey.create(Registries.ITEM, new ResourceLocation(object.get("tag").getAsString())), -1);
                                }
                            }
                        }
                    }
                    if (root.has("entities")) {
                        JsonArray entries = root.get("entities").getAsJsonArray();
                        for (JsonElement entry : entries) {
                            JsonObject object = entry.getAsJsonObject();
                            if (object.has("entity")) {
                                BuiltInRegistries.ENTITY_TYPE.getOptional(new ResourceLocation(object.get("entity").getAsString())).ifPresent(entityType -> {
                                    setBuilder.add(entityType);
                                    if (object.has("value")) {
                                        innerEntityBuilder.put(entityType, object.get("value").getAsInt());
                                    } else {
                                        //0 means that the value will be calculated at run time
                                        innerEntityBuilder.put(entityType, 0);
                                    }
                                });
                            } else if (object.has("tag")) {
                                entityTags.add(TagKey.create(Registries.ENTITY_TYPE, new ResourceLocation(object.get("tag").getAsString())));
                            }
                        }
                    }
                } catch (IOException e) {
                    FossilMod.LOGGER.error("Failed to load food values in {}: {}", fileEntry.getKey(), e);
                    throw new RuntimeException(e);
                }
            }
            blockTagsBuilder.put(type, blockTags);
            itemTagsBuilder.put(type, itemTags);
            entityTagsBuilder.put(type, entityTags);
            blockBuilder.put(type, innerBlockBuilder);
            itemBuilder.put(type, innerItemBuilder);
            entityBuilder.put(type, innerEntityBuilder);
        }
        return new MapPair(blockBuilder.build(), itemBuilder.build(), entityBuilder.build(), setBuilder.build(), blockTagsBuilder.build(), itemTagsBuilder.build(), entityTagsBuilder.build());
    }

    @Override
    protected void apply(MapPair mapPair, ResourceManager resourceManager, ProfilerFiller profiler) {
        blockValues = mapPair.blocks;
        itemValues = mapPair.items;
        entityValues = mapPair.entities;
        entities = mapPair.allEntities;
        blockTags = mapPair.blockTags;
        itemTags = mapPair.itemTags;
        entityTags = mapPair.entityTags;
        FossilMod.LOGGER.info("Loaded food values: (blocks: {}) (items: {}) (entities: {}, {}) (Tags: {}, {}, {})",
                blockValues.values().stream().mapToInt(m -> m.keySet().size()).sum(),
                itemValues.values().stream().mapToInt(m -> m.keySet().size()).sum(),
                entityValues.values().stream().mapToInt(m -> m.keySet().size()).sum(),
                entities.size(),
                blockTags.values().stream().mapToInt(m -> m.keySet().size()).sum(),
                itemTags.values().stream().mapToInt(m -> m.keySet().size()).sum(),
                entityTags.values().stream().mapToInt(Collection::size).sum());
        listeners.forEach(listener -> listener.accept(this));
        generateCache();
    }

    public void listen(Consumer<FoodMappingsManager> listener) {
        listeners.add(listener);
    }

    public void replaceValues(Map<FoodType, Map<Block, Integer>> blockValues, Map<FoodType, Map<Item, Integer>> itemValues,
                              Map<FoodType, Map<EntityType<?>, Integer>> entityValues, Set<EntityType<?>> entities) {
        FossilMod.LOGGER.info("Replacing client food values: (blocks: {}) (items: {}) (entities: {}, {})",
                blockValues.values().stream().mapToInt(m -> m.keySet().size()).sum(),
                itemValues.values().stream().mapToInt(m -> m.keySet().size()).sum(),
                entityValues.values().stream().mapToInt(m -> m.keySet().size()).sum(),
                entities.size());
        this.blockValues = blockValues;
        this.itemValues = itemValues;
        this.entityValues = entityValues;
        this.entities = entities;
        generateCache();
    }

    private void generateCache() {
        Comparator<ItemLike> byId = Comparator.comparingInt(item -> {
            if (item instanceof Block block) {
                return BuiltInRegistries.BLOCK.getId(block);
            } else {
                return Item.getId(item.asItem());
            }
        });
        Supplier<TreeSet<ItemLike>> set = () -> new TreeSet<>(byId);
        items = Arrays.stream(Diet.values()).collect(Collectors.toMap(Function.identity(), diet -> diet.flags().stream()
                .flatMap(type -> Sets.union(itemValues.get(type).keySet(), blockValues.get(type).keySet()).stream()).collect(Collectors.toCollection(set))));
    }

    public Map<Block, Integer> getBlockValues(FoodType type) {
        return blockValues.get(type);
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

    public void addBlock(FoodType type, Block block, int value) {
        blockValues.get(type).put(block, value);
    }

    public void addItem(FoodType type, Item item, int value) {
        itemValues.get(type).put(item, value);
    }

    public void addEntity(FoodType type, EntityType<?> entityType, int value) {
        entityValues.get(type).put(entityType, value);
    }

    public Map<Diet, TreeSet<ItemLike>> getItemCache() {
        if (items == null) {
            generateCache();
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
        addBlock(FoodType.PLANT, block, food);
    }

    public void addPlant(Item item) {
        int food = item.getFoodProperties() != null ? item.getFoodProperties().getNutrition() * 5 : 17;
        addPlant(item, food);
    }

    public void addPlant(Item item, int food) {
        addItem(FoodType.PLANT, item, food);
    }

    /**
     * Load items from tags. Tags have lower priority than direct references
     */
    private void loadTags() {
        ImmutableMap.Builder<FoodType, Map<Block, Integer>> blockBuilder = new ImmutableMap.Builder<>();
        ImmutableMap.Builder<FoodType, Map<Item, Integer>> itemBuilder = new ImmutableMap.Builder<>();
        ImmutableMap.Builder<FoodType, Map<EntityType<?>, Integer>> entityBuilder = new ImmutableMap.Builder<>();
        ImmutableSet.Builder<EntityType<?>> setBuilder = new ImmutableSet.Builder<>();
        setBuilder.addAll(entities);
        for (FoodType type : FoodType.values()) {
            Map<Block, Integer> innerBlockBuilder = new Object2IntOpenHashMap<>();
            Map<Item, Integer> innerItemBuilder = new Object2IntOpenHashMap<>();
            Map<EntityType<?>, Integer> innerEntityBuilder = new Object2IntOpenHashMap<>();
            innerBlockBuilder.putAll(blockValues.get(type));
            innerItemBuilder.putAll(itemValues.get(type));
            innerEntityBuilder.putAll(entityValues.get(type));
            for (Map.Entry<TagKey<Block>, Integer> entry : blockTags.get(type).entrySet()) {
                for (Holder<Block> itemHolder : BuiltInRegistries.BLOCK.getTagOrEmpty(entry.getKey())) {
                    Block block = itemHolder.value();
                    if (entry.getValue() != -1) {
                        innerBlockBuilder.put(block, entry.getValue());
                    } else {
                        innerBlockBuilder.put(block, type.fallback());
                    }
                }
            }
            for (Map.Entry<TagKey<Item>, Integer> entry : itemTags.get(type).entrySet()) {
                for (Holder<Item> itemHolder : BuiltInRegistries.ITEM.getTagOrEmpty(entry.getKey())) {
                    Item item = itemHolder.value();
                    if (item.getFoodProperties() != null) {
                        innerItemBuilder.put(item, item.getFoodProperties().getNutrition() * type.multiplier());
                    } else if (entry.getValue() != -1) {
                        innerItemBuilder.put(item, entry.getValue());
                    } else {
                        innerItemBuilder.put(item, type.fallback());
                    }
                }
            }
            for (TagKey<EntityType<?>> tagKey : entityTags.get(type)) {
                for (Holder<EntityType<?>> entityHolder : BuiltInRegistries.ENTITY_TYPE.getTagOrEmpty(tagKey)) {
                    EntityType<?> entityType = entityHolder.value();
                    setBuilder.add(entityType);
                    //0 means that the value will be calculated at run time
                    innerEntityBuilder.put(entityType, 0);
                }
            }
            blockBuilder.put(type, innerBlockBuilder);
            itemBuilder.put(type, innerItemBuilder);
            entityBuilder.put(type, innerEntityBuilder);
        }
        blockValues = blockBuilder.build();
        itemValues = itemBuilder.build();
        entityValues = entityBuilder.build();
        entities = setBuilder.build();
        generateCache();
    }

    public S2CSyncFoodMappingsMessage message() {
        loadTags();
        return new S2CSyncFoodMappingsMessage(blockValues, itemValues, entityValues, entities);
    }

    protected record MapPair(Map<FoodType, Map<Block, Integer>> blocks, Map<FoodType, Map<Item, Integer>> items,
                             Map<FoodType, Map<EntityType<?>, Integer>> entities,
                             Set<EntityType<?>> allEntities, Map<FoodType, Map<TagKey<Block>, Integer>> blockTags,
                             Map<FoodType, Map<TagKey<Item>, Integer>> itemTags,
                             Map<FoodType, Set<TagKey<EntityType<?>>>> entityTags) {

    }
}
