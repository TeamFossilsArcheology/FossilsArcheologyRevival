package com.fossil.fossil.entity.animation;

import com.fossil.fossil.entity.prehistoric.base.*;
import com.fossil.fossil.network.MessageHandler;
import com.fossil.fossil.network.S2CSyncActiveAnimationMessage;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes.PLAY_ONCE;

public class AnimationLogic<T extends Mob & PrehistoricAnimatable<T>> {
    private final Map<String, ActiveAnimationInfo> activeAnimations = new HashMap<>();
    private final Map<String, ActiveAnimationInfo> nextAnimations = new HashMap<>();
    protected final T entity;

    public AnimationLogic(T entity) {
        this.entity = entity;
    }

    public Optional<ActiveAnimationInfo> getActiveAnimation(String controller) {
        return Optional.ofNullable(activeAnimations.get(controller));
    }

    public void triggerAnimation(String controller, Animation animation, String category) {
        if (animation != null) {
            ActiveAnimationInfo activeAnimationInfo = new ActiveAnimationInfo(animation.animationName, entity.level.getGameTime(),
                    entity.level.getGameTime() + animation.animationLength, category, true, 1
            );
            addNextAnimation(controller, activeAnimationInfo);
            if (!entity.level.isClientSide) {
                TargetingConditions conditions = TargetingConditions.forNonCombat().ignoreLineOfSight().range(30);
                var players = ((ServerLevel) entity.level).getPlayers(serverPlayer -> conditions.test(serverPlayer, entity));
                System.out.println("Attack trigger send: " + players.size());
                MessageHandler.SYNC_CHANNEL.sendToPlayers(players, new S2CSyncActiveAnimationMessage(entity, controller, activeAnimationInfo));
            }
        }
    }

    public void forceActiveAnimation(String controller, Animation animation, String category, double speed) {
        //TODO: More debug control
        if (animation != null) {
            ActiveAnimationInfo activeAnimationInfo = new ActiveAnimationInfo(animation.animationName, entity.level.getGameTime(),
                    entity.level.getGameTime() + animation.animationLength, category, true, speed
            );
            addNextAnimation(controller, activeAnimationInfo);
            if (!entity.level.isClientSide) {
                TargetingConditions conditions = TargetingConditions.forNonCombat().ignoreLineOfSight().range(30);
                var players = ((ServerLevel) entity.level).getPlayers(serverPlayer -> conditions.test(serverPlayer, entity));
                MessageHandler.SYNC_CHANNEL.sendToPlayers(players, new S2CSyncActiveAnimationMessage(entity, controller, activeAnimationInfo));
            }
        }
    }

    public void addActiveAnimation(String controller, Animation animation, String category) {
        if (animation != null) {
            activeAnimations.put(controller, new ActiveAnimationInfo(animation.animationName, entity.level.getGameTime(), entity.level.getGameTime() + animation.animationLength, category, false, 1));
        }
    }

    public void addNextAnimation(String controller, ActiveAnimationInfo activeAnimationInfo) {
        nextAnimations.put(controller, activeAnimationInfo);
    }

    public boolean isAnimationDone(String controller) {
        if (!activeAnimations.containsKey(controller)) {
            return false;
        }
        ActiveAnimationInfo activeAnimation = activeAnimations.get(controller);
        //TODO: Use isLoop? || event.getController().getAnimationState() == AnimationState.Stopped
        return entity.level.getGameTime() >= activeAnimation.endTick;
    }

    public double getActionDelay(String controller) {
        if (activeAnimations.containsKey(controller)) {
            ActiveAnimationInfo activeAnimation = activeAnimations.get(controller);
            Map<String, AnimationInfoManager.ServerAnimationInfo> animationData = entity.getServerAnimationInfos();
            if (animationData.containsKey(activeAnimation.animationName)) {
                return animationData.get(activeAnimation.animationName).actionDelay;
            }
        }
        return 0;
    }

    public static double getMovementSpeed(PrehistoricAnimatable<Prehistoric> entity, String animationName) {
        Map<String, AnimationInfoManager.ServerAnimationInfo> animationData = entity.getServerAnimationInfos();
        if (animationData.containsKey(animationName)) {
            return animationData.get(animationName).blocksPerSecond;
        }
        return 0;
    }

