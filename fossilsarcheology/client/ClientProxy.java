package fossilsarcheology.client;

import fossilsarcheology.Revival;
import fossilsarcheology.client.model.ModelAncientHelmet;
import fossilsarcheology.client.model.ModelTriceratops;
import fossilsarcheology.client.render.RenderPrehistoric;
import fossilsarcheology.server.ServerProxy;
import fossilsarcheology.server.entity.FAEntityRegistry;
import net.minecraft.entity.LivingEntity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.common.Mod;

@OnlyIn(Dist.CLIENT)
@Mod.EventBusSubscriber(modid = Revival.MODID, value = Dist.CLIENT)
public class ClientProxy extends ServerProxy {
    private static final ModelAncientHelmet ANCIENT_HELMET_MODEL = new ModelAncientHelmet(0.5F);

    @OnlyIn(Dist.CLIENT)
    public void setupClient() {
        RenderingRegistry.registerEntityRenderingHandler(FAEntityRegistry.TRICERATOPS, manager -> new RenderPrehistoric(new ModelTriceratops()));
    }

    @OnlyIn(Dist.CLIENT)
    public Object getArmorModel(int i, LivingEntity entity) {
        if(i == 0){
            return ANCIENT_HELMET_MODEL;
        }
        return null;
    }

}
