package com.github.teamfossilsarcheology.fossil.capabilities.fabric;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.block.entity.CommonEnergyStorage;
import com.github.teamfossilsarcheology.fossil.config.FossilConfig;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.EntityInfo;
import com.github.teamfossilsarcheology.fossil.fabric.capabilities.FirstHatchComponent;
import com.github.teamfossilsarcheology.fossil.fabric.capabilities.MammalComponent;
import com.github.teamfossilsarcheology.fossil.fabric.energy.FAEnergyStorage;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class ModCapabilitiesImpl {
    public static final ComponentKey<MammalComponent> MAMMAL =
            ComponentRegistry.getOrCreate(FossilMod.location("mammal"), MammalComponent.class);
    public static final ComponentKey<FirstHatchComponent> PLAYER =
            ComponentRegistry.getOrCreate(FossilMod.location("first_hatch"), FirstHatchComponent.class);

    public static boolean hasEmbryo(Animal animal) {
        return MAMMAL.get(animal).getEmbryo() != null;
    }

    public static int getEmbryoProgress(Animal animal) {
        return MAMMAL.get(animal).getEmbryoProgress();
    }

    public static EntityInfo getEmbryo(Animal animal) {
        return MAMMAL.get(animal).getEmbryo();
    }

    public static void setEmbryoProgress(Animal animal, int embryoProgress) {
        MAMMAL.get(animal).setEmbryoProgress(embryoProgress);
    }

    public static void setEmbryo(Animal animal, @Nullable EntityInfo embryo) {
        MAMMAL.get(animal).setEmbryo(embryo);
    }

    public static void syncMammalWithClient(Animal animal, int embryoProgress, EntityInfo embryo) {
        MAMMAL.sync(animal);
    }

    public static boolean hasHatchedDinosaur(Player player) {
        return PLAYER.get(player).hasHatchedDinosaur();
    }

    public static void setHatchedDinosaur(Player player, boolean hatched) {
        PLAYER.get(player).setHatchedDinosaur(hatched);
    }

    public static CommonEnergyStorage createEnergyStorage(Runnable setChanged) {
        return new FAEnergyStorage(FossilConfig.getInt(FossilConfig.MACHINE_MAX_ENERGY), FossilConfig.getInt(FossilConfig.MACHINE_TRANSFER_RATE), 0) {
            @Override
            protected void onFinalCommit() {
                setChanged.run();
            }
        };
    }
}
