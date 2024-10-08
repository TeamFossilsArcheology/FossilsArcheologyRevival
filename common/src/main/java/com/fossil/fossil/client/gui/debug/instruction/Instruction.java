package com.fossil.fossil.client.gui.debug.instruction;

import com.fossil.fossil.Fossil;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtUtils;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.phys.Vec3;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class Instruction {
    public final Type type;

    private Instruction(Type type) {
        this.type = type;
    }

    public static void encodeBuffer(List<Instruction> instructions, FriendlyByteBuf buf) {
        for (Instruction instruction : instructions) {
            buf.writeNbt(instruction.encodeTag());
        }
    }

    public CompoundTag encodeTag() {
        CompoundTag tag = new CompoundTag();
        tag.putString("Type", type.name());
        return tag;
    }

    public static Instruction decodeFromTag(CompoundTag tag) {
        try {
            Type type = Type.valueOf(tag.getString("Type"));
            return switch (type) {
                case MOVE_TO -> MoveTo.decodeTag(tag);
                case TELEPORT_TO -> TeleportTo.decodeTag(tag);
                case ATTACH_TO -> AttachTo.decodeTag(tag);
                case ATTACK -> Attack.decodeTag(tag);
                case BREACH -> Breach.decodeTag(tag);
                case PLAY_ANIM -> PlayAnim.decodeTag(tag);
                case IDLE -> Idle.decodeTag(tag);
            };
        } catch (RuntimeException e) {
            Fossil.LOGGER.error("Could not read Instruction enum from tag: ", e);
            return new Idle(0);
        }
    }

    public static List<Instruction> decodeBuffer(FriendlyByteBuf buf) {
        List<Instruction> list = new ArrayList<>();
        while (buf.isReadable()) {
            try {
                CompoundTag tag = buf.readNbt();
                if (tag == null) {
                    continue;
                }
                list.add(decodeFromTag(tag));
            } catch (RuntimeException e) {
                Fossil.LOGGER.error("Could not read Instruction enum from buffer: ", e);
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
        public CompoundTag encodeTag() {
            CompoundTag tag = super.encodeTag();
            tag.put("Target", NbtUtils.writeBlockPos(target));
            tag.putInt("Rotation", rotation);
            return tag;
        }

        protected static Instruction decodeTag(CompoundTag tag) {
            return new TeleportTo(NbtUtils.readBlockPos(tag.getCompound("Target")), tag.getInt("Rotation"));
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
        public CompoundTag encodeTag() {
            CompoundTag tag = super.encodeTag();
            tag.put("Target", NbtUtils.writeBlockPos(target));
            tag.putInt("Direction", direction.ordinal());
            tag.putDouble("LocationX", location.x);
            tag.putDouble("LocationY", location.y);
            tag.putDouble("LocationZ", location.z);
            return tag;
        }

        protected static Instruction decodeTag(CompoundTag tag) {
            return new AttachTo(NbtUtils.readBlockPos(tag.getCompound("Target")), Direction.values()[tag.getInt("Direction")], new Vec3(tag.getDouble("LocationX"), tag.getDouble("LocationY"), tag.getDouble("LocationZ")));
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
        public CompoundTag encodeTag() {
            CompoundTag tag = super.encodeTag();
            tag.put("Target", NbtUtils.writeBlockPos(target));
            return tag;
        }

        protected static Instruction decodeTag(CompoundTag tag) {
            return new MoveTo(NbtUtils.readBlockPos(tag.getCompound("Target")));
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
        public CompoundTag encodeTag() {
            CompoundTag tag = super.encodeTag();
            tag.putInt("TargetId", targetId);
            return tag;
        }

        protected static Instruction decodeTag(CompoundTag tag) {
            return new Attack(tag.getInt("TargetId"));
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
        public CompoundTag encodeTag() {
            CompoundTag tag = super.encodeTag();
            tag.putInt("TargetId", targetId);
            return tag;
        }

        protected static Instruction decodeTag(CompoundTag tag) {
            return new Breach(tag.getInt("TargetId"));
        }

        @Override
        public String toString() {
            return type.name() + ": " + targetId;
        }
    }

    public static class PlayAnim extends Instruction {
        public final String name;
        public final String controller;
        /**
         * Should it play for x ticks or loop x times
         */
        public final boolean timeBased;
        public final int count;
        private final String displayName;

        public PlayAnim(String name, String controller, boolean timeBased, int count) {
            super(Type.PLAY_ANIM);
            this.name = name;
            this.controller = controller;
            this.timeBased = timeBased;
            this.count = count;
            String[] split = name.split("\\.");
            this.displayName = split.length > 0 ? StringUtils.capitalize(split[split.length - 1]) : "";
        }

        @Override
        public CompoundTag encodeTag() {
            CompoundTag tag = super.encodeTag();
            tag.putString("Name", name);
            tag.putString("Controller", controller);
            tag.putBoolean("TimeBased", timeBased);
            tag.putInt("Count", count);
            return tag;
        }

        protected static Instruction decodeTag(CompoundTag tag) {
            return new PlayAnim(tag.getString("Name"), tag.getString("Controller"), tag.getBoolean("TimeBased"), tag.getInt("Count"));
        }

        @Override
        public String toString() {
            return type.name() + ": " + displayName + " " + timeBased + " " + count;
        }
    }

    public static class Idle extends Instruction {
        public final int duration;

        public Idle(int duration) {
            super(Type.IDLE);
            this.duration = duration;
        }

        @Override
        public CompoundTag encodeTag() {
            CompoundTag tag = super.encodeTag();
            tag.putInt("Duration", duration);
            return tag;
        }

        protected static Instruction decodeTag(CompoundTag tag) {
            return new Idle(tag.getInt("Duration"));
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
