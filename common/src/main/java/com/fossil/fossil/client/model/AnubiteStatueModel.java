package com.fossil.fossil.client.model;

import com.fossil.fossil.Fossil;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;

public class AnubiteStatueModel {
    public static final ResourceLocation TEXTURE = new ResourceLocation(Fossil.MOD_ID, "textures/entity/anubite_statue.png");

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition root = meshDefinition.getRoot();
        root.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).mirror()
                .addBox(-4, -8, -4, 8, 8, 7), PartPose.offset(0, -2, 0));
        root.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(31, 4).mirror()
                .addBox(-2, -5.5f, -8, 4, 5, 5), PartPose.offset(0, -2, 0));

        root.addOrReplaceChild("ear1", CubeListBuilder.create().texOffs(58, 14).mirror()
                .addBox(-3.5f, -13, 0, 2, 5, 1), PartPose.offset(0, -2, 0));
        root.addOrReplaceChild("ear2", CubeListBuilder.create().texOffs(58, 14).mirror()
                .addBox(1.5f, -13, 0, 2, 5, 1), PartPose.offset(0, -2, 0));
        root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 15).mirror()
                .addBox(-4, 0, -2, 8, 13, 4), PartPose.offset(0, -2, 0));

        root.addOrReplaceChild("right_leg", CubeListBuilder.create().texOffs(0, 15).mirror()
                .addBox(-2, 1, -2, 4, 13, 4), PartPose.offsetAndRotation(-2, 10, 0, 0, 0, 0.0174533f));
        root.addOrReplaceChild("left_leg", CubeListBuilder.create().texOffs(0, 15)
                .addBox(-2, 1, -2, 4, 13, 4), PartPose.offsetAndRotation(2, 10, 0, 0, 0, -0.0174533f));
        PartDefinition right_arm = root.addOrReplaceChild("right_arm", CubeListBuilder.create().texOffs(40, 15).mirror()
                .addBox(-3, -2, -2, 4, 13, 4), PartPose.offsetAndRotation(-5, 0, 0, 0, 0, 0.0349066f));
        root.addOrReplaceChild("left_arm", CubeListBuilder.create().texOffs(40, 15).mirror()
                .addBox(-1, -2, -2, 4, 13, 4), PartPose.offsetAndRotation(5, 0, 0, 0, 0, -0.0349066f));


        right_arm.addOrReplaceChild("sword", CubeListBuilder.create().texOffs(0, 16).mirror()
                .addBox(0, -16, -16, 0, 16, 16), PartPose.offsetAndRotation(-1, 12, 5, 0.4712389f, 0, 0));


        return LayerDefinition.create(meshDefinition, 128, 64);
    }
}
