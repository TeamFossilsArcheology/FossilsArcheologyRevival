package com.github.teamfossilsarcheology.fossil.forge.data;

import com.github.teamfossilsarcheology.fossil.Fossil;
import com.github.teamfossilsarcheology.fossil.forge.data.providers.*;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.forge.event.lifecycle.GatherDataEvent;

@Mod.EventBusSubscriber(modid = Fossil.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void register(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        generator.addProvider(new ModBlockStateProvider(generator, event.getExistingFileHelper()));
        generator.addProvider(new ModItemProvider(generator, event.getExistingFileHelper()));
        BlockTagsProvider blockTagsProvider = new ModBlockTagsProvider(generator, event.getExistingFileHelper());
        generator.addProvider(blockTagsProvider);
        generator.addProvider(new ModItemTagsProvider(generator, blockTagsProvider, event.getExistingFileHelper()));
        generator.addProvider(new ModEntityTypeTagsProvider(generator, event.getExistingFileHelper()));
        generator.addProvider(new ModLootProvider(generator));
        generator.addProvider(new ModRecipeProvider(generator));
        generator.addProvider(new ModAdvancements(generator, event.getExistingFileHelper()));
    }
}
