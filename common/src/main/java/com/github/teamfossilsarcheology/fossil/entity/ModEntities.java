package com.github.teamfossilsarcheology.fossil.entity;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.entity.monster.*;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.*;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.DinosaurEgg;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricFish;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricFlying;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.fish.AlligatorGar;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.fish.Coelacanth;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.fish.Nautilus;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.fish.Sturgeon;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.flying.Confuciusornis;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.flying.Dimorphodon;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.flying.Pteranodon;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.flying.Quetzalcoatlus;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.swimming.*;
import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.animal.horse.AbstractChestedHorse;

public class ModEntities {
    public static final DeferredRegister<EntityType<?>> ENTITIES = DeferredRegister.create(FossilMod.MOD_ID, Registries.ENTITY_TYPE);

    public static final RegistrySupplier<EntityType<ToyBall>> TOY_BALL = registerMisc("toy_ball", ToyBall::new, 0.5f, 0.5f, 5, 3);
    public static final RegistrySupplier<EntityType<ToyTetheredLog>> TOY_TETHERED_LOG = registerMisc("toy_tethered_log", ToyTetheredLog::new, 0.6f, 1.9375f, 5, 20);
    public static final RegistrySupplier<EntityType<ToyScratchingPost>> TOY_SCRATCHING_POST = registerMisc("toy_scratching_post", ToyScratchingPost::new, 0.6f, 2, 5, 20);
    public static final RegistrySupplier<EntityType<AlligatorGar>> ALLIGATOR_GAR = registerFish("alligator_gar", AlligatorGar::new, 2, 0.5f);
    public static final RegistrySupplier<EntityType<Allosaurus>> ALLOSAURUS = registerDino("allosaurus", Allosaurus::new, 1.1f, 1.3f);
    public static final RegistrySupplier<EntityType<Ankylosaurus>> ANKYLOSAURUS = registerDino("ankylosaurus", Ankylosaurus::new, 1.6f, 1.4f);
    public static final RegistrySupplier<EntityType<Aquilolamna>> AQUILOLAMNA = registerAquatic("aquilolamna", Aquilolamna::new, 1.3f, 1);
    public static final RegistrySupplier<EntityType<Arthropleura>> ARTHROPLEURA = registerDino("arthropleura", Arthropleura::new, 1, 0.3f);
    public static final RegistrySupplier<EntityType<Brachiosaurus>> BRACHIOSAURUS = registerDino("brachiosaurus", Brachiosaurus::new, 0.95f, 1.3f);
    public static final RegistrySupplier<EntityType<Ceratosaurus>> CERATOSAURUS = registerDino("ceratosaurus", Ceratosaurus::new, 0.65f, 1.2f);
    public static final RegistrySupplier<EntityType<Citipati>> CITIPATI = registerDino("citipati", Citipati::new, 1.15f, 1.5f);
    public static final RegistrySupplier<EntityType<Coelacanth>> COELACANTH = registerFish("coelacanth", Coelacanth::new, 2, 0.5f);
    public static final RegistrySupplier<EntityType<Compsognathus>> COMPSOGNATHUS = registerDino("compsognathus", Compsognathus::new, 1.1f, 0.8f);
    public static final RegistrySupplier<EntityType<Confuciusornis>> CONFUCIUSORNIS = registerDino("confuciusornis", Confuciusornis::new, 2, 1f);
    public static final RegistrySupplier<EntityType<Crassigyrinus>> CRASSIGYRINUS = registerAquatic("crassigyrinus", Crassigyrinus::new, 1.5f, 0.9f);
    public static final RegistrySupplier<EntityType<Deinonychus>> DEINONYCHUS = registerDino("deinonychus", Deinonychus::new, 1.2f, 1.3f);
    public static final RegistrySupplier<EntityType<Dilophosaurus>> DILOPHOSAURUS = registerDino("dilophosaurus", Dilophosaurus::new, 1.25f, 1.5f);
    public static final RegistrySupplier<EntityType<Dimetrodon>> DIMETRODON = registerDino("dimetrodon", Dimetrodon::new, 1.3f, 1.3f);
    public static final RegistrySupplier<EntityType<Dimorphodon>> DIMORPHODON = registerDino("dimorphodon", Dimorphodon::new, 1.2f, 0.8f);
    public static final RegistrySupplier<EntityType<Diplocaulus>> DIPLOCAULUS = registerAquatic("diplocaulus", Diplocaulus::new, 1.5f, 0.9f);
    public static final RegistrySupplier<EntityType<Diplodocus>> DIPLODOCUS = registerDino("diplodocus", Diplodocus::new, 1.85f, 1.85f);
    public static final RegistrySupplier<EntityType<Dodo>> DODO = registerDino("dodo", Dodo::new, 1, 1);
    public static final RegistrySupplier<EntityType<Dryosaurus>> DRYOSAURUS = registerDino("dryosaurus", Dryosaurus::new, 1.3f, 1.2f);
    public static final RegistrySupplier<EntityType<Edaphosaurus>> EDAPHOSAURUS = registerDino("edaphosaurus", Edaphosaurus::new, 1.4f, 1.4f);
    public static final RegistrySupplier<EntityType<Elasmotherium>> ELASMOTHERIUM = registerDino("elasmotherium", Elasmotherium::new, 1.5f, 2);
    public static final RegistrySupplier<EntityType<Gallimimus>> GALLIMIMUS = registerDino("gallimimus", Gallimimus::new, 1.2f, 1.5f);
    public static final RegistrySupplier<EntityType<Gastornis>> GASTORNIS = registerDino("gastornis", Gastornis::new, 1.4f, 2);
    public static final RegistrySupplier<EntityType<Henodus>> HENODUS = registerAquatic("henodus", Henodus::new, 1.1f, 0.8f);
    public static final RegistrySupplier<EntityType<Ichthyosaurus>> ICHTHYOSAURUS = registerAquatic("ichthyosaurus", Ichthyosaurus::new, 1.2f, 1);
    public static final RegistrySupplier<EntityType<Kelenken>> KELENKEN = registerDino("kelenken", Kelenken::new, 1.4f, 2f);
    public static final RegistrySupplier<EntityType<Liopleurodon>> LIOPLEURODON = registerAquatic("liopleurodon", Liopleurodon::new, 2.25f, 0.7f);
    public static final RegistrySupplier<EntityType<Mammoth>> MAMMOTH = registerDino("mammoth", Mammoth::new, 1.2f, 2f);
    public static final RegistrySupplier<EntityType<Megalania>> MEGALANIA = registerDino("megalania", Megalania::new, 1.6f, 0.8f);
    public static final RegistrySupplier<EntityType<Megaloceros>> MEGALOCEROS = registerDino("megaloceros", Megaloceros::new, 1f, 1.7f);
    public static final RegistrySupplier<EntityType<Megalodon>> MEGALODON = registerAquatic("megalodon", Megalodon::new, 2f, 1.1f);
    public static final RegistrySupplier<EntityType<Megalograptus>> MEGALOGRAPTUS = registerDino("megalograptus", Megalograptus::new, 1.8f, 0.75f);
    public static final RegistrySupplier<EntityType<Meganeura>> MEGANEURA = registerDino("meganeura", Meganeura::new, 2.5f, 1.25f);
    public static final RegistrySupplier<EntityType<Mosasaurus>> MOSASAURUS = registerAquatic("mosasaurus", Mosasaurus::new, 1.3f, 0.6f);
    public static final RegistrySupplier<EntityType<Nautilus>> NAUTILUS = registerFish("nautilus", Nautilus::new, 0.8f, 0.795f);
    public static final RegistrySupplier<EntityType<Ornitholestes>> ORNITHOLESTES = registerDino("ornitholestes", Ornitholestes::new, 2.25f, 1.9f);
    public static final RegistrySupplier<EntityType<Pachycephalosaurus>> PACHYCEPHALOSAURUS = registerDino("pachycephalosaurus", Pachycephalosaurus::new, 1, 1.5f);
    public static final RegistrySupplier<EntityType<Pachyrhinosaurus>> PACHYRHINOSAURUS = registerDino("pachyrhinosaurus", Pachyrhinosaurus::new, 1.4f, 1.5f);
    public static final RegistrySupplier<EntityType<Parasaurolophus>> PARASAUROLOPHUS = registerDino("parasaurolophus", Parasaurolophus::new, 1.3f, 1.7f);
    public static final RegistrySupplier<EntityType<Phorusrhacos>> PHORUSRHACOS = registerDino("phorusrhacos", Phorusrhacos::new, 1.2f, 2f);
    public static final RegistrySupplier<EntityType<Platybelodon>> PLATYBELODON = registerDino("platybelodon", Platybelodon::new, 1.5f, 1.9f);
    public static final RegistrySupplier<EntityType<Plesiosaurus>> PLESIOSAURUS = registerAquatic("plesiosaurus", Plesiosaurus::new, 1, 1);
    public static final RegistrySupplier<EntityType<Postosuchus>> POSTOSUCHUS = registerDino("postosuchus", Postosuchus::new, 0.7f, 1f);
    public static final RegistrySupplier<EntityType<Protoceratops>> PROTOCERATOPS = registerDino("protoceratops", Protoceratops::new, 1.5f, 1.25f);
    public static final RegistrySupplier<EntityType<Psittacosaurus>> PSITTACOSAURUS = registerDino("psittacosaurus", Psittacosaurus::new, 1.5f, 1.25f);
    public static final RegistrySupplier<EntityType<Pteranodon>> PTERANODON = registerDino("pteranodon", Pteranodon::new, 0.7f, 1f);
    public static final RegistrySupplier<EntityType<Quagga>> QUAGGA = registerDino("quagga", Quagga::new, 1.3964844f, 1.6f);
    public static final RegistrySupplier<EntityType<Quetzalcoatlus>> QUETZALCOATLUS = registerDino("quetzalcoatlus", Quetzalcoatlus::new, 1f, 2f);
    public static final RegistrySupplier<EntityType<Sarcosuchus>> SARCOSUCHUS = registerDino("sarcosuchus", Sarcosuchus::new, 2, 1);
    public static final RegistrySupplier<EntityType<Smilodon>> SMILODON = registerDino("smilodon", Smilodon::new, 1.2f, 1.4f);
    public static final RegistrySupplier<EntityType<Spinosaurus>> SPINOSAURUS = registerDino("spinosaurus", Spinosaurus::new, 1.1f, 1.3f);
    public static final RegistrySupplier<EntityType<Stegosaurus>> STEGOSAURUS = registerDino("stegosaurus", Stegosaurus::new, 1.4f, 1.8f);
    public static final RegistrySupplier<EntityType<Sturgeon>> STURGEON = registerFish("sturgeon", Sturgeon::new, 1.9f, 0.5f);
    public static final RegistrySupplier<EntityType<Therizinosaurus>> THERIZINOSAURUS = registerDino("therizinosaurus", Therizinosaurus::new, 1.1f, 1.5f);
    public static final RegistrySupplier<EntityType<Tiktaalik>> TIKTAALIK = registerAquatic("tiktaalik", Tiktaalik::new, 1.7f, 0.4f);
    public static final RegistrySupplier<EntityType<Titanis>> TITANIS = registerDino("titanis", Titanis::new, 1.3f, 2f);
    public static final RegistrySupplier<EntityType<Triceratops>> TRICERATOPS = registerDino("triceratops", Triceratops::new, 0.8f, 1);
    public static final RegistrySupplier<EntityType<Tyrannosaurus>> TYRANNOSAURUS = registerDino("tyrannosaurus", Tyrannosaurus::new, 1.1f, 1.25f);
    public static final RegistrySupplier<EntityType<Velociraptor>> VELOCIRAPTOR = registerDino("velociraptor", Velociraptor::new, 1.6f, 1.5f);
    public static final RegistrySupplier<EntityType<Dicranurus>> DICRANURUS = registerDino("dicranurus", Dicranurus::new, 0.8f, 0.3f);
    public static final RegistrySupplier<EntityType<Lonchodomas>> LONCHODOMAS = registerDino("lonchodomas", Lonchodomas::new, 0.8f, 0.3f);
    public static final RegistrySupplier<EntityType<Scotoharpes>> SCOTOHARPES = registerDino("scotoharpes", Scotoharpes::new, 0.8f, 0.3f);
    public static final RegistrySupplier<EntityType<Walliserops>> WALLISEROPS = registerDino("walliserops", Walliserops::new, 0.8f, 0.3f);

