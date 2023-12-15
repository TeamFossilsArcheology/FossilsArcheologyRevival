package com.fossil.fossil.client.renderer.armor;

import com.fossil.fossil.client.model.armor.AncientHelmetModel;
import com.fossil.fossil.item.AncientHelmetItem;
import software.bernie.geckolib3.renderers.geo.GeoArmorRenderer;

public class AncientHelmetRenderer extends GeoArmorRenderer<AncientHelmetItem> {
    public AncientHelmetRenderer() {
        super(new AncientHelmetModel());

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
