package fossilsarcheology.server.block;

import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;

public interface IBlockItem {
    ItemBlock getItemBlock(Block block);
}
