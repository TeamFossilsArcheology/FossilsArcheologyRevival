package com.github.teamfossilsarcheology.fossil.network;

import net.minecraft.network.syncher.EntityDataAccessor;

public interface SyncedEntityDataHelper {
    void fossilsArcheologyRevival$markNonDefaultAsDirty();
    void fossilsArcheologyRevival$markDirty(EntityDataAccessor<Integer> id);
}
