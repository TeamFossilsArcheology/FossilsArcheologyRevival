package com.fossil.fossil.forge.data.providers;

import com.fossil.fossil.Fossil;
import com.fossil.fossil.block.ModBlocks;
import net.minecraft.data.DataGenerator;
import net.minecraft.data.tags.BlockTagsProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class ModBlockTagsProvider extends BlockTagsProvider {
    public static final TagKey<Block> CALAMITES_LOGS = BlockTags.create(new ResourceLocation(Fossil.MOD_ID, "calamites_logs"));
    public static final TagKey<Block> CORDAITES_LOGS = BlockTags.create(new ResourceLocation(Fossil.MOD_ID, "cordaites_logs"));
    public static final TagKey<Block> PALM_LOGS = BlockTags.create(new ResourceLocation(Fossil.MOD_ID, "palm_logs"));
    public static final TagKey<Block> SIGILLARIA_LOGS = BlockTags.create(new ResourceLocation(Fossil.MOD_ID, "sigillaria_logs"));
    public static final TagKey<Block> TEMPSKYA_LOGS = BlockTags.create(new ResourceLocation(Fossil.MOD_ID, "tempskya_logs"));

    public ModBlockTagsProvider(DataGenerator arg, @Nullable ExistingFileHelper existingFileHelper) {
        super(arg, Fossil.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags() {
        tag(CALAMITES_LOGS).add(ModBlocks.CALAMITES_LOG.get(), ModBlocks.CALAMITES_WOOD.get(), ModBlocks.STRIPPED_CALAMITES_LOG.get(), ModBlocks.STRIPPED_CALAMITES_WOOD.get());
        tag(CORDAITES_LOGS).add(ModBlocks.CORDAITES_LOG.get(), ModBlocks.CORDAITES_WOOD.get(), ModBlocks.STRIPPED_CORDAITES_LOG.get(), ModBlocks.STRIPPED_CORDAITES_WOOD.get());
        tag(PALM_LOGS).add(ModBlocks.PALM_LOG.get(), ModBlocks.PALM_WOOD.get(), ModBlocks.STRIPPED_PALM_LOG.get(), ModBlocks.STRIPPED_PALM_WOOD.get());
        tag(SIGILLARIA_LOGS).add(ModBlocks.SIGILLARIA_LOG.get(), ModBlocks.SIGILLARIA_WOOD.get(), ModBlocks.STRIPPED_SIGILLARIA_LOG.get(), ModBlocks.STRIPPED_SIGILLARIA_WOOD.get());
        tag(TEMPSKYA_LOGS).add(ModBlocks.TEMPSKYA_LOG.get(), ModBlocks.TEMPSKYA_WOOD.get(), ModBlocks.STRIPPED_TEMPSKYA_LOG.get(), ModBlocks.STRIPPED_TEMPSKYA_WOOD.get());
        tag(BlockTags.LOGS_THAT_BURN).addTags(CALAMITES_LOGS, CORDAITES_LOGS, PALM_LOGS, SIGILLARIA_LOGS, TEMPSKYA_LOGS);
        tag(BlockTags.PLANKS).add(ModBlocks.CALAMITES_PLANKS.get(), ModBlocks.CORDAITES_PLANKS.get(), ModBlocks.PALM_PLANKS.get(), ModBlocks.SIGILLARIA_PLANKS.get(), ModBlocks.TEMPSKYA_PLANKS.get());
        tag(BlockTags.WOODEN_BUTTONS).add(ModBlocks.CALAMITES_BUTTON.get(), ModBlocks.CORDAITES_BUTTON.get(), ModBlocks.PALM_BUTTON.get(), ModBlocks.SIGILLARIA_BUTTON.get(), ModBlocks.TEMPSKYA_BUTTON.get());
        tag(BlockTags.WOODEN_DOORS).add(ModBlocks.CALAMITES_DOOR.get(), ModBlocks.CORDAITES_DOOR.get(), ModBlocks.PALM_DOOR.get(), ModBlocks.SIGILLARIA_DOOR.get(), ModBlocks.TEMPSKYA_DOOR.get());
        tag(BlockTags.WOODEN_STAIRS).add(ModBlocks.CALAMITES_STAIRS.get(), ModBlocks.CORDAITES_STAIRS.get(), ModBlocks.PALM_STAIRS.get(), ModBlocks.SIGILLARIA_STAIRS.get(), ModBlocks.TEMPSKYA_STAIRS.get());
        tag(BlockTags.WOODEN_SLABS).add(ModBlocks.CALAMITES_SLAB.get(), ModBlocks.CORDAITES_SLAB.get(), ModBlocks.PALM_SLAB.get(), ModBlocks.SIGILLARIA_SLAB.get(), ModBlocks.TEMPSKYA_SLAB.get());
        tag(BlockTags.WOODEN_FENCES).add(ModBlocks.CALAMITES_FENCE.get(), ModBlocks.CORDAITES_FENCE.get(), ModBlocks.PALM_FENCE.get(), ModBlocks.SIGILLARIA_FENCE.get(), ModBlocks.TEMPSKYA_FENCE.get());
        tag(BlockTags.FENCE_GATES).add(ModBlocks.CALAMITES_FENCE_GATE.get(), ModBlocks.CORDAITES_FENCE_GATE.get(), ModBlocks.PALM_FENCE_GATE.get(), ModBlocks.SIGILLARIA_FENCE_GATE.get(), ModBlocks.TEMPSKYA_FENCE_GATE.get());
        tag(BlockTags.WOODEN_PRESSURE_PLATES).add(ModBlocks.CALAMITES_PRESSURE_PLATE.get(), ModBlocks.CORDAITES_PRESSURE_PLATE.get(), ModBlocks.PALM_PRESSURE_PLATE.get(), ModBlocks.SIGILLARIA_PRESSURE_PLATE.get(), ModBlocks.TEMPSKYA_PRESSURE_PLATE.get());
        tag(BlockTags.WOODEN_TRAPDOORS).add(ModBlocks.CALAMITES_TRAPDOOR.get(), ModBlocks.CORDAITES_TRAPDOOR.get(), ModBlocks.PALM_TRAPDOOR.get(), ModBlocks.SIGILLARIA_TRAPDOOR.get(), ModBlocks.TEMPSKYA_TRAPDOOR.get());
        tag(BlockTags.SAPLINGS).add(ModBlocks.CALAMITES_SAPLING.get(), ModBlocks.CORDAITES_SAPLING.get(), ModBlocks.PALM_SAPLING.get(), ModBlocks.SIGILLARIA_SAPLING.get(), ModBlocks.TEMPSKYA_SAPLING.get());
        tag(BlockTags.WALLS).add(ModBlocks.ANCIENT_STONE_WALL.get(), ModBlocks.VOLCANIC_BRICK_WALL.get(), ModBlocks.VOLCANIC_TILE_WALL.get());
        //TODO: Small flowers?
        tag(BlockTags.LEAVES).add(ModBlocks.CALAMITES_LEAVES.get(), ModBlocks.CORDAITES_LEAVES.get(), ModBlocks.PALM_LEAVES.get(), ModBlocks.SIGILLARIA_LEAVES.get());
        tag(BlockTags.PORTALS).add(ModBlocks.ANU_PORTAL.get(), ModBlocks.HOME_PORTAL.get());
        tag(BlockTags.SOUL_SPEED_BLOCKS).add(ModBlocks.TARRED_DIRT.get());
        tag(BlockTags.MINEABLE_WITH_AXE).add(ModBlocks.DRUM.get(), ModBlocks.SIFTER.get(), ModBlocks.WORKTABLE.get());
        tag(BlockTags.MINEABLE_WITH_PICKAXE).add(ModBlocks.ANCIENT_GLASS.get(), ModBlocks.REINFORCED_GLASS.get(), ModBlocks.FAKE_OBSIDIAN.get(), ModBlocks.CULTIVATE.get(), ModBlocks.ANALYZER.get(), ModBlocks.FOSSIL.get(), ModBlocks.ICED_STONE.get(), ModBlocks.SKULL_BLOCK.get(), ModBlocks.SKULL_LANTERN.get(), ModBlocks.BUBBLE_BLOWER.get(),
                ModBlocks.ANCIENT_STONE.get(), ModBlocks.ANCIENT_STONE_BRICKS.get(), ModBlocks.ANCIENT_STONE_SLAB.get(), ModBlocks.ANCIENT_STONE_STAIRS.get(), ModBlocks.ANCIENT_STONE_WALL.get(),
                ModBlocks.VOLCANIC_ROCK.get(), ModBlocks.VOLCANIC_BRICKS.get(), ModBlocks.VOLCANIC_BRICK_SLAB.get(), ModBlocks.VOLCANIC_BRICK_STAIRS.get(), ModBlocks.VOLCANIC_BRICK_WALL.get(),
                ModBlocks.VOLCANIC_TILES.get(), ModBlocks.VOLCANIC_TILE_SLAB.get(), ModBlocks.VOLCANIC_TILE_STAIRS.get(), ModBlocks.VOLCANIC_TILE_WALL.get());
        tag(BlockTags.MINEABLE_WITH_SHOVEL).add(ModBlocks.DENSE_SAND.get(), ModBlocks.VOLCANIC_ASH.get(), ModBlocks.TARRED_DIRT.get(), ModBlocks.SLIME_TRAIL.get());
        tag(BlockTags.NEEDS_DIAMOND_TOOL).add(ModBlocks.FAKE_OBSIDIAN.get(), ModBlocks.OBSIDIAN_SPIKES.get());//Time Machine
        tag(BlockTags.NEEDS_IRON_TOOL).add(ModBlocks.FOSSIL.get(), ModBlocks.PERMAFROST_BLOCK.get());
        tag(BlockTags.NEEDS_STONE_TOOL).add(ModBlocks.ANCIENT_GLASS.get(), ModBlocks.REINFORCED_GLASS.get(), ModBlocks.ICED_STONE.get());
    }

    @Override
    public @NotNull String getName() {
        return "Fossil Block Tags";
    }
}