    public static final RegistrySupplier<EntityType<DinosaurEgg>> DINOSAUR_EGG = ENTITIES.register(
            "dinosaur_egg", () -> EntityType.Builder.of(DinosaurEgg::new, MobCategory.CREATURE).sized(0.5F, 0.6F).build("dinosaur_egg"));
    public static final RegistrySupplier<EntityType<ThrownBirdEgg>> THROWN_BIRD_EGG = registerMisc("thrown_bird_egg", ThrownBirdEgg::new, 0.25f, 0.25f, 4, 10);

    public static final RegistrySupplier<EntityType<Failuresaurus>> FAILURESAURUS = ENTITIES.register("failuresaurus",
            () -> EntityType.Builder.of(Failuresaurus::new, MobCategory.MONSTER).sized(0.9f, 1).build("failuresaurus"));
    public static final RegistrySupplier<EntityType<TarSlime>> TAR_SLIME = ENTITIES.register("tar_slime",
            () -> EntityType.Builder.of(TarSlime::new, MobCategory.MONSTER).sized(2.04f, 2.04f).build("tar_slime"));
    public static final RegistrySupplier<EntityType<Anubite>> ANUBITE = ENTITIES.register("anubite",
            () -> EntityType.Builder.of(Anubite::new, MobCategory.MONSTER).sized(1, 2.3f).fireImmune().build("anubite"));
    public static final RegistrySupplier<EntityType<AnuBoss>> ANU_BOSS = ENTITIES.register("anu_boss",
            () -> EntityType.Builder.of(AnuBoss::new, MobCategory.MONSTER).sized(1, 1.8f).fireImmune()
                    .clientTrackingRange(64).build("anu_boss"));
    public static final RegistrySupplier<EntityType<AnuDead>> ANU_DEAD = ENTITIES.register("anu_dead",
            () -> EntityType.Builder.of(AnuDead::new, MobCategory.MONSTER).sized(1.8f, 0.8f).fireImmune().build("anu_dead"));
    public static final RegistrySupplier<EntityType<AnuTotem>> ANU_TOTEM = registerMisc("anu_totem", AnuTotem::new, 0.9f, 1.8f, 5, 20);
    public static final RegistrySupplier<EntityType<SentryPiglin>> SENTRY_PIGLIN = ENTITIES.register("sentry_piglin",
            () -> EntityType.Builder.of(SentryPiglin::new, MobCategory.MONSTER).sized(0.8f, 2).fireImmune().build("sentry_piglin"));

