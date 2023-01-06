package com.fossil.fossil.block.entity;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.block.ModBlocks;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;

import java.util.function.Supplier;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Fossil.MOD_ID, Registry.BLOCK_ENTITY_TYPE_REGISTRY);
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

    public static void register() {
        BLOCK_ENTITIES.register();
    }
}
