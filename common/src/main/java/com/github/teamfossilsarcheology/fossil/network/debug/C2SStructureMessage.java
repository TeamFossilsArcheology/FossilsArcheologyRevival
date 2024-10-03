package com.github.teamfossilsarcheology.fossil.network.debug;

import com.github.teamfossilsarcheology.fossil.client.gui.debug.instruction.Instruction;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricEntityInfo;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricFlying;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricSwimming;
import com.github.teamfossilsarcheology.fossil.util.Version;
import dev.architectury.networking.NetworkManager;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.MobSpawnType;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class C2SStructureMessage {
    private final boolean onlyStructure;

    public C2SStructureMessage(FriendlyByteBuf buf) {
        this(buf.readBoolean());
    }

    public C2SStructureMessage(boolean onlyStructure) {
        this.onlyStructure = onlyStructure;
    }

    public void write(FriendlyByteBuf buf) {
        buf.writeBoolean(onlyStructure);
    }

    public void apply(Supplier<NetworkManager.PacketContext> contextSupplier) {
        contextSupplier.get().queue(() -> {
            if (Version.debugEnabled() && contextSupplier.get().getPlayer().level instanceof ServerLevel serverLevel) {
                spawnTestStructure(serverLevel, onlyStructure);
            }
        });
    }

    private static void spawnTestStructure(ServerLevel level, boolean onlyStructure) {
        int length = 0;
        List<Prehistoric> toAdd = new ArrayList<>();
        for (PrehistoricEntityInfo info : PrehistoricEntityInfo.values()) {
            Entity entity = info.entityType().create(level);
            if (entity instanceof PrehistoricSwimming swimming && !swimming.isAmphibious()) {
                continue;
            }
            if (entity instanceof PrehistoricFlying) {
                continue;
            }
            if (entity instanceof Prehistoric prehistoric) {
                toAdd.add(prehistoric);
                length++;
            }
        }
        BlockState ironBlock = Blocks.JUNGLE_PLANKS.defaultBlockState();
        for (BlockPos blockPos : BlockPos.betweenClosed(0, 119, 0, length * 5 + 20, 119, 30)) {
            level.setBlock(blockPos, ironBlock, 2);
        }
        for (int i = 0; i < toAdd.size(); i++) {
            Prehistoric prehistoric = toAdd.get(i);
            double xOffset = i * 5d + 3;
            List<Instruction> instructions = List.of(
                    new Instruction.TeleportTo(new BlockPos(xOffset, 120, 0), 0),
                    new Instruction.MoveTo(new BlockPos(xOffset, 120, 25)));
            prehistoric.getInstructionSystem().start(instructions, true);
            prehistoric.getInstructionSystem().syncWithClients();
            prehistoric.finalizeSpawn(level, level.getCurrentDifficultyAt(prehistoric.blockPosition()), MobSpawnType.SPAWN_EGG, null, null);
            prehistoric.moveTo(xOffset, 120, 0, 0, 0);
            level.addFreshEntity(prehistoric);
        }
    }
}
