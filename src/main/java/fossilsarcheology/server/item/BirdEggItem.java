package fossilsarcheology.server.item;

import fossilsarcheology.server.api.DefaultRenderedItem;
import fossilsarcheology.server.entity.prehistoric.PrehistoricEntityType;
import fossilsarcheology.server.entity.projectile.EntityBirdEgg;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;

public class BirdEggItem extends PrehistoricEntityItem implements DefaultRenderedItem {
    private final boolean cultivated;

    public BirdEggItem(PrehistoricEntityType type, boolean cultivated) {
        super(cultivated ? "egg_cultivated" : "egg", type);
        this.cultivated = cultivated;
        this.maxStackSize = 16;
    }

    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
        if (!worldIn.isRemote && entityIn instanceof EntityPlayerMP) {
            DinoEggItem.EGG_TRIGGER.trigger((EntityPlayerMP) entityIn);
        }
    }

    @Override
    public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
        ItemStack stack = player.getHeldItem(hand);
        if (!player.capabilities.isCreativeMode) {
            stack.shrink(1);
        }
        EntityBirdEgg egg = new EntityBirdEgg(world, player, this.cultivated);
        player.playSound(SoundEvents.ENTITY_ARROW_SHOOT, 0.5F, 0.4F / (itemRand.nextFloat() * 0.4F + 0.8F));
        egg.shoot(player, player.rotationPitch, player.rotationYaw, 0.0F, 1.5F, 1.0F);
        if (!world.isRemote) {
            world.spawnEntity(egg);
        }
        egg.setEnumType(this.type);
        return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
    }
}
