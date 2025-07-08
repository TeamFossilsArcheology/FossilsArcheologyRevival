package com.github.teamfossilsarcheology.fossil.entity;

import com.github.teamfossilsarcheology.fossil.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.EntityDataSerializers;
import net.minecraft.network.syncher.SynchedEntityData;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.phys.Vec3;
import org.jetbrains.annotations.Nullable;

public class ToyScratchingPost extends ToyBase {
    private static final EntityDataAccessor<String> WOOD_TYPE = SynchedEntityData.defineId(ToyScratchingPost.class, EntityDataSerializers.STRING);

    public ToyScratchingPost(EntityType<ToyScratchingPost> type, Level level) {
        super(type, level, 20, SoundEvents.WOOL_BREAK);
    }

    @Override
    protected void defineSynchedData() {
        entityData.define(WOOD_TYPE, WoodType.OAK.name());
    }

    @Override
    public void tick() {
        super.tick();
        setDeltaMovement(Vec3.ZERO);
        if (!isOnBlock()) {
            if (!level().isClientSide) {
                Block.popResource(level(), blockPosition(), getPickResult());
            }
            discard();
            playSound(attackNoise, 1, getVoicePitch());
        }
    }

    private boolean isOnBlock() {
        return !level().isEmptyBlock(BlockPos.containing(position().add(0, -1, 0)));
    }

    @Nullable
    @Override
    public ItemStack getPickResult() {
        return new ItemStack(ModItems.TOY_SCRATCHING_POSTS.get(getWoodTypeName()).get());
    }

    public void setWoodType(String woodType) {
        entityData.set(WOOD_TYPE, woodType);
    }

    public String getWoodTypeName() {
        return entityData.get(WOOD_TYPE);
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compound) {
        compound.putString("woodType", getWoodTypeName());
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compound) {
        String woodType = compound.getString("woodType");
        if (woodType.isBlank()) {
            woodType = WoodType.OAK.name();
        }
        setWoodType(woodType);
    }

    @Override
    public float getVoicePitch() {
        return super.getVoicePitch() * 0.2f;
    }
}
