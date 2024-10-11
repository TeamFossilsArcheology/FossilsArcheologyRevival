package com.github.teamfossilsarcheology.fossil.entity.prehistoric.base;

import java.util.EnumSet;

public enum InfoFlag {
    BUCKETABLE;

    public static EnumSet<InfoFlag> bucketable() {
        return EnumSet.of(BUCKETABLE);
    }
    public static final EnumSet<InfoFlag> ALL_OPTS = EnumSet.allOf(InfoFlag.class);
}
