package com.Orion.Armory.Common.Logic;
/*
*   ArmorBuilder
*   Created by: Orion
*   Created on: 4-4-2014
*/

import com.Orion.Armory.Common.Armor.ArmorCore;
import com.Orion.Armory.Common.Armor.ArmorModifier;
import com.Orion.Armory.Common.Armor.ArmorUpgrade;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.List;

public class ArmorBuilder
{
    public ArmorBuilder instance;

    public void init()
    {
       instance = new ArmorBuilder();
    }

    public ItemStack buildArmor(ItemStack pBaseArmor, ArmorUpgrade[] pUpgrades, ArmorModifier[] pModifiers)
    {
        if (pBaseArmor.stackTagCompound.getBoolean("CleanArmor") == false)
        {
            return modifyArmor(pBaseArmor, pUpgrades, pModifiers);
        }

        NBTTagCompound tBaseCompound = pBaseArmor.getTagCompound();
        tBaseCompound.setBoolean("CleanArmor", false);

    }

    public ItemStack modifyArmor(ItemStack pBaseArmor, ArmorUpgrade[] pUpgrades, ArmorModifier[] pModifiers)
    {

    }





}
