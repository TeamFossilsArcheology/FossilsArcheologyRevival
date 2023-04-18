package com.fossil.fossil.villager;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.block.ModBlocks;
import com.google.common.collect.ImmutableSet;
import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Set;

public class ModVillagers {
    public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(Fossil.MOD_ID, Registry.POINT_OF_INTEREST_TYPE_REGISTRY);
    public static final DeferredRegister<VillagerProfession> PROFESSIONS = DeferredRegister.create(Fossil.MOD_ID,
            Registry.VILLAGER_PROFESSION_REGISTRY);


    public static final RegistrySupplier<PoiType> ARCHEOLOGIST_POI = POI_TYPES.register("archeologist_poi",
            () -> new PoiType("archeologist_poi", getBlockStates(ModBlocks.WORKTABLE.get()), 1, 1));
    public static final RegistrySupplier<VillagerProfession> ARCHEOLOGIST = PROFESSIONS.register("archeologist",
            () -> new VillagerProfession("archeologist", ARCHEOLOGIST_POI.get(), ImmutableSet.of(), ImmutableSet.of(),
                    SoundEvents.VILLAGER_WORK_LEATHERWORKER));
    public static final RegistrySupplier<PoiType> PALEONTOLOGIST_POI = POI_TYPES.register("paleontologist_poi",
            () -> new PoiType("paleontologist_poi", getBlockStates(ModBlocks.ANALYZER.get()), 1, 1));
    public static final RegistrySupplier<VillagerProfession> PALEONTOLOGIST = PROFESSIONS.register("paleontologist",
            () -> new VillagerProfession("paleontologist", PALEONTOLOGIST_POI.get(), ImmutableSet.of(), ImmutableSet.of(),
                    SoundEvents.VILLAGER_WORK_CARTOGRAPHER));

    public static void register() {
        POI_TYPES.register();
        PROFESSIONS.register();
        LifecycleEvent.SETUP.register(() -> {
            PoiType.registerBlockStates(ARCHEOLOGIST_POI.get());
            PoiType.registerBlockStates(PALEONTOLOGIST_POI.get());
        });
    }

    private static Set<BlockState> getBlockStates(Block block) {
        return ImmutableSet.copyOf(block.getStateDefinition().getPossibleStates());
    }
}
