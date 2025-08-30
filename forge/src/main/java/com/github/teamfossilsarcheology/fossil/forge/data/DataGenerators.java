package com.github.teamfossilsarcheology.fossil.forge.data;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.forge.data.advancements.FossilAdvancements;
import com.github.teamfossilsarcheology.fossil.forge.data.providers.*;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.DataProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import net.minecraftforge.common.data.ForgeAdvancementProvider;
import net.minecraftforge.data.event.GatherDataEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.util.List;
import java.util.concurrent.CompletableFuture;

@Mod.EventBusSubscriber(modid = FossilMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class DataGenerators {

    @SubscribeEvent
    public static void register(GatherDataEvent event) {
        DataGenerator generator = event.getGenerator();
        CompletableFuture<HolderLookup.Provider> lookup = event.getLookupProvider();
        ExistingFileHelper efh = event.getExistingFileHelper();
        generator.addProvider(event.includeClient(), (DataProvider.Factory<DataProvider>) output -> new ModBlockStateProvider(output, efh));
        generator.addProvider(event.includeClient(), (DataProvider.Factory<DataProvider>) output -> new ModItemProvider(output, efh));
        var blockTags = new ModBlockTagsProvider(generator.getPackOutput(), lookup, efh);
        generator.addProvider(event.includeServer(), blockTags);
        generator.addProvider(event.includeServer(), (DataProvider.Factory<DataProvider>) output -> new ModItemTagsProvider(output, lookup, blockTags.contentsGetter(), efh));
        generator.addProvider(event.includeServer(), (DataProvider.Factory<DataProvider>) output -> new ModEntityTypeTagsProvider(output, lookup, efh));
        generator.addProvider(event.includeServer(), (DataProvider.Factory<DataProvider>) ModRecipeProvider::new);
        generator.addProvider(event.includeServer(), (DataProvider.Factory<DataProvider>) output -> new ForgeAdvancementProvider(
                output, lookup, efh, List.of((registries, writer, existingFileHelper) -> new FossilAdvancements().accept(writer))));
        generator.addProvider(event.includeServer(), (DataProvider.Factory<DataProvider>) ModLootProvider::new);
        generator.addProvider(event.includeServer(), (DataProvider.Factory<DataProvider>) output -> new ModWorldGenProvider(output, lookup));
        generator.addProvider(event.includeServer(), (DataProvider.Factory<DataProvider>) ModFoodValueProvider::new);
    }
}
