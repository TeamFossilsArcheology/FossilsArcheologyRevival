package com.fossil.fossil.entity.util;

import com.fossil.fossil.client.gui.debug.instruction.Instruction;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import net.minecraft.world.entity.ai.navigation.GroundPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.entity.ai.navigation.WaterBoundPathNavigation;
import net.minecraft.world.level.pathfinder.Path;

import java.util.ArrayList;
import java.util.List;

public class InstructionSystem {
    protected final Prehistoric entity;
    private final List<Instruction> instructions = new ArrayList<>();
    private int index = -1;
    private boolean shouldLoop;
    private int tries;
    private long endTick;

    public InstructionSystem(Prehistoric entity) {
        this.entity = entity;
    }

    public void tick() {
        if (instructions.isEmpty() || index >= instructions.size()) {
            return;
        }
        if (!tickRunning()) {
            startNext();
        }
    }

    private boolean tickRunning() {
        Instruction current = instructions.get(index);
        if (current instanceof Instruction.MoveTo moveTo) {
            return tryUpdatePath(moveTo);
        } else if (current instanceof Instruction.TeleportTo teleportTo) {
            return false;
        } else if (current instanceof Instruction.Idle idle) {
            return endTick >= entity.level.getGameTime();
        } else if (current instanceof Instruction.PlayAnim playAnim) {

        } else if (current instanceof Instruction.Attack attack) {

        }
        return false;
    }

    private boolean tryUpdatePath(Instruction.MoveTo moveTo) {
        PathNavigation navigation = entity.getNavigation();
        if (tries >= 15) {
            tries = 0;
            return false;
        }
        if (navigation instanceof GroundPathNavigation && !entity.isOnGround() && !entity.isInWaterOrBubble()) {
            return true;
        }
        if (navigation instanceof WaterBoundPathNavigation && !entity.isInWaterOrBubble()) {
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
        if (navigation.getPath().isDone() && moveTo.target.closerToCenterThan(entity.position(), acceptedDistance())) {
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
        if (entity.isCustomMultiPart()) {
            return entity.getHeadRadius() + 1;
        }
        return entity.getBbWidth() / 2 + 1;
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
            entity.getNavigation().moveTo(moveTo.target.getX(), moveTo.target.getY(), moveTo.target.getZ(), 1);
        } else if (current instanceof Instruction.TeleportTo teleportTo) {
            entity.moveTo(teleportTo.target, entity.getYRot(), entity.getXRot());
        } else if (current instanceof Instruction.Idle idle) {
            endTick = entity.level.getGameTime() + idle.duration;
        } else if (current instanceof Instruction.PlayAnim playAnim) {

        } else if (current instanceof Instruction.Attack attack) {

        }
    }

    public void start(List<Instruction> instructions, boolean loop) {
        this.instructions.clear();
        this.instructions.addAll(instructions);
        entity.getNavigation().stop();
        index = -1;
        shouldLoop = loop;
        if (instructions.isEmpty()) {
            stop();
        } else {
            entity.disableCustomAI((byte) 0, false);
            entity.disableCustomAI((byte) 1, true);
            entity.disableCustomAI((byte) 2, false);
            entity.disableCustomAI((byte) 3, false);
            startNext();
        }
    }

    public void stop() {
        entity.disableCustomAI((byte) 0, true);
        entity.disableCustomAI((byte) 1, false);
    }
}
