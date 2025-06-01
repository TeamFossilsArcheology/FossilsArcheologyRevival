package com.github.teamfossilsarcheology.fossil.villager;

import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import com.github.teamfossilsarcheology.fossil.enchantment.ModEnchantments;
import com.github.teamfossilsarcheology.fossil.item.ModItems;
import net.minecraft.util.Mth;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.EnchantedBookItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import net.minecraft.world.item.trading.MerchantOffer;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;

public class ModTrades {

    public static List<VillagerTrades.ItemListing> getArcheoList(int level) {
        List<VillagerTrades.ItemListing> list = new ArrayList<>();
        switch (level) {
            case 1 -> {
                list.add(new ItemsForEmeralds(ModItems.RELIC_SCRAP.get(), 3, 1, 8, 2));
                list.add(new EmeraldForItems(ModItems.POTTERY_SHARD.get(), 3, 8, 6));
                list.add(new ItemsForEmeralds(ModBlocks.WORKTABLE.get(), 1, 1, 4, 1));
            }
            case 2 -> {
                list.add(new ItemsForEmeralds(ModItems.STONE_TABLET.get(), 4, 1, 6, 4));
                list.add(new ItemsForEmeralds(Items.BOOK, 1, 1, 4));
                list.add(new ItemsForEmeralds(ModItems.WOODEN_JAVELIN.get(), 1, 1, 1));
            }
            case 3 -> {
                list.add(new ItemsForEmeralds(ModItems.STONE_JAVELIN.get(), 2, 1, 2));
                list.add(new ItemsForEmeralds(ModItems.BROKEN_SWORD.get(), 6, 1, 2, 12));
                list.add(new ItemsForEmeralds(ModItems.BROKEN_HELMET.get(), 6, 1, 2, 12));
                list.add(new ItemsForEmeralds(ModBlocks.AMPHORA_VASE_DAMAGED.get(), 3, 1, 3, 4));
                list.add(new ItemsForEmeralds(ModBlocks.VOLUTE_VASE_DAMAGED.get(), 3, 1, 3, 4));
            }
            case 4 -> {
                list.add(new ItemsForEmeralds(ModBlocks.KYLIX_VASE_DAMAGED.get(), 3, 1, 3, 6));
                list.add(new ItemsForEmeralds(ModBlocks.ANCIENT_GLASS.get(), 4, 4, 6, 12));
                list.add(new ItemsForEmeralds(ModBlocks.ANCIENT_WOOD_PLANKS.get(), 2, 4, 6, 6));
                list.add(new EmeraldForItems(ModBlocks.SKELETON_FIGURINE_DESTROYED.get(), 2, 1, 2, 8));
                list.add(new EmeraldForItems(ModBlocks.ZOMBIE_FIGURINE_DESTROYED.get(), 2, 1, 2, 8));
                list.add(new EmeraldForItems(ModBlocks.ENDERMAN_FIGURINE_DESTROYED.get(), 2, 1, 2, 8));
                list.add(new EmeraldForItems(ModBlocks.STEVE_FIGURINE_DESTROYED.get(), 2, 1, 2, 8));
                list.add(new EmeraldForItems(ModBlocks.PIGLIN_FIGURINE_DESTROYED.get(), 2, 1, 2, 8));
            }
            case 5 -> {
                list.add(new EnchantBookForEmeralds(ModEnchantments.ARCHEOLOGY.get(), 10));
                list.add(new ItemsAndEmeraldsToItems(ModItems.BROKEN_HELMET.get(), 1, ModItems.ANCIENT_HELMET.get(), 1, 2, 10));
                list.add(new ItemsAndEmeraldsToItems(ModItems.BROKEN_SWORD.get(), 1, ModItems.ANCIENT_SWORD.get(), 1, 2, 10));
                list.add(new ItemsForEmeralds(ModBlocks.ANCIENT_STONE.get(), 4, 4, 6, 12));
            }
        }
        return list;
    }

