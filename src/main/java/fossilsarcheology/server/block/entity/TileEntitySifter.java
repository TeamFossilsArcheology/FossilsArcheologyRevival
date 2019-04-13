package fossilsarcheology.server.block.entity;

import fossilsarcheology.server.block.SifterBlock;
import fossilsarcheology.server.recipe.FAMachineRecipeRegistry;
import net.minecraft.block.BlockConcretePowder;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.inventory.IInventory;
import net.minecraft.inventory.ISidedInventory;
import net.minecraft.inventory.ItemStackHelper;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.util.NonNullList;

import javax.annotation.Nullable;
import java.util.Random;

public class TileEntitySifter extends TileEntity implements IInventory, ISidedInventory, ITickable {

    private static final int[] slots_bottom = new int[] { 1, 2, 3, 4, 5 }; // output
    private static final int[] slots_top = new int[] { 0 };// fuel
    public int sifterBurnTime = 0;
    public int currentItemBurnTime = 0;
    public int sifterCookTime = 0;
    private String customName;
    private NonNullList<ItemStack> stacks = NonNullList.withSize(6, ItemStack.EMPTY);
    private int rawIndex = -1;
    private int spaceIndex = -1;

    private static int getItemBurnTime(ItemStack stack) {
        return 100;
    }

    public static boolean isItemFuel(ItemStack stack) {
        return getItemBurnTime(stack) > 0;
    }

