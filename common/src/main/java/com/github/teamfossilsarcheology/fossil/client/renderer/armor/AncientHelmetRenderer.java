package com.github.teamfossilsarcheology.fossil.client.renderer.armor;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.item.AncientHelmetItem;
import software.bernie.geckolib.model.DefaultedItemGeoModel;
import software.bernie.geckolib.renderer.GeoArmorRenderer;

public class AncientHelmetRenderer extends GeoArmorRenderer<AncientHelmetItem> {
    public AncientHelmetRenderer() {
        super(new DefaultedItemGeoModel<>(FossilMod.location("armor/ancient_helmet")));
    }
}
