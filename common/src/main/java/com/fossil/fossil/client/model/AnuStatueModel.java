package com.fossil.fossil.client.model;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.entity.AnuStatueEntity;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;

public class AnuStatueModel extends EntityModel<AnuStatueEntity> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(Fossil.MOD_ID, "textures/entity/anu_statue.png");
    private final ModelPart model = createBodyLayer().bakeRoot();

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition root = meshDefinition.getRoot();
        PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 16)
                .addBox(-4, -8, -4, 8, 8, 8), PartPose.offsetAndRotation(0, 4, 0, 0.07214433182969043f, 0, 0));
        root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(40, 18)
                .addBox(-4, -4, -2, 8, 10, 4), PartPose.offset(0, 8, 0));
        root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(47, 0)
                .addBox(-2, 0, -2, 4, 10, 4), PartPose.offset(-2, 14, 0));
        root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(47, 0)
                .addBox(-2, 0, -2, 4, 10, 4), PartPose.offset(2, 14, 0));
        root.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-4, -1, -2, 4, 10, 4), PartPose.offsetAndRotation(-4, 5, 0, 0, 0, 0.14116187756067558f));
        root.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(16, 0)
                .addBox(0, -1, -2, 4, 10, 4), PartPose.offsetAndRotation(4, 5, 0, -1.605702911834783f, -0.045553093477052f, 0));

        PartDefinition mouth = head.addOrReplaceChild("mouth", CubeListBuilder.create().texOffs(26, 17)
                .addBox(-3, -3, -2, 6, 3, 2), PartPose.offset(0, 0, -4));

        mouth.addOrReplaceChild("right_tusk", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-0.5f, -1, -0.5f, 1, 1, 1), PartPose.offset(-2.5f, -3, -1.5f));

        mouth.addOrReplaceChild("left_tusk", CubeListBuilder.create().texOffs(0, 0)
                .addBox(-0.5f, -1, -0.5f, 1, 1, 1), PartPose.offset(2.5f, -3, -1.5f));
        head.addOrReplaceChild("right_horn", CubeListBuilder.create().texOffs(0, 6)
                .addBox(-1, -6, -0.5f, 2, 6, 1), PartPose.offset(-3, -8, 3.5f));

        head.addOrReplaceChild("left_horn", CubeListBuilder.create().texOffs(0, 17)
                .addBox(-1, -6, -0.5f, 2, 6, 1), PartPose.offset(3, -8, 3.5f));

        head.addOrReplaceChild("head_thing", CubeListBuilder.create().texOffs(24, 2).mirror()
                .addBox(-2, -3, -8, 4, 3, 12), PartPose.offset(0, -8, 0));


        return LayerDefinition.create(meshDefinition, 64, 32);
    }

    @Override
    public void setupAnim(AnuStatueEntity entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float red, float green, float blue,
                               float alpha) {
        model.render(poseStack, buffer, packedLight, packedOverlay, red, green, blue, alpha);
    }
}
