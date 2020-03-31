package fossilsarcheology.server.container;

import fossilsarcheology.server.block.entity.TileEntityTimeMachine;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.inventory.Slot;
import net.minecraft.item.ItemStack;

public class TimeMachineContainer extends Container {
    private final TileEntityTimeMachine timeMachine;


    public TimeMachineContainer(InventoryPlayer var1, TileEntityTimeMachine var2) {
        this.timeMachine = var2;
        int var3;
        int var4;
        this.addSlotToContainer(new Slot(var2, 0, 35, 46));
        for (var3 = 0; var3 < 3; ++var3) {
            for (var4 = 0; var4 < 9; ++var4) {
                this.addSlotToContainer(new Slot(var1, var4 + var3 * 9 + 9, 7 + var4 * 18, 107 + var3 * 18));
            }
        }
        for (var3 = 0; var3 < 9; ++var3) {
            this.addSlotToContainer(new Slot(var1, var3, 7 + var3 * 18, 165));
        }
    }

    @Override
    public void updateProgressBar(int var1, int var2) {
    }

    @Override
    public boolean canInteractWith(EntityPlayer var1) {
        return this.timeMachine.isUsableByPlayer(var1);
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
}
