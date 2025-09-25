package com.github.teamfossilsarcheology.fossil;

import com.github.teamfossilsarcheology.fossil.util.Version;
import dev.architectury.injectables.annotations.ExpectPlatform;
import dev.architectury.platform.Platform;
import org.apache.commons.lang3.NotImplementedException;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

/**
 * For now just disables mixins used for debugging
 */
public class FossilMixinPlugin implements IMixinConfigPlugin {
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

    /**
     * Needed because {@link Platform#isModLoaded(String)} won't work on forge during mixin loading
     */
    @ExpectPlatform
    private static boolean isModLoaded(String mod) {
        throw new NotImplementedException();
    }
}
