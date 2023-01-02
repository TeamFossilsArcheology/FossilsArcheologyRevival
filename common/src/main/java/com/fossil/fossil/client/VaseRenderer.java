package com.fossil.fossil.client;

import com.fossil.fossil.block.custom_blocks.AmphoraVaseBlock;
import com.fossil.fossil.block.custom_blocks.VaseBlock;
import com.fossil.fossil.block.entity.VaseBlockEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.core.Direction;

public class VaseRenderer implements BlockEntityRenderer<VaseBlockEntity> {
    private final ModelPart vaseModel;

    public VaseRenderer(BlockEntityRendererProvider.Context context) {
        vaseModel = createBodyLayer().bakeRoot();
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        partDefinition.addOrReplaceChild("west_handle1", CubeListBuilder.create().texOffs(0, 15).mirror()
                .addBox(7, 6.5f, 7, 1, 6, 2), PartPose.rotation(0, 0, 0.37332593f));
        partDefinition.addOrReplaceChild("west_handle2", CubeListBuilder.create().texOffs(0, 33).mirror()
                .addBox(1.94f, 14.2f, 7, 1, 4, 2), PartPose.ZERO);
        partDefinition.addOrReplaceChild("west_handle3", CubeListBuilder.create().texOffs(0, 55).mirror()
                .addBox(-8.55f, 16.1f, 7, 1, 2, 2), PartPose.rotation(0, 0, -0.593412f));
        partDefinition.addOrReplaceChild("west_handle4", CubeListBuilder.create().texOffs(0, 51).mirror()
                .addBox(8, 17.3f, 7, 2, 1, 2), PartPose.rotation(0, 0, 0.26022859f));

        partDefinition.addOrReplaceChild("east_handle1", CubeListBuilder.create().texOffs(0, 15)
                .addBox(6.85f, 12.45f, 7, 1, 6, 2), PartPose.rotation(0, 0, -0.37332593f));
        partDefinition.addOrReplaceChild("east_handle2", CubeListBuilder.create().texOffs(0, 33)
                .addBox(13, 14.25f, 7, 1, 4, 2), PartPose.ZERO);
        partDefinition.addOrReplaceChild("east_handle3", CubeListBuilder.create().texOffs(0, 55)
                .addBox(20.8f, 7.25f, 7, 1, 2, 2), PartPose.rotation(0, 0, 0.593412f));
        partDefinition.addOrReplaceChild("east_handle4", CubeListBuilder.create().texOffs(0, 51)
                .addBox(5.45f, 21.45f, 7, 2, 1, 2), PartPose.rotation(0, 0, -0.26022859f));
        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    @Override
    public void render(VaseBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight,
                       int packedOverlay) {
        VaseBlock block = (VaseBlock) blockEntity.getBlockState().getBlock();
        if (block instanceof AmphoraVaseBlock) {
            poseStack.pushPose();
            if (blockEntity.getBlockState().getValue(VaseBlock.FACING).getAxis() == Direction.Axis.X) {
                poseStack.translate(0,0,1);
                poseStack.mulPose(Vector3f.YP.rotationDegrees(90));
            }
            var c = bufferSource.getBuffer(RenderType.entityCutout((block.getVariantTexture())));
            vaseModel.render(poseStack, c, packedLight, packedOverlay);
            poseStack.popPose();
        }
    }
}
