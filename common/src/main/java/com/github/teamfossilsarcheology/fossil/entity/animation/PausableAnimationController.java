package com.github.teamfossilsarcheology.fossil.entity.animation;


import software.bernie.geckolib.animatable.GeoEntity;
import software.bernie.geckolib.core.animation.AnimationController;

public class PausableAnimationController<T extends GeoEntity> extends AnimationController<T> {
    private boolean paused;
    private double lastTick;

    public PausableAnimationController(T animatable, String name, int transitionTickTime, AnimationStateHandler<T> animationHandler) {
        super(animatable, name, transitionTickTime, animationHandler);
    }

    public boolean isPaused() {
        return paused;
    }

    /**
     * Pauses the current animation.
     *
     * @see #overrideTick(double)
     */
    public void pause(boolean paused) {
        this.paused = paused;
    }

    /**
     * Returns the current tick. If an animation is running the tick will be within its animation length.
     */
    public double getCurrentTick() {
        if (getCurrentAnimation() != null) {
            return lastTick % getCurrentAnimation().animation().length();
        }
        return lastTick;
    }

    /**
     * Overrides the current tick. Only works if the animation is paused.
     */
    public void overrideTick(double tick) {
        this.lastTick = tick;
    }

    @Override
    protected double adjustTick(double tick) {
        if (!paused) {
            lastTick = super.adjustTick(tick);
        }
        return lastTick;
    }

    /**
     * Updates the animation speed without changing the current point in the animation
     *
     * @param animationSpeed the new animation speed
     * @param animationTick  the current animation tick returned by event.getAnimationTick()
     */
    public void setAnimationSpeed(double animationSpeed, double animationTick) {
        //Updating the animation speed also retroactively changes the current tick/tickOffset of the animation
        if (animationSpeed == 0) {
            return;
        }
        if (animationSpeed != getAnimationSpeed() && getCurrentAnimation() != null) {
            double timeInAnim = animationTick - tickOffset;
            //Inverse of how much the tick will change after changing the animation speed
            double mult = getAnimationSpeed() / animationSpeed;
            tickOffset = animationTick - timeInAnim * mult;
            setAnimationSpeed(animationSpeed);
        }
    }
}
