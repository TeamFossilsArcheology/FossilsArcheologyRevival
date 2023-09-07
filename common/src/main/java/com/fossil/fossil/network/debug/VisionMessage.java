package com.fossil.fossil.network.debug;

import com.fossil.fossil.client.gui.debug.DebugScreen;
import dev.architectury.networking.NetworkManager;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;

import java.util.function.Supplier;

public class VisionMessage {
    private final BlockPos target;
    private final BlockState block;

    public VisionMessage(FriendlyByteBuf buf) {
        this(buf.readVarIntArray());
    }

    public VisionMessage(int[] targetArray) {
        this.target = new BlockPos(targetArray[0], targetArray[1], targetArray[2]);
        this.block = Block.stateById(targetArray[3]);
    }

    public VisionMessage(BlockPos target, BlockState block) {
        this.target = target;
        this.block = block;
    }

    public void write(FriendlyByteBuf buf) {
        int[] targetsOut = new int[4];
        targetsOut[0] = target.getX();
        targetsOut[1] = target.getY();
        targetsOut[2] = target.getZ();
        targetsOut[3] = Block.getId(block);
        buf.writeVarIntArray(targetsOut);
    }

    public void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {
        contextSupplier.get().queue(() -> {
            DebugScreen.showVision(contextSupplier.get().getPlayer(), target, block);
        });
    }
}
