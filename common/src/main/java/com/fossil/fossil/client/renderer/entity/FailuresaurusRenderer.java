package com.fossil.fossil.client.renderer.entity;

import com.fossil.fossil.client.model.FailuresaurusModel;
import com.fossil.fossil.entity.monster.Failuresaurus;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib3.renderers.geo.GeoEntityRenderer;

public class FailuresaurusRenderer extends GeoEntityRenderer<Failuresaurus> {
    public FailuresaurusRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new FailuresaurusModel());
    }
}
