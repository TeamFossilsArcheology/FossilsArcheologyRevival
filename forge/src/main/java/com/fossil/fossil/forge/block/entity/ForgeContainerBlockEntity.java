package com.fossil.fossil.forge.block.entity;

import com.fossil.fossil.block.entity.CustomBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public abstract class ForgeContainerBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer, CustomBlockEntity {
    protected int litTime; //fuel goes from x to 0
    protected int litDuration; //fuel x
    protected int cookingProgress; //item goes from 0 to x
    protected int cookingTotalTime; //item x
    LazyOptional<? extends IItemHandler>[] handlers = SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);

    protected ForgeContainerBlockEntity(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }


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
        tag.putShort("LitTime", (short) litTime);
        tag.putShort("LitDuration", (short) litDuration);
        tag.putShort("CookingProgress", (short) cookingProgress);
        tag.putShort("CookingTotalTime", (short) cookingTotalTime);
        ContainerHelper.saveAllItems(tag, getItems());
    }

    /**
     * @return the amount of ingredient slots this container has
     */
    public int getIngredientsSize() {
        return 1;
    }

    protected boolean isProcessing() {
        return litTime > 0;
    }

    protected abstract boolean canProcess();

    protected abstract void createItem();

    protected abstract NonNullList<ItemStack> getItems();

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
    public boolean stillValid(@NotNull Player player) {
        if (level.getBlockEntity(worldPosition) != this) {
            return false;
        }
        return player.distanceToSqr(worldPosition.getX() + 0.5, worldPosition.getY() + 0.5, worldPosition.getZ() + 0.5) <= 64.0;
    }

    @Override
    public void clearContent() {
        getItems().clear();
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (!remove && side != null && cap == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (side == Direction.UP) {
                return handlers[0].cast();
            }
            if (side == Direction.DOWN) {
                return handlers[1].cast();
            }
            return handlers[2].cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        for (LazyOptional<? extends IItemHandler> handler : handlers) {
            handler.invalidate();
        }
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
        handlers = SidedInvWrapper.create(this, Direction.UP, Direction.DOWN, Direction.NORTH);
    }
}
