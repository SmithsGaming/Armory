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
            ArmorUpgrade tTopHead = new ArmorUpgrade(ARegistry.iInstance.getMaterialID(tMaterial), 0, 0, tMaterial.iInternalName+".TopHead", "Head protection", "", "_Top_Head", 2.5F, 60, 1);
            ARegistry.iInstance.registerUpgrade(tTopHead);

            if (tMaterial.iInternalName.equals("vanilla.Obsidian"))
            {
                tMaterial.registerNewActivePart(ARegistry.iInstance.getUpgradeID(tTopHead), false);
            }
            else
            {
                tMaterial.registerNewActivePart(ARegistry.iInstance.getUpgradeID(tTopHead), true);
            }
        }
    }

    private static void registerEarProtection()
    {
        for (ArmorMaterial tMaterial: ARegistry.iInstance.getArmorMaterials())
        {
            ArmorUpgrade tEarProtectionLeft = new ArmorUpgrade(ARegistry.iInstance.getMaterialID(tMaterial), 0, 1, tMaterial.iInternalName+".EarProtection.Left", "Ear protection left", "", "_Ear_Protection_Left", 0.5F, 20, 1);
            ArmorUpgrade tEarProtectionRight = new ArmorUpgrade(ARegistry.iInstance.getMaterialID(tMaterial), 0, 2, tMaterial.iInternalName+".EarProtection.Right", "Ear protection right", "", "_Ear_Protection_Right", 0.5F, 20, 1);
            ARegistry.iInstance.registerUpgrade(tEarProtectionLeft);
            ARegistry.iInstance.registerUpgrade(tEarProtectionRight);

            if (tMaterial.iInternalName.equals("tconstruct.Manyullun"))
            {
                tMaterial.registerNewActivePart(ARegistry.iInstance.getUpgradeID(tEarProtectionLeft), false);
                tMaterial.registerNewActivePart(ARegistry.iInstance.getUpgradeID(tEarProtectionRight), false);
            }
            else
            {
                tMaterial.registerNewActivePart(ARegistry.iInstance.getUpgradeID(tEarProtectionLeft), true);
                tMaterial.registerNewActivePart(ARegistry.iInstance.getUpgradeID(tEarProtectionRight), true);
            }
        }
    }

    private static void registerShoulderPads()
    {
        for (ArmorMaterial tMaterial: ARegistry.iInstance.getArmorMaterials())
        {
            ArmorUpgrade tShoulderPadLeft = new ArmorUpgrade(ARegistry.iInstance.getMaterialID(tMaterial), 1, 0, tMaterial.iInternalName+".ShoulderPad.Left", "Shoulder pad left", "", "_Shoulder_Pad_Left", 1F, 50, 1);
            ArmorUpgrade tShoulderPadRight = new ArmorUpgrade(ARegistry.iInstance.getMaterialID(tMaterial), 1, 0, tMaterial.iInternalName+".ShoulderPad.Right", "Shoulder pad right", "", "_Shoulder_Pad_Right", 1F, 50, 1);
            ARegistry.iInstance.registerUpgrade(tShoulderPadLeft);
            ARegistry.iInstance.registerUpgrade(tShoulderPadRight);

            if ((tMaterial.iInternalName.equals("tconstruct.Cobalt")) || (tMaterial.iInternalName.equals("tconstruct.Manyullun")))
            {
                tMaterial.registerNewActivePart(ARegistry.iInstance.getUpgradeID(tShoulderPadLeft), false);
                tMaterial.registerNewActivePart(ARegistry.iInstance.getUpgradeID(tShoulderPadRight), false);
            }
            else
            {
                tMaterial.registerNewActivePart(ARegistry.iInstance.getUpgradeID(tShoulderPadLeft), true);
                tMaterial.registerNewActivePart(ARegistry.iInstance.getUpgradeID(tShoulderPadRight), true);
            }
        }
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
