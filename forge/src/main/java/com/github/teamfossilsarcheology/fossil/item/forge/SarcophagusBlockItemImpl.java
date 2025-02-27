package com.github.teamfossilsarcheology.fossil.item.forge;

import com.github.teamfossilsarcheology.fossil.client.renderer.CustomItemRenderer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.extensions.common.IClientItemExtensions;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class SarcophagusBlockItemImpl {
    public static BlockItem get(Block block, Item.Properties properties) {
        return new BlockItem(block, properties) {

            @Override
            public void initializeClient(@NotNull Consumer<IClientItemExtensions> consumer) {
                consumer.accept(new IClientItemExtensions() {
                    @Override
                    public BlockEntityWithoutLevelRenderer getCustomRenderer() {
                        return CustomItemRenderer.INSTANCE;
                    }
                });
            }
        };
    }
}
