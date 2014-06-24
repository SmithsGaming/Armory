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
import com.Orion.Armory.Common.Armor.Modifiers.ArmorModifier;
import com.Orion.Armory.Common.Events.*;
import com.Orion.OrionsBelt.Client.CustomResource;
import com.Orion.OrionsBelt.Client.Render.RenderMultiLayeredArmor;
import com.Orion.OrionsBelt.Client.Render.RendererItemMultiLayeredArmor;
import com.Orion.OrionsBelt.OrionsBelt;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.MinecraftForgeClient;
import net.minecraftforge.common.MinecraftForge;

import java.util.ArrayList;
import java.util.HashMap;

public class ArmoryInitializer
{
    public ArmoryInitializer iInstance;

    public static void Initialize(Side pSide)
    {
        if (Armory.instance.iIsInitialized)
        {
            return;
        }

        ArmorBuilder.init();

        registerMaterials();
        //registerUpgrades();
        registerModifiers();
        registerArmors();

        if (pSide == Side.CLIENT) {
            registerRenderMappings();
        }

        prepareGame();

        Armory.instance.iIsInitialized = true;
    }

    private static void registerMaterials()
    {
        registerIron();
        registerChain();
        registerObsidian();
        registerAlumite();
        registerArdite();
        registerCobalt();
        registerManyullun();
        registerBronze();

        MinecraftForge.EVENT_BUS.post(new RegisterMaterialsEvent());
    }

    private static void registerIron()
    {
        ArmorMaterial tIron = new ArmorMaterial("vanilla.Iron", "Iron", "", true, new HashMap<Integer, Float>(), new HashMap<Integer, Integer>(), new HashMap<Integer, Integer>(), new HashMap<Integer, Boolean>());
        CustomResource tHelmetResource = new CustomResource("vanilla.Iron.Base.Helmet", "tconstruct-armory:multiarmor/base/armory.Helmet_Base", "tconstruct-armory:textures/models/multiarmor/base/Base.png", 255, 255, 255);
        CustomResource tChestPlateResource = new CustomResource("vanilla.Iron.Base.Chestplate", "tconstruct-armory:multiarmor/base/armory.Chestplate_Base", "tconstruct-armory:textures/models/multiarmor/base/Base.png", 255, 255, 255);
        CustomResource tLegginsResource = new CustomResource("vanilla.Iron.Base.Leggins", "tconstruct-armory:multiarmor/base/armory.Leggins_Base", "tconstruct-armory:textures/models/multiarmor/base/Base.png", 255, 255, 255);
        CustomResource tShoesResource = new CustomResource("vanilla.Iron.Base.Shoes", "tconstruct-armory:multiarmor/base/armory.Shoes_Base", "tconstruct-armory:textures/models/multiarmor/base/Base.png", 255, 255, 255);
        
        tIron.registerResource(tHelmetResource);
        tIron.registerResource(tChestPlateResource);
        tIron.registerResource(tLegginsResource);
        tIron.registerResource(tShoesResource);
        
        ARegistry.iInstance.registerMaterial(tIron, true);
    }

    private static void registerChain()
    {
        ArmorMaterial tChain = new ArmorMaterial("vanilla.Chain", "Steel", "", true, new HashMap<Integer, Float>(), new HashMap<Integer, Integer>(), new HashMap<Integer, Integer>(), new HashMap<Integer, Boolean>());
        CustomResource tHelmetResource = new CustomResource("vanilla.Chain.Base.Helmet", "tconstruct-armory:multiarmor/base/armory.Helmet_Base", "tconstruct-armory:textures/models/multiarmor/base/Base.png", 98, 98, 98);
        CustomResource tChestPlateResource = new CustomResource("vanilla.Chain.Base.Chestplate", "tconstruct-armory:multiarmor/base/armory.Chestplate_Base", "tconstruct-armory:textures/models/multiarmor/base/Base.png", 98, 98, 98);
        CustomResource tLegginsResource = new CustomResource("vanilla.Chain.Base.Leggins", "tconstruct-armory:multiarmor/base/armory.Leggins_Base", "tconstruct-armory:textures/models/multiarmor/base/Base.png", 98, 98, 98);
        CustomResource tShoesResource = new CustomResource("vanilla.Chain.Base.Shoes", "tconstruct-armory:multiarmor/base/armory.Shoes_Base", "tconstruct-armory:textures/models/multiarmor/base/Base.png", 98, 98, 98);

        tChain.registerResource(tHelmetResource);
        tChain.registerResource(tChestPlateResource);
        tChain.registerResource(tLegginsResource);
        tChain.registerResource(tShoesResource);
        
        ARegistry.iInstance.registerMaterial(tChain, true);
    }

