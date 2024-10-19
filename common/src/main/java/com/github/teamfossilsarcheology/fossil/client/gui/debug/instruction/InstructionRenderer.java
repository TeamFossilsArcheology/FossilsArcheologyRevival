package com.github.teamfossilsarcheology.fossil.client.gui.debug.instruction;

import com.github.teamfossilsarcheology.fossil.client.gui.debug.InstructionTab;
import com.github.teamfossilsarcheology.fossil.client.gui.debug.navigation.PathingDebug;
import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.Prehistoric;
import com.mojang.blaze3d.vertex.PoseStack;
import it.unimi.dsi.fastutil.Pair;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.core.BlockPos;
import net.minecraft.util.Mth;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.core.util.Color;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class InstructionRenderer {
    static Map<BlockPos, Integer> countAtPos = new Object2IntOpenHashMap<>();
    static List<Pair<Vec3, Instruction>> texts = new ArrayList<>();

    public static void render(PoseStack poseStack, MultiBufferSource buffer, float partialTicks, long finishNanoTime, Frustum frustum) {
        Minecraft mc = Minecraft.getInstance();
        if (InstructionTab.activeEntity != null) {
            List<Instruction> instructions = InstructionTab.INSTRUCTIONS.get(InstructionTab.activeEntity.getUUID()).instructions();
            countAtPos.clear();
            texts.clear();
            BlockPos currentPos = InstructionTab.activeEntity.blockPosition();
            for (int i = 0; i < instructions.size(); i++) {
                Instruction instruction = instructions.get(i);
                if (instruction instanceof Instruction.MoveTo moveTo) {
                    addPosition(poseStack, buffer, instruction, moveTo.target, Color.WHITE);
                    currentPos = moveTo.target;
                } else if (instruction instanceof Instruction.TeleportTo teleportTo) {
                    if (frustum.isVisible(new AABB(teleportTo.target))) {
                        addPosition(poseStack, buffer, instruction, teleportTo.target, Color.PINK);
                        InstructionRenderUtil.renderArrow(poseStack, Vec3.atBottomCenterOf(teleportTo.target).add(0, 1, 0), Color.ofRGBA(1, 1, 0, 0.5f), -(teleportTo.rotation - 180));
                        currentPos = teleportTo.target;
                    }
                } else if (instruction instanceof Instruction.AttachTo attachTo) {
                    addPosition(poseStack, buffer, instruction, attachTo.target, Color.GREEN);
                    Vec3 pos = attachTo.location;
                    LevelRenderer.renderLineBox(poseStack, buffer.getBuffer(RenderType.LINES), new AABB(pos.x - 0.05, pos.y, pos.z - 0.05, pos.x + 0.05, pos.y + 0.05, pos.z + 0.05), 1, 0, 1, 1);
                    currentPos = attachTo.target;
                } else if (instruction instanceof Instruction.Attack attack) {
                } else if (instruction instanceof Instruction.PlayAnim playAnim) {
                } else if (instruction instanceof Instruction.Idle idle) {
                    int stack = countAtPos.compute(currentPos, (blockPos, count) -> count == null ? 1 : count + 1);
                    texts.add(Pair.of(Vec3.atCenterOf(currentPos).add(0, 0.5 + stack * 0.2, 0), instruction));
                }
            }
            InstructionRenderUtil.renderTextBatch(poseStack, mc, texts);
            if (InstructionTab.highlightInstruction instanceof Instruction.MoveTo moveTo) {
                addPosition(poseStack, buffer, moveTo, moveTo.target, Color.RED);
            }
            if (InstructionTab.activeEntity != null && frustum.isVisible(InstructionTab.activeEntity.getBoundingBoxForCulling())) {
                Prehistoric entity = InstructionTab.activeEntity;
                double lerpX = Mth.lerp(partialTicks, entity.xOld, entity.getX());
                double lerpY = Mth.lerp(partialTicks, entity.yOld, entity.getY());
                double lerpZ = Mth.lerp(partialTicks, entity.zOld, entity.getZ());
                Vec3 pos = new Vec3(lerpX, lerpY + entity.getBbHeight() + 1, lerpZ);
                InstructionRenderUtil.renderDownArrow(poseStack, pos, Color.ofRGBA(1, 1, 0, 0.5f), Math.max(1, entity.getBbWidth()), finishNanoTime);
            }
        }
        if (InstructionTab.positionMode != Instruction.Type.IDLE) {
            InstructionRenderUtil.renderWholeBox(poseStack, PathingDebug.getBlockHitResult(mc), Color.ofRGBA(1, 0, 0, 0.5f), finishNanoTime);
            if (InstructionTab.positionMode == Instruction.Type.TELEPORT_TO) {
                InstructionRenderUtil.renderArrow(poseStack, Vec3.atBottomCenterOf(PathingDebug.getBlockHitResult(mc)).add(0, 1, 0), Color.ofRGBA(1, 0, 1, 0.5f), InstructionTab.teleportRotation);
            }
        }
    }

    private static void addPosition(PoseStack poseStack, MultiBufferSource buffer, Instruction instruction, BlockPos target, Color color) {
        LevelRenderer.renderLineBox(poseStack, buffer.getBuffer(RenderType.LINES), new AABB(target), color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
        int stack = countAtPos.compute(target, (blockPos, count) -> count == null ? 1 : count + 1);
        texts.add(Pair.of(Vec3.atCenterOf(target).add(0, 0.5 + stack * 0.2, 0), instruction));
    }
}
