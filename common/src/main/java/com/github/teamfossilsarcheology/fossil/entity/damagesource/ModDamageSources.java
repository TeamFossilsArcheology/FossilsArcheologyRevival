package com.github.teamfossilsarcheology.fossil.entity.damagesource;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import net.minecraft.world.damagesource.DamageSource;

public class ModDamageSources extends DamageSource {

    public static final DamageSource SUFFOCATE = new ModDamageSources("aquatic_suffocate").bypassArmor();

    public ModDamageSources(String name) {
        super(FossilMod.MOD_ID + "." + name);
    }
}
