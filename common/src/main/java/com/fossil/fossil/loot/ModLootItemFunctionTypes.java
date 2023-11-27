package com.fossil.fossil.loot;

import com.fossil.fossil.Fossil;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.level.storage.loot.functions.LootItemConditionalFunction;
import net.minecraft.world.level.storage.loot.functions.LootItemFunctionType;

public class ModLootItemFunctionTypes {

    private static final DeferredRegister<LootItemFunctionType> LOOT_FUNCTION_TYPES = DeferredRegister.create(Fossil.MOD_ID, Registry.LOOT_FUNCTION_REGISTRY);

    public static final RegistrySupplier<LootItemFunctionType> CUSTOMIZE_TO_DINOSAUR = register("customize_to_dinosaur", new CustomizeToDinoFunction.Serializer());

    private static <T extends LootItemConditionalFunction> RegistrySupplier<LootItemFunctionType> register(String name, LootItemConditionalFunction.Serializer<T> serializer) {
        return LOOT_FUNCTION_TYPES.register(name, () -> new LootItemFunctionType(serializer));
    }

    public static void register() {
        LOOT_FUNCTION_TYPES.register();
    }
}
