package com.fossil.fossil.network.debug;

import com.fossil.fossil.client.gui.debug.instruction.Instruction;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import com.fossil.fossil.util.Version;
import dev.architectury.networking.NetworkManager;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.entity.Entity;

import java.util.List;
import java.util.function.Supplier;

public class C2SInstructionMessage {
    private final int entityId;
    private final boolean loop;
    private final List<Instruction> instructions;

    public C2SInstructionMessage(FriendlyByteBuf buf) {
        this(buf.readInt(), buf.readBoolean(), Instruction.decodeBuffer(buf));
    }

    public C2SInstructionMessage(int entityId, boolean loop, List<Instruction> instructions) {
        this.entityId = entityId;
        this.loop = loop;
        this.instructions = instructions;
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeInt(entityId);
        buf.writeBoolean(loop);
        Instruction.encodeBuffer(instructions, buf);
    }

    public void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {
        contextSupplier.get().queue(() -> {
            if (Version.debugEnabled()) {
                Entity entity = contextSupplier.get().getPlayer().level.getEntity(entityId);
                if (entity instanceof Prehistoric prehistoric) {
                    prehistoric.getInstructionSystem().start(instructions, loop);
                }
            }
        });
    }
}
