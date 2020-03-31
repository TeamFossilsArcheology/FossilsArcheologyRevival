package fossilsarcheology.server.block;

import fossilsarcheology.server.api.DefaultRenderedItem;
import fossilsarcheology.server.tab.FATabRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;

public class AncientWoodBlock extends Block implements DefaultRenderedItem {
    public AncientWoodBlock() {
        super(Material.WOOD);
        this.setCreativeTab(FATabRegistry.BLOCKS);
        setHardness(2.0F);
        setSoundType(SoundType.WOOD);
        setTranslationKey("ancient_wood");
    }

    @Override
    public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return true;
    }
}
