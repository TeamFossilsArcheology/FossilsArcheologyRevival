package com.github.teamfossilsarcheology.fossil.item.fabric;

import com.github.teamfossilsarcheology.fossil.item.AnubiteStatueBlockItem;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class AnubiteStatueBlockItemImpl {
    public static BlockItem get(Block block, Item.Properties properties) {
        return new AnubiteStatueBlockItem(block, properties);
    }
}
