package fossilsarcheology.server.block;

import fossilsarcheology.Revival;
import fossilsarcheology.server.api.DefaultRenderedItem;
import fossilsarcheology.server.dimension.AnuTeleporter;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.Item;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Random;

public class HomePortalBlock extends Block implements DefaultRenderedItem {
    public HomePortalBlock() {
        super(Material.PORTAL);
        this.setResistance(60000000.0F);
        this.setHardness(60000000.0F);
        this.setSoundType(SoundType.GLASS);
        this.setTranslationKey("home_portal");
    }

    @SuppressWarnings("deprecation")
    public boolean isOpaqueCube(IBlockState state) {
        return false;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getRenderLayer()
    {
        return BlockRenderLayer.TRANSLUCENT;
    }

    @SuppressWarnings("deprecation")
    public boolean isFullCube(IBlockState state)
    {
        return false;
    }

    @SuppressWarnings("deprecation")
    @Deprecated
    public void addCollisionBoxToList(IBlockState state, World worldIn, BlockPos pos, AxisAlignedBB entityBox, List<AxisAlignedBB> collidingBoxes, @Nullable Entity entityIn, boolean isActualState) {
    }

    @SuppressWarnings("deprecation")
    @SideOnly(Side.CLIENT)
    public boolean shouldSideBeRendered(IBlockState blockState, IBlockAccess blockAccess, BlockPos pos, EnumFacing side) {
        IBlockState iblockstate = blockAccess.getBlockState(pos.offset(side));
        Block block = iblockstate.getBlock();

        if (blockState != iblockstate) {
            return true;
        }
        if (block == this) {
            return false;
        }
        return super.shouldSideBeRendered(blockState, blockAccess, pos, side);
    }

    @Override
    public Item getItemDropped(IBlockState state, Random rand, int fortune) {
        return null;
    }

    @Override
    public void onEntityCollision(World world, BlockPos pos, IBlockState state, Entity entity) {
        if ((!entity.isBeingRidden()) && (entity.getPassengers().isEmpty()) && (entity instanceof EntityPlayerMP)) {
            EntityPlayerMP thePlayer = (EntityPlayerMP) entity;
            if (thePlayer.timeUntilPortal > 0) {
                thePlayer.timeUntilPortal = 10;
            } else if (thePlayer.dimension != 0) {
                thePlayer.timeUntilPortal = 10;
                thePlayer.server.getPlayerList().transferPlayerToDimension(thePlayer, Revival.CONFIG_OPTIONS.homePortalExitDimension, new AnuTeleporter(thePlayer.server.getWorld(Revival.CONFIG_OPTIONS.homePortalExitDimension)));
            }
        }
    }

    @Override
    @SideOnly(Side.CLIENT)
    public void randomDisplayTick(IBlockState stateIn, World world, BlockPos pos, Random rand) {
        super.randomDisplayTick(stateIn, world, pos, rand);
        if (rand.nextInt(100) == 0) {
            world.playSound((double) pos.getX() + 0.5D, (double) pos.getY() + 0.5D, (double) pos.getZ() + 0.5D, SoundEvents.BLOCK_PORTAL_AMBIENT, SoundCategory.BLOCKS, 1.5F, rand.nextFloat() * 0.4F + 0.8F, false);
        }
        int x = pos.getX();
        int y = pos.getY();
        int z = pos.getZ();
        for (int l = x - 2; l <= x + 2; ++l) {
            for (int i1 = z - 2; i1 <= z + 2; ++i1) {
                if (l > x - 2 && l < x + 2 && i1 == z - 1) {
                    i1 = z + 2;
                }
                if (rand.nextInt(16) == 0) {
                    for (int j1 = y; j1 <= y + 1; ++j1) {
                        if (!world.isAirBlock(new BlockPos((l - x) / 2 + x, j1, (i1 - z) / 2 + z))) {
                            break;
                        }
                        world.spawnParticle(EnumParticleTypes.PORTAL, x + 0.5D, y + 0.5D, z + 0.5D, (double) ((float) (l - x) + rand.nextFloat()) - 0.5D, (double) ((float) (j1 - y) - rand.nextFloat() - 1.0F), (double) ((float) (i1 - z) + rand.nextFloat()) - 0.5D);
                    }
                }
            }
        }
    }

}
