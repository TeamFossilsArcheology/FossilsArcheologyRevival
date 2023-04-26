package com.fossil.fossil.client.model;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.entity.AnuDead;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.resources.ResourceLocation;

public class AnuDeadModel extends EntityModel<AnuDead> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(Fossil.MOD_ID, "textures/entity/anu.png");
    private final ModelPart model = createBodyLayer().bakeRoot();

    public AnuDeadModel() {
        super(RenderType::entityTranslucent);
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition root = meshDefinition.getRoot();
        PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 16)
                .addBox(-4, -8, -4, 8, 8, 8), PartPose.offset(0, 4, 0));
        head.addOrReplaceChild("right_horn", CubeListBuilder.create().texOffs(0, 6)
                .addBox(-1, -6, -0.5f, 2, 6, 1), PartPose.offset(-3, -8, 3.5f));
        head.addOrReplaceChild("left_horn", CubeListBuilder.create().texOffs(0, 17)
                .addBox(-1, -6, -0.5f, 2, 6, 1), PartPose.offset(3, -8, 3.5f));
        head.addOrReplaceChild("head_thing", CubeListBuilder.create().texOffs(24, 2).mirror()
                .addBox(-2, -3, -8, 4, 3, 12), PartPose.offset(0, -8, 0));
        PartDefinition mouth = head.addOrReplaceChild("mouth", CubeListBuilder.create().texOffs(26, 17)
                .addBox(-3, -3, -2, 6, 3, 2), PartPose.offset(0, 0, -4));
        mouth.addOrReplaceChild("right_tusk", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-0.5f, -1, -0.5f, 1, 1, 1), PartPose.offset(-2.5f, -3, -1.5f));
        mouth.addOrReplaceChild("left_tusk", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-0.5f, -1, -0.5f, 1, 1, 1), PartPose.offset(2.5f, -3, -1.5f));

        root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(40, 18)
                .addBox(-4, -4, -2, 8, 10, 4), PartPose.offset(0, 8, 0));
        root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(47, 0)
                .addBox(-2, 0, -2, 4, 10, 4), PartPose.offsetAndRotation(-2, 13.7f, 0, 0, 0, 0.18203784098300857f));
        root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(47, 0)
                .addBox(-2, 0, -2, 4, 10, 4), PartPose.offsetAndRotation(2, 13.7f, 0, 0, 0, -0.18203784098300857f));
        root.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(16, 0)
                .addBox(-4, -1, -2, 4, 10, 4), PartPose.offsetAndRotation(-3.5f, 5, 0.1f, 0, 0, 0.22759093446006054f));
        root.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(0, 0)
                .addBox(0, -1, -2, 4, 10, 4), PartPose.offsetAndRotation(3.5f, 5, 0.1f, 0, 0, -0.22759093446006054f));

        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        model.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void setupAnim(AnuDead entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

}
