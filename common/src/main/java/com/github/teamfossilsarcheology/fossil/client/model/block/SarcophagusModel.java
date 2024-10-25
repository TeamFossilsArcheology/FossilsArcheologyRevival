package com.github.teamfossilsarcheology.fossil.client.model.block;


import com.github.teamfossilsarcheology.fossil.FossilMod;
import net.minecraft.client.model.geom.PartPose;
import net.minecraft.client.model.geom.builders.CubeListBuilder;
import net.minecraft.client.model.geom.builders.LayerDefinition;
import net.minecraft.client.model.geom.builders.MeshDefinition;
import net.minecraft.client.model.geom.builders.PartDefinition;
import net.minecraft.resources.ResourceLocation;

public class SarcophagusModel {
    public static final ResourceLocation UNAWAKENED = FossilMod.location("textures/entity/sarcophagus.png");
    public static final ResourceLocation AWAKENED = FossilMod.location("textures/entity/sarcophagus_awakened.png");

    public static LayerDefinition createBodyLayer() {
        MeshDefinition meshDefinition = new MeshDefinition();
        PartDefinition partdefinition = meshDefinition.getRoot();
        PartDefinition root = partdefinition.addOrReplaceChild("root", CubeListBuilder.create(), PartPose.offset(0, 0, 6));
        PartDefinition front = root.addOrReplaceChild("front", CubeListBuilder.create().texOffs(112, 0).addBox(0, 0, -4, 14, 24, 4), PartPose.offset(-7, 0, -10));
        PartDefinition lid = front.addOrReplaceChild("lid", CubeListBuilder.create().texOffs(52, 32).addBox(0, -11, -4, 18, 24, 4), PartPose.offset(-2, -13, 0));
        lid.addOrReplaceChild("top_front_roof", CubeListBuilder.create().texOffs(66, 14).addBox(-7, -2, -4, 14, 1, 4), PartPose.offset(9, -10, 0));
        PartDefinition head = lid.addOrReplaceChild("head", CubeListBuilder.create().texOffs(111, 30).addBox(-4, -3, -1, 8, 8, 4), PartPose.offset(9, -4, -7));
        PartDefinition nose = head.addOrReplaceChild("nose", CubeListBuilder.create().texOffs(111, 43).addBox(-2, -0.5f, -6, 4, 5, 4), PartPose.offset(0, 0, 1));
        nose.addOrReplaceChild("tooth_right", CubeListBuilder.create().texOffs(1, 34).addBox(-0.5f, 0, -0.5f, 1, 2, 1), PartPose.offset(2, 2.5f, -4.5f));
        nose.addOrReplaceChild("tooth_left", CubeListBuilder.create().texOffs(1, 34).addBox(-0.5f, 0, -0.5f, 1, 2, 1), PartPose.offset(-2, 2.5f, -4.5f));
        head.addOrReplaceChild("ear_right", CubeListBuilder.create().texOffs(129, 44).mirror().addBox(1.5f, -7, 2, 2, 4, 1).mirror(false), PartPose.ZERO);
        head.addOrReplaceChild("ear_left", CubeListBuilder.create().texOffs(129, 44).addBox(-3.5f, -7, 2, 2, 4, 1), PartPose.ZERO);
        PartDefinition scarab = lid.addOrReplaceChild("scarab", CubeListBuilder.create().texOffs(78, 27).addBox(-1.5f, -1, -2, 3, 2, 2), PartPose.offsetAndRotation(9, 8, -5, -1.5708f, -3.1416f, 0));
        PartDefinition abdomen = scarab.addOrReplaceChild("abdomen", CubeListBuilder.create().texOffs(71, 21).addBox(-1.5f, -2, 0, 3, 2, 3), PartPose.offset(0, 1, 0));
        PartDefinition elythra_right = abdomen.addOrReplaceChild("elythra_right", CubeListBuilder.create().texOffs(87, 21).mirror().addBox(-2, -1, 0, 2, 3, 4).mirror(false), PartPose.offsetAndRotation(0, -2, 0, 0, 0.0002f, 0.0002f));
        PartDefinition wing1_right = elythra_right.addOrReplaceChild("wing1_right", CubeListBuilder.create().texOffs(61, 23).addBox(-2, 0, 0, 3, 0, 4), PartPose.ZERO);
        wing1_right.addOrReplaceChild("wing2_right", CubeListBuilder.create().texOffs(63, 19).addBox(-1, 0, -3, 3, 0, 3), PartPose.offset(0, 0, 3.5f));
        PartDefinition elythra_left = abdomen.addOrReplaceChild("elythra_left", CubeListBuilder.create().texOffs(87, 21).addBox(0, -1, 0, 2, 3, 4), PartPose.offset(0, -2, 0));
        PartDefinition wing1_left = elythra_left.addOrReplaceChild("wing1_left", CubeListBuilder.create().texOffs(61, 23).mirror().addBox(-1, 0, 0, 3, 0, 4).mirror(false), PartPose.ZERO);
        wing1_left.addOrReplaceChild("wing2_left", CubeListBuilder.create().texOffs(63, 19).mirror().addBox(-2, 0, -3, 3, 0, 3).mirror(false), PartPose.offset(0, 0, 3.5f));
        abdomen.addOrReplaceChild("leg2_right", CubeListBuilder.create().texOffs(82, 19).addBox(-2, -0.5f, -0.5f, 3, 1, 1), PartPose.offsetAndRotation(-1.7f, -0.5f, 0.5f, 0, -0.0391f, 0));
        abdomen.addOrReplaceChild("leg3_right", CubeListBuilder.create().texOffs(82, 19).addBox(-2, -0.5f, -0.5f, 3, 1, 1), PartPose.offsetAndRotation(-1.4f, -0.5f, 1.5f, 0, 0.4691f, 0));
        abdomen.addOrReplaceChild("leg2_left", CubeListBuilder.create().texOffs(82, 19).mirror().addBox(-1, -0.5f, -0.5f, 3, 1, 1).mirror(false), PartPose.offsetAndRotation(1.7f, -0.5f, 0.5f, 0, 0.0391f, 0));
        abdomen.addOrReplaceChild("leg3_left", CubeListBuilder.create().texOffs(82, 19).mirror().addBox(-1, -0.5f, -0.5f, 3, 1, 1).mirror(false), PartPose.offsetAndRotation(1.4f, -0.5f, 1.5f, 0, -0.4691f, 0));
        scarab.addOrReplaceChild("leg1_right", CubeListBuilder.create().texOffs(82, 19).addBox(-2, -0.5f, -0.5f, 3, 1, 1), PartPose.offsetAndRotation(-1.5f, 0.5f, -0.5f, 0, -0.43f, 0));
        scarab.addOrReplaceChild("leg1_left", CubeListBuilder.create().texOffs(82, 19).mirror().addBox(-1, -0.5f, -0.5f, 3, 1, 1).mirror(false), PartPose.offsetAndRotation(1.5f, 0.5f, -0.5f, 0, 0.43f, 0));
        PartDefinition back = root.addOrReplaceChild("back", CubeListBuilder.create(), PartPose.ZERO);
        PartDefinition top = back.addOrReplaceChild("top", CubeListBuilder.create().texOffs(0, 32).addBox(-9, -11, -6, 18, 24, 7), PartPose.offset(0, -13, 0));
        top.addOrReplaceChild("top_back_roof", CubeListBuilder.create().texOffs(60, 0).addBox(-7, -2, -6, 14, 1, 11), PartPose.offset(0, -10, -4));
        back.addOrReplaceChild("bottom", CubeListBuilder.create().texOffs(0, 0).addBox(-7, 0, -6, 14, 24, 7), PartPose.ZERO);
        PartDefinition side = root.addOrReplaceChild("side", CubeListBuilder.create(), PartPose.ZERO);
        side.addOrReplaceChild("side_bottom_left", CubeListBuilder.create().texOffs(97, 31).addBox(-1, 0, -6, 2, 24, 4), PartPose.offset(-6, 0, -4));
        side.addOrReplaceChild("side_bottom_right", CubeListBuilder.create().texOffs(97, 31).mirror().addBox(-1, 0, -6, 2, 24, 4).mirror(false), PartPose.offset(6, 0, -4));
        side.addOrReplaceChild("side_top_left", CubeListBuilder.create().texOffs(45, 0).addBox(-1, -11, -6, 3, 24, 4), PartPose.offset(-8, -13, -4));
        side.addOrReplaceChild("side_top_right", CubeListBuilder.create().texOffs(45, 0).mirror().addBox(-2, -11, -6, 3, 24, 4).mirror(false), PartPose.offset(8, -13, -4));
        side.addOrReplaceChild("side_bottom_middle", CubeListBuilder.create().texOffs(129, 51).addBox(-5, 0, -6, 10, 2, 6), PartPose.offset(0, 22, -4));


        return LayerDefinition.create(meshDefinition, 164, 64);
    }
}
