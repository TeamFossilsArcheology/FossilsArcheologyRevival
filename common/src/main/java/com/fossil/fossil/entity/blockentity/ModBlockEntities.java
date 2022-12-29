package com.fossil.fossil.entity.blockentity;

import com.fossil.fossil.Fossil;
import dev.architectury.registry.registries.DeferredRegister;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.entity.BlockEntityType;

public class ModBlockEntities {
    private static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITIES =
            DeferredRegister.create(Fossil.MOD_ID, Registry.BLOCK_ENTITY_TYPE_REGISTRY);

    public static void register() {
        BLOCK_ENTITIES.register();
    }
}
