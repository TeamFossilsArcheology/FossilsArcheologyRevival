package fossilsarcheology.server.container;

import fossilsarcheology.server.block.entity.TileEntityFeeder;
import fossilsarcheology.server.entity.prehistoric.Diet;
import fossilsarcheology.server.util.FoodMappings;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class FeederContainer extends SyncedFieldContainer {
    private final TileEntityFeeder feeder;

    public FeederContainer(IInventory playerInventory, TileEntityFeeder feeder) {
        super(feeder);
        this.feeder = feeder;
        this.addSlotToContainer(new Slot(this.feeder, 0, 60, 62) {
            public boolean isItemValid(ItemStack stack) {
                return FoodMappings.INSTANCE.getItemFoodAmount(stack, Diet.CARNIVORE_EGG) > 0 || FoodMappings.INSTANCE.getItemFoodAmount(stack, Diet.PISCCARNIVORE) > 0;
            }
        });
        this.addSlotToContainer(new Slot(this.feeder, 1, 104, 62) {
            public boolean isItemValid(ItemStack stack) {
                return FoodMappings.INSTANCE.getItemFoodAmount(stack, Diet.HERBIVORE) > 0;
            }
        });
        int var3;

        for (var3 = 0; var3 < 3; ++var3) {
            for (int var4 = 0; var4 < 9; ++var4) {
                this.addSlotToContainer(new Slot(playerInventory, var4 + var3 * 9 + 9, 8 + var4 * 18, 84 + var3 * 18));
            }
        }

        for (var3 = 0; var3 < 9; ++var3) {
            this.addSlotToContainer(new Slot(playerInventory, var3, 8 + var3 * 18, 142));
        }
    }


    @Override
    public ItemStack transferStackInSlot(EntityPlayer player, int slotIndex) {
        ItemStack transferred = ItemStack.EMPTY;
        Slot slot = this.inventorySlots.get(slotIndex);
        int otherSlots = this.inventorySlots.size() - 36;
        if (slot != null && slot.getHasStack()) {
            ItemStack current = slot.getStack();
            transferred = current.copy();
            if (slotIndex < otherSlots) {
                if (!this.mergeItemStack(current, otherSlots, this.inventorySlots.size(), true)) {
                    return ItemStack.EMPTY;
                }
            } else if (!this.mergeItemStack(current, 0, otherSlots, false)) {
                return ItemStack.EMPTY;
            }
            if (current.getCount() == 0) {
                slot.putStack(ItemStack.EMPTY);
            } else {
                slot.onSlotChanged();
            }
        }
        return transferred;
    }

    @Override
    public boolean canInteractWith(EntityPlayer player) {
        return this.feeder.isUsableByPlayer(player);
    }
}
