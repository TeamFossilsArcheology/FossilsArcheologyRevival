package com.github.teamfossilsarcheology.fossil.block.entity;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(FossilMod.MOD_ID, Registry.BLOCK_ENTITY_TYPE_REGISTRY);
    public static final RegistrySupplier<BlockEntityType<BlockEntity>> SIFTER = BLOCK_ENTITIES.register("sifter",
            () -> BlockEntityType.Builder.of(SifterBlockEntity::get, ModBlocks.SIFTER.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<BlockEntity>> ANALYZER = BLOCK_ENTITIES.register("analyzer",
            () -> BlockEntityType.Builder.of(AnalyzerBlockEntity::get, ModBlocks.ANALYZER.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<BubbleBlowerBlockEntity>> BUBBLE_BLOWER = BLOCK_ENTITIES.register("bubble_blower",
            () -> BlockEntityType.Builder.of(BubbleBlowerBlockEntity::new, ModBlocks.BUBBLE_BLOWER.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<AnuBarrierBlockEntity>> ANU_BARRIER = BLOCK_ENTITIES.register("anu_barrier_origin",
            () -> BlockEntityType.Builder.of(AnuBarrierBlockEntity::new, ModBlocks.ANU_BARRIER_ORIGIN.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<BlockEntity>> CULTURE_VAT = BLOCK_ENTITIES.register("culture_vat",
            () -> BlockEntityType.Builder.of(CultureVatBlockEntity::get, ModBlocks.CULTURE_VAT.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<BlockEntity>> WORKTABLE = BLOCK_ENTITIES.register("worktable",
            () -> BlockEntityType.Builder.of(WorktableBlockEntity::get, ModBlocks.WORKTABLE.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<FeederBlockEntity>> FEEDER = BLOCK_ENTITIES.register("feeder",
            () -> BlockEntityType.Builder.of(FeederBlockEntity::new, ModBlocks.FEEDER.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<AnuStatueBlockEntity>> ANU_STATUE = BLOCK_ENTITIES.register("anu_statue",
            () -> BlockEntityType.Builder.of(AnuStatueBlockEntity::new, ModBlocks.ANU_STATUE.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<AnubiteStatueBlockEntity>> ANUBITE_STATUE = BLOCK_ENTITIES.register("anubite_statue",
            () -> BlockEntityType.Builder.of(AnubiteStatueBlockEntity::new, ModBlocks.ANUBITE_STATUE.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<AncientChestBlockEntity>> ANCIENT_CHEST = BLOCK_ENTITIES.register("ancient_chest",
            () -> BlockEntityType.Builder.of(AncientChestBlockEntity::new, ModBlocks.ANCIENT_CHEST.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<SarcophagusBlockEntity>> SARCOPHAGUS = BLOCK_ENTITIES.register("sarcophagus",
            () -> BlockEntityType.Builder.of(SarcophagusBlockEntity::new, ModBlocks.SARCOPHAGUS.get()).build(null));
    public static final RegistrySupplier<BlockEntityType<FakeObsidianBlockEntity>> FAKE_OBSIDIAN = BLOCK_ENTITIES.register("fake_obsidian",
            () -> BlockEntityType.Builder.of(FakeObsidianBlockEntity::new, ModBlocks.FAKE_OBSIDIAN.get()).build(null));

    public static void register() {
        BLOCK_ENTITIES.register();
    }
}
