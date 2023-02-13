package com.fossil.fossil.client.model;

import com.fossil.fossil.Fossil;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;

public class EmbryoGenericModel {
    public static final ResourceLocation TEXTURE_GENERIC = new ResourceLocation(Fossil.MOD_ID, "textures/block/cultivate/embryo_generic.png");
    public static final ResourceLocation TEXTURE_LIMBLESS = new ResourceLocation(Fossil.MOD_ID, "textures/block/cultivate/embryo_limbless.png");
    public static final ResourceLocation TEXTURE_INSECT = new ResourceLocation(Fossil.MOD_ID, "textures/block/cultivate/embryo_insect.png");

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        partDefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).mirror()
                .addBox(-1.5f, -1.5f, -3, 3, 3, 3), PartPose.offsetAndRotation(0, 13, -0.2f, 0.3141593f, 0, 0));
        partDefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 15).mirror()
                .addBox(-2, 0, -1, 4, 4, 2), PartPose.offsetAndRotation(0, 13.9f, 1.8f, -0.0349066f, 0, 0));
        partDefinition.addOrReplaceChild("neck2", CubeListBuilder.create().texOffs(0, 6).mirror()
                .addBox(-1, -1, -1, 2, 3, 2), PartPose.offsetAndRotation(0, 13, -0.5f, 1.413717f, 0, 0));
        partDefinition.addOrReplaceChild("neck1", CubeListBuilder.create().texOffs(0, 11).mirror()
                .addBox(-1.5f, 0, -1, 3, 2, 2), PartPose.offsetAndRotation(0, 12.9f, 0.8f, 0.5585054f, 0, 0));

        partDefinition.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(0, 21).mirror()
                .addBox(-1.5f, 0, -1, 3, 2, 2), PartPose.offsetAndRotation(0, 17.3f, 1.7f, -0.715585f, 0, 0));
        partDefinition.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(0, 25).mirror()
                .addBox(-1, -0.5f, -2, 2, 1, 2), PartPose.offsetAndRotation(0, 18.8f, 1, -0.122173f, 0, 0));
        partDefinition.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(0, 28).mirror()
                .addBox(-0.5f, -0.5f, -2, 1, 1, 2), PartPose.offsetAndRotation(0, 18.6f, -0.8f, -0.5235988f, 0, 0));

        partDefinition.addOrReplaceChild("leftarm", CubeListBuilder.create().texOffs(60, 0).mirror()
                .addBox(-0.5f, 0, -0.5f, 1, 2, 1), PartPose.offsetAndRotation(1.4f, 14.6f, 2, -1.204277f, -0.2094395f, 0));
        partDefinition.addOrReplaceChild("rightarm", CubeListBuilder.create().texOffs(60, 0).mirror()
                .addBox(-0.5f, 0, -0.5f, 1, 2, 1), PartPose.offsetAndRotation(-1.4f, 14.6f, 2, -1.204277f, 0.2094395f, 0));
        partDefinition.addOrReplaceChild("left_foot", CubeListBuilder.create().texOffs(60, 3).mirror()
                .addBox(-0.5f, 0, -0.5f, 1, 2, 1), PartPose.offsetAndRotation(1, 17.4f, 1.4f, -1.204277f, -0.9773844f, 0));
        partDefinition.addOrReplaceChild("rightfoot", CubeListBuilder.create().texOffs(60, 3).mirror()
                .addBox(-0.5f, 0, -0.5f, 1, 2, 1), PartPose.offsetAndRotation(-1, 17.4f, 1.4f, -1.204277f, 0.9773844f, 0));
        return LayerDefinition.create(meshDefinition, 64, 32);
    }
}
