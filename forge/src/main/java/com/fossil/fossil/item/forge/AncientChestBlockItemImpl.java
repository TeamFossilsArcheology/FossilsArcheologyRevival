package com.fossil.fossil.item.forge;

import com.fossil.fossil.client.renderer.CustomItemRenderer;
import net.minecraft.client.renderer.BlockEntityWithoutLevelRenderer;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.client.IItemRenderProperties;
import org.jetbrains.annotations.NotNull;

import java.util.function.Consumer;

public class AncientChestBlockItemImpl {
    public static BlockItem get(Block block, Item.Properties properties) {
        return new BlockItem(block, properties) {
            @Override
            public void initializeClient(@NotNull Consumer<IItemRenderProperties> consumer) {
                consumer.accept(new IItemRenderProperties() {
                    @Override
                    public BlockEntityWithoutLevelRenderer getItemStackRenderer() {
                        return CustomItemRenderer.INSTANCE;
                    }
                });
            }
        };
    }
}
