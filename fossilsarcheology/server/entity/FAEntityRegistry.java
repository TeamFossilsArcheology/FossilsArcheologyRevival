package fossilsarcheology.server.entity;

import fossilsarcheology.Revival;
import fossilsarcheology.server.entity.prehistoric.EntityTriceratops;
import fossilsarcheology.server.entity.prehistoric.base.EntityPrehistoric;
import net.minecraft.entity.EntityClassification;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ai.attributes.GlobalEntityTypeAttributes;
import net.minecraft.item.Item;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

import java.lang.reflect.Field;

@Mod.EventBusSubscriber(modid = Revival.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class FAEntityRegistry {

    public static final EntityType<EntityTriceratops> TRICERATOPS = registerEntity(EntityType.Builder.create(EntityTriceratops::new, EntityClassification.CREATURE).size(1.1F, 0.6F), "triceratops");

    private static final EntityType registerEntity(EntityType.Builder builder, String entityName) {
        ResourceLocation nameLoc = new ResourceLocation(Revival.MODID, entityName);
        return (EntityType) builder.build(entityName).setRegistryName(nameLoc);
    }

    private static void initializeAttributes() {
        GlobalEntityTypeAttributes.put(TRICERATOPS, EntityPrehistoric.bakeAttributes().create());
    }

    @SubscribeEvent
    public static void registerEntities(RegistryEvent.Register<EntityType<?>> event) {
        try {
            for (Field f : FAEntityRegistry.class.getDeclaredFields()) {
                Object obj = f.get(null);
                if (obj instanceof EntityType) {
                    event.getRegistry().register((EntityType) obj);
                } else if (obj instanceof EntityType[]) {
                    for (EntityType type : (EntityType[]) obj) {
                        event.getRegistry().register(type);

                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        initializeAttributes();
    }

    @SubscribeEvent
    public static void registerSpawnEggs(RegistryEvent.Register<Item> event) {
        event.getRegistry().register(new SpawnEggItem(TRICERATOPS, 0X64352D, 0X251A17, new Item.Properties().group(Revival.TAB_ITEMS)).setRegistryName("alexsmobs:spawn_egg_triceratops"));

    }
}
