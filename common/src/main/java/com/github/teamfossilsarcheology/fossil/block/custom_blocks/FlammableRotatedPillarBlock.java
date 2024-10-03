package com.github.teamfossilsarcheology.fossil.block.custom_blocks;

import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.RotatedPillarBlock;
import net.minecraft.world.level.block.state.BlockBehaviour;

import static com.github.teamfossilsarcheology.fossil.block.ModBlocks.*;

public class FlammableRotatedPillarBlock {
    @ExpectPlatform
    public static void registerStripped(Block base, Block stripped) {
        throw new AssertionError();
    }

    @ExpectPlatform
    public static RotatedPillarBlock get(BlockBehaviour.Properties properties) {
        throw new AssertionError();
    }

    public static void registerAllStripped() {
        STRIPPED_CALAMITES_LOG.listen(stripped -> CALAMITES_LOG.listen(base -> registerStripped(base, stripped)));
        STRIPPED_CORDAITES_LOG.listen(stripped -> CORDAITES_LOG.listen(base -> registerStripped(base, stripped)));
        STRIPPED_PALM_LOG.listen(stripped -> PALM_LOG.listen(base -> registerStripped(base, stripped)));
        STRIPPED_SIGILLARIA_LOG.listen(stripped -> SIGILLARIA_LOG.listen(base -> registerStripped(base, stripped)));
        STRIPPED_TEMPSKYA_LOG.listen(stripped -> TEMPSKYA_LOG.listen(base -> registerStripped(base, stripped)));
        STRIPPED_MUTANT_TREE_LOG.listen(stripped -> MUTANT_TREE_LOG.listen(base -> registerStripped(base, stripped)));
        STRIPPED_CALAMITES_WOOD.listen(stripped -> CALAMITES_WOOD.listen(base -> registerStripped(base, stripped)));
        STRIPPED_CORDAITES_WOOD.listen(stripped -> CORDAITES_WOOD.listen(base -> registerStripped(base, stripped)));
        STRIPPED_PALM_WOOD.listen(stripped -> PALM_WOOD.listen(base -> registerStripped(base, stripped)));
        STRIPPED_SIGILLARIA_WOOD.listen(stripped -> SIGILLARIA_WOOD.listen(base -> registerStripped(base, stripped)));
        STRIPPED_TEMPSKYA_WOOD.listen(stripped -> TEMPSKYA_WOOD.listen(base -> registerStripped(base, stripped)));
        STRIPPED_MUTANT_TREE_WOOD.listen(stripped -> MUTANT_TREE_WOOD.listen(base -> registerStripped(base, stripped)));
    }
}