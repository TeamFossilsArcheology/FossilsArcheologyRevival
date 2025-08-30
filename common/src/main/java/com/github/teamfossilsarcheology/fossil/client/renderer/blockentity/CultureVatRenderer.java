package com.github.teamfossilsarcheology.fossil.client.renderer.blockentity;

import com.github.teamfossilsarcheology.fossil.block.custom_blocks.CultureVatBlock;
import com.github.teamfossilsarcheology.fossil.block.entity.CultureVatBlockEntity;
import com.github.teamfossilsarcheology.fossil.client.model.EmbryoGenericModel;
import com.github.teamfossilsarcheology.fossil.client.model.EmbryoPlantModel;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Axis;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;

public class CultureVatRenderer implements BlockEntityRenderer<CultureVatBlockEntity> {
    private final ModelPart modelGeneric;
    private final ModelPart modelPlant;

    public CultureVatRenderer(BlockEntityRendererProvider.Context context) {
        modelGeneric = EmbryoGenericModel.createBodyLayer().bakeRoot();
        modelPlant = EmbryoPlantModel.createBodyLayer().bakeRoot();
    }

    @Override
    public void render(CultureVatBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight,
                       int packedOverlay) {
        if (blockEntity.getBlockState().getValue(CultureVatBlock.ACTIVE)) {
            float rot = Minecraft.getInstance().player.tickCount + partialTick;
            float bob = (float) (Math.sin((Minecraft.getInstance().player.tickCount + partialTick) * 0.03F) * 1 * 0.05F - 1 * 0.05F);
            poseStack.pushPose();
            poseStack.translate(0.5, 1.5, 0.5);
            poseStack.mulPose(Axis.ZP.rotationDegrees(180));
            poseStack.translate(0, 0.5F + bob, 0);
            poseStack.scale(0.5F, 0.5F, 0.5F);
            poseStack.mulPose(Axis.YP.rotationDegrees(rot));
            CultureVatBlock.EmbryoType embryoType = blockEntity.getBlockState().getValue(CultureVatBlock.EMBRYO);
            if (embryoType == CultureVatBlock.EmbryoType.GENERIC) {
                var c = bufferSource.getBuffer(RenderType.entityCutout(EmbryoGenericModel.TEXTURE_GENERIC));
                this.modelGeneric.render(poseStack, c, packedLight, packedOverlay);
            } else if (embryoType == CultureVatBlock.EmbryoType.PLANT || embryoType == CultureVatBlock.EmbryoType.TREE) {
                var c = bufferSource.getBuffer(RenderType.entityCutout(EmbryoPlantModel.TEXTURE));
                poseStack.translate(0, 0.3, 0);
                this.modelPlant.render(poseStack, c, packedLight, packedOverlay);
            } else if (embryoType == CultureVatBlock.EmbryoType.INSECT) {
                var c = bufferSource.getBuffer(RenderType.entityCutout(EmbryoGenericModel.TEXTURE_INSECT));
                this.modelGeneric.render(poseStack, c, packedLight, packedOverlay);
            } else if (embryoType == CultureVatBlock.EmbryoType.LIMBLESS) {
                var c = bufferSource.getBuffer(RenderType.entityCutout(EmbryoGenericModel.TEXTURE_LIMBLESS));
                this.modelGeneric.render(poseStack, c, packedLight, packedOverlay);
            }
            poseStack.popPose();
        }
    }
}
