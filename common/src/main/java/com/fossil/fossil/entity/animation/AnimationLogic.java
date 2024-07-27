package com.fossil.fossil.entity.animation;

import com.fossil.fossil.entity.prehistoric.base.*;
import com.fossil.fossil.network.MessageHandler;
import com.fossil.fossil.network.S2CSyncActiveAnimationMessage;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

import static software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes.LOOP;
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

    public void triggerAnimation(String controller, Animation animation, Category category) {
        if (animation != null) {
            ActiveAnimationInfo activeAnimationInfo = new ActiveAnimationInfo(animation.animationName, entity.level.getGameTime(),
                    entity.level.getGameTime() + animation.animationLength, category, true, 1
            );
            addNextAnimation(controller, activeAnimationInfo);
            if (!entity.level.isClientSide) {
                TargetingConditions conditions = TargetingConditions.forNonCombat().ignoreLineOfSight().range(30);
                var players = ((ServerLevel) entity.level).getPlayers(serverPlayer -> conditions.test(serverPlayer, entity));
                MessageHandler.SYNC_CHANNEL.sendToPlayers(players, new S2CSyncActiveAnimationMessage(entity, controller, activeAnimationInfo));
            }
        }
    }

    public void forceAnimation(String controller, Animation animation, Category category, double speed) {
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

    public boolean addActiveAnimation(String controller, Animation animation, Category category) {
        if (animation == null) {
            return false;
        }
        ActiveAnimationInfo active = getActiveAnimation(controller).orElse(null);
        if (active == null) {
            activeAnimations.put(controller, new ActiveAnimationInfo(animation.animationName, entity.level.getGameTime(), entity.level.getGameTime() + animation.animationLength, category, false, 5));
            return true;
        }
        boolean replaceAnim = false;
        boolean isLoop = entity.getAllAnimations().get(active.animationName).loop == LOOP;
        if (active.category == category && isAnimationDone(active)) {
            replaceAnim = !isLoop || entity.getRandom().nextFloat() < category.chance;
        } else if (active.category != category) {
            replaceAnim = isLoop || isAnimationDone(active);
        }
        if (replaceAnim) {
            activeAnimations.put(controller, new ActiveAnimationInfo(animation.animationName, entity.level.getGameTime(), entity.level.getGameTime() + animation.animationLength, category, false, 5));
            return true;
        }
        return false;
    }

    public void addNextAnimation(String controller, ActiveAnimationInfo activeAnimationInfo) {
        nextAnimations.put(controller, activeAnimationInfo);
    }

    private boolean isAnimationDone(String controller) {
        if (!activeAnimations.containsKey(controller)) {
            return false;
        }
        ActiveAnimationInfo activeAnimation = activeAnimations.get(controller);
        return isAnimationDone(activeAnimation);
    }

    private boolean isAnimationDone(ActiveAnimationInfo activeAnimation) {
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
            //event.getController().setAnimationSpeed(activeAnimation.get().speed);
        } else {
            if (event.getAnimatable().isBeached()) {
                addActiveAnimation(controller.getName(), event.getAnimatable().nextBeachedAnimation(), Category.BEACHED);
            } else if (event.isMoving()) {
                if (entity.isSprinting()) {
                    addActiveAnimation(controller.getName(), entity.nextSprintingAnimation(), Category.SPRINT);
                } else {
                    addActiveAnimation(controller.getName(), entity.nextMovingAnimation(), Category.WALK);
                }
            } else if (entity.isSleeping()) {
                addActiveAnimation(controller.getName(), entity.nextSleepingAnimation(), Category.SLEEP);
            } else if (entity.shouldStartEatAnimation()) {
                addActiveAnimation(controller.getName(), entity.nextEatingAnimation(), Category.EAT);
                entity.setStartEatAnimation(false);//This technically doesn't work because it does not set the serverside to false
            } else {
                addActiveAnimation(controller.getName(), entity.nextIdleAnimation(), Category.IDLE);
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
            controller.transitionLengthTicks = next.speed;
            controller.markNeedsReload();
            return PlayState.CONTINUE;
        }
        Optional<ActiveAnimationInfo> activeAnimation = getActiveAnimation(controller.getName());
        ILoopType loopType = null;
        double animationSpeed = 1;
        if (activeAnimation.isPresent() && activeAnimation.get().forced && !isAnimationDone(controller.getName())) {
            loopType = PLAY_ONCE;
        } else {
            if (event.isMoving()) {
                Animation movementAnim;
                if (entity.isSprinting()) {
                    movementAnim = entity.nextSprintingAnimation();
                    addActiveAnimation(controller.getName(), movementAnim, Category.WALK);
                } else {
                    movementAnim = entity.nextMovingAnimation();
                    addActiveAnimation(controller.getName(), movementAnim, Category.SPRINT);
                }
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
            } else if (entity.isSleeping()) {
                addActiveAnimation(controller.getName(), entity.nextSleepingAnimation(), Category.SLEEP);
            } else if (event.getAnimatable().isSitting()) {
                addActiveAnimation(controller.getName(), entity.nextSittingAnimation(), Category.SIT);
            } else if (entity.shouldStartEatAnimation()) {
                if (addActiveAnimation(controller.getName(), entity.nextEatingAnimation(), Category.EAT)) {
                    entity.setStartEatAnimation(false);//This technically doesn't work because it does not set the serverside to false
                }
            } else {
                addActiveAnimation(controller.getName(), entity.nextIdleAnimation(), Category.IDLE);
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

    public PlayState grabAttackPredicate(AnimationEvent<PrehistoricSwimming> event) {
        AnimationController<PrehistoricSwimming> controller = event.getController();
        if (nextAnimations.containsKey(controller.getName())) {
            ActiveAnimationInfo next = nextAnimations.remove(controller.getName());
            activeAnimations.put(controller.getName(), next);

            controller.setAnimation(new AnimationBuilder().addAnimation(next.animationName));
            controller.transitionLengthTicks = next.speed;
            controller.markNeedsReload();
            return PlayState.CONTINUE;
        }
        if (event.getAnimatable().isDoingGrabAttack()) {
            addActiveAnimation(controller.getName(), event.getAnimatable().nextGrabbingAnimation(), Category.ATTACK);
        } else if (isAnimationDone(controller.getName())) {
            activeAnimations.remove(controller.getName());
        }
        Optional<AnimationLogic.ActiveAnimationInfo> newAnimation = getActiveAnimation(controller.getName());
        if (newAnimation.isPresent()) {
            controller.setAnimation(new AnimationBuilder().addAnimation(newAnimation.get().animationName()));
            return PlayState.CONTINUE;
        } else {
            event.getController().markNeedsReload();
            return PlayState.STOP;
        }
    }

    public PlayState fishPredicate(AnimationEvent<PrehistoricFish> event) {
        AnimationController<PrehistoricFish> controller = event.getController();
        if (!entity.isInWater() && entity.isOnGround()) {
            addActiveAnimation(controller.getName(), event.getAnimatable().nextBeachedAnimation(), Category.BEACHED);
        } else if (event.isMoving()) {
            addActiveAnimation(controller.getName(), entity.nextMovingAnimation(), Category.WALK);
        } else {
            addActiveAnimation(controller.getName(), entity.nextIdleAnimation(), Category.IDLE);
        }
        Optional<ActiveAnimationInfo> newAnimation = getActiveAnimation(controller.getName());
        newAnimation.ifPresent(newInfo -> controller.setAnimation(new AnimationBuilder().addAnimation(newInfo.animationName())));
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
                addActiveAnimation(controller.getName(), animation, Category.WALK);
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
            } else if (entity.isSleeping()) {
                addActiveAnimation(controller.getName(), entity.nextSleepingAnimation(), Category.SLEEP);
            } else if (event.getAnimatable().isSitting()) {
                addActiveAnimation(controller.getName(), entity.nextSittingAnimation(), Category.SIT);
            } else if (entity.shouldStartEatAnimation()) {
                addActiveAnimation(controller.getName(), entity.nextEatingAnimation(), Category.EAT);
                entity.setStartEatAnimation(false);//This technically doesn't work because it does not set the serverside to false
            } else {
                addActiveAnimation(controller.getName(), entity.nextIdleAnimation(), Category.IDLE);
            }
        }
        lastSpeed = animSpeed;
        event.getController().setAnimationSpeed(animSpeed);
        Optional<AnimationLogic.ActiveAnimationInfo> newAnimation = getActiveAnimation(controller.getName());
        newAnimation.ifPresent(newInfo -> controller.setAnimation(new AnimationBuilder().addAnimation(newInfo.animationName())));
        return PlayState.CONTINUE;
    }

    public record ActiveAnimationInfo(String animationName, double startTick, double endTick, Category category,
                                      boolean forced, double speed) {

    }

    public enum Category {
        ATTACK, BEACHED(false, 0), EAT, IDLE, SIT(true, 0.2f),
        SLEEP(true, 0.2f), SPRINT, WALK, NONE;
        public final boolean canBeReplaced;
        public final float chance;

        Category() {
            this(true, 1);
        }

        Category(boolean canBeReplaced, float chance) {
            this.canBeReplaced = canBeReplaced;
            this.chance = chance;
        }
    }
}
