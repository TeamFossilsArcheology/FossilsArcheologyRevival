package com.fossil.fossil.tags;

import com.fossil.fossil.Fossil;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class ModEntityTypeTags {
    private static final ResourceKey<Registry<EntityType<?>>> key = Registry.ENTITY_TYPE_REGISTRY;
    public static final TagKey<EntityType<?>> LIVESTOCK = TagKey.create(key, new ResourceLocation(Fossil.MOD_ID, "livestock"));
}
