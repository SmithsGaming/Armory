package com.Orion.Armory.Common.Logic;
/*
 *   ArmoryInitializer
 *   Created by: Orion
 *   Created on: 17-9-2014
 */

import com.Orion.Armory.Armory;
import com.Orion.Armory.Common.Blocks.BlockArmorsAnvil;
import com.Orion.Armory.Common.Blocks.BlockFirePit;
import com.Orion.Armory.Common.Blocks.BlockHeater;
import com.Orion.Armory.Common.Crafting.Anvil.AnvilRecipe;
import com.Orion.Armory.Common.Crafting.Anvil.HeatedAnvilRecipeComponent;
import com.Orion.Armory.Common.Crafting.ChainCraftingRecipe;
import com.Orion.Armory.Common.Crafting.MedievalArmorCraftingRecipe;
import com.Orion.Armory.Common.Events.ModifyMaterialEvent;
import com.Orion.Armory.Common.Events.RegisterArmorEvent;
import com.Orion.Armory.Common.Events.RegisterMaterialsEvent;
import com.Orion.Armory.Common.Events.RegisterUpgradesEvent;
import com.Orion.Armory.Common.Factory.HeatedItemFactory;
import com.Orion.Armory.Common.Factory.MedievalArmorFactory;
import com.Orion.Armory.Common.Item.*;
import com.Orion.Armory.Common.Item.Armor.Core.ArmorAddonPosition;
import com.Orion.Armory.Common.Item.Armor.Core.MLAAddon;
import com.Orion.Armory.Common.Item.Armor.Core.MultiLayeredArmor;
import com.Orion.Armory.Common.Item.Armor.TierMedieval.ArmorMaterialMedieval;
import com.Orion.Armory.Common.Item.Armor.TierMedieval.ArmorMedieval;
import com.Orion.Armory.Common.Item.Armor.TierMedieval.ArmorUpgradeMedieval;
import com.Orion.Armory.Common.Registry.GeneralRegistry;
import com.Orion.Armory.Common.Registry.MedievalRegistry;
import com.Orion.Armory.Common.TileEntity.TileEntityArmorsAnvil;
import com.Orion.Armory.Common.TileEntity.TileEntityFirePit;
import com.Orion.Armory.Common.TileEntity.TileEntityHeater;
import com.Orion.Armory.Util.Client.Colors;
import com.Orion.Armory.Util.Client.TextureAddressHelper;
import com.Orion.Armory.Util.Client.TranslationKeys;
import com.Orion.Armory.Util.References;
import com.Orion.Armory.Util.References.InternalNames;
import cpw.mods.fml.common.registry.GameRegistry;
import cpw.mods.fml.relauncher.Side;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumChatFormatting;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.oredict.OreDictionary;
import net.minecraftforge.oredict.ShapelessOreRecipe;
import tconstruct.tools.TinkerTools;

import java.util.HashMap;
import java.util.ListIterator;

public class ArmoryInitializer
{
    public static void InitializeServer()
    {
        Armory.iSide = Side.SERVER;
        MedievalInitialization.Initialize();
        SystemInit.RegisterBlocks();
        SystemInit.RegisterItems();
        SystemInit.RegisterTileEntities();
        MedievalInitialization.prepareGame();
    }

    public static void postInitializeServer()
    {
        SystemInit.removeRecipes();
    }

    public static class MedievalInitialization
    {
        public static void Initialize()
        {
            registerArmorPieces();
            registerMaterials();
            registerAddonPositions();
            registerUpgrades();
            modifyMaterials();
        }

        private static void registerArmorPieces()
        {
            MedievalRegistry.getInstance().registerNewArmor(new ArmorMedieval(InternalNames.Armor.MEDIEVALHELMET, 0));
            MedievalRegistry.getInstance().registerNewArmor(new ArmorMedieval(InternalNames.Armor.MEDIEVALCHESTPLATE, 1));
            MedievalRegistry.getInstance().registerNewArmor(new ArmorMedieval(InternalNames.Armor.MEDIEVALLEGGINGS, 2));
            MedievalRegistry.getInstance().registerNewArmor(new ArmorMedieval(InternalNames.Armor.MEDIEVALSHOES, 3));

            MinecraftForge.EVENT_BUS.register(new RegisterArmorEvent());
        }

        private static void registerMaterials()
        {
            ArmorMaterialMedieval tIron = new ArmorMaterialMedieval(InternalNames.Materials.Vanilla.IRON, TranslationKeys.Materials.VisibleNames.Iron, "Iron", EnumChatFormatting.WHITE, true, new HashMap<String, Float>(), new HashMap<String, Integer>(), new HashMap<String, Integer>(), new HashMap<String, Boolean>(), Colors.Metals.IRON, 1538, 0.225F, new ItemStack(Items.iron_ingot));
            ArmorMaterialMedieval tChain = new ArmorMaterialMedieval(InternalNames.Materials.Vanilla.CHAIN, TranslationKeys.Materials.VisibleNames.Steel, "Steel", EnumChatFormatting.GRAY, true, new HashMap<String, Float>(), new HashMap<String, Integer>(), new HashMap<String, Integer>(), new HashMap<String, Boolean>(), Colors.Metals.CHAIN, 1148, 0.25F, new ItemStack(TinkerTools.materials, 1, 16));
            ArmorMaterialMedieval tObsidian = new ArmorMaterialMedieval(InternalNames.Materials.Vanilla.OBSIDIAN, TranslationKeys.Materials.VisibleNames.Obsidian, "Obsidian", EnumChatFormatting.DARK_PURPLE, true, new HashMap<String, Float>(), new HashMap<String, Integer>(), new HashMap<String, Integer>(), new HashMap<String, Boolean>(), Colors.Metals.OBSIDIAN, 998, 0.345F, new ItemStack(Item.getItemFromBlock(Blocks.obsidian)));
            ArmorMaterialMedieval tAlumite = new ArmorMaterialMedieval(InternalNames.Materials.ModMaterials.TinkersConstruct.ALUMITE, TranslationKeys.Materials.VisibleNames.Alumite, "Alumite", EnumChatFormatting.RED, true, new HashMap<String, Float>(), new HashMap<String, Integer>(), new HashMap<String, Integer>(), new HashMap<String, Boolean>(), Colors.Metals.ALUMITE, 1023, 0.2F, new ItemStack(TinkerTools.materials, 1, 15));
            ArmorMaterialMedieval tArdite = new ArmorMaterialMedieval(InternalNames.Materials.ModMaterials.TinkersConstruct.ARDITE, TranslationKeys.Materials.VisibleNames.Ardite, "Ardite", EnumChatFormatting.DARK_RED, true, new HashMap<String, Float>(), new HashMap<String, Integer>(), new HashMap<String, Integer>(), new HashMap<String, Boolean>(), Colors.Metals.ARDITE, 1752, 0.4F, new ItemStack(TinkerTools.materials, 1, 4));
            ArmorMaterialMedieval tCobalt = new ArmorMaterialMedieval(InternalNames.Materials.ModMaterials.TinkersConstruct.COBALT, TranslationKeys.Materials.VisibleNames.Cobalt, "Cobalt", EnumChatFormatting.BLUE, true, new HashMap<String, Float>(), new HashMap<String, Integer>(), new HashMap<String, Integer>(), new HashMap<String, Boolean>(), Colors.Metals.COBALT, 1635, 0.3F, new ItemStack(TinkerTools.materials, 1, 3));
            ArmorMaterialMedieval tManyullun = new ArmorMaterialMedieval(InternalNames.Materials.ModMaterials.TinkersConstruct.MANYULLUN, TranslationKeys.Materials.VisibleNames.Manyullun, "Manyullyn", EnumChatFormatting.LIGHT_PURPLE, true, new HashMap<String, Float>(), new HashMap<String, Integer>(), new HashMap<String, Integer>(), new HashMap<String, Boolean>(), Colors.Metals.MANYULLUN, 2963, 0.489F, new ItemStack(TinkerTools.materials, 1, 5));
            ArmorMaterialMedieval tBronze = new ArmorMaterialMedieval(InternalNames.Materials.Common.BRONZE, TranslationKeys.Materials.VisibleNames.Bronze, "Bronze", EnumChatFormatting.GOLD, true, new HashMap<String, Float>(), new HashMap<String, Integer>(), new HashMap<String, Integer>(), new HashMap<String, Boolean>(), Colors.Metals.BRONZE, 950, 0.186F, new ItemStack(TinkerTools.materials, 1, 13));

            MedievalRegistry.getInstance().registerMaterial(tIron);
            MedievalRegistry.getInstance().registerMaterial(tChain);
            MedievalRegistry.getInstance().registerMaterial(tObsidian);
            MedievalRegistry.getInstance().registerMaterial(tAlumite);
            MedievalRegistry.getInstance().registerMaterial(tArdite);
            MedievalRegistry.getInstance().registerMaterial(tCobalt);
            MedievalRegistry.getInstance().registerMaterial(tManyullun);
            MedievalRegistry.getInstance().registerMaterial(tBronze);

            MinecraftForge.EVENT_BUS.register(new RegisterMaterialsEvent());
        }

