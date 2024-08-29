package com.fossil.fossil.forge.data.loot;

import com.fossil.fossil.block.PrehistoricPlantInfo;
import com.fossil.fossil.block.custom_blocks.AmphoraVaseBlock;
import com.fossil.fossil.block.custom_blocks.FourTallFlowerBlock;
import com.fossil.fossil.block.custom_blocks.TallFlowerBlock;
import com.fossil.fossil.tags.ModItemTags;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLoot;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BedPart;
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

import static com.fossil.fossil.block.ModBlocks.*;
import static com.fossil.fossil.enchantment.ModEnchantments.ARCHEOLOGY;
import static com.fossil.fossil.enchantment.ModEnchantments.PALEONTOLOGY;
import static com.fossil.fossil.item.ModItems.*;

public class ModBlockLootTables extends BlockLoot {

    private static final List<Block> NO_TABLE = List.of(ANU_PORTAL.get(), SARCOPHAGUS.get(), TAR.get(),
            ANU_STATUE.get(), ANUBITE_STATUE.get(), ANCIENT_CHEST.get(), ANU_BARRIER.get(),
            MUTANT_TREE_TUMOR.get());
    private final List<Block> tableDone = new ArrayList<>();
    private int scarab;
    private int broken;
    private int skullBlock;
    private int bioFossil;
    private int plantFossil;
    private int relic;
    private int bone;

