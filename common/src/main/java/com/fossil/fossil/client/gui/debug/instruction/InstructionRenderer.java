package com.fossil.fossil.client.gui.debug.instruction;

import com.fossil.fossil.client.gui.debug.InstructionTab;
import com.fossil.fossil.client.gui.debug.navigation.PathingDebug;
import com.mojang.blaze3d.vertex.PoseStack;
import it.unimi.dsi.fastutil.Pair;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.core.BlockPos;
import net.minecraft.world.phys.AABB;
import net.minecraft.world.phys.Vec3;
import software.bernie.geckolib3.core.util.Color;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class InstructionRenderer {
    static Map<BlockPos, Integer> countAtPos = new HashMap<>();
    static List<Pair<Vec3, Instruction>> texts = new ArrayList<>();

    public static void render(PoseStack poseStack, MultiBufferSource buffer, float partialTicks, long finishNanoTime) {
        Minecraft mc = Minecraft.getInstance();
        if (InstructionTab.activeEntity != null) {
            List<Instruction> instructions = InstructionTab.INSTRUCTIONS.get(InstructionTab.activeEntity.getId());
            countAtPos.clear();
            texts.clear();
            BlockPos currentPos = InstructionTab.activeEntity.blockPosition();
            for (int i = 0; i < instructions.size(); i++) {
                Instruction instruction = instructions.get(i);
                if (instruction instanceof Instruction.MoveTo moveTo) {
                    addPosition(poseStack, buffer, instruction, moveTo.target, Color.WHITE);
                    currentPos = moveTo.target;
                } else if (instruction instanceof Instruction.TeleportTo teleportTo) {
                    //TODO: Render bounding box
                    addPosition(poseStack, buffer, instruction, teleportTo.target, Color.PINK);
                    currentPos = teleportTo.target;
                } else if (instruction instanceof Instruction.Attack attack) {
                } else if (instruction instanceof Instruction.PlayAnim playAnim) {
                } else if (instruction instanceof Instruction.Idle idle) {
                    int stack = countAtPos.compute(currentPos, (blockPos, count) -> count == null ? 1 : count + 1);
                    texts.add(Pair.of(Vec3.atCenterOf(currentPos).add(0, (stack - 1) * 0.2, 0), instruction));
                }
            }
            InstructionRenderUtil.renderTextBatch(poseStack, mc, texts);
        }
        if (InstructionTab.positionMode != Instruction.Type.IDLE) {
            InstructionRenderUtil.renderWholeBox(poseStack, PathingDebug.getBlockHitResult(mc), Color.ofRGBA(1, 0, 0, 0.5f), finishNanoTime);
        }
    }

    private static void addPosition(PoseStack poseStack, MultiBufferSource buffer, Instruction instruction, BlockPos target, Color color) {
        LevelRenderer.renderLineBox(poseStack, buffer.getBuffer(RenderType.LINES), new AABB(target), color.getRed() / 255f, color.getGreen() / 255f, color.getBlue() / 255f, color.getAlpha() / 255f);
        int stack = countAtPos.compute(target, (blockPos, count) -> count == null ? 1 : count + 1);
        texts.add(Pair.of(Vec3.atCenterOf(target).add(0, (stack - 1) * 0.2, 0), instruction));
    }
}
