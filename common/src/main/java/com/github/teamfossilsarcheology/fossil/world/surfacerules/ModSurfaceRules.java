package com.github.teamfossilsarcheology.fossil.world.surfacerules;

import com.github.teamfossilsarcheology.fossil.block.ModBlocks;
import com.github.teamfossilsarcheology.fossil.world.biome.ModBiomes;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.levelgen.Noises;
import net.minecraft.world.level.levelgen.SurfaceRules;
import net.minecraft.world.level.levelgen.VerticalAnchor;

public class ModSurfaceRules {
    private static SurfaceRules.RuleSource makeStateRule(Block block) {
        return SurfaceRules.state(block.defaultBlockState());
    }

    public static final SurfaceRules.RuleSource STONE_RULE = SurfaceRules.sequence(SurfaceRules.ifTrue(SurfaceRules.noiseCondition(
                    Noises.GRAVEL, -0.05, 0.05), SurfaceRules.state(Blocks.ANDESITE.defaultBlockState())),
            SurfaceRules.state(Blocks.STONE.defaultBlockState()));

    public static final SurfaceRules.RuleSource VOLCANIC_SURFACE_RULE = volcanoBiome();

    public static SurfaceRules.RuleSource volcanoBiome() {
        SurfaceRules.RuleSource rock = makeStateRule(ModBlocks.VOLCANIC_ROCK.get());
        SurfaceRules.RuleSource rules = SurfaceRules.sequence(
                SurfaceRules.ifTrue(SurfaceRules.verticalGradient("bedrock_floor", VerticalAnchor.bottom(), VerticalAnchor.aboveBottom(5)), makeStateRule(Blocks.BEDROCK)),
                SurfaceRules.ifTrue(SurfaceRules.ON_FLOOR, rock),
                SurfaceRules.ifTrue(SurfaceRules.noiseCondition(Noises.SURFACE, -1 / 8.25, Double.MAX_VALUE), rock),
                SurfaceRules.ifTrue(SurfaceRules.ON_CEILING, STONE_RULE),
                SurfaceRules.ifTrue(SurfaceRules.UNDER_CEILING, STONE_RULE),
                SurfaceRules.ifTrue(SurfaceRules.UNDER_FLOOR, rock),
                SurfaceRules.ifTrue(SurfaceRules.verticalGradient("deepslate", VerticalAnchor.absolute(0), VerticalAnchor.absolute(8)), makeStateRule(Blocks.DEEPSLATE)));

        SurfaceRules.RuleSource all = SurfaceRules.ifTrue(SurfaceRules.isBiome(ModBiomes.VOLCANO_KEY), rules);
        return SurfaceRules.sequence(all);
    }
}
