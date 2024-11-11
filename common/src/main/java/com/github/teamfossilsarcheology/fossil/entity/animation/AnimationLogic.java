package com.github.teamfossilsarcheology.fossil.entity.animation;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.*;
import com.github.teamfossilsarcheology.fossil.entity.util.Util;
import com.github.teamfossilsarcheology.fossil.network.MessageHandler;
import com.github.teamfossilsarcheology.fossil.network.S2CSyncActiveAnimationMessage;
import com.github.teamfossilsarcheology.fossil.network.debug.S2CCancelAnimationMessage;
import dev.architectury.injectables.annotations.ExpectPlatform;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import org.apache.commons.lang3.NotImplementedException;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

import java.util.Map;
import java.util.Optional;

import static software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes.LOOP;
import static software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes.PLAY_ONCE;

public class AnimationLogic<T extends Mob & PrehistoricAnimatable<T>> {
    public static final String IDLE_CTRL = "Movement/Idle";
    public static final String EAT_CTRL = "Eat";
    public static final String ATTACK_CTRL = "Attack";
    private final Map<String, ActiveAnimationInfo> activeAnimations = new Object2ObjectOpenHashMap<>();
    /**
     * Any animation in here will replace the active animation on the next tick
     */
    private final Map<String, ActiveAnimationInfo> nextAnimations = new Object2ObjectOpenHashMap<>();
    protected final T entity;
    /**
     * {@link net.minecraft.world.entity.ai.attributes.Attributes#MOVEMENT_SPEED} calculated for the animation speed calculation
     */
    private double attributeSpeed;

    public AnimationLogic(T entity) {
        this.entity = entity;
        if (entity instanceof Prehistoric prehistoric) {
            this.attributeSpeed = prehistoric.attributes().maxSpeed();
        }
    }

    /**
     * Returns the active animation for the given controller if one is active
     *
     * @param controller the name of the controller to check
     */
    public Optional<ActiveAnimationInfo> getActiveAnimation(String controller) {
        return Optional.ofNullable(activeAnimations.get(controller));
    }

    /**
     * Server side method that will trigger the animation on the client side of all players in range.
     * The end tick of the animation will be determined by the client
     *
     * @param controller    the name of the controller the animation will play on
     * @param animationInfo the animation to play
     * @param category      the category of the animation
     */
    public void triggerAnimation(String controller, AnimationInfo animationInfo, AnimationCategory category) {
        if (animationInfo != null && !entity.level.isClientSide) {
            ActiveAnimationInfo activeAnimationInfo = new ActiveAnimationInfo(animationInfo.animation.animationName,
                    entity.level.getGameTime() + animationInfo.animation.animationLength, category, true, 5
            );
            TargetingConditions conditions = TargetingConditions.forNonCombat().ignoreLineOfSight().range(30);
            var players = ((ServerLevel) entity.level).getPlayers(serverPlayer -> conditions.test(serverPlayer, entity));
            MessageHandler.SYNC_CHANNEL.sendToPlayers(players, new S2CSyncActiveAnimationMessage(entity, controller, activeAnimationInfo));
        }
    }

    /**
     * This method can be used to debug force an animation
     *
     * @param controller       the name of the controller the animation will play on
     * @param animationInfo    the animation to play
     * @param category         the category of the animation
     * @param transitionLength the length of the transition from the previous animation in ticks
     * @param loop             whether the animation should loop (until manually stopped because forced)
     */
    public ActiveAnimationInfo forceAnimation(String controller, AnimationInfo animationInfo, AnimationCategory category, double transitionLength, boolean loop) {
        if (animationInfo != null) {
            ActiveAnimationInfo activeAnimationInfo = new ActiveAnimationInfo(animationInfo.animation.animationName,
                    entity.level.getGameTime() + animationInfo.animation.animationLength, category, true, transitionLength, loop
            );
            addNextAnimation(controller, activeAnimationInfo);
            if (!entity.level.isClientSide) {
                TargetingConditions conditions = TargetingConditions.forNonCombat().ignoreLineOfSight().range(30);
                var players = ((ServerLevel) entity.level).getPlayers(serverPlayer -> conditions.test(serverPlayer, entity));
                MessageHandler.SYNC_CHANNEL.sendToPlayers(players, new S2CSyncActiveAnimationMessage(entity, controller, activeAnimationInfo));
            }
            return activeAnimationInfo;
        }
        return null;
    }

