package fossilsarcheology.server.block;

import fossilsarcheology.server.api.BlockEntity;
import fossilsarcheology.server.api.IgnoreRenderProperty;
import fossilsarcheology.server.api.SubtypeRenderedItem;
import fossilsarcheology.server.block.entity.TileEntityVase;
import fossilsarcheology.server.tab.FATabRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public abstract class VaseBlock extends BlockContainer implements BlockEntity, IBlockItem, SubtypeRenderedItem, IgnoreRenderProperty {
    public static final PropertyEnum<VaseVariant> VARIANT = PropertyEnum.create("variant", VaseVariant.class);

    protected VaseBlock(String type) {
        super(Material.ROCK);
        this.setCreativeTab(FATabRegistry.BLOCKS);
        this.setTranslationKey("vase_" + type);
        this.setHardness(2);
        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, VaseVariant.DAMAGED));
    }

    @SuppressWarnings("deprecation")
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    @Override
    public IProperty[] getIgnoredProperties() {
        return new IProperty[]{VARIANT};
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(VARIANT).ordinal();
    }

    @Override
    public void onBlockPlacedBy(World world, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        EnumFacing rotation = placer.getHorizontalFacing().rotateY();
        TileEntity entity = world.getTileEntity(pos);
        if (entity instanceof TileEntityVase) {
            TileEntityVase vase = (TileEntityVase) entity;
            vase.setVaseRotation(rotation);
        }
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(VARIANT, VaseVariant.get(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(VARIANT).ordinal();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, VARIANT);
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public ItemBlock getItemBlock(Block block) {
        return new VaseItemBlock(block);
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @Override
    @SuppressWarnings("deprecation")
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @Override
    public abstract TileEntity createNewTileEntity(World world, int meta);

    @Override
    public int[] getUsedSubtypes() {
        int[] usedSubtypes = new int[VaseVariant.values().length];
        for (int i = 0; i < usedSubtypes.length; i++) {
            usedSubtypes[i] = i;
        }
        return usedSubtypes;
    }

    @Override
    public String getResource(ResourceLocation name, int metadata) {
        return name.getPath() + "_" + metadata;
    }

    public class VaseItemBlock extends ItemBlock {
        private VaseItemBlock(Block block) {
            super(block);
            this.setHasSubtypes(true);
        }

        @Override
        public String getTranslationKey(ItemStack stack) {
            return this.getTranslationKey() + "." + VaseVariant.get(stack.getItemDamage()).getName();
        }

        @Override
        public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
            if (this.isInCreativeTab(tab)) {
                for (VaseVariant variant : VaseVariant.values()) {
                    items.add(new ItemStack(this, 1, variant.ordinal()));
                }
            }
        }

        @Override
        public int getMetadata(int damage) {
            return damage;
        }
    }
}
