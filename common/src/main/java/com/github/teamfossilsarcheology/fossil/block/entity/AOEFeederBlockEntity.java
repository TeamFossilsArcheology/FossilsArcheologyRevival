package com.github.teamfossilsarcheology.fossil.block.entity;

import com.github.teamfossilsarcheology.fossil.block.custom_blocks.AOEFeederBlock;
import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationCategory;
import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationInfo;
import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationLogic;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.entity.util.Util;
import com.github.teamfossilsarcheology.fossil.food.Diet;
import com.github.teamfossilsarcheology.fossil.food.FoodMappings;
import com.github.teamfossilsarcheology.fossil.food.FoodType;
import com.github.teamfossilsarcheology.fossil.inventory.AOEFeederMenu;
import com.github.teamfossilsarcheology.fossil.inventory.FeederMenu;
import com.github.teamfossilsarcheology.fossil.item.ModItems;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.NonNullList;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.particles.SimpleParticleType;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;
import net.minecraft.server.level.ServerLevel;
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
import net.minecraft.world.phys.AABB;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;
import java.util.Map;

import static com.github.teamfossilsarcheology.fossil.food.FoodType.*;
import static com.github.teamfossilsarcheology.fossil.inventory.AOEFeederMenu.*;

public class AOEFeederBlockEntity extends BaseContainerBlockEntity implements WorldlyContainer {
    private static final int SIZE = 4;
    private static final int[] SLOTS_TOP = new int[]{MEAT_SLOT_ID, PLANT_SLOT_ID, FISH_SLOT_ID, UPGRADE_SLOT_ID};
    private static final String[] NAMES = new String[]{"Meat", "Plant", "Fish"};
    private static final FoodType[] FOOD_TYPES = new FoodType[]{MEAT, PLANT, FISH};
    private final Map<FoodType, Integer> foodStored = new Object2IntOpenHashMap<>(FOOD_TYPES.length);
    private int particleTimer = 0;

    private final ContainerData dataAccess = new ContainerData() {
        @Override
        public int get(int index) {
            if (index < FOOD_TYPES.length) {
                return foodStored.getOrDefault(FOOD_TYPES[index], 0);
            }
            return 0; // i.e. return 0 for the upgrade slot
        }

        @Override
        public void set(int index, int value) {
            if (index < FOOD_TYPES.length) {
                foodStored.put(FOOD_TYPES[index], value);
            }
        }

        @Override
        public int getCount() {
            return FOOD_TYPES.length;
        }
    };
    protected NonNullList<ItemStack> items = NonNullList.withSize(SIZE, ItemStack.EMPTY);
    private int ticksExisted;

    public AOEFeederBlockEntity(BlockPos blockPos, BlockState blockState) {
        super(ModBlockEntities.AOE_FEEDER.get(), blockPos, blockState);
        for (FoodType type : FOOD_TYPES) {
            foodStored.put(type, 0);
        }
    }

    private static void loadFood(Level level, BlockPos pos, BlockState state, AOEFeederBlockEntity blockEntity){
        boolean dirty = false;
        for (int i = 0; i < FOOD_TYPES.length; i++) {
            FoodType type = FOOD_TYPES[i];
            int current = blockEntity.foodStored.get(type);
            blockEntity.foodStored.put(type, Math.max(current, 0));
            ItemStack foodStack = blockEntity.getItem(i);
            if (!foodStack.isEmpty()) {
                if (blockEntity.ticksExisted % 5 == 0 && current < 10000) {
                    int foodPoints = FoodMappings.getFoodAmount(foodStack.getItem(), type);
                    if (foodPoints > 0) {
                        dirty = true;
                        blockEntity.foodStored.put(type, current + foodPoints);
                        foodStack.shrink(1);
                    }
                }
            }
        }
        if (dirty) {
            state = state.setValue(AOEFeederBlock.HERB, blockEntity.foodStored.get(PLANT) > 0).setValue(AOEFeederBlock.CARN, blockEntity.foodStored.get(MEAT) > 0 || blockEntity.foodStored.get(FISH) > 0);
            level.setBlock(pos, state, 3);
            setChanged(level, pos, state);
        }
    }

    // could possibly be improved by having a custom item class for the upgrades that stores the radius
    // but this works for now
    public int getUpgradeRadius() {
        ItemStack upgradeStack = getItem(UPGRADE_SLOT_ID);

        if (upgradeStack.isEmpty()) {
            return 0;
        }

        if (upgradeStack.is(ModItems.IRON_FEEDER_UPGRADE.get())) {
            return 16;
        } else if (upgradeStack.is(ModItems.DIAMOND_FEEDER_UPGRADE.get())) {
            return 32;
        } else if (upgradeStack.is(ModItems.NETHERITE_FEEDER_UPGRADE.get())) {
            return 64;
        }

        return 0;
    }

    private static void feedDinosNearby(Level level, BlockPos pos, BlockState state, AOEFeederBlockEntity blockEntity) {
        // every 5 seconds
        if (blockEntity.ticksExisted % (5 * 20) != 0) {
            return;
        }

        AABB areaOfEffect = new AABB(pos).inflate(blockEntity.getUpgradeRadius());

        List<Prehistoric> dinosInRange = level.getEntitiesOfClass(Prehistoric.class, areaOfEffect);

        for (Prehistoric entity : dinosInRange) {
            if (entity.getHunger() < entity.getMaxHunger() && !blockEntity.isEmpty(entity.data().diet())) {
                blockEntity.feedDinosaur(entity);

                entity.heal(0.1F);

                AnimationInfo animationInfo = entity.nextEatingAnimation();
                entity.getAnimationLogic().triggerAnimation(AnimationLogic.IDLE_CTRL, animationInfo, AnimationCategory.EAT);
            }
        }
    }


