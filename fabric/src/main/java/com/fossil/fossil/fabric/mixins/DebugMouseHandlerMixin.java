package com.fossil.fossil.fabric.mixins;

import com.fossil.fossil.client.gui.debug.InstructionTab;
import com.fossil.fossil.client.gui.debug.navigation.PathingDebug;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public class DebugMouseHandlerMixin {


    @Inject(method = "onScroll", at = @At("HEAD"), cancellable = true)
    private void debugCancelScroll(final long window, final double xScroll, final double yScroll, final CallbackInfo ci) {
        if (InstructionTab.positionActive() || PathingDebug.showHelpMenu) {
            PathingDebug.pickBlockOffset += (int) yScroll;
            ci.cancel();
        }
    }
}
