package com.github.teamfossilsarcheology.fossil.network;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.util.Version;
import dev.architectury.networking.NetworkChannel;

public class MessageHandler {
    public static final NetworkChannel DEBUG_CHANNEL;
    public static final NetworkChannel CAP_CHANNEL = NetworkChannel.create(FossilMod.location("cap_channel"));
    public static final NetworkChannel SYNC_CHANNEL = NetworkChannel.create(FossilMod.location("sync_channel"));

    static {
        if (Version.debugEnabled()) {
            DEBUG_CHANNEL = NetworkChannel.create(FossilMod.location("debug_channel"));
        } else {
            //Absolutely make sure this is not enabled by default
            DEBUG_CHANNEL = null;
        }
    }

}
