package com.fossil.fossil.client.model.block;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class FigurineSteveModel {

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition root = meshDefinition.getRoot();
        PartDefinition notDestroyed = root.addOrReplaceChild("notDestroyed", CubeListBuilder.create().texOffs(0, 0).addBox(-5.5f, 0, -5.5f, 11, 2, 11), PartPose.offset(0, 22, 0));
        PartDefinition body = notDestroyed.addOrReplaceChild("body", CubeListBuilder.create().texOffs(35, 0).addBox(-2, 0, -1, 4, 6, 2), PartPose.offsetAndRotation(0, -6, 0, 0, 0.182f, 0));
        PartDefinition bodyTop = body.addOrReplaceChild("bodyTop", CubeListBuilder.create().texOffs(49, 0).addBox(-2, 0, -1, 4, 6, 2), PartPose.offset(0, -6, 0));
        bodyTop.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 14).addBox(-2, -18, -2, 4, 4, 4), PartPose.offsetAndRotation(0, 14, 0, 0, -0.182f, 0));
        bodyTop.addOrReplaceChild("leftArm", CubeListBuilder.create().texOffs(0, 23).addBox(2, -14, -1, 2, 6, 2), PartPose.offsetAndRotation(0.5941f, 14.0776f, 0, 0, 0, -0.0456f));
        PartDefinition bodyWeapon = bodyTop.addOrReplaceChild("bodyWeapon", CubeListBuilder.create().texOffs(13, 14).addBox(-1.5f, 3, 1, 1, 2, 1)
                .texOffs(9, 23).addBox(-2, -1, -1, 2, 6, 2), PartPose.offsetAndRotation(-2, 1, 0, -0.4554f, 0.0911f, 0.0456f));
        bodyWeapon.addOrReplaceChild("sword", CubeListBuilder.create().texOffs(18, 15).addBox(-0.5f, -1, -5.5f, 1, 2, 5)
                .texOffs(18, 14).addBox(-0.5f, -2, -0.5f, 1, 4, 1), PartPose.offset(-1, 4, -1.5f));

        bodyTop.addOrReplaceChild("headDestroyed", CubeListBuilder.create().texOffs(18, 23).addBox(-2, -18, -2, 4, 4, 4), PartPose.offsetAndRotation(-9.3205f, 10.4464f, 0, 0, 0, 0.7285f));
        PartDefinition bodyWeaponDestroyed = root.addOrReplaceChild("bodyWeaponDestroyed", CubeListBuilder.create().texOffs(40, 14).addBox(-1.5f, 3, 1, 1, 2, 1)
                .texOffs(31, 14).addBox(-2, -1, -1, 2, 6, 2), PartPose.offsetAndRotation(-3.1f, 22, 0.6f, -1.4114f, 0, 1.5708f));
        bodyWeaponDestroyed.addOrReplaceChild("swordDestroyed", CubeListBuilder.create().texOffs(46, 15).addBox(-0.5f, -1, -5.5f, 1, 2, 5)
                .texOffs(45, 14).addBox(-0.5f, -2, -0.5f, 1, 4, 1), PartPose.offset(-1, 4, -1.5f));

        return LayerDefinition.create(meshDefinition, 74, 32);
    }
}
