package com.github.teamfossilsarcheology.fossil.forge.compat.jade;

import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import mcp.mobius.waila.api.IWailaClientRegistration;
import mcp.mobius.waila.api.IWailaCommonRegistration;
import mcp.mobius.waila.api.IWailaPlugin;
import mcp.mobius.waila.api.WailaPlugin;

@WailaPlugin
public class FossilJadePluginForge implements IWailaPlugin {
    @Override
    public void register(IWailaCommonRegistration registration) {
        registration.registerEntityDataProvider(PrehistoricBreedingProvider.INSTANCE, Prehistoric.class);
        registration.registerEntityDataProvider(PrehistoricGrowthProvider.INSTANCE, Prehistoric.class);
    }

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.hideTarget(ModBlocks.ANU_BARRIER_FACE.get());
        registration.hideTarget(ModBlocks.ANU_BARRIER_ORIGIN.get());
    }
}