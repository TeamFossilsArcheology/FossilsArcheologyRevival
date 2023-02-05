package com.fossil.fossil.forge.data.providers;

import com.fossil.fossil.block.ModBlocks;
import com.fossil.fossil.block.custom_blocks.FourTallFlowerBlock;
import com.fossil.fossil.block.custom_blocks.TallFlowerBlock;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.advancements.critereon.StatePropertiesPredicate;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraft.data.HashCache;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.BushBlock;
import net.minecraft.world.level.block.state.properties.DoubleBlockHalf;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.LootTables;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.minecraft.world.level.storage.loot.predicates.ExplosionCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;

public class ModLootProvider extends LootTableProvider {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();

    private final Map<ResourceLocation, LootTable.Builder> lootTables = new HashMap<>();

    private final DataGenerator generator;

    public ModLootProvider(DataGenerator dataGeneratorIn) {
        super(dataGeneratorIn);
        this.generator = dataGeneratorIn;
    }

    protected void addLootTable(ResourceLocation name, LootTable.Builder table) {
        this.lootTables.put(name, table);
    }

    protected void addTables() {
        /*ModBlocks.SLIME_TRAIL.ifPresent(block -> addLootTable(block.getLootTable(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(Items.SLIME_BALL)
                                .when(LootItemRandomChanceCondition.randomChance(0.33f)).when(ExplosionCondition.survivesExplosion())))
                .setParamSet(LootContextParamSets.BLOCK)));
        ModBlocks.BLOCKS.forEach(supplier -> supplier.ifPresent(block -> addLootTable(block.getLootTable(), LootTable.lootTable()
                .withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1))
                        .add(LootItem.lootTableItem(block.asItem())
                                .when(ExplosionCondition.survivesExplosion())))
                .setParamSet(LootContextParamSets.BLOCK))));*/
        for (RegistrySupplier<? extends BushBlock> flowerRegistry : ModBlocks.FLOWERS) {
            BushBlock flower = flowerRegistry.get();
            var condition = LootItem.lootTableItem(flower.asItem()).when(ExplosionCondition.survivesExplosion());
            if (flower instanceof TallFlowerBlock) {
                condition.when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(flower).setProperties(
                        StatePropertiesPredicate.Builder.properties().hasProperty(TallFlowerBlock.HALF, DoubleBlockHalf.LOWER)));
            } else if (flower instanceof FourTallFlowerBlock) {
                condition.when(LootItemBlockStatePropertyCondition.hasBlockStateProperties(flower).setProperties(
                        StatePropertiesPredicate.Builder.properties().hasProperty(FourTallFlowerBlock.LAYER, 0)));
            }
            addLootTable(flower.getLootTable(), LootTable.lootTable().withPool(LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(condition))
                    .setParamSet(LootContextParamSets.BLOCK));
        }
    }

    @Override
    public void run(@NotNull HashCache cache) {
        addTables();

        Map<ResourceLocation, LootTable> tables = new HashMap<>();
        for (Map.Entry<ResourceLocation, LootTable.Builder> entry : lootTables.entrySet()) {
            tables.put(entry.getKey(), entry.getValue().build());
        }
        writeTables(cache, tables);
    }

    private void writeTables(HashCache cache, Map<ResourceLocation, LootTable> tables) {
        Path outputFolder = this.generator.getOutputFolder();
        tables.forEach((key, lootTable) -> {
            Path path = outputFolder.resolve("data/" + key.getNamespace() + "/loot_tables/" + key.getPath() + ".json");
            try {
                DataProvider.save(GSON, cache, LootTables.serialize(lootTable), path);
            } catch (IOException e) {
                LOGGER.error("Couldn't write loot table {}", path, e);
            }
        });
    }
}
