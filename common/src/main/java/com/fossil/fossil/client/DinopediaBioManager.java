package com.fossil.fossil.client;

import com.google.common.collect.ImmutableMap;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
import net.minecraft.locale.Language;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.packs.resources.Resource;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimplePreparableReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.Map;

public class DinopediaBioManager extends SimplePreparableReloadListener<Map<String, Map<String, String>>> {
    public static final DinopediaBioManager DINOPEDIA = new DinopediaBioManager();
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String DIRECTORY = "dinopedia";
    private static final String PATH_SUFFIX = ".txt";
    private static final int PATH_SUFFIX_LENGTH = PATH_SUFFIX.length();
    private ImmutableMap<String, String> englishFallback = ImmutableMap.of();
    private ImmutableMap<String, String> dinopediaTexts = ImmutableMap.of();

    public DinopediaBioManager() {
    }

    @Override
    protected @NotNull Map<String, Map<String, String>> prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
        ImmutableMap.Builder<String, Map<String, String>> mapBuilder = ImmutableMap.builder();
        ImmutableMap.Builder<String, String> selectedLangBuilder = ImmutableMap.builder();
        ImmutableMap.Builder<String, String> fallbackLangBuilder = ImmutableMap.builder();
        int i = DIRECTORY.length() + 1;
        String selectedLang = Minecraft.getInstance().options.languageCode;
        for (ResourceLocation resourceLocation : resourceManager.listResources(DIRECTORY, string -> string.endsWith(PATH_SUFFIX))) {
            String path = resourceLocation.getPath();
            ResourceLocation locationWithLang = new ResourceLocation(resourceLocation.getNamespace(), path.substring(i, path.length() - PATH_SUFFIX_LENGTH));
            try {
                String[] dinoInfo = locationWithLang.getPath().split("/");
                if (Language.DEFAULT.equals(dinoInfo[0])) {
                    fallbackLangBuilder.put(dinoInfo[1], readFile(resourceManager, resourceLocation));
                }
                if (!selectedLang.equals(dinoInfo[0])) {
                    continue;
                }
                selectedLangBuilder.put(dinoInfo[1], readFile(resourceManager, resourceLocation));
            } catch (IOException | IllegalArgumentException exception) {
                LOGGER.error("Couldn't parse data file {} from {}", locationWithLang, resourceLocation, exception);
            }
        }
        mapBuilder.put(selectedLang, selectedLangBuilder.build());
        if (!selectedLang.equals(Language.DEFAULT)) {
            mapBuilder.put(Language.DEFAULT, fallbackLangBuilder.build());
        }
        return mapBuilder.build();
    }

    private String readFile(ResourceManager resourceManager, ResourceLocation resourceLocation) throws IOException {
        try (Resource resource = resourceManager.getResource(resourceLocation)) {
            try (InputStream inputStream = resource.getInputStream(); BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                StringBuilder builder = new StringBuilder();
                String line;
                while ((line = reader.readLine()) != null) {
                    builder.append(line);
                }
                return builder.toString();
            }
        }
    }

    @Override
    protected void apply(Map<String, Map<String, String>> files, ResourceManager resourceManager, ProfilerFiller profiler) {
        ImmutableMap.Builder<String, String> selectedBuilder = ImmutableMap.builder();
        ImmutableMap.Builder<String, String> fallbackBuilder = ImmutableMap.builder();
        dinopediaTexts = selectedBuilder.putAll(files.get(Minecraft.getInstance().options.languageCode)).build();
        englishFallback = fallbackBuilder.putAll(files.get(Language.DEFAULT)).build();
    }

    public String getDinopediaBio(String fileName) {
        if (dinopediaTexts.containsKey(fileName)) {
            return dinopediaTexts.get(fileName);
        }
        if (englishFallback.containsKey(fileName)) {
            return englishFallback.get(fileName);
        }
        return "No bio found. This should not have happened";
    }
}
