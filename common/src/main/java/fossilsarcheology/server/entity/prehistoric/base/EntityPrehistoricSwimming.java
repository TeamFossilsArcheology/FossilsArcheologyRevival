package fossilsarcheology.server.entity.prehistoric.base;

import fossilsarcheology.server.util.PrehistoricEntityType;
import net.minecraft.entity.AgeableEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.item.Item;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.world.World;
import net.minecraft.world.server.ServerWorld;

import javax.annotation.Nullable;

public abstract class EntityPrehistoricSwimming extends EntityPrehistoric{

    public EntityPrehistoricSwimming(EntityType t, World world, PrehistoricEntityType type, double baseDamage, double maxDamage, double baseHealth, double maxHealth, double baseSpeed, double maxSpeed, double baseArmor, double maxArmor) {
        super(t, world, type, baseDamage, maxDamage, baseHealth, maxHealth, baseSpeed, maxSpeed, baseArmor, maxArmor);
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
    public AgeableEntity func_241840_a(ServerWorld p_241840_1_, AgeableEntity p_241840_2_) {
        return null;
    }

    @Override
    public Entity getEntity() {
        return null;
    }

    @Override
    public void deserializeNBT(CompoundNBT nbt) {

    }
}
