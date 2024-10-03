package com.github.teamfossilsarcheology.fossil.inventory;

import com.github.teamfossilsarcheology.fossil.recipe.ModRecipes;
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
        for (int slot = 0; slot < 5; slot++) {
            addSlot(new FurnaceResultSlot(playerInventory.player, container, 1 + slot, 44 + 18 * slot, 62));
        }
        for (int y = 0; y < 3; y++) {
            for (int x = 0; x < 9; x++) {
                addSlot(new Slot(playerInventory, x + y * 9 + 9, 8 + x * 18, 84 + y * 18));
            }
        }
        for (int x = 0; x < 9; x++) {
            addSlot(new Slot(playerInventory, x, 8 + x * 18, 142));
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
                if (!moveItemStackTo(current, 6, inventorySlots + 6, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (index > 0) {
                if (ModRecipes.getSifterRecipeForItem(new SimpleContainer(itemStack), player.level) != null) {
                    if (!this.moveItemStackTo(current, 0, 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < inventorySlots + 6 - 9 && !this.moveItemStackTo(current, inventorySlots + 6 - 9, inventorySlots + 6, false)) {
                    return ItemStack.EMPTY;
                } else if (index >= inventorySlots + 6 - 9 && index < inventorySlots + 6 && !this.moveItemStackTo(current, 6, inventorySlots + 6 - 9, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(current, 6, inventorySlots + 6, false)) {
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
