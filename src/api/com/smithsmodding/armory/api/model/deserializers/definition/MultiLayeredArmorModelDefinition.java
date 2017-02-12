package com.smithsmodding.armory.api.model.deserializers.definition;

import com.google.common.collect.ImmutableMap;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.model.TRSRTransformation;
import org.jetbrains.annotations.NotNull;

import java.util.Map;

/**
 * Author Marc (Created on: 28.05.2016)
 */
public class MultiLayeredArmorModelDefinition {

    final ResourceLocation baseLocation;
    @NotNull
    final Map<String, ResourceLocation> layerLocations;
    final Map<String, ResourceLocation> brokenLocations;
    final Map<ItemCameraTransforms.TransformType, TRSRTransformation> transforms;

    public MultiLayeredArmorModelDefinition(ResourceLocation baseLocation, @NotNull Map<String, ResourceLocation> layerLocations, Map<String, ResourceLocation> brokenLocations, Map<ItemCameraTransforms.TransformType, TRSRTransformation> transforms) {
        this.baseLocation = baseLocation;
        this.layerLocations = layerLocations;
        this.brokenLocations = brokenLocations;
        this.transforms = transforms;

        if (layerLocations.isEmpty())
            throw new IllegalArgumentException("Cannot create a MultiLayeredArmorModel without components!");
    }

    public ResourceLocation getBaseLocation() {
        return baseLocation;
    }

    public ImmutableMap<String, ResourceLocation> getLayerLocations() {
        return ImmutableMap.copyOf(layerLocations);
    }

    public ImmutableMap<String, ResourceLocation> getBrokenLocations() {
        return ImmutableMap.copyOf(brokenLocations);
    }

    public Map<ItemCameraTransforms.TransformType, TRSRTransformation> getTransforms() {
        return transforms;
    }
}
