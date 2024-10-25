package com.github.teamfossilsarcheology.fossil.tags;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.tags.TagKey;
import net.minecraft.world.entity.EntityType;

public class ModEntityTypeTags {
    private static final ResourceKey<Registry<EntityType<?>>> key = Registry.ENTITY_TYPE_REGISTRY;
    public static final TagKey<EntityType<?>> LIVESTOCK = TagKey.create(key, FossilMod.location("livestock"));
}
