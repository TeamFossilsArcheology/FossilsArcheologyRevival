package com.github.teamfossilsarcheology.fossil.client.renderer.blockentity;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.block.custom_blocks.AnubiteStatueBlock;
import com.github.teamfossilsarcheology.fossil.block.entity.AnubiteStatueBlockEntity;
import com.github.teamfossilsarcheology.fossil.client.model.AnubiteModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.resources.ResourceLocation;

public class AnubiteStatueRenderer implements BlockEntityRenderer<AnubiteStatueBlockEntity> {
    public static final ResourceLocation TEXTURE = FossilMod.location("textures/entity/anubite_statue.png");

    private final ModelPart anubiteModel;

    public AnubiteStatueRenderer(BlockEntityRendererProvider.Context context) {
        anubiteModel = AnubiteModel.createBodyLayer().bakeRoot();
    }

    @Override
    public void render(AnubiteStatueBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight,
                       int packedOverlay) {
        poseStack.pushPose();
        poseStack.translate(0.5f, 1.5f, 0.5f);
        poseStack.mulPose(Vector3f.ZP.rotationDegrees(180));
        poseStack.mulPose(Vector3f.YP.rotationDegrees(blockEntity.getBlockState().getValue(AnubiteStatueBlock.FACING).getOpposite().toYRot()));
        var c = bufferSource.getBuffer(RenderType.entityCutoutNoCull(TEXTURE));
        if (blockEntity.getBlockState().getValue(AnubiteStatueBlock.LIT)) {
            anubiteModel.getChild("right_arm").xRot = -90;
        } else{
            anubiteModel.getChild("right_arm").xRot = 0;//TODO: Raise arm based on cooldown
        }
        anubiteModel.render(poseStack, c, packedLight, packedOverlay);
        poseStack.popPose();
    }


}
