package fossilsarcheology.server.block.entity;

import fossilsarcheology.Revival;
import fossilsarcheology.server.block.AnalyzerBlock;
import fossilsarcheology.server.item.FAItemRegistry;
import fossilsarcheology.server.recipe.FAMachineRecipeRegistry;
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
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.energy.CapabilityEnergy;
import net.minecraftforge.energy.EnergyStorage;
import net.minecraftforge.energy.IEnergyStorage;

import java.util.Random;

public class TileEntityAnalyzer extends TileEntity implements IInventory, ISidedInventory, ITickable {
	private static final int[] SLOTS_TOP = new int[]{};
	private static final int[] SLOTS_BOTTOM = new int[]{9, 10, 11, 12};
	private static final int[] SLOTS_SIDES = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8};

	public int analyzeFuelTime = 0;
	public int currentFuelTime = 100;
	public int analyzeTime = 0;
	private String customName;
	private NonNullList<ItemStack> stacks = NonNullList.withSize(13, ItemStack.EMPTY);
	private int rawIndex = -1;
	public FAEnergyStorage energyStorage;
	private static int getFuelTime(ItemStack stack) {
		return 100;
	}

	public static boolean isFuel(ItemStack stack) {
		return getFuelTime(stack) > 0;
	}

	@Override
	public int getSizeInventory() {
		return this.stacks.size();
	}

	public TileEntityAnalyzer(){
		energyStorage = new FAEnergyStorage(Revival.CONFIG_OPTIONS.machineMaxEnergy, Revival.CONFIG_OPTIONS.machineTransferRate, Revival.CONFIG_OPTIONS.machineTransferRate, 0);
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
		this.analyzeFuelTime = compound.getShort("FuelTime");
		this.analyzeTime = compound.getShort("AnalyzeTime");
		this.currentFuelTime = 100;
		if (compound.hasKey("CustomName")) {
			this.customName = compound.getString("CustomName");
		}
		this.energyStorage.deserializeNBT(compound);
	}

	@Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
        compound = super.writeToNBT(compound);
		compound.setShort("FuelTime", (short) this.analyzeFuelTime);
		compound.setShort("AnalyzeTime", (short) this.analyzeTime);
		ItemStackHelper.saveAllItems(compound, this.stacks);
		if (this.hasCustomName()) {
			compound.setString("CustomName", this.customName);
		}
		NBTTagCompound secondTag = this.energyStorage.serializeNBT();
		compound.setInteger("EnergyStored", secondTag.getInteger("EnergyStored"));
		return compound;
	}

	@Override
	public int getInventoryStackLimit() {
		return 64;
	}

	public int getAnalyzeProgressScaled(int scale) {
		return this.analyzeTime * scale / 200;
	}

	public boolean isAnalyzing() {
		return this.analyzeFuelTime > 0;
	}

	@Override
	public void update() {
		if(Revival.CONFIG_OPTIONS.machinesRequireEnergy){
			for(EnumFacing facing : EnumFacing.values()){
				if(world.getTileEntity(this.pos.offset(facing)) != null && world.getTileEntity(this.pos.offset(facing)).hasCapability(CapabilityEnergy.ENERGY, facing.getOpposite())){
					IEnergyStorage storage = world.getTileEntity(this.pos.offset(facing)).getCapability(CapabilityEnergy.ENERGY, facing.getOpposite());
					if(storage != null) {
						int energy = storage.extractEnergy(Revival.CONFIG_OPTIONS.machineTransferRate, false);
						this.energyStorage.receiveEnergy(energy, false);
					}
				}
			}
		}
		boolean fueled = this.analyzeFuelTime > 0;
		boolean dirty = false;
		if (this.analyzeFuelTime > 0) {
			--this.analyzeFuelTime;
		}
		if (!this.world.isRemote) {
			if (this.analyzeFuelTime == 0 && this.canAnalyze()) {
				this.currentFuelTime = this.analyzeFuelTime = 100;
				dirty = true;
			}
			if (this.isAnalyzing() && this.canAnalyze()) {
				++this.analyzeTime;
				this.energyStorage.extractEnergy(Revival.CONFIG_OPTIONS.machineEnergyUsage, false);
				if (this.analyzeTime == 200) {
					this.analyzeTime = 0;
					this.analyzeItem();
					dirty = true;
				}
			} else {
				this.analyzeTime = 0;
			}
			if (fueled != this.analyzeFuelTime > 0) {
				dirty = true;
				AnalyzerBlock.setState(this.analyzeFuelTime > 0, this.world, this.pos);
			}
		}
		if (dirty) {
			this.markDirty();
		}
	}

	private boolean canAnalyze() {
		int spaceIndex = -1;
		this.rawIndex = -1;
		boolean hasPower = true;
		if(Revival.CONFIG_OPTIONS.machinesRequireEnergy){
			hasPower = this.energyStorage.energy > 0;
		}
		boolean flag = false;
		for (int slot = 0; slot < 9; ++slot) {
			if (!this.stacks.get(slot).isEmpty()) {
				if (isAnalyzable(this.stacks.get(slot))){
					this.rawIndex = slot;
					flag = true;
					break;
				}
			}
		}
		if (this.rawIndex == -1 || !flag) {
			return false;
		} else {
			for (int slot = 12; slot > 8; --slot) {
				if (this.stacks.get(slot).isEmpty()) {
					spaceIndex = slot;
					break;
				}
			}
			return spaceIndex != -1 && this.rawIndex != -1 && hasPower;
		}
	}

	public static boolean isAnalyzable(ItemStack stack){
		return FAMachineRecipeRegistry.getAnalyzerRecipeForItem(stack) != null;
		}

	public void analyzeItem() {
		if (this.canAnalyze()) {
			ItemStack output = ItemStack.EMPTY;
			Random random = this.world.rand;
			ItemStack input = this.stacks.get(rawIndex);
			output = FAMachineRecipeRegistry.getAnalyzerRecipeForItem(input).generateOutput(new Random());
			if(output.getCount() > 1){
				int maxCount = output.getCount() - 1;
				output.setCount(1 + random.nextInt(maxCount));
			}
			if (!output.isEmpty()) {
				for (int slot = 9; slot < 13; slot++) {
					ItemStack stack = this.stacks.get(slot);
					if (stack.isEmpty()) {
						this.stacks.set(slot, output);
						break;
					} else {
						if (stack.isItemEqual(output) && stack.getCount() + output.getCount() < 64) {
							stack.setCount(stack.getCount() + output.getCount());
							break;
						}
					}
				}
			}
			input.shrink(1);
		}
	}

	@Override
	public boolean isUsableByPlayer(EntityPlayer player) {
		return this.world.getTileEntity(this.pos) == this && player.getDistanceSq(this.pos.getX() + 0.5D, this.pos.getY() + 0.5D, this.pos.getZ() + 0.5D) <= 64.0D;
	}

	@Override
	public boolean isItemValidForSlot(int slot, ItemStack stack) {
		return (slot != 1 || isFuel(stack));
	}

	@Override
	public int getField(int id) {
		switch (id) {
			case 0:
				return this.analyzeFuelTime;
			case 1:
				return this.currentFuelTime;
			case 2:
				return this.analyzeTime;
			case 3:
				return this.energyStorage.energy;
			default:
				return 0;
		}
	}

	@Override
	public void setField(int id, int value) {
		switch (id) {
			case 0:
				this.analyzeFuelTime = value;
				break;
			case 1:
				this.currentFuelTime = value;
				break;
			case 2:
				this.analyzeTime = value;
				break;
			case 3:
				this.energyStorage.energy = value;
				break;
		}
	}

	@Override
	public int getFieldCount() {
		return 4;
	}

	@Override
	public void clear() {
		this.stacks.clear();
	}

	@Override
	public void openInventory(EntityPlayer player) {
		for (int slots = 12; slots > 8; --slots) {
			if (!this.stacks.get(slots).isEmpty()) {
				if (this.stacks.get(slots).getItem() == FAItemRegistry.STONE_TABLET) {
					//player.addStat(FossilAchievements.TABLET, 1);
				}
			}
		}
	}

	@Override
	public void closeInventory(EntityPlayer player) {
	}

	@Override
	public int[] getSlotsForFace(EnumFacing side) {
		return side == EnumFacing.DOWN ? SLOTS_BOTTOM : SLOTS_SIDES;
	}

	@Override
	public boolean canInsertItem(int index, ItemStack stack, EnumFacing direction) {
		return this.isItemValidForSlot(index, stack);
	}

	@Override
	public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
		return direction != EnumFacing.DOWN || index != 1;
	}

	@SuppressWarnings("deprecation")
	@Override
	public String getName() {
		return this.hasCustomName() ? this.customName : I18n.translateToLocal("tile.analyzer.name");
	}

	@Override
	public boolean hasCustomName() {
		return this.customName != null && this.customName.length() > 0;
	}

	@Override
	public SPacketUpdateTileEntity getUpdatePacket() {
		NBTTagCompound tag = new NBTTagCompound();
		this.writeToNBT(tag);
		return new SPacketUpdateTileEntity(pos, 1, tag);
	}

	@Override
	public void onDataPacket(NetworkManager netManager, net.minecraft.network.play.server.SPacketUpdateTileEntity packet) {
		readFromNBT(packet.getNbtCompound());
	}

	net.minecraftforge.items.IItemHandler handlerTop = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.UP);
	net.minecraftforge.items.IItemHandler handlerBottom = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.DOWN);
	@SuppressWarnings("unchecked")
	@Override
	@javax.annotation.Nullable
	public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @javax.annotation.Nullable net.minecraft.util.EnumFacing facing)
	{
		if(capability == CapabilityEnergy.ENERGY && Revival.CONFIG_OPTIONS.machinesRequireEnergy){
			return (T) energyStorage;
		}
		if (facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
			if (facing == EnumFacing.DOWN)
				return (T) handlerBottom;
			else
				return (T) handlerTop;
		return super.getCapability(capability, facing);
	}
}
