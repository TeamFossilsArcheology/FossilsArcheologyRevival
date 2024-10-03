package com.github.teamfossilsarcheology.fossil.fabric.capabilities;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.EntityInfo;
import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;
import org.jetbrains.annotations.Nullable;

public interface IMammalComponent extends AutoSyncedComponent {
    int getEmbryoProgress();

    void setEmbryoProgress(int progress);

    EntityInfo getEmbryo();

    void setEmbryo(@Nullable EntityInfo embryo);
}
