package com.fossil.fossil.fabric.capabilities;

import com.fossil.fossil.entity.prehistoric.base.PrehistoricEntityType;
import dev.onyxstudios.cca.api.v3.component.Component;
import org.jetbrains.annotations.Nullable;

public interface IMammalComponent extends Component {
    int getEmbryoProgress();

    void setEmbryoProgress(int progress);

    PrehistoricEntityType getEmbryo();

    void setEmbryo(@Nullable PrehistoricEntityType embryo);
}
