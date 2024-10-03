package com.github.teamfossilsarcheology.fossil.capabilities.forge;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.EntityInfo;
import com.github.teamfossilsarcheology.fossil.forge.capabilities.mammal.IMammalCap;
import com.github.teamfossilsarcheology.fossil.forge.capabilities.mammal.MammalCapProvider;
import com.github.teamfossilsarcheology.fossil.forge.capabilities.player.FirstHatchCapProvider;
import com.github.teamfossilsarcheology.fossil.forge.capabilities.player.IFirstHatchCap;
import com.github.teamfossilsarcheology.fossil.network.MessageHandler;
import com.github.teamfossilsarcheology.fossil.network.S2CMammalCapMessage;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

public class ModCapabilitiesImpl {
    private static final Map<Animal, LazyOptional<IMammalCap>> cachedMammals = new HashMap<>();
    private static final Map<Player, LazyOptional<IFirstHatchCap>> cachedPlayers = new HashMap<>();

    public static Optional<IMammalCap> getMammalCap(Animal animal) {
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

    public static Optional<IFirstHatchCap> getFirstHatchCap(Player player) {
        LazyOptional<IFirstHatchCap> cap = cachedPlayers.get(player);
        if (cap == null) {
            cap = player.getCapability(FirstHatchCapProvider.FIRST_HATCH_CAPABILITY);
            cachedPlayers.put(player, cap);
            cap.addListener(optional -> cachedPlayers.remove(player));
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

    public static boolean hasHatchedDinosaur(Player player) {
        Optional<IFirstHatchCap> cap = getFirstHatchCap(player);
        return cap.map(IFirstHatchCap::hasHatchedDinosaur).orElse(false);
    }

    public static void setHatchedDinosaur(Player player, boolean hatched) {
        Optional<IFirstHatchCap> cap = getFirstHatchCap(player);
        cap.ifPresent(iFirstHatchCap -> iFirstHatchCap.setHatchedDinosaur(hatched));
    }
}
