package com.fossil.fossil.world.feature.village.forge;

import com.fossil.fossil.Fossil;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.fossil.fossil.world.feature.village.ModVillages.*;

@Mod.EventBusSubscriber(modid = Fossil.MOD_ID)
public class ModVillagesImpl {
    public static void register() {
    }

    @SubscribeEvent
    public static void addVillageBuilding(ServerAboutToStartEvent event) {
        var templatePoolRegistry = event.getServer().registryAccess().registry(Registry.TEMPLATE_POOL_REGISTRY).orElseThrow();
        var processorListRegistry = event.getServer().registryAccess().registry(Registry.PROCESSOR_LIST_REGISTRY).orElseThrow();
        var desert = templatePoolRegistry.get(new ResourceLocation("minecraft:village/desert/houses"));
        addBuildingToPool(desert, processorListRegistry, ARCHEOLOGIST_HOUSE_DESERT);
        addBuildingToPool(desert, processorListRegistry, PALEONTOLOGIST_HOUSE_DESERT);
        var plains = templatePoolRegistry.get(new ResourceLocation("minecraft:village/plains/houses"));
        addBuildingToPool(plains, processorListRegistry, ARCHEOLOGIST_HOUSE_PLAINS);
        addBuildingToPool(plains, processorListRegistry, PALEONTOLOGIST_HOUSE_PLAINS);
        var savanna = templatePoolRegistry.get(new ResourceLocation("minecraft:village/savanna/houses"));
        //addBuildingToPool(savanna, processorListRegistry, ARCHEOLOGIST_HOUSE_SAVANNA);
        //addBuildingToPool(savanna, processorListRegistry, PALEONTOLOGIST_HOUSE_SAVANNA);
        var snowy = templatePoolRegistry.get(new ResourceLocation("minecraft:village/snowy/houses"));
        addBuildingToPool(snowy, processorListRegistry, ARCHEOLOGIST_HOUSE_SNOWY);
        addBuildingToPool(snowy, processorListRegistry, PALEONTOLOGIST_HOUSE_SNOWY);
        var taiga = templatePoolRegistry.get(new ResourceLocation("minecraft:village/taiga/houses"));
        addBuildingToPool(taiga, processorListRegistry, ARCHEOLOGIST_HOUSE_TAIGA);
        addBuildingToPool(taiga, processorListRegistry, PALEONTOLOGIST_HOUSE_TAIGA);
    }
}
