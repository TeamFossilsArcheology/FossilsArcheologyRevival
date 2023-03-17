package com.fossil.fossil.fabric;

import com.fossil.fossil.fabric.mixins.ServerLevelMixin;
import net.minecraft.world.entity.Entity;

import java.util.ArrayList;
import java.util.Collection;

/**
 * Fabric has no PartEntity so we mixin our own
 *
 * @see ServerLevelMixin
 * @see com.fossil.fossil.fabric.mixins.EntityCallbacksMixin
 * @see com.fossil.fossil.fabric.mixins.LevelMixin
 */
public interface MultiPartServerLevel {
    default Collection<Entity> getMultiParts() {
        return new ArrayList<>();
    }

    default void addMultiPart(Entity part) {
    }

    default void removeMultiPart(Entity part) {

    }
}
