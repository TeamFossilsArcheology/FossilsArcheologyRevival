package com.fossil.fossil.client.model;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class FigurinePiglinModel {

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition root = meshDefinition.getRoot();

        PartDefinition notDestroyed = root.addOrReplaceChild("notDestroyed", CubeListBuilder.create().texOffs(0, 0).addBox(-5.5F, 0, -5.5F, 11, 2, 11), PartPose.offset(0, 22, 0));
        PartDefinition body = notDestroyed.addOrReplaceChild("body", CubeListBuilder.create().texOffs(35, 0).addBox(-2, 0, -1, 4, 6, 2), PartPose.offset(0, -6, 0));
        PartDefinition bodyTop = body.addOrReplaceChild("bodyTop", CubeListBuilder.create().texOffs(49, 0).addBox(-2, 0, -1, 4, 6, 2), PartPose.offset(0, -6, 0));
        bodyTop.addOrReplaceChild("leftArm", CubeListBuilder.create().texOffs(0, 23).addBox(2, -14, -1, 2, 6, 2), PartPose.offsetAndRotation(-4.4923F, 8.6173F, -8.5338F, -0.8652F, 0.3643F, 0.1367F));
        PartDefinition bodyWeapon = bodyTop.addOrReplaceChild("bodyWeapon", CubeListBuilder.create().texOffs(13, 14).addBox(-1.5F, 3, 1, 1, 2, 1)
                .texOffs(9, 23).addBox(-2, -1, -1, 2, 6, 2), PartPose.offsetAndRotation(-2, 1, 0, -0.4554F, -0.4554F, -0.182F));
        bodyWeapon.addOrReplaceChild("sword", CubeListBuilder.create().texOffs(18, 15).addBox(-0.5F, -1, -5.5F, 1, 2, 5)
                .texOffs(18, 14).addBox(-0.5F, -2, -0.5F, 1, 4, 1), PartPose.offset(-1, 4, -1.5F));
        bodyTop.addOrReplaceChild("bodyHead", CubeListBuilder.create().texOffs(0, 4).addBox(-1.5F, -2.1F, -2.5F, 3, 2, 1)
                .texOffs(0, 0).addBox(-2.5F, -3, -0.3F, 1, 2, 1)
                .texOffs(0, 0).addBox(1.5F, -3, -0.3F, 1, 2, 1)
                .texOffs(0, 14).addBox(-2, -4, -2, 4, 4, 4), PartPose.offsetAndRotation(0, 0, 0, 0.2731F, 0, 0));

        root.addOrReplaceChild("swordDestroyed", CubeListBuilder.create().texOffs(46, 15).addBox(-0.5F, -1, -5.5F, 1, 2, 5)
                .texOffs(45, 14).addBox(-0.5F, -2, -0.5F, 1, 4, 1), PartPose.offsetAndRotation(-3.1F, 21.5F, -1.5F, -1.1383F, 0, 1.5708F));
        root.addOrReplaceChild("bodyHeadDestroyed", CubeListBuilder.create().texOffs(31, 23).addBox(-1.5F, -2.1F, -2.5F, 3, 2, 1)
                .texOffs(35, 27).addBox(-2.5F, -3, -0.3F, 1, 2, 1)
                .texOffs(35, 27).addBox(1.5F, -3, -0.3F, 1, 2, 1)
                .texOffs(18, 23).addBox(-2, -4, -2, 4, 4, 4), PartPose.offsetAndRotation(0.1F, 11.5F, -1.5F, 0.6829F, -0.2276F, 0));
        return LayerDefinition.create(meshDefinition, 74, 32);
    }
}
