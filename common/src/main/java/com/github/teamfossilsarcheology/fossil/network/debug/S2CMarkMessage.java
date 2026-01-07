package com.github.teamfossilsarcheology.fossil.network.debug;

import com.github.teamfossilsarcheology.fossil.client.gui.debug.DebugScreen;
import com.github.teamfossilsarcheology.fossil.util.Version;
import dev.architectury.networking.NetworkChannel;
import dev.architectury.networking.NetworkManager;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class S2CMarkMessage {
    private final List<BlockPos> targets;
    private final List<BlockState> blocks;
    private final boolean below;

    private S2CMarkMessage(FriendlyByteBuf buf) {
        this(buf.readVarIntArray(), buf.readBoolean());
    }

    public S2CMarkMessage(int[] targetArray, boolean below) {
        this.targets = new ArrayList<>();
        this.blocks = new ArrayList<>();
        for (int i = 0; i < targetArray.length / 4; i++) {
            this.targets.add(new BlockPos(targetArray[4 * i], targetArray[4 * i + 1], targetArray[4 * i + 2]));
            this.blocks.add(Block.stateById(targetArray[4 * i + 3]));
        }
        this.below = below;
    }

    public S2CMarkMessage(int[] targetArray, BlockState[] blocks, boolean below) {
        this.targets = new ArrayList<>();
        this.blocks = new ArrayList<>();
        for (int i = 0; i < blocks.length; i++) {
            this.targets.add(new BlockPos(targetArray[3 * i], targetArray[3 * i + 1], targetArray[3 * i + 2]));
            this.blocks.add(blocks[i]);
        }
        this.below = below;
    }

    private void write(FriendlyByteBuf buf) {
        int[] targetsOut = new int[targets.size() * 4];
        for (int i = 0; i < targets.size(); i++) {
            targetsOut[4 * i] = targets.get(i).getX();
            targetsOut[4 * i + 1] = targets.get(i).getY();
            targetsOut[4 * i + 2] = targets.get(i).getZ();
            targetsOut[4 * i + 3] = Block.getId(blocks.get(i));
        }
        buf.writeVarIntArray(targetsOut);
        buf.writeBoolean(below);
    }

    private void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {
        contextSupplier.get().queue(() -> {
            if (Version.debugEnabled()) {
                DebugScreen.showPath(contextSupplier.get().getPlayer(), targets, blocks, below);
            }
        });
    }

    public static void register(NetworkChannel channel) {
        channel.register(S2CMarkMessage.class, S2CMarkMessage::write, S2CMarkMessage::new, S2CMarkMessage::apply);
    }
}
