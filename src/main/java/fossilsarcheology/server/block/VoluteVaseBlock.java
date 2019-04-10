package fossilsarcheology.server.block;

import fossilsarcheology.server.block.entity.TileEntityVolute;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class VoluteVaseBlock extends VaseBlock {

    public static final AxisAlignedBB AABB = new AxisAlignedBB(0.1F, 0.0F, 0.1F, 0.9F, 0.9F, 0.9F);

    protected VoluteVaseBlock() {
        super("volute");
    }

    @Override
    public Class<? extends TileEntity> getEntity() {
        return TileEntityVolute.class;
    }

    @SuppressWarnings("deprecation")
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return AABB;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityVolute();
    }
}
