package com.fossil.fossil.entity.animation.fabric;

import com.fossil.fossil.fabric.mixins.AnimationControllerAccessor;
import software.bernie.geckolib3.core.controller.AnimationController;

/**
 * {@link com.fossil.fossil.entity.animation.AnimationLogic}
 */
public class AnimationLogicImpl {

    public static void setAnimationSpeed(AnimationController<?> controller, double animationSpeed, double animationTick) {
        //Updating the animation speed also retroactively changes the current tick/tickOffset of the animation
        if (animationSpeed == 0) {
            return;
        }
        if (animationSpeed != controller.getAnimationSpeed() && controller.getCurrentAnimation() != null) {
            double timeInAnim = animationTick - ((AnimationControllerAccessor) controller).getTickOffset();
            //Inverse of how much the tick will change after changing the animation speed
            double mult = controller.getAnimationSpeed() / animationSpeed;
            ((AnimationControllerAccessor) controller).setTickOffset(animationTick - timeInAnim * mult);
            controller.setAnimationSpeed(animationSpeed);
        }
    }
}
