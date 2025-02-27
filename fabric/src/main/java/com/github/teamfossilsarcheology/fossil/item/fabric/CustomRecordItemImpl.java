package com.github.teamfossilsarcheology.fossil.item.fabric;

import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.RecordItem;

/**
 * @see com.github.teamfossilsarcheology.fossil.item.CustomRecordItem
 */
public class CustomRecordItemImpl {
    public static RecordItem get(int analogOutput, RegistrySupplier<SoundEvent> sound, Item.Properties properties, int lengthInSeconds) {
        return new RecordItem(analogOutput, sound.get(), properties, lengthInSeconds);
    }
}
