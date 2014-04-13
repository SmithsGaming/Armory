package com.Orion.Armory.Common.Logic;
/*
/  ArmoryInitializer
/  Created by : Orion
/  Created on : 08/04/2014
*/

import com.Orion.Armory.Armory;
import com.Orion.Armory.Client.AClientRegistry;
import com.Orion.Armory.Common.ARegistry;
import com.Orion.Armory.Common.Armor.ArmorCore;
import com.Orion.Armory.Common.Armor.ArmorMaterial;
import com.Orion.Armory.Common.Armor.ArmorUpgrade;
import com.Orion.Armory.Common.Events.*;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;

public class ArmoryInitializer
{
    public static void Initialize(Side pSide)
    {
        if (Armory.instance.iIsInitialized)
        {
            return;
        }

        registerMaterials();
        registerUpgrades();
        registerModifiers();
        registerArmors();

        if (pSide == Side.CLIENT) {
            registerRenderMappings();
        }

        prepareGame();
    }

    private static void registerMaterials()
    {
        ARegistry.iInstance.registerMaterial(new ArmorMaterial("vanilla.Iron", "Iron", "", true, new ArrayList<Float>(), new ArrayList<Integer>(), new ArrayList<Integer>(), new ArrayList<Boolean>()), true);
        ARegistry.iInstance.registerMaterial(new ArmorMaterial("vanilla.Chain", "Steel", "", true, new ArrayList<Float>(), new ArrayList<Integer>(), new ArrayList<Integer>(), new ArrayList<Boolean>()), true);
        ARegistry.iInstance.registerMaterial(new ArmorMaterial("tconstruct.Alumite", "Alumite", "", true, new ArrayList<Float>(), new ArrayList<Integer>(), new ArrayList<Integer>(),new ArrayList<Boolean>()), true);
        ARegistry.iInstance.registerMaterial(new ArmorMaterial("armory.Bronze", "Bronze", "", true, new ArrayList<Float>(), new ArrayList<Integer>(), new ArrayList<Integer>(),new ArrayList<Boolean>()), true);
        ARegistry.iInstance.registerMaterial(new ArmorMaterial("tconstruct.Ardite", "Ardite", "", false, new ArrayList<Float>(), new ArrayList<Integer>(), new ArrayList<Integer>(),new ArrayList<Boolean>()), true);
        ARegistry.iInstance.registerMaterial(new ArmorMaterial("tconstruct.Cobalt", "Cobalt", "", true, new ArrayList<Float>(), new ArrayList<Integer>(), new ArrayList<Integer>(),new ArrayList<Boolean>()), true);
        ARegistry.iInstance.registerMaterial(new ArmorMaterial("vanilla.Obsidian", "Obsidian", "", false, new ArrayList<Float>(), new ArrayList<Integer>(), new ArrayList<Integer>(),new ArrayList<Boolean>()), true);
        ARegistry.iInstance.registerMaterial(new ArmorMaterial("tconstruct.Manyullyn", "Manyullun", "", false, new ArrayList<Float>(), new ArrayList<Integer>(), new ArrayList<Integer>(),new ArrayList<Boolean>()), true);

        MinecraftForge.EVENT_BUS.post(new RegisterMaterialsEvent());
    }

