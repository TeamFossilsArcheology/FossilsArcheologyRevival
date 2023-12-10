package com.fossil.fossil.forge.data.loot;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.loot.CustomizeToDinoFunction;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.LootingEnchantFunction;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;
import org.jetbrains.annotations.NotNull;

import java.util.List;

public class ModEntityLootTables extends EntityLoot {

    @Override
    protected void addTables() {
        for (PrehistoricEntityType type : PrehistoricEntityType.values()) {
            if (type.hasBones()) {
                add(new ResourceLocation(Fossil.MOD_ID, "entities/" + type.resourceName), defaultLoot(type));
            } else if (type.foodItem != null) {
                var meat = LootPool.lootPool().setRolls(ConstantValue.exactly(1)).
                        add(LootItem.lootTableItem(type.foodItem).apply(CustomizeToDinoFunction.apply(LootContext.EntityTarget.THIS))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0, 2))));
                add(new ResourceLocation(Fossil.MOD_ID, "entities/" + type.resourceName), LootTable.lootTable().withPool(meat));
            }
        }
        var wool = uniformLoot(Items.BROWN_WOOL, 6, 8);
        add(new ResourceLocation(Fossil.MOD_ID, "entities/" + PrehistoricEntityType.MAMMOTH.resourceName), defaultLoot(PrehistoricEntityType.MAMMOTH).withPool(wool));
        var shell = LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(Items.NAUTILUS_SHELL));
        add(new ResourceLocation(Fossil.MOD_ID, "entities/" + PrehistoricEntityType.NAUTILUS.resourceName), LootTable.lootTable().withPool(shell));
    }

    private LootTable.Builder defaultLoot(PrehistoricEntityType type) {
        var meat = LootPool.lootPool().setRolls(ConstantValue.exactly(1)).
                add(LootItem.lootTableItem(type.foodItem).apply(CustomizeToDinoFunction.apply(LootContext.EntityTarget.THIS))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0, 2))));
        var arm = uniformLoot(type.armBoneItem, 0, 2);
        var foot = uniformLoot(type.footBoneItem, 0, 2);
        var leg = uniformLoot(type.legBoneItem, 0, 2);
        var rib = uniformLoot(type.ribcageBoneItem, 0, 1);
        var skull = uniformLoot(type.skullBoneItem, 0, 1);
        var tail = uniformLoot(type.tailBoneItem, 0, 1);
        var unique = uniformLoot(type.uniqueBoneItem, 0, 2);
        var vertebrae = uniformLoot(type.vertebraeBoneItem, 0, 5);
        return LootTable.lootTable().withPool(meat).withPool(arm).withPool(foot).withPool(leg).withPool(rib).withPool(skull).withPool(tail).withPool(unique).withPool(vertebrae);
    }

    private LootPool.Builder uniformLoot(Item item, int min, int max) {
        return LootPool.lootPool().setRolls(ConstantValue.exactly(1)).
                add(LootItem.lootTableItem(item).apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max))));
    }

    @Override
    protected @NotNull Iterable<EntityType<?>> getKnownEntities() {
        return List.of();
    }
}
