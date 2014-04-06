package com.Orion.Armory.Common.Armor;

import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.EnumHelper;

import java.util.ArrayList;

/**
 * Created by Marc on 27-3-2014.
 */
public class ArmorUpgrade
{
    public static int iMaterialID;
    public static int iTargetArmorID;
    public static int iUpgradeLocation;
    public static String iInternalName;
    public static String iVisibleName;
    public static String iVisibleNameColor;
    public static float iProtection;
    public static int iExtraDurability;
    public static int iMaxUpgrades;

    //Constructors
    public ArmorUpgrade(int pMaterialID, int pTargetArmorID, int pUpgradeLocation, String pInternalName, String pVisibleName, String pVisibleNameColor, float pProtection, int pExtraDurability,int pMaxUpgrades)
    {
        iMaterialID = pMaterialID;
        iTargetArmorID = pTargetArmorID;
        iUpgradeLocation = pUpgradeLocation;
        iInternalName = pInternalName;
        iVisibleName = pVisibleName;
        iVisibleNameColor = pVisibleNameColor;
        iProtection = pProtection;
        iExtraDurability = pExtraDurability;
        iMaxUpgrades = pMaxUpgrades;
    }

    public ArmorUpgrade getCopy()
    {
        return this;
    }

    public boolean validateCraftingForThisUpgrade(ArrayList<ArmorUpgrade> pInstalledUpgrades, ArrayList<ArmorUpgrade> pNewUpgrades)
    {
        int tInstalledAmount = 0;
        for (ArmorUpgrade tUpgrade : pInstalledUpgrades)
        {
            if (tUpgrade == this)
            {
                tInstalledAmount++;
            }
        }

        if (tInstalledAmount == iMaxUpgrades)
        {
            return false;
        }

        return true;
    }
}
