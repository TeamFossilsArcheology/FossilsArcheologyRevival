package com.fossil.fossil.block;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.block.custom_blocks.SkullBlock;
import com.fossil.fossil.block.custom_blocks.*;
import com.fossil.fossil.item.ModItems;
import com.fossil.fossil.item.ModTabs;
import dev.architectury.registry.registries.DeferredRegister;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(Fossil.MOD_ID, Registry.BLOCK_REGISTRY);

    public static final RegistrySupplier<Block> ANALYZER_BLOCK = registerBlock(
            "analyzer_block",
            () -> new AnalyzerBlock(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.METAL).strength(2f).requiresCorrectToolForDrops()),
            ModTabs.FABLOCKTAB
    );
    public static final RegistrySupplier<SifterBlock> SIFTER = registerBlock("sifter",
            () -> new SifterBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2.5f).sound(SoundType.METAL)), ModTabs.FABLOCKTAB);
    public static final RegistrySupplier<Block> CULTIVATE_BLOCK = registerBlock("cultivate_block",
            () -> new CultivateBlock(BlockBehaviour.Properties.of(Material.GLASS, MaterialColor.COLOR_CYAN).strength(2f).requiresCorrectToolForDrops()), ModTabs.FABLOCKTAB);
    public static final RegistrySupplier<Block> WORKTABLE = registerBlock("worktable_block",
            () -> new WorktableBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).strength(1f).requiresCorrectToolForDrops().sound(SoundType.WOOD)), ModTabs.FABLOCKTAB);
    public static final RegistrySupplier<FeederBlock> FEEDER =  registerBlock("feeder",
            () -> new FeederBlock(BlockBehaviour.Properties.of(Material.METAL).strength(3)), ModTabs.FABLOCKTAB);
    public static final RegistrySupplier<OreBlock> AMBER_ORE = registerBlock("amber_ore",
            () -> new OreBlock(BlockBehaviour.Properties.of(Material.STONE).strength(3f).requiresCorrectToolForDrops()), ModTabs.FABLOCKTAB);
    public  static  final RegistrySupplier<IceBlock> ICED_STONE = registerBlock("iced_stone",
            () -> new IceBlock(BlockBehaviour.Properties.of(Material.STONE).strength(1.5f, 10f).requiresCorrectToolForDrops().sound(SoundType.GLASS).randomTicks()), ModTabs.FABLOCKTAB);
    public static final RegistrySupplier<DenseSandBlock> DENSE_SAND = registerBlock("dense_sand",
            () -> new DenseSandBlock(9205340, BlockBehaviour.Properties.of(Material.SAND).strength(3f, 15f).sound(SoundType.SAND)), ModTabs.FABLOCKTAB);
    public static final RegistrySupplier<SkullBlock> SKULL_BLOCK = registerBlock("skull",
            () -> new SkullBlock(BlockBehaviour.Properties.of(Material.STONE).strength(4f, 15f).requiresCorrectToolForDrops()), ModTabs.FABLOCKTAB);
    public static final RegistrySupplier<SkullBlock> SKULL_LANTERN = registerBlock("skull_lantern",
            () -> new SkullBlock(BlockBehaviour.Properties.of(Material.STONE).lightLevel(value -> 14).strength(4f, 15f).requiresCorrectToolForDrops()), ModTabs.FABLOCKTAB);
    public static final RegistrySupplier<Block> ANCIENT_STONE = registerBlock("ancient_stone",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(1.5f).requiresCorrectToolForDrops()), ModTabs.FABLOCKTAB);
    public static final RegistrySupplier<Block> ANCIENT_STONE_BRICKS = registerBlock("ancient_stone_bricks",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE).strength(1.5f, 6f).requiresCorrectToolForDrops()), ModTabs.FABLOCKTAB);
    public static final RegistrySupplier<SlabBlock> ANCIENT_STONE_SLAB = registerBlock("ancient_stone_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of(Material.STONE).strength(1.7f, 7.5f).requiresCorrectToolForDrops()), ModTabs.FABLOCKTAB);
    public static final RegistrySupplier<StairBlock> ANCIENT_STONE_STAIRS = registerBlock("ancient_stone_stairs",
            () -> new StairBlock(ANCIENT_STONE.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.STONE).strength(1.5f).requiresCorrectToolForDrops()), ModTabs.FABLOCKTAB);
    public static final RegistrySupplier<Block> ANCIENT_WOOD = registerBlock("ancient_wood",
            () -> new Block(BlockBehaviour.Properties.of(Material.WOOD).strength(2f, 3f).sound(SoundType.WOOD)), ModTabs.FABLOCKTAB);
    public static final RegistrySupplier<SlabBlock> ANCIENT_WOOD_SLAB = registerBlock("ancient_wood_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(2f, 3f).sound(SoundType.WOOD)), ModTabs.FABLOCKTAB);
    public static final RegistrySupplier<RotatedPillarBlock> ANCIENT_WOOD_PILLAR = registerBlock("ancient_wood_pillar",
            () -> new RotatedPillarBlock(BlockBehaviour.Properties.of(Material.WOOD).strength(1.4f, 1f).sound(SoundType.WOOD)), ModTabs.FABLOCKTAB);
    public static final RegistrySupplier<StairBlock> ANCIENT_WOOD_STAIRS = registerBlock("ancient_wood_stairs",
            () -> new StairBlock(ANCIENT_WOOD.get().defaultBlockState(), BlockBehaviour.Properties.of(Material.WOOD).strength(1.4f, 1f).sound(SoundType.WOOD)), ModTabs.FABLOCKTAB);
    private static boolean never(BlockState state, BlockGetter blockGetter, BlockPos pos) {
        return false;
    }
    public static final RegistrySupplier<ClearGlassBlock> REINFORCED_GLASS = registerBlock("reinforced_glass",
            () -> new ClearGlassBlock(BlockBehaviour.Properties.of(Material.GLASS).strength(3f, 25f).noOcclusion()), ModTabs.FABLOCKTAB);
    public static final RegistrySupplier<ClearGlassBlock> ANCIENT_GLASS = registerBlock("ancient_glass",
            () -> new ClearGlassBlock(BlockBehaviour.Properties.of(Material.GLASS).strength(1f).noOcclusion().isViewBlocking(ModBlocks::never)), ModTabs.FABLOCKTAB);
    public static final RegistrySupplier<DrumBlock> DRUM = registerBlock("drum",
            () -> new DrumBlock(BlockBehaviour.Properties.of(Material.WOOD).sound(SoundType.WOOD)), ModTabs.FABLOCKTAB);
    public static final RegistrySupplier<Block> FOSSIL_BLOCK = registerBlock("fossil_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).strength(2f).requiresCorrectToolForDrops()), ModTabs.FABLOCKTAB);
    public static final RegistrySupplier<Block> PERMAFROST_BLOCK = registerBlock("permafrost_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BLUE).strength(1f).requiresCorrectToolForDrops()), ModTabs.FABLOCKTAB);
    public static final RegistrySupplier<Block> VOLCANIC_ASH = registerBlock("volcanic_ash",
            () -> new Block(BlockBehaviour.Properties.of(Material.DIRT, MaterialColor.COLOR_BLACK).strength(0.2f).requiresCorrectToolForDrops().sound(SoundType.GRAVEL)), ModTabs.FABLOCKTAB);
    public static final RegistrySupplier<Block> VOLCANIC_ROCK = registerBlock("volcanic_rock",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).strength(1f).requiresCorrectToolForDrops()), ModTabs.FABLOCKTAB);
    public static final RegistrySupplier<Block> VOLCANIC_BRICKS = registerBlock("volcanic_bricks",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).strength(1f).requiresCorrectToolForDrops()), ModTabs.FABLOCKTAB);

    public static final RegistrySupplier<Block> VOLCANIC_BRICK_SLAB = registerBlock("volcanic_brick_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.of(Material.STONE).strength(2f, 3f)), ModTabs.FABLOCKTAB);
    public static final RegistrySupplier<Block> VOLCANIC_BRICK_STAIRS = registerBlock("volcanic_brick_stairs",
            () -> new StairBlock(VOLCANIC_BRICKS.get().defaultBlockState(), BlockBehaviour.Properties.copy(VOLCANIC_BRICKS.get())), ModTabs.FABLOCKTAB);

    public static final RegistrySupplier<Block> CORDAITES_PLANKS = registerBlock("cordaites_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.SPRUCE_PLANKS).strength(1f).requiresCorrectToolForDrops()), ModTabs.FABLOCKTAB);
    public static final RegistrySupplier<Block> CORDAITES_STAIRS = registerBlock("cordaites_stairs",
            () -> new StairBlock(ModBlocks.CORDAITES_PLANKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.of(Material.WOOD).strength(1f).requiresCorrectToolForDrops()), ModTabs.FABLOCKTAB);
    public static final RegistrySupplier<Block> CORDAITES_SLAB = registerBlock("cordaites_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_SLAB)
                    .strength(1f).requiresCorrectToolForDrops()), ModTabs.FABLOCKTAB);
    public static final RegistrySupplier<Block> CORDAITES_FENCE = registerBlock("cordaites_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_FENCE)
                    .strength(1f).requiresCorrectToolForDrops()), ModTabs.FABLOCKTAB);
    public static final RegistrySupplier<Block> CORDAITES_FENCE_GATE = registerBlock("cordaites_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_FENCE_GATE)
                    .strength(1f).requiresCorrectToolForDrops()), ModTabs.FABLOCKTAB);
    public static final RegistrySupplier<Block> CORDAITES_DOOR = registerBlock("cordaites_door",
            () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_DOOR)
                    .strength(1f).requiresCorrectToolForDrops()), ModTabs.FABLOCKTAB);
    public static final RegistrySupplier<Block> CORDAITES_TRAPDOOR = registerBlock("cordaites_trapdoor",
            () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_TRAPDOOR)
                    .strength(1f).requiresCorrectToolForDrops()), ModTabs.FABLOCKTAB);
    public static final RegistrySupplier<Block> CORDAITES_BUTTON = registerBlock("cordaites_button",
            () -> new StoneButtonBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_BUTTON)
                    .strength(1f).requiresCorrectToolForDrops()), ModTabs.FABLOCKTAB);
    public static final RegistrySupplier<Block> CORDAITES_PRESSURE_PLATE = registerBlock("cordaites_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.SPRUCE_PRESSURE_PLATE)
                    .strength(1f).requiresCorrectToolForDrops()), ModTabs.FABLOCKTAB);

    public static final RegistrySupplier<Block> CORDAITES_LOG = registerBlock("cordaites_log",
            () -> ModFlammableRotatedPillarBlock.get(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)), ModTabs.FABLOCKTAB);
    public static final RegistrySupplier<Block> CORDAITES_WOOD = registerBlock("cordaites_wood",
            () -> ModFlammableRotatedPillarBlock.get(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)), ModTabs.FABLOCKTAB);
    public static final RegistrySupplier<Block> STRIPPED_CORDAITES_LOG = registerBlock("stripped_cordaites_log",
            () -> ModFlammableRotatedPillarBlock.get(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG)), ModTabs.FABLOCKTAB);
    public static final RegistrySupplier<Block> STRIPPED_CORDAITES_WOOD = registerBlock("stripped_cordaites_wood",
            () -> ModFlammableRotatedPillarBlock.get(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD)), ModTabs.FABLOCKTAB);

    public static final RegistrySupplier<Block> CORDAITES_LEAVES = registerBlock("cordaites_leaves",
            () -> FossilLeavesBlock.get(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)), ModTabs.FABLOCKTAB);

    public static final RegistrySupplier<Block> SIGILLARIA_PLANKS = registerBlock("sigillaria_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.SPRUCE_PLANKS).strength(1f).requiresCorrectToolForDrops()), ModTabs.FABLOCKTAB);
    public static final RegistrySupplier<Block> SIGILLARIA_STAIRS = registerBlock("sigillaria_stairs",
            () -> new StairBlock(ModBlocks.SIGILLARIA_PLANKS.get().defaultBlockState(),
                    BlockBehaviour.Properties.of(Material.WOOD).strength(1f).requiresCorrectToolForDrops()), ModTabs.FABLOCKTAB);
    public static final RegistrySupplier<Block> SIGILLARIA_SLAB = registerBlock("sigillaria_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_SLAB)
                    .strength(1f).requiresCorrectToolForDrops()), ModTabs.FABLOCKTAB);
    public static final RegistrySupplier<Block> SIGILLARIA_FENCE = registerBlock("sigillaria_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_FENCE)
                    .strength(1f).requiresCorrectToolForDrops()), ModTabs.FABLOCKTAB);
    public static final RegistrySupplier<Block> SIGILLARIA_FENCE_GATE = registerBlock("sigillaria_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_FENCE_GATE)
                    .strength(1f).requiresCorrectToolForDrops()), ModTabs.FABLOCKTAB);
    public static final RegistrySupplier<Block> SIGILLARIA_DOOR = registerBlock("sigillaria_door",
            () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_DOOR)
                    .strength(1f).requiresCorrectToolForDrops()), ModTabs.FABLOCKTAB);
    public static final RegistrySupplier<Block> SIGILLARIA_TRAPDOOR = registerBlock("sigillaria_trapdoor",
            () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_TRAPDOOR)
                    .strength(1f).requiresCorrectToolForDrops()), ModTabs.FABLOCKTAB);
    public static final RegistrySupplier<Block> SIGILLARIA_BUTTON = registerBlock("sigillaria_button",
            () -> new StoneButtonBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_BUTTON)
                    .strength(1f).requiresCorrectToolForDrops()), ModTabs.FABLOCKTAB);
    public static final RegistrySupplier<Block> SIGILLARIA_PRESSURE_PLATE = registerBlock("sigillaria_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.SPRUCE_PRESSURE_PLATE)
                    .strength(1f).requiresCorrectToolForDrops()), ModTabs.FABLOCKTAB);

    public static final RegistrySupplier<Block> SIGILLARIA_LOG = registerBlock("sigillaria_log",
            () -> ModFlammableRotatedPillarBlock.get(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)), ModTabs.FABLOCKTAB);
    public static final RegistrySupplier<Block> SIGILLARIA_WOOD = registerBlock("sigillaria_wood",
            () -> ModFlammableRotatedPillarBlock.get(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)), ModTabs.FABLOCKTAB);
    public static final RegistrySupplier<Block> STRIPPED_SIGILLARIA_LOG = registerBlock("stripped_sigillaria_log",
            () -> ModFlammableRotatedPillarBlock.get(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG)), ModTabs.FABLOCKTAB);
    public static final RegistrySupplier<Block> STRIPPED_SIGILLARIA_WOOD = registerBlock("stripped_sigillaria_wood",
            () -> ModFlammableRotatedPillarBlock.get(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD)), ModTabs.FABLOCKTAB);

    public static final RegistrySupplier<Block> SIGILLARIA_LEAVES = registerBlock("sigillaria_leaves",
            () -> FossilLeavesBlock.get(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)), ModTabs.FABLOCKTAB);


    public static final RegistrySupplier<Block> BENNETTITALES_SMALL = registerBlock("bennettitales_small",
            () -> new FlowerBlock(MobEffects.SATURATION, 0,
                    BlockBehaviour.Properties.copy(Blocks.DANDELION).noOcclusion()), ModTabs.FABLOCKTAB);
    public static final RegistrySupplier<Block> BENNETTITALES_TALL = registerBlock("bennettitales_tall",
            () -> new DoublePlantBlock(BlockBehaviour.Properties.copy(Blocks.TALL_GRASS).noOcclusion()), ModTabs.FABLOCKTAB);

    public static final List<RegistrySupplier<VaseBlock>> VASES = new ArrayList<>();
    public static final RegistrySupplier<VaseBlock> VOLUTE_VASE_DAMAGED = registerVolute(VaseBlock.VaseVariant.DAMAGED);
    public static final RegistrySupplier<VaseBlock> VOLUTE_VASE_RESTORED = registerVolute(VaseBlock.VaseVariant.RESTORED);
    public static final RegistrySupplier<VaseBlock> VOLUTE_VASE_RED_FIGURE = registerVolute( VaseBlock.VaseVariant.RED_FIGURE);
    public static final RegistrySupplier<VaseBlock> VOLUTE_VASE_BLACK_FIGURE = registerVolute(VaseBlock.VaseVariant.BLACK_FIGURE);
    public static final RegistrySupplier<VaseBlock> VOLUTE_VASE_PORCELAIN = registerVolute(VaseBlock.VaseVariant.PORCELAIN);

    public static final RegistrySupplier<VaseBlock> KYLIX_VASE_DAMAGED = registerKylix( VaseBlock.VaseVariant.DAMAGED);
    public static final RegistrySupplier<VaseBlock> KYLIX_VASE_RESTORED = registerKylix( VaseBlock.VaseVariant.RESTORED);
    public static final RegistrySupplier<VaseBlock> KYLIX_VASE_RED_FIGURE = registerKylix( VaseBlock.VaseVariant.RED_FIGURE);
    public static final RegistrySupplier<VaseBlock> KYLIX_VASE_BLACK_FIGURE = registerKylix( VaseBlock.VaseVariant.BLACK_FIGURE);
    public static final RegistrySupplier<VaseBlock> KYLIX_VASE_PORCELAIN = registerKylix( VaseBlock.VaseVariant.PORCELAIN);

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
        var toReturn = registerBlock("vase_"+name+"_"+variant.getSerializedName(), supplier, ModTabs.FABLOCKTAB);
        VASES.add(toReturn);
        return toReturn;
    }

    private static <T extends Block> RegistrySupplier<T> registerBlockWithoutBlockItem(String name, Supplier<T> block, CreativeModeTab tab) {
        return BLOCKS.register(name, block);
    }

    private static <T extends Block> RegistrySupplier<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistrySupplier<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;

    }

    private static <T extends Block> RegistrySupplier<Item> registerBlockItem(String name, RegistrySupplier<T> block,
                                                                              CreativeModeTab tab) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties().tab(tab))
        );
    }

    public static void register() {
        BLOCKS.register();
    }
}
