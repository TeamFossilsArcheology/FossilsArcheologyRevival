package fossilsarcheology.client.event;

import com.mojang.authlib.minecraft.MinecraftProfileTexture;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.ReflectionHelper;

import java.util.Map;
import java.util.UUID;

public class FossilClientPlayerEvent {
    public ResourceLocation DEVELOPER_TEXTURE = new ResourceLocation("fossil", "textures/model/developer_cape.png");
    public ResourceLocation CONTRIBUTOR_TEXTURE = new ResourceLocation("fossil", "textures/model/contributor_cape.png");
    public ResourceLocation DEVELOPER_ELYTRA_TEXTURE = new ResourceLocation("fossil", "textures/model/developer_elytra.png");
    public ResourceLocation CONTRIBUTOR_ELYTRA_TEXTURE = new ResourceLocation("fossil", "textures/model/contributor_elytra.png");
    public static final UUID[] DEV_UUIDS = new UUID[]{
            /* tyranno66 */UUID.fromString("04e6d7f5-b587-4e4f-8ce0-b210d368a79e"),
            /* Raptorfarian */UUID.fromString("0ed918c8-d612-4360-b711-cd415671356f"),
            /* 4f6f3b */UUID.fromString("0248b8a4-7fab-4d6f-8a22-8a51534fd53f"),
            /* GinjaninjaS7 */UUID.fromString("18eb6ad8-1656-4e41-89f6-88b708a0474c"),
            /* Bluestreak52 */UUID.fromString("5468a8f2-84d6-46e2-b58c-f9d576b67544"),
            /* Robberto08 */UUID.fromString("05b14ce7-0ff1-4b8e-9ef8-d98502e9bf07"),
            /* Alexthe666 */UUID.fromString("71363abe-fd03-49c9-940d-aae8b8209b7c"),
            /* NanoTyrano */UUID.fromString("d4c29faf-82bc-4d34-bda0-cb850595595a"),
            /* iLexiconn */UUID.fromString("40e85e42-21f6-46b6-b5b3-6aeb07f3e3fd"),
            /* Gegy */UUID.fromString("c3ed4d52-fb4f-4964-ba1b-9cda2453741e"),
            /*Sunconure11*/UUID.fromString("7f4a8204-d5a3-47a6-be6b-7482efa6f955")};
    public static final UUID[] CONTRIBUROR_UUIDS = new UUID[]{
            /* cyborx25 */UUID.fromString("c1637beb-4336-42f2-ad0b-a7188cf13042"),
            /* ExDragonith */UUID.fromString("a7970406-e0ac-446b-8fe0-d42c94b594ea"),
            /* duckdude173 */UUID.fromString("12bde8ed-cfe9-49ac-af14-71762a3f49db"),
            /* whitejoshman */UUID.fromString("28bcc73a-2726-49e8-ac1b-f02dbbb0c83b"),
            /* Shadowbeast007 */UUID.fromString("df3d1115-6601-4346-a063-f1254bf5a069"),
            /* MicroJunk */UUID.fromString("d1c57f9a-069b-46af-b9ee-44ab0fce1d80"),
            /*Gift_Of_Names*/UUID.fromString("a0beae9d-0779-4908-a4d2-f03da651502f"),
            /*PCAwesomeness*/UUID.fromString("befc934f-f684-4049-8f47-7b5a3727dbc6"),
            /*Noonyeyz*/UUID.fromString("4a463319-625c-4b86-a4e7-8b700f023a60"),
            /*JJo5n1peZ*/UUID.fromString("796c6f01-2297-4052-bf59-464a612c1b0f"),
            /*Alpha_Hyperion*/UUID.fromString("13d60612-a851-4945-bf88-4183dd281d7a"),
            /*Sargeant_789575*/UUID.fromString("d243cdf3-fb0f-4fbb-a4a7-6c894a440354"),
            /*Cactus_King9*/UUID.fromString("4cb62b58-3a82-4846-bf9d-5817eabfd22b")};

    @SubscribeEvent
    public void playerRender(RenderPlayerEvent.Pre event) {
        if (event.getEntityPlayer() instanceof AbstractClientPlayer) {
            NetworkPlayerInfo info = null;
            try {
                info = (NetworkPlayerInfo) ReflectionHelper.findField(AbstractClientPlayer.class, new String[]{"playerInfo", "field_175157_a"}).get(event.getEntityPlayer());
            } catch (IllegalArgumentException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            }
            if (info != null) {
                Map<MinecraftProfileTexture.Type, ResourceLocation> textureMap = null;
                try {
                    textureMap = (Map<MinecraftProfileTexture.Type, ResourceLocation>) ReflectionHelper.findField(NetworkPlayerInfo.class, new String[]{"playerTextures", "field_187107_a"}).get(info);
                } catch (IllegalArgumentException e) {
                    e.printStackTrace();
                } catch (IllegalAccessException e) {
                    e.printStackTrace();
                }
                if (textureMap != null) {
                    if (hasDevCape(event.getEntityPlayer().getUniqueID())) {
                        textureMap.put(MinecraftProfileTexture.Type.CAPE, DEVELOPER_TEXTURE);
                        textureMap.put(MinecraftProfileTexture.Type.ELYTRA, DEVELOPER_ELYTRA_TEXTURE);
                    }
                    if (hasContributorCape(event.getEntityPlayer().getUniqueID())) {
                        textureMap.put(MinecraftProfileTexture.Type.CAPE, CONTRIBUTOR_TEXTURE);
                        textureMap.put(MinecraftProfileTexture.Type.ELYTRA, CONTRIBUTOR_ELYTRA_TEXTURE);
                    }
                }
            }
        }
    }

    private boolean hasDevCape(UUID uniqueID) {
        for (UUID uuid1 : DEV_UUIDS) {
            if (uniqueID.equals(uuid1)) {
                return true;
            }
        }
        return false;
    }

    private boolean hasContributorCape(UUID uniqueID) {
        for (UUID uuid1 : CONTRIBUROR_UUIDS) {
            if (uniqueID.equals(uuid1)) {
                return true;
            }
        }
        return false;
    }
}