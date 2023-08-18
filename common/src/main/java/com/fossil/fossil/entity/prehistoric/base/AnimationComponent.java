package com.fossil.fossil.entity.prehistoric.base;

import net.minecraft.world.entity.Mob;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

/**
 *
 * @param <T>
 */
//TODO: Rename Class and write javadoc
public class AnimationComponent<T extends Mob & PrehistoricAnimatable> {
    private final T entity;
    private int changedAnimationAt;

    public AnimationComponent(T entity) {
        this.entity = entity;
        this.changedAnimationAt = entity.tickCount;
    }

    public void tick() {
        if (isAnimationFrozen()) {
            entity.setCurrentAnimation(entity.nextIdleAnimation());
        }
        if (getCurrentAnimation().priority <= entity.MOVING_PRIORITY) {
            Prehistoric.ServerAnimationInfo next;
            if (entity.getNavigation().isInProgress()) {
                next = entity.nextMovingAnimation();
            } else {
                next = entity.nextIdleAnimation();
            }
            entity.setCurrentAnimation(next);
        }
    }

    public Prehistoric.ServerAnimationInfo getCurrentAnimation() {
        return entity.getCurrentAnimation();
    }

    public void setCurrentAnimation(@NotNull Prehistoric.ServerAnimationInfo newAnimation) {
        changedAnimationAt = entity.tickCount;
    }

    public boolean isAnimationFrozen() {
        var current = getCurrentAnimation();
        if (current.isLoop) return false;

        return changedAnimationAt + current.length < entity.tickCount;
    }

    public PlayState walkPredicate(AnimationEvent<T> event) {
        if (event.isMoving()) {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(entity.nextMovingAnimation().animationId));
        } else {
            event.getController().setAnimation(new AnimationBuilder().addAnimation(entity.nextIdleAnimation().animationId));
        }
        return PlayState.CONTINUE;
    }

    public PlayState onFrame(AnimationEvent<T> event) {
        var controller = event.getController();
        //controller.setAnimation(new AnimationBuilder().addAnimation(getCurrentAnimation().animationId));
        return PlayState.CONTINUE;
    }
}
