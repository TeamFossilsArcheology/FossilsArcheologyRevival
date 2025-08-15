package com.github.teamfossilsarcheology.fossil.fabric.mixin;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.config.fabric.FabricConfigFix;
import com.github.teamfossilsarcheology.fossil.config.fabric.FossilConfigImpl;
import eu.midnightdust.lib.config.MidnightConfig;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.lang.reflect.Field;

@Mixin(MidnightConfig.class)
public class MidnightConfigMixin {

    @Inject(method = "init", remap = false, at = @At(value = "INVOKE", target = "Ljava/util/List;iterator()Ljava/util/Iterator;"))
    private static void initFabricConfig(String modId, Class<?> config, CallbackInfo ci) {
        if (modId.equals(FossilMod.MOD_ID)) {
            Field[] allFields = FossilConfigImpl.class.getDeclaredFields();
            for (Field field : allFields) {
                if (field.getAnnotation(MidnightConfig.Entry.class) != null) {
                    FossilConfigImpl.MAPPED_ENTRIES.put(field.getName(), field);
                }
            }
            FabricConfigFix.fixConfig(FossilConfigImpl.MAPPED_ENTRIES);
        }
    }
}
