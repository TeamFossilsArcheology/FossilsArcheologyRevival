package com.fossil.fossil.item;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.level.block.Block;
import org.apache.commons.lang3.NotImplementedException;

public class AncientChestBlockItem extends BlockItem {
    private AncientChestBlockItem(Block block, Properties properties) {
        super(block, properties);
    }

    @ExpectPlatform
    public static BlockItem get(Block block, Properties properties) {
        throw new NotImplementedException();
    }
}
