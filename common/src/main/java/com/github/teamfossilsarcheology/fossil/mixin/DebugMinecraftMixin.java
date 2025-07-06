package com.github.teamfossilsarcheology.fossil.mixin;

import com.github.teamfossilsarcheology.fossil.client.gui.debug.DebugScreen;
import com.github.teamfossilsarcheology.fossil.client.gui.debug.InstructionTab;
import com.github.teamfossilsarcheology.fossil.client.gui.debug.instruction.Instruction;
import com.github.teamfossilsarcheology.fossil.client.gui.debug.instruction.InstructionRenderer;
import com.github.teamfossilsarcheology.fossil.client.gui.debug.navigation.PathingDebug;
import com.github.teamfossilsarcheology.fossil.util.Version;
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
        if (!Version.debugEnabled()) {
            return;
        }
        DebugScreen.entity = null;
        InstructionTab.entityListHighlight = null;
        InstructionTab.highlightInstructionEntity = null;
        InstructionTab.activeEntity = null;
    }

    @Inject(method = "pickBlock", at = @At(value = "HEAD"), cancellable = true)
    public void debugCancelMiddleClick(final CallbackInfo ci) {
        if (!Version.debugEnabled()) {
            return;
        }
        if (PathingDebug.showHelpMenu) {
            PathingDebug.setPos3(PathingDebug.getHitResult((Minecraft) (Object) this));
            ci.cancel();
        }
    }

    @Inject(method = "startUseItem", at = @At(value = "HEAD"), cancellable = true)
    public void debugCancelRightClick(final CallbackInfo ci) {
        if (!Version.debugEnabled()) {
            return;
        }
        if (DebugScreen.rulerMode > 0) {
            DebugScreen.rulerMode = 0;
            InstructionRenderer.rulerEndPos = PathingDebug.getHitResult((Minecraft) (Object) this);
            this.rightClickDelay = 4;
            ci.cancel();
        } else if (InstructionTab.positionActive()) {
            InstructionTab.positionMode = Instruction.Type.IDLE;
            this.rightClickDelay = 4;
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
        if (!Version.debugEnabled()) {
            return;
        }
        if (DebugScreen.rulerMode > 0) {
            DebugScreen.rulerMode = 0;
            InstructionRenderer.rulerStartPos = PathingDebug.getHitResult((Minecraft) (Object) this);
            cir.setReturnValue(false);
        } else if (InstructionTab.positionMode == Instruction.Type.FLY_TO) {
            BlockPos blockPos = PathingDebug.getAirHitResult((Minecraft) (Object) this);
            InstructionTab.addFlyPosition(blockPos);
            cir.setReturnValue(false);
        } else if (InstructionTab.positionActive()) {
            BlockHitResult hitResult = PathingDebug.getOffsetHitResult((Minecraft) (Object) this);
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
        if (!Version.debugEnabled()) {
            return;
        }
        if (InstructionTab.positionActive() || PathingDebug.showHelpMenu || DebugScreen.rulerMode > 0) {
            ci.cancel();
        }
    }
}
