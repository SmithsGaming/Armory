package com.smithsmodding.armory.common.logic;
/*
 *   ArmoryInitializer
 *   Created by: Orion
 *   Created on: 17-9-2014
 */

import com.smithsmodding.armory.api.armor.ArmorAddonPosition;
import com.smithsmodding.armory.api.armor.MLAAddon;
import com.smithsmodding.armory.api.armor.MultiLayeredArmor;
import com.smithsmodding.armory.api.crafting.blacksmiths.component.StandardAnvilRecipeComponent;
import com.smithsmodding.armory.api.crafting.blacksmiths.recipe.AnvilRecipe;
import com.smithsmodding.armory.api.materials.IAnvilMaterial;
import com.smithsmodding.armory.api.materials.IArmorMaterial;
import com.smithsmodding.armory.api.util.client.TranslationKeys;
import com.smithsmodding.armory.api.util.references.*;
import com.smithsmodding.armory.common.addons.ArmorUpgradeMedieval;
import com.smithsmodding.armory.common.anvil.AnvilMaterial;
import com.smithsmodding.armory.common.block.*;
import com.smithsmodding.armory.common.config.ArmorDataConfigHandler;
import com.smithsmodding.armory.common.config.ArmoryConfig;
import com.smithsmodding.armory.common.crafting.blacksmiths.component.HeatedAnvilRecipeComponent;
import com.smithsmodding.armory.common.crafting.blacksmiths.component.OreDicAnvilRecipeComponent;
import com.smithsmodding.armory.common.crafting.blacksmiths.recipe.ArmorUpgradeAnvilRecipe;
import com.smithsmodding.armory.common.creativetabs.ArmorTab;
import com.smithsmodding.armory.common.creativetabs.ComponentsTab;
import com.smithsmodding.armory.common.creativetabs.GeneralTabs;
import com.smithsmodding.armory.common.creativetabs.HeatedItemTab;
import com.smithsmodding.armory.common.factory.MedievalArmorFactory;
import com.smithsmodding.armory.common.fluid.FluidMoltenMetal;
import com.smithsmodding.armory.common.item.*;
import com.smithsmodding.armory.common.item.armor.tiermedieval.ArmorMedieval;
import com.smithsmodding.armory.common.item.block.ItemBlockBlackSmithsAnvil;
import com.smithsmodding.armory.common.logic.material.IronMaterialInitializer;
import com.smithsmodding.armory.common.logic.material.ObsidianMaterialInitializer;
import com.smithsmodding.armory.common.material.ArmorMaterial;
import com.smithsmodding.armory.common.material.fluidmodifiers.ObsidianToLavaSetter;
import com.smithsmodding.armory.common.registry.*;
import com.smithsmodding.armory.common.tileentity.*;
import com.smithsmodding.smithscore.SmithsCore;
import com.smithsmodding.smithscore.util.common.ItemStackHelper;
import net.minecraft.client.resources.I18n;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.item.crafting.CraftingManager;
import net.minecraft.item.crafting.IRecipe;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.fluids.FluidRegistry;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.ListIterator;

public class ArmoryInitializer {
    public static void InitializeServer() {
        SystemInit.registerCreativeTabs();
        SystemInit.registerFluids();
        MedievalInitialization.preInitialize();
        GlobalInitialization.registerAnvilMaterials();
        SystemInit.registerBlocks();
        SystemInit.registerItems();
        SystemInit.registerTileEntities();
    }

    public static void postInitializeServer() {

    }

    public static void onLoadCompleted() {
        SystemInit.loadMaterialConfig();
        MedievalInitialization.prepareGame();
        SystemInit.removeRecipes();
        HeatableItemRegistry.getInstance().reloadOreDictionary();
        SystemInit.initializeOreDict();
    }

    public static class MedievalInitialization {
        public static void preInitialize() {
            registerArmorPieces();
            registerAddonPositions();
            registerMaterials();
        }

        public static void registerArmorPieces() {
            ArmorRegistry.getInstance().registerNewArmor(new ArmorMedieval(References.InternalNames.Armor.MEDIEVALHELMET, References.InternalNames.AddonPositions.Helmet.BASE, EntityEquipmentSlot.HEAD));
            ArmorRegistry.getInstance().registerNewArmor(new ArmorMedieval(References.InternalNames.Armor.MEDIEVALCHESTPLATE, References.InternalNames.AddonPositions.Chestplate.BASE, EntityEquipmentSlot.CHEST));
            ArmorRegistry.getInstance().registerNewArmor(new ArmorMedieval(References.InternalNames.Armor.MEDIEVALLEGGINGS, References.InternalNames.AddonPositions.Leggings.BASE, EntityEquipmentSlot.LEGS));
            ArmorRegistry.getInstance().registerNewArmor(new ArmorMedieval(References.InternalNames.Armor.MEDIEVALSHOES, References.InternalNames.AddonPositions.Shoes.BASE, EntityEquipmentSlot.FEET));
        }

