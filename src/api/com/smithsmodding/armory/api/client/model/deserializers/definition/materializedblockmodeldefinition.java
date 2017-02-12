package com.smithsmodding.armory.api.client.model.deserializers.definition;

import com.google.common.collect.ImmutableMap;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.TRSRTransformation;

import java.util.Map;

/**
 * Author Marc (Created on: 14.06.2016)
 */
public class MaterializedBlockModelDefinition {
    private final ResourceLocation coreModel;
    private final ResourceLocation coreTexture;
    private final Map<ResourceLocation, ResourceLocation> materialOverrides;
    private final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms;


    public MaterializedBlockModelDefinition(ResourceLocation coreModel, ResourceLocation coreTexture, Map<ResourceLocation, ResourceLocation> materialOverrides, ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms) {
        this.coreModel = coreModel;
        this.coreTexture = coreTexture;
        this.materialOverrides = materialOverrides;
        this.transforms = transforms;
    }

    public ResourceLocation getCoreModel() {
        return coreModel;
    }

    public ResourceLocation getCoreTexture() {
        return coreTexture;
    }

    public Map<ResourceLocation, ResourceLocation> getMaterialOverrides() {
        return materialOverrides;
    }

    public ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> getTransforms() {
        return transforms;
    }
}
