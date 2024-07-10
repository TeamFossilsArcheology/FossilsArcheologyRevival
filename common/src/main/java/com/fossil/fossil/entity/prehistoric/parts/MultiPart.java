package com.fossil.fossil.entity.prehistoric.parts;

import com.fossil.fossil.entity.data.EntityHitboxManager;
import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.phys.Vec3;

/**
 *
 */
public interface MultiPart {

    @ExpectPlatform
    static <T extends Prehistoric> MultiPart get(T entity, EntityHitboxManager.Hitbox hitbox) {
        throw new AssertionError();
    }

    Prehistoric getParent();

    Entity getEntity();

    Vec3 getOffset();

    void setOverride(AnimationOverride animationOverride);

    AnimationOverride getOverride();

    default void updatePosition() {
        Entity entity = getEntity();
        entity.xo = entity.getX();
        entity.yo = entity.getY();
        entity.zo = entity.getZ();
        entity.xOld = entity.xo;
        entity.yOld = entity.yo;
        entity.zOld = entity.zo;
        Vec3 offset = getOffset();
        AnimationOverride animationOverride = getOverride();
        if (animationOverride != null) {
            entity.setPos(getParent().position().add(animationOverride.localPos()));
        } else {
            Vec3 newPos = getParent().position().add(new Vec3(offset.x, offset.y, offset.z).yRot(-getParent().yBodyRot * Mth.DEG_TO_RAD).scale(getParent().getScale()));
            entity.setPos(newPos);
        }
    }
}
