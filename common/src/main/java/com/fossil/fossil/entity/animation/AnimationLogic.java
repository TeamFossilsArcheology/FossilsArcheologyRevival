package com.fossil.fossil.entity.animation;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricAnimatable;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricFish;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricSwimming;
import com.fossil.fossil.network.MessageHandler;
import com.fossil.fossil.network.SyncActiveAnimationMessage;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.world.entity.Mob;
import org.jetbrains.annotations.Nullable;
import software.bernie.geckolib3.core.AnimationState;
import software.bernie.geckolib3.core.PlayState;
import software.bernie.geckolib3.core.builder.Animation;
import software.bernie.geckolib3.core.builder.AnimationBuilder;
import software.bernie.geckolib3.core.builder.ILoopType;
import software.bernie.geckolib3.core.controller.AnimationController;
import software.bernie.geckolib3.core.event.predicate.AnimationEvent;

import static software.bernie.geckolib3.core.builder.ILoopType.EDefaultLoopTypes.PLAY_ONCE;

public class AnimationLogic<T extends Mob & PrehistoricAnimatable<T>> {
    private final CompoundTag activeAnimations = new CompoundTag();
    protected final T entity;

    public AnimationLogic(T entity) {
        this.entity = entity;
    }

    public @Nullable AnimationLogic.ActiveAnimationInfo getActiveAnimation(String controller) {
        CompoundTag animationTag = activeAnimations.getCompound(controller);
        if (animationTag.contains("Animation")) {
            return new AnimationLogic.ActiveAnimationInfo(animationTag.getString("Animation"), animationTag.getDouble("EndTick"), animationTag.getString("Category"));
        }
        return null;
    }

    public void addActiveAnimation(String controller, Animation animation, String category) {
        if (animation != null) {
            CompoundTag animationTag = new CompoundTag();
            animationTag.putString("Animation", animation.animationName);
            animationTag.putDouble("EndTick", entity.level.getGameTime() + animation.animationLength);
            animationTag.putString("Category", category);
            activeAnimations.put(controller, animationTag);
            MessageHandler.SYNC_CHANNEL.sendToServer(new SyncActiveAnimationMessage(entity.getId(), controller, animationTag));
        }
    }

    public void addActiveAnimation(String controller, CompoundTag animationTag) {
        activeAnimations.put(controller, animationTag);
    }

    public boolean isAnimationDone(String controller) {
        ActiveAnimationInfo activeAnimation = getActiveAnimation(controller);
        if (activeAnimation != null) {
            //TODO: Use isLoop? || event.getController().getAnimationState() == AnimationState.Stopped
            return activeAnimation.endTick <= entity.level.getGameTime();
        }
        return true;
    }

    public int getActionDelay(String controller) {
        ActiveAnimationInfo activeAnimation = getActiveAnimation(controller);
        if (activeAnimation != null && entity.getServerAnimationInfos().containsKey(activeAnimation.animationName())) {
            return entity.getServerAnimationInfos().get(activeAnimation.animationName()).actionDelay();
        }
        return -1;
    }

    public PlayState waterPredicate(AnimationEvent<PrehistoricSwimming> event) {
        AnimationController<PrehistoricSwimming> controller = event.getController();
        ActiveAnimationInfo activeAnimation = getActiveAnimation(controller.getName());
        ILoopType loopType = null;
        if (event.getAnimatable().isBeached()) {
            if (activeAnimation == null || !activeAnimation.category.equals("Beached")) {
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
            } else if (activeAnimation == null || isAnimationDone(controller.getName()) || activeAnimation.category.equals("Beached")) {
                addActiveAnimation(controller.getName(), entity.nextIdleAnimation(), "Idle");
            }
        }
        ActiveAnimationInfo newAnimation = getActiveAnimation(controller.getName());
        if (newAnimation != null) {
            controller.setAnimation(new AnimationBuilder().addAnimation(newAnimation.animationName, loopType));
        }
        return PlayState.CONTINUE;
    }

    public PlayState landPredicate(AnimationEvent<Prehistoric> event) {
        AnimationController<Prehistoric> controller = event.getController();
        ActiveAnimationInfo activeAnimation = getActiveAnimation(controller.getName());
        ILoopType loopType = null;
        if (event.isMoving()) {
            if (entity.isSprinting()) {
                addActiveAnimation(controller.getName(), entity.nextSprintingAnimation(), "Move");
            } else {
                addActiveAnimation(controller.getName(), entity.nextMovingAnimation(), "Move");
            }
        } else {
            if (entity.isSleeping()) {
                if (activeAnimation == null || !activeAnimation.category.equals("Sleep")) {
                    addActiveAnimation(controller.getName(), entity.nextSleepingAnimation(), "Sleep");
                }
            } else if (event.getAnimatable().isSitting()) {
                if (activeAnimation == null || !activeAnimation.category.equals("Sit")) {
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
            } else {
                //TODO: Need to be able to cancel animation but also not replace running one if from same category etc
                addActiveAnimation(controller.getName(), entity.nextIdleAnimation(), "Idle");
                loopType = PLAY_ONCE;
            }
        }
        ActiveAnimationInfo newAnimation = getActiveAnimation(controller.getName());
        if (newAnimation != null) {
            controller.setAnimation(new AnimationBuilder().addAnimation(newAnimation.animationName, loopType));
        }
        return PlayState.CONTINUE;
    }

    public PlayState attackPredicate(AnimationEvent<Prehistoric> event) {
        AnimationController<Prehistoric> controller = event.getController();
        if (entity.swinging) {
            if (isAnimationDone(controller.getName())) {
                addActiveAnimation(controller.getName(), event.getAnimatable().nextAttackAnimation(), "Attack");
            }
            ActiveAnimationInfo activeAnimation = getActiveAnimation(controller.getName());
            if (activeAnimation != null) {
                controller.setAnimation(new AnimationBuilder().addAnimation(activeAnimation.animationName()));
            }
            return PlayState.CONTINUE;
        } else {
            event.getController().markNeedsReload();
            return PlayState.STOP;
        }
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
        AnimationLogic.ActiveAnimationInfo activeAnimation = getActiveAnimation(controller.getName());
        if (activeAnimation != null) {
            controller.setAnimation(new AnimationBuilder().addAnimation(activeAnimation.animationName()));
        }
        return PlayState.CONTINUE;
    }

    public record ActiveAnimationInfo(String animationName, double endTick, String category) {

    }
}