    /**
     * Tries to add a new active animation
     *
     * @param controller the name of the controller the animation will play on
     * @param category   the category of the animation
     * @return {@code true} if the animation was successfully added
     */
    public boolean addActiveAnimation(String controller, AnimationCategory category) {
        return addActiveAnimation(controller, entity.getAnimation(category).animation, category, false);
    }

    public boolean addActiveAnimation(String controller, AnimationCategory category, boolean keepActive) {
        return addActiveAnimation(controller, entity.getAnimation(category).animation, category, keepActive);
    }

    /**
     * Tries to add a new active animation
     *
     * @param controller the name of the controller the animation will play on
     * @param animation  the animation to play
     * @param category   the category of the animation
     * @return {@code true} if the animation was successfully added
     */
    public boolean addActiveAnimation(String controller, Animation animation, AnimationCategory category, boolean keepActive) {
        //TODO: Skip if same animation
        if (animation == null) {
            return false;
        }
        ActiveAnimationInfo active = getActiveAnimation(controller).orElse(null);
        if (active == null) {
            activeAnimations.put(controller, new ActiveAnimationInfo(animation.animationName, entity.level.getGameTime() + animation.animationLength, category, false, category.transitionLength(), animation.loop.isRepeatingAfterEnd(), keepActive));
            return true;
        }
        boolean replaceAnim = false;
        if (active.category == category && isAnimationDone(active)) {
            //Loops in the same category can only replace sometimes
            replaceAnim = !active.loop || entity.getRandom().nextFloat() < category.chance();
        } else if (active.category != category) {
            //Can only replace if loop or previous animation done
            replaceAnim = (!active.keepActive && active.loop) || isAnimationDone(active);
        }
        if (replaceAnim) {
            int transitionLength = Math.max(category.transitionLength(), active.category.transitionLength());
            activeAnimations.put(controller, new ActiveAnimationInfo(animation.animationName, entity.level.getGameTime() + animation.animationLength, category, false, transitionLength, animation.loop.isRepeatingAfterEnd(), keepActive));
            return true;
        }
        return false;
    }

    /**
     * The given animation will most likely be played on the next tick
     */
    public void addNextAnimation(String controller, ActiveAnimationInfo activeAnimationInfo) {
        nextAnimations.put(controller, activeAnimationInfo);
    }

    public void cancelAnimation(String controller) {
        if (entity.level.isClientSide) {
            activeAnimations.remove(controller);
        } else {
            TargetingConditions conditions = TargetingConditions.forNonCombat().ignoreLineOfSight().range(30);
            var players = ((ServerLevel) entity.level).getPlayers(serverPlayer -> conditions.test(serverPlayer, entity));
            MessageHandler.DEBUG_CHANNEL.sendToPlayers(players, new S2CCancelAnimationMessage(entity.getId(), controller));
        }
    }

    public boolean isAnimationDone(String controller) {
        return getActiveAnimation(controller).filter(this::isAnimationDone).isPresent();
    }

    public boolean isAnimationDone(ActiveAnimationInfo activeAnimation) {
        return !activeAnimation.loop && entity.level.getGameTime() >= activeAnimation.endTick;
    }

    private boolean isBlocked() {
        if (entity instanceof PrehistoricSwimming swimming && swimming.isDoingGrabAttack()) {
            return true;
        }
        //TODO: Heavy attacks need priority. Think of something better in the future
        return entity instanceof Prehistoric prehistoric && entity.level.getGameTime() < prehistoric.getEntityHitboxData().getAttackBoxData().attackBoxEndTime();
    }

    /**
     * The action delay of an animation is defined server side by the {@link AnimationInfoLoader} and represents a
     * delay from the start of the animation until an action should be played
     */
    public double getActionDelay(String controller) {
        if (activeAnimations.containsKey(controller)) {
            ActiveAnimationInfo activeAnimation = activeAnimations.get(controller);
            Map<String, ServerAnimationInfo> animationData = entity.getServerAnimationInfos();
            if (animationData.containsKey(activeAnimation.animationName)) {
                return animationData.get(activeAnimation.animationName).actionDelay;
            }
        }
        return 0;
    }

