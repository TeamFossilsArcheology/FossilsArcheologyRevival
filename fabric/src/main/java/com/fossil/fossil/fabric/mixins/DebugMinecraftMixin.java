package com.fossil.fossil.fabric.mixins;

import com.fossil.fossil.client.gui.debug.navigation.PathingDebug;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Minecraft.class)
public class DebugMinecraftMixin {

    @Shadow
    private int rightClickDelay;

    @Inject(method = "pickBlock", at = @At(value = "HEAD"), cancellable = true)
    public void debugCancelMiddleClick(final CallbackInfo ci) {
        if (PathingDebug.showHelpMenu) {
            PathingDebug.setPos3(PathingDebug.getHitResult((Minecraft) (Object) this));
            ci.cancel();
        }
    }

    @Inject(method = "startUseItem", at = @At(value = "HEAD"), cancellable = true)
    public void debugCancelRightClick(final CallbackInfo ci) {
        if (PathingDebug.showHelpMenu) {
            BlockPos hitResult = PathingDebug.getBlockHitResult((Minecraft) (Object) this);
            if (PathingDebug.addNodeToPathMode) {
                PathingDebug.removeFromPath(hitResult);
            } else {
                PathingDebug.setPos2(hitResult);
            }
            this.rightClickDelay = 4;
            ci.cancel();
        }
    }

    @Inject(method = "startAttack", at = @At(value = "HEAD"), cancellable = true)
    public void debugCancelLeftClick(CallbackInfoReturnable<Boolean> cir) {
        if (PathingDebug.showHelpMenu) {
            BlockPos hitResult = PathingDebug.getBlockHitResult((Minecraft) (Object) this);
            if (PathingDebug.addNodeToPathMode) {
                PathingDebug.addToPath(hitResult);
            } else {
                PathingDebug.setPos1(hitResult);
            }
            cir.setReturnValue(false);
        }
    }

    @Inject(method = "continueAttack", at = @At(value = "HEAD"), cancellable = true)
    public void debugCancelLeftClick(boolean leftClick, CallbackInfo ci) {
        if (PathingDebug.showHelpMenu) {
            ci.cancel();
        }
    }
}
