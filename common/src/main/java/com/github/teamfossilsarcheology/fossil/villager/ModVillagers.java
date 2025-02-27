package com.github.teamfossilsarcheology.fossil.villager;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import com.google.common.collect.ImmutableSet;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.ai.village.poi.PoiType;
import net.minecraft.world.entity.npc.VillagerProfession;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class ModVillagers {
    public static final DeferredRegister<PoiType> POI_TYPES = DeferredRegister.create(FossilMod.MOD_ID, Registry.POINT_OF_INTEREST_TYPE_REGISTRY);
    public static final DeferredRegister<VillagerProfession> PROFESSIONS = DeferredRegister.create(FossilMod.MOD_ID,
            Registry.VILLAGER_PROFESSION_REGISTRY);

    public static final RegistrySupplier<PoiType> ARCHEOLOGIST_POI = register("archeologist_poi", ModBlocks.WORKTABLE);
    public static final RegistrySupplier<VillagerProfession> ARCHEOLOGIST = register("archeologist", ARCHEOLOGIST_POI, SoundEvents.VILLAGER_WORK_FISHERMAN);
    public static final RegistrySupplier<PoiType> PALEONTOLOGIST_POI = register("paleontologist_poi", ModBlocks.ANALYZER);
    public static final RegistrySupplier<VillagerProfession> PALEONTOLOGIST = register("paleontologist", PALEONTOLOGIST_POI, SoundEvents.VILLAGER_WORK_TOOLSMITH);

    public static void register() {
        POI_TYPES.register();
        PROFESSIONS.register();
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
}
