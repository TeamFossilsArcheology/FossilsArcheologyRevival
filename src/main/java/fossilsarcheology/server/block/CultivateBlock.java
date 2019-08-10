package fossilsarcheology.server.block;

import fossilsarcheology.Revival;
import fossilsarcheology.server.ServerProxy;
import fossilsarcheology.server.api.BlockEntity;
import fossilsarcheology.server.api.DefaultRenderedItem;
import fossilsarcheology.server.block.entity.TileEntityCultivate;
import fossilsarcheology.server.entity.monster.EntityFailuresaurus;
import fossilsarcheology.server.tab.FATabRegistry;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.entity.passive.EntityZombieHorse;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.InventoryHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.text.TextComponentTranslation;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

import java.util.List;
import java.util.Random;

public class CultivateBlock extends BlockContainer implements DefaultRenderedItem, BlockEntity {

    private static boolean keepInventory = false;

    public CultivateBlock(boolean isActive) {
        super(Material.IRON);
        this.setHardness(0.3F);
        this.setSoundType(SoundType.GLASS);
        if (isActive) {
            this.setLightLevel(0.9375F);
            this.setTranslationKey("cultivate_active");
        } else {
            this.setTranslationKey("cultivate");
            this.setCreativeTab(FATabRegistry.BLOCKS);
        }
    }

    public static void setState(boolean isActive, World world, BlockPos pos) {
        TileEntity tile = world.getTileEntity(pos);
        keepInventory = true;
        if (isActive) {
            world.setBlockState(pos, FABlockRegistry.CULTIVATE_ACTIVEE.getDefaultState());
        } else {
            world.setBlockState(pos, FABlockRegistry.CULTIVATE_IDLE.getDefaultState());
        }
        keepInventory = false;
        if (tile != null) {
            tile.validate();
            world.setTileEntity(pos, tile);
        }
    }

    public void onBlockRemovalLost(World world, BlockPos pos, boolean isActive) {
        keepInventory = false;

        List<EntityPlayer> nearby = world.getEntitiesWithinAABB(EntityPlayer.class, new AxisAlignedBB(pos.getX() - 50.0, pos.getY() - 50.0, pos.getZ() - 50.0, pos.getX() + 50.0, pos.getY() + 50.0, pos.getZ() + 50.0));
        for (EntityPlayer player : nearby) {
            player.sendStatusMessage(new TextComponentTranslation("cultivate.outBreak"), false);
        }

        this.returnIron(world, pos);
        this.dropInventory(world, pos);

        if (!world.isRemote) {
            if (isActive) {
                TileEntityCultivate entity = (TileEntityCultivate) world.getTileEntity(pos);
                if (entity != null) {
                    world.destroyBlock(pos, false);
                    if (entity.isPlant) {
                        world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.BLOCKS, 1, 1, false);
                        world.setBlockState(pos, Blocks.DIRT.getDefaultState());
                        world.setBlockState(pos.up(), FABlockRegistry.MUTANT_FLOWER.getDefaultState().withProperty(TallFlowerBlock.HALF, TallFlowerBlock.EnumBlockHalf.LOWER));
                        world.setBlockState(pos.up(2), FABlockRegistry.MUTANT_FLOWER.getDefaultState().withProperty(TallFlowerBlock.HALF, TallFlowerBlock.EnumBlockHalf.UPPER));
                    } else {
                        EntityLiving creature;
                        world.playSound(pos.getX(), pos.getY(), pos.getZ(), SoundEvents.BLOCK_GLASS_BREAK, SoundCategory.BLOCKS, 1, 1, false);
                        world.setBlockState(pos, Blocks.WATER.getDefaultState());
                        int rand = world.rand.nextInt(100);

                        if (rand <= 5) {
                            creature = new EntityCreeper(world);
                        } else if (rand < 10) {
                            creature = new EntityPigZombie(world);
                        } else if (rand > 10 && rand < 15) {
                            creature = new EntityZombieHorse(world);
                        } else if (rand > 15 && rand < 20) {
                            creature = new EntityMooshroom(world);
                        } else {
                            creature = new EntityFailuresaurus(world);
                            ((EntityFailuresaurus) creature).setSkin(new Random().nextInt(3));
                        }

                        creature.setLocationAndAngles((double) pos.getX() + 0.5, (double) pos.getY() + 0.5, (double) pos.getZ() + 0.5, world.rand.nextFloat() * 360.0F, 0.0F);
                        world.spawnEntity(creature);
                    }
                    world.removeTileEntity(pos);
                }
            }
        }
    }

    private void returnIron(World world, BlockPos pos) {
        Random rand = new Random();
        ItemStack stack = new ItemStack(Items.IRON_INGOT, 1 + world.rand.nextInt(2));
        float offsetX = rand.nextFloat() * 0.8F + 0.1F;
        float offsetY = rand.nextFloat() * 0.8F + 0.1F;
        float offsetZ = rand.nextFloat() * 0.8F + 0.1F;
        EntityItem item = new EntityItem(world, (double) ((float) pos.getX() + offsetX), (double) ((float) pos.getY() + offsetY), (double) ((float) pos.getZ() + offsetZ), stack);
        float motionMutlipler = 0.05F;
        item.motionX = (double) ((float) rand.nextGaussian() * motionMutlipler);
        item.motionY = (double) ((float) rand.nextGaussian() * motionMutlipler + 0.2F);
        item.motionZ = (double) ((float) rand.nextGaussian() * motionMutlipler);
        world.spawnEntity(item);
    }

    @SuppressWarnings("deprecation")
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }

    @Override
    public Item getItemDropped(IBlockState state, Random random, int fortune) {
        return Item.getItemFromBlock(FABlockRegistry.CULTIVATE_IDLE);
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        if (!world.isRemote) {
            player.openGui(Revival.INSTANCE, ServerProxy.GUI_CULTIVATE, world, pos.getX(), pos.getY(), pos.getZ());
        }
        return true;
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        if (stack.hasDisplayName()) {
            TileEntity tile = world.getTileEntity(pos);
            if (tile instanceof TileEntityCultivate) {
                ((TileEntityCultivate) tile).setCustomName(stack.getDisplayName());
            }
        }
    }

    @Override
    public void breakBlock(World world, BlockPos pos, IBlockState state) {
        this.dropInventory(world, pos);
        super.breakBlock(world, pos, state);
    }

    private void dropInventory(World world, BlockPos pos) {
        if (!keepInventory) {
            TileEntity entity = world.getTileEntity(pos);
            if (entity == null) {
                return;
            }

            IItemHandler inventory = entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
            if (inventory == null) {
                return;
            }

            for (int i = 0; i < inventory.getSlots(); i++) {
                ItemStack stack = inventory.getStackInSlot(i);
                if (!stack.isEmpty()) {
                    InventoryHelper.spawnItemStack(world, pos.getX(), pos.getY(), pos.getZ(), stack);
                }
            }
        }
    }

    @Override
    public TileEntity createNewTileEntity(World world, int metadata) {
        return new TileEntityCultivate();
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
        return new ItemStack(FABlockRegistry.CULTIVATE_IDLE);
    }

    @Override
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public Class<? extends TileEntity> getEntity() {
        return TileEntityCultivate.class;
    }

    @Deprecated
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side){
        return true;
    }
}
