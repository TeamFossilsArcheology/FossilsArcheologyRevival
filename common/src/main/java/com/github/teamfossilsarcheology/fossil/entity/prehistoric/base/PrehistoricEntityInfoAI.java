package com.github.teamfossilsarcheology.fossil.entity.prehistoric.base;

import com.github.teamfossilsarcheology.fossil.util.DinopediaInfo;
import net.minecraft.network.chat.Component;

import java.util.Locale;

/**
 * Many of these are unused
 * @see com.github.teamfossilsarcheology.fossil.entity.data.AI
 */
public class PrehistoricEntityInfoAI {
    public enum Moving {
        WALK, FLIGHT, AQUATIC, SEMI_AQUATIC, WALK_AND_GLIDE
    }

    public enum Response implements DinopediaInfo {
        CALM, SCARED, TERRITORIAL, AGGRESSIVE, WATER_AGGRESSIVE, WATER_CALM;
        private final Component name = Component.translatable("pedia.fossil.temperament." + name().toLowerCase(Locale.ROOT));
        private final Component description = Component.translatable("pedia.fossil.temperament." + name().toLowerCase(Locale.ROOT) + ".desc");

        @Override
        public Component getName() {
            return name;
        }

        @Override
        public Component getDescription() {
            return description;
        }
    }

    public enum Following {
        NONE, NORMAL, SKITTISH, AGGRESSIVE
    }

    public enum Taming {
        IMPRINTING, FEEDING, GEM, AQUATIC_GEM, NONE
    }

    public enum Untaming {
        NONE, ATTACK, STARVE
    }

    public enum Climbing {
        NONE, ARTHROPOD
    }

    public enum Jumping {
        BASIC, TWO_BLOCKS
    }

    public enum Stalking {
        NONE, STEALTH
    }

    public enum Activity implements DinopediaInfo {
        DIURNAL, NOCTURNAL, BOTH, NO_SLEEP;
        private final Component name = Component.translatable("pedia.fossil.activity." + name().toLowerCase(Locale.ROOT));
        private final Component description = Component.translatable("pedia.fossil.activity." + name().toLowerCase(Locale.ROOT) + ".desc");

        @Override
        public Component getName() {
            return name;
        }

        @Override
        public Component getDescription() {
            return description;
        }
    }

    public enum Attacking {
        BASIC, KNOCK_UP, GRAB, TAIL_SWING, CHARGE, DROWN, DROP, STOMP, JUMP, NONE
    }

    public enum WaterAbility {
        NONE, FLEE, ATTACK, IGNORE_AND_FISH
    }
}
