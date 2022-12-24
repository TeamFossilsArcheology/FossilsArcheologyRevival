package fossilsarcheology.server;

import fossilsarcheology.Revival;
import fossilsarcheology.server.entity.prehistoric.base.EntityPrehistoric;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Revival.MODID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ServerProxy {

    public void setup() {
    }

    public void setupClient() {
    }

    public void setupParticles() {
    }

    public Item.Properties setupISTER(Item.Properties props) {
        return props;
    }

    public Object getArmorModel(int i, LivingEntity entity) {
        return null;
    }

    public void setPediaObject(EntityPrehistoric prehistoric) {
    }
}
