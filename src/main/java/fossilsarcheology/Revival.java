package fossilsarcheology;

import fossilsarcheology.server.ServerProxy;
import fossilsarcheology.server.block.FABlockRegistry;
import fossilsarcheology.server.compat.thaumcraft.ThaumcraftCompatBridge;
import fossilsarcheology.server.compat.tinkers.TinkersCompatBridge;
import fossilsarcheology.server.config.FossilConfig;
import fossilsarcheology.server.entity.utility.FossilsMammalProperties;
import fossilsarcheology.server.entity.utility.FossilsPlayerProperties;
import fossilsarcheology.server.event.EventSharedConfig;
import fossilsarcheology.server.event.FossilBonemealEvent;
import fossilsarcheology.server.event.FossilLivingEvent;
import fossilsarcheology.server.lib.LibDependencies;
import fossilsarcheology.server.loot.CustomizeToDinosaur;
import fossilsarcheology.server.message.*;
import fossilsarcheology.server.recipe.FAMachineRecipeRegistry;
import fossilsarcheology.server.util.ReleaseType;
import net.ilexiconn.llibrary.server.entity.EntityPropertiesHandler;
import net.ilexiconn.llibrary.server.network.NetworkWrapper;
import net.minecraft.world.storage.loot.functions.LootFunctionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.config.Configuration;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.File;

@Mod(modid = Revival.MODID, name = "Fossils and Archeology Revival", version = Revival.VERSION, acceptedMinecraftVersions = "[1.12,1.12.2]", dependencies = LibDependencies.DEPENDENCIES, guiFactory = "fossilsarcheology.client.gui.FAGUIFactory")

public class Revival {
    public static final String MODID = "fossil";
    public static final String VERSION = "8.0.3";
    public static final ReleaseType RELEASE_TYPE = ReleaseType.parseVersion(VERSION);
    public static final String LLIBRARY_VERSION = "1.7.17";

    public static final Logger LOGGER = LogManager.getLogger("fossils");

    @SidedProxy(clientSide = "fossilsarcheology.client.ClientProxy", serverSide = "fossilsarcheology.server.ServerProxy")
    public static ServerProxy PROXY;
    @NetworkWrapper({MessageFoodParticles.class, MessageSetDay.class, MessageHappyParticles.class, MessageUpdateEgg.class, MessageRollBall.class, MessageUpdateFeeder.class})
    public static SimpleNetworkWrapper NETWORK_WRAPPER;
    public static FossilConfig CONFIG_OPTIONS = new FossilConfig();
    public static Configuration config;

    @Mod.Instance(MODID)
    public static Revival INSTANCE;
    public static Object PEDIA_OBJECT;

    static {
        FluidRegistry.enableUniversalBucket();
    }

    public static void debug(String message) {
        if (RELEASE_TYPE.enableDebugging()) {
            LOGGER.debug(message);
        }
    }

    @Mod.EventHandler
    public void onPreInit(FMLPreInitializationEvent event) {
        loadConfig();
        syncConfig();
        PROXY.onPreInit();
        ThaumcraftCompatBridge.loadThaumcraftCompat();
        TinkersCompatBridge.loadTinkersCompat();
        LOGGER.info("Archaean horizon");
        LOGGER.info("The first sunrise");
        LOGGER.info("On a pristine Gaea");
        LOGGER.info("Opus perfectum");
        LOGGER.info("Somewhere there, us sleeping");
        MinecraftForge.EVENT_BUS.register(new EventSharedConfig());
        MinecraftForge.EVENT_BUS.register(new FossilLivingEvent());
        EntityPropertiesHandler.INSTANCE.registerProperties(FossilsPlayerProperties.class);
        EntityPropertiesHandler.INSTANCE.registerProperties(FossilsMammalProperties.class);
    }

    @Mod.EventHandler
    public void onInit(FMLInitializationEvent event) {
        PROXY.onInit();
        LootFunctionManager.registerFunction(new CustomizeToDinosaur.Serializer());
        FABlockRegistry.init();
        LOGGER.info("After a billion years");
        LOGGER.info("The show is still here");
        LOGGER.info("Not a single one of your fathers died young");
        LOGGER.info("The handy travelers out of Africa");
        LOGGER.info("Little Lucy of the Afar");

    }

    @Mod.EventHandler
    public void onPostInit(FMLPostInitializationEvent event) {
        PROXY.onPostInit();
        FAMachineRecipeRegistry.init();
        TinkersCompatBridge.loadTinkersPostInitCompat();
    }

    public static void loadConfig() {
        File configFile = new File(Loader.instance().getConfigDir(), "fossil.cfg");
        if (!configFile.exists()) {
            try {
                configFile.createNewFile();
            } catch (Exception e) {
                LOGGER.warn("Could not create a new FA config file.");
                LOGGER.warn(e.getLocalizedMessage());
            }
        }
        config = new Configuration(configFile);
        config.load();
    }

    public static void syncConfig() {
        CONFIG_OPTIONS.init(config);
        config.save();
    }
}
