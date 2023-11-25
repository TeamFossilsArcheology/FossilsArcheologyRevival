package com.fossil.fossil.forge.mixins;

import com.fossil.fossil.entity.monster.AnuBoss;
import com.fossil.fossil.world.dimension.ModDimensions;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.List;

@Mixin(Explosion.class)
public class ExplosionMixin {
    @Shadow
    @Final
    private List<BlockPos> toBlow;

    @Shadow
    @Final
    private Level level;

    @Inject(method = "explode", at = @At(value = "INVOKE", target = "Ljava/util/List;addAll(Ljava/util/Collection;)Z", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILSOFT)
    public void doNotAddMultiPart(CallbackInfo ci) {
        if (level.dimension() == ModDimensions.ANU_LAIR && level instanceof ServerLevel serverLevel) {
            AnuBoss.AnuLair anuLair = serverLevel.getDataStorage().get(c -> new AnuBoss.AnuLair(), "anu_lair");
            if (anuLair == null || !anuLair.isAnuKilled()) {
                toBlow.clear();
            }
        }
    }
}
