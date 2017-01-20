package com.smithsmodding.armory.client.model.item.unbaked.components;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.smithsmodding.armory.client.model.item.baked.components.BakedCoreComponentModel;
import com.smithsmodding.armory.client.model.item.baked.components.BakedSubComponentModel;
import com.smithsmodding.smithscore.client.model.unbaked.ItemLayerModel;
import com.smithsmodding.smithscore.util.client.ModelHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import org.jetbrains.annotations.Nullable;

import javax.annotation.Nonnull;
import java.util.ArrayList;

/**
 * Created by marcf on 1/6/2017.
 */
public class ArmorSubComponentModel extends ItemLayerModel implements IModel {
    protected final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms;

    public ArmorSubComponentModel(ImmutableList<ResourceLocation> textures) {
        this(textures, ModelHelper.DEFAULT_ITEM_TRANSFORMS);
    }

    public ArmorSubComponentModel(ImmutableList<ResourceLocation> textures, ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms) {
        super(textures);
        this.transforms = transforms;
    }

    /**
     * Function get the baked end model.
     *
     * @param state              The modelstate you want a model for.
     * @param format             The format the vertexes are stored in.
     * @param bakedTextureGetter Function to get the Texture for the model.
     * @return A ItemStack depending model that is ready to be used.
     */
    @Nonnull
    @Override
    public IBakedModel bake(IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        return generateBackedComponentModel(state, format, bakedTextureGetter);
    }

    /**
     * Function to get the grayscale texture location of this model faster.
     *
     * @return The location of the grayscale texture.
     */
    @Nullable
    public ResourceLocation getTexture() {
        ArrayList<ResourceLocation> textures = new ArrayList<ResourceLocation>();
        textures.addAll(getTextures());

        if (textures.size() == 0)
            return null;

        return textures.get(0);
    }

    /**
     * Function to get a baked model from outside of the baking proces.
     *
     * @param state              The model state to retrieve a model for.
     * @param format             The format of storing the individual vertexes in memory
     * @param bakedTextureGetter Function to get the baked textures.
     * @return A baked model containing all individual possible textures this model can have.
     */
    @Nonnull
    public BakedSubComponentModel generateBackedComponentModel(@Nonnull IModelState state, VertexFormat format, @Nonnull Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        return new BakedSubComponentModel(super.bake(state, format, bakedTextureGetter), transforms);
    }
}
