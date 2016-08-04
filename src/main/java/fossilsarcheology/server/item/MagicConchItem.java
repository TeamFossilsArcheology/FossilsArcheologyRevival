package fossilsarcheology.server.item;

import fossilsarcheology.Revival;
import fossilsarcheology.server.entity.EntityPrehistoric;
import fossilsarcheology.server.entity.mob.EntityPlesiosaurus;
import fossilsarcheology.server.enums.EnumOrderType;
import net.minecraft.client.renderer.texture.IIconRegister;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.AxisAlignedBB;
import net.minecraft.util.I18n;
import net.minecraft.world.World;

import java.util.List;

public class MagicConchItem extends Item {
    public MagicConchItem() {
        super();
        // this.setHasSubtypes(true);
        this.setMaxDamage(0);
        this.maxStackSize = 1;
    }

    /**
     * Gets an icon index based on an item's damage value
     */
    // public int getIconFromDamage(int var1)
    // {
    // return this.iconIndex;
    // }

    // public String getItemNameIS(ItemStack var1)
    // {
    // String var2 = "MagicConch";
    // String var3 = EnumOrderType.values()[var1.getItemDamage()].toString();
    // return "MagicConch" + var3;
    // }

    /**
     * Called whenever this item is equipped and the right mouse button is
     * pressed. Args: itemStack, world, entityPlayer
     */
    @Override
    public ItemStack onItemRightClick(ItemStack var1, World var2, EntityPlayer var3) {
        // String var4 = "Drum.";
        // String var5 = "Msg.";
        // String var6 = "Head";
        // String var7 = "Middle";
        // String var8 = "Tail";
        String var9 = I18n.translateToLocal("fossil.entity.Plesiosaur.name");// EntityDinosaur.GetNameByEnum(EnumDinoType.Plesiosaur,
        // true);
        String var10 = I18n.translateToLocal("mGCName.head");
        // String var11 = I18n.translateToLocal("Drum.Msg.Middle");
        // String var12 = I18n.translateToLocal("Drum.Msg.Tail");
        String var13 = "";
        List var14 = var2.getEntitiesWithinAABB(EntityPlesiosaurus.class, AxisAlignedBB.getBoundingBox(var3.posX, var3.posY, var3.posZ, var3.posX + 1.0D, var3.posY + 1.0D, var3.posZ + 1.0D).expand(30.0D, 4.0D, 30.0D));

        for (Object aVar14 : var14) {
            Entity var16 = (Entity) aVar14;
            EntityPrehistoric var17 = (EntityPrehistoric) var16;

            if (var17.isTamed()) {
                var17.setOrder(EnumOrderType.values()[var1.getItemDamage()]);
                var2.spawnParticle("note", var16.posX, var16.posY + 1.2D, var16.posZ, 0.0D, 0.0D, 0.0D);
            }
        }

        var13 = I18n.translateToLocal("order." + EnumOrderType.values()[var1.getItemDamage()].toString());
        // Revival.ShowMessage(var10 + var9 + var11 + " " + var13 + var12,
        // var3);
        if (!var3.worldObj.isRemote) {
            Revival.messagePlayer("Try asking again.", var3);
        }
        return var1;
    }

    @Override
    public void registerIcons(IIconRegister iconRegister) {
        itemIcon = iconRegister.registerIcon("fossil:Magic_Conch");
    }
}
