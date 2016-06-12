package com.smithsmodding.armory.common.logic;
/*
 *   ArmoryInitializer
 *   Created by: Orion
 *   Created on: 17-9-2014
 */

import com.smithsmodding.armory.Armory;
import com.smithsmodding.armory.api.armor.ArmorAddonPosition;
import com.smithsmodding.armory.api.armor.MultiLayeredArmor;
import com.smithsmodding.armory.api.events.common.ModifyMaterialEvent;
import com.smithsmodding.armory.api.events.common.RegisterArmorEvent;
import com.smithsmodding.armory.api.events.common.RegisterMaterialsEvent;
import com.smithsmodding.armory.api.events.common.RegisterUpgradesEvent;
import com.smithsmodding.armory.api.materials.IArmorMaterial;
import com.smithsmodding.armory.common.addons.ArmorUpgradeMedieval;
import com.smithsmodding.armory.common.addons.MedievalAddonRegistry;
import com.smithsmodding.armory.common.anvil.AnvilMaterial;
import com.smithsmodding.armory.common.block.BlockBlackSmithsAnvil;
import com.smithsmodding.armory.common.block.BlockFirePit;
import com.smithsmodding.armory.common.block.BlockFirePlace;
import com.smithsmodding.armory.common.fluid.FluidMoltenMetal;
import com.smithsmodding.armory.common.item.ItemArmorComponent;
import com.smithsmodding.armory.common.item.ItemHeatedItem;
import com.smithsmodding.armory.common.item.ItemSmithingsGuide;
import com.smithsmodding.armory.common.item.armor.tiermedieval.ArmorMedieval;
import com.smithsmodding.armory.common.item.block.ItemBlockBlackSmithsAnvil;
import com.smithsmodding.armory.common.material.ArmorMaterial;
import com.smithsmodding.armory.common.material.ChainLayer;
import com.smithsmodding.armory.common.material.MaterialRegistry;
import com.smithsmodding.armory.common.material.fluidmodifiers.ObsidianToLavaSetter;
import com.smithsmodding.armory.common.registry.AnvilMaterialRegistry;
import com.smithsmodding.armory.common.registry.GeneralRegistry;
import com.smithsmodding.armory.common.tileentity.TileEntityBlackSmithsAnvil;
import com.smithsmodding.armory.common.tileentity.TileEntityFirePit;
import com.smithsmodding.armory.common.tileentity.TileEntityFireplace;
import com.smithsmodding.armory.util.References;
import com.smithsmodding.armory.util.client.Textures;
import com.smithsmodding.armory.util.client.TranslationKeys;
import com.smithsmodding.smithscore.SmithsCore;
import com.smithsmodding.smithscore.util.common.ItemStackHelper;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.translation.I18n;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import java.util.Iterator;

public class ArmoryInitializer {
    public static void InitializeServer() {
        SystemInit.RegisterFluids();
        MedievalInitialization.Initialize();
        GlobalInitialization.RegisterAnvilMaterials();
        SystemInit.RegisterBlocks();
        SystemInit.RegisterItems();
        SystemInit.RegisterTileEntities();
        SystemInit.loadMaterialConfig();
        MedievalInitialization.prepareGame();
        SystemInit.initializeOreDic();
    }

    public static void postInitializeServer() {
        SystemInit.removeRecipes();
    }

    public static class MedievalInitialization {
        public static void Initialize() {
            registerArmorPieces();
            registerMaterials();
            registerAddonPositions();
            registerUpgrades();
            modifyMaterials();
        }


        private static void registerArmorPieces() {
            MaterialRegistry.getInstance().registerNewArmor(new ArmorMedieval(References.InternalNames.Armor.MEDIEVALHELMET, EntityEquipmentSlot.HEAD));
            MaterialRegistry.getInstance().registerNewArmor(new ArmorMedieval(References.InternalNames.Armor.MEDIEVALCHESTPLATE, EntityEquipmentSlot.CHEST));
            MaterialRegistry.getInstance().registerNewArmor(new ArmorMedieval(References.InternalNames.Armor.MEDIEVALLEGGINGS, EntityEquipmentSlot.LEGS));
            MaterialRegistry.getInstance().registerNewArmor(new ArmorMedieval(References.InternalNames.Armor.MEDIEVALSHOES, EntityEquipmentSlot.FEET));

            MinecraftForge.EVENT_BUS.register(new RegisterArmorEvent());
        }

        private static void registerMaterials() {
            ArmorMaterial tIron = new ArmorMaterial(References.InternalNames.Materials.Vanilla.IRON, "Iron", true, 1865, 500, 0.225F, new ItemStack(Items.iron_ingot));
            ArmorMaterial tObsidian = new ArmorMaterial(References.InternalNames.Materials.Vanilla.OBSIDIAN, "Obsidian", true, 1404, 3000, 0.345F, new ItemStack(Item.getItemFromBlock(Blocks.obsidian)));

            MaterialRegistry.getInstance().registerMaterial(tIron);
            MaterialRegistry.getInstance().registerMaterial(tObsidian);

            MinecraftForge.EVENT_BUS.post(new RegisterMaterialsEvent());
        }

