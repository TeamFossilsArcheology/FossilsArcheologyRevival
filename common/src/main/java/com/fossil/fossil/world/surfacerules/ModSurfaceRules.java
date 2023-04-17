package com.fossil.fossil.world.surfacerules;

import com.fossil.fossil.block.ModBlocks;
import com.fossil.fossil.world.biome.ModBiomes;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;

public class ModSurfaceRules {

    public static final SurfaceRules.RuleSource STONE_RULE = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.noiseCondition(
                    Noises.GRAVEL, -0.05, 0.05), SurfaceRules.state(Blocks.ANDESITE.defaultBlockState())),
            SurfaceRules.state(Blocks.STONE.defaultBlockState()));
    public static final SurfaceRules.RuleSource VOLCANIC_SURFACE_RULE = SurfaceRules.sequence(
            SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.VOLCANO_KEY),
                    SurfaceRules.sequence(
                            SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, SurfaceRules.state(ModBlocks.VOLCANIC_ROCK.get().defaultBlockState())),
                            SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.SURFACE, -1 / 8.25, Double.MAX_VALUE),
                                    SurfaceRules.state(ModBlocks.VOLCANIC_ROCK.get().defaultBlockState())),
                            SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, STONE_RULE),
                            SurfaceRules.ifTrue(SurfaceRules.UNDER_CEILING, STONE_RULE),
                            SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, SurfaceRules.state(ModBlocks.VOLCANIC_ROCK.get().defaultBlockState())))));
}
