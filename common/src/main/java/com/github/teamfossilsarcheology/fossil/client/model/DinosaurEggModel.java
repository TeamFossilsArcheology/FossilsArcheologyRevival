package com.github.teamfossilsarcheology.fossil.client.model;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.DinosaurEgg;
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

public class DinosaurEggModel extends EntityModel<DinosaurEgg> {

    private final ModelPart model = createBodyLayer().bakeRoot();
    private final float defaultYRot = model.getChild("Egg1").yRot;
    private final float defaultZRot = model.getChild("Egg1").zRot;
    private final float defaultY = model.getChild("Egg1").y;

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition root = meshDefinition.getRoot();
        var egg1 = root.addOrReplaceChild("Egg1", CubeListBuilder.create().texOffs(0, 12).addBox(-3, -2.8f, -3, 6, 6, 6), PartPose.offset(0, 19.6f, 0));
        egg1.addOrReplaceChild("Egg2", CubeListBuilder.create().texOffs(28, 16).addBox(-2, -4.8f, -2, 4, 4, 4), PartPose.offset(0, -0.9f, 0));
        var egg3 = egg1.addOrReplaceChild("Egg3", CubeListBuilder.create().texOffs(22, 2).addBox(-2.5f, -0.6f, -2.5f, 5, 5, 5), PartPose.ZERO);
        egg3.addOrReplaceChild("Egg4", CubeListBuilder.create().texOffs(0, 0).addBox(-2.5f, -4.6f, -2.5f, 5, 5, 5), PartPose.ZERO);

        return LayerDefinition.create(meshDefinition, 64, 32);
    }

    @Override
    public void setupAnim(DinosaurEgg entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        ModelPart egg1 = model.getChild("Egg1");
        egg1.yRot = defaultYRot;
        egg1.zRot = defaultZRot;
        egg1.y = defaultY;
        if (!entity.isTooCold() && entity.getHatchingTime() > entity.getTotalHatchingTime() * 0.9) {
            egg1.yRot += calcRotation(0.3F, 0.5F, false, 0.25F, 0, ageInTicks, 1);
            egg1.zRot += calcRotation(0.3F, 0.5F, true, 0.25F, 0, ageInTicks, 1);
            egg1.y += calcBob(0.3F, 0.9F, true, ageInTicks, 1);
        }
    }

    private float calcRotation(float speed, float degree, boolean invert, float offset, float weight, float ageInTicks, float amount) {
        float rotation = Mth.cos(ageInTicks * speed + offset) * degree * amount + weight * amount;
        return invert ? -rotation : rotation;
    }

    private float calcBob(float speed, float degree, boolean bounce, float ageInTicks, float amount) {
        return bounce ? -Mth.abs(Mth.sin(ageInTicks * speed) * amount * degree) : Mth.sin(ageInTicks * speed) * degree * amount - amount * degree;
    }

    @Override
    public void renderToBuffer(PoseStack poseStack, VertexConsumer buffer, int packedLight, int packedOverlay, float r, float g, float b, float alpha) {
        model.render(poseStack, buffer, packedLight, packedOverlay, r, g, b, alpha);
    }
}
