package com.smithsmodding.armory.api.armor;

import com.smithsmodding.armory.api.capability.armor.IArmorCapability;
import com.smithsmodding.armory.api.material.armor.IAddonArmorMaterial;
import net.minecraftforge.common.capabilities.Capability;

import javax.annotation.Nonnull;
import java.util.HashMap;

/**
 * Created by marcf on 1/3/2017.
 */
public interface IMaterialDependantMultiComponentArmorExtension extends IMultiComponentArmorExtension {

    /**
     * Getter for the material independent extension.
     *
     * @return The material independent extension.
     */
    @Nonnull
    IMultiComponentArmorExtension getMaterialIndependentExtension();

    /**
     *
     * @return
     */
    @Nonnull
    IAddonArmorMaterial getMaterial();

    /**
     * Method to get all the default capabilities this Component provides.
     * The Capabilities stored here override those stored in the Armor and in its CoreMaterial.
     *
     * In its default implementation it takes the Capabilities from the MaterialIndependentExtension
     * and overrides those with the Capabilities from the material this Addon is made of.
     *
     * @return All the default capabilities this Component provides.
     */
    @Nonnull
    @Override
    default HashMap<Capability<? extends IArmorCapability>, Object> getDefaultComponentCapabilities() {
        HashMap<Capability<? extends IArmorCapability>, Object> capabilityObjectHashMap = getMaterialIndependentExtension().getDefaultComponentCapabilities();
        capabilityObjectHashMap.putAll(getMaterial().getOverrideAddonMaterialCapabilities(getMaterialIndependentExtension()));

        return capabilityObjectHashMap;
    }
}
