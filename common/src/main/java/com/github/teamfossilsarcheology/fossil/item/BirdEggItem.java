package com.github.teamfossilsarcheology.fossil.item;

import com.github.teamfossilsarcheology.fossil.entity.ThrownBirdEgg;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.EntityInfo;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.stats.Stats;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class BirdEggItem extends PrehistoricEntityItem {
    private final boolean cultivated;

    public BirdEggItem(EntityInfo info, boolean cultivated) {
        super(new Properties().stacksTo(16), info, cultivated ? "bird_egg_cultivated" : "bird_egg");
        this.cultivated = cultivated;
    }

    @Override
    public @NotNull InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand usedHand) {
        ItemStack stack = player.getItemInHand(usedHand);
        level.playSound(null, player.getX(), player.getY(), player.getZ(), SoundEvents.EGG_THROW, SoundSource.PLAYERS, 0.5f, 0.4f / (level.getRandom().nextFloat() * 0.4f + 0.8f));
        if (!level.isClientSide) {
            ThrownBirdEgg thrownEgg = ThrownBirdEgg.get(player, level);
            thrownEgg.setType(info);
            thrownEgg.setCultivated(cultivated);
            thrownEgg.setItem(stack);
            thrownEgg.shootFromRotation(player, player.getXRot(), player.getYRot(), 0, 1.5f, 1);
            level.addFreshEntity(thrownEgg);
        }
        player.awardStat(Stats.ITEM_USED.get(this));
        if (!player.getAbilities().instabuild) {
            stack.shrink(1);
        }
        return InteractionResultHolder.sidedSuccess(stack, level.isClientSide());
    }
}
