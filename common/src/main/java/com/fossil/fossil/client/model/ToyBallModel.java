package com.fossil.fossil.client.model;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.entity.ToyBall;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Vector3f;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;

public class ToyBallModel extends EntityModel<ToyBall> {

    public static final ResourceLocation TEXTURE = new ResourceLocation(Fossil.MOD_ID, "textures/entity/toy/ball_white.png");

    private final ModelPart model = createBodyLayer().bakeRoot();
    private float rotationX;
    private float[] rgb;

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition root = meshDefinition.getRoot();
        root.addOrReplaceChild("ball", CubeListBuilder.create().addBox(-4, 0, -4, 8, 8, 8),
                PartPose.offset(0, 16, 0));
        return LayerDefinition.create(meshDefinition, 32, 16);
    }


    @Override
    public void setupAnim(ToyBall entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        rotationX = headPitch;
        rgb = entity.getColor().getTextureDiffuseColors();
    }

    @Override
    public void renderToBuffer(PoseStack stack, VertexConsumer buffer, int packedLight, int packedOverlay, float r, float g, float b, float alpha) {
        r = rgb[0];
        g = rgb[1];
        b = rgb[2];
        stack.pushPose();
        stack.translate(0, 1.25, 0);
        stack.mulPose(Vector3f.XP.rotationDegrees(rotationX));
        stack.translate(0, -1.25, 0);
        model.render(stack, buffer, packedLight, packedOverlay, r, g, b, alpha);
        stack.popPose();
    }
}
