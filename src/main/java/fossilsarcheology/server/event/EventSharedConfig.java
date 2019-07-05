package fossilsarcheology.server.event;

import fossilsarcheology.Revival;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class EventSharedConfig {

    @SubscribeEvent
    public void onConfigChanged(ConfigChangedEvent.OnConfigChangedEvent event) {
        System.out.println(event.getModID());
        if (event.getModID().equalsIgnoreCase(Revival.MODID)) {
            Revival.syncConfig();
        }
    }

}
