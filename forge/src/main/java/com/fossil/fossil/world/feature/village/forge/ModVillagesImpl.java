package com.fossil.fossil.world.feature.village.forge;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.world.feature.village.ModVillages;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.event.server.ServerAboutToStartEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import static com.fossil.fossil.world.feature.village.ModVillages.ARCHEOLOGIST_HOUSE;
import static com.fossil.fossil.world.feature.village.ModVillages.PALEONTOLOGIST_HOUSE;

@Mod.EventBusSubscriber(modid = Fossil.MOD_ID)
public class ModVillagesImpl {
    public static void register() {
    }

    @SubscribeEvent
    public static void addVillageBuilding(ServerAboutToStartEvent event) {
        var templatePoolRegistry = event.getServer().registryAccess().registry(Registry.TEMPLATE_POOL_REGISTRY).orElseThrow();
        var processorListRegistry = event.getServer().registryAccess().registry(Registry.PROCESSOR_LIST_REGISTRY).orElseThrow();
        var pool = templatePoolRegistry.get(new ResourceLocation("minecraft:village/plains/houses"));
        ModVillages.addBuildingToPool(pool, processorListRegistry, ARCHEOLOGIST_HOUSE.location(), ARCHEOLOGIST_HOUSE.weight());
        ModVillages.addBuildingToPool(pool, processorListRegistry, PALEONTOLOGIST_HOUSE.location(), PALEONTOLOGIST_HOUSE.weight());
    }
}
