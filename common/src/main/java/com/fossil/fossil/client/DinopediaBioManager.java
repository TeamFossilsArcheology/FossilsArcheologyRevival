package com.fossil.fossil.client;

import com.google.common.collect.ImmutableMap;
import com.mojang.logging.LogUtils;
import net.minecraft.client.Minecraft;
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
import java.util.HashMap;
import java.util.Map;

public class DinopediaBioManager extends SimplePreparableReloadListener<Map<String, String>> {
    public static final DinopediaBioManager DINOPEDIA = new DinopediaBioManager();
    private static final Logger LOGGER = LogUtils.getLogger();
    private static final String DIRECTORY = "dinopedia";
    private static final String PATH_SUFFIX = ".txt";
    private static final int PATH_SUFFIX_LENGTH = PATH_SUFFIX.length();
    private ImmutableMap<String, String> dinopediaTexts = ImmutableMap.of();

    public DinopediaBioManager() {
    }

    @Override
    protected @NotNull Map<String, String> prepare(ResourceManager resourceManager, ProfilerFiller profiler) {
        Map<String, String> map = new HashMap<>();
        int i = DIRECTORY.length() + 1;
        for (ResourceLocation resourceLocation : resourceManager.listResources(DIRECTORY, string -> string.endsWith(PATH_SUFFIX))) {
            String path = resourceLocation.getPath();
            ResourceLocation locationWithLang = new ResourceLocation(resourceLocation.getNamespace(), path.substring(i, path.length() - PATH_SUFFIX_LENGTH));
            try {
                String[] dinoInfo = locationWithLang.getPath().split("/");
                if (!Minecraft.getInstance().getLanguageManager().getSelected().getCode().equals(dinoInfo[0])) {
                    break;
                }
                try (Resource resource = resourceManager.getResource(resourceLocation)) {
                    try (InputStream inputStream = resource.getInputStream(); BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream, StandardCharsets.UTF_8))) {
                        StringBuilder builder = new StringBuilder();
                        String line;
                        while ((line = reader.readLine()) != null) {
                            builder.append(line);
                        }
                        map.put(dinoInfo[1], builder.toString());
                    }
                }
            } catch (IOException | IllegalArgumentException exception) {
                LOGGER.error("Couldn't parse data file {} from {}", locationWithLang, resourceLocation, exception);
            }
        }
        return map;
    }

    @Override
    protected void apply(Map<String, String> files, ResourceManager resourceManager, ProfilerFiller profiler) {
        ImmutableMap.Builder<String, String> mapBuilder = ImmutableMap.builder();
        mapBuilder.putAll(files);
        dinopediaTexts = mapBuilder.build();
    }

    public String getDinopediaBio(String fileName) {
        String bio = dinopediaTexts.get(fileName);
        //TODO: Proper error message
        return bio == null ? "No bio found" : bio;
    }
}
