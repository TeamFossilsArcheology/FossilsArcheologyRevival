package com.fossil.fossil.fabric.mixins;

import com.fossil.fossil.entity.prehistoric.parts.MultiPart;
import com.fossil.fossil.fabric.MultiPartEntityHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;

@Mixin(EntityHitResult.class)
public class EntityHitResultMixin implements MultiPartEntityHitResult {
    @Unique
    @Nullable
    private MultiPart part;

    @Override
    public void fossilsArcheologyRevival$setMultiPart(MultiPart part) {
        this.part = part;
    }

    @Override
    public MultiPart fossilsArcheologyRevival$getMultiPart() {
        return part;
    }
}
