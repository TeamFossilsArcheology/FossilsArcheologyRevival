package fossilsarcheology.server.config;

import net.ilexiconn.llibrary.server.config.ConfigEntry;

@SuppressWarnings("deprecation")
public class FossilConfig {
    @ConfigEntry(category = "generation")
    public boolean generatePalaeoraphe = false;
    @ConfigEntry(category = "generation")
    public boolean generateHellShips = true;
    @ConfigEntry(category = "generation")
    public boolean generateAcademy = true;
    @ConfigEntry(category = "generation")
    public boolean generateTemple = true;
    @ConfigEntry(category = "generation")
    public boolean generateFossils = true;
    @ConfigEntry(category = "generation")
    public boolean generatePermafrost = true;
    @ConfigEntry(category = "generation")
    public boolean generateVolcanicRock = true;
    @ConfigEntry(category = "generation")
    public boolean generateAztecWeaponShops = true;
    @ConfigEntry(category = "generation")
    public boolean generateMoai = true;
    @ConfigEntry(category = "generation")
    public boolean generateTarSites = true;
    @ConfigEntry(category = "generation")
    public boolean generateFossilSites = true;
    @ConfigEntry(category = "generation")
    public int[] oreGenerationDimensions = { 0 };
    @ConfigEntry(category = "generation")
    public int generateHellShipRarity = 100;
    @ConfigEntry(category = "generation")
    public int generateTarSiteRarity = 700;
    @ConfigEntry(category = "generation")
    public int generateFossilSiteRarity = 700;
    @ConfigEntry(category = "generation")
    public int generateMoaiRarity = 400;
    @ConfigEntry(category = "generation")
    public int generateWeaponShopRarity = 40;
    @ConfigEntry(category = "generation")
    public int generateTempleRarity = 90;
    @ConfigEntry(category = "generation")
    public int generateAcademyRarity = 500;
    @ConfigEntry(category = "entity spawning")
    public boolean spawnCoelacanth = true;
    @ConfigEntry(category = "entity spawning")
    public boolean spawnSturgeon = true;
    @ConfigEntry(category = "entity spawning")
    public boolean spawnAlligatorGar = true;
    @ConfigEntry(category = "entity spawning")
    public boolean spawnNautilus = true;
    @ConfigEntry
    public boolean healingDinos = true;
    @ConfigEntry
    public boolean starvingDinos = true;
    @ConfigEntry
    public boolean dinoBlockBreaking = true;
    @ConfigEntry
    public boolean dinoEatModdedMobs = true;
    @ConfigEntry(category = "client")
    public boolean customMainMenu = true;

    @ConfigEntry(category = "dinosaurs")
    public boolean featheredDeinonychus = true;
    @ConfigEntry(category = "dinosaurs")
    public boolean featheredGallimimus = true;
    @ConfigEntry(category = "dinosaurs")
    public boolean featheredCompsognathus = true;
    @ConfigEntry(category = "dinosaurs")
    public boolean quilledTriceratops = false;
    @ConfigEntry(category = "dinosaurs")
    public boolean featheredVelociraptor = true;
    @ConfigEntry(category = "dinosaurs")
    public boolean featheredTherizinosaurus = true;
    @ConfigEntry(category = "dinosaurs")
    public boolean featheredDryosaurus = false;
    @ConfigEntry(category = "dinosaurs")
    public boolean eggsLikeChickens = false;
    @ConfigEntry(category = "dinosaurs")
    public int flyingTargetMaxHeight = 128;
    @ConfigEntry(category = "ids")
    public int dimensionIDDarknessLair = -23;
    @ConfigEntry(category = "ids")
    public int dimensionIDTreasure = -34;
    @ConfigEntry(category = "generation")
    public boolean logCascadingWorldGen = false;
}
