package com.SmithsModding.Armory.Client.Model.Item;

import com.SmithsModding.Armory.API.Materials.IArmorMaterial;
import com.SmithsModding.Armory.Common.Material.MaterialRegistry;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import gnu.trove.map.hash.THashMap;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.client.resources.model.ModelRotation;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.*;

import javax.vecmath.Vector3f;
import java.util.Collection;
import java.util.Map;

/**
 * Created by Marc on 06.12.2015.
 */
public class ComponentModel implements IModel {

    private Map<String, String> models = new THashMap<String, String>();

    public ComponentModel () {
    }

    public void addModelForModifier (String modifier, String texture) {
        models.put(modifier, texture);
    }

    public String getTextureForModifier (String modifier) {
        if (!models.containsKey(modifier)) {
            return null;
        }

        return models.get(modifier);
    }

    @Override
    public Collection<ResourceLocation> getDependencies () {
        return ImmutableList.of(); // none
    }

    @Override
    public Collection<ResourceLocation> getTextures () {
        ImmutableSet.Builder<ResourceLocation> builder = ImmutableSet.builder();

        for (String texture : models.values()) {
            builder.add(new ResourceLocation(texture));
        }

        return builder.build();
    }

    @Override
    public IFlexibleBakedModel bake (IModelState state, VertexFormat format,
                                     Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        throw new UnsupportedOperationException("The modifier-Model is not built to be used as an item model");
    }

    public Map<String, IFlexibleBakedModel> bakeModels (IModelState state, VertexFormat format,
                                                        Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        Map<String, IFlexibleBakedModel> bakedModels = new THashMap<String, IFlexibleBakedModel>();

        // we scale the modifier up slightly so it's always above the tool
        float s = 0.025f;
        ITransformation
                transformation =
                new TRSRTransformation(new Vector3f(0, 0, 0.0001f - s / 2f), null, new Vector3f(1, 1, 1f + s), null);

        for (Map.Entry<String, String> entry : models.entrySet()) {
            // todo: turn this into an event?
            ArmorComponentModel materialModel = new ArmorComponentModel(ImmutableList.of(new ResourceLocation(entry.getValue())));

            BakedComponentModel bakedModel = materialModel.generateBackedComponentModel(state, format, bakedTextureGetter);

            for (IArmorMaterial material : MaterialRegistry.getInstance().getArmorMaterials().values()) {
                IFlexibleBakedModel materialBakedModel = bakedModel.getModelByIdentifier(material.getInternalMaterialName());
                if (materialBakedModel != bakedModel) {
                    bakedModels.put(entry.getKey() + material.getInternalMaterialName(), materialBakedModel);
                }
            }
        }

        return bakedModels;
    }

    @Override
    public IModelState getDefaultState () {
        return ModelRotation.X0_Y0;
    }
}
