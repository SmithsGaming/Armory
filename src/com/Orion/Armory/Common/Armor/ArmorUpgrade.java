package com.Orion.Armory.Common.Armor;

import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.EnumHelper;

import java.util.ArrayList;

/**
 * Created by Orion on 27-3-2014.
 */
public class ArmorUpgrade
{
    public int iMaterialID;
    public int iTargetArmorID;
    public int iUpgradeLocation;
    public String iInternalName;
    public String iVisibleName;
    public String iVisibleNameColor;
    public String iTextureSuffix;
    public float iProtection;
    public int iExtraDurability;
    public int iMaxUpgrades;

    //Constructors
    public ArmorUpgrade(int pMaterialID, int pTargetArmorID, int pUpgradeLocation, String pInternalName, String pVisibleName, String pVisibleNameColor, String pTextureSuffix, float pProtection, int pExtraDurability,int pMaxUpgrades)
    {
        iMaterialID = pMaterialID;
        iTargetArmorID = pTargetArmorID;
        iUpgradeLocation = pUpgradeLocation;
        iInternalName = pInternalName;
        iVisibleName = pVisibleName;
        iVisibleNameColor = pVisibleNameColor;
        iTextureSuffix = pTextureSuffix;
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
