package com.fossil.fossil.network.debug;

import com.fossil.fossil.client.renderer.entity.PrehistoricGeoRenderer;
import dev.architectury.networking.NetworkManager;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class NewMarkMessage {
    private final List<BlockPos> targets;
    private final BlockPos target;

    public NewMarkMessage(FriendlyByteBuf buf) {
        this(buf.readVarIntArray(), buf.readBlockPos());
    }

    public NewMarkMessage(int[] targetArray, BlockPos target) {
        this.targets = new ArrayList<>();
        for (int i = 0; i < targetArray.length / 3; i++) {
            this.targets.add(new BlockPos(targetArray[3 * i], targetArray[3 * i + 1], targetArray[3 * i + 2]));
        }
        this.target = target;
    }

    public void write(FriendlyByteBuf buf) {
        int[] targetsOut = new int[targets.size() * 3];
        for (int i = 0; i < targets.size(); i++) {
            targetsOut[3 * i] = targets.get(i).getX();
            targetsOut[3 * i + 1] = targets.get(i).getY();
            targetsOut[3 * i + 2] = targets.get(i).getZ();
        }
        buf.writeVarIntArray(targetsOut);
        buf.writeBlockPos(target);
    }

    public void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {
        contextSupplier.get().queue(() -> PrehistoricGeoRenderer.showPath(targets, target));
    }
}
