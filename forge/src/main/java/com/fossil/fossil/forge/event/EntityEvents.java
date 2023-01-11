package com.fossil.fossil.forge.event;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import net.minecraftforge.event.entity.living.LivingKnockBackEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = Fossil.MOD_ID)
public class EntityEvents {
    @SubscribeEvent
    public static void onKnockBack(LivingKnockBackEvent event) {
        double newStrength = Prehistoric.beforeKnockBack(
                event.getEntityLiving(),
                event.getStrength(),
                event.getRatioX(),
                event.getRatioZ()
        );
        event.setStrength((float) newStrength);
    }
}
