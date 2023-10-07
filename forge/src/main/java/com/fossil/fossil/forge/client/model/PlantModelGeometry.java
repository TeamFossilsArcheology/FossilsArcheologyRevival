package com.fossil.fossil.forge.client.model;

import com.fossil.fossil.client.model.block.PlantBlockModel;
import com.fossil.fossil.client.model.block.PlantBlockModel.PlantBlockElement;
import com.fossil.fossil.client.model.block.PlantBlockModel.PlantBlockElementFace;
import com.fossil.fossil.client.model.block.PlantModelBakery;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemOverrides;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.resources.model.*;
import net.minecraft.core.Direction;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.client.model.IModelConfiguration;
import net.minecraftforge.client.model.geometry.IModelGeometry;

import java.util.*;
import java.util.function.Function;

public class PlantModelGeometry implements IModelGeometry<PlantModelGeometry> {

    private final PlantBlockModel model;

    public PlantModelGeometry(PlantBlockModel model) {
        this.model = model;
    }

    @Override
    public BakedModel bake(IModelConfiguration iModelConfiguration, ModelBakery arg, Function<Material, TextureAtlasSprite> spriteGetter, ModelState modelState, ItemOverrides arg3, ResourceLocation location) {
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

    @Override
    public Collection<Material> getTextures(IModelConfiguration config, Function<ResourceLocation, UnbakedModel> function, Set<Pair<String, String>> set) {
        Set<Material> textures = Sets.newHashSet();
        for (PlantBlockElement element : model.elements()) {
            Material texture;
            for (Iterator<PlantBlockElementFace> var7 = element.faces().values().iterator(); var7.hasNext(); textures.add(texture)) {
                PlantBlockElementFace face = var7.next();
                texture = config.resolveTexture(face.texture());
            }
        }
        return textures;
    }
}
