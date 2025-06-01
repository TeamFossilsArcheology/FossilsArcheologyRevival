package com.github.teamfossilsarcheology.fossil.entity.prehistoric.fish;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricFish;
import net.minecraft.core.BlockPos;
import net.minecraft.util.RandomSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import org.jetbrains.annotations.NotNull;

public class Coelacanth extends PrehistoricFish {

    public Coelacanth(EntityType<Coelacanth> entityType, Level level) {
        super(entityType, level);
    }

    public static boolean canCoelacanthSpawn(EntityType<Coelacanth> entityType, LevelAccessor level, MobSpawnType spawnType, BlockPos pos, RandomSource rand) {
        return pos.getY() < 35 && PrehistoricFish.canSpawn(entityType, level, spawnType, pos, rand);
    }

    @Override
    public @NotNull PrehistoricEntityInfo info() {
        return PrehistoricEntityInfo.COELACANTH;
    }
}