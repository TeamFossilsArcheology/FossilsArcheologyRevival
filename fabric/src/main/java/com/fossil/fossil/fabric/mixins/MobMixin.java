package com.fossil.fossil.fabric.mixins;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
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
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(Mob.class)
public abstract class MobMixin extends LivingEntity {

    private MobMixin(EntityType<? extends LivingEntity> entityType, Level level) {
        super(entityType, level);
    }

    @Redirect(method = "serverAiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/goal/GoalSelector;tick()V"))
    protected final void disableGoalAI(GoalSelector instance) {
        Mob entity = ((Mob) (Object) this);
        if (entity instanceof Prehistoric prehistoric) {
            CompoundTag tag = prehistoric.getEntityData().get(Prehistoric.DEBUG);
            if (!tag.getBoolean("disableGoalAI")) {
                instance.tick();
            }
        } else {
            instance.tick();
        }
    }

    @Redirect(method = "serverAiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/goal/GoalSelector;tickRunningGoals(Z)V"))
    protected final void disableGoalAI(GoalSelector instance, boolean tickAllRunning) {
        Mob entity = ((Mob) (Object) this);
        if (entity instanceof Prehistoric prehistoric) {
            CompoundTag tag = prehistoric.getEntityData().get(Prehistoric.DEBUG);
            if (!tag.getBoolean("disableGoalAI")) {
                instance.tickRunningGoals(tickAllRunning);
            }
        } else {
            instance.tickRunningGoals(tickAllRunning);
        }
    }

    @Redirect(method = "serverAiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/control/MoveControl;tick()V"))
    protected final void disableMoveAI(MoveControl instance) {
        Mob entity = ((Mob) (Object) this);
        if (entity instanceof Prehistoric prehistoric) {
            CompoundTag tag = prehistoric.getEntityData().get(Prehistoric.DEBUG);
            if (!tag.getBoolean("disableMoveAI")) {
                instance.tick();
            }
        } else {
            instance.tick();
        }
    }

    @Redirect(method = "serverAiStep", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/ai/control/LookControl;tick()V"))
    protected final void disableLookAI(LookControl instance) {
        Mob entity = ((Mob) (Object) this);
        if (entity instanceof Prehistoric prehistoric) {
            CompoundTag tag = prehistoric.getEntityData().get(Prehistoric.DEBUG);
            if (!tag.getBoolean("disableLookAI")) {
                instance.tick();
            }
        } else {
            instance.tick();
        }
    }
}