    public static List<VillagerTrades.ItemListing> getPaleoList(int level) {
        List<VillagerTrades.ItemListing> list = new ArrayList<>();
        switch (level) {
            case 1 -> {
                list.add(new ItemsForEmeralds(ModItems.BIO_FOSSIL.get(), 1, 1, 1));
                list.add(new EmeraldForItems(ModBlocks.SKULL_BLOCK.get(), 2, 12, 4));
                list.add(new EmeraldForItems(Items.BONE, 10, 12, 2));
            }
            case 2 -> {
                list.add(new ItemsForEmeralds(ModItems.TAR_DROP.get(), 2, 1, 2));
                list.add(new ItemsForEmeralds(ModItems.PlANT_FOSSIL.get(), 2, 1, 2));
                list.add(new EmeraldForItems(ModItems.BIO_GOO.get(), 3, 6, 6));
            }
            case 3 -> {
                list.add(new ItemsForEmeralds(ModItems.FROZEN_MEAT.get(), 3, 1, 2));
                list.add(new ItemsForEmeralds(ModItems.TAR_FOSSIL.get(), 3, 1, 3));
            }
            case 4 -> {
                list.add(new ItemsForEmeralds(ModItems.CHICKEN_ESSENCE.get(), 3, 1, 6));
                list.add(new ItemsForEmeralds(ModItems.FAILURESAURUS_FLESH.get(), 5, 2, 12));
                list.add(new ItemsForEmeralds(ModBlocks.PERMAFROST_BLOCK.get(), 2, 4, 6, 4));
                list.add(new ItemsForEmeralds(ModItems.SHALE_FOSSIL.get(), 3, 1, 6));
                list.add(new EmeraldForItems(ModItems.ELASMOTHERIUM_FUR.get(), 6, 6, 6));
                list.add(new EmeraldForItems(ModItems.MAMMOTH_FUR.get(), 6, 6, 6));
                list.add(new EmeraldForItems(ModItems.MAGIC_CONCH.get(), 2, 2, 8));
            }
            case 5 -> {
                list.add(new EnchantBookForEmeralds(ModEnchantments.PALEONTOLOGY.get(), 10));
                list.add(new ItemsForEmeralds(ModItems.BONE_HELMET.get(), 3, 1, 2));
                list.add(new ItemsForEmeralds(ModItems.BONE_CHESTPLATE.get(), 4, 1, 3));
                list.add(new ItemsForEmeralds(ModItems.BONE_LEGGINGS.get(), 3, 1, 2));
                list.add(new ItemsForEmeralds(ModItems.BONE_BOOTS.get(), 2, 1, 1));
            }
        }
        return list;
    }

    static class EmeraldForItems implements VillagerTrades.ItemListing {
        private final Item item;
        private final int cost;
        private final int emeraldCount;
        private final int maxUses;
        private final int villagerXp;
        private final float priceMultiplier;

        public EmeraldForItems(ItemLike itemLike, int cost, int maxUses, int villagerXp) {
            this(itemLike, 1, cost, maxUses, villagerXp);
        }

        public EmeraldForItems(ItemLike itemLike, int emeraldCount, int cost, int maxUses, int villagerXp) {
            this.item = itemLike.asItem();
            this.emeraldCount = emeraldCount;
            this.cost = cost;
            this.maxUses = maxUses;
            this.villagerXp = villagerXp;
            this.priceMultiplier = 0.05f;
        }

        @Override
        public MerchantOffer getOffer(Entity trader, RandomSource random) {
            ItemStack itemStack = new ItemStack(this.item, this.cost);
            return new MerchantOffer(itemStack, new ItemStack(Items.EMERALD, emeraldCount), this.maxUses, this.villagerXp, this.priceMultiplier);
        }
    }

    static class ItemsForEmeralds implements VillagerTrades.ItemListing {
        private final ItemStack itemStack;
        private final int emeraldCost;
        private final int numberOfItems;
        private final int maxUses;
        private final int villagerXp;
        private final float priceMultiplier;

        public ItemsForEmeralds(Block block, int emeraldCost, int numberOfItems, int maxUses, int villagerXp) {
            this(new ItemStack(block), emeraldCost, numberOfItems, maxUses, villagerXp);
        }

