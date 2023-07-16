package com.fossil.fossil.entity;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.entity.monster.AnuBoss;
import com.fossil.fossil.entity.monster.FriendlyPiglin;
import com.fossil.fossil.entity.monster.SentryPiglin;
import com.fossil.fossil.entity.monster.TarSlime;
import com.fossil.fossil.entity.prehistoric.*;
import com.fossil.fossil.entity.prehistoric.base.DinosaurEgg;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import dev.architectury.registry.level.entity.EntityAttributeRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.entity.Entity;
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

    public static final RegistrySupplier<EntityType<AlligatorGar>> ALLIGATOR_GAR = registerDino("alligator_gar", AlligatorGar::new, 2, 1);
    public static final RegistrySupplier<EntityType<Allosaurus>> ALLOSAURUS = registerDino("allosaurus", Allosaurus::new, 2, 1);
    //public static final RegistrySupplier<EntityType<Ammonite>> AMMONITE = registerDino("ammonite", Ammonite::new, 2, 1);
    public static final RegistrySupplier<EntityType<Ankylosaurus>> ANKYLOSAURUS = registerDino("ankylosaurus", Ankylosaurus::new, 2, 1);
    public static final RegistrySupplier<EntityType<Arthropleura>> ARTHROPLEURA = registerDino("arthropleura", Arthropleura::new, 2, 1);
    public static final RegistrySupplier<EntityType<Brachiosaurus>> BRACHIOSAURUS = registerDino("brachiosaurus", Brachiosaurus::new, 2, 1);
    public static final RegistrySupplier<EntityType<Ceratosaurus>> CERATOSAURUS = registerDino("ceratosaurus", Ceratosaurus::new, 2, 1);
    public static final RegistrySupplier<EntityType<Citipati>> CITIPATI = registerDino("citipati", Citipati::new, 2, 1);
    public static final RegistrySupplier<EntityType<Coelacanth>> COELACANTH = registerDino("coelacanth", Coelacanth::new, 2, 1);
    public static final RegistrySupplier<EntityType<Compsognathus>> COMPSOGNATHUS = registerDino("compsognathus", Compsognathus::new, 2, 1);
    public static final RegistrySupplier<EntityType<Confuciusornis>> CONFUCIUSORNIS = registerDino("confuciusornis", Confuciusornis::new, 2, 1);
    public static final RegistrySupplier<EntityType<Crassigyrinus>> CRASSIGYRINUS = registerDino("crassigyrinus", Crassigyrinus::new, 2, 1);
    public static final RegistrySupplier<EntityType<Deinonychus>> DEINONYCHUS = registerDino("deinonychus", Deinonychus::new, 2, 1);
    public static final RegistrySupplier<EntityType<Dilophosaurus>> DILOPHOSAURUS = registerDino("dilophosaurus", Dilophosaurus::new, 3, 2);
    public static final RegistrySupplier<EntityType<Diplocaulus>> DIPLOCAULUS = registerDino("diplocaulus", Diplocaulus::new, 2, 1);
    public static final RegistrySupplier<EntityType<Diplodocus>> DIPLODOCUS = registerDino("diplodocus", Diplodocus::new, 2, 1);
    public static final RegistrySupplier<EntityType<Dodo>> DODO = registerDino("dodo", Dodo::new, 2, 1);
    public static final RegistrySupplier<EntityType<Dryosaurus>> DRYOSAURUS = registerDino("dryosaurus", Dryosaurus::new, 2, 1);
    public static final RegistrySupplier<EntityType<Edaphosaurus>> EDAPHOSAURUS = registerDino("edaphosaurus", Edaphosaurus::new, 2, 1);
    public static final RegistrySupplier<EntityType<Elasmotherium>> ELASMOTHERIUM = registerDino("elasmotherium", Elasmotherium::new, 2, 1);
    public static final RegistrySupplier<EntityType<Gallimimus>> GALLIMIMUS = registerDino("gallimimus", Gallimimus::new, 2, 1);
    public static final RegistrySupplier<EntityType<Gastornis>> GASTORNIS = registerDino("gastornis", Gastornis::new, 2, 1);
    public static final RegistrySupplier<EntityType<Henodus>> HENODUS = registerDino("henodus", Henodus::new, 2, 1);
    public static final RegistrySupplier<EntityType<Ichtyosaurus>> ICHTYOSAURUS = registerDino("ichthyosaurus", Ichtyosaurus::new, 2, 1);
    public static final RegistrySupplier<EntityType<Kelenken>> KELENKEN = registerDino("kelenken", Kelenken::new, 2, 1);
    public static final RegistrySupplier<EntityType<Liopleurodon>> LIOPLEURODON = registerDino("liopleurodon", Liopleurodon::new, 2, 1);
    public static final RegistrySupplier<EntityType<Mammoth>> MAMMOTH = registerDino("mammoth", Mammoth::new, 2, 1);
    public static final RegistrySupplier<EntityType<Megalania>> MEGALANIA = registerDino("megalania", Megalania::new, 2, 1);
    public static final RegistrySupplier<EntityType<Megaloceros>> MEGALOCEROS = registerDino("megaloceros", Megaloceros::new, 2, 1);
    public static final RegistrySupplier<EntityType<Megalodon>> MEGALODON = registerDino("megalodon", Megalodon::new, 2, 1);
    public static final RegistrySupplier<EntityType<Megalograptus>> MEGALOGRAPTUS = registerDino("megalograptus", Megalograptus::new, 2, 1);
    public static final RegistrySupplier<EntityType<Meganeura>> MEGANEURA = registerDino("meganeura", Meganeura::new, 2, 1);
    public static final RegistrySupplier<EntityType<Mosasaurus>> MOSASAURUS = registerDino("mosasaurus", Mosasaurus::new, 2, 1);
    public static final RegistrySupplier<EntityType<Nautilus>> NAUTILUS = registerDino("nautilus", Nautilus::new, 2, 1);
    public static final RegistrySupplier<EntityType<Ornitholestes>> ORNITHOLESTES = registerDino("ornitholestes", Ornitholestes::new, 2, 1);
    public static final RegistrySupplier<EntityType<Pachycephalosaurus>> PACHYCEPHALOSAURUS = registerDino("pachycephalosaurus", Pachycephalosaurus::new, 2, 1);
    public static final RegistrySupplier<EntityType<Parasaurolophus>> PARASAUROLOPHUS = registerDino("parasaurolophus", Parasaurolophus::new, 2, 1);
    public static final RegistrySupplier<EntityType<Phorusrhacos>> PHORUSRHACOS = registerDino("phorusrhacos", Phorusrhacos::new, 2, 1);
    public static final RegistrySupplier<EntityType<Platybelodon>> PLATYBELODON = registerDino("platybelodon", Platybelodon::new, 2, 1);
    public static final RegistrySupplier<EntityType<Plesiosaurus>> PLESIOSAURUS = registerDino("plesiosaurus", Plesiosaurus::new, 2, 1);
    public static final RegistrySupplier<EntityType<Quagga>> QUAGGA = registerDino("quagga", Quagga::new, 2, 1);
    public static final RegistrySupplier<EntityType<Sarcosuchus>> SARCOSUCHUS = registerDino("sarcosuchus", Sarcosuchus::new, 2, 1);
    public static final RegistrySupplier<EntityType<Smilodon>> SMILODON = registerDino("smilodon", Smilodon::new, 2, 1);
    public static final RegistrySupplier<EntityType<Spinosaurus>> SPINOSAURUS = registerDino("spinosaurus", Spinosaurus::new, 2, 1);
    public static final RegistrySupplier<EntityType<Stegosaurus>> STEGOSAURUS = registerDino("stegosaurus", Stegosaurus::new, 2, 1);
    public static final RegistrySupplier<EntityType<Sturgeon>> STURGEON = registerDino("sturgeon", Sturgeon::new, 2, 1);
    public static final RegistrySupplier<EntityType<Therizinosaurus>> THERIZINOSAURUS = registerDino("therizinosaurus", Therizinosaurus::new, 2.5f, 2.5f);
    public static final RegistrySupplier<EntityType<Tiktaalik>> TIKTAALIK = registerDino("tiktaalik", Tiktaalik::new, 2, 1);
    public static final RegistrySupplier<EntityType<Titanis>> TITANIS = registerDino("titanis", Titanis::new, 2, 1);
    public static final RegistrySupplier<EntityType<Triceratops>> TRICERATOPS = registerDino("triceratops", Triceratops::new, 3.5f, 3);
    public static final RegistrySupplier<EntityType<Tropeognathus>> TROPEOGNATHUS = registerDino("tropeognathus", Tropeognathus::new, 3.5f, 3);
    public static final RegistrySupplier<EntityType<Tyrannosaurus>> TYRANNOSAURUS = registerDino("tyrannosaurus", Tyrannosaurus::new, 2, 1);
    public static final RegistrySupplier<EntityType<Velociraptor>> VELOCIRAPTOR = registerDino("velociraptor", Velociraptor::new, 2, 1);

    public static final RegistrySupplier<EntityType<DinosaurEgg>> DINOSAUR_EGG = ENTITIES.register(
            "dinosaur_egg", () -> EntityType.Builder.of(DinosaurEgg::new, MobCategory.CREATURE).sized(0.5F, 0.6F).build("dinosaur_egg"));

    public static final RegistrySupplier<EntityType<TarSlime>> TAR_SLIME = ENTITIES.register("tar_slime",
            () -> EntityType.Builder.of(TarSlime::new, MobCategory.MONSTER).build("tar_slime"));
    public static final RegistrySupplier<EntityType<AnuStatueEntity>> ANU_STATUE = ENTITIES.register("anu_statue",
            () -> EntityType.Builder.of(AnuStatueEntity::new, MobCategory.MISC).sized(0.9f, 1.8f).build("anu_statue"));
    public static final RegistrySupplier<EntityType<AnuBoss>> ANU_BOSS = ENTITIES.register("anu_boss",
            () -> EntityType.Builder.of(AnuBoss::new, MobCategory.MONSTER).sized(1, 1.8f).fireImmune().build("anu_boss"));
    public static final RegistrySupplier<EntityType<AnuDead>> ANU_DEAD = ENTITIES.register("anu_dead",
            () -> EntityType.Builder.of(AnuDead::new, MobCategory.MONSTER).sized(1.8f, 0.8f).fireImmune().build("anu_dead"));
    public static final RegistrySupplier<EntityType<SentryPiglin>> SENTRY_PIGLIN = ENTITIES.register("sentry_piglin",
            () -> EntityType.Builder.of(SentryPiglin::new, MobCategory.MONSTER).sized(1.8f, 0.8f).fireImmune().build("sentry_piglin"));

    public static final RegistrySupplier<EntityType<StoneTablet>> STONE_TABLET = ENTITIES.register("stone_tablet",
            () -> EntityType.Builder.<StoneTablet>of(StoneTablet::new, MobCategory.MISC).sized(0.5f, 0.5f).clientTrackingRange(10)
                    .updateInterval(Integer.MAX_VALUE).build("stone_tablet"));

    public static final RegistrySupplier<EntityType<Javelin>> JAVELIN = ENTITIES.register("javelin",
            () -> EntityType.Builder.<Javelin>of(Javelin::new, MobCategory.MISC).sized(0.5f, 0.5f).clientTrackingRange(4).updateInterval(20)
                    .build("javelin"));
    public static final RegistrySupplier<EntityType<AncientLightningBolt>> ANCIENT_LIGHTNING_BOLT = ENTITIES.register("ancient_lightning_bolt",
            () -> EntityType.Builder.of(AncientLightningBolt::new, MobCategory.MISC).sized(0, 0)
                    .clientTrackingRange(16).updateInterval(Integer.MAX_VALUE).build("ancient_lightning_bolt"));
    public static final RegistrySupplier<EntityType<FriendlyPiglin>> FRIENDLY_PIGLIN = ENTITIES.register("friendly_piglin",
            () -> EntityType.Builder.of(FriendlyPiglin::new, MobCategory.MONSTER).sized(0.6f, 1.95f).fireImmune().clientTrackingRange(8)
                    .build("friendly_piglin"));

    private static <T extends Entity> RegistrySupplier<EntityType<T>> registerDino(String name, EntityType.EntityFactory<T> factory, float width, float height) {
        return ENTITIES.register(name, () -> EntityType.Builder.of(factory, MobCategory.CREATURE).sized(width, height).build(name));
    }

    public static void register() {
        ENTITIES.register();
        EntityAttributeRegistry.register(ALLIGATOR_GAR, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(ALLOSAURUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(ANKYLOSAURUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(ARTHROPLEURA, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(BRACHIOSAURUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(CERATOSAURUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(CITIPATI, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(COELACANTH, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(COMPSOGNATHUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(CONFUCIUSORNIS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(CRASSIGYRINUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(DEINONYCHUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(DILOPHOSAURUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(DIPLOCAULUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(DIPLODOCUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(DODO, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(DRYOSAURUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(EDAPHOSAURUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(ELASMOTHERIUM, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(GALLIMIMUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(GASTORNIS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(HENODUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(ICHTYOSAURUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(KELENKEN, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(LIOPLEURODON, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(MAMMOTH, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(MEGALANIA, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(MEGALOCEROS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(MEGALODON, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(MEGALOGRAPTUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(MEGANEURA, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(MOSASAURUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(NAUTILUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(ORNITHOLESTES, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(PACHYCEPHALOSAURUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(PARASAUROLOPHUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(PHORUSRHACOS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(PLATYBELODON, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(PLESIOSAURUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(QUAGGA, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(SARCOSUCHUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(SMILODON, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(SPINOSAURUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(STEGOSAURUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(STURGEON, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(THERIZINOSAURUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(TIKTAALIK, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(TITANIS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(TRICERATOPS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(TROPEOGNATHUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(TYRANNOSAURUS, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(VELOCIRAPTOR, Prehistoric::createAttributes);
        EntityAttributeRegistry.register(ANU_BOSS, AnuBoss::createAttributes);
        EntityAttributeRegistry.register(ANU_DEAD, AnuDead::createAttributes);
        EntityAttributeRegistry.register(ANU_STATUE, AnuStatueEntity::createAttributes);
        EntityAttributeRegistry.register(SENTRY_PIGLIN, SentryPiglin::createAttributes);
        EntityAttributeRegistry.register(TOY_BALL, ToyBase::createAttributes);
        EntityAttributeRegistry.register(TOY_TETHERED_LOG, ToyBase::createAttributes);
        EntityAttributeRegistry.register(TOY_SCRATCHING_POST, ToyBase::createAttributes);
        EntityAttributeRegistry.register(TAR_SLIME, TarSlime::createAttributes);
        EntityAttributeRegistry.register(FRIENDLY_PIGLIN, FriendlyPiglin::createAttributes);
    }
}
