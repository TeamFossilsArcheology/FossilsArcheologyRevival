package com.github.teamfossilsarcheology.fossil.client.model;

import com.github.teamfossilsarcheology.fossil.entity.monster.SentryPiglin;
import net.minecraft.client.model.PiglinModel;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.util.Mth;

public class SentryPiglinModel extends PiglinModel<SentryPiglin> {

    public SentryPiglinModel() {
        super(createBodyLayer().bakeRoot());
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = PlayerModel.createMesh(CubeDeformation.NONE, false);
        PartDefinition root = meshDefinition.getRoot();
        root.addOrReplaceChild("body", CubeListBuilder.create().texOffs(16, 16)
                .addBox(-4, 0, -2, 8, 12, 4), PartPose.ZERO);
        PartDefinition head = root.addOrReplaceChild("head", CubeListBuilder.create()
                .addBox(-5, -8, -4, 10, 8, 8).texOffs(31, 1)
                .addBox(-2, -4, -5, 4, 4, 1).texOffs(2, 4)
                .addBox(2, -2, -5, 1, 2, 1).texOffs(2, 0)
                .addBox(-3, -2, -5, 1, 2, 1), PartPose.ZERO);
        head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(51, 6)
                        .addBox(0, 0, -2, 1, 5, 4),
                PartPose.offsetAndRotation(4.5f, -6, 0, 0, 0, -30 * Mth.DEG_TO_RAD));
        head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(39, 6)
                        .addBox(-1, 0, -2, 1, 5, 4),
                PartPose.offsetAndRotation(-4.5f, -6, 0, 0, 0, 30 * Mth.DEG_TO_RAD));
        root.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
        return LayerDefinition.create(meshDefinition, 64, 64);
    }
}
