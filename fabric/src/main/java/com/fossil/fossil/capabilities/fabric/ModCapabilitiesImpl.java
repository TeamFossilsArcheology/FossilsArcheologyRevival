package com.fossil.fossil.capabilities.fabric;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.fabric.capabilities.MammalComponent;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Animal;
import org.jetbrains.annotations.Nullable;

public class ModCapabilitiesImpl {
    public static final ComponentKey<MammalComponent> MAMMAL =
            ComponentRegistry.getOrCreate(new ResourceLocation(Fossil.MOD_ID, "mammal"), MammalComponent.class);
    public static int getEmbryoProgress(Animal animal) {
        return MAMMAL.get(animal).getEmbryoProgress();
    }

    public static PrehistoricEntityType getEmbryo(Animal animal) {
        return MAMMAL.get(animal).getEmbryo();
    }

    public static void setEmbryoProgress(Animal animal, int embryoProgress) {
        MAMMAL.get(animal).setEmbryoProgress(embryoProgress);
    }

    public static void setEmbryo(Animal animal, @Nullable PrehistoricEntityType embryo) {
        MAMMAL.get(animal).setEmbryo(embryo);
    }

    public static void syncMammalWithClient(Animal animal, int embryoProgress, PrehistoricEntityType embryo) {
        MAMMAL.sync(animal);
    }
}
