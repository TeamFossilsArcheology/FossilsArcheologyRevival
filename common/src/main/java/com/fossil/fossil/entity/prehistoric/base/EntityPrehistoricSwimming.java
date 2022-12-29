package com.fossil.fossil.entity.prehistoric.base;

import com.fossil.fossil.util.PrehistoricEntityType;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.AgeableMob;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.Nullable;

public abstract class EntityPrehistoricSwimming extends EntityPrehistoric{

    public EntityPrehistoricSwimming(
            EntityType<? extends EntityPrehistoricSwimming> entityType,
            Level level,
            PrehistoricEntityType type,
            double baseDamage,
            double maxDamage,
            double baseHealth,
            double maxHealth,
            double baseSpeed,
            double maxSpeed,
            double baseArmor,
            double maxArmor
    ) {
        super(entityType, level, type, baseDamage, maxDamage, baseHealth, maxHealth, baseSpeed, maxSpeed, baseArmor, maxArmor);
    }

    @Override
    public void setSpawnValues() {

    }

    @Override
    public PrehistoricEntityTypeAI.Activity aiActivityType() {
        return null;
    }

    @Override
    public PrehistoricEntityTypeAI.Attacking aiAttackType() {
        return null;
    }

    @Override
    public PrehistoricEntityTypeAI.Climbing aiClimbType() {
        return null;
    }

    @Override
    public PrehistoricEntityTypeAI.Following aiFollowType() {
        return null;
    }

    @Override
    public PrehistoricEntityTypeAI.Jumping aiJumpType() {
        return null;
    }

    @Override
    public PrehistoricEntityTypeAI.Response aiResponseType() {
        return null;
    }

    @Override
    public PrehistoricEntityTypeAI.Stalking aiStalkType() {
        return null;
    }

    @Override
    public PrehistoricEntityTypeAI.Taming aiTameType() {
        return null;
    }

    @Override
    public PrehistoricEntityTypeAI.Untaming aiUntameType() {
        return null;
    }

    @Override
    public PrehistoricEntityTypeAI.Moving aiMovingType() {
        return null;
    }

    @Override
    public PrehistoricEntityTypeAI.WaterAbility aiWaterAbilityType() {
        return null;
    }

    @Override
    public int getAdultAge() {
        return 0;
    }

    @Override
    public int getMaxHunger() {
        return 0;
    }

    @Override
    public Item getOrderItem() {
        return null;
    }

    @Override
    public boolean canBeRidden() {
        return false;
    }

    @Nullable
    @Override
    public AgeableMob getBreedOffspring(ServerLevel p_241840_1_, AgeableMob p_241840_2_) {
        return null;
    }
}
