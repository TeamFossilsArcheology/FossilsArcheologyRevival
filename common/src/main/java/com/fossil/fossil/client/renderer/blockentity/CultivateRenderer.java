package com.fossil.fossil.client.renderer.blockentity;

import com.fossil.fossil.block.custom_blocks.CultivateBlock;
import com.fossil.fossil.block.entity.CultivateBlockEntity;
import com.fossil.fossil.client.model.EmbryoGenericModel;
import com.fossil.fossil.client.model.EmbryoPlantModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class CultivateRenderer implements BlockEntityRenderer<CultivateBlockEntity> {
    private final ModelPart modelGeneric;
    private final ModelPart modelPlant;

    public CultivateRenderer(BlockEntityRendererProvider.Context context) {
        modelGeneric = EmbryoGenericModel.createBodyLayer().bakeRoot();
        modelPlant = EmbryoPlantModel.createBodyLayer().bakeRoot();
    }

    @Override
    public void render(CultivateBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight,
                       int packedOverlay) {
        if (blockEntity.getBlockState().getValue(CultivateBlock.ACTIVE)) {
            float rot = Minecraft.getInstance().player.tickCount + partialTick;
            float bob = (float) (Math.sin((Minecraft.getInstance().player.tickCount + partialTick) * 0.03F) * 1 * 0.05F - 1 * 0.05F);
            poseStack.pushPose();
            poseStack.translate(0.5, 1.5, 0.5);
            poseStack.mulPose(Vector3f.ZP.rotationDegrees(180));
            poseStack.translate(0, 0.5F + bob, 0);
            poseStack.scale(0.5F, 0.5F, 0.5F);
            poseStack.mulPose(Vector3f.YP.rotationDegrees(rot));
            if (blockEntity.getBlockState().getValue(CultivateBlock.EMBRYO) == CultivateBlock.EmbryoType.GENERIC) {//generic
                var c = bufferSource.getBuffer(RenderType.entityCutout(EmbryoGenericModel.TEXTURE));
                this.modelGeneric.render(poseStack, c, packedLight, packedOverlay);
            } else if (blockEntity.getBlockState().getValue(CultivateBlock.EMBRYO) == CultivateBlock.EmbryoType.PLANT) {//plant
                var c = bufferSource.getBuffer(RenderType.entityCutout(EmbryoPlantModel.TEXTURE));
                this.modelPlant.render(poseStack, c, packedLight, packedOverlay);
            }
            poseStack.popPose();
        }
    }
}