    public static double getAnimationTargetSpeed(PrehistoricAnimatable<Prehistoric> entity, String animationName) {
        Map<String, ServerAnimationInfo> animationData = entity.getServerAnimationInfos();
        if (animationData.containsKey(animationName)) {
            return animationData.get(animationName).blocksPerSecond;
        }
        return 0;
    }

    public PlayState waterPredicate(AnimationEvent<PrehistoricSwimming> event) {
        if (isBlocked()) return PlayState.STOP;
        AnimationController<PrehistoricSwimming> controller = event.getController();
        if (tryNextAnimation(controller)) {
            return PlayState.CONTINUE;
        }
        Optional<ActiveAnimationInfo> activeAnimation = getActiveAnimation(controller.getName());
        ILoopType loopType = null;

        if (activeAnimation.isPresent() && activeAnimation.get().forced && !isAnimationDone(controller.getName())) {
            loopType = activeAnimation.get().loop ? LOOP : PLAY_ONCE;
            //event.getController().setAnimationSpeed(activeAnimation.get().speed);
        } else {
            if (event.getAnimatable().isBeached()) {
                addActiveAnimation(controller.getName(), AnimationCategory.BEACHED);
            } else if (entity.isSleeping()) {
                addActiveAnimation(controller.getName(), AnimationCategory.SLEEP);
            } else if (event.isMoving()) {
                //TODO: AnimSpeed for amphibians on land
                if (entity.isInWater()) {
                    if (entity.isSprinting()) {
                        addActiveAnimation(controller.getName(), AnimationCategory.SWIM_FAST);
                    } else {
                        addActiveAnimation(controller.getName(), AnimationCategory.SWIM);
                    }
                } else {
                    if (entity.isSprinting()) {
                        addActiveAnimation(controller.getName(), AnimationCategory.SPRINT);
                    } else {
                        addActiveAnimation(controller.getName(), AnimationCategory.WALK);
                    }
                }
            } else if (event.getAnimatable().isWeak()) {
                addActiveAnimation(controller.getName(), AnimationCategory.KNOCKOUT);
            } else {
                addActiveAnimation(controller.getName(), AnimationCategory.IDLE);
            }
        }
        Optional<ActiveAnimationInfo> newAnimation = getActiveAnimation(controller.getName());
        if (newAnimation.isPresent()) {
            if (loopType == null) {
                loopType = newAnimation.get().loop ? LOOP : PLAY_ONCE;
            }
            controller.setAnimation(new AnimationBuilder().addAnimation(newAnimation.get().animationName, loopType));
        }
        return PlayState.CONTINUE;
    }


    /**
     * Sets the animation speed without changing the current point in the animation
     *
     * @param animationSpeed the new animation speed
     * @param animationTick  the current animation tick returned by event.getAnimationTick()
     */
    @ExpectPlatform
    public static void setAnimationSpeed(AnimationController<?> controller, double animationSpeed, double animationTick) {
        //Has to be done this way because our common mixins/accessors break on forge
        throw new NotImplementedException();
    }

    public boolean tryNextAnimation(AnimationController<?> controller) {
        if (!nextAnimations.containsKey(controller.getName())) {
            return false;
        }
        ActiveAnimationInfo next = nextAnimations.remove(controller.getName());
        activeAnimations.put(controller.getName(), next);

        controller.transitionLengthTicks = next.transitionLength;
        controller.markNeedsReload();
        controller.setAnimation(new AnimationBuilder().addAnimation(next.animationName, next.loop ? LOOP : null));
        return true;
    }

