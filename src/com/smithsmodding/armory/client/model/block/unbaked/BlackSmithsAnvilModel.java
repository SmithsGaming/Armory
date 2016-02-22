package com.smithsmodding.armory.client.model.block.unbaked;

import com.google.common.base.*;
import com.smithsmodding.armory.client.model.block.baked.*;
import net.minecraft.block.state.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.client.resources.model.*;
import net.minecraft.util.*;
import net.minecraftforge.client.model.*;
import net.minecraftforge.client.model.obj.*;

import java.util.*;

/**
 * Created by Marc on 22.02.2016.
 */
public class BlackSmithsAnvilModel implements IModel {

    OBJModel original;
    HashMap<String, OBJModel> unbakedOBJModels = new HashMap<>();

    public BlackSmithsAnvilModel(OBJModel original)
    {
        this.original = original;
    }

    public void registerNewMaterializedModel(OBJModel model, String materialID)
    {
        unbakedOBJModels.put(materialID, model);
    }

    @Override
    public Collection<ResourceLocation> getDependencies () {
        return original.getDependencies();
    }

    @Override
    public Collection<ResourceLocation> getTextures () {
        ArrayList<ResourceLocation> resourceLocations = new ArrayList<>();

        for (OBJModel model : unbakedOBJModels.values())
        {
            resourceLocations.addAll(model.getTextures());
        }

        return resourceLocations;
    }

    @Override
    public IFlexibleBakedModel bake (IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        BlackSmithsAnvilBakedModel bakedModel = new BlackSmithsAnvilBakedModel(original.bake(state, format, bakedTextureGetter));

        for(Map.Entry<String, OBJModel> modelEntry : unbakedOBJModels.entrySet())
        {
            bakedModel.registerBakedModel(modelEntry.getValue().bake(state, format, bakedTextureGetter), modelEntry.getKey());
        }

        return bakedModel;
    }

    @Override
    public IModelState getDefaultState () {
        return original.getDefaultState();
    }
}
