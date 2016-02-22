package com.smithsmodding.armory.api.registries;

import com.smithsmodding.armory.api.armor.*;
import com.smithsmodding.armory.api.materials.*;

import java.util.*;

/**
 * Created by Marc on 22.02.2016.
 */
public interface IAnvilMaterialRegistry
{

    /**
     * Function to retrieve all of the types registered to this registry
     *
     * @return A HashMap with as Key the uniqueID of the anvil material and as Value a instance of a IAnvilMaterial
     */
    HashMap<String, IAnvilMaterial> getAllRegisteredAnvilMaterials();

    /**
     * Function to register a new Instance of MultiLayeredAnvilMaterial
     *
     * @param pAnvilMaterial The armor you want to register.
     */
    void registerNewAnvilMaterial(IAnvilMaterial pAnvilMaterial);

    /**
     * Returns the material for a given uniqueID or null if not registered
     *
     * @param pInternalName The uniqueID of the requested armor.
     * @return The instance of AnvilMaterial registered to this IAnvilMaterialRegistry with the given uniqueID, if present or null if none is registered with that name.
     */
    IAnvilMaterial getAnvilMaterial(String pInternalName);

    /**
     * Function to get all the materials registered to this IAnvilMaterialRegistry
     *
     * @return A HashMap with as Key the uniqueID of the material and as Value the a instance of IAnvilMaterial
     */
    HashMap<String, IAnvilMaterial> getAnvilMaterials();

    /**
     * Sets all the materials at once.
     *
     * @param pNewMaterials A new HashMap with material Definitions.
     */
    void setAllAnvilMaterialMaterials(HashMap<String, IAnvilMaterial> pNewMaterials);
}
