package fossilsarcheology.server.block;

import fossilsarcheology.server.advancement.EggTrigger;
import fossilsarcheology.server.advancement.UseScarabTrigger;
import fossilsarcheology.server.api.BlockEntity;
import fossilsarcheology.server.api.DefaultRenderedItem;
import fossilsarcheology.server.block.entity.TileEntitySarcophagus;
import fossilsarcheology.server.item.FAItemRegistry;
import fossilsarcheology.server.tab.FATabRegistry;
import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.PropertyDirection;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;

public class SarcophagusBlock extends BlockContainer implements DefaultRenderedItem, BlockEntity {
    public static final PropertyDirection FACING = PropertyDirection.create("facing", EnumFacing.Plane.HORIZONTAL);
    public static final UseScarabTrigger SCARAB_TRIGGER = CriteriaTriggers.register(new UseScarabTrigger());

    public SarcophagusBlock() {
        super(Material.ROCK);
        this.setCreativeTab(FATabRegistry.BLOCKS);
        this.setTickRandomly(true);
        this.setBlockUnbreakable();
        this.setResistance(60000000.0F);
        this.setTranslationKey("sarcophagus");

    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player, EnumHand hand, EnumFacing side, float hitX, float hitY, float hitZ) {
        TileEntitySarcophagus tile = (TileEntitySarcophagus) world.getTileEntity(pos);
        if (tile.chestState == 0) {
            if (player.getHeldItem(hand).getItem() == FAItemRegistry.SCARAB_GEM) {
                tile.chestState = 1;
                if (!player.capabilities.isCreativeMode) {
                    player.getHeldItem(hand).shrink(1);
                }
                if (!world.isRemote && player instanceof EntityPlayerMP) {
                    SCARAB_TRIGGER.trigger((EntityPlayerMP) player);
                }
            }
        } else if (tile.chestState == 1) {
            tile.chestState = 2;
            tile.chestLidCounter = 1;
            world.playSound(player, pos, SoundEvents.BLOCK_CHEST_OPEN, SoundCategory.BLOCKS, 0.5F, world.rand.nextFloat() * 0.1F + 0.9F);

        }
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getStateForPlacement(World worldIn, BlockPos pos, EnumFacing facing, float hitX, float hitY, float hitZ, int meta, EntityLivingBase placer) {
        return super.getStateForPlacement(worldIn, pos, facing, hitX, hitY, hitZ, meta, placer).withProperty(FACING, placer.getHorizontalFacing().getOpposite());
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
    @SuppressWarnings("deprecation")
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(FACING, EnumFacing.byHorizontalIndex(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(FACING).getHorizontalIndex();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, FACING);
    }

    @Override
    @SuppressWarnings("deprecation")
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return new AxisAlignedBB(0F, 0.0F, 0F, 1F, 1.9F, 1);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntitySarcophagus();
    }

    @Override
    public Class<? extends TileEntity> getEntity() {
        return TileEntitySarcophagus.class;
    }
}
