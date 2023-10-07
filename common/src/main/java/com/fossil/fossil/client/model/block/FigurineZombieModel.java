package com.fossil.fossil.client.model.block;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class FigurineZombieModel {
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition root = meshDefinition.getRoot();
        PartDefinition notDestroyed = root.addOrReplaceChild("notDestroyed", CubeListBuilder.create().texOffs(0, 0).addBox(-5.5F, 0, -5.5F, 11, 2, 11), PartPose.offset(0, 22, 0));
        PartDefinition body = notDestroyed.addOrReplaceChild("body", CubeListBuilder.create().texOffs(35, 0).addBox(-2, 0, -1, 4, 6, 2), PartPose.offset(0, -6, 0));
        PartDefinition bodyTop = body.addOrReplaceChild("bodyTop", CubeListBuilder.create().texOffs(0, 14).addBox(-2, -4, -2, 4, 4, 4)
                .texOffs(49, 0).addBox(-2, 0, -1, 4, 6, 2), PartPose.offsetAndRotation(0, -5.9F, -0.5F, 0.0911F, 0, 0));
        bodyTop.addOrReplaceChild("leftArm", CubeListBuilder.create().texOffs(0, 23).addBox(2, -13.9F, -1.5F, 2, 6, 2), PartPose.offsetAndRotation(0.3378F, 8.4566F, -10.597F, -1.0016F, 0, -0.0456F));
        bodyTop.addOrReplaceChild("rightArm", CubeListBuilder.create().texOffs(9, 23).addBox(-4, -13.9F, -1.5F, 2, 6, 2), PartPose.offsetAndRotation(-1.2908F, 4.1499F, -12.6584F, -1.3659F, 0.0911F, 0.0456F));

        root.addOrReplaceChild("headDestroyed", CubeListBuilder.create().texOffs(18, 23).addBox(0.9F, -6, 0.6F, 4, 4, 4), PartPose.offsetAndRotation(2.2912F, 24, -1.247F, 0, -0.6829F, 0));
        root.addOrReplaceChild("leftArmDestroyed", CubeListBuilder.create().texOffs(0, 0).addBox(2.4F, -3, -0.5F, 2, 6, 2), PartPose.offsetAndRotation(-1.0954F, 20.5F, -0.0929F, -1.5708F, 0.5918F, 0));
        root.addOrReplaceChild("rightArmDestroyed", CubeListBuilder.create().texOffs(31, 14).addBox(-5.1F, -3, -0.4F, 2, 6, 2), PartPose.offsetAndRotation(-2.825F, 25.1F, -1.4699F, -1.4114F, 0, 1.5708F));
        return LayerDefinition.create(meshDefinition, 74, 32);
    }
}
