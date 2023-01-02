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

    public static void register() {
        BLOCK_ENTITIES.register();
    }
}
