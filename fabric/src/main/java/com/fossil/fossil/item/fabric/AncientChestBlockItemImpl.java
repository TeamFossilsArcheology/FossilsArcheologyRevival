package com.fossil.fossil.item.fabric;

import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class AncientChestBlockItemImpl {
    public static BlockItem get(Block block, Item.Properties properties) {
        return new BlockItem(block, properties);
    }
}
