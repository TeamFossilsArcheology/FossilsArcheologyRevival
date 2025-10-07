package com.github.teamfossilsarcheology.fossil.mixin;

import com.github.teamfossilsarcheology.fossil.network.SyncedEntityDataHelper;
import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import net.minecraft.network.syncher.EntityDataAccessor;
import net.minecraft.network.syncher.SynchedEntityData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

import java.util.concurrent.locks.ReadWriteLock;

@Mixin(SynchedEntityData.class)
public abstract class SyncedEntityDataMixin implements SyncedEntityDataHelper {

    @Shadow
    @Final
    private ReadWriteLock lock;

    @Shadow
    @Final
    private Int2ObjectMap<SynchedEntityData.DataItem<?>> itemsById;

    @Shadow
    private boolean isDirty;

    @Override
    public void fossilsArcheologyRevival$markNonDefaultAsDirty() {
        lock.readLock().lock();

        for (SynchedEntityData.DataItem<?> item : itemsById.values()) {
            if (!item.isSetToDefault()) {
                item.setDirty(true);
                isDirty = true;
            }
        }
        lock.readLock().unlock();
    }

    @Override
    public void fossilsArcheologyRevival$markDirty(EntityDataAccessor<Integer> dataAccessor) {
        itemsById.get(dataAccessor.getId()).setDirty(true);
        isDirty = true;
    }
}
