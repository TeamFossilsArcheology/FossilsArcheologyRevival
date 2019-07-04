package fossilsarcheology.server.block.entity;

import com.google.common.base.Strings;
import fossilsarcheology.server.block.CultivateBlock;
import fossilsarcheology.server.block.FABlockRegistry;
import fossilsarcheology.server.entity.prehistoric.PrehistoricEntityType;
import fossilsarcheology.server.item.BirdEggItem;
import fossilsarcheology.server.item.FAItemRegistry;
import fossilsarcheology.server.recipe.FAMachineRecipeRegistry;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.NetworkManager;
import net.minecraft.network.play.server.SPacketUpdateTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.ITickable;
import net.minecraft.world.IWorldNameable;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.items.CapabilityItemHandler;
import net.minecraftforge.items.IItemHandlerModifiable;
import net.minecraftforge.items.ItemStackHandler;
import net.minecraftforge.items.wrapper.CombinedInvWrapper;

import javax.annotation.Nullable;
import java.util.Random;

public class TileEntityCultivate extends TileEntity implements ITickable, IWorldNameable {
    public int fuelTime = 0;
    public int totalFuelTime = 0;
    public int cultivationTime = 0;
    public boolean isActive;
    private String customName;
    public boolean isPlant;
    public final IItemHandlerModifiable inputInventory = new ItemStackHandler(1);
    private final IItemHandlerModifiable fuelInventory = new ItemStackHandler(1);
    private final IItemHandlerModifiable outputInventory = new ItemStackHandler(1);
    private final IItemHandlerModifiable wrappedOutputInventory = new DirectionalInvWrapper(this.outputInventory, DirectionalInvWrapper.Mode.OUTPUT);
    private final IItemHandlerModifiable globalInventory = new CombinedInvWrapper(this.inputInventory, this.fuelInventory, this.outputInventory);

    public static int getItemFuelTime(ItemStack stack) {
        if (!stack.isEmpty()) {
            Item output = stack.getItem();

            if (output == FAItemRegistry.BIOFOSSIL) {
                return 300;
            }

            if (output == Items.PORKCHOP) {
                return 3000;
            }

            if (output == Items.MUTTON) {
                return 3000;
            }

            if (output == Items.FISH) {
                return 1500;
            }

            if (output == Items.COOKED_FISH) {
                return 1500;
            }

            if (output == Items.BEEF) {
                return 4000;
            }

            if (output == Items.CHICKEN) {
                return 1500;
            }

            if (output == Items.RABBIT_FOOT) {
                return 500;
            }

            if (output == Items.RABBIT) {
                return 1500;
            }

            if (output == Items.EGG) {
                return 1000;
            }

            if (output instanceof BirdEggItem) {
                return 1000;
            }

            if (output instanceof ItemFood && ((ItemFood) output).isWolfsFavoriteMeat()) {
                return 1500;
            }

            if (output == Items.SLIME_BALL) {
                return 800;
            }

            if (output == Items.MILK_BUCKET) {
                return 6000;
            }
        }

        return 0;
    }

    public static ItemStack getCultivationOutput(ItemStack input) {
        if (input.getItem() == FAItemRegistry.FOSSIL_SEED_FERN) {
            return new ItemStack(FAItemRegistry.FERN_SEED, 1);
        }
        if (input.getItem() == FAItemRegistry.PALAE_SAPLING_FOSSIL) {
            return new ItemStack(FABlockRegistry.PALM_SAPLING, 1);
        }
        if (input.getItem() == FAItemRegistry.CALAMITES_SAPLING_FOSSIL) {
            return new ItemStack(FABlockRegistry.CALAMITES_SAPLING, 1);
        }
        if (input.getItem() == FAItemRegistry.FOSSIL_SEED) {
            return new ItemStack(FAItemRegistry.SEED, 1, input.getItemDamage());
        }

        if (PrehistoricEntityType.getEgg(input.getItem()) != null) {
            return new ItemStack(PrehistoricEntityType.getEgg(input.getItem()), 1);
        }

        if (PrehistoricEntityType.getEmbryo(input.getItem()) != null) {
            return new ItemStack(PrehistoricEntityType.getEmbryo(input.getItem()), 1);
        }
        if (PrehistoricEntityType.getBestBirdEgg(input.getItem()) != null) {
            return new ItemStack(PrehistoricEntityType.getBestBirdEgg(input.getItem()), 1);
        }

        return ItemStack.EMPTY;
    }

    @Override
    public void readFromNBT(NBTTagCompound nbt) {
        super.readFromNBT(nbt);

        CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.readNBT(this.globalInventory, null, nbt.getTag("inventory"));

        this.fuelTime = nbt.getShort("fuel_time");
        this.cultivationTime = nbt.getShort("cultivation_time");
        this.isPlant = nbt.getBoolean("is_plant");
        this.totalFuelTime = getItemFuelTime(this.fuelInventory.getStackInSlot(0));

        if (nbt.hasKey("CustomName")) {
            this.customName = nbt.getString("CustomName");
        }
    }

    @Override
    public NBTTagCompound writeToNBT(NBTTagCompound nbt) {
        super.writeToNBT(nbt);
        nbt.setTag("inventory", CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.writeNBT(this.globalInventory, null));
        nbt.setShort("fuel_time", (short) this.fuelTime);
        nbt.setBoolean("is_plant", this.isPlant);
        nbt.setShort("cultivation_time", (short) this.cultivationTime);
        if (customName != null) {
            nbt.setString("CustomName", this.customName);
        }
        return nbt;
    }

