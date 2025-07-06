package com.github.teamfossilsarcheology.fossil.mixin;

import com.github.teamfossilsarcheology.fossil.client.FAPlayerCapes;
import com.mojang.authlib.GameProfile;
import net.minecraft.client.multiplayer.PlayerInfo;
import net.minecraft.resources.ResourceLocation;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

@Mixin(PlayerInfo.class)
public class PlayerInfoMixin {

    @Shadow
    @Final
    private GameProfile profile;

    @Inject(method = "getCapeLocation", at = @At("RETURN"), cancellable = true)
    private void fossilsArcheologyRevival$getCape(CallbackInfoReturnable<ResourceLocation> cir) {
        if (fossilsArcheologyRevival$hasDevCape(profile.getId())) {
            cir.setReturnValue(FAPlayerCapes.DEVELOPER_CAPE_TEXTURE);
        } else if (fossilsArcheologyRevival$hasContributorCape(profile.getId())) {
            cir.setReturnValue(FAPlayerCapes.CONTRIBUTOR_CAPE_TEXTURE);
        }
    }

    @Inject(method = "getElytraLocation", at = @At("RETURN"), cancellable = true)
    private void fossilsArcheologyRevival$getElytra(CallbackInfoReturnable<ResourceLocation> cir) {
        if (fossilsArcheologyRevival$hasDevCape(profile.getId())) {
            cir.setReturnValue(FAPlayerCapes.DEVELOPER_ELYTRA_TEXTURE);
        } else if (fossilsArcheologyRevival$hasContributorCape(profile.getId())) {
            cir.setReturnValue(FAPlayerCapes.CONTRIBUTOR_ELYTRA_TEXTURE);
        }
    }

    @Unique
    private boolean fossilsArcheologyRevival$hasDevCape(UUID playerId) {
        return FAPlayerCapes.DEV_UUIDS.contains(playerId);
    }

    @Unique
    private boolean fossilsArcheologyRevival$hasContributorCape(UUID playerId) {
        return FAPlayerCapes.CONTRIBUTOR_UUIDS.contains(playerId);
    }
}
