package com.Orion.Armory.Common.Armor;

import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor;
import net.minecraft.item.ItemArmor.*;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.common.util.EnumHelper;

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
    public static int iMaxUpgrades;

    //Constructors
    public ArmorUpgrade(int pMaterialID, int pTargetArmorID, int pUpgradeLocation, String pInternalName, String pVisibleName, String pVisibleNameColor, float pProtection, int pMaxUpgrades)
    {
        iMaterialID = pMaterialID;
        iTargetArmorID = pTargetArmorID;
        iUpgradeLocation = pUpgradeLocation;
        iInternalName = pInternalName;
        iVisibleName = pVisibleName;
        iVisibleNameColor = pVisibleNameColor;
        iProtection = pProtection;
        iMaxUpgrades = pMaxUpgrades;
    }
}
