package com.fossil.fossil.client.model;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.entity.AnuBoss;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.ArmedModel;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.HumanoidArm;

public class AnuBossModel extends EntityModel<AnuBoss> implements ArmedModel {
    public static final ResourceLocation TEXTURE = new ResourceLocation(Fossil.MOD_ID, "textures/entity/anu.png");
    private final ModelPart model = createBodyLayer().bakeRoot();
    private final ModelPart rightArm;
    private final ModelPart leftArm;
    private final ModelPart rightLeg;
    private final ModelPart leftLeg;
    private final ModelPart head;
    private final ModelPart rightWing1;
    private final ModelPart rightWing2;
    private final ModelPart rightWing3;
    private final ModelPart leftWing1;
    private final ModelPart leftWing2;
    private final ModelPart leftWing3;

    public AnuBossModel() {
        rightArm = model.getChild("right_arm");
        leftArm = model.getChild("left_arm");
        rightLeg = model.getChild("right_leg");
        leftLeg = model.getChild("left_leg");
        head = model.getChild("head");
        rightWing1 = model.getChild("right_wing_1");
        rightWing2 = rightWing1.getChild("right_wing_2");
        rightWing3 = rightWing2.getChild("right_wing_3");
        leftWing1 = model.getChild("left_wing_1");
        leftWing2 = leftWing1.getChild("left_wing_2");
        leftWing3 = leftWing2.getChild("left_wing_3");
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
                .addBox(-2, 0, -2, 4, 10, 4), PartPose.offset(-2, 14, 0));
        root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(47, 0)
                .addBox(-2, 0, -2, 4, 10, 4), PartPose.offset(2, 14, 0));
        root.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-4, -1, -2, 4, 10, 4), PartPose.offset(-4, 5, 0));
        root.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(16, 0)
                .addBox(0, -1, -2, 4, 10, 4), PartPose.offset(4, 5, 0));


        PartDefinition leftWing1 = root.addOrReplaceChild("left_wing_1", CubeListBuilder.create().texOffs(0, 42)
                .addBox(0, -3, 0, 6, 10, 0), PartPose.offsetAndRotation(2, 7.5f, 1.9f, 0, -0.4363323129985824f, 0));
        PartDefinition leftWing2 = leftWing1.addOrReplaceChild("left_wing_2", CubeListBuilder.create().texOffs(12, 43)
                .addBox(0, -4, 0, 12, 12, 0), PartPose.offsetAndRotation(6, 0, 0, 0, 0.4363323129985824f, 0));
        leftWing2.addOrReplaceChild("left_wing_3", CubeListBuilder.create().texOffs(36, 43)
                .addBox(0, -4, 0, 6, 10, 0), PartPose.offset(12, 1, 0));
        PartDefinition rightWing1 = root.addOrReplaceChild("right_wing_1", CubeListBuilder.create().mirror().texOffs(0, 42)
                .addBox(-6, -3, 0, 6, 10, 0), PartPose.offsetAndRotation(-2, 7.5f, 1.9f, 0, 0.4363323129985824f, 0));
        PartDefinition rightWing2 = rightWing1.addOrReplaceChild("right_wing_2", CubeListBuilder.create().mirror().texOffs(12, 43)
                .addBox(-12, -4, 0, 12, 12, 0), PartPose.offsetAndRotation(-6, 0, 0, 0, -0.4363323129985824f, 0));
        rightWing2.addOrReplaceChild("right_wing_3", CubeListBuilder.create().mirror().texOffs(36, 43)
                .addBox(-6, -4, 0, 6, 10, 0), PartPose.offset(-12, 1, 0));

        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    @Override
    public void prepareMobModel(AnuBoss entity, float limbSwing, float limbSwingAmount, float partialTick) {
        boolean wingsVisible = entity.getAttackMode() == AnuBoss.AttackMode.FLIGHT;
        leftWing1.visible = wingsVisible;
        rightWing1.visible = wingsVisible;
    }

    @Override
    public void setupAnim(AnuBoss entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        if (entity.getAttackMode() != AnuBoss.AttackMode.FLIGHT) {
            rightArm.xRot = Mth.cos(limbSwing * 0.6662f + Mth.PI) * limbSwingAmount;
            leftArm.xRot = Mth.cos(limbSwing * 0.6662f + 1) * limbSwingAmount;
            rightLeg.xRot = Mth.cos(limbSwing * 0.6662f + 1) * limbSwingAmount;
            leftLeg.xRot = Mth.cos(limbSwing * 0.6662f + Mth.PI + 2) * limbSwingAmount;
            if (entity.getAttackMode() == AnuBoss.AttackMode.DEFENSE) {
                head.setRotation(headPitch / (180 / Mth.PI), netHeadYaw / (180 / Mth.PI), head.zRot);
            }
        }
        if (entity.getAttackMode() == AnuBoss.AttackMode.FLIGHT) {
            head.setRotation((float) Math.toRadians(-35), 0, head.zRot);
            rightArm.xRot = 0;
            leftArm.xRot = (float) Math.toRadians(-125);
            rightLeg.xRot = 0;
            leftLeg.xRot = 0;
        }
        leftWing2.yRot = 0.5f * Mth.sin(limbSwingAmount * 0.1f + limbSwing);
        leftWing3.yRot = -0.5f * Mth.sin(limbSwingAmount * 0.1f + limbSwing);
        rightWing2.yRot = 0.5f * Mth.sin(limbSwingAmount * 0.1f + (limbSwing + 1));
        rightWing3.yRot = -0.5f * Mth.sin(limbSwingAmount * 0.1f + (limbSwing + 1));
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
        model.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }

    @Override
    public void translateToHand(HumanoidArm side, PoseStack poseStack) {
        getArm(side).translateAndRotate(poseStack);
    }

    protected ModelPart getArm(HumanoidArm side) {
        if (side == HumanoidArm.LEFT) {
            return leftArm;
        }
        return rightArm;
    }
}
