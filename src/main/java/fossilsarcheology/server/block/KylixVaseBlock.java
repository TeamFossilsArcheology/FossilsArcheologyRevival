package fossilsarcheology.server.block;

import fossilsarcheology.server.block.entity.TileEntityKylix;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class KylixVaseBlock extends VaseBlock {

    public static final AxisAlignedBB AABB = new AxisAlignedBB(0.25F, 0.0F, 0.25F, 0.75F, 0.5F, 0.75F);

    protected KylixVaseBlock() {
        super("kylix");
    }

    @Override
    public Class<? extends TileEntity> getEntity() {
        return TileEntityKylix.class;
    }

    @SuppressWarnings("deprecation")
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return AABB;
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityKylix();
    }
}
