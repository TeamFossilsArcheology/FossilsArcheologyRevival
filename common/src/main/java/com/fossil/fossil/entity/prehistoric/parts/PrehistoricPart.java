package com.fossil.fossil.entity.prehistoric.parts;

import com.fossil.fossil.entity.prehistoric.base.Prehistoric;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.entity.Entity;

public class PrehistoricPart {

    @ExpectPlatform
    public static <T extends Prehistoric> Entity get(T entity, float f, float g) {
        throw new AssertionError();
    }
}
