package fossilsarcheology.server.block;

import fossilsarcheology.Revival;
import fossilsarcheology.server.api.DefaultRenderedItem;
import fossilsarcheology.server.item.FAItemRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class FernsBlock extends BlockBush implements DefaultRenderedItem {

    public static final PropertyInteger GROWTH = PropertyInteger.create("growth", 0, 7);
    public static final PropertyInteger TYPE = PropertyInteger.create("type", 0, 1);
    protected static final AxisAlignedBB FERN_AABB = new AxisAlignedBB(0.0F, 0.0F, 0.0F, 1.0F, 0.25F, 1.0F);

    public FernsBlock() {
        super(Material.PLANTS);
        this.setTickRandomly(true);
        this.disableStats();
        this.setTranslationKey("fern_block");
        this.setHardness(0.0F);
        this.setSoundType(SoundType.PLANT);
        this.setCreativeTab(null);
        this.setDefaultState(this.blockState.getBaseState().withProperty(TYPE, 0).withProperty(GROWTH, 0));
    }

    @Override
    public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return true;
    }

    public static boolean checkUnderTree(World var0, BlockPos pos) {
        for (int i = 0; i <= 128; ++i) {
            if (var0.getBlockState(pos.up(i)).getMaterial() == Material.LEAVES) {
                return true;
            }
        }
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return FERN_AABB;
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
        super.updateTick(world, pos, state, rand);
        IBlockState currentState = world.getBlockState(pos);
        int var6 = currentState.getBlock().getMetaFromState(currentState);

        if (checkUnderTree(world, pos) && this.canGrow(var6)) {
            if (rand.nextInt(Revival.CONFIG_OPTIONS.fernTickRate) == 0) {
                if (world.getBlockState(pos.down()).getBlock() != this || var6 < 2) {
                    ++var6;
                    if (this.getLv2(var6)) {
                        if (!world.isAirBlock(pos.up())) {
                            --var6;
                        } else {
                            world.setBlockState(pos.up(), FABlockRegistry.FERNS.getStateFromMeta(this.checkSubType(var6) == 0 ? var6 + 2 : var6 + 1));
                        }
                    } else if (this.hasLv2(var6)) {
                        if (world.getBlockState(pos.up()).getBlock() == FABlockRegistry.FERNS) {
                            world.setBlockState(pos.up(), FABlockRegistry.FERNS.getStateFromMeta(this.checkSubType(var6) == 0 ? var6 + 2 : var6 + 1));
                        } else {
                            var6 = this.checkSubType(var6) == 0 ? 3 : 10;
                        }
                    }
                    world.setBlockState(pos, currentState.getBlock().getStateFromMeta(var6));
                }
            }
        }

        if (var6 % 7 >= 3) {
            for (int var8 = -1; var8 <= 1; ++var8) {
                for (int var9 = -1; var9 <= 1; ++var9) {
                    for (int var10 = -1; var10 <= 1; ++var10) {
                        if ((var8 != 0 || var10 != 0 || var9 != 0) && canPlaceBlockOn(world.getBlockState(pos.add(var8, var9 - 1, var10)).getBlock()) && (world.isAirBlock(pos.add(var8, var9, var10)) || world.getBlockState(pos.add(var8, var9, var10)).getBlock() == Blocks.TALLGRASS) && checkUnderTree(world, pos.add(var8, var9, var10)) && (new Random()).nextInt(10) <= 9) {
                            world.setBlockState(pos.add(var8, var9, var10), FABlockRegistry.FERNS.getStateFromMeta(8 * this.checkSubType(var6)));
                        }
                    }
                }
            }
        }
    }

    @Override
    public boolean canBlockStay(World world, BlockPos pos, IBlockState state) {
        IBlockState currentState = world.getBlockState(pos);
        int meta = currentState.getBlock().getMetaFromState(currentState);
        return (canGrowOn(world.getBlockState(pos.down())) || world.getBlockState(pos.down()).getBlock() == this) && checkUnderTree(world, pos.up());

    }

    public static boolean canGrowOn(IBlockState state){
        return state.getMaterial() == Material.GRASS || state.getBlock() == Blocks.DIRT || state.getBlock().getTranslationKey().contains("dirt");
    }

    public boolean checkLevel(int meta) {
        return (meta >= 6 && meta <= 7) || meta == 12;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        if (rand.nextInt(4) == 0) {
            return FAItemRegistry.FERN_SEED;
        }
        return null;
    }

    public int checkSubType(int meta) {
        return meta > 7 ? 1 : 0;
    }

    protected boolean canPlaceBlockOn(Block block) {
        return block == Blocks.GRASS || block == Blocks.DIRT || block.getTranslationKey().contains("dirt");
    }

    @Override
    public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
        return this.canPlaceBlockOn(worldIn.getBlockState(pos).getBlock());
    }

    public boolean canGrow(int meta) {
        return (meta >= 0 && meta < 5) || (meta >= 8 && meta < 11);
    }

    public boolean hasLv2(int meta) {
        return (meta >= 4 && meta <= 5) || (meta >= 11 && meta <= 11);
    }

    public boolean getLv2(int meta) {
        return (meta == 4) || (meta == 11);
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(TYPE, meta / 8).withProperty(GROWTH, (meta < 8 ? meta : meta - 8));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(TYPE) == 0 ? state.getValue(GROWTH) : state.getValue(GROWTH) + 7;
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, TYPE, GROWTH);
    }
}
