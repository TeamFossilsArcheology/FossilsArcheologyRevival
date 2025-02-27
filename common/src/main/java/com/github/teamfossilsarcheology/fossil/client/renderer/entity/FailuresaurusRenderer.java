package com.github.teamfossilsarcheology.fossil.client.renderer.entity;

import com.github.teamfossilsarcheology.fossil.client.model.FailuresaurusModel;
import com.github.teamfossilsarcheology.fossil.entity.monster.Failuresaurus;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import software.bernie.geckolib.renderer.GeoEntityRenderer;

public class FailuresaurusRenderer extends GeoEntityRenderer<Failuresaurus> {
    public FailuresaurusRenderer(EntityRendererProvider.Context renderManager) {
        super(renderManager, new FailuresaurusModel());
    }
}
