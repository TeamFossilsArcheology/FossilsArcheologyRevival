package com.fossil.fossil.forge.data.loot;

import com.fossil.fossil.block.ModBlocks;
import com.fossil.fossil.block.PrehistoricPlantType;
import com.fossil.fossil.block.custom_blocks.FossilBlock;
import com.fossil.fossil.block.custom_blocks.FourTallFlowerBlock;
import com.fossil.fossil.block.custom_blocks.TallFlowerBlock;
import com.fossil.fossil.enchantment.ModEnchantments;
import com.fossil.fossil.item.ModItems;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.AbstractGlassBlock;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.SlabBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.*;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;

public class ModBlockLootTables extends BlockLoot {

    private static final List<Block> NO_TABLE = List.of(ModBlocks.ANU_PORTAL.get(), ModBlocks.SARCOPHAGUS.get(), ModBlocks.TAR.get(),
            ModBlocks.ANU_STATUE.get(), ModBlocks.ANUBITE_STATUE.get(), ModBlocks.ANCIENT_CHEST.get());
    private final List<Block> tableDone = new ArrayList<>();

    @Override
    protected void addTables() {
        ModBlocks.AMBER_ORE.ifPresent(block -> addCustom(block, createOreDrop(block, Item.byBlock(ModBlocks.AMBER_CHUNK.get()))));
        ModBlocks.ICED_DIRT.ifPresent(block -> addCustom(block, createSilkTouchOnlyTable(block)));
        ModBlocks.PERMAFROST_BLOCK.ifPresent(block -> addCustom(block, multiple(20, ModItems.FERN_SEED_FOSSIL.get(),
                ModBlocks.SKULL_BLOCK.get(), ModItems.FROZEN_MEAT.get(), Items.BONE, Items.BOOK)));

        ModBlocks.SLIME_TRAIL.ifPresent(block -> addCustom(block, randomItem(Items.SLIME_BALL, 0.33f)));

        ModBlocks.CALAMITES_LEAVES.ifPresent(block -> addCustom(block, createLeavesDrops(block, ModBlocks.CALAMITES_SAPLING.get(), 0.05f, 0.0625f, 0.083333336f, 0.1f)));
        ModBlocks.CORDAITES_LEAVES.ifPresent(block -> addCustom(block, createLeavesDrops(block, ModBlocks.CORDAITES_SAPLING.get(), 0.05f, 0.0625f, 0.083333336f, 0.1f)));
        ModBlocks.PALM_LEAVES.ifPresent(block -> addCustom(block, createLeavesDrops(block, ModBlocks.PALM_SAPLING.get(), 0.05f, 0.0625f, 0.083333336f, 0.1f)));
        ModBlocks.SIGILLARIA_LEAVES.ifPresent(block -> addCustom(block, createLeavesDrops(block, ModBlocks.SIGILLARIA_SAPLING.get(), 0.05f, 0.0625f, 0.083333336f, 0.1f)));


        for (PrehistoricPlantType type : PrehistoricPlantType.values()) {
            BushBlock flower = type.getPlantBlock();
            var condition = LootItem.lootTableItem(flower.asItem());
            if (flower instanceof TallFlowerBlock) {
                condition.when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(flower).setProperties(
                        StatePropertiesPredicate.Builder.properties().hasProperty(TallFlowerBlock.HALF, DoubleBlockHalf.LOWER)));
            } else if (flower instanceof FourTallFlowerBlock) {
                condition.when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(flower).setProperties(
                        StatePropertiesPredicate.Builder.properties().hasProperty(FourTallFlowerBlock.LAYER, 0)));
            }
            addCustom(flower, LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(condition)
                    .when(ExplosionCondition.survivesExplosion())).setParamSet(LootContextParamSets.BLOCK));
        }
        var archList1 = group(6, 3, 7, 37, 275, 475, 75, 200, 4, 3, 3, 3, 3, 3, 3).when(enchant(ModEnchantments.ARCHEOLOGY.get(), 1));
        var archList2 = group(11, 1, 9, 37, 275, 750, 0, 0, 0, 2, 3, 3, 3, 3, 3).when(enchant(ModEnchantments.ARCHEOLOGY.get(), 2));
        var archList3 = group(16, 0, 10, 37, 275, 750, 0, 0, 0, 0, 0, 3, 3, 3, 3).when(enchant(ModEnchantments.ARCHEOLOGY.get(), 3));
        var paleList1 = group(6, 3, 7, 75, 375, 25, 550, 0, 28, 6, 6, 6, 6, 6, 6).when(enchant(ModEnchantments.PALEONTOLOGY.get(), 1));
        var paleList2 = group(11, 1, 9, 112, 475, 0, 400, 0, 48, 9, 9, 9, 9, 9, 9).when(enchant(ModEnchantments.PALEONTOLOGY.get(), 2));
        var paleList3 = group(16, 0, 10, 150, 575, 0, 225, 0, 67, 12, 12, 12, 12, 12, 12).when(enchant(ModEnchantments.PALEONTOLOGY.get(), 3));
        var defauList = group(1, 5, 5, 37, 275, 200, 350, 200, 9, 3, 3, 3, 3, 3, 3);
        addCustom(ModBlocks.FOSSIL.get(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(AlternativesEntry.alternatives(archList1, archList2, archList3, paleList1, paleList2, paleList3, defauList)))
                .setParamSet(LootContextParamSets.BLOCK));

        ModBlocks.BLOCKS.forEach(supplier -> supplier.ifPresent(block -> {
            if (block instanceof AbstractGlassBlock) {
                dropWhenSilkTouch(block);
            } else if (block instanceof SlabBlock) {
                addCustom(block, createSlabItemTable(block));
            } else if (!NO_TABLE.contains(block) && !tableDone.contains(block)) {
                dropSelf(block);
            }
            tableDone.add(block);
        }));

        NO_TABLE.forEach(block -> add(block, noDrop()));
    }

    private void addCustom(Block block, LootTable.Builder table) {
        add(block, table);
        tableDone.add(block);
    }

    private LootTable.Builder randomItem(Item item, float chance) {
        return LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(item).when(LootItemRandomChanceCondition.randomChance(chance))).when(ExplosionCondition.survivesExplosion())).setParamSet(LootContextParamSets.BLOCK);
    }

    private LootTable.Builder multiple(int weight, ItemLike... items) {
        LootPool.Builder loot = LootPool.lootPool().setRolls(ConstantValue.exactly(1));
        for (ItemLike item : items) {
            loot.add(LootItem.lootTableItem(item).setWeight(weight));
        }
        return LootTable.lootTable().withPool(loot.when(ExplosionCondition.survivesExplosion())).setParamSet(LootContextParamSets.BLOCK);
    }

    private LootItemCondition.Builder enchant(Enchantment enchantment, int value) {
        return MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(enchantment, MinMaxBounds.Ints.exactly(value))));
    }

    private EntryGroup.Builder group(int... weights) {
        return EntryGroup.list(lootItem(ModItems.SCARAB_GEM.get(), weights[0]), lootItem(ModItems.BROKEN_SWORD.get(), weights[1]),
                lootItem(ModItems.BROKEN_HELMET.get(), weights[2]), lootItem(ModBlocks.SKULL_BLOCK.get(), weights[3]),
                lootItem(ModItems.BIO_FOSSIL.get(), weights[4]), lootItem(ModItems.RELIC_SCRAP.get(), weights[5]),
                lootItem(Items.BONE, weights[6]), lootItem(ModItems.PlANT_FOSSIL.get(), weights[7]),
                DynamicLoot.dynamicEntry(FossilBlock.LEG_BONES).setWeight(weights[8]),
                DynamicLoot.dynamicEntry(FossilBlock.ARM_BONES).setWeight(weights[9]),
                DynamicLoot.dynamicEntry(FossilBlock.FOOT_BONES).setWeight(weights[10]),
                DynamicLoot.dynamicEntry(FossilBlock.SKULL_BONES).setWeight(weights[11]),
                DynamicLoot.dynamicEntry(FossilBlock.RIBCAGE_BONES).setWeight(weights[12]),
                DynamicLoot.dynamicEntry(FossilBlock.VERTEBRAE_BONES).setWeight(weights[13]),
                DynamicLoot.dynamicEntry(FossilBlock.UNIQUE_BONES).setWeight(weights[14]));
    }

    private LootPoolSingletonContainer.Builder<?> lootItem(ItemLike item, int weight) {
        return LootItem.lootTableItem(item).setWeight(weight);
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        List<Block> list = new ArrayList<>();
        ModBlocks.BLOCKS.iterator().forEachRemaining(blockRegistrySupplier -> list.add(blockRegistrySupplier.get()));
        return list;
    }
}
