package com.github.teamfossilsarcheology.fossil.world.feature.forge;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.world.feature.ModFeatures;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegisterEvent;

import static com.github.teamfossilsarcheology.fossil.world.feature.ModFeatures.*;

/**
 * @see com.github.teamfossilsarcheology.fossil.world.feature.ModFeatures
 */
@Mod.EventBusSubscriber(modid = FossilMod.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModFeaturesImpl {
    @SubscribeEvent
    public static void registerFeatures(RegisterEvent event) {
        register(event, ASH_DISK);
        register(event, CALAMITES_TREE);
        register(event, CORDAITES_TREE);
        register(event, MUTANT_TREE);
        register(event, PALM_TREE);
        register(event, SIGILLARIA_TREE);
        register(event, TEMPSKYA_TREE);
        register(event, MOAI_STATUE);
        register(event, VOLCANO_CONE);
        register(event, VOLCANO_VENT);
    }

    private static void register(RegisterEvent event, ModFeatures.Tuple<?, ?> tuple) {
        event.register(ForgeRegistries.Keys.FEATURES, helper -> helper.register(tuple.location(), tuple.feature()));
    }

    public static void register() {
    }
}
