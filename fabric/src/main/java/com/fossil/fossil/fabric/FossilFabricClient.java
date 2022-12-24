package com.fossil.fossil.fabric;

import com.fossil.fossil.client.ModRenderLayers;
import net.fabricmc.api.ClientModInitializer;

public class FossilFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ModRenderLayers.register();
    }
}
