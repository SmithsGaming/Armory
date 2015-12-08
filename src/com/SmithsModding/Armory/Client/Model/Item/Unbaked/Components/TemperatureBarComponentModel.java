package com.SmithsModding.Armory.Client.Model.Item.Unbaked.Components;

import com.SmithsModding.SmithsCore.Util.Client.ModelHelper;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IFlexibleBakedModel;
import net.minecraftforge.client.model.IModelState;
import net.minecraftforge.client.model.ItemLayerModel;

import java.util.ArrayList;

/**
 * Created by Marc on 08.12.2015.
 */
public class TemperatureBarComponentModel extends ItemLayerModel {

    /**
     * Creates a new unbaked model, given the parameters list of possible textures.
     *
     * @param textures The possible textures for the unbaked Model.
     */
    public TemperatureBarComponentModel (ImmutableList<ResourceLocation> textures) {
        super(textures);
    }

    /**
     * Function get the baked end model.
     *
     * @param state              The modelstate you want a model for.
     * @param format             The format the vertexes are stored in.
     * @param bakedTextureGetter Function to get the Texture for the Model.
     * @return A ItemStack depending model that is ready to be used.
     */
    @Override
    public IFlexibleBakedModel bake (IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        return generateBackedComponentModel(state, format, bakedTextureGetter);
    }

    /**
     * Function to get the grayscale texture location of this Model faster.
     *
     * @return The location of the grayscale texture.
     */
    public ResourceLocation getTexture () {
        ArrayList<ResourceLocation> textures = new ArrayList<ResourceLocation>();
        textures.addAll(getTextures());

        if (textures.size() == 0)
            return null;

        return textures.get(0);
    }

    @Override
    public IModelState getDefaultState () {
        return ModelHelper.DEFAULT_ITEM_STATE;
    }

    /**
     * Function to get a baked model from outside of the baking proces.
     *
     * @param state              The model state to retrieve a model for.
     * @param format             The format of storing the individual vertexes in memory
     * @param bakedTextureGetter Function to get the baked textures.
     * @return A baked model containing all individual possible textures this model can have.
     */
    public IFlexibleBakedModel generateBackedComponentModel (IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        // Get ourselfs a normal model to use.
        return super.bake(state, format, bakedTextureGetter);
    }

}
