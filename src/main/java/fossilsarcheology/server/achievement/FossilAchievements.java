package fossilsarcheology.server.achievement;

public class FossilAchievements {
   /* public static final Achievement FIRST_FOSSIL = new Achievement("achievement.firstFossil", "firstFossil", 0, 0, new ItemStack(FAItemRegistry.BIOFOSSIL), null).registerStat();
    public static final Achievement ANALYZER = new Achievement("achievement.analyzer", "analyzer", 0, -2, new ItemStack(FABlockRegistry.ANALYZER_ACTIVE), FIRST_FOSSIL).registerStat();
    public static final Achievement TABLET = new Achievement("achievement.tablet", "tablet", -2, -2, new ItemStack(FAItemRegistry.STONE_TABLET), ANALYZER).registerStat();
    public static final Achievement DINO_DNA = new Achievement("achievement.dinoDna", "dinoDna", 0, -4, new ItemStack(PrehistoricEntityType.TYRANNOSAURUS.dnaItem), ANALYZER).registerStat().setSpecial();
    public static final Achievement CULTIVATE = new Achievement("achievement.cultivate", "cultivate", 2, -4, new ItemStack(FABlockRegistry.CULTIVATE_ACTIVEE), DINO_DNA).registerStat();
    public static final Achievement DINO_EGG = new Achievement("achievement.dinoEgg", "dinoEgg", 4, -4, new ItemStack(PrehistoricEntityType.TYRANNOSAURUS.eggItem), CULTIVATE).registerStat();
    public static final Achievement MAMMAL_EMBRYO = new Achievement("achievement.mammalEmbryo", "mammalEmbryo", 4, -6, new ItemStack(PrehistoricEntityType.MAMMOTH.embryoItem), CULTIVATE).registerStat();
    public static final Achievement BIRD_EGG = new Achievement("achievement.birdEgg", "birdEgg", 4, -2, new ItemStack(PrehistoricEntityType.CONFUCIUSORNIS.bestBirdEggItem), CULTIVATE).registerStat();
    public static final Achievement SIFTER = new Achievement("achievement.sifter", "sifter", -2, 0, new ItemStack(FABlockRegistry.SIFTER_ACTIVE), null).registerStat();
    public static final Achievement FOSSIL_SEEDS = new Achievement("achievement.fossilSeeds", "fossilSeeds", -2, -3, new ItemStack(FAItemRegistry.FOSSIL_SEED), ANALYZER).registerStat();
    public static final Achievement FAILURESAURUS = new Achievement("achievement.failuresaurus", "failuresaurus", 4, 2, new ItemStack(FABlockRegistry.SLIME_TRAIL), CULTIVATE).registerStat();
    public static final Achievement FAILURESAURUS_ANALYZER = new Achievement("achievement.failuresaurusAnalyzer", "failuresaurusAnalyzer", 4, 4, new ItemStack(FAItemRegistry.FAILURESAURUS_FLESH), FAILURESAURUS).registerStat();
    public static final Achievement FIND_ANU_TOTEM = new Achievement("achievement.findAnuTotem", "findAnuTotem", -6, 6, new ItemStack(FABlockRegistry.ANU_STATUE), null).registerStat();
    public static final Achievement ANU_PORTAL = new Achievement("achievement.anuPortal", "anuPortal", -4, 5, new ItemStack(FABlockRegistry.ANU_PORTAL), FIND_ANU_TOTEM).registerStat();
    public static final Achievement ANUBITE_ENCOUNTER = new Achievement("achievement.anubiteEncounter", "anubiteEncounter", -2, 5, new ItemStack(FABlockRegistry.ANUBITE_STATUE), ANU_PORTAL).registerStat();
    public static final Achievement ANU_ATTACK = new Achievement("achievement.anuAttack", "anuAttack", 0, 5, new ItemStack(FAItemRegistry.ANCIENT_SWORD), ANUBITE_ENCOUNTER).registerStat();
    public static final Achievement ANU_DEAD = new Achievement("achievement.anuDead", "anuDead", 2, 5, new ItemStack(FAItemRegistry.BROKEN_SWORD), ANU_ATTACK).registerStat().setSpecial();
    public static final Achievement ARC_WORKBENCH = new Achievement("achievement.arcWorkbench", "arcWorkbench", -6, 0, new ItemStack(FABlockRegistry.WORKTABLE_ACTIVE), null).registerStat();
    public static final Achievement FIXED_SWORD = new Achievement("achievement.fixedSword", "fixedSword", -6, -2, new ItemStack(FAItemRegistry.ANCIENT_SWORD), ARC_WORKBENCH).registerStat();
    public static final Achievement FIXED_HELMET = new Achievement("achievement.fixedHelmet", "fixedHelmet", -6, 2, new ItemStack(FAItemRegistry.ANCIENT_HELMET), ARC_WORKBENCH).registerStat();
    public static final Achievement FIXED_VASE = new Achievement("achievement.fixedVase", "fixedVase", -4, 0, new ItemStack(FABlockRegistry.AMPHORA_VASE, 1, 1), ARC_WORKBENCH).registerStat();
    public static final Achievement DINOPEDIA = new Achievement("achievement.dinopedia", "dinopedia", 0, -6, new ItemStack(FAItemRegistry.DINOPEDIA), DINO_DNA).registerStat();
    public static final Achievement SCARAB = new Achievement("achievement.scarab", "scarab", 0, 2, new ItemStack(FAItemRegistry.SCARAB_GEM), FIRST_FOSSIL).registerStat();
    public static final Achievement SCARAB_TOOLS = new Achievement("achievement.scarabTools", "scarabTools", 0, 4, new ItemStack(FAItemRegistry.SCARAB_SWORD), SCARAB).registerStat();
    public static final Achievement BLUE_SCARAB = new Achievement("achievement.blueScarab", "blueScarab", -2, 2, new ItemStack(FAItemRegistry.AQUATIC_SCARAB_GEM), SCARAB).registerStat();
    public static final Achievement KEY = new Achievement("achievement.key", "key", 2, 7, new ItemStack(FAItemRegistry.ANCIENT_KEY), ANU_DEAD).registerStat();
    public static final Achievement WTF = new Achievement("achievement.inTreasure", "inTreasure", 4, 7, new ItemStack(Blocks.STONEBRICK), KEY).registerStat();
    public static final Achievement CLOCK = new Achievement("achievement.clock", "clock", 6, 7, new ItemStack(FAItemRegistry.ANCIENT_CLOCK), WTF).registerStat();
    public static final Achievement FIRST_DINO = new Achievement("achievement.firstDino", "firstDino", 6, -4, new ItemStack(FAItemRegistry.SKULL, 1, 0), DINO_EGG).registerStat().setSpecial();
    public static final Achievement THE_KING = new Achievement("achievement.theKing", "theKing", 8, -5, new ItemStack(FAItemRegistry.SKULL, 1, 2), FIRST_DINO).registerStat().setSpecial();
    public static final Achievement SQUIRE = new Achievement("achievement.squire", "squire", 7, -1, new ItemStack(FAItemRegistry.SKULL, 1, DinosaurBoneType.ALLOSAURUS.ordinal()), FIRST_DINO).registerStat();
    public static final Achievement USURPER = new Achievement("achievement.usurper", "usurper", 8, -3, new ItemStack(FAItemRegistry.SKULL, 1, DinosaurBoneType.SPINOSAURUS.ordinal()), FIRST_DINO).registerStat().setSpecial();
    public static final Achievement SHEAR = new Achievement("achievement.shear", "shear", 6, -6, new ItemStack(Blocks.WOOL, 1, 12), MAMMAL_EMBRYO).registerStat();
    public static final Achievement DEAD_DODO = new Achievement("achievement.deadDodo", "deadDodo", 4, 0, new ItemStack(FAItemRegistry.SKULL, 1, DinosaurBoneType.DODO.ordinal()), BIRD_EGG).registerStat();
    public static final Achievement TREX_KILL = new Achievement("achievement.trexKill", "trexKill", 8, -7, new ItemStack(FAItemRegistry.TOOTH_DAGGER), THE_KING).registerStat();

    public static final AchievementPage PAGE = new AchievementPage("Fossils and Archeology",
            FIRST_FOSSIL, ANALYZER, TABLET, FIRST_DINO, DINO_DNA, CULTIVATE, DINO_EGG, MAMMAL_EMBRYO, BIRD_EGG, SIFTER, FOSSIL_SEEDS,
            FAILURESAURUS, FAILURESAURUS_ANALYZER, FIND_ANU_TOTEM, ANU_PORTAL, ANUBITE_ENCOUNTER, ANU_ATTACK, ANU_DEAD, ARC_WORKBENCH,
            FIXED_SWORD, FIXED_HELMET, FIXED_VASE, DINOPEDIA, SCARAB, SCARAB_TOOLS, BLUE_SCARAB, KEY, WTF, CLOCK, THE_KING,
            USURPER, SQUIRE, SHEAR, DEAD_DODO, TREX_KILL);

    public static void register() {
        AchievementPage.registerAchievementPage(PAGE);
    }
    */
}
