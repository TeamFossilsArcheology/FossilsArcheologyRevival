package com.github.teamfossilsarcheology.fossil.forge.mixin;

import com.github.teamfossilsarcheology.fossil.block.entity.CommonEnergyStorage;
import com.github.teamfossilsarcheology.fossil.block.entity.EnergyContainerBlockEntity;
import com.github.teamfossilsarcheology.fossil.block.entity.MachineContainerBlockEntity;
import com.github.teamfossilsarcheology.fossil.config.FossilConfig;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.ForgeCapabilities;
import net.minecraftforge.common.util.LazyOptional;
import net.minecraftforge.energy.IEnergyStorage;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

@Mixin(EnergyContainerBlockEntity.class)
public abstract class EnergyContainerBlockEntityMixin extends MachineContainerBlockEntity {
    @Shadow
    private CommonEnergyStorage energyStorage;
    @Unique
    private LazyOptional<IEnergyStorage> fossilsArcheologyRevival$lazyEnergyStorage = LazyOptional.empty();

    private EnergyContainerBlockEntityMixin(BlockEntityType<?> blockEntityType, BlockPos blockPos, BlockState blockState) {
        super(blockEntityType, blockPos, blockState);
    }

    @Override
    public void onLoad() {
        super.onLoad();
        fossilsArcheologyRevival$lazyEnergyStorage = LazyOptional.of(() -> (IEnergyStorage) energyStorage);
    }

    @Override
    public @NotNull <T> LazyOptional<T> getCapability(@NotNull Capability<T> cap, @Nullable Direction side) {
        if (!remove && side != null && cap == ForgeCapabilities.ENERGY && FossilConfig.isEnabled(FossilConfig.MACHINES_REQUIRE_ENERGY)) {
            return fossilsArcheologyRevival$lazyEnergyStorage.cast();
        }
        return super.getCapability(cap, side);
    }

    @Override
    public void invalidateCaps() {
        super.invalidateCaps();
        fossilsArcheologyRevival$lazyEnergyStorage.invalidate();
    }
}
