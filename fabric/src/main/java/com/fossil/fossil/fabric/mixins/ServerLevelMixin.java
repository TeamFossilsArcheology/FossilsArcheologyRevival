package com.fossil.fossil.fabric.mixins;

import com.fossil.fossil.fabric.MultiPartServerLevel;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.entity.LevelEntityGetter;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Collection;

/**
 * Fabric has no PartEntity so we mixin our own
 *
 * @see EntityCallbacksMixin
 * @see MultiPartServerLevel
 * @see LevelMixin
 */
@Mixin(ServerLevel.class)
public abstract class ServerLevelMixin implements MultiPartServerLevel {
    @Shadow
    protected abstract LevelEntityGetter<Entity> getEntities();

    @Unique
    private Int2ObjectMap<Entity> parts = new Int2ObjectOpenHashMap<>();

    @Override
    public Collection<Entity> getMultiParts() {
        return parts.values();
    }

    @Override
    public void addMultiPart(Entity part) {
        parts.put(part.getId(), part);
    }

    @Override
    public void removeMultiPart(Entity part) {
        parts.remove(part.getId());
    }


    @Inject(method = "getEntityOrPart(I)Lnet/minecraft/world/entity/Entity;", at = @At("TAIL"), cancellable = true)
    private void getEntityOrMultiPart(int id, CallbackInfoReturnable<Entity> cir) {
        if (getEntities().get(id) == null) {
            if (parts.containsKey(id)) {
                cir.setReturnValue(parts.get(id));
            }
        }
    }
}
