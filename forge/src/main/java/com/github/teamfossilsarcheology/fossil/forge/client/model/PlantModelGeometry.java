package com.github.teamfossilsarcheology.fossil.forge.client.model;

import com.github.teamfossilsarcheology.fossil.client.model.block.PlantBlockModel;
import com.github.teamfossilsarcheology.fossil.client.model.block.PlantBlockModel.PlantBlockElement;
import com.github.teamfossilsarcheology.fossil.client.model.block.PlantBlockModel.PlantBlockElementFace;
import com.github.teamfossilsarcheology.fossil.client.model.block.PlantModelBakery;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.BakedModel;
import net.minecraft.client.resources.model.Material;
import net.minecraft.client.resources.model.ModelBaker;
import net.minecraft.client.resources.model.ModelState;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.geometry.IGeometryBakingContext;
import net.minecraftforge.client.model.geometry.IUnbakedGeometry;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public class PlantModelGeometry implements IUnbakedGeometry<PlantModelGeometry> {

    private final PlantBlockModel model;

    public PlantModelGeometry(PlantBlockModel model) {
        this.model = model;
    }

    @Override
    public BakedModel bake(IGeometryBakingContext iGeometryBakingContext, ModelBaker arg, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, ItemOverrides arg3, ResourceLocation location) {
        List<BakedQuad> list = new ArrayList<>();
        for (PlantBlockElement element : model.elements()) {
            for (Direction direction : element.faces().keySet()) {
                PlantBlockElementFace face = element.faces().get(direction);
                //Resolve texture references
                TextureAtlasSprite texture = spriteGetter.apply(model.materials().get(face.texture().substring(1)));
                list.add(PlantModelBakery.bakeFace(element, face, texture, direction, modelState));
            }
        }
        return new PlantBakedModel(list, model.materials());
    }
}
