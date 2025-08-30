package com.github.teamfossilsarcheology.fossil.block.entity;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES = DeferredRegister.create(FossilMod.MOD_ID, Registries.BLOCK_ENTITY_TYPE);
    public static final RegistrySupplier<BlockEntityType<AnalyzerBlockEntity>> ANALYZER = register(AnalyzerBlockEntity::new, ModBlocks.ANALYZER);
    public static final RegistrySupplier<BlockEntityType<BubbleBlowerBlockEntity>> BUBBLE_BLOWER = register(BubbleBlowerBlockEntity::new, ModBlocks.BUBBLE_BLOWER);
    public static final RegistrySupplier<BlockEntityType<CultureVatBlockEntity>> CULTURE_VAT = register(CultureVatBlockEntity::new, ModBlocks.CULTURE_VAT);
    public static final RegistrySupplier<BlockEntityType<FeederBlockEntity>> FEEDER = register(FeederBlockEntity::new, ModBlocks.FEEDER);
    public static final RegistrySupplier<BlockEntityType<SifterBlockEntity>> SIFTER = register(SifterBlockEntity::new, ModBlocks.SIFTER);
    public static final RegistrySupplier<BlockEntityType<WorktableBlockEntity>> WORKTABLE = register(WorktableBlockEntity::new, ModBlocks.WORKTABLE);
    public static final RegistrySupplier<BlockEntityType<AnuBarrierBlockEntity>> ANU_BARRIER = register(AnuBarrierBlockEntity::new, ModBlocks.ANU_BARRIER_ORIGIN);
    public static final RegistrySupplier<BlockEntityType<AnuStatueBlockEntity>> ANU_STATUE = register(AnuStatueBlockEntity::new, ModBlocks.ANU_STATUE);
    public static final RegistrySupplier<BlockEntityType<AnubiteStatueBlockEntity>> ANUBITE_STATUE = register(AnubiteStatueBlockEntity::new, ModBlocks.ANUBITE_STATUE);
    public static final RegistrySupplier<BlockEntityType<AncientChestBlockEntity>> ANCIENT_CHEST = register(AncientChestBlockEntity::new, ModBlocks.ANCIENT_CHEST);
    public static final RegistrySupplier<BlockEntityType<SarcophagusBlockEntity>> SARCOPHAGUS = register(SarcophagusBlockEntity::new, ModBlocks.SARCOPHAGUS);
    public static final RegistrySupplier<BlockEntityType<FakeObsidianBlockEntity>> FAKE_OBSIDIAN = register(FakeObsidianBlockEntity::new, ModBlocks.FAKE_OBSIDIAN);

    private static <T extends BlockEntity> RegistrySupplier<BlockEntityType<T>> register(BlockEntityType.BlockEntitySupplier<T> supplier,  RegistrySupplier<? extends Block> block) {
        return BLOCK_ENTITIES.register(block.getId().getPath(), () -> BlockEntityType.Builder.of(supplier, block.get()).build(null));
    }

    public static void register() {
        BLOCK_ENTITIES.register();
    }
}
