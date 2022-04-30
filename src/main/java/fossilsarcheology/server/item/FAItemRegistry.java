package fossilsarcheology.server.item;

import com.github.alexthe666.citadel.server.item.CustomArmorMaterial;
import com.github.alexthe666.citadel.server.item.CustomToolMaterial;
import fossilsarcheology.Revival;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.BannerPatternItem;
import net.minecraft.item.Item;
import net.minecraft.util.SoundEvents;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.lang.reflect.Field;

@Mod.EventBusSubscriber(modid = Revival.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FAItemRegistry {
    public static CustomArmorMaterial ANCIENT_HELMET_MATERIAL = new FAArmorMaterial("ancient_helmet", 18, new int[]{4, 4, 4, 4}, 10, SoundEvents.ITEM_ARMOR_EQUIP_TURTLE, 1);
    public static CustomToolMaterial ANCIENT_SWORD_MATERIAL = new CustomToolMaterial("ancient_sword", 2, 300, 2.0F, 6.5F, 10);

    public static final ItemBioFossil BIO_FOSSIL = new ItemBioFossil(false);
    public static final ItemBioFossil TAR_FOSSIL = new ItemBioFossil(true);
    public static final Item RELIC_SCRAP = new Item(new Item.Properties().group(Revival.TAB_ITEMS)).setRegistryName("fossil:relic_scrap");
    public static final Item BROKEN_SWORD = new Item(new Item.Properties().group(Revival.TAB_ITEMS)).setRegistryName("fossil:broken_sword");
    public static final Item BROKEN_HELMET = new Item(new Item.Properties().group(Revival.TAB_ITEMS)).setRegistryName("fossil:broken_helmet");
    public static final Item ANCIENT_SWORD = new ItemAncientSword();
    public static final Item ANCIENT_HELMET = new ItemRevivalArmor(ANCIENT_HELMET_MATERIAL, EquipmentSlotType.HEAD).setRegistryName("fossil:ancient_helmet");
    public static final Item DINOPEDIA = new Item(new Item.Properties().group(Revival.TAB_ITEMS).maxStackSize(1)).setRegistryName("fossil:dinopedia");
    public static final Item SCARAB_GEM = new Item(new Item.Properties().group(Revival.TAB_ITEMS)).setRegistryName("fossil:scarab_gem");
    public static final Item AQUATIC_SCARAB_GEM = new Item(new Item.Properties().group(Revival.TAB_ITEMS)).setRegistryName("fossil:aquatic_scarab_gem");
    public static final Item CHICKEN_ESSENCE = new Item(new Item.Properties().group(Revival.TAB_ITEMS)).setRegistryName("fossil:chicken_essence");
    public static final Item STUNTED_ESSENCE = new Item(new Item.Properties().group(Revival.TAB_ITEMS)).setRegistryName("fossil:stunted_essence");
    public static final Item WHIP = new Item(new Item.Properties().group(Revival.TAB_ITEMS).maxDamage(256)).setRegistryName("fossil:whip");

    @SubscribeEvent
    public static void registerItem(RegistryEvent.Register<Item> event) {
        try {
            for (Field f : FAItemRegistry.class.getDeclaredFields()) {
                Object obj = f.get(null);
                if (obj instanceof Item && ((Item) obj).getRegistryName() != null) {
                    event.getRegistry().register((Item) obj);
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
