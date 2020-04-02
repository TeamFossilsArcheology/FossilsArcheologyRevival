package fossilsarcheology.server.block;

import fossilsarcheology.server.api.DefaultRenderedItem;
import fossilsarcheology.server.tab.FATabRegistry;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;

import javax.annotation.Nullable;
import java.util.Random;

public class FourTallFlowerBlock extends BlockBush implements DefaultRenderedItem, IGrowable {

    public static final PropertyInteger LAYER = PropertyInteger.create("layer", 0, 3);
    private static final AxisAlignedBB AABB = new AxisAlignedBB(0.30000001192092896D, 0.0D, 0.30000001192092896D, 0.699999988079071D, 1D, 0.699999988079071D);

    public FourTallFlowerBlock(String name) {
        super(Material.PLANTS);
        this.setDefaultState(this.blockState.getBaseState().withProperty(LAYER, 0));
        this.setTickRandomly(true);
        this.setTranslationKey(name);
        this.setSoundType(SoundType.PLANT);
        this.setCreativeTab(FATabRegistry.BLOCKS);
        this.setHardness(0.5F);
    }

    @Override
    public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return true;
    }

    @Override
    @SuppressWarnings("deprecation")
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return AABB;
    }

    @Nullable
    @SuppressWarnings("deprecation")
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
        return AABB;
    }

    @Override
    public boolean isLadder(IBlockState state, IBlockAccess world, BlockPos pos, EntityLivingBase entity) {
        return true;
    }

    @Override
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
        if (state.getBlock() == this && state.getValue(LAYER) == 0) {
            IBlockState soil = worldIn.getBlockState(pos.down());
            return soil.getBlock().canSustainPlant(soil, worldIn, pos.down(), net.minecraft.util.EnumFacing.UP, this);
        }
        if (state.getBlock() == this && state.getValue(LAYER) != 0) {
            IBlockState below = worldIn.getBlockState(pos.down());
            return below.getBlock() == this;
        }
        return this.canSustainBush(worldIn.getBlockState(pos.down()));
    }

    @Override
    public boolean canGrow(World worldIn, BlockPos pos, IBlockState state, boolean isClient) {
        return true;
    }

    @Override
    public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        return true;
    }

    @Override
    public void grow(World worldIn, Random rand, BlockPos pos, IBlockState state) {
        spreadAsBonemeal(worldIn, pos);
    }

    @Override
    protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state) {
        if (!this.canBlockStay(worldIn, pos, state)) {
            if (state.getValue(LAYER) == 0) {
                this.dropBlockAsItem(worldIn, pos, state, 0);
            }
            worldIn.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
        }
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return super.canPlaceBlockAt(worldIn, pos) && worldIn.isAirBlock(pos.up()) && worldIn.isAirBlock(pos.up(2)) && worldIn.isAirBlock(pos.up(3));
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        worldIn.setBlockState(pos, this.getDefaultState().withProperty(LAYER, 0), 2);
        worldIn.setBlockState(pos.up(), this.getDefaultState().withProperty(LAYER, 1), 2);
        worldIn.setBlockState(pos.up(2), this.getDefaultState().withProperty(LAYER, 2), 2);
        worldIn.setBlockState(pos.up(3), this.getDefaultState().withProperty(LAYER, 3), 2);
    }

    private void spreadAsBonemeal(World world, BlockPos pos) {
        Random rand = new Random();
        if (world.isRemote) {
            world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, pos.getX() + (rand.nextDouble() - 0.5D), pos.getY() + rand.nextDouble(), pos.getZ() + (rand.nextDouble() - 0.5D), 0.0D, 0.0D, 0.0D);
            world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, pos.getX() + (rand.nextDouble() - 0.5D), pos.getY() + rand.nextDouble(), pos.getZ() + (rand.nextDouble() - 0.5D), 0.0D, 0.0D, 0.0D);
            world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, pos.getX() + (rand.nextDouble() - 0.5D), pos.getY() + rand.nextDouble(), pos.getZ() + (rand.nextDouble() - 0.5D), 0.0D, 0.0D, 0.0D);
        }
        int maxTries = rand.nextInt(3);
        int tries = 0;
        int timeout = 0;
        while (tries < maxTries && timeout < 101) {
            timeout++;
            BlockPos tryPos = pos.add(rand.nextInt(10) - 4, rand.nextInt(8) - 4, rand.nextInt(10) - 4);
            if (world.isAirBlock(tryPos.up()) && world.isAirBlock(tryPos.up(2)) && world.isAirBlock(tryPos.up(3)) && world.isAirBlock(tryPos.up(4)) && canSustainBush(world.getBlockState(tryPos))) {
                tries++;
                if (!world.isRemote) {
                    world.setBlockState(tryPos.up(), this.getDefaultState().withProperty(LAYER, 0), 2);
                    world.setBlockState(tryPos.up(2), this.getDefaultState().withProperty(LAYER, 1), 2);
                    world.setBlockState(tryPos.up(3), this.getDefaultState().withProperty(LAYER, 2), 2);
                    world.setBlockState(tryPos.up(4), this.getDefaultState().withProperty(LAYER, 3), 2);
                }
            } else {
                continue;
            }
        }
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
        if (state.getValue(LAYER) == 0) {
            worldIn.destroyBlock(pos, true);
            for (int i = 1; i < 3; i++) {
                if (worldIn.getBlockState(pos.up(i)).getBlock() == this) {
                    worldIn.destroyBlock(pos.up(i), false);
                }
            }
        } else {
            worldIn.destroyBlock(pos, false);
        }
        super.onBlockHarvested(worldIn, pos, state, player);
    }

    @Override
    public net.minecraftforge.common.EnumPlantType getPlantType(net.minecraft.world.IBlockAccess world, BlockPos pos) {
        return EnumPlantType.Plains;
    }

    @Override
    public IBlockState getPlant(net.minecraft.world.IBlockAccess world, BlockPos pos) {
        return getDefaultState();
    }


    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(LAYER, meta);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(LAYER);
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, LAYER);
    }

}
