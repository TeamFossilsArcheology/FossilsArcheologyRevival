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
import net.minecraft.world.level.ItemLike;

import java.util.function.Supplier;

public class ModTabs {
    public static final DeferredRegister<CreativeModeTab> TABS =
            DeferredRegister.create(FossilMod.MOD_ID, Registries.CREATIVE_MODE_TAB);

    public static final RegistrySupplier<CreativeModeTab> FA_BLOCK_TAB = register("fa_block_tab", ModBlocks.WORKTABLE::get);
    public static final RegistrySupplier<CreativeModeTab> FA_MOB_ITEM_TAB = register("fa_mob_item_tab", () -> PrehistoricEntityInfo.PROTOCERATOPS.dnaItem);
    public static final RegistrySupplier<CreativeModeTab> FA_OTHER_ITEM_TAB = register("fa_other_item_tab", ModItems.BIO_FOSSIL::get);

    private static RegistrySupplier<CreativeModeTab> register(String id, Supplier<ItemLike> itemLike) {
        return TABS.register(id, () -> CreativeTabRegistry.create(Component.translatable("itemGroup." + FossilMod.MOD_ID + "." + id),
                () -> new ItemStack(itemLike.get())));
    }

    public static void register() {
        TABS.register();
    }
}
