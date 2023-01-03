package com.fossil.fossil.inventory;

import com.fossil.fossil.Fossil;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.inventory.MenuType;

public class ModMenus {
    private static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(Fossil.MOD_ID, Registry.MENU_REGISTRY);

    public static final RegistrySupplier<MenuType<FeederMenu>> FEEDER = MENUS.register("feeder", () -> new MenuType<>(FeederMenu::new));

    public static void register() {
        MENUS.register();
    }
}
