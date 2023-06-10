package com.fossil.fossil.sounds;

import com.fossil.fossil.Fossil;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS = DeferredRegister.create(Fossil.MOD_ID, Registry.SOUND_EVENT_REGISTRY);

    public static final RegistrySupplier<SoundEvent> ANU_TOTEM = SOUND_EVENTS.register("anu_totem",
            () -> new SoundEvent(new ResourceLocation(Fossil.MOD_ID, "anu_totem")));
    public static final RegistrySupplier<SoundEvent> ANU_COUGH = SOUND_EVENTS.register("anu_cough",
            () -> new SoundEvent(new ResourceLocation(Fossil.MOD_ID, "anu_cough")));
    public static final RegistrySupplier<SoundEvent> ANU_DEATH = SOUND_EVENTS.register("anu_death",
            () -> new SoundEvent(new ResourceLocation(Fossil.MOD_ID, "anu_death")));
    public static final RegistrySupplier<SoundEvent> ANU_LAUGH = SOUND_EVENTS.register("anu_laugh",
            () -> new SoundEvent(new ResourceLocation(Fossil.MOD_ID, "anu_laugh")));
    public static final RegistrySupplier<SoundEvent> ANU_MUSIC = SOUND_EVENTS.register("anu_music",
            () -> new SoundEvent(new ResourceLocation(Fossil.MOD_ID, "anu_music")));
    public static final RegistrySupplier<SoundEvent> DRUM_SINGLE = SOUND_EVENTS.register("drum_single",
            () -> new SoundEvent(new ResourceLocation(Fossil.MOD_ID, "drum_single")));
    public static final RegistrySupplier<SoundEvent> MATING_MUSIC = SOUND_EVENTS.register("mating_music",
            () -> new SoundEvent(new ResourceLocation(Fossil.MOD_ID, "mating_music")));
    public static final RegistrySupplier<SoundEvent> TAR = SOUND_EVENTS.register("tar",
            () -> new SoundEvent(new ResourceLocation(Fossil.MOD_ID, "tar")));
    public static final RegistrySupplier<SoundEvent> WHIP = SOUND_EVENTS.register("whip",
            () -> new SoundEvent(new ResourceLocation(Fossil.MOD_ID, "whip")));

    public static void register() {
        SOUND_EVENTS.register();
    }
}
