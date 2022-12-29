package com.fossil.fossil.entity.prehistoric.base;

enum OrderType {
    STAY, FOLLOW, WANDER;

    public final OrderType next() {
        return OrderType.values()[(this.ordinal() + 1) % OrderType.values().length];
    }

}
