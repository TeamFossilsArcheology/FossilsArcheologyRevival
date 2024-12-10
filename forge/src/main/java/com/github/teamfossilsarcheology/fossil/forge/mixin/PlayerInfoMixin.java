package com.github.teamfossilsarcheology.fossil.forge.mixin;

import com.github.teamfossilsarcheology.fossil.FossilMod;
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
    @Unique
    private static final UUID[] DEV_UUIDS = new UUID[]{
            /* Dark_Pred */UUID.fromString("f831e2f6-9670-46af-b1c0-e1e1a9fa6086"),
            /* Dipple_Effect */UUID.fromString("81a2e754-9f39-487d-8e1c-bf5311db79ec"),
            /* GinjaninjaS7 */UUID.fromString("18eb6ad8-1656-4e41-89f6-88b708a0474c"),
            /* Smallpred */UUID.fromString("fe87a60b-fafe-4bec-ab92-6f489d99e0c4"),
            /* Robberto08 */UUID.fromString("05b14ce7-0ff1-4b8e-9ef8-d98502e9bf07"),
            /* dino_dan_ */UUID.fromString("fe4efe89-82eb-4c9e-bd35-f661147209f8")};
    @Unique
    private static final UUID[] CONTRIBUTOR_UUIDS = new UUID[]{
            /* tmvkrpxl0 */UUID.fromString("b6c7411f-15e2-48f6-b53f-876ec0577e82"),
            /* FrogToad87 */UUID.fromString("4bada6b2-ba98-4a69-b304-c33ab64f1d72"),
            /* SJ_Code */UUID.fromString("1f1b6f21-5314-41b5-8482-3f7b0f07303b"),
            /* TheTotemaster */UUID.fromString("d3b6e383-53ba-40cd-bf7a-c073e0fe39f7"),
            /* Galli23 */UUID.fromString("7af6ab44-9a02-4ef0-804e-d82590095d65"),
            /* _W0r1d_ */UUID.fromString("b4df5358-4596-4b03-a2b8-048ac15e401a"),
            /* ElSeaJuice */UUID.fromString("a7447129-dccb-4b6f-8b60-c28a73f8c5a3"),
            /* Krimpomp */UUID.fromString("7a4804fe-a6df-4243-9076-ff719f5ec627"),
            /* NotThatHyper */UUID.fromString("13d60612-a851-4945-bf88-4183dd281d7a"),
            /* Poecilokillia */ UUID.fromString("33ec43ff-ea27-4a6b-a4c0-bedde458488c")};
    @Unique
    private static final ResourceLocation DEVELOPER_CAPE_TEXTURE = FossilMod.location("textures/skins/developer_cape.png");
    @Unique
    private static final ResourceLocation CONTRIBUTOR_CAPE_TEXTURE = FossilMod.location("textures/skins/contributor_cape.png");
    @Unique
    private static final ResourceLocation DEVELOPER_ELYTRA_TEXTURE = FossilMod.location("textures/skins/developer_elytra.png");
    @Unique
    private static final ResourceLocation CONTRIBUTOR_ELYTRA_TEXTURE = FossilMod.location("textures/skins/contributor_elytra.png");

    @Inject(method = "getCapeLocation", at = @At("RETURN"), cancellable = true)
    private void fossilsArcheologyRevival$getCape(CallbackInfoReturnable<ResourceLocation> cir) {
        if (fossilsArcheologyRevival$hasDevCape(profile.getId())) {
            cir.setReturnValue(DEVELOPER_CAPE_TEXTURE);
        } else if (fossilsArcheologyRevival$hasContributorCape(profile.getId())) {
            cir.setReturnValue(CONTRIBUTOR_CAPE_TEXTURE);
        }
    }

    @Inject(method = "getElytraLocation", at = @At("RETURN"), cancellable = true)
    private void fossilsArcheologyRevival$getElytra(CallbackInfoReturnable<ResourceLocation> cir) {
        if (fossilsArcheologyRevival$hasDevCape(profile.getId())) {
            cir.setReturnValue(DEVELOPER_ELYTRA_TEXTURE);
        } else if (fossilsArcheologyRevival$hasContributorCape(profile.getId())) {
            cir.setReturnValue(CONTRIBUTOR_ELYTRA_TEXTURE);
        }
    }

    @Unique
    private boolean fossilsArcheologyRevival$hasDevCape(UUID playerId) {
        for (UUID uuid : DEV_UUIDS) {
            if (playerId.equals(uuid)) {
                return true;
            }
        }
        return false;
    }

    @Unique
    private boolean fossilsArcheologyRevival$hasContributorCape(UUID playerId) {
        for (UUID uuid : CONTRIBUTOR_UUIDS) {
            if (playerId.equals(uuid)) {
                return true;
            }
        }
        return false;
    }
}
