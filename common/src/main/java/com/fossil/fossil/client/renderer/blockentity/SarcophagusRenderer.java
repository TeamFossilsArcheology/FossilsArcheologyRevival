package com.fossil.fossil.client.renderer.blockentity;

import com.fossil.fossil.block.custom_blocks.SarcophagusBlock;
import com.fossil.fossil.block.entity.SarcophagusBlockEntity;
import com.fossil.fossil.client.model.AnuBossModel;
import com.fossil.fossil.client.model.SarcophagusModel;
import com.fossil.fossil.item.ModItems;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.math.Vector3f;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.block.model.ItemTransforms;
import net.minecraft.client.renderer.blockentity.BlockEntityRenderer;
import net.minecraft.client.renderer.blockentity.BlockEntityRendererProvider;
import net.minecraft.client.renderer.entity.ItemRenderer;
import net.minecraft.core.Direction;
import net.minecraft.util.Mth;
import net.minecraft.world.item.ItemStack;

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
        Direction direction = blockEntity.getBlockState().getValue(SarcophagusBlock.FACING);
        poseStack.pushPose();
        poseStack.translate(0f, 1f, 1f);
        poseStack.scale(1f, -1f, -1f);
        poseStack.translate(0.5f, -0.5f, 0.5f);
        poseStack.mulPose(Vector3f.YP.rotationDegrees(direction.toYRot()));

        float f1 = blockEntity.getDoorTimer();
        sarcophagusModel.getChild("hinge").setRotation(0, -f1 * Mth.DEG_TO_RAD, 0);

        var vertexConsumer = bufferSource.getBuffer(RenderType.entityCutout(SarcophagusModel.TEXTURE));
        sarcophagusModel.render(poseStack, vertexConsumer, packedLight, packedOverlay);
        if (blockEntity.getState() == SarcophagusBlockEntity.STATE_OPENING) {
            poseStack.translate(0, 0, 0.2);
            vertexConsumer = bufferSource.getBuffer(RenderType.entityCutout(AnuBossModel.TEXTURE));
            anuModel.render(poseStack, vertexConsumer, packedLight, packedOverlay);
        }
        poseStack.popPose();
        if (blockEntity.getState() == SarcophagusBlockEntity.STATE_UNLOCKED) {
            poseStack.pushPose();
            if (direction == Direction.NORTH) {
                poseStack.translate(0.5f, 1.2f, 0);
            } else if (direction == Direction.WEST) {
                poseStack.translate(0, 1.2f, 0.5f);
                poseStack.mulPose(Vector3f.YP.rotationDegrees(90));
            } else if (direction == Direction.SOUTH) {
                poseStack.translate(0.5f, 1.2f, 1);
                poseStack.mulPose(Vector3f.YP.rotationDegrees(180));
            } else if (direction == Direction.EAST) {
                poseStack.translate(1, 1.2f, 0.5f);
                poseStack.mulPose(Vector3f.YP.rotationDegrees(-90));
            }
            poseStack.scale(1, 1, 2);
            Minecraft mc = Minecraft.getInstance();
            ItemRenderer itemRenderer = mc.getItemRenderer();
            itemRenderer.renderStatic(mc.player, new ItemStack(ModItems.SCARAB_GEM.get()), ItemTransforms.TransformType.FIXED, false, poseStack,
                    bufferSource, mc.level, packedLight, packedOverlay, 0);
            poseStack.popPose();
        }
    }


}
