package fossilsarcheology.server.block;

import fossilsarcheology.server.block.entity.TileEntityAmphora;
import net.minecraft.block.state.IBlockState;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class AmphoraVaseBlock extends VaseBlock {

    public static final AxisAlignedBB AABB = new AxisAlignedBB(0.15F, 0.0F, 0.15F, 0.85F, 1.3F, 0.85F);

    protected AmphoraVaseBlock() {
        super("amphora");
    }

    @Override
    public Class<? extends TileEntity> getEntity() {
        return TileEntityAmphora.class;
    }

    @SuppressWarnings("deprecation")
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return AABB;
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityAmphora();
    }
}
