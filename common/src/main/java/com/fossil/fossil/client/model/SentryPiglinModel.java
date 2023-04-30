package com.fossil.fossil.client.model;

import com.fossil.fossil.entity.monster.SentryPiglin;
import net.minecraft.client.model.PlayerModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class SentryPiglinModel extends PlayerModel<SentryPiglin> {
    private final ModelPart rightEar;
    private final ModelPart leftEar;
    private final PartPose bodyDefault;
    private final PartPose headDefault;
    private final PartPose leftArmDefault;
    private final PartPose rightArmDefault;

    public SentryPiglinModel() {
        super(createBodyLayer().bakeRoot(), false);
        rightEar = head.getChild("right_ear");
        leftEar = head.getChild("left_ear");
        bodyDefault = body.storePose();
        headDefault = head.storePose();
        leftArmDefault = leftArm.storePose();
        rightArmDefault = rightArm.storePose();
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
                PartPose.offsetAndRotation(4.5f, -6, 0, 0, 0, -0.5235988f));
        head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(39, 6)
                        .addBox(-1, 0, -2, 1, 5, 4),
                PartPose.offsetAndRotation(-4.5f, -6, 0, 0, 0, 0.5235988f));
        root.addOrReplaceChild("hat", CubeListBuilder.create(), PartPose.ZERO);
        return LayerDefinition.create(meshDefinition, 64, 64);
    }
}
