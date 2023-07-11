package com.fossil.fossil.entity.prehistoric;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityTypeAI;
import com.fossil.fossil.entity.prehistoric.base.Pterosaurs;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.Level;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.manager.AnimationFactory;

import java.util.Map;

public class Nyctosaurus extends Pterosaurs {
    public Nyctosaurus(EntityType<Nyctosaurus> entityType, Level level) {
        super(
                entityType,
                level,
                false,
                0.4F,
                1.0F,
                0F,
                0F,
                2,
                3,
                3,
                6,
                5,
                20,
                0.2,
                0.45,
                0,
                0
        );
    }

    @Override
    public Entity[] getCustomParts() {
        return new Entity[0];
    }

    @Override
    public PrehistoricEntityType type() {
        return null;
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
    public int getMaxHunger() {
        return 0;
    }

    @Override
    public Map<String, ServerAnimationInfo> getAllAnimations() {
        return null;
    }

    @Override
    public Item getOrderItem() {
        return null;
    }

    @Override
    public boolean canBeRidden() {
        return false;
    }

    @Override
    public ServerAnimationInfo nextEatingAnimation() {
        return null;
    }

    @Override
    public @NotNull ServerAnimationInfo nextIdleAnimation() {
        return null;
    }

    @Override
    public @NotNull ServerAnimationInfo nextMovingAnimation() {
        return null;
    }

    @Override
    public @NotNull Prehistoric.ServerAnimationInfo nextChasingAnimation() {
        return null;
    }

    @Override
    public @NotNull Prehistoric.ServerAttackAnimationInfo nextAttackAnimation() {
        return null;
    }

    @Override
    public ServerAnimationInfo getTakeOffAnimation() {
        return null;
    }

    @Override
    public boolean isFlying() {
        return false;
    }

    @Override
    public AnimationFactory getFactory() {
        return null;
    }
}
