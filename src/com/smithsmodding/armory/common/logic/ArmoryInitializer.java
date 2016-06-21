package com.smithsmodding.armory.common.logic;
/*
 *   ArmoryInitializer
 *   Created by: Orion
 *   Created on: 17-9-2014
 */

import com.smithsmodding.armory.Armory;
import com.smithsmodding.armory.api.armor.ArmorAddonPosition;
import com.smithsmodding.armory.api.armor.MLAAddon;
import com.smithsmodding.armory.api.armor.MultiLayeredArmor;
import com.smithsmodding.armory.api.crafting.blacksmiths.component.HeatedAnvilRecipeComponent;
import com.smithsmodding.armory.api.crafting.blacksmiths.component.OreDicAnvilRecipeComponent;
import com.smithsmodding.armory.api.crafting.blacksmiths.component.StandardAnvilRecipeComponent;
import com.smithsmodding.armory.api.crafting.blacksmiths.recipe.AnvilRecipe;
import com.smithsmodding.armory.api.crafting.blacksmiths.recipe.ArmorUpgradeAnvilRecipe;
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
import com.smithsmodding.armory.common.factory.MedievalArmorFactory;
import com.smithsmodding.armory.common.fluid.FluidMoltenMetal;
import com.smithsmodding.armory.common.item.*;
import com.smithsmodding.armory.common.item.armor.tiermedieval.ArmorMedieval;
import com.smithsmodding.armory.common.item.block.ItemBlockBlackSmithsAnvil;
import com.smithsmodding.armory.common.material.ArmorMaterial;
import com.smithsmodding.armory.common.material.ChainLayer;
import com.smithsmodding.armory.common.material.MaterialRegistry;
import com.smithsmodding.armory.common.material.fluidmodifiers.ObsidianToLavaSetter;
import com.smithsmodding.armory.common.registry.AnvilMaterialRegistry;
import com.smithsmodding.armory.common.registry.AnvilRecipeRegistry;
import com.smithsmodding.armory.common.registry.GeneralRegistry;
import com.smithsmodding.armory.common.tileentity.TileEntityBlackSmithsAnvil;
import com.smithsmodding.armory.common.tileentity.TileEntityFirePit;
import com.smithsmodding.armory.common.tileentity.TileEntityFireplace;
import com.smithsmodding.armory.util.References;
import com.smithsmodding.armory.util.client.Textures;
import com.smithsmodding.armory.util.client.TranslationKeys;
import com.smithsmodding.smithscore.SmithsCore;
import com.smithsmodding.smithscore.util.common.ItemStackHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TextFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import java.util.HashMap;
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
            ArmorMaterial tIron = new ArmorMaterial(References.InternalNames.Materials.Vanilla.IRON, "Iron", true, 1865, 500, 0.225F, new ItemStack(Items.IRON_INGOT));
            ArmorMaterial tObsidian = new ArmorMaterial(References.InternalNames.Materials.Vanilla.OBSIDIAN, "Obsidian", true, 1404, 3000, 0.345F, new ItemStack(Item.getItemFromBlock(Blocks.OBSIDIAN)));

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
            initializeAnvilRecipes();
        }

        public static void initializeAnvilRecipes() {
            ItemStack tHammerStack = new ItemStack(GeneralRegistry.Items.hammer, 1);
            tHammerStack.setItemDamage(150);
            AnvilRecipe tHammerRecipe = new AnvilRecipe().setCraftingSlotContent(3, (new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.INGOT, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F)))
                    .setCraftingSlotContent(7, (new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.INGOT, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F)))
                    .setCraftingSlotContent(9, (new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.INGOT, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F)))
                    .setCraftingSlotContent(13, (new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.INGOT, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F)))
                    .setCraftingSlotContent(14, (new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.INGOT, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F)))
                    .setCraftingSlotContent(8, (new OreDicAnvilRecipeComponent("stickWood", 1)))
                    .setCraftingSlotContent(12, (new OreDicAnvilRecipeComponent("stickWood", 1)))
                    .setCraftingSlotContent(16, (new OreDicAnvilRecipeComponent("stickWood", 1)))
                    .setCraftingSlotContent(20, (new OreDicAnvilRecipeComponent("stickWood", 1)))
                    .setProgress(4).setResult(tHammerStack).setHammerUsage(4).setTongUsage(0);
            AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.HAMMER, tHammerRecipe);

            ItemStack tTongStack = new ItemStack(GeneralRegistry.Items.tongs, 1);
            tTongStack.setItemDamage(150);
            AnvilRecipe tTongRecipe = new AnvilRecipe().setCraftingSlotContent(3, (new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F)))
                    .setCraftingSlotContent(7, (new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F)))
                    .setCraftingSlotContent(9, (new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F)))
                    .setCraftingSlotContent(13, (new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F)))
                    .setCraftingSlotContent(12, (new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.RING, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F)))
                    .setCraftingSlotContent(11, (new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.INGOT, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F)))
                    .setCraftingSlotContent(15, (new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.INGOT, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F)))
                    .setCraftingSlotContent(17, (new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.INGOT, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F)))
                    .setCraftingSlotContent(21, (new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.INGOT, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F)))
                    .setProgress(4).setResult(tTongStack).setHammerUsage(4).setTongUsage(0);
            AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.TONGS, tTongRecipe);

            initializeMedievalArmorAnvilRecipes();
            initializeMedievalUpgradeAnvilRecipes();
            initializeUpgradeRecipeSystem();
        }

        public static void initializeMedievalArmorAnvilRecipes() {
            for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values()) {
                ItemStack tRingStack = new ItemStack(GeneralRegistry.Items.metalRing, 1, tMaterial.getItemDamageMaterialIndex());
                NBTTagCompound pRingCompound = new NBTTagCompound();
                pRingCompound.setString(References.NBTTagCompoundData.Material, tMaterial.getUniqueID());
                tRingStack.setTagCompound(pRingCompound);

                AnvilRecipe tRingRecipe = new AnvilRecipe().setCraftingSlotContent(0, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.95F)))
                        .setProgress(9).setResult(tRingStack).setHammerUsage(4).setTongUsage(0).setShapeLess();

                AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.RING + tMaterial.getOreDicName(), tRingRecipe);

                ItemStack tPlateStack = new ItemStack(GeneralRegistry.Items.metalPlate, 1, tMaterial.getItemDamageMaterialIndex());
                NBTTagCompound pPlateCompound = new NBTTagCompound();
                pPlateCompound.setString(References.NBTTagCompoundData.Material, tMaterial.getUniqueID());
                tPlateStack.setTagCompound(pPlateCompound);

                AnvilRecipe tPlateRecipe = new AnvilRecipe().setCraftingSlotContent(0, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.INGOT, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.95F)))
                        .setProgress(15).setResult(tPlateStack).setHammerUsage(15).setTongUsage(2).setShapeLess();

                AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.PLATE + tMaterial.getOreDicName(), tPlateRecipe);

                ItemStack tNuggetStack = new ItemStack(GeneralRegistry.Items.metalNugget, 9, tMaterial.getItemDamageMaterialIndex());
                NBTTagCompound pNuggetCompound = new NBTTagCompound();
                pNuggetCompound.setString(References.NBTTagCompoundData.Material, tMaterial.getUniqueID());
                tNuggetStack.setTagCompound(pNuggetCompound);

                AnvilRecipe tNuggetRecipe = new AnvilRecipe().setCraftingSlotContent(0, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.INGOT, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F) * 0.95F)))
                        .setProgress(6).setResult(tNuggetStack).setHammerUsage(4).setTongUsage(0).setShapeLess();

                AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.NUGGET + tMaterial.getOreDicName(), tNuggetRecipe);

                ItemStack tChainStack = new ItemStack(GeneralRegistry.Items.metalChain, 1, tMaterial.getItemDamageMaterialIndex());
                NBTTagCompound tChainCompound = new NBTTagCompound();
                tChainCompound.setString(References.NBTTagCompoundData.Material, tMaterial.getUniqueID());
                tChainStack.setTagCompound(tChainCompound);

                AnvilRecipe tChainRecipe = new AnvilRecipe().setCraftingSlotContent(2, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(6, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(8, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(10, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(12, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(14, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(16, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(18, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(22, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setProgress(10).setResult(tChainStack).setHammerUsage(16).setTongUsage(16);

                AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.CHAIN + tMaterial.getOreDicName(), tChainRecipe);

                if (!tMaterial.getIsBaseArmorMaterial())
                    continue;

                ItemStack tChestplateStack = MedievalArmorFactory.getInstance().buildNewMLAArmor(MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALCHESTPLATE), new HashMap<MLAAddon, Integer>(), tMaterial.getBaseDurability(References.InternalNames.Armor.MEDIEVALCHESTPLATE), tMaterial.getUniqueID());
                AnvilRecipe tChestplateRecipe = new AnvilRecipe().setCraftingSlotContent(0, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.CHAIN, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(4, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.CHAIN, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(5, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.CHAIN, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(6, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(8, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(9, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.CHAIN, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(10, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.CHAIN, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(11, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(12, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.CHAIN, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(13, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(14, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.CHAIN, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(15, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.CHAIN, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(16, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(17, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.CHAIN, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(18, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(19, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.CHAIN, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(20, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.CHAIN, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(21, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(22, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.CHAIN, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(23, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(24, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.CHAIN, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setProgress(20).setResult(tChestplateStack).setHammerUsage(38).setTongUsage(24);
                AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.CHESTPLATE + tMaterial.getOreDicName(), tChestplateRecipe);

                ItemStack tHelmetStack = MedievalArmorFactory.getInstance().buildNewMLAArmor(MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALHELMET), new HashMap<MLAAddon, Integer>(), tMaterial.getBaseDurability(References.InternalNames.Armor.MEDIEVALHELMET), tMaterial.getUniqueID());
                AnvilRecipe tHelmetRecipe = new AnvilRecipe().setCraftingSlotContent(0, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.CHAIN, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(1, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.CHAIN, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(2, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.CHAIN, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(3, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.CHAIN, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(4, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.CHAIN, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(5, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.CHAIN, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(6, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(7, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(8, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(9, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.CHAIN, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(10, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.CHAIN, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(12, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(14, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.CHAIN, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setProgress(20).setResult(tHelmetStack).setHammerUsage(28).setTongUsage(16);
                AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.HELMET + tMaterial.getOreDicName(), tHelmetRecipe);

                ItemStack tPantsStack = MedievalArmorFactory.getInstance().buildNewMLAArmor(MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALLEGGINGS), new HashMap<MLAAddon, Integer>(), tMaterial.getBaseDurability(References.InternalNames.Armor.MEDIEVALLEGGINGS), tMaterial.getUniqueID());
                AnvilRecipe tPantsRecipe = new AnvilRecipe().setCraftingSlotContent(0, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.CHAIN, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(1, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.CHAIN, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(2, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.CHAIN, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(3, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.CHAIN, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(4, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.CHAIN, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))

                        .setCraftingSlotContent(5, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.CHAIN, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(6, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(7, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(8, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(9, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.CHAIN, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))

                        .setCraftingSlotContent(10, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.CHAIN, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(11, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(13, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(14, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.CHAIN, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))

                        .setCraftingSlotContent(15, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.CHAIN, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(16, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(18, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(19, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.CHAIN, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))

                        .setCraftingSlotContent(20, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.CHAIN, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(21, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(23, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(24, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.CHAIN, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setProgress(20).setResult(tPantsStack).setHammerUsage(28).setTongUsage(16);
                AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.LEGGINGS + tMaterial.getOreDicName(), tPantsRecipe);

                ItemStack tShoeStack = MedievalArmorFactory.getInstance().buildNewMLAArmor(MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALSHOES), new HashMap<MLAAddon, Integer>(), tMaterial.getBaseDurability(References.InternalNames.Armor.MEDIEVALSHOES), tMaterial.getUniqueID());
                AnvilRecipe tShoeRecipe = new AnvilRecipe().setCraftingSlotContent(6, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.CHAIN, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(8, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.CHAIN, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(10, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(11, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.CHAIN, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(13, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.CHAIN, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(14, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(15, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.CHAIN, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(16, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.CHAIN, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(18, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.CHAIN, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(19, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.CHAIN, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.85F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.95F)))
                        .setProgress(20).setResult(tShoeStack).setHammerUsage(18).setTongUsage(12);
                AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.SHOES + tMaterial.getOreDicName(), tShoeRecipe);
            }
        }

        public static void initializeMedievalUpgradeAnvilRecipes() {
            initializeMedievalHelmetUpgradeAnvilRecipes();
            initializeMedievalChestPlateUpgradeAnvilRecipes();
            initializeMedievalLeggingsUpgradeAnvilRecipes();
            initializeMedievalShoesUpgradeAnvilRecipes();
        }

        public static void initializeMedievalHelmetUpgradeAnvilRecipes() {
            for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values()) {
                if (MedievalAddonRegistry.getInstance().getPartStateForMaterial(tMaterial, References.InternalNames.Upgrades.Helmet.TOP)) {
                    ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval) MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALHELMET).getAddon(References.InternalNames.Upgrades.Helmet.TOP + "-" + tMaterial.getUniqueID());
                    ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.armorComponent, 1);
                    NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgrade.getUniqueMaterialID());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALHELMET).getUniqueID());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, tUpgrade.getUniqueID());

                    tUpgradeStack.setTagCompound(pUpgradeCompound);

                    AnvilRecipe tRecipe = new AnvilRecipe()
                            .setCraftingSlotContent(6, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(7, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(8, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(10, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(14, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setResult(tUpgradeStack).setHammerUsage((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() / 300).setTongUsage((int) (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() - 1000) / 300).setProgress((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() / 100);

                    AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.HELMETTOP + tMaterial.getOreDicName(), tRecipe);
                }

                if (MedievalAddonRegistry.getInstance().getPartStateForMaterial(tMaterial, References.InternalNames.Upgrades.Helmet.LEFT)) {
                    ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval) MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALHELMET).getAddon(References.InternalNames.Upgrades.Helmet.LEFT + "-" + tMaterial.getUniqueID());
                    ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.armorComponent, 1);
                    NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgrade.getUniqueMaterialID());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALHELMET).getUniqueID());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, tUpgrade.getUniqueID());

                    tUpgradeStack.setTagCompound(pUpgradeCompound);

                    AnvilRecipe tRecipe = new AnvilRecipe()
                            .setCraftingSlotContent(13, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(18, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(23, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setResult(tUpgradeStack).setHammerUsage((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() / 300).setTongUsage((int) (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() - 1000) / 300).setProgress((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() / 100);

                    AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.HELMETLEFT + tMaterial.getOreDicName(), tRecipe);
                }

                if (MedievalAddonRegistry.getInstance().getPartStateForMaterial(tMaterial, References.InternalNames.Upgrades.Helmet.RIGHT)) {
                    ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval) MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALHELMET).getAddon(References.InternalNames.Upgrades.Helmet.RIGHT + "-" + tMaterial.getUniqueID());
                    ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.armorComponent, 1);
                    NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgrade.getUniqueMaterialID());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALHELMET).getUniqueID());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, tUpgrade.getUniqueID());

                    tUpgradeStack.setTagCompound(pUpgradeCompound);

                    AnvilRecipe tRecipe = new AnvilRecipe()
                            .setCraftingSlotContent(11, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(16, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(21, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setResult(tUpgradeStack).setHammerUsage((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() / 300).setTongUsage((int) (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() - 1000) / 300).setProgress((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() / 100);

                    AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.HELMETRIGHT + tMaterial.getOreDicName(), tRecipe);
                }
            }
        }

        public static void initializeMedievalChestPlateUpgradeAnvilRecipes() {
            for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values()) {
                if (MedievalAddonRegistry.getInstance().getPartStateForMaterial(tMaterial, References.InternalNames.Upgrades.Chestplate.SHOULDERLEFT)) {
                    ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval) MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALCHESTPLATE).getAddon(References.InternalNames.Upgrades.Chestplate.SHOULDERLEFT + "-" + tMaterial.getUniqueID());
                    ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.armorComponent, 1);
                    NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgrade.getUniqueMaterialID());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALCHESTPLATE).getUniqueID());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, tUpgrade.getUniqueID());

                    tUpgradeStack.setTagCompound(pUpgradeCompound);

                    AnvilRecipe tRecipe = new AnvilRecipe()
                            .setCraftingSlotContent(2, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(3, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(4, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(9, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setResult(tUpgradeStack).setHammerUsage((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() / 300).setTongUsage((int) (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() - 1000) / 300).setProgress((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() / 100);

                    AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.CHESTPLATESHOULDERLEFT + tMaterial.getOreDicName(), tRecipe);
                }

                if (MedievalAddonRegistry.getInstance().getPartStateForMaterial(tMaterial, References.InternalNames.Upgrades.Chestplate.SHOULDERRIGHT)) {
                    ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval) MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALCHESTPLATE).getAddon(References.InternalNames.Upgrades.Chestplate.SHOULDERRIGHT + "-" + tMaterial.getUniqueID());
                    ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.armorComponent, 1);
                    NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgrade.getUniqueMaterialID());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALCHESTPLATE).getUniqueID());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, tUpgrade.getUniqueID());

                    tUpgradeStack.setTagCompound(pUpgradeCompound);

                    AnvilRecipe tRecipe = new AnvilRecipe()
                            .setCraftingSlotContent(0, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(1, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(2, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(5, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setResult(tUpgradeStack).setHammerUsage((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() / 300).setTongUsage((int) (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() - 1000) / 300).setProgress((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() / 100);

                    AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.CHESTPLATESHOULDERRIGHT + tMaterial.getOreDicName(), tRecipe);
                }

                if (MedievalAddonRegistry.getInstance().getPartStateForMaterial(tMaterial, References.InternalNames.Upgrades.Chestplate.BACKRIGHT)) {
                    ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval) MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALCHESTPLATE).getAddon(References.InternalNames.Upgrades.Chestplate.BACKRIGHT + "-" + tMaterial.getUniqueID());
                    ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.armorComponent, 1);
                    NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgrade.getUniqueMaterialID());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALCHESTPLATE).getUniqueID());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, tUpgrade.getUniqueID());

                    tUpgradeStack.setTagCompound(pUpgradeCompound);

                    AnvilRecipe tRecipe = new AnvilRecipe()
                            .setCraftingSlotContent(5, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(10, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(11, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(15, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(16, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(21, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setResult(tUpgradeStack).setHammerUsage((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() / 300).setTongUsage((int) (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() - 1000) / 300).setProgress((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() / 100);

                    AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.CHESTPLATEBACKRIGHT + tMaterial.getOreDicName(), tRecipe);
                }

                if (MedievalAddonRegistry.getInstance().getPartStateForMaterial(tMaterial, References.InternalNames.Upgrades.Chestplate.BACKLEFT)) {
                    ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval) MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALCHESTPLATE).getAddon(References.InternalNames.Upgrades.Chestplate.BACKLEFT + "-" + tMaterial.getUniqueID());
                    ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.armorComponent, 1);
                    NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgrade.getUniqueMaterialID());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALCHESTPLATE).getUniqueID());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, tUpgrade.getUniqueID());

                    tUpgradeStack.setTagCompound(pUpgradeCompound);

                    AnvilRecipe tRecipe = new AnvilRecipe()
                            .setCraftingSlotContent(9, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(14, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(13, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(19, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(18, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(23, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setResult(tUpgradeStack).setHammerUsage((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() / 300).setTongUsage((int) (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() - 1000) / 300).setProgress((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() / 100);

                    AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.CHESTPLATEBACKLEFT + tMaterial.getOreDicName(), tRecipe);
                }

                if (MedievalAddonRegistry.getInstance().getPartStateForMaterial(tMaterial, References.InternalNames.Upgrades.Chestplate.FRONTRIGHT)) {
                    ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval) MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALCHESTPLATE).getAddon(References.InternalNames.Upgrades.Chestplate.FRONTRIGHT + "-" + tMaterial.getUniqueID());
                    ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.armorComponent, 1);
                    NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgrade.getUniqueMaterialID());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALCHESTPLATE).getUniqueID());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, tUpgrade.getUniqueID());

                    tUpgradeStack.setTagCompound(pUpgradeCompound);

                    AnvilRecipe tRecipe = new AnvilRecipe()
                            .setCraftingSlotContent(6, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(10, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(11, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(15, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(16, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(20, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setResult(tUpgradeStack).setHammerUsage((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() / 300).setTongUsage((int) (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() - 1000) / 300).setProgress((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() / 100);

                    AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.CHESTPLATEFRONTRIGHT + tMaterial.getOreDicName(), tRecipe);
                }

                if (MedievalAddonRegistry.getInstance().getPartStateForMaterial(tMaterial, References.InternalNames.Upgrades.Chestplate.FRONTLEFT)) {
                    ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval) MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALCHESTPLATE).getAddon(References.InternalNames.Upgrades.Chestplate.FRONTLEFT + "-" + tMaterial.getUniqueID());
                    ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.armorComponent, 1);
                    NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgrade.getUniqueMaterialID());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALCHESTPLATE).getUniqueID());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, tUpgrade.getUniqueID());

                    tUpgradeStack.setTagCompound(pUpgradeCompound);

                    AnvilRecipe tRecipe = new AnvilRecipe()
                            .setCraftingSlotContent(8, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(13, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(14, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(18, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(19, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(24, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setResult(tUpgradeStack).setHammerUsage((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() / 300).setTongUsage((int) (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() - 1000) / 300).setProgress((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() / 100);

                    AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.CHESTPLATEFRONTLEFT + tMaterial.getOreDicName(), tRecipe);
                }
            }
        }

        public static void initializeMedievalLeggingsUpgradeAnvilRecipes() {
            for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values()) {
                if (MedievalAddonRegistry.getInstance().getPartStateForMaterial(tMaterial, References.InternalNames.Upgrades.Leggings.BACKRIGHT)) {
                    ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval) MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALLEGGINGS).getAddon(References.InternalNames.Upgrades.Leggings.BACKRIGHT + "-" + tMaterial.getUniqueID());
                    ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.armorComponent, 1);
                    NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgrade.getUniqueMaterialID());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALLEGGINGS).getUniqueID());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, tUpgrade.getUniqueID());

                    tUpgradeStack.setTagCompound(pUpgradeCompound);

                    AnvilRecipe tRecipe = new AnvilRecipe()
                            .setCraftingSlotContent(1, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(6, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(11, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(16, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(21, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setResult(tUpgradeStack).setHammerUsage((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() / 300).setTongUsage((int) (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() - 1000) / 300).setProgress((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() / 100);

                    AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.LEGGINGSBACKRIGHT + tMaterial.getOreDicName(), tRecipe);
                }

                if (MedievalAddonRegistry.getInstance().getPartStateForMaterial(tMaterial, References.InternalNames.Upgrades.Leggings.BACKLEFT)) {
                    ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval) MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALLEGGINGS).getAddon(References.InternalNames.Upgrades.Leggings.BACKLEFT + "-" + tMaterial.getUniqueID());
                    ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.armorComponent, 1);
                    NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgrade.getUniqueMaterialID());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALLEGGINGS).getUniqueID());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, tUpgrade.getUniqueID());

                    tUpgradeStack.setTagCompound(pUpgradeCompound);

                    AnvilRecipe tRecipe = new AnvilRecipe()
                            .setCraftingSlotContent(3, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(8, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(13, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(18, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(23, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setResult(tUpgradeStack).setHammerUsage((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() / 300).setTongUsage((int) (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() - 1000) / 300).setProgress((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() / 100);

                    AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.LEGGINGSBACKLEFT + tMaterial.getOreDicName(), tRecipe);
                }

                if (MedievalAddonRegistry.getInstance().getPartStateForMaterial(tMaterial, References.InternalNames.Upgrades.Leggings.FRONTRIGHT)) {
                    ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval) MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALLEGGINGS).getAddon(References.InternalNames.Upgrades.Leggings.FRONTRIGHT + "-" + tMaterial.getUniqueID());
                    ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.armorComponent, 1);
                    NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgrade.getUniqueMaterialID());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALLEGGINGS).getUniqueID());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, tUpgrade.getUniqueID());

                    tUpgradeStack.setTagCompound(pUpgradeCompound);

                    AnvilRecipe tRecipe = new AnvilRecipe()
                            .setCraftingSlotContent(6, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(7, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(11, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(12, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(16, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(17, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setResult(tUpgradeStack).setHammerUsage((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() / 300).setTongUsage((int) (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() - 1000) / 300).setProgress((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() / 100);

                    AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.LEGGINGSFRONTRIGHT + tMaterial.getOreDicName(), tRecipe);
                }

                if (MedievalAddonRegistry.getInstance().getPartStateForMaterial(tMaterial, References.InternalNames.Upgrades.Leggings.FRONTLEFT)) {
                    ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval) MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALLEGGINGS).getAddon(References.InternalNames.Upgrades.Leggings.FRONTLEFT + "-" + tMaterial.getUniqueID());
                    ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.armorComponent, 1);
                    NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgrade.getUniqueMaterialID());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALLEGGINGS).getUniqueID());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, tUpgrade.getUniqueID());

                    tUpgradeStack.setTagCompound(pUpgradeCompound);

                    AnvilRecipe tRecipe = new AnvilRecipe()
                            .setCraftingSlotContent(7, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(8, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(12, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(13, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(17, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(18, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setResult(tUpgradeStack).setHammerUsage((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() / 300).setTongUsage((int) (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() - 1000) / 300).setProgress((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() / 100);

                    AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.LEGGINGSFRONTLEFT + tMaterial.getOreDicName(), tRecipe);
                }
            }
        }

        public static void initializeMedievalShoesUpgradeAnvilRecipes() {
            for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values()) {
                if (MedievalAddonRegistry.getInstance().getPartStateForMaterial(tMaterial, References.InternalNames.Upgrades.Shoes.LEFT)) {
                    ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval) MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALSHOES).getAddon(References.InternalNames.Upgrades.Shoes.LEFT + "-" + tMaterial.getUniqueID());
                    ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.armorComponent, 1);
                    NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgrade.getUniqueMaterialID());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALSHOES).getUniqueID());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, tUpgrade.getUniqueID());

                    tUpgradeStack.setTagCompound(pUpgradeCompound);

                    AnvilRecipe tRecipe = new AnvilRecipe()
                            .setCraftingSlotContent(6, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(11, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(15, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(16, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(20, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setResult(tUpgradeStack).setHammerUsage((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() / 300).setTongUsage((int) (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() - 1000) / 300).setProgress((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() / 100);

                    AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.SHOESLEFT + tMaterial.getOreDicName(), tRecipe);
                }

                if (MedievalAddonRegistry.getInstance().getPartStateForMaterial(tMaterial, References.InternalNames.Upgrades.Shoes.RIGHT)) {
                    ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval) MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALSHOES).getAddon(References.InternalNames.Upgrades.Shoes.RIGHT + "-" + tMaterial.getUniqueID());
                    ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.armorComponent, 1);
                    NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgrade.getUniqueMaterialID());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALSHOES).getUniqueID());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, tUpgrade.getUniqueID());

                    tUpgradeStack.setTagCompound(pUpgradeCompound);

                    AnvilRecipe tRecipe = new AnvilRecipe()
                            .setCraftingSlotContent(8, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(13, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(18, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(19, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(24, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setResult(tUpgradeStack).setHammerUsage((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() / 300).setTongUsage((int) (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() - 1000) / 300).setProgress((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() / 100);

                    AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.SHOESRIGHT + tMaterial.getOreDicName(), tRecipe);
                }
            }
        }

        public static void initializeUpgradeRecipeSystem() {
            initializeUpgradeHelmetRecipeSystem();
            initializeUpgradeChestPlateRecipeSystem();
            initializeUpgradeLeggingsRecipeSystem();
            initializeUpgradeShoesRecipeSystem();
        }

        public static void initializeUpgradeHelmetRecipeSystem() {
            for (IArmorMaterial tArmorMaterial : MaterialRegistry.getInstance().getArmorMaterials().values()) {
                if (!tArmorMaterial.getIsBaseArmorMaterial())
                    continue;

                for (IArmorMaterial tUpgradeMaterial : MaterialRegistry.getInstance().getArmorMaterials().values()) {
                    if (MedievalAddonRegistry.getInstance().getPartStateForMaterial(tUpgradeMaterial, References.InternalNames.Upgrades.Helmet.TOP)) {
                        ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.armorComponent, 1);
                        NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgradeMaterial.getUniqueID());
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, References.InternalNames.Armor.MEDIEVALHELMET);
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, References.InternalNames.AddonPositions.Helmet.TOP + "-" + tUpgradeMaterial.getUniqueID());

                        tUpgradeStack.setTagCompound(pUpgradeCompound);

                        AnvilRecipe tRecipe = new ArmorUpgradeAnvilRecipe(References.InternalNames.Armor.MEDIEVALHELMET, tArmorMaterial.getUniqueID())
                                .setCraftingSlotContent(6, new HeatedAnvilRecipeComponent(tArmorMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F))
                                .setCraftingSlotContent(8, new HeatedAnvilRecipeComponent(tArmorMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F))
                                .setUpgradeCraftingSlotComponent(2, new StandardAnvilRecipeComponent(tUpgradeStack))
                                .setProgress(8).setHammerUsage(5).setTongUsage(4);

                        AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.HELMETUPGRADETOP + tArmorMaterial.getOreDicName() + "." + tUpgradeMaterial.getOreDicName(), tRecipe);
                    }

                    if (MedievalAddonRegistry.getInstance().getPartStateForMaterial(tUpgradeMaterial, References.InternalNames.Upgrades.Helmet.LEFT)) {
                        ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.armorComponent, 1);
                        NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgradeMaterial.getUniqueID());
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, References.InternalNames.Armor.MEDIEVALHELMET);
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, References.InternalNames.Upgrades.Helmet.LEFT + "-" + tUpgradeMaterial.getUniqueID());

                        tUpgradeStack.setTagCompound(pUpgradeCompound);

                        AnvilRecipe tRecipe = new ArmorUpgradeAnvilRecipe(References.InternalNames.Armor.MEDIEVALHELMET, tArmorMaterial.getUniqueID())
                                .setCraftingSlotContent(6, new HeatedAnvilRecipeComponent(tArmorMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F))
                                .setCraftingSlotContent(16, new HeatedAnvilRecipeComponent(tArmorMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F))
                                .setUpgradeCraftingSlotComponent(10, new StandardAnvilRecipeComponent(tUpgradeStack))
                                .setProgress(8).setHammerUsage(5).setTongUsage(4);

                        AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.HELMETUPGRADELEFT + tArmorMaterial.getOreDicName() + "." + tUpgradeMaterial.getOreDicName(), tRecipe);
                    }

                    if (MedievalAddonRegistry.getInstance().getPartStateForMaterial(tUpgradeMaterial, References.InternalNames.Upgrades.Helmet.RIGHT)) {
                        ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.armorComponent, 1);
                        NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgradeMaterial.getUniqueID());
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, References.InternalNames.Armor.MEDIEVALHELMET);
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, References.InternalNames.Upgrades.Helmet.RIGHT + "-" + tUpgradeMaterial.getUniqueID());

                        tUpgradeStack.setTagCompound(pUpgradeCompound);

                        AnvilRecipe tRecipe = new ArmorUpgradeAnvilRecipe(References.InternalNames.Armor.MEDIEVALHELMET, tArmorMaterial.getUniqueID())
                                .setCraftingSlotContent(8, new HeatedAnvilRecipeComponent(tArmorMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F))
                                .setCraftingSlotContent(18, new HeatedAnvilRecipeComponent(tArmorMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F))
                                .setUpgradeCraftingSlotComponent(14, new StandardAnvilRecipeComponent(tUpgradeStack))
                                .setProgress(8).setHammerUsage(5).setTongUsage(4);

                        AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.HELMETUPGRADERIGHT + tArmorMaterial.getOreDicName() + "." + tUpgradeMaterial.getOreDicName(), tRecipe);
                    }
                }
            }
        }

        public static void initializeUpgradeChestPlateRecipeSystem() {
            for (IArmorMaterial tArmorMaterial : MaterialRegistry.getInstance().getArmorMaterials().values()) {
                if (!tArmorMaterial.getIsBaseArmorMaterial())
                    continue;

                for (IArmorMaterial tUpgradeMaterial : MaterialRegistry.getInstance().getArmorMaterials().values()) {
                    if (MedievalAddonRegistry.getInstance().getPartStateForMaterial(tUpgradeMaterial, References.InternalNames.Upgrades.Chestplate.SHOULDERLEFT)) {
                        ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.armorComponent, 1);
                        NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgradeMaterial.getUniqueID());
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, References.InternalNames.Armor.MEDIEVALCHESTPLATE);
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, References.InternalNames.AddonPositions.Chestplate.SHOULDERLEFT + "-" + tUpgradeMaterial.getUniqueID());

                        tUpgradeStack.setTagCompound(pUpgradeCompound);

                        AnvilRecipe tRecipe = new ArmorUpgradeAnvilRecipe(References.InternalNames.Armor.MEDIEVALCHESTPLATE, tArmorMaterial.getUniqueID())
                                .setCraftingSlotContent(7, new HeatedAnvilRecipeComponent(tArmorMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F))
                                .setCraftingSlotContent(11, new HeatedAnvilRecipeComponent(tArmorMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F))
                                .setUpgradeCraftingSlotComponent(6, new StandardAnvilRecipeComponent(tUpgradeStack))
                                .setProgress(12).setHammerUsage(7).setTongUsage(8);

                        AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.CHESTPLATEUPGRADESHOULDERLEFT + tArmorMaterial.getOreDicName() + "." + tUpgradeMaterial.getOreDicName(), tRecipe);
                    }

                    if (MedievalAddonRegistry.getInstance().getPartStateForMaterial(tUpgradeMaterial, References.InternalNames.Upgrades.Chestplate.SHOULDERRIGHT)) {
                        ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.armorComponent, 1);
                        NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgradeMaterial.getUniqueID());
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, References.InternalNames.Armor.MEDIEVALCHESTPLATE);
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, References.InternalNames.AddonPositions.Chestplate.SHOULDERRIGHT + "-" + tUpgradeMaterial.getUniqueID());

                        tUpgradeStack.setTagCompound(pUpgradeCompound);

                        AnvilRecipe tRecipe = new ArmorUpgradeAnvilRecipe(References.InternalNames.Armor.MEDIEVALCHESTPLATE, tArmorMaterial.getUniqueID())
                                .setCraftingSlotContent(7, new HeatedAnvilRecipeComponent(tArmorMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F))
                                .setCraftingSlotContent(13, new HeatedAnvilRecipeComponent(tArmorMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F))
                                .setUpgradeCraftingSlotComponent(8, new StandardAnvilRecipeComponent(tUpgradeStack))
                                .setProgress(12).setHammerUsage(7).setTongUsage(8);

                        AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.CHESTPLATEUPGRADESHOULDERRIGHT + tArmorMaterial.getOreDicName() + "." + tUpgradeMaterial.getOreDicName(), tRecipe);
                    }

                    if (MedievalAddonRegistry.getInstance().getPartStateForMaterial(tUpgradeMaterial, References.InternalNames.Upgrades.Chestplate.BACKRIGHT)) {
                        ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.armorComponent, 1);
                        NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgradeMaterial.getUniqueID());
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, References.InternalNames.Armor.MEDIEVALCHESTPLATE);
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, References.InternalNames.AddonPositions.Chestplate.BACKRIGHT + "-" + tUpgradeMaterial.getUniqueID());

                        tUpgradeStack.setTagCompound(pUpgradeCompound);

                        AnvilRecipe tRecipe = new ArmorUpgradeAnvilRecipe(References.InternalNames.Armor.MEDIEVALCHESTPLATE, tArmorMaterial.getUniqueID())
                                .setCraftingSlotContent(13, new HeatedAnvilRecipeComponent(tArmorMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F))
                                .setCraftingSlotContent(17, new HeatedAnvilRecipeComponent(tArmorMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F))
                                .setUpgradeCraftingSlotComponent(18, new StandardAnvilRecipeComponent(tUpgradeStack))
                                .setProgress(12).setHammerUsage(7).setTongUsage(8);

                        AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.CHESTPLATEUPGRADEBACKRIGHT + tArmorMaterial.getOreDicName() + "." + tUpgradeMaterial.getOreDicName(), tRecipe);
                    }

                    if (MedievalAddonRegistry.getInstance().getPartStateForMaterial(tUpgradeMaterial, References.InternalNames.Upgrades.Chestplate.BACKLEFT)) {
                        ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.armorComponent, 1);
                        NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgradeMaterial.getUniqueID());
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, References.InternalNames.Armor.MEDIEVALCHESTPLATE);
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, References.InternalNames.AddonPositions.Chestplate.BACKLEFT + "-" + tUpgradeMaterial.getUniqueID());

                        tUpgradeStack.setTagCompound(pUpgradeCompound);

                        AnvilRecipe tRecipe = new ArmorUpgradeAnvilRecipe(References.InternalNames.Armor.MEDIEVALCHESTPLATE, tArmorMaterial.getUniqueID())
                                .setCraftingSlotContent(11, new HeatedAnvilRecipeComponent(tArmorMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F))
                                .setCraftingSlotContent(17, new HeatedAnvilRecipeComponent(tArmorMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F))
                                .setUpgradeCraftingSlotComponent(16, new StandardAnvilRecipeComponent(tUpgradeStack))
                                .setProgress(12).setHammerUsage(7).setTongUsage(8);

                        AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.CHESTPLATEUPGRADEBACKLEFT + tArmorMaterial.getOreDicName() + "." + tUpgradeMaterial.getOreDicName(), tRecipe);
                    }

                    if (MedievalAddonRegistry.getInstance().getPartStateForMaterial(tUpgradeMaterial, References.InternalNames.Upgrades.Chestplate.FRONTRIGHT)) {
                        ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.armorComponent, 1);
                        NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgradeMaterial.getUniqueID());
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, References.InternalNames.Armor.MEDIEVALCHESTPLATE);
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, References.InternalNames.AddonPositions.Chestplate.FRONTRIGHT + "-" + tUpgradeMaterial.getUniqueID());

                        tUpgradeStack.setTagCompound(pUpgradeCompound);

                        AnvilRecipe tRecipe = new ArmorUpgradeAnvilRecipe(References.InternalNames.Armor.MEDIEVALCHESTPLATE, tArmorMaterial.getUniqueID())
                                .setCraftingSlotContent(13, new HeatedAnvilRecipeComponent(tArmorMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F))
                                .setCraftingSlotContent(17, new HeatedAnvilRecipeComponent(tArmorMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F))
                                .setUpgradeCraftingSlotComponent(18, new StandardAnvilRecipeComponent(tUpgradeStack))
                                .setProgress(12).setHammerUsage(7).setTongUsage(8);

                        AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.CHESTPLATEUPGRADEFRONTRIGHT + tArmorMaterial.getOreDicName() + "." + tUpgradeMaterial.getOreDicName(), tRecipe);
                    }

                    if (MedievalAddonRegistry.getInstance().getPartStateForMaterial(tUpgradeMaterial, References.InternalNames.Upgrades.Chestplate.FRONTLEFT)) {
                        ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.armorComponent, 1);
                        NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgradeMaterial.getUniqueID());
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, References.InternalNames.Armor.MEDIEVALCHESTPLATE);
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, References.InternalNames.AddonPositions.Chestplate.FRONTLEFT + "-" + tUpgradeMaterial.getUniqueID());

                        tUpgradeStack.setTagCompound(pUpgradeCompound);

                        AnvilRecipe tRecipe = new ArmorUpgradeAnvilRecipe(References.InternalNames.Armor.MEDIEVALCHESTPLATE, tArmorMaterial.getUniqueID())
                                .setCraftingSlotContent(11, new HeatedAnvilRecipeComponent(tArmorMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F))
                                .setCraftingSlotContent(17, new HeatedAnvilRecipeComponent(tArmorMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F))
                                .setUpgradeCraftingSlotComponent(16, new StandardAnvilRecipeComponent(tUpgradeStack))
                                .setProgress(12).setHammerUsage(7).setTongUsage(8);

                        AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.CHESTPLATEUPGRADEFRONTLEFT + tArmorMaterial.getOreDicName() + "." + tUpgradeMaterial.getOreDicName(), tRecipe);
                    }
                }
            }
        }

        public static void initializeUpgradeLeggingsRecipeSystem() {
            for (IArmorMaterial tArmorMaterial : MaterialRegistry.getInstance().getArmorMaterials().values()) {
                if (!tArmorMaterial.getIsBaseArmorMaterial())
                    continue;

                for (IArmorMaterial tUpgradeMaterial : MaterialRegistry.getInstance().getArmorMaterials().values()) {
                    if (MedievalAddonRegistry.getInstance().getPartStateForMaterial(tUpgradeMaterial, References.InternalNames.Upgrades.Leggings.BACKRIGHT)) {
                        ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.armorComponent, 1);
                        NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgradeMaterial.getUniqueID());
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, References.InternalNames.Armor.MEDIEVALLEGGINGS);
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, References.InternalNames.AddonPositions.Leggings.BACKRIGHT + "-" + tUpgradeMaterial.getUniqueID());

                        tUpgradeStack.setTagCompound(pUpgradeCompound);

                        AnvilRecipe tRecipe = new ArmorUpgradeAnvilRecipe(References.InternalNames.Armor.MEDIEVALLEGGINGS, tArmorMaterial.getUniqueID())
                                .setCraftingSlotContent(13, new HeatedAnvilRecipeComponent(tArmorMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F))
                                .setCraftingSlotContent(17, new HeatedAnvilRecipeComponent(tArmorMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F))
                                .setUpgradeCraftingSlotComponent(18, new StandardAnvilRecipeComponent(tUpgradeStack))
                                .setProgress(12).setHammerUsage(7).setTongUsage(6);

                        AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.LEGGINGSUPGRADEBACKRIGHT + tArmorMaterial.getOreDicName() + "." + tUpgradeMaterial.getOreDicName(), tRecipe);
                    }

                    if (MedievalAddonRegistry.getInstance().getPartStateForMaterial(tUpgradeMaterial, References.InternalNames.Upgrades.Leggings.BACKLEFT)) {
                        ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.armorComponent, 1);
                        NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgradeMaterial.getUniqueID());
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, References.InternalNames.Armor.MEDIEVALLEGGINGS);
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, References.InternalNames.AddonPositions.Leggings.BACKLEFT + "-" + tUpgradeMaterial.getUniqueID());

                        tUpgradeStack.setTagCompound(pUpgradeCompound);

                        AnvilRecipe tRecipe = new ArmorUpgradeAnvilRecipe(References.InternalNames.Armor.MEDIEVALLEGGINGS, tArmorMaterial.getUniqueID())
                                .setCraftingSlotContent(11, new HeatedAnvilRecipeComponent(tArmorMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F))
                                .setCraftingSlotContent(17, new HeatedAnvilRecipeComponent(tArmorMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F))
                                .setUpgradeCraftingSlotComponent(16, new StandardAnvilRecipeComponent(tUpgradeStack))
                                .setProgress(12).setHammerUsage(7).setTongUsage(6);

                        AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.LEGGINGSUPGRADEBACKLEFT + tArmorMaterial.getOreDicName() + "." + tUpgradeMaterial.getOreDicName(), tRecipe);
                    }

                    if (MedievalAddonRegistry.getInstance().getPartStateForMaterial(tUpgradeMaterial, References.InternalNames.Upgrades.Leggings.FRONTRIGHT)) {
                        ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.armorComponent, 1);
                        NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgradeMaterial.getUniqueID());
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, References.InternalNames.Armor.MEDIEVALLEGGINGS);
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, References.InternalNames.AddonPositions.Leggings.FRONTRIGHT + "-" + tUpgradeMaterial.getUniqueID());

                        tUpgradeStack.setTagCompound(pUpgradeCompound);

                        AnvilRecipe tRecipe = new ArmorUpgradeAnvilRecipe(References.InternalNames.Armor.MEDIEVALLEGGINGS, tArmorMaterial.getUniqueID())
                                .setCraftingSlotContent(13, new HeatedAnvilRecipeComponent(tArmorMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F))
                                .setCraftingSlotContent(7, new HeatedAnvilRecipeComponent(tArmorMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F))
                                .setUpgradeCraftingSlotComponent(8, new StandardAnvilRecipeComponent(tUpgradeStack))
                                .setProgress(12).setHammerUsage(7).setTongUsage(6);

                        AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.LEGGINGSUPGRADEFRONTRIGHT + tArmorMaterial.getOreDicName() + "." + tUpgradeMaterial.getOreDicName(), tRecipe);
                    }

                    if (MedievalAddonRegistry.getInstance().getPartStateForMaterial(tUpgradeMaterial, References.InternalNames.Upgrades.Leggings.FRONTLEFT)) {
                        ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.armorComponent, 1);
                        NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgradeMaterial.getUniqueID());
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, References.InternalNames.Armor.MEDIEVALLEGGINGS);
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, References.InternalNames.AddonPositions.Leggings.FRONTLEFT + "-" + tUpgradeMaterial.getUniqueID());

                        tUpgradeStack.setTagCompound(pUpgradeCompound);

                        AnvilRecipe tRecipe = new ArmorUpgradeAnvilRecipe(References.InternalNames.Armor.MEDIEVALLEGGINGS, tArmorMaterial.getUniqueID())
                                .setCraftingSlotContent(11, new HeatedAnvilRecipeComponent(tArmorMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F))
                                .setCraftingSlotContent(7, new HeatedAnvilRecipeComponent(tArmorMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F))
                                .setUpgradeCraftingSlotComponent(6, new StandardAnvilRecipeComponent(tUpgradeStack))
                                .setProgress(12).setHammerUsage(7).setTongUsage(6);

                        AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.LEGGINGSUPGRADEFRONTLEFT + tArmorMaterial.getOreDicName() + "." + tUpgradeMaterial.getOreDicName(), tRecipe);
                    }
                }
            }
        }

        public static void initializeUpgradeShoesRecipeSystem() {
            for (IArmorMaterial tArmorMaterial : MaterialRegistry.getInstance().getArmorMaterials().values()) {
                if (!tArmorMaterial.getIsBaseArmorMaterial())
                    continue;

                for (IArmorMaterial tUpgradeMaterial : MaterialRegistry.getInstance().getArmorMaterials().values()) {
                    if (MedievalAddonRegistry.getInstance().getPartStateForMaterial(tUpgradeMaterial, References.InternalNames.Upgrades.Shoes.LEFT)) {
                        ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.armorComponent, 1);
                        NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgradeMaterial.getUniqueID());
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, References.InternalNames.Armor.MEDIEVALSHOES);
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, References.InternalNames.AddonPositions.Shoes.LEFT + "-" + tUpgradeMaterial.getUniqueID());

                        tUpgradeStack.setTagCompound(pUpgradeCompound);

                        AnvilRecipe tRecipe = new ArmorUpgradeAnvilRecipe(References.InternalNames.Armor.MEDIEVALSHOES, tArmorMaterial.getUniqueID())
                                .setCraftingSlotContent(11, new HeatedAnvilRecipeComponent(tArmorMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F))
                                .setCraftingSlotContent(7, new HeatedAnvilRecipeComponent(tArmorMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F))
                                .setUpgradeCraftingSlotComponent(6, new StandardAnvilRecipeComponent(tUpgradeStack))
                                .setProgress(8).setHammerUsage(4).setTongUsage(5);

                        AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.SHOESUPGRADELEFT + tArmorMaterial.getOreDicName() + "." + tUpgradeMaterial.getOreDicName(), tRecipe);
                    }

                    if (MedievalAddonRegistry.getInstance().getPartStateForMaterial(tUpgradeMaterial, References.InternalNames.Upgrades.Shoes.RIGHT)) {
                        ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.armorComponent, 1);
                        NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgradeMaterial.getUniqueID());
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, References.InternalNames.Armor.MEDIEVALSHOES);
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, References.InternalNames.AddonPositions.Shoes.RIGHT + "-" + tUpgradeMaterial.getUniqueID());

                        tUpgradeStack.setTagCompound(pUpgradeCompound);

                        AnvilRecipe tRecipe = new ArmorUpgradeAnvilRecipe(References.InternalNames.Armor.MEDIEVALSHOES, tArmorMaterial.getUniqueID())
                                .setCraftingSlotContent(13, new HeatedAnvilRecipeComponent(tArmorMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F))
                                .setCraftingSlotContent(7, new HeatedAnvilRecipeComponent(tArmorMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F))
                                .setUpgradeCraftingSlotComponent(8, new StandardAnvilRecipeComponent(tUpgradeStack))
                                .setProgress(8).setHammerUsage(4).setTongUsage(5);

                        AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.SHOESUPGRADERIGHT + tArmorMaterial.getOreDicName() + "." + tUpgradeMaterial.getOreDicName(), tRecipe);
                    }
                }
            }
        }
    }

    public static class GlobalInitialization {
        public static void RegisterAnvilMaterials()
        {
            AnvilMaterialRegistry.getInstance().registerNewAnvilMaterial(new AnvilMaterial(References.InternalNames.Materials.Anvil.STONE, 250, I18n.format(TranslationKeys.Materials.Anvil.Stone)));
            AnvilMaterialRegistry.getInstance().registerNewAnvilMaterial(new AnvilMaterial(References.InternalNames.Materials.Anvil.IRON, 1500, I18n.format(TranslationKeys.Materials.Anvil.Iron)));
            AnvilMaterialRegistry.getInstance().registerNewAnvilMaterial(new AnvilMaterial(References.InternalNames.Materials.Anvil.OBSIDIAN, 2200, I18n.format(TranslationKeys.Materials.Anvil.Obsidian)));
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
            GeneralRegistry.Items.tongs = new ItemTongs();
            GeneralRegistry.Items.hammer = new ItemHammer();
            GeneralRegistry.Items.metalRing = new ItemMetalRing();
            GeneralRegistry.Items.metalChain = new ItemMetalChain();
            GeneralRegistry.Items.metalNugget = new ItemNugget();
            GeneralRegistry.Items.metalPlate = new ItemPlate();

            MaterialRegistry.getInstance().getAllRegisteredArmors().values().forEach(GameRegistry::register);

            GameRegistry.register(GeneralRegistry.Items.heatedItem);
            GameRegistry.register(GeneralRegistry.Items.guide);
            GameRegistry.register(GeneralRegistry.Items.armorComponent);
            GameRegistry.register(GeneralRegistry.Items.tongs);
            GameRegistry.register(GeneralRegistry.Items.hammer);
            GameRegistry.register(GeneralRegistry.Items.metalRing);
            GameRegistry.register(GeneralRegistry.Items.metalChain);
            GameRegistry.register(GeneralRegistry.Items.metalNugget);
            GameRegistry.register(GeneralRegistry.Items.metalPlate);
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
