package com.fossil.fossil.client.model;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.entity.ToyTetheredLog;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;

public class ToyTetheredLogModel extends EntityModel<ToyTetheredLog> {
    public static final ResourceLocation TEXTURE = new ResourceLocation(Fossil.MOD_ID, "textures/entity/toy/log_swing_oak.png");

    private final ModelPart model = createBodyLayer().bakeRoot();

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition root = meshDefinition.getRoot();

        PartDefinition rope1 = root.addOrReplaceChild("rope1", CubeListBuilder.create().addBox(-1, 0, -1, 2, 5, 2), PartPose.offset(0, -9, 0));
        PartDefinition rope2 = rope1.addOrReplaceChild("rope2", CubeListBuilder.create().addBox(-1, 0, -1, 2, 5, 2), PartPose.offset(0, 5, 0));
        PartDefinition rope3 = rope2.addOrReplaceChild("rope3", CubeListBuilder.create().addBox(-1, 0, -1, 2, 5, 2), PartPose.offset(0, 5, 0));

        rope3.addOrReplaceChild("log", CubeListBuilder.create().addBox(-4, 0, -4, 8, 16, 8), PartPose.offset(0, 5, 0));

        return LayerDefinition.create(meshDefinition, 64, 32);
    }

    @Override
    public void setupAnim(ToyTetheredLog entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack stack, VertexConsumer buffer, int packedLight, int packedOverlay, float r, float g, float b, float alpha) {
        model.render(stack, buffer, packedLight, packedOverlay, r, g, b, alpha);
    }
}
