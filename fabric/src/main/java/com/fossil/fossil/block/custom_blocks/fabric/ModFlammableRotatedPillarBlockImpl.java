package com.fossil.fossil.block.custom_blocks.fabric;

import com.fossil.fossil.block.ModBlocks;
import net.fabricmc.fabric.api.event.registry.RegistryEntryAddedCallback;
import net.fabricmc.fabric.api.registry.FlammableBlockRegistry;
import net.fabricmc.fabric.api.registry.StrippableBlockRegistry;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

public class ModFlammableRotatedPillarBlockImpl {
    static {
        // It seems that registration order isn't deterministic? I wish it was though, as code below can be cleaner.
        RegistryEntryAddedCallback.event(Registry.BLOCK).register((rawId, id, object) -> {
            if (id.equals(ModBlocks.STRIPPED_CORDAITES_LOG.getId())) {
                if (ModBlocks.CORDAITES_LOG.isPresent()) StrippableBlockRegistry.register(ModBlocks.CORDAITES_LOG.get(), object);
            } else if (id.equals(ModBlocks.CORDAITES_LOG.getId())) {
                if (ModBlocks.STRIPPED_CORDAITES_WOOD.isPresent()) StrippableBlockRegistry.register(object, ModBlocks.STRIPPED_CORDAITES_WOOD.get());
            } else if (id.equals(ModBlocks.STRIPPED_CORDAITES_WOOD.getId())) {
                if (ModBlocks.CORDAITES_WOOD.isPresent()) StrippableBlockRegistry.register(ModBlocks.CORDAITES_WOOD.get(), object);
            } else if (id.equals(ModBlocks.CORDAITES_WOOD.getId())) {
                if (ModBlocks.STRIPPED_CORDAITES_WOOD.isPresent()) StrippableBlockRegistry.register(object, ModBlocks.STRIPPED_CORDAITES_WOOD.get());
            }
        });
    }
    public static Block get(BlockBehaviour.Properties properties) {
        Block block = new RotatedPillarBlock(properties);
        FlammableBlockRegistry.getDefaultInstance().add(block, 5, 5);
        return block;
    }
}
