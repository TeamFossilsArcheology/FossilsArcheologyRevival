package com.fossil.fossil.client.gui.debug.instruction;

import com.fossil.fossil.Fossil;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;

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
                    case WALK_TO -> list.add(new MoveTo(buf.readBlockPos()));
                    case TELEPORT -> list.add(new TeleportTo(buf.readBlockPos()));
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

        public TeleportTo(BlockPos target) {
            super(Type.TELEPORT);
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

    public static class MoveTo extends Instruction {
        public final BlockPos target;

        public MoveTo(BlockPos target) {
            super(Type.WALK_TO);
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
        ATTACK, WALK_TO, PLAY_ANIM, IDLE, TELEPORT, BREACH;
    }
}
