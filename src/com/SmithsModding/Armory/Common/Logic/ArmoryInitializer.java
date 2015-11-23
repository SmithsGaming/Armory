package com.SmithsModding.Armory.Common.Logic;
/*
 *   ArmoryInitializer
 *   Created by: Orion
 *   Created on: 17-9-2014
 */

import com.SmithsModding.Armory.API.Armor.ArmorAddonPosition;
import com.SmithsModding.Armory.API.Armor.MLAAddon;
import com.SmithsModding.Armory.API.Armor.MultiLayeredArmor;
import com.SmithsModding.Armory.API.Crafting.SmithingsAnvil.AnvilRecipeRegistry;
import com.SmithsModding.Armory.API.Crafting.SmithingsAnvil.Components.HeatedAnvilRecipeComponent;
import com.SmithsModding.Armory.API.Crafting.SmithingsAnvil.Components.OreDicAnvilRecipeComponent;
import com.SmithsModding.Armory.API.Crafting.SmithingsAnvil.Components.StandardAnvilRecipeComponent;
import com.SmithsModding.Armory.API.Crafting.SmithingsAnvil.Recipe.AnvilRecipe;
import com.SmithsModding.Armory.API.Crafting.SmithingsAnvil.Recipe.ArmorUpgradeAnvilRecipe;
import com.SmithsModding.Armory.API.Events.Common.*;
import com.SmithsModding.Armory.API.Knowledge.BlueprintRegistry;
import com.SmithsModding.Armory.API.Knowledge.IResearchTreeComponent;
import com.SmithsModding.Armory.API.Knowledge.KnowledgeRegistry;
import com.SmithsModding.Armory.API.Materials.IArmorMaterial;
import com.SmithsModding.Armory.Common.Addons.ArmorUpgradeMedieval;
import com.SmithsModding.Armory.Common.Addons.MedievalAddonRegistry;
import com.SmithsModding.Armory.Common.Blocks.BlockArmorsAnvil;
import com.SmithsModding.Armory.Common.Blocks.BlockBookBinder;
import com.SmithsModding.Armory.Common.Blocks.BlockFirePit;
import com.SmithsModding.Armory.Common.Blocks.BlockHeater;
import com.SmithsModding.Armory.Common.Config.ArmorDataConfigHandler;
import com.SmithsModding.Armory.Common.Config.ArmoryConfig;
import com.SmithsModding.Armory.Common.Factory.HeatedItemFactory;
import com.SmithsModding.Armory.Common.Factory.MedievalArmorFactory;
import com.SmithsModding.Armory.Common.Item.Armor.TierMedieval.ArmorMedieval;
import com.SmithsModding.Armory.Common.Item.Armor.TierMedieval.ItemUpgradeMedieval;
import com.SmithsModding.Armory.Common.Item.*;
import com.SmithsModding.Armory.Common.Item.Knowledge.ItemBlueprint;
import com.SmithsModding.Armory.Common.Item.Knowledge.ItemSmithingsGuide;
import com.SmithsModding.Armory.Common.Knowledge.Blueprint.*;
import com.SmithsModding.Armory.Common.Knowledge.Research.DebugResearchTreeComponent;
import com.SmithsModding.Armory.Common.Knowledge.Research.Implementations.*;
import com.SmithsModding.Armory.Common.Knowledge.Research.Implementations.ResearchResultComponents.BlueprintResultComponent;
import com.SmithsModding.Armory.Common.Material.ArmorMaterial;
import com.SmithsModding.Armory.Common.Material.MaterialRegistry;
import com.SmithsModding.Armory.Common.Registry.GeneralRegistry;
import com.SmithsModding.Armory.Common.TileEntity.Anvil.TileEntityArmorsAnvil;
import com.SmithsModding.Armory.Common.TileEntity.FirePit.TileEntityFirePit;
import com.SmithsModding.Armory.Common.TileEntity.FirePit.TileEntityHeater;
import com.SmithsModding.Armory.Common.TileEntity.TileEntityBookBinder;
import com.SmithsModding.Armory.Util.Client.Colors;
import com.SmithsModding.Armory.Util.Client.TextureAddressHelper;
import com.SmithsModding.Armory.Util.Client.TranslationKeys;
import com.SmithsModding.Armory.Util.Core.ItemStackHelper;
import com.SmithsModding.Armory.Util.References;
import cpw.mods.fml.common.registry.GameRegistry;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapedOreRecipe;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;

public class ArmoryInitializer {
    public static void InitializeServer() {
        MedievalInitialization.Initialize();
        SystemInit.RegisterBlocks();
        SystemInit.RegisterItems();
        SystemInit.RegisterTileEntities();
        SystemInit.loadMaterialConfig();
        MedievalInitialization.prepareGame();
        SystemInit.initializeOreDic();
    }

    public static void postInitializeServer() {
        SystemInit.removeRecipes();
        HeatedItemFactory.getInstance().reloadAllItemStackOreDic();
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
            MaterialRegistry.getInstance().registerNewArmor(new ArmorMedieval(References.InternalNames.Armor.MEDIEVALHELMET, 0));
            MaterialRegistry.getInstance().registerNewArmor(new ArmorMedieval(References.InternalNames.Armor.MEDIEVALCHESTPLATE, 1));
            MaterialRegistry.getInstance().registerNewArmor(new ArmorMedieval(References.InternalNames.Armor.MEDIEVALLEGGINGS, 2));
            MaterialRegistry.getInstance().registerNewArmor(new ArmorMedieval(References.InternalNames.Armor.MEDIEVALSHOES, 3));

            MinecraftForge.EVENT_BUS.register(new RegisterArmorEvent());
        }

        private static void registerMaterials() {
            ArmorMaterial tIron = new ArmorMaterial(References.InternalNames.Materials.Vanilla.IRON, TranslationKeys.Materials.VisibleNames.Iron, "Iron", EnumChatFormatting.DARK_GRAY, true, 1865, 0.225F, Colors.Metals.IRON, new ItemStack(Items.iron_ingot));
            ArmorMaterial tObsidian = new ArmorMaterial(References.InternalNames.Materials.Vanilla.OBSIDIAN, TranslationKeys.Materials.VisibleNames.Obsidian, "Obsidian", EnumChatFormatting.BLUE, true, 1404, 0.345F, Colors.Metals.OBSIDIAN, new ItemStack(Item.getItemFromBlock(Blocks.obsidian)));

            MaterialRegistry.getInstance().registerMaterial(tIron);
            MaterialRegistry.getInstance().registerMaterial(tObsidian);

            MinecraftForge.EVENT_BUS.post(new RegisterMaterialsEvent());
        }

