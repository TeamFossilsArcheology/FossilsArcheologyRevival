package com.github.teamfossilsarcheology.fossil.client.model;

import com.github.teamfossilsarcheology.fossil.Fossil;
import com.github.teamfossilsarcheology.fossil.client.renderer.entity.FailuresaurusRenderer;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class FailuresaurusTherapodModel extends FailuresaurusRenderer.FailuresaurusModel {
    public static final ResourceLocation TEXTURE = Fossil.location("textures/entity/failuresaurus/failuresaurus_theropod.png");

    private final ModelPart model = createBodyLayer().bakeRoot();

    public FailuresaurusTherapodModel() {
        super(RenderType::entityCutout);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition root = meshdefinition.getRoot();
        PartDefinition body1 = root.addOrReplaceChild("body1", CubeListBuilder.create().texOffs(85, 1).addBox(-5.5f, 0, -5, 11, 4, 10), PartPose.offset(2, 20, -2));
        PartDefinition body2 = body1.addOrReplaceChild("body2", CubeListBuilder.create().texOffs(50, 1).addBox(-4.5f, -3, -4, 9, 4, 8), PartPose.rotation(0, 0, 0.0873f));

        PartDefinition neck = body2.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(35, 10).addBox(-2, -6, -2.5f, 4, 6, 5), PartPose.offsetAndRotation(2, -1, 1, -0.1571f, 0, -0.6981f));
        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(18, 3).addBox(-2.5f, -2.5f, -5, 5, 5, 5), PartPose.offsetAndRotation(0, -3.8f, 1.5f, -0.4887f, 0, 0));

        PartDefinition snout = head.addOrReplaceChild("snout", CubeListBuilder.create().texOffs(16, 15).addBox(-1.5f, -1.5f, -5, 3, 3, 5), PartPose.offsetAndRotation(0, -0.5f, -4, 0.0524f, 0, 0));
        snout.addOrReplaceChild("snout_rtteeth", CubeListBuilder.create().texOffs(6, 20).addBox(0, 0, -5, 0, 4, 5), PartPose.offset(-1.5f, 1.5f, 0));
        snout.addOrReplaceChild("snout_ltteeth", CubeListBuilder.create().texOffs(6, 20).addBox(0, 0, -5, 0, 4, 5), PartPose.offset(1.5f, 1.5f, 0));

        PartDefinition jaw = head.addOrReplaceChild("jaw", CubeListBuilder.create().texOffs(29, 22).addBox(-1, 0, -4, 2, 1, 4), PartPose.offsetAndRotation(0, 1, -4.7f, 1.2217f, 0, 0));
        jaw.addOrReplaceChild("jaw_rtteeth", CubeListBuilder.create().texOffs(6, 30).addBox(0, -3, -4, 0, 3, 4), PartPose.offset(-1, 0, 0));
        jaw.addOrReplaceChild("jaw_ltteeth", CubeListBuilder.create().texOffs(6, 30).addBox(0, -3, -4, 0, 3, 4), PartPose.offset(1, 0, 0));

        PartDefinition tail1 = body1.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(50, 18).addBox(-1.5f, -2, 0, 3, 4, 7), PartPose.offsetAndRotation(0, 0, 3, 0.2618f, -0.7854f, 0));
        PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(67, 14).addBox(-1, -1.5f, 0, 2, 3, 6), PartPose.offsetAndRotation(0, -0.5f, 6, -0.1745f, -1.5359f, 0));
        tail2.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(79, 18).addBox(-0.5f, -1, 0, 1, 2, 7), PartPose.offsetAndRotation(0, -0.5f, 5.5f, -0.1571f, -1.2741f, 0));

        return LayerDefinition.create(meshdefinition, 128, 64);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        model.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public ResourceLocation getTexture() {
        return TEXTURE;
    }
}
