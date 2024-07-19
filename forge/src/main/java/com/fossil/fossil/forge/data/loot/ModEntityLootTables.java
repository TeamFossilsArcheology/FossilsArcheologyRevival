package com.fossil.fossil.forge.data.loot;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.entity.ModEntities;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.fossil.fossil.item.ModItems;
import com.fossil.fossil.loot.CustomizeToDinoFunction;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.EmptyLootItem;
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
        for (PrehistoricEntityInfo info : PrehistoricEntityInfo.values()) {
            if (info.hasBones()) {
                add(new ResourceLocation(Fossil.MOD_ID, "entities/" + info.resourceName), defaultLoot(info));
            } else if(info.uniqueBoneItem != null) {
                var meat = LootPool.lootPool().setRolls(ConstantValue.exactly(1)).
                        add(LootItem.lootTableItem(info.foodItem).apply(CustomizeToDinoFunction.apply(LootContext.EntityTarget.THIS))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0, 2))));
                var unique = LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(info.uniqueBoneItem).setWeight(50)).add(EmptyLootItem.emptyItem().setWeight(50));
                add(new ResourceLocation(Fossil.MOD_ID, "entities/" + info.resourceName), LootTable.lootTable().withPool(meat).withPool(unique));
            } else if (info.foodItem != null) {
                var meat = LootPool.lootPool().setRolls(ConstantValue.exactly(1)).
                        add(LootItem.lootTableItem(info.foodItem).apply(CustomizeToDinoFunction.apply(LootContext.EntityTarget.THIS))
                                .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0, 2))));
                add(new ResourceLocation(Fossil.MOD_ID, "entities/" + info.resourceName), LootTable.lootTable().withPool(meat));
            }
        }
        var wool = uniformLoot(Items.BROWN_WOOL, 6, 8);
        add(new ResourceLocation(Fossil.MOD_ID, "entities/" + PrehistoricEntityInfo.MAMMOTH.resourceName), defaultLoot(PrehistoricEntityInfo.MAMMOTH).withPool(wool));
        var shell = LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(Items.NAUTILUS_SHELL));
        add(new ResourceLocation(Fossil.MOD_ID, "entities/" + PrehistoricEntityInfo.NAUTILUS.resourceName), LootTable.lootTable().withPool(shell));
        add(ModEntities.FAILURESAURUS.get(), LootTable.lootTable().withPool(
                LootPool.lootPool().setRolls(ConstantValue.exactly(1)).add(LootItem.lootTableItem(ModItems.FAILURESAURUS_FLESH.get()))));
    }

    private LootTable.Builder defaultLoot(PrehistoricEntityInfo info) {
        var meat = LootPool.lootPool().setRolls(ConstantValue.exactly(1)).
                add(LootItem.lootTableItem(info.foodItem).apply(CustomizeToDinoFunction.apply(LootContext.EntityTarget.THIS))
                        .apply(LootingEnchantFunction.lootingMultiplier(UniformGenerator.between(0, 2))));
        var arm = uniformLoot(info.armBoneItem, 0, 2);
        var foot = uniformLoot(info.footBoneItem, 0, 2);
        var leg = uniformLoot(info.legBoneItem, 0, 2);
        var rib = uniformLoot(info.ribcageBoneItem, 0, 1);
        var skull = uniformLoot(info.skullBoneItem, 0, 1);
        var tail = uniformLoot(info.tailBoneItem, 0, 1);
        var unique = uniformLoot(info.uniqueBoneItem, 0, 2);
        var vertebrae = uniformLoot(info.vertebraeBoneItem, 0, 5);
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
