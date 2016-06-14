package com.smithsmodding.armory.client.model.deserializers.definition;

import com.google.common.collect.ImmutableMap;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.TRSRTransformation;

/**
 * @Author Marc (Created on: 14.06.2016)
 */
public class MaterializedItemModelDefinition {
    private final ResourceLocation coreTexture;
    private final ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms;

    public MaterializedItemModelDefinition(ResourceLocation coreTexture, ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> transforms) {
        this.coreTexture = coreTexture;
        this.transforms = transforms;
    }

    public ResourceLocation getCoreTexture() {
        return coreTexture;
    }

    public ImmutableMap<ItemCameraTransforms.TransformType, TRSRTransformation> getTransforms() {
        return transforms;
    }
}
