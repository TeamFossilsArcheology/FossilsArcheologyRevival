package com.fossil.fossil.entity.util;

import com.fossil.fossil.client.gui.debug.instruction.Instruction;
import com.fossil.fossil.entity.ai.BreachAttackGoal;
import com.fossil.fossil.entity.animation.AnimationLogic;
import com.fossil.fossil.entity.prehistoric.base.AISystem;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.entity.prehistoric.base.PrehistoricSwimming;
import com.fossil.fossil.entity.prehistoric.swimming.Meganeura;
import com.fossil.fossil.network.MessageHandler;
import com.fossil.fossil.network.debug.InstructionMessage;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public class InstructionSystem extends AISystem {
    private final List<Instruction> instructions = new ArrayList<>();
    private int index = -1;
    private boolean shouldLoop;
    private int tries;
    private long endTick;
    private boolean breachTargetReached;
    private boolean attached;
    private AnimationLogic.ActiveAnimationInfo activeAnim;
    private long animCount;

    public InstructionSystem(Prehistoric entity) {
        super(entity);
    }

    @Override
    public void serverTick() {
        if (instructions.isEmpty() || index >= instructions.size()) {
            return;
        }
        if (!tickRunning()) {
            startNext();
        }
    }

    private boolean tickRunning() {
        Instruction current = instructions.get(index);
        System.out.println("tickRunning " + current);
        if (current instanceof Instruction.MoveTo moveTo) {
            return tryUpdatePath(moveTo);
        } else if (current instanceof Instruction.TeleportTo teleportTo) {
            if (endTick < mob.level.getGameTime()) {
                return false;
            }
            mob.moveTo(teleportTo.target, teleportTo.rotation, mob.getXRot());
            mob.setYHeadRot(teleportTo.rotation);
            return true;
        } else if (current instanceof Instruction.AttachTo attachTo) {
            if (mob instanceof Meganeura meganeura) {
                if (!meganeura.getAttachSystem().isAttached()) {
                    return true;
                } else if (!attached) {
                    attached = true;
                    endTick = mob.level.getGameTime() + 100;
                }
                if (endTick < mob.level.getGameTime()) {
                    meganeura.getAttachSystem().stopAttaching();
                    return false;
                }
                return true;
            }
            return false;
        } else if (current instanceof Instruction.Idle idle) {
            return endTick >= mob.level.getGameTime();
        } else if (current instanceof Instruction.PlayAnim playAnim) {
            if (playAnim.timeBased) {
                if (animCount < mob.level.getGameTime()) {
                    mob.getAnimationLogic().cancelAnimation(playAnim.controller);
                    return false;
                }
                return true;
            } else {
                if (mob.getAnimationLogic().isAnimationDone(activeAnim)) {
                    animCount--;
                    if (animCount > 0) {
                        activeAnim = mob.getAnimationLogic().forceAnimation(playAnim.controller, mob.getAllAnimations().get(playAnim.name), AnimationLogic.Category.IDLE, 5, false);
                    } else {
                        return false;
                    }
                }
                return true;
            }
        } else if (current instanceof Instruction.Attack attack) {

        } else if (current instanceof Instruction.Breach breach) {
            PrehistoricSwimming swimming = (PrehistoricSwimming) mob;
            Entity target = mob.level.getEntity(breach.targetId);
            if (target instanceof LivingEntity livingEntity) {
                if (!livingEntity.isAlive() || BreachAttackGoal.isEntitySubmerged(livingEntity) || !PrehistoricSwimming.isOverWater(livingEntity)) {
                    return false;
                }
                if (Util.canReachPrey(mob, target)) {
                    breachTargetReached = true;
                    //swimming.startGrabAttack(target);
                }
                if (breachTargetReached && mob.isInWater()) {
                    swimming.setBreaching(false);
                    breachTargetReached = false;
                    swimming.stopGrabAttack(livingEntity);
                    return false;
                }
                return true;
            }
        }
        return false;
    }

    private boolean tryUpdatePath(Instruction.MoveTo moveTo) {
        PathNavigation navigation = mob.getNavigation();
        if (tries >= 15) {
            tries = 0;
            return false;
        }
        if (navigation instanceof GroundPathNavigation && !mob.isOnGround() && !mob.isInWaterOrBubble()) {
            return true;
        }
        if (navigation instanceof WaterBoundPathNavigation && !mob.isInWaterOrBubble()) {
            return true;
        }

        if (navigation.getPath() == null) {
            Path path = navigation.createPath(moveTo.target, 1);
            if (path == null) {
                tries++;
                return true;
            }
            return navigation.moveTo(path, 1);
        }
        if (navigation.getPath().isDone() && !moveTo.target.closerToCenterThan(mob.position(), acceptedDistance())) {
            Path path = navigation.createPath(moveTo.target, 1);
            if (path == null) {
                tries++;
                return true;
            }
            return navigation.moveTo(path, 1);
        }
        return !navigation.getPath().isDone();
    }

    public double acceptedDistance() {
        if (mob.isCustomMultiPart()) {
            return mob.getHeadRadius() + 1;
        }
        return mob.getBbWidth() / 2 + 1;
    }

    private void startNext() {
        index++;
        tries = 0;
        if (instructions.size() == index) {
            if (shouldLoop) {
                index = 0;
            } else {
                stop();
                return;
            }
        }
        Instruction current = instructions.get(index);
        if (current instanceof Instruction.MoveTo moveTo) {
            mob.getNavigation().moveTo(moveTo.target.getX(), moveTo.target.getY(), moveTo.target.getZ(), 1);
        } else if (current instanceof Instruction.TeleportTo teleportTo) {
            mob.getNavigation().stop();
            mob.moveTo(teleportTo.target, teleportTo.rotation, mob.getXRot());
            mob.setYHeadRot(teleportTo.rotation);
            endTick = mob.level.getGameTime() + 5;
        } else if (current instanceof Instruction.AttachTo attachTo) {
            attached = false;
            mob.getNavigation().moveTo(attachTo.target.getX(), attachTo.target.getY(), attachTo.target.getZ(), 1);
            if (mob instanceof Meganeura meganeura) {
                Direction face = attachTo.direction;
                float rad = mob.getBbWidth() / 2;
                if (!meganeura.usesAttachHitBox()) rad *= meganeura.getAttachHitBoxScale();
                Vec3 pos = new Vec3(attachTo.location.x + rad * face.getStepX(), attachTo.location.y, attachTo.location.z + rad * face.getStepZ());
                meganeura.getAttachSystem().setAttachTarget(attachTo.target, face, pos);
            }
        } else if (current instanceof Instruction.Idle idle) {
            endTick = mob.level.getGameTime() + idle.duration;
        } else if (current instanceof Instruction.PlayAnim playAnim) {
            if (playAnim.timeBased) {
                animCount = mob.level.getGameTime() + playAnim.count * 20L;
                activeAnim = mob.getAnimationLogic().forceAnimation(playAnim.controller, mob.getAllAnimations().get(playAnim.name), AnimationLogic.Category.IDLE, 5, true);
            } else {
                animCount = playAnim.count;
                activeAnim = mob.getAnimationLogic().forceAnimation(playAnim.controller, mob.getAllAnimations().get(playAnim.name), AnimationLogic.Category.IDLE, 5, false);
            }
        } else if (current instanceof Instruction.Breach breach) {
            Entity target = mob.level.getEntity(breach.targetId);
            if (target instanceof LivingEntity livingEntity && mob instanceof PrehistoricSwimming swimming) {
                mob.setTarget(livingEntity);
                mob.getMoveControl().setWantedPosition(target.position().x, target.position().y + 4, target.position().z, 1);
                swimming.setBreaching(true);
            }
        }
    }

    public void start(List<Instruction> instructions, boolean loop) {
        this.instructions.clear();
        this.instructions.addAll(instructions);
        mob.getNavigation().stop();
        index = -1;
        shouldLoop = loop;
        syncWithClients();
        if (instructions.isEmpty()) {
            stop();
        } else {
            if (mob instanceof Meganeura meganeura) {
                meganeura.getAttachSystem().stopAttaching();
            }
            if (mob.isSleeping()) mob.sleepSystem.setSleeping(false);
            if (mob.sitSystem.isSitting()) mob.sitSystem.setSitting(false);
            mob.disableCustomAI((byte) 0, false);
            mob.disableCustomAI((byte) 1, true);
            mob.disableCustomAI((byte) 2, false);
            mob.disableCustomAI((byte) 3, false);
            startNext();
        }
    }

    public void stop() {
        mob.disableCustomAI((byte) 0, true);
        mob.disableCustomAI((byte) 1, false);
    }

    public void syncWithClients() {
        MessageHandler.DEBUG_CHANNEL.sendToPlayers(((ServerLevel) mob.level).getPlayers(serverPlayer -> serverPlayer.distanceTo(mob) < 32),
                new InstructionMessage(mob.getId(), shouldLoop, instructions));
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        ListTag saved = new ListTag();
        for (int i = 0; i < instructions.size(); i++) {
            saved.addTag(i, instructions.get(i).encodeTag());
        }
        tag.put("Instructions", saved);
    }

    @Override
    public void load(CompoundTag tag) {
        instructions.clear();
        ListTag saved = tag.getList("Instructions", Tag.TAG_LIST);
        for (Tag savedTag : saved) {
            instructions.add(Instruction.decodeFromTag((CompoundTag) savedTag));
        }
        syncWithClients();
    }
}
