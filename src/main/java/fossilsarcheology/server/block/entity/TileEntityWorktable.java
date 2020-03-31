package fossilsarcheology.server.block.entity;

import fossilsarcheology.server.block.WorktableBlock;
import fossilsarcheology.server.item.FAItemRegistry;
import fossilsarcheology.server.recipe.FAMachineRecipeRegistry;
import fossilsarcheology.server.recipe.RecipeWorktable;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;

import javax.annotation.Nullable;

public class TileEntityWorktable extends TileEntity implements IInventory, ISidedInventory, ITickable {
    private static final int[] slots_top = new int[]{1}; // input
    private static final int[] slots_bottom = new int[]{2}; // output
    private static final int[] slots_sides = new int[]{0};// fuel
    public int furnaceBurnTime = 0;
    public int currentItemBurnTime = 0;
    public int furnaceCookTime = 0;
    net.minecraftforge.items.IItemHandler handlerTop = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.UP);
    net.minecraftforge.items.IItemHandler handlerBottom = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.DOWN);
    net.minecraftforge.items.IItemHandler handlerSide = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.WEST);
    private NonNullList<ItemStack> stacks = NonNullList.withSize(3, ItemStack.EMPTY);
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
    public ItemStack getStackInSlot(int var1) {
        return this.stacks.get(var1);
    }

    @Nullable
    @Override
    public ItemStack decrStackSize(int slot, int amount) {
        return ItemStackHelper.getAndSplit(this.stacks, slot, amount);
    }

    @Override
    public void readFromNBT(NBTTagCompound var1) {
        super.readFromNBT(var1);
        this.stacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(var1, this.stacks);

        this.furnaceBurnTime = var1.getShort("BurnTime");
        this.furnaceCookTime = var1.getShort("CookTime");
        this.currentItemBurnTime = this.getItemBurnTime(this.stacks.get(1));

        if (var1.hasKey("CustomName")) {
            this.customName = var1.getString("CustomName");
        }
    }

    @Override
    public int getInventoryStackLimit() {
        return 64;
    }

    @Override
    public boolean isUsableByPlayer(EntityPlayer player) {
        return true;
    }

    @Override
    public void openInventory(EntityPlayer player) {

    }

    @Override
    public void closeInventory(EntityPlayer player) {

    }

    @Override
    public boolean isItemValidForSlot(int index, ItemStack stack) {
        return true;
    }

    @Override
    public int getField(int id) {
        switch (id) {
            case 0:
                return this.furnaceCookTime;
            case 1:
                return this.furnaceBurnTime;
            case 2:
                return this.currentItemBurnTime;
            default:
                return 0;
        }
    }

    @Override
    public void setField(int id, int value) {
        switch (id) {
            case 0:
                this.furnaceCookTime = value;
                break;
            case 1:
                this.furnaceBurnTime = value;
                break;
            case 2:
                this.currentItemBurnTime = value;
                break;
        }
    }

    @Override
    public int getFieldCount() {
        return 3;
    }

    @Override
    public void clear() {
        this.stacks.clear();
    }

    public int getCookProgressScaled(int var1) {
        return this.furnaceCookTime * var1 / this.timeToSmelt();
    }

    public int getBurnTimeRemainingScaled(int var1) {
        if (this.currentItemBurnTime == 0) {
            this.currentItemBurnTime = timeToSmelt();
        }

        return this.furnaceBurnTime * var1 / this.currentItemBurnTime;
    }

    private int timeToSmelt() {
        if (this.stacks.get(0).isEmpty()) {
            return 3000;
        }

        if (this.stacks.get(0).getItem() == FAItemRegistry.BROKEN_HELMET) {
            return 3000;
        }

        if (this.stacks.get(0).getItem() == FAItemRegistry.BROKEN_SWORD) {
            return 3000;
        }

        return 300;
    }

    @Override
    public void update() {
        boolean var1 = this.furnaceBurnTime > 0;
        boolean var2 = false;

        if (this.furnaceBurnTime > 0) {
            --this.furnaceBurnTime;
        }

        if (!this.world.isRemote) {
            if (this.furnaceBurnTime == 0 && this.canSmelt()) {
                this.currentItemBurnTime = this.furnaceBurnTime = this.getItemBurnTime(this.stacks.get(1));

                if (this.furnaceBurnTime > 0) {
                    var2 = true;

                    if (!this.stacks.get(1).isEmpty()) {
                        if (this.stacks.get(1).getItem().hasContainerItem(null)) {
                            this.stacks.set(1, new ItemStack(this.stacks.get(1).getItem().getContainerItem()));
                        } else {
                            this.stacks.get(1).shrink(1);
                        }

                        if (this.stacks.get(1).getCount() == 0) {
                            this.stacks.set(1, ItemStack.EMPTY);
                        }
                    }
                }
            }

            if (this.isBurning() && this.canSmelt()) {
                ++this.furnaceCookTime;

                if (this.furnaceCookTime == timeToSmelt()) {
                    this.furnaceCookTime = 0;
                    this.smeltItem();
                    var2 = true;
                }
            } else {
                this.furnaceCookTime = 0;
            }

            if (var1 != this.furnaceBurnTime > 0) {
                var2 = true;
                WorktableBlock.setState(this.furnaceBurnTime > 0, this.world, pos);
            }
        }

        if (var2) {
            this.markDirty();
        }
    }

    public boolean isBurning() {
        return this.furnaceBurnTime > 0;
    }

    private ItemStack checkSmelt(ItemStack itemstack) {
        RecipeWorktable recipeWorktable = FAMachineRecipeRegistry.getWorktableRecipeForItem(itemstack);
        if (recipeWorktable != null) {
            return recipeWorktable.getOutput();
        }
        return null;
    }

    public void smeltItem() {
        if (this.canSmelt()) {
            ItemStack var1 = this.checkSmelt(this.stacks.get(0));

            if (this.stacks.get(2).isEmpty()) {
                if (var1 != null) {
                    this.stacks.set(2, var1.copy());
                }
            } else if (this.stacks.get(2).getItem() == var1.getItem()) {
                this.stacks.get(2).grow(var1.getCount());
            }

            if (this.stacks.get(0).getItem().hasContainerItem(ItemStack.EMPTY)) {
                this.stacks.set(0, new ItemStack(this.stacks.get(0).getItem().getContainerItem()));
            } else {
                this.stacks.get(0).shrink(1);
            }

            if (this.stacks.get(0).getCount() <= 0) {
                this.stacks.set(0, ItemStack.EMPTY);
            }
        }
    }

    private boolean canSmelt() {
        if (this.stacks.get(0).isEmpty()) {
            return false;
        } else {
            ItemStack var1 = this.checkSmelt(this.stacks.get(0));
            return var1 != null && !var1.isEmpty() && (this.stacks.get(2).isEmpty() || (this.stacks.get(2).isItemEqual(var1) && (this.stacks.get(2).getCount() < this.getInventoryStackLimit() && this.stacks.get(2).getCount() < this.stacks.get(2).getMaxStackSize() || this.stacks.get(2).getCount() < var1.getMaxStackSize())));
        }
    }

    private int getItemBurnTime(ItemStack itemstack) {
        if (itemstack.isEmpty()) {
            return 0;
        } else {
            Item var2 = itemstack.getItem();
            RecipeWorktable recipeWorktable = FAMachineRecipeRegistry.getWorktableRecipeForItem(this.stacks.get(0));
            if (recipeWorktable != null) {
                return var2 == recipeWorktable.getFuel().getItem() ? 300 : 0;
            }
            return 0;
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setShort("BurnTime", (short) this.furnaceBurnTime);
        nbt.setShort("CookTime", (short) this.furnaceCookTime);
        ItemStackHelper.saveAllItems(nbt, this.stacks);

        if (this.customName != null) {
            nbt.setString("CustomName", this.customName);
        }
        return nbt;
    }

    @Override
    public ItemStack removeStackFromSlot(int index) {
        return ItemStackHelper.getAndRemove(this.stacks, index);
    }

    @Override
    public void setInventorySlotContents(int index, ItemStack stack) {
        this.stacks.set(index, stack);

        if (!stack.isEmpty() && stack.getCount() > this.getInventoryStackLimit()) {
            stack.setCount(this.getInventoryStackLimit());
        }
    }

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        return side == EnumFacing.DOWN ? slots_bottom : (side == EnumFacing.UP ? slots_sides : slots_top);
    }

    @Override
    public boolean canInsertItem(int index, ItemStack stack, EnumFacing direction) {
        return this.isItemValidForSlot(index, stack);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        return direction != EnumFacing.DOWN || index != 1 || stack.getItem() == Items.BUCKET;
    }

    @Override
    public String getName() {
        return this.customName != null ? this.customName : "tile.worktable.name";
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
    public void onDataPacket(NetworkManager netManager, SPacketUpdateTileEntity packet) {
        readFromNBT(packet.getNbtCompound());
    }

    @SuppressWarnings("unchecked")
    @Override
    @javax.annotation.Nullable
    public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @javax.annotation.Nullable net.minecraft.util.EnumFacing facing) {
        if (facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            if (facing == EnumFacing.DOWN)
                return (T) handlerBottom;
            else if (facing == EnumFacing.UP)
                return (T) handlerTop;
            else
                return (T) handlerSide;
        return super.getCapability(capability, facing);
    }
}
