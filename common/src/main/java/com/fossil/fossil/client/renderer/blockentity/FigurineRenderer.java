package com.fossil.fossil.client.renderer.blockentity;

import com.fossil.fossil.block.custom_blocks.*;
import com.fossil.fossil.block.entity.FigurineBlockEntity;
import com.fossil.fossil.client.model.*;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.core.Direction;

public class FigurineRenderer implements BlockEntityRenderer<FigurineBlockEntity> {
    private final ModelPart endermanModel;
    private final ModelPart piglinModel;
    private final ModelPart skeletonModel;
    private final ModelPart steveModel;
    private final ModelPart zombieModel;

    public FigurineRenderer() {
        endermanModel = FigurineEndermanModel.createBodyLayer().bakeRoot();
        piglinModel = FigurinePiglinModel.createBodyLayer().bakeRoot();
        skeletonModel = FigurineSkeletonModel.createBodyLayer().bakeRoot();
        steveModel = FigurineSteveModel.createBodyLayer().bakeRoot();
        zombieModel = FigurineZombieModel.createBodyLayer().bakeRoot();
    }
    @Override
    public void render(FigurineBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight, int packedOverlay) {
        poseStack.pushPose();
        poseStack.scale(-1, -1, 1);
        poseStack.translate(-0.5, -1.5, 0.5);
        Direction direction = blockEntity.getBlockState().getValue(FigurineBlock.FACING);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(direction.getOpposite().toYRot()));
        FigurineBlock figurineBlock = (FigurineBlock) blockEntity.getBlockState().getBlock();
        if (figurineBlock instanceof FigurineSkeletonBlock) {
            var consumer = bufferSource.getBuffer(RenderType.entityTranslucent(figurineBlock.getTexture()));
            skeletonModel.render(poseStack, consumer, packedLight, packedOverlay);
        } else {
            var consumer = bufferSource.getBuffer(RenderType.entityCutout(figurineBlock.getTexture()));
            if (figurineBlock instanceof FigurineEndermanBlock) {
                endermanModel.render(poseStack, consumer, packedLight, packedOverlay);
            } else if (figurineBlock instanceof FigurinePiglinBlock) {
                piglinModel.render(poseStack, consumer, packedLight, packedOverlay);
            } else if (figurineBlock instanceof FigurineSteveBlock) {
                steveModel.render(poseStack, consumer, packedLight, packedOverlay);
            } else if (figurineBlock instanceof FigurineZombieBlock) {
                zombieModel.render(poseStack, consumer, packedLight, packedOverlay);
            }
        }
        poseStack.popPose();
    }
}
