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
                return FoodMappings.getItemFoodAmount(stack, Diet.CARNIVORE_EGG) > 0 || FoodMappings.getItemFoodAmount(stack, Diet.PISCCARNIVORE) > 0;
            }
        });
        addSlot(new Slot(container, 1, 104, 62) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return FoodMappings.getItemFoodAmount(stack, Diet.HERBIVORE) > 0;
            }
        });
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
        int otherSlots = slots.size() - 36;
        if (slot.hasItem()) {
            ItemStack current = slot.getItem();
            itemStack = current.copy();
            if (index < otherSlots) {
                if (!moveItemStackTo(current, otherSlots, slots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!moveItemStackTo(current, 0, otherSlots, false)) {
                return ItemStack.EMPTY;
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
