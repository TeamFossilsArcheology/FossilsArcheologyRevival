package com.github.teamfossilsarcheology.fossil.inventory;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;

public class ModMenus {
    private static final DeferredRegister<MenuType<?>> MENUS = DeferredRegister.create(FossilMod.MOD_ID, Registries.MENU);

    public static void register() {
        MENUS.register();
    }

    public static final RegistrySupplier<MenuType<FeederMenu>> FEEDER = register("feeder", FeederMenu::new);
    public static final RegistrySupplier<MenuType<SifterMenu>> SIFTER = register("sifter", SifterMenu::new);
    public static final RegistrySupplier<MenuType<AnalyzerMenu>> ANALYZER = register("analyzer", AnalyzerMenu::new);
    public static final RegistrySupplier<MenuType<WorktableMenu>> WORKTABLE = register("worktable", WorktableMenu::new);
    public static final RegistrySupplier<MenuType<CultureVatMenu>> CULTURE_VAT = register("culture_vat", CultureVatMenu::new);

    private static <T extends AbstractContainerMenu> RegistrySupplier<MenuType<T>> register(String key, MenuType.MenuSupplier<T> factory) {
        return MENUS.register(key, () -> new MenuType<>(factory, FeatureFlags.VANILLA_SET));
    }
}