    @Override
    public int getSizeInventory() {
        return this.stacks.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack stack : this.stacks) {
            if (!stack.isEmpty()) {
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
    public ItemStack decrStackSize(int slot, int count) {
        if (!this.stacks.get(slot).isEmpty()) {
            if (this.stacks.get(slot).getCount() <= count) {
                ItemStack var3 = this.stacks.get(slot);
                this.stacks.set(slot, ItemStack.EMPTY);
                return var3;
            } else {
                ItemStack var3 = this.stacks.get(slot).splitStack(count);
                if (this.stacks.get(slot).getCount() == 0) {
                    this.stacks.set(slot, ItemStack.EMPTY);
                }
                return var3;
            }
        }
        return ItemStack.EMPTY;
    }

    @Nullable
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

    @Override
    public void readFromNBT(NBTTagCompound var1) {
        super.readFromNBT(var1);
        this.stacks = NonNullList.withSize(this.getSizeInventory(), ItemStack.EMPTY);
        ItemStackHelper.loadAllItems(var1, this.stacks);

        this.sifterBurnTime = var1.getShort("BurnTime");
        this.sifterCookTime = var1.getShort("CookTime");
        this.currentItemBurnTime = 100;
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound var1) {
        super.writeToNBT(var1);
        var1.setShort("BurnTime", (short) this.sifterBurnTime);
        var1.setShort("CookTime", (short) this.sifterCookTime);
        ItemStackHelper.saveAllItems(var1, this.stacks);
        return var1;
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

    public int getSiftProgressScaled(int var1) {
        return this.sifterCookTime * var1 / 200;
    }

    public int getSiftTimeRemainingScaled(int var1) {
        if (this.currentItemBurnTime == 0) {
            this.currentItemBurnTime = 100;
        }

        return this.sifterBurnTime * var1 / this.currentItemBurnTime;
    }

    public boolean isBurning() {
        return this.sifterBurnTime > 0;
    }

    @Override
    public void update() {
        boolean var1 = this.sifterBurnTime > 0;
        boolean var2 = false;
        if (this.sifterBurnTime > 0) {
            --this.sifterBurnTime;
        }

        if (!this.world.isRemote) {
            if (this.sifterBurnTime == 0 && this.canSmelt()) {
                this.currentItemBurnTime = this.sifterBurnTime = 100;

                if (this.sifterBurnTime > 0) {
                    var2 = true;
                }
            }

            if (this.isBurning() && this.canSmelt()) {
                ++this.sifterCookTime;

                if (this.sifterCookTime == 200) {
                    this.sifterCookTime = 0;
                    this.smeltItem();
                    var2 = true;
                }
            } else {
                this.sifterCookTime = 0;
            }

            if (var1 != this.sifterBurnTime > 0) {
                var2 = true;
                SifterBlock.setState(this.sifterBurnTime > 0, this.world, this.pos);
            }
        }

        if (var2) {
            this.markDirty();
        }
    }

    public static boolean isAnalyzable(ItemStack stack){
        return FAMachineRecipeRegistry.getSifterRecipeForItem(stack) != null;
    }

    private boolean canSmelt() {
        this.spaceIndex = -1;
        this.rawIndex = -1;
        for (int var1 = 0; var1 < 1; ++var1) {
            if (!this.stacks.get(var1).isEmpty()) {
                if (isAnalyzable(this.stacks.get(var1))) {
                    this.rawIndex = var1;
                    break;
                }
            }
        }
        if (this.rawIndex == -1) {
            return false;
        } else {
            for (int var1 = 5; var1 > 0; --var1) {
                if (this.stacks.get(var1).isEmpty()) {
                    this.spaceIndex = var1;
                    break;
                }
            }
            return this.spaceIndex != -1 && this.rawIndex != -1;
        }
    }

    public void smeltItem() {
        if (this.canSmelt()) {
            ItemStack result = ItemStack.EMPTY;
            result = FAMachineRecipeRegistry.getSifterRecipeForItem(this.stacks.get(rawIndex)).generateOutput(new Random());
            for (int slots = 1; slots < 5; slots++) {
                ItemStack stackInSlot = this.stacks.get(slots);
                if (!stackInSlot.isEmpty()) {
                    if (stackInSlot.isItemEqual(result) && stackInSlot.getCount() + result.getCount() < 64) {
                        stackInSlot.grow(result.getCount());
                        if (this.stacks.get(this.rawIndex).getCount() > 1) {
                            this.stacks.get(this.rawIndex).shrink(1);
                        } else {
                            this.stacks.set(this.rawIndex, ItemStack.EMPTY);
                        }
                        break;
                    }
                } else if (stackInSlot.isEmpty()) {
                    this.stacks.set(slots, result);
                    this.stacks.get(this.rawIndex).shrink(1);
                    break;
                }
            }
        }
    }

    public boolean isStackValidForSlot(int par1, ItemStack par2ItemStack) {
        return par1 <= 8 && (par1 >= 8 || isItemFuel(par2ItemStack));
    }

    @Override
    public boolean isItemValidForSlot(int par1, ItemStack par2ItemStack) {
        return par1 == 0 && isItemFuel(par2ItemStack);
    }

    @Override
    public int getField(int id) {
        switch (id) {
            case 0:
                return this.sifterCookTime;
            case 1:
                return this.sifterBurnTime;
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
                this.sifterCookTime = value;
                break;
            case 1:
                this.sifterBurnTime = value;
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

    @Override
    public int[] getSlotsForFace(EnumFacing side) {
        return side == EnumFacing.UP ? slots_top : slots_bottom;
    }

    @Override
    public boolean canInsertItem(int index, ItemStack itemStackIn, EnumFacing direction) {
        return this.isItemValidForSlot(index, itemStackIn);
    }

    @Override
    public boolean canExtractItem(int index, ItemStack stack, EnumFacing direction) {
        return direction != EnumFacing.UP || index != 1 || stack.getItem() == Items.BUCKET;
    }

    @Override
    public String getName() {
        return "tile.sifter.name";
    }

    @Override
    public boolean hasCustomName() {
        return false;
    }

    public enum EnumSiftType{
        NONE, GROUND, SAND;
    }

    public static EnumSiftType getSiftTypeFromStack(ItemStack stack){
        if(stack.getItem() instanceof ItemBlock){
            IBlockState block = ((ItemBlock) stack.getItem()).getBlock().getDefaultState();
            if(block.getMaterial() == Material.SAND && !(block.getBlock() instanceof BlockConcretePowder)){
                return EnumSiftType.SAND;
            }
            if(block.getMaterial() == Material.GROUND){
                return EnumSiftType.GROUND;
            }
        }
        return EnumSiftType.NONE;
    }

    net.minecraftforge.items.IItemHandler handlerTop = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.UP);
    net.minecraftforge.items.IItemHandler handlerBottom = new net.minecraftforge.items.wrapper.SidedInvWrapper(this, net.minecraft.util.EnumFacing.DOWN);

    @SuppressWarnings("unchecked")
    @Override
    @javax.annotation.Nullable
    public <T> T getCapability(net.minecraftforge.common.capabilities.Capability<T> capability, @javax.annotation.Nullable net.minecraft.util.EnumFacing facing) {
        if (facing != null && capability == net.minecraftforge.items.CapabilityItemHandler.ITEM_HANDLER_CAPABILITY)
            if (facing == EnumFacing.DOWN)
                return (T) handlerBottom;
            else
                return (T) handlerTop;
        return super.getCapability(capability, facing);
    }
}
