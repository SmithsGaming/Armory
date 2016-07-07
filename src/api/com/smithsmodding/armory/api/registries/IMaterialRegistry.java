package com.smithsmodding.armory.api.registries;

import com.smithsmodding.armory.api.armor.*;
import com.smithsmodding.armory.api.materials.*;

import java.util.*;

/**
 * Created by Orion
 * Created on 08.06.2015
 * 10:03
 *
 * Copyrighted according to Project specific license
 */
public interface IMaterialRegistry {
    /**
     * Function to retrieve all of the types registered to this registry
     *
     * @return A HashMap with as Key the uniqueID of the armor and as Value a instance of a MultiLayeredArmor
     */
    HashMap<String, MultiLayeredArmor> getAllRegisteredArmors();

    /**
     * Function to register a new Instance of MultiLayeredArmor
     *
     * @param pArmor The armor you want to register.
     */
    void registerNewArmor(MultiLayeredArmor pArmor);

    /**
     * Returns the armor for a given uniqueID or null if not registered
     *
     * @param pInternalName The uniqueID of the requested armor.
     * @return The instance of MultiLayeredArmor registered to this IMaterialRegistry with the given uniqueID, if present or null if none is registerd with that name.
     */
    MultiLayeredArmor getArmor(String pInternalName);

    /**
     * Function to get all the materials registered to this IMaterialRegistry
     *
     * @return A HashMap with as Key the uniqueID of the material and as Value the a instance of IArmorMaterial
     */
    HashMap<String, IArmorMaterial> getArmorMaterials();

    /**
     * Sets all the materials at once.
     *
     * @param pNewMaterials A new HashMap with material Definitions.
     */
    void setAllArmorMaterials(HashMap<String, IArmorMaterial> pNewMaterials);

    /**
     * Function to register a new material to this registry
     *
     * @param pMaterial The new material you want to register
     */
    void registerMaterial(IArmorMaterial pMaterial);

    /**
     * Function to get a material from its uniqueID if registered.
     *
     * @param pInternalName The uniqueID of the material you try to retrieve from the registry
     * @return A instance of IArmorMaterial that is represented by the given uniqueID or null if no material with that name is registered.
     */
    IArmorMaterial getMaterial(String pInternalName);
}
