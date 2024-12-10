package com.github.teamfossilsarcheology.fossil.block;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.block.custom_blocks.SkullBlock;
import com.github.teamfossilsarcheology.fossil.block.custom_blocks.TallFlowerBlock;
import com.github.teamfossilsarcheology.fossil.block.custom_blocks.*;
import com.github.teamfossilsarcheology.fossil.item.*;
import com.github.teamfossilsarcheology.fossil.material.ModFluids;
import com.github.teamfossilsarcheology.fossil.util.Version;
import com.github.teamfossilsarcheology.fossil.world.feature.tree.*;
import dev.architectury.core.block.ArchitecturyLiquidBlock;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

import static net.minecraft.world.level.block.state.BlockBehaviour.Properties;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(FossilMod.MOD_ID, Registry.BLOCK_REGISTRY);

    public static final RegistrySupplier<BubbleBlowerBlock> BUBBLE_BLOWER = registerBlock("bubble_blower",
            () -> new BubbleBlowerBlock(Properties.of(Material.METAL).strength(3).sound(SoundType.METAL).requiresCorrectToolForDrops())
    );
    public static final RegistrySupplier<Block> ANALYZER = registerBlock("analyzer",
            () -> new AnalyzerBlock(Properties.of(Material.METAL, MaterialColor.METAL).strength(3f).requiresCorrectToolForDrops()
                    .lightLevel(activeBlockEmission(14))));
    public static final RegistrySupplier<SifterBlock> SIFTER = registerBlock("sifter",
            () -> new SifterBlock(Properties.of(Material.WOOD).strength(2.5f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<CultureVatBlock> CULTURE_VAT = registerBlock("culture_vat", () -> new CultureVatBlock(
            Properties.of(Material.GLASS, MaterialColor.COLOR_CYAN).strength(2f).requiresCorrectToolForDrops()
                    .lightLevel(activeBlockEmission(14)).noOcclusion()));
    public static final RegistrySupplier<Block> WORKTABLE = registerBlock("worktable", () -> new WorktableBlock(
            Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).strength(1f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<FeederBlock> FEEDER = registerBlock("feeder",
            () -> new FeederBlock(Properties.of(Material.METAL).strength(3).requiresCorrectToolForDrops()));

    public static final RegistrySupplier<ArchitecturyLiquidBlock> TAR = registerBlockWithoutBlockItem("tar",
            () -> TarBlock.get(ModFluids.TAR, Properties.copy(Blocks.WATER).isViewBlocking(ModBlocks::always)));

    public static final RegistrySupplier<AnuStatueBlock> ANU_STATUE = registerBlockWithCustomBlockItem("anu_statue",
            () -> new AnuStatueBlock(Properties.of(Material.STONE).noOcclusion().strength(-1, 60000000)),
            block -> AnuStatueBlockItem.get(block, new Item.Properties().tab(ModTabs.FA_BLOCK_TAB)));
    public static final RegistrySupplier<AnubiteStatueBlock> ANUBITE_STATUE = registerBlockWithCustomBlockItem("anubite_statue",
            () -> new AnubiteStatueBlock(Properties.of(Material.STONE).noOcclusion().strength(-1, 60000000)),
            block -> AnubiteStatueBlockItem.get(block, new Item.Properties().tab(ModTabs.FA_BLOCK_TAB)));
    public static final RegistrySupplier<AnuBarrierOriginBlock> ANU_BARRIER_ORIGIN = registerBlockWithDebugItem("anu_barrier_origin",
            () -> new AnuBarrierOriginBlock(Properties.copy(Blocks.BARRIER)));
    public static final RegistrySupplier<AnuBarrierFaceBlock> ANU_BARRIER_FACE = registerBlockWithDebugItem("anu_barrier_face",
            () -> new AnuBarrierFaceBlock(Properties.copy(Blocks.BARRIER)));
    public static final RegistrySupplier<AnuPortal> ANU_PORTAL = registerBlockWithDebugItem("anu_portal",
            () -> new AnuPortal(Properties.copy(Blocks.NETHER_PORTAL)));
    public static final RegistrySupplier<HomePortal> HOME_PORTAL = registerBlockWithDebugItem("home_portal",
            () -> new HomePortal(Properties.copy(Blocks.NETHER_PORTAL)));
    public static final RegistrySupplier<AncientChestBlock> ANCIENT_CHEST = registerBlockWithCustomBlockItem("ancient_chest",
            () -> new AncientChestBlock(Properties.of(Material.WOOD).noOcclusion().strength(-1, 3600000)),
            block -> AncientChestBlockItem.get(block, new Item.Properties().tab(ModTabs.FA_BLOCK_TAB)));
    public static final RegistrySupplier<Block> SARCOPHAGUS = registerBlockWithCustomBlockItem("sarcophagus",
            () -> new SarcophagusBlock(Properties.of(Material.STONE).noOcclusion().strength(-1, 60000000)
                    .lightLevel(state -> state.getValue(SarcophagusBlock.LIT) ? 7 : 0)), block -> SarcophagusBlockItem.get(block, new Item.Properties().tab(ModTabs.FA_BLOCK_TAB)));
    public static final RegistrySupplier<Block> FAKE_OBSIDIAN = registerBlockWithDebugItem("fake_obsidian",
            () -> new FakeObsidian(Properties.copy(Blocks.OBSIDIAN)));
    public static final RegistrySupplier<Block> OBSIDIAN_SPIKES = registerBlock("obsidian_spikes",
            () -> new ObsidianSpikesBlock(Properties.of(Material.STONE).strength(50, 2000).sound(SoundType.STONE)
                    .requiresCorrectToolForDrops().noOcclusion()));
    public static final RegistrySupplier<VolcanoAshVent> ASH_VENT = registerBlockWithDebugItem("ash_vent",
            VolcanoAshVent::new);

    public static final RegistrySupplier<DrumBlock> DRUM = registerBlock("drum",
            () -> new DrumBlock(Properties.of(Material.WOOD).sound(SoundType.WOOD).strength(2.5f)));
    public static final RegistrySupplier<BedBlock> COMFY_BED = registerBlock("comfy_bed", () -> new ComfyBedBlock(
            Properties.of(Material.WOOL).sound(SoundType.WOOD).strength(0.2f).noOcclusion()));

    public static final RegistrySupplier<Block> SHELL = registerBlock("shell",
            () -> new ShellBlock(Properties.of(Material.STONE).strength(1).requiresCorrectToolForDrops().noOcclusion()));
    public static final RegistrySupplier<OreBlock> AMBER_ORE = registerBlock("amber_ore",
            () -> new OreBlock(Properties.of(Material.STONE).strength(3f).requiresCorrectToolForDrops()));
    public static final RegistrySupplier<Block> AMBER_BLOCK = registerBlock("amber_block",
            () -> new Block(Properties.of(Material.STONE).strength(3f).requiresCorrectToolForDrops().noOcclusion()
                    .isViewBlocking(ModBlocks::never)));
    public static final RegistrySupplier<Block> AMBER_CHUNK = registerBlock("amber_chunk",
            () -> new AmberChunkBlock(Properties.of(Material.STONE).strength(3f).requiresCorrectToolForDrops()
                    .noOcclusion().isViewBlocking(ModBlocks::never)));
    public static final RegistrySupplier<Block> AMBER_CHUNK_DOMINICAN = registerBlock("amber_chunk_dominican",
            () -> new AmberChunkBlock(Properties.of(Material.STONE).strength(3f).requiresCorrectToolForDrops()
                    .noOcclusion().isViewBlocking(ModBlocks::never)));
    public static final RegistrySupplier<Block> AMBER_CHUNK_MOSQUITO = registerBlock("amber_chunk_mosquito",
            () -> new AmberChunkBlock(Properties.of(Material.STONE).strength(3f).requiresCorrectToolForDrops()
                    .noOcclusion().isViewBlocking(ModBlocks::never)));
    public static final RegistrySupplier<IcedDirtBlock> ICED_DIRT = registerBlock("iced_dirt",
            () -> new IcedDirtBlock(Properties.of(Material.DIRT).strength(1, 4).sound(SoundType.MOSS).randomTicks()));
    public static final RegistrySupplier<SandBlock> DENSE_SAND = registerBlock("dense_sand",
            () -> new SandBlock(0x8C765C, Properties.of(Material.SAND).strength(3f, 15f).sound(SoundType.SAND)));
    public static final RegistrySupplier<SkullBlock> SKULL_BLOCK = registerBlock("skull",
            () -> new SkullBlock(Properties.of(Material.STONE).strength(2, 15f)
                    .requiresCorrectToolForDrops().sound(SoundType.BONE_BLOCK)));
    public static final RegistrySupplier<SkullBlock> SKULL_LANTERN = registerBlock("skull_lantern",
            () -> new SkullBlock(Properties.of(Material.STONE).lightLevel(value -> 14).strength(2, 15f)
                    .requiresCorrectToolForDrops().sound(SoundType.BONE_BLOCK)));
    public static final RegistrySupplier<Block> SLIME_TRAIL = registerBlock("slime_trail",
            () -> new RailBlock(Properties.copy(Blocks.SLIME_BLOCK)));

    public static final RegistrySupplier<Block> ANCIENT_STONE = registerBlock("ancient_stone",
            () -> new Block(Properties.of(Material.STONE).strength(1.5f).requiresCorrectToolForDrops()));
    public static final RegistrySupplier<Block> ANCIENT_STONE_BRICKS = registerBlock("ancient_stone_bricks",
            () -> new Block(Properties.copy(ANCIENT_STONE.get())));
    public static final RegistrySupplier<SlabBlock> ANCIENT_STONE_SLAB = registerBlock("ancient_stone_slab",
            () -> new SlabBlock(Properties.copy(ANCIENT_STONE.get())));
    public static final RegistrySupplier<StairBlock> ANCIENT_STONE_STAIRS = registerBlock("ancient_stone_stairs",
            () -> new StairBlock(ANCIENT_STONE.get().defaultBlockState(), Properties.copy(ANCIENT_STONE.get())));
    public static final RegistrySupplier<WallBlock> ANCIENT_STONE_WALL = registerBlock("ancient_stone_wall",
            () -> new WallBlock(Properties.copy(ANCIENT_STONE.get())));
    public static final RegistrySupplier<Block> ANCIENT_WOOD_PLANKS = registerBlock("ancient_wood_planks",
            () -> new Block(Properties.of(Material.WOOD).strength(2f, 3f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<SlabBlock> ANCIENT_WOOD_SLAB = registerBlock("ancient_wood_slab",
            () -> new SlabBlock(Properties.copy(ANCIENT_WOOD_PLANKS.get())));
    public static final RegistrySupplier<RotatedPillarBlock> ANCIENT_WOOD_LOG = registerBlock("ancient_wood_log",
            () -> FlammableRotatedPillarBlock.get(Properties.copy(ANCIENT_WOOD_PLANKS.get())));
    public static final RegistrySupplier<StairBlock> ANCIENT_WOOD_STAIRS = registerBlock("ancient_wood_stairs",
            () -> new StairBlock(ANCIENT_WOOD_PLANKS.get().defaultBlockState(), Properties.copy(ANCIENT_WOOD_PLANKS.get())));
    public static final RegistrySupplier<ClearGlassBlock> REINFORCED_GLASS = registerBlock("reinforced_glass",
            () -> new ClearGlassBlock(Properties.copy(Blocks.GLASS).strength(3f, 25f)));
    public static final RegistrySupplier<ClearGlassBlock> ANCIENT_GLASS = registerBlock("ancient_glass",
            () -> new ClearGlassBlock(Properties.copy(Blocks.GLASS).strength(1f)));

    //Fossil Blocks
    public static final RegistrySupplier<Block> CALCITE_FOSSIL = registerBlock("fossil_calcite",
            () -> new FossilBlock(Properties.copy(Blocks.CALCITE).strength(1.25f).requiresCorrectToolForDrops()));
    public static final RegistrySupplier<Block> DEEPSLATE_FOSSIL = registerBlock("fossil_deepslate",
            () -> new FossilBlock(Properties.copy(Blocks.DEEPSLATE).strength(4, 7).requiresCorrectToolForDrops()));
    public static final RegistrySupplier<Block> DRIPSTONE_FOSSIL = registerBlock("fossil_dripstone",
            () -> new FossilBlock(Properties.copy(Blocks.DRIPSTONE_BLOCK).strength(3, 2).requiresCorrectToolForDrops()));
    public static final RegistrySupplier<Block> RED_SANDSTONE_FOSSIL = registerBlock("fossil_red_sandstone",
            () -> new FossilBlock(Properties.copy(Blocks.RED_SANDSTONE).strength(1.6f).requiresCorrectToolForDrops()));
    public static final RegistrySupplier<Block> SANDSTONE_FOSSIL = registerBlock("fossil_sandstone",
            () -> new FossilBlock(Properties.copy(Blocks.SANDSTONE).strength(1.6f).requiresCorrectToolForDrops()));
    public static final RegistrySupplier<Block> STONE_FOSSIL = registerBlock("fossil_stone",
            () -> new FossilBlock(Properties.copy(Blocks.STONE).strength(3, 6).requiresCorrectToolForDrops()));
    public static final RegistrySupplier<Block> TUFF_FOSSIL = registerBlock("fossil_tuff",
            () -> new FossilBlock(Properties.copy(Blocks.TUFF).strength(3, 6).requiresCorrectToolForDrops()));

    public static final RegistrySupplier<Block> TARRED_DIRT = registerBlock("tarred_dirt",
            () -> new Block(Properties.copy(Blocks.DIRT)));
    public static final RegistrySupplier<Block> PERMAFROST_BLOCK = registerBlock("permafrost_block",
            () -> new PermafrostBlock(Properties.of(Material.STONE, MaterialColor.COLOR_BLUE).strength(2f).requiresCorrectToolForDrops()));
    public static final RegistrySupplier<Block> VOLCANIC_ASH = registerBlock("volcanic_ash",
            () -> new Block(Properties.of(Material.DIRT, MaterialColor.COLOR_BLACK).strength(0.2f).requiresCorrectToolForDrops().sound(SoundType.GRAVEL)));
    public static final RegistrySupplier<Block> VOLCANIC_ROCK = registerBlock("volcanic_rock",
            () -> new Block(Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).strength(1f).requiresCorrectToolForDrops()));
    public static final RegistrySupplier<Block> VOLCANIC_BRICKS = registerBlock("volcanic_bricks",
            () -> new Block(Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).strength(1.5f).requiresCorrectToolForDrops()));
    public static final RegistrySupplier<SlabBlock> VOLCANIC_BRICK_SLAB = registerBlock("volcanic_brick_slab",
            () -> new SlabBlock(Properties.copy(VOLCANIC_BRICKS.get())));
    public static final RegistrySupplier<StairBlock> VOLCANIC_BRICK_STAIRS = registerBlock("volcanic_brick_stairs",
            () -> new StairBlock(VOLCANIC_BRICKS.get().defaultBlockState(), Properties.copy(VOLCANIC_BRICKS.get())));
    public static final RegistrySupplier<WallBlock> VOLCANIC_BRICK_WALL = registerBlock("volcanic_brick_wall",
            () -> new WallBlock(Properties.copy(VOLCANIC_BRICKS.get())));
    public static final RegistrySupplier<Block> VOLCANIC_TILES = registerBlock("volcanic_tiles",
            () -> new Block(Properties.copy(VOLCANIC_BRICKS.get())));
    public static final RegistrySupplier<SlabBlock> VOLCANIC_TILE_SLAB = registerBlock("volcanic_tile_slab",
            () -> new SlabBlock(Properties.copy(VOLCANIC_TILES.get())));
    public static final RegistrySupplier<StairBlock> VOLCANIC_TILE_STAIRS = registerBlock("volcanic_tile_stairs",
            () -> new StairBlock(VOLCANIC_TILES.get().defaultBlockState(), Properties.copy(VOLCANIC_TILES.get())));
    public static final RegistrySupplier<WallBlock> VOLCANIC_TILE_WALL = registerBlock("volcanic_tile_wall",
            () -> new WallBlock(Properties.copy(VOLCANIC_TILES.get())));
    public static final RegistrySupplier<Block> CALAMITES_PLANKS = registerBlock("calamites_planks",
            () -> new Block(Properties.copy(Blocks.SPRUCE_PLANKS).strength(1f)));
    public static final RegistrySupplier<StairBlock> CALAMITES_STAIRS = registerBlock("calamites_stairs",
            () -> new StairBlock(ModBlocks.CALAMITES_PLANKS.get().defaultBlockState(),
                    Properties.of(Material.WOOD).strength(1f)));
    public static final RegistrySupplier<SlabBlock> CALAMITES_SLAB = registerBlock("calamites_slab",
            () -> new SlabBlock(Properties.copy(Blocks.SPRUCE_SLAB).strength(1f)));
    public static final RegistrySupplier<FenceBlock> CALAMITES_FENCE = registerBlock("calamites_fence",
            () -> new FenceBlock(Properties.copy(Blocks.SPRUCE_FENCE).strength(1f)));
    public static final RegistrySupplier<FenceGateBlock> CALAMITES_FENCE_GATE = registerBlock("calamites_fence_gate",
            () -> new FenceGateBlock(Properties.copy(Blocks.SPRUCE_FENCE_GATE).strength(1f)));
    public static final RegistrySupplier<DoorBlock> CALAMITES_DOOR = registerBlock("calamites_door",
            () -> new DoorBlock(Properties.copy(Blocks.SPRUCE_DOOR).strength(1f)));
    public static final RegistrySupplier<TrapDoorBlock> CALAMITES_TRAPDOOR = registerBlock("calamites_trapdoor",
            () -> new TrapDoorBlock(Properties.copy(Blocks.SPRUCE_TRAPDOOR).strength(1f)));
    public static final RegistrySupplier<WoodButtonBlock> CALAMITES_BUTTON = registerBlock("calamites_button",
            () -> new WoodButtonBlock(Properties.copy(Blocks.SPRUCE_BUTTON).strength(1f)));
    public static final RegistrySupplier<PressurePlateBlock> CALAMITES_PRESSURE_PLATE = registerBlock("calamites_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Properties.copy(Blocks.SPRUCE_PRESSURE_PLATE)
                    .strength(1f)));
    public static final RegistrySupplier<RotatedPillarBlock> CALAMITES_LOG = registerBlock("calamites_log",
            () -> FlammableRotatedPillarBlock.get(Properties.copy(Blocks.OAK_LOG)));
    public static final RegistrySupplier<RotatedPillarBlock> CALAMITES_WOOD = registerBlock("calamites_wood",
            () -> FlammableRotatedPillarBlock.get(Properties.copy(Blocks.OAK_WOOD)));
    public static final RegistrySupplier<RotatedPillarBlock> STRIPPED_CALAMITES_LOG = registerBlock("stripped_calamites_log",
            () -> FlammableRotatedPillarBlock.get(Properties.copy(Blocks.STRIPPED_OAK_LOG)));
    public static final RegistrySupplier<RotatedPillarBlock> STRIPPED_CALAMITES_WOOD = registerBlock("stripped_calamites_wood",
            () -> FlammableRotatedPillarBlock.get(Properties.copy(Blocks.STRIPPED_OAK_WOOD)));
    public static final RegistrySupplier<LeavesBlock> CALAMITES_LEAVES = registerBlock("calamites_leaves",
            () -> FossilLeavesBlock.get(Properties.copy(Blocks.OAK_LEAVES)));
    public static final RegistrySupplier<Block> CALAMITES_SAPLING = registerBlock("calamites_sapling",
            () -> new SaplingBlock(new CalamitesTreeGrower(), Properties.copy(Blocks.OAK_SAPLING)));
    public static final RegistrySupplier<Block> CORDAITES_PLANKS = registerBlock("cordaites_planks",
            () -> new Block(Properties.copy(Blocks.SPRUCE_PLANKS).strength(1f)));
    public static final RegistrySupplier<StairBlock> CORDAITES_STAIRS = registerBlock("cordaites_stairs",
            () -> new StairBlock(ModBlocks.CORDAITES_PLANKS.get().defaultBlockState(),
                    Properties.of(Material.WOOD).strength(1f)));
    public static final RegistrySupplier<SlabBlock> CORDAITES_SLAB = registerBlock("cordaites_slab",
            () -> new SlabBlock(Properties.copy(Blocks.SPRUCE_SLAB).strength(1f)));
    public static final RegistrySupplier<FenceBlock> CORDAITES_FENCE = registerBlock("cordaites_fence",
            () -> new FenceBlock(Properties.copy(Blocks.SPRUCE_FENCE).strength(1f)));
    public static final RegistrySupplier<FenceGateBlock> CORDAITES_FENCE_GATE = registerBlock("cordaites_fence_gate",
            () -> new FenceGateBlock(Properties.copy(Blocks.SPRUCE_FENCE_GATE).strength(1f)));
    public static final RegistrySupplier<DoorBlock> CORDAITES_DOOR = registerBlock("cordaites_door",
            () -> new DoorBlock(Properties.copy(Blocks.SPRUCE_DOOR).strength(1f)));
    public static final RegistrySupplier<TrapDoorBlock> CORDAITES_TRAPDOOR = registerBlock("cordaites_trapdoor",
            () -> new TrapDoorBlock(Properties.copy(Blocks.SPRUCE_TRAPDOOR).strength(1f)));
    public static final RegistrySupplier<WoodButtonBlock> CORDAITES_BUTTON = registerBlock("cordaites_button",
            () -> new WoodButtonBlock(Properties.copy(Blocks.SPRUCE_BUTTON).strength(1f)));
    public static final RegistrySupplier<PressurePlateBlock> CORDAITES_PRESSURE_PLATE = registerBlock("cordaites_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Properties.copy(Blocks.SPRUCE_PRESSURE_PLATE)
                    .strength(1f)));
    public static final RegistrySupplier<RotatedPillarBlock> CORDAITES_LOG = registerBlock("cordaites_log",
            () -> FlammableRotatedPillarBlock.get(Properties.copy(Blocks.OAK_LOG)));
    public static final RegistrySupplier<RotatedPillarBlock> CORDAITES_WOOD = registerBlock("cordaites_wood",
            () -> FlammableRotatedPillarBlock.get(Properties.copy(Blocks.OAK_WOOD)));
    public static final RegistrySupplier<RotatedPillarBlock> STRIPPED_CORDAITES_LOG = registerBlock("stripped_cordaites_log",
            () -> FlammableRotatedPillarBlock.get(Properties.copy(Blocks.STRIPPED_OAK_LOG)));
    public static final RegistrySupplier<RotatedPillarBlock> STRIPPED_CORDAITES_WOOD = registerBlock("stripped_cordaites_wood",
            () -> FlammableRotatedPillarBlock.get(Properties.copy(Blocks.STRIPPED_OAK_WOOD)));
    public static final RegistrySupplier<LeavesBlock> CORDAITES_LEAVES = registerBlock("cordaites_leaves",
            () -> FossilLeavesBlock.get(Properties.copy(Blocks.OAK_LEAVES)));
    public static final RegistrySupplier<SaplingBlock> CORDAITES_SAPLING = registerBlock("cordaites_sapling",
            () -> new SaplingBlock(new CordaitesTreeGrower(), Properties.copy(Blocks.OAK_SAPLING)));
    public static final RegistrySupplier<Block> MUTANT_TREE_PLANKS = registerBlock("mutant_tree_planks",
            () -> new Block(Properties.copy(Blocks.SPRUCE_PLANKS).strength(1f)));
    public static final RegistrySupplier<StairBlock> MUTANT_TREE_STAIRS = registerBlock("mutant_tree_stairs",
            () -> new StairBlock(ModBlocks.MUTANT_TREE_PLANKS.get().defaultBlockState(),
                    Properties.of(Material.WOOD).strength(1f)));
    public static final RegistrySupplier<SlabBlock> MUTANT_TREE_SLAB = registerBlock("mutant_tree_slab",
            () -> new SlabBlock(Properties.copy(Blocks.SPRUCE_SLAB).strength(1f)));
    public static final RegistrySupplier<FenceBlock> MUTANT_TREE_FENCE = registerBlock("mutant_tree_fence",
            () -> new FenceBlock(Properties.copy(Blocks.SPRUCE_FENCE).strength(1f)));
    public static final RegistrySupplier<FenceGateBlock> MUTANT_TREE_FENCE_GATE = registerBlock("mutant_tree_fence_gate",
            () -> new FenceGateBlock(Properties.copy(Blocks.SPRUCE_FENCE_GATE).strength(1f)));
    public static final RegistrySupplier<DoorBlock> MUTANT_TREE_DOOR = registerBlock("mutant_tree_door",
            () -> new DoorBlock(Properties.copy(Blocks.SPRUCE_DOOR).strength(1f)));
    public static final RegistrySupplier<TrapDoorBlock> MUTANT_TREE_TRAPDOOR = registerBlock("mutant_tree_trapdoor",
            () -> new TrapDoorBlock(Properties.copy(Blocks.SPRUCE_TRAPDOOR).strength(1f)));
    public static final RegistrySupplier<WoodButtonBlock> MUTANT_TREE_BUTTON = registerBlock("mutant_tree_button",
            () -> new WoodButtonBlock(Properties.copy(Blocks.SPRUCE_BUTTON).strength(1f)));
    public static final RegistrySupplier<PressurePlateBlock> MUTANT_TREE_PRESSURE_PLATE = registerBlock("mutant_tree_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Properties.copy(Blocks.SPRUCE_PRESSURE_PLATE)
                    .strength(1f)));
    public static final RegistrySupplier<RotatedPillarBlock> MUTANT_TREE_LOG = registerBlock("mutant_tree_log",
            () -> FlammableRotatedPillarBlock.get(Properties.copy(Blocks.OAK_LOG)));
    public static final RegistrySupplier<RotatedPillarBlock> MUTANT_TREE_WOOD = registerBlock("mutant_tree_wood",
            () -> FlammableRotatedPillarBlock.get(Properties.copy(Blocks.OAK_WOOD)));
    public static final RegistrySupplier<RotatedPillarBlock> STRIPPED_MUTANT_TREE_LOG = registerBlock("stripped_mutant_tree_log",
            () -> FlammableRotatedPillarBlock.get(Properties.copy(Blocks.STRIPPED_OAK_LOG)));
    public static final RegistrySupplier<RotatedPillarBlock> STRIPPED_MUTANT_TREE_WOOD = registerBlock("stripped_mutant_tree_wood",
            () -> FlammableRotatedPillarBlock.get(Properties.copy(Blocks.STRIPPED_OAK_WOOD)));
    public static final RegistrySupplier<LeavesBlock> MUTANT_TREE_LEAVES = registerBlock("mutant_tree_leaves",
            () -> FossilLeavesBlock.get(Properties.copy(Blocks.OAK_LEAVES)));
    public static final RegistrySupplier<Block> MUTANT_TREE_SAPLING = registerBlock("mutant_tree_sapling",
            () -> new SaplingBlock(new MutantTreeGrower(), Properties.copy(Blocks.OAK_SAPLING)));
    public static final RegistrySupplier<Block> MUTANT_TREE_TUMOR = registerBlockWithDebugItem("mutant_tree_tumor",
            () -> new MutantTreeTumor(Properties.of(Material.LEAVES).noOcclusion().dynamicShape().sound(SoundType.GRASS)));
    public static final RegistrySupplier<Block> MUTANT_TREE_VINE = registerBlock("mutant_tree_vine",
            () -> new VineBlock(Properties.of(Material.REPLACEABLE_PLANT).noCollission().lightLevel(value -> 10).randomTicks().strength(0.2f).sound(SoundType.VINE)));
    public static final RegistrySupplier<Block> PALM_PLANKS = registerBlock("palm_planks",
            () -> new Block(Properties.copy(Blocks.SPRUCE_PLANKS).strength(1f)));
    public static final RegistrySupplier<StairBlock> PALM_STAIRS = registerBlock("palm_stairs",
            () -> new StairBlock(ModBlocks.PALM_PLANKS.get().defaultBlockState(),
                    Properties.of(Material.WOOD).strength(1f)));
    public static final RegistrySupplier<SlabBlock> PALM_SLAB = registerBlock("palm_slab",
            () -> new SlabBlock(Properties.copy(Blocks.SPRUCE_SLAB).strength(1f)));
    public static final RegistrySupplier<FenceBlock> PALM_FENCE = registerBlock("palm_fence",
            () -> new FenceBlock(Properties.copy(Blocks.SPRUCE_FENCE).strength(1f)));
    public static final RegistrySupplier<FenceGateBlock> PALM_FENCE_GATE = registerBlock("palm_fence_gate",
            () -> new FenceGateBlock(Properties.copy(Blocks.SPRUCE_FENCE_GATE).strength(1f)));
    public static final RegistrySupplier<DoorBlock> PALM_DOOR = registerBlock("palm_door",
            () -> new DoorBlock(Properties.copy(Blocks.SPRUCE_DOOR).strength(1f)));
    public static final RegistrySupplier<TrapDoorBlock> PALM_TRAPDOOR = registerBlock("palm_trapdoor",
            () -> new TrapDoorBlock(Properties.copy(Blocks.SPRUCE_TRAPDOOR).strength(1f)));
    public static final RegistrySupplier<WoodButtonBlock> PALM_BUTTON = registerBlock("palm_button",
            () -> new WoodButtonBlock(Properties.copy(Blocks.SPRUCE_BUTTON).strength(1f)));
    public static final RegistrySupplier<PressurePlateBlock> PALM_PRESSURE_PLATE = registerBlock("palm_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Properties.copy(Blocks.SPRUCE_PRESSURE_PLATE)
                    .strength(1f)));
    public static final RegistrySupplier<RotatedPillarBlock> PALM_LOG = registerBlock("palm_log",
            () -> FlammableRotatedPillarBlock.get(Properties.copy(Blocks.OAK_LOG)));
    public static final RegistrySupplier<RotatedPillarBlock> PALM_WOOD = registerBlock("palm_wood",
            () -> FlammableRotatedPillarBlock.get(Properties.copy(Blocks.OAK_WOOD)));
    public static final RegistrySupplier<RotatedPillarBlock> STRIPPED_PALM_LOG = registerBlock("stripped_palm_log",
            () -> FlammableRotatedPillarBlock.get(Properties.copy(Blocks.STRIPPED_OAK_LOG)));
    public static final RegistrySupplier<RotatedPillarBlock> STRIPPED_PALM_WOOD = registerBlock("stripped_palm_wood",
            () -> FlammableRotatedPillarBlock.get(Properties.copy(Blocks.STRIPPED_OAK_WOOD)));
    public static final RegistrySupplier<LeavesBlock> PALM_LEAVES = registerBlock("palm_leaves",
            () -> FossilLeavesBlock.get(Properties.copy(Blocks.OAK_LEAVES)));
    public static final RegistrySupplier<Block> PALM_SAPLING = registerBlock("palm_sapling",
            () -> new SaplingBlock(new PalmTreeGrower(), Properties.copy(Blocks.OAK_SAPLING)));
    public static final RegistrySupplier<Block> SIGILLARIA_PLANKS = registerBlock("sigillaria_planks",
            () -> new Block(Properties.copy(Blocks.SPRUCE_PLANKS).strength(1f)));
    public static final RegistrySupplier<StairBlock> SIGILLARIA_STAIRS = registerBlock("sigillaria_stairs",
            () -> new StairBlock(ModBlocks.SIGILLARIA_PLANKS.get().defaultBlockState(),
                    Properties.of(Material.WOOD).strength(1f)));
    public static final RegistrySupplier<SlabBlock> SIGILLARIA_SLAB = registerBlock("sigillaria_slab",
            () -> new SlabBlock(Properties.copy(Blocks.SPRUCE_SLAB).strength(1f)));
    public static final RegistrySupplier<FenceBlock> SIGILLARIA_FENCE = registerBlock("sigillaria_fence",
            () -> new FenceBlock(Properties.copy(Blocks.SPRUCE_FENCE).strength(1f)));
    public static final RegistrySupplier<FenceGateBlock> SIGILLARIA_FENCE_GATE = registerBlock("sigillaria_fence_gate",
            () -> new FenceGateBlock(Properties.copy(Blocks.SPRUCE_FENCE_GATE).strength(1f)));
    public static final RegistrySupplier<DoorBlock> SIGILLARIA_DOOR = registerBlock("sigillaria_door",
            () -> new DoorBlock(Properties.copy(Blocks.SPRUCE_DOOR).strength(1f)));
    public static final RegistrySupplier<TrapDoorBlock> SIGILLARIA_TRAPDOOR = registerBlock("sigillaria_trapdoor",
            () -> new TrapDoorBlock(Properties.copy(Blocks.SPRUCE_TRAPDOOR).strength(1f)));
    public static final RegistrySupplier<WoodButtonBlock> SIGILLARIA_BUTTON = registerBlock("sigillaria_button",
            () -> new WoodButtonBlock(Properties.copy(Blocks.SPRUCE_BUTTON).strength(1f)));
    public static final RegistrySupplier<PressurePlateBlock> SIGILLARIA_PRESSURE_PLATE = registerBlock("sigillaria_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Properties.copy(Blocks.SPRUCE_PRESSURE_PLATE)
                    .strength(1f)));
    public static final RegistrySupplier<RotatedPillarBlock> SIGILLARIA_LOG = registerBlock("sigillaria_log",
            () -> FlammableRotatedPillarBlock.get(Properties.copy(Blocks.OAK_LOG)));
    public static final RegistrySupplier<RotatedPillarBlock> SIGILLARIA_WOOD = registerBlock("sigillaria_wood",
            () -> FlammableRotatedPillarBlock.get(Properties.copy(Blocks.OAK_WOOD)));
    public static final RegistrySupplier<RotatedPillarBlock> STRIPPED_SIGILLARIA_LOG = registerBlock("stripped_sigillaria_log",
            () -> FlammableRotatedPillarBlock.get(Properties.copy(Blocks.STRIPPED_OAK_LOG)));
    public static final RegistrySupplier<RotatedPillarBlock> STRIPPED_SIGILLARIA_WOOD = registerBlock("stripped_sigillaria_wood",
            () -> FlammableRotatedPillarBlock.get(Properties.copy(Blocks.STRIPPED_OAK_WOOD)));
    public static final RegistrySupplier<LeavesBlock> SIGILLARIA_LEAVES = registerBlock("sigillaria_leaves",
            () -> FossilLeavesBlock.get(Properties.copy(Blocks.OAK_LEAVES)));
    public static final RegistrySupplier<Block> SIGILLARIA_SAPLING = registerBlock("sigillaria_sapling",
            () -> new SaplingBlock(new SigillariaTreeGrower(), Properties.copy(Blocks.OAK_SAPLING)));
    public static final RegistrySupplier<Block> TEMPSKYA_PLANKS = registerBlock("tempskya_planks",
            () -> new Block(Properties.copy(Blocks.SPRUCE_PLANKS).strength(1f)));
    public static final RegistrySupplier<StairBlock> TEMPSKYA_STAIRS = registerBlock("tempskya_stairs",
            () -> new StairBlock(ModBlocks.TEMPSKYA_PLANKS.get().defaultBlockState(),
                    Properties.of(Material.WOOD).strength(1f)));
    public static final RegistrySupplier<SlabBlock> TEMPSKYA_SLAB = registerBlock("tempskya_slab",
            () -> new SlabBlock(Properties.copy(Blocks.SPRUCE_SLAB).strength(1f)));
    public static final RegistrySupplier<FenceBlock> TEMPSKYA_FENCE = registerBlock("tempskya_fence",
            () -> new FenceBlock(Properties.copy(Blocks.SPRUCE_FENCE).strength(1f)));
    public static final RegistrySupplier<FenceGateBlock> TEMPSKYA_FENCE_GATE = registerBlock("tempskya_fence_gate",
            () -> new FenceGateBlock(Properties.copy(Blocks.SPRUCE_FENCE_GATE).strength(1f)));
    public static final RegistrySupplier<DoorBlock> TEMPSKYA_DOOR = registerBlock("tempskya_door",
            () -> new DoorBlock(Properties.copy(Blocks.SPRUCE_DOOR).strength(1f)));
    public static final RegistrySupplier<TrapDoorBlock> TEMPSKYA_TRAPDOOR = registerBlock("tempskya_trapdoor",
            () -> new TrapDoorBlock(Properties.copy(Blocks.SPRUCE_TRAPDOOR).strength(1f)));
    public static final RegistrySupplier<WoodButtonBlock> TEMPSKYA_BUTTON = registerBlock("tempskya_button",
            () -> new WoodButtonBlock(Properties.copy(Blocks.SPRUCE_BUTTON).strength(1f)));
    public static final RegistrySupplier<PressurePlateBlock> TEMPSKYA_PRESSURE_PLATE = registerBlock("tempskya_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, Properties.copy(Blocks.SPRUCE_PRESSURE_PLATE)
                    .strength(1f)));
    public static final RegistrySupplier<RotatedPillarBlock> TEMPSKYA_LOG = registerBlock("tempskya_log",
            () -> FlammableRotatedPillarBlock.get(Properties.copy(Blocks.OAK_LOG)));
    public static final RegistrySupplier<RotatedPillarBlock> TEMPSKYA_WOOD = registerBlock("tempskya_wood",
            () -> FlammableRotatedPillarBlock.get(Properties.copy(Blocks.OAK_WOOD)));
    public static final RegistrySupplier<RotatedPillarBlock> STRIPPED_TEMPSKYA_LOG = registerBlock("stripped_tempskya_log",
            () -> FlammableRotatedPillarBlock.get(Properties.copy(Blocks.STRIPPED_OAK_LOG)));
    public static final RegistrySupplier<RotatedPillarBlock> STRIPPED_TEMPSKYA_WOOD = registerBlock("stripped_tempskya_wood",
            () -> FlammableRotatedPillarBlock.get(Properties.copy(Blocks.STRIPPED_OAK_WOOD)));
    public static final RegistrySupplier<Block> TEMPSKYA_SAPLING = registerBlock("tempskya_sapling",
            () -> new SaplingBlock(new TempskyaTreeGrower(), Properties.copy(Blocks.OAK_SAPLING)));
    public static final RegistrySupplier<Block> TEMPSKYA_TOP = registerBlock("tempskya_top",
            () -> new TempskyaTopBlock(Properties.of(Material.PLANT).noOcclusion().sound(SoundType.GRASS)));
    public static final RegistrySupplier<Block> TEMPSKYA_LEAF = registerBlock("tempskya_leaf",
            () -> new TempskyaLeafBlock(Properties.of(Material.LEAVES).noCollission().noOcclusion().sound(SoundType.GRASS)));
    public static final List<RegistrySupplier<VaseBlock>> VASES = new ArrayList<>();
    public static final RegistrySupplier<VaseBlock> VOLUTE_VASE_DAMAGED = registerVolute(VaseBlock.VaseVariant.DAMAGED);
    public static final RegistrySupplier<VaseBlock> VOLUTE_VASE_RESTORED = registerVolute(VaseBlock.VaseVariant.RESTORED);
    public static final RegistrySupplier<VaseBlock> KYLIX_VASE_DAMAGED = registerKylix(VaseBlock.VaseVariant.DAMAGED);
    public static final RegistrySupplier<VaseBlock> KYLIX_VASE_RESTORED = registerKylix(VaseBlock.VaseVariant.RESTORED);
    public static final RegistrySupplier<VaseBlock> AMPHORA_VASE_DAMAGED = registerAmphora(VaseBlock.VaseVariant.DAMAGED);
    public static final RegistrySupplier<VaseBlock> AMPHORA_VASE_RESTORED = registerAmphora(VaseBlock.VaseVariant.RESTORED);
    public static final List<RegistrySupplier<FigurineBlock>> FIGURINES = new ArrayList<>();
    public static final RegistrySupplier<FigurineBlock> ANU_FIGURINE_DESTROYED = registerAnu(FigurineBlock.FigurineVariant.DESTROYED);
    public static final RegistrySupplier<FigurineBlock> ANU_FIGURINE_RESTORED = registerAnu(FigurineBlock.FigurineVariant.RESTORED);
    public static final RegistrySupplier<FigurineBlock> ANU_FIGURINE_PRISTINE = registerAnu(FigurineBlock.FigurineVariant.PRISTINE);
    public static final RegistrySupplier<FigurineBlock> ENDERMAN_FIGURINE_DESTROYED = registerEnderman(FigurineBlock.FigurineVariant.DESTROYED);
    public static final RegistrySupplier<FigurineBlock> ENDERMAN_FIGURINE_RESTORED = registerEnderman(FigurineBlock.FigurineVariant.RESTORED);
    public static final RegistrySupplier<FigurineBlock> ENDERMAN_FIGURINE_PRISTINE = registerEnderman(FigurineBlock.FigurineVariant.PRISTINE);
    public static final RegistrySupplier<FigurineBlock> PIGLIN_FIGURINE_DESTROYED = registerPiglin(FigurineBlock.FigurineVariant.DESTROYED);
    public static final RegistrySupplier<FigurineBlock> PIGLIN_FIGURINE_RESTORED = registerPiglin(FigurineBlock.FigurineVariant.RESTORED);
    public static final RegistrySupplier<FigurineBlock> PIGLIN_FIGURINE_PRISTINE = registerPiglin(FigurineBlock.FigurineVariant.PRISTINE);
    public static final RegistrySupplier<FigurineBlock> SKELETON_FIGURINE_DESTROYED = registerSkeleton(FigurineBlock.FigurineVariant.DESTROYED);
    public static final RegistrySupplier<FigurineBlock> SKELETON_FIGURINE_RESTORED = registerSkeleton(FigurineBlock.FigurineVariant.RESTORED);
    public static final RegistrySupplier<FigurineBlock> SKELETON_FIGURINE_PRISTINE = registerSkeleton(FigurineBlock.FigurineVariant.PRISTINE);
    public static final RegistrySupplier<FigurineBlock> STEVE_FIGURINE_DESTROYED = registerSteve(FigurineBlock.FigurineVariant.DESTROYED);
    public static final RegistrySupplier<FigurineBlock> STEVE_FIGURINE_RESTORED = registerSteve(FigurineBlock.FigurineVariant.RESTORED);
    public static final RegistrySupplier<FigurineBlock> STEVE_FIGURINE_PRISTINE = registerSteve(FigurineBlock.FigurineVariant.PRISTINE);
    public static final RegistrySupplier<FigurineBlock> ZOMBIE_FIGURINE_DESTROYED = registerZombie(FigurineBlock.FigurineVariant.DESTROYED);
    public static final RegistrySupplier<FigurineBlock> ZOMBIE_FIGURINE_RESTORED = registerZombie(FigurineBlock.FigurineVariant.RESTORED);
    public static final RegistrySupplier<FigurineBlock> ZOMBIE_FIGURINE_PRISTINE = registerZombie(FigurineBlock.FigurineVariant.PRISTINE);
    public static final RegistrySupplier<Block> FERNS = registerBlockWithoutBlockItem("plant_ferns", FernsBlock::new);

    static {
        for (DyeColor color : DyeColor.values()) {
            registerVase("amphora", color.getSerializedName(), AmphoraVaseBlock::new);
            registerVase("kylix", color.getSerializedName(), KylixVaseBlock::new);
            registerVase("volute", color.getSerializedName(), VoluteVaseBlock::new);
        }
    }

    private static ToIntFunction<BlockState> activeBlockEmission(int lightValue) {
        return arg -> arg.getValue(CustomEntityBlock.ACTIVE) ? lightValue : 0;
    }

    private static boolean never(BlockState state, BlockGetter blockGetter, BlockPos pos) {
        return false;
    }

    private static boolean always(BlockState state, BlockGetter blockGetter, BlockPos pos) {
        return true;
    }

    private static RegistrySupplier<VaseBlock> registerVolute(VaseBlock.VaseVariant variant) {
        return registerVase("volute", variant.getSerializedName(), VoluteVaseBlock::new);
    }

    private static RegistrySupplier<VaseBlock> registerKylix(VaseBlock.VaseVariant variant) {
        return registerVase("kylix", variant.getSerializedName(), KylixVaseBlock::new);
    }

    private static RegistrySupplier<VaseBlock> registerAmphora(VaseBlock.VaseVariant variant) {
        return registerVase("amphora", variant.getSerializedName(), AmphoraVaseBlock::new);
    }

    private static RegistrySupplier<VaseBlock> registerVase(String name, String variant, Supplier<VaseBlock> supplier) {
        var toReturn = registerBlock("vase_" + name + "_" + variant, supplier);
        VASES.add(toReturn);
        return toReturn;
    }

    private static RegistrySupplier<FigurineBlock> registerAnu(FigurineBlock.FigurineVariant variant) {
        return registerFigurine("anu", variant, () -> new FigurineAnuBlock(variant));
    }

    private static RegistrySupplier<FigurineBlock> registerEnderman(FigurineBlock.FigurineVariant variant) {
        return registerFigurine("enderman", variant, () -> new FigurineEndermanBlock(variant));
    }

    private static RegistrySupplier<FigurineBlock> registerPiglin(FigurineBlock.FigurineVariant variant) {
        return registerFigurine("piglin", variant, () -> new FigurinePiglinBlock(variant));
    }

    private static RegistrySupplier<FigurineBlock> registerSkeleton(FigurineBlock.FigurineVariant variant) {
        return registerFigurine("skeleton", variant, () -> new FigurineSkeletonBlock(variant));
    }

    private static RegistrySupplier<FigurineBlock> registerSteve(FigurineBlock.FigurineVariant variant) {
        return registerFigurine("steve", variant, () -> new FigurineSteveBlock(variant));
    }

    private static RegistrySupplier<FigurineBlock> registerZombie(FigurineBlock.FigurineVariant variant) {
        return registerFigurine("zombie", variant, () -> new FigurineZombieBlock(variant));
    }

    private static RegistrySupplier<FigurineBlock> registerFigurine(String name, FigurineBlock.FigurineVariant variant, Supplier<FigurineBlock> supplier) {
        var toReturn = registerBlock("figurine_" + name + "_" + variant.getSerializedName(), supplier);
        FIGURINES.add(toReturn);
        return toReturn;
    }

    public static RegistrySupplier<ShortFlowerBlock> registerShortFlower(String name, VoxelShape shape) {
        return registerBlock(name,
                () -> new ShortFlowerBlock(Properties.of(Material.PLANT).noCollission().noOcclusion().sound(SoundType.GRASS), shape));
    }

    public static RegistrySupplier<TallFlowerBlock> registerTallFlower(String name, VoxelShape shape) {
        return registerBlock(name,
                () -> new TallFlowerBlock(Properties.of(Material.PLANT).noCollission().noOcclusion().sound(SoundType.GRASS), shape));
    }

    public static RegistrySupplier<FourTallFlowerBlock> registerFourTallFlower(String name, VoxelShape shape) {
        return registerBlock(name,
                () -> new FourTallFlowerBlock(Properties.of(Material.PLANT).noCollission().noOcclusion().sound(SoundType.GRASS),
                        shape));
    }

    public static RegistrySupplier<GrowableFlowerBlock> registerGrowableFlower(String name, RegistrySupplier<TallFlowerBlock> tallFlower,
                                                                               VoxelShape shape) {
        return registerBlock(name,
                () -> new GrowableFlowerBlock(Properties.of(Material.PLANT).noCollission().noOcclusion().sound(SoundType.GRASS),
                        tallFlower, shape));
    }

    public static <T extends Block> RegistrySupplier<T> registerBlockWithoutBlockItem(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    private static <T extends Block, R extends BlockItem> RegistrySupplier<T> registerBlockWithCustomBlockItem(String name, Supplier<T> block,
                                                                                                               Function<T, R> blockItem) {
        RegistrySupplier<T> toReturn = BLOCKS.register(name, block);
        ModItems.ITEMS.register(name, () -> blockItem.apply(toReturn.get()));
        return toReturn;
    }

    public static <T extends Block> RegistrySupplier<T> registerBlockWithDebugItem(String name, Supplier<T> block) {
        RegistrySupplier<T> toReturn = BLOCKS.register(name, block);
        if (Version.debugEnabled()) {
            registerBlockItem(name, toReturn);
        }
        return toReturn;
    }

    public static <T extends Block> RegistrySupplier<T> registerBlock(String name, Supplier<T> block) {
        RegistrySupplier<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistrySupplier<Item> registerBlockItem(String name, RegistrySupplier<T> block) {
        return ModItems.ITEMS.register(name, () -> new CustomBlockItem(block.get(), new Item.Properties().tab(ModTabs.FA_BLOCK_TAB)));
    }

    public static void register() {
        PrehistoricPlantInfo.register();
        FlammableRotatedPillarBlock.registerAllStripped();
        BLOCKS.register();
    }
}
