package com.fossil.fossil.block;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.block.custom_blocks.SkullBlock;
import com.fossil.fossil.block.custom_blocks.TallFlowerBlock;
import com.fossil.fossil.block.custom_blocks.*;
import com.fossil.fossil.item.*;
import com.fossil.fossil.material.ModFluids;
import com.fossil.fossil.world.feature.tree.CordaitesTreeGrower;
import com.fossil.fossil.world.feature.tree.SigillariaTreeGrower;
import dev.architectury.core.block.ArchitecturyLiquidBlock;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(Fossil.MOD_ID, Registry.BLOCK_REGISTRY);

    private static ToIntFunction<BlockState> activeBlockEmission(int lightValue) {
        return arg -> arg.getValue(CustomEntityBlock.ACTIVE) ? lightValue : 0;
    }

    public static final RegistrySupplier<BubbleBlowerBlock> BUBBLE_BLOWER = registerBlock("bubble_blower",
            () -> new BubbleBlowerBlock(BlockBehaviour.Properties.of(Material.METAL).strength(3).sound(SoundType.METAL).requiresCorrectToolForDrops())
    );
    public static final RegistrySupplier<Block> ANALYZER = registerBlock("analyzer",
            () -> new AnalyzerBlock(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.METAL).strength(3f).requiresCorrectToolForDrops()
                    .lightLevel(activeBlockEmission(14))));
    public static final RegistrySupplier<SifterBlock> SIFTER = registerBlock("sifter",
            () -> new SifterBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.5f).sound(SoundType.METAL)));
    public static final RegistrySupplier<CultivateBlock> CULTIVATE = registerBlock("cultivate", () -> new CultivateBlock(
            BlockBehaviour.Properties.of(Material.GLASS, MaterialColor.COLOR_CYAN).strength(2f).requiresCorrectToolForDrops()
                    .lightLevel(activeBlockEmission(14)).noOcclusion()));
    public static final RegistrySupplier<Block> WORKTABLE = registerBlock("worktable", () -> new WorktableBlock(
            BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).strength(1f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<FeederBlock> FEEDER = registerBlock("feeder",
            () -> new FeederBlock(BlockBehaviour.Properties.of(Material.METAL).strength(3)));
    public static final RegistrySupplier<ArchitecturyLiquidBlock> TAR = registerBlockWithoutBlockItem("tar",
            () -> TarBlock.get(ModFluids.TAR, BlockBehaviour.Properties.copy(Blocks.WATER).isSuffocating(ModBlocks::always).isViewBlocking(ModBlocks::always)));
    public static final RegistrySupplier<AnuStatueBlock> ANU_STATUE = registerBlockWithCustomBlockItem("anu_statue",
            () -> new AnuStatueBlock(BlockBehaviour.Properties.of(Material.STONE).noOcclusion().strength(-1, 60000000)),
            block -> AnuStatueBlockItem.get(block, new Item.Properties().tab(ModTabs.FABLOCKTAB)));
    public static final RegistrySupplier<AnubiteStatueBlock> ANUBITE_STATUE = registerBlockWithCustomBlockItem("anubite_statue",
            () -> new AnubiteStatueBlock(BlockBehaviour.Properties.of(Material.STONE).noOcclusion().strength(-1, 60000000)),
            block -> AnubiteStatueBlockItem.get(block, new Item.Properties().tab(ModTabs.FABLOCKTAB)));
    public static final RegistrySupplier<AnuPortal> ANU_PORTAL = registerBlock("anu_portal",
            () -> new AnuPortal(BlockBehaviour.Properties.copy(Blocks.NETHER_PORTAL).noDrops()));
    public static final RegistrySupplier<AnuPortal> HOME_PORTAL = registerBlock("home_portal",
            () -> new AnuPortal(BlockBehaviour.Properties.copy(Blocks.NETHER_PORTAL).noDrops()));
    public static final RegistrySupplier<AncientChestBlock> ANCIENT_CHEST = registerBlockWithCustomBlockItem("ancient_chest",
            () -> new AncientChestBlock(BlockBehaviour.Properties.of(Material.WOOD).noOcclusion().strength(-1, 3600000).noDrops()),
            block -> AncientChestBlockItem.get(block, new Item.Properties().tab(ModTabs.FABLOCKTAB)));
    public static final RegistrySupplier<Block> SARCOPHAGUS = registerBlockWithCustomBlockItem("sarcophagus",
            () -> new SarcophagusBlock(BlockBehaviour.Properties.of(Material.STONE).noOcclusion().strength(-1, 60000000)),
            block -> SarcophagusBlockItem.get(block, new Item.Properties().tab(ModTabs.FABLOCKTAB)));
    public static final RegistrySupplier<OreBlock> AMBER_ORE = registerBlock("amber_ore",
            () -> new OreBlock(BlockBehaviour.Properties.of(Material.STONE).strength(3f).requiresCorrectToolForDrops()));
    public static final RegistrySupplier<Block> AMBER_BLOCK = registerBlock("amber_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(3f).requiresCorrectToolForDrops()));
    public static final RegistrySupplier<IcedStoneBlock> ICED_STONE = registerBlock("iced_stone",
            () -> new IcedStoneBlock(BlockBehaviour.Properties.of(Material.STONE).strength(1.5f, 10f).requiresCorrectToolForDrops()
                    .sound(SoundType.GLASS).randomTicks()));
    public static final RegistrySupplier<Block> OBSIDIAN_SPIKES = registerBlock("obsidian_spikes",
            () -> new ObsidianSpikesBlock(BlockBehaviour.Properties.of(Material.STONE).strength(50, 2000).sound(SoundType.STONE)
                    .requiresCorrectToolForDrops().noOcclusion()));
    public static final RegistrySupplier<DenseSandBlock> DENSE_SAND = registerBlock("dense_sand",
            () -> new DenseSandBlock(9205340, BlockBehaviour.Properties.of(Material.SAND).strength(3f, 15f).sound(SoundType.SAND)));
    public static final RegistrySupplier<SkullBlock> SKULL_BLOCK = registerBlock("skull",
            () -> new SkullBlock(BlockBehaviour.Properties.of(Material.STONE).strength(4f, 15f).requiresCorrectToolForDrops()));
    public static final RegistrySupplier<SkullBlock> SKULL_LANTERN = registerBlock("skull_lantern",
            () -> new SkullBlock(BlockBehaviour.Properties.of(Material.STONE).lightLevel(value -> 14).strength(4f, 15f)
                    .requiresCorrectToolForDrops()));
    public static final RegistrySupplier<Block> SLIME_TRAIL = registerBlock("slime_trail",
            () -> new RailBlock(BlockBehaviour.Properties.copy(Blocks.SLIME_BLOCK)));
    public static final RegistrySupplier<Block> ANCIENT_STONE = registerBlock("ancient_stone",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(1.5f).requiresCorrectToolForDrops()));
    public static final RegistrySupplier<Block> ANCIENT_STONE_BRICKS = registerBlock("ancient_stone_bricks",
            () -> new Block(BlockBehaviour.Properties.copy(ANCIENT_STONE.get())));
    public static final RegistrySupplier<SlabBlock> ANCIENT_STONE_SLAB = registerBlock("ancient_stone_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(ANCIENT_STONE.get())));
    public static final RegistrySupplier<StairBlock> ANCIENT_STONE_STAIRS = registerBlock("ancient_stone_stairs",
            () -> new StairBlock(ANCIENT_STONE.get().defaultBlockState(), BlockBehaviour.Properties.copy(ANCIENT_STONE.get())));
    public static final RegistrySupplier<Block> ANCIENT_STONE_WALL = registerBlock("ancient_stone_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(ANCIENT_STONE.get())));
    public static final RegistrySupplier<Block> ANCIENT_WOOD = registerBlock("ancient_wood",
            () -> new Block(BlockBehaviour.Properties.of(Material.WOOD).strength(2f, 3f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<SlabBlock> ANCIENT_WOOD_SLAB = registerBlock("ancient_wood_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(ANCIENT_WOOD.get())));
    public static final RegistrySupplier<RotatedPillarBlock> ANCIENT_WOOD_PILLAR = registerBlock("ancient_wood_pillar",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.copy(ANCIENT_WOOD.get())));
    public static final RegistrySupplier<StairBlock> ANCIENT_WOOD_STAIRS = registerBlock("ancient_wood_stairs",
            () -> new StairBlock(ANCIENT_WOOD.get().defaultBlockState(), BlockBehaviour.Properties.copy(ANCIENT_WOOD.get())));

    private static boolean never(BlockState state, BlockGetter blockGetter, BlockPos pos) {
        return false;
    }

    private static boolean always(BlockState state, BlockGetter blockGetter, BlockPos pos) {
        return true;
    }

    public static final RegistrySupplier<ClearGlassBlock> REINFORCED_GLASS = registerBlock("reinforced_glass",
            () -> new ClearGlassBlock(BlockBehaviour.Properties.of(Material.GLASS).strength(3f, 25f).noOcclusion()
                    .isViewBlocking(ModBlocks::never)));
    public static final RegistrySupplier<ClearGlassBlock> ANCIENT_GLASS = registerBlock("ancient_glass",
            () -> new ClearGlassBlock(BlockBehaviour.Properties.of(Material.GLASS).strength(1f).noOcclusion().isViewBlocking(ModBlocks::never)));
    public static final RegistrySupplier<DrumBlock> DRUM = registerBlock("drum",
            () -> new DrumBlock(BlockBehaviour.Properties.of(Material.WOOD).sound(SoundType.WOOD)));
    public static final RegistrySupplier<Block> FOSSIL = registerBlock("fossil",
            () -> new FossilBlock(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).strength(2f).requiresCorrectToolForDrops()));
    public static final RegistrySupplier<Block> TARRED_DIRT = registerBlock("tarred_dirt",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.DIRT)));
    public static final RegistrySupplier<Block> PERMAFROST_BLOCK = registerBlock("permafrost_block",
            () -> new PermafrostBlock(
                    BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BLUE).strength(1f).requiresCorrectToolForDrops()));
    public static final RegistrySupplier<Block> VOLCANIC_ASH = registerBlock("volcanic_ash",
            () -> new Block(BlockBehaviour.Properties.of(Material.DIRT, MaterialColor.COLOR_BLACK).strength(0.2f).requiresCorrectToolForDrops().sound(
                    SoundType.GRAVEL)));
    public static final RegistrySupplier<Block> VOLCANIC_ROCK = registerBlock("volcanic_rock",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).strength(1f).requiresCorrectToolForDrops()));
    public static final RegistrySupplier<Block> VOLCANIC_BRICKS = registerBlock("volcanic_bricks",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).strength(1.5f).requiresCorrectToolForDrops()));

    public static final RegistrySupplier<Block> VOLCANIC_BRICK_SLAB = registerBlock("volcanic_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(VOLCANIC_BRICKS.get())));
    public static final RegistrySupplier<Block> VOLCANIC_BRICK_STAIRS = registerBlock("volcanic_brick_stairs",
            () -> new StairBlock(VOLCANIC_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(VOLCANIC_BRICKS.get())));
    public static final RegistrySupplier<Block> VOLCANIC_BRICK_WALL = registerBlock("volcanic_brick_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(VOLCANIC_BRICKS.get())));
    public static final RegistrySupplier<Block> VOLCANIC_TILES = registerBlock("volcanic_tiles",
            () -> new Block(BlockBehaviour.Properties.copy(VOLCANIC_BRICKS.get())));
    public static final RegistrySupplier<Block> VOLCANIC_TILE_SLAB = registerBlock("volcanic_tile_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(VOLCANIC_TILES.get())));
    public static final RegistrySupplier<Block> VOLCANIC_TILE_STAIRS = registerBlock("volcanic_tile_stairs",
            () -> new StairBlock(VOLCANIC_TILES.get().defaultBlockState(), BlockBehaviour.Properties.copy(VOLCANIC_TILES.get())));
    public static final RegistrySupplier<Block> VOLCANIC_TILE_WALL = registerBlock("volcanic_tile_wall",
            () -> new WallBlock(BlockBehaviour.Properties.copy(VOLCANIC_TILES.get())));

    public static final RegistrySupplier<Block> CORDAITES_PLANKS = registerBlock("cordaites_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.SPRUCE_PLANKS).strength(1f).requiresCorrectToolForDrops()));
    public static final RegistrySupplier<Block> CORDAITES_STAIRS = registerBlock("cordaites_stairs",
            () -> new StairBlock(ModBlocks.CORDAITES_PLANKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.of(Material.WOOD).strength(1f).requiresCorrectToolForDrops()));
    public static final RegistrySupplier<Block> CORDAITES_SLAB = registerBlock("cordaites_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_SLAB).strength(1f).requiresCorrectToolForDrops()));
    public static final RegistrySupplier<Block> CORDAITES_FENCE = registerBlock("cordaites_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_FENCE).strength(1f).requiresCorrectToolForDrops()));
    public static final RegistrySupplier<Block> CORDAITES_FENCE_GATE = registerBlock("cordaites_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_FENCE_GATE).strength(1f).requiresCorrectToolForDrops()));
    public static final RegistrySupplier<Block> CORDAITES_DOOR = registerBlock("cordaites_door",
            () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_DOOR).strength(1f).requiresCorrectToolForDrops()));
    public static final RegistrySupplier<Block> CORDAITES_TRAPDOOR = registerBlock("cordaites_trapdoor",
            () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_TRAPDOOR).strength(1f).requiresCorrectToolForDrops()));
    public static final RegistrySupplier<Block> CORDAITES_BUTTON = registerBlock("cordaites_button",
            () -> new StoneButtonBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_BUTTON).strength(1f).requiresCorrectToolForDrops()));
    public static final RegistrySupplier<Block> CORDAITES_PRESSURE_PLATE = registerBlock("cordaites_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.SPRUCE_PRESSURE_PLATE)
                    .strength(1f).requiresCorrectToolForDrops()));

    public static final RegistrySupplier<Block> CORDAITES_LOG = registerBlock("cordaites_log",
            () -> ModFlammableRotatedPillarBlock.get(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));
    public static final RegistrySupplier<Block> CORDAITES_WOOD = registerBlock("cordaites_wood",
            () -> ModFlammableRotatedPillarBlock.get(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)));
    public static final RegistrySupplier<Block> STRIPPED_CORDAITES_LOG = registerBlock("stripped_cordaites_log",
            () -> ModFlammableRotatedPillarBlock.get(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG)));
    public static final RegistrySupplier<Block> STRIPPED_CORDAITES_WOOD = registerBlock("stripped_cordaites_wood",
            () -> ModFlammableRotatedPillarBlock.get(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD)));

    public static final RegistrySupplier<Block> CORDAITES_LEAVES = registerBlock("cordaites_leaves",
            () -> FossilLeavesBlock.get(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)));
    public static final RegistrySupplier<Block> CORDAITES_SAPLING = registerBlock("cordaites_sapling",
            () -> new SaplingBlock(new CordaitesTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));

    public static final RegistrySupplier<Block> SIGILLARIA_PLANKS = registerBlock("sigillaria_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.SPRUCE_PLANKS).strength(1f).requiresCorrectToolForDrops()));
    public static final RegistrySupplier<Block> SIGILLARIA_STAIRS = registerBlock("sigillaria_stairs",
            () -> new StairBlock(ModBlocks.SIGILLARIA_PLANKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.of(Material.WOOD).strength(1f).requiresCorrectToolForDrops()));
    public static final RegistrySupplier<Block> SIGILLARIA_SLAB = registerBlock("sigillaria_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_SLAB).strength(1f).requiresCorrectToolForDrops()));
    public static final RegistrySupplier<Block> SIGILLARIA_FENCE = registerBlock("sigillaria_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_FENCE).strength(1f).requiresCorrectToolForDrops()));
    public static final RegistrySupplier<Block> SIGILLARIA_FENCE_GATE = registerBlock("sigillaria_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_FENCE_GATE).strength(1f).requiresCorrectToolForDrops()));
    public static final RegistrySupplier<Block> SIGILLARIA_DOOR = registerBlock("sigillaria_door",
            () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_DOOR).strength(1f).requiresCorrectToolForDrops()));
    public static final RegistrySupplier<Block> SIGILLARIA_TRAPDOOR = registerBlock("sigillaria_trapdoor",
            () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_TRAPDOOR).strength(1f).requiresCorrectToolForDrops()));
    public static final RegistrySupplier<Block> SIGILLARIA_BUTTON = registerBlock("sigillaria_button",
            () -> new StoneButtonBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_BUTTON).strength(1f).requiresCorrectToolForDrops()));
    public static final RegistrySupplier<Block> SIGILLARIA_PRESSURE_PLATE = registerBlock("sigillaria_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.SPRUCE_PRESSURE_PLATE)
                    .strength(1f).requiresCorrectToolForDrops()));

    public static final RegistrySupplier<Block> SIGILLARIA_LOG = registerBlock("sigillaria_log",
            () -> ModFlammableRotatedPillarBlock.get(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)));
    public static final RegistrySupplier<Block> SIGILLARIA_WOOD = registerBlock("sigillaria_wood",
            () -> ModFlammableRotatedPillarBlock.get(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)));
    public static final RegistrySupplier<Block> STRIPPED_SIGILLARIA_LOG = registerBlock("stripped_sigillaria_log",
            () -> ModFlammableRotatedPillarBlock.get(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG)));
    public static final RegistrySupplier<Block> STRIPPED_SIGILLARIA_WOOD = registerBlock("stripped_sigillaria_wood",
            () -> ModFlammableRotatedPillarBlock.get(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD)));

    public static final RegistrySupplier<Block> SIGILLARIA_LEAVES = registerBlock("sigillaria_leaves",
            () -> FossilLeavesBlock.get(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)));
    public static final RegistrySupplier<Block> SIGILLARIA_SAPLING = registerBlock("sigillaria_sapling",
            () -> new SaplingBlock(new SigillariaTreeGrower(), BlockBehaviour.Properties.copy(Blocks.OAK_SAPLING)));

    public static final List<RegistrySupplier<VaseBlock>> VASES = new ArrayList<>();
    public static final RegistrySupplier<VaseBlock> VOLUTE_VASE_DAMAGED = registerVolute(VaseBlock.VaseVariant.DAMAGED);
    public static final RegistrySupplier<VaseBlock> VOLUTE_VASE_RESTORED = registerVolute(VaseBlock.VaseVariant.RESTORED);
    public static final RegistrySupplier<VaseBlock> VOLUTE_VASE_RED_FIGURE = registerVolute(VaseBlock.VaseVariant.RED_FIGURE);
    public static final RegistrySupplier<VaseBlock> VOLUTE_VASE_BLACK_FIGURE = registerVolute(VaseBlock.VaseVariant.BLACK_FIGURE);
    public static final RegistrySupplier<VaseBlock> VOLUTE_VASE_PORCELAIN = registerVolute(VaseBlock.VaseVariant.PORCELAIN);

    public static final RegistrySupplier<VaseBlock> KYLIX_VASE_DAMAGED = registerKylix(VaseBlock.VaseVariant.DAMAGED);
    public static final RegistrySupplier<VaseBlock> KYLIX_VASE_RESTORED = registerKylix(VaseBlock.VaseVariant.RESTORED);
    public static final RegistrySupplier<VaseBlock> KYLIX_VASE_RED_FIGURE = registerKylix(VaseBlock.VaseVariant.RED_FIGURE);
    public static final RegistrySupplier<VaseBlock> KYLIX_VASE_BLACK_FIGURE = registerKylix(VaseBlock.VaseVariant.BLACK_FIGURE);
    public static final RegistrySupplier<VaseBlock> KYLIX_VASE_PORCELAIN = registerKylix(VaseBlock.VaseVariant.PORCELAIN);

    public static final RegistrySupplier<VaseBlock> AMPHORA_VASE_DAMAGED = registerAmphora(VaseBlock.VaseVariant.DAMAGED);
    public static final RegistrySupplier<VaseBlock> AMPHORA_VASE_RESTORED = registerAmphora(VaseBlock.VaseVariant.RESTORED);
    public static final RegistrySupplier<VaseBlock> AMPHORA_VASE_RED_FIGURE = registerAmphora(VaseBlock.VaseVariant.RED_FIGURE);
    public static final RegistrySupplier<VaseBlock> AMPHORA_VASE_BLACK_FIGURE = registerAmphora(VaseBlock.VaseVariant.BLACK_FIGURE);
    public static final RegistrySupplier<VaseBlock> AMPHORA_VASE_PORCELAIN = registerAmphora(VaseBlock.VaseVariant.PORCELAIN);

    private static RegistrySupplier<VaseBlock> registerVolute(VaseBlock.VaseVariant variant) {
        return registerVase("volute", variant, () -> new VoluteVaseBlock(variant));
    }

    private static RegistrySupplier<VaseBlock> registerKylix(VaseBlock.VaseVariant variant) {
        return registerVase("kylix", variant, () -> new KylixVaseBlock(variant));
    }

    private static RegistrySupplier<VaseBlock> registerAmphora(VaseBlock.VaseVariant variant) {
        return registerVase("amphora", variant, () -> new AmphoraVaseBlock(variant));
    }

    private static RegistrySupplier<VaseBlock> registerVase(String name, VaseBlock.VaseVariant variant, Supplier<VaseBlock> supplier) {
        var toReturn = registerBlock("vase_" + name + "_" + variant.getSerializedName(), supplier);
        VASES.add(toReturn);
        return toReturn;
    }

    public static final RegistrySupplier<FigurineBlock> STEVE_FIGURINE_BROKEN = registerFigurine("steve_broken");
    public static final RegistrySupplier<FigurineBlock> STEVE_FIGURINE_DAMAGED = registerFigurine("steve_damaged");
    public static final RegistrySupplier<FigurineBlock> STEVE_FIGURINE_PRISTINE = registerFigurine("steve_pristine");
    public static final RegistrySupplier<FigurineBlock> SKELETON_FIGURINE_BROKEN = registerFigurine("skeleton_broken");
    public static final RegistrySupplier<FigurineBlock> SKELETON_FIGURINE_DAMAGED = registerFigurine("skeleton_damaged");
    public static final RegistrySupplier<FigurineBlock> SKELETON_FIGURINE_PRISTINE = registerFigurine("skeleton_pristine");
    public static final RegistrySupplier<FigurineBlock> ZOMBIE_FIGURINE_BROKEN = registerFigurine("zombie_broken");
    public static final RegistrySupplier<FigurineBlock> ZOMBIE_FIGURINE_DAMAGED = registerFigurine("zombie_damaged");
    public static final RegistrySupplier<FigurineBlock> ZOMBIE_FIGURINE_PRISTINE = registerFigurine("zombie_pristine");
    public static final RegistrySupplier<FigurineBlock> ENDERMAN_FIGURINE_BROKEN = registerFigurine("enderman_broken");
    public static final RegistrySupplier<FigurineBlock> ENDERMAN_FIGURINE_DAMAGED = registerFigurine("enderman_damaged");
    public static final RegistrySupplier<FigurineBlock> ENDERMAN_FIGURINE_PRISTINE = registerFigurine("enderman_pristine");
    public static final RegistrySupplier<FigurineBlock> PIGZOMBIE_FIGURINE_BROKEN = registerFigurine("piglin_zombie_broken");
    public static final RegistrySupplier<FigurineBlock> PIGZOMBIE_FIGURINE_DAMAGED = registerFigurine("piglin_zombie_damaged");
    public static final RegistrySupplier<FigurineBlock> PIGZOMBIE_FIGURINE_PRISTINE = registerFigurine("piglin_zombie_pristine");
    public static final RegistrySupplier<FigurineBlock> MYSTERIOUS_FIGURINE = registerFigurine("mysterious");

    private static RegistrySupplier<FigurineBlock> registerFigurine(String name) {
        return registerBlock("figurine_" + name, FigurineBlock::new);
    }

    public static RegistrySupplier<ShortFlowerBlock> registerShortFlower(String name, VoxelShape shape) {
        return registerBlock(name,
                () -> new ShortFlowerBlock(BlockBehaviour.Properties.of(Material.PLANT).noCollission().noOcclusion().sound(SoundType.GRASS), shape));
    }

    public static final RegistrySupplier<Block> FERNS = registerBlockWithoutBlockItem("ferns", FernsBlock::new);

    public static RegistrySupplier<TallFlowerBlock> registerTallFlower(String name, VoxelShape... shapes) {
        return registerBlock(name,
                () -> new TallFlowerBlock(BlockBehaviour.Properties.of(Material.PLANT).noCollission().noOcclusion().sound(SoundType.GRASS), shapes));
    }

    public static RegistrySupplier<FourTallFlowerBlock> registerFourTallFlower(String name, VoxelShape shape) {
        return registerBlock(name,
                () -> new FourTallFlowerBlock(BlockBehaviour.Properties.of(Material.PLANT).noCollission().noOcclusion().sound(SoundType.GRASS),
                        shape));
    }

    public static RegistrySupplier<GrowableFlowerBlock> registerGrowableFlower(String name, RegistrySupplier<TallFlowerBlock> tallFlower,
                                                                               VoxelShape shape) {
        return registerBlock(name,
                () -> new GrowableFlowerBlock(BlockBehaviour.Properties.of(Material.PLANT).noCollission().noOcclusion().sound(SoundType.GRASS),
                        tallFlower, shape));
    }

    private static <T extends Block> RegistrySupplier<T> registerBlockWithoutBlockItem(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    private static <T extends Block, R extends BlockItem> RegistrySupplier<T> registerBlockWithCustomBlockItem(String name, Supplier<T> block,
                                                                                                               Function<T, R> blockItem) {
        RegistrySupplier<T> toReturn = BLOCKS.register(name, block);
        ModItems.ITEMS.register(name, () -> blockItem.apply(toReturn.get()));
        return toReturn;
    }

    private static <T extends Block> RegistrySupplier<T> registerBlock(String name, Supplier<T> block) {
        RegistrySupplier<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;

    }

    private static <T extends Block> RegistrySupplier<Item> registerBlockItem(String name, RegistrySupplier<T> block) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(ModTabs.FABLOCKTAB)));
    }

    public static void register() {
        PrehistoricPlantType.register();
        BLOCKS.register();
    }
}
