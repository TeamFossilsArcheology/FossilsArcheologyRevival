package com.fossil.fossil.entity;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.entity.monster.TarSlime;
import com.fossil.fossil.entity.prehistoric.Therizinosaurus;
import com.fossil.fossil.entity.prehistoric.Triceratops;
import com.fossil.fossil.entity.prehistoric.base.DinosaurEgg;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;

public class ModEntities {
    private static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(Fossil.MOD_ID, Registry.ENTITY_TYPE_REGISTRY);

    public static final RegistrySupplier<EntityType<Triceratops>> TRICERATOPS = ENTITIES.register(
    "triceratops",
        () -> EntityType.Builder.of(
            Triceratops::new,
            MobCategory.CREATURE
        ).sized(3.5F, 3F).build("triceratops")
    );

    public static final RegistrySupplier<EntityType<Therizinosaurus>> THERIZINOSAURUS = ENTITIES.register(
    "therizinosaurus",
        () -> EntityType.Builder.of(
            Therizinosaurus::new,
            MobCategory.CREATURE
        ).sized(2.5F, 2.5F).build("therizinosaurus")
    );

    public static final RegistrySupplier<EntityType<DinosaurEgg>> DINOSAUR_EGG = ENTITIES.register(
    "dinosaur_egg",
        () -> EntityType.Builder.<DinosaurEgg>of(
            DinosaurEgg::new,
            MobCategory.CREATURE
        ).sized(0.5F, 0.7F).build("dinosaur_egg")
    );

    public static final RegistrySupplier<EntityType<TarSlime>> TAR_SLIME = ENTITIES.register("tar_slime",
            () -> EntityType.Builder.of(TarSlime::new, MobCategory.MONSTER).build("tar_slime"));
    public static final RegistrySupplier<EntityType<AnuStatueEntity>> ANU_STATUE = ENTITIES.register("anu_statue",
            () -> EntityType.Builder.of(AnuStatueEntity::new, MobCategory.MISC).sized(0.9f, 1.8f).build("anu_statue"));

    public static void register() {
        ENTITIES.register();
        EntityAttributeRegistry.register(TRICERATOPS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(THERIZINOSAURUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(DINOSAUR_EGG, DinosaurEgg::createAttributes);
        EntityAttributeRegistry.register(ANU_STATUE, AnuStatueEntity::createAttributes);
        EntityAttributeRegistry.register(TAR_SLIME, TarSlime::createAttributes);
    }
}
