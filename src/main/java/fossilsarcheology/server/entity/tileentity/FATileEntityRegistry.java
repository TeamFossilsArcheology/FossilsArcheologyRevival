package fossilsarcheology.server.entity.tileentity;

import fossilsarcheology.Revival;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.lang.reflect.Field;

@Mod.EventBusSubscriber(modid = Revival.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FATileEntityRegistry {

    @SubscribeEvent
    public static void registerTileEntities(final RegistryEvent.Register<TileEntityType<?>> event) {
        try {
            for (Field f : FATileEntityRegistry.class.getDeclaredFields()) {
                Object obj = f.get(null);
                if (obj instanceof TileEntityType) {
                    event.getRegistry().register((TileEntityType) obj);
                } else if (obj instanceof TileEntityType[]) {
                    for (TileEntityType te : (TileEntityType[]) obj) {
                        event.getRegistry().register(te);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
