package com.github.teamfossilsarcheology.fossil.inventory;

import com.github.teamfossilsarcheology.fossil.recipe.CultureVatRecipe;
import com.github.teamfossilsarcheology.fossil.recipe.ModRecipes;
import com.github.teamfossilsarcheology.fossil.recipe.WithFuelRecipe;
import com.github.teamfossilsarcheology.fossil.util.Version;
import net.minecraft.world.Container;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.inventory.SimpleContainerData;
import net.minecraft.world.inventory.Slot;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;

public class CultureVatMenu extends AbstractContainerMenu {
    public static final int INPUT_SLOT_ID = 0;
    public static final int FUEL_SLOT_ID = 1;
    public static final int OUTPUT_SLOT_ID = 2;
    public static final int CULTIVATION_DURATION = Version.debugEnabled() ? 100 : 6000;

    private final Container container;
    private final ContainerData containerData;

    private final Level level;

    public CultureVatMenu(int id, Inventory playerInventory) {
        this(id, playerInventory, new SimpleContainer(3), new SimpleContainerData(4));
    }

    public CultureVatMenu(int id, Inventory playerInventory, Container container, ContainerData containerData) {
        super(ModMenus.CULTURE_VAT.get(), id);
        this.container = container;
        this.containerData = containerData;
        this.level = playerInventory.player.level();
        addSlot(new Slot(container, INPUT_SLOT_ID, 45, 21) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return super.mayPlace(stack) && canPutStackInInput(stack);
            }
        });
        addSlot(new Slot(container, FUEL_SLOT_ID, 80, 54) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return super.mayPlace(stack) && getItemFuelTime(stack) > 0;
            }
        });
        addSlot(new Slot(container, OUTPUT_SLOT_ID, 115, 21) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
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

    public static int getItemFuelTime(ItemStack stack) {
        return 40;
    }

    public boolean canPutStackInInput(ItemStack stack) {
        if (stack != null && !stack.isEmpty()) {
            CultureVatRecipe recipe = ModRecipes.getCultureVatRecipeForItem(new WithFuelRecipe.ContainerWithAnyFuel(true, stack), level);
            return recipe != null;
        }
        return false;
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
                if (!moveItemStackTo(current, 3, inventorySlots + 3, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (index == INPUT_SLOT_ID || index == FUEL_SLOT_ID) {
                if (!moveItemStackTo(current, 3, 39, false)) {
                    return ItemStack.EMPTY;
                }
            } else {
                if (ModRecipes.getCultureVatRecipeForItem(new WithFuelRecipe.ContainerWithAnyFuel(true, itemStack), player.level()) != null) {
                    if (!moveItemStackTo(current, INPUT_SLOT_ID, INPUT_SLOT_ID + 1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (ModRecipes.isCultureVatFuel(current.getItem())) {
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

    public int getFuelTime() {
        return containerData.get(0);
    }

    public int getTotalFuelTime() {
        return containerData.get(1);
    }

    public int getCultivationTime() {
        return containerData.get(2);
    }

    public int getStoredEnergy() {
        return containerData.get(3);
    }

    @Override
    public boolean stillValid(Player player) {
        return container.stillValid(player);
    }
}