    public static final RegistrySupplier<EntityType<StoneTablet>> STONE_TABLET = registerMisc("stone_tablet", StoneTablet::new, 0.5f, 0.5f, 10, Integer.MAX_VALUE);

    public static final RegistrySupplier<EntityType<LaserPointEntity>> LASER_POINT = registerMisc("laser_point", LaserPointEntity::new, 0.2f, 0.2f, 10, 3);

    public static final RegistrySupplier<EntityType<Javelin>> JAVELIN = registerMisc("javelin", Javelin::new, 0.5f, 0.5f, 4, 20);
    public static final RegistrySupplier<EntityType<AncientLightningBolt>> ANCIENT_LIGHTNING_BOLT = ENTITIES.register("ancient_lightning_bolt",
            () -> EntityType.Builder.of(AncientLightningBolt::new, MobCategory.MISC).sized(0, 0)
                    .clientTrackingRange(16).updateInterval(Integer.MAX_VALUE).build("ancient_lightning_bolt"));
    public static final RegistrySupplier<EntityType<FriendlyPiglin>> FRIENDLY_PIGLIN = ENTITIES.register("friendly_piglin",
            () -> EntityType.Builder.of(FriendlyPiglin::new, MobCategory.MONSTER).sized(0.6f, 1.95f).fireImmune().clientTrackingRange(8)
                    .build("friendly_piglin"));
    public static final RegistrySupplier<EntityType<PrehistoricSkeleton>> SKELETON = registerMisc("skeleton", PrehistoricSkeleton::new, 1, 1, 10, 3);


