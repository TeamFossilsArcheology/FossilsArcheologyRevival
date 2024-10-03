package com.github.teamfossilsarcheology.fossil.item.fabric;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.RecordItem;

public class CustomRecordItemImpl {
    public static RecordItem get(int analogOutput, RegistrySupplier<SoundEvent> sound, Item.Properties properties) {
        return new RecordItem(analogOutput, sound.get(), properties);
    }
}
