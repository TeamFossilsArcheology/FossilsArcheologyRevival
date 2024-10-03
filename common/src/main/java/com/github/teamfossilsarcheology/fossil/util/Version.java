package com.github.teamfossilsarcheology.fossil.util;

import dev.architectury.injectables.annotations.ExpectPlatform;
import org.apache.commons.lang3.NotImplementedException;

public class Version {
    public static final ReleaseType RELEASE_TYPE = ReleaseType.parseVersion(getVersion());

    @ExpectPlatform
    public static String getVersion() {
        throw new NotImplementedException();
    }

    public static boolean debugEnabled() {
        return RELEASE_TYPE == ReleaseType.DEVELOP;
    }

    public enum ReleaseType {
        DEVELOP, RELEASE_CANDIDATE, RELEASE;

        public static ReleaseType parseVersion(String version) {
            if (version.contains("-develop")) {
                return DEVELOP;
            } else if (version.contains("-rc")) {
                return RELEASE_CANDIDATE;
            } else {
                return RELEASE;
            }
        }
    }
}