        public ItemsForEmeralds(Item item, int emeraldCost, int numberOfItems, int villagerXp) {
            this(new ItemStack(item), emeraldCost, numberOfItems, 12, villagerXp);
        }

        public ItemsForEmeralds(Item item, int emeraldCost, int numberOfItems, int maxUses, int villagerXp) {
            this(new ItemStack(item), emeraldCost, numberOfItems, maxUses, villagerXp);
        }

        public ItemsForEmeralds(ItemStack itemStack, int emeraldCost, int numberOfItems, int maxUses, int villagerXp) {
            this(itemStack, emeraldCost, numberOfItems, maxUses, villagerXp, 0.05f);
        }

        public ItemsForEmeralds(ItemStack itemStack, int emeraldCost, int numberOfItems, int maxUses, int villagerXp, float priceMultiplier) {
            this.itemStack = itemStack;
            this.emeraldCost = emeraldCost;
            this.numberOfItems = numberOfItems;
            this.maxUses = maxUses;
            this.villagerXp = villagerXp;
            this.priceMultiplier = priceMultiplier;
        }

        @Override
        public MerchantOffer getOffer(Entity trader, RandomSource random) {
            return new MerchantOffer(new ItemStack(Items.EMERALD, this.emeraldCost), new ItemStack(this.itemStack.getItem(), this.numberOfItems), this.maxUses, this.villagerXp, this.priceMultiplier);
        }
    }

    static class ItemsAndEmeraldsToItems implements VillagerTrades.ItemListing {
        private final ItemStack fromItem;
        private final int fromCount;
        private final int emeraldCost;
        private final ItemStack toItem;
        private final int toCount;
        private final int maxUses;
        private final int villagerXp;
        private final float priceMultiplier;

        public ItemsAndEmeraldsToItems(ItemLike fromItem, int fromCount, Item toItem, int toCount, int maxUses, int villagerXp) {
            this(fromItem, fromCount, 12, toItem, toCount, maxUses, villagerXp);
        }

        public ItemsAndEmeraldsToItems(ItemLike fromItem, int fromCount, int emeraldCost, Item toItem, int toCount, int maxUses, int villagerXp) {
            this.fromItem = new ItemStack(fromItem);
            this.fromCount = fromCount;
            this.emeraldCost = emeraldCost;
            this.toItem = new ItemStack(toItem);
            this.toCount = toCount;
            this.maxUses = maxUses;
            this.villagerXp = villagerXp;
            this.priceMultiplier = 0.05f;
        }

        @Override
        @Nullable
        public MerchantOffer getOffer(Entity trader, RandomSource random) {
            return new MerchantOffer(new ItemStack(Items.EMERALD, this.emeraldCost), new ItemStack(this.fromItem.getItem(), this.fromCount), new ItemStack(this.toItem.getItem(), this.toCount), this.maxUses, this.villagerXp, this.priceMultiplier);
        }
    }

    static class EnchantBookForEmeralds implements VillagerTrades.ItemListing {
        private final Enchantment enchantment;
        private final int villagerXp;

        public EnchantBookForEmeralds(Enchantment enchantment, int villagerXp) {
            this.enchantment = enchantment;
            this.villagerXp = villagerXp;
        }

        @Override
        public MerchantOffer getOffer(Entity trader, RandomSource random) {
            int level = Mth.nextInt(random, enchantment.getMinLevel(), enchantment.getMaxLevel());
            ItemStack itemStack = EnchantedBookItem.createForEnchantment(new EnchantmentInstance(enchantment, level));
            int cost = 2 + random.nextInt(5 + level * 10) + 3 * level;
            if (enchantment.isTreasureOnly()) {
                cost *= 2;
            }
            if (cost > 64) {
                cost = 64;
            }
            return new MerchantOffer(new ItemStack(Items.EMERALD, cost), new ItemStack(Items.BOOK), itemStack, 12, this.villagerXp, 0.2f);
        }
    }
}