    private boolean isSeed(ItemStack stack){
      return stack.getItem() == FAItemRegistry.FOSSIL_SEED_FERN || stack.getItem() == FAItemRegistry.CALAMITES_SAPLING_FOSSIL || stack.getItem() == FAItemRegistry.PALAE_SAPLING_FOSSIL || stack.getItem() == FAItemRegistry.FOSSIL_SEED;
    }

    @Override
    public void update() {
        boolean wasActive = this.cultivationTime > 0;
        boolean dirty = false;
        isActive = this.cultivationTime > 0;
        if(this.inputInventory.getStackInSlot(0).isEmpty()){
            isPlant = isSeed((this.inputInventory.getStackInSlot(0)));
        }
        if (this.fuelTime > 0) {
            --this.fuelTime;
        }

        if (!this.world.isRemote) {
            if (this.fuelTime == 0 && this.canCultivate()) {
                ItemStack fuelStack = this.fuelInventory.getStackInSlot(0);
                this.totalFuelTime = this.fuelTime = getItemFuelTime(fuelStack);

                if (this.fuelTime > 0) {
                    dirty = true;

                    if (!fuelStack.isEmpty()) {
                        ItemStack containerItem = fuelStack.getItem().getContainerItem(fuelStack);
                        if (!containerItem.isEmpty()) {
                            this.fuelInventory.setStackInSlot(0, containerItem);
                        } else {
                            fuelStack.shrink(1);
                        }
                    }
                }
            }

            if (this.fuelTime > 0 && this.canCultivate()) {
                this.cultivationTime++;

                if (this.cultivationTime >= 6000) {
                    this.cultivationTime = 0;
                    this.cultivate();
                    dirty = true;
                }
            } else if (this.cultivationTime != 0 && !this.canCultivate()) {
                this.cultivationTime = 0;
            }

            if (wasActive != this.cultivationTime > 0) {
                dirty = true;
                isPlant = isSeed((this.inputInventory.getStackInSlot(0)));
                CultivateBlock.setState(this.cultivationTime > 0, this.world, this.pos);
            }
        }

        if (dirty) {
            this.markDirty();
        }

        if (this.cultivationTime == 3001 && new Random().nextInt(100) < 20) {
            FABlockRegistry.CULTIVATE_IDLE.onBlockRemovalLost(world, pos, true);
        }
    }

    private boolean canCultivate() {
        ItemStack inputStack = this.inputInventory.getStackInSlot(0);
        if (!inputStack.isEmpty()) {
            ItemStack cultivatedStack = FAMachineRecipeRegistry.getCultivateResult(inputStack).copy();
            if (cultivatedStack.isEmpty()) {
                return false;
            }
            return this.outputInventory.insertItem(0, cultivatedStack, true).isEmpty();
        }
        return false;
    }

    public void cultivate() {
        if (this.canCultivate()) {
            ItemStack inputStack = this.inputInventory.extractItem(0, 1, false);
            ItemStack cultivatedStack = FAMachineRecipeRegistry.getCultivateResult(inputStack).copy();
            this.outputInventory.insertItem(0, cultivatedStack, false);

            ItemStack containerStack = inputStack.getItem().getContainerItem(inputStack);
            if (!containerStack.isEmpty()) {
                this.inputInventory.setStackInSlot(0, containerStack);
            }
        }
    }

    public void setCustomName(String name) {
        this.customName = name;
    }

    public int getDNAType() {
        ItemStack inputStack = this.inputInventory.getStackInSlot(0);
        if (!inputStack.isEmpty()) {
            if (inputStack.getItem() == PrehistoricEntityType.COELACANTH.dnaItem || inputStack.getItem() == PrehistoricEntityType.STURGEON.dnaItem || inputStack.getItem() == PrehistoricEntityType.ALLIGATOR_GAR.dnaItem) {
                return 1;
            }
            if (inputStack.getItem() == FAItemRegistry.FOSSIL_SEED_FERN || inputStack.getItem() == FAItemRegistry.CALAMITES_SAPLING_FOSSIL || inputStack.getItem() == FAItemRegistry.PALAE_SAPLING_FOSSIL || inputStack.getItem() == FAItemRegistry.FOSSIL_SEED) {
                return 2;
            }
        }
        return 0;
    }

    @Override
    public boolean hasCapability(Capability<?> capability, @Nullable EnumFacing facing) {
        return capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY || super.hasCapability(capability, facing);
    }

    @Nullable
    @Override
    public <T> T getCapability(Capability<T> capability, @Nullable EnumFacing facing) {
        if (capability == CapabilityItemHandler.ITEM_HANDLER_CAPABILITY) {
            if (facing == null) {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.globalInventory);
            } else if (facing == EnumFacing.UP) {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.fuelInventory);
            } else if (facing == EnumFacing.DOWN) {
                return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.wrappedOutputInventory);
            }
            return CapabilityItemHandler.ITEM_HANDLER_CAPABILITY.cast(this.inputInventory);
        }
        return super.getCapability(capability, facing);
    }

    @Override
    public SPacketUpdateTileEntity getUpdatePacket() {
        NBTTagCompound tag = new NBTTagCompound();
        this.writeToNBT(tag);
        return new SPacketUpdateTileEntity(pos, 0, tag);
    }

    @Override
    public void onDataPacket(NetworkManager netManager, SPacketUpdateTileEntity packet) {
        readFromNBT(packet.getNbtCompound());
    }

    @Override
    public NBTTagCompound getUpdateTag() {
        return this.writeToNBT(new NBTTagCompound());
    }

    @Override
    public String getName() {
        return this.hasCustomName() ? this.customName : "tile.cultivate.name";
    }

    @Override
    public boolean hasCustomName() {
        return !Strings.isNullOrEmpty(this.customName);
    }
}
