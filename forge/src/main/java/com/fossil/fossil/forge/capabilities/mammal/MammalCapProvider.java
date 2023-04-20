package com.fossil.fossil.forge.capabilities.mammal;

import com.fossil.fossil.Fossil;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.capabilities.Capability;
import net.minecraftforge.common.capabilities.CapabilityManager;
import net.minecraftforge.common.capabilities.CapabilityToken;
import net.minecraftforge.common.capabilities.ICapabilitySerializable;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.NotNull;

public class MammalCapProvider implements ICapabilitySerializable<CompoundTag> {
    public static final ResourceLocation IDENTIFIER = new ResourceLocation(Fossil.MOD_ID, "mammal");

    public static final Capability<IMammalCap> MAMMAL_CAPABILITY = CapabilityManager.get(new CapabilityToken<>() {
    });

    private final IMammalCap instance = new MammalCap();
    private final LazyOptional<IMammalCap> optional = LazyOptional.of(() -> instance);

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
        return MAMMAL_CAPABILITY.orEmpty(cap, optional);
    }

    public void invalidate() {
        optional.invalidate();
    }
}
