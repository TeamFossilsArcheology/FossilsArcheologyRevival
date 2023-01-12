package com.fossil.fossil.client.renderer.blockentity;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.block.custom_blocks.AncientChestBlock;
import com.fossil.fossil.block.entity.AncientChestBlockEntity;
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
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

public class AncientChestRenderer implements BlockEntityRenderer<AncientChestBlockEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(Fossil.MOD_ID, "textures/entity/ancient_chest.png");
    private final ModelPart chestModel;

    public AncientChestRenderer(BlockEntityRendererProvider.Context context) {
        chestModel = createBodyLayer().bakeRoot();
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        partDefinition.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(0, 19)
                .addBox(1.0f, 0.0f, 1.0f, 14.0f, 10.0f, 14.0f), PartPose.ZERO);
        partDefinition.addOrReplaceChild("lid", CubeListBuilder.create().texOffs(0, 0)
                .addBox(1.0f, 0.0f, 0.0f, 14.0f, 5.0f, 14.0f), PartPose.offset(0.0f, 9.0f, 1.0f));
        partDefinition.addOrReplaceChild("lock", CubeListBuilder.create().texOffs(0, 0)
                .addBox(7.0f, -1.0f, 15.0f, 2.0f, 4.0f, 1.0f), PartPose.offset(0.0f, 8.0f, 0.0f));
        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    @Override
    public void render(AncientChestBlockEntity blockEntity, float partialTick, PoseStack poseStack, MultiBufferSource bufferSource, int packedLight,
                       int packedOverlay) {
        Direction direction = blockEntity.getBlockState().getValue(AncientChestBlock.FACING);
        poseStack.pushPose();
        poseStack.translate(0.5f, 0.5f, 0.5f);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(-direction.toYRot()));
        poseStack.translate(-0.5f, -0.5f, -0.5f);
        float f1 = blockEntity.getLidTimer();
        chestModel.getChild("lid").setRotation(-f1 * Mth.DEG_TO_RAD, 0, 0);
        var c = bufferSource.getBuffer(RenderType.entityCutout(TEXTURE));
        chestModel.render(poseStack, c, packedLight, packedOverlay);
        poseStack.popPose();
    }
}
