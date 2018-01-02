	package fossilsarcheology.server.block;

    import fossilsarcheology.Revival;
import fossilsarcheology.server.api.BlockEntity;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
    import net.minecraftforge.event.RegistryEvent;
    import net.minecraftforge.fml.common.registry.GameRegistry;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

public class FABlockRegistry {
    public static final List<Block> BLOCKS = new ArrayList<>();


    public static final FossilBlock FOSSIL = new FossilBlock();
    public static final AmberOreBlock AMBER_ORE = new AmberOreBlock();
    public static final SkullBlock SKULL_BLOCK = new SkullBlock(false);
    public static final SkullBlock SKULL_LANTERN = new SkullBlock(true);
    public static final CultivateBlock CULTIVATE_IDLE = new CultivateBlock(false);
    public static final CultivateBlock CULTIVATE_ACTIVEE = new CultivateBlock(true);
    public static final WorktableBlock WORKTABLE_IDLE = new WorktableBlock(false);
    public static final WorktableBlock WORKTABLE_ACTIVE = new WorktableBlock(true);
    public static final SifterBlock SIFTER_IDLE = new SifterBlock(false);
    public static final SifterBlock SIFTER_ACTIVE = new SifterBlock(true);
    public static final AnalyzerBlock ANALYZER = new AnalyzerBlock(false);
    public static final AnalyzerBlock ANALYZER_ACTIVE = new AnalyzerBlock(true);
    public static final BubbleBlowerBlock BUBBLE_MACHINE = new BubbleBlowerBlock();
    public static final FeederBlock FEEDER = new FeederBlock();
    public static final PermafrostBlock PERMAFROST = new PermafrostBlock();
    public static final IcedStoneBlock ICED_STONE = new IcedStoneBlock();
    public static final DenseSandBlock DENSE_SAND = new DenseSandBlock();
    public static final StrongGlassBlock STRONG_GLASS = new StrongGlassBlock();
    public static final AncientGlassBlock ANCIENT_GLASS = new AncientGlassBlock();
    public static final SlimeTrailBlock SLIME_TRAIL = new SlimeTrailBlock();
    public static final AncientStoneBlock ANCIENT_STONE = new AncientStoneBlock();
    public static final AncientStonebrickBlock ANCIENT_STONE_BRICK = new AncientStonebrickBlock();
    public static final FossilStairsBlock ANCIENT_STONE_STAIRS = new FossilStairsBlock(ANCIENT_STONE.getDefaultState(), "ancientStoneStairs");
    public static final FossilSlabBlock ANCIENT_STONE_SINGLESLAB = new AncientStoneSlabBlock.Half("ancientStoneSlab", 1.7F, 7.5F, SoundType.STONE);
    public static final FossilSlabBlock ANCIENT_STONE_DOUBLESLAB = new AncientStoneSlabBlock.Double("ancientStoneSlab", 1.7F, 7.5F, SoundType.STONE);
    public static final VolcanicAshBlock VOLCANIC_ASH = new VolcanicAshBlock("ash");
    public static final VolcanicAshBlock VOLCANIC_BRICK = new VolcanicAshBlock("brick");
    public static final VolcanicAshBlock VOLCANIC_ROCK = new VolcanicAshBlock("rock");
    public static final FossilStairsBlock VOLCANIC_STAIRS = new FossilStairsBlock(VOLCANIC_BRICK.getDefaultState(), "volcanicStairs");
    public static final FossilSlabBlock VOLCANIC_DOUBLESLAB = new VolcanicSlabBlock.Double("volcanicSlab", 1.4F, 7.5F, SoundType.STONE);
    public static final FossilSlabBlock VOLCANIC_SINGLESLAB = new VolcanicSlabBlock.Half("volcanicSlab", 1.4F, 7.5F, SoundType.STONE);
    public static final AnuPortalBlock ANU_PORTAL = new AnuPortalBlock();
    public static final AnuStatueBlock ANU_STATUE = new AnuStatueBlock();
    public static final HomePortalBlock HOME_PORTAL = new HomePortalBlock();
    public static final AnubiteStatueBlock ANUBITE_STATUE = new AnubiteStatueBlock();
    public static final AncientChestBlock ANCIENT_CHEST = new AncientChestBlock();
    public static final SarcophagusBlock SARCOPHAGUS = new SarcophagusBlock();
    public static final FigurineBlock FIGURINE = new FigurineBlock();
    public static final AmphoraVaseBlock AMPHORA_VASE = new AmphoraVaseBlock();
    public static final KylixVaseBlock KYLIX_VASE = new KylixVaseBlock();
    public static final VoluteVaseBlock VOLUTE_VASE = new VoluteVaseBlock();
    public static final TimeMachineBlock TIME_MACHINE = new TimeMachineBlock();
    public static final DrumBlock DRUM = new DrumBlock();
    public static final TarBlock TAR = new TarBlock();
    public static final ObsidianSpikesBlock OBSIDIAN_SPIKES = new ObsidianSpikesBlock();
    public static final PalmLogBlock PALM_LOG = new PalmLogBlock();
    public static final PalmLeavesBlock PALM_LEAVES = new PalmLeavesBlock();
    public static final PalmSaplingBlock PALM_SAPLING = new PalmSaplingBlock();
    public static final PalmPlanksBlock PALM_PLANKS = new PalmPlanksBlock();
    public static final FossilSlabBlock PALM_PLANKS_DOUBLESLAB = new PalmPlanksSlabBlock.Double("palmPlanksSlab", 1.4F, 7.5F, SoundType.WOOD);
    public static final FossilSlabBlock PALM_PLANKS_SINGLESLAB = new PalmPlanksSlabBlock.Half("palmPlanksSlab", 1.4F, 7.5F, SoundType.WOOD);
    public static final FossilStairsBlock PALM_PLANKS_STAIRS = new FossilStairsBlock(PALM_PLANKS.getDefaultState(), "palmStairs");