    private static void registerObsidian()
    {
        ArmorMaterial tObsidian = new ArmorMaterial("vanilla.Obsidian", "Steel", "", true, new HashMap<Integer, Float>(), new HashMap<Integer, Integer>(), new HashMap<Integer, Integer>(), new HashMap<Integer, Boolean>());
        CustomResource tHelmetResource = new CustomResource("vanilla.Obsidian.Base.Helmet", "tconstruct-armory:multiarmor/base/armory.Helmet_Base", "tconstruct-armory:textures/models/multiarmor/base/Base.png", 86, 63, 124);
        CustomResource tChestPlateResource = new CustomResource("vanilla.Obsidian.Base.Chestplate", "tconstruct-armory:multiarmor/base/armory.Chestplate_Base", "tconstruct-armory:textures/models/multiarmor/base/Base.png", 86, 63, 124);
        CustomResource tLegginsResource = new CustomResource("vanilla.Obsidian.Base.Leggins", "tconstruct-armory:multiarmor/base/armory.Leggins_Base", "tconstruct-armory:textures/models/multiarmor/base/Base.png", 86, 63, 124);
        CustomResource tShoesResource = new CustomResource("vanilla.Obsidian.Base.Shoes", "tconstruct-armory:multiarmor/base/armory.Shoes_Base", "tconstruct-armory:textures/models/multiarmor/base/Base.png", 86, 63, 124);

        tObsidian.registerResource(tHelmetResource);
        tObsidian.registerResource(tChestPlateResource);
        tObsidian.registerResource(tLegginsResource);
        tObsidian.registerResource(tShoesResource);
        
        ARegistry.iInstance.registerMaterial(tObsidian, false);
    }

    private static void registerAlumite()
    {
        ArmorMaterial tAlumite = new ArmorMaterial("tconstruct.Alumite", "Steel", "", true, new HashMap<Integer, Float>(), new HashMap<Integer, Integer>(), new HashMap<Integer, Integer>(), new HashMap<Integer, Boolean>());
        CustomResource tHelmetResource = new CustomResource("tconstruct.Alumite.Base.Helmet", "tconstruct-armory:multiarmor/base/armory.Helmet_Base", "tconstruct-armory:textures/models/multiarmor/base/Base.png", 244, 204, 236);
        CustomResource tChestPlateResource = new CustomResource("tconstruct.Alumite.Base.Chestplate", "tconstruct-armory:multiarmor/base/armory.Chestplate_Base", "tconstruct-armory:textures/models/multiarmor/base/Base.png", 244, 204, 236);
        CustomResource tLegginsResource = new CustomResource("tconstruct.Alumite.Base.Leggins", "tconstruct-armory:multiarmor/base/armory.Leggins_Base", "tconstruct-armory:textures/models/multiarmor/base/Base.png", 244, 204, 236);
        CustomResource tShoesResource = new CustomResource("tconstruct.Alumite.Base.Shoes", "tconstruct-armory:multiarmor/base/armory.Shoes_Base", "tconstruct-armory:textures/models/multiarmor/base/Base.png", 244, 204, 236);

        tAlumite.registerResource(tHelmetResource);
        tAlumite.registerResource(tChestPlateResource);
        tAlumite.registerResource(tLegginsResource);
        tAlumite.registerResource(tShoesResource);

        ARegistry.iInstance.registerMaterial(tAlumite, true);
    }

