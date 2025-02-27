package com.github.teamfossilsarcheology.fossil.forge.data;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.forge.data.providers.*;
import com.github.teamfossilsarcheology.fossil.util.ModConstants;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = FossilMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void register(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        generator.addProvider(true, new ModBlockStateProvider(generator, event.getExistingFileHelper()));
        generator.addProvider(true, new ModItemProvider(generator, event.getExistingFileHelper()));
        BlockTagsProvider blockTagsProvider = new ModBlockTagsProvider(generator, event.getExistingFileHelper());
        generator.addProvider(true, blockTagsProvider);
        generator.addProvider(true, new ModItemTagsProvider(generator, blockTagsProvider, event.getExistingFileHelper()));
        generator.addProvider(true, new ModEntityTypeTagsProvider(generator, event.getExistingFileHelper()));
        generator.addProvider(true, new ModLootProvider(generator));
        generator.addProvider(true, new ModRecipeProvider(generator));
        generator.addProvider(true, new ModAdvancements(generator, event.getExistingFileHelper()));
    }
}
