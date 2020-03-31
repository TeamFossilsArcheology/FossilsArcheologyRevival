package fossilsarcheology.server.block;

import fossilsarcheology.server.api.DefaultRenderedItem;
import fossilsarcheology.server.tab.FATabRegistry;
import net.minecraft.block.BlockFence;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class FossilFenceBlock extends BlockFence implements DefaultRenderedItem {

    public FossilFenceBlock(IBlockState parent, String name) {
        super(parent.getMaterial(), parent.getMaterial().getMaterialMapColor());
        this.setCreativeTab(FATabRegistry.BLOCKS);
        this.setTranslationKey(name);
        this.setRegistryName(name);
        this.setHardness(2.0F);
        this.setResistance(5.0F);
        this.setSoundType(SoundType.WOOD);
    }

    @Override
    public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return true;
    }
}
