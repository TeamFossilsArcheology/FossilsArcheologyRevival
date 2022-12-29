package com.fossil.fossil.client.model;// Made with Blockbench 4.5.2
// Exported for Minecraft version 1.17 - 1.18 with Mojang mappings
// Paste this class into your mod and generate all required imports


import com.fossil.fossil.Fossil;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import net.minecraft.client.model.EntityModel;
import net.minecraft.client.model.geom.ModelLayerLocation;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Entity;

public class TriceratopsModel<T extends Entity> extends EntityModel<T> {
	// This layer location should be baked with EntityRendererProvider.Context in the entity renderer and passed into this model's constructor
	public static final ModelLayerLocation LAYER_LOCATION = new ModelLayerLocation(new ResourceLocation(Fossil.MOD_ID, "triceratops"), "main");
	private final ModelPart upperBody;
	private final ModelPart rightFrontThigh;
	private final ModelPart leftFrontThigh;
	private final ModelPart rightHindThigh;
	private final ModelPart leftHindThigh;

	public TriceratopsModel(ModelPart root) {
		this.upperBody = root.getChild("upperBody");
		this.rightFrontThigh = root.getChild("rightFrontThigh");
		this.leftFrontThigh = root.getChild("leftFrontThigh");
		this.rightHindThigh = root.getChild("rightHindThigh");
		this.leftHindThigh = root.getChild("leftHindThigh");
	}

	public static LayerDefinition createBodyLayer() {
		MeshDefinition meshdefinition = new MeshDefinition();
		PartDefinition partdefinition = meshdefinition.getRoot();

		PartDefinition upperBody = partdefinition.addOrReplaceChild("upperBody", CubeListBuilder.create().texOffs(29, 0).addBox(-2.5F, 0.0F, -6.0F, 5.0F, 7.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 11.5F, -0.1F, 0.3187F, 0.0F, 0.0F));

		PartDefinition lowerBody = upperBody.addOrReplaceChild("lowerBody", CubeListBuilder.create().texOffs(0, 0).addBox(-3.0F, -2.5F, 0.0F, 6.0F, 8.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 2.2F, -1.0F, -0.3643F, 0.0F, 0.0F));

		PartDefinition tail1 = lowerBody.addOrReplaceChild("tail1", CubeListBuilder.create().texOffs(24, 14).addBox(-2.5F, -0.2F, 0.0F, 5.0F, 5.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -2.2F, 7.5F, -0.4098F, 0.0F, 0.0F));

		PartDefinition tail2 = tail1.addOrReplaceChild("tail2", CubeListBuilder.create().texOffs(39, 20).addBox(-1.5F, 0.0F, -1.0F, 3.0F, 3.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 0.6F, 4.5F, -0.182F, 0.0F, 0.0F));

		PartDefinition tail3 = tail2.addOrReplaceChild("tail3", CubeListBuilder.create().texOffs(52, 24).addBox(-1.0F, -1.0F, -1.0F, 2.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.6F, 4.2F, 0.3643F, 0.0F, 0.0F));

		PartDefinition tailspikesL = tail1.addOrReplaceChild("tailspikesL", CubeListBuilder.create().texOffs(29, 42).addBox(0.0F, -2.0F, 0.0F, 3.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, -0.2F, 0.0F, 0.0F, 0.0F, 0.0F));

		PartDefinition tailspikesR = tail1.addOrReplaceChild("tailspikesR", CubeListBuilder.create().texOffs(27, 42).addBox(-3.0F, -2.0F, 0.0F, 3.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, -0.2F, 0.0F, 0.0F, 0.0F, 0.0F));

		PartDefinition basinspikesL = lowerBody.addOrReplaceChild("basinspikesL", CubeListBuilder.create().texOffs(10, 42).addBox(0.0F, -2.0F, 0.0F, 3.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, -2.5F, 0.0F, 0.0F, -0.0073F, 0.0F));

		PartDefinition basinspikesR = lowerBody.addOrReplaceChild("basinspikesR", CubeListBuilder.create().texOffs(12, 42).addBox(-4.0F, -2.0F, 0.0F, 3.0F, 2.0F, 8.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.7F, -2.5F, 0.0F, 0.0F, 0.0F, 0.0F));

		PartDefinition neck = upperBody.addOrReplaceChild("neck", CubeListBuilder.create().texOffs(52, 0).addBox(-2.0F, -0.4F, -2.9F, 3.0F, 5.0F, 7.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 0.3F, -6.0F, -0.2731F, 0.0F, 0.0F));

		PartDefinition head = neck.addOrReplaceChild("head", CubeListBuilder.create().texOffs(73, 5).addBox(-2.0F, -2.0F, -3.0F, 4.0F, 4.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 1.8F, -2.0F, 0.4098F, 0.0F, 0.0F));

		PartDefinition beak = head.addOrReplaceChild("beak", CubeListBuilder.create().texOffs(76, 13).addBox(-1.5F, -1.0F, -3.5F, 3.0F, 3.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.2F, -3.6F, 0.182F, 0.0F, 0.0F));

		PartDefinition noseHorn = beak.addOrReplaceChild("noseHorn", CubeListBuilder.create().texOffs(32, 34).addBox(-0.5F, -0.5F, -2.2F, 1.0F, 1.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -0.1F, -1.6F, -1.1839F, 0.0F, 0.0F));

		PartDefinition frill = head.addOrReplaceChild("frill", CubeListBuilder.create().texOffs(52, 13).addBox(-2.0F, -7.0F, 0.0F, 4.0F, 7.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, -1.4F, -1.5F, -0.9105F, 0.0F, 0.0F));

		PartDefinition frillL2 = frill.addOrReplaceChild("frillL2", CubeListBuilder.create().texOffs(63, 13).mirror().addBox(-1.0F, -7.3F, -0.5F, 5.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)).mirror(false), PartPose.offsetAndRotation(-0.4F, 0.2F, 0.8F, -0.0175F, -0.2731F, 0.3643F));

