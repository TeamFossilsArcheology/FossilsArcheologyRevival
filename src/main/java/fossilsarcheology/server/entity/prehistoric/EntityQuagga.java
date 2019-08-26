package fossilsarcheology.server.entity.prehistoric;


import fossilsarcheology.Revival;
import fossilsarcheology.client.sound.FASoundRegistry;
import fossilsarcheology.server.item.FAItemRegistry;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.AbstractChestHorse;
import net.minecraft.entity.passive.EntityAnimal;
import net.minecraft.entity.passive.HorseArmorType;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import net.minecraft.world.storage.loot.LootTableList;
import net.minecraftforge.fml.common.FMLCommonHandler;

import javax.annotation.Nullable;

public class EntityQuagga extends AbstractChestHorse {

    public static final ResourceLocation LOOT = LootTableList.register(new ResourceLocation("fossil", "prehistoric/quagga"));

    public EntityQuagga(World par1World) {
        super(par1World);
    }

    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue((double) this.getModifiedMaxHealth());
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(this.getModifiedMovementSpeed());
        this.getEntityAttribute(JUMP_STRENGTH).setBaseValue(this.getModifiedJumpStrength());
    }

    protected boolean canDespawn() {
        return false;
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return LOOT;
    }

    protected SoundEvent getAmbientSound() {
        super.getAmbientSound();
        return FASoundRegistry.QUAGGA_LIVING;
    }

    protected SoundEvent getDeathSound() {
        super.getDeathSound();
        return FASoundRegistry.QUAGGA_DEATH;
    }

    protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
        super.getHurtSound(damageSourceIn);
        return FASoundRegistry.QUAGGA_HURT;
    }

    public boolean canMateWith(EntityAnimal otherAnimal) {
        if (otherAnimal == this) {
            return false;
        } else if (!(otherAnimal instanceof EntityQuagga)) {
            return false;
        } else {
            return this.canMate() && ((EntityQuagga) otherAnimal).canMate();
        }
    }

    public boolean processInteract(EntityPlayer player, EnumHand hand) {
        ItemStack itemstack = player.getHeldItem(hand);
        boolean flag = !itemstack.isEmpty();
        if (itemstack != null && FMLCommonHandler.instance().getSide().isClient() && itemstack.getItem() == FAItemRegistry.DINOPEDIA) {
            Revival.PEDIA_OBJECT = this;
            player.openGui(Revival.INSTANCE, 6, this.world, (int) this.posX, (int) this.posY, (int) this.posZ);
            return true;
        }
        if (flag && (itemstack.getItem() == Items.SPAWN_EGG || itemstack.getItem() == Item.getItemFromBlock(Blocks.CHEST))) {
            return super.processInteract(player, hand);
        } else {
            if (!this.isChild()) {
                if (this.isTame() && player.isSneaking()) {
                    this.openGUI(player);
                    return true;
                }

                if (this.isBeingRidden()) {
                    return super.processInteract(player, hand);
                }
            }

            if (flag) {
                if (this.handleEating(player, itemstack)) {
                    if (!player.capabilities.isCreativeMode) {
                        itemstack.shrink(1);
                    }

                    return true;
                }

                if (itemstack.interactWithEntity(player, this, hand)) {
                    return true;
                }

                if (!this.isTame()) {
                    this.makeMad();
                    return true;
                }

                boolean flag1 = HorseArmorType.getByItemStack(itemstack) != HorseArmorType.NONE;
                boolean flag2 = !this.isChild() && !this.isHorseSaddled() && itemstack.getItem() == Items.SADDLE;

                if (flag1 || flag2) {
                    this.openGUI(player);
                    return true;
                }
            }

            if (this.isChild()) {
                return super.processInteract(player, hand);
            } else {
                this.mountTo(player);
                return super.processInteract(player, hand);
            }
        }
    }

    protected int getInventorySize() {
        return super.getInventorySize();
    }

    public EntityAgeable createChild(EntityAgeable ageable) {
        EntityQuagga abstracthorse = new EntityQuagga(this.world);
        this.setOffspringAttributes(ageable, abstracthorse);
        return abstracthorse;
    }

}