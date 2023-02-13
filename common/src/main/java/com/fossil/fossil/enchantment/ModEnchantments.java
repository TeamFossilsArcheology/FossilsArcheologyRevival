package com.fossil.fossil.enchantment;

import com.fossil.fossil.Fossil;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.item.enchantment.Enchantment;

public class ModEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(Fossil.MOD_ID, Registry.ENCHANTMENT_REGISTRY);

    public static final RegistrySupplier<Enchantment> ARCHEOLOGY = ENCHANTMENTS.register("archeology", ArcheologyEnchantment::new);
    public static final RegistrySupplier<Enchantment> PALEONTOLOGY = ENCHANTMENTS.register("paleontology", PaleontologyEnchantment::new);

    public static void register() {
        ENCHANTMENTS.register();
    }
}