    private static void registerUpgrades()
    {
        registerTopHead();
        registerEarProtection();
        registerShoulderPads();
        registerFrontProtection();
        registerBackProtection();
        registerFrontLegProtection();
        registerBackLegProtection();
        registerShoeProtection();

        MinecraftForge.EVENT_BUS.post(new RegisterUpgradesEvent());
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
            ArmorUpgrade tShoulderPadRight = new ArmorUpgrade(ARegistry.iInstance.getMaterialID(tMaterial), 1, 1, tMaterial.iInternalName+".ShoulderPad.Right", "Shoulder pad right", "", "_Shoulder_Pad_Right", 1F, 50, 1);
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
        for (ArmorMaterial tMaterial: ARegistry.iInstance.getArmorMaterials())
        {
            ArmorUpgrade tFrontChestProtectionLeft = new ArmorUpgrade(ARegistry.iInstance.getMaterialID(tMaterial), 1, 3, tMaterial.iInternalName+".Protection.Chest.Front.Left", "Front chest protection left", "", "_Protection_Chest_Front_Left", 2F, 100, 1);
            ArmorUpgrade tFrontChestProtectionRight = new ArmorUpgrade(ARegistry.iInstance.getMaterialID(tMaterial), 1, 4, tMaterial.iInternalName+".Protection.Chest.Front.Right", "Front chest protection right", "", "_Protection_Chest_Front_Right", 2F, 100, 1);
            ARegistry.iInstance.registerUpgrade(tFrontChestProtectionLeft);
            ARegistry.iInstance.registerUpgrade(tFrontChestProtectionRight);

            if (tMaterial.iInternalName.equals("vanilla.Obsidian"))
            {
                tMaterial.registerNewActivePart(ARegistry.iInstance.getUpgradeID(tFrontChestProtectionLeft), false);
                tMaterial.registerNewActivePart(ARegistry.iInstance.getUpgradeID(tFrontChestProtectionRight), false);
            }
            else
            {
                tMaterial.registerNewActivePart(ARegistry.iInstance.getUpgradeID(tFrontChestProtectionLeft), true);
                tMaterial.registerNewActivePart(ARegistry.iInstance.getUpgradeID(tFrontChestProtectionRight), true);
            }
        }
    }

    private static void registerBackProtection()
    {
        for (ArmorMaterial tMaterial: ARegistry.iInstance.getArmorMaterials())
        {
            ArmorUpgrade tBackChestProtectionLeft = new ArmorUpgrade(ARegistry.iInstance.getMaterialID(tMaterial), 1, 5, tMaterial.iInternalName+".Protection.Chest.Back.Left", "Back chest protection left", "", "_Protection_Chest_Front_Left", 2F, 150, 1);
            ArmorUpgrade tBackChestProtectionRight = new ArmorUpgrade(ARegistry.iInstance.getMaterialID(tMaterial), 1, 6, tMaterial.iInternalName+".Protection.Chest.Back.Right", "Back chest protection right", "", "_Protection_Chest_Back_Right", 2F, 150, 1);
            ARegistry.iInstance.registerUpgrade(tBackChestProtectionLeft);
            ARegistry.iInstance.registerUpgrade(tBackChestProtectionRight);

            if (tMaterial.iInternalName.equals("vanilla.Obsidian"))
            {
                tMaterial.registerNewActivePart(ARegistry.iInstance.getUpgradeID(tBackChestProtectionLeft), false);
                tMaterial.registerNewActivePart(ARegistry.iInstance.getUpgradeID(tBackChestProtectionRight), false);
            }
            else
            {
                tMaterial.registerNewActivePart(ARegistry.iInstance.getUpgradeID(tBackChestProtectionLeft), true);
                tMaterial.registerNewActivePart(ARegistry.iInstance.getUpgradeID(tBackChestProtectionRight), true);
            }
        }
    }

