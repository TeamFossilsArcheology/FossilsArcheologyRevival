package fossilsarcheology.server.event;


import fossilsarcheology.server.block.FABlockRegistry;
import fossilsarcheology.server.world.gen.WorldGenCalamites;
import fossilsarcheology.server.world.gen.WorldGenPalm;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FossilBonemealEvent {

    @SubscribeEvent
    public void onUseBonemeal(BonemealEvent event) {
    }
}
