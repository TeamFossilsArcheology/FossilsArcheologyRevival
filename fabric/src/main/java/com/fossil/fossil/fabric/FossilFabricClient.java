package com.fossil.fossil.fabric;

import com.fossil.fossil.client.ClientInit;
import net.fabricmc.api.ClientModInitializer;

public class FossilFabricClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        ClientInit.immediate();
        ClientInit.later();
    }
}
