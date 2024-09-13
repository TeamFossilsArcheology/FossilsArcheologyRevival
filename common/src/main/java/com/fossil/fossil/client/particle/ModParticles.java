package com.fossil.fossil.client.particle;

import com.fossil.fossil.Fossil;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.Registry;
import net.minecraft.core.particles.ParticleType;
import net.minecraft.core.particles.SimpleParticleType;

public class ModParticles {
    public static final DeferredRegister<ParticleType<?>> PARTICLE_TYPES = DeferredRegister.create(Fossil.MOD_ID, Registry.PARTICLE_TYPE_REGISTRY);

    public static final RegistrySupplier<Type> BUBBLE = register("bubble");

    public static final RegistrySupplier<Type> REDSTONE_EXPLOSION = register("redstone_explosion");
    public static final RegistrySupplier<Type> REDSTONE_EXPLOSION_EMITTER = register("redstone_explosion_emitter");

    public static final RegistrySupplier<Type> TAR_BUBBLE = register("tar_bubble");

    public static final RegistrySupplier<Type> VOLCANO_VENT_ASH = register("volcano_vent_ash");
    public static final RegistrySupplier<Type> VOLCANO_VENT_ASH_EMITTER = register("volcano_vent_ash_emitter");

    private static RegistrySupplier<Type> register(String key) {
        return PARTICLE_TYPES.register(key, Type::new);
    }

    public static void register() {
        PARTICLE_TYPES.register();
    }

    public static class Type extends SimpleParticleType {
        public Type() {
            super(false);
        }
    }
}
