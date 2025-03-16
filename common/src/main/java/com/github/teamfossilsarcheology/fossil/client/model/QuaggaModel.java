package com.github.teamfossilsarcheology.fossil.client.model;

import com.github.teamfossilsarcheology.fossil.entity.Quagga;
import net.minecraft.client.model.HorseModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;

public class QuaggaModel<T extends Quagga> extends HorseModel<T> {
    private final ModelPart leftChest;
    private final ModelPart rightChest;

    public QuaggaModel() {
        super(createBodyLayer().bakeRoot());
        this.leftChest = body.getChild("left_chest");
        this.rightChest = body.getChild("right_chest");
    }

    public static LayerDefinition createBodyLayer() {
        MeshDefinition horseMesh = HorseModel.createBodyMesh(CubeDeformation.NONE);
        PartDefinition root = horseMesh.getRoot();
        PartDefinition body = root.getChild("body");
        CubeListBuilder chestCube = CubeListBuilder.create().texOffs(26, 21).addBox(-4, 0, -2, 8, 8, 3);
        body.addOrReplaceChild("left_chest", chestCube, PartPose.offsetAndRotation(6, -8, 0, 0, -1.5707964F, 0));
        body.addOrReplaceChild("right_chest", chestCube, PartPose.offsetAndRotation(-6, -8, 0, 0, 1.5707964F, 0));
        PartDefinition head = root.getChild("head_parts").getChild("head");
        head.addOrReplaceChild("left_ear", CubeListBuilder.create().texOffs(19, 16).addBox(0.55F, -13, 4, 2, 3, 1, new CubeDeformation(-0.001F)), PartPose.ZERO);
        head.addOrReplaceChild("right_ear", CubeListBuilder.create().texOffs(19, 16).addBox(-2.55F, -13, 4, 2, 3, 1, new CubeDeformation(-0.001F)), PartPose.ZERO);
        return LayerDefinition.create(horseMesh, 64, 64);
    }

    @Override
    public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        if (entity.hasChest()) {
            leftChest.visible = true;
            rightChest.visible = true;
        } else {
            leftChest.visible = false;
            rightChest.visible = false;
        }
    }
}
