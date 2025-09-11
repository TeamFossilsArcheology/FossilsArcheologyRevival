package com.github.teamfossilsarcheology.fossil.entity.variant;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.google.common.collect.ImmutableMap;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Loads dino variants from data/variants files
 */
public class EntityVariantLoader extends SimpleJsonResourceReloadListener {
    private static final Gson GSON = new GsonBuilder().registerTypeAdapter(Variant.class, new Variant.Deserializer()).create();
    public static final EntityVariantLoader INSTANCE = new EntityVariantLoader(GSON);
    private Map<String, Map<String, Variant>> variants = ImmutableMap.of();
    private Map<String, Map<Class<? extends VariantCondition>, List<VariantCondition.WithVariant<VariantCondition>>>> variantsByCondition = ImmutableMap.of();

    public EntityVariantLoader(Gson gson) {
        super(gson, "variants");
    }

    @Override
    protected void apply(Map<ResourceLocation, JsonElement> jsons, ResourceManager resourceManager, ProfilerFiller profiler) {
        ImmutableMap.Builder<String, Map<String, Variant>> builder = ImmutableMap.builder();
        Map<String, ImmutableMap.Builder<String, Variant>> innerBuilder = new HashMap<>();
        for (Map.Entry<ResourceLocation, JsonElement> fileEntry : jsons.entrySet()) {
            if (!(fileEntry.getValue() instanceof JsonObject root) || !fileEntry.getKey().getNamespace().equals(FossilMod.MOD_ID)) {
                continue;
            }
            String filePath = fileEntry.getKey().getPath();
            if (!filePath.contains("/")) {
                FossilMod.LOGGER.warn("Variant {} needs to be nested in a folder with the name of the entity", fileEntry);
                continue;
            }
            String entityName = filePath.split("/")[0];
            String variantId = filePath.split("/")[1];
            //No custom value
            root.addProperty("variantId", variantId);

            Variant variant = GSON.fromJson(root, Variant.class);
            innerBuilder.putIfAbsent(entityName, ImmutableMap.builder());
            innerBuilder.get(entityName).put(variantId, variant);
        }
        for (Map.Entry<String, ImmutableMap.Builder<String, Variant>> entry : innerBuilder.entrySet()) {
            builder.put(entry.getKey(), entry.getValue().build());
        }
        variants = builder.build();
        //Groups variants by their conditions
        variantsByCondition = variants.entrySet().stream().collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, entry -> entry.getValue().values().stream()
                .flatMap(variant -> Arrays.stream(variant.conditions()).map(condition -> VariantCondition.WithVariant.of(condition, variant)).collect(
                        Collectors.toMap(pair -> pair.condition().getClass(), List::of,
                                (list, list2) -> Stream.concat(list.stream(), list2.stream()).toList())).entrySet().stream())
                .collect(Collectors.toUnmodifiableMap(Map.Entry::getKey, Map.Entry::getValue))));
        FossilMod.LOGGER.info("Loaded {} variants", variants.values().stream().map(Map::size).reduce(Integer::sum).orElse(0));
    }

    /**
     * Returns all variants for the given entity type grouped by their id
     *
     * @param entityName the registry name of the entity
     * @return an unmodifiable map
     */
    @NotNull
    public Map<String, Variant> getVariants(String entityName) {
        return variants.getOrDefault(entityName, Map.of());
    }

    /**
     * Returns all variants for the given entity type grouped by their activation condition. Will include duplicates for
     * variants with multiple conditions
     *
     * @param clazz      the {@link VariantCondition} subclass
     * @param entityName the registry name of the entity
     * @return an unmodifiable map
     */
    public <T extends VariantCondition> List<VariantCondition.WithVariant<T>> getVariantsByCondition(Class<T> clazz, String entityName) {
        var temp = variantsByCondition.getOrDefault(entityName, Map.of()).getOrDefault(clazz, List.of());
        //We know that this is safe because of how the map was built
        return temp.stream().map(pair -> VariantCondition.WithVariant.of((T) pair.condition(), pair.variant())).toList();
    }

    public Map<String, Map<String, Variant>> getVariants() {
        return variants;
    }

    public void replaceVariants(Map<String, Map<String, Variant>> variantsMap) {
        variants = ImmutableMap.copyOf(variantsMap);
        FossilMod.LOGGER.info("Replacing {} client variants", variants.size());
    }
}
