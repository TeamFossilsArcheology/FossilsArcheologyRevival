package com.fossil.fossil.entity.prehistoric.base;

import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.Item;
import org.jetbrains.annotations.Nullable;

public interface EntityInfo {
    EntityType<? extends Entity> entityType();

    PrehistoricMobType mobType();

    @Nullable Item getDNAResult();

    String toNbt();

    static EntityInfo fromNbt(String name) throws IllegalArgumentException {
        //Somewhat cursed, but I want to separate vanilla and our mobs, and I am to lazy to do this properly
        EntityInfo toReturn;
        try {
            toReturn = PrehistoricEntityType.valueOf(name);
        } catch (IllegalArgumentException e) {
            toReturn = VanillaEntityInfo.valueOf(name);
        }
        return toReturn;
    }
}
