package com.Orion.Armory.Common.Logic;
/*
 *   ArmoryInitializer
 *   Created by: Orion
 *   Created on: 17-9-2014
 */

import com.Orion.Armory.Client.Util.Colors;
import com.Orion.Armory.Common.ARegistry;
import com.Orion.Armory.Common.Armor.Core.ArmorMaterial;
import com.Orion.Armory.Common.Armor.Core.MultiLayeredArmor;
import com.Orion.Armory.Common.Armor.TierChain.ArmorChain;
import com.Orion.Armory.Common.Armor.TierChain.ArmorChainUpgrade;
import com.Orion.Armory.Common.Events.ModifyMaterialEvent;
import com.Orion.Armory.Common.Events.RegisterArmorEvent;
import com.Orion.Armory.Common.Events.RegisterMaterialsEvent;
import com.Orion.Armory.Common.Events.RegisterUpgradesEvent;
import com.Orion.Armory.Util.References;
import com.Orion.Armory.Util.References.InternalNames;
import com.Orion.Armory.Common.Armor.Core.ArmorAddonPosition;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraftforge.common.MinecraftForge;

import java.util.HashMap;

public class ArmoryInitializer
{
    public static void InitializeServer()
    {
        Initialize();
        prepareGame();
    }

    protected static void Initialize()
    {
        registerArmorPieces();
        registerMaterials();
        registerAddonPositions();
        registerUpgrades();
        modifyMaterials();
    }
    
    private static void registerArmorPieces()
    {
        ARegistry.iInstance.addArmorMapping(new ArmorChain(InternalNames.Armor.HELMET, 0));
        ARegistry.iInstance.addArmorMapping(new ArmorChain(InternalNames.Armor.CHESTPLATE, 1));
        ARegistry.iInstance.addArmorMapping(new ArmorChain(InternalNames.Armor.LEGGINGS, 2));
        ARegistry.iInstance.addArmorMapping(new ArmorChain(InternalNames.Armor.SHOES, 3));

        MinecraftForge.EVENT_BUS.register(new RegisterArmorEvent());
    }
    
    private static void registerMaterials()
    {
        ArmorMaterial tIron = new ArmorMaterial(InternalNames.Materials.Vanilla.IRON, "Iron", "", true, new HashMap<String, Float>(), new HashMap<String, Integer>(), new HashMap<String, Integer>(), new HashMap<String, Boolean>(), Colors.IRON);
        ArmorMaterial tChain = new ArmorMaterial(InternalNames.Materials.Vanilla.CHAIN, "Steel", "", true, new HashMap<String, Float>(), new HashMap<String, Integer>(), new HashMap<String, Integer>(), new HashMap<String, Boolean>(), Colors.CHAIN);
        ArmorMaterial tObsidian = new ArmorMaterial(InternalNames.Materials.Vanilla.OBSIDIAN, "Obsidian", "", true, new HashMap<String, Float>(), new HashMap<String, Integer>(), new HashMap<String, Integer>(), new HashMap<String, Boolean>(), Colors.OBSIDIAN);
        ArmorMaterial tAlumite = new ArmorMaterial(InternalNames.Materials.ModMaterials.TinkersConstruct.ALUMITE, "Alumite", "", true, new HashMap<String, Float>(), new HashMap<String, Integer>(), new HashMap<String, Integer>(), new HashMap<String, Boolean>(), Colors.ALUMITE);
        ArmorMaterial tArdite = new ArmorMaterial(InternalNames.Materials.ModMaterials.TinkersConstruct.ARDITE, "Ardite", "", true, new HashMap<String, Float>(), new HashMap<String, Integer>(), new HashMap<String, Integer>(), new HashMap<String, Boolean>(), Colors.ARDITE);
        ArmorMaterial tCobalt = new ArmorMaterial(InternalNames.Materials.ModMaterials.TinkersConstruct.COBALT, "Cobalt", "", true, new HashMap<String, Float>(), new HashMap<String, Integer>(), new HashMap<String, Integer>(), new HashMap<String, Boolean>(), Colors.COBALT);
        ArmorMaterial tManyullun = new ArmorMaterial(InternalNames.Materials.ModMaterials.TinkersConstruct.MANYULLUN, "Manyullun", "", true, new HashMap<String, Float>(), new HashMap<String, Integer>(), new HashMap<String, Integer>(), new HashMap<String, Boolean>(), Colors.MANYULLUN);
        ArmorMaterial tBronze = new ArmorMaterial(InternalNames.Materials.Common.BRONZE, "Bronze", "", true, new HashMap<String, Float>(), new HashMap<String, Integer>(), new HashMap<String, Integer>(), new HashMap<String, Boolean>(), Colors.BRONZE);
        
        ARegistry.iInstance.registerMaterial(tIron);
        ARegistry.iInstance.registerMaterial(tChain);
        ARegistry.iInstance.registerMaterial(tObsidian);
        ARegistry.iInstance.registerMaterial(tAlumite);
        ARegistry.iInstance.registerMaterial(tArdite);
        ARegistry.iInstance.registerMaterial(tCobalt);
        ARegistry.iInstance.registerMaterial(tManyullun);
        ARegistry.iInstance.registerMaterial(tBronze);

        MinecraftForge.EVENT_BUS.register(new RegisterMaterialsEvent());
    }
    