    private static <T extends Entity> RegistrySupplier<EntityType<T>> registerMisc(String name, EntityType.EntityFactory<T> factory, float width, float height, int trackRange, int updateInterval) {
        return ENTITIES.register(name, () -> EntityType.Builder.of(factory, MobCategory.MISC).sized(width, height).clientTrackingRange(trackRange).updateInterval(updateInterval).build(name));
    }

    private static <T extends Entity> RegistrySupplier<EntityType<T>> registerDino(String name, EntityType.EntityFactory<T> factory, float width, float height) {
        return ENTITIES.register(name, () -> EntityType.Builder.of(factory, MobCategory.CREATURE).sized(width, height).clientTrackingRange(8).build(name));
    }

    private static <T extends Entity> RegistrySupplier<EntityType<T>> registerAquatic(String name, EntityType.EntityFactory<T> factory, float width, float height) {
        return ENTITIES.register(name, () -> EntityType.Builder.of(factory, MobCategory.WATER_CREATURE).sized(width, height).clientTrackingRange(8).build(name));
    }

    private static <T extends Entity> RegistrySupplier<EntityType<T>> registerFish(String name, EntityType.EntityFactory<T> factory, float width, float height) {
        return ENTITIES.register(name, () -> EntityType.Builder.of(factory, MobCategory.WATER_CREATURE).sized(width, height).clientTrackingRange(8).build(name));
    }

