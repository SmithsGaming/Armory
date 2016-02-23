package com.smithsmodding.armory.common.registry;

import com.smithsmodding.armory.api.materials.*;
import com.smithsmodding.armory.api.registries.*;

import java.util.*;

/**
 * Created by Marc on 22.02.2016.
 */
public class AnvilMaterialRegistry implements IAnvilMaterialRegistry {

    protected static AnvilMaterialRegistry instance = new AnvilMaterialRegistry();

    HashMap<String, IAnvilMaterial> materialHashMap = new HashMap<>();

    public static AnvilMaterialRegistry getInstance()
    {
        return instance;
    }

    /**
     * Function to retrieve all of the types registered to this registry
     *
     * @return A HashMap with as Key the uniqueID of the anvil material and as Value a instance of a IAnvilMaterial
     */
    @Override
    public HashMap<String, IAnvilMaterial> getAllRegisteredAnvilMaterials () {
        return materialHashMap;
    }

    /**
     * Function to register a new Instance of MultiLayeredAnvilMaterial
     *
     * @param pAnvilMaterial The armor you want to register.
     */
    @Override
    public void registerNewAnvilMaterial (IAnvilMaterial pAnvilMaterial) {
        materialHashMap.put(pAnvilMaterial.getID(), pAnvilMaterial);
    }

    /**
     * Returns the material for a given uniqueID or null if not registered
     *
     * @param pInternalName The uniqueID of the requested armor.
     *
     * @return The instance of AnvilMaterial registered to this IAnvilMaterialRegistry with the given uniqueID, if
     * present or null if none is registered with that name.
     */
    @Override
    public IAnvilMaterial getAnvilMaterial (String pInternalName) {
        return materialHashMap.get(pInternalName);
    }

    /**
     * Function to get all the materials registered to this IAnvilMaterialRegistry
     *
     * @return A HashMap with as Key the uniqueID of the material and as Value the a instance of IAnvilMaterial
     */
    @Override
    public HashMap<String, IAnvilMaterial> getAnvilMaterials () {
        return materialHashMap;
    }

    /**
     * Sets all the materials at once.
     *
     * @param pNewMaterials A new HashMap with material Definitions.
     */
    @Override
    public void setAllAnvilMaterialMaterials (HashMap<String, IAnvilMaterial> pNewMaterials) {
        materialHashMap = pNewMaterials;
    }
}
