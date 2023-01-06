package com.fossil.fossil.client.model;

import com.fossil.fossil.Fossil;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;

public class EmbryoPlantModel {
    public static final ResourceLocation TEXTURE = new ResourceLocation(Fossil.MOD_ID, "textures/block/cultivate/embryo_plant.png");
    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        partDefinition.addOrReplaceChild("shape1", CubeListBuilder.create().texOffs(0, 0).mirror()
                .addBox(-1.5f, -1, -1, 3, 3, 3), PartPose.offsetAndRotation(0, 13, 0, -0.418879f, 0, 0));
        partDefinition.addOrReplaceChild("shape2", CubeListBuilder.create().texOffs(0, 6).mirror()
                .addBox(-0.5f, -3, -1, 1, 3, 2), PartPose.offset(0, 13, 0));
        partDefinition.addOrReplaceChild("shape3", CubeListBuilder.create().texOffs(0, 11).mirror()
                .addBox(-1, -6, -2, 2, 5, 4), PartPose.offsetAndRotation(0, 11.2f, 0, -0.1745329f, 0, 0));
        partDefinition.addOrReplaceChild("shape4", CubeListBuilder.create().texOffs(0, 20).mirror()
                .addBox(-0.5f, -2, -1, 1, 2, 2), PartPose.offsetAndRotation(0, 5.6f, 0, 0.3141593f, 0, 0));
        partDefinition.addOrReplaceChild("shape5", CubeListBuilder.create().texOffs(0, 24).mirror()
                .addBox(-0.5f, -2, -1, 1, 2, 2), PartPose.offsetAndRotation(0, 6, 2, -0.4886922f, 0, 0));
        return LayerDefinition.create(meshDefinition, 64, 32);
    }
}
