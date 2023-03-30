package com.fossil.fossil.item;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.sounds.ModSounds;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

public class WhipItem extends Item {
    public WhipItem(Properties properties) {
        super(properties);
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        if (!level.isClientSide && player.isPassenger() && player.getVehicle() instanceof Prehistoric) {
            player.getItemInHand(usedHand).hurtAndBreak(1, player, p -> p.broadcastBreakEvent(usedHand));
            player.getVehicle().playSound(new SoundEvent(ModSounds.WHIP), 1, 1);
        } else {
            player.playSound(new SoundEvent(ModSounds.WHIP), 1, 1);
        }
        player.swing(usedHand);
        player.awardStat(Stats.ITEM_USED.get(this));
        return InteractionResultHolder.success(player.getItemInHand(usedHand));
    }
}
