package com.github.teamfossilsarcheology.fossil.item;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.sounds.ModSounds;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class WhipItem extends Item {
    public WhipItem() {
        super(new Item.Properties().tab(ModTabs.FA_PARK_TAB));
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (!level.isClientSide && player.isPassenger() && player.getVehicle() instanceof Prehistoric) {
            player.getItemInHand(usedHand).hurtAndBreak(1, player, p -> p.broadcastBreakEvent(usedHand));
            player.getVehicle().playSound(ModSounds.WHIP.get(), 1, 1);
        } else {
            player.playSound(ModSounds.WHIP.get(), 1, 1);
        }
        player.swing(usedHand);
        player.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.success(player.getItemInHand(usedHand));
    }
}
