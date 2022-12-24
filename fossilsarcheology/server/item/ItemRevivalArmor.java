package fossilsarcheology.server.item;

import com.github.alexthe666.citadel.server.item.CustomArmorMaterial;
import com.google.common.collect.Multimap;
import fossilsarcheology.Revival;
import net.minecraft.client.renderer.entity.model.BipedModel;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.common.ForgeMod;
import javax.annotation.Nullable;
import java.util.List;

public class ItemRevivalArmor  extends ArmorItem {

    public ItemRevivalArmor(CustomArmorMaterial armorMaterial, EquipmentSlotType slot) {
        super(armorMaterial, slot, new Item.Properties().group(Revival.TAB_ITEMS));
    }

    @Override
    public void addInformation(ItemStack stack, @Nullable World worldIn, List<ITextComponent> tooltip, ITooltipFlag flagIn) {
        super.addInformation(stack, worldIn, tooltip, flagIn);
    }

    public Multimap<Attribute, AttributeModifier> getAttributeModifiers(EquipmentSlotType equipmentSlot) {
        return super.getAttributeModifiers(equipmentSlot);
    }

    @Nullable
    public String getArmorTexture(ItemStack stack, Entity entity, EquipmentSlotType slot, String type) {
        if (this.material == FAItemRegistry.ANCIENT_HELMET_MATERIAL) {
            return "fossil:textures/armor/ancient_helmet.png";
        }
        return super.getArmorTexture(stack, entity, slot, type);
    }

    @OnlyIn(Dist.CLIENT)
    @Nullable
    public <A extends BipedModel<?>> A getArmorModel(LivingEntity entity, ItemStack itemStack, EquipmentSlotType armorSlot, A _default) {
        if (this.material == FAItemRegistry.ANCIENT_HELMET_MATERIAL) {
            return (A) Revival.PROXY.getArmorModel(0, entity);
        } else {
            return super.getArmorModel(entity, itemStack, armorSlot, _default);
        }
    }
}
