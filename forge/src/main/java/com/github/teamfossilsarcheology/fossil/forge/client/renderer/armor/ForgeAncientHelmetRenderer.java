package com.github.teamfossilsarcheology.fossil.forge.client.renderer.armor;

import com.github.teamfossilsarcheology.fossil.forge.client.model.armor.ForgeAncientHelmetModel;
import com.github.teamfossilsarcheology.fossil.item.forge.AncientHelmetItemImpl;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class ForgeAncientHelmetRenderer extends GeoArmorRenderer<AncientHelmetItemImpl> {
    public ForgeAncientHelmetRenderer() {
        super(new ForgeAncientHelmetModel());

        this.headBone = "armorHead";
        this.bodyBone = "armorBody";
        this.rightArmBone = "armorRightArm";
        this.leftArmBone = "armorLeftArm";
        this.rightLegBone = "armorLeftLeg";
        this.leftLegBone = "armorRightLeg";
        this.rightBootBone = "armorLeftBoot";
        this.leftBootBone = "armorRightBoot";
    }
}
