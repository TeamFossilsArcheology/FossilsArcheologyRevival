package com.github.teamfossilsarcheology.fossil.fabric.client.model;

import com.github.teamfossilsarcheology.fossil.client.model.block.PlantBlockModel;
import com.github.teamfossilsarcheology.fossil.client.model.block.PlantBlockModel.PlantBlockElement;
import com.github.teamfossilsarcheology.fossil.client.model.block.PlantBlockModel.PlantBlockElementFace;
import com.github.teamfossilsarcheology.fossil.client.model.block.PlantModelBakery;
import net.fabricmc.fabric.api.renderer.v1.Renderer;
import net.fabricmc.fabric.api.renderer.v1.RendererAccess;
import net.fabricmc.fabric.api.renderer.v1.mesh.MeshBuilder;
import net.fabricmc.fabric.api.renderer.v1.mesh.QuadEmitter;
import net.fabricmc.fabric.impl.client.indigo.renderer.IndigoRenderer;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.Collection;
import java.util.Collections;
import java.util.function.Function;

public class FabricPlantUnbakedModel implements UnbakedModel {
    @Nullable
    public static final Renderer RENDERER = RendererAccess.INSTANCE.getRenderer();

    private final PlantBlockModel model;

    public FabricPlantUnbakedModel(PlantBlockModel model) {
        this.model = model;
    }

    @Override
    public @NotNull Collection<ResourceLocation> getDependencies() {
        return Collections.emptyList();
    }

    @Override
    public void resolveParents(@NotNull Function<ResourceLocation, UnbakedModel> modelGetter) {

    }

    @Override
    public @Nullable BakedModel bake(ModelBaker modelBaker, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, ResourceLocation location) {
        Renderer renderer = RENDERER == null ? IndigoRenderer.INSTANCE : RENDERER;
        MeshBuilder builder = renderer.meshBuilder();
        QuadEmitter emitter = builder.getEmitter();

        for (PlantBlockElement element : model.elements()) {
            for (Direction direction : element.faces().keySet()) {
                PlantBlockElementFace face = element.faces().get(direction);

                TextureAtlasSprite texture = spriteGetter.apply(model.materials().get(face.texture().substring(1)));
                BakedQuad bakedQuad = PlantModelBakery.bakeFace(element, face, texture, direction, modelState);
                //I'm sure that this isn't the best, but it's really easy
                emitter.fromVanilla(bakedQuad, renderer.materialFinder().find(), direction);
                emitter.cullFace(null);
                emitter.nominalFace(direction);
                emitter.emit();
            }
        }
        return new PlantBakedModel(builder.build(), model.materials());
    }
}