        public static void registerAddonPositions() {
            //Registering the positions to the helmet
            MultiLayeredArmor helmet = ArmorRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALHELMET);
            helmet.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Helmet.BASE, References.InternalNames.Armor.MEDIEVALHELMET, 1));
            helmet.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Helmet.TOP, References.InternalNames.Armor.MEDIEVALHELMET, 1));
            helmet.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Helmet.LEFT, References.InternalNames.Armor.MEDIEVALHELMET, 1));
            helmet.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Helmet.RIGHT, References.InternalNames.Armor.MEDIEVALHELMET, 1));
            helmet.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Helmet.AQUABREATHING, References.InternalNames.Armor.MEDIEVALHELMET, 1));
            helmet.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Helmet.NIGHTSIGHT, References.InternalNames.Armor.MEDIEVALHELMET, 1));
            helmet.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Helmet.THORNS, References.InternalNames.Armor.MEDIEVALHELMET, 1));
            helmet.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Helmet.REINFORCED, References.InternalNames.Armor.MEDIEVALHELMET, 1));
            helmet.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Helmet.AUTOREPAIR, References.InternalNames.Armor.MEDIEVALHELMET, 1));
            helmet.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Helmet.ELECTRIC, References.InternalNames.Armor.MEDIEVALHELMET, 1));

            //Registering the positions to the chestplate
            MultiLayeredArmor chestplate = ArmorRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALCHESTPLATE);
            chestplate.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Chestplate.BASE, References.InternalNames.Armor.MEDIEVALCHESTPLATE, 1));
            chestplate.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Chestplate.SHOULDERLEFT, References.InternalNames.Armor.MEDIEVALCHESTPLATE, 1));
            chestplate.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Chestplate.SHOULDERRIGHT, References.InternalNames.Armor.MEDIEVALCHESTPLATE, 1));
            chestplate.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Chestplate.FRONTLEFT, References.InternalNames.Armor.MEDIEVALCHESTPLATE, 1));
            chestplate.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Chestplate.FRONTRIGHT, References.InternalNames.Armor.MEDIEVALCHESTPLATE, 1));
            chestplate.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Chestplate.BACKLEFT, References.InternalNames.Armor.MEDIEVALCHESTPLATE, 1));
            chestplate.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Chestplate.BACKRIGHT, References.InternalNames.Armor.MEDIEVALCHESTPLATE, 1));
            chestplate.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Chestplate.STRENGTH, References.InternalNames.Armor.MEDIEVALCHESTPLATE, 1));
            chestplate.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Chestplate.HASTE, References.InternalNames.Armor.MEDIEVALCHESTPLATE, 1));
            chestplate.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Chestplate.FLYING, References.InternalNames.Armor.MEDIEVALCHESTPLATE, 1));
            chestplate.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Chestplate.THORNS, References.InternalNames.Armor.MEDIEVALCHESTPLATE, 1));
            chestplate.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Chestplate.REINFORCED, References.InternalNames.Armor.MEDIEVALCHESTPLATE, 1));
            chestplate.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Chestplate.AUTOREPAIR, References.InternalNames.Armor.MEDIEVALCHESTPLATE, 1));
            chestplate.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Chestplate.ELECTRIC, References.InternalNames.Armor.MEDIEVALCHESTPLATE, 1));

            //Registering the positions to the leggins
            MultiLayeredArmor leggings = ArmorRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALLEGGINGS);
            leggings.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Leggings.BASE, References.InternalNames.Armor.MEDIEVALLEGGINGS, 1));
            leggings.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Leggings.FRONTLEFT, References.InternalNames.Armor.MEDIEVALLEGGINGS, 1));
            leggings.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Leggings.FRONTRIGHT, References.InternalNames.Armor.MEDIEVALLEGGINGS, 1));
            leggings.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Leggings.BACKLEFT, References.InternalNames.Armor.MEDIEVALLEGGINGS, 1));
            leggings.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Leggings.BACKRIGHT, References.InternalNames.Armor.MEDIEVALLEGGINGS, 1));
            leggings.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Leggings.SPEED, References.InternalNames.Armor.MEDIEVALLEGGINGS, 1));
            leggings.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Leggings.JUMPASSIST, References.InternalNames.Armor.MEDIEVALLEGGINGS, 1));
            leggings.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Leggings.UPHILLASSIST, References.InternalNames.Armor.MEDIEVALLEGGINGS, 1));
            leggings.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Leggings.THORNS, References.InternalNames.Armor.MEDIEVALLEGGINGS, 1));
            leggings.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Leggings.REINFORCED, References.InternalNames.Armor.MEDIEVALLEGGINGS, 1));
            leggings.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Leggings.AUTOREPAIR, References.InternalNames.Armor.MEDIEVALLEGGINGS, 1));
            leggings.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Leggings.ELECTRIC, References.InternalNames.Armor.MEDIEVALLEGGINGS, 1));

            //Registering the positions to the shoes
            MultiLayeredArmor shoes = ArmorRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALSHOES);
            shoes.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Shoes.BASE, References.InternalNames.Armor.MEDIEVALSHOES, 1));
            shoes.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Shoes.LEFT, References.InternalNames.Armor.MEDIEVALSHOES, 1));
            shoes.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Shoes.RIGHT, References.InternalNames.Armor.MEDIEVALSHOES, 1));
            shoes.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Shoes.FALLASSIST, References.InternalNames.Armor.MEDIEVALSHOES, 1));
            shoes.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Shoes.SWIMASSIST, References.InternalNames.Armor.MEDIEVALSHOES, 1));
            shoes.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Shoes.REINFORCED, References.InternalNames.Armor.MEDIEVALSHOES, 1));
            shoes.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Shoes.AUTOREPAIR, References.InternalNames.Armor.MEDIEVALSHOES, 1));
            shoes.registerAddonPosition(new ArmorAddonPosition(References.InternalNames.AddonPositions.Shoes.ELECTRIC, References.InternalNames.Armor.MEDIEVALSHOES, 1));
        }

        public static void registerMaterials() {
            ArmorMaterial tIron = new ArmorMaterial(References.InternalNames.Materials.Vanilla.IRON, "Iron", true, 1865, 500, 0.225F, new ItemStack(Items.IRON_INGOT));
            ArmorMaterial tObsidian = new ArmorMaterial(References.InternalNames.Materials.Vanilla.OBSIDIAN, "Obsidian", true, 1404, 3000, 0.345F, new ItemStack(Item.getItemFromBlock(Blocks.OBSIDIAN)));

            MaterialRegistry.getInstance().registerMaterial(tIron, new IronMaterialInitializer());
            MaterialRegistry.getInstance().registerMaterial(tObsidian, new ObsidianMaterialInitializer());

            HeatableItemRegistry.getInstance().addBaseStack(tObsidian, new ItemStack(Blocks.OBSIDIAN), References.InternalNames.HeatedItemTypes.BLOCK, 9 * References.General.FLUID_INGOT);
            HeatableItemRegistry.getInstance().addBaseStack(tIron, new ItemStack(Blocks.IRON_BLOCK), References.InternalNames.HeatedItemTypes.BLOCK, 9 * References.General.FLUID_INGOT);
        }

        public static void prepareGame() {
            initializeAnvilRecipes();

            GameRegistry.addShapedRecipe(new ItemStack(ModItems.hammer, 1, 150), "  B", " S ", "S  ", 'B', new ItemStack(Blocks.IRON_BLOCK), 'S', new ItemStack(Items.STICK));
        }

        public static void initializeAnvilRecipes() {
            ItemStack fireplaceStack = new ItemStack(ModBlocks.blockFirePlace);
            AnvilRecipe fireplaceRecipe = new AnvilRecipe()
                    .setCraftingSlotContent(0, new OreDicAnvilRecipeComponent(new ItemStack(Blocks.COBBLESTONE)))
                    .setCraftingSlotContent(1, new OreDicAnvilRecipeComponent(new ItemStack(Blocks.COBBLESTONE)))
                    .setCraftingSlotContent(2, new OreDicAnvilRecipeComponent(new ItemStack(Blocks.COBBLESTONE)))
                    .setCraftingSlotContent(3, new OreDicAnvilRecipeComponent(new ItemStack(Blocks.COBBLESTONE)))
                    .setCraftingSlotContent(4, new OreDicAnvilRecipeComponent(new ItemStack(Blocks.COBBLESTONE)))
                    .setCraftingSlotContent(5, new OreDicAnvilRecipeComponent(new ItemStack(Blocks.COBBLESTONE)))
                    .setCraftingSlotContent(6, new OreDicAnvilRecipeComponent(new ItemStack(Items.STICK)))
                    .setCraftingSlotContent(8, new OreDicAnvilRecipeComponent(new ItemStack(Items.STICK)))
                    .setCraftingSlotContent(9, new OreDicAnvilRecipeComponent(new ItemStack(Blocks.COBBLESTONE)))
                    .setCraftingSlotContent(10, new OreDicAnvilRecipeComponent(new ItemStack(Blocks.COBBLESTONE)))
                    .setCraftingSlotContent(12, new OreDicAnvilRecipeComponent(new ItemStack(Items.STICK)))
                    .setCraftingSlotContent(14, new OreDicAnvilRecipeComponent(new ItemStack(Blocks.COBBLESTONE)))
                    .setCraftingSlotContent(15, new OreDicAnvilRecipeComponent(new ItemStack(Blocks.COBBLESTONE)))
                    .setCraftingSlotContent(16, new OreDicAnvilRecipeComponent(new ItemStack(Items.STICK)))
                    .setCraftingSlotContent(18, new OreDicAnvilRecipeComponent(new ItemStack(Items.STICK)))
                    .setCraftingSlotContent(19, new OreDicAnvilRecipeComponent(new ItemStack(Blocks.COBBLESTONE)))
                    .setCraftingSlotContent(20, new OreDicAnvilRecipeComponent(new ItemStack(Blocks.COBBLESTONE)))
                    .setCraftingSlotContent(21, new OreDicAnvilRecipeComponent(new ItemStack(Blocks.COBBLESTONE)))
                    .setCraftingSlotContent(22, new OreDicAnvilRecipeComponent(new ItemStack(Blocks.COBBLESTONE)))
                    .setCraftingSlotContent(23, new OreDicAnvilRecipeComponent(new ItemStack(Blocks.COBBLESTONE)))
                    .setCraftingSlotContent(24, new OreDicAnvilRecipeComponent(new ItemStack(Blocks.COBBLESTONE)))
                    .setHammerUsage(0).setTongUsage(0).setResult(fireplaceStack).setProgress(10);
            AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.FIREPLACE, fireplaceRecipe);

            ItemStack forgeStack = new ItemStack(ModBlocks.blockForge);
            AnvilRecipe forgeRecipe = new AnvilRecipe()
                    .setCraftingSlotContent(0, new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.INGOT, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.65F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.65F * 0.95F))
                    .setCraftingSlotContent(4, new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.INGOT, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.65F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.65F * 0.95F))
                    .setCraftingSlotContent(5, new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.INGOT, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.75F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.75F * 0.95F))
                    .setCraftingSlotContent(6, new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.PLATE, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.65F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.65F * 0.95F))
                    .setCraftingSlotContent(7, new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.PLATE, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.65F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.65F * 0.95F))
                    .setCraftingSlotContent(8, new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.PLATE, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.65F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.65F * 0.95F))
                    .setCraftingSlotContent(9, new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.INGOT, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.75F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.75F * 0.95F))
                    .setCraftingSlotContent(10, new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.INGOT, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.65F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.65F * 0.95F))
                    .setCraftingSlotContent(14, new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.INGOT, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.65F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.65F * 0.95F))
                    .setCraftingSlotContent(15, new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.INGOT, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.65F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.65F * 0.95F))
                    .setCraftingSlotContent(19, new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.INGOT, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.65F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.65F * 0.95F))
                    .setCraftingSlotContent(20, new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.INGOT, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.65F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.65F * 0.95F))
                    .setCraftingSlotContent(21, new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.INGOT, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.65F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.65F * 0.95F))
                    .setCraftingSlotContent(22, new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.INGOT, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.65F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.65F * 0.95F))
                    .setCraftingSlotContent(23, new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.INGOT, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.65F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.65F * 0.95F))
                    .setCraftingSlotContent(24, new HeatedAnvilRecipeComponent(References.InternalNames.Materials.Vanilla.IRON, References.InternalNames.HeatedItemTypes.INGOT, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.65F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.65F * 0.95F))
                    .setHammerUsage(15).setTongUsage(25).setResult(forgeStack).setProgress(60);
            AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.FORGE, forgeRecipe);

            ItemStack tHammerStack = new ItemStack(ModItems.hammer, 1);
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

            ItemStack tTongStack = new ItemStack(ModItems.tongs, 1);
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

            initializeAnvilCreationAnvilRecipes();
            initializeMedievalArmorAnvilRecipes();
            initializeMedievalUpgradeAnvilRecipes();
            initializeUpgradeRecipeSystem();
        }

        public static void initializeAnvilCreationAnvilRecipes() {
            for (IAnvilMaterial material : AnvilMaterialRegistry.getInstance().getAllRegisteredAnvilMaterials().values()) {
                AnvilRecipe recipe = material.getRecipeForAnvil();

                if (recipe == null)
                    continue;

                AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.ANVIL + "-" + material.getID(), recipe);
            }
        }

        public static void initializeMedievalArmorAnvilRecipes() {
            for (IArmorMaterial tMaterial : MaterialRegistry.getInstance().getArmorMaterials().values()) {
                ItemStack tRingStack = new ItemStack(ModItems.metalRing, 1, tMaterial.getItemDamageMaterialIndex());
                NBTTagCompound pRingCompound = new NBTTagCompound();
                pRingCompound.setString(References.NBTTagCompoundData.Material, tMaterial.getUniqueID());
                tRingStack.setTagCompound(pRingCompound);

                AnvilRecipe tRingRecipe = new AnvilRecipe().setCraftingSlotContent(0, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.65F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.75F)))
                        .setProgress(9).setResult(tRingStack).setHammerUsage(4).setTongUsage(0).setShapeLess();

                AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.RING + tMaterial.getOreDicName(), tRingRecipe);

                ItemStack tPlateStack = new ItemStack(ModItems.metalPlate, 1, tMaterial.getItemDamageMaterialIndex());
                NBTTagCompound pPlateCompound = new NBTTagCompound();
                pPlateCompound.setString(References.NBTTagCompoundData.Material, tMaterial.getUniqueID());
                tPlateStack.setTagCompound(pPlateCompound);

                AnvilRecipe tPlateRecipe = new AnvilRecipe().setCraftingSlotContent(0, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.INGOT, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.65F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.75F)))
                        .setProgress(15).setResult(tPlateStack).setHammerUsage(15).setTongUsage(2).setShapeLess();

                AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.PLATE + tMaterial.getOreDicName(), tPlateRecipe);

                ItemStack tNuggetStack = new ItemStack(ModItems.metalNugget, 9, tMaterial.getItemDamageMaterialIndex());
                NBTTagCompound pNuggetCompound = new NBTTagCompound();
                pNuggetCompound.setString(References.NBTTagCompoundData.Material, tMaterial.getUniqueID());
                tNuggetStack.setTagCompound(pNuggetCompound);

                AnvilRecipe tNuggetRecipe = new AnvilRecipe().setCraftingSlotContent(0, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.INGOT, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F) * 0.65F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F) * 0.75F)))
                        .setProgress(6).setResult(tNuggetStack).setHammerUsage(4).setTongUsage(0).setShapeLess();

                AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.NUGGET + tMaterial.getOreDicName(), tNuggetRecipe);

                ItemStack tChainStack = new ItemStack(ModItems.metalChain, 1, tMaterial.getItemDamageMaterialIndex());
                NBTTagCompound tChainCompound = new NBTTagCompound();
                tChainCompound.setString(References.NBTTagCompoundData.Material, tMaterial.getUniqueID());
                tChainStack.setTagCompound(tChainCompound);

                AnvilRecipe tChainRecipe = new AnvilRecipe().setCraftingSlotContent(2, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.65F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.75F)))
                        .setCraftingSlotContent(6, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.65F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.75F)))
                        .setCraftingSlotContent(8, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.65F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.75F)))
                        .setCraftingSlotContent(10, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.65F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.75F)))
                        .setCraftingSlotContent(12, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.65F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.75F)))
                        .setCraftingSlotContent(14, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.65F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.75F)))
                        .setCraftingSlotContent(16, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.65F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.75F)))
                        .setCraftingSlotContent(18, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.65F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.75F)))
                        .setCraftingSlotContent(22, (new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.65F, (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.35F) * 0.75F)))
                        .setProgress(10).setResult(tChainStack).setHammerUsage(16).setTongUsage(16);

                AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.CHAIN + tMaterial.getOreDicName(), tChainRecipe);

                if (!tMaterial.getIsBaseArmorMaterial())
                    continue;

                ItemStack tChestplateStack = MedievalArmorFactory.getInstance().buildNewMLAArmor(ArmorRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALCHESTPLATE), new HashMap<MLAAddon, Integer>(), tMaterial.getBaseDurability(References.InternalNames.Armor.MEDIEVALCHESTPLATE), tMaterial.getUniqueID());
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

                ItemStack tHelmetStack = MedievalArmorFactory.getInstance().buildNewMLAArmor(ArmorRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALHELMET), new HashMap<MLAAddon, Integer>(), tMaterial.getBaseDurability(References.InternalNames.Armor.MEDIEVALHELMET), tMaterial.getUniqueID());
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

                ItemStack tPantsStack = MedievalArmorFactory.getInstance().buildNewMLAArmor(ArmorRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALLEGGINGS), new HashMap<MLAAddon, Integer>(), tMaterial.getBaseDurability(References.InternalNames.Armor.MEDIEVALLEGGINGS), tMaterial.getUniqueID());
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

                ItemStack tShoeStack = MedievalArmorFactory.getInstance().buildNewMLAArmor(ArmorRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALSHOES), new HashMap<MLAAddon, Integer>(), tMaterial.getBaseDurability(References.InternalNames.Armor.MEDIEVALSHOES), tMaterial.getUniqueID());
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
                    ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval) ArmorRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALHELMET).getAddon(References.InternalNames.Upgrades.Helmet.TOP + "-" + tMaterial.getUniqueID());
                    ItemStack tUpgradeStack = new ItemStack(ModItems.armorComponent, 1);
                    NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgrade.getUniqueMaterialID());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, tUpgrade.getMaterialIndependentID());

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
                    ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval) ArmorRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALHELMET).getAddon(References.InternalNames.Upgrades.Helmet.LEFT + "-" + tMaterial.getUniqueID());
                    ItemStack tUpgradeStack = new ItemStack(ModItems.armorComponent, 1);
                    NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgrade.getUniqueMaterialID());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, tUpgrade.getMaterialIndependentID());

                    tUpgradeStack.setTagCompound(pUpgradeCompound);

                    AnvilRecipe tRecipe = new AnvilRecipe()
                            .setCraftingSlotContent(13, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(18, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setCraftingSlotContent(23, new HeatedAnvilRecipeComponent(tMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.NUGGET, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() * 0.5F * 0.95F))
                            .setResult(tUpgradeStack).setHammerUsage((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() / 300).setTongUsage((int) (MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() - 1000) / 300).setProgress((int) MaterialRegistry.getInstance().getMaterial(tMaterial.getUniqueID()).getMeltingPoint() / 100);

                    AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.HELMETLEFT + tMaterial.getOreDicName(), tRecipe);
                }

                if (MedievalAddonRegistry.getInstance().getPartStateForMaterial(tMaterial, References.InternalNames.Upgrades.Helmet.RIGHT)) {
                    ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval) ArmorRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALHELMET).getAddon(References.InternalNames.Upgrades.Helmet.RIGHT + "-" + tMaterial.getUniqueID());
                    ItemStack tUpgradeStack = new ItemStack(ModItems.armorComponent, 1);
                    NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgrade.getUniqueMaterialID());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, tUpgrade.getMaterialIndependentID());

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
                    ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval) ArmorRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALCHESTPLATE).getAddon(References.InternalNames.Upgrades.Chestplate.SHOULDERLEFT + "-" + tMaterial.getUniqueID());
                    ItemStack tUpgradeStack = new ItemStack(ModItems.armorComponent, 1);
                    NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgrade.getUniqueMaterialID());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, tUpgrade.getMaterialIndependentID());

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
                    ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval) ArmorRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALCHESTPLATE).getAddon(References.InternalNames.Upgrades.Chestplate.SHOULDERRIGHT + "-" + tMaterial.getUniqueID());
                    ItemStack tUpgradeStack = new ItemStack(ModItems.armorComponent, 1);
                    NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgrade.getUniqueMaterialID());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, tUpgrade.getMaterialIndependentID());

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
                    ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval) ArmorRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALCHESTPLATE).getAddon(References.InternalNames.Upgrades.Chestplate.BACKRIGHT + "-" + tMaterial.getUniqueID());
                    ItemStack tUpgradeStack = new ItemStack(ModItems.armorComponent, 1);
                    NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgrade.getUniqueMaterialID());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, tUpgrade.getMaterialIndependentID());

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
                    ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval) ArmorRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALCHESTPLATE).getAddon(References.InternalNames.Upgrades.Chestplate.BACKLEFT + "-" + tMaterial.getUniqueID());
                    ItemStack tUpgradeStack = new ItemStack(ModItems.armorComponent, 1);
                    NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgrade.getUniqueMaterialID());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, tUpgrade.getMaterialIndependentID());

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
                    ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval) ArmorRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALCHESTPLATE).getAddon(References.InternalNames.Upgrades.Chestplate.FRONTRIGHT + "-" + tMaterial.getUniqueID());
                    ItemStack tUpgradeStack = new ItemStack(ModItems.armorComponent, 1);
                    NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgrade.getUniqueMaterialID());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, tUpgrade.getMaterialIndependentID());

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
                    ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval) ArmorRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALCHESTPLATE).getAddon(References.InternalNames.Upgrades.Chestplate.FRONTLEFT + "-" + tMaterial.getUniqueID());
                    ItemStack tUpgradeStack = new ItemStack(ModItems.armorComponent, 1);
                    NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgrade.getUniqueMaterialID());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, tUpgrade.getMaterialIndependentID());

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
                    ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval) ArmorRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALLEGGINGS).getAddon(References.InternalNames.Upgrades.Leggings.BACKRIGHT + "-" + tMaterial.getUniqueID());
                    ItemStack tUpgradeStack = new ItemStack(ModItems.armorComponent, 1);
                    NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgrade.getUniqueMaterialID());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, tUpgrade.getMaterialIndependentID());

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
                    ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval) ArmorRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALLEGGINGS).getAddon(References.InternalNames.Upgrades.Leggings.BACKLEFT + "-" + tMaterial.getUniqueID());
                    ItemStack tUpgradeStack = new ItemStack(ModItems.armorComponent, 1);
                    NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgrade.getUniqueMaterialID());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, tUpgrade.getMaterialIndependentID());

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
                    ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval) ArmorRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALLEGGINGS).getAddon(References.InternalNames.Upgrades.Leggings.FRONTRIGHT + "-" + tMaterial.getUniqueID());
                    ItemStack tUpgradeStack = new ItemStack(ModItems.armorComponent, 1);
                    NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgrade.getUniqueMaterialID());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, tUpgrade.getMaterialIndependentID());

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
                    ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval) ArmorRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALLEGGINGS).getAddon(References.InternalNames.Upgrades.Leggings.FRONTLEFT + "-" + tMaterial.getUniqueID());
                    ItemStack tUpgradeStack = new ItemStack(ModItems.armorComponent, 1);
                    NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgrade.getUniqueMaterialID());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, tUpgrade.getMaterialIndependentID());

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
                    ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval) ArmorRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALSHOES).getAddon(References.InternalNames.Upgrades.Shoes.LEFT + "-" + tMaterial.getUniqueID());
                    ItemStack tUpgradeStack = new ItemStack(ModItems.armorComponent, 1);
                    NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgrade.getUniqueMaterialID());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, tUpgrade.getMaterialIndependentID());

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
                    ArmorUpgradeMedieval tUpgrade = (ArmorUpgradeMedieval) ArmorRegistry.getInstance().getArmor(References.InternalNames.Armor.MEDIEVALSHOES).getAddon(References.InternalNames.Upgrades.Shoes.RIGHT + "-" + tMaterial.getUniqueID());
                    ItemStack tUpgradeStack = new ItemStack(ModItems.armorComponent, 1);
                    NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgrade.getUniqueMaterialID());
                    pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, tUpgrade.getMaterialIndependentID());

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
                        ItemStack tUpgradeStack = new ItemStack(ModItems.armorComponent, 1);
                        NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgradeMaterial.getUniqueID());
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, References.InternalNames.AddonPositions.Helmet.TOP);

                        tUpgradeStack.setTagCompound(pUpgradeCompound);

                        AnvilRecipe tRecipe = new ArmorUpgradeAnvilRecipe(References.InternalNames.Armor.MEDIEVALHELMET, tArmorMaterial.getUniqueID())
                                .setCraftingSlotContent(6, new HeatedAnvilRecipeComponent(tArmorMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F))
                                .setCraftingSlotContent(8, new HeatedAnvilRecipeComponent(tArmorMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F))
                                .setUpgradeCraftingSlotComponent(2, new StandardAnvilRecipeComponent(tUpgradeStack))
                                .setProgress(8).setHammerUsage(5).setTongUsage(4);

                        AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.HELMETUPGRADETOP + tArmorMaterial.getOreDicName() + "." + tUpgradeMaterial.getOreDicName(), tRecipe);
                    }

                    if (MedievalAddonRegistry.getInstance().getPartStateForMaterial(tUpgradeMaterial, References.InternalNames.Upgrades.Helmet.LEFT)) {
                        ItemStack tUpgradeStack = new ItemStack(ModItems.armorComponent, 1);
                        NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgradeMaterial.getUniqueID());
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, References.InternalNames.Upgrades.Helmet.LEFT);

                        tUpgradeStack.setTagCompound(pUpgradeCompound);

                        AnvilRecipe tRecipe = new ArmorUpgradeAnvilRecipe(References.InternalNames.Armor.MEDIEVALHELMET, tArmorMaterial.getUniqueID())
                                .setCraftingSlotContent(6, new HeatedAnvilRecipeComponent(tArmorMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F))
                                .setCraftingSlotContent(16, new HeatedAnvilRecipeComponent(tArmorMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F))
                                .setUpgradeCraftingSlotComponent(10, new StandardAnvilRecipeComponent(tUpgradeStack))
                                .setProgress(8).setHammerUsage(5).setTongUsage(4);

                        AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.HELMETUPGRADELEFT + tArmorMaterial.getOreDicName() + "." + tUpgradeMaterial.getOreDicName(), tRecipe);
                    }

                    if (MedievalAddonRegistry.getInstance().getPartStateForMaterial(tUpgradeMaterial, References.InternalNames.Upgrades.Helmet.RIGHT)) {
                        ItemStack tUpgradeStack = new ItemStack(ModItems.armorComponent, 1);
                        NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgradeMaterial.getUniqueID());
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, References.InternalNames.Upgrades.Helmet.RIGHT);

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
                        ItemStack tUpgradeStack = new ItemStack(ModItems.armorComponent, 1);
                        NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgradeMaterial.getUniqueID());
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, References.InternalNames.AddonPositions.Chestplate.SHOULDERLEFT);

                        tUpgradeStack.setTagCompound(pUpgradeCompound);

                        AnvilRecipe tRecipe = new ArmorUpgradeAnvilRecipe(References.InternalNames.Armor.MEDIEVALCHESTPLATE, tArmorMaterial.getUniqueID())
                                .setCraftingSlotContent(7, new HeatedAnvilRecipeComponent(tArmorMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F))
                                .setCraftingSlotContent(11, new HeatedAnvilRecipeComponent(tArmorMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F))
                                .setUpgradeCraftingSlotComponent(6, new StandardAnvilRecipeComponent(tUpgradeStack))
                                .setProgress(12).setHammerUsage(7).setTongUsage(8);

                        AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.CHESTPLATEUPGRADESHOULDERLEFT + tArmorMaterial.getOreDicName() + "." + tUpgradeMaterial.getOreDicName(), tRecipe);
                    }

                    if (MedievalAddonRegistry.getInstance().getPartStateForMaterial(tUpgradeMaterial, References.InternalNames.Upgrades.Chestplate.SHOULDERRIGHT)) {
                        ItemStack tUpgradeStack = new ItemStack(ModItems.armorComponent, 1);
                        NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgradeMaterial.getUniqueID());
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, References.InternalNames.AddonPositions.Chestplate.SHOULDERRIGHT);

                        tUpgradeStack.setTagCompound(pUpgradeCompound);

                        AnvilRecipe tRecipe = new ArmorUpgradeAnvilRecipe(References.InternalNames.Armor.MEDIEVALCHESTPLATE, tArmorMaterial.getUniqueID())
                                .setCraftingSlotContent(7, new HeatedAnvilRecipeComponent(tArmorMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F))
                                .setCraftingSlotContent(13, new HeatedAnvilRecipeComponent(tArmorMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F))
                                .setUpgradeCraftingSlotComponent(8, new StandardAnvilRecipeComponent(tUpgradeStack))
                                .setProgress(12).setHammerUsage(7).setTongUsage(8);

                        AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.CHESTPLATEUPGRADESHOULDERRIGHT + tArmorMaterial.getOreDicName() + "." + tUpgradeMaterial.getOreDicName(), tRecipe);
                    }

                    if (MedievalAddonRegistry.getInstance().getPartStateForMaterial(tUpgradeMaterial, References.InternalNames.Upgrades.Chestplate.BACKRIGHT)) {
                        ItemStack tUpgradeStack = new ItemStack(ModItems.armorComponent, 1);
                        NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgradeMaterial.getUniqueID());
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, References.InternalNames.AddonPositions.Chestplate.BACKRIGHT );

                        tUpgradeStack.setTagCompound(pUpgradeCompound);

                        AnvilRecipe tRecipe = new ArmorUpgradeAnvilRecipe(References.InternalNames.Armor.MEDIEVALCHESTPLATE, tArmorMaterial.getUniqueID())
                                .setCraftingSlotContent(13, new HeatedAnvilRecipeComponent(tArmorMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F))
                                .setCraftingSlotContent(17, new HeatedAnvilRecipeComponent(tArmorMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F))
                                .setUpgradeCraftingSlotComponent(18, new StandardAnvilRecipeComponent(tUpgradeStack))
                                .setProgress(12).setHammerUsage(7).setTongUsage(8);

                        AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.CHESTPLATEUPGRADEBACKRIGHT + tArmorMaterial.getOreDicName() + "." + tUpgradeMaterial.getOreDicName(), tRecipe);
                    }

                    if (MedievalAddonRegistry.getInstance().getPartStateForMaterial(tUpgradeMaterial, References.InternalNames.Upgrades.Chestplate.BACKLEFT)) {
                        ItemStack tUpgradeStack = new ItemStack(ModItems.armorComponent, 1);
                        NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgradeMaterial.getUniqueID());
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, References.InternalNames.AddonPositions.Chestplate.BACKLEFT );

                        tUpgradeStack.setTagCompound(pUpgradeCompound);

                        AnvilRecipe tRecipe = new ArmorUpgradeAnvilRecipe(References.InternalNames.Armor.MEDIEVALCHESTPLATE, tArmorMaterial.getUniqueID())
                                .setCraftingSlotContent(11, new HeatedAnvilRecipeComponent(tArmorMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F))
                                .setCraftingSlotContent(17, new HeatedAnvilRecipeComponent(tArmorMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F))
                                .setUpgradeCraftingSlotComponent(16, new StandardAnvilRecipeComponent(tUpgradeStack))
                                .setProgress(12).setHammerUsage(7).setTongUsage(8);

                        AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.CHESTPLATEUPGRADEBACKLEFT + tArmorMaterial.getOreDicName() + "." + tUpgradeMaterial.getOreDicName(), tRecipe);
                    }

                    if (MedievalAddonRegistry.getInstance().getPartStateForMaterial(tUpgradeMaterial, References.InternalNames.Upgrades.Chestplate.FRONTRIGHT)) {
                        ItemStack tUpgradeStack = new ItemStack(ModItems.armorComponent, 1);
                        NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgradeMaterial.getUniqueID());
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, References.InternalNames.AddonPositions.Chestplate.FRONTRIGHT );

                        tUpgradeStack.setTagCompound(pUpgradeCompound);

                        AnvilRecipe tRecipe = new ArmorUpgradeAnvilRecipe(References.InternalNames.Armor.MEDIEVALCHESTPLATE, tArmorMaterial.getUniqueID())
                                .setCraftingSlotContent(13, new HeatedAnvilRecipeComponent(tArmorMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F))
                                .setCraftingSlotContent(17, new HeatedAnvilRecipeComponent(tArmorMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F))
                                .setUpgradeCraftingSlotComponent(18, new StandardAnvilRecipeComponent(tUpgradeStack))
                                .setProgress(12).setHammerUsage(7).setTongUsage(8);

                        AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.CHESTPLATEUPGRADEFRONTRIGHT + tArmorMaterial.getOreDicName() + "." + tUpgradeMaterial.getOreDicName(), tRecipe);
                    }

                    if (MedievalAddonRegistry.getInstance().getPartStateForMaterial(tUpgradeMaterial, References.InternalNames.Upgrades.Chestplate.FRONTLEFT)) {
                        ItemStack tUpgradeStack = new ItemStack(ModItems.armorComponent, 1);
                        NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgradeMaterial.getUniqueID());
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, References.InternalNames.AddonPositions.Chestplate.FRONTLEFT );

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
                        ItemStack tUpgradeStack = new ItemStack(ModItems.armorComponent, 1);
                        NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgradeMaterial.getUniqueID());
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, References.InternalNames.AddonPositions.Leggings.BACKRIGHT );

                        tUpgradeStack.setTagCompound(pUpgradeCompound);

                        AnvilRecipe tRecipe = new ArmorUpgradeAnvilRecipe(References.InternalNames.Armor.MEDIEVALLEGGINGS, tArmorMaterial.getUniqueID())
                                .setCraftingSlotContent(13, new HeatedAnvilRecipeComponent(tArmorMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F))
                                .setCraftingSlotContent(17, new HeatedAnvilRecipeComponent(tArmorMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F))
                                .setUpgradeCraftingSlotComponent(18, new StandardAnvilRecipeComponent(tUpgradeStack))
                                .setProgress(12).setHammerUsage(7).setTongUsage(6);

                        AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.LEGGINGSUPGRADEBACKRIGHT + tArmorMaterial.getOreDicName() + "." + tUpgradeMaterial.getOreDicName(), tRecipe);
                    }

                    if (MedievalAddonRegistry.getInstance().getPartStateForMaterial(tUpgradeMaterial, References.InternalNames.Upgrades.Leggings.BACKLEFT)) {
                        ItemStack tUpgradeStack = new ItemStack(ModItems.armorComponent, 1);
                        NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgradeMaterial.getUniqueID());
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, References.InternalNames.AddonPositions.Leggings.BACKLEFT );

                        tUpgradeStack.setTagCompound(pUpgradeCompound);

                        AnvilRecipe tRecipe = new ArmorUpgradeAnvilRecipe(References.InternalNames.Armor.MEDIEVALLEGGINGS, tArmorMaterial.getUniqueID())
                                .setCraftingSlotContent(11, new HeatedAnvilRecipeComponent(tArmorMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F))
                                .setCraftingSlotContent(17, new HeatedAnvilRecipeComponent(tArmorMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F))
                                .setUpgradeCraftingSlotComponent(16, new StandardAnvilRecipeComponent(tUpgradeStack))
                                .setProgress(12).setHammerUsage(7).setTongUsage(6);

                        AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.LEGGINGSUPGRADEBACKLEFT + tArmorMaterial.getOreDicName() + "." + tUpgradeMaterial.getOreDicName(), tRecipe);
                    }

                    if (MedievalAddonRegistry.getInstance().getPartStateForMaterial(tUpgradeMaterial, References.InternalNames.Upgrades.Leggings.FRONTRIGHT)) {
                        ItemStack tUpgradeStack = new ItemStack(ModItems.armorComponent, 1);
                        NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgradeMaterial.getUniqueID());
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, References.InternalNames.AddonPositions.Leggings.FRONTRIGHT );

                        tUpgradeStack.setTagCompound(pUpgradeCompound);

                        AnvilRecipe tRecipe = new ArmorUpgradeAnvilRecipe(References.InternalNames.Armor.MEDIEVALLEGGINGS, tArmorMaterial.getUniqueID())
                                .setCraftingSlotContent(13, new HeatedAnvilRecipeComponent(tArmorMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F))
                                .setCraftingSlotContent(7, new HeatedAnvilRecipeComponent(tArmorMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F))
                                .setUpgradeCraftingSlotComponent(8, new StandardAnvilRecipeComponent(tUpgradeStack))
                                .setProgress(12).setHammerUsage(7).setTongUsage(6);

                        AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.LEGGINGSUPGRADEFRONTRIGHT + tArmorMaterial.getOreDicName() + "." + tUpgradeMaterial.getOreDicName(), tRecipe);
                    }

                    if (MedievalAddonRegistry.getInstance().getPartStateForMaterial(tUpgradeMaterial, References.InternalNames.Upgrades.Leggings.FRONTLEFT)) {
                        ItemStack tUpgradeStack = new ItemStack(ModItems.armorComponent, 1);
                        NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgradeMaterial.getUniqueID());
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, References.InternalNames.AddonPositions.Leggings.FRONTLEFT );

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
                        ItemStack tUpgradeStack = new ItemStack(ModItems.armorComponent, 1);
                        NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgradeMaterial.getUniqueID());
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, References.InternalNames.AddonPositions.Shoes.LEFT );

                        tUpgradeStack.setTagCompound(pUpgradeCompound);

                        AnvilRecipe tRecipe = new ArmorUpgradeAnvilRecipe(References.InternalNames.Armor.MEDIEVALSHOES, tArmorMaterial.getUniqueID())
                                .setCraftingSlotContent(11, new HeatedAnvilRecipeComponent(tArmorMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F))
                                .setCraftingSlotContent(7, new HeatedAnvilRecipeComponent(tArmorMaterial.getUniqueID(), References.InternalNames.HeatedItemTypes.RING, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.85F, MaterialRegistry.getInstance().getMaterial(References.InternalNames.Materials.Vanilla.IRON).getMeltingPoint() * 0.5F * 0.95F))
                                .setUpgradeCraftingSlotComponent(6, new StandardAnvilRecipeComponent(tUpgradeStack))
                                .setProgress(8).setHammerUsage(4).setTongUsage(5);

                        AnvilRecipeRegistry.getInstance().addRecipe(References.InternalNames.Recipes.Anvil.SHOESUPGRADELEFT + tArmorMaterial.getOreDicName() + "." + tUpgradeMaterial.getOreDicName(), tRecipe);
                    }

                    if (MedievalAddonRegistry.getInstance().getPartStateForMaterial(tUpgradeMaterial, References.InternalNames.Upgrades.Shoes.RIGHT)) {
                        ItemStack tUpgradeStack = new ItemStack(ModItems.armorComponent, 1);
                        NBTTagCompound pUpgradeCompound = new NBTTagCompound();
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Material, tUpgradeMaterial.getUniqueID());
                        pUpgradeCompound.setString(References.NBTTagCompoundData.Addons.AddonID, References.InternalNames.AddonPositions.Shoes.RIGHT );

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
        public static void registerAnvilMaterials()
        {
            AnvilMaterialRegistry.getInstance().registerNewAnvilMaterial(new AnvilMaterial(References.InternalNames.Materials.Anvil.STONE, 250, I18n.format(TranslationKeys.Materials.Anvil.Stone)));
            AnvilMaterialRegistry.getInstance().registerNewAnvilMaterial(new AnvilMaterial(References.InternalNames.Materials.Anvil.IRON, 1500, I18n.format(TranslationKeys.Materials.Anvil.Iron)));
            AnvilMaterialRegistry.getInstance().registerNewAnvilMaterial(new AnvilMaterial(References.InternalNames.Materials.Anvil.OBSIDIAN, 2200, I18n.format(TranslationKeys.Materials.Anvil.Obsidian)));
        }
    }

    public static class SystemInit {
        public static void registerBlocks() {
            ModBlocks.blockForge = new BlockForge();
            ModBlocks.blockBlackSmithsAnvil = new BlockBlackSmithsAnvil();
            ModBlocks.blockFirePlace = new BlockFirePlace();
            ModBlocks.blockConduit = new BlockConduit();
            ModBlocks.blockMoltenMetalTank = new BlockMoltenMetalTank();

            GameRegistry.register(ModBlocks.blockForge);
            GameRegistry.register(new ItemBlock(ModBlocks.blockForge).setRegistryName(ModBlocks.blockForge.getRegistryName()));
            GameRegistry.register(ModBlocks.blockBlackSmithsAnvil);
            GameRegistry.register(new ItemBlockBlackSmithsAnvil(ModBlocks.blockBlackSmithsAnvil));
            GameRegistry.register(ModBlocks.blockFirePlace);
            GameRegistry.register(new ItemBlock(ModBlocks.blockFirePlace).setRegistryName(ModBlocks.blockFirePlace.getRegistryName()));
            GameRegistry.register(ModBlocks.blockConduit);
            GameRegistry.register(new ItemBlock(ModBlocks.blockConduit).setRegistryName(ModBlocks.blockConduit.getRegistryName()));
            GameRegistry.register(ModBlocks.blockMoltenMetalTank);
            GameRegistry.register(new ItemBlock(ModBlocks.blockMoltenMetalTank).setRegistryName(ModBlocks.blockMoltenMetalTank.getRegistryName()));
        }

        public static void registerItems() {
            ModItems.heatedItem = new ItemHeatedItem();
            ModItems.guide = new ItemSmithingsGuide();
            ModItems.armorComponent = new ItemArmorComponent();
            ModItems.tongs = new ItemTongs();
            ModItems.hammer = new ItemHammer();
            ModItems.metalRing = new ItemMetalRing();
            ModItems.metalChain = new ItemMetalChain();
            ModItems.metalNugget = new ItemNugget();
            ModItems.metalPlate = new ItemPlate();

            ArmorRegistry.getInstance().getAllRegisteredArmors().values().forEach(GameRegistry::register);

            GameRegistry.register(ModItems.heatedItem);
            GameRegistry.register(ModItems.guide);
            GameRegistry.register(ModItems.armorComponent);
            GameRegistry.register(ModItems.tongs);
            GameRegistry.register(ModItems.hammer);
            GameRegistry.register(ModItems.metalRing);
            GameRegistry.register(ModItems.metalChain);
            GameRegistry.register(ModItems.metalNugget);
            GameRegistry.register(ModItems.metalPlate);
        }

        public static void registerFluids() {
            ModFluids.moltenMetal = new FluidMoltenMetal();

            FluidRegistry.registerFluid(ModFluids.moltenMetal);

            //Makes sure that for Obsidian lava is produced instead of a molten metal.
            SmithsCore.getRegistry().getCommonBus().register(new ObsidianToLavaSetter());
        }

        public static void registerTileEntities() {
            GameRegistry.registerTileEntity(TileEntityForge.class, References.InternalNames.TileEntities.ForgeContainer);
            GameRegistry.registerTileEntity(TileEntityFireplace.class, References.InternalNames.TileEntities.FireplaceContainer);
            GameRegistry.registerTileEntity(TileEntityBlackSmithsAnvil.class, References.InternalNames.TileEntities.ArmorsAnvil);
            GameRegistry.registerTileEntity(TileEntityConduit.class, References.InternalNames.TileEntities.Conduit);
            GameRegistry.registerTileEntity(TileEntityMoltenMetalTank.class, References.InternalNames.TileEntities.Tank);
        }

        public static void registerCreativeTabs() {
            ModCreativeTabs.generalTab = new GeneralTabs();
            ModCreativeTabs.componentsTab = new ComponentsTab();
            ModCreativeTabs.heatedItemTab = new HeatedItemTab();
            ModCreativeTabs.armorTab = new ArmorTab();
        }

        public static void loadMaterialConfig() {
            ModLogger.getInstance().info("Started loading custom ArmorMaterial Values from Config.");
            ArmorDataConfigHandler configHandler = new ArmorDataConfigHandler();

            configHandler.loadIDs();
            configHandler.loadIsBaseArmorMaterial();
            configHandler.loadTemperatureCoefficient();
            configHandler.loadMeltingPoint();
            configHandler.loadActiveParts();
            configHandler.loadBaseDamageAbsorptions();
            configHandler.loadPartModifiers();
            configHandler.loadBaseDurability();

            ModLogger.getInstance().info("Loading of the custom ArmorMaterial Values has succesfully been performed!");
        }

        public static void removeRecipes() {
            if (!ArmoryConfig.enableHardModeNuggetRemoval)
                return;

            ListIterator<IRecipe> iterator = CraftingManager.getInstance().getRecipeList().listIterator();
            while (iterator.hasNext()) {
                IRecipe recipe = iterator.next();
                tryRemoveRecipeFromGame(recipe, iterator);
            }
        }

        public static void tryRemoveRecipeFromGame(IRecipe recipe, Iterator iterator) {
            if (recipe.getRecipeOutput() == null)
                return;

            if (recipe.getRecipeOutput().getItem() == null)
                return;

            int[] oreIds = OreDictionary.getOreIDs(recipe.getRecipeOutput());

            for (int Id : oreIds) {
                String oreName = OreDictionary.getOreName(Id);
                if (oreName.contains("nugget")) {
                    for (IArmorMaterial material : MaterialRegistry.getInstance().getArmorMaterials().values()) {
                        if (oreName.toLowerCase().contains(material.getOreDicName().toLowerCase())) {
                            try {
                                iterator.remove();
                                return;
                            } catch (IllegalStateException ex) {
                                ModLogger.getInstance().info("Could not remove recipe of: " + ItemStackHelper.toString(recipe.getRecipeOutput()));
                            }
                        }
                    }
                }
            }
        }

        public static void initializeOreDict() {
            ArrayList<ItemStack> chains = new ArrayList<ItemStack>();
            ArrayList<ItemStack> rings = new ArrayList<ItemStack>();
            ArrayList<ItemStack> plates = new ArrayList<ItemStack>();
            ArrayList<ItemStack> nuggets = new ArrayList<ItemStack>();

            ModItems.metalChain.getSubItems(null, null, chains);
            ModItems.metalRing.getSubItems(null, null, rings);
            ModItems.metalPlate.getSubItems(null, null, plates);
            ModItems.metalNugget.getSubItems(null, null, nuggets);

            for (ItemStack chain : chains) {
                String material = chain.getTagCompound().getString(References.NBTTagCompoundData.Material);
                OreDictionary.registerOre("chain" + MaterialRegistry.getInstance().getMaterial(material).getOreDicName(), chain);
            }

            for (ItemStack ring : rings) {
                String material = ring.getTagCompound().getString(References.NBTTagCompoundData.Material);
                OreDictionary.registerOre("ring" + MaterialRegistry.getInstance().getMaterial(material).getOreDicName(), ring);
            }

            for (ItemStack plate : plates) {
                String material = plate.getTagCompound().getString(References.NBTTagCompoundData.Material);
                OreDictionary.registerOre("plate" + MaterialRegistry.getInstance().getMaterial(material).getOreDicName(), plate);
            }

            for (ItemStack nugget : nuggets) {
                String material = nugget.getTagCompound().getString(References.NBTTagCompoundData.Material);
                OreDictionary.registerOre("nugget" + MaterialRegistry.getInstance().getMaterial(material).getOreDicName(), nugget);
            }
        }
    }

}
