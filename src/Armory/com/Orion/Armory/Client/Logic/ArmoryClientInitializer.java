package com.Orion.Armory.Client.Logic;
/*
 *   ArmoryClientInitializer
 *   Created by: Orion
 *   Created on: 19-9-2014
 */

import com.Orion.Armory.Client.Util.Textures;
import com.Orion.Armory.Common.ARegistry;
import com.Orion.Armory.Common.Armor.Core.ArmorMaterial;
import com.Orion.Armory.Common.Armor.TierChain.ArmorChain;
import com.Orion.Armory.Common.Logic.ArmoryInitializer;
import com.Orion.Armory.Util.References;
import com.Orion.Armory.Util.Client.CustomResource;

public class ArmoryClientInitializer extends ArmoryInitializer
{
    public static void InitializeClient()
    {
        Initialize();
        registerMaterialResources();
        registerUpgradeResources();
        prepareGame();
    }
    
    private static void registerMaterialResources()
    {
        //Helmet
        ArmorChain tHelmet = (ArmorChain) ARegistry.iInstance.getArmorMapping(References.InternalNames.Armor.HELMET);
        tHelmet.registerResource(Textures.MultiArmor.Materials.Iron.tHelmetResource);
        tHelmet.registerResource(Textures.MultiArmor.Materials.Chain.tHelmetResource);
        tHelmet.registerResource(Textures.MultiArmor.Materials.Obsidian.tHelmetResource);
        tHelmet.registerResource(Textures.MultiArmor.Materials.Bronze.tHelmetResource);
        tHelmet.registerResource(Textures.MultiArmor.Materials.Alumite.tHelmetResource);
        tHelmet.registerResource(Textures.MultiArmor.Materials.Ardite.tHelmetResource);
        tHelmet.registerResource(Textures.MultiArmor.Materials.Cobalt.tHelmetResource);
        tHelmet.registerResource(Textures.MultiArmor.Materials.Manyullun.tHelmetResource);

        //Chestplate
        ArmorChain tChestplate = (ArmorChain) ARegistry.iInstance.getArmorMapping(References.InternalNames.Armor.CHESTPLATE);
        tChestplate.registerResource(Textures.MultiArmor.Materials.Iron.tChestplateResource);
        tChestplate.registerResource(Textures.MultiArmor.Materials.Chain.tChestplateResource);
        tChestplate.registerResource(Textures.MultiArmor.Materials.Obsidian.tChestplateResource);
        tChestplate.registerResource(Textures.MultiArmor.Materials.Bronze.tChestplateResource);
        tChestplate.registerResource(Textures.MultiArmor.Materials.Alumite.tChestplateResource);
        tChestplate.registerResource(Textures.MultiArmor.Materials.Ardite.tChestplateResource);
        tChestplate.registerResource(Textures.MultiArmor.Materials.Cobalt.tChestplateResource);
        tChestplate.registerResource(Textures.MultiArmor.Materials.Manyullun.tChestplateResource);

        //Leggins
        ArmorChain tLeggins = (ArmorChain) ARegistry.iInstance.getArmorMapping(References.InternalNames.Armor.LEGGINGS);
        tLeggins.registerResource(Textures.MultiArmor.Materials.Iron.tLegginsResource);
        tLeggins.registerResource(Textures.MultiArmor.Materials.Chain.tLegginsResource);
        tLeggins.registerResource(Textures.MultiArmor.Materials.Obsidian.tLegginsResource);
        tLeggins.registerResource(Textures.MultiArmor.Materials.Bronze.tLegginsResource);
        tLeggins.registerResource(Textures.MultiArmor.Materials.Alumite.tLegginsResource);
        tLeggins.registerResource(Textures.MultiArmor.Materials.Ardite.tLegginsResource);
        tLeggins.registerResource(Textures.MultiArmor.Materials.Cobalt.tLegginsResource);
        tLeggins.registerResource(Textures.MultiArmor.Materials.Manyullun.tLegginsResource);

        //Shoes
        ArmorChain tShoes = (ArmorChain) ARegistry.iInstance.getArmorMapping(References.InternalNames.Armor.SHOES);
        tShoes.registerResource(Textures.MultiArmor.Materials.Iron.tShoesResource);
        tShoes.registerResource(Textures.MultiArmor.Materials.Chain.tShoesResource);
        tShoes.registerResource(Textures.MultiArmor.Materials.Obsidian.tShoesResource);
        tShoes.registerResource(Textures.MultiArmor.Materials.Bronze.tShoesResource);
        tShoes.registerResource(Textures.MultiArmor.Materials.Alumite.tShoesResource);
        tShoes.registerResource(Textures.MultiArmor.Materials.Ardite.tShoesResource);
        tShoes.registerResource(Textures.MultiArmor.Materials.Cobalt.tShoesResource);
        tShoes.registerResource(Textures.MultiArmor.Materials.Manyullun.tShoesResource);
    }