    public PlayState leapingPredicate(AnimationEvent<PrehistoricLeaping> event) {
        AnimationController<PrehistoricLeaping> controller = event.getController();
        if (tryNextAnimation(controller)) {
            return PlayState.CONTINUE;
        }
        Optional<ActiveAnimationInfo> activeAnimation = getActiveAnimation(controller.getName());
        ILoopType loopType = null;
        double animationSpeed = 1;
        if (activeAnimation.isPresent() && activeAnimation.get().forced && !isAnimationDone(controller.getName())) {
            loopType = activeAnimation.get().loop ? LOOP : PLAY_ONCE;
            if (activeAnimation.get().animationName.contains("land")) {
                animationSpeed = activeAnimation.get().speed;
            }
        } else {
            if (event.getAnimatable().isLeapStart()) {
                controller.setAnimationSpeed(1);
                controller.setAnimation(new AnimationBuilder().playOnce(event.getAnimatable().getLeapStartAnimationName()).loop(entity.getAnimation(AnimationCategory.FALL).animation.animationName));
                return PlayState.CONTINUE;
            } else if (event.getAnimatable().isAttackRiding()) {
                controller.setAnimationSpeed(1);
                controller.setAnimation(new AnimationBuilder().loop(event.getAnimatable().getLeapAttackAnimation().animation.animationName));
                return PlayState.CONTINUE;
            } else if (entity.isSleeping()) {
                addActiveAnimation(controller.getName(), AnimationCategory.SLEEP);
            } else if (event.getAnimatable().sitSystem.isSitting()) {
                addActiveAnimation(controller.getName(), AnimationCategory.SIT);
            } else if (event.getAnimatable().isClimbing()) {
                addActiveAnimation(controller.getName(), AnimationCategory.CLIMB);
            } else if (event.isMoving()) {
                if (entity.isInWater()) {
                    addActiveAnimation(controller.getName(), AnimationCategory.SWIM);
                } else {
                    Animation walkAnim = entity.nextWalkingAnimation().animation;
                    Animation sprintAnim = entity.nextSprintingAnimation().animation;
                    //All animations were done at a scale of 1 -> Slow down animation if scale is bigger than 1
                    double scaleMult = 1 / event.getAnimatable().getScale();
                    //the deltaMovement of the animation should match the mobs deltaMovement
                    double mobSpeed = entity.getDeltaMovement().horizontalDistance() * 20;
                    //Limit mobSpeed to the mobs maximum natural movement speed (23.55 * maxSpeed^2)
                    mobSpeed = Math.min(Util.attributeToSpeed(attributeSpeed), mobSpeed);
                    //All animations were done for a specific movespeed -> Slow down animation if mobSpeed is slower than that speed
                    double animationTargetSpeed = getAnimationTargetSpeed(event.getAnimatable(), walkAnim.animationName);
                    if (animationTargetSpeed > 0) {
                        animationSpeed = scaleMult * mobSpeed / animationTargetSpeed;
                    }
                    if (animationSpeed > 2.75 || entity.isSprinting()) {
                        //Choose sprint
                        animationTargetSpeed = getAnimationTargetSpeed(event.getAnimatable(), sprintAnim.animationName);
                        if (animationTargetSpeed > 0) {
                            animationSpeed = scaleMult * mobSpeed / animationTargetSpeed;
                        }
                        addActiveAnimation(controller.getName(), sprintAnim, AnimationCategory.SPRINT, false);
                    } else {
                        addActiveAnimation(controller.getName(), walkAnim, AnimationCategory.WALK, false);
                    }
                }
            } else {
                if (entity.isInWater()) {
                    addActiveAnimation(controller.getName(), AnimationCategory.SWIM);
                } else {
                    addActiveAnimation(controller.getName(), AnimationCategory.IDLE);
                }
            }
        }
        setAnimationSpeed(controller, animationSpeed, event.getAnimationTick());
        Optional<ActiveAnimationInfo> newAnimation = getActiveAnimation(controller.getName());
        if (newAnimation.isPresent()) {
            controller.transitionLengthTicks = newAnimation.get().transitionLength;
            if (loopType == null) {
                loopType = newAnimation.get().loop ? LOOP : PLAY_ONCE;
            }
            controller.setAnimation(new AnimationBuilder().addAnimation(newAnimation.get().animationName, loopType));
        }
        return PlayState.CONTINUE;
    }

