package com.github.teamfossilsarcheology.fossil.world.feature.village.forge;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.github.teamfossilsarcheology.fossil.world.feature.village.ModVillages.*;

/**
 * @see com.github.teamfossilsarcheology.fossil.world.feature.village.ModVillages
 */
@Mod.EventBusSubscriber(modid = FossilMod.MOD_ID)
public class ModVillagesImpl {
    public static void register() {
    }

    @SubscribeEvent
    public static void addVillageBuilding(ServerAboutToStartEvent event) {
        var templatePoolRegistry = event.getServer().registryAccess().registry(Registries.TEMPLATE_POOL).orElseThrow();
        var processorListRegistry = event.getServer().registryAccess().registry(Registries.PROCESSOR_LIST).orElseThrow();
        var desert = templatePoolRegistry.get(new ResourceLocation("minecraft:village/desert/houses"));
        addBuildingToPool(desert, processorListRegistry, ARCHEOLOGIST_HOUSE_DESERT);
        addBuildingToPool(desert, processorListRegistry, PALEONTOLOGIST_HOUSE_DESERT);
        var plains = templatePoolRegistry.get(new ResourceLocation("minecraft:village/plains/houses"));
        addBuildingToPool(plains, processorListRegistry, ARCHEOLOGIST_HOUSE_PLAINS);
        addBuildingToPool(plains, processorListRegistry, PALEONTOLOGIST_HOUSE_PLAINS);
        var savanna = templatePoolRegistry.get(new ResourceLocation("minecraft:village/savanna/houses"));
        addBuildingToPool(savanna, processorListRegistry, ARCHEOLOGIST_HOUSE_SAVANNA);
        addBuildingToPool(savanna, processorListRegistry, PALEONTOLOGIST_HOUSE_SAVANNA);
        var snowy = templatePoolRegistry.get(new ResourceLocation("minecraft:village/snowy/houses"));
        addBuildingToPool(snowy, processorListRegistry, ARCHEOLOGIST_HOUSE_SNOWY);
        addBuildingToPool(snowy, processorListRegistry, PALEONTOLOGIST_HOUSE_SNOWY);
        var taiga = templatePoolRegistry.get(new ResourceLocation("minecraft:village/taiga/houses"));
        addBuildingToPool(taiga, processorListRegistry, ARCHEOLOGIST_HOUSE_TAIGA);
        addBuildingToPool(taiga, processorListRegistry, PALEONTOLOGIST_HOUSE_TAIGA);
    }
}
