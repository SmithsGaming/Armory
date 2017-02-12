package com.smithsmodding.armory.common.registry;
/*
 *   MaterialRegistry
 *   Created by: Orion
 *   Created on: 24-9-2014
 */

import com.smithsmodding.armory.api.armor.MultiLayeredArmor;
import com.smithsmodding.armory.api.logic.IMaterialInitializer;
import com.smithsmodding.armory.api.materials.IArmorMaterial;
import com.smithsmodding.armory.api.registries.IMaterialRegistry;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;

public class MaterialRegistry implements IMaterialRegistry {
    private static MaterialRegistry instance;

    //Hashmap for storing all the materials
    @NotNull
    protected HashMap<String, IArmorMaterial> materials = new HashMap<String, IArmorMaterial>();

    @NotNull
    protected HashMap<IArmorMaterial, IMaterialInitializer> initializers = new HashMap<>();

    public static MaterialRegistry getInstance() {
        if (instance == null) {
            instance = new MaterialRegistry();
        }

        return instance;
    }

    //ArmorMaterials
    @NotNull
    public HashMap<String, IArmorMaterial> getArmorMaterials() {
        return materials;
    }

    @NotNull
    @Override
    public HashMap<IArmorMaterial, IMaterialInitializer> getInitializers() {
        return initializers;
    }

    @Override
    public void registerMaterial(@NotNull IArmorMaterial material, @NotNull IMaterialInitializer initializer) {
        materials.put(material.getUniqueID(), material);
        initializers.put(material, initializer);

        for (MultiLayeredArmor armor : ArmorRegistry.getInstance().getAllRegisteredArmors().values())
            initializer.registerUpgradesForArmor(material, armor);

        initializer.modifyMaterialForArmor(material);
    }

    public IArmorMaterial getMaterial(String uniqueId) {
        return materials.get(uniqueId);
    }

    @Override
    public IMaterialInitializer getInitializer(IArmorMaterial material) {
        return initializers.get(material);
    }
}
