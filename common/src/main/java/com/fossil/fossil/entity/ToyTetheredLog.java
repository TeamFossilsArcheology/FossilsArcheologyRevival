package com.fossil.fossil.entity;

import com.fossil.fossil.item.ModItems;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

public class ToyTetheredLog extends ToyBase {
    //TODO: Animations
    public ToyTetheredLog(EntityType<ToyTetheredLog> type, Level level) {
        super(type, level, 30, SoundEvents.WOOD_HIT);
    }

    @Override
    public void tick() {
        super.tick();
        setDeltaMovement(0, 0, 0);
        if (!isAttachedToBlock()) {
            if (!level.isClientSide) {
                Block.popResource(level, blockPosition(), getPickResult());
            }
            discard();
            playSound(attackNoise, getSoundVolume(), getVoicePitch());
        }
    }

    private boolean isAttachedToBlock() {
        return !level.isEmptyBlock(blockPosition().above(2));
    }

    @Override
    public boolean isPushable() {
        return false;
    }


    @Nullable
    @Override
    public ItemStack getPickResult() {
        return new ItemStack(ModItems.TOY_TETHERED_LOG.get());
    }
}
