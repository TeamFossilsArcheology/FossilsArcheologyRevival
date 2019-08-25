package fossilsarcheology.server.container;

import fossilsarcheology.server.block.entity.TileEntityCultivate;
import net.minecraft.entity.player.InventoryPlayer;
import net.minecraft.inventory.Slot;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandler;

public class CultivateContainer extends BlockEntityContainer {
    private static final int FUEL_TIME_ID = 0;
    private static final int TOTAL_FUEL_TIME_ID = 1;
    private static final int CULTIVATE_TIME_ID = 2;
    private static final int DNA_TYPE_ID = 3;
    private static final int ENERGY_ID = 4;

    private final TileEntityCultivate entity;

    public int fuelTime;
    public int totalFuelTime;
    public int cultivationTime;
    public int dnaType;
    public int energy;

    public CultivateContainer(InventoryPlayer playerInventory, TileEntityCultivate entity) {
        super(entity);
        this.entity = entity;

        IItemHandler itemHandler = entity.getCapability(CapabilityItemHandler.ITEM_HANDLER_CAPABILITY, null);
        if (itemHandler == null) {
            return;
        }

        this.addSlotToContainer(new SlotItemHandlerPredicate(itemHandler, 0, 49, 20, stack -> !TileEntityCultivate.getCultivationOutput(stack).isEmpty()));
        this.addSlotToContainer(new SlotItemHandlerPredicate(itemHandler, 1, 81, 54, stack -> TileEntityCultivate.getItemFuelTime(stack) > 0));
        this.addSlotToContainer(new SlotItemHandlerPredicate(itemHandler, 2, 116, 21, stack -> false));

        for (int column = 0; column < 3; ++column) {
            for (int row = 0; row < 9; ++row) {
                this.addSlotToContainer(new Slot(playerInventory, row + column * 9 + 9, 8 + row * 18, 84 + column * 18));
            }
        }

        for (int column = 0; column < 9; ++column) {
            this.addSlotToContainer(new Slot(playerInventory, column, 8 + column * 18, 142));
        }
    }

    @Override
    public void detectAndSendChanges() {
        int newFuelTime = this.entity.fuelTime;
        int newTotalFuelTime = this.entity.totalFuelTime;
        int newCultivationTime = this.entity.cultivationTime;
        int newEnergy = this.entity.energyStorage.energy;
        int newDnaType = this.entity.getDNAType();
        if (newFuelTime != this.fuelTime) {
            this.listeners.forEach(listener -> listener.sendWindowProperty(this, FUEL_TIME_ID, newFuelTime));
            this.fuelTime = newFuelTime;
        }
        if (newTotalFuelTime != this.totalFuelTime) {
            this.listeners.forEach(listener -> listener.sendWindowProperty(this, TOTAL_FUEL_TIME_ID, newTotalFuelTime));
            this.totalFuelTime = newTotalFuelTime;
        }
        if (newCultivationTime != this.cultivationTime) {
            this.listeners.forEach(listener -> listener.sendWindowProperty(this, CULTIVATE_TIME_ID, newCultivationTime));
            this.cultivationTime = newCultivationTime;
        }
        if (newDnaType != this.dnaType) {
            this.listeners.forEach(listener -> listener.sendWindowProperty(this, DNA_TYPE_ID, newDnaType));
            this.dnaType = newDnaType;
        }
        if (newEnergy != this.energy) {
            this.listeners.forEach(listener -> listener.sendWindowProperty(this, ENERGY_ID, newEnergy));
            this.energy = newEnergy;
        }
        super.detectAndSendChanges();
    }

    @Override
    public void updateProgressBar(int id, int data) {
        super.updateProgressBar(id, data);
        switch (id) {
            case FUEL_TIME_ID:
                this.fuelTime = data;
                break;
            case TOTAL_FUEL_TIME_ID:
                this.totalFuelTime = data;
                break;
            case CULTIVATE_TIME_ID:
                this.cultivationTime = data;
                break;
            case DNA_TYPE_ID:
                this.dnaType = data;
                break;
            case ENERGY_ID:
                this.energy = data;
                break;
        }
    }
}
