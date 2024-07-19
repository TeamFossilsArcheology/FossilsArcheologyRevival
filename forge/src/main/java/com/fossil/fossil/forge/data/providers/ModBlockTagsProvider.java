package com.fossil.fossil.forge.data.providers;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.block.custom_blocks.FigurineBlock;
import com.fossil.fossil.tags.ModBlockTags;
import dev.architectury.registry.registries.RegistrySupplier;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import static com.fossil.fossil.block.ModBlocks.*;

public class ModBlockTagsProvider extends BlockTagsProvider {

    public ModBlockTagsProvider(DataGenerator arg, @Nullable ExistingFileHelper existingFileHelper) {
        super(arg, Fossil.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(ModBlockTags.CALAMITES_LOGS).add(CALAMITES_LOG.get(), CALAMITES_WOOD.get(), STRIPPED_CALAMITES_LOG.get(), STRIPPED_CALAMITES_WOOD.get());
        tag(ModBlockTags.CORDAITES_LOGS).add(CORDAITES_LOG.get(), CORDAITES_WOOD.get(), STRIPPED_CORDAITES_LOG.get(), STRIPPED_CORDAITES_WOOD.get());
        tag(ModBlockTags.MUTANT_TREE_LOGS).add(MUTANT_TREE_LOG.get(), MUTANT_TREE_WOOD.get(), STRIPPED_MUTANT_TREE_LOG.get(), STRIPPED_MUTANT_TREE_WOOD.get());
        tag(ModBlockTags.PALM_LOGS).add(PALM_LOG.get(), PALM_WOOD.get(), STRIPPED_PALM_LOG.get(), STRIPPED_PALM_WOOD.get());
        tag(ModBlockTags.SIGILLARIA_LOGS).add(SIGILLARIA_LOG.get(), SIGILLARIA_WOOD.get(), STRIPPED_SIGILLARIA_LOG.get(), STRIPPED_SIGILLARIA_WOOD.get());
        tag(ModBlockTags.TEMPSKYA_LOGS).add(TEMPSKYA_LOG.get(), TEMPSKYA_WOOD.get(), STRIPPED_TEMPSKYA_LOG.get(), STRIPPED_TEMPSKYA_WOOD.get());
        var figurines = tag(ModBlockTags.FIGURINES);
        for (RegistrySupplier<FigurineBlock> figurine : FIGURINES) {
            figurines.add(figurine.get());
        }
        tag(BlockTags.LOGS_THAT_BURN).addTags(ModBlockTags.CALAMITES_LOGS, ModBlockTags.CORDAITES_LOGS, ModBlockTags.MUTANT_TREE_LOGS, ModBlockTags.PALM_LOGS, ModBlockTags.SIGILLARIA_LOGS, ModBlockTags.TEMPSKYA_LOGS);
        tag(BlockTags.PLANKS).add(CALAMITES_PLANKS.get(), CORDAITES_PLANKS.get(), MUTANT_TREE_PLANKS.get(), PALM_PLANKS.get(), SIGILLARIA_PLANKS.get(), TEMPSKYA_PLANKS.get());
        tag(BlockTags.WOODEN_BUTTONS).add(CALAMITES_BUTTON.get(), CORDAITES_BUTTON.get(), MUTANT_TREE_BUTTON.get(), PALM_BUTTON.get(), SIGILLARIA_BUTTON.get(), TEMPSKYA_BUTTON.get());
        tag(BlockTags.WOODEN_DOORS).add(CALAMITES_DOOR.get(), CORDAITES_DOOR.get(), MUTANT_TREE_DOOR.get(), PALM_DOOR.get(), SIGILLARIA_DOOR.get(), TEMPSKYA_DOOR.get());
        tag(BlockTags.WOODEN_STAIRS).add(CALAMITES_STAIRS.get(), CORDAITES_STAIRS.get(), MUTANT_TREE_STAIRS.get(), PALM_STAIRS.get(), SIGILLARIA_STAIRS.get(), TEMPSKYA_STAIRS.get());
        tag(BlockTags.WOODEN_SLABS).add(CALAMITES_SLAB.get(), CORDAITES_SLAB.get(), MUTANT_TREE_SLAB.get(), PALM_SLAB.get(), SIGILLARIA_SLAB.get(), TEMPSKYA_SLAB.get());
        tag(BlockTags.WOODEN_FENCES).add(CALAMITES_FENCE.get(), CORDAITES_FENCE.get(), MUTANT_TREE_FENCE.get(), PALM_FENCE.get(), SIGILLARIA_FENCE.get(), TEMPSKYA_FENCE.get());
        tag(BlockTags.FENCE_GATES).add(CALAMITES_FENCE_GATE.get(), CORDAITES_FENCE_GATE.get(), MUTANT_TREE_FENCE_GATE.get(), PALM_FENCE_GATE.get(), SIGILLARIA_FENCE_GATE.get(), TEMPSKYA_FENCE_GATE.get());
        tag(BlockTags.WOODEN_PRESSURE_PLATES).add(CALAMITES_PRESSURE_PLATE.get(), CORDAITES_PRESSURE_PLATE.get(), MUTANT_TREE_PRESSURE_PLATE.get(), PALM_PRESSURE_PLATE.get(), SIGILLARIA_PRESSURE_PLATE.get(), TEMPSKYA_PRESSURE_PLATE.get());
        tag(BlockTags.WOODEN_TRAPDOORS).add(CALAMITES_TRAPDOOR.get(), CORDAITES_TRAPDOOR.get(), MUTANT_TREE_TRAPDOOR.get(), PALM_TRAPDOOR.get(), SIGILLARIA_TRAPDOOR.get(), TEMPSKYA_TRAPDOOR.get());
        tag(BlockTags.SAPLINGS).add(CALAMITES_SAPLING.get(), CORDAITES_SAPLING.get(), MUTANT_TREE_SAPLING.get(), PALM_SAPLING.get(), SIGILLARIA_SAPLING.get(), TEMPSKYA_SAPLING.get());
        tag(BlockTags.WALLS).add(ANCIENT_STONE_WALL.get(), VOLCANIC_BRICK_WALL.get(), VOLCANIC_TILE_WALL.get());
        //TODO: Small flowers?
        tag(BlockTags.LEAVES).add(CALAMITES_LEAVES.get(), CORDAITES_LEAVES.get(), MUTANT_TREE_LEAVES.get(), PALM_LEAVES.get(), SIGILLARIA_LEAVES.get(), TEMPSKYA_LEAF.get());
        tag(BlockTags.PORTALS).add(ANU_PORTAL.get(), HOME_PORTAL.get());
        tag(BlockTags.SOUL_SPEED_BLOCKS).add(TARRED_DIRT.get());
        tag(BlockTags.MINEABLE_WITH_AXE).add(DRUM.get(), SIFTER.get(), WORKTABLE.get(), MUTANT_TREE_VINE.get());
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ANCIENT_GLASS.get(), REINFORCED_GLASS.get(), FAKE_OBSIDIAN.get(), CULTURE_VAT.get(), ANALYZER.get(), FOSSIL.get(), SKULL_BLOCK.get(), SKULL_LANTERN.get(), BUBBLE_BLOWER.get(),
                ANCIENT_STONE.get(), ANCIENT_STONE_BRICKS.get(), ANCIENT_STONE_SLAB.get(), ANCIENT_STONE_STAIRS.get(), ANCIENT_STONE_WALL.get(),
                VOLCANIC_ROCK.get(), VOLCANIC_BRICKS.get(), VOLCANIC_BRICK_SLAB.get(), VOLCANIC_BRICK_STAIRS.get(), VOLCANIC_BRICK_WALL.get(),
                VOLCANIC_TILES.get(), VOLCANIC_TILE_SLAB.get(), VOLCANIC_TILE_STAIRS.get(), VOLCANIC_TILE_WALL.get());
        tag(BlockTags.MINEABLE_WITH_SHOVEL).add(DENSE_SAND.get(), VOLCANIC_ASH.get(), TARRED_DIRT.get(), SLIME_TRAIL.get(), ICED_DIRT.get(), PERMAFROST_BLOCK.get());
        tag(BlockTags.MINEABLE_WITH_HOE).add(CALAMITES_LEAVES.get(), CORDAITES_LEAVES.get(), MUTANT_TREE_LEAVES.get(), PALM_LEAVES.get(), SIGILLARIA_LEAVES.get(), TEMPSKYA_LEAF.get());
        tag(BlockTags.NEEDS_DIAMOND_TOOL).add(FAKE_OBSIDIAN.get(), OBSIDIAN_SPIKES.get());//Time Machine
        tag(BlockTags.NEEDS_IRON_TOOL).add(FOSSIL.get(), PERMAFROST_BLOCK.get());
        tag(BlockTags.NEEDS_STONE_TOOL).add(ANCIENT_GLASS.get(), REINFORCED_GLASS.get(), ICED_DIRT.get(), PERMAFROST_BLOCK.get());
        tag(BlockTags.CLIMBABLE).add(MUTANT_TREE_VINE.get());
        tag(BlockTags.REPLACEABLE_PLANTS).add(MUTANT_TREE_VINE.get());
        tag(BlockTags.RAILS).add(SLIME_TRAIL.get());
    }

    @Override
    public @NotNull String getName() {
        return "Fossil Block Tags";
    }
}
