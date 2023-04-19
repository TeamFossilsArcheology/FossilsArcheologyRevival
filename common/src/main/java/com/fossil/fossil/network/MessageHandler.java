package com.fossil.fossil.network;

import com.fossil.fossil.Fossil;
import dev.architectury.networking.NetworkChannel;
import net.minecraft.resources.ResourceLocation;

public class MessageHandler {
    public static final NetworkChannel DEBUG_CHANNEL = NetworkChannel.create(new ResourceLocation(Fossil.MOD_ID, "debug_channel"));

}
