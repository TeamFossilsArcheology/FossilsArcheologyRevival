package com.fossil.fossil.block.entity;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.block.ModBlocks;
import com.fossil.fossil.client.particle.BubbleParticle;
import com.fossil.fossil.client.particle.TarBubbleParticle;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Fossil.MOD_ID, Registry.BLOCK_ENTITY_TYPE_REGISTRY);
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(Fossil.MOD_ID, Registry.PARTICLE_TYPE_REGISTRY);
    public static final RegistrySupplier<BubbleParticle.Type> BUBBLE = PARTICLE_TYPES.register("bubble", () -> new BubbleParticle.Type(false));
    public static final RegistrySupplier<TarBubbleParticle.Type> TAR_BUBBLE = PARTICLE_TYPES.register("tar_bubble", () -> new TarBubbleParticle.Type(false));

    public static final RegistrySupplier<BlockEntityType<BubbleBlowerBlockEntity>> BUBBLE_BLOWER = BLOCK_ENTITIES.register("bubble_blower",
            () -> BlockEntityType.Builder.of(BubbleBlowerBlockEntity::new, ModBlocks.BUBBLE_BLOWER.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<VaseBlockEntity>> VASE = BLOCK_ENTITIES.register("vase",
            () -> BlockEntityType.Builder.of(VaseBlockEntity::new, ModBlocks.VASES.stream().map(Supplier::get).toArray(Block[]::new)).build(null));
    public static final RegistrySupplier<BlockEntityType<FeederBlockEntity>> FEEDER = BLOCK_ENTITIES.register("feeder",
            () -> BlockEntityType.Builder.of(FeederBlockEntity::new, ModBlocks.FEEDER.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<SifterBlockEntity>> SIFTER = BLOCK_ENTITIES.register("sifter",
            () -> BlockEntityType.Builder.of(SifterBlockEntity::new, ModBlocks.SIFTER.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<AnalyzerBlockEntity>> ANALYZER = BLOCK_ENTITIES.register("analyzer",
            () -> BlockEntityType.Builder.of(AnalyzerBlockEntity::new, ModBlocks.ANALYZER.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<CultivateBlockEntity>> CULTIVATE = BLOCK_ENTITIES.register("cultivate",
            () -> BlockEntityType.Builder.of(CultivateBlockEntity::new, ModBlocks.CULTIVATE.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<WorktableBlockEntity>> WORKTABLE = BLOCK_ENTITIES.register("worktable",
            () -> BlockEntityType.Builder.of(WorktableBlockEntity::new, ModBlocks.WORKTABLE.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<AnuStatueBlockEntity>> ANU_STATUE = BLOCK_ENTITIES.register("anu_statue",
            () -> BlockEntityType.Builder.of(AnuStatueBlockEntity::new, ModBlocks.ANU_STATUE.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<AnubiteStatueBlockEntity>> ANUBITE_STATUE = BLOCK_ENTITIES.register("anubite_statue",
            () -> BlockEntityType.Builder.of(AnubiteStatueBlockEntity::new, ModBlocks.ANUBITE_STATUE.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<AncientChestBlockEntity>> ANCIENT_CHEST = BLOCK_ENTITIES.register("ancient_chest",
            () -> BlockEntityType.Builder.of(AncientChestBlockEntity::new, ModBlocks.ANCIENT_CHEST.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<SarcophagusBlockEntity>> SARCOPHAGUS = BLOCK_ENTITIES.register("sarcophagus",
            () -> BlockEntityType.Builder.of(SarcophagusBlockEntity::new, ModBlocks.SARCOPHAGUS.get()).build(null));

    public static void register() {
        BLOCK_ENTITIES.register();
        PARTICLE_TYPES.register();
    }
}
