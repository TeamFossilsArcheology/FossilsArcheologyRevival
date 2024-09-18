package com.fossil.fossil.forge.mixins;

import com.fossil.fossil.entity.prehistoric.parts.MultiPart;
import com.fossil.fossil.forge.MultiPartEntityHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(EntityHitResult.class)
public class EntityHitResultMixin implements MultiPartEntityHitResult {
    @Unique
    @Nullable
    private MultiPart fossilsArcheologyRevival$part;

    @Override
    public void fossilsArcheologyRevival$setMultiPart(MultiPart part) {
        this.fossilsArcheologyRevival$part = part;
    }

    @Override
    public MultiPart fossilsArcheologyRevival$getMultiPart() {
        return fossilsArcheologyRevival$part;
    }
}
