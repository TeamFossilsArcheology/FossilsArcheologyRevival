package fossilsarcheology.server.block;

import fossilsarcheology.Revival;
import fossilsarcheology.server.ServerProxy;
import fossilsarcheology.server.api.DefaultRenderedItem;
import fossilsarcheology.server.block.entity.TileEntityFeeder;
import fossilsarcheology.server.tab.FATabRegistry;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class FeederBlock extends BlockContainer implements DefaultRenderedItem {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;
    public static final PropertyBool HERB = PropertyBool.create("herb");
    public static final PropertyBool CARN = PropertyBool.create("carn");

    protected FeederBlock() {
        super(Material.IRON);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH).withProperty(HERB, false).withProperty(CARN, false));
        this.setSoundType(SoundType.METAL);
        this.setTranslationKey("feeder");
        this.setCreativeTab(FATabRegistry.BLOCKS);
        this.setHardness(3);
    }

    public static void updateFeederBlockState(boolean herb, boolean carn, World world, BlockPos pos) {
        IBlockState state = world.getBlockState(pos);
        if (state.getValue(HERB) != herb || state.getValue(CARN) != carn) {
            TileEntity entity = world.getTileEntity(pos);
            world.setBlockState(pos, state.withProperty(HERB, herb).withProperty(CARN, carn));
            world.setTileEntity(pos, entity);
        }
    }

    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            player.openGui(Revival.INSTANCE, ServerProxy.GUI_FEEDER, world, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    private void setDefaultFacing(World world, BlockPos pos, IBlockState state) {
        if (!world.isRemote) {
            IBlockState northState = world.getBlockState(pos.north());
            IBlockState southState = world.getBlockState(pos.south());
            IBlockState westState = world.getBlockState(pos.west());
            IBlockState eastState = world.getBlockState(pos.east());
            EnumFacing facing = state.getValue(FACING);

            if (facing == EnumFacing.NORTH && northState.isFullBlock() && !southState.isFullBlock()) {
                facing = EnumFacing.SOUTH;
            } else if (facing == EnumFacing.SOUTH && southState.isFullBlock() && !northState.isFullBlock()) {
                facing = EnumFacing.NORTH;
            } else if (facing == EnumFacing.WEST && westState.isFullBlock() && !eastState.isFullBlock()) {
                facing = EnumFacing.EAST;
            } else if (facing == EnumFacing.EAST && eastState.isFullBlock() && !westState.isFullBlock()) {
                facing = EnumFacing.WEST;
            }

            world.setBlockState(pos, state.withProperty(FACING, facing), 2);
        }
    }

    @Override
    public IBlockState getStateForPlacement(World world, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer, EnumHand hand) {
        return this.getDefaultState().withProperty(FACING, placer.getHorizontalFacing().getOpposite());
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        worldIn.setBlockState(pos, state.withProperty(FACING, placer.getHorizontalFacing().getOpposite()), 2);
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getStateFromMeta(int meta) {
        EnumFacing facing = EnumFacing.byHorizontalIndex(meta & 3);
        if (facing.getAxis() == EnumFacing.Axis.Y) {
            facing = EnumFacing.NORTH;
        }
        boolean herb = (meta >> 2 & 1) != 0;
        boolean carn = (meta >> 3 & 1) != 0;
        return this.getDefaultState().withProperty(FACING, facing).withProperty(HERB, herb).withProperty(CARN, carn);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getHorizontalIndex()
                | (state.getValue(HERB) ? 1 : 0) << 2
                | (state.getValue(CARN) ? 1 : 0) << 3;
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState withRotation(IBlockState state, Rotation rot) {
        return state.withProperty(FACING, rot.rotate(state.getValue(FACING)));
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState withMirror(IBlockState state, Mirror mirrorIn) {
        return state.withRotation(mirrorIn.toRotation(state.getValue(FACING)));
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING, HERB, CARN);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int meta) {
        return new TileEntityFeeder();
    }
}
