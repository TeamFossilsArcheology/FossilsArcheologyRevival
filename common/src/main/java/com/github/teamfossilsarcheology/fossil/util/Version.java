package com.github.teamfossilsarcheology.fossil.util;

import dev.architectury.injectables.annotations.ExpectPlatform;
import org.apache.commons.lang3.NotImplementedException;

import java.util.HashSet;
import java.util.Set;

public class Version {
    private static final Set<ReleaseType> RELEASE_TYPES = ReleaseType.parseVersion(getVersion());

    @ExpectPlatform
    public static String getVersion() {
        throw new NotImplementedException();
    }

    public static boolean isAlpha() {
        return RELEASE_TYPES.contains(ReleaseType.ALPHA);
    }

    public static boolean debugEnabled() {
        return RELEASE_TYPES.contains(ReleaseType.DEVELOP);
    }

    public enum ReleaseType {
        ALPHA, DEVELOP, RELEASE_CANDIDATE;

        public static Set<ReleaseType> parseVersion(String version) {
            Set<ReleaseType> set = new HashSet<>();
            if (version.contains("-develop")) {
                set.add(DEVELOP);
            }
            if (version.contains("-rc")) {
                set.add(RELEASE_CANDIDATE);
            }
            if (version.contains("-alpha")) {
                set.add(ALPHA);
            }
            return set;
        }
    }
}
