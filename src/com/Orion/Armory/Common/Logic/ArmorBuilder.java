package com.Orion.Armory.Common.Logic;
/*
*   ArmorBuilder
*   Created by: Orion
*   Created on: 4-4-2014
*/

import com.Orion.Armory.Common.Armor.Modifiers.ArmorModifier;
import com.Orion.Armory.Common.Armor.ArmorUpgrade;
import net.minecraft.item.ItemStack;

public class ArmorBuilder
{
    //TODO: Create the build/modify functions
    private static ArmorBuilder instance;

    public static void init()
    {
       instance = new ArmorBuilder();
    }

    public ItemStack buildArmor(ItemStack pBaseArmor, ArmorUpgrade[] pUpgrades, ArmorModifier[] pModifiers)
    {
        return null;
    }

    public ItemStack modifyArmor(ItemStack pBaseArmor, ArmorUpgrade[] pUpgrades, ArmorModifier[] pModifiers)
    {
        return null;
    }





}
