package com.fossil.fossil.network;

import com.fossil.fossil.client.gui.DebugScreen;
import dev.architectury.networking.NetworkManager;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class MarkMessage {
    private final List<BlockPos> targets;
    private final List<BlockState> blocks;

    public MarkMessage(FriendlyByteBuf buf) {
        this(buf.readVarIntArray());
    }

    public MarkMessage(int[] targetArray) {
        this.targets = new ArrayList<>();
        this.blocks = new ArrayList<>();
        for (int i = 0; i < targetArray.length / 4; i++) {
            this.targets.add(new BlockPos(targetArray[4 * i], targetArray[4 * i + 1], targetArray[4 * i + 2]));
            this.blocks.add(Block.stateById(targetArray[4 * i + 3]));
        }
    }

    public MarkMessage(int[] targetArray, BlockState[] blocks) {
        this.targets = new ArrayList<>();
        this.blocks = new ArrayList<>();
        for (int i = 0; i < blocks.length; i++) {
            this.targets.add(new BlockPos(targetArray[3 * i], targetArray[3 * i + 1], targetArray[3 * i + 2]));
            this.blocks.add(blocks[i]);
        }
    }

    public void write(FriendlyByteBuf buf) {
        int[] targetsOut = new int[targets.size() * 4];
        for (int i = 0; i < targets.size(); i++) {
            targetsOut[4 * i] = targets.get(i).getX();
            targetsOut[4 * i + 1] = targets.get(i).getY();
            targetsOut[4 * i + 2] = targets.get(i).getZ();
            targetsOut[4 * i + 3] = Block.getId(blocks.get(i));
        }
        buf.writeVarIntArray(targetsOut);
    }

    public void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {
        contextSupplier.get().queue(() -> {
            DebugScreen.showPath(contextSupplier.get().getPlayer(), targets, blocks);
        });
    }
}
