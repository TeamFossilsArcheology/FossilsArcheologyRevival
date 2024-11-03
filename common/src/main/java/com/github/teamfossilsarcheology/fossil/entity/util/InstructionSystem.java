package com.github.teamfossilsarcheology.fossil.entity.util;

import com.github.teamfossilsarcheology.fossil.client.gui.debug.instruction.Instruction;
import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationCategory;
import com.github.teamfossilsarcheology.fossil.entity.animation.AnimationLogic;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.swimming.Meganeura;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.system.AISystem;
import com.github.teamfossilsarcheology.fossil.network.MessageHandler;
import com.github.teamfossilsarcheology.fossil.network.debug.InstructionMessage;
import com.github.teamfossilsarcheology.fossil.util.Version;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.level.pathfinder.Path;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

/**
 * This system handles custom instructions sent by the debug menu {@link com.github.teamfossilsarcheology.fossil.client.gui.debug.InstructionTab InstructionTab}
 * The instructions will be played in order and the mob will be unable to do anything else while this is running.
 */
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
        if (instructions.isEmpty() || index >= instructions.size() || !Version.debugEnabled()) {
            return;
        }
        if (!tickRunning()) {
            startNext();
        }
    }

    private boolean tickRunning() {
        Instruction current = instructions.get(index);
        if (mob.isHungry()) {
            mob.setHunger(mob.getMaxHunger());
        }

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
        } else if (current instanceof Instruction.Sleep sleep) {
            if (endTick < mob.level.getGameTime()) {
                mob.sleepSystem.setSleeping(false);
                mob.sleepSystem.setSleepForced(false);
                return false;
            }
            return true;
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
                        activeAnim = mob.getAnimationLogic().forceAnimation(playAnim.controller, mob.getAllAnimations().get(playAnim.name), AnimationCategory.IDLE, 5, false);
                    } else {
                        return false;
                    }
                }
                return true;
            }
        } else if (current instanceof Instruction.Attack attack) {

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
        if (mob.getEntityHitboxData().hasCustomParts()) {
            return mob.getEntityHitboxData().getHeadRadius() * mob.getScale() + 1;
        }
        return mob.getBbWidth() / 2 + 1;
    }

    private void startNext() {
        index++;
        tries = 0;
        if (instructions.size() == index) {
            if (shouldLoop) {
                syncWithClients();
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
                activeAnim = mob.getAnimationLogic().forceAnimation(playAnim.controller, mob.getAllAnimations().get(playAnim.name), AnimationCategory.IDLE, 5, true);
            } else {
                animCount = playAnim.count;
                activeAnim = mob.getAnimationLogic().forceAnimation(playAnim.controller, mob.getAllAnimations().get(playAnim.name), AnimationCategory.IDLE, 5, false);
            }
        } else if (current instanceof Instruction.Sleep sleep) {
            endTick = mob.level.getGameTime() + sleep.duration;
            mob.sleepSystem.setSleepDisabled(false);
            mob.sleepSystem.setSleeping(true);
            mob.sleepSystem.setSleepForced(true);
        }
    }

    public void start(List<Instruction> instructions, boolean loop) {
        this.instructions.clear();
        this.instructions.addAll(instructions);
        mob.getNavigation().stop();
        index = -1;
        shouldLoop = loop;
        syncWithClients();
        mob.sleepSystem.setSleepDisabled(true);
        mob.sitSystem.setSittingDisabled(true);
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
        mob.sleepSystem.setSleepForced(false);
        mob.sleepSystem.setSleepDisabled(false);
        mob.sitSystem.setSittingDisabled(false);
    }

    public void syncWithClients() {
        MessageHandler.DEBUG_CHANNEL.sendToPlayers(((ServerLevel) mob.level).getPlayers(serverPlayer -> serverPlayer.distanceTo(mob) < 32),
                new InstructionMessage(mob.getId(), shouldLoop, instructions));
    }

    @Override
    public void saveAdditional(CompoundTag tag) {
        if (Version.debugEnabled() && !mob.level.isClientSide) {
            ListTag saved = new ListTag();
            for (int i = 0; i < instructions.size(); i++) {
                saved.addTag(i, instructions.get(i).encodeTag());
            }
            tag.put("Instructions", saved);
            tag.putBoolean("InstructionsLoop", shouldLoop);
        }
    }

    @Override
    public void load(CompoundTag tag) {
        if (Version.debugEnabled() && !mob.level.isClientSide) {
            instructions.clear();
            ListTag saved = tag.getList("Instructions", Tag.TAG_COMPOUND);
            for (Tag savedTag : saved) {
                instructions.add(Instruction.decodeFromTag((CompoundTag) savedTag));
            }
            shouldLoop = tag.getBoolean("InstructionsLoop");
            if (!instructions.isEmpty()) start(instructions, shouldLoop);
            syncWithClients();
        }
    }
}
