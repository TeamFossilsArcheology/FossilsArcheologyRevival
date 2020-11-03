package fossilsarcheology.server.item;

import fossilsarcheology.server.api.DefaultRenderedItem;
import fossilsarcheology.server.entity.monster.EntityFriendlyPigZombie;
import fossilsarcheology.server.entity.utility.EntityAncientLightning;
import fossilsarcheology.server.tab.FATabRegistry;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemSword;

import java.util.Random;

public class AncientSwordItem extends ItemSword implements DefaultRenderedItem {

    public AncientSwordItem() {
        super(ToolMaterial.IRON);
        this.maxStackSize = 1;
        this.setMaxDamage(250);
        this.setCreativeTab(FATabRegistry.ITEMS);
        this.setTranslationKey("ancient_sword");
    }

    @Override
    public boolean hitEntity(ItemStack heldItem, EntityLivingBase targetEntity, EntityLivingBase player) {
        if (player instanceof EntityPlayer) {
            if (this.checkHelmet((EntityPlayer) player)) {
                if (targetEntity instanceof EntityPig || targetEntity instanceof EntityPigZombie) {
                    EntityFriendlyPigZombie fpz = new EntityFriendlyPigZombie(targetEntity.world);
                    fpz.setLocationAndAngles(targetEntity.posX, targetEntity.posY, targetEntity.posZ, targetEntity.rotationYaw, targetEntity.rotationPitch);
                    fpz.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));
                    if (!targetEntity.world.isRemote) {
                        targetEntity.world.spawnEntity(fpz);
                    }
                    fpz.setTamedBy((EntityPlayer) player);
                    if(targetEntity instanceof EntityAgeable){
                        fpz.setGrowingAge(((EntityAgeable)targetEntity).getGrowingAge());
                    }
                    fpz.sendMessageToOwner("pigman.summon");
                    targetEntity.setDead();
                    targetEntity.world.spawnEntity(new EntityLightningBolt(targetEntity.world, targetEntity.posX, targetEntity.posY, targetEntity.posZ, true));
                } else if (targetEntity != null && (new Random()).nextInt(5) == 0) {
                    targetEntity.world.addWeatherEffect(new EntityAncientLightning(targetEntity.world, targetEntity.posX, targetEntity.posY, targetEntity.posZ));
                }
            }
            heldItem.damageItem(1, player);
        }
        return true;
    }

    private boolean checkHelmet(EntityPlayer player) {
        return player.getItemStackFromSlot(EntityEquipmentSlot.HEAD).getItem() == FAItemRegistry.ANCIENT_HELMET;
    }
}
