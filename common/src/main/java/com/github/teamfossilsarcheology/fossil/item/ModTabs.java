package com.github.teamfossilsarcheology.fossil.item;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import dev.architectury.registry.CreativeTabRegistry;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;

public class ModTabs {
    public static final CreativeModeTab FA_BLOCK_TAB = CreativeTabRegistry.create(
            FossilMod.location("fa_block_tab"), () -> new ItemStack(ModBlocks.WORKTABLE.get())
    );
    public static final CreativeModeTab FA_MOB_ITEM_TAB = CreativeTabRegistry.create(
            FossilMod.location("fa_mob_item_tab"), () -> new ItemStack(PrehistoricEntityInfo.PROTOCERATOPS.dnaItem)
    );
    public static final CreativeModeTab FA_OTHER_ITEM_TAB = CreativeTabRegistry.create(
            FossilMod.location("fa_other_item_tab"), () -> new ItemStack(ModItems.BIO_FOSSIL.get())
    );
}