    private static void registerAddonPositions()
    {
        //Registering the positions to the helmet
        ArmorChain tHelmet = (ArmorChain) ARegistry.iInstance.getArmorMapping(InternalNames.Armor.HELMET);
        tHelmet.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Helmet.TOP, InternalNames.Armor.HELMET, 1));
        tHelmet.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Helmet.LEFT, InternalNames.Armor.HELMET, 1));
        tHelmet.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Helmet.RIGHT, InternalNames.Armor.HELMET, 1));
        tHelmet.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Helmet.AQUABREATHING, InternalNames.Armor.HELMET, 1));
        tHelmet.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Helmet.NIGHTSIGHT, InternalNames.Armor.HELMET, 1));
        tHelmet.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Helmet.THORNS, InternalNames.Armor.HELMET, 1));
        tHelmet.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Helmet.REINFORCED, InternalNames.Armor.HELMET, 1));
        tHelmet.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Helmet.AUTOREPAIR, InternalNames.Armor.HELMET, 1));
        tHelmet.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Helmet.ELECTRIC, InternalNames.Armor.HELMET, 1));
        
        //Registering the positions to the chestplate
        ArmorChain tChestplate = (ArmorChain) ARegistry.iInstance.getArmorMapping(InternalNames.Armor.CHESTPLATE);
        tChestplate.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Chestplate.SHOULDERLEFT, InternalNames.Armor.CHESTPLATE, 1));
        tChestplate.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Chestplate.SHOULDERRIGHT, InternalNames.Armor.CHESTPLATE, 1));
        tChestplate.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Chestplate.FRONTLEFT, InternalNames.Armor.CHESTPLATE, 1));
        tChestplate.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Chestplate.FRONTRIGHT, InternalNames.Armor.CHESTPLATE, 1));
        tChestplate.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Chestplate.BACKLEFT, InternalNames.Armor.CHESTPLATE, 1));
        tChestplate.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Chestplate.BACKRIGHT, InternalNames.Armor.CHESTPLATE, 1));
        tChestplate.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Chestplate.STRENGTH, InternalNames.Armor.CHESTPLATE, 1));
        tChestplate.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Chestplate.HASTE, InternalNames.Armor.CHESTPLATE, 1));
        tChestplate.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Chestplate.FLYING, InternalNames.Armor.CHESTPLATE, 1));
        tChestplate.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Chestplate.THORNS, InternalNames.Armor.CHESTPLATE, 1));
        tChestplate.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Chestplate.REINFORCED, InternalNames.Armor.CHESTPLATE, 1));
        tChestplate.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Chestplate.AUTOREPAIR, InternalNames.Armor.CHESTPLATE, 1));
        tChestplate.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Chestplate.ELECTRIC, InternalNames.Armor.CHESTPLATE, 1));
        
        //Registering the positions to the leggins
        ArmorChain tLeggins = (ArmorChain) ARegistry.iInstance.getArmorMapping(InternalNames.Armor.LEGGINGS);
        tLeggins.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Leggings.FRONTLEFT, InternalNames.Armor.LEGGINGS, 1));
        tLeggins.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Leggings.FRONTRIGHT, InternalNames.Armor.LEGGINGS, 1));
        tLeggins.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Leggings.BACKLEFT, InternalNames.Armor.LEGGINGS, 1));
        tLeggins.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Leggings.BACKRIGHT, InternalNames.Armor.LEGGINGS, 1));
        tLeggins.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Leggings.SPEED, InternalNames.Armor.LEGGINGS, 1));
        tLeggins.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Leggings.JUMPASSIST, InternalNames.Armor.LEGGINGS, 1));
        tLeggins.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Leggings.UPHILLASSIST, InternalNames.Armor.LEGGINGS, 1));
        tLeggins.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Leggings.THORNS, InternalNames.Armor.LEGGINGS, 1));
        tLeggins.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Leggings.REINFORCED, InternalNames.Armor.LEGGINGS, 1));
        tLeggins.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Leggings.AUTOREPAIR, InternalNames.Armor.LEGGINGS, 1));
        tLeggins.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Leggings.ELECTRIC, InternalNames.Armor.LEGGINGS, 1));
        
        //Registering the positions to the shoes
        ArmorChain tShoes = (ArmorChain) ARegistry.iInstance.getArmorMapping(InternalNames.Armor.SHOES);
        tLeggins.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Shoes.LEFT, InternalNames.Armor.SHOES, 1));
        tLeggins.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Shoes.RIGHT, InternalNames.Armor.SHOES, 1));
        tLeggins.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Shoes.FALLASSIST, InternalNames.Armor.SHOES, 1));
        tLeggins.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Shoes.SWIMASSIST, InternalNames.Armor.SHOES, 1));
        tLeggins.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Shoes.REINFORCED, InternalNames.Armor.SHOES, 1));
        tLeggins.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Shoes.AUTOREPAIR, InternalNames.Armor.SHOES, 1));
        tLeggins.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Shoes.ELECTRIC, InternalNames.Armor.SHOES, 1));
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
        for (ArmorMaterial tMaterial: ARegistry.iInstance.getArmorMaterials().values())
        {
            ArmorChainUpgrade tTopHead = new ArmorChainUpgrade(InternalNames.Upgrades.Helmet.TOP, InternalNames.Armor.HELMET, InternalNames.AddonPositions.Helmet.TOP, tMaterial.iInternalName, "Head protection", "", 2.5F, 60, 1);
            ARegistry.iInstance.registerUpgrade(tTopHead);

            if (tMaterial.iInternalName.equals(InternalNames.Materials.Vanilla.OBSIDIAN))
            {
                tMaterial.registerNewActivePart(InternalNames.Upgrades.Helmet.TOP, false);
            }
            else
            {
                tMaterial.registerNewActivePart(InternalNames.Upgrades.Helmet.TOP, true);
            }
        }
    }

    private static void registerEarProtection()
    {
        for (ArmorMaterial tMaterial: ARegistry.iInstance.getArmorMaterials().values())
        {
            ArmorChainUpgrade tEarProtectionLeft = new ArmorChainUpgrade(InternalNames.Upgrades.Helmet.LEFT, InternalNames.Armor.HELMET, InternalNames.AddonPositions.Helmet.LEFT, tMaterial.iInternalName, "Ear protection left", "", 0.5F, 20, 1);
            ArmorChainUpgrade tEarProtectionRight = new ArmorChainUpgrade(InternalNames.Upgrades.Helmet.RIGHT, InternalNames.Armor.HELMET, InternalNames.AddonPositions.Helmet.RIGHT, tMaterial.iInternalName, "Ear protection right", "", 0.5F, 20, 1);
            ARegistry.iInstance.registerUpgrade(tEarProtectionLeft);
            ARegistry.iInstance.registerUpgrade(tEarProtectionRight);

            if (tMaterial.iInternalName.equals(InternalNames.Materials.ModMaterials.TinkersConstruct.MANYULLUN))
            {
                tMaterial.registerNewActivePart(InternalNames.Upgrades.Helmet.LEFT, false);
                tMaterial.registerNewActivePart(InternalNames.Upgrades.Helmet.RIGHT, false);
            }
            else
            {
                tMaterial.registerNewActivePart(InternalNames.Upgrades.Helmet.LEFT, true);
                tMaterial.registerNewActivePart(InternalNames.Upgrades.Helmet.RIGHT, true);
            }
        }
    }

    private static void registerShoulderPads()
    {
        for (ArmorMaterial tMaterial: ARegistry.iInstance.getArmorMaterials().values())
        {
            ArmorChainUpgrade tShoulderPadLeft = new ArmorChainUpgrade(InternalNames.Upgrades.Chestplate.SHOULDERLEFT, InternalNames.Armor.CHESTPLATE , InternalNames.AddonPositions.Chestplate.SHOULDERLEFT, tMaterial.iInternalName, "Shoulder pad left", "", 1F, 50, 1);
            ArmorChainUpgrade tShoulderPadRight = new ArmorChainUpgrade(InternalNames.Upgrades.Chestplate.SHOULDERRIGHT, InternalNames.Armor.CHESTPLATE , InternalNames.AddonPositions.Chestplate.SHOULDERRIGHT, tMaterial.iInternalName, "Shoulder pad right", "", 1F, 50, 1);
            ARegistry.iInstance.registerUpgrade(tShoulderPadLeft);
            ARegistry.iInstance.registerUpgrade(tShoulderPadRight);

            if (tMaterial.iInternalName.equals(InternalNames.Materials.ModMaterials.TinkersConstruct.COBALT) || tMaterial.iInternalName.equals(InternalNames.Materials.ModMaterials.TinkersConstruct.MANYULLUN))
            {
                tMaterial.registerNewActivePart(InternalNames.Upgrades.Chestplate.SHOULDERLEFT, false);
                tMaterial.registerNewActivePart(InternalNames.Upgrades.Chestplate.SHOULDERRIGHT, false);
            }
            else
            {
                tMaterial.registerNewActivePart(InternalNames.Upgrades.Chestplate.SHOULDERLEFT, true);
                tMaterial.registerNewActivePart(InternalNames.Upgrades.Chestplate.SHOULDERRIGHT, true);
            }
        }
    }

    private static void registerFrontProtection()
    {
        for (ArmorMaterial tMaterial: ARegistry.iInstance.getArmorMaterials().values())
        {
            ArmorChainUpgrade tFrontChestProtectionLeft = new ArmorChainUpgrade(InternalNames.Upgrades.Chestplate.FRONTLEFT, InternalNames.Armor.CHESTPLATE, InternalNames.AddonPositions.Chestplate.FRONTLEFT, tMaterial.iInternalName, "Front chest protection left", "", 2F, 150, 1);
            ArmorChainUpgrade tFrontChestProtectionRight = new ArmorChainUpgrade(InternalNames.Upgrades.Chestplate.FRONTRIGHT, InternalNames.Armor.CHESTPLATE, InternalNames.AddonPositions.Chestplate.FRONTRIGHT, tMaterial.iInternalName, "Front chest protection right", "", 2F, 150, 1);
            ARegistry.iInstance.registerUpgrade(tFrontChestProtectionLeft);
            ARegistry.iInstance.registerUpgrade(tFrontChestProtectionRight);

            if (tMaterial.iInternalName.equals(InternalNames.Materials.Vanilla.OBSIDIAN))
            {
                tMaterial.registerNewActivePart(InternalNames.Upgrades.Chestplate.FRONTLEFT, false);
                tMaterial.registerNewActivePart(InternalNames.Upgrades.Chestplate.FRONTRIGHT, false);
            }
            else
            {
                tMaterial.registerNewActivePart(InternalNames.Upgrades.Chestplate.FRONTLEFT, true);
                tMaterial.registerNewActivePart(InternalNames.Upgrades.Chestplate.FRONTRIGHT, true);
            }
        }
    }

    private static void registerBackProtection()
    {
        for (ArmorMaterial tMaterial: ARegistry.iInstance.getArmorMaterials().values())
        {
            ArmorChainUpgrade tBackChestProtectionLeft = new ArmorChainUpgrade(InternalNames.Upgrades.Chestplate.BACKLEFT, InternalNames.Armor.CHESTPLATE, InternalNames.AddonPositions.Chestplate.BACKLEFT, tMaterial.iInternalName, "Back chest protection left", "", 2F, 150, 1);
            ArmorChainUpgrade tBackChestProtectionRight = new ArmorChainUpgrade(InternalNames.Upgrades.Chestplate.BACKRIGHT, InternalNames.Armor.CHESTPLATE, InternalNames.AddonPositions.Chestplate.BACKRIGHT, tMaterial.iInternalName, "Back chest protection right", "", 2F, 150, 1);
            ARegistry.iInstance.registerUpgrade(tBackChestProtectionLeft);
            ARegistry.iInstance.registerUpgrade(tBackChestProtectionRight);

            if (tMaterial.iInternalName.equals(InternalNames.Materials.Vanilla.OBSIDIAN))
            {
                tMaterial.registerNewActivePart(InternalNames.Upgrades.Chestplate.BACKLEFT, false);
                tMaterial.registerNewActivePart(InternalNames.Upgrades.Chestplate.BACKRIGHT, false);
            }
            else
            {
                tMaterial.registerNewActivePart(InternalNames.Upgrades.Chestplate.BACKLEFT, true);
                tMaterial.registerNewActivePart(InternalNames.Upgrades.Chestplate.BACKRIGHT, true);
            }
        }
    }

    private static void registerFrontLegProtection()
    {
        for (ArmorMaterial tMaterial: ARegistry.iInstance.getArmorMaterials().values())
        {
            ArmorChainUpgrade tFrontLeggingsProtectionLeft = new ArmorChainUpgrade(InternalNames.Upgrades.Leggings.FRONTLEFT, InternalNames.Armor.LEGGINGS, InternalNames.AddonPositions.Leggings.FRONTLEFT, tMaterial.iInternalName, "Front leg protection left", "", 1.5F, 125, 1);
            ArmorChainUpgrade tFrontLeggingsProtectionRight = new ArmorChainUpgrade(InternalNames.Upgrades.Leggings.FRONTRIGHT, InternalNames.Armor.LEGGINGS, InternalNames.AddonPositions.Leggings.FRONTRIGHT, tMaterial.iInternalName, "Front leg protection right", "", 1.5F, 125, 1);
            ARegistry.iInstance.registerUpgrade(tFrontLeggingsProtectionLeft);
            ARegistry.iInstance.registerUpgrade(tFrontLeggingsProtectionRight);

            if (tMaterial.iInternalName.equals(InternalNames.Materials.Vanilla.OBSIDIAN))
            {
                tMaterial.registerNewActivePart(InternalNames.Upgrades.Leggings.FRONTLEFT, false);
                tMaterial.registerNewActivePart(InternalNames.Upgrades.Leggings.FRONTRIGHT, false);
            }
            else
            {
                tMaterial.registerNewActivePart(InternalNames.Upgrades.Leggings.FRONTLEFT, true);
                tMaterial.registerNewActivePart(InternalNames.Upgrades.Leggings.FRONTRIGHT, true);
            }
        }
    }

    private static void registerBackLegProtection()
    {
        for (ArmorMaterial tMaterial: ARegistry.iInstance.getArmorMaterials().values())
        {
            ArmorChainUpgrade tBackLeggingsProtectionLeft = new ArmorChainUpgrade(InternalNames.Upgrades.Leggings.BACKLEFT, InternalNames.Armor.LEGGINGS, InternalNames.AddonPositions.Leggings.BACKLEFT, tMaterial.iInternalName, "Back leg protection left", "", 2F, 150, 1);
            ArmorChainUpgrade tBackLeggingsProtectionRight = new ArmorChainUpgrade(InternalNames.Upgrades.Leggings.BACKRIGHT, InternalNames.Armor.LEGGINGS, InternalNames.AddonPositions.Leggings.BACKRIGHT, tMaterial.iInternalName, "Back leg protection right", "", 2F, 150, 1);
            ARegistry.iInstance.registerUpgrade(tBackLeggingsProtectionLeft);
            ARegistry.iInstance.registerUpgrade(tBackLeggingsProtectionRight);

            if (tMaterial.iInternalName.equals(InternalNames.Materials.Vanilla.OBSIDIAN))
            {
                tMaterial.registerNewActivePart(InternalNames.Upgrades.Leggings.BACKLEFT, false);
                tMaterial.registerNewActivePart(InternalNames.Upgrades.Leggings.BACKRIGHT, false);
            }
            else
            {
                tMaterial.registerNewActivePart(InternalNames.Upgrades.Leggings.BACKLEFT, true);
                tMaterial.registerNewActivePart(InternalNames.Upgrades.Leggings.BACKRIGHT, true);
            }
        }
    }

    private static void registerShoeProtection()
    {
        for (ArmorMaterial tMaterial: ARegistry.iInstance.getArmorMaterials().values())
        {
            ArmorChainUpgrade tShoeProtectionLeft = new ArmorChainUpgrade(InternalNames.Upgrades.Shoes.LEFT, InternalNames.Armor.SHOES, InternalNames.AddonPositions.Shoes.LEFT, tMaterial.iInternalName, "Shoe protection left", "", 1F, 50, 1);
            ArmorChainUpgrade tShoeProtectionRight = new ArmorChainUpgrade(InternalNames.Upgrades.Shoes.RIGHT, InternalNames.Armor.SHOES, InternalNames.AddonPositions.Shoes.RIGHT, tMaterial.iInternalName, "Shoe protection right", "", 1F, 50, 1);
            ARegistry.iInstance.registerUpgrade(tShoeProtectionLeft);
            ARegistry.iInstance.registerUpgrade(tShoeProtectionRight);

            if ((tMaterial.iInternalName.equals(InternalNames.Materials.ModMaterials.TinkersConstruct.ARDITE)) || (tMaterial.iInternalName.equals(InternalNames.Materials.ModMaterials.TinkersConstruct.COBALT)) || tMaterial.iInternalName.equals(InternalNames.Materials.ModMaterials.TinkersConstruct.MANYULLUN))
            {
                tMaterial.registerNewActivePart(InternalNames.Upgrades.Shoes.LEFT, false);
                tMaterial.registerNewActivePart(InternalNames.Upgrades.Shoes.RIGHT, false);
            }
            else
            {
                tMaterial.registerNewActivePart(InternalNames.Upgrades.Shoes.LEFT, true);
                tMaterial.registerNewActivePart(InternalNames.Upgrades.Shoes.RIGHT, true);
            }
        }
    }
    
    private static void modifyMaterials()
    {
        modifyHelmet();
        modifyChestplate();
        modifyLeggings();
        modifyShoes();
    }
    
    private static void modifyHelmet()
    {
        for(ArmorMaterial tMaterial   : ARegistry.iInstance.getArmorMaterials().values()) {
            if (tMaterial.iInternalName.equals(InternalNames.Materials.Vanilla.IRON)) {
                tMaterial.setBaseDamageAbsorption(InternalNames.Armor.HELMET, 1.5F);
                tMaterial.setBaseDurability(InternalNames.Armor.HELMET, 50);
                tMaterial.setMaxModifiersOnPart(InternalNames.Armor.HELMET, 1);
            } else if (tMaterial.iInternalName.equals(InternalNames.Materials.Vanilla.CHAIN)) {
                tMaterial.setBaseDamageAbsorption(InternalNames.Armor.HELMET, 2.0F);
                tMaterial.setBaseDurability(InternalNames.Armor.HELMET, 60);
                tMaterial.setMaxModifiersOnPart(InternalNames.Armor.HELMET, 1);
            } else if (tMaterial.iInternalName.equals(InternalNames.Materials.ModMaterials.TinkersConstruct.ALUMITE)) {
                tMaterial.setBaseDamageAbsorption(InternalNames.Armor.HELMET, 2.0F);
                tMaterial.setBaseDurability(InternalNames.Armor.HELMET, 100);
                tMaterial.setMaxModifiersOnPart(InternalNames.Armor.HELMET, 2);
            } else if (tMaterial.iInternalName.equals(InternalNames.Materials.Common.BRONZE)) {
                tMaterial.setBaseDamageAbsorption(InternalNames.Armor.HELMET, 1.0F);
                tMaterial.setBaseDurability(InternalNames.Armor.HELMET, 100);
                tMaterial.setMaxModifiersOnPart(InternalNames.Armor.HELMET, 0);
            } else if (tMaterial.iInternalName.equals(InternalNames.Materials.ModMaterials.TinkersConstruct.ARDITE)) {
                tMaterial.setBaseDamageAbsorption(InternalNames.Armor.HELMET, 2.5F);
                tMaterial.setBaseDurability(InternalNames.Armor.HELMET, 100);
                tMaterial.setMaxModifiersOnPart(InternalNames.Armor.HELMET, 2);
            } else if (tMaterial.iInternalName.equals(InternalNames.Materials.ModMaterials.TinkersConstruct.COBALT)) {
                tMaterial.setBaseDamageAbsorption(InternalNames.Armor.HELMET, 3F);
                tMaterial.setBaseDurability(InternalNames.Armor.HELMET, 140);
                tMaterial.setMaxModifiersOnPart(InternalNames.Armor.HELMET, 2);
            } else if (tMaterial.iInternalName.equals(InternalNames.Materials.Vanilla.OBSIDIAN)) {
                tMaterial.setBaseDamageAbsorption(InternalNames.Armor.HELMET, 3F);
                tMaterial.setBaseDurability(InternalNames.Armor.HELMET, 200);
                tMaterial.setMaxModifiersOnPart(InternalNames.Armor.HELMET, 2);
            } else if (tMaterial.iInternalName.equals(InternalNames.Materials.ModMaterials.TinkersConstruct.MANYULLUN)) {
                tMaterial.setBaseDamageAbsorption(InternalNames.Armor.HELMET, 3.5F);
                tMaterial.setBaseDurability(InternalNames.Armor.HELMET, 250);
                tMaterial.setMaxModifiersOnPart(InternalNames.Armor.HELMET, 3);
            } else {
                MinecraftForge.EVENT_BUS.post(new ModifyMaterialEvent(InternalNames.Armor.HELMET, tMaterial.iInternalName));
            }
        }

    }
    
    private static void modifyChestplate()
    {
        for(ArmorMaterial tMaterial   :ARegistry.iInstance.getArmorMaterials().values()) {
            if (tMaterial.iInternalName.equals(InternalNames.Materials.Vanilla.IRON)) {
                tMaterial.setBaseDamageAbsorption(InternalNames.Armor.CHESTPLATE, 2.0F);
                tMaterial.setBaseDurability(InternalNames.Armor.CHESTPLATE, 50);
                tMaterial.setMaxModifiersOnPart(InternalNames.Armor.CHESTPLATE, 1);
            } else if (tMaterial.iInternalName.equals(InternalNames.Materials.Vanilla.CHAIN)) {
                tMaterial.setBaseDamageAbsorption(InternalNames.Armor.CHESTPLATE, 2.5F);
                tMaterial.setBaseDurability(InternalNames.Armor.CHESTPLATE, 60);
                tMaterial.setMaxModifiersOnPart(InternalNames.Armor.CHESTPLATE, 1);
            } else if (tMaterial.iInternalName.equals(InternalNames.Materials.ModMaterials.TinkersConstruct.ALUMITE)) {
                tMaterial.setBaseDamageAbsorption(InternalNames.Armor.CHESTPLATE, 2.5F);
                tMaterial.setBaseDurability(InternalNames.Armor.CHESTPLATE, 100);
                tMaterial.setMaxModifiersOnPart(InternalNames.Armor.CHESTPLATE, 2);
            } else if (tMaterial.iInternalName.equals(InternalNames.Materials.Common.BRONZE)) {
                tMaterial.setBaseDamageAbsorption(InternalNames.Armor.CHESTPLATE, 1.5F);
                tMaterial.setBaseDurability(InternalNames.Armor.CHESTPLATE, 100);
                tMaterial.setMaxModifiersOnPart(InternalNames.Armor.CHESTPLATE, 0);
            } else if (tMaterial.iInternalName.equals(InternalNames.Materials.ModMaterials.TinkersConstruct.ARDITE)) {
                tMaterial.setBaseDamageAbsorption(InternalNames.Armor.CHESTPLATE, 3.0F);
                tMaterial.setBaseDurability(InternalNames.Armor.CHESTPLATE, 100);
                tMaterial.setMaxModifiersOnPart(InternalNames.Armor.CHESTPLATE, 2);
            } else if (tMaterial.iInternalName.equals(InternalNames.Materials.ModMaterials.TinkersConstruct.COBALT)) {
                tMaterial.setBaseDamageAbsorption(InternalNames.Armor.CHESTPLATE, 3.5F);
                tMaterial.setBaseDurability(InternalNames.Armor.CHESTPLATE, 140);
                tMaterial.setMaxModifiersOnPart(InternalNames.Armor.CHESTPLATE, 2);
            } else if (tMaterial.iInternalName.equals(InternalNames.Materials.Vanilla.OBSIDIAN)) {
                tMaterial.setBaseDamageAbsorption(InternalNames.Armor.CHESTPLATE, 3.5F);
                tMaterial.setBaseDurability(InternalNames.Armor.CHESTPLATE, 200);
                tMaterial.setMaxModifiersOnPart(InternalNames.Armor.CHESTPLATE, 2);
            } else if (tMaterial.iInternalName.equals(InternalNames.Materials.ModMaterials.TinkersConstruct.MANYULLUN)) {
                tMaterial.setBaseDamageAbsorption(InternalNames.Armor.CHESTPLATE, 4.0F);
                tMaterial.setBaseDurability(InternalNames.Armor.CHESTPLATE, 250);
                tMaterial.setMaxModifiersOnPart(InternalNames.Armor.CHESTPLATE, 3);
            } else {
                MinecraftForge.EVENT_BUS.post(new ModifyMaterialEvent(InternalNames.Armor.CHESTPLATE, tMaterial.iInternalName));
            }
        }
    }
    
    private static void modifyLeggings()
    {
        for(ArmorMaterial tMaterial   :ARegistry.iInstance.getArmorMaterials().values()) {
            if (tMaterial.iInternalName.equals(InternalNames.Materials.Vanilla.IRON)) {
                tMaterial.setBaseDamageAbsorption(InternalNames.Armor.LEGGINGS, 1.5F);
                tMaterial.setBaseDurability(InternalNames.Armor.LEGGINGS, 50);
                tMaterial.setMaxModifiersOnPart(InternalNames.Armor.LEGGINGS, 1);
            } else if (tMaterial.iInternalName.equals(InternalNames.Materials.Vanilla.CHAIN)) {
                tMaterial.setBaseDamageAbsorption(InternalNames.Armor.LEGGINGS, 2.0F);
                tMaterial.setBaseDurability(InternalNames.Armor.LEGGINGS, 60);
                tMaterial.setMaxModifiersOnPart(InternalNames.Armor.LEGGINGS, 1);
            } else if (tMaterial.iInternalName.equals(InternalNames.Materials.ModMaterials.TinkersConstruct.ALUMITE)) {
                tMaterial.setBaseDamageAbsorption(InternalNames.Armor.LEGGINGS, 2.0F);
                tMaterial.setBaseDurability(InternalNames.Armor.LEGGINGS, 100);
                tMaterial.setMaxModifiersOnPart(InternalNames.Armor.LEGGINGS, 2);
            } else if (tMaterial.iInternalName.equals(InternalNames.Materials.Common.BRONZE)) {
                tMaterial.setBaseDamageAbsorption(InternalNames.Armor.LEGGINGS, 1.0F);
                tMaterial.setBaseDurability(InternalNames.Armor.LEGGINGS, 100);
                tMaterial.setMaxModifiersOnPart(InternalNames.Armor.LEGGINGS, 0);
            } else if (tMaterial.iInternalName.equals(InternalNames.Materials.ModMaterials.TinkersConstruct.ARDITE)) {
                tMaterial.setBaseDamageAbsorption(InternalNames.Armor.LEGGINGS, 2.5F);
                tMaterial.setBaseDurability(InternalNames.Armor.LEGGINGS, 100);
                tMaterial.setMaxModifiersOnPart(InternalNames.Armor.LEGGINGS, 2);
            } else if (tMaterial.iInternalName.equals(InternalNames.Materials.ModMaterials.TinkersConstruct.COBALT)) {
                tMaterial.setBaseDamageAbsorption(InternalNames.Armor.LEGGINGS, 3F);
                tMaterial.setBaseDurability(InternalNames.Armor.LEGGINGS, 140);
                tMaterial.setMaxModifiersOnPart(InternalNames.Armor.LEGGINGS, 2);
            } else if (tMaterial.iInternalName.equals(InternalNames.Materials.Vanilla.OBSIDIAN)) {
                tMaterial.setBaseDamageAbsorption(InternalNames.Armor.LEGGINGS, 3F);
                tMaterial.setBaseDurability(InternalNames.Armor.LEGGINGS, 200);
                tMaterial.setMaxModifiersOnPart(InternalNames.Armor.LEGGINGS, 2);
            } else if (tMaterial.iInternalName.equals(InternalNames.Materials.ModMaterials.TinkersConstruct.MANYULLUN)) {
                tMaterial.setBaseDamageAbsorption(InternalNames.Armor.LEGGINGS, 3.5F);
                tMaterial.setBaseDurability(InternalNames.Armor.LEGGINGS, 250);
                tMaterial.setMaxModifiersOnPart(InternalNames.Armor.LEGGINGS, 3);
            } else {
                MinecraftForge.EVENT_BUS.post(new ModifyMaterialEvent(InternalNames.Armor.LEGGINGS, tMaterial.iInternalName));
            }
        }
    }
    
    private static void modifyShoes()
    {
        for(ArmorMaterial tMaterial   : ARegistry.iInstance.getArmorMaterials().values()) {
            if (tMaterial.iInternalName.equals("vanilla.Iron")) {
                tMaterial.setBaseDamageAbsorption(InternalNames.Materials.Vanilla.IRON, 1.0F);
                tMaterial.setBaseDurability(InternalNames.Armor.SHOES, 50);
                tMaterial.setMaxModifiersOnPart(InternalNames.Armor.SHOES, 1);
            } else if (tMaterial.iInternalName.equals(InternalNames.Materials.Vanilla.CHAIN)) {
                tMaterial.setBaseDamageAbsorption(InternalNames.Armor.SHOES, 1.5F);
                tMaterial.setBaseDurability(InternalNames.Armor.SHOES, 60);
                tMaterial.setMaxModifiersOnPart(InternalNames.Armor.SHOES, 1);
            } else if (tMaterial.iInternalName.equals(InternalNames.Materials.ModMaterials.TinkersConstruct.ALUMITE)) {
                tMaterial.setBaseDamageAbsorption(InternalNames.Armor.SHOES, 1.5F);
                tMaterial.setBaseDurability(InternalNames.Armor.SHOES, 100);
                tMaterial.setMaxModifiersOnPart(InternalNames.Armor.SHOES, 2);
            } else if (tMaterial.iInternalName.equals(InternalNames.Materials.Common.BRONZE)) {
                tMaterial.setBaseDamageAbsorption(InternalNames.Armor.SHOES, 0.5F);
                tMaterial.setBaseDurability(InternalNames.Armor.SHOES, 100);
                tMaterial.setMaxModifiersOnPart(InternalNames.Armor.SHOES, 0);
            } else if (tMaterial.iInternalName.equals(InternalNames.Materials.ModMaterials.TinkersConstruct.ARDITE)) {
                tMaterial.setBaseDamageAbsorption(InternalNames.Armor.SHOES, 2.0F);
                tMaterial.setBaseDurability(InternalNames.Armor.SHOES, 100);
                tMaterial.setMaxModifiersOnPart(InternalNames.Armor.SHOES, 2);
            } else if (tMaterial.iInternalName.equals(InternalNames.Materials.ModMaterials.TinkersConstruct.COBALT)) {
                tMaterial.setBaseDamageAbsorption(InternalNames.Armor.SHOES, 2.5F);
                tMaterial.setBaseDurability(InternalNames.Armor.SHOES, 140);
                tMaterial.setMaxModifiersOnPart(InternalNames.Armor.SHOES, 2);
            } else if (tMaterial.iInternalName.equals(InternalNames.Materials.Vanilla.OBSIDIAN)) {
                tMaterial.setBaseDamageAbsorption(InternalNames.Armor.SHOES, 2.5F);
                tMaterial.setBaseDurability(InternalNames.Armor.SHOES, 200);
                tMaterial.setMaxModifiersOnPart(InternalNames.Armor.SHOES, 2);
            } else if (tMaterial.iInternalName.equals(InternalNames.Materials.ModMaterials.TinkersConstruct.MANYULLUN)) {
                tMaterial.setBaseDamageAbsorption(InternalNames.Armor.SHOES, 3.0F);
                tMaterial.setBaseDurability(InternalNames.Armor.SHOES, 250);
                tMaterial.setMaxModifiersOnPart(InternalNames.Armor.SHOES, 3);
            } else {
                MinecraftForge.EVENT_BUS.post(new ModifyMaterialEvent(InternalNames.Armor.SHOES, tMaterial.iInternalName));
            }
        }
    }

    public static void prepareGame()
    {
        for(MultiLayeredArmor tCore: ARegistry.iInstance.getAllArmorMappings().values())
        {
            GameRegistry.registerItem(tCore, tCore.getInternalName());
        }
    }

}
