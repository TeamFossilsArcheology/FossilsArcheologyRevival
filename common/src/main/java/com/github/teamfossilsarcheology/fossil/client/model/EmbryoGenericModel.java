package com.github.teamfossilsarcheology.fossil.client.model;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Mth;

/**
 * Model displayed in an active culture vat
 */
public class EmbryoGenericModel {
    public static final ResourceLocation TEXTURE_GENERIC = FossilMod.location("textures/block/culture_vat/embryo_generic.png");
    public static final ResourceLocation TEXTURE_LIMBLESS = FossilMod.location("textures/block/culture_vat/embryo_limbless.png");
    public static final ResourceLocation TEXTURE_INSECT = FossilMod.location("textures/block/culture_vat/embryo_insect.png");

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partDefinition = meshDefinition.getRoot();

        partDefinition.addOrReplaceChild("head", CubeListBuilder.create().texOffs(0, 0).mirror()
                .addBox(-1.5f, -1.5f, -3, 3, 3, 3), PartPose.offsetAndRotation(0, 13, -0.2f, 18 * Mth.DEG_TO_RAD, 0, 0));
        partDefinition.addOrReplaceChild("body", CubeListBuilder.create().texOffs(0, 15).mirror()
                .addBox(-2, 0, -1, 4, 4, 2), PartPose.offsetAndRotation(0, 13.9f, 1.8f, -2 * Mth.DEG_TO_RAD, 0, 0));
        partDefinition.addOrReplaceChild("neck2", CubeListBuilder.create().texOffs(0, 6).mirror()
                .addBox(-1, -1, -1, 2, 3, 2), PartPose.offsetAndRotation(0, 13, -0.5f, 81 * Mth.DEG_TO_RAD, 0, 0));
        partDefinition.addOrReplaceChild("neck1", CubeListBuilder.create().texOffs(0, 11).mirror()
                .addBox(-1.5f, 0, -1, 3, 2, 2), PartPose.offsetAndRotation(0, 12.9f, 0.8f, 32 * Mth.DEG_TO_RAD, 0, 0));

        partDefinition.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(0, 21).mirror()
                .addBox(-1.5f, 0, -1, 3, 2, 2), PartPose.offsetAndRotation(0, 17.3f, 1.7f, -41 * Mth.DEG_TO_RAD, 0, 0));
        partDefinition.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(0, 25).mirror()
                .addBox(-1, -0.5f, -2, 2, 1, 2), PartPose.offsetAndRotation(0, 18.8f, 1, -7 * Mth.DEG_TO_RAD, 0, 0));
        partDefinition.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(0, 28).mirror()
                .addBox(-0.5f, -0.5f, -2, 1, 1, 2), PartPose.offsetAndRotation(0, 18.6f, -0.8f, -30 * Mth.DEG_TO_RAD, 0, 0));

        partDefinition.addOrReplaceChild("leftarm", CubeListBuilder.create().texOffs(60, 0).mirror()
                .addBox(-0.5f, 0, -0.5f, 1, 2, 1), PartPose.offsetAndRotation(1.4f, 14.6f, 2, -69 * Mth.DEG_TO_RAD, -12 * Mth.DEG_TO_RAD, 0));
        partDefinition.addOrReplaceChild("rightarm", CubeListBuilder.create().texOffs(60, 0).mirror()
                .addBox(-0.5f, 0, -0.5f, 1, 2, 1), PartPose.offsetAndRotation(-1.4f, 14.6f, 2, -69 * Mth.DEG_TO_RAD, 12 * Mth.DEG_TO_RAD, 0));
        partDefinition.addOrReplaceChild("left_foot", CubeListBuilder.create().texOffs(60, 3).mirror()
                .addBox(-0.5f, 0, -0.5f, 1, 2, 1), PartPose.offsetAndRotation(1, 17.4f, 1.4f, -69 * Mth.DEG_TO_RAD, -56 * Mth.DEG_TO_RAD, 0));
        partDefinition.addOrReplaceChild("rightfoot", CubeListBuilder.create().texOffs(60, 3).mirror()
                .addBox(-0.5f, 0, -0.5f, 1, 2, 1), PartPose.offsetAndRotation(-1, 17.4f, 1.4f, -69 * Mth.DEG_TO_RAD, 56 * Mth.DEG_TO_RAD, 0));
        return LayerDefinition.create(meshDefinition, 64, 32);
    }
}
