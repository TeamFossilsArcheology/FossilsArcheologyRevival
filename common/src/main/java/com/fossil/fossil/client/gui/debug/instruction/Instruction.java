package com.fossil.fossil.client.gui.debug.instruction;

import com.fossil.fossil.Fossil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;

import java.util.ArrayList;
import java.util.List;

public abstract class Instruction {
    public final Type type;

    private Instruction(Type type) {
        this.type = type;
    }

    public static void encodeBuffer(List<Instruction> instructions, FriendlyByteBuf buf) {
        for (Instruction instruction : instructions) {
            buf.writeEnum(instruction.type);
            instruction.encodeBuffer(buf);
        }
    }

    protected abstract void encodeBuffer(FriendlyByteBuf buf);

    public static List<Instruction> decodeBuffer(FriendlyByteBuf buf) {
        List<Instruction> list = new ArrayList<>();
        while (buf.isReadable()) {
            try {
                Type type = buf.readEnum(Type.class);
                switch (type) {
                    case MOVE_TO -> list.add(new MoveTo(buf.readBlockPos()));
                    case TELEPORT_TO -> list.add(new TeleportTo(buf.readBlockPos(), buf.readInt()));
                    case ATTACH_TO -> list.add(new AttachTo(buf.readBlockPos(), buf.readEnum(Direction.class), new Vec3(buf.readDouble(), buf.readDouble() ,buf.readDouble())));
                    case ATTACK -> list.add(new Attack(buf.readInt()));
                    case BREACH -> list.add(new Breach(buf.readInt()));
                    case PLAY_ANIM -> list.add(new PlayAnim());
                    case IDLE -> list.add(new Idle(buf.readInt()));
                    default -> Fossil.LOGGER.error("Instruction type {} not checked", type);
                }
            } catch (RuntimeException e) {
                Fossil.LOGGER.error("Could not read Instruction enum from buffer");
                return List.of();
            }
        }
        return list;
    }

    public static class TeleportTo extends Instruction {
        public final BlockPos target;
        public final int rotation;

        public TeleportTo(BlockPos target, int rotation) {
            super(Type.TELEPORT_TO);
            this.target = target;
            this.rotation = rotation;
        }

        @Override
        protected void encodeBuffer(FriendlyByteBuf buf) {
            buf.writeBlockPos(target);
            buf.writeInt(rotation);
        }

        @Override
        public String toString() {
            return type.name() + ": " + target.getX() + " " + target.getY() + " " + target.getZ();
        }
    }

    public static class AttachTo extends Instruction {
        public final BlockPos target;
        public final Direction direction;
        public final Vec3 location;

        public AttachTo(BlockPos target, Direction direction, Vec3 location) {
            super(Type.ATTACH_TO);
            this.target = target;
            this.direction = direction;
            this.location = location;
        }

        @Override
        protected void encodeBuffer(FriendlyByteBuf buf) {
            buf.writeBlockPos(target);
            buf.writeEnum(direction);
            buf.writeDouble(location.x);
            buf.writeDouble(location.y);
            buf.writeDouble(location.z);
        }

        @Override
        public String toString() {
            return type.name() + ": " + target.getX() + " " + target.getY() + " " + target.getZ() + " " + direction.name();
        }
    }

    public static class MoveTo extends Instruction {
        public final BlockPos target;

        public MoveTo(BlockPos target) {
            super(Type.MOVE_TO);
            this.target = target;
        }

        @Override
        protected void encodeBuffer(FriendlyByteBuf buf) {
            buf.writeBlockPos(target);
        }

        @Override
        public String toString() {
            return type.name() + ": " + target.getX() + " " + target.getY() + " " + target.getZ();
        }
    }

    public static class Attack extends Instruction {
        public final int targetId;

        public Attack(int targetId) {
            super(Type.ATTACK);
            this.targetId = targetId;
        }

        @Override
        protected void encodeBuffer(FriendlyByteBuf buf) {
            buf.writeInt(targetId);
        }

        @Override
        public String toString() {
            return type.name() + ": " + targetId;
        }
    }

    public static class Breach extends Instruction {
        public final int targetId;

        public Breach(int targetId) {
            super(Type.BREACH);
            this.targetId = targetId;
        }

        @Override
        protected void encodeBuffer(FriendlyByteBuf buf) {
            buf.writeInt(targetId);
        }

        @Override
        public String toString() {
            return type.name() + ": " + targetId;
        }
    }

    public static class PlayAnim extends Instruction {
        /**
         * Should it play for x ticks or loop x times
         */
        public final boolean timeBased;
        public final int count;

        public PlayAnim() {
            super(Type.PLAY_ANIM);
            timeBased = false;
            count = 0;
        }

        @Override
        protected void encodeBuffer(FriendlyByteBuf buf) {

        }
    }

    public static class Idle extends Instruction {
        public final int duration;

        public Idle(int duration) {
            super(Type.IDLE);
            this.duration = duration;
        }

        @Override
        protected void encodeBuffer(FriendlyByteBuf buf) {
            buf.writeInt(duration);
        }

        @Override
        public String toString() {
            return type.name() + ": " + (duration / 20f);
        }
    }

    public enum Type {
        ATTACK, MOVE_TO, PLAY_ANIM, IDLE, TELEPORT_TO, BREACH, ATTACH_TO;
    }
}
