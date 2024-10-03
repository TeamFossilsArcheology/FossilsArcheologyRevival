package com.github.teamfossilsarcheology.fossil.client.model;

import com.github.teamfossilsarcheology.fossil.entity.monster.Anubite;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.util.Mth;

public class AnubiteModel extends EntityModel<Anubite> {
    private final ModelPart model = createBodyLayer().bakeRoot();
    private final ModelPart nose;
    private final ModelPart leftEar;
    private final ModelPart rightEar;
    private final ModelPart head;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;
    private final ModelPart rightArm;
    private final ModelPart leftArm;

    public AnubiteModel() {
        super();
        head = model.getChild("head");
        nose = model.getChild("nose");
        rightEar = model.getChild("right_ear");
        leftEar = model.getChild("left_ear");
        rightLeg = model.getChild("right_leg");
        leftLeg = model.getChild("left_leg");
        rightArm = model.getChild("right_arm");
        leftArm = model.getChild("left_arm");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition root = meshDefinition.getRoot();
        root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).mirror()
                .addBox(-4, -8, -4, 8, 8, 7), PartPose.offset(0, -2, 0));
        root.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(31, 4).mirror()
                .addBox(-2, -5.5f, -8, 4, 5, 5), PartPose.offset(0, -2, 0));

        root.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(58, 14).mirror()
                .addBox(-3.5f, -13, 0, 2, 5, 1), PartPose.offset(0, -2, 0));
        root.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(58, 14).mirror()
                .addBox(1.5f, -13, 0, 2, 5, 1), PartPose.offset(0, -2, 0));
        root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 15).mirror()
                .addBox(-4, 0, -2, 8, 13, 4), PartPose.offset(0, -2, 0));

        root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 15).mirror()
                .addBox(-2, 1, -2, 4, 13, 4), PartPose.offsetAndRotation(-2, 10, 0, 0, 0, Mth.DEG_TO_RAD));
        root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 15)
                .addBox(-2, 1, -2, 4, 13, 4), PartPose.offsetAndRotation(2, 10, 0, 0, 0, -Mth.DEG_TO_RAD));
        PartDefinition right_arm = root.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 15).mirror()
                .addBox(-3, -2, -2, 4, 13, 4), PartPose.offsetAndRotation(-5, 0, 0, 0, 0, 2 * Mth.DEG_TO_RAD));
        root.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 15).mirror()
                .addBox(-1, -2, -2, 4, 13, 4), PartPose.offsetAndRotation(5, 0, 0, 0, 0, -2 * Mth.DEG_TO_RAD));


        right_arm.addOrReplaceChild("sword", CubeListBuilder.create().texOffs(0, 16).mirror()
                .addBox(0, -16, -16, 0, 16, 16), PartPose.offsetAndRotation(-1, 12, 5, 27 * Mth.DEG_TO_RAD, 0, 0));


        return LayerDefinition.create(meshDefinition, 128, 64);
    }

    @Override
    public void setupAnim(Anubite entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        head.yRot = netHeadYaw * Mth.DEG_TO_RAD;
        head.xRot = headPitch * Mth.DEG_TO_RAD;
        nose.yRot = head.yRot;
        nose.xRot = head.xRot;
        rightEar.yRot = head.yRot;
        rightEar.xRot = head.xRot;
        leftEar.yRot = head.yRot;
        leftEar.xRot = head.xRot;

        rightArm.xRot = Mth.cos(limbSwing * 0.6662f + Mth.PI) * 2 * limbSwingAmount * 0.5f;
        leftArm.xRot = Mth.cos(limbSwing * 0.6662f) * 2 * limbSwingAmount * 0.5f;
        rightArm.yRot = 0;
        leftArm.yRot = 0;
        rightArm.zRot = 0;
        leftArm.zRot = 0;
        rightLeg.xRot = Mth.cos(limbSwing * 0.6662f) * 1.4f * limbSwingAmount;
        leftLeg.xRot = Mth.cos(limbSwing * 0.6662f + Mth.PI) * 1.4f * limbSwingAmount;
        rightLeg.yRot = 0;
        leftLeg.yRot = 0;
        rightLeg.zRot = 0;
        leftLeg.zRot = 0;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        model.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
