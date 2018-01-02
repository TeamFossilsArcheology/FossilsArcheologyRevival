package fossilsarcheology.server.item;

import fossilsarcheology.server.api.DefaultRenderedItem;
import fossilsarcheology.server.tab.FATabRegistry;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;

public class BioFossilItem extends Item implements DefaultRenderedItem {
    private boolean isTarFossil;

    public BioFossilItem(boolean isTarFossil) {
        this.maxStackSize = 64;
        this.setMaxDamage(0);
        this.isTarFossil = isTarFossil;
        this.setUnlocalizedName(isTarFossil ? "tar_fossil" : "biofossil");
        this.setCreativeTab(FATabRegistry.ITEMS);
    }

    public boolean tryPlaceIntoWorld(ItemStack stack, EntityPlayer player, World world, BlockPos pos) {
        if (world.isRemote) {
            return true;
        } else {
            /*Class<EntityPrehistoric> clazz = EnumPrehistoric.getRandomBioFossil(this.isTarFossil).getDinoClass(); TODO
            EntityPrehistoric entity;
            try {
                entity = clazz.getConstructor(new Class[]{World.class}).newInstance(world);
            } catch (Throwable var14) {
                var14.printStackTrace();
                return false;
            }
            entity.setLocationAndAngles((double) var4, (double) (var5 + 1), (double) var6, world.rand.nextFloat() * 360.0F, 0.0F);
            entity.faceEntity(player, 360.0F, 360.0F);
            entity.setSkeleton(true);
            if (world.checkNoEntityCollision(entity.boundingBox) && world.getCollidingBoundingBoxes(entity, entity.boundingBox).size() == 0) {
                world.spawnEntityInWorld(entity);
                --stack.stackSize;
                return true;
            } else {
                return false;
            }*/
            return false;
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        RayTraceResult result = this.rayTrace(world, player, true);
        ItemStack stack = player.getHeldItem(hand);
        if (result != null && result.typeOfHit == RayTraceResult.Type.BLOCK) {
            BlockPos pos = result.getBlockPos();
            if (world.getBlockState(pos).isSideSolid(world, pos, result.sideHit)) {
                this.tryPlaceIntoWorld(stack, player, world, pos.offset(result.sideHit));
            }
        }
        return ActionResult.newResult(EnumActionResult.PASS, stack);
    }
}
