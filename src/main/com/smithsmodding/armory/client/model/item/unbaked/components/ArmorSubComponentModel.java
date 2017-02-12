package com.smithsmodding.armory.client.model.item.unbaked.components;

import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.smithsmodding.armory.api.materials.IArmorMaterial;
import com.smithsmodding.armory.client.model.item.baked.components.BakedSubComponentModel;
import com.smithsmodding.armory.client.textures.MaterializedTextureCreator;
import com.smithsmodding.armory.common.registry.MaterialRegistry;
import com.smithsmodding.smithscore.client.model.unbaked.ItemLayerModel;
import com.smithsmodding.smithscore.util.client.ModelHelper;
import com.smithsmodding.smithscore.util.client.ResourceHelper;
import com.smithsmodding.smithscore.util.client.color.MinecraftColor;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.IModel;
import net.minecraftforge.client.model.IPerspectiveAwareModel;
import net.minecraftforge.common.model.IModelState;
import net.minecraftforge.common.model.TRSRTransformation;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Marc on 06.12.2015.
 * <p>
 * model used to display singular components of the armor.
 * Is in implementation nearly the same as the TinkersConstruct Toolparts.
 */
public class ArmorSubComponentModel extends ItemLayerModel implements IModel {

    private final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms;

    /**
     * Creates a new unbaked model, given the parameters list of possible textures.
     *
     * @param textures The possible textures for the unbaked model.
     */
    public ArmorSubComponentModel(ImmutableList<ResourceLocation> textures) {
        super(textures);
        transforms = ModelHelper.DEFAULT_ITEM_TRANSFORMS;
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
    public BakedSubComponentModel generateBackedComponentModel(@NotNull IModelState state, VertexFormat format, @NotNull Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        // Get ourselfs a normal model to use.
        IBakedModel base = super.bake(state, format, bakedTextureGetter);

        // Use it as our base for the BakedComponentModel.
        BakedSubComponentModel bakedMaterialModel = new BakedSubComponentModel(base);

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
            IBakedModel bakedModel2 = model2.bake(state, format, bakedTextureGetter);

            // We check if a special texture for that item exists in our textures collection.
            // If not we check if the material needs coloring and color the vertexes individually.
            if (material.getRenderInfo().useVertexColoring() && !ResourceHelper.exists(baseTexture + "-" + material.getUniqueID())) {
                //We get the color for the material.
                MinecraftColor color = (material.getRenderInfo()).getVertexColor();

                //We create a new list of Quads.
                ImmutableList.Builder<BakedQuad> quads = ImmutableList.builder();

                //We color all the quads in the GeneralQuads (As the ItemLayeredModel.BakedModel only uses the General Quads)
                for (BakedQuad quad : bakedModel2.getQuads(null, null, 0)) {
                    quads.add(ModelHelper.colorQuad(color, quad));
                }


                bakedModel2 = new ItemLayerModel.BakedItemModel(quads.build(), bakedModel2.getParticleTexture(), IPerspectiveAwareModel.MapWrapper.getTransforms(state), bakedModel2.getOverrides(), null);

            }

            bakedMaterialModel.addMaterialModel(material, bakedModel2);
        }

        //And we are done, we have a ready to use, baked, textured and colored model.
        return bakedMaterialModel;
    }

}
