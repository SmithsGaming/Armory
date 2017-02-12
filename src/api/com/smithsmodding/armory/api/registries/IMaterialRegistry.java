package com.smithsmodding.armory.api.registries;

import com.smithsmodding.armory.api.logic.IMaterialInitializer;
import com.smithsmodding.armory.api.materials.IArmorMaterial;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

/**
 * Created by Orion
 * Created on 08.06.2015
 * 10:03
 *
 * Copyrighted according to Project specific license
 */
public interface IMaterialRegistry {

    /**
     * Function to get all the materials registered to this IMaterialRegistry
     *
     * @return A HashMap with as Key the uniqueID of the material and as Value the a instance of IArmorMaterial
     */
    @NotNull HashMap<String, IArmorMaterial> getArmorMaterials();

    /**
     * Function to get all the material initializers registered to this IMaterialRegistry
     *
     * @return A HashMap with as Key the material and as Value the a instance of IMaterialInitializer
     */
    @NotNull HashMap<IArmorMaterial, IMaterialInitializer> getInitializers();

    /**
     * Function to register a new material to this registry
     *
     * @param material The new material you want to register
     */
    void registerMaterial(IArmorMaterial material, IMaterialInitializer initializer);

    /**
     * Function to get a material from its uniqueID if registered.
     *
     * @param uniqueId The uniqueID of the material you try to retrieve from the registry
     * @return A instance of IArmorMaterial that is represented by the given uniqueID or null if no material with that name is registered.
     */
    IArmorMaterial getMaterial(String uniqueId);

    /**
     * Getter for a materials initializer.
     *
     * @param material Hte material in question.
     * @return The initializer for a given material.
     */
    IMaterialInitializer getInitializer(IArmorMaterial material);
}
