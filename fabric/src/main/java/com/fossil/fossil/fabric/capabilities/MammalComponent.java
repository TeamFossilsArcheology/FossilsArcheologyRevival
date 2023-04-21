package com.fossil.fossil.fabric.capabilities;

import com.fossil.fossil.capabilities.ModCapabilities;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.event.ModEvents;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import dev.onyxstudios.cca.api.v3.component.tick.CommonTickingComponent;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.animal.Animal;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

public class MammalComponent implements IMammalComponent, AutoSyncedComponent, CommonTickingComponent {
    private int embryoProgress;
    private PrehistoricEntityType embryo;
    private final Animal animal;

    public MammalComponent(Animal provider) {
        this.animal = provider;
    }

    @Override
    public void tick() {
        if (embryoProgress == 0) {
            return;
        }
        if (embryoProgress >= 10000) {
            if (!animal.level.isClientSide) {
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
            setEmbryo(PrehistoricEntityType.valueOf(tag.getString("embryo")));
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
    public PrehistoricEntityType getEmbryo() {
        return embryo;
    }

    @Override
    public void setEmbryo(@Nullable PrehistoricEntityType embryo) {
        this.embryo = embryo;
    }
}
