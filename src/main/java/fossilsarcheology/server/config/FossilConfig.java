package fossilsarcheology.server.config;

import net.minecraftforge.common.config.Configuration;

public class FossilConfig {
    public boolean generatePalaeoraphe = false;
    public boolean generateHellShips = true;
    public boolean generateAcademy = true;
    public boolean generateTemple = true;
    public boolean generateFossils = true;
    public boolean generatePermafrost = true;
    public boolean generateVolcanicRock = true;
    public boolean generateAztecWeaponShops = true;
    public boolean generateMoai = true;
    public boolean generateTarSites = true;
    public boolean generateFossilSites = true;
    public int[] oreGenerationDimensions = { 0 };
    public int generateHellShipRarity = 100;
    public int generateTarSiteRarity = 900;
    public int generateFossilSiteRarity = 900;
    public int generateMoaiRarity = 400;
    public int generateWeaponShopRarity = 400;
    public int generateTempleRarity = 400;
    public int generateAcademyRarity = 500;
    public boolean spawnCoelacanth = true;
    public boolean spawnSturgeon = true;
    public boolean spawnAlligatorGar = true;
    public boolean spawnNautilus = true;
    public boolean spawnTarSlimes = true;
    public int tarSlimeSpawnRate = 75;
    public int nautilusSpawnRate = 6;
    public int coelacanthSpawnRate = 4;
    public int alligatorGarSpawnRate = 3;
    public int sturgeonSpawnRate = 4;
    public boolean healingDinos = true;
    public boolean starvingDinos = true;
    public boolean dinoBlockBreaking = true;
    public boolean dinoEatModdedMobs = true;
    public boolean customMainMenu = true;
    public boolean featheredDeinonychus = true;
    public boolean featheredGallimimus = true;
    public boolean featheredCompsognathus = true;
    public boolean quilledTriceratops = false;
    public boolean featheredVelociraptor = true;
    public boolean featheredTherizinosaurus = true;
    public boolean featheredDryosaurus = false;
    public boolean eggsLikeChickens = false;
    public boolean dinosaurBreeding = true;
    public int flyingTargetMaxHeight = 128;
    public int dinosaurUpdateTick = 10;
    public int dimensionIDDarknessLair = -23;
    public int dimensionIDTreasure = -24;
    public int homePortalExitDimension = 0;
    public boolean logCascadingWorldGen = false;
    public boolean animalsFearDinosaurs = true;
    public int pregnancyTime;

