package fossilsarcheology.server;

import fossilsarcheology.Revival;
import fossilsarcheology.client.sound.FASoundRegistry;
import fossilsarcheology.server.block.FABlockRegistry;
import fossilsarcheology.server.block.FAFluidRegistry;
import fossilsarcheology.server.block.IBlockItem;
import fossilsarcheology.server.block.ISlabItem;
import fossilsarcheology.server.block.entity.*;
import fossilsarcheology.server.container.*;
import fossilsarcheology.server.entity.EntityFishBase;
import fossilsarcheology.server.entity.FAEntityRegistry;
import fossilsarcheology.server.entity.FAVillagerRegistry;
import fossilsarcheology.server.entity.prehistoric.EntityPrehistoric;
import fossilsarcheology.server.entity.prehistoric.MobType;
import fossilsarcheology.server.entity.prehistoric.PrehistoricEntityType;
import fossilsarcheology.server.entity.projectile.EntityBirdEgg;
import fossilsarcheology.server.entity.utility.FossilsMammalProperties;
import fossilsarcheology.server.entity.utility.FossilsPlayerProperties;
import fossilsarcheology.server.event.FossilBonemealEvent;
import fossilsarcheology.server.event.FossilCraftingEvent;
import fossilsarcheology.server.event.FossilLivingEvent;
import fossilsarcheology.server.event.FossilPickupItemEvent;
import fossilsarcheology.server.item.FAItemRegistry;
import fossilsarcheology.server.item.enchantment.FAEnchantmentRegistry;
import fossilsarcheology.server.recipe.FAMachineRecipeRegistry;
import fossilsarcheology.server.recipe.FAOreDictRegistry;
import fossilsarcheology.server.recipe.RecipeTarDrops;
import fossilsarcheology.server.util.FossilFoodMappings;
import fossilsarcheology.server.world.FAWorldGenerator;
import fossilsarcheology.server.world.FAWorldRegistry;
import net.ilexiconn.llibrary.server.entity.EntityPropertiesHandler;
import net.minecraft.block.Block;
import net.minecraft.block.BlockDispenser;
import net.minecraft.dispenser.BehaviorProjectileDispense;
import net.minecraft.dispenser.IPosition;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.entity.IProjectile;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.client.event.ConfigChangedEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.network.IGuiHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.common.registry.VillagerRegistry;

import java.lang.reflect.Field;

@Mod.EventBusSubscriber
public class ServerProxy implements IGuiHandler {
    public static final int GUI_ANALYZER = 0;
    public static final int GUI_CULTIVATE = 1;
    public static final int GUI_FEEDER = 2;
    public static final int GUI_WORKTABLE = 3;
    public static final int GUI_SIFTER = 4;
    public static final int GUI_TIME_MACHINE = 5;
    public static final int GUI_DINOPEDIA = 6;

    @SubscribeEvent
    public static void registerVillagers(RegistryEvent.Register<VillagerRegistry.VillagerProfession> event) {
        event.getRegistry().registerAll(FAVillagerRegistry.ARCHEOLOGIST_PROFESSION, FAVillagerRegistry.PALAEONTOLOGIST_PROFESSION);
    }

    @SubscribeEvent
    public static void registerEnchantments(RegistryEvent.Register<Enchantment> event) {
        event.getRegistry().registerAll(FAEnchantmentRegistry.ENCHANTMENT_ARCHEOLOGY, FAEnchantmentRegistry.ENCHANTMENT_PALEONTOLOGY);
    }

    @SubscribeEvent
    public static void registerRecipes(RegistryEvent.Register<IRecipe> event) {
        IRecipe recipeTarDrops = new RecipeTarDrops().setRegistryName(new ResourceLocation("fossil:tar_drop_from_bucket"));
        event.getRegistry().registerAll(recipeTarDrops);
    }