    public PlayState waterPredicate(AnimationEvent<PrehistoricSwimming> event) {
        AnimationController<PrehistoricSwimming> controller = event.getController();
        Optional<ActiveAnimationInfo> activeAnimation = getActiveAnimation(controller.getName());
        ILoopType loopType = null;

        if (activeAnimation.isPresent() && activeAnimation.get().forced && !isAnimationDone(controller.getName())) {
            loopType = PLAY_ONCE;
            event.getController().setAnimationSpeed(activeAnimation.get().speed);
        } else {
            if (event.getAnimatable().isBeached()) {
                if (activeAnimation.isEmpty() || !activeAnimation.get().category.equals("Beached")) {
                    addActiveAnimation(controller.getName(), event.getAnimatable().nextBeachedAnimation(), "Beached");
                }
            } else if (event.isMoving()) {
                if (entity.isSprinting()) {
                    addActiveAnimation(controller.getName(), entity.nextSprintingAnimation(), "Move");
                } else {
                    addActiveAnimation(controller.getName(), entity.nextMovingAnimation(), "Move");
                }
            } else {
                if (entity.isSleeping()) {
                    addActiveAnimation(controller.getName(), entity.nextSleepingAnimation(), "Sleep");
                } else if (entity.shouldStartEatAnimation()) {
                    addActiveAnimation(controller.getName(), entity.nextEatingAnimation(), "Eat");
                    entity.setStartEatAnimation(false);//This technically doesn't work because it does not set the serverside to false
                    loopType = PLAY_ONCE;
                } else if (controller.getCurrentAnimation() != null && controller.getCurrentAnimation().loop == PLAY_ONCE) {
                    if (controller.getCurrentAnimation().loop == PLAY_ONCE && controller.getAnimationState() == AnimationState.Stopped) {
                        addActiveAnimation(controller.getName(), entity.nextIdleAnimation(), "Idle");
                    }
                } else if (activeAnimation.isEmpty() || isAnimationDone(controller.getName()) || activeAnimation.get().category.equals("Beached")) {
                    addActiveAnimation(controller.getName(), entity.nextIdleAnimation(), "Idle");
                }
            }
        }
        Optional<ActiveAnimationInfo> newAnimation = getActiveAnimation(controller.getName());
        if (newAnimation.isPresent()) {
            controller.setAnimation(new AnimationBuilder().addAnimation(newAnimation.get().animationName, loopType));
        }
        return PlayState.CONTINUE;
    }

    private double lastSpeed = 1;

    public PlayState landPredicate(AnimationEvent<Prehistoric> event) {
        AnimationController<Prehistoric> controller = event.getController();
        if (nextAnimations.containsKey(controller.getName())) {
            ActiveAnimationInfo next = nextAnimations.remove(controller.getName());
            activeAnimations.put(controller.getName(), next);

            controller.setAnimation(new AnimationBuilder().addAnimation(next.animationName));
            //controller.transitionLengthTicks = next.speed;
            controller.markNeedsReload();
            return PlayState.CONTINUE;
        }
        Optional<ActiveAnimationInfo> activeAnimation = getActiveAnimation(controller.getName());
        ILoopType loopType = null;
        double animationSpeed = 1;
        String activeCategory = activeAnimation.isPresent() ? activeAnimation.get().category : "";
        if (activeAnimation.isPresent() && activeAnimation.get().forced && !isAnimationDone(controller.getName())) {
            loopType = PLAY_ONCE;
        } else {
            if (event.isMoving()) {
                Animation movementAnim;
                if (entity.isSprinting()) {
                    movementAnim = entity.nextSprintingAnimation();
                } else {
                    movementAnim = entity.nextMovingAnimation();
                }
                addActiveAnimation(controller.getName(), movementAnim, "Move");
                //All animations were done at a scale of 1 -> Slow down animation if scale is bigger than 1
                animationSpeed = 1 / event.getAnimatable().getScale();
                double animationBaseSpeed = getMovementSpeed(event.getAnimatable(), movementAnim.animationName);
                if (animationBaseSpeed > 0) {
                    //the deltaMovement of the animation should match the mobs deltaMovement
                    double mobSpeed = event.getAnimatable().getDeltaMovement().horizontalDistance() * 20;
                    animationSpeed *= mobSpeed / animationBaseSpeed;
                }
                //TODO: Breaks if you punch the mob around
                //TODO: Choose sprinting anim based on animation speed?
                if (lastSpeed > animationSpeed) {
                    //I would love to always change speed but that causes stuttering, so we just find one speed thats good enough
                    animationSpeed = lastSpeed;
                }
            } else {
                if (entity.isSleeping()) {
                    if (!activeCategory.equals("Sleep")) {
                        addActiveAnimation(controller.getName(), entity.nextSleepingAnimation(), "Sleep");
                    }
                } else if (event.getAnimatable().isSitting()) {
                    if (!activeCategory.equals("Sit")) {
                        addActiveAnimation(controller.getName(), entity.nextSittingAnimation(), "Sit");
                    }
                } else if (entity.shouldStartEatAnimation()) {
                    addActiveAnimation(controller.getName(), entity.nextEatingAnimation(), "Eat");
                    entity.setStartEatAnimation(false);//This technically doesn't work because it does not set the serverside to false
                    loopType = PLAY_ONCE;
                } else if (controller.getCurrentAnimation() != null && controller.getCurrentAnimation().loop == PLAY_ONCE) {
                    if (controller.getCurrentAnimation().loop == PLAY_ONCE && controller.getAnimationState() == AnimationState.Stopped) {
                        addActiveAnimation(controller.getName(), entity.nextIdleAnimation(), "Idle");
                    }
                } else if (!activeCategory.equals("Idle")) {
                    //TODO: Need to be able to cancel animation but also not replace running one if from same category etc
                    addActiveAnimation(controller.getName(), entity.nextIdleAnimation(), "Idle");
                    loopType = PLAY_ONCE;
                }
            }
        }
        lastSpeed = animationSpeed;
        event.getController().setAnimationSpeed(animationSpeed);
        Optional<ActiveAnimationInfo> newAnimation = getActiveAnimation(controller.getName());
        if (newAnimation.isPresent()) {
            controller.setAnimation(new AnimationBuilder().addAnimation(newAnimation.get().animationName, loopType));
        }
        return PlayState.CONTINUE;
    }

