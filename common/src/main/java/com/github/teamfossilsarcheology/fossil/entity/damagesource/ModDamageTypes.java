package com.github.teamfossilsarcheology.fossil.entity.damagesource;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.damagesource.DamageEffects;
import net.minecraft.world.damagesource.DamageType;

public class ModDamageTypes {
    public static final ResourceKey<DamageType> SUFFOCATE_KEY = createKey("aquatic_suffocate");

    public static void bootstrap(BootstapContext<DamageType> context) {
        context.register(SUFFOCATE_KEY, new DamageType(FossilMod.MOD_ID + ".aquatic_suffocate", 0.0F, DamageEffects.DROWNING));
    }

    private static ResourceKey<DamageType> createKey(String name) {
        return ResourceKey.create(Registries.DAMAGE_TYPE, new ResourceLocation(FossilMod.MOD_ID, name));
    }
}
