package com.fossil.fossil.world.feature.structures;

import com.fossil.fossil.config.FossilConfig;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.levelgen.GenerationStep;
import net.minecraft.world.level.levelgen.feature.JigsawFeature;
import net.minecraft.world.level.levelgen.feature.configurations.JigsawConfiguration;
import net.minecraft.world.level.levelgen.structure.pieces.PieceGeneratorSupplier;
import net.minecraft.world.level.levelgen.structure.pools.StructureTemplatePool;
import org.jetbrains.annotations.NotNull;

import java.util.Optional;

/**
 * Custom {@link JigsawFeature} that checks if the structure is enabled in the config
 */
public class ConfigurableStructureFeature extends JigsawFeature {
    public ConfigurableStructureFeature() {
        super(JigsawConfiguration.CODEC, 0, true, true, ConfigurableStructureFeature::checkConfig);
    }

    private static boolean checkConfig(PieceGeneratorSupplier.Context<JigsawConfiguration> context) {
        Optional<ResourceKey<StructureTemplatePool>> key = context.config().startPool().unwrapKey();
        return key.isPresent() && FossilConfig.isStructurePoolEnabled(key.get().location());
    }

    @Override
    public GenerationStep.@NotNull Decoration step() {
        return GenerationStep.Decoration.SURFACE_STRUCTURES;
    }
}