        private static void registerAddonPositions() {
            //Registering the positions to the helmet
            MultiLayeredArmor tHelmet = MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALHELMET);
            tHelmet.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Helmet.BASE, References.InternalNames.Armor.MEDIEVALHELMET, 1));
            tHelmet.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Helmet.TOP, References.InternalNames.Armor.MEDIEVALHELMET, 1));
            tHelmet.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Helmet.LEFT, References.InternalNames.Armor.MEDIEVALHELMET, 1));
            tHelmet.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Helmet.RIGHT, References.InternalNames.Armor.MEDIEVALHELMET, 1));
            tHelmet.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Helmet.AQUABREATHING, References.InternalNames.Armor.MEDIEVALHELMET, 1));
            tHelmet.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Helmet.NIGHTSIGHT, References.InternalNames.Armor.MEDIEVALHELMET, 1));
            tHelmet.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Helmet.THORNS, References.InternalNames.Armor.MEDIEVALHELMET, 1));
            tHelmet.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Helmet.REINFORCED, References.InternalNames.Armor.MEDIEVALHELMET, 1));
            tHelmet.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Helmet.AUTOREPAIR, References.InternalNames.Armor.MEDIEVALHELMET, 1));
            tHelmet.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Helmet.ELECTRIC, References.InternalNames.Armor.MEDIEVALHELMET, 1));

            //Registering the positions to the chestplate
            MultiLayeredArmor tChestplate = MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALCHESTPLATE);
            tChestplate.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Chestplate.BASE, References.InternalNames.Armor.MEDIEVALCHESTPLATE, 1));
            tChestplate.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Chestplate.SHOULDERLEFT, References.InternalNames.Armor.MEDIEVALCHESTPLATE, 1));
            tChestplate.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Chestplate.SHOULDERRIGHT, References.InternalNames.Armor.MEDIEVALCHESTPLATE, 1));
            tChestplate.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Chestplate.FRONTLEFT, References.InternalNames.Armor.MEDIEVALCHESTPLATE, 1));
            tChestplate.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Chestplate.FRONTRIGHT, References.InternalNames.Armor.MEDIEVALCHESTPLATE, 1));
            tChestplate.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Chestplate.BACKLEFT, References.InternalNames.Armor.MEDIEVALCHESTPLATE, 1));
            tChestplate.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Chestplate.BACKRIGHT, References.InternalNames.Armor.MEDIEVALCHESTPLATE, 1));
            tChestplate.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Chestplate.STRENGTH, References.InternalNames.Armor.MEDIEVALCHESTPLATE, 1));
            tChestplate.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Chestplate.HASTE, References.InternalNames.Armor.MEDIEVALCHESTPLATE, 1));
            tChestplate.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Chestplate.FLYING, References.InternalNames.Armor.MEDIEVALCHESTPLATE, 1));
            tChestplate.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Chestplate.THORNS, References.InternalNames.Armor.MEDIEVALCHESTPLATE, 1));
            tChestplate.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Chestplate.REINFORCED, References.InternalNames.Armor.MEDIEVALCHESTPLATE, 1));
            tChestplate.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Chestplate.AUTOREPAIR, References.InternalNames.Armor.MEDIEVALCHESTPLATE, 1));
            tChestplate.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Chestplate.ELECTRIC, References.InternalNames.Armor.MEDIEVALCHESTPLATE, 1));

            //Registering the positions to the leggins
            MultiLayeredArmor tLeggins = MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALLEGGINGS);
            tLeggins.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Leggings.BASE, References.InternalNames.Armor.MEDIEVALLEGGINGS, 1));
            tLeggins.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Leggings.FRONTLEFT, References.InternalNames.Armor.MEDIEVALLEGGINGS, 1));
            tLeggins.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Leggings.FRONTRIGHT, References.InternalNames.Armor.MEDIEVALLEGGINGS, 1));
            tLeggins.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Leggings.BACKLEFT, References.InternalNames.Armor.MEDIEVALLEGGINGS, 1));
            tLeggins.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Leggings.BACKRIGHT, References.InternalNames.Armor.MEDIEVALLEGGINGS, 1));
            tLeggins.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Leggings.SPEED, References.InternalNames.Armor.MEDIEVALLEGGINGS, 1));
            tLeggins.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Leggings.JUMPASSIST, References.InternalNames.Armor.MEDIEVALLEGGINGS, 1));
            tLeggins.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Leggings.UPHILLASSIST, References.InternalNames.Armor.MEDIEVALLEGGINGS, 1));
            tLeggins.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Leggings.THORNS, References.InternalNames.Armor.MEDIEVALLEGGINGS, 1));
            tLeggins.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Leggings.REINFORCED, References.InternalNames.Armor.MEDIEVALLEGGINGS, 1));
            tLeggins.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Leggings.AUTOREPAIR, References.InternalNames.Armor.MEDIEVALLEGGINGS, 1));
            tLeggins.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Leggings.ELECTRIC, References.InternalNames.Armor.MEDIEVALLEGGINGS, 1));

            //Registering the positions to the shoes
            MultiLayeredArmor tShoes = MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALSHOES);
            tShoes.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Shoes.BASE, References.InternalNames.Armor.MEDIEVALSHOES, 1));
            tShoes.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Shoes.LEFT, References.InternalNames.Armor.MEDIEVALSHOES, 1));
            tShoes.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Shoes.RIGHT, References.InternalNames.Armor.MEDIEVALSHOES, 1));
            tShoes.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Shoes.FALLASSIST, References.InternalNames.Armor.MEDIEVALSHOES, 1));
            tShoes.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Shoes.SWIMASSIST, References.InternalNames.Armor.MEDIEVALSHOES, 1));
            tShoes.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Shoes.REINFORCED, References.InternalNames.Armor.MEDIEVALSHOES, 1));
            tShoes.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Shoes.AUTOREPAIR, References.InternalNames.Armor.MEDIEVALSHOES, 1));
            tShoes.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Shoes.ELECTRIC, References.InternalNames.Armor.MEDIEVALSHOES, 1));
        }

        private static void registerUpgrades() {
            registerBaseLayers();
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

        private static void registerBaseLayers () {
            for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values()) {
                ChainLayer tBaseHelmet = new ChainLayer(References.InternalNames.AddonPositions.Helmet.BASE, References.InternalNames.Armor.MEDIEVALHELMET, References.InternalNames.AddonPositions.Helmet.BASE, tMaterial.getUniqueID(), new ResourceLocation(Textures.MultiArmor.Materials.Iron.tHelmetResource.getPrimaryLocation()), new ResourceLocation(Textures.MultiArmor.Materials.Iron.tHelmetResource.getSecondaryLocation()));
                MedievalAddonRegistry.getInstance().registerUpgrade(tBaseHelmet);
                MedievalAddonRegistry.getInstance().setPartStateForMaterial(tMaterial, tBaseHelmet, true);

                ChainLayer tBaseChestplate = new ChainLayer(References.InternalNames.AddonPositions.Chestplate.BASE, References.InternalNames.Armor.MEDIEVALCHESTPLATE, References.InternalNames.AddonPositions.Chestplate.BASE, tMaterial.getUniqueID(), new ResourceLocation(Textures.MultiArmor.Materials.Iron.tChestplateResource.getPrimaryLocation()), new ResourceLocation(Textures.MultiArmor.Materials.Iron.tChestplateResource.getSecondaryLocation()));
                MedievalAddonRegistry.getInstance().registerUpgrade(tBaseChestplate);
                MedievalAddonRegistry.getInstance().setPartStateForMaterial(tMaterial, tBaseChestplate, true);

                ChainLayer tBaseLeggings = new ChainLayer(References.InternalNames.AddonPositions.Leggings.BASE, References.InternalNames.Armor.MEDIEVALLEGGINGS, References.InternalNames.AddonPositions.Leggings.BASE, tMaterial.getUniqueID(), new ResourceLocation(Textures.MultiArmor.Materials.Iron.tLegginsResource.getPrimaryLocation()), new ResourceLocation(Textures.MultiArmor.Materials.Iron.tLegginsResource.getSecondaryLocation()));
                MedievalAddonRegistry.getInstance().registerUpgrade(tBaseLeggings);
                MedievalAddonRegistry.getInstance().setPartStateForMaterial(tMaterial, tBaseLeggings, true);

                ChainLayer tBaseShoes = new ChainLayer(References.InternalNames.AddonPositions.Shoes.BASE, References.InternalNames.Armor.MEDIEVALSHOES, References.InternalNames.AddonPositions.Shoes.BASE, tMaterial.getUniqueID(), new ResourceLocation(Textures.MultiArmor.Materials.Iron.tShoesResource.getPrimaryLocation()), new ResourceLocation(Textures.MultiArmor.Materials.Iron.tShoesResource.getSecondaryLocation()));
                MedievalAddonRegistry.getInstance().registerUpgrade(tBaseShoes);
                MedievalAddonRegistry.getInstance().setPartStateForMaterial(tMaterial, tBaseShoes, true);
            }
        }
        
        private static void registerTopHead() {
            for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values()) {
                ArmorUpgradeMedieval tTopHead = new ArmorUpgradeMedieval(References.InternalNames.Upgrades.Helmet.TOP, References.InternalNames.Armor.MEDIEVALHELMET, References.InternalNames.AddonPositions.Helmet.TOP, tMaterial.getUniqueID(), TranslationKeys.Items.MultiArmor.Upgrades.Helmet.TopHead, TextFormatting.RESET, 2.5F, 60, 1, new ResourceLocation("armory:items/multiarmor/upgrades/armory.Helmet_TopHead"), new ResourceLocation("armory:textures/models/multiarmor/upgrades/armory.Helmet_TopHead.png"));
                MedievalAddonRegistry.getInstance().registerUpgrade(tTopHead);

                MedievalAddonRegistry.getInstance().setPartStateForMaterial(tMaterial, tTopHead, true);
            }
        }

        private static void registerEarProtection() {
            for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values()) {
                ArmorUpgradeMedieval tEarProtectionLeft = new ArmorUpgradeMedieval(References.InternalNames.Upgrades.Helmet.LEFT, References.InternalNames.Armor.MEDIEVALHELMET, References.InternalNames.AddonPositions.Helmet.LEFT, tMaterial.getUniqueID(), TranslationKeys.Items.MultiArmor.Upgrades.Helmet.LeftEar, TextFormatting.RESET, 0.5F, 20, 1, new ResourceLocation("armory:items/multiarmor/upgrades/armory.Helmet_Protection_Ear_Left"), new ResourceLocation("armory:textures/models/multiarmor/upgrades/armory.Helmet_Protection_Ear_Left.png"));
                ArmorUpgradeMedieval tEarProtectionRight = new ArmorUpgradeMedieval(References.InternalNames.Upgrades.Helmet.RIGHT, References.InternalNames.Armor.MEDIEVALHELMET, References.InternalNames.AddonPositions.Helmet.RIGHT, tMaterial.getUniqueID(), TranslationKeys.Items.MultiArmor.Upgrades.Helmet.RightEar, TextFormatting.RESET, 0.5F, 20, 1, new ResourceLocation("armory:items/multiarmor/upgrades/armory.Helmet_Protection_Ear_Right"), new ResourceLocation("armory:textures/models/multiarmor/upgrades/armory.Helmet_Protection_Ear_Right.png"));
                MedievalAddonRegistry.getInstance().registerUpgrade(tEarProtectionLeft);
                MedievalAddonRegistry.getInstance().registerUpgrade(tEarProtectionRight);

                MedievalAddonRegistry.getInstance().setPartStateForMaterial(tMaterial, tEarProtectionLeft, true);
                MedievalAddonRegistry.getInstance().setPartStateForMaterial(tMaterial, tEarProtectionRight, true);
            }
        }

        private static void registerShoulderPads() {
            for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values()) {
                ArmorUpgradeMedieval tShoulderPadLeft = new ArmorUpgradeMedieval(References.InternalNames.Upgrades.Chestplate.SHOULDERLEFT, References.InternalNames.Armor.MEDIEVALCHESTPLATE, References.InternalNames.AddonPositions.Chestplate.SHOULDERLEFT, tMaterial.getUniqueID(), TranslationKeys.Items.MultiArmor.Upgrades.Chestplate.ShoulderLeft, TextFormatting.RESET, 1F, 50, 1, new ResourceLocation("armory:items/multiarmor/upgrades/armory.Chestplate_ShoulderPad_Left"), new ResourceLocation("armory:textures/models/multiarmor/upgrades/armory.Chestplate_ShoulderPad_Left.png"));
                ArmorUpgradeMedieval tShoulderPadRight = new ArmorUpgradeMedieval(References.InternalNames.Upgrades.Chestplate.SHOULDERRIGHT, References.InternalNames.Armor.MEDIEVALCHESTPLATE, References.InternalNames.AddonPositions.Chestplate.SHOULDERRIGHT, tMaterial.getUniqueID(), TranslationKeys.Items.MultiArmor.Upgrades.Chestplate.ShoulderRight, TextFormatting.RESET, 1F, 50, 1, new ResourceLocation("armory:items/multiarmor/upgrades/armory.Chestplate_ShoulderPad_Right"), new ResourceLocation("armory:textures/models/multiarmor/upgrades/armory.Chestplate_ShoulderPad_Right.png"));
                MedievalAddonRegistry.getInstance().registerUpgrade(tShoulderPadLeft);
                MedievalAddonRegistry.getInstance().registerUpgrade(tShoulderPadRight);

                MedievalAddonRegistry.getInstance().setPartStateForMaterial(tMaterial, tShoulderPadLeft, true);
                MedievalAddonRegistry.getInstance().setPartStateForMaterial(tMaterial, tShoulderPadRight, true);
            }
        }

        private static void registerFrontProtection() {
            for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values()) {
                ArmorUpgradeMedieval tFrontChestProtectionLeft = new ArmorUpgradeMedieval(References.InternalNames.Upgrades.Chestplate.FRONTLEFT, References.InternalNames.Armor.MEDIEVALCHESTPLATE, References.InternalNames.AddonPositions.Chestplate.FRONTLEFT, tMaterial.getUniqueID(), TranslationKeys.Items.MultiArmor.Upgrades.Chestplate.FrontLeft, TextFormatting.RESET, 2F, 150, 1, new ResourceLocation("armory:items/multiarmor/upgrades/armory.Chestplate_Protection_Front_Left"), new ResourceLocation("armory:textures/models/multiarmor/upgrades/armory.Chestplate_Protection_Front_Left.png"));
                ArmorUpgradeMedieval tFrontChestProtectionRight = new ArmorUpgradeMedieval(References.InternalNames.Upgrades.Chestplate.FRONTRIGHT, References.InternalNames.Armor.MEDIEVALCHESTPLATE, References.InternalNames.AddonPositions.Chestplate.FRONTRIGHT, tMaterial.getUniqueID(), TranslationKeys.Items.MultiArmor.Upgrades.Chestplate.FrontRight, TextFormatting.RESET, 2F, 150, 1, new ResourceLocation("armory:items/multiarmor/upgrades/armory.Chestplate_Protection_Front_Right"), new ResourceLocation("armory:textures/models/multiarmor/upgrades/armory.Chestplate_Protection_Front_Right.png"));
                MedievalAddonRegistry.getInstance().registerUpgrade(tFrontChestProtectionLeft);
                MedievalAddonRegistry.getInstance().registerUpgrade(tFrontChestProtectionRight);

                if (tMaterial.getUniqueID().equals(References.InternalNames.Materials.Vanilla.OBSIDIAN)) {
                    MedievalAddonRegistry.getInstance().setPartStateForMaterial(tMaterial, tFrontChestProtectionLeft, false);
                    MedievalAddonRegistry.getInstance().setPartStateForMaterial(tMaterial, tFrontChestProtectionRight, false);
                } else {
                    MedievalAddonRegistry.getInstance().setPartStateForMaterial(tMaterial, tFrontChestProtectionLeft, true);
                    MedievalAddonRegistry.getInstance().setPartStateForMaterial(tMaterial, tFrontChestProtectionRight, true);
                }
            }
        }

        private static void registerBackProtection() {
            for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values()) {
                ArmorUpgradeMedieval tBackChestProtectionLeft = new ArmorUpgradeMedieval(References.InternalNames.Upgrades.Chestplate.BACKLEFT, References.InternalNames.Armor.MEDIEVALCHESTPLATE, References.InternalNames.AddonPositions.Chestplate.BACKLEFT, tMaterial.getUniqueID(), TranslationKeys.Items.MultiArmor.Upgrades.Chestplate.BackLeft, TextFormatting.RESET, 2F, 150, 1, new ResourceLocation("armory:items/multiarmor/upgrades/armory.Chestplate_Protection_Back_Left"), new ResourceLocation("armory:textures/models/multiarmor/upgrades/armory.Chestplate_Protection_Back_Left.png"));
                ArmorUpgradeMedieval tBackChestProtectionRight = new ArmorUpgradeMedieval(References.InternalNames.Upgrades.Chestplate.BACKRIGHT, References.InternalNames.Armor.MEDIEVALCHESTPLATE, References.InternalNames.AddonPositions.Chestplate.BACKRIGHT, tMaterial.getUniqueID(), TranslationKeys.Items.MultiArmor.Upgrades.Chestplate.BackRight, TextFormatting.RESET, 2F, 150, 1, new ResourceLocation("armory:items/multiarmor/upgrades/armory.Chestplate_Protection_Back_Right"), new ResourceLocation("armory:textures/models/multiarmor/upgrades/armory.Chestplate_Protection_Back_Right.png"));
                MedievalAddonRegistry.getInstance().registerUpgrade(tBackChestProtectionLeft);
                MedievalAddonRegistry.getInstance().registerUpgrade(tBackChestProtectionRight);

                if (tMaterial.getUniqueID().equals(References.InternalNames.Materials.Vanilla.OBSIDIAN)) {
                    MedievalAddonRegistry.getInstance().setPartStateForMaterial(tMaterial, tBackChestProtectionLeft, false);
                    MedievalAddonRegistry.getInstance().setPartStateForMaterial(tMaterial, tBackChestProtectionRight, false);
                } else {
                    MedievalAddonRegistry.getInstance().setPartStateForMaterial(tMaterial, tBackChestProtectionLeft, true);
                    MedievalAddonRegistry.getInstance().setPartStateForMaterial(tMaterial, tBackChestProtectionRight, true);
                }
            }
        }

        private static void registerFrontLegProtection() {
            for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values()) {
                ArmorUpgradeMedieval tFrontLeggingsProtectionLeft = new ArmorUpgradeMedieval(References.InternalNames.Upgrades.Leggings.FRONTLEFT, References.InternalNames.Armor.MEDIEVALLEGGINGS, References.InternalNames.AddonPositions.Leggings.FRONTLEFT, tMaterial.getUniqueID(), TranslationKeys.Items.MultiArmor.Upgrades.Leggings.FrontLeft, TextFormatting.RESET, 1.5F, 125, 1, new ResourceLocation("armory:items/multiarmor/upgrades/armory.Leggins_Protection_Front_Left"), new ResourceLocation("armory:textures/models/multiarmor/upgrades/armory.Leggins_Protection_Front_Left.png"));
                ArmorUpgradeMedieval tFrontLeggingsProtectionRight = new ArmorUpgradeMedieval(References.InternalNames.Upgrades.Leggings.FRONTRIGHT, References.InternalNames.Armor.MEDIEVALLEGGINGS, References.InternalNames.AddonPositions.Leggings.FRONTRIGHT, tMaterial.getUniqueID(), TranslationKeys.Items.MultiArmor.Upgrades.Leggings.FrontRight, TextFormatting.RESET, 1.5F, 125, 1, new ResourceLocation("armory:items/multiarmor/upgrades/armory.Leggins_Protection_Front_Right"), new ResourceLocation("armory:textures/models/multiarmor/upgrades/armory.Leggins_Protection_Front_Right.png"));
                MedievalAddonRegistry.getInstance().registerUpgrade(tFrontLeggingsProtectionLeft);
                MedievalAddonRegistry.getInstance().registerUpgrade(tFrontLeggingsProtectionRight);

                if (tMaterial.getUniqueID().equals(References.InternalNames.Materials.Vanilla.OBSIDIAN)) {
                    MedievalAddonRegistry.getInstance().setPartStateForMaterial(tMaterial, tFrontLeggingsProtectionLeft, false);
                    MedievalAddonRegistry.getInstance().setPartStateForMaterial(tMaterial, tFrontLeggingsProtectionRight, false);
                } else {
                    MedievalAddonRegistry.getInstance().setPartStateForMaterial(tMaterial, tFrontLeggingsProtectionLeft, true);
                    MedievalAddonRegistry.getInstance().setPartStateForMaterial(tMaterial, tFrontLeggingsProtectionRight, true);
                }
            }
        }

        private static void registerBackLegProtection() {
            for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values()) {
                ArmorUpgradeMedieval tBackLeggingsProtectionLeft = new ArmorUpgradeMedieval(References.InternalNames.Upgrades.Leggings.BACKLEFT, References.InternalNames.Armor.MEDIEVALLEGGINGS, References.InternalNames.AddonPositions.Leggings.BACKLEFT, tMaterial.getUniqueID(), TranslationKeys.Items.MultiArmor.Upgrades.Leggings.BackLeft, TextFormatting.RESET, 2F, 150, 1, new ResourceLocation("armory:items/multiarmor/upgrades/armory.Leggins_Protection_Back_Left"), new ResourceLocation("armory:textures/models/multiarmor/upgrades/armory.Leggins_Protection_Back_Left.png"));
                ArmorUpgradeMedieval tBackLeggingsProtectionRight = new ArmorUpgradeMedieval(References.InternalNames.Upgrades.Leggings.BACKRIGHT, References.InternalNames.Armor.MEDIEVALLEGGINGS, References.InternalNames.AddonPositions.Leggings.BACKRIGHT, tMaterial.getUniqueID(), TranslationKeys.Items.MultiArmor.Upgrades.Leggings.BackRight, TextFormatting.RESET, 2F, 150, 1, new ResourceLocation("armory:items/multiarmor/upgrades/armory.Leggins_Protection_Back_Right"), new ResourceLocation("armory:textures/models/multiarmor/upgrades/armory.Leggins_Protection_Back_Right.png"));
                MedievalAddonRegistry.getInstance().registerUpgrade(tBackLeggingsProtectionLeft);
                MedievalAddonRegistry.getInstance().registerUpgrade(tBackLeggingsProtectionRight);

                if (tMaterial.getUniqueID().equals(References.InternalNames.Materials.Vanilla.OBSIDIAN)) {
                    MedievalAddonRegistry.getInstance().setPartStateForMaterial(tMaterial, tBackLeggingsProtectionLeft, false);
                    MedievalAddonRegistry.getInstance().setPartStateForMaterial(tMaterial, tBackLeggingsProtectionRight, false);
                } else {
                    MedievalAddonRegistry.getInstance().setPartStateForMaterial(tMaterial, tBackLeggingsProtectionLeft, true);
                    MedievalAddonRegistry.getInstance().setPartStateForMaterial(tMaterial, tBackLeggingsProtectionRight, true);
                }
            }
        }

        private static void registerShoeProtection() {
            for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values()) {
                ArmorUpgradeMedieval tShoeProtectionLeft = new ArmorUpgradeMedieval(References.InternalNames.Upgrades.Shoes.LEFT, References.InternalNames.Armor.MEDIEVALSHOES, References.InternalNames.AddonPositions.Shoes.LEFT, tMaterial.getUniqueID(), TranslationKeys.Items.MultiArmor.Upgrades.Shoes.Left, TextFormatting.RESET, 1F, 50, 1, new ResourceLocation("armory:items/multiarmor/upgrades/armory.Shoes_Protection_Left"), new ResourceLocation("armory:textures/models/multiarmor/upgrades/armory.Shoes_Protection_Left.png"));
                ArmorUpgradeMedieval tShoeProtectionRight = new ArmorUpgradeMedieval(References.InternalNames.Upgrades.Shoes.RIGHT, References.InternalNames.Armor.MEDIEVALSHOES, References.InternalNames.AddonPositions.Shoes.RIGHT, tMaterial.getUniqueID(), TranslationKeys.Items.MultiArmor.Upgrades.Shoes.Right, TextFormatting.RESET, 1F, 50, 1, new ResourceLocation("armory:items/multiarmor/upgrades/armory.Shoes_Protection_Right"), new ResourceLocation("armory:textures/models/multiarmor/upgrades/armory.Shoes_Protection_Right.png"));
                MedievalAddonRegistry.getInstance().registerUpgrade(tShoeProtectionLeft);
                MedievalAddonRegistry.getInstance().registerUpgrade(tShoeProtectionRight);

                MedievalAddonRegistry.getInstance().setPartStateForMaterial(tMaterial, tShoeProtectionLeft, true);
                MedievalAddonRegistry.getInstance().setPartStateForMaterial(tMaterial, tShoeProtectionRight, true);
            }
        }

        private static void modifyMaterials() {
            modifyHelmet();
            modifyChestplate();
            modifyLeggings();
            modifyShoes();
        }

        private static void modifyHelmet() {
            for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values()) {
                if (tMaterial.getUniqueID().equals(References.InternalNames.Materials.Vanilla.IRON)) {
                    tMaterial.setBaseDamageAbsorption(References.InternalNames.Armor.MEDIEVALHELMET, 1.5F);
                    tMaterial.setBaseDurability(References.InternalNames.Armor.MEDIEVALHELMET, 50);
                    tMaterial.setMaxModifiersOnPart(References.InternalNames.Armor.MEDIEVALHELMET, 1);
                } else if (tMaterial.getUniqueID().equals(References.InternalNames.Materials.Vanilla.OBSIDIAN)) {
                    tMaterial.setBaseDamageAbsorption(References.InternalNames.Armor.MEDIEVALHELMET, 3F);
                    tMaterial.setBaseDurability(References.InternalNames.Armor.MEDIEVALHELMET, 200);
                    tMaterial.setMaxModifiersOnPart(References.InternalNames.Armor.MEDIEVALHELMET, 2);
                } else {
                    MinecraftForge.EVENT_BUS.post(new ModifyMaterialEvent(tMaterial, MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALHELMET)));
                }
            }

        }

        private static void modifyChestplate() {
            for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values()) {
                if (tMaterial.getUniqueID().equals(References.InternalNames.Materials.Vanilla.IRON)) {
                    tMaterial.setBaseDamageAbsorption(References.InternalNames.Armor.MEDIEVALCHESTPLATE, 2.0F);
                    tMaterial.setBaseDurability(References.InternalNames.Armor.MEDIEVALCHESTPLATE, 50);
                    tMaterial.setMaxModifiersOnPart(References.InternalNames.Armor.MEDIEVALCHESTPLATE, 1);
                } else if (tMaterial.getUniqueID().equals(References.InternalNames.Materials.Vanilla.OBSIDIAN)) {
                    tMaterial.setBaseDamageAbsorption(References.InternalNames.Armor.MEDIEVALCHESTPLATE, 3.5F);
                    tMaterial.setBaseDurability(References.InternalNames.Armor.MEDIEVALCHESTPLATE, 200);
                    tMaterial.setMaxModifiersOnPart(References.InternalNames.Armor.MEDIEVALCHESTPLATE, 2);
                } else {
                    MinecraftForge.EVENT_BUS.post(new ModifyMaterialEvent(tMaterial, MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALCHESTPLATE)));
                }
            }
        }

        private static void modifyLeggings() {
            for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values()) {
                if (tMaterial.getUniqueID().equals(References.InternalNames.Materials.Vanilla.IRON)) {
                    tMaterial.setBaseDamageAbsorption(References.InternalNames.Armor.MEDIEVALLEGGINGS, 1.5F);
                    tMaterial.setBaseDurability(References.InternalNames.Armor.MEDIEVALLEGGINGS, 50);
                    tMaterial.setMaxModifiersOnPart(References.InternalNames.Armor.MEDIEVALLEGGINGS, 1);
                } else if (tMaterial.getUniqueID().equals(References.InternalNames.Materials.Vanilla.OBSIDIAN)) {
                    tMaterial.setBaseDamageAbsorption(References.InternalNames.Armor.MEDIEVALLEGGINGS, 3F);
                    tMaterial.setBaseDurability(References.InternalNames.Armor.MEDIEVALLEGGINGS, 200);
                    tMaterial.setMaxModifiersOnPart(References.InternalNames.Armor.MEDIEVALLEGGINGS, 2);
                } else {
                    MinecraftForge.EVENT_BUS.post(new ModifyMaterialEvent(tMaterial, MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALLEGGINGS)));
                }
            }
        }

        private static void modifyShoes() {
            for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values()) {
                if (tMaterial.getUniqueID().equals(References.InternalNames.Materials.Vanilla.IRON)) {
                    tMaterial.setBaseDamageAbsorption(References.InternalNames.Armor.MEDIEVALSHOES, 1.0F);
                    tMaterial.setBaseDurability(References.InternalNames.Armor.MEDIEVALSHOES, 50);
                    tMaterial.setMaxModifiersOnPart(References.InternalNames.Armor.MEDIEVALSHOES, 1);
                } else if (tMaterial.getUniqueID().equals(References.InternalNames.Materials.Vanilla.OBSIDIAN)) {
                    tMaterial.setBaseDamageAbsorption(References.InternalNames.Armor.MEDIEVALSHOES, 2.5F);
                    tMaterial.setBaseDurability(References.InternalNames.Armor.MEDIEVALSHOES, 200);
                    tMaterial.setMaxModifiersOnPart(References.InternalNames.Armor.MEDIEVALSHOES, 2);
                } else {
                    MinecraftForge.EVENT_BUS.post(new ModifyMaterialEvent(tMaterial, MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALSHOES)));
                }
            }
        }


        public static void prepareGame() {

        }
    }

    public static class GlobalInitialization {
        public static void RegisterAnvilMaterials()
        {
            AnvilMaterialRegistry.getInstance().registerNewAnvilMaterial(new AnvilMaterial(References.InternalNames.Materials.Anvil.STONE, 250, I18n.translateToLocal(TranslationKeys.Materials.Anvil.Stone)));
            AnvilMaterialRegistry.getInstance().registerNewAnvilMaterial(new AnvilMaterial(References.InternalNames.Materials.Anvil.IRON, 1500, I18n.translateToLocal(TranslationKeys.Materials.Anvil.Iron)));
            AnvilMaterialRegistry.getInstance().registerNewAnvilMaterial(new AnvilMaterial(References.InternalNames.Materials.Anvil.OBSIDIAN, 2200, I18n.translateToLocal(TranslationKeys.Materials.Anvil.Obsidian)));
        }
    }

    public static class SystemInit {
        public static void RegisterBlocks() {
            GeneralRegistry.Blocks.blockFirePit = new BlockFirePit();
            GeneralRegistry.Blocks.blockBlackSmithsAnvil = new BlockBlackSmithsAnvil();
            GeneralRegistry.Blocks.blockFirePlace = new BlockFirePlace();

            GameRegistry.register(GeneralRegistry.Blocks.blockFirePit);
            GameRegistry.register(new ItemBlock(GeneralRegistry.Blocks.blockFirePit).setRegistryName(GeneralRegistry.Blocks.blockFirePit.getRegistryName()));
            GameRegistry.register(GeneralRegistry.Blocks.blockBlackSmithsAnvil);
            GameRegistry.register(new ItemBlockBlackSmithsAnvil(GeneralRegistry.Blocks.blockBlackSmithsAnvil));
            GameRegistry.register(GeneralRegistry.Blocks.blockFirePlace);
            GameRegistry.register(new ItemBlock(GeneralRegistry.Blocks.blockFirePlace).setRegistryName(GeneralRegistry.Blocks.blockFirePlace.getRegistryName()));
        }

        public static void RegisterItems() {
            GeneralRegistry.Items.heatedItem = new ItemHeatedItem();
            GeneralRegistry.Items.guide = new ItemSmithingsGuide();
            GeneralRegistry.Items.armorComponent = new ItemArmorComponent();

            MaterialRegistry.getInstance().getAllRegisteredArmors().values().forEach(GameRegistry::register);

            GameRegistry.register(GeneralRegistry.Items.heatedItem);
            GameRegistry.register(GeneralRegistry.Items.guide);
            GameRegistry.register(GeneralRegistry.Items.armorComponent);
        }

        public static void RegisterFluids () {
            GeneralRegistry.Fluids.moltenMetal = new FluidMoltenMetal();

            FluidRegistry.registerFluid(GeneralRegistry.Fluids.moltenMetal);

            //Makes sure that for Obsidian lava is produced instead of a molten metal.
            SmithsCore.getRegistry().getCommonBus().register(new ObsidianToLavaSetter());
        }

        public static void RegisterTileEntities() {
            GameRegistry.registerTileEntity(TileEntityFirePit.class, References.InternalNames.TileEntities.FirePitContainer);
            GameRegistry.registerTileEntity(TileEntityFireplace.class, References.InternalNames.TileEntities.FireplaceContainer);
            GameRegistry.registerTileEntity(TileEntityBlackSmithsAnvil.class, References.InternalNames.TileEntities.ArmorsAnvil);
        }

        public static void loadMaterialConfig() {

        }

        public static void removeRecipes() {

        }

        private static void tryRemoveRecipeFromGame(IRecipe pRecipe, Iterator pIterator) {
            int[] tOreID = OreDictionary.getOreIDs(pRecipe.getRecipeOutput());

            for (int tID : tOreID) {
                String pOreDicID = OreDictionary.getOreName(tID);
                if (pOreDicID.contains("nugget")) {
                    for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values()) {
                        if (pOreDicID.toLowerCase().contains(tMaterial.getOreDicName().toLowerCase())) {
                            try {
                                pIterator.remove();
                                return;
                            } catch (IllegalStateException ex) {
                                Armory.getLogger().info("Could not remove recipe of: " + ItemStackHelper.toString(pRecipe.getRecipeOutput()));
                            }
                        }
                    }
                }
            }
        }

        public static void initializeOreDic() {

        }
    }

}
