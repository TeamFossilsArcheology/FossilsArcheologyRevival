package com.github.teamfossilsarcheology.fossil.client;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.google.common.collect.ImmutableMap;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.locale.Language;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.PackType;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Map;

/**
 * Loads dinopedia bio entries for the currently selected language and fallback language
 */
public class DinopediaBioLoader extends ResourceLoader<Map<String, Map<String, String>>> {
    public static final DinopediaBioLoader INSTANCE = new DinopediaBioLoader();
    private static final Logger LOGGER = LogUtils.getLogger();
    private ImmutableMap<String, String> englishFallback = ImmutableMap.of();
    private ImmutableMap<String, String> dinopediaTexts = ImmutableMap.of();

    public DinopediaBioLoader() {
        super(PackType.CLIENT_RESOURCES, "dinopedia", ".txt");
    }

    @Override
    protected @NotNull Map<String, Map<String, String>> prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
        ImmutableMap.Builder<String, Map<String, String>> mapBuilder = ImmutableMap.builder();
        ImmutableMap.Builder<String, String> selectedLangBuilder = ImmutableMap.builder();
        ImmutableMap.Builder<String, String> fallbackLangBuilder = ImmutableMap.builder();
        int i = directory.length() + 1;
        String selectedLang = Minecraft.getInstance().options.languageCode;
        for (Map.Entry<ResourceLocation, Resource> entry : listResources(resourceManager).entrySet()) {
            String path = entry.getKey().getPath();
            ResourceLocation locationWithLang = new ResourceLocation(entry.getKey().getNamespace(), path.substring(i, path.length() - suffix.length()));
            try {
                String[] dinoInfo = locationWithLang.getPath().split("/");
                if (selectedLang.equals(dinoInfo[0])) {
                    String text = readFile(entry.getValue());
                    selectedLangBuilder.put(dinoInfo[1], text);
                    if (Language.DEFAULT.equals(selectedLang)) {
                        fallbackLangBuilder.put(dinoInfo[1], text);
                    }
                } else if (Language.DEFAULT.equals(dinoInfo[0])) {
                    fallbackLangBuilder.put(dinoInfo[1], readFile(entry.getValue()));
                }
            } catch (IOException | IllegalArgumentException exception) {
                LOGGER.error("Couldn't parse data file {} from {}", locationWithLang, entry.getKey(), exception);
            }
        }
        mapBuilder.put(selectedLang, selectedLangBuilder.build());
        if (!selectedLang.equals(Language.DEFAULT)) {
            mapBuilder.put(Language.DEFAULT, fallbackLangBuilder.build());
        }
        return mapBuilder.build();
    }

    private String readFile(Resource resource) throws IOException {
        try (BufferedReader reader = resource.openAsReader()) {
            StringBuilder builder = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                builder.append(line);
            }
            return builder.toString();
        }
    }

    @Override
    protected void apply(Map<String, Map<String, String>> files, ResourceManager resourceManager, ProfilerFiller profiler) {
        ImmutableMap.Builder<String, String> selectedBuilder = ImmutableMap.builder();
        ImmutableMap.Builder<String, String> fallbackBuilder = ImmutableMap.builder();
        dinopediaTexts = selectedBuilder.putAll(files.get(Minecraft.getInstance().options.languageCode)).build();
        englishFallback = fallbackBuilder.putAll(files.get(Language.DEFAULT)).build();
        FossilMod.LOGGER.info("Loaded {} dinopedia texts for {}", dinopediaTexts.size(), Minecraft.getInstance().options.languageCode);
        FossilMod.LOGGER.info("Loaded {} fallback dinopedia texts for {}", englishFallback.size(), Language.DEFAULT);
    }

    public boolean hasFallback(String entityName) {
        return englishFallback.containsKey(entityName);
    }

    /**
     * Returns the text of a dinopedia bio in the currently selected language or in the fallback language
     */
    public String getDinopediaBio(String entityName) {
        if (dinopediaTexts.containsKey(entityName)) {
            return dinopediaTexts.get(entityName);
        }
        if (englishFallback.containsKey(entityName)) {
            return englishFallback.get(entityName);
        }
        return "No bio found. This should not have happened";
    }
}
