package com.github.teamfossilsarcheology.fossil.forge.data.loot;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.block.PrehistoricPlantInfo;
import com.github.teamfossilsarcheology.fossil.block.custom_blocks.TallFlowerBlock;
import com.github.teamfossilsarcheology.fossil.block.custom_blocks.*;
import net.minecraft.advancements.critereon.EnchantmentPredicate;
import net.minecraft.advancements.critereon.ItemPredicate;
import net.minecraft.advancements.critereon.MinMaxBounds;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.data.loot.packs.VanillaBlockLoot;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.ItemLike;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.properties.BedPart;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.*;
import net.minecraft.world.level.storage.loot.functions.ApplyBonusCount;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.*;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import static com.github.teamfossilsarcheology.fossil.block.ModBlocks.*;
import static com.github.teamfossilsarcheology.fossil.enchantment.ModEnchantments.ARCHEOLOGY;
import static com.github.teamfossilsarcheology.fossil.enchantment.ModEnchantments.PALEONTOLOGY;
import static com.github.teamfossilsarcheology.fossil.item.ModItems.FERN_SEED_FOSSIL;
import static com.github.teamfossilsarcheology.fossil.item.ModItems.FROZEN_MEAT;

/**
 * @see VanillaBlockLoot
 */
public class ModBlockLootTables extends BlockLootSubProvider {

    private static final List<Block> NO_TABLE = List.of(ASH_VENT.get(), HOME_PORTAL.get(), ANU_PORTAL.get(), SARCOPHAGUS.get(), TAR.get(),
            ANU_STATUE.get(), ANUBITE_STATUE.get(), ANCIENT_CHEST.get(), ANU_BARRIER_ORIGIN.get(), ANU_BARRIER_FACE.get(),
            MUTANT_TREE_TUMOR.get());
    private static final Set<Item> EXPLOSION_RESISTANT = Set.of();
    private final List<Block> tableDone = new ArrayList<>();