    @SuppressWarnings("deprecation")
    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        try {
            for (Field f : FABlockRegistry.class.getDeclaredFields()) {
                Object obj = f.get(null);
                if (obj instanceof Block) {
                    FABlockRegistry.registerBlock(event, (Block) obj);
                } else if (obj instanceof Block[]) {
                    for (Block block : (Block[]) obj) {
                        FABlockRegistry.registerBlock(event, block);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        GameRegistry.registerTileEntity(TileEntityCultivate.class, "fossil:cultivate");
        GameRegistry.registerTileEntity(TileEntityFeeder.class, "fossil:feeder");
        GameRegistry.registerTileEntity(TileEntityBubbleBlower.class, "fossil:bubble_blower");
        GameRegistry.registerTileEntity(TileEntityWorktable.class, "fossil:archeology_workbench");
        GameRegistry.registerTileEntity(TileEntityAnalyzer.class, "fossil:analyzer");
        GameRegistry.registerTileEntity(TileEntityAncientChest.class, "fossil:ancient_chest");
        GameRegistry.registerTileEntity(TileEntityAnubiteStatue.class, "fossil:anubite");
        GameRegistry.registerTileEntity(TileEntityAnuStatue.class, "fossil:anu_statue");
        GameRegistry.registerTileEntity(TileEntityFigurine.class, "fossil:figurine");
        GameRegistry.registerTileEntity(TileEntityKylix.class, "fossil:kylix");
        GameRegistry.registerTileEntity(TileEntitySarcophagus.class, "fossil:sarcophagus");
        GameRegistry.registerTileEntity(TileEntitySifter.class, "fossil:sifter");
        GameRegistry.registerTileEntity(TileEntityAmphora.class, "fossil:amphora");
        GameRegistry.registerTileEntity(TileEntityTimeMachine.class, "fossil:time_machine");
        GameRegistry.registerTileEntity(TileEntityVolute.class, "fossil:volute");
    }

    @SubscribeEvent
    public static void registerItems(RegistryEvent.Register<Item> event) {
        FAItemRegistry.COOKED_CHICKEN_SOUP.setContainerItem(Items.BUCKET);
        try {
            for (Field f : FAItemRegistry.class.getDeclaredFields()) {
                Object obj = f.get(null);
                if (obj instanceof Item) {
                    FAItemRegistry.registerItem(event, (Item) obj);
                } else if (obj instanceof Item[]) {
                    for (Item item : (Item[]) obj) {
                        FAItemRegistry.registerItem(event, item);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        try {
            for (Field f : FABlockRegistry.class.getDeclaredFields()) {
                Object obj = f.get(null);
                if (obj instanceof Block) {
                    registerItemBlocks((Block) obj, event);
                } else if (obj instanceof Block[]) {
                    for (Block block : (Block[]) obj) {
                        registerItemBlocks(block, event);
                    }
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }

        PrehistoricEntityType.register(event);
        FAMachineRecipeRegistry.init();
    }

    private static void registerItemBlocks(Block block, RegistryEvent.Register<Item> event) {
        if (block instanceof IBlockItem) {
            ItemBlock itemBlock = ((IBlockItem) block).getItemBlock(block);
            String name = itemBlock.getTranslationKey().substring("item.".length());
            itemBlock.setRegistryName(new ResourceLocation(Revival.MODID, name));
            event.getRegistry().register(itemBlock);
        } else if (block instanceof ISlabItem) {
            ItemBlock itemBlock = ((ISlabItem) block).getItemBlock();
            String name = itemBlock.getTranslationKey().substring("item.".length());
            itemBlock.setRegistryName(new ResourceLocation(Revival.MODID, name));
            event.getRegistry().register(itemBlock);
        } else {
            ItemBlock itemBlock = new ItemBlock(block);
            String name = itemBlock.getTranslationKey().substring("item.".length());
            itemBlock.setRegistryName(new ResourceLocation(Revival.MODID, name));
            event.getRegistry().register(itemBlock);
        }
    }

    @SubscribeEvent
    public static void registerSoundEvents(RegistryEvent.Register<SoundEvent> event) {
        try {
            for (Field f : FASoundRegistry.class.getDeclaredFields()) {
                Object obj = f.get(null);
                if (obj instanceof SoundEvent) {
                    event.getRegistry().register((SoundEvent) obj);
                }
            }
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
    }

    @SubscribeEvent
    public static void registerBiome(RegistryEvent.Register<Biome> event) {
        event.getRegistry().register(FAWorldRegistry.ANU_BIOME.setRegistryName("Lair of Darkness"));
        event.getRegistry().register(FAWorldRegistry.TREASURE_BIOME.setRegistryName("Treasure"));
        event.getRegistry().register(FAWorldRegistry.VOLCANO_BIOME.setRegistryName("Volcano"));
        BiomeDictionary.addTypes(FAWorldRegistry.ANU_BIOME, BiomeDictionary.Type.NETHER, BiomeDictionary.Type.VOID);
        BiomeDictionary.addTypes(FAWorldRegistry.TREASURE_BIOME, BiomeDictionary.Type.VOID);
        BiomeDictionary.addTypes(FAWorldRegistry.VOLCANO_BIOME, BiomeDictionary.Type.DEAD, BiomeDictionary.Type.WASTELAND, BiomeDictionary.Type.HOT, BiomeDictionary.Type.DRY);
        if (Revival.CONFIG_OPTIONS.spawnVolcanoes) {
            BiomeManager.addSpawnBiome(FAWorldRegistry.VOLCANO_BIOME);
            BiomeManager.addBiome(BiomeManager.BiomeType.DESERT, new BiomeManager.BiomeEntry(FAWorldRegistry.VOLCANO_BIOME, Revival.CONFIG_OPTIONS.volcanoSpawnChance));

        }
    }

    public void onPreInit() {
        NetworkRegistry.INSTANCE.registerGuiHandler(Revival.INSTANCE, this);
        FAFluidRegistry.register();
        FAEntityRegistry.register();
        FAWorldRegistry.register();
    }

    public void onInit() {
        FAOreDictRegistry.register();
        MinecraftForge.EVENT_BUS.register(new FossilCraftingEvent());
        MinecraftForge.EVENT_BUS.register(new FossilPickupItemEvent());
        MinecraftForge.EVENT_BUS.register(new FossilBonemealEvent());
        GameRegistry.registerWorldGenerator(new FAWorldGenerator(), 0);
    }

    public void calculateChainBuffer(EntityFishBase entity) {

    }

    public void calculateChainBuffer(EntityPrehistoric entity) {

    }

    public void onPostInit() {
        for (PrehistoricEntityType prehistoric : PrehistoricEntityType.values()) {
            if (prehistoric.mobType == MobType.BIRD) {
                BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(prehistoric.birdEggItem, new BehaviorProjectileDispense() {
                    protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
                        EntityBirdEgg egg = new EntityBirdEgg(false, worldIn, position.getX(), position.getY(), position.getZ());
                        egg.setEnumType(prehistoric);
                        return egg;
                    }
                });
                BlockDispenser.DISPENSE_BEHAVIOR_REGISTRY.putObject(prehistoric.bestBirdEggItem, new BehaviorProjectileDispense() {
                    protected IProjectile getProjectileEntity(World worldIn, IPosition position, ItemStack stackIn) {
                        EntityBirdEgg egg = new EntityBirdEgg(true, worldIn, position.getX(), position.getY(), position.getZ());
                        egg.setEnumType(prehistoric);
                        return egg;
                    }
                });
            }
        }
        FossilFoodMappings.register();
        FAVillagerRegistry.register();

    }

    @Override
    public Object getServerGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        BlockPos pos = new BlockPos(x, y, z);
        TileEntity entity = world.getTileEntity(pos);
        if (id == GUI_ANALYZER && entity instanceof TileEntityAnalyzer) {
            return new AnalyzerContainer(player.inventory, (TileEntityAnalyzer) entity);
        }
        if (id == GUI_CULTIVATE && entity instanceof TileEntityCultivate) {
            return new CultivateContainer(player.inventory, (TileEntityCultivate) entity);
        }
        if (id == GUI_FEEDER && entity instanceof TileEntityFeeder) {
            return new FeederContainer(player.inventory, (TileEntityFeeder) entity);
        }
        if (id == GUI_WORKTABLE && entity instanceof TileEntityWorktable) {
            return new WorktableContainer(player.inventory, (TileEntityWorktable) entity);
        }
        if (id == GUI_SIFTER && entity instanceof TileEntitySifter) {
            return new SifterContainer(player.inventory, (TileEntitySifter) entity);
        }
        if (id == GUI_TIME_MACHINE && entity instanceof TileEntityTimeMachine) {
            return new TimeMachineContainer(player.inventory, (TileEntityTimeMachine) entity);
        }
        return null;
    }

    @Override
    public Object getClientGuiElement(int id, EntityPlayer player, World world, int x, int y, int z) {
        return null;
    }

    public void spawnPacketItemParticles(World worldObj, float f, float f1, float f2, double motionX, double motionY, double motionZ, Item item) {

    }

    public void spawnPacketBlockParticles(World worldObj, float f, float f1, float f2, double motionX, double motionY, double motionZ, Block block) {
    }

    public void spawnPacketHeartParticles(World worldObj, float f, float f1, float f2, double motionX, double motionY, double motionZ) {
    }

    public void spawnBubbleParticles(World world, float f, float f1, float f2, double motionX, double motionY, double motionZ) {
    }

    public void spawnAnuParticle(World world, double posX, double posY, double posZ) {
    }

    public void spawnFAParticle(String type, float posX, float posY, float posZ, double motionX, double motionY, double motionZ) {
    }

    public void playSound(SoundEvent sound) {
    }

    public void stopSound(SoundEvent sound) {
    }

    public Object getArmorModel(int id) {
        return null;
    }

    public void openPedia() {
    }
}
