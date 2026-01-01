package com.github.teamfossilsarcheology.fossil.fabric;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.util.ModConstants;
import com.github.teamfossilsarcheology.fossil.util.Version;
import net.fabricmc.loader.api.FabricLoader;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.Arrays;
import java.util.List;
import java.util.Set;

/**
 * @see com.github.teamfossilsarcheology.fossil.FossilMixinPlugin
 */
public class FossilMixinPluginImpl implements IMixinConfigPlugin {

    @Override
    public void onLoad(String mixinPackage) {
    }

    @Override
    public String getRefMapperConfig() {
        return "";
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        if (!isModLoaded(FossilMod.MOD_ID) || mixinClassName.contains("Debug") && !Version.debugEnabled()) {
            return false;
        }
        if (isModLoaded(ModConstants.MIDNIGHT_LIB) && mixinClassName.contains("MidnightConfig")) {
            int compare = compareSemanticVersions(getModVersion(ModConstants.MIDNIGHT_LIB), "1.9.0");
            if (mixinClassName.contains("Modern")) {
                // Midnightlib is >=1.9.0
                return compare >= 0;
            }
            // Midnightlib is <1.9.0
            return compare < 0;
        }
        return true;
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {

    }

    @Override
    public List<String> getMixins() {
        return List.of();
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {

    }

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {
    }

    private static int compareSemanticVersions(String version1, String version2) {
        List<Integer> version1Components = Arrays.stream(version1.split("\\."))
                .map(Integer::parseInt)
                .toList();
        List<Integer> version2Components = Arrays.stream(version2.split("\\."))
                .map(Integer::parseInt)
                .toList();

        int maxLength = Math.max(version1Components.size(), version2Components.size());

        for (int i = 0; i < maxLength; i++) {
            int v1Component = i < version1Components.size() ? version1Components.get(i) : 0;
            int v2Component = i < version2Components.size() ? version2Components.get(i) : 0;

            if (v1Component > v2Component) {
                return 1;
            } else if (v1Component < v2Component) {
                return -1;
            }
        }

        return 0;
    }

    public static boolean isModLoaded(String mod) {
        return FabricLoader.getInstance().getModContainer(mod).isPresent();
    }

    private static String getModVersion(String mod) {
        var modContainer = FabricLoader.getInstance().getModContainer(mod);
        if (modContainer.isPresent()) {
            return modContainer.get().getMetadata().getVersion().getFriendlyString();
        }
        return "";
    }
}
