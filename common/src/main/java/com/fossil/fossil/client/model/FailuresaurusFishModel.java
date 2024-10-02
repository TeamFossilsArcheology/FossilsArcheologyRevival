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

public class FailuresaurusFishModel extends FailuresaurusRenderer.FailuresaurusModel {
    public static final ResourceLocation TEXTURE = Fossil.location("textures/entity/failuresaurus/failuresaurus_fish.png");

    private final ModelPart model = createBodyLayer().bakeRoot();

    public FailuresaurusFishModel() {
        super(RenderType::entityCutout);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshdefinition = new MeshDefinition();
        PartDefinition partdefinition = meshdefinition.getRoot();

        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0, 0, 0));
        PartDefinition body1 = root.addOrReplaceChild("body1", CubeListBuilder.create().texOffs(85, 1).addBox(-3.5f, 0, -4, 7, 4, 8), PartPose.offset(0, 20, 0));
        PartDefinition body2 = body1.addOrReplaceChild("body2", CubeListBuilder.create().texOffs(50, 1).addBox(-2.5f, -3, -3, 5, 4, 6), PartPose.rotation(0, 0, 0.0873f));
        PartDefinition head = body2.addOrReplaceChild("head", CubeListBuilder.create().texOffs(70, 14).addBox(-2, -2, -3, 4, 4, 3), PartPose.offsetAndRotation(0, -0.5f, -2, 0, 0, -0.1745f));
        head.addOrReplaceChild("snout", CubeListBuilder.create().texOffs(52, 15).addBox(-1.5f, -1, -4, 3, 2, 4), PartPose.offset(0, 0, -2));
        head.addOrReplaceChild("ljaw", CubeListBuilder.create().texOffs(43, 12).addBox(-1, -0.5f, -4, 1, 1, 4), PartPose.offsetAndRotation(0, 0.8f, -2, 0.925f, 0, 0.4363f));
        head.addOrReplaceChild("rjaw", CubeListBuilder.create().texOffs(43, 19).addBox(0, -0.5f, -4, 1, 1, 4), PartPose.offsetAndRotation(0, 0.8f, -2, 0.925f, 0, -0.4363f));

        PartDefinition rtail1 = body2.addOrReplaceChild("rtail1", CubeListBuilder.create().texOffs(50, 30).addBox(-1, -1.5f, 0, 2, 3, 3), PartPose.offsetAndRotation(1, -0.5f, 2, 0, 0.5236f, -0.0524f));
        PartDefinition rtail2 = rtail1.addOrReplaceChild("rtail2", CubeListBuilder.create().texOffs(50, 40).addBox(-0.5f, -1, 0, 1, 2, 3), PartPose.offsetAndRotation(0, 0, 2, 0, 0.3491f, 0));
        rtail2.addOrReplaceChild("rtailfin1", CubeListBuilder.create().texOffs(50, 50).addBox(0, -3, 0, 0, 3, 1), PartPose.offsetAndRotation(0, 0, 2, -0.3491f, 0, 0));
        rtail2.addOrReplaceChild("rtailfin2", CubeListBuilder.create().texOffs(50, 55).addBox(0, 0, 0, 0, 3, 1), PartPose.offsetAndRotation(0, 0, 2, 0.2618f, 0, 0));

        PartDefinition ltail1 = body2.addOrReplaceChild("ltail1", CubeListBuilder.create().texOffs(80, 30).addBox(-1, -1.5f, 0, 2, 3, 3), PartPose.offsetAndRotation(-1.5f, -1, 1, 0.2443f, -0.6632f, 0));
        PartDefinition ltail2 = ltail1.addOrReplaceChild("ltail2", CubeListBuilder.create().texOffs(80, 40).addBox(-0.5f, -1, 0, 1, 2, 3), PartPose.offsetAndRotation(0, 0, 2, 0, -0.2618f, 0));
        ltail2.addOrReplaceChild("ltailfin1", CubeListBuilder.create().texOffs(80, 50).addBox(0, -3, 0, 0, 3, 1), PartPose.offsetAndRotation(0, 0, 2, -0.3491f, 0, 0));
        ltail2.addOrReplaceChild("ltailfin2", CubeListBuilder.create().texOffs(80, 55).addBox(0, 0, 0, 0, 3, 1), PartPose.offsetAndRotation(0, 0, 2, 0.2618f, 0, 0));

        PartDefinition btail1 = body1.addOrReplaceChild("btail1", CubeListBuilder.create().texOffs(65, 30).addBox(-1, -1.5f, 0, 2, 3, 2), PartPose.offsetAndRotation(-2, 2, 3, 0.1047f, -0.1745f, 0));
        PartDefinition btail2 = btail1.addOrReplaceChild("btail2", CubeListBuilder.create().texOffs(65, 40).addBox(-0.5f, -1, 0, 1, 2, 3), PartPose.offsetAndRotation(0, 0, 1, 0, 0.3491f, 0));
        btail2.addOrReplaceChild("btailfin1", CubeListBuilder.create().texOffs(65, 50).addBox(0, -2, 0, 0, 2, 1), PartPose.offsetAndRotation(0, 0, 2, -0.3491f, 0, 0));
        btail2.addOrReplaceChild("btailfin2", CubeListBuilder.create().texOffs(65, 55).addBox(0, 0, 0, 0, 2, 1), PartPose.offsetAndRotation(0, 0, 2, 0.2618f, 0, 0));

        PartDefinition lleg1 = root.addOrReplaceChild("lleg1", CubeListBuilder.create().texOffs(23, 14).addBox(-4, -1, -1, 4, 2, 2), PartPose.offsetAndRotation(-2.5f, 22, -3, -0.2618f, -0.5236f, 0.6109f));
        lleg1.addOrReplaceChild("lleg2", CubeListBuilder.create().texOffs(27, 21).addBox(-0.5f, 0, -0.5f, 1, 4, 1), PartPose.offsetAndRotation(-3.5f, 0.5f, 0, 0, 0, -0.2443f));

        PartDefinition rleg1 = root.addOrReplaceChild("rleg1", CubeListBuilder.create().texOffs(23, 14).mirror().addBox(0, -1, -1, 4, 2, 2).mirror(false), PartPose.offsetAndRotation(2.5f, 22, -3, -0.2618f, 0.5236f, -0.6109f));
        rleg1.addOrReplaceChild("rleg2", CubeListBuilder.create().texOffs(27, 21).mirror().addBox(-0.5f, 0, -0.5f, 1, 4, 1).mirror(false), PartPose.offsetAndRotation(3.5f, 0.5f, 0, 0, 0, 0.2443f));

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
