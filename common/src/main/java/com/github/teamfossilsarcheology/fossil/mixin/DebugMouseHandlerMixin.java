package com.github.teamfossilsarcheology.fossil.mixin;

import com.github.teamfossilsarcheology.fossil.client.gui.debug.InstructionTab;
import com.github.teamfossilsarcheology.fossil.client.gui.debug.navigation.PathingDebug;
import com.github.teamfossilsarcheology.fossil.entity.util.Util;
import com.github.teamfossilsarcheology.fossil.util.Version;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MouseHandler.class)
public class DebugMouseHandlerMixin {

    @Inject(method = "onScroll", at = @At("HEAD"), cancellable = true)
    private void debugCancelScroll(final long window, final double xScroll, final double yScroll, final CallbackInfo ci) {
        if (!Version.debugEnabled()) {
            return;
        }
        if (PathingDebug.showHelpMenu || InstructionTab.positionActive()) {
            PathingDebug.pickBlockOffset += (int) yScroll;
            ci.cancel();
        }
        if (InstructionTab.positionActive()) {
            InstructionTab.teleportRotation = (int) Util.clampTo360(InstructionTab.teleportRotation + yScroll * 45);
            ci.cancel();
        }
    }
}