    private static void registerFrontLegProtection()
    {
        for (ArmorMaterial tMaterial: ARegistry.iInstance.getArmorMaterials())
        {
            ArmorUpgrade tFrontLeggingsProtectionLeft = new ArmorUpgrade(ARegistry.iInstance.getMaterialID(tMaterial), 2, 0, tMaterial.iInternalName+".Protection.Leggings.Front.Left", "Front leg protection left", "", "_Protection_Leggings_Front_Left", 1.5F, 125, 1);
            ArmorUpgrade tFrontLeggingsProtectionRight = new ArmorUpgrade(ARegistry.iInstance.getMaterialID(tMaterial), 2, 1, tMaterial.iInternalName+".Protection.Leggings.Front.Right", "Front leg protection right", "", "_Protection_Leggings_Front_Right", 1.5F, 125, 1);
            ARegistry.iInstance.registerUpgrade(tFrontLeggingsProtectionLeft);
            ARegistry.iInstance.registerUpgrade(tFrontLeggingsProtectionRight);

            if (tMaterial.iInternalName.equals("vanilla.Obsidian"))
            {
                tMaterial.registerNewActivePart(ARegistry.iInstance.getUpgradeID(tFrontLeggingsProtectionLeft), false);
                tMaterial.registerNewActivePart(ARegistry.iInstance.getUpgradeID(tFrontLeggingsProtectionRight), false);
            }
            else
            {
                tMaterial.registerNewActivePart(ARegistry.iInstance.getUpgradeID(tFrontLeggingsProtectionLeft), true);
                tMaterial.registerNewActivePart(ARegistry.iInstance.getUpgradeID(tFrontLeggingsProtectionRight), true);
            }
        }
    }

    private static void registerBackLegProtection()
    {
        for (ArmorMaterial tMaterial: ARegistry.iInstance.getArmorMaterials())
        {
            ArmorUpgrade tBackLeggingsProtectionLeft = new ArmorUpgrade(ARegistry.iInstance.getMaterialID(tMaterial), 2, 5, tMaterial.iInternalName+".Protection.Leggings.Back.Left", "Back leg protection left", "", "_Protection_Leggings_Front_Left", 2F, 150, 1);
            ArmorUpgrade tBackLeggingsProtectionRight = new ArmorUpgrade(ARegistry.iInstance.getMaterialID(tMaterial), 2, 6, tMaterial.iInternalName+".Protection.Leggings.Back.Right", "Back leg protection right", "", "_Protection_Leggings_Back_Right", 2F, 150, 1);
            ARegistry.iInstance.registerUpgrade(tBackLeggingsProtectionLeft);
            ARegistry.iInstance.registerUpgrade(tBackLeggingsProtectionRight);

            if (tMaterial.iInternalName.equals("vanilla.Obsidian"))
            {
                tMaterial.registerNewActivePart(ARegistry.iInstance.getUpgradeID(tBackLeggingsProtectionLeft), false);
                tMaterial.registerNewActivePart(ARegistry.iInstance.getUpgradeID(tBackLeggingsProtectionRight), false);
            }
            else
            {
                tMaterial.registerNewActivePart(ARegistry.iInstance.getUpgradeID(tBackLeggingsProtectionLeft), true);
                tMaterial.registerNewActivePart(ARegistry.iInstance.getUpgradeID(tBackLeggingsProtectionRight), true);
            }
        }
    }

    private static void registerShoeProtection()
    {
        for (ArmorMaterial tMaterial: ARegistry.iInstance.getArmorMaterials())
        {
            ArmorUpgrade tShoeProtectionLeft = new ArmorUpgrade(ARegistry.iInstance.getMaterialID(tMaterial), 3, 0, tMaterial.iInternalName+".Protection.Shoe.Left", "Shoe protection left", "", "_Protection_Shoe_Left", 1F, 50, 1);
            ArmorUpgrade tShoeProtectionRight = new ArmorUpgrade(ARegistry.iInstance.getMaterialID(tMaterial), 3, 1, tMaterial.iInternalName+".Protection.Shoe.Right", "Shoe protection right", "", "_Protection_Shoe_Right", 1F, 50, 1);
            ARegistry.iInstance.registerUpgrade(tShoeProtectionLeft);
            ARegistry.iInstance.registerUpgrade(tShoeProtectionRight);

            if ((tMaterial.iInternalName.equals("tconstruct.Ardite")) || (tMaterial.iInternalName.equals("tconstruct.Cobalt")) || tMaterial.iInternalName.equals("tconstruct.Manyullun"))
            {
                tMaterial.registerNewActivePart(ARegistry.iInstance.getUpgradeID(tShoeProtectionLeft), false);
                tMaterial.registerNewActivePart(ARegistry.iInstance.getUpgradeID(tShoeProtectionRight), false);
            }
            else
            {
                tMaterial.registerNewActivePart(ARegistry.iInstance.getUpgradeID(tShoeProtectionLeft), true);
                tMaterial.registerNewActivePart(ARegistry.iInstance.getUpgradeID(tShoeProtectionRight), true);
            }
        }
    }

