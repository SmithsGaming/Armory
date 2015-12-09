package com.SmithsModding.Armory.Client.Model.Item.Unbaked;

import com.SmithsModding.Armory.Client.Model.Item.Baked.Components.*;
import com.SmithsModding.Armory.Client.Model.Item.Baked.HeatedItem.*;
import com.SmithsModding.Armory.Client.Model.Item.Unbaked.Components.*;
import com.SmithsModding.SmithsCore.Util.Client.*;
import com.google.common.base.*;
import com.google.common.collect.*;
import net.minecraft.client.renderer.block.model.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.util.*;
import net.minecraftforge.client.model.*;

import java.util.*;

/**
 * Created by Marc on 08.12.2015.
 */
public class HeatedItemItemModel extends ItemLayerModel {

    TemperatureBarComponentModel gaugeDisplay;

    public HeatedItemItemModel (ImmutableList<ResourceLocation> defaultTextures) {
        super(defaultTextures);

        this.gaugeDisplay = new TemperatureBarComponentModel(defaultTextures);
    }

    @Override
    public Collection<ResourceLocation> getDependencies () {
        return ImmutableList.of();
    }

    @Override
    public IFlexibleBakedModel bake (IModelState state, VertexFormat format, Function<ResourceLocation, TextureAtlasSprite> bakedTextureGetter) {
        //Get ourselfs the base model to use.
        IFlexibleBakedModel base = super.bake(state, format, bakedTextureGetter);

        ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms = ModelHelper.getTransformsFromState(state, this);

        //Bake the model.
        return new BakedHeatedItemModel(base, (BakedTemperatureBarModel) gaugeDisplay.generateBackedComponentModel(state, format, bakedTextureGetter), transforms);
    }

    @Override
    public IModelState getDefaultState () {
        return ModelHelper.DEFAULT_ITEM_STATE;
    }
}
