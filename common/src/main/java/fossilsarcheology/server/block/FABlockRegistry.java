package fossilsarcheology.server.block;

import fossilsarcheology.Revival;
import fossilsarcheology.server.item.INoTab;
import fossilsarcheology.server.item.IUsesTEISR;
import net.minecraft.block.Block;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.item.WallOrFloorItem;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.lang.reflect.Field;

@Mod.EventBusSubscriber(modid = Revival.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FABlockRegistry {

    public static final BlockFossilOre FOSSIL_ORE = new BlockFossilOre();

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        try {
            for (Field f : FABlockRegistry.class.getDeclaredFields()) {
                Object obj = f.get(null);
                if (obj instanceof Block) {
                    event.getRegistry().register((Block) obj);
                } else if (obj instanceof Block[]) {
                    for (Block block : (Block[]) obj) {
                        event.getRegistry().register(block);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @SubscribeEvent
    public static void registerBlockItems(RegistryEvent.Register<Item> event) {
        try {
            for (Field f : FABlockRegistry.class.getDeclaredFields()) {
                Object obj = f.get(null);
                if (obj instanceof Block) {
                    Item.Properties props = new Item.Properties();
                    if(!(obj instanceof INoTab)){
                        props.group(Revival.TAB_BLOCKS);
                    }
                    if (obj instanceof IUsesTEISR) {
                        Revival.PROXY.setupISTER(props);
                    }
                    BlockItem blockItem = new BlockItem((Block) obj, props);
                    blockItem.setRegistryName(((Block) obj).getRegistryName());
                    event.getRegistry().register(blockItem);
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }
}
