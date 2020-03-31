package fossilsarcheology.server.entity;

import fossilsarcheology.server.block.FABlockRegistry;
import fossilsarcheology.server.item.FAItemRegistry;
import fossilsarcheology.server.item.enchantment.FAEnchantmentRegistry;
import fossilsarcheology.server.world.village.VillageArcheologistHouseCreator;
import fossilsarcheology.server.world.village.VillageComponentArcheologistHouse;
import fossilsarcheology.server.world.village.VillageComponentPaleontologistHouse;
import fossilsarcheology.server.world.village.VillagePaleontologistHouseCreator;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentData;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.IMerchant;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemEnchantedBook;
import net.minecraft.item.ItemStack;
import net.minecraft.village.MerchantRecipe;
import net.minecraft.village.MerchantRecipeList;
import net.minecraft.world.gen.structure.MapGenStructureIO;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

import java.util.Random;

public class FAVillagerRegistry {
    public static final VillagerRegistry.VillagerProfession ARCHEOLOGIST_PROFESSION = new VillagerRegistry.VillagerProfession("fossil:archeologist", "fossil:textures/model/archaeologist.png", "fossil:textures/model/archaeologist_zombie.png");
    public static final VillagerRegistry.VillagerProfession PALAEONTOLOGIST_PROFESSION = new VillagerRegistry.VillagerProfession("fossil:palaeontologist", "fossil:textures/model/palaeontologist.png", "fossil:textures/model/palaeontologist_zombie.png");

