package com.smithsmodding.armory.api.registries;

import com.smithsmodding.armory.api.armor.MLAAddon;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;

/**
 * Created by Orion
 * Created on 08.06.2015
 * 13:30
 *
 * Copyrighted according to Project specific license
 */
public interface IMLAAddonRegistry {
    /**
     * Function to retrieve all registered MLAAddons.
     *
     * @return A HashMap with as Key the uniqueID representing the MLAAddon in the Value.
     */
    @NotNull HashMap<String, MLAAddon> getUpgrades();

    /**
     * Function to register a new Upgrade
     *
     * @param pUpgrade The new upgrade to register
     */
    void registerUpgrade(MLAAddon pUpgrade);

    /**
     * Function to retrieve a MLAAddon from its uniqueID if registered to this IMaterialRegistry
     *
     * @param pUpgradeInternalName THe uniqueID of the MLAAddon
     * @return A MLAAddon instance with that uniqueID if registered, else null.
     */
    MLAAddon getUpgrade(String pUpgradeInternalName);

    /**
     * Gets the first registered instance of a MLAAddon registered with the given addonID as material independent ID.
     *
     * @param addonId The searched AddonID.
     * @return The first registered instance of a MLAAddon registered with the given addonID as material independent ID.
     */
    @Nullable MLAAddon getAddonForMaterialIndependantName(String addonId);
}
