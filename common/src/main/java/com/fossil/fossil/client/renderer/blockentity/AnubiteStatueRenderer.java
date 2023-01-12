package com.fossil.fossil.client.renderer.blockentity;

import com.fossil.fossil.block.custom_blocks.AnubiteStatueBlock;
import com.fossil.fossil.block.entity.AnubiteStatueBlockEntity;
import com.fossil.fossil.client.model.AnubiteStatueModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class AnubiteStatueRenderer implements BlockEntityRenderer<AnubiteStatueBlockEntity> {
    private final ModelPart anubiteModel;

    public AnubiteStatueRenderer(BlockEntityRendererProvider.Context context) {
        anubiteModel = AnubiteStatueModel.createBodyLayer().bakeRoot();
    }

    @Override
    public void render(AnubiteStatueBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight,
                       int packedOverlay) {
        poseStack.pushPose();
        poseStack.translate(0.5f, 1.5f, 0.5f);
        poseStack.mulPose(Vector3f.ZP.rotationDegrees(180));
        poseStack.mulPose(Vector3f.YP.rotationDegrees(blockEntity.getBlockState().getValue(AnubiteStatueBlock.FACING).toYRot() + 180));
        var c = bufferSource.getBuffer(RenderType.entityCutoutNoCull(AnubiteStatueModel.TEXTURE));
        anubiteModel.render(poseStack, c, packedLight, packedOverlay);
        poseStack.popPose();
    }


}
