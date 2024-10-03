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

public class FailuresaurusFlyingModel extends FailuresaurusRenderer.FailuresaurusModel {
    public static final ResourceLocation TEXTURE = Fossil.location("textures/entity/failuresaurus/failuresaurus_flying.png");

    private final ModelPart model = createBodyLayer().bakeRoot();

    public FailuresaurusFlyingModel() {
        super(RenderType::entityCutoutNoCull);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition root = meshdefinition.getRoot();

        PartDefinition body1 = root.addOrReplaceChild("body1", CubeListBuilder.create().texOffs(85, 1).addBox(-4.5f, 0, -5, 9, 4, 10), PartPose.offset(0, 20, 2));
        PartDefinition body2 = body1.addOrReplaceChild("body2", CubeListBuilder.create().texOffs(50, 1).addBox(-3.5f, -3, -4, 7, 4, 8), PartPose.rotation(0, 0, 0.0873f));
        PartDefinition lwing1 = body2.addOrReplaceChild("lwing1", CubeListBuilder.create().texOffs(80, 20).mirror().addBox(-10, 0, -3, 10, 1, 6).mirror(false), PartPose.offsetAndRotation(-0.5f, -2.5f, 0, 0, 0, 1.0821f));
        lwing1.addOrReplaceChild("lwing2", CubeListBuilder.create().texOffs(80, 35).mirror().addBox(-14, -0.5f, -2, 14, 1, 4).mirror(false), PartPose.offsetAndRotation(-9.5f, 0.5f, 0.5f, 0, 0, -2.5831f));
        lwing1.addOrReplaceChild("lhand", CubeListBuilder.create().texOffs(80, 45).mirror().addBox(-1, 0, -4, 1, 1, 4).mirror(false), PartPose.offsetAndRotation(-9.5f, 0, -2, 0.0873f, -0.2618f, -0.0873f));

        PartDefinition rwing1 = body2.addOrReplaceChild("rwing1", CubeListBuilder.create().texOffs(40, 20).mirror().addBox(-10, -1, -3, 10, 1, 6).mirror(false), PartPose.offsetAndRotation(0.5f, -2.5f, 0, 0, 0, 1.9548f));
        rwing1.addOrReplaceChild("rwing2", CubeListBuilder.create().texOffs(40, 35).mirror().addBox(-14, -0.5f, -2, 14, 1, 4).mirror(false), PartPose.offsetAndRotation(-9.5f, -0.5f, 0.5f, 0, 0, 2.5831f));
        rwing1.addOrReplaceChild("rhand", CubeListBuilder.create().texOffs(40, 45).mirror().addBox(-1, -1, -4, 1, 1, 4).mirror(false), PartPose.offsetAndRotation(-9.5f, 0, -2, -0.0873f, -0.2618f, 0.0873f));

        PartDefinition head = body2.addOrReplaceChild("head", CubeListBuilder.create().texOffs(10, 5).addBox(-2, -2, -5, 4, 4, 5), PartPose.offsetAndRotation(0, 1, -1, -0.4363f, 0, -0.1222f));
        PartDefinition snout = head.addOrReplaceChild("snout", CubeListBuilder.create().texOffs(24, 17).addBox(-1, -1, -4, 2, 2, 4), PartPose.offsetAndRotation(0, 0, -4.5f, 0.0698f, 0, 0));
        snout.addOrReplaceChild("lfang", CubeListBuilder.create().texOffs(20, 30).addBox(0, 0, 0, 0, 2, 1), PartPose.offsetAndRotation(-1, 1, -4, 0, 0, 0.1745f));
        snout.addOrReplaceChild("rfang", CubeListBuilder.create().texOffs(20, 30).addBox(0, 0, 0, 0, 2, 1), PartPose.offsetAndRotation(1, 1, -4, 0, 0, -0.1745f));
        head.addOrReplaceChild("Jaw", CubeListBuilder.create().texOffs(7, 22).addBox(-1, -0.5f, -4, 2, 1, 4), PartPose.offsetAndRotation(0.01f, 1.5f, -4.3f, 1.1345f, 0, 0));

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
