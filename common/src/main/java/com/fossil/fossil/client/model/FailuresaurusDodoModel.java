package com.fossil.fossil.client.model;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.client.renderer.entity.FailuresaurusRenderer;
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

public class FailuresaurusDodoModel extends FailuresaurusRenderer.FailuresaurusModel {
    public static final ResourceLocation TEXTURE = new ResourceLocation(Fossil.MOD_ID, "textures/entity/failuresaurus/failuresaurus_dodo.png");

    private final ModelPart model = createBodyLayer().bakeRoot();

    public FailuresaurusDodoModel() {
        super(RenderType::entityCutout);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition root = meshdefinition.getRoot();

        PartDefinition body1 = root.addOrReplaceChild("body1", CubeListBuilder.create().texOffs(85, 1).addBox(-5, 0, -5, 10, 4, 10), PartPose.offset(0, 20, 2.5f));
        PartDefinition body2 = body1.addOrReplaceChild("body2", CubeListBuilder.create().texOffs(50, 1).addBox(-4, -3, -4, 8, 4, 8), PartPose.rotation( 0, 0, 0.0873f));
        PartDefinition lhead = body2.addOrReplaceChild("lhead", CubeListBuilder.create().texOffs(100, 20).addBox(-2.5f, -2, -4, 5, 4, 4), PartPose.offsetAndRotation(-2, -1, -1.5f, -0.1745f, 0.5236f, 0));
        lhead.addOrReplaceChild("lsnout", CubeListBuilder.create().texOffs(100, 30).addBox(-1.5f, -1, -6, 3, 2, 6), PartPose.offsetAndRotation(0, -0.5f, -1, -0.0873f, 0, 0));
        lhead.addOrReplaceChild("ljaw", CubeListBuilder.create().texOffs(100, 40).addBox(-1.5f, -0.5f, -6, 3, 1, 6), PartPose.offsetAndRotation(0, 1.5f, -3, 0.2618f, 0, 0));

        PartDefinition rhead = body2.addOrReplaceChild("rhead", CubeListBuilder.create().texOffs(40, 20).addBox(-2.5f, -2, -4, 5, 4, 4), PartPose.offsetAndRotation(2, -1, -1.5f, -0.384f, -0.5236f, 0));
        rhead.addOrReplaceChild("rsnout", CubeListBuilder.create().texOffs(40, 30).addBox(-1.5f, -1, -6, 3, 2, 6), PartPose.offsetAndRotation(0, 0, -3, -0.4363f, 0, 0));
        rhead.addOrReplaceChild("rjaw", CubeListBuilder.create().texOffs(40, 40).addBox(-1.5f, -0.5f, -6, 3, 1, 6), PartPose.offsetAndRotation(0, 1.5f, -3, 0.5061f, 0, 0));

        PartDefinition neck = body2.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(60, 50).addBox(-2, -7, -1.5f, 4, 7, 3), PartPose.offsetAndRotation(0, -2, 1, -0.4363f, 0.3491f, 0));
        PartDefinition thead = neck.addOrReplaceChild("thead", CubeListBuilder.create().texOffs(60, 20).addBox(-2.5f, -2, -4, 5, 4, 4), PartPose.offsetAndRotation(0, -6, 0, 0.2618f, 0, 0));
        thead.addOrReplaceChild("tsnout", CubeListBuilder.create().texOffs(60, 30).addBox(-1.5f, -1, -6, 3, 2, 6), PartPose.offset(0, 0, -3));
        thead.addOrReplaceChild("tjaw", CubeListBuilder.create().texOffs(60, 40).addBox(-1.5f, -0.5f, -6, 3, 1, 6), PartPose.offsetAndRotation(0, 1.5f, -3, 0.3491f, 0, 0));

        PartDefinition bhead = body1.addOrReplaceChild("bhead", CubeListBuilder.create().texOffs(80, 20).addBox(-2.5f, -2, -4, 5, 4, 4), PartPose.offsetAndRotation(0, 1, -1.5f, 0, -0.0524f, 0));
        bhead.addOrReplaceChild("bsnout", CubeListBuilder.create().texOffs(80, 30).addBox(-1.5f, -1, -6, 3, 2, 6), PartPose.offsetAndRotation(0, 0, -3, -0.0873f, 0, 0));
        bhead.addOrReplaceChild("bjaw", CubeListBuilder.create().texOffs(80, 40).addBox(-1.5f, -0.5f, -6, 3, 1, 6), PartPose.offsetAndRotation(0, 1.5f, -1, 0.1396f, 0, 0));

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
