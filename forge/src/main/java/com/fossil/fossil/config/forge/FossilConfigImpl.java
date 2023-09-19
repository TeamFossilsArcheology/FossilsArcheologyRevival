package com.fossil.fossil.config.forge;

import com.fossil.fossil.config.FossilConfig;
import net.minecraftforge.common.ForgeConfigSpec;

public class FossilConfigImpl {
    public static boolean isEnabled(String field) {
        Object value = ForgeConfig.SPEC.getSpec().get(field);
        if (value instanceof ForgeConfigSpec.BooleanValue booleanValue) {
            return booleanValue.get();
        } else {
            switch (field) {//The forge config will not be initialized before worldgen
                case FossilConfig.GENERATE_FOSSILS -> {
                    return ForgeConfig.GENERATE_FOSSILS.get();
                }
                case FossilConfig.GENERATE_PERMAFROST -> {
                    return ForgeConfig.GENERATE_PERMAFROST.get();
                }
                case FossilConfig.GENERATE_VOLCANIC_ROCK -> {
                    return ForgeConfig.GENERATE_VOLCANIC_ROCK.get();
                }
                case FossilConfig.GENERATE_TAR_SITES -> {
                    return ForgeConfig.GENERATE_TAR_SITES.get();
                }
                default -> {
                    return true;
                }
            }
        }
    }

    public static int getInt(String field) {
        Object value = ForgeConfig.SPEC.getSpec().get(field);
        if (value instanceof ForgeConfigSpec.IntValue intValue) {
            return intValue.get();
        } else {
            switch (field) {
                case FossilConfig.HELL_SHIP_SPACING -> {
                    return ForgeConfig.HELL_SHIP_SPACING.get();
                }
                case FossilConfig.HELL_SHIP_SEPERATION -> {
                    return ForgeConfig.HELL_SHIP_SEPERATION.get();
                }
                case FossilConfig.TAR_SITE_RARITY -> {
                    return ForgeConfig.TAR_SITE_RARITY.get();
                }
                case FossilConfig.FOSSIL_ORE_RARITY -> {
                    return ForgeConfig.FOSSIL_ORE_RARITY.get();
                }
                case FossilConfig.PERMAFROST_RARITY -> {
                    return ForgeConfig.PERMAFROST_RARITY.get();
                }
                default -> {
                    return 1;
                }
            }
        }
    }
}
