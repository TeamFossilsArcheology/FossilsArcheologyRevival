package com.fossil.fossil.entity.prehistoric.base;

import com.fossil.fossil.util.DinopediaInfo;
import net.minecraft.network.chat.Component;
import net.minecraft.network.chat.TranslatableComponent;

public class PrehistoricEntityTypeAI {
    public enum Moving {
        WALK, FLIGHT, AQUATIC, SEMI_AQUATIC, WALK_AND_GLIDE
    }

    public enum Response implements DinopediaInfo {
        CALM, SCARED, TERRITORIAL, AGGRESSIVE, WATER_AGGRESSIVE, WATER_CALM;
        private final TranslatableComponent name = new TranslatableComponent("pedia.fossil.temperament." + name().toLowerCase());
        private final TranslatableComponent description = new TranslatableComponent("pedia.fossil.temperament.desc." + name().toLowerCase());

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
        BASIC, TWOBLOCKS
    }

    public enum Stalking {
        NONE, STEALTH
    }

    public enum Activity implements DinopediaInfo {
        DIURNAL, NOCTURNAL, BOTH, NO_SLEEP;//TODO: NO_SLEEP is unused in 1.12
        private final TranslatableComponent name = new TranslatableComponent("pedia.fossil.activity." + name().toLowerCase());
        private final TranslatableComponent description = new TranslatableComponent("pedia.fossil.activity.desc." + name().toLowerCase());

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
        BASIC, KNOCK_UP, GRAB, TAIL_SWING, CHARGE, DROWN, DROP, STOMP, JUMP
    }

    public enum WaterAbility {
        NONE, FLEE, ATTACK, IGNORE_AND_FISH
    }
}
