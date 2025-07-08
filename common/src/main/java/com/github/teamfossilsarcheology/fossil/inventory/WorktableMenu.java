package com.github.teamfossilsarcheology.fossil.inventory;

import com.github.teamfossilsarcheology.fossil.recipe.ModRecipes;
import com.github.teamfossilsarcheology.fossil.recipe.WithFuelRecipe;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class WorktableMenu extends AbstractContainerMenu {

    public static final int INPUT_SLOT_ID = 0;
    public static final int FUEL_SLOT_ID = 1;
    public static final int OUTPUT_SLOT_ID = 2;
    public static final int DEFAULT_DURATION = 300;

    private final Container container;
    private final ContainerData containerData;

    public WorktableMenu(int id, Inventory playerInventory) {
        this(id, playerInventory, new SimpleContainer(3), new SimpleContainerData(4));
    }

    public WorktableMenu(int id, Inventory playerInventory, Container container, ContainerData containerData) {
        super(ModMenus.WORKTABLE.get(), id);
        this.container = container;
        this.containerData = containerData;
        addSlot(new Slot(container, INPUT_SLOT_ID, 45, 21));
        addSlot(new Slot(container, FUEL_SLOT_ID, 80, 54));
        addSlot(new FurnaceResultSlot(playerInventory.player, container, OUTPUT_SLOT_ID, 115, 21));
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

    public int getBurnProgress() {
        int i = containerData.get(2);
        int j = containerData.get(3);
        if (j == 0 || i == 0) {
            return 0;
        }
        return i * 24 / j;
    }

    public boolean isLit() {
        return containerData.get(0) > 0;
    }

    public int getLitProgress() {
        int i = containerData.get(1);
        if (i == 0) {
            i = 200;
        }
        return containerData.get(0) * 14 / i;
    }

    @Override
    public @NotNull ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = slots.get(index);
        if (slot.hasItem()) {
            ItemStack current = slot.getItem();
            itemStack = current.copy();
            final int inventorySlots = 36;
            final int worktableSlots = 3;
            final int bottomRowEnd = inventorySlots + worktableSlots;
            final int bottomRowStart = bottomRowEnd - 9;
            if (index == OUTPUT_SLOT_ID) {
                if (!moveItemStackTo(current, worktableSlots, bottomRowEnd, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (index == INPUT_SLOT_ID || index == FUEL_SLOT_ID) {
                if (!moveItemStackTo(current, 3, 39, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (ModRecipes.getWorktableRecipeForItem(new WithFuelRecipe.ContainerWithAnyFuel(true, itemStack), player.level()) != null) {
                    if (!moveItemStackTo(current, INPUT_SLOT_ID, INPUT_SLOT_ID + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (ModRecipes.isWorktableFuel(current.getItem())) {
                    if (!moveItemStackTo(current, FUEL_SLOT_ID, FUEL_SLOT_ID + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < bottomRowStart) {
                    if (!moveItemStackTo(current, bottomRowStart, bottomRowEnd, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index < bottomRowEnd && !moveItemStackTo(current, worktableSlots, bottomRowStart, false)) {
                    return ItemStack.EMPTY;
                }
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
