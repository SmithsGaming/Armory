package com.smithsmodding.armory.client.model.item.unbaked.components;

import com.google.common.base.*;
import com.google.common.collect.*;
import com.smithsmodding.armory.api.materials.*;
import com.smithsmodding.armory.client.model.item.baked.components.*;
import com.smithsmodding.armory.client.textures.*;
import com.smithsmodding.armory.common.material.*;
import com.smithsmodding.smithscore.util.client.*;
import com.smithsmodding.smithscore.util.client.color.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.util.*;
import net.minecraftforge.client.model.*;

import java.util.*;

/**
 * Created by Marc on 06.12.2015.
 * <p/>
 * model used to display singular components of the armor.
 * Is in implementation nearly the same as the TinkersConstruct Toolparts.
 */
public class ArmorComponentModel extends ItemLayerModel implements IModelPart {

    /**
     * Creates a new unbaked model, given the parameters list of possible textures.
     *
     * @param textures The possible textures for the unbaked model.
     */
    public ArmorComponentModel (ImmutableList<ResourceLocation> textures) {
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
    @Override
    public IFlexibleBakedModel bake (IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        return generateBackedComponentModel(state, format, bakedTextureGetter);
    }

    /**
     * Function to get the grayscale texture location of this model faster.
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
    public BakedComponentModel generateBackedComponentModel (IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        // Get ourselfs a normal model to use.
        IFlexibleBakedModel base = super.bake(state, format, bakedTextureGetter);

        // Use it as our base for the BakedComponentModel.
        BakedComponentModel bakedMaterialModel = new BakedComponentModel(base);

        //In between the loading of the model from the JSON and the baking the MaterializedTextureCreator was able to
        // generate all the necessary textures for the models.
        //We retrieve those now and register them to the BakedModel later.
        String baseTexture = base.getParticleTexture().getIconName();
        Map<String, TextureAtlasSprite> sprites = MaterializedTextureCreator.getBuildSprites().get(baseTexture);

        //Construct individual models for each of the sprites.
        for (Map.Entry<String, TextureAtlasSprite> entry : sprites.entrySet()) {
            //We grab the material now, that way we know the material exists before continuing.
            IArmorMaterial material = MaterialRegistry.getInstance().getMaterial(entry.getKey());

            //We retexture this model with the newly colored textured from ther creator and get a Copy of this model
            //But then colored instead of grayscaled.
            IModel model2 = this.retexture(ImmutableMap.of("layer0", entry.getValue().getIconName()));

            //We bake the new model to get a ready to use textured and ready to be colored baked model.
            IFlexibleBakedModel bakedModel2 = model2.bake(state, format, bakedTextureGetter);

            // We check if a special texture for that item exists in our textures collection.
            // If not we check if the material needs coloring and color the vertexes individually.
            if (material.getRenderInfo().useVertexColoring() && !ResourceHelper.exists(baseTexture + "-" + material.getUniqueID())) {
                //We get the color for the material.
                MinecraftColor color = (material.getRenderInfo()).getVertexColor();

                //We create a new list of Quads.
                ImmutableList.Builder<BakedQuad> quads = ImmutableList.builder();

                //We color all the quads in the GeneralQuads (As the ItemLayeredModel.BakedModel only uses the General Quads)
                for (BakedQuad quad : bakedModel2.getGeneralQuads()) {
                    quads.add(ModelHelper.colorQuad(color, quad));
                }


                    bakedModel2 = new ItemLayerModel.BakedModel(quads.build(), bakedModel2.getParticleTexture(), bakedModel2.getFormat());

            }

            bakedMaterialModel.addMaterialModel(material, bakedModel2);
        }

        //And we are done, we have a ready to use, baked, textured and colored model.
        return bakedMaterialModel;
    }

}
