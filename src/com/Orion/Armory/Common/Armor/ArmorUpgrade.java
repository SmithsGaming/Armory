package com.Orion.Armory.Common.Armor;

import net.minecraft.item.Item;
import net.minecraft.item.ItemArmor.*;

/**
 * Created by Marc on 27-3-2014.
 */
public class ArmorUpgrade
{
    public static ArmorMaterial iMaterial;
    public static String iIconLocation;
    public static String iModelTextureLocation;
    public static int iExtraProtection;
    public static int iMaxUpgrade;
    public static Item iBaseItem;
    public static int iTargetArmorID;


    public ArmorUpgrade(ArmorMaterial pMaterial, String pIconLocation, String pModelTextureLocation, int pExtraProtection, int pMaxUpgrade, Item pBaseItem, int pTargetArmorID)
    {
        iMaterial = pMaterial;
        iIconLocation = pIconLocation;
        iModelTextureLocation = pModelTextureLocation;
        iExtraProtection = pExtraProtection;
        iMaxUpgrade = pMaxUpgrade;
        iBaseItem = pBaseItem;
        iTargetArmorID = pTargetArmorID;
    }
}
