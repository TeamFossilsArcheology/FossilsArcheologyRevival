package com.github.teamfossilsarcheology.fossil.client.renderer.blockentity;

import com.github.teamfossilsarcheology.fossil.block.custom_blocks.SarcophagusBlock;
import com.github.teamfossilsarcheology.fossil.block.entity.SarcophagusBlockEntity;
import com.github.teamfossilsarcheology.fossil.client.model.AnuBossModel;
import com.github.teamfossilsarcheology.fossil.client.model.block.SarcophagusModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;

public class SarcophagusRenderer implements BlockEntityRenderer<SarcophagusBlockEntity> {
    private final ModelPart sarcophagusModel;
    private final ModelPart anuModel;

    public SarcophagusRenderer(BlockEntityRendererProvider.Context context) {
        sarcophagusModel = SarcophagusModel.createBodyLayer().bakeRoot();
        anuModel = AnuBossModel.createBodyLayer().bakeRoot();
        anuModel.getChild("left_wing_1").visible = false;
        anuModel.getChild("right_wing_1").visible = false;
    }

    @Override
    public void render(SarcophagusBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight,
                       int packedOverlay) {
        if (blockEntity.getBlockState().getValue(SarcophagusBlock.LAYER) == 0) {
            Direction direction = blockEntity.getBlockState().getValue(SarcophagusBlock.FACING);
            poseStack.pushPose();
            poseStack.translate(0f, 1f, 1f);
            poseStack.scale(1f, -1f, -1f);
            poseStack.translate(0.5f, -0.5f, 0.5f);
            poseStack.mulPose(Axis.YP.rotationDegrees(direction.toYRot()));

            sarcophagusModel.getChild("root").getChild("front").setRotation(0, blockEntity.getDoorTimer() * Mth.DEG_TO_RAD, 0);

            VertexConsumer vertexConsumer;
            if (blockEntity.getState() == SarcophagusBlockEntity.STATE_UNLOCKED) {
                vertexConsumer = bufferSource.getBuffer(RenderType.entityCutout(SarcophagusModel.AWAKENED));
            } else {
                vertexConsumer = bufferSource.getBuffer(RenderType.entityCutout(SarcophagusModel.UNAWAKENED));
            }
            sarcophagusModel.render(poseStack, vertexConsumer, packedLight, packedOverlay);
            if (blockEntity.getState() == SarcophagusBlockEntity.STATE_OPENING) {
                poseStack.translate(0, -0.1, 0.02);
                vertexConsumer = bufferSource.getBuffer(RenderType.entityCutout(AnuBossModel.TEXTURE));
                poseStack.pushPose();
                poseStack.translate(0, 0, 0.01);
                poseStack.scale(0.85f, 1, 1);
                anuModel.getChild("left_arm").render(poseStack, vertexConsumer, packedLight, packedOverlay);
                anuModel.getChild("right_arm").render(poseStack, vertexConsumer, packedLight, packedOverlay);
                poseStack.popPose();
                anuModel.getChild("left_arm").visible = false;
                anuModel.getChild("right_arm").visible = false;
                anuModel.render(poseStack, vertexConsumer, packedLight, packedOverlay);
                anuModel.getChild("left_arm").visible = true;
                anuModel.getChild("right_arm").visible = true;
            }
            poseStack.popPose();
        }
    }


}
