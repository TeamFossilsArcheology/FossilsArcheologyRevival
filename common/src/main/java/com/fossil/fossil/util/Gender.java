package com.fossil.fossil.util;

import java.util.Random;

public enum Gender {
    MALE,
    FEMALE;
    public static Gender random(Random random) {
        return values()[random.nextInt(2)];
    }
}
