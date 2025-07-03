package com.github.teamfossilsarcheology.fossil.item;

import com.github.teamfossilsarcheology.fossil.entity.ModEntities;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.DinosaurEgg;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricMobType;
import net.minecraft.advancements.Advancement;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.gameevent.GameEvent;

public class DinoEggItem extends EggItem {
    public DinoEggItem(PrehistoricEntityInfo info) {
        super(info, info.mobType == PrehistoricMobType.DINOSAUR_AQUATIC ? "sac" : "egg");
    }

    @Override
    protected boolean spawnMob(ServerPlayer player, ServerLevel level, double x, double y, double z, boolean aquatic) {
        if (player == null) {
            return false;
        }
        if (level.isClientSide) {
            return true;
        }
        PrehistoricEntityInfo prehistoricInfo = ((PrehistoricEntityInfo) info);
        if (aquatic && !prehistoricInfo.isViviparousAquatic()) {
            return false;
        }
        if (!prehistoricInfo.isViviparousAquatic()) {
            DinosaurEgg egg = ModEntities.DINOSAUR_EGG.get().create(level);
            if (egg == null) {
                return false;
            }
            Advancement adv = level.getServer().getAdvancements().getAdvancement(DinosaurEgg.GOLDEN_EGG_ADV);
            if (adv != null && player.getAdvancements().getOrStartProgress(adv).isDone()) {
                egg.setGoldenEgg(level.getRandom().nextFloat() < 0.05);
            }
            egg.moveTo(x, y, z, 0, 0);
            egg.setPrehistoricEntityInfo(prehistoricInfo);
            level.addFreshEntity(egg);
            egg.gameEvent(GameEvent.ENTITY_PLACE, player);
        } else {
            Entity entity = DinosaurEgg.hatchEgg(level, x, y, z, player, prehistoricInfo, false);
            entity.gameEvent(GameEvent.ENTITY_PLACE, player);
        }
        return true;
    }
}
