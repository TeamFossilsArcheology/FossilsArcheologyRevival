package com.github.teamfossilsarcheology.fossil.forge.data.providers;

import com.github.teamfossilsarcheology.fossil.Fossil;
import com.github.teamfossilsarcheology.fossil.block.custom_blocks.FigurineBlock;
import com.github.teamfossilsarcheology.fossil.block.custom_blocks.VaseBlock;
import com.github.teamfossilsarcheology.fossil.tags.ModBlockTags;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Arrays;
import java.util.function.Supplier;

import static com.github.teamfossilsarcheology.fossil.block.ModBlocks.*;

public class ModBlockTagsProvider extends BlockTagsProvider {

    public ModBlockTagsProvider(DataGenerator arg, @Nullable ExistingFileHelper existingFileHelper) {
        super(arg, Fossil.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        addTag(ModBlockTags.ANCIENT_WOOD_LOGS, ANCIENT_WOOD_LOG);
        addTag(ModBlockTags.CALAMITES_LOGS, CALAMITES_LOG, CALAMITES_WOOD, STRIPPED_CALAMITES_LOG, STRIPPED_CALAMITES_WOOD);
        addTag(ModBlockTags.CORDAITES_LOGS, CORDAITES_LOG, CORDAITES_WOOD, STRIPPED_CORDAITES_LOG, STRIPPED_CORDAITES_WOOD);
        addTag(ModBlockTags.MUTANT_TREE_LOGS, MUTANT_TREE_LOG, MUTANT_TREE_WOOD, STRIPPED_MUTANT_TREE_LOG, STRIPPED_MUTANT_TREE_WOOD);
        addTag(ModBlockTags.PALM_LOGS, PALM_LOG, PALM_WOOD, STRIPPED_PALM_LOG, STRIPPED_PALM_WOOD);
        addTag(ModBlockTags.SIGILLARIA_LOGS, SIGILLARIA_LOG, SIGILLARIA_WOOD, STRIPPED_SIGILLARIA_LOG, STRIPPED_SIGILLARIA_WOOD);
        addTag(ModBlockTags.TEMPSKYA_LOGS, TEMPSKYA_LOG, TEMPSKYA_WOOD, STRIPPED_TEMPSKYA_LOG, STRIPPED_TEMPSKYA_WOOD);
        var figurines = tag(ModBlockTags.FIGURINES);
        for (RegistrySupplier<FigurineBlock> figurine : FIGURINES) {
            figurines.add(figurine.get());
        }
        tag(BlockTags.LOGS_THAT_BURN).addTags(ModBlockTags.ANCIENT_WOOD_LOGS, ModBlockTags.CALAMITES_LOGS, ModBlockTags.CORDAITES_LOGS, ModBlockTags.MUTANT_TREE_LOGS, ModBlockTags.PALM_LOGS, ModBlockTags.SIGILLARIA_LOGS, ModBlockTags.TEMPSKYA_LOGS);
        addTag(BlockTags.PLANKS, ANCIENT_WOOD_PLANKS, CALAMITES_PLANKS, CORDAITES_PLANKS, MUTANT_TREE_PLANKS, PALM_PLANKS, SIGILLARIA_PLANKS, TEMPSKYA_PLANKS);
        addTag(BlockTags.WOODEN_BUTTONS, CALAMITES_BUTTON, CORDAITES_BUTTON, MUTANT_TREE_BUTTON, PALM_BUTTON, SIGILLARIA_BUTTON, TEMPSKYA_BUTTON);
        addTag(BlockTags.WOODEN_DOORS, CALAMITES_DOOR, CORDAITES_DOOR, MUTANT_TREE_DOOR, PALM_DOOR, SIGILLARIA_DOOR, TEMPSKYA_DOOR);
        addTag(BlockTags.WOODEN_STAIRS, ANCIENT_WOOD_STAIRS, CALAMITES_STAIRS, CORDAITES_STAIRS, MUTANT_TREE_STAIRS, PALM_STAIRS, SIGILLARIA_STAIRS, TEMPSKYA_STAIRS);
        addTag(BlockTags.WOODEN_SLABS, ANCIENT_WOOD_SLAB, CALAMITES_SLAB, CORDAITES_SLAB, MUTANT_TREE_SLAB, PALM_SLAB, SIGILLARIA_SLAB, TEMPSKYA_SLAB);
        addTag(BlockTags.WOODEN_FENCES, CALAMITES_FENCE, CORDAITES_FENCE, MUTANT_TREE_FENCE, PALM_FENCE, SIGILLARIA_FENCE, TEMPSKYA_FENCE);
        addTag(BlockTags.FENCE_GATES, CALAMITES_FENCE_GATE, CORDAITES_FENCE_GATE, MUTANT_TREE_FENCE_GATE, PALM_FENCE_GATE, SIGILLARIA_FENCE_GATE, TEMPSKYA_FENCE_GATE);
        addTag(BlockTags.WOODEN_PRESSURE_PLATES, CALAMITES_PRESSURE_PLATE, CORDAITES_PRESSURE_PLATE, MUTANT_TREE_PRESSURE_PLATE, PALM_PRESSURE_PLATE, SIGILLARIA_PRESSURE_PLATE, TEMPSKYA_PRESSURE_PLATE);
        addTag(BlockTags.WOODEN_TRAPDOORS, CALAMITES_TRAPDOOR, CORDAITES_TRAPDOOR, MUTANT_TREE_TRAPDOOR, PALM_TRAPDOOR, SIGILLARIA_TRAPDOOR, TEMPSKYA_TRAPDOOR);
        addTag(BlockTags.SAPLINGS, CALAMITES_SAPLING, CORDAITES_SAPLING, MUTANT_TREE_SAPLING, PALM_SAPLING, SIGILLARIA_SAPLING, TEMPSKYA_SAPLING);
        addTag(BlockTags.STAIRS, ANCIENT_STONE_STAIRS, VOLCANIC_BRICK_STAIRS, VOLCANIC_TILE_STAIRS);
        addTag(BlockTags.SLABS, ANCIENT_STONE_SLAB, VOLCANIC_BRICK_SLAB, VOLCANIC_TILE_SLAB);
        addTag(BlockTags.WALLS, ANCIENT_STONE_WALL, VOLCANIC_BRICK_WALL, VOLCANIC_TILE_WALL);
        addTag(BlockTags.RAILS, SLIME_TRAIL);
        addTag(BlockTags.LEAVES, CALAMITES_LEAVES, CORDAITES_LEAVES, MUTANT_TREE_LEAVES, PALM_LEAVES, SIGILLARIA_LEAVES, TEMPSKYA_LEAF);
        addTag(BlockTags.BEDS, COMFY_BED);
        addTag(BlockTags.PORTALS, ANU_PORTAL, HOME_PORTAL);
        addTag(BlockTags.DIRT, TARRED_DIRT, ICED_DIRT);
        addTag(BlockTags.SOUL_SPEED_BLOCKS, TARRED_DIRT);
        addTag(BlockTags.CLIMBABLE, MUTANT_TREE_VINE);
        addTag(BlockTags.MINEABLE_WITH_AXE, DRUM, SIFTER, WORKTABLE, MUTANT_TREE_VINE);
        addTag(BlockTags.MINEABLE_WITH_PICKAXE,
                CALCITE_FOSSIL, DEEPSLATE_FOSSIL, DRIPSTONE_FOSSIL, RED_SANDSTONE_FOSSIL, SANDSTONE_FOSSIL, STONE_FOSSIL, TUFF_FOSSIL,
                ANCIENT_GLASS, REINFORCED_GLASS, FAKE_OBSIDIAN, CULTURE_VAT, ANALYZER, SKULL_BLOCK, SKULL_LANTERN, BUBBLE_BLOWER,
                ANCIENT_STONE, ANCIENT_STONE_BRICKS, ANCIENT_STONE_SLAB, ANCIENT_STONE_STAIRS, ANCIENT_STONE_WALL,
                VOLCANIC_ROCK, VOLCANIC_BRICKS, VOLCANIC_BRICK_SLAB, VOLCANIC_BRICK_STAIRS, VOLCANIC_BRICK_WALL,
                VOLCANIC_TILES, VOLCANIC_TILE_SLAB, VOLCANIC_TILE_STAIRS, VOLCANIC_TILE_WALL,
                AMBER_ORE, AMBER_BLOCK, AMBER_CHUNK, AMBER_CHUNK_DOMINICAN, AMBER_CHUNK_MOSQUITO, SHELL);
        for (RegistrySupplier<VaseBlock> vase : VASES) {
            addTag(BlockTags.MINEABLE_WITH_PICKAXE, vase);
        }
        addTag(BlockTags.MINEABLE_WITH_SHOVEL, DENSE_SAND, VOLCANIC_ASH, SLIME_TRAIL, PERMAFROST_BLOCK);
        addTag(BlockTags.MINEABLE_WITH_HOE, CALAMITES_LEAVES, CORDAITES_LEAVES, MUTANT_TREE_LEAVES, PALM_LEAVES, SIGILLARIA_LEAVES, TEMPSKYA_LEAF);
        addTag(BlockTags.NEEDS_DIAMOND_TOOL, FAKE_OBSIDIAN, OBSIDIAN_SPIKES);//Time Machine
        addTag(BlockTags.NEEDS_IRON_TOOL, CALCITE_FOSSIL, DEEPSLATE_FOSSIL, DRIPSTONE_FOSSIL, RED_SANDSTONE_FOSSIL, SANDSTONE_FOSSIL, STONE_FOSSIL, TUFF_FOSSIL, PERMAFROST_BLOCK, AMBER_ORE);
        addTag(BlockTags.NEEDS_STONE_TOOL, ANCIENT_GLASS, REINFORCED_GLASS, ICED_DIRT, PERMAFROST_BLOCK);
        addTag(BlockTags.REPLACEABLE_PLANTS, MUTANT_TREE_VINE);

        addTag(ModBlockTags.UNBREAKABLE, ANCIENT_GLASS, BUBBLE_BLOWER, DENSE_SAND, FEEDER, REINFORCED_GLASS);
    }

    @SafeVarargs
    private void addTag(TagKey<Block> tag, RegistrySupplier<? extends Block>... toAdd) {
        tag(tag).add(Arrays.stream(toAdd).map(Supplier::get).toArray(Block[]::new));
    }
    private void addTag(TagKey<Block> tag, RegistrySupplier<Block> toAdd) {
        tag(tag).add(toAdd.get());
    }

    @Override
    public @NotNull String getName() {
        return "Fossil Block Tags";
    }
}