    private static void registerArdite()
    {
        ArmorMaterial tArdite = new ArmorMaterial("tconstruct.Ardite", "Steel", "", true, new HashMap<Integer, Float>(), new HashMap<Integer, Integer>(), new HashMap<Integer, Integer>(), new HashMap<Integer, Boolean>());
        CustomResource tHelmetResource = new CustomResource("tconstruct.Ardite.Base.Helmet", "tconstruct-armory:multiarmor/base/armory.Helmet_Base", "tconstruct-armory:textures/models/multiarmor/base/Base.png", 137, 44, 0);
        CustomResource tChestPlateResource = new CustomResource("tconstruct.Ardite.Base.Chestplate", "tconstruct-armory:multiarmor/base/armory.Chestplate_Base", "tconstruct-armory:textures/models/multiarmor/base/Base.png", 137, 44, 0);
        CustomResource tLegginsResource = new CustomResource("tconstruct.Ardite.Base.Leggins", "tconstruct-armory:multiarmor/base/armory.Leggins_Base", "tconstruct-armory:textures/models/multiarmor/base/Base.png", 137, 44, 0);
        CustomResource tShoesResource = new CustomResource("tconstruct.Ardite.Base.Shoes", "tconstruct-armory:multiarmor/base/armory.Shoes_Base", "tconstruct-armory:textures/models/multiarmor/base/Base.png", 137, 44, 0);

        tArdite.registerResource(tHelmetResource);
        tArdite.registerResource(tChestPlateResource);
        tArdite.registerResource(tLegginsResource);
        tArdite.registerResource(tShoesResource);

        ARegistry.iInstance.registerMaterial(tArdite, true);
    }

    private static void registerCobalt()
    {
        ArmorMaterial tCobalt = new ArmorMaterial("tconstruct.Cobalt", "Steel", "", true, new HashMap<Integer, Float>(), new HashMap<Integer, Integer>(), new HashMap<Integer, Integer>(), new HashMap<Integer, Boolean>());
        CustomResource tHelmetResource = new CustomResource("tconstruct.Cobalt.Base.Helmet", "tconstruct-armory:multiarmor/base/armory.Helmet_Base", "tconstruct-armory:textures/models/multiarmor/base/Base.png", 29, 98, 184);
        CustomResource tChestPlateResource = new CustomResource("tconstruct.Cobalt.Base.Chestplate", "tconstruct-armory:multiarmor/base/armory.Chestplate_Base", "tconstruct-armory:textures/models/multiarmor/base/Base.png", 29, 98, 184);
        CustomResource tLegginsResource = new CustomResource("tconstruct.Cobalt.Base.Leggins", "tconstruct-armory:multiarmor/base/armory.Leggins_Base", "tconstruct-armory:textures/models/multiarmor/base/Base.png", 29, 98, 184);
        CustomResource tShoesResource = new CustomResource("tconstruct.Cobalt.Base.Shoes", "tconstruct-armory:multiarmor/base/armory.Shoes_Base", "tconstruct-armory:textures/models/multiarmor/base/Base.png", 29, 98, 184);

        tCobalt.registerResource(tHelmetResource);
        tCobalt.registerResource(tChestPlateResource);
        tCobalt.registerResource(tLegginsResource);
        tCobalt.registerResource(tShoesResource);

        ARegistry.iInstance.registerMaterial(tCobalt, true);
    }

