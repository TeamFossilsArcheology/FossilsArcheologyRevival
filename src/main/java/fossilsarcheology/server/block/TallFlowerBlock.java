package fossilsarcheology.server.block;

import fossilsarcheology.server.api.DefaultRenderedItem;
import fossilsarcheology.server.tab.FATabRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.IGrowable;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.IStringSerializable;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.EnumPlantType;
import net.minecraftforge.common.IPlantable;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class TallFlowerBlock extends BlockBush implements DefaultRenderedItem, IGrowable, IPlantable {

    public static final PropertyEnum<TallFlowerBlock.EnumBlockHalf> HALF = PropertyEnum.create("half", TallFlowerBlock.EnumBlockHalf.class);

    public TallFlowerBlock(String name) {
        super(Material.PLANTS);
        this.setTickRandomly(true);
        this.setTranslationKey(name);
        this.setSoundType(SoundType.PLANT);
        this.setCreativeTab(FATabRegistry.BLOCKS);
        this.setDefaultState(this.blockState.getBaseState().withProperty(HALF, TallFlowerBlock.EnumBlockHalf.LOWER));
    }

    @Override
    public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return new AxisAlignedBB(0.30000001192092896D, 0.0D, 0.30000001192092896D, 0.699999988079071D, 1D, 0.699999988079071D);
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        if (this == FABlockRegistry.MUTANT_FLOWER) {
            return worldIn.isAirBlock(pos.up()) && worldIn.getBlockState(pos.down()).getMaterial().isSolid();
        }
        return super.canPlaceBlockAt(worldIn, pos) && worldIn.isAirBlock(pos.up());
    }

    @Override
    protected void checkAndDropBlock(World worldIn, BlockPos pos, IBlockState state) {
        if (!this.canBlockStay(worldIn, pos, state)) {
            boolean flag = state.getValue(HALF) == EnumBlockHalf.UPPER;
            BlockPos blockpos = flag ? pos : pos.up();
            BlockPos blockpos1 = flag ? pos.down() : pos;
            Block block = flag ? this : worldIn.getBlockState(blockpos).getBlock();
            Block block1 = flag ? worldIn.getBlockState(blockpos1).getBlock() : this;

            if (!flag) this.dropBlockAsItem(worldIn, pos, state, 0); //Forge move above the setting to air.

            if (block == this) {
                worldIn.setBlockState(blockpos, Blocks.AIR.getDefaultState(), 2);
            }

            if (block1 == this) {
                worldIn.setBlockState(blockpos1, Blocks.AIR.getDefaultState(), 3);
            }
        }
    }

    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        if (state.getValue(HALF) == EnumBlockHalf.UPPER) {
            return Items.AIR;
        } else {
            return super.getItemDropped(state, rand, fortune);
        }
    }

    @Override
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state) {
        if (state.getValue(HALF) == TallFlowerBlock.EnumBlockHalf.UPPER) {
            //worldIn.setBlockState(pos.down(), this.getDefaultState().withProperty(HALF, TallFlowerBlock.EnumBlockHalf.LOWER), 2);
            return worldIn.getBlockState(pos.down()).getBlock() == this;
        } else {
            IBlockState iblockstate = worldIn.getBlockState(pos.up());
            if (this == FABlockRegistry.MUTANT_FLOWER) {
                return iblockstate.getBlock() == this && worldIn.getBlockState(pos.down()).getMaterial().isSolid();
            }
            return iblockstate.getBlock() == this && super.canBlockStay(worldIn, pos, iblockstate);
        }
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        worldIn.setBlockState(pos, this.getDefaultState().withProperty(HALF, TallFlowerBlock.EnumBlockHalf.LOWER), 2);
        worldIn.setBlockState(pos.up(), this.getDefaultState().withProperty(HALF, TallFlowerBlock.EnumBlockHalf.UPPER), 2);
    }

    @Override
    public void onBlockHarvested(World worldIn, BlockPos pos, IBlockState state, EntityPlayer player) {
        if (state.getValue(HALF) == TallFlowerBlock.EnumBlockHalf.UPPER) {
            if (worldIn.getBlockState(pos.down()).getBlock() == this) {
                worldIn.setBlockToAir(pos.down());
            }
        } else if (worldIn.getBlockState(pos.up()).getBlock() == this) {
            worldIn.setBlockState(pos.up(), Blocks.AIR.getDefaultState(), 2);
        }

        super.onBlockHarvested(worldIn, pos, state, player);
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

    private void spreadAsBonemeal(World world, BlockPos pos) {
        Random rand = new Random();
        world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, pos.getX() + (rand.nextDouble() - 0.5D), pos.getY() + rand.nextDouble(), pos.getZ() + (rand.nextDouble() - 0.5D), 0.0D, 0.0D, 0.0D);
        world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, pos.getX() + (rand.nextDouble() - 0.5D), pos.getY() + rand.nextDouble(), pos.getZ() + (rand.nextDouble() - 0.5D), 0.0D, 0.0D, 0.0D);
        world.spawnParticle(EnumParticleTypes.VILLAGER_HAPPY, pos.getX() + (rand.nextDouble() - 0.5D), pos.getY() + rand.nextDouble(), pos.getZ() + (rand.nextDouble() - 0.5D), 0.0D, 0.0D, 0.0D);
        int maxTries = rand.nextInt(2);
        int tries = 0;
        while (tries < maxTries) {
            BlockPos tryPos = pos.add(rand.nextInt(10) - 4, rand.nextInt(8) - 4, rand.nextInt(10) - 4);
            if (world.isAirBlock(tryPos.up()) && world.isAirBlock(tryPos.up(2)) && canSustainBush(world.getBlockState(tryPos))) {
                tries++;
                world.setBlockState(tryPos.up(), this.getDefaultState().withProperty(HALF, TallFlowerBlock.EnumBlockHalf.LOWER), 2);
                world.setBlockState(tryPos.up(2), this.getDefaultState().withProperty(HALF, TallFlowerBlock.EnumBlockHalf.UPPER), 2);
            } else {
                continue;
            }
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(HALF, meta == 1 ? EnumBlockHalf.UPPER : EnumBlockHalf.LOWER);
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(HALF) == TallFlowerBlock.EnumBlockHalf.UPPER ? 1 : 0;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, HALF);
    }

    @Override
    public boolean removedByPlayer(IBlockState state, World world, BlockPos pos, EntityPlayer player, boolean willHarvest) {
        if (state.getBlock() == this && state.getValue(HALF) == EnumBlockHalf.LOWER && world.getBlockState(pos.up()).getBlock() == this) {
            world.setBlockToAir(pos.up());
        }
        return world.setBlockToAir(pos);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public Block.EnumOffsetType getOffsetType() {
        return Block.EnumOffsetType.XZ;
    }

    @Override
    public net.minecraftforge.common.EnumPlantType getPlantType(net.minecraft.world.IBlockAccess world, BlockPos pos) {
        return EnumPlantType.Plains;
    }

    @Override
    public IBlockState getPlant(net.minecraft.world.IBlockAccess world, BlockPos pos) {
        return getDefaultState();
    }

    public enum EnumBlockHalf implements IStringSerializable {
        UPPER, LOWER;

        @Override
        public String toString() {
            return this.getName();
        }

        @Override
        public String getName() {
            return this == UPPER ? "upper" : "lower";
        }
    }
}
