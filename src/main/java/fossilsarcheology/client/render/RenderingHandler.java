package fossilsarcheology.client.render;

import fossilsarcheology.Revival;
import fossilsarcheology.client.model.*;
import fossilsarcheology.client.render.entity.*;
import fossilsarcheology.client.render.tile.*;
import fossilsarcheology.server.block.FABlockRegistry;
import fossilsarcheology.server.block.VaseVariant;
import fossilsarcheology.server.block.entity.*;
import fossilsarcheology.server.entity.EntityDinosaurEgg;
import fossilsarcheology.server.entity.StoneTabletEntity;
import fossilsarcheology.server.entity.monster.*;
import fossilsarcheology.server.entity.prehistoric.*;
import fossilsarcheology.server.entity.projectile.AncientJavelinEntity;
import fossilsarcheology.server.entity.projectile.EntityBirdEgg;
import fossilsarcheology.server.entity.projectile.JavelinEntity;
import fossilsarcheology.server.entity.utility.*;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.world.ColorizerFoliage;
import net.minecraft.world.biome.BiomeColorHelper;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.fml.client.registry.ClientRegistry;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class RenderingHandler {
    private static final Minecraft MINECRAFT = Minecraft.getMinecraft();

    @SuppressWarnings("deprecation")
    public void onPreInit() {
        RenderingRegistry.registerEntityRenderingHandler(JavelinEntity.class, JavelinRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(AncientJavelinEntity.class, JavelinRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(StoneTabletEntity.class, StoneTabletRenderer::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityAnubite.class, RenderAnubite::new);
        RenderingRegistry.registerEntityRenderingHandler(EntitySentryPigman.class, RenderSentryPigman::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityTarSlime.class, RenderTarSlime::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityAnu.class, RenderAnu::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityDinosaurEgg.class, RenderDinoEgg::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityToyBall.class, RenderToyBall::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityToyScratchingPost.class, RenderToyScratchingPost::new);
        RenderingRegistry.registerEntityRenderingHandler(EntityToyTetheredLog.class, RenderToyTetheredLog::new);
    }

    @SuppressWarnings("deprecation")
    public void onInit() {
        RenderingRegistry.registerEntityRenderingHandler(EntityQuagga.class, new RenderQuagga(MINECRAFT.getRenderManager()));

        RenderingRegistry.registerEntityRenderingHandler(EntityAnuStatue.class,new RenderAnuStatue(MINECRAFT.getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler(EntityNautilus.class, new RenderFish(new ModelNautilus(), MINECRAFT.getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler(EntityCoelacanth.class, new RenderFish(new ModelCoelacanth(), MINECRAFT.getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler(EntityAlligatorGar.class, new RenderFish(new ModelAlligatorGar(), MINECRAFT.getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler(EntitySturgeon.class, new RenderFish(new ModelSturgeon(), MINECRAFT.getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler(EntityFailuresaurus.class, new RenderFailuresaurus(new ModelFailuresaurus(), MINECRAFT.getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler(EntityFriendlyPigZombie.class, new RenderFriendlyPigZombie(new ModelFriendlyPigZombie(), MINECRAFT.getRenderManager()));
        RenderingRegistry.registerEntityRenderingHandler(EntityAnuDead.class, new RenderDeadAnu(MINECRAFT.getRenderManager(), new ModelDeadAnu(), 0.3F));
        RenderingRegistry.registerEntityRenderingHandler(EntityBirdEgg.class, new RenderBirdEgg(MINECRAFT.getRenderManager(), MINECRAFT.getRenderItem()));
        RenderingRegistry.registerEntityRenderingHandler(EntityTriceratops.class, new RenderPrehistoric(new ModelTriceratops()));
        RenderingRegistry.registerEntityRenderingHandler(EntityVelociraptor.class, new RenderPrehistoric(new ModelVelociraptor()));
        RenderingRegistry.registerEntityRenderingHandler(EntityTyrannosaurus.class, new RenderPrehistoric(new ModelTyrannosaurus()));
        RenderingRegistry.registerEntityRenderingHandler(EntityPterosaur.class, new RenderPrehistoric(new ModelPteranodon()));
        RenderingRegistry.registerEntityRenderingHandler(EntityPlesiosaurus.class, new RenderPrehistoric(new ModelPlesiosaurus()));
        RenderingRegistry.registerEntityRenderingHandler(EntityMosasaurus.class, new RenderPrehistoric(new ModelMosasaurus()));
        RenderingRegistry.registerEntityRenderingHandler(EntityStegosaurus.class, new RenderPrehistoric(new ModelStegosaurus()));
        RenderingRegistry.registerEntityRenderingHandler(EntityDilophosaurus.class, new RenderPrehistoric(new ModelDilophosaurus()));
        RenderingRegistry.registerEntityRenderingHandler(EntitySmilodon.class, new RenderPrehistoric(new ModelSmilodon()));
        RenderingRegistry.registerEntityRenderingHandler(EntityBrachiosaurus.class, new RenderPrehistoric(new ModelBrachiosaurus()));
        RenderingRegistry.registerEntityRenderingHandler(EntityMammoth.class, new RenderPrehistoric(new ModelMammoth()));
        RenderingRegistry.registerEntityRenderingHandler(EntitySpinosaurus.class, new RenderPrehistoric(new ModelSpinosaurus()));
        RenderingRegistry.registerEntityRenderingHandler(EntityCompsognathus.class, new RenderPrehistoric(new ModelCompsognathus()));
        RenderingRegistry.registerEntityRenderingHandler(EntityDodo.class, new RenderPrehistoric(new ModelDodo()));
        RenderingRegistry.registerEntityRenderingHandler(EntityAnkylosaurus.class, new RenderPrehistoric(new ModelAnkylosaurus()));
        RenderingRegistry.registerEntityRenderingHandler(EntityPachycephalosaurus.class, new RenderPrehistoric(new ModelPachycephalosaurus()));
        RenderingRegistry.registerEntityRenderingHandler(EntityDeinonychus.class, new RenderPrehistoric(new ModelDeinonychus()));
        RenderingRegistry.registerEntityRenderingHandler(EntityGallimimus.class, new RenderPrehistoric(new ModelGallimimus()));
        RenderingRegistry.registerEntityRenderingHandler(EntitySarcosuchus.class, new RenderPrehistoric(new ModelSarcosuchus()));
        RenderingRegistry.registerEntityRenderingHandler(EntityAllosaurus.class, new RenderPrehistoric(new ModelAllosaurus()));
        RenderingRegistry.registerEntityRenderingHandler(EntityLiopleurodon.class, new RenderPrehistoric(new ModelLiopleurodon()));
        RenderingRegistry.registerEntityRenderingHandler(EntityElasmotherium.class, new RenderPrehistoric(new ModelElasmotherium()));
        RenderingRegistry.registerEntityRenderingHandler(EntityCeratosaurus.class, new RenderPrehistoric(new ModelCeratosaurus()));
        RenderingRegistry.registerEntityRenderingHandler(EntityConfuciusornis.class, new RenderPrehistoric(new ModelConfuciusornis()));
        RenderingRegistry.registerEntityRenderingHandler(EntityDryosaurus.class, new RenderPrehistoric(new ModelDryosaurus()));
        RenderingRegistry.registerEntityRenderingHandler(EntityTherizinosaurus.class, new RenderPrehistoric(new ModelTherizinosaurus()));
        RenderingRegistry.registerEntityRenderingHandler(EntityParasaurolophus.class, new RenderPrehistoric(new ModelParasaurolophus()));
        RenderingRegistry.registerEntityRenderingHandler(EntityGastornis.class, new RenderPrehistoric(new ModelTerrorBird()));
        RenderingRegistry.registerEntityRenderingHandler(EntityKelenken.class, new RenderPrehistoric(new ModelTerrorBird()));
        RenderingRegistry.registerEntityRenderingHandler(EntityPhorusrhacos.class, new RenderPrehistoric(new ModelTerrorBird()));
        RenderingRegistry.registerEntityRenderingHandler(EntityTitanis.class, new RenderPrehistoric(new ModelTerrorBird()));
        RenderingRegistry.registerEntityRenderingHandler(EntityHenodus.class, new RenderPrehistoric(new ModelHenodus()));
        RenderingRegistry.registerEntityRenderingHandler(EntityIcthyosaurus.class, new RenderPrehistoric(new ModelIcthyosaurus()));
        RenderingRegistry.registerEntityRenderingHandler(EntityMeganeura.class, new RenderPrehistoric(new ModelMeganeura()));
        RenderingRegistry.registerEntityRenderingHandler(EntityMegaloceros.class, new RenderPrehistoric(new ModelMegaloceros()));
        RenderingRegistry.registerEntityRenderingHandler(EntityMegalania.class, new RenderPrehistoric(new ModelMegalania()));
        RenderingRegistry.registerEntityRenderingHandler(EntityMegalograptus.class, new RenderPrehistoric(new ModelMegalograptus()));
        RenderingRegistry.registerEntityRenderingHandler(EntityMegalodon.class, new RenderPrehistoric(new ModelMegalodon()));
        RenderingRegistry.registerEntityRenderingHandler(EntityPlatybelodon.class, new RenderPrehistoric(new ModelPlatybelodon()));
        RenderingRegistry.registerEntityRenderingHandler(EntityTiktaalik.class, new RenderPrehistoric(new ModelTiktaalik()));
        RenderingRegistry.registerEntityRenderingHandler(EntityCrassigyrinus.class, new RenderPrehistoric(new ModelCrassigyrinus()));
        RenderingRegistry.registerEntityRenderingHandler(EntityDiplocaulus.class, new RenderPrehistoric(new ModelDiplocaulus()));
        RenderingRegistry.registerEntityRenderingHandler(EntityDebugTest.class, new RenderDebugTest());

        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAnuStatue.class, new TileEntityAnuStatueRender());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAnubiteStatue.class, new TileEntityAnubiteStatueRender());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAncientChest.class, new TileEntityAncientChestRender());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntitySarcophagus.class, new TileEntitySarcophagusRender());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityFigurine.class, new TileEntityFigurineRender());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityAmphora.class, new TileEntityVaseRenderer(new ModelVaseAmphora(), VaseVariant::getAmphoraTexture));
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityVolute.class, new TileEntityVaseRenderer(new ModelVaseVolute(), VaseVariant::getVoluteTexture));
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityKylix.class, new TileEntityVaseRenderer(new ModelVaseKylix(), VaseVariant::getKylixTexture));
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityTimeMachine.class, new TileEntityTimeMachineRender());
        ClientRegistry.bindTileEntitySpecialRenderer(TileEntityCultivate.class, new TileEntityCultivateRender());

        MINECRAFT.getBlockColors().registerBlockColorHandler((state, worldIn, pos, tintIndex) -> worldIn != null && pos != null ? BiomeColorHelper.getFoliageColorAtPos(worldIn, pos) : ColorizerFoliage.getFoliageColorBasic(), FABlockRegistry.FERNS);
        MINECRAFT.getItemColors().registerItemColorHandler((stack, tintIndex) -> {
            @SuppressWarnings("deprecation")
            IBlockState state = ((ItemBlock) stack.getItem()).getBlock().getStateFromMeta(stack.getMetadata());
            return MINECRAFT.getBlockColors().colorMultiplier(state, null, null, tintIndex);
        }, FABlockRegistry.FERNS);

    }

    public void onPostInit() {
    }

    public void registerItemRenderer(Item item, String path, String type) {
        this.registerItemRenderer(item, 0, path, type);
    }

    public void registerItemRenderer(Item item, int meta, String path, String type) {
        ModelResourceLocation resource = new ModelResourceLocation(Revival.MODID + ":" + path, type);
        ModelLoader.setCustomModelResourceLocation(item, meta, resource);
    }

    public void registerBlockRenderer(Block block, int meta, String path, String type) {
        this.registerItemRenderer(Item.getItemFromBlock(block), meta, path, type);
    }

    public void registerBlockRenderer(Block block, final String path, final String type) {
        this.registerBlockRenderer(block, 0, path, type);
    }
}
