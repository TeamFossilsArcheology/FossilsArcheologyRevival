package com.fossil.fossil.capabilities;

import com.fossil.fossil.entity.prehistoric.base.EntityInfo;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import org.apache.commons.lang3.NotImplementedException;
import org.jetbrains.annotations.Nullable;

public class ModCapabilities {

    @ExpectPlatform
    public static int getEmbryoProgress(Animal animal) {
        return 0;
    }

    @ExpectPlatform
    public static EntityInfo getEmbryo(Animal animal) {
        return null;
    }

    @ExpectPlatform
    public static void setEmbryoProgress(Animal animal, int embryoProgress) {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    public static void setEmbryo(Animal animal, @Nullable EntityInfo embryo) {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    public static void syncMammalWithClient(Animal animal, int embryoProgress, EntityInfo embryo) {
    }

    public static void startPregnancy(Animal animal, EntityInfo embryo) {
        setEmbryo(animal, embryo);
        setEmbryoProgress(animal, 1);
        syncMammalWithClient(animal, 1, embryo);
    }

    public static void stopPregnancy(Animal animal) {
        setEmbryo(animal, null);
        setEmbryoProgress(animal, 0);
        syncMammalWithClient(animal, 0, null);
    }

    @ExpectPlatform
    public static boolean hasHatchedDinosaur(Player player) {
        throw new NotImplementedException();
    }

    @ExpectPlatform
    public static void setHatchedDinosaur(Player player, boolean hatched) {
        throw new NotImplementedException();
    }
}
