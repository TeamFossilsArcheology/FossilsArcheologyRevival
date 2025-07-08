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
    public static final int SIFTER_DURATION = 200;

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
        return containerData.get(0);
    }

    @Override
    public @NotNull ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = slots.get(index);
        if (slot.hasItem()) {
            ItemStack current = slot.getItem();
            itemStack = current.copy();
            final int inventorySlots = 36;
            final int sifterSlots = 6;
            final int bottomRowEnd = inventorySlots + sifterSlots;
            final int bottomRowStart = bottomRowEnd - 9;
            if (index < sifterSlots) {
                if (!moveItemStackTo(current, sifterSlots, bottomRowEnd, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (ModRecipes.getSifterRecipeForItem(new SimpleContainer(itemStack), player.level()) != null) {
                if (!moveItemStackTo(current, 0, sifterSlots, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index < bottomRowStart) {
                if (!moveItemStackTo(current, bottomRowStart, bottomRowEnd, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index < bottomRowEnd && !moveItemStackTo(current, sifterSlots, bottomRowStart, false)) {
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
