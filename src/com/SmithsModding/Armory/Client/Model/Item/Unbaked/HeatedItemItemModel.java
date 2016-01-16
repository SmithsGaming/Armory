package com.SmithsModding.Armory.Client.Model.Item.Unbaked;

import com.SmithsModding.Armory.Client.Model.Item.Baked.Components.*;
import com.SmithsModding.Armory.Client.Model.Item.Baked.HeatedItem.*;
import com.SmithsModding.Armory.Client.Model.Item.Unbaked.Components.*;
import com.google.common.base.*;
import com.google.common.base.Optional;
import com.google.common.collect.*;
import net.minecraft.client.renderer.texture.*;
import net.minecraft.client.renderer.vertex.*;
import net.minecraft.util.*;
import net.minecraftforge.client.model.*;

import javax.vecmath.*;
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
        float additionalScale = 64 / 40F;

        BakedTemperatureBarModel unrotatedModel = gaugeDisplay.generateBackedComponentModel(state, format, bakedTextureGetter);
        BakedTemperatureBarModel rotatedModel = gaugeDisplay.generateBackedComponentModel(state.apply(Optional.<IModelPart> absent()).get().compose(new TRSRTransformation(new Vector3f(4f, +2f, -1.9f), TRSRTransformation.quatFromYXZDegrees(new Vector3f(-30f, -225f, 0f)), new Vector3f(additionalScale, additionalScale, additionalScale), null)), format, bakedTextureGetter);

        //Bake the model.
        return new BakedHeatedItemModel(base, unrotatedModel, rotatedModel);
    }
}
