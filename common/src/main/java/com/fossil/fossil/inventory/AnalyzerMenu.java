package com.fossil.fossil.inventory;

import com.fossil.fossil.recipe.ModRecipes;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class AnalyzerMenu extends AbstractContainerMenu {

    private final Container container;
    private final ContainerData containerData;

    public AnalyzerMenu(int id, Inventory playerInventory) {
        this(id, playerInventory, new SimpleContainer(13), new SimpleContainerData(3));
    }

    public AnalyzerMenu(int id, Inventory playerInventory, Container container, ContainerData containerData) {
        super(ModMenus.ANALYZER.get(), id);
        this.container = container;
        this.containerData = containerData;
        for (int column = 0; column < 3; ++column) {
            for (int row = 0; row < 3; ++row) {
                addSlot(new Slot(container, row + column * 3, 20 + row * 18, 17 + column * 18));
            }
        }
        this.addSlot(new FurnaceResultSlot(playerInventory.player, container, 9, 116, 21));
        for (int slot = 0; slot < 3; ++slot) {
            this.addSlot(new FurnaceResultSlot(playerInventory.player, container, 10 + slot, 111 + 18 * slot, 53));
        }
        int l;
        for (l = 0; l < 3; ++l) {
            for (int m = 0; m < 9; ++m) {
                this.addSlot(new Slot(playerInventory, m + l * 9 + 9, 8 + m * 18, 84 + l * 18));
            }
        }
        for (l = 0; l < 9; ++l) {
            this.addSlot(new Slot(playerInventory, l, 8 + l * 18, 142));
        }
        this.addDataSlots(containerData);
    }
    @Override
    public @NotNull ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = slots.get(index);
        int inventorySlots = 36;
        if (slot.hasItem()) {
            ItemStack current = slot.getItem();
            itemStack = current.copy();
            if (index >= 9 && index <= 12) {
                if (!moveItemStackTo(current, 13, inventorySlots+13, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (index >= 13) {
                if (ModRecipes.getAnalyzerRecipeForItem(current, player.level) != null) {
                    if (!this.moveItemStackTo(current, 0, 9, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 13 && index < inventorySlots+13-9 && !this.moveItemStackTo(current, inventorySlots+13-9, inventorySlots+13, false)) {
                    return ItemStack.EMPTY;
                } else if (index >= inventorySlots+13-9 && index < inventorySlots+13 && !this.moveItemStackTo(current, 13, inventorySlots+13-9, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(current, 13, inventorySlots+13, false)) {
                return ItemStack.EMPTY;
            }
            if (current.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }

            if (current.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }

            slot.onTake(player, current);
        }
        return itemStack;
    }

    public int getAnalyzeProgress() {
        return containerData.get(2);
    }

    @Override
    public boolean stillValid(Player player) {
        return container.stillValid(player);
    }


}
