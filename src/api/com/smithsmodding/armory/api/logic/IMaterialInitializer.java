package com.smithsmodding.armory.api.logic;

import com.smithsmodding.armory.api.armor.MultiLayeredArmor;
import com.smithsmodding.armory.api.materials.IArmorMaterial;

/**
 * Author Orion (Created on: 07.07.2016)
 */
public interface IMaterialInitializer {
    void registerUpgradesForArmor(IArmorMaterial material, MultiLayeredArmor armor);

    void modifyMaterialForArmor(IArmorMaterial material);
}
