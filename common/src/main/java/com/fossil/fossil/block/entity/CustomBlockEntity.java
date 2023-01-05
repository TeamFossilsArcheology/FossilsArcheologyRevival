package com.fossil.fossil.block.entity;

import net.minecraft.core.BlockPos;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;

public abstract class CustomBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer {
    protected int litTime; //fuel goes from x to 0
    protected int litDuration; //fuel x
    protected int cookingProgress; //item goes from 0 to x
    protected int cookingTotalTime; //item x

    protected CustomBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    protected boolean isProcessing() {
        return litTime > 0;
    }

    protected abstract boolean canProcess();

    protected abstract void createItem();

    protected abstract NonNullList<ItemStack> getItems();


    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        ContainerHelper.loadAllItems(tag, getItems());
        this.litTime = tag.getShort("LitTime");
        this.litDuration = tag.getShort("LitDuration");
        this.cookingProgress = tag.getShort("CookingProgress");
        this.cookingTotalTime = tag.getShort("CookingTotalTime");
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putShort("LitTime", (short) this.litTime);
        tag.putShort("LtDuration", (short) this.litDuration);
        tag.putShort("CookingProgress", (short) this.cookingProgress);
        tag.putShort("CookingTotalTime", (short) this.cookingTotalTime);
        ContainerHelper.saveAllItems(tag, getItems());
    }

    @Override
    public int getContainerSize() {
        return getItems().size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemStack : getItems()) {
            if (itemStack.isEmpty()) continue;
            return false;
        }
        return true;
    }

    @Override
    public @NotNull ItemStack getItem(int slot) {
        return getItems().get(slot);
    }

    @Override
    public @NotNull ItemStack removeItem(int slot, int amount) {
        return ContainerHelper.removeItem(getItems(), slot, amount);
    }

    @Override
    public @NotNull ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(getItems(), slot);
    }


    @Override
    public boolean stillValid(Player player) {
        if (this.level.getBlockEntity(this.worldPosition) != this) {
            return false;
        }
        return player.distanceToSqr((double) this.worldPosition.getX() + 0.5, (double) this.worldPosition.getY() + 0.5,
                (double) this.worldPosition.getZ() + 0.5) <= 64.0;
    }

    @Override
    public void clearContent() {
        getItems().clear();
    }

    /**
     * @return the amount of ingredient slots this container has
     */
    public int getIngredientsSize() {
        return 1;
    }
}
