package com.fossil.fossil.capabilities;

import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.network.MammalCapMessage;
import com.fossil.fossil.network.MessageHandler;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.server.level.ServerLevel;
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
    public static void setEmbryo(Animal animal, @Nullable PrehistoricEntityType type) {
        throw new NotImplementedException();
    }

    public static void startEmbryo(Animal animal, PrehistoricEntityType embryo) {
        setEmbryo(animal, embryo);
        setEmbryoProgress(animal, 1);
        MessageHandler.CAP_CHANNEL.sendToPlayers(((ServerLevel)animal.level).getPlayers(serverPlayer -> true),
                new MammalCapMessage(animal, 1, embryo));
    }
    public static void stopEmbryo(Animal animal) {
        setEmbryo(animal, null);
        setEmbryoProgress(animal, 0);
        MessageHandler.CAP_CHANNEL.sendToPlayers(((ServerLevel)animal.level).getPlayers(serverPlayer -> true),
                new MammalCapMessage(animal, 0, null));
    }
}
