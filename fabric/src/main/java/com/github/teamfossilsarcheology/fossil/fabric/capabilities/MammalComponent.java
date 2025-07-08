package com.github.teamfossilsarcheology.fossil.fabric.capabilities;

import com.github.teamfossilsarcheology.fossil.capabilities.ModCapabilities;
import com.github.teamfossilsarcheology.fossil.config.FossilConfig;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.EntityInfo;
import com.github.teamfossilsarcheology.fossil.event.ModEvents;
import dev.onyxstudios.cca.api.v3.component.tick.CommonTickingComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.animal.Animal;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class MammalComponent implements IMammalComponent, CommonTickingComponent {
    private final Animal animal;
    private int embryoProgress;
    private EntityInfo embryo;

    public MammalComponent(Animal provider) {
        this.animal = provider;
    }

    @Override
    public void tick() {
        if (embryoProgress == 0) {
            return;
        }
        if (embryoProgress >= FossilConfig.getInt(FossilConfig.PREGNANCY_DURATION)) {
            if (!animal.level().isClientSide) {
                ModEvents.growEntity(embryo, animal);
                ModCapabilities.stopPregnancy(animal);
            }
        } else {
            embryoProgress++;
        }
    }

    @Override
    public void readFromNbt(CompoundTag tag) {
        setEmbryoProgress(tag.getInt("embryoProgress"));
        try {
            setEmbryo(EntityInfo.fromNbt(tag.getString("embryo")));
        } catch (IllegalArgumentException e) {
            setEmbryo(null);
        }
    }

    @Override
    public void writeToNbt(CompoundTag tag) {
        tag.putInt("embryoProgress", embryoProgress);
        if (embryo != null) {
            tag.putString("embryo", embryo.name());
        }
    }

    @Override
    public boolean equals(@Nullable Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;
        MammalComponent that = (MammalComponent) obj;
        return Objects.equals(embryoProgress, that.embryoProgress) && Objects.equals(embryo, that.embryo);
    }

    @Override
    public int hashCode() {
        return Objects.hash(embryoProgress, embryo);
    }

    @Override
    public int getEmbryoProgress() {
        return embryoProgress;
    }

    @Override
    public void setEmbryoProgress(int progress) {
        this.embryoProgress = progress;
    }

    @Override
    public EntityInfo getEmbryo() {
        return embryo;
    }

    @Override
    public void setEmbryo(@Nullable EntityInfo embryo) {
        this.embryo = embryo;
    }
}
