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
import net.minecraft.util.Mth;
import net.minecraft.world.level.block.state.properties.WoodType;

import java.util.Map;
import java.util.stream.Collectors;

public class ToyTetheredLogModel extends EntityModel<ToyTetheredLog> {
    public static final Map<String, ResourceLocation> TEXTURES = WoodType.values().filter(woodType -> !woodType.name().contains(":")).collect(Collectors.toMap(WoodType::name,
            woodType -> new ResourceLocation(Fossil.MOD_ID, "textures/entity/toy/log_swing_" + woodType.name() + ".png")));

    private final ModelPart model = createBodyLayer().bakeRoot();
    private final ModelPart rope1 = model.getChild("rope1");

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
    public void setupAnim(ToyTetheredLog entity, float partialTick, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {
        float rot = 0;
        if (entity.animationPlaying) {
            float tick = entity.animationTick + partialTick;
            rot = Mth.sin(1.5f * tick / Mth.PI) / (3.6f + tick / 10);
        }
        rope1.xRot = rot * entity.animationX;
        rope1.zRot = rot * entity.animationZ;
    }

    @Override
    public void renderToBuffer(PoseStack stack, VertexConsumer buffer, int packedLight, int packedOverlay, float r, float g, float b, float alpha) {
        model.render(stack, buffer, packedLight, packedOverlay, r, g, b, alpha);
    }
}
