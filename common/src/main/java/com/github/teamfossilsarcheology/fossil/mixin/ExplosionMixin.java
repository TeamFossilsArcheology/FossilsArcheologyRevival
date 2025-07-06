package com.github.teamfossilsarcheology.fossil.mixin;

import com.github.teamfossilsarcheology.fossil.entity.monster.AnuBoss;
import com.github.teamfossilsarcheology.fossil.world.dimension.ModDimensions;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
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

@Mixin(Explosion.class)
public class ExplosionMixin {
    @Shadow
    @Final
    private ObjectArrayList<BlockPos> toBlow;

    @Shadow
    @Final
    private Level level;

    @Inject(method = "explode", at = @At(value = "INVOKE", target = "Lit/unimi/dsi/fastutil/objects/ObjectArrayList;addAll(Ljava/util/Collection;)Z", shift = At.Shift.AFTER), locals = LocalCapture.CAPTURE_FAILSOFT)
    public void preventExplosion(CallbackInfo ci) {
        if (level.dimension() == ModDimensions.ANU_LAIR && level instanceof ServerLevel serverLevel) {
            AnuBoss.AnuLair anuLair = serverLevel.getDataStorage().get(c -> new AnuBoss.AnuLair(), "anu_lair");
            if (anuLair == null || !anuLair.isAnuKilled()) {
                toBlow.clear();
            }
        }
    }
}