    private static void registerManyullun()
    {
        ArmorMaterial tManyullun = new ArmorMaterial("tconstruct.Manyullun", "Steel", "", true, new HashMap<Integer, Float>(), new HashMap<Integer, Integer>(), new HashMap<Integer, Integer>(), new HashMap<Integer, Boolean>());
        CustomResource tHelmetResource = new CustomResource("tconstruct.Manyullun.Base.Helmet", "tconstruct-armory:multiarmor/base/armory.Helmet_Base", "tconstruct-armory:textures/models/multiarmor/base/Base.png", 92, 38, 138);
        CustomResource tChestPlateResource = new CustomResource("tconstruct.Manyullun.Base.Chestplate", "tconstruct-armory:multiarmor/base/armory.Chestplate_Base", "tconstruct-armory:textures/models/multiarmor/base/Base.png", 92, 38, 138);
        CustomResource tLegginsResource = new CustomResource("tconstruct.Manyullun.Base.Leggins", "tconstruct-armory:multiarmor/base/armory.Leggins_Base", "tconstruct-armory:textures/models/multiarmor/base/Base.png", 92, 38, 138);
        CustomResource tShoesResource = new CustomResource("tconstruct.Manyullun.Base.Shoes", "tconstruct-armory:multiarmor/base/armory.Shoes_Base", "tconstruct-armory:textures/models/multiarmor/base/Base.png", 92, 38, 138);

        tManyullun.registerResource(tHelmetResource);
        tManyullun.registerResource(tChestPlateResource);
        tManyullun.registerResource(tLegginsResource);
        tManyullun.registerResource(tShoesResource);

        ARegistry.iInstance.registerMaterial(tManyullun, false);
    }

    private static void registerBronze()
    {
        ArmorMaterial tBronze = new ArmorMaterial("armory.Bronze", "Steel", "", true, new HashMap<Integer, Float>(), new HashMap<Integer, Integer>(), new HashMap<Integer, Integer>(), new HashMap<Integer, Boolean>());
        CustomResource tHelmetResource = new CustomResource("armory.Bronze.Base.Helmet", "tconstruct-armory:multiarmor/base/armory.Helmet_Base", "tconstruct-armory:textures/models/multiarmor/base/Base.png", 168, 117, 68);
        CustomResource tChestPlateResource = new CustomResource("armory.Bronze.Base.Chestplate", "tconstruct-armory:multiarmor/base/armory.Chestplate_Base", "tconstruct-armory:textures/models/multiarmor/base/Base.png", 168, 117, 68);
        CustomResource tLegginsResource = new CustomResource("armory.Bronze.Base.Leggins", "tconstruct-armory:multiarmor/base/armory.Leggins_Base", "tconstruct-armory:textures/models/multiarmor/base/Base.png", 168, 117, 68);
        CustomResource tShoesResource = new CustomResource("armory.Bronze.Base.Shoes", "tconstruct-armory:multiarmor/base/armory.Shoes_Base", "tconstruct-armory:textures/models/multiarmor/base/Base.png", 168, 117, 68);

        tBronze.registerResource(tHelmetResource);
        tBronze.registerResource(tChestPlateResource);
        tBronze.registerResource(tLegginsResource);
        tBronze.registerResource(tShoesResource);

        ARegistry.iInstance.registerMaterial(tBronze, true);
    }

