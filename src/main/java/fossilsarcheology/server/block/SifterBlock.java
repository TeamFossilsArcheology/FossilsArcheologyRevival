package fossilsarcheology.server.block;

import fossilsarcheology.Revival;
import fossilsarcheology.server.ServerProxy;
import fossilsarcheology.server.api.BlockEntity;
import fossilsarcheology.server.api.DefaultRenderedItem;
import fossilsarcheology.server.block.entity.TileEntitySifter;
import fossilsarcheology.server.tab.FATabRegistry;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import java.util.Random;

public class SifterBlock extends BlockContainer implements DefaultRenderedItem, BlockEntity {
    private static boolean keepInventory = false;

    public SifterBlock(boolean isActive) {
        super(Material.WOOD);
        this.setHarvestLevel("axe", 0);
        this.setHardness(2.5F);
        this.setSoundType(SoundType.METAL);
        if (isActive) {
            setTranslationKey("sifter_active");
        } else {
            setTranslationKey("sifter");
            setCreativeTab(FATabRegistry.BLOCKS);
        }
    }

    public static void setState(boolean isActive, World world, BlockPos pos) {
        TileEntity tile = world.getTileEntity(pos);
        keepInventory = true;
        if (isActive) {
            world.setBlockState(pos, FABlockRegistry.SIFTER_ACTIVE.getDefaultState());
        } else {
            world.setBlockState(pos, FABlockRegistry.SIFTER_IDLE.getDefaultState());
        }
        keepInventory = false;
        if (tile != null) {
            tile.validate();
            world.setTileEntity(pos, tile);
        }
    }

    @Override
    public boolean isFlammable(IBlockAccess world, BlockPos pos, EnumFacing face) {
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random random, int fortune) {
        return Item.getItemFromBlock(FABlockRegistry.SIFTER_IDLE);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            player.openGui(Revival.INSTANCE, ServerProxy.GUI_SIFTER, world, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        if (!keepInventory || !(world.getBlockState(pos).getBlock() instanceof SifterBlock)) {
            TileEntity entity = world.getTileEntity(pos);
            if (entity == null) {
                return;
            }
            if (entity instanceof TileEntitySifter) {
                TileEntitySifter analyzer = (TileEntitySifter) entity;
                for (int i = 0; i < analyzer.getSizeInventory(); i++) {
                    ItemStack stack = analyzer.getStackInSlot(i);
                    if (!stack.isEmpty()) {
                        InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack);
                    }
                }
            }
        }
        super.breakBlock(world, pos, state);
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntitySifter();
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean hasComparatorInputOverride(IBlockState state) {
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public int getComparatorInputOverride(IBlockState state, World world, BlockPos pos) {
        return Container.calcRedstoneFromInventory((IInventory) world.getTileEntity(pos));
    }

    @Override
    @SideOnly(Side.CLIENT)
    public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) {
        return new ItemStack(FABlockRegistry.SIFTER_IDLE);
    }

    @Override
    public Class<? extends TileEntity> getEntity() {
        return TileEntitySifter.class;
    }
}
