package com.smithsmodding.armory.client.model.item.unbaked.components;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.smithsmodding.armory.client.model.item.baked.components.BakedTemperatureBarModel;
import com.smithsmodding.smithscore.client.model.unbaked.ItemLayerModel;
import com.smithsmodding.smithscore.util.client.ModelHelper;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.common.model.IModelPart;
import net.minecraftforge.common.model.IModelState;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;

/**
 * Created by Marc on 08.12.2015.
 */
public class TemperatureBarComponentModel extends ItemLayerModel implements IModelPart {

    /**
     * Creates a new unbaked model, given the parameters list of possible textures.
     *
     * @param textures The possible textures for the unbaked model.
     */
    public TemperatureBarComponentModel(ImmutableList<ResourceLocation> textures) {
        super(textures);
    }

    /**
     * Function get the baked end model.
     *
     * @param state              The modelstate you want a model for.
     * @param format             The format the vertexes are stored in.
     * @param bakedTextureGetter Function to get the Texture for the model.
     * @return A ItemStack depending model that is ready to be used.
     */
    @NotNull
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
    @NotNull
    public BakedTemperatureBarModel generateBackedComponentModel(@NotNull IModelState state, VertexFormat format, @NotNull Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        // Get ourselfs a normal model to use.
        IBakedModel base = super.bake(state, DefaultVertexFormats.ITEM, bakedTextureGetter);

        // Use it as our base for the BakedComponentModel.
        BakedTemperatureBarModel bakedTemperatureBar = new BakedTemperatureBarModel(base);

        //Construct individual models for each of the sprites.
        for (ResourceLocation textureLocation : this.getTextures()) {
            TextureAtlasSprite sprite = bakedTextureGetter.apply(textureLocation);

            //We retexture this model with the newly colored textured from ther creator and get a Copy of this model
            IModel model2 = ItemLayerModel.INSTANCE.retexture(ImmutableMap.of("layer0", sprite.getIconName()));

            //We bake the new model to get a ready to use textured and ready to be colored baked model.
            IBakedModel bakedModel2 = model2.bake(state, DefaultVertexFormats.ITEM, bakedTextureGetter);

            //Set normals to ignore lighting.
            ModelHelper.setNormalsToIgnoreLightingOnItemModel(bakedModel2);

            //Add the model
            bakedTemperatureBar.addTexture(bakedModel2);
        }

        //And we are done, we have a ready to use, baked, textured and colored model.
        return bakedTemperatureBar;
    }

}
