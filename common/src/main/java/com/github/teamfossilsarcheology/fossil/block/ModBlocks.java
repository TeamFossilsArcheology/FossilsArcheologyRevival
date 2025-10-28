package com.github.teamfossilsarcheology.fossil.block;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.block.custom_blocks.SkullBlock;
import com.github.teamfossilsarcheology.fossil.block.custom_blocks.TallFlowerBlock;
import com.github.teamfossilsarcheology.fossil.block.custom_blocks.*;
import com.github.teamfossilsarcheology.fossil.item.*;
import com.github.teamfossilsarcheology.fossil.material.ModFluids;
import com.github.teamfossilsarcheology.fossil.util.Version;
import com.github.teamfossilsarcheology.fossil.world.feature.tree.*;
import com.mojang.datafixers.util.Pair;
import dev.architectury.core.block.ArchitecturyLiquidBlock;
import dev.architectury.registry.CreativeTabRegistry;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.DyeColor;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraft.world.phys.shapes.VoxelShape;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Supplier;
import java.util.function.ToIntFunction;

import static net.minecraft.world.level.block.state.BlockBehaviour.Properties;
import static net.minecraft.world.level.block.state.properties.NoteBlockInstrument.*;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(FossilMod.MOD_ID, Registries.BLOCK);

    public static final RegistrySupplier<BubbleBlowerBlock> BUBBLE_BLOWER = registerBlock("bubble_blower",
            () -> new BubbleBlowerBlock(Properties.of().mapColor(MapColor.METAL).strength(3).sound(SoundType.METAL).requiresCorrectToolForDrops())
    );
    public static final RegistrySupplier<Block> ANALYZER = registerBlock("analyzer",
            () -> new AnalyzerBlock(Properties.copy(BUBBLE_BLOWER.get()).lightLevel(activeBlockEmission(14))));
    public static final RegistrySupplier<SifterBlock> SIFTER = registerBlock("sifter",
            () -> new SifterBlock(Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(BASS)
                    .strength(2.5f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<CultureVatBlock> CULTURE_VAT = registerBlock("culture_vat", () -> new CultureVatBlock(
            Properties.of().mapColor(MapColor.COLOR_CYAN).instrument(HAT).strength(2f).requiresCorrectToolForDrops()
                    .lightLevel(activeBlockEmission(14)).noOcclusion()));
    public static final RegistrySupplier<Block> WORKTABLE = registerBlock("worktable", () -> new WorktableBlock(
            Properties.of().mapColor(MapColor.COLOR_BROWN).ignitedByLava().instrument(BASS)
                    .strength(1f).sound(SoundType.WOOD)));
    public static final RegistrySupplier<FeederBlock> FEEDER = registerBlock("feeder",
            () -> new FeederBlock(Properties.copy(BUBBLE_BLOWER.get())));

    public static final RegistrySupplier<ArchitecturyLiquidBlock> TAR = blockWithoutBlockItem("tar",
            () -> TarBlock.get(ModFluids.TAR, Properties.copy(Blocks.WATER).isSuffocating(ModBlocks::always).isViewBlocking(ModBlocks::always)));

    public static final RegistrySupplier<AnuStatueBlock> ANU_STATUE = blockWithCustomBlockItem("anu_statue",
            () -> new AnuStatueBlock(Properties.of().noOcclusion().strength(-1, 60000000)),
            block -> AnuStatueBlockItem.get(block, new Item.Properties().arch$tab(ModTabs.FA_BLOCK_TAB)));
    public static final RegistrySupplier<AnubiteStatueBlock> ANUBITE_STATUE = blockWithCustomBlockItem("anubite_statue",
            () -> new AnubiteStatueBlock(Properties.of().noOcclusion().strength(-1, 60000000)),
            block -> AnubiteStatueBlockItem.get(block, new Item.Properties().arch$tab(ModTabs.FA_BLOCK_TAB)));
    public static final RegistrySupplier<AnuBarrierOriginBlock> ANU_BARRIER_ORIGIN = blockWithDebugItem("anu_barrier_origin",
            () -> new AnuBarrierOriginBlock(Properties.copy(Blocks.BARRIER)));
    public static final RegistrySupplier<AnuBarrierFaceBlock> ANU_BARRIER_FACE = blockWithDebugItem("anu_barrier_face",
            () -> new AnuBarrierFaceBlock(Properties.copy(Blocks.BARRIER)));
    public static final RegistrySupplier<AnuPortal> ANU_PORTAL = blockWithDebugItem("anu_portal",
            () -> new AnuPortal(Properties.copy(Blocks.NETHER_PORTAL)));
    public static final RegistrySupplier<HomePortal> HOME_PORTAL = blockWithDebugItem("home_portal",
            () -> new HomePortal(Properties.copy(Blocks.NETHER_PORTAL)));
    public static final RegistrySupplier<AncientChestBlock> ANCIENT_CHEST = blockWithCustomBlockItem("ancient_chest",
            () -> new AncientChestBlock(Properties.of().mapColor(MapColor.WOOD).instrument(BASS)
                    .noOcclusion().strength(-1, 3600000)),
            block -> AncientChestBlockItem.get(block, new Item.Properties().arch$tab(ModTabs.FA_BLOCK_TAB)));
    public static final RegistrySupplier<Block> SARCOPHAGUS = blockWithCustomBlockItem("sarcophagus",
            () -> new SarcophagusBlock(Properties.of().noOcclusion().strength(-1, 60000000)
                    .lightLevel(state -> state.getValue(SarcophagusBlock.LIT) ? 7 : 0)), block -> SarcophagusBlockItem.get(block, new Item.Properties().arch$tab(ModTabs.FA_BLOCK_TAB)));
    public static final RegistrySupplier<Block> FAKE_OBSIDIAN = blockWithDebugItem("fake_obsidian",
            () -> new FakeObsidian(Properties.copy(Blocks.OBSIDIAN)));
    public static final RegistrySupplier<Block> OBSIDIAN_SPIKES = registerBlock("obsidian_spikes",
            () -> new ObsidianSpikesBlock(Properties.of().mapColor(MapColor.COLOR_BLACK).strength(50, 2000).sound(SoundType.STONE)
                    .requiresCorrectToolForDrops().noOcclusion()));
    public static final RegistrySupplier<VolcanoAshVent> ASH_VENT = blockWithDebugItem("ash_vent",
            VolcanoAshVent::new);

    public static final RegistrySupplier<DrumBlock> DRUM = registerBlock("drum",
            () -> new DrumBlock(Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(BASS)
                    .sound(SoundType.WOOD)));
    public static final RegistrySupplier<BedBlock> COMFY_BED = registerBlock("comfy_bed", () -> new ComfyBedBlock(
            Properties.of().mapColor(MapColor.WOOL).ignitedByLava().sound(SoundType.WOOD).strength(0.2f).noOcclusion()));

    public static final RegistrySupplier<Block> SHELL = registerBlock("shell",
            () -> new ShellBlock(Properties.of().mapColor(MapColor.STONE).instrument(BASEDRUM).strength(1).requiresCorrectToolForDrops().noOcclusion()));
    public static final RegistrySupplier<DropExperienceBlock> AMBER_ORE = registerBlock("amber_ore",
            () -> new DropExperienceBlock(Properties.of().mapColor(MapColor.STONE).instrument(BASEDRUM).strength(3f).requiresCorrectToolForDrops()));
    public static final RegistrySupplier<Block> AMBER_BLOCK = registerBlock("amber_block",
            () -> new Block(Properties.of().mapColor(MapColor.COLOR_YELLOW).instrument(BASEDRUM).strength(3f).requiresCorrectToolForDrops().noOcclusion()
                    .isViewBlocking(ModBlocks::never)));
    public static final RegistrySupplier<Block> AMBER_CHUNK = registerBlock("amber_chunk",
            () -> new AmberChunkBlock(Properties.copy(AMBER_BLOCK.get()).noOcclusion()));
    public static final RegistrySupplier<Block> AMBER_CHUNK_DOMINICAN = registerBlock("amber_chunk_dominican",
            () -> new AmberChunkBlock(Properties.copy(AMBER_BLOCK.get()).noOcclusion()));
    public static final RegistrySupplier<Block> AMBER_CHUNK_MOSQUITO = registerBlock("amber_chunk_mosquito",
            () -> new AmberChunkBlock(Properties.copy(AMBER_BLOCK.get()).noOcclusion()));

    public static final RegistrySupplier<IcedDirtBlock> ICED_DIRT = registerBlock("iced_dirt",
            () -> new IcedDirtBlock(Properties.of().mapColor(MapColor.DIRT).strength(1, 4).sound(SoundType.MOSS).randomTicks()));
    public static final RegistrySupplier<SandBlock> DENSE_SAND = registerBlock("dense_sand",
            () -> new SandBlock(0x8C765C, Properties.of().mapColor(MapColor.SAND).instrument(SNARE)
                    .strength(3f, 15f).sound(SoundType.SAND)));
    public static final RegistrySupplier<SkullBlock> SKULL_BLOCK = registerBlock("skull",
            () -> new SkullBlock(Properties.of().mapColor(MapColor.SAND).instrument(XYLOPHONE).strength(2, 15f)
                    .requiresCorrectToolForDrops().sound(SoundType.BONE_BLOCK)));
    public static final RegistrySupplier<SkullBlock> SKULL_LANTERN = registerBlock("skull_lantern",
            () -> new SkullBlock(Properties.of().mapColor(MapColor.SAND).instrument(XYLOPHONE).lightLevel(value -> 14).strength(2, 15f)
                    .requiresCorrectToolForDrops().sound(SoundType.BONE_BLOCK)));
    public static final RegistrySupplier<Block> SLIME_TRAIL = registerBlock("slime_trail",
            () -> new RailBlock(Properties.copy(Blocks.SLIME_BLOCK).noCollission()));

    public static final RegistrySupplier<ChainFenceBlock> LARGE_CHAIN_FENCE = registerBlock("large_chain_fence",
            () -> new ChainFenceBlock(Properties.copy(Blocks.IRON_BARS)));
    public static final RegistrySupplier<ChainFenceBlock> SMALL_CHAIN_FENCE = registerBlock("small_chain_fence",
            () -> new ChainFenceBlock(Properties.copy(Blocks.IRON_BARS)));

    public static final RegistrySupplier<Block> ANCIENT_STONE = registerBlock("ancient_stone",
            () -> new Block(Properties.of().mapColor(MapColor.STONE).instrument(BASEDRUM).strength(1.5f).requiresCorrectToolForDrops()));
    public static final RegistrySupplier<Block> ANCIENT_STONE_BRICKS = registerBlock("ancient_stone_bricks",
            () -> new Block(Properties.copy(ANCIENT_STONE.get())));
    public static final RegistrySupplier<SlabBlock> ANCIENT_STONE_SLAB = registerBlock("ancient_stone_slab",
            () -> new SlabBlock(Properties.copy(ANCIENT_STONE.get())));
    public static final RegistrySupplier<StairBlock> ANCIENT_STONE_STAIRS = registerBlock("ancient_stone_stairs",
            () -> new StairBlock(ANCIENT_STONE.get().defaultBlockState(), Properties.copy(ANCIENT_STONE.get())));
    public static final RegistrySupplier<WallBlock> ANCIENT_STONE_WALL = registerBlock("ancient_stone_wall",
            () -> new WallBlock(Properties.copy(ANCIENT_STONE.get())));
    public static final RegistrySupplier<Block> ANCIENT_WOOD_PLANKS = registerBlock("ancient_wood_planks",
            () -> new Block(Properties.of().mapColor(MapColor.WOOD).ignitedByLava().instrument(BASS)
                    .strength(2f, 3f).sound(SoundType.WOOD)));
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
            () -> new FossilBlock(Properties.copy(Blocks.CALCITE).sound(SoundType.CALCITE).strength(1.25f).requiresCorrectToolForDrops()));
    public static final RegistrySupplier<Block> DEEPSLATE_FOSSIL = registerBlock("fossil_deepslate",
            () -> new FossilBlock(Properties.copy(Blocks.DEEPSLATE).sound(SoundType.DEEPSLATE).strength(4, 7).requiresCorrectToolForDrops()));
    public static final RegistrySupplier<Block> DRIPSTONE_FOSSIL = registerBlock("fossil_dripstone",
            () -> new FossilBlock(Properties.copy(Blocks.DRIPSTONE_BLOCK).sound(SoundType.DRIPSTONE_BLOCK).strength(3, 2).requiresCorrectToolForDrops()));
    public static final RegistrySupplier<Block> RED_SANDSTONE_FOSSIL = registerBlock("fossil_red_sandstone",
            () -> new FossilBlock(Properties.copy(Blocks.RED_SANDSTONE).strength(1.6f).requiresCorrectToolForDrops()));
    public static final RegistrySupplier<Block> SANDSTONE_FOSSIL = registerBlock("fossil_sandstone",
            () -> new FossilBlock(Properties.copy(Blocks.SANDSTONE).strength(1.6f).requiresCorrectToolForDrops()));
    public static final RegistrySupplier<Block> STONE_FOSSIL = registerBlock("fossil_stone",
            () -> new FossilBlock(Properties.copy(Blocks.STONE).strength(3, 6).requiresCorrectToolForDrops()));
    public static final RegistrySupplier<Block> TUFF_FOSSIL = registerBlock("fossil_tuff",
            () -> new FossilBlock(Properties.copy(Blocks.TUFF).sound(SoundType.TUFF).strength(3, 6).requiresCorrectToolForDrops()));

    public static final RegistrySupplier<Block> TARRED_DIRT = registerBlock("tarred_dirt",
            () -> new Block(Properties.copy(Blocks.DIRT)));
    public static final RegistrySupplier<Block> PERMAFROST_BLOCK = registerBlock("permafrost_block",
            () -> new PermafrostBlock(Properties.of().mapColor(MapColor.COLOR_BLUE).instrument(BASEDRUM).strength(2f).requiresCorrectToolForDrops()));
    public static final RegistrySupplier<Block> VOLCANIC_ASH = registerBlock("volcanic_ash",
            () -> new Block(Properties.of().mapColor(MapColor.COLOR_BLACK).strength(0.2f).requiresCorrectToolForDrops().sound(SoundType.GRAVEL)));
    public static final RegistrySupplier<Block> VOLCANIC_ROCK = registerBlock("volcanic_rock",
            () -> new Block(Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(BASEDRUM).strength(1f).requiresCorrectToolForDrops()));
    public static final RegistrySupplier<Block> VOLCANIC_BRICKS = registerBlock("volcanic_bricks",
            () -> new Block(Properties.of().mapColor(MapColor.COLOR_BLACK).instrument(BASEDRUM).strength(1.5f).requiresCorrectToolForDrops()));
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
    public static final RegistrySupplier<Block> CALAMITES_PLANKS = planks(ModWoodTypes.CALAMITES);
    public static final RegistrySupplier<StairBlock> CALAMITES_STAIRS = stairs("calamites", CALAMITES_PLANKS);
    public static final RegistrySupplier<SlabBlock> CALAMITES_SLAB = slab(ModWoodTypes.CALAMITES);
    public static final RegistrySupplier<FenceBlock> CALAMITES_FENCE = fence(ModWoodTypes.CALAMITES);
    public static final RegistrySupplier<FenceGateBlock> CALAMITES_FENCE_GATE = fenceGate(ModWoodTypes.CALAMITES);
    public static final RegistrySupplier<DoorBlock> CALAMITES_DOOR = door(ModWoodTypes.CALAMITES);
    public static final RegistrySupplier<TrapDoorBlock> CALAMITES_TRAPDOOR = trapDoor(ModWoodTypes.CALAMITES);
    public static final RegistrySupplier<ButtonBlock> CALAMITES_BUTTON = woodenButton(ModWoodTypes.CALAMITES);
    public static final RegistrySupplier<PressurePlateBlock> CALAMITES_PRESSURE_PLATE = pressurePlate(ModWoodTypes.CALAMITES);
    public static final RegistrySupplier<RotatedPillarBlock> CALAMITES_LOG = log(ModWoodTypes.CALAMITES, false);
    public static final RegistrySupplier<RotatedPillarBlock> CALAMITES_WOOD = wood(ModWoodTypes.CALAMITES, false);
    public static final RegistrySupplier<RotatedPillarBlock> STRIPPED_CALAMITES_LOG = log(ModWoodTypes.CALAMITES, true);
    public static final RegistrySupplier<RotatedPillarBlock> STRIPPED_CALAMITES_WOOD = wood(ModWoodTypes.CALAMITES, true);
    public static final RegistrySupplier<LeavesBlock> CALAMITES_LEAVES = registerBlock("calamites_leaves", () -> FossilLeavesBlock.get(Properties.copy(Blocks.OAK_LEAVES)));
    public static final RegistrySupplier<Block> CALAMITES_SAPLING = registerBlock("calamites_sapling",
            () -> new SaplingBlock(new CalamitesTreeGrower(), Properties.copy(Blocks.OAK_SAPLING)));
    public static final RegistrySupplier<Block> CORDAITES_PLANKS = planks(ModWoodTypes.CORDAITES);
    public static final RegistrySupplier<StairBlock> CORDAITES_STAIRS = stairs("cordaites", CORDAITES_PLANKS);
    public static final RegistrySupplier<SlabBlock> CORDAITES_SLAB = slab(ModWoodTypes.CORDAITES);
    public static final RegistrySupplier<FenceBlock> CORDAITES_FENCE = fence(ModWoodTypes.CORDAITES);
    public static final RegistrySupplier<FenceGateBlock> CORDAITES_FENCE_GATE = fenceGate(ModWoodTypes.CORDAITES);
    public static final RegistrySupplier<DoorBlock> CORDAITES_DOOR = door(ModWoodTypes.CORDAITES);
    public static final RegistrySupplier<TrapDoorBlock> CORDAITES_TRAPDOOR = trapDoor(ModWoodTypes.CORDAITES);
    public static final RegistrySupplier<ButtonBlock> CORDAITES_BUTTON = woodenButton(ModWoodTypes.CORDAITES);
    public static final RegistrySupplier<PressurePlateBlock> CORDAITES_PRESSURE_PLATE = pressurePlate(ModWoodTypes.CORDAITES);
    public static final RegistrySupplier<RotatedPillarBlock> CORDAITES_LOG = log(ModWoodTypes.CORDAITES, false);
    public static final RegistrySupplier<RotatedPillarBlock> CORDAITES_WOOD = wood(ModWoodTypes.CORDAITES, false);
    public static final RegistrySupplier<RotatedPillarBlock> STRIPPED_CORDAITES_LOG = log(ModWoodTypes.CORDAITES, true);
    public static final RegistrySupplier<RotatedPillarBlock> STRIPPED_CORDAITES_WOOD = wood(ModWoodTypes.CORDAITES, true);
    public static final RegistrySupplier<LeavesBlock> CORDAITES_LEAVES = registerBlock("cordaites_leaves", () -> FossilLeavesBlock.get(Properties.copy(Blocks.OAK_LEAVES)));
    public static final RegistrySupplier<SaplingBlock> CORDAITES_SAPLING = registerBlock("cordaites_sapling",
            () -> new SaplingBlock(new CordaitesTreeGrower(), Properties.copy(Blocks.OAK_SAPLING)));

    public static final RegistrySupplier<Block> MUTANT_TREE_PLANKS = planks(ModWoodTypes.MUTANT_TREE);
    public static final RegistrySupplier<StairBlock> MUTANT_TREE_STAIRS = stairs("mutant_tree", MUTANT_TREE_PLANKS);
    public static final RegistrySupplier<SlabBlock> MUTANT_TREE_SLAB = slab(ModWoodTypes.MUTANT_TREE);
    public static final RegistrySupplier<FenceBlock> MUTANT_TREE_FENCE = fence(ModWoodTypes.MUTANT_TREE);
    public static final RegistrySupplier<FenceGateBlock> MUTANT_TREE_FENCE_GATE = fenceGate(ModWoodTypes.MUTANT_TREE);
    public static final RegistrySupplier<DoorBlock> MUTANT_TREE_DOOR = door(ModWoodTypes.MUTANT_TREE);
    public static final RegistrySupplier<TrapDoorBlock> MUTANT_TREE_TRAPDOOR = trapDoor(ModWoodTypes.MUTANT_TREE);
    public static final RegistrySupplier<ButtonBlock> MUTANT_TREE_BUTTON = woodenButton(ModWoodTypes.MUTANT_TREE);
    public static final RegistrySupplier<PressurePlateBlock> MUTANT_TREE_PRESSURE_PLATE = pressurePlate(ModWoodTypes.MUTANT_TREE);
    public static final RegistrySupplier<RotatedPillarBlock> MUTANT_TREE_LOG = log(ModWoodTypes.MUTANT_TREE, false);
    public static final RegistrySupplier<RotatedPillarBlock> MUTANT_TREE_WOOD = wood(ModWoodTypes.MUTANT_TREE, false);
    public static final RegistrySupplier<RotatedPillarBlock> STRIPPED_MUTANT_TREE_LOG = log(ModWoodTypes.MUTANT_TREE, true);
    public static final RegistrySupplier<RotatedPillarBlock> STRIPPED_MUTANT_TREE_WOOD = wood(ModWoodTypes.MUTANT_TREE, true);
    public static final RegistrySupplier<LeavesBlock> MUTANT_TREE_LEAVES = registerBlock("mutant_tree_leaves", () -> FossilLeavesBlock.get(Properties.copy(Blocks.OAK_LEAVES)));
    public static final RegistrySupplier<Block> MUTANT_TREE_SAPLING = registerBlock("mutant_tree_sapling",
            () -> new SaplingBlock(new MutantTreeGrower(), Properties.copy(Blocks.OAK_SAPLING)));
    public static final RegistrySupplier<Block> MUTANT_TREE_TUMOR = blockWithDebugItem("mutant_tree_tumor",
            () -> new MutantTreeTumor(Properties.of().ignitedByLava().pushReaction(PushReaction.DESTROY)
                    .noOcclusion().dynamicShape().sound(SoundType.GRASS)));
    public static final RegistrySupplier<Block> MUTANT_TREE_VINE = registerBlock("mutant_tree_vine",
            () -> new VineBlock(Properties.of().mapColor(MapColor.PLANT).replaceable().ignitedByLava().pushReaction(PushReaction.DESTROY)
                    .forceSolidOff().noCollission().lightLevel(value -> 10).randomTicks().strength(0.2f).sound(SoundType.VINE)));
    //TODO: forceSolidOff()

    public static final RegistrySupplier<Block> PALM_PLANKS = planks(ModWoodTypes.PALM);
    public static final RegistrySupplier<StairBlock> PALM_STAIRS = stairs("palm", PALM_PLANKS);
    public static final RegistrySupplier<SlabBlock> PALM_SLAB = slab(ModWoodTypes.PALM);
    public static final RegistrySupplier<FenceBlock> PALM_FENCE = fence(ModWoodTypes.PALM);
    public static final RegistrySupplier<FenceGateBlock> PALM_FENCE_GATE = fenceGate(ModWoodTypes.PALM);
    public static final RegistrySupplier<DoorBlock> PALM_DOOR = door(ModWoodTypes.PALM);
    public static final RegistrySupplier<TrapDoorBlock> PALM_TRAPDOOR = trapDoor(ModWoodTypes.PALM);
    public static final RegistrySupplier<ButtonBlock> PALM_BUTTON = woodenButton(ModWoodTypes.PALM);
    public static final RegistrySupplier<PressurePlateBlock> PALM_PRESSURE_PLATE = pressurePlate(ModWoodTypes.PALM);
    public static final RegistrySupplier<RotatedPillarBlock> PALM_LOG = log(ModWoodTypes.PALM, false);
    public static final RegistrySupplier<RotatedPillarBlock> PALM_WOOD = wood(ModWoodTypes.PALM, false);
    public static final RegistrySupplier<RotatedPillarBlock> STRIPPED_PALM_LOG = log(ModWoodTypes.PALM, true);
    public static final RegistrySupplier<RotatedPillarBlock> STRIPPED_PALM_WOOD = wood(ModWoodTypes.PALM, true);
    public static final RegistrySupplier<LeavesBlock> PALM_LEAVES = registerBlock("palm_leaves",
            () -> FossilLeavesBlock.get(Properties.copy(Blocks.OAK_LEAVES)));
    public static final RegistrySupplier<Block> PALM_SAPLING = registerBlock("palm_sapling",
            () -> new SaplingBlock(new PalmTreeGrower(), Properties.copy(Blocks.OAK_SAPLING)));

    public static final RegistrySupplier<Block> SIGILLARIA_PLANKS = planks(ModWoodTypes.SIGILLARIA);
    public static final RegistrySupplier<StairBlock> SIGILLARIA_STAIRS = stairs("sigillaria", SIGILLARIA_PLANKS);
    public static final RegistrySupplier<SlabBlock> SIGILLARIA_SLAB = slab(ModWoodTypes.SIGILLARIA);
    public static final RegistrySupplier<FenceBlock> SIGILLARIA_FENCE = fence(ModWoodTypes.SIGILLARIA);
    public static final RegistrySupplier<FenceGateBlock> SIGILLARIA_FENCE_GATE = fenceGate(ModWoodTypes.SIGILLARIA);
    public static final RegistrySupplier<DoorBlock> SIGILLARIA_DOOR = door(ModWoodTypes.SIGILLARIA);
    public static final RegistrySupplier<TrapDoorBlock> SIGILLARIA_TRAPDOOR = trapDoor(ModWoodTypes.SIGILLARIA);
    public static final RegistrySupplier<ButtonBlock> SIGILLARIA_BUTTON = woodenButton(ModWoodTypes.SIGILLARIA);
    public static final RegistrySupplier<PressurePlateBlock> SIGILLARIA_PRESSURE_PLATE = pressurePlate(ModWoodTypes.SIGILLARIA);
    public static final RegistrySupplier<RotatedPillarBlock> SIGILLARIA_LOG = log(ModWoodTypes.SIGILLARIA, false);
    public static final RegistrySupplier<RotatedPillarBlock> SIGILLARIA_WOOD = wood(ModWoodTypes.SIGILLARIA, false);
    public static final RegistrySupplier<RotatedPillarBlock> STRIPPED_SIGILLARIA_LOG = log(ModWoodTypes.SIGILLARIA, true);
    public static final RegistrySupplier<RotatedPillarBlock> STRIPPED_SIGILLARIA_WOOD = wood(ModWoodTypes.SIGILLARIA, true);
    public static final RegistrySupplier<LeavesBlock> SIGILLARIA_LEAVES = registerBlock("sigillaria_leaves", () -> FossilLeavesBlock.get(Properties.copy(Blocks.OAK_LEAVES)));
    public static final RegistrySupplier<Block> SIGILLARIA_SAPLING = registerBlock("sigillaria_sapling",
            () -> new SaplingBlock(new SigillariaTreeGrower(), Properties.copy(Blocks.OAK_SAPLING)));

    public static final RegistrySupplier<Block> TEMPSKYA_PLANKS = planks(ModWoodTypes.TEMPSKYA);
    public static final RegistrySupplier<StairBlock> TEMPSKYA_STAIRS = stairs("tempskya", TEMPSKYA_PLANKS);
    public static final RegistrySupplier<SlabBlock> TEMPSKYA_SLAB = slab(ModWoodTypes.TEMPSKYA);
    public static final RegistrySupplier<FenceBlock> TEMPSKYA_FENCE = fence(ModWoodTypes.TEMPSKYA);
    public static final RegistrySupplier<FenceGateBlock> TEMPSKYA_FENCE_GATE = fenceGate(ModWoodTypes.TEMPSKYA);
    public static final RegistrySupplier<DoorBlock> TEMPSKYA_DOOR = door(ModWoodTypes.TEMPSKYA);
    public static final RegistrySupplier<TrapDoorBlock> TEMPSKYA_TRAPDOOR = trapDoor(ModWoodTypes.TEMPSKYA);
    public static final RegistrySupplier<ButtonBlock> TEMPSKYA_BUTTON = woodenButton(ModWoodTypes.TEMPSKYA);
    public static final RegistrySupplier<PressurePlateBlock> TEMPSKYA_PRESSURE_PLATE = pressurePlate(ModWoodTypes.TEMPSKYA);
    public static final RegistrySupplier<RotatedPillarBlock> TEMPSKYA_LOG = log(ModWoodTypes.TEMPSKYA, false);
    public static final RegistrySupplier<RotatedPillarBlock> TEMPSKYA_WOOD = wood(ModWoodTypes.TEMPSKYA, false);
    public static final RegistrySupplier<RotatedPillarBlock> STRIPPED_TEMPSKYA_LOG = log(ModWoodTypes.TEMPSKYA, true);
    public static final RegistrySupplier<RotatedPillarBlock> STRIPPED_TEMPSKYA_WOOD = wood(ModWoodTypes.TEMPSKYA, true);
    public static final RegistrySupplier<Block> TEMPSKYA_SAPLING = registerBlock("tempskya_sapling",
            () -> new SaplingBlock(new TempskyaTreeGrower(), Properties.copy(Blocks.OAK_SAPLING)));
    public static final RegistrySupplier<Block> TEMPSKYA_TOP = registerBlock("tempskya_top",
            () -> new TempskyaTopBlock(Properties.of().mapColor(MapColor.PLANT).ignitedByLava().pushReaction(PushReaction.DESTROY).noOcclusion().sound(SoundType.GRASS)));
    public static final RegistrySupplier<Block> TEMPSKYA_LEAF = registerBlock("tempskya_leaf",
            () -> new TempskyaLeafBlock(Properties.of().mapColor(MapColor.PLANT).ignitedByLava().pushReaction(PushReaction.DESTROY).noCollission().noOcclusion().sound(SoundType.GRASS)));

    public static final List<RegistrySupplier<VaseBlock>> VASES = new ArrayList<>();
    public static final List<Pair<DyeColor, RegistrySupplier<VaseBlock>>> VASES_WITH_COLOR = new ArrayList<>();
    public static final RegistrySupplier<VaseBlock> VOLUTE_VASE_DAMAGED = volute(VaseBlock.VaseVariant.DAMAGED);
    public static final RegistrySupplier<VaseBlock> VOLUTE_VASE_RESTORED = volute(VaseBlock.VaseVariant.RESTORED);
    public static final RegistrySupplier<VaseBlock> KYLIX_VASE_DAMAGED = kylix(VaseBlock.VaseVariant.DAMAGED);
    public static final RegistrySupplier<VaseBlock> KYLIX_VASE_RESTORED = kylix(VaseBlock.VaseVariant.RESTORED);
    public static final RegistrySupplier<VaseBlock> AMPHORA_VASE_DAMAGED = amphora(VaseBlock.VaseVariant.DAMAGED);
    public static final RegistrySupplier<VaseBlock> AMPHORA_VASE_RESTORED = amphora(VaseBlock.VaseVariant.RESTORED);
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
    public static final RegistrySupplier<Block> FERNS = blockWithoutBlockItem("plant_ferns", FernsBlock::new);

    static {
        for (DyeColor color : DyeColor.values()) {
            VASES_WITH_COLOR.add(new Pair<>(color, vase("amphora", color.getSerializedName(), AmphoraVaseBlock::new)));
            VASES_WITH_COLOR.add(new Pair<>(color, vase("kylix", color.getSerializedName(), KylixVaseBlock::new)));
            VASES_WITH_COLOR.add(new Pair<>(color, vase("volute", color.getSerializedName(), VoluteVaseBlock::new)));
        }
    }

    private static ToIntFunction<BlockState> activeBlockEmission(int lightValue) {
        return arg -> Boolean.TRUE.equals(arg.getValue(CustomEntityBlock.ACTIVE)) ? lightValue : 0;
    }

    private static BlockBehaviour.Properties woodProp(ModWoodTypes.WoodInfo woodInfo) {
        return Properties.of().mapColor(woodInfo.mapColor()).ignitedByLava().instrument(BASS).strength(2, 3).sound(woodInfo.woodType().soundType());
    }

    private static RegistrySupplier<Block> planks(ModWoodTypes.WoodInfo woodInfo) {
        return registerBlock(woodInfo.name() + "_planks", () -> new Block(woodProp(woodInfo)));
    }

    private static RegistrySupplier<StairBlock> stairs(String name, RegistrySupplier<Block> base) {
        return registerBlock(name + "_stairs", () -> new StairBlock(base.get().defaultBlockState(), BlockBehaviour.Properties.copy(base.get())));
    }

    private static RegistrySupplier<SlabBlock> slab(ModWoodTypes.WoodInfo woodInfo) {
        return registerBlock(woodInfo.name() + "_slab", () -> new SlabBlock(woodProp(woodInfo)));
    }

    private static RegistrySupplier<FenceBlock> fence(ModWoodTypes.WoodInfo woodInfo) {
        return registerBlock(woodInfo.name() + "_fence", () -> new FenceBlock(woodProp(woodInfo)));
    }

    private static RegistrySupplier<FenceGateBlock> fenceGate(ModWoodTypes.WoodInfo woodInfo) {
        return registerBlock(woodInfo.name() + "_fence_gate", () -> new FenceGateBlock(woodProp(woodInfo), woodInfo.woodType()));
    }

    private static RegistrySupplier<DoorBlock> door(ModWoodTypes.WoodInfo woodInfo) {
        return registerBlock(woodInfo.name() + "_door", () -> new DoorBlock(woodProp(woodInfo).strength(3).noOcclusion(), woodInfo.setType()));
    }

    private static RegistrySupplier<TrapDoorBlock> trapDoor(ModWoodTypes.WoodInfo woodInfo) {
        return registerBlock(woodInfo.name() + "_trapdoor", () -> new TrapDoorBlock(woodProp(woodInfo)
                .strength(3).noOcclusion().isValidSpawn(ModBlocks::never), woodInfo.setType()));
    }

    private static RegistrySupplier<ButtonBlock> woodenButton(ModWoodTypes.WoodInfo woodInfo) {
        return registerBlock(woodInfo.name() + "_button", () -> new ButtonBlock(Properties.of().pushReaction(PushReaction.DESTROY)
                .strength(0.5f).noCollission(), woodInfo.setType(), 30, true));
    }

    private static RegistrySupplier<ButtonBlock> stoneButton(ModWoodTypes.WoodInfo woodInfo) {
        return registerBlock(woodInfo.name() + "_button", () -> new ButtonBlock(Properties.of().pushReaction(PushReaction.DESTROY)
                .strength(0.5f).noCollission(), woodInfo.setType(), 30, true));
    }

    private static RegistrySupplier<PressurePlateBlock> pressurePlate(ModWoodTypes.WoodInfo woodInfo) {
        return registerBlock(woodInfo.name() + "_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING,
                woodProp(woodInfo).strength(0.5f).noCollission(), woodInfo.setType()));
    }

    private static RegistrySupplier<RotatedPillarBlock> log(ModWoodTypes.WoodInfo woodInfo, boolean stripped) {
        return rotatedPillar((stripped ? "stripped_" : "") + woodInfo.name() + "_log", woodProp(woodInfo).strength(2));
    }

    private static RegistrySupplier<RotatedPillarBlock> wood(ModWoodTypes.WoodInfo woodInfo, boolean stripped) {
        return rotatedPillar((stripped ? "stripped_" : "") + woodInfo.name() + "_wood", woodProp(woodInfo).strength(2));
    }

    private static RegistrySupplier<RotatedPillarBlock> rotatedPillar(String name, Properties properties) {
        return registerBlock(name, () -> FlammableRotatedPillarBlock.get(properties));
    }

    private static Boolean never(BlockState state, BlockGetter blockGetter, BlockPos pos, EntityType<?> entity) {
        return false;
    }

    private static boolean never(BlockState state, BlockGetter blockGetter, BlockPos pos) {
        return false;
    }

    private static boolean always(BlockState state, BlockGetter blockGetter, BlockPos pos) {
        return true;
    }

    private static RegistrySupplier<VaseBlock> volute(VaseBlock.VaseVariant variant) {
        return vase("volute", variant.getSerializedName(), VoluteVaseBlock::new);
    }

    private static RegistrySupplier<VaseBlock> kylix(VaseBlock.VaseVariant variant) {
        return vase("kylix", variant.getSerializedName(), KylixVaseBlock::new);
    }

    private static RegistrySupplier<VaseBlock> amphora(VaseBlock.VaseVariant variant) {
        return vase("amphora", variant.getSerializedName(), AmphoraVaseBlock::new);
    }

    private static RegistrySupplier<VaseBlock> vase(String name, String variant, Supplier<VaseBlock> supplier) {
        var toReturn = registerBlock("vase_" + name + "_" + variant, supplier);
        VASES.add(toReturn);
        return toReturn;
    }

    private static RegistrySupplier<FigurineBlock> registerAnu(FigurineBlock.FigurineVariant variant) {
        return figurine("anu", variant, () -> new FigurineAnuBlock(variant));
    }

    private static RegistrySupplier<FigurineBlock> registerEnderman(FigurineBlock.FigurineVariant variant) {
        return figurine("enderman", variant, () -> new FigurineEndermanBlock(variant));
    }

    private static RegistrySupplier<FigurineBlock> registerPiglin(FigurineBlock.FigurineVariant variant) {
        return figurine("piglin", variant, () -> new FigurinePiglinBlock(variant));
    }

    private static RegistrySupplier<FigurineBlock> registerSkeleton(FigurineBlock.FigurineVariant variant) {
        return figurine("skeleton", variant, () -> new FigurineSkeletonBlock(variant));
    }

    private static RegistrySupplier<FigurineBlock> registerSteve(FigurineBlock.FigurineVariant variant) {
        return figurine("steve", variant, () -> new FigurineSteveBlock(variant));
    }

    private static RegistrySupplier<FigurineBlock> registerZombie(FigurineBlock.FigurineVariant variant) {
        return figurine("zombie", variant, () -> new FigurineZombieBlock(variant));
    }

    private static RegistrySupplier<FigurineBlock> figurine(String name, FigurineBlock.FigurineVariant variant, Supplier<FigurineBlock> supplier) {
        var toReturn = registerBlock("figurine_" + name + "_" + variant.getSerializedName(), supplier);
        FIGURINES.add(toReturn);
        return toReturn;
    }

    public static RegistrySupplier<ShortFlowerBlock> registerShortFlower(String name, VoxelShape shape) {
        return registerBlock(name, () -> new ShortFlowerBlock(shape));
    }

    public static RegistrySupplier<TallFlowerBlock> registerTallFlower(String name, VoxelShape shape) {
        return registerBlock(name, () -> new TallFlowerBlock(shape));
    }

    public static RegistrySupplier<FourTallFlowerBlock> registerFourTallFlower(String name, VoxelShape shape) {
        return registerBlock(name, () -> new FourTallFlowerBlock(shape));
    }

    public static RegistrySupplier<GrowableFlowerBlock> registerGrowableFlower(String name, RegistrySupplier<? extends BushBlock> tallFlower,
                                                                               VoxelShape shape) {
        return registerBlock(name, () -> new GrowableFlowerBlock(tallFlower, shape));
    }

    public static <T extends Block> RegistrySupplier<T> blockWithoutBlockItem(String name, Supplier<T> block) {
        return BLOCKS.register(name, block);
    }

    private static <T extends Block, R extends BlockItem> RegistrySupplier<T> blockWithCustomBlockItem(String name, Supplier<T> block,
                                                                                                       Function<T, R> blockItem) {
        RegistrySupplier<T> toReturn = BLOCKS.register(name, block);
        ModItems.ITEMS.register(name, () -> blockItem.apply(toReturn.get()));
        return toReturn;
    }

    public static <T extends Block> RegistrySupplier<T> blockWithDebugItem(String name, Supplier<T> block) {
        RegistrySupplier<T> toReturn = BLOCKS.register(name, block);
        if (Version.debugEnabled()) {
            RegistrySupplier<Item> item = registerBlockItem(name, toReturn);
            //TODO: See if this works
            CreativeTabRegistry.modify(ModTabs.FA_BLOCK_TAB, (featureFlagSet, creativeTabOutput, canUseGameMasterBlocks) -> {
                if (canUseGameMasterBlocks) {
                    creativeTabOutput.accept(item.get());
                }
            });
        }
        return toReturn;
    }

    public static <T extends Block> RegistrySupplier<T> registerBlock(String name, Supplier<T> block) {
        RegistrySupplier<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistrySupplier<Item> registerBlockItem(String name, RegistrySupplier<T> block) {
        return ModItems.ITEMS.register(name, () -> new CustomBlockItem(block.get(), new Item.Properties().arch$tab(ModTabs.FA_BLOCK_TAB)));
    }

    public static void register() {
        PrehistoricPlantInfo.register();
        FlammableRotatedPillarBlock.registerAllStripped();
        BLOCKS.register();
    }
}
