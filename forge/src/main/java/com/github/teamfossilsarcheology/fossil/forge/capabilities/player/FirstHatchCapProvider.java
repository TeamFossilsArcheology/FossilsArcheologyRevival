package com.github.teamfossilsarcheology.fossil.forge.capabilities.player;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

public class FirstHatchCapProvider implements ICapabilitySerializable<CompoundTag> {
    public static final ResourceLocation IDENTIFIER = FossilMod.location("player");

    public static final Capability<IFirstHatchCap> FIRST_HATCH_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });

    private final IFirstHatchCap instance = new FirstHatchCap();
    private final LazyOptional<IFirstHatchCap> optional = LazyOptional.of(() -> instance);

    @Override
    public CompoundTag serializeNBT() {
        return instance.serializeNBT();
    }

    @Override
    public void deserializeNBT(CompoundTag nbt) {
        instance.deserializeNBT(nbt);
    }

    @Override
    public <T> @NotNull LazyOptional<T> getCapability(@NotNull Capability<T> cap, Direction Dist) {
        return FIRST_HATCH_CAPABILITY.orEmpty(cap, optional);
    }

    public void invalidate() {
        optional.invalidate();
    }
}
