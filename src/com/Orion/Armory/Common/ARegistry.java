package com.Orion.Armory.Common;

import com.Orion.Armory.Common.Armor.ArmorBase;
import com.Orion.Armory.Common.Armor.ArmorCore;
import com.Orion.Armory.Common.Armor.ArmorModifier;
import com.Orion.Armory.Common.Armor.ArmorUpgrade;
import cpw.mods.fml.common.registry.GameRegistry;
import mantle.lib.TabTools;
import net.minecraft.block.Block;
import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemStack;
import net.minecraftforge.common.util.EnumHelper;
import org.apache.logging.log4j.*;
import tconstruct.library.armor.ArmorMod;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Orion on 26-3-2014
 * Based off: TConstructRegistry
 */
public class ARegistry
{
    public static ARegistry instance = new ARegistry();
    public static Logger logger = LogManager.getLogger("Armory");

    // Tabs for the creative inventory
    public static TabTools tabArmoryArmor;
    public static TabTools tabArmoryComponents;

    /*Function regarding the management of these arrays and hashmaps*/

    /*Current Function adds
     *
     */
    public static void addTestArmor()
    {
        ItemArmor.ArmorMaterial testMaterial = EnumHelper.addArmorMaterial("test", 100, new int[]{3,7,6,4}, 25);
        ArmorUpgrade testUpgrade = new ArmorUpgrade(testMaterial, "tconstruct-armory:multiarmor/item/upgrades/testupgrade", "tconstruct-armory:textures/models/multiarmor/upgrades/shoulder.png", 4, 1);
        AUpgradeRepo.shoulderPads = testUpgrade;

        ArmorModifier testModifier = new ArmorModifier(Items.redstone, "tconstruct-armory:multiarmor/item/modifiers/redstone", "tconstruct-armory:textures/models/multiarmor/modifiers/redstone.png", "Speed", 1);
        AModifierRepo.speedUpgrade = testModifier;

        ArmorUpgrade[] upgrades = new ArmorUpgrade[1];
        upgrades[0] = testUpgrade;
        ArmorModifier[] modifiers = new ArmorModifier[1];
        modifiers[0] = testModifier;

        ArmorBase testArmor = (ArmorBase) new ArmorBase(testMaterial, 1, 100, "tconstruct-armory:multiarmor/item/base/chainmail_chestplate", "tconstruct-armory:textures/models/multiarmor/base/test_armor_model.png",Arrays.asList(upgrades), Arrays.asList(modifiers)).setUnlocalizedName("Armory.ArmorTest");
        AArmorRepo.testArmor = testArmor;

        GameRegistry.registerItem(testArmor, "Armorytest");
    }
}
