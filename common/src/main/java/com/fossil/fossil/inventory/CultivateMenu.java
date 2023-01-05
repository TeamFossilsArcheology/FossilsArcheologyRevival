package com.fossil.fossil.inventory;

import com.fossil.fossil.recipe.ModRecipes;
import com.fossil.fossil.recipe.WorktableRecipe;
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

public class CultivateMenu extends AbstractContainerMenu {
    public static final int INPUT_SLOT_ID = 0;
    public static final int FUEL_SLOT_ID = 1;
    public static final int OUTPUT_SLOT_ID = 2;

    private final Container container;
    private final ContainerData containerData;

    private final Level level;

    public CultivateMenu(int id, Inventory playerInventory) {
        this(id, playerInventory, new SimpleContainer(3), new SimpleContainerData(3));
    }

    public CultivateMenu(int id, Inventory playerInventory, Container container, ContainerData containerData) {
        super(ModMenus.CULTIVATE.get(), id);
        this.container = container;
        this.containerData = containerData;
        this.level = playerInventory.player.level;
        addSlot(new Slot(container, INPUT_SLOT_ID, 49, 20) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return super.mayPlace(stack) && canPutStackInInput(stack);
            }
        });
        addSlot(new Slot(container, FUEL_SLOT_ID, 81, 54) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return super.mayPlace(stack) && getItemFuelTime(stack) > 0;
            }
        });
        addSlot(new Slot(container, OUTPUT_SLOT_ID, 116, 21) {
            @Override
            public boolean mayPlace(ItemStack stack) {
                return false;
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

    public boolean canPutStackInInput(ItemStack stack) {
        if (stack != null && !stack.isEmpty()) {
            WorktableRecipe recipe = ModRecipes.getCultivateRecipeForItem(stack, level);
            return recipe != null;
        }
        return false;
    }

    @Override
    public @NotNull ItemStack quickMoveStack(Player player, int index) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = slots.get(index);
        int inventorySlots = 36;
        if (slot.hasItem()) {
            ItemStack current = slot.getItem();
            itemStack = current.copy();
            if (index == OUTPUT_SLOT_ID) {
                if (!moveItemStackTo(current, 3, inventorySlots+3, true)) {
                    return ItemStack.EMPTY;
                }
            } else if (index != INPUT_SLOT_ID && index != FUEL_SLOT_ID) {
                if (ModRecipes.getCultivateRecipeForItem(current, level) != null) {
                    if (!this.moveItemStackTo(current, INPUT_SLOT_ID, INPUT_SLOT_ID+1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (ModRecipes.CULTIVATE_FUEL_VALUES.containsKey(current.getItem())) {
                    if (!this.moveItemStackTo(current, FUEL_SLOT_ID, FUEL_SLOT_ID+1, false)) {
                        return ItemStack.EMPTY;
                    }
                } else if (index >= 3 && index < 30 && !this.moveItemStackTo(current, 30, inventorySlots+3, false)) {
                    return ItemStack.EMPTY;
                } else if (index >= 30 && index < inventorySlots+3 && !this.moveItemStackTo(current, 3, 30, false)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.moveItemStackTo(current, 3, inventorySlots+3, false)) {
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

    public int getFuelTime() {
        return containerData.get(0);
    }

    public int getTotalFuelTime() {
        return containerData.get(1);
    }

    public int getCultivationTime() {
        return containerData.get(2);
    }

    public static int getItemFuelTime(ItemStack stack) {
        return 40;
    }

    @Override
    public boolean stillValid(Player player) {
        return container.stillValid(player);
    }


}
