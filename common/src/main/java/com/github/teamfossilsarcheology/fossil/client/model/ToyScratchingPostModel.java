package com.github.teamfossilsarcheology.fossil.client.model;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.entity.ToyBase;
import com.github.teamfossilsarcheology.fossil.entity.ToyScratchingPost;
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
import net.minecraft.world.level.block.state.properties.WoodType;

import java.util.Map;
import java.util.stream.Collectors;

public class ToyScratchingPostModel extends EntityModel<ToyScratchingPost> {
    //This should prevent wood types from other mods
    public static final Map<String, ResourceLocation> TEXTURES = WoodType.values().filter(ToyBase::isVanillaWood).collect(Collectors.toMap(WoodType::name,
            woodType -> FossilMod.location("textures/entity/toy/scratching_post_" + woodType.name() + ".png")));

    private final ModelPart model = createBodyLayer().bakeRoot();

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition root = meshDefinition.getRoot();
        root.addOrReplaceChild("Wool", CubeListBuilder.create().texOffs(28, 35).addBox(-4.5f, -30.6f, -4.5f, 9, 20, 9), PartPose.offset(0, 24.2f, 0));
        root.addOrReplaceChild("Post", CubeListBuilder.create().texOffs(0, 4).addBox(-4, -32, -4, 8, 32, 8), PartPose.offset(0, 24.2f, 0));
        root.addOrReplaceChild("Base", CubeListBuilder.create().texOffs(24, 0).addBox(-5, 0, -5, 10, 2, 10), PartPose.offset(0, 22, 0));

        return LayerDefinition.create(meshDefinition, 64, 64);
    }

    @Override
    public void setupAnim(ToyScratchingPost entity, float partialTick, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch) {

    }

    @Override
    public void renderToBuffer(PoseStack stack, VertexConsumer buffer, int packedLight, int packedOverlay, float r, float g, float b, float alpha) {
        model.render(stack, buffer, packedLight, packedOverlay, r, g, b, alpha);
    }
}
