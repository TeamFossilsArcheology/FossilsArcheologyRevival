package com.fossil.fossil.util;

import com.fossil.fossil.Fossil;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.world.level.timers.TimerCallback;
import net.minecraft.world.level.timers.TimerQueue;
import org.apache.logging.log4j.util.TriConsumer;

public class DisposableTask implements TimerCallback<MinecraftServer> {
    private final TriConsumer<MinecraftServer, TimerQueue<MinecraftServer>, Long> handler;

    public DisposableTask(TriConsumer<MinecraftServer, TimerQueue<MinecraftServer>, Long> handler) {
        this.handler = handler;
    }
    @Override
    public void handle(MinecraftServer obj, TimerQueue<MinecraftServer> manager, long gameTime) {
        handler.accept(obj, manager, gameTime);
    }

    public static class Serializer extends TimerCallback.Serializer<MinecraftServer, DisposableTask> {
        public Serializer() {
            super(new ResourceLocation(Fossil.MOD_ID, "disposable"), DisposableTask.class);
        }

        @Override
        public void serialize(CompoundTag tag, DisposableTask callback) {

        }

        @Override
        public DisposableTask deserialize(CompoundTag tag) {
            return new DisposableTask((u1, u2, u3) -> {});
        }
    }
}
