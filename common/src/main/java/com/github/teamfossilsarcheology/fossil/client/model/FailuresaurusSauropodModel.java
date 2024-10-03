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

public class FailuresaurusSauropodModel extends FailuresaurusRenderer.FailuresaurusModel {
    public static final ResourceLocation TEXTURE = Fossil.location("textures/entity/failuresaurus/failuresaurus_sauropod.png");

    private final ModelPart model = createBodyLayer().bakeRoot();

    public FailuresaurusSauropodModel() {
        super(RenderType::entityCutoutNoCull);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition root = meshdefinition.getRoot();
        
        PartDefinition body1 = root.addOrReplaceChild("body1", CubeListBuilder.create().texOffs(85, 1).addBox(-4.5f, 0, -4.5f, 9, 4, 9), PartPose.offset(0, 20, 3));
        PartDefinition body2 = body1.addOrReplaceChild("body2", CubeListBuilder.create().texOffs(50, 1).addBox(-3.5f, -3, -3.5f, 7, 4, 7), PartPose.rotation(0, 0, 0.0873f));
        PartDefinition neck = body2.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(30, 10).addBox(-1.5f, -2, -10, 3, 2, 10), PartPose.offsetAndRotation(0, -1, 2.3f, -0.6109f, 0, -0.1745f));
        PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(21, 8).addBox(-2, -2.5f, -4, 4, 3, 4), PartPose.offsetAndRotation(0, 0.2f, -8.4f, -0.6981f, 0, 0));
        head.addOrReplaceChild("snout", CubeListBuilder.create().texOffs(30, 1).addBox(-1.5f, -1.5f, -3, 3, 3, 3), PartPose.offsetAndRotation(0, -0.9f, -3.5f, 0.1396f, 0, 0));
        head.addOrReplaceChild("lspines", CubeListBuilder.create().texOffs(16, 0).addBox(0, 0, -4, 0, 2, 4), PartPose.offset(-2, 0.5f, 0));
        head.addOrReplaceChild("rspines", CubeListBuilder.create().texOffs(16, 0).addBox(0, 0, -4, 0, 2, 4), PartPose.offset(2, 0.5f, 0));
        neck.addOrReplaceChild("lspines2", CubeListBuilder.create().texOffs(28, 18).addBox(0, 0, -10, 0, 2, 10), PartPose.offset(-1.5f, 0, 0));
        neck.addOrReplaceChild("rspines2", CubeListBuilder.create().texOffs(28, 18).addBox(0, 0, -10, 0, 2, 10), PartPose.offset(1.5f, 0, 0));

        PartDefinition rightthigh = root.addOrReplaceChild("rightthigh", CubeListBuilder.create().texOffs(60, 20).mirror().addBox(-1.5f, -1.5f, -6, 3, 3, 6).mirror(false), PartPose.offsetAndRotation(2.5f, 21.5f, 0.2f, -0.7854f, -0.5236f, 0));
        rightthigh.addOrReplaceChild("rightleg", CubeListBuilder.create().texOffs(80, 20).mirror().addBox(-1, -1, 0, 2, 2, 6).mirror(false), PartPose.offsetAndRotation(0, 0, -4.9f, -1.309f, 0, 0));

        PartDefinition leftthigh = root.addOrReplaceChild("leftthigh", CubeListBuilder.create().texOffs(60, 20).addBox(-1.5f, -1.5f, -6, 3, 3, 6), PartPose.offsetAndRotation(-2.5f, 21.5f, 0.2f, -0.7854f, 0.5236f, 0));
        leftthigh.addOrReplaceChild("leftleg", CubeListBuilder.create().texOffs(80, 20).addBox(-1, -1, 0, 2, 2, 6), PartPose.offsetAndRotation(0, 0, -4.9f, -1.309f, 0, 0));

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
