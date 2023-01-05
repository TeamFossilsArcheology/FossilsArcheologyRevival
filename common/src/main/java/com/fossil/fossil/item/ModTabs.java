package com.fossil.fossil.item;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.block.ModBlocks;
import dev.architectury.registry.CreativeTabRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModTabs {
    public static final CreativeModeTab FABLOCKTAB = CreativeTabRegistry.create(
            new ResourceLocation(Fossil.MOD_ID, "fa_block_tab"), () -> new ItemStack(ModBlocks.WORKTABLE.get())
    );
    public static final CreativeModeTab FAITEMTAB = CreativeTabRegistry.create(
            new ResourceLocation(Fossil.MOD_ID, "fa_item_tab"), () -> new ItemStack(ModItems.BIO_FOSSIL.get())
    ); 
}