    public PlayState landPredicate(AnimationEvent<Prehistoric> event) {
        if (isBlocked()) return PlayState.STOP;
        AnimationController<Prehistoric> controller = event.getController();
        if (tryNextAnimation(controller)) {
            return PlayState.CONTINUE;
        }
        Optional<ActiveAnimationInfo> activeAnimation = getActiveAnimation(controller.getName());
        ILoopType loopType = null;
        double animationSpeed = 1;
        if (activeAnimation.isPresent() && activeAnimation.get().forced && !isAnimationDone(controller.getName())) {
            loopType = activeAnimation.get().loop ? LOOP : PLAY_ONCE;
        } else {
            if (event.getAnimatable().isWeak()) {
                addActiveAnimation(controller.getName(), AnimationCategory.KNOCKOUT);
            } else if (entity.isSleeping()) {
                addActiveAnimation(controller.getName(), AnimationCategory.SLEEP);
            } else if (event.getAnimatable().sitSystem.isSitting()) {
                addActiveAnimation(controller.getName(), AnimationCategory.SIT);
            } else if (entity.isInWater()) {
                addActiveAnimation(controller.getName(), AnimationCategory.SWIM, true);
            } else if (event.isMoving()) {
                Animation walkAnim = entity.nextWalkingAnimation().animation;
                Animation sprintAnim = entity.nextSprintingAnimation().animation;
                //All animations were done at a scale of 1 -> Slow down animation if scale is bigger than 1
                double scaleMult = 1 / event.getAnimatable().getScale();
                //the deltaMovement of the animation should match the mobs deltaMovement
                double mobSpeed = entity.getDeltaMovement().horizontalDistance() * 20;
                //Limit mobSpeed to the mobs maximum natural movement speed (23.55 * maxSpeed^2)
                mobSpeed = Math.min(Util.attributeToSpeed(attributeSpeed), mobSpeed);
                //All animations were done for a specific movespeed -> Slow down animation if mobSpeed is slower than that speed
                double animationTargetSpeed = getAnimationTargetSpeed(event.getAnimatable(), walkAnim.animationName);
                if (animationTargetSpeed > 0) {
                    animationSpeed = scaleMult * mobSpeed / animationTargetSpeed;
                }
                //TODO: entity.isSprinting needs rework
                if (animationSpeed > 2.75 || entity.isSprinting()) {
                    //Choose sprint
                    animationTargetSpeed = getAnimationTargetSpeed(event.getAnimatable(), sprintAnim.animationName);
                    if (animationTargetSpeed > 0) {
                        animationSpeed = scaleMult * mobSpeed / animationTargetSpeed;
                    }
                    addActiveAnimation(controller.getName(), sprintAnim, AnimationCategory.SPRINT, false);
                } else {
                    addActiveAnimation(controller.getName(), walkAnim, AnimationCategory.WALK, false);
                }
            } else {
                addActiveAnimation(controller.getName(), AnimationCategory.IDLE);
            }
        }
        setAnimationSpeed(controller, animationSpeed, event.getAnimationTick());
        Optional<ActiveAnimationInfo> newAnimation = getActiveAnimation(controller.getName());
        if (newAnimation.isPresent()) {
            controller.transitionLengthTicks = newAnimation.get().transitionLength;
            if (loopType == null) {
                loopType = newAnimation.get().loop ? LOOP : PLAY_ONCE;
            }
            controller.setAnimation(new AnimationBuilder().addAnimation(newAnimation.get().animationName, loopType));
        }
        return PlayState.CONTINUE;
    }

