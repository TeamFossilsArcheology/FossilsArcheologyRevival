package com.fossil.fossil.capabilities.fabric;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.entity.prehistoric.base.EntityInfo;
import com.fossil.fossil.fabric.capabilities.FirstHatchComponent;
import com.fossil.fossil.fabric.capabilities.MammalComponent;
import dev.onyxstudios.cca.api.v3.component.ComponentKey;
import dev.onyxstudios.cca.api.v3.component.ComponentRegistry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.animal.Animal;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.Nullable;

public class ModCapabilitiesImpl {
    public static final ComponentKey<MammalComponent> MAMMAL =
            ComponentRegistry.getOrCreate(Fossil.location("mammal"), MammalComponent.class);
    public static final ComponentKey<FirstHatchComponent> PLAYER =
            ComponentRegistry.getOrCreate(Fossil.location("first_hatch"), FirstHatchComponent.class);

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
}
