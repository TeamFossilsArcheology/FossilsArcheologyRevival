package com.fossil.fossil.fabric.capabilities;

import com.fossil.fossil.entity.prehistoric.base.EntityInfo;
import dev.onyxstudios.cca.api.v3.component.Component;
import org.jetbrains.annotations.Nullable;

public interface IMammalComponent extends Component {
    int getEmbryoProgress();

    void setEmbryoProgress(int progress);

    EntityInfo getEmbryo();

    void setEmbryo(@Nullable EntityInfo embryo);
}
