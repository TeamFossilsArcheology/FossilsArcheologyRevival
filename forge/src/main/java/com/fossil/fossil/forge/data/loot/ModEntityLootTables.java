package com.fossil.fossil.forge.data.loot;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import net.minecraft.data.loot.EntityLoot;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.storage.loot.LootPool;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.entries.LootItem;
import net.minecraft.world.level.storage.loot.functions.SetItemCountFunction;
import net.minecraft.world.level.storage.loot.providers.number.ConstantValue;
import net.minecraft.world.level.storage.loot.providers.number.UniformGenerator;

import java.util.List;

public class ModEntityLootTables extends EntityLoot {

    @Override
    protected void addTables() {
        for (PrehistoricEntityType type : PrehistoricEntityType.entitiesWithBones()) {
            var arm = boneLoot(type.armBoneItem, 0, 2);
            var leg = boneLoot(type.legBoneItem, 0, 2);
            var rib = boneLoot(type.ribcageBoneItem, 0, 1);
            var vertebrae = boneLoot(type.vertebraeBoneItem, 0, 5);
            var foot = boneLoot(type.footBoneItem, 0, 2);
            var unique = boneLoot(type.uniqueBoneItem, 0, 2);
            add(new ResourceLocation(Fossil.MOD_ID, "entities/" + type.resourceName), LootTable.lootTable().withPool(arm).withPool(leg).withPool(rib).withPool(vertebrae).withPool(foot).withPool(unique));
        }
    }

    private LootPool.Builder boneLoot(Item item, int min, int max) {
        return LootPool.lootPool().setRolls(ConstantValue.exactly(1)).
                add(LootItem.lootTableItem(item).apply(SetItemCountFunction.setCount(UniformGenerator.between(min, max))));
    }

    @Override
    protected Iterable<EntityType<?>> getKnownEntities() {
        return List.of();
    }
}
