package com.SmithsModding.Armory.Client.Model.Item;

import com.SmithsModding.Armory.API.Materials.IArmorMaterial;
import com.SmithsModding.Armory.Client.Textures.MaterializedTextureCreator;
import com.SmithsModding.Armory.Common.Material.MaterialRegistry;
import com.SmithsModding.SmithsCore.Util.Client.Color.MinecraftColor;
import com.SmithsModding.SmithsCore.Util.Client.ModelHelper;
import com.SmithsModding.SmithsCore.Util.Client.ResourceHelper;
import com.google.common.base.Function;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import net.minecraft.client.renderer.block.model.BakedQuad;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.VertexFormat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.*;

import java.util.ArrayList;
import java.util.Map;

/**
 * Created by Marc on 06.12.2015.
 * <p/>
 * Model used to display singular components of the Armor.
 * Is in implementation nearly the same as the TinkersConstruct Toolparts.
 */
public class ArmorComponentModel extends ItemLayerModel {

    /**
     * Creates a new unbaked model, given the parameters list of possible textures.
     *
     * @param textures The possible textures for the unbaked Model.
     */
    public ArmorComponentModel (ImmutableList<ResourceLocation> textures) {
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
        return super.bake(state, format, bakedTextureGetter);
    }

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

    public BakedComponentModel generateBackedComponentModel (IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        // normal model as the base
        IFlexibleBakedModel base = super.bake(state, format, bakedTextureGetter);

        // turn it into a baked material-model
        BakedComponentModel bakedMaterialModel = new BakedComponentModel(base);

        // and generate the baked model for each material-variant we have for the base texture
        String baseTexture = base.getParticleTexture().getIconName();
        Map<String, TextureAtlasSprite> sprites = MaterializedTextureCreator.getBuildSprites().get(baseTexture);

        for (Map.Entry<String, TextureAtlasSprite> entry : sprites.entrySet()) {
            IArmorMaterial material = MaterialRegistry.getInstance().getMaterial(entry.getKey());

            IModel model2 = this.retexture(ImmutableMap.of("layer0", entry.getValue().getIconName()));
            IFlexibleBakedModel bakedModel2 = model2.bake(state, format, bakedTextureGetter);

            // if it's a colored material we need to color the quads. But only if the texture was not a custom texture
            if (material.getRenderInfo().useVertexColoring() && !ResourceHelper.exists(baseTexture + "_" + material.getInternalMaterialName())) {
                MinecraftColor color = (material.getRenderInfo()).getVertexColor();

                ImmutableList.Builder<BakedQuad> quads = ImmutableList.builder();
                // ItemLayerModel.BakedModel only uses general quads
                for (BakedQuad quad : bakedModel2.getGeneralQuads()) {
                    quads.add(ModelHelper.colorQuad(color, quad));
                }

                // create a new model with the colored quads
                if (state instanceof IPerspectiveState) {
                    IPerspectiveState ps = (IPerspectiveState) state;
                    Map<ItemCameraTransforms.TransformType, TRSRTransformation> map = Maps.newHashMap();
                    for (ItemCameraTransforms.TransformType type : ItemCameraTransforms.TransformType.values()) {
                        map.put(type, ps.forPerspective(type).apply(this));
                    }
                    bakedModel2 =
                            new ItemLayerModel.BakedModel(quads.build(), bakedModel2.getParticleTexture(), bakedModel2.getFormat(), Maps
                                    .immutableEnumMap(map));
                } else {
                    bakedModel2 = new ItemLayerModel.BakedModel(quads.build(), bakedModel2.getParticleTexture(), bakedModel2.getFormat());
                }
            }

            bakedMaterialModel.addMaterialModel(material, bakedModel2);
        }

        return bakedMaterialModel;
    }

}
