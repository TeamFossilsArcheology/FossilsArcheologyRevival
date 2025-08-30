package com.github.teamfossilsarcheology.fossil.block.entity;

import com.github.teamfossilsarcheology.fossil.block.custom_blocks.AnalyzerBlock;
import com.github.teamfossilsarcheology.fossil.config.FossilConfig;
import com.github.teamfossilsarcheology.fossil.inventory.AnalyzerMenu;
import com.github.teamfossilsarcheology.fossil.recipe.AnalyzerRecipe;
import com.github.teamfossilsarcheology.fossil.recipe.ModRecipes;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.network.chat.Component;
import net.minecraft.util.Mth;
import net.minecraft.world.SimpleContainer;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.ContainerData;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class AnalyzerBlockEntity extends EnergyContainerBlockEntity {

    private static final int[] SLOTS_FOR_UP = new int[]{0, 1, 2, 3, 4, 5, 6, 7, 8}; //Input
    private static final int[] SLOTS_FOR_DOWN = new int[]{9, 10, 11, 12}; //Output
    private final ContainerData dataAccess = new ContainerData() {

        @Override
        public int get(int index) {
            switch (index) {
                case 0 -> {
                    return cookingProgress;
                }
                case 1 -> {
                    return energyStorage.getEnergy();
                }
            }
            return 0;
        }

        @Override
        public void set(int index, int value) {
            switch (index) {
                case 0 -> cookingProgress = value;
            }
        }

        @Override
        public int getCount() {
            return 2;
        }
    };
    protected NonNullList<ItemStack> items = NonNullList.withSize(13, ItemStack.EMPTY);
    private int rawIndex = -1;

    public AnalyzerBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.ANALYZER.get(), blockPos, blockState);
    }

    @Override
    public ContainerData getDataAccess() {
        return dataAccess;
    }

    public static void serverTick(Level level, BlockPos pos, BlockState state, AnalyzerBlockEntity blockEntity) {
        blockEntity.serverTick(level, pos, state);
    }

    public void serverTick(Level level, BlockPos pos, BlockState state) {
        if (FossilConfig.isEnabled(FossilConfig.MACHINES_REQUIRE_ENERGY) && energyStorage.getEnergy() <= 0) {
            if (cookingProgress > 0) {
                cookingProgress = Mth.clamp(cookingProgress - 2, 0, AnalyzerMenu.ANALYZE_DURATION);
            }
            return;
        }
        boolean wasProcessing = cookingProgress > 0;
        boolean dirty = false;

        if (canProcess()) {
            cookingProgress++;
            if (cookingProgress >= AnalyzerMenu.ANALYZE_DURATION) {
                cookingProgress = 0;
                createItem();
                dirty = true;
            }
            if (FossilConfig.isEnabled(FossilConfig.MACHINES_REQUIRE_ENERGY)) {
                energyStorage.extractEnergy(FossilConfig.getInt(FossilConfig.MACHINE_ENERGY_USAGE));
            }
        } else {
            cookingProgress = Mth.clamp(cookingProgress - 2, 0, AnalyzerMenu.ANALYZE_DURATION);
        }

        if (wasProcessing != cookingProgress > 0) {
            dirty = true;
            state = state.setValue(AnalyzerBlock.ACTIVE, cookingProgress > 0);
            level.setBlock(pos, state, 3);
        }

        if (dirty) {
            setChanged(level, pos, state);
        }
    }

    private boolean isAnalyzable(ItemStack itemStack) {
        return ModRecipes.getAnalyzerRecipeForItem(new SimpleContainer(itemStack), level) != null;
    }

    @Override
    protected boolean canProcess() {
        if (FossilConfig.isEnabled(FossilConfig.MACHINES_REQUIRE_ENERGY) && energyStorage.getEnergy() <= FossilConfig.getInt(FossilConfig.MACHINE_ENERGY_USAGE)) {
            return false;
        }
        rawIndex = -1;
        for (int slot = 0; slot < 9; ++slot) {
            if (!items.get(slot).isEmpty() && isAnalyzable(items.get(slot))) {
                rawIndex = slot;
                break;
            }
        }
        if (rawIndex != -1) {
            for (int slot = 9; slot < 13; slot++) {
                if (items.get(slot).isEmpty()) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    protected void createItem() {
        if (canProcess()) {
            ItemStack input = items.get(rawIndex);
            AnalyzerRecipe recipe = ModRecipes.getAnalyzerRecipeForItem(new SimpleContainer(input), level);
            if (recipe == null) {
                return;
            }
            ItemStack output = recipe.assemble(this, level.registryAccess()).copy();
            if (output.getCount() > 1) {
                output.setCount(1 + level.random.nextInt(output.getCount() - 1));
            }
            if (!output.isEmpty()) {
                for (int slot = 9; slot < 13; slot++) {
                    ItemStack itemStack = items.get(slot);
                    if (ItemStack.isSameItem(itemStack, output) && itemStack.getCount() + output.getCount() < 64) {
                        itemStack.setCount(itemStack.getCount() + output.getCount());
                        input.shrink(1);
                        return;
                    }
                }
                for (int slot = 9; slot < 13; slot++) {
                    ItemStack itemStack = items.get(slot);
                    if (itemStack.isEmpty()) {
                        items.set(slot, output);
                        input.shrink(1);
                        return;
                    }
                }
            }
        }
    }

    @Override
    public @NotNull NonNullList<ItemStack> getItems() {
        return items;
    }

    @Override
    protected @NotNull Component getDefaultName() {
        return Component.translatable("container.fossil.analyzer");
    }

    @Override
    protected @NotNull AbstractContainerMenu createMenu(int containerId, @NotNull Inventory inventory) {
        return new AnalyzerMenu(containerId, inventory, this, dataAccess);
    }

    @Override
    public void setItem(int slot, @NotNull ItemStack stack) {
        items.set(slot, stack);
        if (stack.getCount() > getMaxStackSize()) {
            stack.setCount(getMaxStackSize());
        }
        setChanged();
    }

    @Override
    public boolean canPlaceItem(int slot, @NotNull ItemStack stack) {
        return slot < SLOTS_FOR_DOWN[0] && isAnalyzable(stack);
    }

    @Override
    public int @NotNull [] getSlotsForFace(@NotNull Direction side) {
        return side == Direction.DOWN ? SLOTS_FOR_DOWN : SLOTS_FOR_UP;
    }

    @Override
    public boolean canPlaceItemThroughFace(int slot, @NotNull ItemStack itemStack, @Nullable Direction direction) {
        return canPlaceItem(slot, itemStack);
    }

    @Override
    public boolean canTakeItemThroughFace(int slot, @NotNull ItemStack stack, @NotNull Direction direction) {
        return direction != Direction.UP && slot >= SLOTS_FOR_DOWN[0];
    }
}
