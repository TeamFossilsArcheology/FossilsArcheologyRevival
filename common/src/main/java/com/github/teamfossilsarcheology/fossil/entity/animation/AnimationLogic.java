package com.github.teamfossilsarcheology.fossil.entity.animation;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.*;
import com.github.teamfossilsarcheology.fossil.entity.util.Util;
import com.github.teamfossilsarcheology.fossil.network.MessageHandler;
import com.github.teamfossilsarcheology.fossil.network.S2CSyncActiveAnimationMessage;
import com.github.teamfossilsarcheology.fossil.network.debug.S2CCancelAnimationMessage;
import it.unimi.dsi.fastutil.objects.Object2FloatOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.targeting.TargetingConditions;
import software.bernie.geckolib.core.animation.Animation;
import software.bernie.geckolib.core.animation.AnimationController;
import software.bernie.geckolib.core.animation.AnimationState;
import software.bernie.geckolib.core.animation.RawAnimation;
import software.bernie.geckolib.core.object.PlayState;

import java.util.Map;
import java.util.Optional;
import java.util.function.BooleanSupplier;

import static software.bernie.geckolib.core.animation.Animation.LoopType.DEFAULT;
import static software.bernie.geckolib.core.animation.Animation.LoopType.LOOP;

public class AnimationLogic<T extends Mob & PrehistoricAnimatable<T>> {
    //TODO: Save RawAnimations
    public static final String IDLE_CTRL = "Movement/Idle";
    public static final String EAT_CTRL = "Eat";
    public static final String ATTACK_CTRL = "Attack";
    private final Map<String, ActiveAnimationInfo> activeAnimations = new Object2ObjectOpenHashMap<>();
    /**
     * If the supplier returns true for an active animation with keepActive == true it will still be replaced
     */
    private final Map<ActiveAnimationInfo, BooleanSupplier> additionalLogic = new Object2ObjectOpenHashMap<>();
    /**
     * Any animation in here will replace the active animation on the next tick
     */
    private final Map<String, ActiveAnimationInfo> nextAnimations = new Object2ObjectOpenHashMap<>();
    private final Map<String, Float> prevAnimationSpeeds = new Object2FloatOpenHashMap<>();
    protected final T entity;

    public AnimationLogic(T entity) {
        this.entity = entity;
    }

    /**
     * Returns the active animation for the given controller if one is active
     *
     * @param controller the name of the controller to check
     */
    public Optional<ActiveAnimationInfo> getActiveAnimation(String controller) {
        return Optional.ofNullable(activeAnimations.get(controller));
    }

