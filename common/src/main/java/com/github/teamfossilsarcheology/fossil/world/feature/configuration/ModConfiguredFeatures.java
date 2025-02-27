package com.github.teamfossilsarcheology.fossil.world.feature.configuration;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.world.feature.ModFeatures;
import com.github.teamfossilsarcheology.fossil.world.feature.ModOreFeatures;
import net.minecraft.core.registries.Registries;
import net.minecraft.data.worldgen.BootstapContext;
import net.minecraft.data.worldgen.features.FeatureUtils;
import net.minecraft.resources.ResourceKey;
import net.minecraft.util.valueproviders.UniformInt;
import net.minecraft.world.level.levelgen.feature.ConfiguredFeature;

/**
 * Calling this class before the mod blocks have been initialized will cause a crash at the moment
 */
public class ModConfiguredFeatures {
    public static final ResourceKey<ConfiguredFeature<?, ?>> CALAMITES_TREE_KEY = createKey("calamites_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> CORDAITES_TREE_KEY = createKey("cordaites_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MUTANT_TREE_KEY = createKey("mutant_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> PALM_TREE_KEY = createKey("palm_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> SIGILLARIA_TREE_KEY = createKey("sigillaria_tree");
    public static final ResourceKey<ConfiguredFeature<?, ?>> TEMPSKYA_TREE_KEY = createKey("tempskya_tree");

    public static final ResourceKey<ConfiguredFeature<?, ?>> MOAI_STATUE_KEY = createKey("moai_statue");
    public static final ResourceKey<ConfiguredFeature<?, ?>> ASH_DISK_KEY = createKey("ash_disk");
    public static final ResourceKey<ConfiguredFeature<?, ?>> MAGMA_DISK_KEY = createKey("magma_disk");
    public static final ResourceKey<ConfiguredFeature<?, ?>> VOLCANO_CONE_KEY = createKey("volcano_cone");
    public static final ResourceKey<ConfiguredFeature<?, ?>> VOLCANO_VENT_KEY = createKey("volcano_vent");

    private static ResourceKey<ConfiguredFeature<?, ?>> createKey(String name) {
        return ResourceKey.create(Registries.CONFIGURED_FEATURE, FossilMod.location(name));
    }

    public static void bootstrap(BootstapContext<ConfiguredFeature<?, ?>> context) {
        ModOreFeatures.bootstrap(context);
        FeatureUtils.register(context, CALAMITES_TREE_KEY, ModFeatures.CALAMITES_TREE.feature());
        FeatureUtils.register(context, CORDAITES_TREE_KEY, ModFeatures.CORDAITES_TREE.feature());
        FeatureUtils.register(context, MUTANT_TREE_KEY, ModFeatures.MUTANT_TREE.feature());
        FeatureUtils.register(context, PALM_TREE_KEY, ModFeatures.PALM_TREE.feature());
        FeatureUtils.register(context, SIGILLARIA_TREE_KEY, ModFeatures.SIGILLARIA_TREE.feature());
        FeatureUtils.register(context, TEMPSKYA_TREE_KEY, ModFeatures.TEMPSKYA_TREE.feature());

        FeatureUtils.register(context, MOAI_STATUE_KEY, ModFeatures.MOAI_STATUE.feature());
        FeatureUtils.register(context, ASH_DISK_KEY, ModFeatures.ASH_DISK.feature(),
                new AshDiskConfiguration(UniformInt.of(6, 11), false));
        FeatureUtils.register(context, MAGMA_DISK_KEY, ModFeatures.ASH_DISK.feature(),
                new AshDiskConfiguration(UniformInt.of(4, 6), true));
        FeatureUtils.register(context, VOLCANO_CONE_KEY, ModFeatures.VOLCANO_CONE.feature());
        FeatureUtils.register(context, VOLCANO_VENT_KEY, ModFeatures.VOLCANO_VENT.feature());
    }
}
