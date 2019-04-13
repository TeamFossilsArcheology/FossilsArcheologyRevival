package fossilsarcheology.server.block.entity;

import fossilsarcheology.Revival;
import fossilsarcheology.server.block.FeederBlock;
import fossilsarcheology.server.entity.prehistoric.Diet;
import fossilsarcheology.server.entity.prehistoric.EntityPrehistoric;
import fossilsarcheology.server.entity.prehistoric.PrehistoricEntityType;
import fossilsarcheology.server.message.MessageUpdateFeeder;
import fossilsarcheology.server.util.FoodMappings;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;

public class TileEntityFeeder extends TileEntity implements IInventory, ISidedInventory, ITickable {
    private static final int[] SLOTS_TOP = new int[] { 0, 1 };
    private int meat;
    private int plant;
    private int prevMeat;
    private int prevPlant;
    private int ticksExisted;
    private NonNullList<ItemStack> stacks = NonNullList.withSize(2, ItemStack.EMPTY);
    private String customName;

    @Override
    public int getSizeInventory() {
        return this.stacks.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemstack : this.stacks) {
            if (!itemstack.isEmpty()) {
                return false;
            }
        }
        return true;
    }

    @Override
    public ItemStack getStackInSlot(int slot) {
        return this.stacks.get(slot);
    }

    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        return ItemStackHelper.getAndSplit(this.stacks, slot, amount);
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(this.stacks, index);
    }

    @Override
    public void setInventorySlotContents(int slot, ItemStack stack) {
        this.stacks.set(slot, stack);
        if (!stack.isEmpty() && stack.getCount() > this.getInventoryStackLimit()) {
            stack.setCount(this.getInventoryStackLimit());
        }
    }

    public void setCustomName(String name) {
        this.customName = name;
    }

    @Override
    public void readFromNBT(NBTTagCompound compound) {
        super.readFromNBT(compound);
        this.stacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(compound, this.stacks);
        this.meat = compound.getInteger("Meat");
        this.plant = compound.getInteger("Plant");
        if (compound.hasKey("CustomName")) {
            this.customName = compound.getString("CustomName");
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);
        compound.setInteger("Meat", this.meat);
        compound.setInteger("Plant", this.plant);
        ItemStackHelper.saveAllItems(compound, this.stacks);
        if (this.hasCustomName()) {
            compound.setString("CustomName", this.customName);
        }
        return compound;
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return this.world.getTileEntity(this.pos) == this && player.getDistanceSq(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <= 64.0D;
    }

    @Override
    public void openInventory(EntityPlayer player) {

    }

    @Override
    public void closeInventory(EntityPlayer player) {

    }

    @Override
    public boolean isItemValidForSlot(int slot, ItemStack stack) {
        if(slot == 0){
            return FoodMappings.INSTANCE.getItemFoodAmount(stack, Diet.CARNIVORE_EGG) > 0 || FoodMappings.INSTANCE.getItemFoodAmount(stack, Diet.PISCCARNIVORE) > 0;
        }
        if(slot == 1){
            return FoodMappings.INSTANCE.getItemFoodAmount(stack, Diet.HERBIVORE) > 0;
        }
        return false;
    }

    @Override
    public int getField(int id) {
        switch (id) {
            case 0:
                return this.meat;
            case 1:
                return this.plant;
            default:
                return 0;
        }
    }

    @Override
    public void setField(int id, int value) {
        switch (id) {
            case 0:
                this.meat = value;
                break;
            case 1:
                this.plant = value;
                break;
        }
    }

    @Override
    public int getFieldCount() {
        return 2;
    }

    @Override
    public void clear() {
        this.stacks.clear();
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        return side != EnumFacing.DOWN ? SLOTS_TOP : new int[] {};
    }

    @Override
    public boolean canInsertItem(int index, ItemStack stack, EnumFacing direction) {
        return this.isItemValidForSlot(index, stack);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        return direction != EnumFacing.DOWN;
    }

    @SuppressWarnings("deprecation")
    @Override
    public String getName() {
        return this.hasCustomName() ? this.customName : I18n.translateToLocal("tile.feeder.name");
    }

    @Override
    public boolean hasCustomName() {
        return this.customName != null && this.customName.length() > 0;
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound tag = new NBTTagCompound();
        this.writeToNBT(tag);
        return new SPacketUpdateTileEntity(this.pos, 1, tag);
    }

    @Override
    public void onDataPacket(NetworkManager netManager, net.minecraft.network.play.server.SPacketUpdateTileEntity packet) {
        this.readFromNBT(packet.getNbtCompound());
    }

    public boolean isEmpty(PrehistoricEntityType type) {
        if (type.diet == Diet.CARNIVORE || type.diet == Diet.CARNIVORE_EGG || type.diet == Diet.PISCCARNIVORE || type.diet == Diet.PISCIVORE || type.diet == Diet.INSECTIVORE) {
            return this.meat == 0;
        }
        if (type.diet == Diet.HERBIVORE) {
            return this.plant == 0;
        }
        return type.diet == Diet.OMNIVORE && this.meat == 0 && this.meat == 0;
    }

    public int feedDinosaur(EntityPrehistoric mob) {
        int feedamount = 0;
        if (!this.isEmpty(mob.type)) {
            if (mob.type.diet == Diet.CARNIVORE || mob.type.diet == Diet.CARNIVORE_EGG || mob.type.diet == Diet.PISCCARNIVORE || mob.type.diet == Diet.PISCIVORE || mob.type.diet == Diet.INSECTIVORE) {
                this.meat--;
                this.world.setEntityState(mob, (byte) 47);
                feedamount++;
            }
            if (mob.type.diet == Diet.HERBIVORE) {
                this.plant--;
                this.world.setEntityState(mob, (byte) 45);
                feedamount++;
            }
            if (mob.type.diet == Diet.OMNIVORE) {
                if (this.meat == 0 && this.plant != 0) {
                    this.meat--;
                    feedamount++;
                } else if (this.meat != 0 && this.plant == 0) {
                    this.meat--;
                    feedamount++;
                } else if (this.meat != 0) {
                    this.meat--;
                    feedamount++;
                }
            }
        }
        if (!this.world.isRemote) {
            Revival.NETWORK_WRAPPER.sendToAll(new MessageUpdateFeeder(this.pos.toLong(), this.meat, this.plant));
        }
        FeederBlock.updateFeederBlockState(this.plant > 0, this.meat > 0, this.world, this.getPos());
        mob.setHunger(mob.getHunger() + feedamount);
        return feedamount;
    }

    @Override
    public void update() {
        this.ticksExisted++;
        this.prevMeat = this.meat;
        this.prevPlant = this.plant;
        this.meat = Math.max(this.meat, 0);
        this.plant = Math.max(this.plant, 0);
        if (!this.world.isRemote) {
            if (!this.getStackInSlot(0).isEmpty() && this.isItemValidForSlot(0, this.getStackInSlot(0))) {
                if (this.ticksExisted % 5 == 0 && meat < 10000) {
                    int foodPoints = FoodMappings.INSTANCE.getItemFoodAmount(this.getStackInSlot(0), Diet.CARNIVORE_EGG);
                    if (foodPoints > 0) {
                        this.meat += foodPoints;
                        this.decrStackSize(0, 1);
                    }
                }
            }
            if (!this.getStackInSlot(0).isEmpty() && this.isItemValidForSlot(0, this.getStackInSlot(0))) {
                if (this.ticksExisted % 5 == 0 && meat < 10000) {
                    int foodPoints = FoodMappings.INSTANCE.getItemFoodAmount(this.getStackInSlot(0), Diet.PISCIVORE);
                    if (foodPoints > 0) {
                        this.meat += foodPoints;
                        this.decrStackSize(0, 1);
                    }
                }
            }
            if (!this.getStackInSlot(1).isEmpty() && this.isItemValidForSlot(1, this.getStackInSlot(1))) {
                if (this.ticksExisted % 5 == 0 && plant < 10000) {
                    int foodPoints = FoodMappings.INSTANCE.getItemFoodAmount(this.getStackInSlot(1), Diet.HERBIVORE);
                    if (foodPoints > 0) {
                        this.plant += foodPoints;
                        this.decrStackSize(1, 1);
                    }
                }
            }
            if (this.prevMeat != this.meat || this.prevPlant != this.plant) {
                FeederBlock.updateFeederBlockState(this.plant > 0, this.meat > 0, this.world, this.getPos());
            }
        }
    }

    @Override
    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newSate) {
        return oldState.getBlock() != newSate.getBlock();
    }

    public int getMeatBarScaled(int i) {
        return this.getField(0) * i / 10000;
    }

    public int getVegBarScaled(int i) {
        return this.getField(1) * i / 10000;
    }

    net.minecraftforge.items.IItemHandler handlerTop = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.UP);

    @SuppressWarnings("unchecked")
    @Override
    @javax.annotation.Nullable
    public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @javax.annotation.Nullable net.minecraft.util.EnumFacing facing) {
        if (facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            return (T) handlerTop;
        return super.getCapability(capability, facing);
    }
}
