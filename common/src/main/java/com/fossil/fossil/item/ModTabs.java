package com.fossil.fossil.item;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.block.ModBlocks;
import dev.architectury.registry.CreativeTabRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.ItemStack;

public class ModTabs {
    public static final CreativeModeTab FABLOCKTAB = CreativeTabRegistry.create(
            Fossil.location("fa_block_tab"), () -> new ItemStack(ModBlocks.WORKTABLE.get())
    );
    public static final CreativeModeTab FAITEMTAB = CreativeTabRegistry.create(
            Fossil.location("fa_item_tab"), () -> new ItemStack(ModItems.BIO_FOSSIL.get())
    );
    public static final CreativeModeTab FAPARKTAB = CreativeTabRegistry.create(
            Fossil.location("fa_park_tab"), () -> new ItemStack(ModItems.TOY_BALLS.get(DyeColor.RED).get())
    );
}
