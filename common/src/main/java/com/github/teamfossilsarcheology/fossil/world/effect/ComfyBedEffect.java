package com.github.teamfossilsarcheology.fossil.world.effect;

import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;
import net.minecraft.world.level.Level;

import java.util.Optional;

public class ComfyBedEffect extends MobEffect {
    protected ComfyBedEffect(MobEffectCategory mobEffectCategory, int i) {
        super(mobEffectCategory, i);
    }

    public static boolean canApply(Optional<BlockPos> sleepingPos, Level level) {
        return sleepingPos.isPresent() && level.getBlockState(sleepingPos.get()).is(ModBlocks.COMFY_BED.get());
    }
}