    @Override
    protected void addTables() {
        AMBER_ORE.ifPresent(block -> addCustom(block, createOreDrop(block, Item.byBlock(AMBER_CHUNK.get()))));
        ICED_DIRT.ifPresent(block -> addCustom(block, createSilkTouchOnlyTable(block)));
        PERMAFROST_BLOCK.ifPresent(block -> addCustom(block, multiple(20, FERN_SEED_FOSSIL.get(),
                SKULL_BLOCK.get(), FROZEN_MEAT.get(), Items.BONE, Items.BOOK)));

        SLIME_TRAIL.ifPresent(block -> addCustom(block, randomItem(Items.SLIME_BALL, 0.33f)));

        CALAMITES_LEAVES.ifPresent(block -> addCustom(block, createLeavesDrops(block, CALAMITES_SAPLING.get(), 0.05f, 0.0625f, 0.083333336f, 0.1f)));
        CORDAITES_LEAVES.ifPresent(block -> addCustom(block, createLeavesDrops(block, CORDAITES_SAPLING.get(), 0.05f, 0.0625f, 0.083333336f, 0.1f)));
        MUTANT_TREE_LEAVES.ifPresent(block -> addCustom(block, createSilkTouchOrShearsDispatchTable(block, applyExplosionCondition(block, LootItem.lootTableItem(Items.STICK)))));
        PALM_LEAVES.ifPresent(block -> addCustom(block, createLeavesDrops(block, PALM_SAPLING.get(), 0.05f, 0.0625f, 0.083333336f, 0.1f)));
        SIGILLARIA_LEAVES.ifPresent(block -> addCustom(block, createLeavesDrops(block, SIGILLARIA_SAPLING.get(), 0.05f, 0.0625f, 0.083333336f, 0.1f)));


        for (PrehistoricPlantInfo info : PrehistoricPlantInfo.values()) {
            BushBlock flower = info.getPlantBlock();
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
        //lol this is so bad but I like it
        var defauList = scarab(1).broken(5).skullBlock(34).bio(275).plant(110).relic(200).bone(440)
                .group(6, 6, 3, 3, 3, 3, 3, 3);
        var archList1 = scarab(6).broken(9).skullBlock(64).bio(225).plant(85).relic(475).bone(200)
                .group(3, 3, 3, 3, 3, 3, 3, 3).when(enchant(ARCHEOLOGY.get(), 1));
        var archList2 = scarab(11).broken(11).skullBlock(110).bio(132).plant(50).relic(715).bone(36).
                group(3, 3, 3, 3, 3, 3, 3, 3).when(enchant(ARCHEOLOGY.get(), 2));
        var archList3 = scarab(16).broken(13).skullBlock(144).bio(50).plant(20).relic(820).bone(0).
                group(3, 3, 3, 3, 3, 3, 3, 3).when(enchant(ARCHEOLOGY.get(), 3));

        var paleList1 = scarab(1).broken(5).skullBlock(55).bio(375).plant(145).relic(20).bone(440)
                .group(9, 9, 6, 6, 6, 6, 6, 6).when(enchant(PALEONTOLOGY.get(), 1));
        var paleList2 = scarab(1).broken(5).skullBlock(30).bio(470).plant(175).relic(0).bone(326)
                .group(13, 13, 10, 10, 12, 10, 10, 10).when(enchant(PALEONTOLOGY.get(), 2));
        var paleList3 = scarab(1).broken(5).skullBlock(36).bio(510).plant(205).relic(0).bone(210)
                .group(18, 18, 15, 15, 17, 15, 15, 15).when(enchant(PALEONTOLOGY.get(), 3));
        LootTable.Builder fossils = LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(AlternativesEntry.alternatives(archList1, archList2, archList3, paleList1, paleList2, paleList3, defauList)))
                .setParamSet(LootContextParamSets.BLOCK);
        addCustom(CALCITE_FOSSIL.get(), fossils);
        addCustom(DEEPSLATE_FOSSIL.get(), fossils);
        addCustom(DRIPSTONE_FOSSIL.get(), fossils);
        addCustom(RED_SANDSTONE_FOSSIL.get(), fossils);
        addCustom(SANDSTONE_FOSSIL.get(), fossils);
        addCustom(STONE_FOSSIL.get(), fossils);
        addCustom(TUFF_FOSSIL.get(), fossils);

        BLOCKS.forEach(supplier -> supplier.ifPresent(block -> {
            if (block instanceof AbstractGlassBlock) {
                dropWhenSilkTouch(block);
            } else if (block instanceof SlabBlock) {
                addCustom(block, createSlabItemTable(block));
            } else if (block instanceof DoorBlock || block instanceof AmphoraVaseBlock) {
                addCustom(block, createDoorTable(block));
            } else if (block instanceof BedBlock) {
                addCustom(block, BlockLoot.createSinglePropConditionTable(block, BedBlock.PART, BedPart.HEAD));
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

    private ModBlockLootTables scarab(int weight) {
        scarab = weight;
        return this;
    }

    private ModBlockLootTables broken(int weight) {
        broken = weight;
        return this;
    }

    private ModBlockLootTables skullBlock(int weight) {
        skullBlock = weight;
        return this;
    }

    private ModBlockLootTables relic(int weight) {
        relic = weight;
        return this;
    }

    private ModBlockLootTables bio(int weight) {
        bioFossil = weight;
        return this;
    }

    private ModBlockLootTables plant(int weight) {
        plantFossil = weight;
        return this;
    }

    private ModBlockLootTables bone(int weight) {
        bone = weight;
        return this;
    }

    private EntryGroup.Builder group(int... weights) {
        return EntryGroup.list(lootItem(SCARAB_GEM.get(), scarab), lootItem(BROKEN_SWORD.get(), broken),
                lootItem(BROKEN_HELMET.get(), broken), lootItem(SKULL_BLOCK.get(), skullBlock),
                lootItem(BIO_FOSSIL.get(), bioFossil), lootItem(RELIC_SCRAP.get(), relic),
                lootItem(Items.BONE, bone), lootItem(PlANT_FOSSIL.get(), plantFossil),
                DynamicLoot.dynamicEntry(ModItemTags.LEG_BONES.location()).setWeight(weights[0]),
                DynamicLoot.dynamicEntry(ModItemTags.ARM_BONES.location()).setWeight(weights[1]),
                DynamicLoot.dynamicEntry(ModItemTags.FOOT_BONES.location()).setWeight(weights[2]),
                DynamicLoot.dynamicEntry(ModItemTags.SKULL_BONES.location()).setWeight(weights[3]),
                DynamicLoot.dynamicEntry(ModItemTags.RIBCAGE_BONES.location()).setWeight(weights[4]),
                DynamicLoot.dynamicEntry(ModItemTags.VERTEBRAE_BONES.location()).setWeight(weights[5]),
                DynamicLoot.dynamicEntry(ModItemTags.UNIQUE_BONES.location()).setWeight(weights[6]),
                DynamicLoot.dynamicEntry(ModItemTags.TAIL_BONES.location()).setWeight(weights[7]));
    }

    private LootPoolSingletonContainer.Builder<?> lootItem(ItemLike item, int weight) {
        return LootItem.lootTableItem(item).setWeight(weight);
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        List<Block> list = new ArrayList<>();
        BLOCKS.iterator().forEachRemaining(blockRegistrySupplier -> list.add(blockRegistrySupplier.get()));
        return list;
    }
}
