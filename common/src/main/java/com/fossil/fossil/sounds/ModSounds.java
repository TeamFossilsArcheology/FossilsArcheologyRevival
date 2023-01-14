package com.fossil.fossil.sounds;

import com.fossil.fossil.Fossil;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class ModSounds {

    public static final ResourceLocation DRUM_SINGLE = new ResourceLocation(Fossil.MOD_ID, "drum_single");
    public static final ResourceLocation TAR = new ResourceLocation(Fossil.MOD_ID, "tar");

    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Fossil.MOD_ID, Registry.SOUND_EVENT_REGISTRY);

    public static final RegistrySupplier<SoundEvent> ANU_TOTEM = SOUND_EVENTS.register("anu_totem",
            () -> new SoundEvent(new ResourceLocation(Fossil.MOD_ID, "anu_totem")));

    public static void register() {
        SOUND_EVENTS.register();
    }
}