    public static void register() {
        ENTITIES.register();
        EntityAttributeRegistry.register(ALLIGATOR_GAR, PrehistoricFish::createAttributes);
        EntityAttributeRegistry.register(ALLOSAURUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(ANKYLOSAURUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(AQUILOLAMNA, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(ARTHROPLEURA, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(BRACHIOSAURUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(CERATOSAURUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(CITIPATI, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(COELACANTH, PrehistoricFish::createAttributes);
        EntityAttributeRegistry.register(COMPSOGNATHUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(CONFUCIUSORNIS, PrehistoricFlying::createAttributes);
        EntityAttributeRegistry.register(CRASSIGYRINUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(DEINONYCHUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(DILOPHOSAURUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(DIMETRODON, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(DIMORPHODON, PrehistoricFlying::createAttributes);
        EntityAttributeRegistry.register(DIPLOCAULUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(DIPLODOCUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(DODO, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(DRYOSAURUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(EDAPHOSAURUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(ELASMOTHERIUM, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(GALLIMIMUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(GASTORNIS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(HENODUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(ICHTHYOSAURUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(KELENKEN, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(LIOPLEURODON, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(MAMMOTH, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(MEGALANIA, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(MEGALOCEROS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(MEGALODON, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(MEGALOGRAPTUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(MEGANEURA, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(MOSASAURUS, Mosasaurus::createAttributes);
        EntityAttributeRegistry.register(NAUTILUS, PrehistoricFish::createAttributes);
        EntityAttributeRegistry.register(ORNITHOLESTES, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(PACHYCEPHALOSAURUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(PACHYRHINOSAURUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(PARASAUROLOPHUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(PHORUSRHACOS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(PLATYBELODON, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(PLESIOSAURUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(POSTOSUCHUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(PROTOCERATOPS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(PSITTACOSAURUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(PTERANODON, PrehistoricFlying::createAttributes);
        EntityAttributeRegistry.register(QUAGGA, AbstractChestedHorse::createBaseChestedHorseAttributes);
        EntityAttributeRegistry.register(QUETZALCOATLUS, PrehistoricFlying::createAttributes);
        EntityAttributeRegistry.register(SARCOSUCHUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(SMILODON, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(SPINOSAURUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(STEGOSAURUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(STURGEON, PrehistoricFish::createAttributes);
        EntityAttributeRegistry.register(THERIZINOSAURUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(TIKTAALIK, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(TITANIS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(TRICERATOPS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(TYRANNOSAURUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(VELOCIRAPTOR, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(DICRANURUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(LONCHODOMAS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(SCOTOHARPES, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(WALLISEROPS, Prehistoric::createAttributes);

        EntityAttributeRegistry.register(LASER_POINT, LaserPointEntity::createAttributes);
        EntityAttributeRegistry.register(ANUBITE, Anubite::createAttributes);
        EntityAttributeRegistry.register(ANU_BOSS, AnuBoss::createAttributes);
        EntityAttributeRegistry.register(ANU_DEAD, AnuDead::createAttributes);
        EntityAttributeRegistry.register(ANU_TOTEM, AnuTotem::createAttributes);
        EntityAttributeRegistry.register(SENTRY_PIGLIN, SentryPiglin::createAttributes);
        EntityAttributeRegistry.register(TAR_SLIME, TarSlime::createAttributes);
        EntityAttributeRegistry.register(FRIENDLY_PIGLIN, FriendlyPiglin::createAttributes);
        EntityAttributeRegistry.register(FAILURESAURUS, Failuresaurus::createAttributes);
        EntityAttributeRegistry.register(DINOSAUR_EGG, DinosaurEgg::createAttributes);
    }
}
