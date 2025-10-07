package com.github.teamfossilsarcheology.fossil.inventory;

import com.github.teamfossilsarcheology.fossil.food.FoodMappings;
import com.github.teamfossilsarcheology.fossil.food.FoodType;
import com.github.teamfossilsarcheology.fossil.item.ModItems;
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

public class AOEFeederMenu extends AbstractContainerMenu {
    public static final int MEAT_SLOT_ID = 0;
    public static final int PLANT_SLOT_ID = 1;
    public static final int FISH_SLOT_ID = 2;
    public static final int UPGRADE_SLOT_ID = 3;
    private final Container container;
    private final ContainerData containerData;

    public AOEFeederMenu(int id, Inventory playerInventory) {
        this(id, playerInventory, new SimpleContainer(4), new SimpleContainerData(3));
    }

    public AOEFeederMenu(int id, Inventory playerInventory, Container container, ContainerData containerData) {
        super(ModMenus.AOE_FEEDER.get(), id);
        this.container = container;
        this.containerData = containerData;
        addSlot(new Slot(container, MEAT_SLOT_ID, 68, 61) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return isMeat(stack);
            }
        });
        addSlot(new Slot(container, PLANT_SLOT_ID, 110, 61) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return isPlant(stack);
            }
        });
        addSlot(new Slot(container, FISH_SLOT_ID, 152, 61) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return isFish(stack);
            }
        });

        addSlot(new Slot(container, UPGRADE_SLOT_ID, 50, 31) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return isUpgrade(stack);
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

    private boolean isMeat(ItemStack stack) {
        return FoodMappings.getFoodAmount(stack.getItem(), FoodType.MEAT) > 0;
    }

    private boolean isPlant(ItemStack stack) {
        return FoodMappings.getFoodAmount(stack.getItem(), FoodType.PLANT) > 0;
    }

    private boolean isFish(ItemStack stack) {
        return FoodMappings.getFoodAmount(stack.getItem(), FoodType.FISH) > 0;
    }

    private boolean isUpgrade(ItemStack stack) {
        return ModItems.getFeederUpgrades().contains(stack.getItem());
    }


    public int getMeat() {
        return containerData.get(MEAT_SLOT_ID);
    }

    public int getVeg() {
        return containerData.get(PLANT_SLOT_ID);
    }

    public int getFish() {
        return containerData.get(FISH_SLOT_ID);
    }

    @Override
    public @NotNull ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = slots.get(index);
        if (slot.hasItem()) {
            ItemStack current = slot.getItem();
            itemStack = current.copy();
            final int inventorySlots = 36;
            int feederSlots = 4;
            int bottomRowEnd = inventorySlots + feederSlots;
            int bottomRowStart = bottomRowEnd - 9;
            if (index < feederSlots) {
                if (!moveItemStackTo(current, feederSlots, bottomRowEnd, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (isMeat(current)) {
                if (!moveItemStackTo(current, MEAT_SLOT_ID, MEAT_SLOT_ID + 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (isPlant(current)) {
                if (!moveItemStackTo(current, PLANT_SLOT_ID, PLANT_SLOT_ID + 1, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (isFish(current)) {
                if (!moveItemStackTo(current, FISH_SLOT_ID, FISH_SLOT_ID + 1, false)) {
                    return ItemStack.EMPTY;
                }
            }
            else if (isUpgrade(current)) {
                if (!moveItemStackTo(current, UPGRADE_SLOT_ID, UPGRADE_SLOT_ID + 1, false)) {
                    return ItemStack.EMPTY;
                }
            }
            else if (index < bottomRowStart) {
                if (!moveItemStackTo(current, bottomRowStart, bottomRowEnd, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (index <= bottomRowEnd && !moveItemStackTo(current, feederSlots, bottomRowStart, false)) {
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
