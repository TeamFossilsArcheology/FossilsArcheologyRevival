package com.fossil.fossil.inventory;

import com.fossil.fossil.recipe.ModRecipes;
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

    private final Container container;
    private final ContainerData containerData;

    public WorktableMenu(int id, Inventory playerInventory) {
        this(id, playerInventory, new SimpleContainer(3), new SimpleContainerData(4));
    }

    public WorktableMenu(int id, Inventory playerInventory, Container container, ContainerData containerData) {
        super(ModMenus.WORKTABLE.get(), id);
        this.container = container;
        this.containerData = containerData;
        addSlot(new Slot(container, INPUT_SLOT_ID, 49, 20));
        addSlot(new Slot(container, FUEL_SLOT_ID, 81, 54));
        addSlot(new FurnaceResultSlot(playerInventory.player, container, OUTPUT_SLOT_ID, 116, 21));
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
        return containerData.get(0) * 12 / i;
    }
    @Override
    public @NotNull ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = slots.get(index);
        if (slot.hasItem()) {
            ItemStack current = slot.getItem();
            itemStack = current.copy();
            if (index == OUTPUT_SLOT_ID) {
                if (!moveItemStackTo(current, 3, 39, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (index != INPUT_SLOT_ID && index != FUEL_SLOT_ID) {
                if (ModRecipes.getWorktableRecipeForItem(current, player.level) != null) {
                    if (!this.moveItemStackTo(current, INPUT_SLOT_ID, INPUT_SLOT_ID+1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (ModRecipes.WORKTABLE_FUEL_VALUES.containsKey(current.getItem())) {
                    if (!this.moveItemStackTo(current, FUEL_SLOT_ID, FUEL_SLOT_ID+1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 3 && index < 30 && !this.moveItemStackTo(current, 30, 39, false)) {
                    return ItemStack.EMPTY;
                } else if (index >= 30 && index < 39 && !this.moveItemStackTo(current, 3, 30, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(current, 3, 39, false)) {
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
