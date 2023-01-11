package com.fossil.fossil.util;

public enum FossilAnimation {
    IDLE,
    WALK,
    SLEEP,
    SLEEP_BABY,
    THREAT,
    ATTACK1,
    ATTACK2,
    EAT;

    public boolean isAggressive() {
        return this == THREAT || this == ATTACK1 || this == ATTACK2;
    }
}