        private static void registerAddonPositions() {
            //Registering the positions to the helmet
            MultiLayeredArmor tHelmet = MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALHELMET);
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
            tLeggins.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Shoes.LEFT, References.InternalNames.Armor.MEDIEVALSHOES, 1));
            tLeggins.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Shoes.RIGHT, References.InternalNames.Armor.MEDIEVALSHOES, 1));
            tLeggins.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Shoes.FALLASSIST, References.InternalNames.Armor.MEDIEVALSHOES, 1));
            tLeggins.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Shoes.SWIMASSIST, References.InternalNames.Armor.MEDIEVALSHOES, 1));
            tLeggins.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Shoes.REINFORCED, References.InternalNames.Armor.MEDIEVALSHOES, 1));
            tLeggins.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Shoes.AUTOREPAIR, References.InternalNames.Armor.MEDIEVALSHOES, 1));
            tLeggins.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Shoes.ELECTRIC, References.InternalNames.Armor.MEDIEVALSHOES, 1));
        }

        private static void registerUpgrades() {
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

        private static void registerTopHead() {
            for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values()) {
                ArmorUpgradeMedieval tTopHead = new ArmorUpgradeMedieval(References.InternalNames.Upgrades.Helmet.TOP, References.InternalNames.Armor.MEDIEVALHELMET, References.InternalNames.AddonPositions.Helmet.TOP, tMaterial.getInternalMaterialName(), TranslationKeys.Items.MultiArmor.Upgrades.Helmet.TopHead, "", 2.5F, 60, 1);
                MedievalAddonRegistry.getInstance().registerUpgrade(tTopHead);

                if (tMaterial.getInternalMaterialName().equals(References.InternalNames.Materials.Vanilla.OBSIDIAN)) {
                    tMaterial.registerNewActivePart(References.InternalNames.Upgrades.Helmet.TOP, false);
                } else {
                    tMaterial.registerNewActivePart(References.InternalNames.Upgrades.Helmet.TOP, !MinecraftForge.EVENT_BUS.post(new ActivateArmorAddonEvent(tMaterial, tTopHead)));
                }
            }
        }

        private static void registerEarProtection() {
            for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values()) {
                ArmorUpgradeMedieval tEarProtectionLeft = new ArmorUpgradeMedieval(References.InternalNames.Upgrades.Helmet.LEFT, References.InternalNames.Armor.MEDIEVALHELMET, References.InternalNames.AddonPositions.Helmet.LEFT, tMaterial.getInternalMaterialName(), TranslationKeys.Items.MultiArmor.Upgrades.Helmet.LeftEar, "", 0.5F, 20, 1);
                ArmorUpgradeMedieval tEarProtectionRight = new ArmorUpgradeMedieval(References.InternalNames.Upgrades.Helmet.RIGHT, References.InternalNames.Armor.MEDIEVALHELMET, References.InternalNames.AddonPositions.Helmet.RIGHT, tMaterial.getInternalMaterialName(), TranslationKeys.Items.MultiArmor.Upgrades.Helmet.RightEar, "", 0.5F, 20, 1);
                MedievalAddonRegistry.getInstance().registerUpgrade(tEarProtectionLeft);
                MedievalAddonRegistry.getInstance().registerUpgrade(tEarProtectionRight);

                tMaterial.registerNewActivePart(References.InternalNames.Upgrades.Helmet.LEFT, !MinecraftForge.EVENT_BUS.post(new ActivateArmorAddonEvent(tMaterial, tEarProtectionLeft)));
                tMaterial.registerNewActivePart(References.InternalNames.Upgrades.Helmet.RIGHT, !MinecraftForge.EVENT_BUS.post(new ActivateArmorAddonEvent(tMaterial, tEarProtectionRight)));
            }
        }

        private static void registerShoulderPads() {
            for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values()) {
                ArmorUpgradeMedieval tShoulderPadLeft = new ArmorUpgradeMedieval(References.InternalNames.Upgrades.Chestplate.SHOULDERLEFT, References.InternalNames.Armor.MEDIEVALCHESTPLATE, References.InternalNames.AddonPositions.Chestplate.SHOULDERLEFT, tMaterial.getInternalMaterialName(), TranslationKeys.Items.MultiArmor.Upgrades.Chestplate.ShoulderLeft, "", 1F, 50, 1);
                ArmorUpgradeMedieval tShoulderPadRight = new ArmorUpgradeMedieval(References.InternalNames.Upgrades.Chestplate.SHOULDERRIGHT, References.InternalNames.Armor.MEDIEVALCHESTPLATE, References.InternalNames.AddonPositions.Chestplate.SHOULDERRIGHT, tMaterial.getInternalMaterialName(), TranslationKeys.Items.MultiArmor.Upgrades.Chestplate.ShoulderRight, "", 1F, 50, 1);
                MedievalAddonRegistry.getInstance().registerUpgrade(tShoulderPadLeft);
                MedievalAddonRegistry.getInstance().registerUpgrade(tShoulderPadRight);

                tMaterial.registerNewActivePart(References.InternalNames.Upgrades.Chestplate.SHOULDERLEFT, !MinecraftForge.EVENT_BUS.post(new ActivateArmorAddonEvent(tMaterial, tShoulderPadLeft)));
                tMaterial.registerNewActivePart(References.InternalNames.Upgrades.Chestplate.SHOULDERRIGHT, !MinecraftForge.EVENT_BUS.post(new ActivateArmorAddonEvent(tMaterial, tShoulderPadRight)));
            }
        }

        private static void registerFrontProtection() {
            for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values()) {
                ArmorUpgradeMedieval tFrontChestProtectionLeft = new ArmorUpgradeMedieval(References.InternalNames.Upgrades.Chestplate.FRONTLEFT, References.InternalNames.Armor.MEDIEVALCHESTPLATE, References.InternalNames.AddonPositions.Chestplate.FRONTLEFT, tMaterial.getInternalMaterialName(), TranslationKeys.Items.MultiArmor.Upgrades.Chestplate.FrontLeft, "", 2F, 150, 1);
                ArmorUpgradeMedieval tFrontChestProtectionRight = new ArmorUpgradeMedieval(References.InternalNames.Upgrades.Chestplate.FRONTRIGHT, References.InternalNames.Armor.MEDIEVALCHESTPLATE, References.InternalNames.AddonPositions.Chestplate.FRONTRIGHT, tMaterial.getInternalMaterialName(), TranslationKeys.Items.MultiArmor.Upgrades.Chestplate.FrontRight, "", 2F, 150, 1);
                MedievalAddonRegistry.getInstance().registerUpgrade(tFrontChestProtectionLeft);
                MedievalAddonRegistry.getInstance().registerUpgrade(tFrontChestProtectionRight);

                if (tMaterial.getInternalMaterialName().equals(References.InternalNames.Materials.Vanilla.OBSIDIAN)) {
                    tMaterial.registerNewActivePart(References.InternalNames.Upgrades.Chestplate.FRONTLEFT, false);
                    tMaterial.registerNewActivePart(References.InternalNames.Upgrades.Chestplate.FRONTRIGHT, false);
                } else {
                    tMaterial.registerNewActivePart(References.InternalNames.Upgrades.Chestplate.FRONTLEFT, !MinecraftForge.EVENT_BUS.post(new ActivateArmorAddonEvent(tMaterial, tFrontChestProtectionLeft)));
                    tMaterial.registerNewActivePart(References.InternalNames.Upgrades.Chestplate.FRONTRIGHT, !MinecraftForge.EVENT_BUS.post(new ActivateArmorAddonEvent(tMaterial, tFrontChestProtectionRight)));
                }
            }
        }

        private static void registerBackProtection() {
            for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values()) {
                ArmorUpgradeMedieval tBackChestProtectionLeft = new ArmorUpgradeMedieval(References.InternalNames.Upgrades.Chestplate.BACKLEFT, References.InternalNames.Armor.MEDIEVALCHESTPLATE, References.InternalNames.AddonPositions.Chestplate.BACKLEFT, tMaterial.getInternalMaterialName(), TranslationKeys.Items.MultiArmor.Upgrades.Chestplate.BackLeft, "", 2F, 150, 1);
                ArmorUpgradeMedieval tBackChestProtectionRight = new ArmorUpgradeMedieval(References.InternalNames.Upgrades.Chestplate.BACKRIGHT, References.InternalNames.Armor.MEDIEVALCHESTPLATE, References.InternalNames.AddonPositions.Chestplate.BACKRIGHT, tMaterial.getInternalMaterialName(), TranslationKeys.Items.MultiArmor.Upgrades.Chestplate.BackRight, "", 2F, 150, 1);
                MedievalAddonRegistry.getInstance().registerUpgrade(tBackChestProtectionLeft);
                MedievalAddonRegistry.getInstance().registerUpgrade(tBackChestProtectionRight);

                if (tMaterial.getInternalMaterialName().equals(References.InternalNames.Materials.Vanilla.OBSIDIAN)) {
                    tMaterial.registerNewActivePart(References.InternalNames.Upgrades.Chestplate.BACKLEFT, false);
                    tMaterial.registerNewActivePart(References.InternalNames.Upgrades.Chestplate.BACKRIGHT, false);
                } else {
                    tMaterial.registerNewActivePart(References.InternalNames.Upgrades.Chestplate.BACKLEFT, !MinecraftForge.EVENT_BUS.post(new ActivateArmorAddonEvent(tMaterial, tBackChestProtectionLeft)));
                    tMaterial.registerNewActivePart(References.InternalNames.Upgrades.Chestplate.BACKRIGHT, !MinecraftForge.EVENT_BUS.post(new ActivateArmorAddonEvent(tMaterial, tBackChestProtectionRight)));
                }
            }
        }

        private static void registerFrontLegProtection() {
            for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values()) {
                ArmorUpgradeMedieval tFrontLeggingsProtectionLeft = new ArmorUpgradeMedieval(References.InternalNames.Upgrades.Leggings.FRONTLEFT, References.InternalNames.Armor.MEDIEVALLEGGINGS, References.InternalNames.AddonPositions.Leggings.FRONTLEFT, tMaterial.getInternalMaterialName(), TranslationKeys.Items.MultiArmor.Upgrades.Leggings.FrontLeft, "", 1.5F, 125, 1);
                ArmorUpgradeMedieval tFrontLeggingsProtectionRight = new ArmorUpgradeMedieval(References.InternalNames.Upgrades.Leggings.FRONTRIGHT, References.InternalNames.Armor.MEDIEVALLEGGINGS, References.InternalNames.AddonPositions.Leggings.FRONTRIGHT, tMaterial.getInternalMaterialName(), TranslationKeys.Items.MultiArmor.Upgrades.Leggings.FrontRight, "", 1.5F, 125, 1);
                MedievalAddonRegistry.getInstance().registerUpgrade(tFrontLeggingsProtectionLeft);
                MedievalAddonRegistry.getInstance().registerUpgrade(tFrontLeggingsProtectionRight);

                if (tMaterial.getInternalMaterialName().equals(References.InternalNames.Materials.Vanilla.OBSIDIAN)) {
                    tMaterial.registerNewActivePart(References.InternalNames.Upgrades.Leggings.FRONTLEFT, false);
                    tMaterial.registerNewActivePart(References.InternalNames.Upgrades.Leggings.FRONTRIGHT, false);
                } else {
                    tMaterial.registerNewActivePart(References.InternalNames.Upgrades.Leggings.FRONTLEFT, !MinecraftForge.EVENT_BUS.post(new ActivateArmorAddonEvent(tMaterial, tFrontLeggingsProtectionLeft)));
                    tMaterial.registerNewActivePart(References.InternalNames.Upgrades.Leggings.FRONTRIGHT, !MinecraftForge.EVENT_BUS.post(new ActivateArmorAddonEvent(tMaterial, tFrontLeggingsProtectionRight)));
                }
            }
        }

        private static void registerBackLegProtection() {
            for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values()) {
                ArmorUpgradeMedieval tBackLeggingsProtectionLeft = new ArmorUpgradeMedieval(References.InternalNames.Upgrades.Leggings.BACKLEFT, References.InternalNames.Armor.MEDIEVALLEGGINGS, References.InternalNames.AddonPositions.Leggings.BACKLEFT, tMaterial.getInternalMaterialName(), TranslationKeys.Items.MultiArmor.Upgrades.Leggings.BackLeft, "", 2F, 150, 1);
                ArmorUpgradeMedieval tBackLeggingsProtectionRight = new ArmorUpgradeMedieval(References.InternalNames.Upgrades.Leggings.BACKRIGHT, References.InternalNames.Armor.MEDIEVALLEGGINGS, References.InternalNames.AddonPositions.Leggings.BACKRIGHT, tMaterial.getInternalMaterialName(), TranslationKeys.Items.MultiArmor.Upgrades.Leggings.BackRight, "", 2F, 150, 1);
                MedievalAddonRegistry.getInstance().registerUpgrade(tBackLeggingsProtectionLeft);
                MedievalAddonRegistry.getInstance().registerUpgrade(tBackLeggingsProtectionRight);

                if (tMaterial.getInternalMaterialName().equals(References.InternalNames.Materials.Vanilla.OBSIDIAN)) {
                    tMaterial.registerNewActivePart(References.InternalNames.Upgrades.Leggings.BACKLEFT, false);
                    tMaterial.registerNewActivePart(References.InternalNames.Upgrades.Leggings.BACKRIGHT, false);
                } else {
                    tMaterial.registerNewActivePart(References.InternalNames.Upgrades.Leggings.BACKLEFT, !MinecraftForge.EVENT_BUS.post(new ActivateArmorAddonEvent(tMaterial, tBackLeggingsProtectionLeft)));
                    tMaterial.registerNewActivePart(References.InternalNames.Upgrades.Leggings.BACKRIGHT, !MinecraftForge.EVENT_BUS.post(new ActivateArmorAddonEvent(tMaterial, tBackLeggingsProtectionRight)));
                }
            }
        }

        private static void registerShoeProtection() {
            for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values()) {
                ArmorUpgradeMedieval tShoeProtectionLeft = new ArmorUpgradeMedieval(References.InternalNames.Upgrades.Shoes.LEFT, References.InternalNames.Armor.MEDIEVALSHOES, References.InternalNames.AddonPositions.Shoes.LEFT, tMaterial.getInternalMaterialName(), TranslationKeys.Items.MultiArmor.Upgrades.Shoes.Left, "", 1F, 50, 1);
                ArmorUpgradeMedieval tShoeProtectionRight = new ArmorUpgradeMedieval(References.InternalNames.Upgrades.Shoes.RIGHT, References.InternalNames.Armor.MEDIEVALSHOES, References.InternalNames.AddonPositions.Shoes.RIGHT, tMaterial.getInternalMaterialName(), TranslationKeys.Items.MultiArmor.Upgrades.Shoes.Right, "", 1F, 50, 1);
                MedievalAddonRegistry.getInstance().registerUpgrade(tShoeProtectionLeft);
                MedievalAddonRegistry.getInstance().registerUpgrade(tShoeProtectionRight);

                tMaterial.registerNewActivePart(References.InternalNames.Upgrades.Shoes.LEFT, !MinecraftForge.EVENT_BUS.post(new ActivateArmorAddonEvent(tMaterial, tShoeProtectionLeft)));
                tMaterial.registerNewActivePart(References.InternalNames.Upgrades.Shoes.RIGHT, !MinecraftForge.EVENT_BUS.post(new ActivateArmorAddonEvent(tMaterial, tShoeProtectionRight)));
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
                if (tMaterial.getInternalMaterialName().equals(References.InternalNames.Materials.Vanilla.IRON)) {
                    tMaterial.setBaseDamageAbsorption(References.InternalNames.Armor.MEDIEVALHELMET, 1.5F);
                    tMaterial.setBaseDurability(References.InternalNames.Armor.MEDIEVALHELMET, 50);
                    tMaterial.setMaxModifiersOnPart(References.InternalNames.Armor.MEDIEVALHELMET, 1);
                } else if (tMaterial.getInternalMaterialName().equals(References.InternalNames.Materials.Vanilla.OBSIDIAN)) {
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
                if (tMaterial.getInternalMaterialName().equals(References.InternalNames.Materials.Vanilla.IRON)) {
                    tMaterial.setBaseDamageAbsorption(References.InternalNames.Armor.MEDIEVALCHESTPLATE, 2.0F);
                    tMaterial.setBaseDurability(References.InternalNames.Armor.MEDIEVALCHESTPLATE, 50);
                    tMaterial.setMaxModifiersOnPart(References.InternalNames.Armor.MEDIEVALCHESTPLATE, 1);
                } else if (tMaterial.getInternalMaterialName().equals(References.InternalNames.Materials.Vanilla.OBSIDIAN)) {
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
                if (tMaterial.getInternalMaterialName().equals(References.InternalNames.Materials.Vanilla.IRON)) {
                    tMaterial.setBaseDamageAbsorption(References.InternalNames.Armor.MEDIEVALLEGGINGS, 1.5F);
                    tMaterial.setBaseDurability(References.InternalNames.Armor.MEDIEVALLEGGINGS, 50);
                    tMaterial.setMaxModifiersOnPart(References.InternalNames.Armor.MEDIEVALLEGGINGS, 1);
                } else if (tMaterial.getInternalMaterialName().equals(References.InternalNames.Materials.Vanilla.OBSIDIAN)) {
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
                if (tMaterial.getInternalMaterialName().equals(References.InternalNames.Materials.Vanilla.IRON)) {
                    tMaterial.setBaseDamageAbsorption(References.InternalNames.Armor.MEDIEVALSHOES, 1.0F);
                    tMaterial.setBaseDurability(References.InternalNames.Armor.MEDIEVALSHOES, 50);
                    tMaterial.setMaxModifiersOnPart(References.InternalNames.Armor.MEDIEVALSHOES, 1);
                } else if (tMaterial.getInternalMaterialName().equals(References.InternalNames.Materials.Vanilla.OBSIDIAN)) {
                    tMaterial.setBaseDamageAbsorption(References.InternalNames.Armor.MEDIEVALSHOES, 2.5F);
                    tMaterial.setBaseDurability(References.InternalNames.Armor.MEDIEVALSHOES, 200);
                    tMaterial.setMaxModifiersOnPart(References.InternalNames.Armor.MEDIEVALSHOES, 2);
                } else {
                    MinecraftForge.EVENT_BUS.post(new ModifyMaterialEvent(tMaterial, MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALSHOES)));
                }
            }
        }

        public static void prepareGame() {
            prepareKnowledgeSystem();

            initializeAnvilRecipes();

            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GeneralRegistry.Items.iHammer, 1, 150), "  #", " / ", "/  ", '#', new ItemStack(Blocks.iron_block, 1), '/', new ItemStack(Items.stick, 1)));

            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GeneralRegistry.Blocks.iBlockFirePit, 1), "#=#", "#/#", "###", '#', new ItemStack(Items.iron_ingot, 1), '=', new ItemStack(Items.cauldron, 1), '/', new ItemStack(Blocks.furnace, 1)));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GeneralRegistry.Blocks.iBlockAnvil, 1), "BBB", " I ", "IBI", 'B', new ItemStack(Blocks.iron_block, 1), 'I', new ItemStack(Items.iron_ingot, 1)));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GeneralRegistry.Blocks.iBookBinder, 1), "APA", "BGB", "AOA", 'A', new ItemStack(Blocks.log2, 1, 0), 'P', new ItemStack(Blocks.planks, 1, 4), 'B', new ItemStack(Blocks.stonebrick, 1, 0), 'G', new ItemStack(Items.gold_ingot, 1), 'O', new ItemStack(Blocks.planks, 1, 0)));
            GameRegistry.addRecipe(new ShapedOreRecipe(new ItemStack(GeneralRegistry.Items.iSmithingsGuide, 1), "LL ", "L  ", "LL ", 'L', new ItemStack(Items.leather, 1)));
        }

        public static void prepareKnowledgeSystem() {
            registerKnowledge();
            registerBlueprints();
            registerResearch();

            MinecraftForge.EVENT_BUS.post(new RegisterKnowledgeEvent());
        }

        private static void registerResearch() {
            for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values()) {
                createRingResearch(tMaterial);
                createChainResearch(tMaterial);
                createPlateResearch(tMaterial);
                createNuggetResearch(tMaterial);
                createArmorResearch(tMaterial);
            }

            ItemStack tGuideStack = new ItemStack(GeneralRegistry.Items.iSmithingsGuide);
            GeneralRegistry.Items.iSmithingsGuide.initializeContainer(tGuideStack);

            IResearchTreeComponent tRootComponent = new TargetItemStackSwitchResearchTreeComponent(tGuideStack);
            IResearchTreeComponent tCurrentComponent = tRootComponent;

            tCurrentComponent.registerNewFollowupTreeComponent(new DebugResearchTreeComponent(References.InternalNames.InputHandlers.BookBinder.ANALYZE));

            KnowledgeRegistry.getInstance().registerNewRootElement(tRootComponent);

            GeneralRegistry.iLogger.info("Generated research");
        }

        private static void createRingResearch(IArmorMaterial pMaterial) {
            ItemStack tNuggetStack = new ItemStack(GeneralRegistry.Items.iNugget, 1, pMaterial.getMaterialID());
            NBTTagCompound pNuggetCompound = new NBTTagCompound();
            pNuggetCompound.setString(References.NBTTagCompoundData.Material, pMaterial.getInternalMaterialName());
            tNuggetStack.setTagCompound(pNuggetCompound);


            IResearchTreeComponent tRootComponent = new TargetItemStackSwitchResearchTreeComponent(tNuggetStack);
            IResearchTreeComponent tCurrentComponent = tRootComponent;

            for (int tCurrentTemp = 0; tCurrentTemp < pMaterial.getMeltingPoint(); tCurrentTemp += 75) {
                tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new ApplyHeatToTargetStackResearchComponent(tNuggetStack));
            }

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tNuggetStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tNuggetStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tNuggetStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tNuggetStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new AnalyzeTargetStackResearchComponent(tNuggetStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new BlueprintResultComponent(tNuggetStack, References.InternalNames.Recipes.Anvil.CHAIN + pMaterial.getOreDicName()));

            KnowledgeRegistry.getInstance().registerNewResearchBranch(tRootComponent);
        }

        private static void createChainResearch(IArmorMaterial pMaterial) {
            ItemStack tRingStack = new ItemStack(GeneralRegistry.Items.iMetalRing, 1, pMaterial.getMaterialID());

            NBTTagCompound tStackCompound = new NBTTagCompound();
            tStackCompound.setString(References.NBTTagCompoundData.Material, pMaterial.getInternalMaterialName());
            tRingStack.setTagCompound(tStackCompound);

            IResearchTreeComponent tRootComponent = new TargetItemStackSwitchResearchTreeComponent(tRingStack);
            IResearchTreeComponent tCurrentComponent = tRootComponent;

            for (int tCurrentTemp = 0; tCurrentTemp < (pMaterial.getMeltingPoint() / 2); tCurrentTemp += 75) {
                tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new ApplyHeatToTargetStackResearchComponent(tRingStack));
            }

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tRingStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tRingStack));


            for (int tCurrentTemp = 0; tCurrentTemp < (pMaterial.getMeltingPoint() / 2); tCurrentTemp += 75) {
                tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new ApplyHeatToTargetStackResearchComponent(tRingStack));
            }

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tRingStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tRingStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new AnalyzeTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new BlueprintResultComponent(tRingStack, References.InternalNames.Recipes.Anvil.CHAIN + pMaterial.getOreDicName()));

            KnowledgeRegistry.getInstance().registerNewResearchBranch(tRootComponent);
        }

        private static void createPlateResearch(IArmorMaterial pMaterial) {
            ItemStack tBaseStack = pMaterial.getRootItemStack();

            IResearchTreeComponent tRootComponent = new TargetItemStackSwitchResearchTreeComponent(tBaseStack);
            IResearchTreeComponent tCurrentComponent = tRootComponent;

            for (int tCurrentTemp = 0; tCurrentTemp < pMaterial.getMeltingPoint(); tCurrentTemp += 75) {
                tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new ApplyHeatToTargetStackResearchComponent(tBaseStack));
            }

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tBaseStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tBaseStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tBaseStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tBaseStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tBaseStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tBaseStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tBaseStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tBaseStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tBaseStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tBaseStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tBaseStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tBaseStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tBaseStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tBaseStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tBaseStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tBaseStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tBaseStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new AnalyzeTargetStackResearchComponent(tBaseStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new BlueprintResultComponent(tBaseStack, References.InternalNames.Recipes.Anvil.PLATE + pMaterial.getOreDicName()));

            KnowledgeRegistry.getInstance().registerNewResearchBranch(tRootComponent);
        }

        private static void createNuggetResearch(IArmorMaterial pMaterial) {
            ItemStack tBaseStack = pMaterial.getRootItemStack();

            IResearchTreeComponent tRootComponent = new TargetItemStackSwitchResearchTreeComponent(tBaseStack);
            IResearchTreeComponent tCurrentComponent = tRootComponent;

            for (int tCurrentTemp = 0; tCurrentTemp < (pMaterial.getMeltingPoint() / 2); tCurrentTemp += 75) {
                tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new ApplyHeatToTargetStackResearchComponent(tBaseStack));
            }

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tBaseStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tBaseStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tBaseStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tBaseStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new AnalyzeTargetStackResearchComponent(tBaseStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new BlueprintResultComponent(tBaseStack, References.InternalNames.Recipes.Anvil.NUGGET + pMaterial.getOreDicName()));

            KnowledgeRegistry.getInstance().registerNewResearchBranch(tRootComponent);
        }

        private static void createArmorResearch(IArmorMaterial pMaterial) {
            createHelmetResearch(pMaterial);
            createChestplateResearch(pMaterial);
            createLeggingsResearch(pMaterial);
            createShoeResearch(pMaterial);
        }

        private static void createHelmetResearch(IArmorMaterial pMaterial) {
            ItemStack tRingStack = new ItemStack(GeneralRegistry.Items.iMetalRing, 1, pMaterial.getMaterialID());

            NBTTagCompound tRingStackCompound = new NBTTagCompound();
            tRingStackCompound.setString(References.NBTTagCompoundData.Material, pMaterial.getInternalMaterialName());
            tRingStack.setTagCompound(tRingStackCompound);

            ItemStack tChainStack = new ItemStack(GeneralRegistry.Items.iMetalChain, 1, pMaterial.getMaterialID());

            NBTTagCompound tChainStackCompound = new NBTTagCompound();
            tChainStackCompound.setString(References.NBTTagCompoundData.Material, pMaterial.getInternalMaterialName());
            tChainStack.setTagCompound(tChainStackCompound);


            IResearchTreeComponent tRootComponent = new TargetItemStackSwitchResearchTreeComponent(tChainStack);
            IResearchTreeComponent tCurrentComponent = tRootComponent;

            for (int tCurrentTemp = 0; tCurrentTemp < ((pMaterial.getMeltingPoint() * 0.35F * 0.9F) / 2); tCurrentTemp += 75)
                ;
            {
                tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new ApplyHeatToTargetStackResearchComponent(tChainStack));
            }

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tChainStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tChainStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tChainStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tChainStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tChainStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tChainStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tChainStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tChainStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tChainStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tChainStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tChainStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new TargetItemStackSwitchResearchTreeComponent(tRingStack));

            for (int tCurrentTemp = 0; tCurrentTemp < ((pMaterial.getMeltingPoint() * 0.35F * 0.9F)); tCurrentTemp += 75)
                ;
            {
                tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new ApplyHeatToTargetStackResearchComponent(tRingStack));
            }

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tRingStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tRingStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tRingStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tRingStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new AnalyzeTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new BlueprintResultComponent(tRingStack, References.InternalNames.Recipes.Anvil.HELMET + pMaterial.getOreDicName()));

            KnowledgeRegistry.getInstance().registerNewResearchBranch(tRootComponent);
        }

        private static void createChestplateResearch(IArmorMaterial pMaterial) {
            ItemStack tRingStack = new ItemStack(GeneralRegistry.Items.iMetalRing, 1, pMaterial.getMaterialID());

            NBTTagCompound tRingStackCompound = new NBTTagCompound();
            tRingStackCompound.setString(References.NBTTagCompoundData.Material, pMaterial.getInternalMaterialName());
            tRingStack.setTagCompound(tRingStackCompound);

            ItemStack tChainStack = new ItemStack(GeneralRegistry.Items.iMetalChain, 1, pMaterial.getMaterialID());

            NBTTagCompound tChainStackCompound = new NBTTagCompound();
            tChainStackCompound.setString(References.NBTTagCompoundData.Material, pMaterial.getInternalMaterialName());
            tChainStack.setTagCompound(tChainStackCompound);

            IResearchTreeComponent tRootComponent = new TargetItemStackSwitchResearchTreeComponent(tChainStack);
            IResearchTreeComponent tCurrentComponent = tRootComponent;

            for (int tCurrentTemp = 0; tCurrentTemp < ((pMaterial.getMeltingPoint() * 0.35F * 0.9F) / 3); tCurrentTemp += 75)
                ;
            {
                tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new ApplyHeatToTargetStackResearchComponent(tChainStack));
            }

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tChainStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tChainStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tChainStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tChainStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tChainStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tChainStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tChainStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tChainStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tChainStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tChainStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new TargetItemStackSwitchResearchTreeComponent(tRingStack));

            for (int tCurrentTemp = 0; tCurrentTemp < ((pMaterial.getMeltingPoint() * 0.35F * 0.9F) / 2); tCurrentTemp += 75)
                ;
            {
                tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new ApplyHeatToTargetStackResearchComponent(tRingStack));
            }

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tRingStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tRingStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new TargetItemStackSwitchResearchTreeComponent(tChainStack));

            for (int tCurrentTemp = 0; tCurrentTemp < ((pMaterial.getMeltingPoint() * 0.35F * 0.9F) / 3); tCurrentTemp += 75)
                ;
            {
                tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new ApplyHeatToTargetStackResearchComponent(tChainStack));
            }

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tChainStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tChainStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tChainStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tChainStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tChainStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tChainStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tChainStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tChainStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tChainStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tChainStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new TargetItemStackSwitchResearchTreeComponent(tRingStack));

            for (int tCurrentTemp = 0; tCurrentTemp < ((pMaterial.getMeltingPoint() * 0.35F * 0.9F) / 2); tCurrentTemp += 75)
                ;
            {
                tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new ApplyHeatToTargetStackResearchComponent(tRingStack));
            }

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tRingStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tRingStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new TargetItemStackSwitchResearchTreeComponent(tChainStack));

            for (int tCurrentTemp = 0; tCurrentTemp < ((pMaterial.getMeltingPoint() * 0.35F * 0.9F) / 3); tCurrentTemp += 75)
                ;
            {
                tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new ApplyHeatToTargetStackResearchComponent(tChainStack));
            }

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tChainStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tChainStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tChainStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tChainStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tChainStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tChainStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tChainStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tChainStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tChainStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tChainStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new TargetItemStackSwitchResearchTreeComponent(tRingStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tRingStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tRingStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tRingStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tRingStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new AnalyzeTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new BlueprintResultComponent(tRingStack, References.InternalNames.Recipes.Anvil.CHESTPLATE + pMaterial.getOreDicName()));

            KnowledgeRegistry.getInstance().registerNewResearchBranch(tRootComponent);
        }

        private static void createLeggingsResearch(IArmorMaterial pMaterial) {
            ItemStack tRingStack = new ItemStack(GeneralRegistry.Items.iMetalRing, 1, pMaterial.getMaterialID());

            NBTTagCompound tRingStackCompound = new NBTTagCompound();
            tRingStackCompound.setString(References.NBTTagCompoundData.Material, pMaterial.getInternalMaterialName());
            tRingStack.setTagCompound(tRingStackCompound);

            ItemStack tChainStack = new ItemStack(GeneralRegistry.Items.iMetalChain, 1, pMaterial.getMaterialID());

            NBTTagCompound tChainStackCompound = new NBTTagCompound();
            tChainStackCompound.setString(References.NBTTagCompoundData.Material, pMaterial.getInternalMaterialName());
            tChainStack.setTagCompound(tChainStackCompound);


            IResearchTreeComponent tRootComponent = new TargetItemStackSwitchResearchTreeComponent(tRingStack);
            IResearchTreeComponent tCurrentComponent = tRootComponent;

            for (int tCurrentTemp = 0; tCurrentTemp < ((pMaterial.getMeltingPoint() * 0.35F * 0.9F) / 2); tCurrentTemp += 75)
                ;
            {
                tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new ApplyHeatToTargetStackResearchComponent(tRingStack));
            }

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tRingStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tRingStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new TargetItemStackSwitchResearchTreeComponent(tChainStack));

            for (int tCurrentTemp = 0; tCurrentTemp < ((pMaterial.getMeltingPoint() * 0.35F * 0.9F)); tCurrentTemp += 75)
                ;
            {
                tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new ApplyHeatToTargetStackResearchComponent(tChainStack));
            }

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tChainStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tChainStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tChainStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tChainStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tChainStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tChainStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tChainStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tChainStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tChainStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tChainStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tChainStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tChainStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tChainStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tChainStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tChainStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tChainStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tChainStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tChainStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tChainStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tChainStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tChainStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tChainStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new AnalyzeTargetStackResearchComponent(tChainStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new BlueprintResultComponent(tRingStack, References.InternalNames.Recipes.Anvil.LEGGINGS + pMaterial.getOreDicName()));

            KnowledgeRegistry.getInstance().registerNewResearchBranch(tRootComponent);
        }

        private static void createShoeResearch(IArmorMaterial pMaterial) {
            ItemStack tRingStack = new ItemStack(GeneralRegistry.Items.iMetalRing, 1, pMaterial.getMaterialID());

            NBTTagCompound tRingStackCompound = new NBTTagCompound();
            tRingStackCompound.setString(References.NBTTagCompoundData.Material, pMaterial.getInternalMaterialName());
            tRingStack.setTagCompound(tRingStackCompound);

            ItemStack tChainStack = new ItemStack(GeneralRegistry.Items.iMetalChain, 1, pMaterial.getMaterialID());

            NBTTagCompound tChainStackCompound = new NBTTagCompound();
            tChainStackCompound.setString(References.NBTTagCompoundData.Material, pMaterial.getInternalMaterialName());
            tChainStack.setTagCompound(tChainStackCompound);


            IResearchTreeComponent tRootComponent = new TargetItemStackSwitchResearchTreeComponent(tChainStack);
            IResearchTreeComponent tCurrentComponent = tRootComponent;

            for (int tCurrentTemp = 0; tCurrentTemp < ((pMaterial.getMeltingPoint() * 0.35F * 0.9F) / 2); tCurrentTemp += 75)
                ;
            {
                tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new ApplyHeatToTargetStackResearchComponent(tChainStack));
            }

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tChainStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tChainStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tChainStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tChainStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tChainStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tChainStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tChainStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tChainStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tChainStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new TargetItemStackSwitchResearchTreeComponent(tRingStack));

            for (int tCurrentTemp = 0; tCurrentTemp < ((pMaterial.getMeltingPoint() * 0.35F * 0.9F)); tCurrentTemp += 75)
                ;
            {
                tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new ApplyHeatToTargetStackResearchComponent(tRingStack));
            }

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tRingStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tChainStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tRingStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new TargetItemStackSwitchResearchTreeComponent(tChainStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tChainStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tChainStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tChainStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tChainStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tChainStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tChainStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tChainStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new HammerTargetStackResearchComponent(tChainStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new CutTargetStackResearchComponent(tChainStack));

            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new AnalyzeTargetStackResearchComponent(tRingStack));
            tCurrentComponent = tCurrentComponent.registerNewFollowupTreeComponent(new BlueprintResultComponent(tRingStack, References.InternalNames.Recipes.Anvil.SHOES + pMaterial.getOreDicName()));

            KnowledgeRegistry.getInstance().registerNewResearchBranch(tRootComponent);
        }


        public static void registerBlueprints() {
            BlueprintRegistry.getInstance().registerNewBluePrint(new EasyBlueprint(References.InternalNames.Recipes.Anvil.HAMMER, References.InternalNames.Recipes.Anvil.HAMMER));
            BlueprintRegistry.getInstance().registerNewBluePrint(new EasyBlueprint(References.InternalNames.Recipes.Anvil.TONGS, References.InternalNames.Recipes.Anvil.TONGS));
            BlueprintRegistry.getInstance().registerNewBluePrint(new HardBlueprint(References.InternalNames.Recipes.Anvil.HEATER, References.InternalNames.Recipes.Anvil.HEATER));
            BlueprintRegistry.getInstance().registerNewBluePrint(new BasicBlueprint(References.InternalNames.Recipes.Anvil.FAN, References.InternalNames.Recipes.Anvil.FAN));

            for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values()) {
                BlueprintRegistry.getInstance().registerNewBluePrint(new MaterialBlueprint(tMaterial));
                BlueprintRegistry.getInstance().registerNewBluePrint(new MedievalUpgradeBlueprint(tMaterial));

                BlueprintRegistry.getInstance().registerNewBluePrint(new EasyBlueprint(References.InternalNames.Recipes.Anvil.RING + tMaterial.getOreDicName(), References.InternalNames.Recipes.Anvil.RING + tMaterial.getOreDicName()));
                BlueprintRegistry.getInstance().registerNewBluePrint(new EasyBlueprint(References.InternalNames.Recipes.Anvil.CHAIN + tMaterial.getOreDicName(), References.InternalNames.Recipes.Anvil.CHAIN + tMaterial.getOreDicName()));
                BlueprintRegistry.getInstance().registerNewBluePrint(new BasicBlueprint(References.InternalNames.Recipes.Anvil.NUGGET + tMaterial.getOreDicName(), References.InternalNames.Recipes.Anvil.NUGGET + tMaterial.getOreDicName()));
                BlueprintRegistry.getInstance().registerNewBluePrint(new HardBlueprint(References.InternalNames.Recipes.Anvil.PLATE + tMaterial.getOreDicName(), References.InternalNames.Recipes.Anvil.PLATE + tMaterial.getOreDicName()));

                if (!tMaterial.getIsBaseArmorMaterial())
                    continue;

                BlueprintRegistry.getInstance().registerNewBluePrint(new BasicBlueprint(References.InternalNames.Recipes.Anvil.HELMET + tMaterial.getOreDicName(), References.InternalNames.Recipes.Anvil.HELMET + tMaterial.getOreDicName()));
                BlueprintRegistry.getInstance().registerNewBluePrint(new HardBlueprint(References.InternalNames.Recipes.Anvil.CHESTPLATE + tMaterial.getOreDicName(), References.InternalNames.Recipes.Anvil.CHESTPLATE + tMaterial.getOreDicName()));
                BlueprintRegistry.getInstance().registerNewBluePrint(new BasicBlueprint(References.InternalNames.Recipes.Anvil.LEGGINGS + tMaterial.getOreDicName(), References.InternalNames.Recipes.Anvil.LEGGINGS + tMaterial.getOreDicName()));
                BlueprintRegistry.getInstance().registerNewBluePrint(new EasyBlueprint(References.InternalNames.Recipes.Anvil.SHOES + tMaterial.getOreDicName(), References.InternalNames.Recipes.Anvil.SHOES + tMaterial.getOreDicName()));
            }
        }

        public static void registerKnowledge() {

        }

        public static void initializeAnvilRecipes() {
            ItemStack tHammerStack = new ItemStack(GeneralRegistry.Items.iHammer, 1);
            tHammerStack.setItemDamage(150);
            AnvilRecipe tHammerRecipe = new AnvilRecipe().setCraftingSlotContent(3, (new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.INGOT, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F)))
                    .setCraftingSlotContent(7, (new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.INGOT, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F)))
                    .setCraftingSlotContent(9, (new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.INGOT, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F)))
                    .setCraftingSlotContent(13, (new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.INGOT, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F)))
                    .setCraftingSlotContent(14, (new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.INGOT, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F)))
                    .setCraftingSlotContent(8, (new OreDicAnvilRecipeComponent("stickWood", 1)))
                    .setCraftingSlotContent(12, (new OreDicAnvilRecipeComponent("stickWood", 1)))
                    .setCraftingSlotContent(16, (new OreDicAnvilRecipeComponent("stickWood", 1)))
                    .setCraftingSlotContent(20, (new OreDicAnvilRecipeComponent("stickWood", 1)))
                    .setProgress(4).setResult(tHammerStack).setHammerUsage(4).setTongUsage(0);
            AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.HAMMER, tHammerRecipe);

            ItemStack tTongStack = new ItemStack(GeneralRegistry.Items.iTongs, 1);
            tTongStack.setItemDamage(150);
            AnvilRecipe tTongRecipe = new AnvilRecipe().setCraftingSlotContent(3, (new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F)))
                    .setCraftingSlotContent(7, (new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F)))
                    .setCraftingSlotContent(9, (new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F)))
                    .setCraftingSlotContent(13, (new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F)))
                    .setCraftingSlotContent(12, (new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.RING, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F)))
                    .setCraftingSlotContent(11, (new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.INGOT, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F)))
                    .setCraftingSlotContent(15, (new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.INGOT, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F)))
                    .setCraftingSlotContent(17, (new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.INGOT, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F)))
                    .setCraftingSlotContent(21, (new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.INGOT, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F)))
                    .setProgress(4).setResult(tTongStack).setHammerUsage(4).setTongUsage(0);
            AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.TONGS, tTongRecipe);

            ItemStack tHeaterStack = new ItemStack(GeneralRegistry.Blocks.iBlockHeater, 1);
            AnvilRecipe tHeaterRecipe = new AnvilRecipe().setCraftingSlotContent(6, (new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.PLATE, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F)))
                    .setCraftingSlotContent(7, (new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.PLATE, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F)))
                    .setCraftingSlotContent(8, (new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.PLATE, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F)))
                    .setCraftingSlotContent(11, (new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.PLATE, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F)))
                    .setCraftingSlotContent(13, (new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.PLATE, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F)))
                    .setCraftingSlotContent(16, (new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.PLATE, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F)))
                    .setCraftingSlotContent(17, (new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.PLATE, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F)))
                    .setCraftingSlotContent(18, (new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.PLATE, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F)))
                    .setProgress(20).setResult(tHeaterStack).setHammerUsage(10).setTongUsage(15);
            AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.HEATER, tHeaterRecipe);

            ItemStack tFanStack = new ItemStack(GeneralRegistry.Items.iFan, 1, Short.MAX_VALUE);
            AnvilRecipe tFanRecipe = new AnvilRecipe().setCraftingSlotContent(0, (new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F)))
                    .setCraftingSlotContent(6, (new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F)))
                    .setCraftingSlotContent(8, (new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F)))
                    .setCraftingSlotContent(4, (new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F)))
                    .setCraftingSlotContent(18, (new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F)))
                    .setCraftingSlotContent(24, (new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F)))
                    .setCraftingSlotContent(16, (new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F)))
                    .setCraftingSlotContent(20, (new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F)))
                    .setCraftingSlotContent(7, (new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.INGOT, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F)))
                    .setCraftingSlotContent(13, (new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.INGOT, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F)))
                    .setCraftingSlotContent(17, (new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.INGOT, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F)))
                    .setCraftingSlotContent(11, (new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.INGOT, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F)))
                    .setCraftingSlotContent(12, (new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.PLATE, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F)))
                    .setProgress(12).setResult(tFanStack).setHammerUsage(10).setTongUsage(20);
            AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.FAN, tFanRecipe);

            initializeMedievalArmorAnvilRecipes();
            initializeMedievalUpgradeAnvilRecipes();
            initializeUpgradeRecipeSystem();
        }

        public static void initializeMedievalArmorAnvilRecipes() {
            for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values()) {
                ItemStack tRingStack = new ItemStack(GeneralRegistry.Items.iMetalRing, 1, tMaterial.getMaterialID());
                NBTTagCompound pRingCompound = new NBTTagCompound();
                pRingCompound.setString(References.NBTTagCompoundData.Material, tMaterial.getInternalMaterialName());
                tRingStack.setTagCompound(pRingCompound);

                AnvilRecipe tRingRecipe = new AnvilRecipe().setCraftingSlotContent(0, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.95F)))
                        .setProgress(9).setResult(tRingStack).setHammerUsage(4).setTongUsage(0).setShapeLess();

                AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.RING + tMaterial.getOreDicName(), tRingRecipe);

                ItemStack tPlateStack = new ItemStack(GeneralRegistry.Items.iPlate, 1, tMaterial.getMaterialID());
                NBTTagCompound pPlateCompound = new NBTTagCompound();
                pPlateCompound.setString(References.NBTTagCompoundData.Material, tMaterial.getInternalMaterialName());
                tPlateStack.setTagCompound(pPlateCompound);

                AnvilRecipe tPlateRecipe = new AnvilRecipe().setCraftingSlotContent(0, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.INGOT, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.95F)))
                        .setProgress(15).setResult(tPlateStack).setHammerUsage(15).setTongUsage(2).setShapeLess();

                AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.PLATE + tMaterial.getOreDicName(), tPlateRecipe);

                ItemStack tNuggetStack = new ItemStack(GeneralRegistry.Items.iNugget, 9, tMaterial.getMaterialID());
                NBTTagCompound pNuggetCompound = new NBTTagCompound();
                pNuggetCompound.setString(References.NBTTagCompoundData.Material, tMaterial.getInternalMaterialName());
                tNuggetStack.setTagCompound(pNuggetCompound);

                AnvilRecipe tNuggetRecipe = new AnvilRecipe().setCraftingSlotContent(0, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.INGOT, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F) * 0.95F)))
                        .setProgress(6).setResult(tNuggetStack).setHammerUsage(4).setTongUsage(0).setShapeLess();

                AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.NUGGET + tMaterial.getOreDicName(), tNuggetRecipe);

                ItemStack tChainStack = new ItemStack(GeneralRegistry.Items.iMetalChain, 1, tMaterial.getMaterialID());
                NBTTagCompound tChainCompound = new NBTTagCompound();
                tChainCompound.setString(References.NBTTagCompoundData.Material, tMaterial.getInternalMaterialName());
                tChainStack.setTagCompound(tChainCompound);

                AnvilRecipe tChainRecipe = new AnvilRecipe().setCraftingSlotContent(2, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(6, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(8, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(10, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(12, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(14, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(16, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(18, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(22, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setProgress(10).setResult(tChainStack).setHammerUsage(16).setTongUsage(16);

                AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.CHAIN + tMaterial.getOreDicName(), tChainRecipe);

                if (!tMaterial.getIsBaseArmorMaterial())
                    continue;

                ItemStack tChestplateStack = MedievalArmorFactory.getInstance().buildNewMLAArmor(MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALCHESTPLATE), new HashMap<MLAAddon, Integer>(), tMaterial.getBaseDurability(References.InternalNames.Armor.MEDIEVALCHESTPLATE), tMaterial.getInternalMaterialName());
                AnvilRecipe tChestplateRecipe = new AnvilRecipe().setCraftingSlotContent(0, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.CHAIN, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(4, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.CHAIN, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(5, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.CHAIN, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(6, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(8, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(9, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.CHAIN, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(10, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.CHAIN, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(11, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(12, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.CHAIN, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(13, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(14, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.CHAIN, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(15, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.CHAIN, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(16, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(17, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.CHAIN, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(18, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(19, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.CHAIN, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(20, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.CHAIN, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(21, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(22, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.CHAIN, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(23, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(24, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.CHAIN, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setProgress(20).setResult(tChestplateStack).setHammerUsage(38).setTongUsage(24);
                AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.CHESTPLATE + tMaterial.getOreDicName(), tChestplateRecipe);

                ItemStack tHelmetStack = MedievalArmorFactory.getInstance().buildNewMLAArmor(MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALHELMET), new HashMap<MLAAddon, Integer>(), tMaterial.getBaseDurability(References.InternalNames.Armor.MEDIEVALHELMET), tMaterial.getInternalMaterialName());
                AnvilRecipe tHelmetRecipe = new AnvilRecipe().setCraftingSlotContent(0, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.CHAIN, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(1, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.CHAIN, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(2, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.CHAIN, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(3, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.CHAIN, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(4, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.CHAIN, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(5, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.CHAIN, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(6, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(7, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(8, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(9, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.CHAIN, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(10, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.CHAIN, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(12, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(14, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.CHAIN, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setProgress(20).setResult(tHelmetStack).setHammerUsage(28).setTongUsage(16);
                AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.HELMET + tMaterial.getOreDicName(), tHelmetRecipe);

                ItemStack tPantsStack = MedievalArmorFactory.getInstance().buildNewMLAArmor(MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALLEGGINGS), new HashMap<MLAAddon, Integer>(), tMaterial.getBaseDurability(References.InternalNames.Armor.MEDIEVALLEGGINGS), tMaterial.getInternalMaterialName());
                AnvilRecipe tPantsRecipe = new AnvilRecipe().setCraftingSlotContent(0, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.CHAIN, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(1, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.CHAIN, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(2, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.CHAIN, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(3, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.CHAIN, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(4, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.CHAIN, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))

                        .setCraftingSlotContent(5, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.CHAIN, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(6, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(7, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(8, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(9, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.CHAIN, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))

                        .setCraftingSlotContent(10, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.CHAIN, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(11, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(13, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(14, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.CHAIN, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))

                        .setCraftingSlotContent(15, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.CHAIN, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(16, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(18, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(19, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.CHAIN, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))

                        .setCraftingSlotContent(20, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.CHAIN, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(21, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(23, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(24, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.CHAIN, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setProgress(20).setResult(tPantsStack).setHammerUsage(28).setTongUsage(16);
                AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.LEGGINGS + tMaterial.getOreDicName(), tPantsRecipe);

                ItemStack tShoeStack = MedievalArmorFactory.getInstance().buildNewMLAArmor(MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALSHOES), new HashMap<MLAAddon, Integer>(), tMaterial.getBaseDurability(References.InternalNames.Armor.MEDIEVALSHOES), tMaterial.getInternalMaterialName());
                AnvilRecipe tShoeRecipe = new AnvilRecipe().setCraftingSlotContent(6, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.CHAIN, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(8, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.CHAIN, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(10, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(11, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.CHAIN, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(13, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.CHAIN, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(14, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(15, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.CHAIN, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(16, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.CHAIN, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(18, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.CHAIN, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(19, (new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.CHAIN, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.35F) * 0.95F)))
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
                if (tMaterial.getPartState(References.InternalNames.Upgrades.Helmet.TOP)) {
                    ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval) MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALHELMET).getAddon(References.InternalNames.Upgrades.Helmet.TOP + "-" + tMaterial.getInternalMaterialName());
                    ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.iMedievalUpgrades, 1);
                    NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgrade.iMaterialInternalName);
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALHELMET).getInternalName());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, tUpgrade.getInternalName());

                    tUpgradeStack.setTagCompound(pUpgradeCompound);

                    AnvilRecipe tRecipe = new AnvilRecipe()
                            .setCraftingSlotContent(6, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(7, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(8, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(10, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(14, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setResult(tUpgradeStack).setHammerUsage((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getInternalMaterialName()).getMeltingPoint() / 300).setTongUsage((int) (MaterialRegistry.getInstance().getMaterial(tMaterial.getInternalMaterialName()).getMeltingPoint() - 1000) / 300).setProgress((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getInternalMaterialName()).getMeltingPoint() / 100);

                    AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.HELMETTOP + tMaterial.getOreDicName(), tRecipe);
                }

                if (tMaterial.getPartState(References.InternalNames.Upgrades.Helmet.LEFT)) {
                    ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval) MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALHELMET).getAddon(References.InternalNames.Upgrades.Helmet.LEFT + "-" + tMaterial.getInternalMaterialName());
                    ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.iMedievalUpgrades, 1);
                    NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgrade.iMaterialInternalName);
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALHELMET).getInternalName());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, tUpgrade.getInternalName());

                    tUpgradeStack.setTagCompound(pUpgradeCompound);

                    AnvilRecipe tRecipe = new AnvilRecipe()
                            .setCraftingSlotContent(13, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(18, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(23, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setResult(tUpgradeStack).setHammerUsage((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getInternalMaterialName()).getMeltingPoint() / 300).setTongUsage((int) (MaterialRegistry.getInstance().getMaterial(tMaterial.getInternalMaterialName()).getMeltingPoint() - 1000) / 300).setProgress((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getInternalMaterialName()).getMeltingPoint() / 100);

                    AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.HELMETLEFT + tMaterial.getOreDicName(), tRecipe);
                }

                if (tMaterial.getPartState(References.InternalNames.Upgrades.Helmet.RIGHT)) {
                    ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval) MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALHELMET).getAddon(References.InternalNames.Upgrades.Helmet.RIGHT + "-" + tMaterial.getInternalMaterialName());
                    ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.iMedievalUpgrades, 1);
                    NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgrade.iMaterialInternalName);
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALHELMET).getInternalName());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, tUpgrade.getInternalName());

                    tUpgradeStack.setTagCompound(pUpgradeCompound);

                    AnvilRecipe tRecipe = new AnvilRecipe()
                            .setCraftingSlotContent(11, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(16, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(21, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setResult(tUpgradeStack).setHammerUsage((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getInternalMaterialName()).getMeltingPoint() / 300).setTongUsage((int) (MaterialRegistry.getInstance().getMaterial(tMaterial.getInternalMaterialName()).getMeltingPoint() - 1000) / 300).setProgress((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getInternalMaterialName()).getMeltingPoint() / 100);

                    AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.HELMETRIGHT + tMaterial.getOreDicName(), tRecipe);
                }
            }
        }

        public static void initializeMedievalChestPlateUpgradeAnvilRecipes() {
            for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values()) {
                if (tMaterial.getPartState(References.InternalNames.Upgrades.Chestplate.SHOULDERLEFT)) {
                    ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval) MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALCHESTPLATE).getAddon(References.InternalNames.Upgrades.Chestplate.SHOULDERLEFT + "-" + tMaterial.getInternalMaterialName());
                    ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.iMedievalUpgrades, 1);
                    NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgrade.iMaterialInternalName);
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALCHESTPLATE).getInternalName());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, tUpgrade.getInternalName());

                    tUpgradeStack.setTagCompound(pUpgradeCompound);

                    AnvilRecipe tRecipe = new AnvilRecipe()
                            .setCraftingSlotContent(2, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(3, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(4, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(9, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setResult(tUpgradeStack).setHammerUsage((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getInternalMaterialName()).getMeltingPoint() / 300).setTongUsage((int) (MaterialRegistry.getInstance().getMaterial(tMaterial.getInternalMaterialName()).getMeltingPoint() - 1000) / 300).setProgress((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getInternalMaterialName()).getMeltingPoint() / 100);

                    AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.CHESTPLATESHOULDERLEFT + tMaterial.getOreDicName(), tRecipe);
                }

                if (tMaterial.getPartState(References.InternalNames.Upgrades.Chestplate.SHOULDERRIGHT)) {
                    ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval) MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALCHESTPLATE).getAddon(References.InternalNames.Upgrades.Chestplate.SHOULDERRIGHT + "-" + tMaterial.getInternalMaterialName());
                    ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.iMedievalUpgrades, 1);
                    NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgrade.iMaterialInternalName);
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALCHESTPLATE).getInternalName());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, tUpgrade.getInternalName());

                    tUpgradeStack.setTagCompound(pUpgradeCompound);

                    AnvilRecipe tRecipe = new AnvilRecipe()
                            .setCraftingSlotContent(0, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(1, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(2, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(5, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setResult(tUpgradeStack).setHammerUsage((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getInternalMaterialName()).getMeltingPoint() / 300).setTongUsage((int) (MaterialRegistry.getInstance().getMaterial(tMaterial.getInternalMaterialName()).getMeltingPoint() - 1000) / 300).setProgress((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getInternalMaterialName()).getMeltingPoint() / 100);

                    AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.CHESTPLATESHOULDERRIGHT + tMaterial.getOreDicName(), tRecipe);
                }

                if (tMaterial.getPartState(References.InternalNames.Upgrades.Chestplate.BACKRIGHT)) {
                    ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval) MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALCHESTPLATE).getAddon(References.InternalNames.Upgrades.Chestplate.BACKRIGHT + "-" + tMaterial.getInternalMaterialName());
                    ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.iMedievalUpgrades, 1);
                    NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgrade.iMaterialInternalName);
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALCHESTPLATE).getInternalName());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, tUpgrade.getInternalName());

                    tUpgradeStack.setTagCompound(pUpgradeCompound);

                    AnvilRecipe tRecipe = new AnvilRecipe()
                            .setCraftingSlotContent(5, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(10, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(11, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(15, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(16, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(21, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setResult(tUpgradeStack).setHammerUsage((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getInternalMaterialName()).getMeltingPoint() / 300).setTongUsage((int) (MaterialRegistry.getInstance().getMaterial(tMaterial.getInternalMaterialName()).getMeltingPoint() - 1000) / 300).setProgress((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getInternalMaterialName()).getMeltingPoint() / 100);

                    AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.CHESTPLATEBACKRIGHT + tMaterial.getOreDicName(), tRecipe);
                }

                if (tMaterial.getPartState(References.InternalNames.Upgrades.Chestplate.BACKLEFT)) {
                    ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval) MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALCHESTPLATE).getAddon(References.InternalNames.Upgrades.Chestplate.BACKLEFT + "-" + tMaterial.getInternalMaterialName());
                    ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.iMedievalUpgrades, 1);
                    NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgrade.iMaterialInternalName);
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALCHESTPLATE).getInternalName());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, tUpgrade.getInternalName());

                    tUpgradeStack.setTagCompound(pUpgradeCompound);

                    AnvilRecipe tRecipe = new AnvilRecipe()
                            .setCraftingSlotContent(9, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(14, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(13, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(19, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(18, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(23, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setResult(tUpgradeStack).setHammerUsage((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getInternalMaterialName()).getMeltingPoint() / 300).setTongUsage((int) (MaterialRegistry.getInstance().getMaterial(tMaterial.getInternalMaterialName()).getMeltingPoint() - 1000) / 300).setProgress((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getInternalMaterialName()).getMeltingPoint() / 100);

                    AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.CHESTPLATEBACKLEFT + tMaterial.getOreDicName(), tRecipe);
                }

                if (tMaterial.getPartState(References.InternalNames.Upgrades.Chestplate.FRONTRIGHT)) {
                    ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval) MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALCHESTPLATE).getAddon(References.InternalNames.Upgrades.Chestplate.FRONTRIGHT + "-" + tMaterial.getInternalMaterialName());
                    ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.iMedievalUpgrades, 1);
                    NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgrade.iMaterialInternalName);
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALCHESTPLATE).getInternalName());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, tUpgrade.getInternalName());

                    tUpgradeStack.setTagCompound(pUpgradeCompound);

                    AnvilRecipe tRecipe = new AnvilRecipe()
                            .setCraftingSlotContent(6, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(10, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(11, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(15, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(16, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(20, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setResult(tUpgradeStack).setHammerUsage((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getInternalMaterialName()).getMeltingPoint() / 300).setTongUsage((int) (MaterialRegistry.getInstance().getMaterial(tMaterial.getInternalMaterialName()).getMeltingPoint() - 1000) / 300).setProgress((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getInternalMaterialName()).getMeltingPoint() / 100);

                    AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.CHESTPLATEFRONTRIGHT + tMaterial.getOreDicName(), tRecipe);
                }

                if (tMaterial.getPartState(References.InternalNames.Upgrades.Chestplate.FRONTLEFT)) {
                    ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval) MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALCHESTPLATE).getAddon(References.InternalNames.Upgrades.Chestplate.FRONTLEFT + "-" + tMaterial.getInternalMaterialName());
                    ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.iMedievalUpgrades, 1);
                    NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgrade.iMaterialInternalName);
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALCHESTPLATE).getInternalName());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, tUpgrade.getInternalName());

                    tUpgradeStack.setTagCompound(pUpgradeCompound);

                    AnvilRecipe tRecipe = new AnvilRecipe()
                            .setCraftingSlotContent(8, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(13, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(14, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(18, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(19, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(24, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setResult(tUpgradeStack).setHammerUsage((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getInternalMaterialName()).getMeltingPoint() / 300).setTongUsage((int) (MaterialRegistry.getInstance().getMaterial(tMaterial.getInternalMaterialName()).getMeltingPoint() - 1000) / 300).setProgress((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getInternalMaterialName()).getMeltingPoint() / 100);

                    AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.CHESTPLATEFRONTLEFT + tMaterial.getOreDicName(), tRecipe);
                }
            }
        }

        public static void initializeMedievalLeggingsUpgradeAnvilRecipes() {
            for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values()) {
                if (tMaterial.getPartState(References.InternalNames.Upgrades.Leggings.BACKRIGHT)) {
                    ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval) MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALLEGGINGS).getAddon(References.InternalNames.Upgrades.Leggings.BACKRIGHT + "-" + tMaterial.getInternalMaterialName());
                    ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.iMedievalUpgrades, 1);
                    NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgrade.iMaterialInternalName);
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALLEGGINGS).getInternalName());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, tUpgrade.getInternalName());

                    tUpgradeStack.setTagCompound(pUpgradeCompound);

                    AnvilRecipe tRecipe = new AnvilRecipe()
                            .setCraftingSlotContent(1, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(6, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(11, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(16, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(21, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setResult(tUpgradeStack).setHammerUsage((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getInternalMaterialName()).getMeltingPoint() / 300).setTongUsage((int) (MaterialRegistry.getInstance().getMaterial(tMaterial.getInternalMaterialName()).getMeltingPoint() - 1000) / 300).setProgress((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getInternalMaterialName()).getMeltingPoint() / 100);

                    AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.LEGGINGSBACKRIGHT + tMaterial.getOreDicName(), tRecipe);
                }

                if (tMaterial.getPartState(References.InternalNames.Upgrades.Leggings.BACKLEFT)) {
                    ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval) MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALLEGGINGS).getAddon(References.InternalNames.Upgrades.Leggings.BACKLEFT + "-" + tMaterial.getInternalMaterialName());
                    ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.iMedievalUpgrades, 1);
                    NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgrade.iMaterialInternalName);
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALLEGGINGS).getInternalName());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, tUpgrade.getInternalName());

                    tUpgradeStack.setTagCompound(pUpgradeCompound);

                    AnvilRecipe tRecipe = new AnvilRecipe()
                            .setCraftingSlotContent(3, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(8, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(13, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(18, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(23, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setResult(tUpgradeStack).setHammerUsage((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getInternalMaterialName()).getMeltingPoint() / 300).setTongUsage((int) (MaterialRegistry.getInstance().getMaterial(tMaterial.getInternalMaterialName()).getMeltingPoint() - 1000) / 300).setProgress((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getInternalMaterialName()).getMeltingPoint() / 100);

                    AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.LEGGINGSBACKLEFT + tMaterial.getOreDicName(), tRecipe);
                }

                if (tMaterial.getPartState(References.InternalNames.Upgrades.Leggings.FRONTRIGHT)) {
                    ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval) MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALLEGGINGS).getAddon(References.InternalNames.Upgrades.Leggings.FRONTRIGHT + "-" + tMaterial.getInternalMaterialName());
                    ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.iMedievalUpgrades, 1);
                    NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgrade.iMaterialInternalName);
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALLEGGINGS).getInternalName());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, tUpgrade.getInternalName());

                    tUpgradeStack.setTagCompound(pUpgradeCompound);

                    AnvilRecipe tRecipe = new AnvilRecipe()
                            .setCraftingSlotContent(6, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(7, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(11, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(12, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(16, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(17, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setResult(tUpgradeStack).setHammerUsage((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getInternalMaterialName()).getMeltingPoint() / 300).setTongUsage((int) (MaterialRegistry.getInstance().getMaterial(tMaterial.getInternalMaterialName()).getMeltingPoint() - 1000) / 300).setProgress((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getInternalMaterialName()).getMeltingPoint() / 100);

                    AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.LEGGINGSFRONTRIGHT + tMaterial.getOreDicName(), tRecipe);
                }

                if (tMaterial.getPartState(References.InternalNames.Upgrades.Leggings.FRONTLEFT)) {
                    ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval) MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALLEGGINGS).getAddon(References.InternalNames.Upgrades.Leggings.FRONTLEFT + "-" + tMaterial.getInternalMaterialName());
                    ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.iMedievalUpgrades, 1);
                    NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgrade.iMaterialInternalName);
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALLEGGINGS).getInternalName());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, tUpgrade.getInternalName());

                    tUpgradeStack.setTagCompound(pUpgradeCompound);

                    AnvilRecipe tRecipe = new AnvilRecipe()
                            .setCraftingSlotContent(7, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(8, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(12, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(13, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(17, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(18, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setResult(tUpgradeStack).setHammerUsage((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getInternalMaterialName()).getMeltingPoint() / 300).setTongUsage((int) (MaterialRegistry.getInstance().getMaterial(tMaterial.getInternalMaterialName()).getMeltingPoint() - 1000) / 300).setProgress((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getInternalMaterialName()).getMeltingPoint() / 100);

                    AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.LEGGINGSFRONTLEFT + tMaterial.getOreDicName(), tRecipe);
                }
            }
        }

        public static void initializeMedievalShoesUpgradeAnvilRecipes() {
            for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values()) {
                if (tMaterial.getPartState(References.InternalNames.Upgrades.Shoes.LEFT)) {
                    ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval) MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALSHOES).getAddon(References.InternalNames.Upgrades.Shoes.LEFT + "-" + tMaterial.getInternalMaterialName());
                    ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.iMedievalUpgrades, 1);
                    NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgrade.iMaterialInternalName);
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALSHOES).getInternalName());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, tUpgrade.getInternalName());

                    tUpgradeStack.setTagCompound(pUpgradeCompound);

                    AnvilRecipe tRecipe = new AnvilRecipe()
                            .setCraftingSlotContent(6, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(11, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(15, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(16, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(20, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setResult(tUpgradeStack).setHammerUsage((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getInternalMaterialName()).getMeltingPoint() / 300).setTongUsage((int) (MaterialRegistry.getInstance().getMaterial(tMaterial.getInternalMaterialName()).getMeltingPoint() - 1000) / 300).setProgress((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getInternalMaterialName()).getMeltingPoint() / 100);

                    AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.SHOESLEFT + tMaterial.getOreDicName(), tRecipe);
                }

                if (tMaterial.getPartState(References.InternalNames.Upgrades.Shoes.RIGHT)) {
                    ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval) MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALSHOES).getAddon(References.InternalNames.Upgrades.Shoes.RIGHT + "-" + tMaterial.getInternalMaterialName());
                    ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.iMedievalUpgrades, 1);
                    NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgrade.iMaterialInternalName);
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, MaterialRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALSHOES).getInternalName());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, tUpgrade.getInternalName());

                    tUpgradeStack.setTagCompound(pUpgradeCompound);

                    AnvilRecipe tRecipe = new AnvilRecipe()
                            .setCraftingSlotContent(8, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(13, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(18, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(19, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setCraftingSlotContent(24, new HeatedAnvilRecipeComponent(tMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.getInternalMaterialName()) * 0.5F * 0.95F))
                            .setResult(tUpgradeStack).setHammerUsage((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getInternalMaterialName()).getMeltingPoint() / 300).setTongUsage((int) (MaterialRegistry.getInstance().getMaterial(tMaterial.getInternalMaterialName()).getMeltingPoint() - 1000) / 300).setProgress((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getInternalMaterialName()).getMeltingPoint() / 100);

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
                    if (tUpgradeMaterial.getPartState(References.InternalNames.Upgrades.Helmet.TOP)) {
                        ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.iMedievalUpgrades, 1);
                        NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgradeMaterial.getInternalMaterialName());
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, References.InternalNames.Armor.MEDIEVALHELMET);
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, References.InternalNames.AddonPositions.Helmet.TOP + "-" + tUpgradeMaterial.getInternalMaterialName());

                        tUpgradeStack.setTagCompound(pUpgradeCompound);

                        AnvilRecipe tRecipe = new ArmorUpgradeAnvilRecipe(References.InternalNames.Armor.MEDIEVALHELMET, tArmorMaterial.getInternalMaterialName())
                                .setCraftingSlotContent(6, new HeatedAnvilRecipeComponent(tArmorMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F))
                                .setCraftingSlotContent(8, new HeatedAnvilRecipeComponent(tArmorMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F))
                                .setUpgradeCraftingSlotComponent(2, new StandardAnvilRecipeComponent(tUpgradeStack))
                                .setProgress(8).setHammerUsage(5).setTongUsage(4);

                        AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.HELMETUPGRADETOP + tArmorMaterial.getOreDicName() + "." + tUpgradeMaterial.getOreDicName(), tRecipe);
                    }

                    if (tUpgradeMaterial.getPartState(References.InternalNames.Upgrades.Helmet.LEFT)) {
                        ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.iMedievalUpgrades, 1);
                        NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgradeMaterial.getInternalMaterialName());
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, References.InternalNames.Armor.MEDIEVALHELMET);
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, References.InternalNames.Upgrades.Helmet.LEFT + "-" + tUpgradeMaterial.getInternalMaterialName());

                        tUpgradeStack.setTagCompound(pUpgradeCompound);

                        AnvilRecipe tRecipe = new ArmorUpgradeAnvilRecipe(References.InternalNames.Armor.MEDIEVALHELMET, tArmorMaterial.getInternalMaterialName())
                                .setCraftingSlotContent(6, new HeatedAnvilRecipeComponent(tArmorMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F))
                                .setCraftingSlotContent(16, new HeatedAnvilRecipeComponent(tArmorMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F))
                                .setUpgradeCraftingSlotComponent(10, new StandardAnvilRecipeComponent(tUpgradeStack))
                                .setProgress(8).setHammerUsage(5).setTongUsage(4);

                        AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.HELMETUPGRADELEFT + tArmorMaterial.getOreDicName() + "." + tUpgradeMaterial.getOreDicName(), tRecipe);
                    }

                    if (tUpgradeMaterial.getPartState(References.InternalNames.Upgrades.Helmet.RIGHT)) {
                        ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.iMedievalUpgrades, 1);
                        NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgradeMaterial.getInternalMaterialName());
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, References.InternalNames.Armor.MEDIEVALHELMET);
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, References.InternalNames.Upgrades.Helmet.RIGHT + "-" + tUpgradeMaterial.getInternalMaterialName());

                        tUpgradeStack.setTagCompound(pUpgradeCompound);

                        AnvilRecipe tRecipe = new ArmorUpgradeAnvilRecipe(References.InternalNames.Armor.MEDIEVALHELMET, tArmorMaterial.getInternalMaterialName())
                                .setCraftingSlotContent(8, new HeatedAnvilRecipeComponent(tArmorMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F))
                                .setCraftingSlotContent(18, new HeatedAnvilRecipeComponent(tArmorMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F))
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
                    if (tUpgradeMaterial.getPartState(References.InternalNames.Upgrades.Chestplate.SHOULDERLEFT)) {
                        ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.iMedievalUpgrades, 1);
                        NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgradeMaterial.getInternalMaterialName());
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, References.InternalNames.Armor.MEDIEVALCHESTPLATE);
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, References.InternalNames.AddonPositions.Chestplate.SHOULDERLEFT + "-" + tUpgradeMaterial.getInternalMaterialName());

                        tUpgradeStack.setTagCompound(pUpgradeCompound);

                        AnvilRecipe tRecipe = new ArmorUpgradeAnvilRecipe(References.InternalNames.Armor.MEDIEVALCHESTPLATE, tArmorMaterial.getInternalMaterialName())
                                .setCraftingSlotContent(7, new HeatedAnvilRecipeComponent(tArmorMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F))
                                .setCraftingSlotContent(11, new HeatedAnvilRecipeComponent(tArmorMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F))
                                .setUpgradeCraftingSlotComponent(6, new StandardAnvilRecipeComponent(tUpgradeStack))
                                .setProgress(12).setHammerUsage(7).setTongUsage(8);

                        AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.CHESTPLATEUPGRADESHOULDERLEFT + tArmorMaterial.getOreDicName() + "." + tUpgradeMaterial.getOreDicName(), tRecipe);
                    }

                    if (tUpgradeMaterial.getPartState(References.InternalNames.Upgrades.Chestplate.SHOULDERRIGHT)) {
                        ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.iMedievalUpgrades, 1);
                        NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgradeMaterial.getInternalMaterialName());
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, References.InternalNames.Armor.MEDIEVALCHESTPLATE);
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, References.InternalNames.AddonPositions.Chestplate.SHOULDERRIGHT + "-" + tUpgradeMaterial.getInternalMaterialName());

                        tUpgradeStack.setTagCompound(pUpgradeCompound);

                        AnvilRecipe tRecipe = new ArmorUpgradeAnvilRecipe(References.InternalNames.Armor.MEDIEVALCHESTPLATE, tArmorMaterial.getInternalMaterialName())
                                .setCraftingSlotContent(7, new HeatedAnvilRecipeComponent(tArmorMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F))
                                .setCraftingSlotContent(13, new HeatedAnvilRecipeComponent(tArmorMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F))
                                .setUpgradeCraftingSlotComponent(8, new StandardAnvilRecipeComponent(tUpgradeStack))
                                .setProgress(12).setHammerUsage(7).setTongUsage(8);

                        AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.CHESTPLATEUPGRADESHOULDERRIGHT + tArmorMaterial.getOreDicName() + "." + tUpgradeMaterial.getOreDicName(), tRecipe);
                    }

                    if (tUpgradeMaterial.getPartState(References.InternalNames.Upgrades.Chestplate.BACKRIGHT)) {
                        ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.iMedievalUpgrades, 1);
                        NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgradeMaterial.getInternalMaterialName());
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, References.InternalNames.Armor.MEDIEVALCHESTPLATE);
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, References.InternalNames.AddonPositions.Chestplate.BACKRIGHT + "-" + tUpgradeMaterial.getInternalMaterialName());

                        tUpgradeStack.setTagCompound(pUpgradeCompound);

                        AnvilRecipe tRecipe = new ArmorUpgradeAnvilRecipe(References.InternalNames.Armor.MEDIEVALCHESTPLATE, tArmorMaterial.getInternalMaterialName())
                                .setCraftingSlotContent(13, new HeatedAnvilRecipeComponent(tArmorMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F))
                                .setCraftingSlotContent(17, new HeatedAnvilRecipeComponent(tArmorMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F))
                                .setUpgradeCraftingSlotComponent(18, new StandardAnvilRecipeComponent(tUpgradeStack))
                                .setProgress(12).setHammerUsage(7).setTongUsage(8);

                        AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.CHESTPLATEUPGRADEBACKRIGHT + tArmorMaterial.getOreDicName() + "." + tUpgradeMaterial.getOreDicName(), tRecipe);
                    }

                    if (tUpgradeMaterial.getPartState(References.InternalNames.Upgrades.Chestplate.BACKLEFT)) {
                        ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.iMedievalUpgrades, 1);
                        NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgradeMaterial.getInternalMaterialName());
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, References.InternalNames.Armor.MEDIEVALCHESTPLATE);
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, References.InternalNames.AddonPositions.Chestplate.BACKLEFT + "-" + tUpgradeMaterial.getInternalMaterialName());

                        tUpgradeStack.setTagCompound(pUpgradeCompound);

                        AnvilRecipe tRecipe = new ArmorUpgradeAnvilRecipe(References.InternalNames.Armor.MEDIEVALCHESTPLATE, tArmorMaterial.getInternalMaterialName())
                                .setCraftingSlotContent(11, new HeatedAnvilRecipeComponent(tArmorMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F))
                                .setCraftingSlotContent(17, new HeatedAnvilRecipeComponent(tArmorMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F))
                                .setUpgradeCraftingSlotComponent(16, new StandardAnvilRecipeComponent(tUpgradeStack))
                                .setProgress(12).setHammerUsage(7).setTongUsage(8);

                        AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.CHESTPLATEUPGRADEBACKLEFT + tArmorMaterial.getOreDicName() + "." + tUpgradeMaterial.getOreDicName(), tRecipe);
                    }

                    if (tUpgradeMaterial.getPartState(References.InternalNames.Upgrades.Chestplate.FRONTRIGHT)) {
                        ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.iMedievalUpgrades, 1);
                        NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgradeMaterial.getInternalMaterialName());
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, References.InternalNames.Armor.MEDIEVALCHESTPLATE);
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, References.InternalNames.AddonPositions.Chestplate.FRONTRIGHT + "-" + tUpgradeMaterial.getInternalMaterialName());

                        tUpgradeStack.setTagCompound(pUpgradeCompound);

                        AnvilRecipe tRecipe = new ArmorUpgradeAnvilRecipe(References.InternalNames.Armor.MEDIEVALCHESTPLATE, tArmorMaterial.getInternalMaterialName())
                                .setCraftingSlotContent(13, new HeatedAnvilRecipeComponent(tArmorMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F))
                                .setCraftingSlotContent(17, new HeatedAnvilRecipeComponent(tArmorMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F))
                                .setUpgradeCraftingSlotComponent(18, new StandardAnvilRecipeComponent(tUpgradeStack))
                                .setProgress(12).setHammerUsage(7).setTongUsage(8);

                        AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.CHESTPLATEUPGRADEFRONTRIGHT + tArmorMaterial.getOreDicName() + "." + tUpgradeMaterial.getOreDicName(), tRecipe);
                    }

                    if (tUpgradeMaterial.getPartState(References.InternalNames.Upgrades.Chestplate.FRONTLEFT)) {
                        ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.iMedievalUpgrades, 1);
                        NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgradeMaterial.getInternalMaterialName());
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, References.InternalNames.Armor.MEDIEVALCHESTPLATE);
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, References.InternalNames.AddonPositions.Chestplate.FRONTLEFT + "-" + tUpgradeMaterial.getInternalMaterialName());

                        tUpgradeStack.setTagCompound(pUpgradeCompound);

                        AnvilRecipe tRecipe = new ArmorUpgradeAnvilRecipe(References.InternalNames.Armor.MEDIEVALCHESTPLATE, tArmorMaterial.getInternalMaterialName())
                                .setCraftingSlotContent(11, new HeatedAnvilRecipeComponent(tArmorMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F))
                                .setCraftingSlotContent(17, new HeatedAnvilRecipeComponent(tArmorMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F))
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
                    if (tUpgradeMaterial.getPartState(References.InternalNames.Upgrades.Leggings.BACKRIGHT)) {
                        ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.iMedievalUpgrades, 1);
                        NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgradeMaterial.getInternalMaterialName());
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, References.InternalNames.Armor.MEDIEVALLEGGINGS);
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, References.InternalNames.AddonPositions.Leggings.BACKRIGHT + "-" + tUpgradeMaterial.getInternalMaterialName());

                        tUpgradeStack.setTagCompound(pUpgradeCompound);

                        AnvilRecipe tRecipe = new ArmorUpgradeAnvilRecipe(References.InternalNames.Armor.MEDIEVALLEGGINGS, tArmorMaterial.getInternalMaterialName())
                                .setCraftingSlotContent(13, new HeatedAnvilRecipeComponent(tArmorMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F))
                                .setCraftingSlotContent(17, new HeatedAnvilRecipeComponent(tArmorMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F))
                                .setUpgradeCraftingSlotComponent(18, new StandardAnvilRecipeComponent(tUpgradeStack))
                                .setProgress(12).setHammerUsage(7).setTongUsage(6);

                        AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.LEGGINGSUPGRADEBACKRIGHT + tArmorMaterial.getOreDicName() + "." + tUpgradeMaterial.getOreDicName(), tRecipe);
                    }

                    if (tUpgradeMaterial.getPartState(References.InternalNames.Upgrades.Leggings.BACKLEFT)) {
                        ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.iMedievalUpgrades, 1);
                        NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgradeMaterial.getInternalMaterialName());
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, References.InternalNames.Armor.MEDIEVALLEGGINGS);
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, References.InternalNames.AddonPositions.Leggings.BACKLEFT + "-" + tUpgradeMaterial.getInternalMaterialName());

                        tUpgradeStack.setTagCompound(pUpgradeCompound);

                        AnvilRecipe tRecipe = new ArmorUpgradeAnvilRecipe(References.InternalNames.Armor.MEDIEVALLEGGINGS, tArmorMaterial.getInternalMaterialName())
                                .setCraftingSlotContent(11, new HeatedAnvilRecipeComponent(tArmorMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F))
                                .setCraftingSlotContent(17, new HeatedAnvilRecipeComponent(tArmorMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F))
                                .setUpgradeCraftingSlotComponent(16, new StandardAnvilRecipeComponent(tUpgradeStack))
                                .setProgress(12).setHammerUsage(7).setTongUsage(6);

                        AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.LEGGINGSUPGRADEBACKLEFT + tArmorMaterial.getOreDicName() + "." + tUpgradeMaterial.getOreDicName(), tRecipe);
                    }

                    if (tUpgradeMaterial.getPartState(References.InternalNames.Upgrades.Leggings.FRONTRIGHT)) {
                        ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.iMedievalUpgrades, 1);
                        NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgradeMaterial.getInternalMaterialName());
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, References.InternalNames.Armor.MEDIEVALLEGGINGS);
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, References.InternalNames.AddonPositions.Leggings.FRONTRIGHT + "-" + tUpgradeMaterial.getInternalMaterialName());

                        tUpgradeStack.setTagCompound(pUpgradeCompound);

                        AnvilRecipe tRecipe = new ArmorUpgradeAnvilRecipe(References.InternalNames.Armor.MEDIEVALLEGGINGS, tArmorMaterial.getInternalMaterialName())
                                .setCraftingSlotContent(13, new HeatedAnvilRecipeComponent(tArmorMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F))
                                .setCraftingSlotContent(7, new HeatedAnvilRecipeComponent(tArmorMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F))
                                .setUpgradeCraftingSlotComponent(8, new StandardAnvilRecipeComponent(tUpgradeStack))
                                .setProgress(12).setHammerUsage(7).setTongUsage(6);

                        AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.LEGGINGSUPGRADEFRONTRIGHT + tArmorMaterial.getOreDicName() + "." + tUpgradeMaterial.getOreDicName(), tRecipe);
                    }

                    if (tUpgradeMaterial.getPartState(References.InternalNames.Upgrades.Leggings.FRONTLEFT)) {
                        ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.iMedievalUpgrades, 1);
                        NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgradeMaterial.getInternalMaterialName());
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, References.InternalNames.Armor.MEDIEVALLEGGINGS);
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, References.InternalNames.AddonPositions.Leggings.FRONTLEFT + "-" + tUpgradeMaterial.getInternalMaterialName());

                        tUpgradeStack.setTagCompound(pUpgradeCompound);

                        AnvilRecipe tRecipe = new ArmorUpgradeAnvilRecipe(References.InternalNames.Armor.MEDIEVALLEGGINGS, tArmorMaterial.getInternalMaterialName())
                                .setCraftingSlotContent(11, new HeatedAnvilRecipeComponent(tArmorMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F))
                                .setCraftingSlotContent(7, new HeatedAnvilRecipeComponent(tArmorMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F))
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
                    if (tUpgradeMaterial.getPartState(References.InternalNames.Upgrades.Shoes.LEFT)) {
                        ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.iMedievalUpgrades, 1);
                        NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgradeMaterial.getInternalMaterialName());
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, References.InternalNames.Armor.MEDIEVALSHOES);
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, References.InternalNames.AddonPositions.Shoes.LEFT + "-" + tUpgradeMaterial.getInternalMaterialName());

                        tUpgradeStack.setTagCompound(pUpgradeCompound);

                        AnvilRecipe tRecipe = new ArmorUpgradeAnvilRecipe(References.InternalNames.Armor.MEDIEVALSHOES, tArmorMaterial.getInternalMaterialName())
                                .setCraftingSlotContent(11, new HeatedAnvilRecipeComponent(tArmorMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F))
                                .setCraftingSlotContent(7, new HeatedAnvilRecipeComponent(tArmorMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F))
                                .setUpgradeCraftingSlotComponent(6, new StandardAnvilRecipeComponent(tUpgradeStack))
                                .setProgress(8).setHammerUsage(4).setTongUsage(5);

                        AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.SHOESUPGRADELEFT + tArmorMaterial.getOreDicName() + "." + tUpgradeMaterial.getOreDicName(), tRecipe);
                    }

                    if (tUpgradeMaterial.getPartState(References.InternalNames.Upgrades.Shoes.RIGHT)) {
                        ItemStack tUpgradeStack = new ItemStack(GeneralRegistry.Items.iMedievalUpgrades, 1);
                        NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgradeMaterial.getInternalMaterialName());
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Armor.ArmorID, References.InternalNames.Armor.MEDIEVALSHOES);
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, References.InternalNames.AddonPositions.Shoes.RIGHT + "-" + tUpgradeMaterial.getInternalMaterialName());

                        tUpgradeStack.setTagCompound(pUpgradeCompound);

                        AnvilRecipe tRecipe = new ArmorUpgradeAnvilRecipe(References.InternalNames.Armor.MEDIEVALSHOES, tArmorMaterial.getInternalMaterialName())
                                .setCraftingSlotContent(13, new HeatedAnvilRecipeComponent(tArmorMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F))
                                .setCraftingSlotContent(7, new HeatedAnvilRecipeComponent(tArmorMaterial.getInternalMaterialName(), References.InternalNames.HeatedItemTypes.RING, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(References.InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F))
                                .setUpgradeCraftingSlotComponent(8, new StandardAnvilRecipeComponent(tUpgradeStack))
                                .setProgress(8).setHammerUsage(4).setTongUsage(5);

                        AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.SHOESUPGRADERIGHT + tArmorMaterial.getOreDicName() + "." + tUpgradeMaterial.getOreDicName(), tRecipe);
                    }
                }
            }
        }
    }

    public static class SystemInit {
        public static void RegisterBlocks() {
            GeneralRegistry.Blocks.iBlockFirePit = (BlockFirePit) new BlockFirePit().setHardness(2.5F).setStepSound(Block.soundTypeMetal)
                    .setBlockName(References.InternalNames.Blocks.FirePit).setCreativeTab(GeneralRegistry.iTabArmoryComponents).setBlockTextureName(TextureAddressHelper.getTextureAddress("DarkSteelPartical"));

            GeneralRegistry.Blocks.iBlockHeater = (BlockHeater) new BlockHeater().setHardness(2F).setStepSound(Block.soundTypeMetal)
                    .setBlockName(References.InternalNames.Blocks.Heater).setCreativeTab(GeneralRegistry.iTabArmoryComponents).setBlockTextureName(TextureAddressHelper.getTextureAddress("DarkSteelPartical"));

            GeneralRegistry.Blocks.iBlockAnvil = (BlockArmorsAnvil) new BlockArmorsAnvil().setHardness(2F).setStepSound(Block.soundTypeAnvil)
                    .setBlockName(References.InternalNames.Blocks.ArmorsAnvil).setCreativeTab(GeneralRegistry.iTabArmoryComponents).setBlockTextureName(TextureAddressHelper.getTextureAddress("DarkSteelPartical"));

            GeneralRegistry.Blocks.iBookBinder = (BlockBookBinder) new BlockBookBinder().setHardness(1F).setStepSound(Block.soundTypeWood)
                    .setBlockName(References.InternalNames.Blocks.BookBinder).setCreativeTab(GeneralRegistry.iTabArmoryComponents).setBlockTextureName(TextureAddressHelper.getTextureAddress("DarkSteelPartical"));

            GameRegistry.registerBlock(GeneralRegistry.Blocks.iBlockFirePit, References.InternalNames.Blocks.FirePit);
            GameRegistry.registerBlock(GeneralRegistry.Blocks.iBlockHeater, References.InternalNames.Blocks.Heater);
            GameRegistry.registerBlock(GeneralRegistry.Blocks.iBlockAnvil, References.InternalNames.Blocks.ArmorsAnvil);
            GameRegistry.registerBlock(GeneralRegistry.Blocks.iBookBinder, References.InternalNames.Blocks.BookBinder);
        }

        public static void RegisterItems() {
            GeneralRegistry.Items.iMetalChain = new ItemMetalChain();
            GeneralRegistry.Items.iMetalRing = new ItemMetalRing();
            GeneralRegistry.Items.iHeatedIngot = new ItemHeatedItem();
            GeneralRegistry.Items.iNugget = new ItemNugget();
            GeneralRegistry.Items.iPlate = new ItemPlate();
            GeneralRegistry.Items.iFan = new ItemFan();
            GeneralRegistry.Items.iHammer = new ItemHammer();
            GeneralRegistry.Items.iTongs = new ItemTongs();
            GeneralRegistry.Items.iMedievalUpgrades = new ItemUpgradeMedieval();
            GeneralRegistry.Items.iSmithingsGuide = new ItemSmithingsGuide();
            GeneralRegistry.Items.iBlueprints = new ItemBlueprint();
            GeneralRegistry.Items.iLabel = new ItemGuideLabel();

            for (MultiLayeredArmor tCore : MaterialRegistry.getInstance().getAllRegisteredArmors().values()) {
                GameRegistry.registerItem(tCore, tCore.getInternalName());
            }

            GameRegistry.registerItem(GeneralRegistry.Items.iMetalChain, References.InternalNames.Items.ItemMetalChain);
            GameRegistry.registerItem(GeneralRegistry.Items.iMetalRing, References.InternalNames.Items.ItemMetalRing);
            GameRegistry.registerItem(GeneralRegistry.Items.iHeatedIngot, References.InternalNames.Items.ItemHeatedIngot);
            GameRegistry.registerItem(GeneralRegistry.Items.iNugget, References.InternalNames.Items.ItemNugget);
            GameRegistry.registerItem(GeneralRegistry.Items.iPlate, References.InternalNames.Items.ItemPlate);
            GameRegistry.registerItem(GeneralRegistry.Items.iFan, References.InternalNames.Items.ItemFan);
            GameRegistry.registerItem(GeneralRegistry.Items.iHammer, References.InternalNames.Items.ItemHammer);
            GameRegistry.registerItem(GeneralRegistry.Items.iTongs, References.InternalNames.Items.ItemTongs);
            GameRegistry.registerItem(GeneralRegistry.Items.iMedievalUpgrades, References.InternalNames.Items.ItemMedievalUpdrade);
            GameRegistry.registerItem(GeneralRegistry.Items.iSmithingsGuide, References.InternalNames.Items.ItemSmithingsGuide);
            GameRegistry.registerItem(GeneralRegistry.Items.iBlueprints, References.InternalNames.Items.ItemBlueprint);
            GameRegistry.registerItem(GeneralRegistry.Items.iLabel, References.InternalNames.Items.ItemGuideLabel);
        }

        public static void RegisterTileEntities() {
            GameRegistry.registerTileEntity(TileEntityFirePit.class, References.InternalNames.TileEntities.FirePitContainer);
            GameRegistry.registerTileEntity(TileEntityHeater.class, References.InternalNames.TileEntities.HeaterComponent);
            GameRegistry.registerTileEntity(TileEntityArmorsAnvil.class, References.InternalNames.TileEntities.ArmorsAnvil);
            GameRegistry.registerTileEntity(TileEntityBookBinder.class, References.InternalNames.TileEntities.BookBinder);
        }

        public static void loadMaterialConfig() {
            GeneralRegistry.iLogger.info("Started loading custom ArmorMaterial Values from Config.");
            ArmorDataConfigHandler tConfigHandler = new ArmorDataConfigHandler();

            tConfigHandler.loadIDs();
            tConfigHandler.loadIsBaseArmorMaterial();
            tConfigHandler.loadTemperatureCoefficient();
            tConfigHandler.loadMeltingPoint();
            tConfigHandler.loadActiveParts();
            tConfigHandler.loadBaseDamageAbsorptions();
            tConfigHandler.loadPartModifiers();
            tConfigHandler.loadBaseDurability();
            tConfigHandler.loadColorSettings();

            GeneralRegistry.iLogger.info("Loading of the custom ArmorMaterial Values has succesfully been performed!");
        }

        public static void removeRecipes() {
            if (!ArmoryConfig.enableHardModeNuggetRemoval)
                return;

            ListIterator<IRecipe> tIterator = CraftingManager.getInstance().getRecipeList().listIterator();
            while (tIterator.hasNext()) {
                IRecipe tRecipe = tIterator.next();
                tryRemoveRecipeFromGame(tRecipe, tIterator);
            }
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
                                GeneralRegistry.iLogger.info("Could not remove recipe of: " + ItemStackHelper.toString(pRecipe.getRecipeOutput()));
                            }
                        }
                    }
                }
            }
        }

        public static void initializeOreDic() {
            ArrayList<ItemStack> tChains = new ArrayList<ItemStack>();
            ArrayList<ItemStack> tRings = new ArrayList<ItemStack>();
            ArrayList<ItemStack> tPlates = new ArrayList<ItemStack>();
            ArrayList<ItemStack> tNuggets = new ArrayList<ItemStack>();

            GeneralRegistry.Items.iMetalChain.getSubItems(null, null, tChains);
            GeneralRegistry.Items.iMetalRing.getSubItems(null, null, tRings);
            GeneralRegistry.Items.iPlate.getSubItems(null, null, tPlates);
            GeneralRegistry.Items.iNugget.getSubItems(null, null, tNuggets);

            for (ItemStack tChain : tChains) {
                String tMaterial = tChain.getTagCompound().getString(References.NBTTagCompoundData.Material);
                OreDictionary.registerOre("chain" + MaterialRegistry.getInstance().getMaterial(tMaterial).getOreDicName(), tChain);
            }

            for (ItemStack tRing : tRings) {
                String tMaterial = tRing.getTagCompound().getString(References.NBTTagCompoundData.Material);
                OreDictionary.registerOre("ring" + MaterialRegistry.getInstance().getMaterial(tMaterial).getOreDicName(), tRing);
            }

            for (ItemStack tPlate : tPlates) {
                String tMaterial = tPlate.getTagCompound().getString(References.NBTTagCompoundData.Material);
                OreDictionary.registerOre("plate" + MaterialRegistry.getInstance().getMaterial(tMaterial).getOreDicName(), tPlate);
            }

            for (ItemStack tNugget : tNuggets) {
                String tMaterial = tNugget.getTagCompound().getString(References.NBTTagCompoundData.Material);
                OreDictionary.registerOre("nugget" + MaterialRegistry.getInstance().getMaterial(tMaterial).getOreDicName(), tNugget);
            }
        }
    }

}
