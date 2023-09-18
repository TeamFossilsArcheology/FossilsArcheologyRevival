package com.fossil.fossil.entity.animation;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

public class AttackAnimationLogic<T extends Prehistoric> extends AnimationLogic<T> {

    public AttackAnimationLogic(T entity) {
        super(entity);
    }

    public int getAttackDelay() {
        ActiveAnimationInfo activeAnimation = entity.getActiveAnimation("Active");
        if (activeAnimation != null) {
            return entity.getServerAnimationInfos().get(activeAnimation.animationName()).attackDelay();
        }
        return 0;
    }

    public PlayState attackPredicate(AnimationEvent<Prehistoric> event) {
        AnimationController<Prehistoric> controller = event.getController();
        if (entity.swinging) {
            if (isAnimationDone(controller.getName())) {
                entity.addActiveAnimation(controller.getName(), entity.nextAttackAnimation());
            }
            ActiveAnimationInfo activeAnimation = entity.getActiveAnimation(controller.getName());
            if (activeAnimation != null) {
                controller.setAnimation(new AnimationBuilder().addAnimation(activeAnimation.animationName()));
            }
            return PlayState.CONTINUE;
        } else {
            event.getController().markNeedsReload();
            return PlayState.STOP;
        }
    }
}
