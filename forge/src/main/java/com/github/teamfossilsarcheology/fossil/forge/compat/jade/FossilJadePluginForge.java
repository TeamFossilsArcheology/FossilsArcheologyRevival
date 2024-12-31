package com.github.teamfossilsarcheology.fossil.forge.compat.jade;

import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import com.github.teamfossilsarcheology.fossil.block.custom_blocks.FeederBlock;
import com.github.teamfossilsarcheology.fossil.block.entity.FeederBlockEntity;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import mcp.mobius.waila.api.*;

@WailaPlugin
public class FossilJadePluginForge implements IWailaPlugin {
    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerEntityDataProvider(PrehistoricBreedingProvider.INSTANCE, Prehistoric.class);
        registration.registerEntityDataProvider(PrehistoricGrowthProvider.INSTANCE, Prehistoric.class);
        registration.registerBlockDataProvider(FeederStatusProvider.INSTANCE, FeederBlockEntity.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerComponentProvider(FeederStatusProvider.INSTANCE, TooltipPosition.BODY, FeederBlock.class);
        registration.hideTarget(ModBlocks.ANU_BARRIER_FACE.get());
        registration.hideTarget(ModBlocks.ANU_BARRIER_ORIGIN.get());
    }
}