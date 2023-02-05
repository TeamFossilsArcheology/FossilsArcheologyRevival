package com.fossil.fossil.forge.data;

import com.fossil.fossil.forge.data.providers.ModBlockStateProvider;
import com.fossil.fossil.forge.data.providers.ModItemProvider;
import com.fossil.fossil.forge.data.providers.ModLootProvider;
import com.fossil.fossil.forge.data.providers.ModRecipeProvider;
import net.minecraft.data.DataGenerator;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void register(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        generator.addProvider(new ModBlockStateProvider(generator, event.getExistingFileHelper()));
        generator.addProvider(new ModItemProvider(generator, event.getExistingFileHelper()));
        generator.addProvider(new ModLootProvider(generator));
        generator.addProvider(new ModRecipeProvider(generator));
    }
}