        private static void registerAddonPositions()
        {
            //Registering the positions to the helmet
            ArmorMedieval tHelmet = (ArmorMedieval) MedievalRegistry.getInstance().getArmor(InternalNames.Armor.MEDIEVALHELMET);
            tHelmet.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Helmet.TOP, InternalNames.Armor.MEDIEVALHELMET, 1));
            tHelmet.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Helmet.LEFT, InternalNames.Armor.MEDIEVALHELMET, 1));
            tHelmet.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Helmet.RIGHT, InternalNames.Armor.MEDIEVALHELMET, 1));
            tHelmet.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Helmet.AQUABREATHING, InternalNames.Armor.MEDIEVALHELMET, 1));
            tHelmet.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Helmet.NIGHTSIGHT, InternalNames.Armor.MEDIEVALHELMET, 1));
            tHelmet.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Helmet.THORNS, InternalNames.Armor.MEDIEVALHELMET, 1));
            tHelmet.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Helmet.REINFORCED, InternalNames.Armor.MEDIEVALHELMET, 1));
            tHelmet.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Helmet.AUTOREPAIR, InternalNames.Armor.MEDIEVALHELMET, 1));
            tHelmet.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Helmet.ELECTRIC, InternalNames.Armor.MEDIEVALHELMET, 1));

            //Registering the positions to the chestplate
            ArmorMedieval tChestplate = (ArmorMedieval) MedievalRegistry.getInstance().getArmor(InternalNames.Armor.MEDIEVALCHESTPLATE);
            tChestplate.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Chestplate.SHOULDERLEFT, InternalNames.Armor.MEDIEVALCHESTPLATE, 1));
            tChestplate.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Chestplate.SHOULDERRIGHT, InternalNames.Armor.MEDIEVALCHESTPLATE, 1));
            tChestplate.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Chestplate.FRONTLEFT, InternalNames.Armor.MEDIEVALCHESTPLATE, 1));
            tChestplate.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Chestplate.FRONTRIGHT, InternalNames.Armor.MEDIEVALCHESTPLATE, 1));
            tChestplate.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Chestplate.BACKLEFT, InternalNames.Armor.MEDIEVALCHESTPLATE, 1));
            tChestplate.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Chestplate.BACKRIGHT, InternalNames.Armor.MEDIEVALCHESTPLATE, 1));
            tChestplate.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Chestplate.STRENGTH, InternalNames.Armor.MEDIEVALCHESTPLATE, 1));
            tChestplate.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Chestplate.HASTE, InternalNames.Armor.MEDIEVALCHESTPLATE, 1));
            tChestplate.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Chestplate.FLYING, InternalNames.Armor.MEDIEVALCHESTPLATE, 1));
            tChestplate.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Chestplate.THORNS, InternalNames.Armor.MEDIEVALCHESTPLATE, 1));
            tChestplate.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Chestplate.REINFORCED, InternalNames.Armor.MEDIEVALCHESTPLATE, 1));
            tChestplate.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Chestplate.AUTOREPAIR, InternalNames.Armor.MEDIEVALCHESTPLATE, 1));
            tChestplate.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Chestplate.ELECTRIC, InternalNames.Armor.MEDIEVALCHESTPLATE, 1));

            //Registering the positions to the leggins
            ArmorMedieval tLeggins = (ArmorMedieval) MedievalRegistry.getInstance().getArmor(InternalNames.Armor.MEDIEVALLEGGINGS);
            tLeggins.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Leggings.FRONTLEFT, InternalNames.Armor.MEDIEVALLEGGINGS, 1));
            tLeggins.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Leggings.FRONTRIGHT, InternalNames.Armor.MEDIEVALLEGGINGS, 1));
            tLeggins.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Leggings.BACKLEFT, InternalNames.Armor.MEDIEVALLEGGINGS, 1));
            tLeggins.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Leggings.BACKRIGHT, InternalNames.Armor.MEDIEVALLEGGINGS, 1));
            tLeggins.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Leggings.SPEED, InternalNames.Armor.MEDIEVALLEGGINGS, 1));
            tLeggins.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Leggings.JUMPASSIST, InternalNames.Armor.MEDIEVALLEGGINGS, 1));
            tLeggins.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Leggings.UPHILLASSIST, InternalNames.Armor.MEDIEVALLEGGINGS, 1));
            tLeggins.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Leggings.THORNS, InternalNames.Armor.MEDIEVALLEGGINGS, 1));
            tLeggins.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Leggings.REINFORCED, InternalNames.Armor.MEDIEVALLEGGINGS, 1));
            tLeggins.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Leggings.AUTOREPAIR, InternalNames.Armor.MEDIEVALLEGGINGS, 1));
            tLeggins.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Leggings.ELECTRIC, InternalNames.Armor.MEDIEVALLEGGINGS, 1));

            //Registering the positions to the shoes
            ArmorMedieval tShoes = (ArmorMedieval) MedievalRegistry.getInstance().getArmor(InternalNames.Armor.MEDIEVALSHOES);
            tLeggins.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Shoes.LEFT, InternalNames.Armor.MEDIEVALSHOES, 1));
            tLeggins.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Shoes.RIGHT, InternalNames.Armor.MEDIEVALSHOES, 1));
            tLeggins.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Shoes.FALLASSIST, InternalNames.Armor.MEDIEVALSHOES, 1));
            tLeggins.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Shoes.SWIMASSIST, InternalNames.Armor.MEDIEVALSHOES, 1));
            tLeggins.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Shoes.REINFORCED, InternalNames.Armor.MEDIEVALSHOES, 1));
            tLeggins.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Shoes.AUTOREPAIR, InternalNames.Armor.MEDIEVALSHOES, 1));
            tLeggins.registerAddonPosition(new ArmorAddonPosition(InternalNames.AddonPositions.Shoes.ELECTRIC, InternalNames.Armor.MEDIEVALSHOES, 1));
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
            for (ArmorMaterialMedieval tMaterial: MedievalRegistry.getInstance().getArmorMaterials().values())
            {
                ArmorUpgradeMedieval tTopHead = new ArmorUpgradeMedieval(InternalNames.Upgrades.Helmet.TOP, InternalNames.Armor.MEDIEVALHELMET, InternalNames.AddonPositions.Helmet.TOP, tMaterial.iInternalName, "Head protection", "", 2.5F, 60, 1);
                MedievalRegistry.getInstance().registerUpgrade(tTopHead);

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
            for (ArmorMaterialMedieval tMaterial: MedievalRegistry.getInstance().getArmorMaterials().values())
            {
                ArmorUpgradeMedieval tEarProtectionLeft = new ArmorUpgradeMedieval(InternalNames.Upgrades.Helmet.LEFT, InternalNames.Armor.MEDIEVALHELMET, InternalNames.AddonPositions.Helmet.LEFT, tMaterial.iInternalName, "Ear protection left", "", 0.5F, 20, 1);
                ArmorUpgradeMedieval tEarProtectionRight = new ArmorUpgradeMedieval(InternalNames.Upgrades.Helmet.RIGHT, InternalNames.Armor.MEDIEVALHELMET, InternalNames.AddonPositions.Helmet.RIGHT, tMaterial.iInternalName, "Ear protection right", "", 0.5F, 20, 1);
                MedievalRegistry.getInstance().registerUpgrade(tEarProtectionLeft);
                MedievalRegistry.getInstance().registerUpgrade(tEarProtectionRight);

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
            for (ArmorMaterialMedieval tMaterial: MedievalRegistry.getInstance().getArmorMaterials().values())
            {
                ArmorUpgradeMedieval tShoulderPadLeft = new ArmorUpgradeMedieval(InternalNames.Upgrades.Chestplate.SHOULDERLEFT, InternalNames.Armor.MEDIEVALCHESTPLATE, InternalNames.AddonPositions.Chestplate.SHOULDERLEFT, tMaterial.iInternalName, "Shoulder pad left", "", 1F, 50, 1);
                ArmorUpgradeMedieval tShoulderPadRight = new ArmorUpgradeMedieval(InternalNames.Upgrades.Chestplate.SHOULDERRIGHT, InternalNames.Armor.MEDIEVALCHESTPLATE, InternalNames.AddonPositions.Chestplate.SHOULDERRIGHT, tMaterial.iInternalName, "Shoulder pad right", "", 1F, 50, 1);
                MedievalRegistry.getInstance().registerUpgrade(tShoulderPadLeft);
                MedievalRegistry.getInstance().registerUpgrade(tShoulderPadRight);

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
            for (ArmorMaterialMedieval tMaterial: MedievalRegistry.getInstance().getArmorMaterials().values())
            {
                ArmorUpgradeMedieval tFrontChestProtectionLeft = new ArmorUpgradeMedieval(InternalNames.Upgrades.Chestplate.FRONTLEFT, InternalNames.Armor.MEDIEVALCHESTPLATE, InternalNames.AddonPositions.Chestplate.FRONTLEFT, tMaterial.iInternalName, "Front chest protection left", "", 2F, 150, 1);
                ArmorUpgradeMedieval tFrontChestProtectionRight = new ArmorUpgradeMedieval(InternalNames.Upgrades.Chestplate.FRONTRIGHT, InternalNames.Armor.MEDIEVALCHESTPLATE, InternalNames.AddonPositions.Chestplate.FRONTRIGHT, tMaterial.iInternalName, "Front chest protection right", "", 2F, 150, 1);
                MedievalRegistry.getInstance().registerUpgrade(tFrontChestProtectionLeft);
                MedievalRegistry.getInstance().registerUpgrade(tFrontChestProtectionRight);

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
            for (ArmorMaterialMedieval tMaterial: MedievalRegistry.getInstance().getArmorMaterials().values())
            {
                ArmorUpgradeMedieval tBackChestProtectionLeft = new ArmorUpgradeMedieval(InternalNames.Upgrades.Chestplate.BACKLEFT, InternalNames.Armor.MEDIEVALCHESTPLATE, InternalNames.AddonPositions.Chestplate.BACKLEFT, tMaterial.iInternalName, "Back chest protection left", "", 2F, 150, 1);
                ArmorUpgradeMedieval tBackChestProtectionRight = new ArmorUpgradeMedieval(InternalNames.Upgrades.Chestplate.BACKRIGHT, InternalNames.Armor.MEDIEVALCHESTPLATE, InternalNames.AddonPositions.Chestplate.BACKRIGHT, tMaterial.iInternalName, "Back chest protection right", "", 2F, 150, 1);
                MedievalRegistry.getInstance().registerUpgrade(tBackChestProtectionLeft);
                MedievalRegistry.getInstance().registerUpgrade(tBackChestProtectionRight);

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
            for (ArmorMaterialMedieval tMaterial: MedievalRegistry.getInstance().getArmorMaterials().values())
            {
                ArmorUpgradeMedieval tFrontLeggingsProtectionLeft = new ArmorUpgradeMedieval(InternalNames.Upgrades.Leggings.FRONTLEFT, InternalNames.Armor.MEDIEVALLEGGINGS, InternalNames.AddonPositions.Leggings.FRONTLEFT, tMaterial.iInternalName, "Front leg protection left", "", 1.5F, 125, 1);
                ArmorUpgradeMedieval tFrontLeggingsProtectionRight = new ArmorUpgradeMedieval(InternalNames.Upgrades.Leggings.FRONTRIGHT, InternalNames.Armor.MEDIEVALLEGGINGS, InternalNames.AddonPositions.Leggings.FRONTRIGHT, tMaterial.iInternalName, "Front leg protection right", "", 1.5F, 125, 1);
                MedievalRegistry.getInstance().registerUpgrade(tFrontLeggingsProtectionLeft);
                MedievalRegistry.getInstance().registerUpgrade(tFrontLeggingsProtectionRight);

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
            for (ArmorMaterialMedieval tMaterial: MedievalRegistry.getInstance().getArmorMaterials().values())
            {
                ArmorUpgradeMedieval tBackLeggingsProtectionLeft = new ArmorUpgradeMedieval(InternalNames.Upgrades.Leggings.BACKLEFT, InternalNames.Armor.MEDIEVALLEGGINGS, InternalNames.AddonPositions.Leggings.BACKLEFT, tMaterial.iInternalName, "Back leg protection left", "", 2F, 150, 1);
                ArmorUpgradeMedieval tBackLeggingsProtectionRight = new ArmorUpgradeMedieval(InternalNames.Upgrades.Leggings.BACKRIGHT, InternalNames.Armor.MEDIEVALLEGGINGS, InternalNames.AddonPositions.Leggings.BACKRIGHT, tMaterial.iInternalName, "Back leg protection right", "", 2F, 150, 1);
                MedievalRegistry.getInstance().registerUpgrade(tBackLeggingsProtectionLeft);
                MedievalRegistry.getInstance().registerUpgrade(tBackLeggingsProtectionRight);

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
            for (ArmorMaterialMedieval tMaterial: MedievalRegistry.getInstance().getArmorMaterials().values())
            {
                ArmorUpgradeMedieval tShoeProtectionLeft = new ArmorUpgradeMedieval(InternalNames.Upgrades.Shoes.LEFT, InternalNames.Armor.MEDIEVALSHOES, InternalNames.AddonPositions.Shoes.LEFT, tMaterial.iInternalName, "Shoe protection left", "", 1F, 50, 1);
                ArmorUpgradeMedieval tShoeProtectionRight = new ArmorUpgradeMedieval(InternalNames.Upgrades.Shoes.RIGHT, InternalNames.Armor.MEDIEVALSHOES, InternalNames.AddonPositions.Shoes.RIGHT, tMaterial.iInternalName, "Shoe protection right", "", 1F, 50, 1);
                MedievalRegistry.getInstance().registerUpgrade(tShoeProtectionLeft);
                MedievalRegistry.getInstance().registerUpgrade(tShoeProtectionRight);

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
            for(ArmorMaterialMedieval tMaterial   : MedievalRegistry.getInstance().getArmorMaterials().values()) {
                if (tMaterial.iInternalName.equals(InternalNames.Materials.Vanilla.IRON)) {
                    tMaterial.setBaseDamageAbsorption(InternalNames.Armor.MEDIEVALHELMET, 1.5F);
                    tMaterial.setBaseDurability(InternalNames.Armor.MEDIEVALHELMET, 50);
                    tMaterial.setMaxModifiersOnPart(InternalNames.Armor.MEDIEVALHELMET, 1);
                } else if (tMaterial.iInternalName.equals(InternalNames.Materials.Vanilla.CHAIN)) {
                    tMaterial.setBaseDamageAbsorption(InternalNames.Armor.MEDIEVALHELMET, 2.0F);
                    tMaterial.setBaseDurability(InternalNames.Armor.MEDIEVALHELMET, 60);
                    tMaterial.setMaxModifiersOnPart(InternalNames.Armor.MEDIEVALHELMET, 1);
                } else if (tMaterial.iInternalName.equals(InternalNames.Materials.ModMaterials.TinkersConstruct.ALUMITE)) {
                    tMaterial.setBaseDamageAbsorption(InternalNames.Armor.MEDIEVALHELMET, 2.0F);
                    tMaterial.setBaseDurability(InternalNames.Armor.MEDIEVALHELMET, 100);
                    tMaterial.setMaxModifiersOnPart(InternalNames.Armor.MEDIEVALHELMET, 2);
                } else if (tMaterial.iInternalName.equals(InternalNames.Materials.Common.BRONZE)) {
                    tMaterial.setBaseDamageAbsorption(InternalNames.Armor.MEDIEVALHELMET, 1.0F);
                    tMaterial.setBaseDurability(InternalNames.Armor.MEDIEVALHELMET, 100);
                    tMaterial.setMaxModifiersOnPart(InternalNames.Armor.MEDIEVALHELMET, 0);
                } else if (tMaterial.iInternalName.equals(InternalNames.Materials.ModMaterials.TinkersConstruct.ARDITE)) {
                    tMaterial.setBaseDamageAbsorption(InternalNames.Armor.MEDIEVALHELMET, 2.5F);
                    tMaterial.setBaseDurability(InternalNames.Armor.MEDIEVALHELMET, 100);
                    tMaterial.setMaxModifiersOnPart(InternalNames.Armor.MEDIEVALHELMET, 2);
                } else if (tMaterial.iInternalName.equals(InternalNames.Materials.ModMaterials.TinkersConstruct.COBALT)) {
                    tMaterial.setBaseDamageAbsorption(InternalNames.Armor.MEDIEVALHELMET, 3F);
                    tMaterial.setBaseDurability(InternalNames.Armor.MEDIEVALHELMET, 140);
                    tMaterial.setMaxModifiersOnPart(InternalNames.Armor.MEDIEVALHELMET, 2);
                } else if (tMaterial.iInternalName.equals(InternalNames.Materials.Vanilla.OBSIDIAN)) {
                    tMaterial.setBaseDamageAbsorption(InternalNames.Armor.MEDIEVALHELMET, 3F);
                    tMaterial.setBaseDurability(InternalNames.Armor.MEDIEVALHELMET, 200);
                    tMaterial.setMaxModifiersOnPart(InternalNames.Armor.MEDIEVALHELMET, 2);
                } else if (tMaterial.iInternalName.equals(InternalNames.Materials.ModMaterials.TinkersConstruct.MANYULLUN)) {
                    tMaterial.setBaseDamageAbsorption(InternalNames.Armor.MEDIEVALHELMET, 3.5F);
                    tMaterial.setBaseDurability(InternalNames.Armor.MEDIEVALHELMET, 250);
                    tMaterial.setMaxModifiersOnPart(InternalNames.Armor.MEDIEVALHELMET, 3);
                } else {
                    MinecraftForge.EVENT_BUS.post(new ModifyMaterialEvent(InternalNames.Armor.MEDIEVALHELMET, tMaterial.iInternalName));
                }
            }

        }

        private static void modifyChestplate()
        {
            for(ArmorMaterialMedieval tMaterial   :MedievalRegistry.getInstance().getArmorMaterials().values()) {
                if (tMaterial.iInternalName.equals(InternalNames.Materials.Vanilla.IRON)) {
                    tMaterial.setBaseDamageAbsorption(InternalNames.Armor.MEDIEVALCHESTPLATE, 2.0F);
                    tMaterial.setBaseDurability(InternalNames.Armor.MEDIEVALCHESTPLATE, 50);
                    tMaterial.setMaxModifiersOnPart(InternalNames.Armor.MEDIEVALCHESTPLATE, 1);
                } else if (tMaterial.iInternalName.equals(InternalNames.Materials.Vanilla.CHAIN)) {
                    tMaterial.setBaseDamageAbsorption(InternalNames.Armor.MEDIEVALCHESTPLATE, 2.5F);
                    tMaterial.setBaseDurability(InternalNames.Armor.MEDIEVALCHESTPLATE, 60);
                    tMaterial.setMaxModifiersOnPart(InternalNames.Armor.MEDIEVALCHESTPLATE, 1);
                } else if (tMaterial.iInternalName.equals(InternalNames.Materials.ModMaterials.TinkersConstruct.ALUMITE)) {
                    tMaterial.setBaseDamageAbsorption(InternalNames.Armor.MEDIEVALCHESTPLATE, 2.5F);
                    tMaterial.setBaseDurability(InternalNames.Armor.MEDIEVALCHESTPLATE, 100);
                    tMaterial.setMaxModifiersOnPart(InternalNames.Armor.MEDIEVALCHESTPLATE, 2);
                } else if (tMaterial.iInternalName.equals(InternalNames.Materials.Common.BRONZE)) {
                    tMaterial.setBaseDamageAbsorption(InternalNames.Armor.MEDIEVALCHESTPLATE, 1.5F);
                    tMaterial.setBaseDurability(InternalNames.Armor.MEDIEVALCHESTPLATE, 100);
                    tMaterial.setMaxModifiersOnPart(InternalNames.Armor.MEDIEVALCHESTPLATE, 0);
                } else if (tMaterial.iInternalName.equals(InternalNames.Materials.ModMaterials.TinkersConstruct.ARDITE)) {
                    tMaterial.setBaseDamageAbsorption(InternalNames.Armor.MEDIEVALCHESTPLATE, 3.0F);
                    tMaterial.setBaseDurability(InternalNames.Armor.MEDIEVALCHESTPLATE, 100);
                    tMaterial.setMaxModifiersOnPart(InternalNames.Armor.MEDIEVALCHESTPLATE, 2);
                } else if (tMaterial.iInternalName.equals(InternalNames.Materials.ModMaterials.TinkersConstruct.COBALT)) {
                    tMaterial.setBaseDamageAbsorption(InternalNames.Armor.MEDIEVALCHESTPLATE, 3.5F);
                    tMaterial.setBaseDurability(InternalNames.Armor.MEDIEVALCHESTPLATE, 140);
                    tMaterial.setMaxModifiersOnPart(InternalNames.Armor.MEDIEVALCHESTPLATE, 2);
                } else if (tMaterial.iInternalName.equals(InternalNames.Materials.Vanilla.OBSIDIAN)) {
                    tMaterial.setBaseDamageAbsorption(InternalNames.Armor.MEDIEVALCHESTPLATE, 3.5F);
                    tMaterial.setBaseDurability(InternalNames.Armor.MEDIEVALCHESTPLATE, 200);
                    tMaterial.setMaxModifiersOnPart(InternalNames.Armor.MEDIEVALCHESTPLATE, 2);
                } else if (tMaterial.iInternalName.equals(InternalNames.Materials.ModMaterials.TinkersConstruct.MANYULLUN)) {
                    tMaterial.setBaseDamageAbsorption(InternalNames.Armor.MEDIEVALCHESTPLATE, 4.0F);
                    tMaterial.setBaseDurability(InternalNames.Armor.MEDIEVALCHESTPLATE, 250);
                    tMaterial.setMaxModifiersOnPart(InternalNames.Armor.MEDIEVALCHESTPLATE, 3);
                } else {
                    MinecraftForge.EVENT_BUS.post(new ModifyMaterialEvent(InternalNames.Armor.MEDIEVALCHESTPLATE, tMaterial.iInternalName));
                }
            }
        }

        private static void modifyLeggings()
        {
            for(ArmorMaterialMedieval tMaterial   :MedievalRegistry.getInstance().getArmorMaterials().values()) {
                if (tMaterial.iInternalName.equals(InternalNames.Materials.Vanilla.IRON)) {
                    tMaterial.setBaseDamageAbsorption(InternalNames.Armor.MEDIEVALLEGGINGS, 1.5F);
                    tMaterial.setBaseDurability(InternalNames.Armor.MEDIEVALLEGGINGS, 50);
                    tMaterial.setMaxModifiersOnPart(InternalNames.Armor.MEDIEVALLEGGINGS, 1);
                } else if (tMaterial.iInternalName.equals(InternalNames.Materials.Vanilla.CHAIN)) {
                    tMaterial.setBaseDamageAbsorption(InternalNames.Armor.MEDIEVALLEGGINGS, 2.0F);
                    tMaterial.setBaseDurability(InternalNames.Armor.MEDIEVALLEGGINGS, 60);
                    tMaterial.setMaxModifiersOnPart(InternalNames.Armor.MEDIEVALLEGGINGS, 1);
                } else if (tMaterial.iInternalName.equals(InternalNames.Materials.ModMaterials.TinkersConstruct.ALUMITE)) {
                    tMaterial.setBaseDamageAbsorption(InternalNames.Armor.MEDIEVALLEGGINGS, 2.0F);
                    tMaterial.setBaseDurability(InternalNames.Armor.MEDIEVALLEGGINGS, 100);
                    tMaterial.setMaxModifiersOnPart(InternalNames.Armor.MEDIEVALLEGGINGS, 2);
                } else if (tMaterial.iInternalName.equals(InternalNames.Materials.Common.BRONZE)) {
                    tMaterial.setBaseDamageAbsorption(InternalNames.Armor.MEDIEVALLEGGINGS, 1.0F);
                    tMaterial.setBaseDurability(InternalNames.Armor.MEDIEVALLEGGINGS, 100);
                    tMaterial.setMaxModifiersOnPart(InternalNames.Armor.MEDIEVALLEGGINGS, 0);
                } else if (tMaterial.iInternalName.equals(InternalNames.Materials.ModMaterials.TinkersConstruct.ARDITE)) {
                    tMaterial.setBaseDamageAbsorption(InternalNames.Armor.MEDIEVALLEGGINGS, 2.5F);
                    tMaterial.setBaseDurability(InternalNames.Armor.MEDIEVALLEGGINGS, 100);
                    tMaterial.setMaxModifiersOnPart(InternalNames.Armor.MEDIEVALLEGGINGS, 2);
                } else if (tMaterial.iInternalName.equals(InternalNames.Materials.ModMaterials.TinkersConstruct.COBALT)) {
                    tMaterial.setBaseDamageAbsorption(InternalNames.Armor.MEDIEVALLEGGINGS, 3F);
                    tMaterial.setBaseDurability(InternalNames.Armor.MEDIEVALLEGGINGS, 140);
                    tMaterial.setMaxModifiersOnPart(InternalNames.Armor.MEDIEVALLEGGINGS, 2);
                } else if (tMaterial.iInternalName.equals(InternalNames.Materials.Vanilla.OBSIDIAN)) {
                    tMaterial.setBaseDamageAbsorption(InternalNames.Armor.MEDIEVALLEGGINGS, 3F);
                    tMaterial.setBaseDurability(InternalNames.Armor.MEDIEVALLEGGINGS, 200);
                    tMaterial.setMaxModifiersOnPart(InternalNames.Armor.MEDIEVALLEGGINGS, 2);
                } else if (tMaterial.iInternalName.equals(InternalNames.Materials.ModMaterials.TinkersConstruct.MANYULLUN)) {
                    tMaterial.setBaseDamageAbsorption(InternalNames.Armor.MEDIEVALLEGGINGS, 3.5F);
                    tMaterial.setBaseDurability(InternalNames.Armor.MEDIEVALLEGGINGS, 250);
                    tMaterial.setMaxModifiersOnPart(InternalNames.Armor.MEDIEVALLEGGINGS, 3);
                } else {
                    MinecraftForge.EVENT_BUS.post(new ModifyMaterialEvent(InternalNames.Armor.MEDIEVALLEGGINGS, tMaterial.iInternalName));
                }
            }
        }

        private static void modifyShoes()
        {
            for(ArmorMaterialMedieval tMaterial   : MedievalRegistry.getInstance().getArmorMaterials().values()) {
                if (tMaterial.iInternalName.equals(InternalNames.Materials.Vanilla.IRON)) {
                    tMaterial.setBaseDamageAbsorption(InternalNames.Armor.MEDIEVALSHOES, 1.0F);
                    tMaterial.setBaseDurability(InternalNames.Armor.MEDIEVALSHOES, 50);
                    tMaterial.setMaxModifiersOnPart(InternalNames.Armor.MEDIEVALSHOES, 1);
                } else if (tMaterial.iInternalName.equals(InternalNames.Materials.Vanilla.CHAIN)) {
                    tMaterial.setBaseDamageAbsorption(InternalNames.Armor.MEDIEVALSHOES, 1.5F);
                    tMaterial.setBaseDurability(InternalNames.Armor.MEDIEVALSHOES, 60);
                    tMaterial.setMaxModifiersOnPart(InternalNames.Armor.MEDIEVALSHOES, 1);
                } else if (tMaterial.iInternalName.equals(InternalNames.Materials.ModMaterials.TinkersConstruct.ALUMITE)) {
                    tMaterial.setBaseDamageAbsorption(InternalNames.Armor.MEDIEVALSHOES, 1.5F);
                    tMaterial.setBaseDurability(InternalNames.Armor.MEDIEVALSHOES, 100);
                    tMaterial.setMaxModifiersOnPart(InternalNames.Armor.MEDIEVALSHOES, 2);
                } else if (tMaterial.iInternalName.equals(InternalNames.Materials.Common.BRONZE)) {
                    tMaterial.setBaseDamageAbsorption(InternalNames.Armor.MEDIEVALSHOES, 0.5F);
                    tMaterial.setBaseDurability(InternalNames.Armor.MEDIEVALSHOES, 100);
                    tMaterial.setMaxModifiersOnPart(InternalNames.Armor.MEDIEVALSHOES, 0);
                } else if (tMaterial.iInternalName.equals(InternalNames.Materials.ModMaterials.TinkersConstruct.ARDITE)) {
                    tMaterial.setBaseDamageAbsorption(InternalNames.Armor.MEDIEVALSHOES, 2.0F);
                    tMaterial.setBaseDurability(InternalNames.Armor.MEDIEVALSHOES, 100);
                    tMaterial.setMaxModifiersOnPart(InternalNames.Armor.MEDIEVALSHOES, 2);
                } else if (tMaterial.iInternalName.equals(InternalNames.Materials.ModMaterials.TinkersConstruct.COBALT)) {
                    tMaterial.setBaseDamageAbsorption(InternalNames.Armor.MEDIEVALSHOES, 2.5F);
                    tMaterial.setBaseDurability(InternalNames.Armor.MEDIEVALSHOES, 140);
                    tMaterial.setMaxModifiersOnPart(InternalNames.Armor.MEDIEVALSHOES, 2);
                } else if (tMaterial.iInternalName.equals(InternalNames.Materials.Vanilla.OBSIDIAN)) {
                    tMaterial.setBaseDamageAbsorption(InternalNames.Armor.MEDIEVALSHOES, 2.5F);
                    tMaterial.setBaseDurability(InternalNames.Armor.MEDIEVALSHOES, 200);
                    tMaterial.setMaxModifiersOnPart(InternalNames.Armor.MEDIEVALSHOES, 2);
                } else if (tMaterial.iInternalName.equals(InternalNames.Materials.ModMaterials.TinkersConstruct.MANYULLUN)) {
                    tMaterial.setBaseDamageAbsorption(InternalNames.Armor.MEDIEVALSHOES, 3.0F);
                    tMaterial.setBaseDurability(InternalNames.Armor.MEDIEVALSHOES, 250);
                    tMaterial.setMaxModifiersOnPart(InternalNames.Armor.MEDIEVALSHOES, 3);
                } else {
                    MinecraftForge.EVENT_BUS.post(new ModifyMaterialEvent(InternalNames.Armor.MEDIEVALSHOES, tMaterial.iInternalName));
                }
            }
        }

        public static void prepareGame()
        {
            initializeAnvilRecipes();

            GameRegistry.addRecipe(new ChainCraftingRecipe());
            GameRegistry.addRecipe(new MedievalArmorCraftingRecipe());

            GameRegistry.addShapedRecipe(new ItemStack(GeneralRegistry.Blocks.iBlockFirePit, 1), "#=#", "#/#", "###", '#', new ItemStack(Items.iron_ingot, 1), '=', new ItemStack(Items.cauldron, 1), '/', new ItemStack(Blocks.furnace, 1));
        }


        public static void initializeAnvilRecipes()
        {
            for (ArmorMaterialMedieval tMaterial : MedievalRegistry.getInstance().getArmorMaterials().values())
            {
                ItemStack tRingStack = new ItemStack(GeneralRegistry.Items.iMetalRing, 1);
                NBTTagCompound pRingCompound = new NBTTagCompound();
                pRingCompound.setString(References.NBTTagCompoundData.Material, tMaterial.iInternalName);
                tRingStack.setTagCompound(pRingCompound);

                AnvilRecipe tRingRecipe = new AnvilRecipe().setCraftingSlotContent(0, (new HeatedAnvilRecipeComponent(tMaterial.iInternalName, InternalNames.HeatedItemTypes.NUGGET, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.95F)))
                        .setProgress(9).setResult(tRingStack).setHammerUsage(4).setTongUsage(1).setShapeLess();

                TileEntityArmorsAnvil.addRecipe(tRingRecipe);

                ItemStack tPlateStack = new ItemStack(GeneralRegistry.Items.iPlate, 1);
                NBTTagCompound pPlateCompound = new NBTTagCompound();
                pPlateCompound.setString(References.NBTTagCompoundData.Material, tMaterial.iInternalName);
                tPlateStack.setTagCompound(pPlateCompound);

                AnvilRecipe tPlateRecipe = new AnvilRecipe().setCraftingSlotContent(0, (new HeatedAnvilRecipeComponent(tMaterial.iInternalName, InternalNames.HeatedItemTypes.INGOT, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.95F)))
                        .setProgress(15).setResult(tPlateStack).setHammerUsage(15).setTongUsage(2).setShapeLess();

                TileEntityArmorsAnvil.addRecipe(tPlateRecipe);

                ItemStack tNuggetStack = new ItemStack(GeneralRegistry.Items.iNugget, 9);
                NBTTagCompound pNuggetCompound = new NBTTagCompound();
                pNuggetCompound.setString(References.NBTTagCompoundData.Material, tMaterial.iInternalName);
                tNuggetStack.setTagCompound(pNuggetCompound);

                AnvilRecipe tNuggetRecipe = new AnvilRecipe().setCraftingSlotContent(0, (new HeatedAnvilRecipeComponent(tMaterial.iInternalName, InternalNames.HeatedItemTypes.INGOT, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.5F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.5F) * 0.95F)))
                        .setProgress(6).setResult(tNuggetStack).setHammerUsage(4).setTongUsage(0).setShapeLess();

                TileEntityArmorsAnvil.addRecipe(tNuggetRecipe);

                ItemStack tChainStack = new ItemStack(GeneralRegistry.Items.iMetalChain, 1);
                NBTTagCompound tChainCompound = new NBTTagCompound();
                tChainCompound.setString(References.NBTTagCompoundData.Material, tMaterial.iInternalName);
                tChainStack.setTagCompound(tChainCompound);

                AnvilRecipe tChainRecipe = new AnvilRecipe().setCraftingSlotContent(2, (new HeatedAnvilRecipeComponent(tMaterial.iInternalName, InternalNames.HeatedItemTypes.RING, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(6, (new HeatedAnvilRecipeComponent(tMaterial.iInternalName, InternalNames.HeatedItemTypes.RING, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(8, (new HeatedAnvilRecipeComponent(tMaterial.iInternalName, InternalNames.HeatedItemTypes.RING, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(10, (new HeatedAnvilRecipeComponent(tMaterial.iInternalName, InternalNames.HeatedItemTypes.RING, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(12, (new HeatedAnvilRecipeComponent(tMaterial.iInternalName, InternalNames.HeatedItemTypes.RING, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(14, (new HeatedAnvilRecipeComponent(tMaterial.iInternalName, InternalNames.HeatedItemTypes.RING, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(16, (new HeatedAnvilRecipeComponent(tMaterial.iInternalName, InternalNames.HeatedItemTypes.RING, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(18, (new HeatedAnvilRecipeComponent(tMaterial.iInternalName, InternalNames.HeatedItemTypes.RING, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(22, (new HeatedAnvilRecipeComponent(tMaterial.iInternalName, InternalNames.HeatedItemTypes.RING, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.95F)))
                        .setProgress(10).setResult(tChainStack).setHammerUsage(16).setTongUsage(16);

                TileEntityArmorsAnvil.addRecipe(tChainRecipe);

                ItemStack tChestplateStack = MedievalArmorFactory.getInstance().buildNewMLAArmor(MedievalRegistry.getInstance().getArmor(InternalNames.Armor.MEDIEVALCHESTPLATE), new HashMap<MLAAddon, Integer>(), tMaterial.getBaseDurability(InternalNames.Armor.MEDIEVALCHESTPLATE), tMaterial.iInternalName);

                AnvilRecipe tChestplateRecipe = new AnvilRecipe().setCraftingSlotContent(0, (new HeatedAnvilRecipeComponent(tMaterial.iInternalName, InternalNames.HeatedItemTypes.CHAIN, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(4, (new HeatedAnvilRecipeComponent(tMaterial.iInternalName, InternalNames.HeatedItemTypes.CHAIN, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(5, (new HeatedAnvilRecipeComponent(tMaterial.iInternalName, InternalNames.HeatedItemTypes.CHAIN, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(6, (new HeatedAnvilRecipeComponent(tMaterial.iInternalName, InternalNames.HeatedItemTypes.RING, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(8, (new HeatedAnvilRecipeComponent(tMaterial.iInternalName, InternalNames.HeatedItemTypes.RING, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(9, (new HeatedAnvilRecipeComponent(tMaterial.iInternalName, InternalNames.HeatedItemTypes.CHAIN, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(10, (new HeatedAnvilRecipeComponent(tMaterial.iInternalName, InternalNames.HeatedItemTypes.CHAIN, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(11, (new HeatedAnvilRecipeComponent(tMaterial.iInternalName, InternalNames.HeatedItemTypes.RING, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(12, (new HeatedAnvilRecipeComponent(tMaterial.iInternalName, InternalNames.HeatedItemTypes.CHAIN, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(13, (new HeatedAnvilRecipeComponent(tMaterial.iInternalName, InternalNames.HeatedItemTypes.RING, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(14, (new HeatedAnvilRecipeComponent(tMaterial.iInternalName, InternalNames.HeatedItemTypes.CHAIN, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(15, (new HeatedAnvilRecipeComponent(tMaterial.iInternalName, InternalNames.HeatedItemTypes.CHAIN, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(16, (new HeatedAnvilRecipeComponent(tMaterial.iInternalName, InternalNames.HeatedItemTypes.RING, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(17, (new HeatedAnvilRecipeComponent(tMaterial.iInternalName, InternalNames.HeatedItemTypes.CHAIN, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(18, (new HeatedAnvilRecipeComponent(tMaterial.iInternalName, InternalNames.HeatedItemTypes.RING, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(19, (new HeatedAnvilRecipeComponent(tMaterial.iInternalName, InternalNames.HeatedItemTypes.CHAIN, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(20, (new HeatedAnvilRecipeComponent(tMaterial.iInternalName, InternalNames.HeatedItemTypes.CHAIN, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(21, (new HeatedAnvilRecipeComponent(tMaterial.iInternalName, InternalNames.HeatedItemTypes.RING, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(22, (new HeatedAnvilRecipeComponent(tMaterial.iInternalName, InternalNames.HeatedItemTypes.CHAIN, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(23, (new HeatedAnvilRecipeComponent(tMaterial.iInternalName, InternalNames.HeatedItemTypes.RING, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.95F)))
                        .setCraftingSlotContent(24, (new HeatedAnvilRecipeComponent(tMaterial.iInternalName, InternalNames.HeatedItemTypes.CHAIN, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.85F, (HeatedItemFactory.getInstance().getMeltingPointFromMaterial(tMaterial.iInternalName) * 0.35F) * 0.95F)))
                        .setProgress(20).setResult(tChestplateStack).setHammerUsage(38).setTongUsage(24);

                TileEntityArmorsAnvil.addRecipe(tChestplateRecipe);
            }
        }


        /*
        public static void initializeAnvilRecipes()
        {
            ItemStack tNuggetStack = new ItemStack(GeneralRegistry.Items.iNugget, 1);
            NBTTagCompound pRingCompound = new NBTTagCompound();
            pRingCompound.setString(References.NBTTagCompoundData.Material, InternalNames.Materials.Vanilla.IRON);
            tNuggetStack.setTagCompound(pRingCompound);

            AnvilRecipe tRingRecipe = new AnvilRecipe().setCraftingSlotContent(0, (new HeatedAnvilRecipeComponent(InternalNames.Materials.Vanilla.IRON, InternalNames.HeatedItemTypes.INGOT, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.85F, HeatedItemFactory.getInstance().getMeltingPointFromMaterial(InternalNames.Materials.Vanilla.IRON) * 0.5F * 0.95F)))
                    .setProgress(9).setResult(tNuggetStack).setHammerUsage(4).setTongUsage(1).setShapeLess();

            TileEntityArmorsAnvil.addRecipe(tRingRecipe);
        }
        */
    }

    public static class SystemInit
    {
        public static void RegisterBlocks()
        {
            GeneralRegistry.Blocks.iBlockFirePit = (BlockFirePit) new BlockFirePit().setHardness(1F).setStepSound(Block.soundTypeMetal)
                    .setBlockName(InternalNames.Blocks.FirePit).setCreativeTab(GeneralRegistry.iTabArmoryComponents).setBlockTextureName(TextureAddressHelper.getTextureAddress("DarkSteelPartical"));

            GeneralRegistry.Blocks.iBlockHeater = (BlockHeater) new BlockHeater().setHardness(1F).setStepSound(Block.soundTypeMetal)
                    .setBlockName(InternalNames.Blocks.Heater).setCreativeTab(GeneralRegistry.iTabArmoryComponents).setBlockTextureName(TextureAddressHelper.getTextureAddress("DarkSteelPartical"));

            GeneralRegistry.Blocks.iBlockAnvil = (BlockArmorsAnvil) new BlockArmorsAnvil().setHardness(1F).setStepSound(Block.soundTypeAnvil)
                    .setBlockName(InternalNames.Blocks.ArmorsAnvil).setCreativeTab(GeneralRegistry.iTabArmoryComponents).setBlockTextureName(TextureAddressHelper.getTextureAddress("DarkSteelPartical"));


            GameRegistry.registerBlock(GeneralRegistry.Blocks.iBlockFirePit, InternalNames.Blocks.FirePit);
            GameRegistry.registerBlock(GeneralRegistry.Blocks.iBlockHeater, InternalNames.Blocks.Heater);
            GameRegistry.registerBlock(GeneralRegistry.Blocks.iBlockAnvil, InternalNames.Blocks.ArmorsAnvil);
        }

        public static void RegisterItems()
        {
            GeneralRegistry.Items.iMetalChain = new ItemMetalChain();
            GeneralRegistry.Items.iMetalRing = new ItemMetalRing();
            GeneralRegistry.Items.iHeatedIngot = new ItemHeatedItem();
            GeneralRegistry.Items.iNugget = new ItemNugget();
            GeneralRegistry.Items.iPlate = new ItemPlate();
            GeneralRegistry.Items.iFan = new ItemFan();
            GeneralRegistry.Items.iHammer = new ItemHammer();
            GeneralRegistry.Items.iTongs = new ItemTongs();

            for(MultiLayeredArmor tCore: MedievalRegistry.getInstance().getAllRegisteredArmors().values())
            {
                GameRegistry.registerItem(tCore, tCore.getInternalName());
            }

            GameRegistry.registerItem(GeneralRegistry.Items.iMetalChain, InternalNames.Items.ItemMetalChain);
            GameRegistry.registerItem(GeneralRegistry.Items.iMetalRing, InternalNames.Items.ItemMetalRing);
            GameRegistry.registerItem(GeneralRegistry.Items.iHeatedIngot, InternalNames.Items.ItemHeatedIngot);
            GameRegistry.registerItem(GeneralRegistry.Items.iNugget, InternalNames.Items.ItemNugget);
            GameRegistry.registerItem(GeneralRegistry.Items.iPlate, InternalNames.Items.ItemPlate);
            GameRegistry.registerItem(GeneralRegistry.Items.iFan, InternalNames.Items.ItemFan);
            GameRegistry.registerItem(GeneralRegistry.Items.iHammer, InternalNames.Items.ItemHammer);
            GameRegistry.registerItem(GeneralRegistry.Items.iTongs, InternalNames.Items.ItemTongs);
        }

        public static void RegisterTileEntities()
        {
            GameRegistry.registerTileEntity(TileEntityFirePit.class, InternalNames.TileEntities.FirePitContainer);
            GameRegistry.registerTileEntity(TileEntityHeater.class, InternalNames.TileEntities.HeaterComponent);
            GameRegistry.registerTileEntity(TileEntityArmorsAnvil.class, InternalNames.TileEntities.ArmorsAnvil);
        }

        public static void removeRecipes()
        {
            ListIterator<IRecipe> iterator = CraftingManager.getInstance().getRecipeList().listIterator();
            while (iterator.hasNext())
            {
                IRecipe r = iterator.next();
                int[] tOreID = OreDictionary.getOreIDs(r.getRecipeOutput());

                for (int tID: tOreID)
                {
                    String pOreDicID = OreDictionary.getOreName(tID);
                    if (pOreDicID.contains("nugget"))
                    {
                        for(ArmorMaterialMedieval tMaterial : MedievalRegistry.getInstance().getArmorMaterials().values())
                        {
                            if (pOreDicID.toLowerCase().contains(tMaterial.iOreDicName.toLowerCase()))
                            {
                                iterator.remove();
                            }
                        }
                    }
                }
            }
        }
    }

}
