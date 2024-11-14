package com.github.teamfossilsarcheology.fossil.client.model;

import com.github.teamfossilsarcheology.fossil.entity.prehistoric.base.PrehistoricAnimatable;
import net.minecraft.world.entity.Mob;
import software.bernie.geckolib3.model.AnimatedGeoModel;

/**
 * Really just there to have an easy target for our {@code AnimationProcessor} mixin
 */
public abstract class PrehistoricAnimatableModel<T extends Mob & PrehistoricAnimatable<?>> extends AnimatedGeoModel<T> {
}
