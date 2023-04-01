package com.fossil.fossil.event;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.entity.ai.AnimalFearGoal;
import com.fossil.fossil.entity.prehistoric.IScaryDinosaur;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.animal.horse.AbstractHorse;

public class ModEvents {

    public static void init() {
        EntityEvent.ADD.register((entity, level) -> {
            if (entity instanceof PathfinderMob mob && isLivestock(mob) && Fossil.CONFIG_OPTIONS.animalsFearDinosaurs) {
                mob.goalSelector.addGoal(1, new AnimalFearGoal(mob, Prehistoric.class, 12, 1.2, 1.5,
                        living -> living instanceof IScaryDinosaur));
            }
            return EventResult.pass();
        });
    }

    private static boolean isLivestock(PathfinderMob mob) {
        //TODO: Maybe could be done with tags?
        String className = "";
        try {
            className = mob.getClass().getSimpleName();
        } catch (Exception e) {
            System.out.println(e);
        }
        return !className.isEmpty() && (mob instanceof Cow || mob instanceof Sheep || mob instanceof Pig || mob instanceof Chicken
                || mob instanceof Rabbit || mob instanceof AbstractHorse
                || className.contains("Cow") || className.contains("Sheep") || className.contains("Pig") || className.contains("Chicken")
                || className.contains("Rabbit") || className.contains("Peacock") || className.contains("Goat") || className.contains("Ferret")
                || className.contains("Hedgehog") || className.contains("Peahen") || className.contains("Peafowl") || className.contains("Sow")
                || className.contains("Hog"));
    }
}
