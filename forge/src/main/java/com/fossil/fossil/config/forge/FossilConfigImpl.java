package com.fossil.fossil.config.forge;

import net.minecraftforge.common.ForgeConfigSpec;

public class FossilConfigImpl {
    public static boolean isEnabled(String field) {
        Object value = ForgeConfig.SPEC.getSpec().get(field);
        if (value instanceof ForgeConfigSpec.BooleanValue booleanValue) {
            return booleanValue.get();
        } else {
            switch (field) {//The forge config will not be initialized before worldgen
                case "generateFossils" -> {
                    return ForgeConfig.GENERATE_FOSSILS.get();
                }
                case "generatePermafrost" -> {
                    return ForgeConfig.GENERATE_PERMAFROST.get();
                }
                case "generateVolcanicRock" -> {
                    return ForgeConfig.GENERATE_VOLCANIC_ROCK.get();
                }
                case "generateTarSites" -> {
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
                case "hellShipSpacing" -> {
                    return ForgeConfig.HELL_SHIP_SPACING.get();
                }
                case "hellShipSeperation" -> {
                    return ForgeConfig.HELL_SHIP_SEPERATION.get();
                }
                case "tarSiteRarity" -> {
                    return ForgeConfig.TAR_SITE_RARITY.get();
                }
                case "fossilOreRarity" -> {
                    return ForgeConfig.FOSSIL_ORE_RARITY.get();
                }
                case "permafrostOreRarity" -> {
                    return ForgeConfig.PERMAFROST_RARITY.get();
                }
                default -> {
                    return 1;
                }
            }
        }
    }
}
