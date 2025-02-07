package com.github.teamfossilsarcheology.fossil.block.entity;

import com.github.teamfossilsarcheology.fossil.block.custom_blocks.FeederBlock;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.inventory.FeederMenu;
import com.github.teamfossilsarcheology.fossil.util.Diet;
import com.github.teamfossilsarcheology.fossil.util.FoodMappings;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.ContainerHelper;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class FeederBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer {
    public static final String MEAT = "Meat";
    public static final String PLANT = "Plant";
    private static final int[] SLOTS_TOP = new int[]{FeederMenu.MEAT_SLOT_ID, FeederMenu.PLANT_SLOT_ID};
    protected NonNullList<ItemStack> items = NonNullList.withSize(2, ItemStack.EMPTY);
    private int meat;
    private int plant;
    private final ContainerData dataAccess = new ContainerData() {

        @Override
        public int get(int index) {
            switch (index) {
                case 0 -> {
                    return meat;
                }
                case 1 -> {
                    return plant;
                }
            }
            return 0;
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> meat = value;
                case 1 -> plant = value;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    };
    private int prevMeat;
    private int prevPlant;
    private int ticksExisted;

    public FeederBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.FEEDER.get(), blockPos, blockState);
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, FeederBlockEntity blockEntity) {
        blockEntity.ticksExisted++;
        blockEntity.prevMeat = blockEntity.meat;
        blockEntity.prevPlant = blockEntity.plant;
        blockEntity.meat = Math.max(blockEntity.meat, 0);
        blockEntity.plant = Math.max(blockEntity.plant, 0);
        boolean dirty = false;
        ItemStack foodStack = blockEntity.getItem(FeederMenu.MEAT_SLOT_ID);
        if (!foodStack.isEmpty()) {
            if (blockEntity.canPlaceItem(FeederMenu.MEAT_SLOT_ID, foodStack) && blockEntity.ticksExisted % 5 == 0 && blockEntity.meat < 10000) {
                int foodPoints = FoodMappings.getFoodAmount(foodStack.getItem(), Diet.CARNIVORE_EGG);
                if (foodPoints == 0) {
                    foodPoints = FoodMappings.getFoodAmount(foodStack.getItem(), Diet.PISCIVORE);
                }
                if (foodPoints > 0) {
                    dirty = true;
                    blockEntity.meat += foodPoints;
                    foodStack.shrink(1);
                }
            }
        }
        foodStack = blockEntity.getItem(FeederMenu.PLANT_SLOT_ID);
        if (!foodStack.isEmpty()) {
            if (blockEntity.canPlaceItem(FeederMenu.PLANT_SLOT_ID, foodStack) && blockEntity.ticksExisted % 5 == 0 && blockEntity.plant < 10000) {
                int foodPoints = FoodMappings.getFoodAmount(foodStack.getItem(), Diet.HERBIVORE);
                if (foodPoints > 0) {
                    dirty = true;
                    blockEntity.plant += foodPoints;
                    foodStack.shrink(1);
                }
            }
        }
        if (blockEntity.prevMeat != blockEntity.meat || blockEntity.prevPlant != blockEntity.plant) {
            dirty = true;
            state = state.setValue(FeederBlock.HERB, blockEntity.plant > 0).setValue(FeederBlock.CARN, blockEntity.meat > 0);
            level.setBlock(pos, state, 3);
        }
        if (dirty) {
            setChanged(level, pos, state);
        }
    }

    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, this.items);
        this.meat = tag.getShort(MEAT);
        this.plant = tag.getShort(PLANT);
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        tag.putShort(MEAT, (short) this.meat);
        tag.putShort(PLANT, (short) this.plant);
        ContainerHelper.saveAllItems(tag, this.items);
    }

    @Override
    protected @NotNull Component getDefaultName() {
        return new TranslatableComponent("container.fossil.feeder");
    }

    @Override
    protected @NotNull AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new FeederMenu(containerId, inventory, this, dataAccess);
    }

    @Override
    public int getContainerSize() {
        return items.size();
    }

    @Override
    public boolean isEmpty() {
        for (ItemStack itemStack : items) {
            if (itemStack.isEmpty()) continue;
            return false;
        }
        return true;
    }

    public boolean isEmpty(Diet diet) {
        if (diet == Diet.CARNIVORE || diet == Diet.CARNIVORE_EGG || diet == Diet.PISCI_CARNIVORE || diet == Diet.PISCIVORE || diet == Diet.INSECTIVORE) {
            return meat == 0;
        }
        if (diet == Diet.HERBIVORE) {
            return plant == 0;
        }
        return diet == Diet.OMNIVORE && meat == 0 && plant == 0;
    }

    public void feedDinosaur(Prehistoric mob) {
        if (level != null) {
            int feedAmount = 0;
            if (!isEmpty(mob.data().diet())) {
                if (mob.data().diet() == Diet.CARNIVORE || mob.data().diet() == Diet.CARNIVORE_EGG || mob.data().diet() == Diet.PISCI_CARNIVORE || mob.data().diet() == Diet.PISCIVORE || mob.data().diet() == Diet.INSECTIVORE) {
                    meat--;
                    feedAmount++;
                }
                if (mob.data().diet() == Diet.HERBIVORE) {
                    plant--;
                    feedAmount++;
                }
                if (mob.data().diet() == Diet.OMNIVORE) {
                    if (meat == 0 && plant != 0) {
                        plant--;
                        feedAmount++;
                    } else if (meat != 0 && plant == 0) {
                        meat--;
                        feedAmount++;
                    } else if (meat != 0) {
                        meat--;
                        feedAmount++;
                    }
                }
            }
            if (feedAmount > 0) {
                BlockState blockState = level.getBlockState(getBlockPos()).setValue(FeederBlock.HERB, plant > 0).setValue(FeederBlock.CARN, meat > 0);
                level.setBlockAndUpdate(getBlockPos(), blockState);
                setChanged(level, getBlockPos(), blockState);
                mob.feed(feedAmount);
            }
        }
    }

    @Override
    public @NotNull ItemStack getItem(int slot) {
        return items.get(slot);
    }

    @Override
    public @NotNull ItemStack removeItem(int slot, int amount) {
        return ContainerHelper.removeItem(items, slot, amount);
    }

    @Override
    public @NotNull ItemStack removeItemNoUpdate(int slot) {
        return ContainerHelper.takeItem(items, slot);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        items.set(slot, stack);
        if (stack.getCount() > getMaxStackSize()) {
            stack.setCount(getMaxStackSize());
        }
    }

    @Override
    public boolean stillValid(Player player) {
        return level.getBlockEntity(worldPosition) == this && player.distanceToSqr(worldPosition.getX() + 0.5,
                worldPosition.getY() + 0.5, worldPosition.getZ() + 0.5) <= 64.0;
    }

    @Override
    public void clearContent() {
        items.clear();
    }

    @Override
    public int @NotNull [] getSlotsForFace(Direction side) {
        return side != Direction.DOWN ? SLOTS_TOP : new int[]{};
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack itemStack, @Nullable Direction direction) {
        return canPlaceItem(index, itemStack);
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return direction != Direction.DOWN;
    }

    public void setMeat(int meat) {
        this.meat = meat;
    }

    public void setPlant(int plant) {
        this.plant = plant;
    }

    public int getMeat() {
        return meat;
    }

    public int getPlant() {
        return plant;
    }
}