		PartDefinition frillL = frill.addOrReplaceChild("frillL", CubeListBuilder.create().texOffs(63, 13).addBox(-4.0F, -7.3F, -0.5F, 5.0F, 6.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.4F, 0.2F, 0.8F, -0.0175F, 0.2705F, -0.3643F));

		PartDefinition leftHorn1 = head.addOrReplaceChild("leftHorn1", CubeListBuilder.create().texOffs(22, 25).addBox(-0.6F, -0.5F, -3.5F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.5F, -1.7F, 0.5F, -0.8051F, -0.0698F, 0.2094F));

		PartDefinition leftHorn2 = leftHorn1.addOrReplaceChild("leftHorn2", CubeListBuilder.create().texOffs(27, 26).addBox(-0.5F, -0.8F, -4.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.1F, 0.39F, -3.3F, 0.3187F, 0.0F, 0.0F));

		PartDefinition rightHorn1 = head.addOrReplaceChild("rightHorn1", CubeListBuilder.create().texOffs(22, 25).addBox(-0.4F, -0.5F, -3.5F, 1.0F, 2.0F, 3.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.5F, -1.7F, 0.5F, -0.8051F, 0.0698F, -0.2094F));

		PartDefinition rightHorn2 = rightHorn1.addOrReplaceChild("rightHorn2", CubeListBuilder.create().texOffs(27, 26).addBox(-0.5F, -0.8F, -4.5F, 1.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.1F, 0.39F, -3.3F, 0.3187F, 0.0F, 0.0F));

		PartDefinition beakbottom = head.addOrReplaceChild("beakbottom", CubeListBuilder.create().texOffs(76, 22).addBox(-1.0F, 0.0F, -3.5F, 2.0F, 1.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.0F, 1.5F, -2.8F, 0.0F, 0.0F, 0.0F));

