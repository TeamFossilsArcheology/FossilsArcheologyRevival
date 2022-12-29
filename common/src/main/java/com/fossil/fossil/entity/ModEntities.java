package com.fossil.fossil.entity;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.entity.prehistoric.EntityTriceratops;
import com.fossil.fossil.entity.prehistoric.base.EntityPrehistoric;
import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ModEntities {
    private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Fossil.MOD_ID, Registry.ENTITY_TYPE_REGISTRY);

    public static final RegistrySupplier<EntityType<EntityTriceratops>> TRICERATOPS = ENTITIES.register(
            "triceratops",
            () -> EntityType.Builder.of(EntityTriceratops::new, MobCategory.CREATURE).sized(1.1F, 0.6F).build("triceratops")
    );

    public static void register() {
        ENTITIES.register();
        EntityAttributeRegistry.register(TRICERATOPS, EntityPrehistoric::createAttributes);
    }
}
