package com.smithsmodding.armory.client.model.block.unbaked;

import com.google.common.base.Function;
import com.smithsmodding.armory.client.model.block.baked.BlackSmithsAnvilBakedModel;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by Marc on 22.02.2016.
 */
public class BlackSmithsAnvilModel implements IModel {

    IModel original;
    @NotNull HashMap<String, IModel> unbakedOBJModels = new HashMap<>();

    public BlackSmithsAnvilModel(IModel original) {
        this.original = original;
    }

    public void registerNewMaterializedModel(IModel model, String materialID) {
        unbakedOBJModels.put(materialID, model);
    }

    @Override
    public Collection<ResourceLocation> getDependencies() {
        return original.getDependencies();
    }

    @NotNull
    @Override
    public Collection<ResourceLocation> getTextures() {
        ArrayList<ResourceLocation> resourceLocations = new ArrayList<>();

        for (IModel model : unbakedOBJModels.values()) {
            resourceLocations.addAll(model.getTextures());
        }

        return resourceLocations;
    }

    @NotNull
    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        BlackSmithsAnvilBakedModel bakedModel = new BlackSmithsAnvilBakedModel(original.bake(state, format, bakedTextureGetter));

        for (Map.Entry<String, IModel> modelEntry : unbakedOBJModels.entrySet()) {
            IBakedModel bakedCustomModel = modelEntry.getValue().bake(state, format, bakedTextureGetter);
            bakedModel.registerBakedModel(bakedCustomModel, modelEntry.getKey());
        }

        return bakedModel;
    }

    @Override
    public IModelState getDefaultState() {
        return original.getDefaultState();
    }
}
