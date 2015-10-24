package com.SmithsModding.Armory.API.Registries;

import com.SmithsModding.Armory.API.Armor.MLAAddon;

import java.util.HashMap;

/**
 * Created by Orion
 * Created on 08.06.2015
 * 13:30
 * <p/>
 * Copyrighted according to Project specific license
 */
public interface IMLAAddonRegistry {
    /**
     * Function to retrieve all registered MLAAddons.
     *
     * @return A HashMap with as Key the internalname representing the MLAAddon in the Value.
     */
    HashMap<String, MLAAddon> getUpgrades();

    /**
     * Function to register a new Upgrade
     *
     * @param pUpgrade The new upgrade to register
     */
    void registerUpgrade(MLAAddon pUpgrade);

    /**
     * Function to retrieve a MLAAddon from its internalname if registered to this IMaterialRegistry
     *
     * @param pUpgradeInternalName THe internalname of the MLAAddon
     * @return A MLAAddon instance with that internalname if registered, else null.
     */
    MLAAddon getUpgrade(String pUpgradeInternalName);
}
