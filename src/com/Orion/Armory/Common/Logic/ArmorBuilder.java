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
        //Checking if this is a clean armor without any modifiers, if it is already modified send it to be remodified
        //If not set its CleanArmor value to false
        if (pBaseArmor.stackTagCompound.getBoolean("CleanArmor") == false)
        {
            return modifyArmor(pBaseArmor, pUpgrades, pModifiers);
        }

        // Checking if the armor stacks need modification
        if ((pUpgrades.length == 0) && (pModifiers.length == 0))
        {
            return pBaseArmor;
        }

        //TODO: Addin a check for a valid recipe. If not return an empty ItemStack. Else proceed

        //Creating the new NBT Tag for the armor
        //Setting the state of the armor to modified
        NBTTagCompound tBaseCompound = pBaseArmor.getTagCompound();
        tBaseCompound.setBoolean("CleanArmor", false);

        //Adding the upgrades if there are upgrades in the list
        if (pUpgrades.length > 0)
        {
            tBaseCompound.setTag("Upgrades", new NBTTagCompound());

            int upgradeCounter = 1;
            for (ArmorUpgrade tUpgrade : pUpgrades) {
                tBaseCompound.getCompoundTag("Upgrades").setTag("Upgrade" + upgradeCounter, tUpgrade.createNBTTagCompound());
                upgradeCounter++;
            }
        }



    }

    public ItemStack modifyArmor(ItemStack pBaseArmor, ArmorUpgrade[] pUpgrades, ArmorModifier[] pModifiers)
    {

    }





}