    private ActiveAnimationInfo putActiveAnimation(String controller, ActiveAnimationInfo info) {
        additionalLogic.remove(activeAnimations.put(controller, info));
        return info;
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
        if (animationInfo != null && !entity.level().isClientSide) {
            ActiveAnimationInfo activeAnimationInfo = new Builder(animationInfo.animation, entity.level().getGameTime(), category).forced().transitionLength(5).loop(false).build();
            TargetingConditions conditions = TargetingConditions.forNonCombat().ignoreLineOfSight().range(30);
            var players = ((ServerLevel) entity.level()).getPlayers(serverPlayer -> conditions.test(serverPlayer, entity));
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
    public ActiveAnimationInfo forceAnimation(String controller, AnimationInfo animationInfo, AnimationCategory category, double speed, int transitionLength, boolean loop) {
        if (animationInfo != null) {
            ActiveAnimationInfo activeAnimationInfo = new Builder(animationInfo.animation, entity.level().getGameTime(), category).forced().transitionLength(transitionLength).speed(speed).loop(loop).build();
            addNextAnimation(controller, activeAnimationInfo);
            if (!entity.level().isClientSide) {
                TargetingConditions conditions = TargetingConditions.forNonCombat().ignoreLineOfSight().range(30);
                var players = ((ServerLevel) entity.level()).getPlayers(serverPlayer -> conditions.test(serverPlayer, entity));
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
     * @return the animation info if it was successfully added or {@code null}
     */
    public ActiveAnimationInfo addActiveAnimation(String controller, AnimationCategory category) {
        return addActiveAnimation(controller, entity.getAnimation(category).animation, category, false);
    }

    /**
     * Tries to add a new active animation
     *
     * @param controller the name of the controller the animation will play on
     * @param category   the category of the animation
     * @param keepActive whether the animation can only be replaced once it's done
     * @return the animation info if it was successfully added or {@code null}
     */
    public ActiveAnimationInfo addActiveAnimation(String controller, AnimationCategory category, boolean keepActive) {
        return addActiveAnimation(controller, entity.getAnimation(category).animation, category, keepActive);
    }

    /**
     * Tries to add a new active animation
     *
     * @param controller the name of the controller the animation will play on
     * @param animation  the animation to play
     * @param category   the category of the animation
     * @param keepActive whether the animation can only be replaced once it's done
     * @return the animation info if it was successfully added or {@code null}
     */
    public ActiveAnimationInfo addActiveAnimation(String controller, Animation animation, AnimationCategory category, boolean keepActive) {
        //TODO: Skip if same animation
        if (animation == null) {
            return null;
        }
        ActiveAnimationInfo active = getActiveAnimation(controller).orElse(null);
        if (active == null) {
            return putActiveAnimation(controller, new Builder(animation, entity.level().getGameTime(), category).keepActive(keepActive).build());
        }
        boolean replaceAnim = false;
        if (active.category == category && isAnimationDone(active)) {
            //Loops in the same category can only replace sometimes
            replaceAnim = !active.loop || entity.getRandom().nextFloat() < category.chance();
        } else if (active.category != category) {
            //Can only replace if loop or previous animation done
            replaceAnim = active.loop && (!active.keepActive || additionalLogic.getOrDefault(active, () -> false).getAsBoolean()) || isAnimationDone(active);
        }
        if (replaceAnim) {
            int transitionLength = Math.max(category.transitionLength(), active.category.transitionLength());
            return putActiveAnimation(controller, new Builder(animation, entity.level().getGameTime(), category).transitionLength(transitionLength).keepActive(keepActive).build());
        }
        return null;
    }

    /**
     * The given animation will most likely be played on the next tick
     */
    public void addNextAnimation(String controller, ActiveAnimationInfo activeAnimationInfo) {
        nextAnimations.put(controller, activeAnimationInfo);
    }

    public void cancelAnimation(String controller) {
        if (entity.level().isClientSide) {
            getActiveAnimation(controller).ifPresent(additionalLogic::remove);
            activeAnimations.remove(controller);
        } else {
            TargetingConditions conditions = TargetingConditions.forNonCombat().ignoreLineOfSight().range(30);
            var players = ((ServerLevel) entity.level()).getPlayers(serverPlayer -> conditions.test(serverPlayer, entity));
            MessageHandler.DEBUG_CHANNEL.sendToPlayers(players, new S2CCancelAnimationMessage(entity.getId(), controller));
        }
    }

    public boolean isAnimationDone(String controller) {
        Optional<ActiveAnimationInfo> opt = getActiveAnimation(controller);
        return opt.isEmpty() || isAnimationDone(opt.get());
    }

    public boolean isAnimationDone(ActiveAnimationInfo activeAnimation) {
        return entity.level().getGameTime() >= activeAnimation.endTick;
    }

    protected boolean isBlocked() {
        if (entity instanceof PrehistoricSwimming swimming && swimming.isDoingGrabAttack()) {
            return true;
        }
        //TODO: Heavy attacks need priority. Think of something better in the future
        return entity instanceof Prehistoric prehistoric && entity.level().getGameTime() < prehistoric.getEntityHitboxData().getAttackBoxData().attackBoxEndTime();
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

    public PlayState waterPredicate(AnimationState<PrehistoricSwimming> state) {
        if (isBlocked()) return PlayState.STOP;
        AnimationController<PrehistoricSwimming> controller = state.getController();
        if (tryNextAnimation(state, controller)) {
            return PlayState.CONTINUE;
        }
        Optional<ActiveAnimationInfo> activeAnimation = getActiveAnimation(controller.getName());
        if (activeAnimation.isPresent() && tryForcedAnimation(state, activeAnimation.get())) {
            return PlayState.CONTINUE;
        }
        double animationSpeed = 1;
        if (state.getAnimatable().isBeached()) {
            addActiveAnimation(controller.getName(), AnimationCategory.BEACHED);
        } else if (entity.isSleeping()) {
            addActiveAnimation(controller.getName(), AnimationCategory.SLEEP);
        } else if (state.isMoving()) {
            //TODO: AnimSpeed for amphibians on land
            if (entity.isInWater()) {
                if (entity.isSprinting()) {
                    addActiveAnimation(controller.getName(), AnimationCategory.SWIM_FAST);
                } else {
                    addActiveAnimation(controller.getName(), AnimationCategory.SWIM);
                }
            } else {
                animationSpeed = addMovementAnimation(state, true);
            }
        } else if (state.getAnimatable().isWeak()) {
            addActiveAnimation(controller.getName(), AnimationCategory.KNOCKOUT);
        } else {
            addActiveAnimation(controller.getName(), AnimationCategory.IDLE);
        }
        prevAnimationSpeeds.put(controller.getName(), (float) animationSpeed);
        setAnimationSpeed(controller, animationSpeed, state.getAnimationTick());
        Optional<ActiveAnimationInfo> newAnimation = getActiveAnimation(controller.getName());
        if (newAnimation.isPresent()) {
            state.setAnimation(RawAnimation.begin().then(newAnimation.get().animationName, newAnimation.get().loop ? LOOP : DEFAULT));
        }
        return PlayState.CONTINUE;
    }


    /**
     * @see PausableAnimationController#setAnimationSpeed(double, double)
     */
    public static void setAnimationSpeed(AnimationController<?> controller, double animationSpeed, double animationTick) {
        if (controller instanceof PausableAnimationController<?> pausableAnimationController) {
            pausableAnimationController.setAnimationSpeed(animationSpeed, animationTick);
        }
    }

    public boolean tryNextAnimation(AnimationState<?> state, AnimationController<?> controller) {
        if (!nextAnimations.containsKey(controller.getName())) {
            return false;
        }
        ActiveAnimationInfo next = nextAnimations.remove(controller.getName());
        putActiveAnimation(controller.getName(), next);

        controller.transitionLength(next.transitionLength);
        controller.forceAnimationReset();
        state.setAnimation(RawAnimation.begin().then(next.animationName, next.loop ? LOOP : DEFAULT));
        return true;
    }

    public boolean tryForcedAnimation(AnimationState<?> state, ActiveAnimationInfo activeAnimation) {
        if (activeAnimation.forced && (activeAnimation.loop || !isAnimationDone(activeAnimation))) {
            AnimationController<?> controller = state.getController();
            setAnimationSpeed(controller, activeAnimation.speed, state.getAnimationTick());
            controller.transitionLength(activeAnimation.transitionLength);
            state.setAnimation(RawAnimation.begin().then(activeAnimation.animationName, activeAnimation.loop ? LOOP : DEFAULT));
            return true;
        }
        return false;
    }

    public double addMovementAnimation(AnimationState<? extends Prehistoric> state, boolean canSprint) {
        AnimationController<? extends Prehistoric> controller = state.getController();
        ClientAnimationInfo walkAnim = (ClientAnimationInfo) entity.nextWalkingAnimation();
        //All animations were done at a scale of 1 -> Slow down animation if scale is bigger than 1
        double scaleMult = 1 / state.getAnimatable().getScale();
        double animationSpeed = scaleMult;
        //the deltaMovement of the animation should match the mobs deltaMovement
        double f = entity.onGround() ? entity.level().getBlockState(entity.blockPosition().below()).getBlock().getFriction() * 0.91F : 0.91F;
        double mobSpeed = entity.getDeltaMovement().horizontalDistance() / f * 20;
        //Limit mobSpeed to the mobs maximum natural movement speed
        mobSpeed = Math.min(Util.attributeToSpeed(entity.getAttributeValue(Attributes.MOVEMENT_SPEED), state.getAnimatable().attributes().sprintMod(), entity.isSprinting()), mobSpeed);
        //All animations were done for a specific movespeed -> Slow down animation if mobSpeed is slower than that speed
        if (walkAnim.blocksPerSecond > 0) {
            animationSpeed *= mobSpeed / walkAnim.blocksPerSecond;
        }
        if (animationSpeed <= 0.1) {
            //The transition to idle works better than slowing down the animation
            addActiveAnimation(controller.getName(), AnimationCategory.IDLE);
        } else {
            if (animationSpeed < prevAnimationSpeeds.getOrDefault(controller.getName(), 0f) - Mth.EPSILON) {
                //Add some inertia to prevent sudden animation stops
                animationSpeed = Mth.approach(prevAnimationSpeeds.get(controller.getName()), (float) animationSpeed, 0.05f);
            }
            if (canSprint && (animationSpeed > 2.75 || entity.isSprinting())) {
                //Choose sprint
                ClientAnimationInfo sprintAnim = (ClientAnimationInfo) entity.nextSprintingAnimation();
                animationSpeed = scaleMult;
                if (sprintAnim.blocksPerSecond > 0) {
                    animationSpeed *= mobSpeed / sprintAnim.blocksPerSecond;
                }
                addActiveAnimation(controller.getName(), sprintAnim.animation, AnimationCategory.SPRINT, false);
            } else {
                addActiveAnimation(controller.getName(), walkAnim.animation, AnimationCategory.WALK, false);
            }
        }
        return animationSpeed;
    }

    public PlayState leapingPredicate(AnimationState<PrehistoricLeaping> state) {
        AnimationController<PrehistoricLeaping> controller = state.getController();
        if (tryNextAnimation(state, controller)) {
            return PlayState.CONTINUE;
        }
        Optional<ActiveAnimationInfo> activeAnimation = getActiveAnimation(controller.getName());
        if (activeAnimation.isPresent() && tryForcedAnimation(state, activeAnimation.get())) {
            return PlayState.CONTINUE;
        }
        double animationSpeed = 1;
        PrehistoricLeaping entity = state.getAnimatable();

        if (entity.getLeapSystem().isAttackRiding()) {
            setAnimationSpeed(controller, 1, state.getAnimationTick());
            controller.transitionLength(10);
            state.setAnimation(RawAnimation.begin().thenLoop(entity.getLeapAttackAnimationName()));
            return PlayState.CONTINUE;
        } else if (entity.getLeapSystem().hasLeapStarted() || entity.getLeapSystem().isLeapFlying()) {
            setAnimationSpeed(controller, 1, state.getAnimationTick());
            if (controller.getCurrentAnimation() != null && entity.getAnimations().get(AnimationCategory.FALL).hasAnimation(controller.getCurrentAnimation().animation().name()) && entity.onGround()) {
                controller.transitionLength(0);
                state.setAnimation(RawAnimation.begin().thenPlay(entity.getLandAnimationName()));
            } else {
                state.setAnimation(RawAnimation.begin().thenPlay(entity.getLeapStartAnimationName()).thenLoop(entity.getAnimation(AnimationCategory.FALL).animation.name()));
            }
            return PlayState.CONTINUE;
        } else if (entity.getLeapSystem().isLanding()) {
            setAnimationSpeed(controller, 1, state.getAnimationTick());
            controller.transitionLength(0);
            state.setAnimation(RawAnimation.begin().thenPlay(entity.getLandAnimationName()));
            return PlayState.CONTINUE;
        } else if (entity.isSleeping()) {
            addActiveAnimation(controller.getName(), AnimationCategory.SLEEP);
        } else if (entity.sitSystem.isSitting()) {
            addActiveAnimation(controller.getName(), AnimationCategory.SIT);
        } else if (entity.isClimbing()) {
            addActiveAnimation(controller.getName(), AnimationCategory.CLIMB);
            //TODO: Quick fix. Think of something better leater
            animationSpeed = 2;
        } else if (state.isMoving()) {
            if (entity.isInWater()) {
                ActiveAnimationInfo info = addActiveAnimation(controller.getName(), AnimationCategory.SWIM, true);
                if (info != null) {
                    additionalLogic.put(info, entity::onGround);
                }
            } else {
                animationSpeed = addMovementAnimation(state, true);
            }
        } else {
            if (entity.isInWater()) {
                addActiveAnimation(controller.getName(), AnimationCategory.SWIM);
            } else {
                addActiveAnimation(controller.getName(), AnimationCategory.IDLE);
            }
        }
        setAnimationSpeed(controller, animationSpeed, state.getAnimationTick());
        Optional<ActiveAnimationInfo> newAnimation = getActiveAnimation(controller.getName());
        if (newAnimation.isPresent()) {
            controller.transitionLength(newAnimation.get().transitionLength);
            state.setAnimation(RawAnimation.begin().then(newAnimation.get().animationName, newAnimation.get().loop ? LOOP : DEFAULT));
        }
        return PlayState.CONTINUE;
    }

    public PlayState landPredicate(AnimationState<Prehistoric> state) {
        if (isBlocked()) return PlayState.STOP;
        AnimationController<Prehistoric> controller = state.getController();
        if (tryNextAnimation(state, controller)) {
            return PlayState.CONTINUE;
        }
        Optional<ActiveAnimationInfo> activeAnimation = getActiveAnimation(controller.getName());
        if (activeAnimation.isPresent() && tryForcedAnimation(state, activeAnimation.get())) {
            return PlayState.CONTINUE;
        }
        double animationSpeed = 1;
        if (state.getAnimatable().isWeak()) {
            addActiveAnimation(controller.getName(), AnimationCategory.KNOCKOUT);
        } else if (entity.isSleeping()) {
            addActiveAnimation(controller.getName(), AnimationCategory.SLEEP);
        } else if (state.getAnimatable().sitSystem.isSitting()) {
            addActiveAnimation(controller.getName(), AnimationCategory.SIT);
        } else if (entity.isInWater()) {
            ActiveAnimationInfo info = addActiveAnimation(controller.getName(), AnimationCategory.SWIM, true);
            if (info != null) {
                additionalLogic.put(info, entity::onGround);
            }
        } else if (state.isMoving()) {
            animationSpeed = addMovementAnimation(state, true);
        } else {
            addActiveAnimation(controller.getName(), AnimationCategory.IDLE);
        }
        prevAnimationSpeeds.put(controller.getName(), (float) animationSpeed);
        setAnimationSpeed(controller, animationSpeed, state.getAnimationTick());
        Optional<ActiveAnimationInfo> newAnimation = getActiveAnimation(controller.getName());
        if (newAnimation.isPresent()) {
            controller.transitionLength(newAnimation.get().transitionLength);
            state.setAnimation(RawAnimation.begin().then(newAnimation.get().animationName, newAnimation.get().loop ? LOOP : DEFAULT));
        }
        return PlayState.CONTINUE;
    }

    public PlayState attackPredicate(AnimationState<Prehistoric> state) {
        AnimationController<Prehistoric> controller = state.getController();
        if (tryNextAnimation(state, controller)) {
            return PlayState.CONTINUE;
        }
        if (!isAnimationDone(controller.getName())) {
            return PlayState.CONTINUE;
        }
        return PlayState.STOP;
    }

    public PlayState grabAttackPredicate(AnimationState<PrehistoricSwimming> state) {
        AnimationController<PrehistoricSwimming> controller = state.getController();
        if (tryNextAnimation(state, controller)) {
            return PlayState.CONTINUE;
        }
        if (state.getAnimatable().isDoingGrabAttack()) {
            addActiveAnimation(controller.getName(), state.getAnimatable().nextGrabbingAnimation().animation, AnimationCategory.ATTACK, false);
        } else if (isAnimationDone(controller.getName())) {
            activeAnimations.remove(controller.getName());
        }
        Optional<ActiveAnimationInfo> newAnimation = getActiveAnimation(controller.getName());
        if (newAnimation.isPresent()) {
            state.setAnimation(RawAnimation.begin().thenPlay(newAnimation.get().animationName()));
            return PlayState.CONTINUE;
        } else {
            state.getController().forceAnimationReset();
            return PlayState.STOP;
        }
    }

    public PlayState fishPredicate(AnimationState<PrehistoricFish> state) {
        AnimationController<PrehistoricFish> controller = state.getController();
        if (!entity.isInWater()) {
            addActiveAnimation(controller.getName(), AnimationCategory.BEACHED);
        } else if (state.isMoving()) {
            addActiveAnimation(controller.getName(), AnimationCategory.SWIM);
        } else {
            addActiveAnimation(controller.getName(), AnimationCategory.IDLE);
        }
        Optional<ActiveAnimationInfo> newAnimation = getActiveAnimation(controller.getName());
        newAnimation.ifPresent(newInfo -> state.setAnimation(RawAnimation.begin().then(newInfo.animationName(), newInfo.loop ? LOOP : DEFAULT)));
        return PlayState.CONTINUE;
    }

    public PlayState flyingPredicate(AnimationState<PrehistoricFlying> state) {
        AnimationController<PrehistoricFlying> controller = state.getController();
        if (tryNextAnimation(state, controller)) {
            return PlayState.CONTINUE;
        }

        Optional<ActiveAnimationInfo> activeAnimation = getActiveAnimation(controller.getName());
        if (activeAnimation.isPresent() && tryForcedAnimation(state, activeAnimation.get())) {
            return PlayState.CONTINUE;
        }
        controller.transitionLength(5);
        double animationSpeed = 1;
        if (!state.getAnimatable().isTakingOff()) {
            if (state.getAnimatable().isFlying()) {
                if (entity.isSprinting()) {
                    addActiveAnimation(controller.getName(), AnimationCategory.FLY_FAST);
                } else {
                    addActiveAnimation(controller.getName(), AnimationCategory.FLY);
                }
            } else if (entity.isSleeping()) {
                addActiveAnimation(controller.getName(), AnimationCategory.SLEEP);
            } else if (state.getAnimatable().sitSystem.isSitting()) {
                addActiveAnimation(controller.getName(), AnimationCategory.SIT);
            } else if (state.getAnimatable().isClimbing()) {
                addActiveAnimation(controller.getName(), AnimationCategory.CLIMB);
            } else if (entity.isInWater()) {
                ActiveAnimationInfo info = addActiveAnimation(controller.getName(), AnimationCategory.SWIM, true);
                if (info != null) {
                    additionalLogic.put(info, entity::onGround);
                }
            } else if (!entity.onGround()) {
                addActiveAnimation(controller.getName(), AnimationCategory.FLY);
                controller.transitionLength(10);
                animationSpeed = 0.5;
            } else if (state.isMoving()) {
                //TODO: Flying mob might need different limit
                animationSpeed = addMovementAnimation(state, false);
            } else {
                addActiveAnimation(controller.getName(), AnimationCategory.IDLE);
            }
        }
        setAnimationSpeed(controller, animationSpeed, state.getAnimationTick());
        Optional<ActiveAnimationInfo> newAnimation = getActiveAnimation(controller.getName());
        newAnimation.ifPresent(newInfo -> state.setAnimation(RawAnimation.begin().then(newInfo.animationName(), newInfo.loop ? LOOP : DEFAULT)));
        return PlayState.CONTINUE;
    }

    public record ActiveAnimationInfo(String animationName, double endTick, AnimationCategory category,
                                      boolean forced, int transitionLength, double speed, boolean loop,
                                      boolean keepActive) {
    }

    public static class Builder {
        private final String animationName;
        private final double endTick;
        private final AnimationCategory category;
        private boolean forced;
        private int transitionLength;
        private double speed = 1;
        private boolean loop;
        private boolean keepActive;

        public Builder(Animation animation, long currentTime, AnimationCategory category) {
            this.animationName = animation.name();
            this.endTick = currentTime + animation.length();
            this.category = category;
            this.transitionLength = category.transitionLength();
            this.loop = animation.loopType() == LOOP;
        }

        public Builder(String animationName, double endTick, AnimationCategory category) {
            this.animationName = animationName;
            this.endTick = endTick;
            this.category = category;
            transitionLength = category.transitionLength();
        }

        public Builder forced() {
            forced = true;
            return this;
        }

        public Builder transitionLength(int length) {
            transitionLength = length;
            return this;
        }

        public Builder speed(double speed) {
            this.speed = speed;
            return this;
        }

        public Builder loop(boolean loop) {
            this.loop = loop;
            return this;
        }

        public Builder loop() {
            loop = true;
            return this;
        }

        public Builder keepActive(boolean keepActive) {
            this.keepActive = keepActive;
            return this;
        }

        public ActiveAnimationInfo build() {
            return new ActiveAnimationInfo(animationName, endTick, category, forced, transitionLength, speed, loop, keepActive);
        }
    }

}
