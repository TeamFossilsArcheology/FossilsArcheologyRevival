package com.fossil.fossil.capabilities;

import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.entity.animal.Animal;
import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.Nullable;

public class ModCapabilities {

    @ExpectPlatform
    public static int getEmbryoProgress(Animal animal) {
        return 0;
    }

    @ExpectPlatform
    public static PrehistoricEntityType getEmbryo(Animal animal) {
        return null;
    }

    @ExpectPlatform
    public static void setEmbryoProgress(Animal animal, int embryoProgress) {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    public static void setEmbryo(Animal animal, @Nullable PrehistoricEntityType embryo) {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    public static void syncMammalWithClient(Animal animal, int embryoProgress, PrehistoricEntityType embryo) {
    }

    public static void startPregnancy(Animal animal, PrehistoricEntityType embryo) {
        setEmbryo(animal, embryo);
        setEmbryoProgress(animal, 1);
        syncMammalWithClient(animal, 1, embryo);
    }

    public static void stopPregnancy(Animal animal) {
        setEmbryo(animal, null);
        setEmbryoProgress(animal, 0);
        syncMammalWithClient(animal, 0, null);
    }
}