    private static void registerModifiers()
    {
        //TODO: Create all the modifiers

        MinecraftForge.EVENT_BUS.post(new RegisterModifiersEvent());
    }

    private static void registerArmors()
    {
        registerHelmet();
        registerChestplate();
        registerLeggins();
        registerShoes();
        MinecraftForge.EVENT_BUS.post(new RegisterArmorEvent());
    }

    private static void registerHelmet()
    {
        ArmorCore tHelmet = new ArmorCore("armory.Helmet", 0);

        for(ArmorMaterial tMaterial   :ARegistry.iInstance.getArmorMaterials()) {
            if (tMaterial.iInternalName.equals("vanilla.Iron")) {
                tMaterial.setBaseDamageAbsorption(0, 1.5F);
                tMaterial.setBaseDurability(0, 50);
                tMaterial.setMaxModifiersOnPart(0, 1);
            } else if (tMaterial.iInternalName.equals("vanilla.Chain")) {
                tMaterial.setBaseDamageAbsorption(0, 2.0F);
                tMaterial.setBaseDurability(0, 60);
                tMaterial.setMaxModifiersOnPart(0, 1);
            } else if (tMaterial.iInternalName.equals("tconstruct.Alumite")) {
                tMaterial.setBaseDamageAbsorption(0, 2.0F);
                tMaterial.setBaseDurability(0, 100);
                tMaterial.setMaxModifiersOnPart(0, 2);
            } else if (tMaterial.iInternalName.equals("armory.Bronze")) {
                tMaterial.setBaseDamageAbsorption(0, 1.0F);
                tMaterial.setBaseDurability(0, 100);
                tMaterial.setMaxModifiersOnPart(0, 0);
            } else if (tMaterial.iInternalName.equals("tconstruct.Ardite")) {
                tMaterial.setBaseDamageAbsorption(0, 2.5F);
                tMaterial.setBaseDurability(0, 100);
                tMaterial.setMaxModifiersOnPart(0, 2);
            } else if (tMaterial.iInternalName.equals("tconstruct.Cobalt")) {
                tMaterial.setBaseDamageAbsorption(0, 3F);
                tMaterial.setBaseDurability(0, 140);
                tMaterial.setMaxModifiersOnPart(0, 2);
            } else if (tMaterial.iInternalName.equals("vanilla.Obsidian")) {
                tMaterial.setBaseDamageAbsorption(0, 3F);
                tMaterial.setBaseDurability(0, 200);
                tMaterial.setMaxModifiersOnPart(0, 2);
            } else if (tMaterial.iInternalName.equals("tconstruct.Manyullun")) {
                tMaterial.setBaseDamageAbsorption(0, 3.5F);
                tMaterial.setBaseDurability(0, 250);
                tMaterial.setMaxModifiersOnPart(0, 3);
            } else {
                MinecraftForge.EVENT_BUS.post(new ModifyMaterialEvent(0, ARegistry.iInstance.getMaterialID(tMaterial)));
            }
        }


        ARegistry.iInstance.addArmorMapping(tHelmet);
    }

