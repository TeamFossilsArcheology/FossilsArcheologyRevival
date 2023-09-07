package com.fossil.fossil.entity.animation;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import net.minecraft.world.level.Level;
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
            if (entity.getAllAnimations().get(activeAnimation.animationId()) instanceof ServerAttackAnimationInfo attackAnimationInfo) {
                return attackAnimationInfo.attackDelays[0];
            }
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
                controller.setAnimation(new AnimationBuilder().addAnimation(activeAnimation.animationId()));
            }
            return PlayState.CONTINUE;
        } else {
            event.getController().markNeedsReload();
            return PlayState.STOP;
        }
    }

    /**
     * Some animations attack multiple times in single animation, so it holds var int.
     * When current gameTime equals to {@link Level#getGameTime()} + {@code any of supplied delays},
     * dinosaur will attack.
     */
    public static class ServerAttackAnimationInfo extends ServerAnimationInfo {
        public static final ServerAttackAnimationInfo EMPTY = new ServerAttackAnimationInfo("empty", 0, 0, 0);
        public final int[] attackDelays;

        /**
         * @param attackDelays array of delays to attack target.
         */
        public ServerAttackAnimationInfo(String animationId, int lengthInTicks, int... attackDelays) {
            super(animationId, lengthInTicks, false);
            this.attackDelays = attackDelays;
            if (attackDelays.length == 0) throw new IllegalArgumentException("Attack delays must not be empty");
        }

        public ServerAttackAnimationInfo(AnimationManager.Animation animation, int... attackDelays) {
            this(animation.animationId(), (int) Math.round(animation.animationLength()), attackDelays);
        }
    }
}
