package com.fossil.fossil.network;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.util.Version;
import dev.architectury.networking.NetworkChannel;

public class MessageHandler {
    public static final NetworkChannel DEBUG_CHANNEL;
    public static final NetworkChannel CAP_CHANNEL = NetworkChannel.create(Fossil.location("cap_channel"));
    public static final NetworkChannel SYNC_CHANNEL = NetworkChannel.create(Fossil.location("sync_channel"));

    static {
        if (Version.debugEnabled()) {
            DEBUG_CHANNEL = NetworkChannel.create(Fossil.location("debug_channel"));
        } else {
            //Absolutely make sure this is not enabled by default
            DEBUG_CHANNEL = null;
        }
    }

}
