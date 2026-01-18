package com.github.teamfossilsarcheology.fossil.network.debug;

import com.github.teamfossilsarcheology.fossil.client.gui.debug.InstructionTab;
import com.github.teamfossilsarcheology.fossil.client.gui.debug.instruction.Instruction;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.util.Version;
import dev.architectury.networking.NetworkChannel;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;

import java.util.List;
import java.util.function.Supplier;

public class InstructionMessage {
    private final int entityId;
    private final boolean loop;
    private final List<Instruction> instructions;

    private InstructionMessage(FriendlyByteBuf buf) {
        this(buf.readInt(), buf.readBoolean(), Instruction.decodeBuffer(buf));
    }

    public InstructionMessage(int entityId, boolean loop, List<Instruction> instructions) {
        this.entityId = entityId;
        this.loop = loop;
        this.instructions = instructions;
    }

    private void write(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeBoolean(loop);
        Instruction.encodeBuffer(instructions, buf);
    }

    private void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {
        contextSupplier.get().queue(() -> {
            if (Version.debugEnabled()) {
                Level level = contextSupplier.get().getPlayer().level();
                Entity entity = level.getEntity(entityId);
                if (entity instanceof Prehistoric prehistoric) {
                    if (level.isClientSide) {
                        if (InstructionTab.activeEntity == null || InstructionTab.activeEntity.getId() != entity.getId()) {
                            InstructionTab.INSTRUCTIONS.put(entity.getUUID(), new InstructionTab.Pair(entityId, instructions));
                        }
                    } else {
                        prehistoric.getInstructionSystem().start(instructions, loop, !instructions.isEmpty());
                    }
                }
            }
        });
    }

    public static void register(NetworkChannel channel) {
        channel.register(InstructionMessage.class, InstructionMessage::write, InstructionMessage::new, InstructionMessage::apply);
    }
}