    public PlayState attackPredicate(AnimationEvent<Prehistoric> event) {
        AnimationController<Prehistoric> controller = event.getController();
        if (nextAnimations.containsKey(controller.getName())) {
            ActiveAnimationInfo next = nextAnimations.remove(controller.getName());
            activeAnimations.put(controller.getName(), next);

            controller.setAnimation(new AnimationBuilder().addAnimation(next.animationName));
            controller.transitionLengthTicks = next.speed;
            controller.markNeedsReload();
            return PlayState.CONTINUE;
        }
        if (!isAnimationDone(controller.getName())) {
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    public PlayState fishPredicate(AnimationEvent<PrehistoricFish> event) {
        AnimationController<PrehistoricFish> controller = event.getController();
        if (!entity.isInWater() && entity.isOnGround()) {
            addActiveAnimation(controller.getName(), event.getAnimatable().nextBeachedAnimation(), "Beached");
        } else if (event.isMoving()) {
            addActiveAnimation(controller.getName(), entity.nextMovingAnimation(), "Move");
        } else {
            addActiveAnimation(controller.getName(), entity.nextIdleAnimation(), "Idle");
        }
        Optional<ActiveAnimationInfo> activeAnimation = getActiveAnimation(controller.getName());
        activeAnimation.ifPresent(anim -> controller.setAnimation(new AnimationBuilder().addAnimation(anim.animationName())));
        return PlayState.CONTINUE;
    }

    public PlayState flyingPredicate(AnimationEvent<PrehistoricFlying> event) {
        AnimationController<PrehistoricFlying> controller = event.getController();
        if (nextAnimations.containsKey(controller.getName())) {
            AnimationLogic.ActiveAnimationInfo next = nextAnimations.remove(controller.getName());
            activeAnimations.put(controller.getName(), next);

            controller.setAnimation(new AnimationBuilder().addAnimation(next.animationName));
            //controller.transitionLengthTicks = next.speed;
            controller.markNeedsReload();
            return PlayState.CONTINUE;
        }

        Optional<AnimationLogic.ActiveAnimationInfo> activeAnimation = getActiveAnimation(controller.getName());
        if (activeAnimation.isPresent() && activeAnimation.get().forced() && !isAnimationDone(controller.getName())) {
            controller.setAnimation(new AnimationBuilder().addAnimation(activeAnimation.get().animationName()));
            return PlayState.CONTINUE;
        }
        double animSpeed = 1;
        if (!event.getAnimatable().isTakingOff()) {
            if (event.isMoving()) {
                Animation animation = entity.nextMovingAnimation();
                addActiveAnimation(controller.getName(), animation, "Walk");
                //All animations were done at a scale of 1 -> Slow down animation if scale is bigger than 1
                animSpeed = 1 / event.getAnimatable().getScale();
                double animationBaseSpeed = AnimationLogic.getMovementSpeed(event.getAnimatable(), animation.animationName);
                if (animationBaseSpeed > 0) {
                    //the deltaMovement of the animation should match the mobs deltaMovement
                    double mobSpeed = event.getAnimatable().getDeltaMovement().horizontalDistance() * 20;
                    animSpeed *= mobSpeed / animationBaseSpeed;
                }
                if (lastSpeed > animSpeed) {
                    //I would love to always change speed but that causes stuttering, so we just find one speed thats good enough
                    animSpeed = lastSpeed;
                }
            } else {
                Animation animation = entity.nextIdleAnimation();
                addActiveAnimation(controller.getName(), animation, "Idle");
            }
        }
        lastSpeed = animSpeed;
        event.getController().setAnimationSpeed(animSpeed);
        Optional<AnimationLogic.ActiveAnimationInfo> newAnimation = getActiveAnimation(controller.getName());
        if (newAnimation.isPresent()) {
            controller.setAnimation(new AnimationBuilder().addAnimation(newAnimation.get().animationName()));
        }
        return PlayState.CONTINUE;
    }

    public record ActiveAnimationInfo(String animationName, double startTick, double endTick, String category,
                                      boolean forced, double speed) {

    }
}