    private static void registerChestplate()
    {
        ArmorCore tChestPlate = new ArmorCore("armory.Chestplate", 1);

        for(ArmorMaterial tMaterial   :ARegistry.iInstance.getArmorMaterials()) {
            if (tMaterial.iInternalName.equals("vanilla.Iron")) {
                tMaterial.setBaseDamageAbsorption(1, 2.0F);
                tMaterial.setBaseDurability(1, 50);
                tMaterial.setMaxModifiersOnPart(1, 1);
            } else if (tMaterial.iInternalName.equals("vanilla.Chain")) {
                tMaterial.setBaseDamageAbsorption(1, 2.5F);
                tMaterial.setBaseDurability(1, 60);
                tMaterial.setMaxModifiersOnPart(1, 1);
            } else if (tMaterial.iInternalName.equals("tconstruct.Alumite")) {
                tMaterial.setBaseDamageAbsorption(1, 2.5F);
                tMaterial.setBaseDurability(1, 100);
                tMaterial.setMaxModifiersOnPart(1, 2);
            } else if (tMaterial.iInternalName.equals("armory.Bronze")) {
                tMaterial.setBaseDamageAbsorption(1, 1.5F);
                tMaterial.setBaseDurability(1, 100);
                tMaterial.setMaxModifiersOnPart(1, 0);
            } else if (tMaterial.iInternalName.equals("tconstruct.Ardite")) {
                tMaterial.setBaseDamageAbsorption(1, 3.0F);
                tMaterial.setBaseDurability(1, 100);
                tMaterial.setMaxModifiersOnPart(1, 2);
            } else if (tMaterial.iInternalName.equals("tconstruct.Cobalt")) {
                tMaterial.setBaseDamageAbsorption(1, 3.5F);
                tMaterial.setBaseDurability(1, 140);
                tMaterial.setMaxModifiersOnPart(1, 2);
            } else if (tMaterial.iInternalName.equals("vanilla.Obsidian")) {
                tMaterial.setBaseDamageAbsorption(1, 3.5F);
                tMaterial.setBaseDurability(1, 200);
                tMaterial.setMaxModifiersOnPart(1, 2);
            } else if (tMaterial.iInternalName.equals("tconstruct.Manyullun")) {
                tMaterial.setBaseDamageAbsorption(1, 4.0F);
                tMaterial.setBaseDurability(1, 250);
                tMaterial.setMaxModifiersOnPart(1, 3);
            } else {
                MinecraftForge.EVENT_BUS.post(new ModifyMaterialEvent(1, ARegistry.iInstance.getMaterialID(tMaterial)));
            }
        }

        ARegistry.iInstance.addArmorMapping(tChestPlate);
    }

    private static void registerLeggins()
    {
        ArmorCore tLeggings = new ArmorCore("armory.Leggins", 2);
        
        for(ArmorMaterial tMaterial   :ARegistry.iInstance.getArmorMaterials()) {
            if (tMaterial.iInternalName.equals("vanilla.Iron")) {
                tMaterial.setBaseDamageAbsorption(2, 1.5F);
                tMaterial.setBaseDurability(2, 50);
                tMaterial.setMaxModifiersOnPart(2, 1);
            } else if (tMaterial.iInternalName.equals("vanilla.Chain")) {
                tMaterial.setBaseDamageAbsorption(2, 2.0F);
                tMaterial.setBaseDurability(2, 60);
                tMaterial.setMaxModifiersOnPart(2, 1);
            } else if (tMaterial.iInternalName.equals("tconstruct.Alumite")) {
                tMaterial.setBaseDamageAbsorption(2, 2.0F);
                tMaterial.setBaseDurability(2, 100);
                tMaterial.setMaxModifiersOnPart(2, 2);
            } else if (tMaterial.iInternalName.equals("armory.Bronze")) {
                tMaterial.setBaseDamageAbsorption(2, 1.0F);
                tMaterial.setBaseDurability(2, 100);
                tMaterial.setMaxModifiersOnPart(2, 0);
            } else if (tMaterial.iInternalName.equals("tconstruct.Ardite")) {
                tMaterial.setBaseDamageAbsorption(2, 2.5F);
                tMaterial.setBaseDurability(2, 100);
                tMaterial.setMaxModifiersOnPart(2, 2);
            } else if (tMaterial.iInternalName.equals("tconstruct.Cobalt")) {
                tMaterial.setBaseDamageAbsorption(2, 3F);
                tMaterial.setBaseDurability(2, 140);
                tMaterial.setMaxModifiersOnPart(2, 2);
            } else if (tMaterial.iInternalName.equals("vanilla.Obsidian")) {
                tMaterial.setBaseDamageAbsorption(2, 3F);
                tMaterial.setBaseDurability(2, 200);
                tMaterial.setMaxModifiersOnPart(2, 2);
            } else if (tMaterial.iInternalName.equals("tconstruct.Manyullun")) {
                tMaterial.setBaseDamageAbsorption(2, 3.5F);
                tMaterial.setBaseDurability(2, 250);
                tMaterial.setMaxModifiersOnPart(2, 3);
            } else {
                MinecraftForge.EVENT_BUS.post(new ModifyMaterialEvent(2, ARegistry.iInstance.getMaterialID(tMaterial)));
            }
        }

        ARegistry.iInstance.addArmorMapping(tLeggings);
    }

