package com.github.teamfossilsarcheology.fossil.world.feature;

import com.github.teamfossilsarcheology.fossil.FossilMod;
import com.github.teamfossilsarcheology.fossil.world.feature.configuration.AshDiskConfiguration;
import com.github.teamfossilsarcheology.fossil.world.feature.tree.*;
import dev.architectury.injectables.annotations.ExpectPlatform;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.levelgen.feature.Feature;
import net.minecraft.world.level.levelgen.feature.configurations.FeatureConfiguration;
import net.minecraft.world.level.levelgen.feature.configurations.NoneFeatureConfiguration;

public class ModFeatures {
    public static final Tuple<AshDiskConfiguration, AshDiskFeature> ASH_DISK = create("ask_disk", new AshDiskFeature());
    public static final Tuple<NoneFeatureConfiguration, CalamitesTreeFeature> CALAMITES_TREE = create("calamites_tree", new CalamitesTreeFeature());
    public static final Tuple<NoneFeatureConfiguration, CordaitesTreeFeature> CORDAITES_TREE = create("cordaites_tree", new CordaitesTreeFeature());
    public static final Tuple<NoneFeatureConfiguration, MutantTreeFeature> MUTANT_TREE = create("mutant_tree", new MutantTreeFeature());
    public static final Tuple<NoneFeatureConfiguration, PalmTreeFeature> PALM_TREE = create("palm_tree", new PalmTreeFeature());
    public static final Tuple<NoneFeatureConfiguration, SigillariaTreeFeature> SIGILLARIA_TREE = create("sigillaria_tree", new SigillariaTreeFeature());
    public static final Tuple<NoneFeatureConfiguration, TempskyaTreeFeature> TEMPSKYA_TREE = create("tempskya_tree", new TempskyaTreeFeature());
    public static final Tuple<NoneFeatureConfiguration, MoaiStatueFeature> MOAI_STATUE = create("moai_statue", new MoaiStatueFeature());
    public static final Tuple<NoneFeatureConfiguration, VolcanoConeFeature> VOLCANO_CONE = create("volcano_cone", new VolcanoConeFeature());
    public static final Tuple<NoneFeatureConfiguration, VolcanoVentFeature> VOLCANO_VENT = create("volcano_vent", new VolcanoVentFeature());

    public static <C extends FeatureConfiguration, F extends Feature<C>> Tuple<C, F> create(String name, F feature) {
        return new Tuple<>(FossilMod.location(name), feature);
    }

    @ExpectPlatform
    public static void register() {
    }

    public record Tuple<C extends FeatureConfiguration, F extends Feature<C>>(ResourceLocation location, F feature) {

    }
}
