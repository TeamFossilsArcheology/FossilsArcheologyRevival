package fossilsarcheology.server.block;

import fossilsarcheology.server.api.DefaultRenderedItem;
import fossilsarcheology.server.tab.FATabRegistry;
import net.minecraft.block.BlockTrapDoor;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;

public class FossilTrapdoorBlock extends BlockTrapDoor implements DefaultRenderedItem {

    protected FossilTrapdoorBlock(IBlockState parent, String name) {
        super(parent.getMaterial());
        this.setTranslationKey(name);
        this.setRegistryName(name);
        this.setCreativeTab(FATabRegistry.BLOCKS);
        this.setHardness(2.0F);
        this.setResistance(5.0F);
        this.setSoundType(SoundType.WOOD);
    }


}
