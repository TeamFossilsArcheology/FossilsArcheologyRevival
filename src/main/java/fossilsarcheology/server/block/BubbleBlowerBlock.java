package fossilsarcheology.server.block;

import fossilsarcheology.Revival;
import fossilsarcheology.server.api.DefaultRenderedItem;
import fossilsarcheology.server.tab.FATabRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class BubbleBlowerBlock extends Block implements DefaultRenderedItem {
    public static final PropertyDirection FACING = BlockHorizontal.FACING;

    public BubbleBlowerBlock() {
        super(Material.IRON);
        this.setHardness(3.0F);
        this.setDefaultState(this.blockState.getBaseState().withProperty(FACING, EnumFacing.NORTH));
        setSoundType(SoundType.METAL);
        this.setTranslationKey("bubble_blower");
        setCreativeTab(FATabRegistry.BLOCKS);
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
        EnumFacing facing = EnumFacing.byIndex(meta);
        if (facing.getAxis() == EnumFacing.Axis.Y) {
            facing = EnumFacing.NORTH;
        }
        return this.getDefaultState().withProperty(FACING, facing);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getIndex();
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
        return new BlockStateContainer(this, FACING);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World world, BlockPos pos, Random rand) {

        super.randomDisplayTick(stateIn, world, pos, rand);
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        if (world.getRedstonePowerFromNeighbors(pos) > 0) {
            world.playSound(pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.NEUTRAL, 0.5F, rand.nextFloat() * 0.7F + 0.4F, false);
            EnumFacing enumfacing = stateIn.getValue(FACING);

            switch (enumfacing) {
                case NORTH:
                    Revival.PROXY.spawnBubbleParticles(world, x + rand.nextFloat(), y + rand.nextFloat(), z, 0, 0.1, 0);
                    Revival.PROXY.spawnBubbleParticles(world, x + rand.nextFloat(), y + rand.nextFloat(), z, 0, 0.1, 0);
                    Revival.PROXY.spawnBubbleParticles(world, x + rand.nextFloat(), y + rand.nextFloat(), z, 0, 0.1, 0);
                    Revival.PROXY.spawnBubbleParticles(world, x + rand.nextFloat(), y + rand.nextFloat(), z, 0, 0.1, 0);
                    break;
                case EAST:
                    Revival.PROXY.spawnBubbleParticles(world, x + 1.1F, y + rand.nextFloat(), z + rand.nextFloat(), 0, 0.1, 0);
                    Revival.PROXY.spawnBubbleParticles(world, x + 1.1F, y + rand.nextFloat(), z + rand.nextFloat(), 0, 0.1, 0);
                    Revival.PROXY.spawnBubbleParticles(world, x + 1.1F, y + rand.nextFloat(), z + rand.nextFloat(), 0, 0.1, 0);
                    Revival.PROXY.spawnBubbleParticles(world, x + 1.1F, y + rand.nextFloat(), z + rand.nextFloat(), 0, 0.1, 0);
                    break;
                case SOUTH:
                    Revival.PROXY.spawnBubbleParticles(world, x + rand.nextFloat(), y + rand.nextFloat(), z + 1.1F, 0, 0.1, 0);
                    Revival.PROXY.spawnBubbleParticles(world, x + rand.nextFloat(), y + rand.nextFloat(), z + 1.1F, 0, 0.1, 0);
                    Revival.PROXY.spawnBubbleParticles(world, x + rand.nextFloat(), y + rand.nextFloat(), z + 1.1F, 0, 0.1, 0);
                    Revival.PROXY.spawnBubbleParticles(world, x + rand.nextFloat(), y + rand.nextFloat(), z + 1.1F, 0, 0.1, 0);
                    break;
                case WEST:
                    Revival.PROXY.spawnBubbleParticles(world, x, y + rand.nextFloat(), z + rand.nextFloat(), 0, 0.1, 0);
                    Revival.PROXY.spawnBubbleParticles(world, x, y + rand.nextFloat(), z + rand.nextFloat(), 0, 0.1, 0);
                    Revival.PROXY.spawnBubbleParticles(world, x, y + rand.nextFloat(), z + rand.nextFloat(), 0, 0.1, 0);
                    Revival.PROXY.spawnBubbleParticles(world, x, y + rand.nextFloat(), z + rand.nextFloat(), 0, 0.1, 0);

                    break;
            }
        }
    }
}
