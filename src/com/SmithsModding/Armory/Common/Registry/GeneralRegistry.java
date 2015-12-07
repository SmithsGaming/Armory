package com.SmithsModding.Armory.Common.Registry;
/*
 *   GeneralRegistry
 *   Created by: Orion
 *   Created on: 24-9-2014
 */

import net.minecraft.item.ItemArmor;
import net.minecraftforge.common.util.EnumHelper;

import java.util.Properties;

public class GeneralRegistry {
    public static boolean isInDevEnvironment = false;
    protected static GeneralRegistry INSTANCE;
    private static ItemArmor.ArmorMaterial armorMaterial = EnumHelper.addArmorMaterial("Armory-Dummy", "missingno", 100, new int[]{0, 0, 0, 0}, 0);

    public GeneralRegistry() {
        Properties tSysProp = System.getProperties();
        isInDevEnvironment = Boolean.parseBoolean(tSysProp.getProperty("Armory.Dev", "false"));
    }

    public static GeneralRegistry getInstance() {
        if (INSTANCE == null) INSTANCE = new GeneralRegistry();
        return INSTANCE;
    }

    public static final ItemArmor.ArmorMaterial getVanillaArmorDefitinition () {
        return armorMaterial;
    }
}