    public PlayState attackPredicate(AnimationEvent<Prehistoric> event) {
        AnimationController<Prehistoric> controller = event.getController();
        if (tryNextAnimation(controller)) {
            return PlayState.CONTINUE;
        }
        if (!isAnimationDone(controller.getName())) {
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    public PlayState grabAttackPredicate(AnimationEvent<PrehistoricSwimming> event) {
        AnimationController<PrehistoricSwimming> controller = event.getController();
        if (tryNextAnimation(controller)) {
            return PlayState.CONTINUE;
        }
        if (event.getAnimatable().isDoingGrabAttack()) {
            addActiveAnimation(controller.getName(), event.getAnimatable().nextGrabbingAnimation().animation, AnimationCategory.ATTACK, false);
        } else if (isAnimationDone(controller.getName())) {
            activeAnimations.remove(controller.getName());
        }
        Optional<ActiveAnimationInfo> newAnimation = getActiveAnimation(controller.getName());
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
        if (!entity.isInWater()) {
            addActiveAnimation(controller.getName(), AnimationCategory.BEACHED);
        } else if (event.isMoving()) {
            addActiveAnimation(controller.getName(), AnimationCategory.SWIM);
        } else {
            addActiveAnimation(controller.getName(), AnimationCategory.IDLE);
        }
        Optional<ActiveAnimationInfo> newAnimation = getActiveAnimation(controller.getName());
        newAnimation.ifPresent(newInfo -> controller.setAnimation(new AnimationBuilder().addAnimation(newInfo.animationName(), newInfo.loop ? LOOP : PLAY_ONCE)));
        return PlayState.CONTINUE;
    }

    public PlayState flyingPredicate(AnimationEvent<PrehistoricFlying> event) {
        AnimationController<PrehistoricFlying> controller = event.getController();
        if (tryNextAnimation(controller)) {
            return PlayState.CONTINUE;
        }

        Optional<ActiveAnimationInfo> activeAnimation = getActiveAnimation(controller.getName());
        if (activeAnimation.isPresent() && activeAnimation.get().forced() && !isAnimationDone(controller.getName())) {
            controller.setAnimation(new AnimationBuilder().addAnimation(activeAnimation.get().animationName(), activeAnimation.get().loop ? LOOP : PLAY_ONCE));
            return PlayState.CONTINUE;
        }
        double animSpeed = 1;
        if (!event.getAnimatable().isTakingOff()) {
            if (entity.isSleeping()) {
                addActiveAnimation(controller.getName(), AnimationCategory.SLEEP);
            } else if (event.getAnimatable().sitSystem.isSitting()) {
                addActiveAnimation(controller.getName(), AnimationCategory.SIT);
            } else if (entity.isInWater()) {
                addActiveAnimation(controller.getName(), AnimationCategory.SWIM, true);
            } else if (event.isMoving()) {
                if (event.getAnimatable().isFlying()) {
                    if (entity.isSprinting()) {
                        addActiveAnimation(controller.getName(), AnimationCategory.FLY_FAST);
                    } else {
                        addActiveAnimation(controller.getName(), AnimationCategory.FLY);
                    }
                } else {
                    Animation animation = entity.nextWalkingAnimation().animation;
                    addActiveAnimation(controller.getName(), animation, AnimationCategory.WALK, false);
                    //All animations were done at a scale of 1 -> Slow down animation if scale is bigger than 1
                    double scaleMult = 1 / event.getAnimatable().getScale();
                    //the deltaMovement of the animation should match the mobs deltaMovement
                    double mobSpeed = entity.getDeltaMovement().horizontalDistance() * 20;
                    //Limit mobSpeed to the mobs maximum natural movement speed (23.55 * maxSpeed^2)
                    //TODO: Flying mob might need different limit
                    mobSpeed = Math.min(Util.attributeToSpeed(attributeSpeed), mobSpeed);
                    //All animations were done for a specific movespeed -> Slow down animation if mobSpeed is slower than that speed
                    double animationTargetSpeed = getAnimationTargetSpeed(event.getAnimatable(), animation.animationName);
                    if (animationTargetSpeed > 0) {
                        animSpeed = scaleMult * mobSpeed / animationTargetSpeed;
                    }
                }
            } else {
                addActiveAnimation(controller.getName(), AnimationCategory.IDLE);
            }
        }
        setAnimationSpeed(controller, animSpeed, event.getAnimationTick());
        Optional<ActiveAnimationInfo> newAnimation = getActiveAnimation(controller.getName());
        newAnimation.ifPresent(newInfo -> controller.setAnimation(new AnimationBuilder().addAnimation(newInfo.animationName(), newInfo.loop ? LOOP : PLAY_ONCE)));
        return PlayState.CONTINUE;
    }

    public void setAttributeSpeed(double attributeSpeed) {
        this.attributeSpeed = attributeSpeed;
    }

    public record ActiveAnimationInfo(String animationName, double endTick, AnimationCategory category,
                                      boolean forced, double transitionLength, boolean loop, boolean keepActive) {
        public ActiveAnimationInfo(String animationName, double endTick, AnimationCategory category, boolean forced, double speed, boolean loop) {
            this(animationName, endTick, category, forced, speed, loop, false);
        }

        public ActiveAnimationInfo(String animationName, double endTick, AnimationCategory category, boolean forced, double speed) {
            this(animationName, endTick, category, forced, speed, false, false);
        }
    }

}