    public static void register() {
        VillagerRegistry.VillagerCareer archeoCareer = new VillagerRegistry.VillagerCareer(ARCHEOLOGIST_PROFESSION, "archeologist");
        archeoCareer.addTrade(1, new EntityVillager.ListItemForEmeralds(FAItemRegistry.RELIC_SCRAP, new EntityVillager.PriceInfo(1, 6)));
        archeoCareer.addTrade(1, new EntityVillager.EmeraldForItems(FAItemRegistry.POTTERY_SHARD, new EntityVillager.PriceInfo(1, 3)));
        archeoCareer.addTrade(1, new EntityVillager.ListItemForEmeralds(Item.getItemFromBlock(FABlockRegistry.WORKTABLE_IDLE), new EntityVillager.PriceInfo(1, 3)));

        archeoCareer.addTrade(2, new EntityVillager.ListItemForEmeralds(FAItemRegistry.STONE_TABLET, new EntityVillager.PriceInfo(1, 8)));
        archeoCareer.addTrade(2, new EntityVillager.ListItemForEmeralds(Items.BOOK, new EntityVillager.PriceInfo(2, 4)));
        archeoCareer.addTrade(3, new EntityVillager.ListItemForEmeralds(FAItemRegistry.WOODEN_JAVELIN, new EntityVillager.PriceInfo(1, 1)));

        archeoCareer.addTrade(3, new EntityVillager.ListItemForEmeralds(FAItemRegistry.BROKEN_SWORD, new EntityVillager.PriceInfo(1, 8)));
        archeoCareer.addTrade(3, new EntityVillager.ListItemForEmeralds(FAItemRegistry.BROKEN_HELMET, new EntityVillager.PriceInfo(1, 8)));
        archeoCareer.addTrade(3, new EntityVillager.ListItemForEmeralds(FAItemRegistry.STONE_JAVELIN, new EntityVillager.PriceInfo(1, 2)));
        archeoCareer.addTrade(3, new EntityVillager.ListItemForEmeralds(Item.getItemFromBlock(FABlockRegistry.VOLUTE_VASE), new EntityVillager.PriceInfo(1, 2)));
        archeoCareer.addTrade(3, new EntityVillager.ListItemForEmeralds(Item.getItemFromBlock(FABlockRegistry.AMPHORA_VASE), new EntityVillager.PriceInfo(1, 3)));

        archeoCareer.addTrade(4, new EntityVillager.ListItemForEmeralds(Item.getItemFromBlock(FABlockRegistry.KYLIX_VASE), new EntityVillager.PriceInfo(1, 3)));
        archeoCareer.addTrade(4, new EntityVillager.ListItemForEmeralds(Item.getItemFromBlock(FABlockRegistry.FIGURINE), new EntityVillager.PriceInfo(1, 6)));
        archeoCareer.addTrade(4, new EntityVillager.ListItemForEmeralds(Item.getItemFromBlock(FABlockRegistry.ANCIENT_GLASS), new EntityVillager.PriceInfo(2, 6)));
        archeoCareer.addTrade(4, new EntityVillager.ListItemForEmeralds(Item.getItemFromBlock(FABlockRegistry.ANCIENT_WOOD), new EntityVillager.PriceInfo(2, 20)));

        archeoCareer.addTrade(5, new ListEnchantedBookForEmeralds(FAEnchantmentRegistry.ENCHANTMENT_ARCHEOLOGY, new EntityVillager.PriceInfo(1, 10)));
        archeoCareer.addTrade(5, new EntityVillager.ItemAndEmeraldToItem(FAItemRegistry.BROKEN_HELMET, new EntityVillager.PriceInfo(1, 1), FAItemRegistry.ANCIENT_HELMET, new EntityVillager.PriceInfo(1, 1)));
        archeoCareer.addTrade(5, new EntityVillager.ItemAndEmeraldToItem(FAItemRegistry.BROKEN_SWORD, new EntityVillager.PriceInfo(1, 1), FAItemRegistry.ANCIENT_SWORD, new EntityVillager.PriceInfo(1, 1)));
        archeoCareer.addTrade(5, new EntityVillager.ListItemForEmeralds(Item.getItemFromBlock(FABlockRegistry.ANCIENT_STONE), new EntityVillager.PriceInfo(2, 20)));
        archeoCareer.addTrade(5, new EntityVillager.ListItemForEmeralds(new ItemStack(FABlockRegistry.FIGURINE, 1, 15), new EntityVillager.PriceInfo(1, 19)));

        VillagerRegistry.VillagerCareer paleoCareer = new VillagerRegistry.VillagerCareer(PALAEONTOLOGIST_PROFESSION, "palaeontologist");
        paleoCareer.addTrade(1, new EntityVillager.ListItemForEmeralds(FAItemRegistry.BIOFOSSIL, new EntityVillager.PriceInfo(1, 2)));
        paleoCareer.addTrade(1, new EntityVillager.EmeraldForItems(Item.getItemFromBlock(FABlockRegistry.SKULL_BLOCK), new EntityVillager.PriceInfo(1, 3)));
        paleoCareer.addTrade(1, new EntityVillager.EmeraldForItems(Items.BONE, new EntityVillager.PriceInfo(1, 17)));

        paleoCareer.addTrade(2, new EntityVillager.ListItemForEmeralds(FAItemRegistry.TARDROP, new EntityVillager.PriceInfo(1, 2)));
        paleoCareer.addTrade(2, new EntityVillager.ListItemForEmeralds(FAItemRegistry.PLANT_FOSSIL, new EntityVillager.PriceInfo(1, 3)));

        paleoCareer.addTrade(3, new EntityVillager.ListItemForEmeralds(FAItemRegistry.ICED_MEAT, new EntityVillager.PriceInfo(1, 6)));
        paleoCareer.addTrade(3, new EntityVillager.ListItemForEmeralds(FAItemRegistry.TAR_FOSSIL, new EntityVillager.PriceInfo(1, 4)));

        paleoCareer.addTrade(4, new EntityVillager.ListItemForEmeralds(FAItemRegistry.CHICKEN_ESSENCE, new EntityVillager.PriceInfo(1, 2)));
        paleoCareer.addTrade(4, new EntityVillager.ListItemForEmeralds(FAItemRegistry.FAILURESAURUS_FLESH, new EntityVillager.PriceInfo(1, 10)));
        paleoCareer.addTrade(4, new EntityVillager.ListItemForEmeralds(Item.getItemFromBlock(FABlockRegistry.PERMAFROST), new EntityVillager.PriceInfo(1, 3)));

        paleoCareer.addTrade(5, new EntityVillager.ListItemForEmeralds(FAItemRegistry.SKULL_HELMET, new EntityVillager.PriceInfo(1, 5)));
        paleoCareer.addTrade(5, new EntityVillager.ListItemForEmeralds(FAItemRegistry.RIBCAGE_CHESTPLATE, new EntityVillager.PriceInfo(1, 6)));
        paleoCareer.addTrade(5, new EntityVillager.ListItemForEmeralds(FAItemRegistry.SHIN_LEGGINGS, new EntityVillager.PriceInfo(1, 4)));
        paleoCareer.addTrade(5, new EntityVillager.ListItemForEmeralds(FAItemRegistry.FEET_BOOTS, new EntityVillager.PriceInfo(1, 3)));
        paleoCareer.addTrade(5, new ListEnchantedBookForEmeralds(FAEnchantmentRegistry.ENCHANTMENT_PALEONTOLOGY, new EntityVillager.PriceInfo(1, 10)));

        MapGenStructureIO.registerStructureComponent(VillageComponentArcheologistHouse.class, "archeologist_house");
        VillagerRegistry.instance().registerVillageCreationHandler(new VillageArcheologistHouseCreator());

        MapGenStructureIO.registerStructureComponent(VillageComponentPaleontologistHouse.class, "paleontologist_house");
        VillagerRegistry.instance().registerVillageCreationHandler(new VillagePaleontologistHouseCreator());
    }

    public static class ListEnchantedBookForEmeralds implements EntityVillager.ITradeList {
        public ItemStack enchantedItemStack;
        public EntityVillager.PriceInfo priceInfo;
        private Enchantment enchantment;

        public ListEnchantedBookForEmeralds(Enchantment enchant, EntityVillager.PriceInfo p_i45814_2_) {
            this.enchantedItemStack = new ItemStack(Items.ENCHANTED_BOOK);
            this.priceInfo = p_i45814_2_;
            this.enchantment = enchant;
        }

        public void addMerchantRecipe(IMerchant merchant, MerchantRecipeList recipeList, Random random) {
            int i = 1;

            if (this.priceInfo != null) {
                i = this.priceInfo.getPrice(random);
            }

            ItemStack itemstack = new ItemStack(Items.EMERALD, i, 0);
            ItemStack soldBook = this.enchantedItemStack.copy();
            ItemEnchantedBook.addEnchantment(soldBook, new EnchantmentData(enchantment, 1 + random.nextInt(2)));
            recipeList.add(new MerchantRecipe(itemstack, soldBook));
        }
    }
}
