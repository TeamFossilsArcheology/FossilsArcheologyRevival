package com.fossil.fossil.block.entity;

import com.fossil.fossil.block.custom_blocks.CultivateBlock;
import com.fossil.fossil.inventory.CultivateMenu;
import com.fossil.fossil.recipe.ModRecipes;
import com.fossil.fossil.recipe.WorktableRecipe;
import com.fossil.fossil.util.Diet;
import com.fossil.fossil.util.FoodMappings;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Random;

public class CultivateBlockEntity extends CustomBlockEntity {
    private static final int[] SLOTS_FOR_UP = new int[]{CultivateMenu.INPUT_SLOT_ID}; //Input
    private static final int[] SLOTS_FOR_SIDES = new int[]{CultivateMenu.FUEL_SLOT_ID, CultivateMenu.OUTPUT_SLOT_ID}; //Fuel+Output
    private static final int[] SLOTS_FOR_DOWN = new int[]{CultivateMenu.OUTPUT_SLOT_ID}; //Output

    protected NonNullList<ItemStack> items = NonNullList.withSize(3, ItemStack.EMPTY);
    private final ContainerData dataAccess = new ContainerData() {

        @Override
        public int get(int index) {
            switch (index) {
                case 0 -> {
                    return litTime;
                }
                case 1 -> {
                    return litDuration;
                }
                case 2 -> {
                    return cookingProgress;
                }
            }
            return 0;
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> litTime = value;
                case 1 -> litDuration = value;
                case 2 -> cookingProgress = value;
            }
        }

        @Override
        public int getCount() {
            return 3;
        }
    };

    public CultivateBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.CULTIVATE.get(), blockPos, blockState);
    }

    public static int getItemFuelTime(ItemStack stack) {
        Integer fuel = ModRecipes.CULTIVATE_FUEL_VALUES.get(stack.getItem());
        if (fuel != null) {
            return fuel;
        }
        return 0;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, CultivateBlockEntity blockEntity) {
        boolean wasActive = blockEntity.cookingProgress > 0;
        boolean dirty = false;
        if (blockEntity.items.get(CultivateMenu.INPUT_SLOT_ID).isEmpty()) {
            //isPlant = isSeed((blockEntity.items.get(INPUT_SLOT_ID)));
        }
        if (blockEntity.isProcessing()) {
            --blockEntity.litTime;
        }

        if (blockEntity.litTime == 0 && blockEntity.canProcess()) {
            ItemStack fuelStack = blockEntity.items.get(CultivateMenu.FUEL_SLOT_ID);
            blockEntity.litDuration = blockEntity.litTime = getItemFuelTime(fuelStack);

            if (blockEntity.isProcessing()) {
                dirty = true;
                if (!fuelStack.isEmpty()) {
                    if (fuelStack.getItem().hasCraftingRemainingItem()) {
                        blockEntity.items.set(CultivateMenu.FUEL_SLOT_ID, new ItemStack(fuelStack.getItem().getCraftingRemainingItem()));
                    } else {
                        fuelStack.shrink(1);
                    }
                }
            }
        }

        if (blockEntity.isProcessing() && blockEntity.canProcess()) {
            blockEntity.cookingProgress++;
            if (blockEntity.cookingProgress >= 6000) {
                blockEntity.cookingProgress = 0;
                blockEntity.createItem();
                dirty = true;
            }
        } else if (blockEntity.cookingProgress != 0 && !blockEntity.canProcess()) {
            blockEntity.cookingProgress = 0;
        }

        if (wasActive != blockEntity.cookingProgress > 0) {
            dirty = true;
            //isPlant = isSeed((blockEntity.items.get(INPUT_SLOT_ID)));
            state = state.setValue(CultivateBlock.ACTIVE, blockEntity.cookingProgress > 0);
            state = state.setValue(CultivateBlock.EMBRYO, getDNAType(blockEntity));
            level.setBlock(pos, state, 3);
        }

        if (dirty) {
            setChanged(level, pos, state);
        }

        if (blockEntity.cookingProgress == 3001 && new Random().nextInt(100) < 20) {
            // FABlockRegistry.CULTIVATE_IDLE.onBlockRemovalLost(world, pos, true);
        }
    }

    protected boolean canProcess() {
        ItemStack inputStack = items.get(CultivateMenu.INPUT_SLOT_ID);
        if (!inputStack.isEmpty()) {
            WorktableRecipe recipe = ModRecipes.getCultivateRecipeForItem(inputStack, level);
            if (recipe != null) {
                ItemStack output = items.get(CultivateMenu.OUTPUT_SLOT_ID);
                return output.isEmpty() || output.sameItem(recipe.getOutput());
            }
        }
        return false;
    }

    protected void createItem() {
        if (this.canProcess()) {
            ItemStack inputStack = items.get(CultivateMenu.INPUT_SLOT_ID);

            WorktableRecipe recipe = ModRecipes.getCultivateRecipeForItem(inputStack, level);
            ItemStack result = recipe.getOutput().copy();
            ItemStack output = items.get(CultivateMenu.OUTPUT_SLOT_ID);
            if (output.isEmpty()) {
                items.set(CultivateMenu.OUTPUT_SLOT_ID, result);
            } else if (output.sameItem(result)) {
                output.grow(result.getCount());
            }
            this.items.get(CultivateMenu.INPUT_SLOT_ID).shrink(1);
        }
    }

    @Override
    public NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected @NotNull Component getDefaultName() {
        return new TranslatableComponent("fossil.container.cultivate");
    }

    public static CultivateBlock.EmbryoType getDNAType(CultivateBlockEntity blockEntity) {
        ItemStack inputStack = blockEntity.items.get(CultivateMenu.INPUT_SLOT_ID);
        if (!inputStack.isEmpty()) {
            if (inputStack.is(Items.WHEAT_SEEDS)) {
                return CultivateBlock.EmbryoType.PLANT;
            }
        }
        return CultivateBlock.EmbryoType.GENERIC;
    }

    @Override
    protected @NotNull AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new CultivateMenu(containerId, inventory, this, dataAccess);
    }

    @Override
    public void setItem(int slot, ItemStack stack) {
        items.set(slot, stack);
        if (stack.getCount() > getMaxStackSize()) {
            stack.setCount(getMaxStackSize());
        }
        setChanged();
    }

    @Override
    public boolean canPlaceItem(int index, ItemStack stack) {
        if (index == 0) {
            return FoodMappings.getItemFoodAmount(stack, Diet.CARNIVORE_EGG) > 0 || FoodMappings.getItemFoodAmount(stack, Diet.PISCCARNIVORE) > 0;
        }
        if (index == 1) {
            return FoodMappings.getItemFoodAmount(stack, Diet.HERBIVORE) > 0;
        }
        return false;
    }

    @Override
    public int @NotNull [] getSlotsForFace(Direction side) {
        return side == Direction.DOWN ? SLOTS_FOR_DOWN : (side == Direction.UP ? SLOTS_FOR_UP : SLOTS_FOR_SIDES);
    }

    @Override
    public boolean canPlaceItemThroughFace(int index, ItemStack itemStack, @Nullable Direction direction) {
        return canPlaceItem(index, itemStack);
    }

    @Override
    public boolean canTakeItemThroughFace(int index, ItemStack stack, Direction direction) {
        return direction != Direction.DOWN || index != 1 || stack.is(Items.BUCKET);
    }
}
