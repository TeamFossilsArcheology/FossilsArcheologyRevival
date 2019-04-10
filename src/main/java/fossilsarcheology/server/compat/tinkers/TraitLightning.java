package fossilsarcheology.server.compat.tinkers;

import fossilsarcheology.server.entity.monster.EntityFriendlyPigZombie;
import fossilsarcheology.server.entity.utility.EntityAncientLightning;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.monster.EntityPigZombie;
import net.minecraft.entity.passive.EntityPig;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import slimeknights.tconstruct.library.traits.AbstractTrait;

import java.util.Random;

public class TraitLightning extends AbstractTrait {

    public TraitLightning() {
        super("lightning", 0XB81200);
    }

    @Override
    public void onHit(ItemStack tool, EntityLivingBase player, EntityLivingBase target, float damage, boolean isCritical) {
        if (target instanceof EntityPig || target instanceof EntityPigZombie) {
            EntityFriendlyPigZombie fpz = new EntityFriendlyPigZombie(target.world);
            fpz.setLocationAndAngles(target.posX, target.posY, target.posZ, target.rotationYaw, target.rotationPitch);
            fpz.setItemStackToSlot(EntityEquipmentSlot.MAINHAND, new ItemStack(Items.GOLDEN_SWORD));
            if (!target.world.isRemote) {
                target.world.spawnEntity(fpz);
            }
            fpz.setTamedBy((EntityPlayer) player);
            fpz.sendMessageToOwner("pigman.summon");
            target.setDead();
            target.world.spawnEntity(new EntityLightningBolt(target.world, target.posX, target.posY, target.posZ, true));
        } else if (target != null && (new Random()).nextInt(8) == 0) {
            target.world.addWeatherEffect(new EntityAncientLightning(target.world, target.posX, target.posY, target.posZ));
        }
    }
}
