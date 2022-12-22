package com.fossil.fossil.block;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.block.custom_blocks.AnalyzerBlock;
import com.fossil.fossil.block.custom_blocks.ModFlammableRotatedPillarBlock;
import com.fossil.fossil.block.custom_blocks.WorktableBlock;
import com.fossil.fossil.item.FATabRegistry;
import com.fossil.fossil.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
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
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, Fossil.MOD_ID);

    public static final RegistryObject<Block> ANALYZER_BLOCK = registerBlock("analyzer_block",
            () -> new AnalyzerBlock(BlockBehaviour.Properties.of(Material.METAL, MaterialColor.METAL).strength(2f).requiresCorrectToolForDrops()), FATabRegistry.FABLOCKTAB);

    public static final RegistryObject<Block> CULTIVATE_BLOCK = registerBlock("cultivate_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.GLASS, MaterialColor.COLOR_CYAN).strength(2f).requiresCorrectToolForDrops()), FATabRegistry.FABLOCKTAB);
    public static final RegistryObject<Block> FOSSIL_BLOCK = registerBlock("fossil_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.STONE).strength(2f).requiresCorrectToolForDrops()), FATabRegistry.FABLOCKTAB);
    public static final RegistryObject<Block> PERMAFROST_BLOCK = registerBlock("permafrost_block",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BLUE).strength(1f).requiresCorrectToolForDrops()), FATabRegistry.FABLOCKTAB);
    public static final RegistryObject<Block> WORKTABLE = registerBlock("worktable_block",
            () -> new WorktableBlock(BlockBehaviour.Properties.of(Material.WOOD, MaterialColor.COLOR_BROWN).strength(1f).requiresCorrectToolForDrops()), FATabRegistry.FABLOCKTAB);

    public static final RegistryObject<Block> VOLCANIC_ROCK = registerBlock("volcanic_rock",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).strength(1f).requiresCorrectToolForDrops()), FATabRegistry.FABLOCKTAB);
    public static final RegistryObject<Block> VOLCANIC_BRICKS = registerBlock("volcanic_bricks",
            () -> new Block(BlockBehaviour.Properties.of(Material.STONE, MaterialColor.COLOR_BLACK).strength(1f).requiresCorrectToolForDrops()), FATabRegistry.FABLOCKTAB);

    public static final RegistryObject<Block> CORDAITES_PLANKS = registerBlock("cordaites_planks",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.SPRUCE_PLANKS).strength(1f).requiresCorrectToolForDrops()), FATabRegistry.FABLOCKTAB);
    public static final RegistryObject<Block> CORDAITES_STAIRS = registerBlock("cordaites_stairs",
            () -> new StairBlock(() -> ModBlocks.CORDAITES_PLANKS.get().defaultBlockState(),
            BlockBehaviour.Properties.of(Material.WOOD).strength(1f).requiresCorrectToolForDrops()), FATabRegistry.FABLOCKTAB);
    public static final RegistryObject<Block> CORDAITES_SLAB = registerBlock("cordaites_slab",
            () -> new SlabBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_SLAB)
                    .strength(1f).requiresCorrectToolForDrops()), FATabRegistry.FABLOCKTAB);
    public static final RegistryObject<Block> CORDAITES_FENCE = registerBlock("cordaites_fence",
            () -> new FenceBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_FENCE)
                    .strength(1f).requiresCorrectToolForDrops()), FATabRegistry.FABLOCKTAB);
    public static final RegistryObject<Block> CORDAITES_FENCE_GATE = registerBlock("cordaites_fence_gate",
            () -> new FenceGateBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_FENCE_GATE)
                    .strength(1f).requiresCorrectToolForDrops()), FATabRegistry.FABLOCKTAB);
    public static final RegistryObject<Block> CORDAITES_DOOR = registerBlock("cordaites_door",
            () -> new DoorBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_DOOR)
                    .strength(1f).requiresCorrectToolForDrops()), FATabRegistry.FABLOCKTAB);
    public static final RegistryObject<Block> CORDAITES_TRAPDOOR = registerBlock("cordaites_trapdoor",
            () -> new TrapDoorBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_TRAPDOOR)
                    .strength(1f).requiresCorrectToolForDrops()), FATabRegistry.FABLOCKTAB);
    public static final RegistryObject<Block> CORDAITES_BUTTON = registerBlock("cordaites_button",
            () -> new StoneButtonBlock(BlockBehaviour.Properties.copy(Blocks.SPRUCE_BUTTON)
                    .strength(1f).requiresCorrectToolForDrops()), FATabRegistry.FABLOCKTAB);
    public static final RegistryObject<Block> CORDAITES_PRESSURE_PLATE = registerBlock("cordaites_pressure_plate",
            () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.copy(Blocks.SPRUCE_PRESSURE_PLATE)
                    .strength(1f).requiresCorrectToolForDrops()), FATabRegistry.FABLOCKTAB);

    public static final RegistryObject<Block> CORDAITES_LOG = registerBlock("cordaites_log",
        () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LOG)), FATabRegistry.FABLOCKTAB);
    public static final RegistryObject<Block> CORDAITES_WOOD = registerBlock("cordaites_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.OAK_WOOD)), FATabRegistry.FABLOCKTAB);
    public static final RegistryObject<Block> STRIPPED_CORDAITES_LOG = registerBlock("stripped_cordaites_log",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_LOG)), FATabRegistry.FABLOCKTAB);
    public static final RegistryObject<Block> STRIPPED_CORDAITES_WOOD = registerBlock("stripped_cordaites_wood",
            () -> new ModFlammableRotatedPillarBlock(BlockBehaviour.Properties.copy(Blocks.STRIPPED_OAK_WOOD)), FATabRegistry.FABLOCKTAB);

    public static final RegistryObject<Block> CORDAITES_LEAVES = registerBlock("cordaites_leaves",
            () -> new LeavesBlock(BlockBehaviour.Properties.copy(Blocks.OAK_LEAVES)) {
    @Override
    public boolean isFlammable(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return true;
                }
    @Override
    public int getFlammability(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 60;
                }

   @Override
   public int getFireSpreadSpeed(BlockState state, BlockGetter world, BlockPos pos, Direction face) {
        return 30;
                }
            },FATabRegistry.FABLOCKTAB);


    public static final RegistryObject<Block> BENNETTITALES_SMALL = registerBlock("bennettitales_small",
            () -> new FlowerBlock(MobEffects.SATURATION,0,
                    BlockBehaviour.Properties.copy(Blocks.DANDELION).noOcclusion()), FATabRegistry.FABLOCKTAB);
    public static final RegistryObject<Block> BENNETTITALES_TALL = registerBlock("bennettitales_tall",
            () -> new DoublePlantBlock(BlockBehaviour.Properties.copy(Blocks.TALL_GRASS).noOcclusion()), FATabRegistry.FABLOCKTAB);


    private static <T extends Block> RegistryObject<T> registerBlockWithoutBlockItem(String name, Supplier<T> block, CreativeModeTab tab) {
       return BLOCKS.register(name, block);

    }
    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block, CreativeModeTab tab) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn, tab);
        return toReturn;

    }

    private static <T extends Block> RegistryObject<Item> registerBlockItem(String name, RegistryObject<T> block,
                                                                            CreativeModeTab tab) {
        return ModItems.ITEMS.register(name, () -> new BlockItem(block.get(),
                new Item.Properties().tab(tab)));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
