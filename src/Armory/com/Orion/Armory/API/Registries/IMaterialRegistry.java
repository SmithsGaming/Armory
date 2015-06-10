package com.Orion.Armory.API.Registries;

import com.Orion.Armory.API.Armor.MLAAddon;
import com.Orion.Armory.API.Armor.MultiLayeredArmor;
import com.Orion.Armory.API.Materials.IArmorMaterial;

import java.util.HashMap;

/**
 * Created by Orion
 * Created on 08.06.2015
 * 10:03
 * <p/>
 * Copyrighted according to Project specific license
 */
public interface IMaterialRegistry
{
    /**
     * Function to retrieve all of the types registered to this Registry
     *
     * @return A HashMap with as Key the internalname of the Armor and as Value a instance of a MultiLayeredArmor
     */
    HashMap<String, MultiLayeredArmor> getAllRegisteredArmors();

    /**
     * Function to register a new Instance of MultiLayeredArmor
     *
     * @param pArmor The Armor you want to register.
     */
    void registerNewArmor(MultiLayeredArmor pArmor);

    /**
     * Returns the Armor for a given internalname or null if not registered
     *
     * @param pInternalName The internalname of the requested armor.
     * @return The instance of MultiLayeredArmor registered to this IMaterialRegistry with the given internalname, if present or null if none is registerd with that name.
     */
    MultiLayeredArmor getArmor(String pInternalName);

    /**
     * Function to get all the materials registered to this IMaterialRegistry
     *
     * @return A HashMap with as Key the internalname of the Material and as Value the a instance of IArmorMaterial
     */
    HashMap<String, IArmorMaterial> getArmorMaterials();

    /**
     * Sets all the Materials at once.
     *
     * @param pNewMaterials A new HashMap with Material Definitions.
     */
    void setAllArmorMaterials(HashMap<String, IArmorMaterial> pNewMaterials);

    /**
     * Function to register a new Material to this registry
     *
     * @param pMaterial The new Material you want to register
     */
    void registerMaterial(IArmorMaterial pMaterial);

    /**
     * Function to get a material from its internalname if registered.
     *
     * @param pInternalName The internalname of the material you try to retrieve from the registry
     * @return A instance of IArmorMaterial that is represented by the given internalname or null if no material with that name is registered.
     */
    IArmorMaterial getMaterial(String pInternalName);

    /**
     * The MLAAddonState determines if a MLAAddon
     * of that material can be crafted and
     * applied to a MultiLayeredArmor.
     *
     * Setting it to false will disable the MLAAddon of that material.
     *
     * @param pMaterialInternalName The internalname of the material.
     * @param pUpgradeInternalName The internalname of the upgrade
     * @param pPartState The new state of the MLAAddon
     */
    void changeUpgradeStateOnMaterial(String pMaterialInternalName, String pUpgradeInternalName, boolean pPartState);
}
