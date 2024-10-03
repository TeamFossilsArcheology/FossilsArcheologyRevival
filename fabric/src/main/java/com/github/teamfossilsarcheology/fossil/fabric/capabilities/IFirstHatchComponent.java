package com.github.teamfossilsarcheology.fossil.fabric.capabilities;

import dev.onyxstudios.cca.api.v3.component.sync.AutoSyncedComponent;

public interface IFirstHatchComponent extends AutoSyncedComponent {

    boolean hasHatchedDinosaur();

    void setHatchedDinosaur(boolean hatched);
}