		PartDefinition spikesL = upperBody.addOrReplaceChild("spikesL", CubeListBuilder.create().texOffs(1, 39).addBox(0.0F, -2.0F, -6.0F, 3.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

		PartDefinition spikesR = upperBody.addOrReplaceChild("spikesR", CubeListBuilder.create().texOffs(-1, 39).addBox(-3.0F, -2.0F, -6.0F, 3.0F, 2.0F, 6.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.0F, 0.0F, 0.0F, 0.0F, 0.0F, 0.0F));

		PartDefinition rightFrontThigh = partdefinition.addOrReplaceChild("rightFrontThigh", CubeListBuilder.create().texOffs(1, 17).addBox(-2.0F, -1.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.8F, 18.4F, -3.1F, 0.2094F, 0.0F, 0.0F));

		PartDefinition rightFrontLeg = rightFrontThigh.addOrReplaceChild("rightFrontLeg", CubeListBuilder.create().texOffs(1, 23).addBox(-0.5F, -1.0F, -4.1F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-1.3F, 1.6F, 0.2F, 1.3614F, 0.0F, 0.0F));

		PartDefinition rightFrontFoot = rightFrontLeg.addOrReplaceChild("rightFrontFoot", CubeListBuilder.create().texOffs(1, 30).addBox(-1.5F, -2.0F, -1.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 0.5F, -3.4F, 0.0F, 0.0F, 0.0F));

		PartDefinition leftFrontThigh = partdefinition.addOrReplaceChild("leftFrontThigh", CubeListBuilder.create().texOffs(1, 17).addBox(0.0F, -1.0F, -1.0F, 2.0F, 3.0F, 2.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.8F, 18.4F, -3.1F, 0.2094F, 0.0F, 0.0F));

		PartDefinition leftFrontLeg = leftFrontThigh.addOrReplaceChild("leftFrontLeg", CubeListBuilder.create().texOffs(1, 23).addBox(-1.5F, -1.0F, -4.1F, 2.0F, 2.0F, 4.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(1.3F, 1.6F, 0.2F, 1.3614F, 0.0F, 0.0F));

		PartDefinition leftFrontFoot = leftFrontLeg.addOrReplaceChild("leftFrontFoot", CubeListBuilder.create().texOffs(1, 30).addBox(-1.5F, -2.0F, -1.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 0.5F, -3.4F, 0.0F, 0.0F, 0.0F));

		PartDefinition rightHindThigh = partdefinition.addOrReplaceChild("rightHindThigh", CubeListBuilder.create().texOffs(12, 26).addBox(-2.0F, -1.0F, -3.0F, 2.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(-2.4F, 14.8F, 5.0F));

		PartDefinition rightHindLeg = rightHindThigh.addOrReplaceChild("rightHindLeg", CubeListBuilder.create().texOffs(22, 33).addBox(-1.5F, -1.0F, -4.5F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.4F, 4.6F, 1.5F, 1.5708F, 0.0F, 0.0F));

		PartDefinition righthindFoot = rightHindLeg.addOrReplaceChild("righthindFoot", CubeListBuilder.create().texOffs(13, 38).addBox(-1.5F, -2.0F, -1.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(-0.5F, 0.5F, -3.7F, 0.0F, 0.0F, 0.0F));

		PartDefinition leftHindThigh = partdefinition.addOrReplaceChild("leftHindThigh", CubeListBuilder.create().texOffs(12, 26).addBox(0.0F, -1.0F, -3.0F, 2.0F, 6.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offset(2.4F, 14.8F, 5.0F));

		PartDefinition leftHindLeg = leftHindThigh.addOrReplaceChild("leftHindLeg", CubeListBuilder.create().texOffs(22, 33).addBox(-0.5F, -1.0F, -4.5F, 2.0F, 2.0F, 5.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.4F, 4.6F, 1.5F, 1.5708F, 0.0F, 0.0F));

		PartDefinition lefthindFoot = leftHindLeg.addOrReplaceChild("lefthindFoot", CubeListBuilder.create().texOffs(13, 38).addBox(-1.5F, -2.0F, -1.0F, 3.0F, 2.0F, 1.0F, new CubeDeformation(0.0F)), PartPose.offsetAndRotation(0.5F, 0.5F, -3.7F, 0.0F, 0.0F, 0.0F));

		return LayerDefinition.create(meshdefinition, 97, 64);
	}

	@Override
	public void setupAnim(T entity, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

	}

	@Override
	public void renderToBuffer(PoseStack poseStack, VertexConsumer vertexConsumer, int packedLight, int packedOverlay, float red, float green, float blue, float alpha) {
		upperBody.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		rightFrontThigh.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leftFrontThigh.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		rightHindThigh.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
		leftHindThigh.render(poseStack, vertexConsumer, packedLight, packedOverlay, red, green, blue, alpha);
	}
}