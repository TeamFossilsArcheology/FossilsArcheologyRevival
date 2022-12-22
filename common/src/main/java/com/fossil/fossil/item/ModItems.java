package com.fossil.fossil.item;

import com.fossil.fossil.Fossil;
import net.minecraft.world.item.Item;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModItems {
    public static final DeferredRegister<Item> ITEMS =
            DeferredRegister.create(ForgeRegistries.ITEMS, Fossil.MOD_ID );

    public static final RegistryObject<Item> BIOFOSSIL = ITEMS.register("biofossil",
            () -> new Item(new Item.Properties().tab(FATabRegistry.FAITEMTAB)));
    public static final RegistryObject<Item> RELIC_SCRAP = ITEMS.register("relic_scrap",
            () -> new Item(new Item.Properties().tab(FATabRegistry.FAITEMTAB)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}