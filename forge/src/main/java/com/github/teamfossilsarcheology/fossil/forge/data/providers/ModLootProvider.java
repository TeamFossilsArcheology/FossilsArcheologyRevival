package com.github.teamfossilsarcheology.fossil.forge.data.providers;

import com.github.teamfossilsarcheology.fossil.forge.data.loot.ModBlockLootTables;
import com.github.teamfossilsarcheology.fossil.forge.data.loot.ModEntityLootTables;
import com.github.teamfossilsarcheology.fossil.forge.data.loot.ModGenericLootTables;
import net.minecraft.data.PackOutput;
import net.minecraft.data.loot.LootTableProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.storage.loot.LootDataId;
import net.minecraft.world.level.storage.loot.LootDataType;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.ValidationContext;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import org.jetbrains.annotations.NotNull;

import java.util.List;
import java.util.Map;
import java.util.Set;

public class ModLootProvider extends LootTableProvider {

    public ModLootProvider(PackOutput output) {
        super(output, Set.of(), List.of(
                new SubProviderEntry(ModBlockLootTables::new, LootContextParamSets.BLOCK),
                new SubProviderEntry(ModEntityLootTables::new, LootContextParamSets.ENTITY),
                new SubProviderEntry(ModGenericLootTables::new, LootContextParamSets.ALL_PARAMS)));
    }

    @Override
    protected void validate(Map<ResourceLocation, LootTable> map, @NotNull ValidationContext validationcontext) {
        map.forEach((location, lootTable) -> {
            lootTable.validate(validationcontext.setParams(lootTable.getParamSet())
                    .enterElement("{" + lootTable + "}", new LootDataId<>(LootDataType.TABLE, location)));
        });
    }
}
