package com.fossil.fossil.client.model.block;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class FigurineSkeletonModel {

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition root = meshDefinition.getRoot();
        PartDefinition notDestroyed = root.addOrReplaceChild("notDestroyed", CubeListBuilder.create().texOffs(0, 0).addBox(-5.5F, 0, -5.5F, 11, 2, 11), PartPose.offset(0, 22, 0));
        PartDefinition body = notDestroyed.addOrReplaceChild("body", CubeListBuilder.create().texOffs(35, 0).addBox(-2, 0, -1, 4, 6, 2), PartPose.offsetAndRotation(0, -6, 0, 0, 1.0928F, 0));
        PartDefinition bodyTop = body.addOrReplaceChild("bodyTop", CubeListBuilder.create().texOffs(49, 0).addBox(-2, 0, -1, 4, 6, 2), PartPose.offset(0, -6, 0));
        bodyTop.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 14).addBox(-2, -18, -2, 4, 4, 4), PartPose.offsetAndRotation(0, 14, 0, 0, -0.9561F, 0));
        bodyTop.addOrReplaceChild("rightArm", CubeListBuilder.create().texOffs(9, 23).mirror().addBox(-4, -14, -1, 2, 6, 2).mirror(false), PartPose.offsetAndRotation(9.7994F, 3.6451F, -5.1747F, -1.3659F, -1.0016F, 0));
        PartDefinition bodyWeapon = bodyTop.addOrReplaceChild("bodyWeapon", CubeListBuilder.create().texOffs(0, 23).addBox(0, -1, -1, 2, 6, 2), PartPose.offsetAndRotation(2, 1, 0, -1.5026F, -0.7285F, -0.0456F));
        bodyWeapon.addOrReplaceChild("bow", CubeListBuilder.create().texOffs(17, 14).addBox(-0.5F, 1, 0, 1, 6, 1)
                .texOffs(41, 9).addBox(-0.5F, 0, -3.5F, 1, 5, 8)
                .texOffs(41, 9).addBox(0.5f, 5, 3.4f, -1, -5, -8), PartPose.offsetAndRotation(-2.5F, 1, -0.4F, 0, 0, -0.5463F));

        root.addOrReplaceChild("headDestroyed", CubeListBuilder.create().texOffs(18, 23).addBox(0.9F, -6, -4.2F, 4, 4, 4), PartPose.offsetAndRotation(0.974F, 24, 0.8888F, 0, 0.3643F, 0));
        root.addOrReplaceChild("leftArmDestroyed", CubeListBuilder.create().texOffs(0, 0).addBox(1.9F, -7, -0.5F, 2, 6, 2), PartPose.offsetAndRotation(0, 16.5F, -6.5F, -1.5708F, 0, 0));
        root.addOrReplaceChild("rightArmDestroyed", CubeListBuilder.create().texOffs(31, 14).addBox(-5.1F, -3, -0.4F, 2, 6, 2), PartPose.offsetAndRotation(-4.0097F, 25.1F, 2.4795F, 1.4114F, 0, 1.5708F));
        return LayerDefinition.create(meshDefinition, 74, 32);
    }
}
