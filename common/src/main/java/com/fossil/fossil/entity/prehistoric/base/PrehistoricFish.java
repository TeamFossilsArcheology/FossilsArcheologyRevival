package com.fossil.fossil.entity.prehistoric.base;

import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.tags.FluidTags;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.animal.AbstractFish;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public abstract class PrehistoricFish extends AbstractFish {
    private int absoluteEggCooldown = 0;
    public PrehistoricFish(EntityType<? extends PrehistoricFish> entityType, Level level) {
        super(entityType, level);
    }

    public @NotNull abstract PrehistoricEntityType type();

    public static AttributeSupplier.@NotNull Builder createAttributes() {
        return AbstractFish.createAttributes().add(Attributes.MOVEMENT_SPEED, 0.25);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        super.addAdditionalSaveData(compound);
        compound.putInt("absoluteEggCooldown", absoluteEggCooldown);
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        super.readAdditionalSaveData(compound);
        absoluteEggCooldown = compound.getInt("absoluteEggCooldown");
    }

    @Override
    protected @NotNull SoundEvent getFlopSound() {
        return SoundEvents.COD_FLOP;//TODO: Flops
    }

    @Override
    public @NotNull ItemStack getBucketItemStack() {
        return new ItemStack(Items.COD_BUCKET);//TODO: BUcket
    }

    @Override
    public void tick() {
        super.tick();
        if (absoluteEggCooldown > 0) {
            absoluteEggCooldown--;
        }
        PrehistoricFish closestMate = getClosestMate();
        if (closestMate != null && isInWater() && absoluteEggCooldown <= 0) {
            absoluteEggCooldown = 48000 + random.nextInt(48000);
            level.addFreshEntity(new ItemEntity(level, getX(), getY(), getZ(), new ItemStack(type().eggItem)));
        }
    }

    private @Nullable PrehistoricFish getClosestMate() {
        List<? extends PrehistoricFish> sameTypes = level.getEntitiesOfClass(getClass(), getBoundingBox().inflate(2, 2, 2), fish -> fish != this);
        double shortestDistance = Double.MAX_VALUE;
        PrehistoricFish other = null;
        for (PrehistoricFish sameType : sameTypes) {
            if (distanceToSqr(sameType) > shortestDistance) {
                continue;
            }
            other = sameType;
            shortestDistance = distanceToSqr(other);
        }
        return other;
    }

    public static boolean canSpawn(Level level, BlockPos pos) {
        return pos.getY() < level.getSeaLevel() && level.getFluidState(pos.below()).is(FluidTags.WATER) && level.getBlockState(pos.above()).is(Blocks.WATER);
    }
}
