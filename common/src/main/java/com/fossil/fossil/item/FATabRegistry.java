package com.fossil.fossil.item;

import com.fossil.fossil.block.ModBlocks;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class FATabRegistry {
    public static final CreativeModeTab FABLOCKTAB = new CreativeModeTab("fa_block_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModBlocks.WORKTABLE.get());
        }
    };
    public static final CreativeModeTab FAITEMTAB = new CreativeModeTab("fa_item_tab") {
        @Override
        public ItemStack makeIcon() {
            return new ItemStack(ModItems.BIOFOSSIL.get());
        }
    };
}
