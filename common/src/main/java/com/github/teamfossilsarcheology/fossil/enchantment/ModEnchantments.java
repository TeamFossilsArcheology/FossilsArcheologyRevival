package com.github.teamfossilsarcheology.fossil.enchantment;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.item.enchantment.Enchantment;

public class ModEnchantments {
    public static final DeferredRegister<Enchantment> ENCHANTMENTS = DeferredRegister.create(FossilMod.MOD_ID, Registries.ENCHANTMENT);

    public static final RegistrySupplier<Enchantment> ARCHEOLOGY = ENCHANTMENTS.register("archeology", ArcheologyEnchantment::new);
    public static final RegistrySupplier<Enchantment> PALEONTOLOGY = ENCHANTMENTS.register("paleontology", PaleontologyEnchantment::new);

    public static void register() {
        ENCHANTMENTS.register();
    }
}
