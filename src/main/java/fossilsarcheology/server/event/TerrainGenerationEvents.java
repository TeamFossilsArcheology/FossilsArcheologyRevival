package fossilsarcheology.server.event;

import fossilsarcheology.server.world.FAWorldRegistry;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.event.terraingen.PopulateChunkEvent;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class TerrainGenerationEvents {

    @SubscribeEvent
    public void onGenerateEvent(PopulateChunkEvent.Populate event){
        if(event.getType() == PopulateChunkEvent.Populate.EventType.LAKE){
            if(event.getWorld().getBiome(new BlockPos(event.getChunkX() * 16, 0, event.getChunkZ() * 16)) == FAWorldRegistry.VOLCANO_BIOME){
                event.setResult(Event.Result.DENY);
            }
        }
    }
}