    public static void serverTick(Level level, BlockPos pos, BlockState state, AOEFeederBlockEntity blockEntity) {
        blockEntity.ticksExisted++;

        loadFood(level, pos, state, blockEntity);
        feedDinosNearby(level, pos, state, blockEntity);

        if (blockEntity.particleTimer > 0) {
            if (blockEntity.ticksExisted % 10 == 0) {
                blockEntity.spawnParticleLines();
            }
            blockEntity.particleTimer--;
        }
    }

    public void startParticleDisplay() {
        this.particleTimer = 20 * 15; // 30s (1s = 20t)
    }

    private void spawnParticleLines() {
        if (!(level instanceof ServerLevel serverLevel)) {
            return;
        }

        double centerX = worldPosition.getX() + 0.5;
        double centerY = worldPosition.getY() + 0.5;
        double centerZ = worldPosition.getZ() + 0.5;
        int radius = getUpgradeRadius();

        final SimpleParticleType particleType = ParticleTypes.SMOKE;

        for (int i = 0; i <= radius; i += 2) {
            serverLevel.sendParticles(particleType,
                    centerX, centerY, centerZ - i,
                    1, 0, 0, 0, 0);
        }
        for (int i = 0; i <= radius; i += 2) {
            serverLevel.sendParticles(particleType,
                    centerX, centerY, centerZ + i,
                    1, 0, 0, 0, 0);
        }
        for (int i = 0; i <= radius; i += 2) {
            serverLevel.sendParticles(particleType,
                    centerX + i, centerY, centerZ,
                    1, 0, 0, 0, 0);
        }
        for (int i = 0; i <= radius; i += 2) {
            serverLevel.sendParticles(particleType,
                    centerX - i, centerY, centerZ,
                    1, 0, 0, 0, 0);
        }
    }



    @Override
    public void load(CompoundTag tag) {
        super.load(tag);
        this.items = NonNullList.withSize(this.getContainerSize(), ItemStack.EMPTY);
        ContainerHelper.loadAllItems(tag, this.items);
        for (int i = 0; i < FOOD_TYPES.length; i++) {
            foodStored.put(FOOD_TYPES[i], (int) tag.getShort(NAMES[i]));
        }

        this.particleTimer = tag.getInt("ParticleTimer");
    }

    @Override
    protected void saveAdditional(CompoundTag tag) {
        super.saveAdditional(tag);
        for (int i = 0; i < FOOD_TYPES.length; i++) {
            tag.putShort(NAMES[i], (short) (int) foodStored.get(FOOD_TYPES[i]));
        }
        ContainerHelper.saveAllItems(tag, this.items);

        tag.putInt("ParticleTimer", this.particleTimer);
    }

    @Override
    protected @NotNull Component getDefaultName() {
        return Component.nullToEmpty("AOE Feeder");
    }

    @Override
    protected @NotNull AbstractContainerMenu createMenu(int containerId, Inventory inventory) {
        return new AOEFeederMenu(containerId, inventory, this, dataAccess);
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
        for (Map.Entry<FoodType, Integer> entry : foodStored.entrySet()) {
            if (entry.getValue() > 0 && diet.canEat(entry.getKey())) {
                return false;
            }
        }
        return true;
    }

    public Integer getSignalStrength() {
        return foodStored.values().stream().reduce(Integer::sum).orElse(0);
    }

    public void feedDinosaur(Prehistoric mob) {
        if (level != null) {

            int feedAmount = 0;
            Diet diet = mob.data().diet();

            // feed in batches of 30
            for (int i = 0; i < 30; i++) {
                boolean fedThisCycle = false;

                for (Map.Entry<FoodType, Integer> entry : foodStored.entrySet()) {
                    if (entry.getValue() > 0 && diet.canEat(entry.getKey())) {
                        foodStored.put(entry.getKey(), entry.getValue() - 1);
                        feedAmount++;
                        fedThisCycle = true;
                        break;
                    }
                }

                if (!fedThisCycle) {
                    break;
                }

                if (mob.getHunger() + feedAmount >= mob.getMaxHunger()) {
                    break;
                }
            }

            if (feedAmount > 0) {
                BlockState blockState = level.getBlockState(getBlockPos()).setValue(AOEFeederBlock.HERB, foodStored.get(PLANT) > 0).setValue(AOEFeederBlock.CARN, foodStored.get(MEAT) > 0 || foodStored.get(FISH) > 0);
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
    public boolean canPlaceItem(int index, ItemStack stack) {
        if (index == AOEFeederMenu.UPGRADE_SLOT_ID) {
            return ModItems.getFeederUpgrades().contains(stack.getItem());
        }
        return FoodMappings.getFoodAmount(stack.getItem(), FOOD_TYPES[index]) > 0;
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
        return false;
    }

    public int getValue(FoodType type) {
        return foodStored.get(type);
    }
}