    private static void registerUpgradeResources()
    {
        //ArmorPieces
        ArmorChain tHelmet = (ArmorChain) ARegistry.iInstance.getArmorMapping(References.InternalNames.Armor.HELMET);
        ArmorChain tChestplate = (ArmorChain) ARegistry.iInstance.getArmorMapping(References.InternalNames.Armor.CHESTPLATE);
        ArmorChain tLeggings = (ArmorChain) ARegistry.iInstance.getArmorMapping(References.InternalNames.Armor.LEGGINGS);
        ArmorChain tShoes = (ArmorChain) ARegistry.iInstance.getArmorMapping(References.InternalNames.Armor.SHOES);
        
        for (ArmorMaterial tMaterial : ARegistry.iInstance.getArmorMaterials().values())
        {
            //Helmet resources
            CustomResource tTopHelmetResource = new CustomResource(References.InternalNames.Upgrades.Helmet.TOP + "-" + tMaterial.iInternalName, "armory:multiarmor/upgrades/armory.Helmet_TopHead", "armory:textures/models/multiarmor/upgrades/armory.Helmet_TopHead.png", tMaterial.getColor());
            CustomResource tLeftHelmetResource = new CustomResource(References.InternalNames.Upgrades.Helmet.LEFT + "-" + tMaterial.iInternalName, "armory:multiarmor/upgrades/armory.Helmet_Protection_Ear_Left", "armory:textures/models/multiarmor/upgrades/armory.Helmet_Protection_Ear_Left.png", tMaterial.getColor());
            CustomResource tRightHelmetResource = new CustomResource(References.InternalNames.Upgrades.Helmet.RIGHT + "-" + tMaterial.iInternalName, "armory:multiarmor/upgrades/armory.Helmet_Protection_Ear_Right", "armory:textures/models/multiarmor/upgrades/armory.Helmet_Protection_Ear_Right.png", tMaterial.getColor());
            tHelmet.registerResource(tTopHelmetResource);
            ARegistry.iInstance.getUpgrade(References.InternalNames.Upgrades.Helmet.TOP + "-" + tMaterial.iInternalName).setResource(tTopHelmetResource);
            tHelmet.registerResource(tLeftHelmetResource);
            ARegistry.iInstance.getUpgrade(References.InternalNames.Upgrades.Helmet.LEFT + "-" + tMaterial.iInternalName).setResource(tLeftHelmetResource);
            tHelmet.registerResource(tRightHelmetResource);
            ARegistry.iInstance.getUpgrade(References.InternalNames.Upgrades.Helmet.RIGHT + "-" + tMaterial.iInternalName).setResource(tRightHelmetResource);

            //Chestplate resources
            CustomResource tLeftShoulderChestplateResource = new CustomResource(References.InternalNames.Upgrades.Chestplate.SHOULDERLEFT + "-" + tMaterial.iInternalName, "armory:multiarmor/upgrades/armory.Chestplate_ShoulderPad_Left", "armory:textures/models/multiarmor/upgrades/armory.Chestplate_ShoulderPad_Left.png", tMaterial.getColor());
            CustomResource tRightShoulderChestplateResource = new CustomResource(References.InternalNames.Upgrades.Chestplate.SHOULDERRIGHT + "-" + tMaterial.iInternalName, "armory:multiarmor/upgrades/armory.Chestplate_ShoulderPad_Right", "armory:textures/models/multiarmor/upgrades/armory.Chestplate_ShoulderPad_Right.png", tMaterial.getColor());
            CustomResource tLeftFrontChestplateResource = new CustomResource(References.InternalNames.Upgrades.Chestplate.FRONTLEFT + "-" + tMaterial.iInternalName, "armory:multiarmor/upgrades/armory.Chestplate_Protection_Front_Left", "armory:textures/models/multiarmor/upgrades/armory.Chestplate_Protection_Front_Left.png", tMaterial.getColor());
            CustomResource tRightFrontChestplateResource = new CustomResource(References.InternalNames.Upgrades.Chestplate.FRONTRIGHT + "-" + tMaterial.iInternalName, "armory:multiarmor/upgrades/armory.Chestplate_Protection_Front_Right", "armory:textures/models/multiarmor/upgrades/armory.Chestplate_Protection_Front_Right.png", tMaterial.getColor());
            CustomResource tLeftBackChestplateResource = new CustomResource(References.InternalNames.Upgrades.Chestplate.BACKLEFT + "-" + tMaterial.iInternalName, "armory:multiarmor/upgrades/armory.Chestplate_Protection_Back_Left", "armory:textures/models/multiarmor/upgrades/armory.Chestplate_Protection_Back_Left.png", tMaterial.getColor());
            CustomResource tRightBackChestplateResource = new CustomResource(References.InternalNames.Upgrades.Chestplate.BACKRIGHT + "-" + tMaterial.iInternalName, "armory:multiarmor/upgrades/armory.Chestplate_Protection_Back_Right", "armory:textures/models/multiarmor/upgrades/armory.Chestplate_Protection_Back_Right.png", tMaterial.getColor());
            tChestplate.registerResource(tLeftShoulderChestplateResource);
            ARegistry.iInstance.getUpgrade(References.InternalNames.Upgrades.Chestplate.SHOULDERLEFT + "-" + tMaterial.iInternalName).setResource(tLeftShoulderChestplateResource);
            tChestplate.registerResource(tRightShoulderChestplateResource);
            ARegistry.iInstance.getUpgrade(References.InternalNames.Upgrades.Chestplate.SHOULDERRIGHT + "-" + tMaterial.iInternalName).setResource(tRightShoulderChestplateResource);
            tChestplate.registerResource(tLeftFrontChestplateResource);
            ARegistry.iInstance.getUpgrade(References.InternalNames.Upgrades.Chestplate.FRONTLEFT + "-" + tMaterial.iInternalName).setResource(tLeftFrontChestplateResource);
            tChestplate.registerResource(tRightFrontChestplateResource);
            ARegistry.iInstance.getUpgrade(References.InternalNames.Upgrades.Chestplate.FRONTRIGHT + "-" + tMaterial.iInternalName).setResource(tRightFrontChestplateResource);
            tChestplate.registerResource(tLeftBackChestplateResource);
            ARegistry.iInstance.getUpgrade(References.InternalNames.Upgrades.Chestplate.BACKLEFT + "-" + tMaterial.iInternalName).setResource(tLeftBackChestplateResource);
            tChestplate.registerResource(tRightBackChestplateResource);
            ARegistry.iInstance.getUpgrade(References.InternalNames.Upgrades.Chestplate.BACKRIGHT + "-" + tMaterial.iInternalName).setResource(tRightBackChestplateResource);

            //Legging resources
            CustomResource tLeftFrontLeggingsResource = new CustomResource(References.InternalNames.Upgrades.Leggings.FRONTLEFT + "-" + tMaterial.iInternalName, "armory:multiarmor/upgrades/armory.Leggins_Protection_Front_Left", "armory:textures/models/multiarmor/upgrades/armory.Leggins_Protection_Front_Left.png", tMaterial.getColor());
            CustomResource tRightFrontLeggingsResource = new CustomResource(References.InternalNames.Upgrades.Leggings.FRONTRIGHT + "-" + tMaterial.iInternalName, "armory:multiarmor/upgrades/armory.Leggins_Protection_Front_Right", "armory:textures/models/multiarmor/upgrades/armory.Leggins_Protection_Front_Right.png", tMaterial.getColor());
            CustomResource tLeftBackLeggingsResource = new CustomResource(References.InternalNames.Upgrades.Leggings.BACKLEFT + "-" + tMaterial.iInternalName, "armory:multiarmor/upgrades/armory.Leggins_Protection_Back_Left", "armory:textures/models/multiarmor/upgrades/armory.Leggins_Protection_Back_Left.png", tMaterial.getColor());
            CustomResource tRightBackLeggingsResource = new CustomResource(References.InternalNames.Upgrades.Leggings.BACKRIGHT + "-" + tMaterial.iInternalName, "armory:multiarmor/upgrades/armory.Leggins_Protection_Back_Right", "armory:textures/models/multiarmor/upgrades/armory.Leggins_Protection_Back_Right.png", tMaterial.getColor());
            tLeggings.registerResource(tLeftFrontLeggingsResource);
            ARegistry.iInstance.getUpgrade(References.InternalNames.Upgrades.Leggings.FRONTLEFT + "-" + tMaterial.iInternalName).setResource(tLeftFrontLeggingsResource);
            tLeggings.registerResource(tRightFrontLeggingsResource);
            ARegistry.iInstance.getUpgrade(References.InternalNames.Upgrades.Leggings.FRONTRIGHT + "-" + tMaterial.iInternalName).setResource(tRightFrontLeggingsResource);
            tLeggings.registerResource(tLeftBackLeggingsResource);
            ARegistry.iInstance.getUpgrade(References.InternalNames.Upgrades.Leggings.BACKLEFT + "-" + tMaterial.iInternalName).setResource(tLeftBackLeggingsResource);
            tLeggings.registerResource(tRightBackLeggingsResource);
            ARegistry.iInstance.getUpgrade(References.InternalNames.Upgrades.Leggings.BACKRIGHT + "-" + tMaterial.iInternalName).setResource(tRightBackLeggingsResource);

            //Shoes resources
            CustomResource tLeftShoeResource = new CustomResource(References.InternalNames.Upgrades.Shoes.LEFT + "-" + tMaterial.iInternalName, "armory:multiarmor/upgrades/armory.Shoes_Protection_Left", "armory:textures/models/multiarmor/upgrades/armory.Shoes_Protection_Left.png", tMaterial.getColor());
            CustomResource tRightShoeResource = new CustomResource(References.InternalNames.Upgrades.Shoes.RIGHT + "-" + tMaterial.iInternalName, "armory:multiarmor/upgrades/armory.Shoes_Protection_Right", "armory:textures/models/multiarmor/upgrades/armory.Shoes_Protection_Right.png", tMaterial.getColor());
            tShoes.registerResource(tLeftShoeResource);
            ARegistry.iInstance.getUpgrade(References.InternalNames.Upgrades.Shoes.LEFT + "-" + tMaterial.iInternalName).setResource(tLeftShoeResource);
            tShoes.registerResource(tRightShoeResource);
            ARegistry.iInstance.getUpgrade(References.InternalNames.Upgrades.Shoes.RIGHT + "-" + tMaterial.iInternalName).setResource(tRightShoeResource);
        }
    }
}
