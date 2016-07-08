package com.smithsmodding.armory.api.registries;

import com.smithsmodding.armory.api.armor.MultiLayeredArmor;

import java.util.HashMap;

/**
 * Author Orion (Created on: 07.07.2016)
 */
public interface IArmorRegistry {
    /**
     * Function to retrieve all of the types registered to this registry
     *
     * @return A HashMap with as Key the uniqueID of the armor and as Value a instance of a MultiLayeredArmor
     */
    HashMap<String, MultiLayeredArmor> getAllRegisteredArmors();

    /**
     * Function to register a new Instance of MultiLayeredArmor
     *
     * @param armor The armor you want to register.
     */
    void registerNewArmor(MultiLayeredArmor armor);

    /**
     * Returns the armor for a given uniqueID or null if not registered
     *
     * @param uniqueId The uniqueID of the requested armor.
     * @return The instance of MultiLayeredArmor registered to this IMaterialRegistry with the given uniqueID, if present or null if none is registerd with that name.
     */
    MultiLayeredArmor getArmor(String uniqueId);
}