    public static final AncientWoodBlock ANCIENT_WOOD = new AncientWoodBlock();
    public static final AncientWoodPillarBlock ANCIENT_WOOD_PILLAR = new AncientWoodPillarBlock();
    public static final AncientWoodPlateBlock ANCIENT_WOOD_PLATE = new AncientWoodPlateBlock();
    public static final FossilStairsBlock ANCIENT_WOOD_STAIRS = new FossilStairsBlock(ANCIENT_WOOD.getDefaultState(), "ancient_wood_stairs");
    public static final FossilSlabBlock ANCIENT_WOOD_DOUBLESLAB = new AncientWoodSlabBlock.Double("ancientWoodSlab", 1.4F, 7.5F, SoundType.WOOD);
    public static final FossilSlabBlock ANCIENT_WOOD_SINGLESLAB = new AncientWoodSlabBlock.Half("ancientWoodSlab", 1.4F, 7.5F, SoundType.WOOD);

    public static final FernsBlock FERNS = new FernsBlock();
    public static final DillhoffiaFlowerBlock DILLHOFFIA_FLOWER = new DillhoffiaFlowerBlock();
    public static final TallFlowerBlock SARRACENIA_FLOWER = new TallFlowerBlock("sarracenia");
    public static final ShortFlowerBlock CEPHALOTAXUS_FLOWER = new ShortFlowerBlock("cephalotaxus");
    public static final ShortFlowerBlock LICOPODIOPHYTA_FLOWER = new ShortFlowerBlock("licopodiophyta");
    public static final TallFlowerBlock PALEOPANAX_FLOWER = new TallFlowerBlock("paleopanax");
    public static final ShortFlowerBlock ZAMITES_FLOWER = new ShortFlowerBlock("zamites");
    public static final ShortFlowerBlock BENNETTITALES_SMALL_FLOWER = new ShortFlowerBlock("bennettitales_small");
    public static final TallFlowerBlock BENNETTITALES_LARGE_FLOWER = new TallFlowerBlock("bennettitales_large");
    public static final ShortFlowerBlock WELWITSCHIA_FLOWER = new ShortFlowerBlock("welwitschia");
    public static final ShortFlowerBlock HORSETAIL_SMALL_FLOWER = new ShortFlowerBlock("horsetail_small");
    public static final TallFlowerBlock HORSETAIL_LARGE_FLOWER = new TallFlowerBlock("horsetail_large");
    public static final TallFlowerBlock MUTANT_FLOWER = new TallFlowerBlock("mutant_plant");
    public static final TempskyaBlock TEMPSKYA_FLOWER = new TempskyaBlock();
    public static final ShortFlowerBlock VACCINIUM_FLOWER = new ShortFlowerBlock("vaccinium");
    public static final ShortFlowerBlock OSMUNDA_FLOWER = new ShortFlowerBlock("osmunda");
    public static final TallFlowerBlock CRATAEGUS_FLOWER = new TallFlowerBlock("crataegus");
    public static final ShortFlowerBlock FLORISSANTIA_FLOWER = new ShortFlowerBlock("florissantia");
    public static final ShortFlowerBlock EPENDRA_FLOWER = new ShortFlowerBlock("ependra");


    public static void registerBlock(RegistryEvent.Register<Block> event, Block block) {
        String name = block.getUnlocalizedName().substring("tile.".length());
        ResourceLocation identifier = new ResourceLocation(Revival.MODID, name);
        block.setRegistryName(identifier);
        event.getRegistry().register(block);
        BLOCKS.add(block);

    }
}
