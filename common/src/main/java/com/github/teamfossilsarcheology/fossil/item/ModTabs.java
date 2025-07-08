package com.github.teamfossilsarcheology.fossil.item;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModTabs {
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(FossilMod.MOD_ID, Registries.CREATIVE_MODE_TAB);

    public static final RegistrySupplier<CreativeModeTab> FA_BLOCK_TAB = TABS.register("fa_block_tab",
            () -> CreativeTabRegistry.create(Component.translatable("category.fa_block_tab"),
            () -> new ItemStack(ModBlocks.WORKTABLE.get())));

    public static final RegistrySupplier<CreativeModeTab> FA_MOB_ITEM_TAB = TABS.register("fa_mob_item_tab",
            () -> CreativeTabRegistry.create(Component.translatable("category.fa_mob_item_tab"),
                    () -> new ItemStack(PrehistoricEntityInfo.PROTOCERATOPS.dnaItem)));

    public static final RegistrySupplier<CreativeModeTab> FA_OTHER_ITEM_TAB = TABS.register("fa_other_item_tab",
            () -> CreativeTabRegistry.create(Component.translatable("category.fa_other_item_tab"),
                    () -> new ItemStack(ModItems.BIO_FOSSIL.get())));

    public static void register() {
        TABS.register();
    }
}
