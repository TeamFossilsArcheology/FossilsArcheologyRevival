package com.fossil.fossil.entity;

import com.fossil.fossil.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import org.jetbrains.annotations.Nullable;

public class ToyScratchingPost extends ToyBase {
    public ToyScratchingPost(EntityType<ToyScratchingPost> type, Level level) {
        super(type, level, 20, SoundEvents.WOOL_BREAK);
    }

    @Override
    public void tick() {
        super.tick();
        if (!isOnBlock()) {
            if (!level.isClientSide) {
                Block.popResource(level, blockPosition(), getPickResult());
            }
            discard();
            playSound(attackNoise, getSoundVolume(), getVoicePitch());
        }
    }

    private boolean isOnBlock() {
        return !level.isEmptyBlock(new BlockPos(position().add(0, -1, 0)));
    }

    @Override
    public boolean isPushable() {
        return false;
    }

    @Override
    public Component getDisplayName() {
        return super.getDisplayName();
    }

    @Nullable
    @Override
    public ItemStack getPickResult() {
        return new ItemStack(ModItems.TOY_SCRATCHING_POST.get());
    }
}
