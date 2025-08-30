package com.github.teamfossilsarcheology.fossil.forge.mixin;

import com.github.teamfossilsarcheology.fossil.block.entity.MachineContainerBlockEntity;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.WorldlyContainer;
import net.minecraft.world.level.block.entity.BaseContainerBlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.items.IItemHandler;
import net.minecraftforge.items.wrapper.SidedInvWrapper;
import org.jetbrains.annotations.NotNull;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

import javax.annotation.Nullable;

@Mixin(MachineContainerBlockEntity.class)
public abstract class MachineContainerBlockEntityMixin extends BaseContainerBlockEntity {
    @Unique
    LazyOptional<? extends IItemHandler>[] fossilsArcheologyRevival$handlers = initArray((MachineContainerBlockEntity)(Object) this);

    private static LazyOptional<? extends IItemHandler>[] initArray(WorldlyContainer container) {
        return SidedInvWrapper.create(container, Direction.UP, Direction.DOWN, Direction.NORTH);
    }

    private MachineContainerBlockEntityMixin(BlockEntityType<?> type, BlockPos pos, BlockState blockState) {
        super(type, pos, blockState);
    }

    @NotNull
    @Override
    public <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (!remove && side != null && cap == ForgeCapabilities.ITEM_HANDLER) {
            if (side == Direction.UP) {
                return fossilsArcheologyRevival$handlers[0].cast();
            }
            if (side == Direction.DOWN) {
                return fossilsArcheologyRevival$handlers[1].cast();
            }
            return fossilsArcheologyRevival$handlers[2].cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        for (LazyOptional<? extends IItemHandler> handler : fossilsArcheologyRevival$handlers) {
            handler.invalidate();
        }
    }

    @Override
    public void reviveCaps() {
        super.reviveCaps();
        fossilsArcheologyRevival$handlers = SidedInvWrapper.create((MachineContainerBlockEntity)(Object) this, Direction.UP, Direction.DOWN, Direction.NORTH);
    }
}