    /*
    TODO: Implement Items for crafting
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
            CustomResource tResource = new CustomResource(tMaterial.iInternalName+".Helmet.TopHead", "tconstruct-armory:multiarmor/upgrades/armory.Helmet_TopHead", "tconstruct-armory:textures/models/multiarmor/upgrades/armory.Helmet_TopHead.png", tMaterial.getResource(0).getColor(0), tMaterial.getResource(0).getColor(1), tMaterial.getResource(0).getColor(2));
            ArmorUpgrade tTopHead = new ArmorUpgrade(ARegistry.iInstance.getMaterialID(tMaterial), 0, 0, tMaterial.iInternalName+".TopHead", "TopHead", "Head protection", "", tResource, 2.5F, 60, 1);
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
            CustomResource tLeftResource = new CustomResource(tMaterial.iInternalName+".Helmet.Protection.Ear.Left", "tconstruct-armory:multiarmor/upgrades/armory.Helmet_Protection_Ear_Left", "tconstruct-armory:textures/models/multiarmor/upgrades/armory.Helmet_Protection_Ear_Left.png", tMaterial.getResource(0).getColor(0), tMaterial.getResource(0).getColor(1), tMaterial.getResource(0).getColor(2));
            CustomResource tRightResource = new CustomResource(tMaterial.iInternalName+".Helmet.Protection.Ear.Right", "tconstruct-armory:multiarmor/upgrades/armory.Helmet_Protection_Ear_Right", "tconstruct-armory:textures/models/multiarmor/upgrades/armory.Helmet_Protection_Ear_Right.png", tMaterial.getResource(0).getColor(0), tMaterial.getResource(0).getColor(1), tMaterial.getResource(0).getColor(2));
            ArmorUpgrade tEarProtectionLeft = new ArmorUpgrade(ARegistry.iInstance.getMaterialID(tMaterial), 0, 1, tMaterial.iInternalName+".Protection.Ear.Left", "Helmet.Protection.Ear.Left","Ear protection left", "", tLeftResource, 0.5F, 20, 1);
            ArmorUpgrade tEarProtectionRight = new ArmorUpgrade(ARegistry.iInstance.getMaterialID(tMaterial), 0, 2, tMaterial.iInternalName+".Protection.Ear.Right", "Helmet.Protection.Ear.Right","Ear protection right", "", tRightResource, 0.5F, 20, 1);
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
            CustomResource tLeftResource = new CustomResource(tMaterial.iInternalName+".Chestplate.ShoulderPad.Left", "tconstruct-armory:multiarmor/upgrades/armory.Chestplate_ShoulderPad_Left", "tconstruct-armory:textures/models/multiarmor/upgrades/armory.Chestplate_ShoulderPad_Left.png", tMaterial.getResource(1).getColor(0), tMaterial.getResource(1).getColor(1), tMaterial.getResource(1).getColor(2));
            CustomResource tRightResource = new CustomResource(tMaterial.iInternalName+".Chestplate.ShoulderPad.Right", "tconstruct-armory:multiarmor/upgrades/armory.Chestplate_ShoulderPad_Right", "tconstruct-armory:textures/models/multiarmor/upgrades/armory.Chestplate_ShoulderPad_Right.png", tMaterial.getResource(1).getColor(0), tMaterial.getResource(1).getColor(1), tMaterial.getResource(1).getColor(2));
            ArmorUpgrade tShoulderPadLeft = new ArmorUpgrade(ARegistry.iInstance.getMaterialID(tMaterial), 1, 0, tMaterial.iInternalName+".ShoulderPad.Left","Chestplate.ShoulderPad.Left",  "Shoulder pad left", "", tLeftResource, 1F, 50, 1);
            ArmorUpgrade tShoulderPadRight = new ArmorUpgrade(ARegistry.iInstance.getMaterialID(tMaterial), 1, 1, tMaterial.iInternalName+".ShoulderPad.Right", "Chestplate.ShoulderPad.Right", "Shoulder pad right", "", tRightResource, 1F, 50, 1);
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
            CustomResource tLeftResource = new CustomResource(tMaterial.iInternalName+".Chestplate.Protection.Front.Left", "tconstruct-armory:multiarmor/upgrades/armory.Chestplate_Protection_Front_Left", "tconstruct-armory:textures/models/multiarmor/upgrades/armory.Chestplate_Protection_Front_Left.png", tMaterial.getResource(1).getColor(0), tMaterial.getResource(1).getColor(1), tMaterial.getResource(1).getColor(2));
            CustomResource tRightResource = new CustomResource(tMaterial.iInternalName+".Chestplate.Protection.Front.Right", "tconstruct-armory:multiarmor/upgrades/armory.Chestplate_Protection_Front_Right", "tconstruct-armory:textures/models/multiarmor/upgrades/armory.Chestplate_Protection_Front_Right.png", tMaterial.getResource(1).getColor(0), tMaterial.getResource(1).getColor(1), tMaterial.getResource(1).getColor(2));
            ArmorUpgrade tFrontChestProtectionLeft = new ArmorUpgrade(ARegistry.iInstance.getMaterialID(tMaterial), 1, 3, tMaterial.iInternalName+".Protection.Chest.Front.Left", "Chestplate.Protection.Front.Left","Front chest protection left", "", tLeftResource, 2F, 100, 1);
            ArmorUpgrade tFrontChestProtectionRight = new ArmorUpgrade(ARegistry.iInstance.getMaterialID(tMaterial), 1, 4, tMaterial.iInternalName+".Protection.Chest.Front.Right", "Chestplate.Protection.Front.Right", "Front chest protection right", "", tRightResource, 2F, 100, 1);
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
            CustomResource tLeftResource = new CustomResource(tMaterial.iInternalName+".Chestplate.Protection.Back.Left", "tconstruct-armory:multiarmor/upgrades/armory.Chestplate_Protection_Back_Left", "tconstruct-armory:textures/models/multiarmor/upgrades/armory.Chestplate_Protection_Back_Left.png", tMaterial.getResource(1).getColor(0), tMaterial.getResource(1).getColor(1), tMaterial.getResource(1).getColor(2));
            CustomResource tRightResource = new CustomResource(tMaterial.iInternalName+".Chestplate.Protection.Back.Right", "tconstruct-armory:multiarmor/upgrades/armory.Chestplate_Protection_Back_Right", "tconstruct-armory:textures/models/multiarmor/upgrades/armory.Chestplate_Protection_Back_Right.png", tMaterial.getResource(1).getColor(0), tMaterial.getResource(1).getColor(1), tMaterial.getResource(1).getColor(2));
            ArmorUpgrade tBackChestProtectionLeft = new ArmorUpgrade(ARegistry.iInstance.getMaterialID(tMaterial), 1, 5, tMaterial.iInternalName+".Protection.Chest.Back.Left", "Chestplate.Protection.Back.Left","Back chest protection left", "", tLeftResource, 2F, 150, 1);
            ArmorUpgrade tBackChestProtectionRight = new ArmorUpgrade(ARegistry.iInstance.getMaterialID(tMaterial), 1, 6, tMaterial.iInternalName+".Protection.Chest.Back.Right", "Chestplate.Protection.Back.Right","Back chest protection right", "", tRightResource, 2F, 150, 1);
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
            CustomResource tLeftResource = new CustomResource(tMaterial.iInternalName+".Leggins.Protection.Front.Left", "tconstruct-armory:multiarmor/upgrades/armory.Leggins_Protection_Front_Left", "tconstruct-armory:textures/models/multiarmor/upgrades/armory.Leggins_Protection_Front_Left.png", tMaterial.getResource(2).getColor(0), tMaterial.getResource(2).getColor(1), tMaterial.getResource(2).getColor(2));
            CustomResource tRightResource = new CustomResource(tMaterial.iInternalName+".Leggins.Protection.Front.Right", "tconstruct-armory:multiarmor/upgrades/armory.Leggins_Protection_Front_Right", "tconstruct-armory:textures/models/multiarmor/upgrades/armory.Leggins_Protection_Front_Right.png", tMaterial.getResource(2).getColor(0), tMaterial.getResource(2).getColor(1), tMaterial.getResource(2).getColor(2));
            ArmorUpgrade tFrontLeggingsProtectionLeft = new ArmorUpgrade(ARegistry.iInstance.getMaterialID(tMaterial), 2, 0, tMaterial.iInternalName+".Protection.Leggings.Front.Left", "Leggins.Protection.Front.Left","Front leg protection left", "", tLeftResource, 1.5F, 125, 1);
            ArmorUpgrade tFrontLeggingsProtectionRight = new ArmorUpgrade(ARegistry.iInstance.getMaterialID(tMaterial), 2, 1, tMaterial.iInternalName+".Protection.Leggings.Front.Right", "Leggins.Protection.Front.Right","Front leg protection right", "", tRightResource, 1.5F, 125, 1);
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
            CustomResource tLeftResource = new CustomResource(tMaterial.iInternalName+".Leggins.Protection.Back.Left", "tconstruct-armory:multiarmor/upgrades/armory.Leggins_Protection_Back_Left", "tconstruct-armory:textures/models/multiarmor/upgrades/armory.Leggins_Protection_Back_Left.png", tMaterial.getResource(2).getColor(0), tMaterial.getResource(2).getColor(1), tMaterial.getResource(2).getColor(2));
            CustomResource tRightResource = new CustomResource(tMaterial.iInternalName+".Leggins.Protection.Back.Right", "tconstruct-armory:multiarmor/upgrades/armory.Leggins_Protection_Back_Right", "tconstruct-armory:textures/models/multiarmor/upgrades/armory.Leggins_Protection_Back_Right.png", tMaterial.getResource(2).getColor(0), tMaterial.getResource(2).getColor(1), tMaterial.getResource(2).getColor(2));
            ArmorUpgrade tBackLeggingsProtectionLeft = new ArmorUpgrade(ARegistry.iInstance.getMaterialID(tMaterial), 2, 5, tMaterial.iInternalName+".Protection.Leggings.Back.Left","Leggins.Protection.Back.Left", "Back leg protection left", "", tLeftResource, 2F, 150, 1);
            ArmorUpgrade tBackLeggingsProtectionRight = new ArmorUpgrade(ARegistry.iInstance.getMaterialID(tMaterial), 2, 6, tMaterial.iInternalName+".Protection.Leggings.Back.Right","Leggins.Protection.Back.Right", "Back leg protection right", "", tRightResource, 2F, 150, 1);
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
            CustomResource tLeftResource = new CustomResource(tMaterial.iInternalName+".Shoes.Protection.Left", "tconstruct-armory:multiarmor/upgrades/armory.Shoes_Protection_Left", "tconstruct-armory:textures/models/multiarmor/upgrades/armory.Shoes_Protection_Left.png", tMaterial.getResource(3).getColor(0), tMaterial.getResource(3).getColor(1), tMaterial.getResource(3).getColor(2));
            CustomResource tRightResource = new CustomResource(tMaterial.iInternalName+".Shoes.Protection.Right", "tconstruct-armory:multiarmor/upgrades/armory.Shoes_Protection_Right", "tconstruct-armory:textures/models/multiarmor/upgrades/armory.Shoes_Protection_Right.png", tMaterial.getResource(3).getColor(0), tMaterial.getResource(3).getColor(1), tMaterial.getResource(3).getColor(2));
            ArmorUpgrade tShoeProtectionLeft = new ArmorUpgrade(ARegistry.iInstance.getMaterialID(tMaterial), 3, 0, tMaterial.iInternalName+".Protection.Shoe.Left", "Shoes.Protection.Left","Shoe protection left", "", tLeftResource, 1F, 50, 1);
            ArmorUpgrade tShoeProtectionRight = new ArmorUpgrade(ARegistry.iInstance.getMaterialID(tMaterial), 3, 1, tMaterial.iInternalName+".Protection.Shoe.Right", "Shoes.Protection.Right", "Shoe protection right", "", tRightResource, 1F, 50, 1);
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

    */
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

        for(ArmorCore tCore: ARegistry.iInstance.getAllArmorMappings())
        {
            MinecraftForgeClient.registerItemRenderer(tCore, new RendererItemMultiLayeredArmor());
            OrionsBelt.iInstance.iRenderRegistry.addNewRenderer(tCore, new RenderMultiLayeredArmor());
        }
    }

    private static void prepareGame()
    {
        for(ArmorCore tCore: ARegistry.iInstance.getAllArmorMappings())
        {
            GameRegistry.registerItem(tCore, tCore.iInternalName, "armory");
        }

        GameRegistry.addRecipe(new ArmorChangeRecipe());
    }
}