    public ModBlockLootTables() {
        super(EXPLOSION_RESISTANT, FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    public void generate() {
        FAKE_OBSIDIAN.ifPresent(block -> addCustom(block, createSingleItemTable(Blocks.OBSIDIAN)));

        AMBER_ORE.ifPresent(block -> addCustom(block, createSilkTouchDispatchTable(block, applyExplosionCondition(block,
                LootItem.lootTableItem(AMBER_CHUNK_MOSQUITO.get()).when(LootItemRandomChanceCondition.randomChance(0.05f)).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE))
                        .otherwise(LootItem.lootTableItem(AMBER_CHUNK.get()).apply(ApplyBonusCount.addOreBonusCount(Enchantments.BLOCK_FORTUNE)))))));
        ICED_DIRT.ifPresent(block -> addCustom(block, createSilkTouchOnlyTable(block)));
        PERMAFROST_BLOCK.ifPresent(block -> addCustom(block, multiple(block, 20, FERN_SEED_FOSSIL.get(),
                SKULL_BLOCK.get(), FROZEN_MEAT.get(), Items.BONE, Items.BOOK)));

        SLIME_TRAIL.ifPresent(block -> addCustom(block, randomItem(Items.SLIME_BALL, 0.33f)));

        CALAMITES_LEAVES.ifPresent(block -> addCustom(block, createLeavesDrops(block, CALAMITES_SAPLING.get(), 0.05f, 0.0625f, 0.083333336f, 0.1f)));
        CORDAITES_LEAVES.ifPresent(block -> addCustom(block, createLeavesDrops(block, CORDAITES_SAPLING.get(), 0.05f, 0.0625f, 0.083333336f, 0.1f)));
        MUTANT_TREE_LEAVES.ifPresent(block -> addCustom(block, createSilkTouchOrShearsDispatchTable(block, applyExplosionCondition(block, LootItem.lootTableItem(Items.STICK)))));
        PALM_LEAVES.ifPresent(block -> addCustom(block, createLeavesDrops(block, PALM_SAPLING.get(), 0.05f, 0.0625f, 0.083333336f, 0.1f)));
        SIGILLARIA_LEAVES.ifPresent(block -> addCustom(block, createLeavesDrops(block, SIGILLARIA_SAPLING.get(), 0.05f, 0.0625f, 0.083333336f, 0.1f)));
        TEMPSKYA_LEAF.ifPresent(block -> addCustom(block, createLeavesDrops(block, TEMPSKYA_SAPLING.get(), 0.3f, 0.375f, 0.5f, 0.6f)));

        TEMPSKYA_TOP.ifPresent(block -> {
            var condition = LootItem.lootTableItem(block).when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(block)
                    .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(TempskyaTopBlock.HALF, DoubleBlockHalf.LOWER)));
            addCustom(block, LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(condition)
                    .when(ExplosionCondition.survivesExplosion())).setParamSet(LootContextParamSets.BLOCK));
        });
        for (PrehistoricPlantInfo info : PrehistoricPlantInfo.values()) {
            BushBlock flower = info.getPlantBlock();
            if (info.berryAge() != 0 && info.maxAge() != 0) {
                addCustom(flower, berryBlock(info));
            } else {
                var condition = LootItem.lootTableItem(flower);
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
        }
        var fossils = AlternativesEntry.alternatives(
                fossilReference("regular_fossil_arch_1", enchant(ARCHEOLOGY.get(), 1)),
                fossilReference("regular_fossil_arch_2", enchant(ARCHEOLOGY.get(), 2)),
                fossilReference("regular_fossil_arch_3", enchant(ARCHEOLOGY.get(), 3)),
                fossilReference("regular_fossil_paleo_1", enchant(PALEONTOLOGY.get(), 1)),
                fossilReference("regular_fossil_paleo_2", enchant(PALEONTOLOGY.get(), 2)),
                fossilReference("regular_fossil_paleo_3", enchant(PALEONTOLOGY.get(), 3)),
                fossilReference("regular_fossil", null));
        CALCITE_FOSSIL.ifPresent(block -> addCustom(block, createSilkTouchDispatchTable(block, applyExplosionCondition(block, fossils))));
        DRIPSTONE_FOSSIL.ifPresent(block -> addCustom(block, createSilkTouchDispatchTable(block, applyExplosionCondition(block, fossils))));
        RED_SANDSTONE_FOSSIL.ifPresent(block -> addCustom(block, createSilkTouchDispatchTable(block, applyExplosionCondition(block, fossils))));
        SANDSTONE_FOSSIL.ifPresent(block -> addCustom(block, createSilkTouchDispatchTable(block, applyExplosionCondition(block, fossils))));
        STONE_FOSSIL.ifPresent(block -> addCustom(block, createSilkTouchDispatchTable(block, applyExplosionCondition(block, fossils))));

        var deepSlateFossils = AlternativesEntry.alternatives(
                fossilReference("deep_fossil_arch_1", enchant(ARCHEOLOGY.get(), 1)),
                fossilReference("deep_fossil_arch_2", enchant(ARCHEOLOGY.get(), 2)),
                fossilReference("deep_fossil_arch_3", enchant(ARCHEOLOGY.get(), 3)),
                fossilReference("deep_fossil_paleo_1", enchant(PALEONTOLOGY.get(), 1)),
                fossilReference("deep_fossil_paleo_2", enchant(PALEONTOLOGY.get(), 2)),
                fossilReference("deep_fossil_paleo_3", enchant(PALEONTOLOGY.get(), 3)),
                fossilReference("deep_fossil", null));
        DEEPSLATE_FOSSIL.ifPresent(block -> addCustom(block, createSilkTouchDispatchTable(block, applyExplosionCondition(block, deepSlateFossils))));
        TUFF_FOSSIL.ifPresent(block -> addCustom(block, createSilkTouchDispatchTable(block, applyExplosionCondition(block, deepSlateFossils))));
        BLOCKS.forEach(supplier -> supplier.ifPresent(block -> {
            if (block instanceof AbstractGlassBlock) {
                dropWhenSilkTouch(block);
            } else if (block instanceof SlabBlock) {
                addCustom(block, createSlabItemTable(block));
            } else if (block instanceof DoorBlock || block instanceof AmphoraVaseBlock) {
                addCustom(block, createDoorTable(block));
            } else if (block instanceof BedBlock) {
                addCustom(block, createSinglePropConditionTable(block, BedBlock.PART, BedPart.HEAD));
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

    private LootTable.Builder multiple(Block block, int weight, ItemLike... items) {
        EntryGroup.Builder group = EntryGroup.list();
        for (ItemLike item : items) {
            group.append(LootItem.lootTableItem(item).setWeight(weight));
        }
        return createSilkTouchDispatchTable(block, applyExplosionCondition(block, group));
    }

    private LootPoolSingletonContainer.Builder<?> fossilReference(String location, LootItemCondition.Builder condition) {
        if (condition == null) {
            return LootTableReference.lootTableReference(FossilMod.location(location));
        }
        return LootTableReference.lootTableReference(FossilMod.location(location)).when(condition);
    }

    private LootItemCondition.Builder enchant(Enchantment enchantment, int value) {
        return MatchTool.toolMatches(ItemPredicate.Builder.item().hasEnchantment(new EnchantmentPredicate(enchantment, MinMaxBounds.Ints.exactly(value))));
    }

    private LootTable.Builder berryBlock(PrehistoricPlantInfo info) {
        if (info.getPlantBlock() instanceof BerryBushBlock block) {
            var lootTable = LootTable.lootTable();
            for (int i = info.berryAge(); i <= info.maxAge() ; i++) {
                lootTable = lootTable.withPool(
                        LootPool.lootPool()
                                .when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(info.getPlantBlock())
                                        .setProperties(StatePropertiesPredicate.Builder.properties().hasProperty(block.ageProperty(), i)))
                                .add(LootItem.lootTableItem(info.berryItem().get()))
                                .apply(SetItemCountFunction.setCount(UniformGenerator.between(1 + (i - info.berryAge()), 2 + (i - info.berryAge()))))
                                .apply(ApplyBonusCount.addUniformBonusCount(Enchantments.BLOCK_FORTUNE))
                );
            }
            return applyExplosionDecay(info.getPlantBlock(), lootTable);
        }
        return LootTable.lootTable();
    }

    @Override
    protected @NotNull Iterable<Block> getKnownBlocks() {
        List<Block> list = new ArrayList<>();
        BLOCKS.iterator().forEachRemaining(blockRegistrySupplier -> list.add(blockRegistrySupplier.get()));
        return list;
    }
}
