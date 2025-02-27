package com.github.teamfossilsarcheology.fossil.item;

import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.RecordItem;
import org.apache.commons.lang3.NotImplementedException;

public class CustomRecordItem {

    @ExpectPlatform
    public static RecordItem get(int analogOutput, RegistrySupplier<SoundEvent> sound, Item.Properties properties, int lengthInSeconds) {
        throw new NotImplementedException();
    }
}
