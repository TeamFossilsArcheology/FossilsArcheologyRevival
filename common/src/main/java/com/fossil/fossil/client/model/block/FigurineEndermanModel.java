package com.fossil.fossil.client.model.block;

import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;

public class FigurineEndermanModel {

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition root = meshDefinition.getRoot();
        root.addOrReplaceChild("block", CubeListBuilder.create().texOffs(42, 16).addBox(-2, 15, -4.5F, 4, 4, 4), PartPose.ZERO);
        PartDefinition notDestroyed = root.addOrReplaceChild("notDestroyed", CubeListBuilder.create().texOffs(0, 0).addBox(-5.5f, 0, -5.5f, 11, 2, 11), PartPose.offset(0, 22, 0));
        PartDefinition body = notDestroyed.addOrReplaceChild("body", CubeListBuilder.create().texOffs(31, 14).addBox(-1.5f, -1, -1, 3, 10, 2), PartPose.offset(0, -9, 1));
        PartDefinition bodyTop = body.addOrReplaceChild("bodyTop", CubeListBuilder.create().texOffs(0, 14).addBox(-2, -4, -2, 4, 4, 4)
                .texOffs(17, 14).addBox(-2, 0, -1, 4, 5, 2), PartPose.offset(0, -6, 0));
        bodyTop.addOrReplaceChild("rightArm", CubeListBuilder.create().texOffs(45, 0).mirror().addBox(-4, -17, 0, 2, 13, 2).mirror(false)
                .texOffs(45, 0).addBox(2, -17, 0, 2, 13, 2), PartPose.offsetAndRotation(0, 15.594f, -6.6344f, -0.3643f, 0, 0));
        root.addOrReplaceChild("blockDestroyed", CubeListBuilder.create().texOffs(14, 23).addBox(-0.6f, -6, -5.5f, 4, 4, 4), PartPose.offsetAndRotation(-1.7899f, 21.5005f, 0.717f, 0.4554f, -0.3187f, 0.182f));
        root.addOrReplaceChild("rightArmDestroyed", CubeListBuilder.create().texOffs(55, 1).addBox(4.4f, -4, 0.8f, 2, 13, 2), PartPose.offsetAndRotation(-0.3981f, 19.2f, 4.1105f, -1.5708f, 1.0472f, 0));
        return LayerDefinition.create(meshDefinition, 64, 32);
    }
}
