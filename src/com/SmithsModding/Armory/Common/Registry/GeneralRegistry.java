package com.SmithsModding.Armory.Common.Registry;
/*
 *   GeneralRegistry
 *   Created by: Orion
 *   Created on: 24-9-2014
 */

import com.SmithsModding.Armory.API.Armor.MLAAddon;
import com.SmithsModding.Armory.API.Armor.MultiLayeredArmor;
import com.SmithsModding.Armory.Common.Material.MaterialRegistry;
import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;

import java.util.Properties;

public class GeneralRegistry {
    public static ItemArmor.ArmorMaterial iArmorMaterial = EnumHelper.addArmorMaterial("Armory-Dummy", "missingno", 100, new int[]{0, 0, 0, 0}, 0);
    public static boolean iIsInDevEnvironment = false;
    protected static GeneralRegistry iInstance;

    public GeneralRegistry() {
        Properties tSysProp = System.getProperties();
        iIsInDevEnvironment = Boolean.parseBoolean(tSysProp.getProperty("Armory.Dev", "false"));
    }

    public static GeneralRegistry getInstance() {
        if (iInstance == null) iInstance = new GeneralRegistry();
        return iInstance;
    }

    public static MLAAddon retrieveAddon (String internalName) {
        for (MultiLayeredArmor armor : MaterialRegistry.getInstance().getAllRegisteredArmors().values()) {
            if (armor.getAddon(internalName) != null)
                return armor.getAddon(internalName);
        }

        return null;
    }
}
