package com.fossil.fossil.inventory;

import com.fossil.fossil.util.Diet;
import com.fossil.fossil.util.FoodMappings;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.NotNull;

public class FeederMenu extends AbstractContainerMenu {

    private final Container container;
    private final ContainerData containerData;

    public FeederMenu(int id, Inventory playerInventory) {
        this(id, playerInventory, new SimpleContainer(2), new SimpleContainerData(2));
    }

    public FeederMenu(int id, Inventory playerInventory, Container container, ContainerData containerData) {
        super(ModMenus.FEEDER.get(), id);
        this.container = container;
        this.containerData = containerData;
        addSlot(new Slot(container, 0, 60, 62) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return FoodMappings.getFoodAmount(stack.getItem(), Diet.CARNIVORE_EGG) > 0 || FoodMappings.getFoodAmount(stack.getItem(),
                        Diet.PISCI_CARNIVORE) > 0;
            }
        });
        addSlot(new Slot(container, 1, 104, 62) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return FoodMappings.getFoodAmount(stack.getItem(), Diet.HERBIVORE) > 0;
            }
        });
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

    public int getMeat() {
        return containerData.get(0);
    }

    public int getVeg() {
        return containerData.get(1);
    }

    @Override
    public @NotNull ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = slots.get(index);
        int inventorySlots = 36;
        if (slot.hasItem()) {
            ItemStack current = slot.getItem();
            itemStack = current.copy();
            if (index < 3) {
                if (!moveItemStackTo(current, 2, inventorySlots + 2, true)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (!moveItemStackTo(current, 0, 2, false)) {
                    return ItemStack.EMPTY;
                } else if (index < inventorySlots + 2 - 9 && !moveItemStackTo(current, inventorySlots + 2 - 9, inventorySlots + 2, false)) {
                    return ItemStack.EMPTY;
                } else if (index >= inventorySlots + 2 - 9 && index < inventorySlots + 3 && !moveItemStackTo(current, 2, inventorySlots + 2 - 9,
                        false)) {
                    return ItemStack.EMPTY;
                }
            }
            if (current.isEmpty()) {
                slot.set(ItemStack.EMPTY);
            } else {
                slot.setChanged();
            }
        }
        return itemStack;
    }

    @Override
    public boolean stillValid(Player player) {
        return container.stillValid(player);
    }
}
