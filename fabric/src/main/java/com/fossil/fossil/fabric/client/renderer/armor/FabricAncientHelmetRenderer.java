package com.fossil.fossil.fabric.client.renderer.armor;

import com.fossil.fossil.fabric.client.model.armor.FabricAncientHelmetModel;
import com.fossil.fossil.item.fabric.AncientHelmetItemImpl;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class FabricAncientHelmetRenderer extends GeoArmorRenderer<AncientHelmetItemImpl> {
    public FabricAncientHelmetRenderer() {
        super(new FabricAncientHelmetModel());

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
