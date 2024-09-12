package com.fossil.fossil.fabric.mixins;

import com.fossil.fossil.client.gui.debug.DebugScreen;
import com.fossil.fossil.client.gui.debug.InstructionTab;
import com.fossil.fossil.client.gui.debug.instruction.Instruction;
import com.fossil.fossil.client.gui.debug.navigation.PathingDebug;
import com.fossil.fossil.util.Version;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.BlockHitResult;
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

    @Inject(method = "clearLevel(Lnet/minecraft/client/gui/screens/Screen;)V", at = @At(value = "HEAD"))
    public void clearDebugEntity(final CallbackInfo ci) {
        if (Version.debugEnabled()) {
            DebugScreen.entity = null;
            InstructionTab.entityListHighlight = null;
            InstructionTab.highlightInstructionEntity = null;
            InstructionTab.activeEntity = null;
        }
    }

    @Inject(method = "pickBlock", at = @At(value = "HEAD"), cancellable = true)
    public void debugCancelMiddleClick(final CallbackInfo ci) {
        if (PathingDebug.showHelpMenu) {
            PathingDebug.setPos3(PathingDebug.getHitResult((Minecraft) (Object) this));
            ci.cancel();
        }
    }

    @Inject(method = "startUseItem", at = @At(value = "HEAD"), cancellable = true)
    public void debugCancelRightClick(final CallbackInfo ci) {
        if (InstructionTab.positionActive()) {
            InstructionTab.positionMode = Instruction.Type.IDLE;
            ci.cancel();
        } else if (PathingDebug.showHelpMenu) {
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
        if (InstructionTab.positionActive()) {
            BlockHitResult hitResult = PathingDebug.getFullHitResult((Minecraft) (Object) this);
            InstructionTab.addPosition(hitResult);
            cir.setReturnValue(false);
        } else if (PathingDebug.showHelpMenu) {
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
        if (InstructionTab.positionActive() || PathingDebug.showHelpMenu) {
            ci.cancel();
        }
    }
}
