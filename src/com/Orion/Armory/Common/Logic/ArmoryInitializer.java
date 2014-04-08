package com.Orion.Armory.Common.Logic;
/*
/  ArmoryInitializer
/  Created by : Orion
/  Created on : 08/04/2014
*/

import com.Orion.Armory.Armory;
import com.Orion.Armory.Common.ARegistry;
import com.Orion.Armory.Common.Armor.ArmorMaterial;
import com.Orion.Armory.Common.Armor.ArmorUpgrade;

import java.util.ArrayList;

public class ArmoryInitializer
{

    public static void Initialize()
    {
        if (Armory.instance.iIsInitialized)
        {
            return;
        }


    }

    private static void registerMaterials()
    {
        ARegistry.iInstance.registerMaterial(new ArmorMaterial("vanilla.Iron", "Iron", "", true, new ArrayList<Boolean>()), true);
        ARegistry.iInstance.registerMaterial(new ArmorMaterial("vanilla.Chain", "Steel", "", true, new ArrayList<Boolean>()), true);
        ARegistry.iInstance.registerMaterial(new ArmorMaterial("tconstruct.Alumite", "Alumite", "", true, new ArrayList<Boolean>()), true);
        ARegistry.iInstance.registerMaterial(new ArmorMaterial("armory.Bronze", "Bronze", "", true, new ArrayList<Boolean>()), true);
        ARegistry.iInstance.registerMaterial(new ArmorMaterial("tconstruct.Ardite", "Ardite", "", false, new ArrayList<Boolean>()), true);
        ARegistry.iInstance.registerMaterial(new ArmorMaterial("tconstruct.Cobalt", "Cobalt", "", true, new ArrayList<Boolean>()), true);
        ARegistry.iInstance.registerMaterial(new ArmorMaterial("vanilla.Obsidian", "Obsidian", "", false, new ArrayList<Boolean>()), true);
        ARegistry.iInstance.registerMaterial(new ArmorMaterial("tconstruct.Manyullyn", "Manyullun", "", false, new ArrayList<Boolean>()), true);
    }

    private static void registerUpgrades()
    {
        registerTopHead();
        registerEarProtection();
        registerShoulderPads();
        registerFrontProtection();
        registerBackProtetion();
        registerFrontLegProtection();
        registerBackLegProtection();
        registerShoeProtection();
    }

    private static void registerTopHead()
    {
        for (ArmorMaterial tMaterial: ARegistry.iInstance.getArmorMaterials())
        {
            ARegistry.iInstance.registerUpgrade(new ArmorUpgrade(ARegistry.iInstance.getMaterialID(tMaterial), 0, 0, "topHead", "Head protection", "", "_Top_Head", 2.5F, 60, 1));

        }
    }

    private static void registerEarProtection()
    {

    }

    private static void registerShoulderPads()
    {

    }

    private static void registerFrontProtection()
    {

    }

    private static void registerBackProtetion()
    {

    }

    private static void registerFrontLegProtection()
    {

    }

    private static void registerBackLegProtection()
    {

    }

    private static void registerShoeProtection()
    {

    }

    private static void registerModifiers()
    {

    }

    private static void registerArmors()
    {

    }

    private static void registerRenderMappings()
    {

    }
}
