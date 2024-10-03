package com.github.teamfossilsarcheology.fossil.fabric.capabilities;

import com.github.teamfossilsarcheology.fossil.capabilities.fabric.ModCapabilitiesImpl;
import dev.onyxstudios.cca.api.v3.entity.PlayerCopyCallback;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class FirstHatchComponent implements IFirstHatchComponent, PlayerCopyCallback {
    private final Player player;
    private boolean hatchedDinosaur;

    public FirstHatchComponent(Player provider) {
        this.player = provider;
    }

    @Override
    public void readFromNbt(CompoundTag tag) {
        setHatchedDinosaur(tag.getBoolean("HatchedDinosaur"));
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
        tag.putBoolean("HatchedDinosaur", hatchedDinosaur);
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        FirstHatchComponent that = (FirstHatchComponent) obj;
        return Objects.equals(hatchedDinosaur, that.hatchedDinosaur);
    }

    @Override
    public int hashCode() {
        return Objects.hash(hatchedDinosaur);
    }

    @Override
    public boolean hasHatchedDinosaur() {
        return hatchedDinosaur;
    }

    @Override
    public void setHatchedDinosaur(boolean hatched) {
        this.hatchedDinosaur = hatched;
    }

    @Override
    public void copyData(@NotNull ServerPlayer original, @NotNull ServerPlayer clone, boolean lossless) {
        ModCapabilitiesImpl.PLAYER.get(clone).setHatchedDinosaur(ModCapabilitiesImpl.PLAYER.get(original).hasHatchedDinosaur());
    }

    @Override
    public boolean shouldSyncWith(ServerPlayer player) {
        return player.getId() == this.player.getId();
    }
}
