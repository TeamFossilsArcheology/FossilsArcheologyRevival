package com.github.teamfossilsarcheology.fossil.mixin;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricDebug;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.control.LookControl;
import net.minecraft.world.entity.ai.control.MoveControl;
import net.minecraft.world.entity.ai.goal.GoalSelector;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(Mob.class)
public abstract class MobMixin extends LivingEntity {

    private MobMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @WrapWithCondition(method = "serverAiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/goal/GoalSelector;tick()V"))
    protected final boolean disableGoalAI(GoalSelector instance) {
        if (this instanceof PrehistoricDebug prehistoric) {
            CompoundTag tag = prehistoric.getDebugTag();
            return !tag.getBoolean("disableGoalAI");
        }
        return true;
    }

    @WrapWithCondition(method = "serverAiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/goal/GoalSelector;tickRunningGoals(Z)V"))
    protected final boolean disableGoalAI(GoalSelector instance, boolean tickAllRunning) {
        if (this instanceof PrehistoricDebug prehistoric) {
            CompoundTag tag = prehistoric.getDebugTag();
            return !tag.getBoolean("disableGoalAI");
        }
        return true;
    }

    @WrapWithCondition(method = "serverAiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/control/MoveControl;tick()V"))
    protected final boolean disableMoveAI(MoveControl instance) {
        if (this instanceof PrehistoricDebug prehistoric) {
            CompoundTag tag = prehistoric.getDebugTag();
            return !tag.getBoolean("disableMoveAI");
        }
        return true;
    }

    @WrapWithCondition(method = "serverAiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/control/LookControl;tick()V"))
    protected final boolean disableLookAI(LookControl instance) {
        if (this instanceof PrehistoricDebug prehistoric) {
            CompoundTag tag = prehistoric.getDebugTag();
            return !tag.getBoolean("disableLookAI");
        }
        return true;
    }
}
