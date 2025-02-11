package com.github.teamfossilsarcheology.fossil.inventory;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.inventory.MenuType;

public class ModMenus {
    private static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(FossilMod.MOD_ID, Registry.MENU_REGISTRY);

    public static void register() {
        MENUS.register();
    }

    public static final RegistrySupplier<MenuType<FeederMenu>> FEEDER = MENUS.register("feeder", () -> new MenuType<>(FeederMenu::new));
    public static final RegistrySupplier<MenuType<SifterMenu>> SIFTER = MENUS.register("sifter", () -> new MenuType<>(SifterMenu::new));
    public static final RegistrySupplier<MenuType<AnalyzerMenu>> ANALYZER = MENUS.register("analyzer", () -> new MenuType<>(AnalyzerMenu::new));
    public static final RegistrySupplier<MenuType<WorktableMenu>> WORKTABLE = MENUS.register("worktable", () -> new MenuType<>(WorktableMenu::new));
    public static final RegistrySupplier<MenuType<CultureVatMenu>> CULTURE_VAT = MENUS.register("culture_vat", () -> new MenuType<>(CultureVatMenu::new));


}
