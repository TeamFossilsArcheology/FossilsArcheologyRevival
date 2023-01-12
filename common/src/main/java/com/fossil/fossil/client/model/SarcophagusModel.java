package com.fossil.fossil.client.model;


import com.fossil.fossil.Fossil;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;

public class SarcophagusModel {
    public static final ResourceLocation TEXTURE = new ResourceLocation(Fossil.MOD_ID, "textures/entity/sarcophagus.png");

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition root = meshDefinition.getRoot();

        root.addOrReplaceChild("bottom_back", CubeListBuilder.create().texOffs(0, 105).addBox(-7, 0, 0, 14, 1, 7),
                PartPose.offset(0, 23, 0));
        PartDefinition hinge = root.addOrReplaceChild("hinge", CubeListBuilder.create().texOffs(0, 0).addBox(0, 0, 0, 0, 0, 0),
                PartPose.offset(8, 0, 0));
        PartDefinition doorMain = hinge.addOrReplaceChild("door_main", CubeListBuilder.create().texOffs(0, 0).addBox(0, 0, 0, 0, 0, 0),
                PartPose.offset(-8, 0, 0));
        PartDefinition face = doorMain.addOrReplaceChild("face", CubeListBuilder.create().texOffs(24, 38).addBox(-4, -8, -7.599999904632568f, 8, 8, 3),
                PartPose.offset(0, -5, -3));
        PartDefinition nose = face.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(65, 0).addBox(-3, -2, -2, 6, 3, 2),
                PartPose.offset(0, -1, -7.5f));
        nose.addOrReplaceChild("tusk_right", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5f, -1, -0.5f, 1, 1, 1),
                PartPose.offset(2.5f, -2, -1.5f));
        nose.addOrReplaceChild("tusk_left", CubeListBuilder.create().texOffs(0, 0).addBox(-0.5f, -1, -0.5f, 1, 1, 1),
                PartPose.offset(-2.5f, -2, -1.5f));
        doorMain.addOrReplaceChild("bottom_front", CubeListBuilder.create().texOffs(0, 94).addBox(-7, 0, -7, 14, 1, 7),
                PartPose.offset(0, 23, 0));
        doorMain.addOrReplaceChild("ear_left", CubeListBuilder.create().texOffs(24, 17).mirror().addBox(-1, -6, -0.5f, 2, 6, 1),
                PartPose.offset(-3, -13, -8.600000381469727f));
        doorMain.addOrReplaceChild("ear_right", CubeListBuilder.create().texOffs(24, 17).addBox(-1, -6, -0.5f, 2, 6, 1),
                PartPose.offset(3, -13, -8.600000381469727f));
        doorMain.addOrReplaceChild("snout", CubeListBuilder.create().texOffs(24, 24).addBox(-2, -3, -9, 4, 3, 5),
                PartPose.offset(0, -13, -4));
        doorMain.addOrReplaceChild("tooth_right", CubeListBuilder.create().texOffs(24, 32).mirror().addBox(-0.5f, 0, -0.5f, 1, 2, 1),
                PartPose.offset(1.5f, -13, -12.5f));
        doorMain.addOrReplaceChild("tooth_left", CubeListBuilder.create().texOffs(24, 32).addBox(-0.5f, 0, -0.5f, 1, 2, 1),
                PartPose.offset(-1.5f, -13, -12.5f));
        doorMain.addOrReplaceChild("scarab", CubeListBuilder.create().texOffs(116, 0).addBox(-2, -2.799999952316284f, -2, 4, 6, 2),
                PartPose.offset(0, 0, -8));

        root.addOrReplaceChild("side_top_back_right", CubeListBuilder.create().texOffs(57, 37).addBox(0, -9, 0, 1, 9, 8),
                PartPose.offsetAndRotation(7.400000095367432f, 1, 0, 0, -0, -0.01745329238474369f));
        root.addOrReplaceChild("side_top_back_left", CubeListBuilder.create().texOffs(57, 37).mirror().addBox(-1, -11, 0, 1, 9, 8),
                PartPose.offsetAndRotation(-7.400000095367432f, 3, 0, 0, -0, 0.01745329238474369f));
        doorMain.addOrReplaceChild("side_top_front_right", CubeListBuilder.create().texOffs(0, 37).mirror().addBox(0, -9, -8, 1, 9, 8),
                PartPose.offsetAndRotation(7.400000095367432f, 1, 0, 0, -0, -0.01745329238474369f));
        doorMain.addOrReplaceChild("side_top_front_left", CubeListBuilder.create().texOffs(0, 37).addBox(-1, -11, -8, 1, 9, 8),
                PartPose.offsetAndRotation(-7.400000095367432f, 3, 0, 0, -0, 0.01745329238474369f));
        root.addOrReplaceChild("side_bottom_back_right", CubeListBuilder.create().texOffs(57, 55).addBox(0.23999999463558197f, -12, 0, 1, 23, 8),
                PartPose.offsetAndRotation(7, 13, 0, 0, -0, 0.01745329238474369f));
        root.addOrReplaceChild("side_bottom_back_left", CubeListBuilder.create().texOffs(57, 55).mirror().addBox(-1, -23, 0, 1, 23, 8),
                PartPose.offsetAndRotation(-7, 24, 0, 0, -0, -0.01745329238474369f));
        doorMain.addOrReplaceChild("side_bottom_front_right", CubeListBuilder.create().texOffs(0, 55).mirror().addBox(0.23999999463558197f, -12, -8, 1, 23, 8),
                PartPose.offsetAndRotation(7, 13, 0, 0, -0, 0.01745329238474369f));
        doorMain.addOrReplaceChild("side_bottom_front_left", CubeListBuilder.create().texOffs(0, 55).addBox(-1, -23, -8, 1, 23, 8),
                PartPose.offsetAndRotation(-7, 24, 0, 0, -0, -0.01745329238474369f));
        doorMain.addOrReplaceChild("wall_front_1", CubeListBuilder.create().texOffs(20, 55).addBox(-8, -32, -1, 16, 32, 1),
                PartPose.offset(0, 24, -6.900000095367432f));
        root.addOrReplaceChild("top_back_right_1", CubeListBuilder.create().texOffs(57, 23).addBox(0, -5, 0, 1, 5, 8),
                PartPose.offsetAndRotation(7.199999809265137f, -7.900000095367432f, 0, 0, -0, -0.15707963705062866f));
        root.addOrReplaceChild("top_back_left_1", CubeListBuilder.create().texOffs(57, 23).mirror().addBox(-1, -5, 0, 1, 5, 8),
                PartPose.offsetAndRotation(-7.199999809265137f, -7.900000095367432f, 0, 0, -0, 0.15707963705062866f));
        doorMain.addOrReplaceChild("top_front_right_1", CubeListBuilder.create().texOffs(0, 23).addBox(0, -5, -8, 1, 5, 8),
                PartPose.offsetAndRotation(7.199999809265137f, -7.900000095367432f, 0, 0, -0, -0.15707963705062866f));
        doorMain.addOrReplaceChild("top_front_left_1", CubeListBuilder.create().texOffs(0, 23).mirror().addBox(-1, -5, -8, 1, 5, 8),
                PartPose.offsetAndRotation(-7.199999809265137f, -7.900000095367432f, 0, 0, 0, 0.15707963705062866f));
        root.addOrReplaceChild("top_back_left_2", CubeListBuilder.create().texOffs(57, 10).mirror().addBox(-1, -5, 0, 1, 5, 8),
                PartPose.offsetAndRotation(-6.599999904632568f, -12.5f, 0, 0, -0, 0.5759586691856384f));
        root.addOrReplaceChild("top_back_right_2", CubeListBuilder.create().texOffs(57, 10).addBox(0, -5, 0, 1, 5, 8),
                PartPose.offsetAndRotation(6.599999904632568f, -12.5f, 0, 0, -0, -0.5759586691856384f));
        doorMain.addOrReplaceChild("top_front_right_2", CubeListBuilder.create().texOffs(0, 10).addBox(0, -5, -8, 1, 5, 8),
                PartPose.offsetAndRotation(6.599999904632568f, -12.5f, 0, 0, -0, -0.5759586691856384f));
        doorMain.addOrReplaceChild("top_front_left_2", CubeListBuilder.create().texOffs(0, 10).mirror().addBox(-1, -5, -8, 1, 5, 8),
                PartPose.offsetAndRotation(-6.599999904632568f, -12.5f, 0, 0, -0, 0.5759586691856384f));
        root.addOrReplaceChild("top_back_right_3", CubeListBuilder.create().texOffs(40, 0).addBox(0, -5, 0, 1, 5, 8),
                PartPose.offsetAndRotation(4.400000095367432f, -16.299999237060547f, 0, 0, -0, -1.222428560256958f));
        root.addOrReplaceChild("top_back_left_3", CubeListBuilder.create().texOffs(40, 0).mirror().addBox(-1, -5, 0, 1, 5, 8),
                PartPose.offsetAndRotation(-4.400000095367432f, -16.299999237060547f, 0, 0, -0, 1.222428560256958f));
        doorMain.addOrReplaceChild("top_front_right_3", CubeListBuilder.create().texOffs(20, 0).addBox(0, -5, -8, 1, 5, 8),
                PartPose.offsetAndRotation(4.400000095367432f, -16.299999237060547f, 0, 0, 0, -1.222428560256958f));
        doorMain.addOrReplaceChild("top_front_left_3", CubeListBuilder.create().texOffs(20, 0).mirror().addBox(-1, -5, -8, 1, 5, 8),
                PartPose.offsetAndRotation(-4.400000095367432f, -16.299999237060547f, 0, 0, -0, 1.222428560256958f));
        root.addOrReplaceChild("wall_back_2", CubeListBuilder.create().texOffs(81, 44).mirror().addBox(-7.5f, -4, 0, 15, 4, 1),
                PartPose.offset(0, -8, 6.900000095367432f));
        doorMain.addOrReplaceChild("wall_front_2", CubeListBuilder.create().texOffs(81, 44).addBox(-7.5f, -4, -1, 15, 4, 1),
                PartPose.offset(0, -8, -6.900000095367432f));
        root.addOrReplaceChild("wall_back_1", CubeListBuilder.create().texOffs(80, 53).addBox(-8, -32, 1, 16, 32, 1),
                PartPose.offset(0, 24, 5.900000095367432f));
        root.addOrReplaceChild("wall_back_3", CubeListBuilder.create().texOffs(83, 40).mirror().addBox(-6.5f, -2, 0, 13, 2, 1),
                PartPose.offset(0, -12, 6.900000095367432f));
        doorMain.addOrReplaceChild("wall_front_3", CubeListBuilder.create().texOffs(83, 40).addBox(-6.5f, -2, -1, 13, 2, 1),
                PartPose.offset(0, -12, -6.900000095367432f));
        root.addOrReplaceChild("wall_back_4", CubeListBuilder.create().texOffs(85, 36).addBox(-5.5f, -2, 0, 11, 2, 1),
                PartPose.offset(0, -14, 6.900000095367432f));
        doorMain.addOrReplaceChild("wall_front_4", CubeListBuilder.create().texOffs(85, 36).addBox(-5.5f, -2, -1, 11, 2, 1),
                PartPose.offset(0, -14, -6.900000095367432f));
        root.addOrReplaceChild("wall_back_5", CubeListBuilder.create().texOffs(87, 33).mirror().addBox(-4.5f, -2, 0, 9, 1, 1),
                PartPose.offset(0, -15, 6.900000095367432f));
        doorMain.addOrReplaceChild("wall_front_5", CubeListBuilder.create().texOffs(87, 33).addBox(-4.5f, -2, -1, 9, 1, 1),
                PartPose.offset(0, -15, -6.900000095367432f));
        root.addOrReplaceChild("wall_back_6", CubeListBuilder.create().texOffs(91, 30).mirror().addBox(-2.5f, -1, 0, 5, 1, 1),
                PartPose.offset(0, -17, 6.900000095367432f));
        doorMain.addOrReplaceChild("wall_front_6", CubeListBuilder.create().texOffs(91, 30).addBox(-2.5f, -1, -1, 5, 1, 1),
                PartPose.offset(0, -17, -6.900000095367432f));

        return LayerDefinition.create(meshDefinition, 128, 128);
    }
}
