package com.fossil.fossil.config;

import dev.architectury.injectables.annotations.ExpectPlatform;

public class FossilConfig {

    @ExpectPlatform
    public static boolean isEnabled(String field) {
        return false;
    }

    @ExpectPlatform
    public static int getInt(String field) {
        return 0;
    }
}