    public void init(Configuration config) {
        this.generatePalaeoraphe = config.getBoolean("Generate Palaeoraphe", "all", false, "True if Palaeoraphe Trees are to generate naturally");
        this.generateHellShips = config.getBoolean("Generate Hell Ships", "all", true, "True if Hell Ships are to generate naturally");
        this.generateAcademy = config.getBoolean("Generate Desert Academies", "all", true, "True if Desert Academies are to generate naturally");
        this.generateTemple = config.getBoolean("Generate Aztec Temple", "all", true, "True if Aztec Temples are to generate naturally");
        this.generateFossils = config.getBoolean("Generate Fossil Ores", "all", true, "True if Fossil Ores are to generate naturally");
        this.generatePermafrost = config.getBoolean("Generate Permafrost", "all", true, "True if Permafrost Ore is to generate naturally");
        this.generateVolcanicRock = config.getBoolean("Generate Volcanic Rock", "all", true, "True if Volcanic Rock is to generate naturally");
        this.generateAztecWeaponShops = config.getBoolean("Generate Aztec Weapon Shops", "all", true, "True if Aztec Weapon Shops are to generate naturally");
        this.generateMoai = config.getBoolean("Generate Moai", "all", true, "True if Moai are to generate naturally");
        this.generateTarSites = config.getBoolean("Generate Tarpit Dig Sites", "all", true, "True if Tarpit Dig Sites are to generate naturally");
        this.generateFossilSites = config.getBoolean("Generate Fossil Dig Sites", "all", true, "True if Fossil Dig Sites are to generate naturally");
        this.oreGenerationDimensions = config.get("Ore Generation Dimensions", "all", new int[]{0}, "List of Dimension IDs to spawn Fossil and Permafrost ores in").getIntList();
        this.generateHellShipRarity = config.getInt("Hell Ship Rarity", "all", 100, 1, 100000000, "Rarity of Hell Ship Structure. Higher number = more rare");
        this.generateTarSiteRarity = config.getInt("Tarpit Dig Site Rarity", "all", 900, 1, 100000000, "Rarity of Tarpit Dig Site Structure. Higher number = more rare");
        this.generateFossilSiteRarity = config.getInt("Fossil Dig Site Rarity", "all", 900, 1, 100000000, "Rarity of Fossil Dig Site Structure. Higher number = more rare");
        this.generateMoaiRarity = config.getInt("Moai Rarity", "all", 400, 1, 100000000, "Rarity of Moai Structure. Higher number = more rare");
        this.generateWeaponShopRarity = config.getInt("Aztec Weapon Shop Rarity", "all", 400, 1, 100000000, "Rarity of Aztec Weapon Shop Structure. Higher number = more rare");
        this.generateTempleRarity = config.getInt("Aztec Temple Rarity", "all", 500, 1, 100000000, "Rarity of Aztec Temple Structure. Higher number = more rare");
        this.generateAcademyRarity = config.getInt("Desert Academy Rarity", "all", 500, 1, 100000000, "Rarity of Desert Academy Structure. Higher number = more rare");
        this.spawnCoelacanth = config.getBoolean("Spawn Coelacanth", "all", true, "True if Coelacanths are to spawn naturally in oceans");
        this.spawnSturgeon = config.getBoolean("Spawn Sturgeon", "all", true, "True if Sturgeons are to spawn naturally in rivers");
        this.spawnAlligatorGar = config.getBoolean("Spawn Alligator Gar", "all", true, "True if Alligator Gars are to spawn naturally in swamps");
        this.spawnNautilus = config.getBoolean("Spawn Nautilus", "all", true, "True if Nautilus are to spawn naturally in oceans");
        this.spawnTarSlimes = config.getBoolean("Spawn Tar Slimes", "all", true, "True if Tar Slimes are to spawn naturally in tar pits");
        this.tarSlimeSpawnRate = config.getInt("Tar Slime Spawn Rarity", "all", 75, 1, 100000000, "Chance of Tar Slimes spawning in a tarpit per tick. Higher number = more rare");
        this.nautilusSpawnRate = config.getInt("Nautilus Spawn Rarity", "all", 6, 1, 100000000, "Chance of Nautilus spawning in a new chunks. Higher number = more rare");
        this.coelacanthSpawnRate = config.getInt("Coelacanth Spawn Rarity", "all", 4, 1, 100000000, "Chance of Coelacanth spawning in a new chunks. Higher number = more rare");
        this.alligatorGarSpawnRate = config.getInt("Alligator Gar Spawn Rarity", "all", 3, 1, 100000000, "Chance of Alligator Gars spawning in a new chunks. Higher number = more rare");
        this.sturgeonSpawnRate = config.getInt("Sturgeon Spawn Rarity", "all", 4, 1, 100000000, "Chance of Sturgeon spawning in a new chunks. Higher number = more rare");
        this.healingDinos = config.getBoolean("Healing Dinos", "all", true, "True if Dinosaurs can heal with food");
        this.starvingDinos = config.getBoolean("Starving Dinos", "all", true, "True if Dinosaurs have hunger");
        this.dinosaurUpdateTick = config.getInt("Dino Upgrade Tick", "all", 10, 1, 10000, "Dinosaurs will conduct expensive CPU operations like looking for plants or feeders every fraction of a tick(20 per second). Default is 10, so dinosaurs therefore update every half-second.");
        this.pregnancyTime = config.getInt("Pregnancy TIme", "all", 10000, 1, 1000000000, "How long do mammal pregnancies last, in ticks.");
        this.dinoBlockBreaking = config.getBoolean("Dino Block Breaking", "all", true, "True if certain Dinosaurs can break blocks weaker than iron");
        this.dinoEatModdedMobs = config.getBoolean("Dino Eat Modded Mobs", "all", true, "True if Dinosaurs can eat non-vanilla mobs");
        this.customMainMenu = config.getBoolean("Custom Main Menu", "all", true, "True if Custom Main Menu is enabled");
        this.featheredDeinonychus = config.getBoolean("Feathered Deinonychus", "all", true, "True if Deinonychus is accurate to science and not a fake movie monster or a relic of past age of scientific progress");
        this.featheredGallimimus = config.getBoolean("Feathered Gallimimus", "all", true, "True if Gallimimus is accurate to science and not a fake movie monster or a relic of past age of scientific progress");
        this.featheredCompsognathus = config.getBoolean("Feathered Compsognathus", "all", true, "True if Compsognathus should be represented with plumage");
        this.quilledTriceratops = config.getBoolean("Quilled Triceratops", "all", false, "True if Triceratops should have quills like one of its distant relatives");
        this.featheredVelociraptor = config.getBoolean("Feathered Velociraptor", "all", true, "True if Velociraptor is accurate to science and not a fake movie monster or a relic of past age of scientific progress");
        this.featheredTherizinosaurus = config.getBoolean("Feathered Therizinosaurus", "all", true, "True if Therizinosaurus should be represented with plumage");
        this.featheredDryosaurus = config.getBoolean("Feathered Dryosaurus", "all", false, "True if Dryosaurus should be represented with plumage");
        this.eggsLikeChickens = config.getBoolean("Eggs Like Chickens", "all", false, "True if Dinosaurs should create item eggs instead of entities");
        this.dinosaurBreeding = config.getBoolean("Dinosaur Breeding", "all", true, "True if Dinosaurs should breed");
        this.flyingTargetMaxHeight = config.getInt("Flying Target Max Height", "all", 128, 1, 512, "Maximum height that flying creatures should soar to");
        this.dimensionIDDarknessLair = config.getInt("Lair of Darkness Dimension ID", "all", -23, -1000000, 1000000, "Lair of Darkness Dimension ID");
        this.dimensionIDTreasure = config.getInt("Treasure Room Dimension ID", "all", -24, -1000000, 1000000, "Treasure Room Dimension ID");
        this.homePortalExitDimension = config.getInt("Home Portal Exit Dimension ID", "all", 0, -1000000, 1000000, "Dimension ID that home portals should return players to");
        this.logCascadingWorldGen = config.getBoolean("Log Cascading World Gen", "all", false, "True if you want to spam the console");
        this.animalsFearDinosaurs = config.getBoolean("Animals Fear Dinosaurs", "all", false, "True if vanilla animals should run away from dinosaurs");

    }
}
