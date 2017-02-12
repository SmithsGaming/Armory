package com.smithsmodding.armory.client.model.item.baked.components;

import com.google.common.collect.ImmutableMap;
import com.smithsmodding.smithscore.client.model.baked.BakedWrappedModel;
import net.minecraft.client.renderer.block.model.IBakedModel;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.TRSRTransformation;

/**
 * Created by marcf on 1/8/2017.
 */
public class BakedSubComponentModel extends BakedWrappedModel.PerspectiveAware {
    public BakedSubComponentModel(IBakedModel parentModel, ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transformations) {
        super(parentModel, transformations);
    }

    /**
     * Function to get a model from a Material.
     *
     * @param identifier The Material to get the model for.
     * @return If registered it will return the prebaked model that is registered to that material id, if not it will return this instance of a BakedComponent model.
     */
    public IBakedModel getModelByIdentifier(ResourceLocation identifier) {
        return this;
    }
}
