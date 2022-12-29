package com.fossil.fossil.item;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.entity.ModEntities;
import dev.architectury.core.item.ArchitecturySpawnEggItem;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.SpawnEggItem;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(Fossil.MOD_ID, Registry.ITEM_REGISTRY);

    public static final RegistrySupplier<Item> BIOFOSSIL = ITEMS.register("biofossil",
            () -> new BioFossil(false));

    public static final RegistrySupplier<Item> TARFOSSIL = ITEMS.register("tarfossil",
            () -> new BioFossil(true));

    public static final RegistrySupplier<Item> RELIC_SCRAP = ITEMS.register("relic_scrap",
            () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB)));

    public static final RegistrySupplier<Item> CHICKEN_ESSENCE = ITEMS.register("chicken_essence",
            () -> new Item(new Item.Properties().tab(ModTabs.FAITEMTAB)));

    public static final RegistrySupplier<SpawnEggItem> TRICERATOPS_SPAWN_EGG = ITEMS.register("spawn_egg_triceratops",
            () -> new ArchitecturySpawnEggItem(ModEntities.TRICERATOPS, 0x64352D, 0x251A17, new Item.Properties().tab(ModTabs.FAITEMTAB))
    );

    public static void register() {
        ITEMS.register();
    }
}