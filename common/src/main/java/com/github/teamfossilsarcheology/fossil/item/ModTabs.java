package com.github.teamfossilsarcheology.fossil.item;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import dev.architectury.registry.CreativeTabRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

public class ModTabs {
    public static final CreativeModeTab FA_BLOCK_TAB = CreativeTabRegistry.create(
            FossilMod.location("fa_block_tab"), () -> new ItemStack(ModBlocks.WORKTABLE.get())
    );
    public static final CreativeModeTab FA_ITEM_TAB = CreativeTabRegistry.create(
            FossilMod.location("fa_item_tab"), () -> new ItemStack(ModItems.BIO_FOSSIL.get())
    );
    public static final CreativeModeTab FA_PARK_TAB = CreativeTabRegistry.create(
            FossilMod.location("fa_park_tab"), () -> new ItemStack(ModItems.TOY_BALLS.get(DyeColor.RED).get())
    );
}
