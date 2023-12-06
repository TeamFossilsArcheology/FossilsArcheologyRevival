package com.fossil.fossil.forge.data.providers;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.block.ModBlocks;
import com.fossil.fossil.block.custom_blocks.FigurineBlock;
import com.fossil.fossil.tags.ModBlockTags;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ModBlockTagsProvider extends BlockTagsProvider {

    public ModBlockTagsProvider(DataGenerator arg, @Nullable ExistingFileHelper existingFileHelper) {
        super(arg, Fossil.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(ModBlockTags.CALAMITES_LOGS).add(ModBlocks.CALAMITES_LOG.get(), ModBlocks.CALAMITES_WOOD.get(), ModBlocks.STRIPPED_CALAMITES_LOG.get(), ModBlocks.STRIPPED_CALAMITES_WOOD.get());
        tag(ModBlockTags.CORDAITES_LOGS).add(ModBlocks.CORDAITES_LOG.get(), ModBlocks.CORDAITES_WOOD.get(), ModBlocks.STRIPPED_CORDAITES_LOG.get(), ModBlocks.STRIPPED_CORDAITES_WOOD.get());
        tag(ModBlockTags.MUTANT_TREE_LOGS).add(ModBlocks.MUTANT_TREE_LOG.get(), ModBlocks.MUTANT_TREE_WOOD.get(), ModBlocks.STRIPPED_MUTANT_TREE_LOG.get(), ModBlocks.STRIPPED_MUTANT_TREE_WOOD.get());
        tag(ModBlockTags.PALM_LOGS).add(ModBlocks.PALM_LOG.get(), ModBlocks.PALM_WOOD.get(), ModBlocks.STRIPPED_PALM_LOG.get(), ModBlocks.STRIPPED_PALM_WOOD.get());
        tag(ModBlockTags.SIGILLARIA_LOGS).add(ModBlocks.SIGILLARIA_LOG.get(), ModBlocks.SIGILLARIA_WOOD.get(), ModBlocks.STRIPPED_SIGILLARIA_LOG.get(), ModBlocks.STRIPPED_SIGILLARIA_WOOD.get());
        tag(ModBlockTags.TEMPSKYA_LOGS).add(ModBlocks.TEMPSKYA_LOG.get(), ModBlocks.TEMPSKYA_WOOD.get(), ModBlocks.STRIPPED_TEMPSKYA_LOG.get(), ModBlocks.STRIPPED_TEMPSKYA_WOOD.get());
        var figurines = tag(ModBlockTags.FIGURINES);
        for (RegistrySupplier<FigurineBlock> figurine : ModBlocks.FIGURINES) {
            figurines.add(figurine.get());
        }
        tag(BlockTags.LOGS_THAT_BURN).addTags(ModBlockTags.CALAMITES_LOGS, ModBlockTags.CORDAITES_LOGS, ModBlockTags.MUTANT_TREE_LOGS, ModBlockTags.PALM_LOGS, ModBlockTags.SIGILLARIA_LOGS, ModBlockTags.TEMPSKYA_LOGS);
        tag(BlockTags.PLANKS).add(ModBlocks.CALAMITES_PLANKS.get(), ModBlocks.CORDAITES_PLANKS.get(), ModBlocks.MUTANT_TREE_PLANKS.get(), ModBlocks.PALM_PLANKS.get(), ModBlocks.SIGILLARIA_PLANKS.get(), ModBlocks.TEMPSKYA_PLANKS.get());
        tag(BlockTags.WOODEN_BUTTONS).add(ModBlocks.CALAMITES_BUTTON.get(), ModBlocks.CORDAITES_BUTTON.get(), ModBlocks.MUTANT_TREE_BUTTON.get(), ModBlocks.PALM_BUTTON.get(), ModBlocks.SIGILLARIA_BUTTON.get(), ModBlocks.TEMPSKYA_BUTTON.get());
        tag(BlockTags.WOODEN_DOORS).add(ModBlocks.CALAMITES_DOOR.get(), ModBlocks.CORDAITES_DOOR.get(), ModBlocks.MUTANT_TREE_DOOR.get(), ModBlocks.PALM_DOOR.get(), ModBlocks.SIGILLARIA_DOOR.get(), ModBlocks.TEMPSKYA_DOOR.get());
        tag(BlockTags.WOODEN_STAIRS).add(ModBlocks.CALAMITES_STAIRS.get(), ModBlocks.CORDAITES_STAIRS.get(), ModBlocks.MUTANT_TREE_STAIRS.get(), ModBlocks.PALM_STAIRS.get(), ModBlocks.SIGILLARIA_STAIRS.get(), ModBlocks.TEMPSKYA_STAIRS.get());
        tag(BlockTags.WOODEN_SLABS).add(ModBlocks.CALAMITES_SLAB.get(), ModBlocks.CORDAITES_SLAB.get(), ModBlocks.MUTANT_TREE_SLAB.get(), ModBlocks.PALM_SLAB.get(), ModBlocks.SIGILLARIA_SLAB.get(), ModBlocks.TEMPSKYA_SLAB.get());
        tag(BlockTags.WOODEN_FENCES).add(ModBlocks.CALAMITES_FENCE.get(), ModBlocks.CORDAITES_FENCE.get(), ModBlocks.MUTANT_TREE_FENCE.get(), ModBlocks.PALM_FENCE.get(), ModBlocks.SIGILLARIA_FENCE.get(), ModBlocks.TEMPSKYA_FENCE.get());
        tag(BlockTags.FENCE_GATES).add(ModBlocks.CALAMITES_FENCE_GATE.get(), ModBlocks.CORDAITES_FENCE_GATE.get(), ModBlocks.MUTANT_TREE_FENCE_GATE.get(), ModBlocks.PALM_FENCE_GATE.get(), ModBlocks.SIGILLARIA_FENCE_GATE.get(), ModBlocks.TEMPSKYA_FENCE_GATE.get());
        tag(BlockTags.WOODEN_PRESSURE_PLATES).add(ModBlocks.CALAMITES_PRESSURE_PLATE.get(), ModBlocks.CORDAITES_PRESSURE_PLATE.get(), ModBlocks.MUTANT_TREE_PRESSURE_PLATE.get(), ModBlocks.PALM_PRESSURE_PLATE.get(), ModBlocks.SIGILLARIA_PRESSURE_PLATE.get(), ModBlocks.TEMPSKYA_PRESSURE_PLATE.get());
        tag(BlockTags.WOODEN_TRAPDOORS).add(ModBlocks.CALAMITES_TRAPDOOR.get(), ModBlocks.CORDAITES_TRAPDOOR.get(), ModBlocks.MUTANT_TREE_TRAPDOOR.get(), ModBlocks.PALM_TRAPDOOR.get(), ModBlocks.SIGILLARIA_TRAPDOOR.get(), ModBlocks.TEMPSKYA_TRAPDOOR.get());
        tag(BlockTags.SAPLINGS).add(ModBlocks.CALAMITES_SAPLING.get(), ModBlocks.CORDAITES_SAPLING.get(), ModBlocks.MUTANT_TREE_SAPLING.get(), ModBlocks.PALM_SAPLING.get(), ModBlocks.SIGILLARIA_SAPLING.get(), ModBlocks.TEMPSKYA_SAPLING.get());
        tag(BlockTags.WALLS).add(ModBlocks.ANCIENT_STONE_WALL.get(), ModBlocks.VOLCANIC_BRICK_WALL.get(), ModBlocks.VOLCANIC_TILE_WALL.get());
        //TODO: Small flowers?
        tag(BlockTags.LEAVES).add(ModBlocks.CALAMITES_LEAVES.get(), ModBlocks.CORDAITES_LEAVES.get(), ModBlocks.MUTANT_TREE_LEAVES.get(), ModBlocks.PALM_LEAVES.get(), ModBlocks.SIGILLARIA_LEAVES.get(), ModBlocks.TEMPSKYA_LEAF.get());
        tag(BlockTags.PORTALS).add(ModBlocks.ANU_PORTAL.get(), ModBlocks.HOME_PORTAL.get());
        tag(BlockTags.SOUL_SPEED_BLOCKS).add(ModBlocks.TARRED_DIRT.get());
        tag(BlockTags.MINEABLE_WITH_AXE).add(ModBlocks.DRUM.get(), ModBlocks.SIFTER.get(), ModBlocks.WORKTABLE.get(), ModBlocks.MUTANT_TREE_VINE.get());
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.ANCIENT_GLASS.get(), ModBlocks.REINFORCED_GLASS.get(), ModBlocks.FAKE_OBSIDIAN.get(), ModBlocks.CULTURE_VAT.get(), ModBlocks.ANALYZER.get(), ModBlocks.FOSSIL.get(), ModBlocks.SKULL_BLOCK.get(), ModBlocks.SKULL_LANTERN.get(), ModBlocks.BUBBLE_BLOWER.get(),
                ModBlocks.ANCIENT_STONE.get(), ModBlocks.ANCIENT_STONE_BRICKS.get(), ModBlocks.ANCIENT_STONE_SLAB.get(), ModBlocks.ANCIENT_STONE_STAIRS.get(), ModBlocks.ANCIENT_STONE_WALL.get(),
                ModBlocks.VOLCANIC_ROCK.get(), ModBlocks.VOLCANIC_BRICKS.get(), ModBlocks.VOLCANIC_BRICK_SLAB.get(), ModBlocks.VOLCANIC_BRICK_STAIRS.get(), ModBlocks.VOLCANIC_BRICK_WALL.get(),
                ModBlocks.VOLCANIC_TILES.get(), ModBlocks.VOLCANIC_TILE_SLAB.get(), ModBlocks.VOLCANIC_TILE_STAIRS.get(), ModBlocks.VOLCANIC_TILE_WALL.get());
        tag(BlockTags.MINEABLE_WITH_SHOVEL).add(ModBlocks.DENSE_SAND.get(), ModBlocks.VOLCANIC_ASH.get(), ModBlocks.TARRED_DIRT.get(), ModBlocks.SLIME_TRAIL.get(), ModBlocks.ICED_DIRT.get(), ModBlocks.PERMAFROST_BLOCK.get());
        tag(BlockTags.MINEABLE_WITH_HOE).add(ModBlocks.CALAMITES_LEAVES.get(), ModBlocks.CORDAITES_LEAVES.get(), ModBlocks.MUTANT_TREE_LEAVES.get(), ModBlocks.PALM_LEAVES.get(), ModBlocks.SIGILLARIA_LEAVES.get(), ModBlocks.TEMPSKYA_LEAF.get());
        tag(BlockTags.NEEDS_DIAMOND_TOOL).add(ModBlocks.FAKE_OBSIDIAN.get(), ModBlocks.OBSIDIAN_SPIKES.get());//Time Machine
        tag(BlockTags.NEEDS_IRON_TOOL).add(ModBlocks.FOSSIL.get(), ModBlocks.PERMAFROST_BLOCK.get());
        tag(BlockTags.NEEDS_STONE_TOOL).add(ModBlocks.ANCIENT_GLASS.get(), ModBlocks.REINFORCED_GLASS.get(), ModBlocks.ICED_DIRT.get(), ModBlocks.PERMAFROST_BLOCK.get());
        tag(BlockTags.CLIMBABLE).add(ModBlocks.MUTANT_TREE_VINE.get());
        tag(BlockTags.REPLACEABLE_PLANTS).add(ModBlocks.MUTANT_TREE_VINE.get());
    }

    @Override
    public @NotNull String getName() {
        return "Fossil Block Tags";
    }
}
