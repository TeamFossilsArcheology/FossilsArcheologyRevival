package com.fossil.fossil.capabilities.forge;

import com.fossil.fossil.entity.prehistoric.base.EntityInfo;
import com.fossil.fossil.forge.capabilities.mammal.IMammalCap;
import com.fossil.fossil.forge.capabilities.mammal.MammalCapProvider;
import com.fossil.fossil.network.MessageHandler;
import com.fossil.fossil.network.S2CMammalCapMessage;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.animal.Animal;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ModCapabilitiesImpl {
    private static final Map<Animal, LazyOptional<IMammalCap>> cachedMammals = new HashMap<>();

    private static Optional<IMammalCap> getMammalCap(Animal animal) {
        LazyOptional<IMammalCap> cap = cachedMammals.get(animal);
        if (cap == null) {
            cap = animal.getCapability(MammalCapProvider.MAMMAL_CAPABILITY);
            cachedMammals.put(animal, cap);
            cap.addListener(optional -> cachedMammals.remove(animal));
        }
        if (cap.isPresent()) {
            return cap.resolve();
        }
        return Optional.empty();
    }

    public static int getEmbryoProgress(Animal animal) {
        Optional<IMammalCap> cap = getMammalCap(animal);
        return cap.map(IMammalCap::getEmbryoProgress).orElse(0);
    }

    public static EntityInfo getEmbryo(Animal animal) {
        Optional<IMammalCap> cap = getMammalCap(animal);
        return cap.map(IMammalCap::getEmbryo).orElse(null);
    }

    public static void setEmbryoProgress(Animal animal, int embryoProgress) {
        Optional<IMammalCap> cap = getMammalCap(animal);
        cap.ifPresent(iMammalCap -> iMammalCap.setEmbryoProgress(embryoProgress));
    }

    public static void setEmbryo(Animal animal, @Nullable EntityInfo embryo) {
        Optional<IMammalCap> cap = getMammalCap(animal);
        cap.ifPresent(iMammalCap -> iMammalCap.setEmbryo(embryo));
    }

    public static void syncMammalWithClient(Animal animal, int embryoProgress, EntityInfo embryo) {
        MessageHandler.CAP_CHANNEL.sendToPlayers(((ServerLevel) animal.level).getPlayers(serverPlayer -> true),
                new S2CMammalCapMessage(animal, embryoProgress, embryo));
    }
}
