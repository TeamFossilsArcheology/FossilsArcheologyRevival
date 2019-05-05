package fossilsarcheology.server.block;

import fossilsarcheology.server.api.BlockEntity;
import fossilsarcheology.server.api.IgnoreRenderProperty;
import fossilsarcheology.server.block.entity.TileEntityFigurine;
import fossilsarcheology.server.tab.FATabRegistry;
import net.minecraft.block.Block;
import net.minecraft.block.BlockContainer;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockFaceShape;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.Random;

public class FigurineBlock extends BlockContainer implements IBlockItem, BlockEntity, IgnoreRenderProperty {
    public static final PropertyEnum<FigurineBlock.EnumType> VARIANT = PropertyEnum.create("variant", FigurineBlock.EnumType.class);
    public static final AxisAlignedBB AABB = new AxisAlignedBB(0.25f, 0f, 0.25f, 0.75f, 0.5f, 0.75f);

    protected FigurineBlock() {
        super(Material.CIRCUITS);
        this.setCreativeTab(FATabRegistry.BLOCKS);
        this.setTranslationKey("figurine");
        this.setSoundType(SoundType.STONE);
        this.setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, EnumType.FIGURINE_STEVE_PRISTINE));
    }

    @SuppressWarnings("deprecation")
    public BlockFaceShape getBlockFaceShape(IBlockAccess worldIn, IBlockState state, BlockPos pos, EnumFacing face) {
        return BlockFaceShape.UNDEFINED;
    }

    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return Item.getItemFromBlock(FABlockRegistry.FIGURINE);
    }

    @Override
    public IProperty[] getIgnoredProperties() {
        return new IProperty[]{VARIANT};
    }

    @SuppressWarnings("deprecation")
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos) {
        return AABB;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isFullCube(IBlockState state) {
        return false;
    }

    @Override
    public int damageDropped(IBlockState state) {
        return state.getValue(VARIANT).getMetadata();
    }

    @Override
    public void onBlockPlacedBy(World worldIn, BlockPos pos, IBlockState state, EntityLivingBase placer, ItemStack stack) {
        int l = MathHelper.floor((double) (placer.rotationYaw * 4.0F / 360.0F) + 1.5D) & 3;
        worldIn.setBlockState(pos, state.withProperty(VARIANT, EnumType.byMetadata(stack.getMetadata())));
        TileEntity tileentity = worldIn.getTileEntity(pos);
        ((TileEntityFigurine) tileentity).setFigurineType(stack.getItemDamage());
        ((TileEntityFigurine) tileentity).setFigurineRotation(l);
        super.onBlockPlacedBy(worldIn, pos, state, placer, stack);
    }

    @Override
    public void getSubBlocks(CreativeTabs tab, NonNullList<ItemStack> items) {
        for (FigurineBlock.EnumType types : FigurineBlock.EnumType.values()) {
            items.add(new ItemStack(this, 1, types.getMetadata()));
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer() {
        return BlockRenderLayer.CUTOUT;
    }

    @SuppressWarnings("deprecation")
    @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.ENTITYBLOCK_ANIMATED;
    }

    @SuppressWarnings("deprecation")
    @Override
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Override
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState().withProperty(VARIANT, FigurineBlock.EnumType.byMetadata(meta));
    }

    @Override
    public int getMetaFromState(IBlockState state) {
        return state.getValue(VARIANT).getMetadata();
    }

    @Override
    protected BlockStateContainer createBlockState() {
        return new BlockStateContainer(this, VARIANT);
    }

    @Override
    public TileEntity createNewTileEntity(World worldIn, int meta) {
        return new TileEntityFigurine();
    }

    @Override
    public Class<? extends TileEntity> getEntity() {
        return TileEntityFigurine.class;
    }

    public boolean canConnectRedstone(IBlockState state, IBlockAccess world, BlockPos pos, @Nullable EnumFacing side){
        return true;
    }

    @Override
    public ItemBlock getItemBlock(Block block) {
        return new FigurineBlockItem(block);
    }

    public enum EnumType implements IStringSerializable {
        FIGURINE_STEVE_PRISTINE(0, "figurine_steve_pristine"),
        FIGURINE_SKELETON_PRISTINE(1, "figurine_skeleton_pristine"),
        FIGURINE_ZOMBIE_PRISTINE(2, "figurine_zombie_pristine"),
        FIGURINE_PIGZOMBIE_PRISTINE(3, "figurine_pigzombie_pristine"),
        FIGURINE_ENDERMAN_PRISTINE(4, "figurine_enderman_pristine"),

        FIGURINE_STEVE_DAMAGED(5, "figurine_steve_damaged"),
        FIGURINE_SKELETON_DAMAGED(6, "figurine_skeleton_damaged"),
        FIGURINE_ZOMBIE_DAMAGED(7, "figurine_zombie_damaged"),
        FIGURINE_PIGZOMBIE_DAMAGED(8, "figurine_pigzombie_damaged"),
        FIGURINE_ENDERMAN_DAMAGED(9, "figurine_enderman_damaged"),

        FIGURINE_STEVE_BROKEN(10, "figurine_steve_broken"),
        FIGURINE_SKELETON_BROKEN(11, "figurine_skeleton_broken"),
        FIGURINE_ZOMBIE_BROKEN(12, "figurine_zombie_broken"),
        FIGURINE_PIGZOMBIE_BROKEN(13, "figurine_pigzombie_broken"),
        FIGURINE_ENDERMAN_BROKEN(14, "figurine_enderman_broken"),

        FIGURINE_MYSTERIOUS(15, "figurine_mysterious");

        private static final FigurineBlock.EnumType[] META_LOOKUP = new FigurineBlock.EnumType[values().length];

        static {
            for (FigurineBlock.EnumType type : values()) {
                META_LOOKUP[type.getMetadata()] = type;
            }
        }

        private final int meta;
        private final String name;
        private final String unlocalizedName;

        EnumType(int metaIn, String nameIn) {
            this(metaIn, nameIn, nameIn);
        }

        EnumType(int metaIn, String nameIn, String unlocalizedNameIn) {
            this.meta = metaIn;
            this.name = nameIn;
            this.unlocalizedName = unlocalizedNameIn;
        }

        public static FigurineBlock.EnumType byMetadata(int meta) {
            if (meta < 0 || meta >= META_LOOKUP.length) {
                meta = 0;
            }

            return META_LOOKUP[meta];
        }

        public int getMetadata() {
            return this.meta;
        }

        public String toString() {
            return this.name;
        }

        @Override
        public String getName() {
            return this.name;
        }

        public String getTranslationKey() {
            return this.unlocalizedName;
        }
    }

    class FigurineBlockItem extends ItemBlock {
        public FigurineBlockItem(Block block) {
            super(block);
            this.setHasSubtypes(true);
        }

        @Override
        public void getSubItems(CreativeTabs tab, NonNullList<ItemStack> items) {
            if (this.isInCreativeTab(tab)) {
                for (int i = 0; i < 16; i++) {
                    items.add(new ItemStack(this, 1, i));
                }
            }
        }

        @Override
        public String getTranslationKey(ItemStack itemstack) {
            return getTranslationKey() + "." + EnumType.byMetadata(itemstack.getItemDamage()).getName();
        }
    }
}
