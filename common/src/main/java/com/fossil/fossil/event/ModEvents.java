package com.fossil.fossil.event;

import com.fossil.fossil.config.FossilConfig;
import com.fossil.fossil.entity.ai.AnimalFearGoal;
import com.fossil.fossil.entity.prehistoric.IScaryDinosaur;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.util.Gender;
import dev.architectury.event.EventResult;
import dev.architectury.event.events.common.EntityEvent;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.animal.*;
import net.minecraft.world.entity.animal.horse.AbstractHorse;
import net.minecraft.world.entity.player.Player;

import java.util.Random;

public class ModEvents {

    public static void init() {
        EntityEvent.ADD.register((entity, level) -> {
            if (entity instanceof PathfinderMob mob && isLivestock(mob) && FossilConfig.isEnabled("animalsFearDinos")) {
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

    public static void growEntity(PrehistoricEntityType embryo, LivingEntity parent) {
        Random random = parent.level.random;
        if (parent.level.isClientSide) {
            for (int i = 0; i < 7; ++i) {
                double d = random.nextGaussian() * 0.02;
                double e = random.nextGaussian() * 0.02;
                double f = random.nextGaussian() * 0.02;
                parent.level.addParticle(ParticleTypes.HEART, parent.getRandomX(1.0), parent.getRandomY() + 0.5, parent.getRandomZ(1.0), d, e, f);
            }
            return;
        }
        ServerLevel level = (ServerLevel) parent.level;
        Entity newEntity = embryo.entity.create(level);
        int result = random.nextInt(100);
        if (newEntity instanceof AbstractHorse newHorse) {
            if (parent instanceof AbstractHorse parentHorse && parent.getClass().equals(newEntity.getClass())) {
                if (result < 10) {
                    if (parentHorse.getOwnerUUID() != null) {
                        newHorse.setOwnerUUID(parentHorse.getOwnerUUID());
                        newHorse.setTamed(true);
                    }
                    //TODO: Horse Variants and Markings
                } else {
                    newEntity = parentHorse.getBreedOffspring(level, parentHorse);
                }
            } else {
                newEntity = newHorse.getBreedOffspring(level, newHorse);
            }
        }
        //TODO: Quagga
        if (newEntity instanceof Prehistoric prehistoric) {
            Player player = level.getNearestPlayer(parent, 15);
            if (player != null) {
                prehistoric.tame(player);
            }
            prehistoric.setGender(Gender.random(level.random));
            prehistoric.setAgeinTicks(0);
            prehistoric.setAgeInDays(0);
        } else if (newEntity instanceof Animal) {
            ((Animal) newEntity).setAge(-24000);
        }
        newEntity.moveTo(parent.getX(), parent.getY(), parent.getZ(), parent.getYRot(), parent.getXRot());
        level.addFreshEntity(newEntity);
    }
}
