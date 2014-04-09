package com.Orion.Armory.Common.Logic;
/*
*   ArmorBuilder
*   Created by: Orion
*   Created on: 4-4-2014
*/

import com.Orion.Armory.Common.ARegistry;
import com.Orion.Armory.Common.Armor.ArmorCore;
import com.Orion.Armory.Common.Armor.Modifiers.ArmorModifier;
import com.Orion.Armory.Common.Armor.ArmorUpgrade;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;

import java.util.ArrayList;
import java.util.Arrays;

public class ArmorBuilder
{
    //TODO: Create the build/modify functions
    private static ArmorBuilder instance;

    public static void init()
    {
       instance = new ArmorBuilder();
    }

    public ItemStack buildArmor(ItemStack pBaseArmor, ArrayList<ArmorUpgrade> pUpgrades, ArrayList<ArmorModifier> pModifiers)
    {
        //Check if the given Armor needs to be modified.
        if (pUpgrades.isEmpty() && pModifiers.isEmpty())
        {
            return pBaseArmor;
        }

        //Grab the base component of the NBT Tag
        NBTTagCompound tBaseCompound = pBaseArmor.getTagCompound();
        ArrayList<ArmorModifier> tInstalledModifiers = ARegistry.iInstance.getInstalledModifiersOnItemStack(pBaseArmor);

        //Check if the maximum amount of modifiers is already installed
        if (tBaseCompound.getInteger("MaxModifiers") == tInstalledModifiers.size())
        {
            return null;
        }

        //Check if the new modifiers and upgrades are valid if not return an empty stack.
        for (ArmorUpgrade tUpgrade: pUpgrades)
        {
           if (!tUpgrade.validateCraftingForThisUpgrade(ARegistry.iInstance.getInstalledArmorUpgradesOnItemStack(pBaseArmor), pUpgrades))
           {
               return null;
           }
        }

        for (ArmorModifier tModifier: pModifiers)
        {
            if (!tModifier.validateCraftingForThisModifier(tInstalledModifiers, pModifiers))
            {
                return null;
            }
        }

        ArmorCore tCore = (ArmorCore) pBaseArmor.getItem();
        ItemStack tReturnStack = new ItemStack(tCore);

        //Create the parameters for the new NBTTag compound
        int tNewMaxDurability = tBaseCompound.getInteger("MaxDurability");
        int tNewMaxAbsorption = tBaseCompound.getInteger("BaseDamageAbsorption");
        for (ArmorUpgrade tUpgrade: pUpgrades)
        {
            tNewMaxDurability += tUpgrade.iExtraDurability;
            tNewMaxAbsorption += (tBaseCompound.getInteger("BaseDamageAbsorption") * tUpgrade.iProtection);
        }









        return tReturnStack;
    }

    public ItemStack modifyArmor(ItemStack pBaseArmor, ArmorUpgrade[] pUpgrades, ArmorModifier[] pModifiers)
    {
        return null;
    }




}
