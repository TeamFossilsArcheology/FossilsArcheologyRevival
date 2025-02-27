package com.github.teamfossilsarcheology.fossil.villager;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import com.google.common.collect.ImmutableSet;
import dev.architectury.event.events.common.LifecycleEvent;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.ai.village.poi.PoiTypes;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class ModVillagers {
    public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(FossilMod.MOD_ID, Registries.POINT_OF_INTEREST_TYPE);
    public static final DeferredRegister<VillagerProfession> PROFESSIONS = DeferredRegister.create(FossilMod.MOD_ID,
            Registries.VILLAGER_PROFESSION);

    public static final RegistrySupplier<PoiType> ARCHEOLOGIST_POI = register("archeologist_poi", ModBlocks.WORKTABLE);
    public static final RegistrySupplier<VillagerProfession> ARCHEOLOGIST = register("archeologist", ARCHEOLOGIST_POI, SoundEvents.VILLAGER_WORK_FISHERMAN);
    public static final RegistrySupplier<PoiType> PALEONTOLOGIST_POI = register("paleontologist_poi", ModBlocks.ANALYZER);
    public static final RegistrySupplier<VillagerProfession> PALEONTOLOGIST = register("paleontologist", PALEONTOLOGIST_POI, SoundEvents.VILLAGER_WORK_TOOLSMITH);

    public static void register() {
        POI_TYPES.register();
        PROFESSIONS.register();
        LifecycleEvent.SETUP.register(() -> {
            registerBlockStates(ARCHEOLOGIST_POI);
            registerBlockStates(PALEONTOLOGIST_POI);
        });
    }

    private static void registerBlockStates(RegistrySupplier<PoiType> supplier) {
        Registry<PoiType> registry = BuiltInRegistries.POINT_OF_INTEREST_TYPE;
        PoiType type = supplier.get();
        registry.getResourceKey(type).ifPresentOrElse(poiTypeResourceKey -> {
            PoiTypes.registerBlockStates(registry.getHolderOrThrow(poiTypeResourceKey), type.matchingStates());
        }, () -> FossilMod.LOGGER.error("Failed to register point of interest: {}", supplier.getId()));
    }

    private static RegistrySupplier<PoiType> register(String name, RegistrySupplier<Block> block) {
        return register(name, block, 1, 1);
    }

    private static RegistrySupplier<PoiType> register(String name, RegistrySupplier<Block> block, int maxTickets, int validRange) {
        return POI_TYPES.register(name, () -> new PoiType(getBlockStates(block), maxTickets, validRange));
    }

    private static RegistrySupplier<VillagerProfession> register(String name, RegistrySupplier<PoiType> jobSite, @Nullable SoundEvent workSound) {
        return PROFESSIONS.register(name, () -> new VillagerProfession(name, holder -> holder.is(jobSite.getId()), holder -> holder.is(jobSite.getId()),
                ImmutableSet.of(), ImmutableSet.of(), workSound));
    }

    private static Set<BlockState> getBlockStates(RegistrySupplier<Block> block) {
        return ImmutableSet.copyOf(block.get().getStateDefinition().getPossibleStates());
    }

    private static ResourceKey<PoiType> key(RegistrySupplier<VillagerProfession> name) {
        return ResourceKey.create(Registries.POINT_OF_INTEREST_TYPE, name.getId());
    }
}
