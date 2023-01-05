package com.fossil.fossil.inventory;

import com.fossil.fossil.recipe.ModRecipes;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class SifterMenu extends AbstractContainerMenu {

    private final Container container;
    private final ContainerData containerData;

    public SifterMenu(int id, Inventory playerInventory) {
        this(id, playerInventory, new SimpleContainer(6), new SimpleContainerData(3));
    }

    public SifterMenu(int id, Inventory playerInventory, Container container, ContainerData containerData) {
        super(ModMenus.SIFTER.get(), id);
        this.container = container;
        this.containerData = containerData;
        addSlot(new Slot(container, 0, 80, 10));
        for (int i = 0; i < 5; i++) {
            addSlot(new FurnaceResultSlot(playerInventory.player, container, 1 + i, 44 + 18 * i, 62));
        }
        int l;
        for (l = 0; l < 3; ++l) {
            for (int m = 0; m < 9; ++m) {
                addSlot(new Slot(playerInventory, m + l * 9 + 9, 8 + m * 18, 84 + l * 18));
            }
        }
        for (l = 0; l < 9; ++l) {
            addSlot(new Slot(playerInventory, l, 8 + l * 18, 142));
        }
        addDataSlots(containerData);
    }

    public int getSiftProgress() {
        return containerData.get(2);
    }
    @Override
    public @NotNull ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = slots.get(index);
        int inventorySlots = 36;
        if (slot.hasItem()) {
            ItemStack current = slot.getItem();
            itemStack = current.copy();
            if (index >= 1 && index <= 5) {
                if (!moveItemStackTo(current, 6, inventorySlots+6, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (index > 0) {
                if (ModRecipes.getSifterRecipeForItem(current, player.level) != null) {
                    if (!this.moveItemStackTo(current, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < inventorySlots + 6 - 9 && !this.moveItemStackTo(current, inventorySlots + 6 - 9, inventorySlots + 6, false)) {
                    return ItemStack.EMPTY;
                } else if (index >= inventorySlots+6-9 && index < inventorySlots+6 && !this.moveItemStackTo(current, 6, inventorySlots+6-9, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(current, 6, inventorySlots+6, false)) {
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

    @Override
    public boolean stillValid(Player player) {
        return container.stillValid(player);
    }


}
