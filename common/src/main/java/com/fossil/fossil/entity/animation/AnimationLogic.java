package com.fossil.fossil.entity.animation;

import com.fossil.fossil.entity.prehistoric.base.PrehistoricAnimatable;
import net.minecraft.world.entity.Mob;
import org.jetbrains.annotations.NotNull;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

/**
 *
 */
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
                entity.setStartEatAnimation(false);
            }
            if (isAnimationDone(controller.getName())) {
                entity.addActiveAnimation(controller.getName(), entity.nextIdleAnimation());
            }
        }
        ActiveAnimationInfo activeAnimation = entity.getActiveAnimation(controller.getName());
        if (activeAnimation != null) {
            controller.setAnimation(new AnimationBuilder().addAnimation(activeAnimation.animationId));
        }
        return PlayState.CONTINUE;
    }

    public static class ServerAnimationInfo {
        public static final ServerAnimationInfo EMPTY = new ServerAnimationInfo("empty", 0, false);
        public final @NotNull String animationId;
        public final int length;
        public final boolean isLoop;

        public ServerAnimationInfo(@NotNull String animationId, int lengthInTicks, boolean isLoop) {
            this.animationId = animationId;
            this.length = lengthInTicks;
            this.isLoop = isLoop;
        }

        public ServerAnimationInfo(AnimationManager.Animation animation) {
            this(animation.animationId(), (int) Math.round(animation.animationLength()), animation.loop() == ILoopType.EDefaultLoopTypes.LOOP);
        }
    }

    public record ActiveAnimationInfo(String animationId, long endTick) {

    }
}
