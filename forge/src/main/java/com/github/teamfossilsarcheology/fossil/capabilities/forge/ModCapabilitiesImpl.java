package com.github.teamfossilsarcheology.fossil.capabilities.forge;

import com.github.teamfossilsarcheology.fossil.block.entity.CommonEnergyStorage;
import com.github.teamfossilsarcheology.fossil.config.FossilConfig;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.EntityInfo;
import com.github.teamfossilsarcheology.fossil.forge.capabilities.mammal.IMammalCap;
import com.github.teamfossilsarcheology.fossil.forge.capabilities.mammal.MammalCapProvider;
import com.github.teamfossilsarcheology.fossil.forge.capabilities.player.FirstHatchCapProvider;
import com.github.teamfossilsarcheology.fossil.forge.capabilities.player.IFirstHatchCap;
import com.github.teamfossilsarcheology.fossil.forge.energy.FAEnergyStorage;
import com.github.teamfossilsarcheology.fossil.network.MessageHandler;
import com.github.teamfossilsarcheology.fossil.network.S2CMammalCapMessage;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import net.minecraftforge.common.util.LazyOptional;
import org.jetbrains.annotations.Nullable;

import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ModCapabilitiesImpl {
    private static final ConcurrentMap<Animal, LazyOptional<IMammalCap>> cachedMammals = new ConcurrentHashMap<>();
    private static final ConcurrentMap<Player, LazyOptional<IFirstHatchCap>> cachedPlayers = new ConcurrentHashMap<>();

    public static Optional<IMammalCap> getMammalCap(Animal animal) {
        LazyOptional<IMammalCap> cap = cachedMammals.get(animal);
        if (cap == null) {
            cap = animal.getCapability(MammalCapProvider.MAMMAL_CAPABILITY);
            cachedMammals.put(animal, cap);
            cap.addListener(optional -> cachedMammals.remove(animal));
        }
        return cap.resolve();
    }

    public static Optional<IFirstHatchCap> getFirstHatchCap(Player player) {
        LazyOptional<IFirstHatchCap> cap = cachedPlayers.get(player);
        if (cap == null) {
            cap = player.getCapability(FirstHatchCapProvider.FIRST_HATCH_CAPABILITY);
            cachedPlayers.put(player, cap);
            cap.addListener(optional -> cachedPlayers.remove(player));
        }
        return cap.resolve();
    }

    public static boolean hasEmbryo(Animal animal) {
        return getMammalCap(animal).map(iMammalCap -> iMammalCap.getEmbryo() != null).orElse(false);
    }

    public static int getEmbryoProgress(Animal animal) {
        return getMammalCap(animal).map(IMammalCap::getEmbryoProgress).orElse(0);
    }

    public static EntityInfo getEmbryo(Animal animal) {
        return getMammalCap(animal).map(IMammalCap::getEmbryo).orElse(null);
    }

    public static void setEmbryoProgress(Animal animal, int embryoProgress) {
        getMammalCap(animal).ifPresent(iMammalCap -> iMammalCap.setEmbryoProgress(embryoProgress));
    }

    public static void setEmbryo(Animal animal, @Nullable EntityInfo embryo) {
        getMammalCap(animal).ifPresent(iMammalCap -> iMammalCap.setEmbryo(embryo));
    }

    public static void syncMammalWithClient(Animal animal, int embryoProgress, EntityInfo embryo) {
        MessageHandler.CAP_CHANNEL.sendToPlayers(((ServerLevel) animal.level()).getPlayers(serverPlayer -> true),
                new S2CMammalCapMessage(animal, embryoProgress, embryo));
    }

    public static boolean hasHatchedDinosaur(Player player) {
        return getFirstHatchCap(player).map(IFirstHatchCap::hasHatchedDinosaur).orElse(false);
    }

    public static void setHatchedDinosaur(Player player, boolean hatched) {
        getFirstHatchCap(player).ifPresent(iFirstHatchCap -> iFirstHatchCap.setHatchedDinosaur(hatched));
    }

    public static CommonEnergyStorage createEnergyStorage(Runnable setChanged) {
        return new FAEnergyStorage(FossilConfig.getInt(FossilConfig.MACHINE_MAX_ENERGY), FossilConfig.getInt(FossilConfig.MACHINE_TRANSFER_RATE), FossilConfig.getInt(FossilConfig.MACHINE_ENERGY_USAGE), 0) {
            @Override
            protected void onChange() {
                setChanged.run();
            }
        };
    }
}