    private static void registerShoes()
    {
        ArmorCore tShoes = new ArmorCore("armory.Shoes", 3);

        for(ArmorMaterial tMaterial   :ARegistry.iInstance.getArmorMaterials()) {
            if (tMaterial.iInternalName.equals("vanilla.Iron")) {
                tMaterial.setBaseDamageAbsorption(3, 1.0F);
                tMaterial.setBaseDurability(3, 50);
                tMaterial.setMaxModifiersOnPart(3, 1);
            } else if (tMaterial.iInternalName.equals("vanilla.Chain")) {
                tMaterial.setBaseDamageAbsorption(3, 1.5F);
                tMaterial.setBaseDurability(3, 60);
                tMaterial.setMaxModifiersOnPart(3, 1);
            } else if (tMaterial.iInternalName.equals("tconstruct.Alumite")) {
                tMaterial.setBaseDamageAbsorption(3, 1.5F);
                tMaterial.setBaseDurability(3, 100);
                tMaterial.setMaxModifiersOnPart(3, 2);
            } else if (tMaterial.iInternalName.equals("armory.Bronze")) {
                tMaterial.setBaseDamageAbsorption(3, 0.5F);
                tMaterial.setBaseDurability(3, 100);
                tMaterial.setMaxModifiersOnPart(3, 0);
            } else if (tMaterial.iInternalName.equals("tconstruct.Ardite")) {
                tMaterial.setBaseDamageAbsorption(3, 2.0F);
                tMaterial.setBaseDurability(3, 100);
                tMaterial.setMaxModifiersOnPart(3, 2);
            } else if (tMaterial.iInternalName.equals("tconstruct.Cobalt")) {
                tMaterial.setBaseDamageAbsorption(3, 2.5F);
                tMaterial.setBaseDurability(3, 140);
                tMaterial.setMaxModifiersOnPart(3, 2);
            } else if (tMaterial.iInternalName.equals("vanilla.Obsidian")) {
                tMaterial.setBaseDamageAbsorption(3, 2.5F);
                tMaterial.setBaseDurability(3, 200);
                tMaterial.setMaxModifiersOnPart(3, 2);
            } else if (tMaterial.iInternalName.equals("tconstruct.Manyullun")) {
                tMaterial.setBaseDamageAbsorption(3, 3.0F);
                tMaterial.setBaseDurability(3, 250);
                tMaterial.setMaxModifiersOnPart(3, 3);
            } else {
                MinecraftForge.EVENT_BUS.post(new ModifyMaterialEvent(3, ARegistry.iInstance.getMaterialID(tMaterial)));
            }
        }

        ARegistry.iInstance.addArmorMapping(tShoes);
    }

    @SideOnly(Side.CLIENT)
    private static void registerRenderMappings()
    {
        AClientRegistry.registerRenderMappings();
    }

    private static void prepareGame()
    {
        //TODO: Implement registration of items into the game.
    }
}
