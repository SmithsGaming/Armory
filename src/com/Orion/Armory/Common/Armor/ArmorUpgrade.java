package com.Orion.Armory.Common.Armor;

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


    public ArmorUpgrade(ArmorMaterial pMaterial, String pIconLocation, String pModelTextureLocation, int pExtraProtection, int pMaxUpgrade)
    {
        iMaterial = pMaterial;
        iIconLocation = pIconLocation;
        iModelTextureLocation = pModelTextureLocation;
        iExtraProtection = pExtraProtection;
        iMaxUpgrade = pMaxUpgrade;
    }

    public ArmorMaterial getMaterial()
    {
        return iMaterial;
    }

    public String getIconLocation()
    {
        return iIconLocation;
    }

    public String getModelTextureLocation()
    {
        return iModelTextureLocation;
    }

    public int getExtraProtection()
    {
        return iExtraProtection;
    }

    public int getMaxUpgrade()
    {
        return iMaxUpgrade;
    }
}
