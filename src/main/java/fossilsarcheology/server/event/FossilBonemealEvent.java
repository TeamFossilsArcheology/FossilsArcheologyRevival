package fossilsarcheology.server.event;


import fossilsarcheology.server.block.FABlockRegistry;
import fossilsarcheology.server.world.gen.WorldGenPalm;
import net.minecraftforge.event.entity.player.BonemealEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class FossilBonemealEvent {

	@SubscribeEvent
	public void onUseBonemeal(BonemealEvent event) {
		if (event.getBlock().getBlock() == FABlockRegistry.PALM_SAPLING) {
			if (!event.getWorld().isRemote && WorldGenPalm.canGenTree(event.getWorld(), event.getPos()) && event.getWorld().rand.nextFloat() < 0.45D) {
				FABlockRegistry.PALM_SAPLING.generateTree(event.getWorld(), event.getPos(), event.getWorld().rand);
				event.setResult(Event.Result.ALLOW);
			}
		}
	}
}
