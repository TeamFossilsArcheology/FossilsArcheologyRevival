package com.fossil.fossil.entity;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.entity.monster.TarSlime;
import com.fossil.fossil.entity.prehistoric.Dilophosaurus;
import com.fossil.fossil.entity.prehistoric.Therizinosaurus;
import com.fossil.fossil.entity.prehistoric.Triceratops;
import com.fossil.fossil.entity.prehistoric.Tropeognathus;
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

    public static final RegistrySupplier<EntityType<ToyBall>> TOY_BALL = ENTITIES.register("toy_ball",
            () -> EntityType.Builder.of(ToyBall::new, MobCategory.MISC).sized(0.5f, 0.5f).build("toy_ball"));
    public static final RegistrySupplier<EntityType<ToyTetheredLog>> TOY_TETHERED_LOG = ENTITIES.register("toy_tethered_log",
            () -> EntityType.Builder.of(ToyTetheredLog::new, MobCategory.MISC).sized(0.6f, 1.9375f).build("toy_tethered_log"));
    public static final RegistrySupplier<EntityType<ToyScratchingPost>> TOY_SCRATCHING_POST = ENTITIES.register("toy_scratching_post",
            () -> EntityType.Builder.of(ToyScratchingPost::new, MobCategory.MISC).sized(0.6f, 2).build("toy_scratching_post"));

    public static final RegistrySupplier<EntityType<Dilophosaurus>> DILOPHOSAURUS = ENTITIES.register("dilophosaurus",
            () -> EntityType.Builder.of(Dilophosaurus::new, MobCategory.CREATURE).sized(3, 2).build("dilophosaurus"));
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

    public static final RegistrySupplier<EntityType<Tropeognathus>> TROPEOGNATHUS = ENTITIES.register(
            "tropeognathus",
            () -> EntityType.Builder.of(
                    Tropeognathus::new,
                    MobCategory.CREATURE
            ).sized(2F, 1F).build("tropeognathus")
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

    public static final RegistrySupplier<EntityType<StoneTablet>> STONE_TABLET = ENTITIES.register("stone_tablet",
            () -> EntityType.Builder.<StoneTablet>of(StoneTablet::new, MobCategory.MISC).sized(0.5f, 0.5f).clientTrackingRange(10)
                    .updateInterval(Integer.MAX_VALUE).build("stone_tablet"));

    public static final RegistrySupplier<EntityType<Javelin>> JAVELIN = ENTITIES.register("javelin",
            () -> EntityType.Builder.<Javelin>of(Javelin::new, MobCategory.MISC).sized(0.5f, 0.5f).clientTrackingRange(4).updateInterval(20)
                    .build("javelin"));

    public static void register() {
        ENTITIES.register();
        EntityAttributeRegistry.register(DILOPHOSAURUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(TRICERATOPS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(THERIZINOSAURUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(TROPEOGNATHUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(DINOSAUR_EGG, DinosaurEgg::createAttributes);
        EntityAttributeRegistry.register(ANU_STATUE, AnuStatueEntity::createAttributes);
        EntityAttributeRegistry.register(TOY_BALL, ToyBase::createAttributes);
        EntityAttributeRegistry.register(TOY_TETHERED_LOG, ToyBase::createAttributes);
        EntityAttributeRegistry.register(TOY_SCRATCHING_POST, ToyBase::createAttributes);
        EntityAttributeRegistry.register(TAR_SLIME, TarSlime::createAttributes);
    }
}
