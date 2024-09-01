package com.fossil.fossil.world.effect;

import com.fossil.fossil.Fossil;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.world.effect.MobEffect;
import net.minecraft.world.effect.MobEffectCategory;

public class ModEffects {

    public static final DeferredRegister<MobEffect> MOB_EFFECTS = DeferredRegister.create(Fossil.MOD_ID, Registry.MOB_EFFECT_REGISTRY);

    public static final RegistrySupplier<MobEffect> COMFY_BED = MOB_EFFECTS.register("comfy_bed", () -> new ComfyBedEffect(MobEffectCategory.BENEFICIAL, 8954814));

    public static void register() {
        MOB_EFFECTS.register();
    }
}
