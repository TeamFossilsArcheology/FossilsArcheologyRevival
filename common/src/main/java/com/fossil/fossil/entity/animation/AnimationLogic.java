package com.fossil.fossil.entity.animation;

import com.fossil.fossil.entity.prehistoric.base.PrehistoricAnimatable;
import net.minecraft.world.entity.Mob;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

public class AnimationLogic<T extends Mob & PrehistoricAnimatable> {
    protected final T entity;

    public AnimationLogic(T entity) {
        this.entity = entity;
    }

    public boolean isAnimationDone(String controller) {
        ActiveAnimationInfo activeAnimation = entity.getActiveAnimation(controller);
        if (activeAnimation != null) {
            //TODO: Use isLoop? || event.getController().getAnimationState() == AnimationState.Stopped
            return activeAnimation.endTick <= entity.level.getGameTime();
        }
        return true;
    }

    public PlayState movementPredicate(AnimationEvent<T> event) {
        AnimationController<T> controller = event.getController();
        if (event.isMoving()) {
            entity.addActiveAnimation(controller.getName(), entity.nextMovingAnimation());
        } else {
            if (entity.shouldStartEatAnimation()) {
                entity.addActiveAnimation(controller.getName(), entity.nextEatingAnimation());
                entity.setStartEatAnimation(false);//This technically doesn't work because it does not set the serverside to false
            }
            if (isAnimationDone(controller.getName())) {
                entity.addActiveAnimation(controller.getName(), entity.nextIdleAnimation());
            }
        }
        ActiveAnimationInfo activeAnimation = entity.getActiveAnimation(controller.getName());
        if (activeAnimation != null) {
            controller.setAnimation(new AnimationBuilder().addAnimation(activeAnimation.animationName));
        }
        return PlayState.CONTINUE;
    }

    public record ActiveAnimationInfo(String animationName, double endTick) {

    }
}
