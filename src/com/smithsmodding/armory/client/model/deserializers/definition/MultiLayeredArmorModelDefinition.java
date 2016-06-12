package com.smithsmodding.armory.client.model.deserializers.definition;

import com.google.common.collect.ImmutableMap;
import net.minecraft.util.ResourceLocation;

import java.util.Map;

/**
 * @Author Marc (Created on: 28.05.2016)
 */
public class MultiLayeredArmorModelDefinition {

    final ResourceLocation baseLocation;
    final Map<String, ResourceLocation> layerLocations;
    final Map<String, ResourceLocation> brokenLocations;

    public MultiLayeredArmorModelDefinition(ResourceLocation baseLocation, Map<String, ResourceLocation> layerLocations, Map<String, ResourceLocation> brokenLocations) {
        this.baseLocation = baseLocation;
        this.layerLocations = layerLocations;
        this.brokenLocations = brokenLocations;

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
}
